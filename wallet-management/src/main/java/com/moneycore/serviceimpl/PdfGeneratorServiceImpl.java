package com.moneycore.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.moneycore.bean.StatementInfo;
import com.moneycore.bean.StatementTransactionInfo;
import com.moneycore.entity.Address;
import com.moneycore.entity.Wallet;
import com.moneycore.service.PdfGeneratorService;

@SuppressWarnings("deprecation")
@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

	@Override
	public ByteArrayInputStream generatePDF(Wallet wallet, Address address, String branchName, StatementInfo statementInfo,
			String walletTypeName) throws IOException, DocumentException {
//		File tempsellfile = File.createTempFile("SamplePDF", ".pdf");
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();

		Font fontHeaderChunk1 = FontFactory.getFont(FontFactory.COURIER, 12, WebColors.getRGBColor("#555555"));
		Font fontHeaderChunk2 = FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD,
				WebColors.getRGBColor("#555555"));
		Paragraph headerPara = new Paragraph();
		Chunk headerChunk1 = new Chunk("MoneyCore Pay", fontHeaderChunk1);
		Chunk headerChunk2 = new Chunk("37, rue Adam Ledoux,", fontHeaderChunk2);
		Chunk headerChunk3 = new Chunk("92400 Courbevoie, La Défense", fontHeaderChunk2);
		headerPara.add(headerChunk1);
		headerPara.add("\n");
		headerPara.add("\n");
		headerPara.add(headerChunk2);
		headerPara.add("\n");
		headerPara.add(headerChunk3);
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setSpacingAfter(25);
		PdfPCell headerLeftCell = new PdfPCell(new Paragraph(""));//setImage()
		PdfPCell headerRightCell = new PdfPCell(headerPara);
		headerRightCell.setPaddingBottom(10);
		headerLeftCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		headerRightCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerLeftCell.setBorderWidthBottom(1);
		headerLeftCell.setBorderWidthLeft(0);
		headerLeftCell.setBorderWidthRight(0);
		headerLeftCell.setBorderWidthTop(0);
		headerLeftCell.setBorderColorBottom(WebColors.getRGBColor("#AAAAAA"));
		headerLeftCell.setBorderColorLeft(BaseColor.WHITE);
		headerLeftCell.setBorderColorRight(BaseColor.WHITE);
		headerLeftCell.setBorderColorTop(BaseColor.WHITE);
		headerRightCell.setBorderWidthBottom(1);
		headerRightCell.setBorderWidthLeft(0);
		headerRightCell.setBorderWidthRight(0);
		headerRightCell.setBorderWidthTop(0);
		headerRightCell.setBorderColorBottom(WebColors.getRGBColor("#AAAAAA"));
		headerRightCell.setBorderColorLeft(BaseColor.WHITE);
		headerRightCell.setBorderColorRight(BaseColor.WHITE);
		headerRightCell.setBorderColorTop(BaseColor.WHITE);
		headerTable.addCell(headerLeftCell);
		headerTable.addCell(headerRightCell);
		document.add(headerTable);

		//
		Paragraph detailsPara = new Paragraph();
		Font fontdetailsChunk1 = FontFactory.getFont(FontFactory.COURIER, 6, WebColors.getRGBColor("#777777"));
		Font fontdetailsChunk2 = FontFactory.getFont(FontFactory.COURIER, 10, WebColors.getRGBColor("#555555"));
		Font fontdetailsChunk3 = FontFactory.getFont(FontFactory.COURIER, 7, WebColors.getRGBColor("#555555"));
		Chunk detailsChunk1 = new Chunk("Wallet Statement", fontdetailsChunk1);
		String clientName = wallet.getClientCode().getFirstName();
		if (wallet.getClientCode().getMiddleName() != null)
			clientName = clientName + " " + wallet.getClientCode().getMiddleName();
		if (wallet.getClientCode().getLastName() != null)
			clientName = clientName + " " + wallet.getClientCode().getLastName();
		Chunk detailsChunk2 = new Chunk(clientName, fontdetailsChunk2);
		String clientAddress = "";
		if (address != null) {
			clientAddress = address.getAddressLine1();
			if (address.getAddressLine2() != null)
				clientAddress = clientAddress + ", " + address.getAddressLine2();
			if (address.getAddressLine3() != null)
				clientAddress = clientAddress + ", " + address.getAddressLine3();
			if (address.getAddressLine4() != null)
				clientAddress = clientAddress + ", " + address.getAddressLine4();
			clientAddress = clientAddress + ", " + address.getCity() + " " + address.getCountry() + " - "
					+ address.getPostalCode();
		}
		Chunk detailsChunk3 = new Chunk("Address: " + clientAddress, fontdetailsChunk3);
		Chunk detailsChunk4 = new Chunk("Phone No. : " + wallet.getClientCode().getPrPhone1(), fontdetailsChunk3);
		Chunk detailsChunk5 = new Chunk("Wallet ID. : " + "#" + wallet.getWalletId(), fontdetailsChunk3);
		Chunk detailsChunk6 = new Chunk("Branch Name : " + "#" + branchName, fontdetailsChunk3);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);

		Chunk detailsChunk7 = new Chunk("Statement Date: " + strDate, fontdetailsChunk3);
		int lastDateIndex = statementInfo.getStatement().size() - 1;
		Date startDate = statementInfo.getStatement().get(0).getTransactionDate();
		Date endDate = statementInfo.getStatement().get(lastDateIndex).getTransactionDate();
		String firstDate = formatter.format(startDate);
		String lastDate = formatter.format(endDate);
		Chunk detailsChunk8 = new Chunk("Statement Period: " + firstDate + " to " + lastDate, fontdetailsChunk3);
		detailsPara.add(detailsChunk1);
		detailsPara.add("\n");
		detailsPara.add("\n");
		detailsPara.add(detailsChunk2);
		detailsPara.add("\n");
		detailsPara.add(detailsChunk3);
		detailsPara.add("\n");
		detailsPara.add(detailsChunk4);
		detailsPara.add("\n");
		detailsPara.add(detailsChunk5);
		detailsPara.add("\n");
		detailsPara.add(detailsChunk6);
		detailsPara.add("\n");
		detailsPara.add("\n");
		detailsPara.add(detailsChunk7);
		detailsPara.add("\n");
		detailsPara.add(detailsChunk8);

		//
		Paragraph accountPara = new Paragraph();
		Chunk accountChunk1 = new Chunk("Statement of Account", fontdetailsChunk1);
		Chunk accountChunk2 = new Chunk("Opening Balance: " + statementInfo.getOpeningBalance(), fontdetailsChunk3);
		Chunk accountChunk3 = new Chunk("Total Credit Amount: " + statementInfo.getTotalCredit(), fontdetailsChunk3);
		Chunk accountChunk4 = new Chunk("Total Debit Amount: " + statementInfo.getTotalDebit(), fontdetailsChunk3);
		Chunk accountChunk5 = new Chunk("Closing Balance: " + statementInfo.getClosingBalance(), fontdetailsChunk3);
		Chunk accountChunk6 = new Chunk("Wallet Type: " + walletTypeName, fontdetailsChunk3);
		Chunk accountChunk7 = new Chunk("No of Tranactions: " + statementInfo.getStatement().size(), fontdetailsChunk3);
		accountPara.add(accountChunk1);
		accountPara.add("\n");
		accountPara.add("\n");
		accountPara.add(accountChunk2);
		accountPara.add("\n");
		accountPara.add(accountChunk3);
		accountPara.add("\n");
		accountPara.add(accountChunk4);
		accountPara.add("\n");
		accountPara.add(accountChunk5);
		accountPara.add("\n");
		accountPara.add("\n");
		accountPara.add(accountChunk6);
		accountPara.add("\n");
		accountPara.add(accountChunk7);
		PdfPTable detailsTable = new PdfPTable(2);
		PdfPCell detailsLeftCell = new PdfPCell(detailsPara);
		PdfPCell accountRightCell = new PdfPCell(accountPara);
		detailsLeftCell.setPaddingLeft(10);
		detailsLeftCell.setBorderWidthLeft(5);
		detailsLeftCell.setBorderColorLeft(WebColors.getRGBColor("#0477C6"));
		detailsLeftCell.setBorderColorRight(BaseColor.WHITE);
		detailsLeftCell.setBorderColorBottom(BaseColor.WHITE);
		detailsLeftCell.setBorderColorTop(BaseColor.WHITE);
		accountRightCell.setBorder(PdfPCell.NO_BORDER);
		detailsLeftCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		accountRightCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
		accountRightCell.setPaddingLeft(80);
		detailsTable.addCell(detailsLeftCell);
		detailsTable.addCell(accountRightCell);
		document.add(detailsTable);

		//
		PdfPTable gapTable = new PdfPTable(1);
		PdfPCell gapCell = new PdfPCell();
		gapCell.setFixedHeight(20);
		gapCell.setBorder(PdfPCell.NO_BORDER);
		gapTable.addCell(gapCell);
		document.add(gapTable);
		//
		PdfPTable table = new PdfPTable(new float[] { 15, 40, 15, 15, 15 });
		Font dateCellFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
		Font descCellFont = FontFactory.getFont(FontFactory.COURIER, 8, WebColors.getRGBColor("#555555"));
		Font creditCellFont = FontFactory.getFont(FontFactory.COURIER, 8, WebColors.getRGBColor("#555555"));
		Font balanceCellFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.WHITE);
		PdfPCell dateCell = new PdfPCell(new Paragraph("DATE & TIME", dateCellFont));
		PdfPCell descCell = new PdfPCell(new Paragraph("DESCRIPTION", descCellFont));
		PdfPCell creditCell = new PdfPCell(new Paragraph("CREDIT", creditCellFont));
		PdfPCell debitCell = new PdfPCell(new Paragraph("DEBIT", descCellFont));
		PdfPCell balanceCell = new PdfPCell(new Paragraph("BALANCE", balanceCellFont));
		dateCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		descCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		creditCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		debitCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		balanceCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		dateCell.setPaddingTop(7);
		dateCell.setPaddingBottom(7);
		descCell.setPaddingTop(7);
		descCell.setPaddingBottom(7);
		creditCell.setPaddingTop(7);
		creditCell.setPaddingBottom(7);
		debitCell.setPaddingTop(7);
		debitCell.setPaddingBottom(7);
		balanceCell.setPaddingTop(7);
		balanceCell.setPaddingBottom(7);
		dateCell.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
		dateCell.setBorderColor(WebColors.getRGBColor("#EEEEEE"));
		descCell.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
		descCell.setBorderColor(WebColors.getRGBColor("#EEEEEE"));
		creditCell.setBackgroundColor(WebColors.getRGBColor("#DDDDDD"));
		creditCell.setBorderColor(WebColors.getRGBColor("#DDDDDD"));
		debitCell.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
		debitCell.setBorderColor(WebColors.getRGBColor("#EEEEEE"));
		balanceCell.setBackgroundColor(WebColors.getRGBColor("#01104d"));
		balanceCell.setBorderColor(WebColors.getRGBColor("#01104d"));

		table.addCell(dateCell);
		table.addCell(descCell);
		table.addCell(creditCell);
		table.addCell(debitCell);
		table.addCell(balanceCell);
		document.add(table);

		//
		Font tableCellFont1 = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
		Font tableCellFont2 = FontFactory.getFont(FontFactory.COURIER, 8, WebColors.getRGBColor("#555555"));
		Font tableCellFont3 = FontFactory.getFont(FontFactory.COURIER, 8, WebColors.getRGBColor("#FFFFFF"));
		for (StatementTransactionInfo info : statementInfo.getStatement()) {
			Date date1 = info.getTransactionDate();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String transactionDate = format.format(date1);
			PdfPTable tableValues = new PdfPTable(new float[] { 15, 40, 15, 15, 15 });
			PdfPCell c1 = new PdfPCell(new Paragraph(transactionDate, tableCellFont1));
			c1.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
			Paragraph p1 = new Paragraph();
			Chunk ch1 = new Chunk(info.getName() + " - " + info.getDescription(), tableCellFont2);
			Chunk ch2 = new Chunk("Transaction No #" + info.getTransactionCode(), tableCellFont2);
			p1.add(ch1);
			p1.add("\n");
			p1.add(ch2);
			PdfPCell c2 = new PdfPCell(p1);
			c2.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
			PdfPCell c3 = null;
			PdfPCell c4 = null;
			if (info.getTransactionType() == 'C') {
				c3 = new PdfPCell(new Paragraph(Double.toString(info.getTransactionAmount()), tableCellFont2));
			} else {
				c3 = new PdfPCell(new Paragraph("", tableCellFont2));
			}
			c3.setBackgroundColor(WebColors.getRGBColor("#DDDDDD"));
			if (info.getTransactionType() == 'D') {
				c4 = new PdfPCell(new Paragraph(Double.toString(info.getTransactionAmount()), tableCellFont2));
			} else {
				c4 = new PdfPCell(new Paragraph("", tableCellFont1));
			}
			c4.setBackgroundColor(WebColors.getRGBColor("#EEEEEE"));
			PdfPCell c5 = new PdfPCell(new Paragraph(Double.toString(info.getBalanceAmount()), tableCellFont3));
			c5.setBackgroundColor(WebColors.getRGBColor("#01104d"));
			c1.setBorder(PdfPCell.NO_BORDER);
			c2.setBorder(PdfPCell.NO_BORDER);
			c3.setBorder(PdfPCell.NO_BORDER);
			c4.setBorder(PdfPCell.NO_BORDER);
			c4.setBorder(PdfPCell.NO_BORDER);
			tableValues.addCell(c1);
			tableValues.addCell(c2);
			tableValues.addCell(c3);
			tableValues.addCell(c4);
			tableValues.addCell(c5);

			PdfPTable gapTable1 = new PdfPTable(1);
			PdfPCell gapCell1 = new PdfPCell();
			gapCell1.setFixedHeight(1);
			gapCell1.setBorder(PdfPCell.NO_BORDER);
			gapTable1.addCell(gapCell1);
			document.add(gapTable1);

			document.add(tableValues);
		}

		//
		PdfPTable thanksParaTable = new PdfPTable(1);
		PdfPCell thanksParaCell = new PdfPCell(new Paragraph("Thank you!"));
		thanksParaCell.setBorder(PdfPCell.NO_BORDER);
		thanksParaCell.setPaddingBottom(30);
		thanksParaTable.addCell(thanksParaCell);
		document.add(thanksParaTable);

		//
//		Font noteParaFont = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
//		Paragraph notePara = new Paragraph();
//		notePara.setFont(noteParaFont);
//		notePara.add("NOTE:");
//		notePara.add("\n");
//		notePara.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
//		PdfPTable noteParaTable = new PdfPTable(1);
//		PdfPCell noteParaCell = new PdfPCell(notePara);
//		noteParaCell.setPaddingLeft(10);
//		noteParaCell.setBorderWidthLeft(5);
//		noteParaCell.setBorderColorLeft(WebColors.getRGBColor("#0477C6"));
//		noteParaCell.setBorderColorRight(BaseColor.WHITE);
//		noteParaCell.setBorderColorBottom(BaseColor.WHITE);
//		noteParaCell.setBorderColorTop(BaseColor.WHITE);
//		noteParaTable.addCell(noteParaCell);
//		document.add(noteParaTable);

		//
		Font fontChunk1 = FontFactory.getFont(FontFactory.COURIER, 6, BaseColor.BLACK);
		Font fontChunk2 = FontFactory.getFont(FontFactory.COURIER, 6, BaseColor.BLUE);
		Paragraph footerPara = new Paragraph();
		Chunk a = new Chunk("For any support or assistance you may contact us on following ", fontChunk1);
		Chunk b = new Chunk("info@moneycore.com", fontChunk2);
		Chunk c = new Chunk(" or call us at ", fontChunk1);
		Chunk d = new Chunk("+31 9865242233.", fontChunk2);
		Chunk e = new Chunk(" We are happy to serve you. ", fontChunk1);
		Chunk f = new Chunk("www.moneycore.com", fontChunk2);
		footerPara.add(a);
		footerPara.add(b);
		footerPara.add(c);
		footerPara.add(d);
		footerPara.add("\n");
		footerPara.add(e);
		footerPara.add(f);
		PdfPTable footerParaTable = new PdfPTable(1);
		footerParaTable.setSpacingBefore(25);
		PdfPCell footerParaCell = new PdfPCell(footerPara);
		footerParaCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		footerParaCell.setBorderWidthTop(1);
		footerParaCell.setBorderColorTop(WebColors.getRGBColor("#AAAAAA"));
		footerParaCell.setBorderColorLeft(BaseColor.WHITE);
		footerParaCell.setBorderColorRight(BaseColor.WHITE);
		footerParaCell.setBorderColorBottom(BaseColor.WHITE);
		footerParaTable.addCell(footerParaCell);
		document.add(footerParaTable);

		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

	private Image setImage() throws BadElementException, IOException {
		String imagePath = "logo.png";
		Image image = Image.getInstance(imagePath);
		image.scaleToFit(130, 130);
		image.setAlignment(Element.ALIGN_LEFT);
		return image;
	}
}
