package com.moneycore.serviceimpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.component.Translator;
import com.moneycore.entity.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.SearchWallet;
import com.moneycore.bean.TopUpWalletModel;
import com.moneycore.bean.WalletResponse;
import com.moneycore.bean.WalletTransactionModel;
import com.moneycore.bean.WalletTransferAndAddResponse;
import com.moneycore.bean.WalletUpdateInfo;
import com.moneycore.entity.DomainControl;
import com.moneycore.entity.PricingProfile;
import com.moneycore.entity.StatusList;
import com.moneycore.entity.StatusReasonCode;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletAccountLink;
import com.moneycore.entity.WalletBalance;
import com.moneycore.entity.WalletType;
import com.moneycore.model.ResponseModel;
import com.moneycore.model.SearchWalletResponse;
import com.moneycore.repository.WalletRepository;
import com.moneycore.service.TransactionListService;
import com.moneycore.service.WalletBalanceService;
import com.moneycore.service.WalletService;
import com.moneycore.util.CommonUtil;

@Service("walletService")
public class WalletServiceImpl implements WalletService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WalletRepository walletRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	WalletBalanceService walletBalanceService;

	@Autowired
	TransactionListService transactionListService;

	@SuppressWarnings("unchecked")
	@Override
	public List<Wallet> findByWalletId(String walletId) {
		List<Wallet> wallet = em.createQuery("FROM Wallet WHERE walletId='" + walletId + "' and is_deleted=false").getResultList();
		return wallet;
	}

	@Override
	public Wallet addWallet(Wallet wallet) {
//		String contentsQR = wallet.getWalletId() + " " + wallet.getFirstName() + " " + wallet.getMiddelName();
//		wallet.setQrCode(generateQR(contentsQR));
		if (wallet.getWalletType() == null)
			wallet.setWalletType(getWalletType(wallet.getWalletTypeT()));
		if (wallet.getControlProfile() == null)
			wallet.setControlProfile(getDomainControl(wallet.getControlProfileT()));
		if (wallet.getPricingProfile() == null)
			wallet.setPricingProfile(getPricingProfile(wallet.getPricingProfileT()));
		return walletRepository.save(wallet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Wallet> findById(int clientCode, String institutionCode) {
		List<Wallet> wallet = em.createQuery(
				"FROM Wallet WHERE clientCode='" + clientCode + "' AND institutionCode='" + institutionCode + "' and is_deleted=false")
				.getResultList();

		return wallet;
	}

	@Override
	public Wallet findWallet(String walletId) {
		Wallet wallet = null;
		wallet = (Wallet) em.createQuery("FROM Wallet where wallet_id='" + walletId + "' and is_deleted=false").getSingleResult();
		return wallet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchWalletResponse getWalletDetails(SearchWallet searchWallet) {
		List<Wallet> wallets = null;
		String filterQuery = "";
		if (searchWallet.getInstitutionCode() != null) {
			filterQuery = filterQuery + " AND w.institutionCode LIKE '%" + searchWallet.getInstitutionCode() + "%'";
		}
		if (searchWallet.getWalletId() != null) {
			filterQuery = filterQuery + " AND w.walletId LIKE '%" + searchWallet.getWalletId() + "%'";
		}
		if (searchWallet.getClientCode() > 0) {
			filterQuery = filterQuery + " AND w.clientCode LIKE '%" + searchWallet.getClientCode() + "%'";
		}
		if (searchWallet.getFirstName() != null) {
			filterQuery = filterQuery + " AND w.firstName LIKE '%" + searchWallet.getFirstName() + "%'";
		}
		if (searchWallet.getFamilyName() != null) {
			filterQuery = filterQuery + " AND w.familyName LIKE '%" + searchWallet.getFamilyName() + "%'";
		}
		if (searchWallet.getLegalId() != null) {
			filterQuery = filterQuery + " AND w.legalId LIKE '%" + searchWallet.getLegalId() + "%'";
		}
		if (searchWallet.getStatusCode() != null) {
			filterQuery = filterQuery + " AND w.statusCode IN ('" + searchWallet.getStatusCode() + "')";
		}
		if (searchWallet.getPrPhone1() != null) {
			filterQuery = filterQuery + " AND w.prPhone1 LIKE '%" + searchWallet.getPrPhone1() + "%'";
		}
		if (searchWallet.getIsMerchant() != null) {
			filterQuery = filterQuery + " AND c.isMerchant = " + searchWallet.getIsMerchant();
		}
		if (searchWallet.getStatusFromDate() != null) {
			filterQuery = filterQuery + " AND c.suspendStatusFromDate = " + searchWallet.getStatusFromDate();
		}
		if (searchWallet.getGetStatusToDate() != null) {
			filterQuery = filterQuery + " AND c.suspendStatusToDate = " + searchWallet.getGetStatusToDate();
		}
		Query countQuery = em.createQuery("select count(1) FROM Wallet w , Client c WHERE w.clientCode = c AND w.dateCreate IS NOT NULL and w.isDeleted=false" + filterQuery);
		long countResult = (Long)countQuery.getSingleResult();

		Query query = em.createQuery(
				"select w FROM Wallet w, Client c WHERE w.clientCode = c AND w.dateCreate IS NOT NULL" + filterQuery + " and w.isDeleted=false ORDER BY w.dateCreate DESC", Wallet.class);
		int pageNumber = searchWallet.getPageNo();
		int pageSize = searchWallet.getPageSize();
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		wallets = query.getResultList();
		if (wallets != null && !wallets.isEmpty()) {
			for (Wallet wallet : wallets) {
//				WalletBalance walletBalance = findWalletBalance(wallet.getWalletId());
//				WalletBalanceInfo walletBalanceInfo = new WalletBalanceInfo();
//				walletBalanceInfo.setBalance(walletBalance.getBalance());
//				walletBalanceInfo.setBlockedAmount(walletBalance.getBlockedAmount());
//				walletBalanceInfo.setCurrentAuth(walletBalance.getCurrentAuth());
//				walletBalanceInfo.setFacilities(walletBalance.getFacilities());
//				walletBalanceInfo.setAvailableBalance(walletBalance.getBalance() - walletBalance.getBlockedAmount()
//						- walletBalance.getCurrentAuth() - walletBalance.getFacilities());
//				wallet.setBalance(walletBalanceInfo);
				WalletAccountLink accountLink = getAccountTypeCode(wallet.getWalletId());
				if(accountLink.getAccountType() != null) {
					wallet.setAccountTypeCode(accountLink.getAccountType().getAccountTypeCode());
					wallet.setAccountTypeName(accountLink.getAccountType().getAccountTypeName());
				}
			}
		}
		SearchWalletResponse searchWalletResponse = new SearchWalletResponse();

		searchWalletResponse.setTotalPages((int)(countResult%pageSize == 0 ? (countResult/pageSize) : (countResult/pageSize)+1)) ;
		List<WalletResponse> walletResponses = WalletResponse.createResponse(wallets);
		searchWalletResponse.setWalletResponses(walletResponses);
		return searchWalletResponse;
	}

	public WalletBalance findWalletBalance(String walletId) {
		WalletBalance walletBalance = null;
		walletBalance = (WalletBalance) em.createQuery("FROM WalletBalance WHERE walletId='" + walletId + "' ")
					.getSingleResult();
		return walletBalance;
	}
//	public byte[] generateQR(String contents) {
//		try {
////			String contents = walletId + " " + name;
////			Base64.Encoder encoder = Base64.getEncoder();
////			String str = encoder.encodeToString(contents.getBytes());
//			BitMatrix matrix;
//			Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//			hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//			matrix = new MultiFormatWriter().encode(new String(contents.getBytes("UTF-8"), "UTF-8"), BarcodeFormat.QR_CODE,
//					200, 200);
//			int width = matrix.getWidth();
//			int height = matrix.getHeight();
//			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
////			File outputFile = new File("myqrcode.png");
//			for (int x = 0; x < width; x++) {
//				for (int y = 0; y < height; y++) {
//					image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
//				}
//			}
////			if (!ImageIO.write(image, "png", outputFile)) {
////				throw new IOException("png" + " not to " + outputFile);
////			}
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			ImageIO.write(image, "png", os);
//			byte b[] = os.toByteArray();
//			return b;
////			MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	@Override
	public String getWalletIdByClientCode(int clientCode) {
		Wallet wallet = null;
		Optional<Wallet> optional = walletRepository.findByClientCode(clientCode);
		if (optional.isPresent()) {
			wallet = optional.get();
		}
		return wallet.getWalletId();
	}

	@Override
	public Wallet updateWallet(Wallet wallet, WalletUpdateInfo walletUpdateInfo) {
		wallet.setStatusCode(getStatus(walletUpdateInfo.getStatusCode()));
		wallet.setStatusDate(new Date());
		wallet.setStatusReason(getStatusReason(walletUpdateInfo.getStatusReason()));
		wallet.setControlProfile(getDomainControl(walletUpdateInfo.getControlProfile()));
		wallet.setPricingProfile(getPricingProfile(walletUpdateInfo.getPricingProfile()));
		wallet.setWalletType(getWalletType(walletUpdateInfo.getWalletType()));
		wallet.setUserModified(walletUpdateInfo.getUserModif());
		wallet.setDateModified(new Date());
		return walletRepository.save(wallet);
	}

	public WalletAccountLink getAccountTypeCode(String walletId) {
		WalletAccountLink walletAccountLink = null;
		walletAccountLink = em
				.createQuery("FROM WalletAccountLink where entityId='" + walletId + "'", WalletAccountLink.class)
				.getSingleResult();
		return walletAccountLink;
	}

	@Override
	public Wallet findWalletId(String walletId) {
		Wallet wallet = null;
		Optional<Wallet> optional = walletRepository.find(walletId);
		if (optional.isPresent()) {
			wallet = optional.get();
		}
		return wallet;
	}

	@Override
	public ResponseEntity<ResponseModel> checkTransferAndPay(WalletTransactionModel model, WalletBalance sender,
			WalletBalance receiver, boolean isPay) throws JsonProcessingException {

		if (sender.getClientCode().getIsMerchant() &&
				!(sender.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
						|| sender.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M"))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletyour", null)+sender.getClientCode().getStatusCode().getStatusName(), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (receiver.getClientCode().getIsMerchant() &&
				!(receiver.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
						|| receiver.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M"))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletmerchant", null)+receiver.getClientCode().getStatusCode().getStatusName(), null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (!sender.getClientCode().getIsMerchant() &&
				!(sender.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
						|| sender.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M")
						|| sender.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("I"))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletyour", null)+receiver.getClientCode().getStatusCode().getStatusName(), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!receiver.getClientCode().getIsMerchant() &&
				!(receiver.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
						|| receiver.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M")
						|| receiver.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("I"))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.recieverdwallet", null)+receiver.getClientCode().getStatusCode().getStatusName(), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(sender.getWalletId().getControlProfile().getCountryCode().split(",")).contains(receiver.getClientCode().getAddress().getCountry().getCountryCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.countryblocked", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(receiver.getWalletId().getControlProfile().getCountryCode().split(",")).contains(sender.getClientCode().getAddress().getCountry().getCountryCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.countryblocked", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(sender.getWalletId().getControlProfile().getTransactionCode().split(",")).contains(model.getTransactionCode())) {
			ResponseModel responseModel = new ResponseModel(false, isPay ? Translator.toLocale("wallet.walletnopay", null) : Translator.toLocale("wallet.walletnotransfer", null), null); // check is pay condition
			return ResponseEntity.accepted().body(responseModel);
		}
		if (receiver.getClientCode().getIsMerchant() &&
				!Arrays.asList(sender.getWalletId().getControlProfile().getMerchantId().split(",")).contains(String.valueOf(receiver.getClientCode().getClientCode()))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.merchantno", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
//		if (receiver.getClientCode().getIsMerchant() &&
//				!sender.getWalletId().getControlProfile().getMccCode().contains(receiver.getClientCode().getMccCode())) {
//			ResponseModel responseModel = new ResponseModel(false, "MCC code not matched", null);
//			return ResponseEntity.accepted().body(responseModel);
//		}
		if (!Arrays.asList(sender.getWalletId().getControlProfile().getCurrencyCode().split(",")).contains(model.getCurrency())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.recieverd", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(sender.getWalletId().getControlProfile().getCurrencyCode().split(",")).contains(receiver.getClientCode().getCurrencyCode().getCurrencyCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.recieverd", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(receiver.getWalletId().getControlProfile().getCurrencyCode().split(",")).contains(model.getCurrency())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.recieverd", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (!Arrays.asList(receiver.getWalletId().getControlProfile().getCurrencyCode().split(",")).contains(sender.getClientCode().getCurrencyCode().getCurrencyCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.recieverd", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		// Daily Internal Transaction Amount Limit
		if (model.getTransactionMoney() > sender.getWalletId().getControlProfile().getDailyOnusAmnt()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		double dailyTransactionAmountLimit = transactionListService
				.findDailyTransactionAmountLimit(model.getSenderWalletId());
		if (dailyTransactionAmountLimit >= sender.getWalletId().getControlProfile().getDailyOnusAmnt()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		double dailyAmountSpend = Math.round(
				(sender.getWalletId().getControlProfile().getDailyOnusAmnt() - dailyTransactionAmountLimit) * 100.0)
				/ 100.0;
		if (model.getTransactionMoney() > dailyAmountSpend) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		// Number of Daily Internal Transactions
		double noOfDailyInternalTransactions = transactionListService
				.findNoOfDailyInternalTransactions(model.getSenderWalletId());
		int size = (int) noOfDailyInternalTransactions;
		if (size >= sender.getWalletId().getControlProfile().getDailyOnusNbr()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistnumber", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		// Internal Transaction Amount Limit / Month
		double totalInternalTransactionAmountLimit = transactionListService
				.findTotalInternalTransactionAmountLimitByWalletId(model.getSenderWalletId());
		if (totalInternalTransactionAmountLimit >= sender.getWalletId().getControlProfile().getPerOnusAmnt()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistmonthlynumber", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		double monthlyAmountSpend = Math
				.round((sender.getWalletId().getControlProfile().getPerOnusAmnt() - totalInternalTransactionAmountLimit)
						* 100.0)
				/ 100.0;
		if (model.getTransactionMoney() > monthlyAmountSpend) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistmonthlynumberamount", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		// Number of Internal Transactions / Month
		double totalNoOfInternalTransactions = transactionListService
				.findtotalNoOfInternalTransactions(model.getSenderWalletId());
		if (totalNoOfInternalTransactions >= sender.getWalletId().getControlProfile().getPerOnusNbr()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistmonthlynumbertranscation", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		// Total Transaction Amount Limit
		double totalTransactionAmountLimit = transactionListService
				.findtotalTransactionAmountLimit(model.getSenderWalletId());
		if (totalTransactionAmountLimit >= sender.getWalletId().getControlProfile().getPerTotAmnt()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistdailynumbertranscation", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		double totalAmountSpend = Math
				.round((sender.getWalletId().getControlProfile().getPerTotAmnt() - totalTransactionAmountLimit) * 100.0)
				/ 100.0;
		if (model.getTransactionMoney() > totalAmountSpend) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.limitexistdailynumbertranscationlist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		// Total Number of Transactions
		double totalNoOfTransactions = transactionListService.findtotalNoOfTransactions(model.getSenderWalletId());
		if (totalNoOfTransactions >= sender.getWalletId().getControlProfile().getPerTotNbr()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.totallimitexistdailynumbertranscationlist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (sender != null && sender.getBalance() >= model.getTransactionMoney()) {
			if(!sender.getClientCode().getIsMerchant() && sender.getClientCode().getStatusCode().getStatusCode().equals("I")){
				walletBalanceService.activateClient(sender.getClientCode().getClientCode());
				Wallet wallet = findWallet(sender.getWalletId().getWalletId());
				sender.getWalletId().setStatusCode(getStatus("A"));
				walletRepository.save(wallet);
			}
			if(!receiver.getClientCode().getIsMerchant() && receiver.getClientCode().getStatusCode().getStatusCode().equals("I")){
				walletBalanceService.activateClient(receiver.getClientCode().getClientCode());
				Wallet wallet = findWallet(receiver.getWalletId().getWalletId());
				receiver.getWalletId().setStatusCode(getStatus("A"));
				walletRepository.save(wallet);
			}
			receiver.setBalance(receiver.getBalance() + model.getTransactionMoney());
			double oldBalance = sender.getBalance();
			sender.setBalance(sender.getBalance() - (model.getTransactionMoney() + model.getFee()));
			receiver = walletBalanceService.saveWalletBalance(receiver);
			sender = walletBalanceService.saveWalletBalance(sender);
			WalletTransferAndAddResponse response = new WalletTransferAndAddResponse();
			response.setWalletBalance(sender.getBalance());
			response.setTotalSendAmount(model.getTransactionMoney());
			response.setCurrency(model.getCurrency());
			response.setFee(model.getFee());
			if (receiver.getWalletId().getClientCode().getIsMerchant()) {
				TransactionList transactionList = transactionListService.saveTransaction(sender, model.getTransactionCode(), model.getReason(),
						model.getTransactionMoney(), model.getCurrency(), isPay ? "PUR2W" :"W2W", model.getReceiverName(), '\u0000',
						(oldBalance - model.getTransactionMoney()));
				response.setTransactionId(transactionList.getMicrofilmRefNumber());

				if (model.getFee() != 0) {

					if(model.getReferenceCode() !="" && model.getReferenceCode() !=null){

						transactionListService.saveTransactionSplit(sender, model.getTransactionCode(), model.getReason(),
								model.getFee(), response.getCurrency(), isPay ? "FPUR2W" : "FT2W", isPay ? "Purchase Fee" : "Transfer Fee",
								'D', sender.getBalance(), model.getReferenceCode());
					}else{
						transactionListService.saveTransaction(sender, model.getTransactionCode(), model.getReason(),
								model.getFee(), response.getCurrency(), isPay ? "FPUR2W" : "FT2W", isPay ? "Purchase Fee" : "Transfer Fee",
								'D', sender.getBalance());
					}
				}
				if(model.getReferenceCode() !="" && model.getReferenceCode() !=null){
					transactionListService.saveTransactionSplit(receiver, model.getTransactionCode(), model.getReason(),
							model.getTransactionMoney(), receiver.getCurrencyCode().getCurrencyCode(), "PAY2W",
							model.getSenderName(), '\u0000', receiver.getBalance(), model.getReferenceCode());
				}else {
					transactionListService.saveTransaction(receiver, model.getTransactionCode(), model.getReason(),
							model.getTransactionMoney(), receiver.getCurrencyCode().getCurrencyCode(), "PAY2W",
							model.getSenderName(), '\u0000', receiver.getBalance());
				}

				ResponseModel responseModel;
				if(isPay){
					responseModel = new ResponseModel(true, Translator.toLocale("wallet.paymensuccess", null), response);
				}else{

					responseModel = new ResponseModel(true, Translator.toLocale("wallet.transfer", null), response);
				}
				return ResponseEntity.accepted().body(responseModel);
			} else {
				TransactionList transactionList = transactionListService.saveTransaction(sender, model.getTransactionCode(), model.getReason(),
						model.getTransactionMoney(), model.getCurrency(), "W2W", model.getReceiverName(), '\u0000',
						(oldBalance - model.getTransactionMoney()));
				response.setTransactionId(transactionList.getMicrofilmRefNumber());
				if (model.getFee() != 0) {

					if(model.getReferenceCode() !="" && model.getReferenceCode() !=null){

						transactionListService.saveTransactionSplit(sender, model.getTransactionCode(), model.getReason(),
								model.getFee(), response.getCurrency(), isPay ? "FPUR2W" : "FT2W", isPay ? "Purchase Fee" : "Transfer Fee",
								'D', sender.getBalance(), model.getReferenceCode());
					}else{
						transactionListService.saveTransaction(sender, model.getTransactionCode(), model.getReason(),
								model.getFee(), response.getCurrency(), "FT2W", "Transfer Fee", 'D', sender.getBalance());
					}

				}
				if(model.getReferenceCode() !="" && model.getReferenceCode() !=null){
					transactionListService.saveTransactionSplit(receiver, model.getTransactionCode(), model.getReason(),
							model.getTransactionMoney(), receiver.getCurrencyCode().getCurrencyCode(), "R2W",
							model.getSenderName(), '\u0000', receiver.getBalance(), model.getReferenceCode());
				}else{
					transactionListService.saveTransaction(receiver, model.getTransactionCode(), model.getReason(),
							model.getTransactionMoney(), receiver.getCurrencyCode().getCurrencyCode(), "R2W",
							model.getSenderName(), '\u0000', receiver.getBalance());
				}

				ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.wallettransf", null), response);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.insufficient", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<ResponseModel> checkAdjustBalance(TopUpWalletModel topUpWalletModel, String operation,
			String type, WalletBalance walBal) throws JsonProcessingException {
		String typeOfTransfer = null;
		double oldBalance = 0.0;
		if ((walBal.getClientCode().getIsMerchant() &&
				!(walBal.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
						|| walBal.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M")))
				|| (walBal.getClientCode().getIsMerchant() &&
						!(walBal.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("A")
								|| walBal.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("M")
								|| walBal.getWalletId().getClientCode().getStatusCode().getStatusCode().equalsIgnoreCase("I")))) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletyour", null)+walBal.getWalletId().getClientCode().getStatusCode().getStatusName(), null);
			return ResponseEntity.accepted().body(responseModel);
		}
//		if (!wallet.getControlProfile().getTransactionCode().equalsIgnoreCase(topUpWalletModel.getTransactionCode())) {
//			ResponseModel responseModel = new ResponseModel(false, "Transaction code mismatched", null);
//			return ResponseEntity.status(400).body(responseModel);
//		}
		if (!walBal.getWalletId().getControlProfile().getCurrencyCode().contains(topUpWalletModel.getCurrency())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.currencynovalid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}

		if (!Arrays.asList(walBal.getWalletId().getControlProfile().getTransactionCode().split(",")).contains(topUpWalletModel.getTransactionCode())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.cashfact", null), null); // check is pay condition
			return ResponseEntity.accepted().body(responseModel);
		}
		oldBalance = walBal.getBalance();
		char transactionType = 'D';
		if (walBal != null) {
			if(!walBal.getClientCode().getIsMerchant() && walBal.getClientCode().getStatusCode().getStatusCode().equals("I")){
				walletBalanceService.activateClient(walBal.getClientCode().getClientCode());
				Wallet wallet = findWallet(walBal.getWalletId().getWalletId());
				walBal.getWalletId().setStatusCode(getStatus("A"));
				walletRepository.save(wallet);
			}
			String currencyCode = walBal.getCurrencyCode().getCurrencyCode();
			if (!currencyCode.equalsIgnoreCase(topUpWalletModel.getCurrency())) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.currencynovalid", null), null);
				return ResponseEntity.accepted().body(responseModel);
			}
			if(operation == null && type != null && type.equalsIgnoreCase("topup")) {
				walBal.setBalance(walBal.getBalance() + (topUpWalletModel.getAmount() - topUpWalletModel.getFee()));
				typeOfTransfer = "T2W";
			}
			if (operation == null && type == null) {
				walBal.setBalance(walBal.getBalance() + topUpWalletModel.getAmount());
				typeOfTransfer = "A2W";
				transactionType = 'C';
			}
			if (operation != null && type == null) {
				if (walBal.getBalance() > 0 && walBal.getBalance() >= topUpWalletModel.getAmount()) {
					walBal.setBalance(walBal.getBalance() - topUpWalletModel.getAmount());
					typeOfTransfer = "A2W";
				} else {
					ResponseModel responseModel = new ResponseModel(false,
							"Invalid operation. Wallet balance should be greater than or equal to the amount", null);
					return ResponseEntity.accepted().body(responseModel);
				}
			}
			walletBalanceService.saveWalletBalance(walBal);
			WalletTransferAndAddResponse response = new WalletTransferAndAddResponse();
			response.setWalletBalance(walBal.getBalance());
			response.setTotalSendAmount(topUpWalletModel.getAmount());
			response.setCurrency(walBal.getCurrencyCode().getCurrencyCode());
			response.setFee(topUpWalletModel.getFee());

			transactionListService.saveTransaction(walBal, topUpWalletModel.getTransactionCode(),
					topUpWalletModel.getReason(), topUpWalletModel.getAmount(),
					response.getCurrency(), typeOfTransfer, topUpWalletModel.getName(), transactionType,(topUpWalletModel.getAmount() + oldBalance));
			if (type != null && type.equalsIgnoreCase("topup") && topUpWalletModel.getFee() != 0) {
				transactionListService.saveTransaction(walBal, topUpWalletModel.getTransactionCode(),
						topUpWalletModel.getReason(), topUpWalletModel.getFee(), response.getCurrency(), "F2W",
						"Top Up Fee", 'D',walBal.getBalance());
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.balanceupdate", null), response);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletid", null), null);
		return ResponseEntity.accepted().body(responseModel);
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
	@Override
	@Transactional
	public void walletType() {
		String clientCode= Translator.toLocale("client.clientcode", null);
		String walletType= Translator.toLocale("client.walletType", null);
		em.createNativeQuery("update wallet set wallet_type=:walletType where client_code!=:clientCode", Wallet.class)
				.setParameter("walletType", walletType).setParameter("clientCode",clientCode).executeUpdate();
	}
	private WalletType getWalletType(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletType walletType = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/" + walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletType = CommonUtil.convertToOriginalObject(responseModel.getResult(), WalletType.class);
		}
		return walletType;
	}

	private DomainControl getDomainControl(String id) {
		ResponseModel responseModel = null;
		DomainControl domainControl = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/domaincontrol/" + id;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			domainControl = CommonUtil.convertToOriginalObject(responseModel.getResult(), DomainControl.class);
		}
		return domainControl;
	}

	private PricingProfile getPricingProfile(String id) {
		ResponseModel responseModel = null;
		PricingProfile pricingProfile = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/pricingprofile/" + id;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			pricingProfile = CommonUtil.convertToOriginalObject(responseModel.getResult(), PricingProfile.class);
		}
		return pricingProfile;
	}

	@Override
	public Wallet updateWalletStatus(Wallet wallet, String statusCode) {
		wallet.setStatusCode(getStatus(statusCode));
		return walletRepository.save(wallet);
	}
	@Override
	public Wallet updateWallet(Wallet wallet) {
		return walletRepository.save(wallet);
	}

	@Override
	@Transactional
	public void profileBalance() {
		String clientCode= Translator.toLocale("client.clientcode", null);
		String pricingProfile= Translator.toLocale("client.pricingprofile", null);
		em.createNativeQuery("update wallet set pricing_profile=:pricingProfile where client_code!=:clientCode", Wallet.class)
				.setParameter("pricingProfile", pricingProfile).setParameter("clientCode",clientCode).executeUpdate();
	}

	public void delete(String wallet) {
		walletRepository.deleteById(wallet);
	}
}

