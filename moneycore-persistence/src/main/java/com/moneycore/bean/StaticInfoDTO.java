package com.moneycore.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StaticInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String institutionCode;
	private String aboutContent;
	private String privacyPolicy;
	private String termsAndCondition;
	private String support;
	private List<Map<String, String>> faq;
	private String userCreate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getAboutContent() {
		return aboutContent;
	}

	public void setAboutContent(String aboutContent) {
		this.aboutContent = aboutContent;
	}

	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public List<Map<String, String>> getFaq() {
		return faq;
	}

	public void setFaq(List<Map<String, String>> faq) {
		this.faq = faq;
	}
}
