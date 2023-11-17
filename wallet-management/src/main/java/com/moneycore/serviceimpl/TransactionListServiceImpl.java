package com.moneycore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.moneycore.bean.SearchTransaction;
import com.moneycore.bean.WalletResponse;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletAccountLink;
import com.moneycore.model.SearchWalletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moneycore.bean.StatementInfo;
import com.moneycore.bean.StatementTransactionInfo;
import com.moneycore.bean.TransactionModel;
import com.moneycore.entity.Client;
import com.moneycore.entity.TransactionList;
import com.moneycore.entity.WalletBalance;
import com.moneycore.model.TransactionData;
import com.moneycore.model.TransactionHistoryModel;
import com.moneycore.repository.CommonRepository;
import com.moneycore.repository.TransactionListRepository;
import com.moneycore.service.TransactionListService;

@Service("transactionListServiceImpl")
public class TransactionListServiceImpl implements TransactionListService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CommonRepository commonRepo;

	@Autowired
	TransactionListRepository transactionListRepository;

	@Override
	public TransactionList saveTransaction(WalletBalance walBal, String transactionCode, String reason, double amount,
			String currency, String typeOfTransfer, String name, char transactionType, double transactionAvailableBalance) {
		String referenceNumber = getReferenceNumber();
//		int precision = getPrecision(walBal.getInstitutionCode().getInstitutionCode(), currency);
		TransactionList transactionList = TransactionModel.createTransaction(walBal, transactionCode, reason, amount,
				currency, typeOfTransfer, referenceNumber,name, transactionType, transactionAvailableBalance);
		return transactionListRepository.save(transactionList);
	}


	@Override
	public TransactionList saveTransactionSplit(WalletBalance walBal, String transactionCode, String reason, double amount,
										   String currency, String typeOfTransfer, String name, char transactionType, double transactionAvailableBalance,String referenceCode) {
		String referenceNumber = getReferenceNumber();
//		int precision = getPrecision(walBal.getInstitutionCode().getInstitutionCode(), currency);
		TransactionList transactionList = TransactionModel.createTransactionSplit(walBal, transactionCode, reason, amount,
				currency, typeOfTransfer, referenceNumber,name, transactionType, transactionAvailableBalance,referenceCode);
		return transactionListRepository.save(transactionList);
	}


	public String getReferenceNumber() {
		String original = null;
		int code = commonRepo.generateRefNumber();
		TransactionList list = null;
		try {
			list = (TransactionList) em
					.createNativeQuery("SELECT * FROM transaction_list ORDER BY microfilm_ref_number DESC LIMIT 1",
							TransactionList.class)
					.getSingleResult();
		} catch (Exception e) {
			list = null;
		}
		if (list != null) {
			original = list.getMicrofilmRefNumber().replaceAll("[^0-9]", "");
		} else {
			original = "0000000000000000";
		}
		String incremented = String.format("%0" + original.length() + "d", code);
		incremented = "REF" + incremented;
		return incremented;
	}

	@Override
	public double findTotalReceivedAmount(String walletId) {
		double totalReceivedAmount = 0;
		totalReceivedAmount = (double) em
				.createNativeQuery("SELECT IFNULL( sum(settlement_amount), 0) as settlement_amount from transaction_list where "
						+ "wallet_id='" + walletId + "' and transaction_sign='C'")
				.getSingleResult();
		return totalReceivedAmount;
	}

	@Override
	public double findTotalSendAmount(String walletId) {
		double totalSendAmount = 0;
		double registrationAmount = 0;
		totalSendAmount = (double) em.createNativeQuery(
				"SELECT IFNULL(sum(transaction_amount),0) as transaction_amount from transaction_list where " + "wallet_id='"
						+ walletId
						+ "' and transaction_sign='D' AND transaction_description NOT LIKE '%Top%' AND "
						+ "transaction_description NOT LIKE '%Space%'  AND transaction_description NOT LIKE '%Registration%'")
				.getSingleResult();
		registrationAmount = (double) em.createNativeQuery(
				"SELECT IFNULL(sum(transaction_amount),0) as transaction_amount from transaction_list where " + "wallet_id='"
						+ walletId
						+ "' and "
						+ "transaction_description LIKE '%Registration%'")
				.getSingleResult();
		totalSendAmount = totalSendAmount + registrationAmount;
		return totalSendAmount;
	}

	@Override
	public TransactionData getWalletTransactionHisotry(SearchTransaction searchTransaction) {
		TransactionData transactionData = new TransactionData();
		List<TransactionHistoryModel> historyModels = new ArrayList<TransactionHistoryModel>();
		String filterQuery = "";
		if (searchTransaction.getWalletId() != null && !searchTransaction.getWalletId().isEmpty()) {
			filterQuery = filterQuery + " AND wallet_id LIKE '%" + searchTransaction.getWalletId() + "%'";
		}
		if (searchTransaction.getFromDate() != null && !searchTransaction.getFromDate().isEmpty()
				&& searchTransaction.getToDate() != null && !searchTransaction.getToDate().isEmpty()) {
			filterQuery = filterQuery + " AND Date(transaction_date) between '" + searchTransaction.getFromDate() + "' and '" + searchTransaction.getToDate() + "'";
		}
		if (searchTransaction.getTransactionCode() != null && !searchTransaction.getTransactionCode().isEmpty()) {
			filterQuery = filterQuery + " AND transaction_code LIKE '%" + searchTransaction.getTransactionCode() + "%'";
		}
		if (searchTransaction.getTransactionId() != null && !searchTransaction.getTransactionId().isEmpty()) {
			filterQuery = filterQuery + " AND microfilm_ref_number LIKE '%" + searchTransaction.getTransactionId() + "%'";
		}
		if (searchTransaction.getTransactionType() != null && !searchTransaction.getTransactionType().isEmpty()) {
			filterQuery = filterQuery + " AND transaction_sign IN ('" + (searchTransaction.getTransactionType().equals("A")?"C','D":searchTransaction.getTransactionType()) + "')";
		}
		if (searchTransaction.getServiceType() != null && !searchTransaction.getServiceType().isEmpty()) {
			filterQuery = filterQuery + " AND transaction_description LIKE '%" + searchTransaction.getServiceType() + "%'";
		}
		if (searchTransaction.getSortBy() != null && !searchTransaction.getSortBy().isEmpty()) {
			filterQuery = filterQuery + " order by " + searchTransaction.getSortBy();
		}

		Query countQuery = em.createQuery("select count(1) FROM TransactionList where 1=1" + filterQuery);
		long countResult = (long) countQuery.getSingleResult();

		Query query = em.createQuery(
				"FROM TransactionList where 1=1 " + filterQuery);
		int pageNumber = searchTransaction.getPageNo();
		int pageSize = searchTransaction.getPageSize();
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<TransactionList> transactionLists = query.getResultList();
		for (TransactionList transactionList : transactionLists) {
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();
			String[] wording = transactionList.getTransactionWording().split("transaction", 2);
			String name = wording[1];
			transactionHistoryModel.setName(name);
			transactionHistoryModel.setStatus(transactionList.getReversalFlag());
			transactionHistoryModel.setTransactionAmount(transactionList.getTransactionAmount());
			transactionHistoryModel.setTransactionDate(transactionList.getTransactionDate());
			transactionHistoryModel.setTransactionType(transactionList.getTransactionSign());
			transactionHistoryModel.setDescription(transactionList.getTransactionDescription().replace(" transaction", ""));
			transactionHistoryModel.setTransactionId(transactionList.getMicrofilmRefNumber());
			transactionHistoryModel.setTransactionCode(transactionList.getTransactionCode());
			historyModels.add(transactionHistoryModel);
		}
		transactionData.setTotalPages((int)(countResult%pageSize == 0 ? (countResult/pageSize) : (countResult/pageSize)+1));
		transactionData.setTransactionHistoryModels(historyModels);

		return transactionData;
	}

	@Override
	public Client getNameOfClient(String clientCode) {
		return (Client) em
				.createNativeQuery("select * from client where client_code='" + clientCode + "'", Client.class)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionList> findLast10ByWalletId(String walletId) {
		List<TransactionList> transactionList = null;
		transactionList = em.createNativeQuery("SELECT * FROM transaction_list WHERE wallet_id='" + walletId + "'"
				+ " ORDER BY transaction_date DESC LIMIT 10", TransactionList.class).getResultList();
		return transactionList;
	}

	@Override
	public StatementInfo getWalletStatementHistory(String walletId, String startDate, String endDate, int pageNo,
			int pageSize) {
		StatementInfo statementInfo = new StatementInfo();
		List<StatementTransactionInfo> transactionInfos = new ArrayList<StatementTransactionInfo>();
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<TransactionList> pageList = null;
		pageList = transactionListRepository.getAllSummaryListBetweenDates(walletId, startDate, endDate, paging);
		if (pageList != null && pageList.hasContent()) {
			List<TransactionList> transactionLists = pageList.getContent();
			for (TransactionList transactionList : transactionLists) {
				StatementTransactionInfo statementTransactionInfo = new StatementTransactionInfo();
				statementTransactionInfo.setTransactionDate(transactionList.getTransactionDate());
				String[] wording = transactionList.getTransactionWording().split("transaction", 2);
				String name = wording[1];
				statementTransactionInfo.setName(name);
				statementTransactionInfo.setTransactionCode(transactionList.getTransactionCode());
				statementTransactionInfo.setDescription(transactionList.getTransactionDescription().replace(" transaction", ""));
				statementTransactionInfo.setTransactionAmount(transactionList.getTransactionAmount());
				statementTransactionInfo.setTransactionType(transactionList.getTransactionSign());
				statementTransactionInfo.setBalanceAmount(transactionList.getAvailableBalance());
				transactionInfos.add(statementTransactionInfo);
			}
			statementInfo.setTotalPages(pageSize);
			statementInfo.setStatement(transactionInfos);
			int size = statementInfo.getStatement().size();
			String startTime = statementInfo.getStatement().get(0).getTransactionDate().toString();
			String[] arrStartTime = startTime.split("\\s+", 2);
			String start = arrStartTime[0];
			String endTime = statementInfo.getStatement().get(size - 1).getTransactionDate().toString();
			String[] arrEndTime = endTime.split("\\s+", 2);
			String end = arrEndTime[0];
			try {
			double totalDebit = transactionListRepository.getTotalDebitAndCreditAmountBetweenDates(
					walletId, start, end, 'D');
			statementInfo.setTotalDebit(totalDebit);
			} catch(Exception e) {
				statementInfo.setTotalDebit(0.0);
			}
//			if (!totalDebit.isPresent()) {

//			} else {
//				statementInfo.setTotalDebit(totalDebit.get().getTransactionAmount());
//			}
			try {
				double totalCredit = transactionListRepository.getTotalDebitAndCreditAmountBetweenDates(
					walletId, start, end, 'C');
				statementInfo.setTotalCredit(totalCredit);
			} catch(Exception e) {
				statementInfo.setTotalCredit(0.0);
			}
//			if (!totalCredit.isPresent()) {

//			} else {
//				statementInfo.setTotalCredit(totalCredit.get().getTransactionAmount());
//			}
			Optional<TransactionList> openingBalance = transactionListRepository.getOpeningBalanceFromLastDate(walletId,
					startDate);
			Optional<TransactionList> closingBalance = transactionListRepository
					.getClosingBalanceFromLastTransactionToday(walletId, end);
			if (!openingBalance.isPresent()) {
				statementInfo.setOpeningBalance(0.0);
			} else {
				statementInfo.setOpeningBalance(openingBalance.get().getAvailableBalance());
			}
			if (!closingBalance.isPresent()) {
				statementInfo.setClosingBalance(0.0);
			} else {
				statementInfo.setClosingBalance(closingBalance.get().getAvailableBalance());
			}
		}
		return statementInfo;
	}

	@Override
	public StatementInfo getWalletStatementToDownload(String walletId, String startDate, String endDate) {
		StatementInfo statementInfo = new StatementInfo();
		List<StatementTransactionInfo> transactionInfos = new ArrayList<StatementTransactionInfo>();
		List<TransactionList> transactionLists = transactionListRepository.getAllSummaryListToDownload(walletId,
				startDate, endDate);
		if (transactionLists != null && !transactionLists.isEmpty()) {
			for (TransactionList transactionList : transactionLists) {
				StatementTransactionInfo statementTransactionInfo = new StatementTransactionInfo();
				statementTransactionInfo.setTransactionDate(transactionList.getTransactionDate());
				String[] wording = transactionList.getTransactionWording().split("transaction", 2);
				String name = wording[1];
				statementTransactionInfo.setName(name);
				statementTransactionInfo.setTransactionCode(transactionList.getTransactionCode());
				statementTransactionInfo.setDescription(transactionList.getTransactionDescription());
				statementTransactionInfo.setTransactionAmount(transactionList.getTransactionAmount());
				statementTransactionInfo.setTransactionType(transactionList.getTransactionSign());
				statementTransactionInfo.setBalanceAmount(transactionList.getAvailableBalance());
				transactionInfos.add(statementTransactionInfo);
			}
			statementInfo.setStatement(transactionInfos);
			int size = statementInfo.getStatement().size();
			String startTime = statementInfo.getStatement().get(0).getTransactionDate().toString();
			String[] arrStartTime = startTime.split("\\s+", 2);
			String start = arrStartTime[0];
			String endTime = statementInfo.getStatement().get(size - 1).getTransactionDate().toString();
			String[] arrEndTime = endTime.split("\\s+", 2);
			String end = arrEndTime[0];
			try {
				double totalDebit = transactionListRepository.getTotalDebitAndCreditAmountBetweenDates(walletId, start,
						end, 'D');
				statementInfo.setTotalDebit(totalDebit);
			} catch (Exception e) {
				statementInfo.setTotalDebit(0.0);
			}
			try {
				double totalCredit = transactionListRepository.getTotalDebitAndCreditAmountBetweenDates(walletId, start,
						end, 'C');
				statementInfo.setTotalCredit(totalCredit);
			} catch (Exception e) {
				statementInfo.setTotalCredit(0.0);
			}
			Optional<TransactionList> openingBalance = transactionListRepository.getOpeningBalanceFromLastDate(walletId,
					startDate);
			Optional<TransactionList> closingBalance = transactionListRepository
					.getClosingBalanceFromLastTransactionToday(walletId, end);
			if (!openingBalance.isPresent()) {
				statementInfo.setOpeningBalance(0.0);
			} else {
				statementInfo.setOpeningBalance(openingBalance.get().getAvailableBalance());
			}
			if (!closingBalance.isPresent()) {
				statementInfo.setClosingBalance(0.0);
			} else {
				statementInfo.setClosingBalance(closingBalance.get().getAvailableBalance());
			}
		}
		return statementInfo;
	}

	@Override
	public List<TransactionList> findDailyInternalTransactionByWalletId(String walletId) {
		return transactionListRepository.findDailyInternalTransactionByWalletId(walletId);
	}

	@Override
	public double findTotalInternalTransactionAmountLimitByWalletId(String walletId) {
		Double d = transactionListRepository.findTotalInternalTransactionAmountLimitByWalletId(walletId);
		if(d == null) {
			return 0.0;
		}
		return d;
	}

	@Override
	public double findtotalNoOfInternalTransactions(String walletId) {
		return transactionListRepository.findtotalNoOfInternalTransactions(walletId);
	}

	@Override
	public double findNoOfDailyInternalTransactions(String walletId) {
		return transactionListRepository.findNoOfDailyInternalTransactions(walletId);
	}

	@Override
	public double findDailyTransactionAmountLimit(String walletId) {
		Double d = transactionListRepository.findDailyTransactionAmountLimit(walletId);
		if(d == null) {
			return 0.0;
		}
		return d;
	}

	@Override
	public double findtotalTransactionAmountLimit(String senderWalletId) {
		Double d = transactionListRepository.findtotalTransactionAmountLimit(senderWalletId);
		if (d == null) {
			return 0.0;
		}
		return d;
	}

	@Override
	public double findtotalNoOfTransactions(String senderWalletId) {
		return transactionListRepository.findtotalNoOfTransactions(senderWalletId);
	}

//	public int getPrecision(String institutionCode, String currencyCode) {
//		int precision = (int) em.createNativeQuery("SELECT precision FROM currency_list WHERE institution_code='"
//				+ institutionCode + "'" + " AND currency_code='" + currencyCode + "'").getSingleResult();
//		return precision;
//	}
}
