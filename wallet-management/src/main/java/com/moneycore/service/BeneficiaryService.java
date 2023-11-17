package com.moneycore.service;

import com.moneycore.bean.BeneficiaryInfo;
import com.moneycore.entity.Beneficiary;
import com.moneycore.entity.Wallet;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface BeneficiaryService {

	List<BeneficiaryInfo> findByWalletId(String walletId);

	Beneficiary addBeneficiary(@Valid Beneficiary beneficiary);

	Optional<Beneficiary> findById(int beneficiaryId);

	Long checkBeneficiary(String walletId,String beneficiaryId);

	public void delete(int beneficiaryId);
}
