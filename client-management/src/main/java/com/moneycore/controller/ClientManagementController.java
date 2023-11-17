package com.moneycore.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.moneycore.bean.*;
import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import com.moneycore.model.Note;
import com.moneycore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.moneycore.email.EmailBuilder;
import com.moneycore.email.Mail;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.ClientsRepository;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/clientmanagement")
public class ClientManagementController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private ClientService clientService;

	@Autowired
	private FileStorageService storageService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ClientsRepository clientsRepo;

	@Autowired
	private EmailVerificationService emailVerificationService;

	@Value("${mail.from}")
	private String fromEmail;

	@Autowired
	private Mailer emailService;

	@Autowired
	private ProfilePictureService profilePictureService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private SpaceAccountDetailsService spaceAccountDetailsService;

	@Autowired
	private SpaceAccountsService spaceAccountsService;

	@Autowired
	private FirebaseMessagingService firebaseService;

	@Autowired
	private NotificationServiceDef notificationServiceDef;

	@RequestMapping(value = { "/internal/client/{email}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getClientByEmail(HttpServletRequest request,
																		@PathVariable("email") String email) {
		Client client = clientService.getClientByEmail(email);
		ResponseModel responseModel = new ResponseModel(true, "Client", client);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/client/id/{clientid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getClient(HttpServletRequest request,
																 @PathVariable("clientid") int clientId) {
		Client client = clientService.findByClientByID(clientId);
		ResponseModel responseModel = new ResponseModel(true, "Client", client);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/client/address/{clientid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getAddress(HttpServletRequest request,
																  @PathVariable("clientid") int clientId) {
		Client client = clientService.findByClientByID(clientId);
		Address address = addressService.findAddress(client.getAddress().getId());
		ResponseModel responseModel = new ResponseModel(true, "Address", address);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/client/{clientid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getClientByEmail(HttpServletRequest request,
																		@PathVariable("clientid") int clientId) {
		Client client = clientService.findByClientByID(clientId);
		ResponseModel responseModel = new ResponseModel(true, "Client", client);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/clients" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> clientlist(HttpServletRequest request) {
		List<Client> users = clientService.findAllClients();
		ResponseModel responseModel = new ResponseModel(true, "Client", users);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/client/phone/{phonenumber}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getClientByPhoneNumber(HttpServletRequest request,
																			  @PathVariable("phonenumber") String phoneNumber) {
		Client client = clientService.getClientByPhoneNumber(phoneNumber);
		ResponseModel responseModel = new ResponseModel(true, "Client", client);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> registerClient(HttpServletRequest request,
														  @RequestBody ClientRegister clientRegister) throws MessagingException {
		boolean checkMail = false;
		String password = null;
		List<Client> clientlist = clientService.findUserByEmail(clientRegister.getEmail());
		if (!clientlist.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.emailexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		clientlist = clientService.findUserByContact(clientRegister.getPrPhone1());
		if (!clientlist.isEmpty() && clientlist.get(0).getPrPhone1().equalsIgnoreCase(clientRegister.getPrPhone1())) {
			ResponseModel responseModel = new ResponseModel(false, "Customer already registered with this phone", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		InstitutionList institute = getInstitutionByCode(clientRegister.getInstitutionCode(), request);
		if (institute == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notmatched", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (clientRegister.getPassword() == null && "Admin".equalsIgnoreCase(clientRegister.getCreateSource())) {
			password = clientService.randomPassword();
			clientRegister.setPassword(password);
			checkMail = true;
		}

		Address address= null;
		if (clientRegister.getAddress() != null) {
			for (ClientAddress clientAddress : clientRegister.getAddress()) {
				address = addressService.saveAddress(clientAddress);
			}
		}

		Client clientCreated = clientService.insert(clientRegister, address, institute);
		if (checkMail) {
			Mail mail = new EmailBuilder().From(fromEmail).To(clientCreated.getEmail())
					.Template("clientregistration.html").AddContext("subject", "Money Core")
					.AddContext("password", password).AddContext("username", clientCreated.getEmail())
					.AddContext("email", clientCreated.getEmail()).Subject("Money Core Registration").createMail();
			emailService.sendMail(mail, true);
		}
		if (clientRegister.getId() != null) {
			for (ClientDocument document : clientRegister.getId()) {
				boolean status = storageService.checkFileExist(document.getIdNo());
                if(status){
					ResponseModel responseModel = new ResponseModel(false, "Document id number already exists", null);
					clientService.hardDelete(clientCreated.getClientCode());
					return ResponseEntity.accepted().body(responseModel);
				}else{
					storageService.saveDocument(clientCreated.getClientCode(), document);
				}
			}
		}
		/*Note note = new Note();
		note.setSubject("Your wallet account has been created, start using your wallet, cheers!");
		note.setContent("Your wallet account has been created, start using your wallet, cheers!");
		Map<String,String> data = new HashMap();
		data.put("Info","Your wallet account has been created, start using your wallet, cheers!");
		note.setData(data);

		String token=clientCreated.getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=clientCreated.getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(clientCreated.getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}*/
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.regsuccessfully", null), clientCreated.getClientCode());
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/validate" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateClient(@Valid @RequestBody Client client) {
		boolean emailCheck = false;
		boolean phoneCheck = false;
		String email = null;
		if (client.getEmail() != null) {
			List<Client> clientList = clientService.findUserByEmail(client.getEmail());
			if (clientList != null && !clientList.isEmpty()) {
				emailCheck = true;
			}
		}
		if (client.getPrPhone1() != null) {
			List<Client> contactVerify = clientService.findUserByContact(client.getPrPhone1());
			if (!contactVerify.isEmpty() && contactVerify != null) {
				for (Client c : contactVerify) {
					email = c.getEmail();
				}
				phoneCheck = true;
			}
		}
		if (emailCheck && phoneCheck) {
			ResponseModel responseModel = new ResponseModel(false,
					"Email address and mobile number already exists", null);
			return ResponseEntity.status(200).body(responseModel);
		} else if (emailCheck && !phoneCheck) {
			ResponseModel responseModel = new ResponseModel(false, "Username already exists", null);
			return ResponseEntity.status(200).body(responseModel);
		} else if (!emailCheck && phoneCheck) {
			ResponseModel responseModel = new ResponseModel(false, "Mobile number already exists", email);
			return ResponseEntity.status(200).body(responseModel);
		} else if (!emailCheck && !phoneCheck) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("client.wrongmobleoremail", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notvalid", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/passwordReset" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> passwordResest(@Valid @RequestBody Client client) {
		if (client.getEmail() != null) {
			Client updateClient = clientsRepo.findByEmail(client.getEmail());
			if (updateClient != null) {
				updateClient.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
				clientsRepo.save(updateClient);
				ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.successfullyupdated", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
			ResponseModel responseModel = new ResponseModel(false, "Customer does not exist with this email address",
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notvalid", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = {
			"/backoffice/addressdata" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateAddress() {
		List<Client> clients = clientService.findAllClients();
		Client client = clients.get(0);
		clientService.updateAddressToken(client, getAlphaNumericString(6));
		ResponseModel responseModel = new ResponseModel(true, "Updated Successfully", null);
		return ResponseEntity.status(500).body(responseModel);
	}


	@RequestMapping(value = {
			"/client/mpin/update" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> mpinUpdate(@Valid @RequestBody MpinUpdateInfo mpinUpdateInfo) throws MessagingException {

		Client client = clientService.findByClientByID(mpinUpdateInfo.getClientCode());
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String mPIN = clientService.updateMpin(mpinUpdateInfo);

		// 1 // send notification to client ============================================================================
		String mgsTitle = "MPIN updated alert";
		String mgsBody = "Your MPIN has been updated successfully, please re-login";
		Map<String,String> data = new HashMap();
		data.put("Info", mgsBody);
		Note note = new Note(mgsTitle, "Your MPIN has been updated successfully, updated MPIN " + mPIN, data);

		String token = client.getDeviceToken();
		Date sysdate = new Date();
		String insCodeValue = client.getInstitutionList().getInstitutionCode();
		String branchCodeValue = "";
		String clientCodeValue = String.valueOf(client.getClientCode());
		String userCreate = "";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);
				NotoficationService saveService = new NotoficationService(mgsTitle, mgsBody, insCodeValue, branchCodeValue, clientCodeValue, userCreate, sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService(mgsTitle, mgsBody, insCodeValue, branchCodeValue, clientCodeValue, userCreate, sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}
		} else{
			NotoficationService saveService = new NotoficationService(mgsTitle, mgsBody, insCodeValue, branchCodeValue, clientCodeValue, userCreate, sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}
		// 2 // Send Email to client ------------------------------------------
		Mail mail = new EmailBuilder().From(fromEmail).To(client.getEmail())
				.Template("mpinupdate.html")
				.AddContext("subject", "Account MPIN Update")
				.AddContext("updatepin", mPIN)
				.Subject("Account MPIN Update").createMail();
		emailService.sendMail(mail, true);
		// =============================================================================================================

		ResponseModel responseModel = new ResponseModel(true, "mpin updated successfully", null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/logout" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logoutCient(@Valid @RequestBody Client client) {
		Client clientRecord = clientService.findByClientByID(client.getClientCode());
		if (clientRecord != null) {
			clientService.saveClient(clientRecord, false);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.logout", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/phone/{clientcode}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateClientPhone(HttpServletRequest request, @PathVariable("clientcode") int clientCode,
											   @Valid @RequestBody PhoneUpdateInfo phoneUpdateInfo) {
		String newPhoneNumber = null;
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (phoneUpdateInfo.getOldPhoneNo() != null && client.getPrPhone1() != null && !client.getPrPhone1().equalsIgnoreCase(phoneUpdateInfo.getOldPhoneNo())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.wrongoldphone", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}else {
			if (phoneUpdateInfo.getOldPhoneNo() != null && client.getPrPhone1() != null) {
				List<Client> clientlist = clientService.findUserByContact(phoneUpdateInfo.getNewPhoneNo());
				if (!clientlist.isEmpty() && clientlist.get(0).getPrPhone1().equalsIgnoreCase(phoneUpdateInfo.getOldPhoneNo())) {
					ResponseModel responseModel = new ResponseModel(false, "Customer already registered with this phone", null);
					return ResponseEntity.accepted().body(responseModel);
				}
				client.setPrPhone1(phoneUpdateInfo.getNewPhoneNo());
				newPhoneNumber = phoneUpdateInfo.getNewPhoneNo();
			} else if (phoneUpdateInfo.getOldPhoneNo2() != null && client.getPrPhone2() != null
					&& client.getPrPhone2().equalsIgnoreCase(phoneUpdateInfo.getOldPhoneNo2())) {
				client.setPrPhone2(phoneUpdateInfo.getNewPhoneNo2());
				newPhoneNumber = phoneUpdateInfo.getNewPhoneNo2();
			} else if (phoneUpdateInfo.getOldPhoneNo3() != null && client.getPrPhone3() != null
					&& client.getPrPhone3().equalsIgnoreCase(phoneUpdateInfo.getOldPhoneNo3())) {
				client.setPrPhone3(phoneUpdateInfo.getNewPhoneNo3());
				newPhoneNumber = phoneUpdateInfo.getNewPhoneNo3();
			} else if (phoneUpdateInfo.getOldPhoneNo4() != null && client.getPrPhone4() != null
					&& client.getPrPhone4().equalsIgnoreCase(phoneUpdateInfo.getOldPhoneNo4())) {
				client.setPrPhone4(phoneUpdateInfo.getNewPhoneNo4());
				newPhoneNumber = phoneUpdateInfo.getNewPhoneNo4();
			} else {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.wrongoldphone", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}
		Client clientResponse = clientService.updateClient(client, null);
		String walletId = clientService.getWalletByClientCode(request, client.getClientCode());
		clientService.updateWallet(request, walletId, phoneUpdateInfo.getNewPhoneNo());
		if (clientResponse == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.badhappened", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Note note = new Note();
		note.setSubject("Your phone number has been updated sucessfully.");
		note.setContent("Your phone number has been updated sucessfully.");
		Map<String,String> data = new HashMap();
		data.put("Info","Your phone number has been updated sucessfully.");
		note.setData(data);

		String token=client.getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=client.getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(client.getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your phone number has been updated sucessfully.", "Your phone number has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("Your phone number has been updated sucessfully.", "Your phone number has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your phone number has been updated sucessfully.", "Your phone number has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}

		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.newphoneno", null), newPhoneNumber);
		return ResponseEntity.status(200).body(responseModel);
	}


	@RequestMapping(value = {
			"/client/email/{clientcode}" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateClientEmail(@PathVariable("clientcode") int clientCode,
											   @Valid @RequestBody EmailUpdateInfo emailUpdateInfo) throws MessagingException {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (client.getEmail().contentEquals(emailUpdateInfo.getOldEmail())) {
			Optional<EmailVerification> evOptional = emailVerificationService.find(clientCode,
					emailUpdateInfo.getNewEmail(), null);
			if (evOptional.isPresent()) {
				emailVerificationService.deleteExisting(clientCode);
			}
			EmailVerification emailVerification = emailVerificationService.addVerificationCode(clientCode,
					emailUpdateInfo.getNewEmail());
			String to = emailVerification.getEmail();
			Mail mail = new EmailBuilder().From(fromEmail).To(to).Template("emailconfirmation.html")
					.AddContext("subject", "Money Core Email Verification")
					.AddContext("code", emailVerification.getVerificationCode())
					.AddContext("username", emailVerification.getEmail()).Subject("Money Core Email Verification")
					.createMail();
			emailService.sendMail(mail, true);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.verifycode", null),
					emailVerification.getVerificationCode());
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.oldemail", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/backoffice/devicetoken" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateDevice() {
		List<Client> clients = clientService.findAllClients();
		Client client = clients.get(0);
		clientService.updateDeviceTokenBackOffice(client, getAlphaNumericString(5));
		ResponseModel responseModel = new ResponseModel(true, "Updated Successfully", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/email/verify/{clientcode}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyClientEmail(@PathVariable("clientcode") int clientCode,
											   @Valid @RequestBody EmailUpdateInfo emailUpdateInfo) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Optional<EmailVerification> evOptional = emailVerificationService.find(clientCode,
				emailUpdateInfo.getNewEmail(), emailUpdateInfo.getVerificationCode());
		if (evOptional.isPresent()) {
			client.setEmail(emailUpdateInfo.getNewEmail());
			Client clientResponse  =clientService.updateClient(client, null);
			Note note = new Note();
			note.setSubject("Your email has been updated sucessfully.");
			note.setContent("Your email has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your email has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your email has been updated sucessfully.", "Your email has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your email has been updated sucessfully.", "Your email has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your email has been updated sucessfully.", "Your email has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.newemail", null), clientResponse.getEmail());
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.wrongemailidcode", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/picture/{clientcode}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProfilePicture(@PathVariable("clientcode") int clientCode,
												  @RequestBody ProfilePictureData pictureData) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Optional<ProfilePicture> optionalPicture = profilePictureService.findByClientCode(clientCode);
		ProfilePicture profilePicture = null;
		String statusText = null;
		if (optionalPicture.isPresent()) {
			profilePicture = profilePictureService.store(clientCode, pictureData, optionalPicture.get());
			statusText = Translator.toLocale("client.profilepicupdated", null);
		} else {
			profilePicture = profilePictureService.store(clientCode, pictureData, new ProfilePicture());
			statusText = Translator.toLocale("client.profilepicadd", null);
		}
		if (profilePicture != null) {
			Note note = new Note();
			note.setSubject("Your account has been updated sucessfully.");
			note.setContent("Your account has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your account has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}

			ResponseModel responseModel = new ResponseModel(true, statusText, pictureData);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.plstryagain", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	public static String getAlphaNumericString(int n)// length of string n
	{
		byte[] array = new byte[256];
		new Random().nextBytes(array);
		String randomString = new String(array, Charset.forName("UTF-8"));
		StringBuffer r = new StringBuffer();
		for (int k = 0; k < randomString.length(); k++) {
			char ch = randomString.charAt(k);
			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
				r.append(ch);
				n--;
			}
		}
		return r.toString();
	}
	@RequestMapping(value = {
			"/client/legaldocument/{clientcode}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLegalDocument(@PathVariable("clientcode") int clientCode,
												 @RequestBody ClientDocument clientDocument) throws IOException {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Optional<FileDB> optionalDocument = storageService.findDocument(clientCode, clientDocument.getIdType());
		FileDB fileDB = null;
		String statusText = null;
		if (optionalDocument.isPresent()) {
			fileDB = storageService.update(optionalDocument.get(), clientCode, clientDocument.getIdType(),
					clientDocument.getIdNo(), clientDocument.getIdFrontImage(), clientDocument.getIdBackImage());
			statusText = Translator.toLocale("client.legaldocupdate", null);
		} else {
			fileDB = storageService.update(new FileDB(), clientCode, clientDocument.getIdType(),
					clientDocument.getIdNo(), clientDocument.getIdFrontImage(), clientDocument.getIdBackImage());
			statusText = Translator.toLocale("client.legaldocadded", null);
		}



		if (fileDB != null) {

			Note note = new Note();
			note.setSubject("Your document has been updated sucessfully.");
			note.setContent("Your document has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your document has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your document has been updated sucessfully.", "Your document has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your document has been updated sucessfully.", "Your document has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your document has been updated sucessfully.", "Your document has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, statusText, clientDocument);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.plstryagain", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/legaldocument/{clientcode}/{idtype}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteLegalDocument(@PathVariable("clientcode") int clientCode,
												 @PathVariable("idtype") String idType) {
		Optional<FileDB> optionalDocument = storageService.findDocument(clientCode, idType);
		if (!optionalDocument.isPresent()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.legaldocnotpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<FileDB> count = storageService.findByClientCode(clientCode);
		if (count.size() <= 1) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("client.legaldocnotallow", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		storageService.deleteDocument(clientCode, idType);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.legaliddeleted", null), idType);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/profile/{clientcode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClientProfile(@PathVariable("clientcode") int clientCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<FileDB> documents = storageService.findByClientCode(clientCode);
		Optional<ProfilePicture> profilePicture = profilePictureService.findByClientCode(clientCode);
//		List<Address> address = addressService.findByClientCode(clientCode);
		ClientProfileResponse clientProfileResponse = ClientProfileResponse.generateResponse(client, documents,
				(profilePicture.isPresent()) ? profilePicture.get() : null);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.profiledetials", null), clientProfileResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/status/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> changeClientStatus(HttpServletRequest request, @PathVariable("clientcode") int clientCode,
															  @Valid @RequestBody ClientStatusInfo clientStatusInfo) throws ParseException {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (client.getStatusCode() != null && client.getStatusCode().getStatusCode().equalsIgnoreCase(clientStatusInfo.getStatusCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.operationdone", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		client.setStatusCode(getStatus(clientStatusInfo.getStatusCode()));
		client.setStatusReason(getStatusReason(clientStatusInfo.getStatusReason()));
		client.setUserModif(clientStatusInfo.getUserModif());
		client.setDateModif(new Date());
		client.setStatusFromDate(clientStatusInfo.getStatusFromDate());
		client.setStatusToDate(clientStatusInfo.getStatusToDate());
		Client clientResponse = clientService.updateClient(client, null);
		String message = null;
		switch (clientResponse.getStatusCode().getStatusCode()) {
			case "A":
				message = Translator.toLocale("client.successactive", null);
				break;
			case "S":
				message = Translator.toLocale("client.successsuspended", null);
				break;
			case "B":
				message = Translator.toLocale("client.successblocked", null);
				break;
			case "C":
				message = Translator.toLocale("client.succsscanceled", null);
				break;
			case "M":
				message = Translator.toLocale("client.succssmonitored", null);
				break;
			default:
				message = Translator.toLocale("client.wrongoperation", null);
		}

		Note note = new Note();
		note.setSubject("Your account has been activated.");
		note.setContent("Your account has been activated.");
		Map<String,String> data = new HashMap();
		data.put("Info","Your account has been activated.");
		note.setData(data);

		String token=client.getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=client.getInstitutionList().getInstitutionCode();
		client.getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(client.getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your account has been activated.", "Your account has been activated.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("Your account has been activated.", "Your account has been activated.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your account has been activated.", "Your account has been activated.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}



		ResponseModel responseModel = new ResponseModel(true, message, clientResponse.getStatusCode());
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/address/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateAddress(@PathVariable("clientcode") int clientCode,
														 @Valid @RequestBody ClientAddress clientAddress) {
		boolean updateStatus = false;
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Address address =null;
		if (clientAddress.getAddressId() > 0) {
			address = addressService.findAddress(client.getAddress().getId());
			if (address != null) {
				address = addressService.updateAddress(address, clientAddress);
				updateStatus = true;
			} else {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.invalidaddressid", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
		} else {
			address = addressService.saveAddress(clientAddress);
			client.setAddress(address);
			clientService.updateClient(client);
		}

		Note note = new Note();
		note.setSubject("Your address has been updated sucessfully.");
		note.setContent("Your address has been updated sucessfully.");
		Map<String,String> data = new HashMap();
		data.put("Info","Your address has been updated sucessfully.");
		note.setData(data);

		String token=client.getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=client.getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(client.getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your address has been updated sucessfully.", "Your address has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("Your address has been updated sucessfully.", "Your address has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your address has been updated sucessfully.", "Your address has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}
		if (updateStatus) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.addressupdated", null), address);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.addressesadd", null), address);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/address/{clientcode}/{addressid}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteAddress(@PathVariable("clientcode") int clientCode,
														 @PathVariable("addressid") int addressId) {
		Address address = addressService.findAddress(addressId);
		if (address == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.noaddressinclient", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
//		List<Address> count = addressService.findByClientCode(clientCode);
//		if (count.size() <= 1) {
//			ResponseModel responseModel = new ResponseModel(false,
//					"Operation not allowed. Atleast one address should be present.", null);
//			return ResponseEntity.status(403).body(responseModel);
//		}
		addressService.delete(addressId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.addressdeleted", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateClient(HttpServletRequest request, @PathVariable("clientcode") int clientCode,
														@Valid @RequestBody ClientRegister clientRegister) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.clientinvalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(!client.getPrPhone1().equalsIgnoreCase(clientRegister.getPrPhone1())) {
			List<Client> clientlist = clientService.findUserByContact(clientRegister.getPrPhone1());
			if (!clientlist.isEmpty() && clientlist.get(0).getPrPhone1().equals(clientRegister.getPrPhone1())) {
				ResponseModel responseModel = new ResponseModel(false, "Customer already registered with this phone", null);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		Client clientResponse = clientService.updateClient(client, clientRegister);
		String walletId = clientService.getWalletByClientCode(request, client.getClientCode());
		clientService.updateWallet(request, walletId, clientRegister.getPrPhone1());
		if (clientResponse != null) {
			ClientResponse response = ClientResponse.createResponse(clientResponse);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.updated", null), response);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/space" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addWalletType(HttpServletRequest request, @RequestBody SpaceInfo spaceInfo) {
		SpaceAccounts spaceAccounts = spaceAccountsService.findSpaceAccount(spaceInfo.getWalletId(), spaceInfo.getIban());
		if (spaceAccounts != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.spacepresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		spaceAccounts = spaceAccountsService.save(spaceAccounts,spaceInfo);
		if(spaceAccounts != null) {
			SpaceAccountDetails spaceAccountDetails = null;
			for(SpaceAccountDetailsInfo info : spaceInfo.getSpaceAccountDetails()) {
				SpaceAccountFields spaceAccountFields = getSpaceAccountFields(info.getSpaceAccountFieldsFk());
				spaceAccountDetails = spaceAccountDetailsService.save(spaceInfo, spaceAccounts, spaceAccountFields, info.getSpaceAccountFieldValue());
			}
			if (spaceAccountDetails != null) {
				ResponseModel responseModel = new ResponseModel(true, "Space Account added successfully", null);
				return ResponseEntity.status(201).body(responseModel);
			}
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/space/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getSpaceAccountFields(HttpServletRequest request, @PathVariable("walletid") String walletId) {
		List<SpaceAccounts> spaceAccounts = spaceAccountsService.fetchSpaceAccounts(walletId);
		if (!spaceAccounts.isEmpty() && spaceAccounts != null) {
			List<SpaceAccountsResponse> spaceAccountResponses = SpaceAccountsResponse.createResponse(spaceAccounts);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.spacesuccessfully", null), spaceAccountResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.datanotpresent", null), null);
		return ResponseEntity.status(202).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/space/details/{spaceid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getSpaceAccountDetails(HttpServletRequest request,
																  @PathVariable("spaceid") String spaceId) {
		List<Map<String, String>> map = spaceAccountDetailsService.fetchSpaceAccountDetailsByFk(spaceId);
		if (!map.isEmpty() && map != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.spacedetials", null), map);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.spacenotpresents", null), null);
		return ResponseEntity.status(202).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/space/{id}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteSpaceAccounts(@PathVariable("id") int id) {
		SpaceAccounts spaceAccounts = spaceAccountsService.findSpaceAccounts(id);
		if (spaceAccounts == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.spaceaccountnotpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		spaceAccountDetailsService.deleteSpaceDetails(spaceAccounts.getSpaceAccountPk());
		spaceAccountsService.deleteSpace(id);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.spaceaccoumtdelete", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/language/{institutioncode}/{clientcode}/{languagecode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateLanguage(HttpServletRequest request,
														  @PathVariable("institutioncode") String institutionCode, @PathVariable("clientcode") int clientCode,
														  @PathVariable("languagecode") String languageCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		client = clientService.updateClientLanguage(client, languageCode);
		if (client != null) {
			Note note = new Note();
			note.setSubject("Your account has been updated sucessfully.");
			note.setContent("Your account has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your account has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.languageupdated", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/nationality/{clientcode}/{nationalitycode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateNationality(HttpServletRequest request,
															 @PathVariable("clientcode") int clientCode,
															 @PathVariable("nationalitycode") String nationalityCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		client = clientService.updateClientNationality(client, nationalityCode);
		if (client != null) {
			Note note = new Note();
			note.setSubject("Your account has been updated sucessfully.");
			note.setContent("Your account has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your account has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, "Nationality Updated Successfully", null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/country/{institutioncode}/{clientcode}/{countryCode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCountry(HttpServletRequest request,
														 @PathVariable("institutioncode") String institutionCode, @PathVariable("clientcode") int clientCode,
														 @PathVariable("countryCode") String nationalityCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		client = clientService.updateClientCountry(client, nationalityCode);
		if (client != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.countryupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/block/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> blockClient(@PathVariable("clientcode") int clientCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, "Client not present", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (client.getStatusCode() != null && client.getStatusCode().getStatusCode().equalsIgnoreCase("S")) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.alreadyblocked", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		client.setStatusCode(getStatus("B"));
		client.setStatusReason(getStatusReasonCode(client.getInstitutionList().getInstitutionCode(), "B"));
		clientService.updateClient(client, null);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.blocked", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/client/unblock/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> unblockClient(@PathVariable("clientcode") int clientCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, "Client not present", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (client.getStatusCode().getStatusCode().equalsIgnoreCase("A")) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.upblocked", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		client.setStatusCode(getStatus("A"));
		client.setStatusReason(getStatusReasonCode(client.getInstitutionList().getInstitutionCode(), "A"));
		clientService.updateClient(client, null);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.unblocked", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	private InstitutionList getInstitutionByCode(String institutionCode, HttpServletRequest request) {
		ResponseModel responseModel = null;
		InstitutionList institutionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/internal/institution/" + institutionCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			institutionList = CommonUtil.convertToOriginalObject(responseModel.getResult(), InstitutionList.class);
		}
		return institutionList;
	}

	private SpaceAccountFields getSpaceAccountFields(int id) {
		ResponseModel responseModel = null;
		SpaceAccountFields spaceAccountFields = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/space/field/" + id;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			spaceAccountFields = CommonUtil.convertToOriginalObject(responseModel.getResult(), SpaceAccountFields.class);
		}
		return spaceAccountFields;
	}

	private StatusList getStatus(String statusCode) {
		ResponseModel responseModel = null;
		StatusList statusList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/status/" + statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			statusList = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusList.class);
		}
		return statusList;
	}

	private StatusReasonCode getStatusReason(String statusReasonCode) {
		ResponseModel responseModel = null;
		StatusReasonCode reasonCode = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/statusreason/" + statusReasonCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			reasonCode = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusReasonCode.class);
		}
		return reasonCode;
	}

	private StatusReasonCode getStatusReasonCode(String institutionCode, String statusCode) {
		ResponseModel responseModel = null;
		StatusReasonCode reasonCode = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/statusreason/status/" + institutionCode+"/"+statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			reasonCode = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusReasonCode.class);
		}
		return reasonCode;
	}

	@RequestMapping(value = {
			"/client/createSecurityCode"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSecurityCode(@Valid  @RequestBody Map<String, String> requestbody) {
		String walletId = requestbody.get("walletID");
		String mPin = requestbody.get("mPin");
		if (walletId != null) {
			Wallet wallet = clientService.getWallet(walletId);
			Client client = wallet.getClientCode();
			if (client != null) {
				client.setMpin(bCryptPasswordEncoder.encode(mPin));
				clientsRepo.save(client);

				class Response{
					private String mpin;

					public String getMpin() {
						return mpin;
					}

					public void setMpin(String mpin) {
						this.mpin = mpin;
					}
				}
				Response response = new Response();
				response.setMpin(client.getMpin());
				ResponseModel responseModel = new ResponseModel(true, "Security Code is created successfully", response);
				return ResponseEntity.status(200).body(responseModel);
			}
			ResponseModel responseModel = new ResponseModel(true, "Customer does not exist with this email address",
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notvalid", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/changeSecurityCode"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeSecurityCode(@Valid  @RequestBody Map<String, String> requestbody) {
		String walletId = requestbody.get("walletID");
		String oldmPin = requestbody.get("oldmPin");
		String newmPin = requestbody.get("newmPin");
		if (walletId != null) {
			Wallet wallet = clientService.getWallet(walletId);
			Client client = wallet.getClientCode();
			ResponseModel responseModel;
			if (client != null) {
				if (client.getMpin() != null && bCryptPasswordEncoder.matches(oldmPin, client.getMpin())) {
					client.setMpin(bCryptPasswordEncoder.encode(newmPin));
					clientsRepo.save(client);

					Note note = new Note();
					note.setSubject("Your security PIN has been updated sucessfully.");
					note.setContent("Your security PIN has been updated sucessfully.");
					Map<String,String> data = new HashMap();
					data.put("Info","Your security PIN has been updated sucessfully.");
					note.setData(data);

					String token=client.getDeviceToken();
					Date sysdate = new Date();
					String insCodevalue=client.getInstitutionList().getInstitutionCode();
					String branchCodeValue="";
					String clientCodeValue=String.valueOf(client.getClientCode());
					String userCreate="";

					if(token!=null){
						try {
							firebaseService.sendNotification(note, token);

							NotoficationService saveService = new NotoficationService("Your security PIN has been updated sucessfully.", "Your security PIN has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
							notificationServiceDef.saveNotification(saveService);
						} catch (FirebaseMessagingException e) {
							e.printStackTrace();
							NotoficationService saveService = new NotoficationService("Your security PIN has been updated sucessfully.", "Your security PIN has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
							notificationServiceDef.saveNotification(saveService);
						}


					}else{
						NotoficationService saveService = new NotoficationService("Your security PIN has been updated sucessfully.", "Your security PIN has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
						notificationServiceDef.saveNotification(saveService);
					}


					responseModel = new ResponseModel(true, "Security code was changed successfully", null);
				} else {
					responseModel = new ResponseModel(false, Translator.toLocale("client.crtoldmipn", null), null);
				}
				return ResponseEntity.status(200).body(responseModel);
			}
			responseModel = new ResponseModel(true, "Customer does not exist with this email address",
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notvalid", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/changePassword"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@Valid  @RequestBody Map<String, String> requestbody) {
		String walletId = requestbody.get("walletID");
		String oldPassword = requestbody.get("oldPassword");
		String newPassword = requestbody.get("newPassword");
		if (walletId != null) {
			Wallet wallet = clientService.getWallet(walletId);
			Client client = wallet.getClientCode();
			ResponseModel responseModel;
			if (client != null) {
				if (client.getPassword() != null && bCryptPasswordEncoder.matches(oldPassword, client.getPassword())) {
					client.setPassword(bCryptPasswordEncoder.encode(newPassword));
					clientsRepo.save(client);
					Note note = new Note();
					note.setSubject("Your password has been updated sucessfully..");
					note.setContent("Your password has been updated sucessfully..");
					Map<String,String> data = new HashMap();
					data.put("Info","Your password has been updated sucessfully.");
					note.setData(data);

					String token=client.getDeviceToken();
					Date sysdate = new Date();
					String insCodevalue=client.getInstitutionList().getInstitutionCode();
					String branchCodeValue="";
					String clientCodeValue=String.valueOf(client.getClientCode());
					String userCreate="";

					if(token!=null){
						try {
							firebaseService.sendNotification(note, token);

							NotoficationService saveService = new NotoficationService("Your password has been updated sucessfully.", "Your password has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
							notificationServiceDef.saveNotification(saveService);
						} catch (FirebaseMessagingException e) {
							e.printStackTrace();
							NotoficationService saveService = new NotoficationService("Your password has been updated sucessfully.", "Your password has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
							notificationServiceDef.saveNotification(saveService);
						}

					}else{
						NotoficationService saveService = new NotoficationService("Your password has been updated sucessfully.", "Your password has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
						notificationServiceDef.saveNotification(saveService);
					}

					responseModel = new ResponseModel(true, "The password is changed successfully", null);
				} else {
					responseModel = new ResponseModel(false, Translator.toLocale("client.crtodpsw", null), null);
				}
				return ResponseEntity.status(200).body(responseModel);
			}
			responseModel = new ResponseModel(true, "Customer does not exist with this email address",
					null);
			return ResponseEntity.status(200).body(responseModel);
		}



		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notvalid", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/getMerchantByMccCode/{institutioncode}/{mcccode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMerchantByMccCode(@PathVariable("institutioncode") String institutioncode, @PathVariable("mcccode") String mcccode) {
		List mccList= Arrays.asList(mcccode.split(","));
		List<Client> client = clientService.getMerchantByMccCode(institutioncode, mccList);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.invalidmcc", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.merchant", null), client);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/client/getDashBoardDetails" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDashboardData(HttpServletRequest request) {
		int instituteCount = clientService.getTotalInstitutions(request);
		int branchCount = clientService.getTotalBranch(request);
		Map<String, Long> clinetDetail = clientService.getTotalClientsAndMerchnat(request);
		String wsDetail = clientService.getTotalWalletSpaceDetail(request);

		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.dashboard", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	/*@RequestMapping(value = { "/client/getDashboardInfo" }, method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getDashboardInfo(HttpServletRequest request) {
		DashboardInfo dashboardInfo = clientService.getDashboardInfo();
		List<RegistrationDataInfo> regInfo = clientService.getRegDataInfo();
		List<RegDataInfodtls> listRegDtls = new ArrayList();
		for (int i = 0; i < regInfo.size(); i++) {
			RegDataInfodtls regDtls = new RegDataInfodtls(regInfo.get(i).getMonth(),regInfo.get(i).getTotalInstitutions(),regInfo.get(i).getTotalClients(),regInfo.get(i).getTotalMerchants());
			listRegDtls.add(regDtls);
		}

		DashboardInfodtls dashBoDtls = new DashboardInfodtls(dashboardInfo.getTotalInstitutions(),
				dashboardInfo.getTotalBranches(),dashboardInfo.getTotalClients(),dashboardInfo.getTotalMerchants(),dashboardInfo.getTotalTopup(),dashboardInfo.getTotalTransfer()
				,dashboardInfo.getTotalPurchase(),dashboardInfo.getTotalWallet2Wallet(),dashboardInfo.getTotalNumberOfSpaceAccounts(),dashboardInfo.getTotalMoneyInSpaces(),dashboardInfo.getCancelledUsers(), dashboardInfo.getBlockedUsers(),dashboardInfo.getActiveUsers(),dashboardInfo.getInActivedUsers(),dashboardInfo.getMonitoredUsers(),dashboardInfo.getSuspendedUsers(),listRegDtls);

		ResponseModel responseModel = new ResponseModel(true, "Dashboard Info", dashBoDtls);
		return ResponseEntity.accepted().body(responseModel);
	}*/

	@RequestMapping(value = {
			"/client/getDashboardInfo" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> dashboardFilter(@RequestParam(required = false) String institutionCode,
											 @RequestParam(required = false) String branchCode,
											 @RequestParam(required = false) String filterFromDate, @RequestParam(required = false) String filterToDate) {
		System.out.println(filterFromDate);
		DashboardFilter dashboardFilter = new DashboardFilter(institutionCode,branchCode,filterFromDate,filterToDate);
		return ResponseEntity.status(HttpStatus.OK).body(clientService.getDashboardDetails(dashboardFilter));
	}

	@RequestMapping(value = {
			"/client/getRegisterInfo" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerFilter(@RequestParam(required = false) String lastMonth) {

		return ResponseEntity.status(HttpStatus.OK).body(clientService.getRegDataInfo(lastMonth));
	}

	@RequestMapping(value = {
			"/client/updateMccCode"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<?> updateMccCode(@Valid  @RequestBody Map<String, String> requestbody) {
		String clientCode = requestbody.get("clientCode");
		String mccCode = requestbody.get("mccCode");
		Client client = clientService.findByClientByID(Integer.parseInt(clientCode));
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		MccList mccList = clientService.getMccListByMccCode(client.getInstitutionList().getInstitutionCode(), mccCode);
		if(mccList != null){
			client.setMccCode(mccList);
			client = clientService.updateClient(client);
			if (client != null) {
				Map<String, String> mp = new HashMap();
				mp.put("mccName",mccList.getMccName());
				mp.put("mccCode",mccList.getMccCode());
				Note note = new Note();
				note.setSubject("Your account has been updated sucessfully.");
				note.setContent("Your account has been updated sucessfully.");
				Map<String,String> data = new HashMap();
				data.put("Info","Your account has been updated sucessfully.");
				note.setData(data);

				String token=client.getDeviceToken();
				Date sysdate = new Date();
				String insCodevalue=client.getInstitutionList().getInstitutionCode();
				String branchCodeValue="";
				String clientCodeValue=String.valueOf(client.getClientCode());
				String userCreate="";

				if(token!=null){
					try {
						firebaseService.sendNotification(note, token);

						NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
						notificationServiceDef.saveNotification(saveService);
					} catch (FirebaseMessagingException e) {
						e.printStackTrace();
						NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
						notificationServiceDef.saveNotification(saveService);
					}

				}else{
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
					notificationServiceDef.saveNotification(saveService);
				}

				ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.mccupdate", null), mp);
				return ResponseEntity.status(202).body(responseModel);
			}
		}else{
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.mccnotfound", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = {
			"/client/updateLicenceNo"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<?> updateLicenceNo(@Valid  @RequestBody Map<String, String> requestbody) throws JsonProcessingException {
		String clientCode = requestbody.get("clientCode");
		String oldLicenceImage = requestbody.get("oldLicenceNo");
		String newLicenseNo = requestbody.get("newLicenceNo");
		Client client = clientService.findByClientByID(Integer.parseInt(clientCode));
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(!client.getLicenceNo().equalsIgnoreCase(oldLicenceImage)){
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.oldlicence", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(newLicenseNo != null) {
			client.setLicenceNo(newLicenseNo);
		}
		client = clientService.updateClient(client);
		if (client != null) {
			Map<String, String> mp = new HashMap();
			mp.put("newLicenseNo",newLicenseNo);

			Note note = new Note();
			note.setSubject("Your account has been updated sucessfully.");
			note.setContent("Your account has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your account has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.licencenoupdate", null), mp);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/client/updateLicenceImage"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<?> updateLicenceImage(@Valid  @RequestBody Map<String, String> requestbody) {
		String clientCode = requestbody.get("clientCode");
		String licenceImage = requestbody.get("licenceImage");
		Client client = clientService.findByClientByID(Integer.parseInt(clientCode));
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(licenceImage != null) {
			client.setLicenceImage(licenceImage);
		}
		client = clientService.updateClient(client);
		if (client != null) {
			Note note = new Note();
			note.setSubject("Your account has been updated sucessfully.");
			note.setContent("Your account has been updated sucessfully.");
			Map<String,String> data = new HashMap();
			data.put("Info","Your account has been updated sucessfully.");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your account has been updated sucessfully.", "Your account has been updated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.licenceimgupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}


	@RequestMapping(value = {
			"/client/updateDeviceToken"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<?> updateDeviceToken(@RequestBody Map<String, String> requestParam) {
		String clientCode = requestParam.get("clientCode");
		String deviceToken = requestParam.get("deviceToken");
		Integer id = Integer.valueOf(clientCode);
		Client client = clientService.findByClientByID(id);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(deviceToken != null) {
			client.setDeviceToken(deviceToken);
		}
		client = clientService.updateDeviceToken(client, deviceToken);
		if (client != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.devicetokenupdated", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"client/send-notification" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  ResponseEntity<?> sendNotification(@RequestBody NotoficationDetails details) throws FirebaseMessagingException {

		Note note = new Note();
		note.setSubject(details.getNotificationTitle());
		note.setContent(details.getDescription());
		note.setImage(details.getImage());
		Map<String,String> data = new HashMap();
		data.put("Info",details.getDescription());
		note.setData(data);

		List<String> institutionList = details.getInstitutionCode();
		List<String> branchList = details.getBranchCode();
		List<String> clientList = details.getClientCode();
		String userCreate = details.getUserCreate();

		List<String> tokenList = clientService.getTokenDetails(institutionList,
				branchList,clientList);

		if(tokenList.size()>0){
			for(int i=0;i<tokenList.size();i++){
				String token = tokenList.get(i);
				firebaseService.sendNotification(note, token);
			}
			String insCodevalue="";
			String branchCodeValue="";
			String clientCodeValue="";

			if(institutionList.size()>0){
				insCodevalue= institutionList.stream().collect(Collectors.joining (","));

			}

			if(branchList.size()>0){
				branchCodeValue= branchList.stream().collect(Collectors.joining (","));
			}

			if(clientList.size()>0){
				clientCodeValue= clientList.stream().collect(Collectors.joining (","));
			}

			Date sysdate = new Date();
			NotoficationService saveService = new NotoficationService(details.getNotificationTitle(), details.getDescription(),insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
			notificationServiceDef.saveNotification(saveService);
		}else{
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.devicetokenempty", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}

		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.msgsuccess", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "client/notifications" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> notificationList(HttpServletRequest request) {
		List<NotoficationService> notification = notificationServiceDef.findAllNotificationServices();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.notifications", null), notification);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "client/notifications/success" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> successnotificationList(HttpServletRequest request) {
		List<NotoficationService> notification = notificationServiceDef.findbystatus();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.notifications", null), notification);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "client/notifications/{clientCode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> listByClientCode(@PathVariable("clientCode") int clientCode) {
		List<NotoficationService> notification = notificationServiceDef.findbyclientcode(clientCode);

		if(notification.size() == 0){
			ResponseModel responseModel = new ResponseModel(false, "No Notification Found", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.notifications", null), notification);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/notification-delete/{notificationId}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteNotification(@PathVariable("notificationId") int notificationId) {
		Optional<NotoficationService> notification = notificationServiceDef.findNotification(notificationId);
		if (notification == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notificationsnotfound", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		notificationServiceDef.delete(notificationId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("client.notificationsdelete", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/delete/{clientCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteClientCode(@PathVariable("clientCode") int clientCode) {
		Client client = clientService.findByClientByID(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("client.notpresent", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		clientService.delete(clientCode);
		ResponseModel responseModel = new ResponseModel(true, "Client Account deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

}
