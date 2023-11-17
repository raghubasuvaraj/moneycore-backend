package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.TransactionList;

@Repository("transactionListRepository")
public interface TransactionListRepository extends PagingAndSortingRepository<TransactionList, Integer> {

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND Date(transaction_date) BETWEEN :fromDate and :toDate", nativeQuery = true)
	Page<TransactionList> getAllTransactionListBetweenDates(@Param("walletId") String walletId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate, Pageable pageable);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND Date(transaction_date) BETWEEN :fromDate and :toDate ORDER BY transaction_date DESC", nativeQuery = true)
	Page<TransactionList> getAllSummaryListBetweenDates(@Param("walletId") String walletId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate, Pageable pageable);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND reversal_flag = :status AND Date(transaction_date) BETWEEN :fromDate and :toDate", nativeQuery = true)
	Page<TransactionList> getAllTransactionListBetweenDatesWithStatus(@Param("walletId") String walletId,
			@Param("status") String status, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			Pageable pageable);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND Date(transaction_date) BETWEEN :fromDate and :toDate AND transaction_sign = :transact", nativeQuery = true)
	Page<TransactionList> getAllTransactionListBetweenDatesWithTransact(@Param("walletId") String walletId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate, Pageable pageable, @Param("transact") char transact);

	@Query(value = "SELECT SUM(transaction_amount) FROM transaction_list WHERE wallet_id = :walletId AND transaction_sign = :transact AND Date(transaction_date) BETWEEN :fromDate and :toDate", nativeQuery = true)
	double getTotalDebitAndCreditAmountBetweenDates(@Param("walletId") String walletId, @Param("fromDate") String startDate,
			@Param("toDate") String endDate, @Param("transact") char transact);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND transaction_date < :fromDate ORDER BY transaction_date DESC LIMIT 1", nativeQuery = true)
	Optional<TransactionList> getOpeningBalanceFromLastDate(@Param("walletId") String walletId, @Param("fromDate") String startDate);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND Date(transaction_date) = :toDate ORDER BY transaction_date DESC LIMIT 1", nativeQuery = true)
	Optional<TransactionList> getClosingBalanceFromLastTransactionToday(@Param("walletId") String walletId, @Param("toDate")  String endDate);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId AND Date(transaction_date) BETWEEN :fromDate and :toDate ORDER BY transaction_date", nativeQuery = true)
	List<TransactionList> getAllSummaryListToDownload(@Param("walletId") String walletId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "SELECT * FROM transaction_list WHERE wallet_id = :walletId and transaction_date <= CURDATE()", nativeQuery = true)
	List<TransactionList> findDailyInternalTransactionByWalletId(@Param("walletId") String walletId);

	@Query(value = "SELECT sum(transaction_amount) FROM transaction_list WHERE wallet_id = :walletId AND MONTH(transaction_date) = MONTH(CURRENT_DATE()) AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%')", nativeQuery = true)
	Double findTotalInternalTransactionAmountLimitByWalletId(@Param("walletId") String walletId);

	@Query(value = "SELECT COUNT(*) FROM transaction_list WHERE wallet_id = :walletId AND MONTH(transaction_date) = MONTH(CURRENT_DATE()) AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%')", nativeQuery = true)
	double findtotalNoOfInternalTransactions(@Param("walletId") String walletId);

	@Query(value = "SELECT COUNT(*) FROM transaction_list WHERE wallet_id = :walletId AND transaction_date >= CURDATE() AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%')", nativeQuery = true)
	double findNoOfDailyInternalTransactions(@Param("walletId") String walletId);

	@Query(value = "SELECT sum(transaction_amount) FROM transaction_list WHERE wallet_id = :walletId AND transaction_date >= CURDATE() AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%') ORDER BY transaction_date DESC LIMIT 1", nativeQuery = true)
	Double findDailyTransactionAmountLimit(@Param("walletId") String walletId);

	@Query(value = "SELECT sum(transaction_amount) FROM transaction_list WHERE wallet_id = :walletId AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%')", nativeQuery = true)
	Double findtotalTransactionAmountLimit(@Param("walletId") String senderWalletId);

	@Query(value = "SELECT COUNT(*) FROM transaction_list WHERE wallet_id = :walletId AND (transaction_description NOT LIKE '%Top Up%' AND transaction_description NOT LIKE '%Received%' AND transaction_description NOT LIKE '%Registration%' AND transaction_description NOT LIKE '%Fee%' AND transaction_description NOT LIKE '%Space%')", nativeQuery = true)
	double findtotalNoOfTransactions(@Param("walletId") String senderWalletId);

}
