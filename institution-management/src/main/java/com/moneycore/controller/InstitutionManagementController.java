package com.moneycore.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.Branch;
import com.moneycore.entity.CurrencyList;
import com.moneycore.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneycore.bean.InstitutionRegisterInfo;
import com.moneycore.email.EmailBuilder;
import com.moneycore.email.Mail;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.InstitutionRegister;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.InstitutionRegisterService;
import com.moneycore.service.InstitutionService;
import com.moneycore.service.Mailer;

@RestController
@RequestMapping("/api/institutionmanagement")
public class InstitutionManagementController {

	@Autowired
	private InstitutionService instituteService;
	@Autowired
	private BranchService branchService;

	@Value("${mail.from}")
	private String fromEmail;

	@Autowired
	private Mailer emailService;

	@Autowired
	private InstitutionRegisterService institutionRegisterService;

	@RequestMapping(value = {
			"/institution/signup" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerInstituteByInstitution(
			@Valid @RequestBody InstitutionRegisterInfo institutionRegisterInfo) throws MessagingException {
		InstitutionRegister institutionRegister = institutionRegisterService
				.findByInstitutionCode(institutionRegisterInfo.getInstitutionCode());
		InstitutionList institutionList = instituteService
				.findByInstitutionCode(institutionRegisterInfo.getInstitutionCode());
		InstitutionList institutionList2 = instituteService.findByEmail(institutionRegisterInfo.getEmailAddress());



		if (institutionRegister != null || institutionList != null) {
			ResponseModel responseModel = new ResponseModel(false,
					"Institution already registered with this Institute Code.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (institutionList2 != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("instituion.email", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		institutionRegister = institutionRegisterService.insertInstitution(institutionRegisterInfo,
				institutionRegister);
		if (institutionRegister != null) {
			Mail mail = new EmailBuilder().From(fromEmail).To(institutionRegister.getEmail())
					.Template("institutionregistration.html").Subject("Money Core Registration").createMail();
			emailService.sendMail(mail, true);
			InstitutionRegisterInfo info = InstitutionRegisterInfo.createSingleResponse(institutionRegister);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.reg", null),
					info);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong. Please try again.", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/institution/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerInstitution(@Valid @RequestBody InstitutionList newInstitute) {
		InstitutionList institute = instituteService.findByInstitutionCode(newInstitute.getInstitutionCode());
		Boolean instiutdup=instituteService.finddup(newInstitute.getInstitutionName());

		if (instiutdup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "institution name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (institute != null) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("instituion.regalready", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		InstitutionList instituteList = instituteService.insert(newInstitute);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.list", null), instituteList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/institutions" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> institutionList(HttpServletRequest request) {
		List<InstitutionList> institute = instituteService.findAll();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.list", null), institute);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/institution/{institutioncode}" }, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateInstitution(@PathVariable("institutioncode") String institutionCode,
			@RequestBody InstitutionList instituteList) {
		InstitutionList institute = instituteService.findByInstitutionCode(institutionCode);
		if (institute == null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.invalidcode", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		List<InstitutionList> instiutdup=instituteService.finddup(instituteList.getInstitutionName(),institutionCode);

		if (instiutdup.size() > 0 ) {
			ResponseModel responseModel = new ResponseModel(false, "Institution Name is Already Exist", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		instituteList.setInstitutionCode(institutionCode);
		instituteList.setDateModif(new Date());
		InstitutionList institutionResponse = instituteService.update(instituteList);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.update", null), institutionResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/institution/{institutionCode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getInstituteByCode(HttpServletRequest request,
			@PathVariable("institutionCode") String institutionCode) {
		InstitutionList institute = instituteService.findByInstitutionCode(institutionCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.list", null), institute);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/institutions/status" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getInstitutionUnapproved(HttpServletRequest request,
			@RequestParam(required = false, name = "status") String status) {
		List<InstitutionRegister> instituteRegisters = institutionRegisterService.findAll(status);
		List<InstitutionRegisterInfo> institutionRegisterInfos = InstitutionRegisterInfo.createResponse(instituteRegisters);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.listofinst", null), institutionRegisterInfos);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "/internal/institution/{institutionCode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getInstitute(HttpServletRequest request,
			@PathVariable("institutionCode") String institutionCode) {
		InstitutionList institute = instituteService.findByInstitutionCode(institutionCode);
		ResponseModel responseModel = new ResponseModel(true, "", institute);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/institution/changestatus" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> approveInstitution(HttpServletRequest request,
			@RequestBody InstitutionRegisterInfo institutionRegisterInfo) throws MessagingException {
		InstitutionRegister institutionRegister = institutionRegisterService
				.findByInstitutionCode(institutionRegisterInfo.getInstitutionCode());
		if (institutionRegister == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("instituion.noinstitutions", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (institutionRegisterInfo.getStatus().equalsIgnoreCase("approve")) {
			InstitutionList institutionList = institutionRegisterService
					.deleteAndCopyToInstitutionList(institutionRegister);
			if (institutionRegister != null) {
				Mail mail = new EmailBuilder().From(fromEmail).To(institutionList.getEmail())
						.Template("institutionconfirmation.html").Subject("Money Core Registration").createMail();
				emailService.sendMail(mail, true);
				ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("instituion.approvedsucccess", null),
						null);
				return ResponseEntity.status(202).body(responseModel);
			}
		} else {
			institutionRegister = institutionRegisterService.update(institutionRegister,
					institutionRegisterInfo.getReason());
			if (institutionRegister != null) {
				Mail mail = new EmailBuilder().From(fromEmail).To(institutionRegister.getEmail())
						.Template("institutionDeclined.html").AddContext("subject", "Money Core")
						.AddContext("reason", institutionRegister.getReason()).Subject("Money Core Registration")
						.createMail();
				emailService.sendMail(mail, true);
				ResponseModel responseModel = new ResponseModel(true, "Institution request rejected successfully",
						null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}


	@RequestMapping(value = {
			"/institution/delete/{institutionCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteinstitutionCode(@PathVariable("institutionCode") String institutionCode) {
		InstitutionList InstitutionList = instituteService.findByInstitutionCode(institutionCode);
		if ( InstitutionList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Institution is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<Branch> checkingBranch=branchService.getDefaultBranchByIC(institutionCode);
		if(checkingBranch.size()!=0){
			ResponseModel responseModel = new ResponseModel(false, "Institution is mapped to Branch it cannot been deleted ", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		instituteService.delete(institutionCode);
		ResponseModel responseModel = new ResponseModel(true, " Institution  deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

}
