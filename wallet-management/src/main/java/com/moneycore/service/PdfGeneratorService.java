package com.moneycore.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.moneycore.bean.StatementInfo;
import com.moneycore.entity.Address;
import com.moneycore.entity.Wallet;

public interface PdfGeneratorService {

	ByteArrayInputStream generatePDF(Wallet wallet, Address address, String branchName, StatementInfo statementInfo,
			String walletTypeName) throws IOException, DocumentException;

}
