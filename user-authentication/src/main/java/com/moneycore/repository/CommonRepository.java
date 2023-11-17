package com.moneycore.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.moneycore.entity.*;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CommonRepository {

	@PersistenceContext
	private EntityManager manager;

	public int generateCode() {
		CodeGenerator codeGenerator = new CodeGenerator();
		codeGenerator.setData("A");
		manager.persist(codeGenerator);
		return codeGenerator.getId();
	}

	public int generateRefNumber() {
		ReferenceNumberGenerator gGenerator = new ReferenceNumberGenerator();
		gGenerator.setData("A");
		manager.persist(gGenerator);
		return gGenerator.getId();
	}

	public int generateControlIndex() {
		ControlIndexGenerator controlIndexGenerator = new ControlIndexGenerator();
		controlIndexGenerator.setData("CI");
		manager.persist(controlIndexGenerator);
		return controlIndexGenerator.getId();
	}

	public int generatePricingIndex() {
		PricingIndexGenerator pricingIndexGenerator = new PricingIndexGenerator();
		pricingIndexGenerator.setData("PI");
		manager.persist(pricingIndexGenerator);
		return pricingIndexGenerator.getId();
	}

	public int generateWalletTypeId() {
		WalletTypeGenerator walletTypeGenerator = new WalletTypeGenerator();
		walletTypeGenerator.setData("WT");
		manager.persist(walletTypeGenerator);
		return walletTypeGenerator.getId();
	}

	public int generateWalletSpaceId() {
		WalletSpaceCodeGenerator walletSpaceCodeGenerator = new WalletSpaceCodeGenerator();
		walletSpaceCodeGenerator.setData("WS");
		manager.persist(walletSpaceCodeGenerator);
		return walletSpaceCodeGenerator.getId();
	}


	public int generateReferenceCode() {
		ReferenceCodeGenerator referenceCodeGenerator = new ReferenceCodeGenerator();
		referenceCodeGenerator.setData("REF");
		manager.persist(referenceCodeGenerator);
		return referenceCodeGenerator.getId();
	}

	public int generateBillId() {
		BillidGenerator billidGenerator = new BillidGenerator();
		billidGenerator.setData("BILL");
		manager.persist(billidGenerator);
		return billidGenerator.getId();
	}
}
