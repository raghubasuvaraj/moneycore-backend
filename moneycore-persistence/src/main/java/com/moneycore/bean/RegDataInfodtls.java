package com.moneycore.bean;

public class RegDataInfodtls {
	
	private String month;
	private Long totalInstitutions;
	private Long totalClients;
	private Long totalMerchants;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getTotalInstitutions() {
		return totalInstitutions;
	}
	public void setTotalInstitutions(Long totalInstitutions) {
		this.totalInstitutions = totalInstitutions;
	}
	public Long getTotalClients() {
		return totalClients;
	}
	public void setTotalClients(Long totalClients) {
		this.totalClients = totalClients;
	}
	public Long getTotalMerchants() {
		return totalMerchants;
	}
	public void setTotalMerchants(Long totalMerchants) {
		this.totalMerchants = totalMerchants;
	}
	public RegDataInfodtls(String month, Long totalInstitutions, Long totalClients, Long totalMerchants) {
		super();
		this.month = month;
		this.totalInstitutions = totalInstitutions;
		this.totalClients = totalClients;
		this.totalMerchants = totalMerchants;
	}
	public RegDataInfodtls() {
		super();
	}
	
	
	

}
