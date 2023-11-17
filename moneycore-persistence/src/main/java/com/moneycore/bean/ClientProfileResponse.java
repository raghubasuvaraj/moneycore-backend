package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.Address;
import com.moneycore.entity.Client;
import com.moneycore.entity.CountryList;
import com.moneycore.entity.FileDB;
import com.moneycore.entity.NationalityList;
import com.moneycore.entity.ProfilePicture;

public class ClientProfileResponse {

	private String emailID;
	private String phonerNo;
	private String gender;
	private String dob;
	private NationalityList nationality;
	private String language;
	private List<ClientDocument> id;
	private Address address;
	private String profilePic;
	private String licenceImage;

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPhonerNo() {
		return phonerNo;
	}

	public void setPhonerNo(String phonerNo) {
		this.phonerNo = phonerNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<ClientDocument> getId() {
		return id;
	}

	public void setId(List<ClientDocument> id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public NationalityList getNationality() {
		return nationality;
	}

	public void setNationality(NationalityList nationality) {
		this.nationality = nationality;
	}


	public String getLicenceImage() {
		return licenceImage;
	}

	public void setLicenceImage(String licenceImage) {
		this.licenceImage = licenceImage;
	}

	public static ClientProfileResponse generateResponse(Client client, List<FileDB> documents,
														 ProfilePicture profilePicture) {
		List<ClientDocument> clientDocuments = new ArrayList<ClientDocument>();
		List<ClientAddress> clientAddresses = new ArrayList<ClientAddress>();
		ClientProfileResponse clientProfileResponse = new ClientProfileResponse();
		clientProfileResponse.setEmailID(client.getEmail());
		clientProfileResponse.setPhonerNo(client.getPrPhone1());
		clientProfileResponse.setGender(client.getGender());
		clientProfileResponse.setDob(client.getDateOfBirth());
		if(client.getNationalityCode()!=null)
			clientProfileResponse.setNationality(client.getNationalityCode());
		if(client.getLanguageCode()!=null)
			clientProfileResponse.setLanguage(client.getLanguageCode().getLanguageName());
		if (profilePicture != null) {
			clientProfileResponse.setProfilePic(profilePicture.getProfilePic());
		}
		if (client.getAddress() != null) {
			clientProfileResponse.setAddress(client.getAddress());
		}
		if (documents != null) {
			for (FileDB document : documents) {
				ClientDocument clientDocument = new ClientDocument();
				clientDocument.setIdType(document.getIdType());
				clientDocument.setIdNo(document.getIdNumber());
				clientDocument.setIdFrontImage(document.getFrontData());
				clientDocument.setIdBackImage(document.getBackData());
				clientDocument.setIdIssuingDate(document.getIdIssuingDate());
				clientDocument.setIdValidityDate(document.getIdValidityDate());
				clientDocuments.add(clientDocument);
			}
			clientProfileResponse.setId(clientDocuments);
		}
		clientProfileResponse.setLicenceImage(client.getLicenceImage());
		return clientProfileResponse;
	}
}
