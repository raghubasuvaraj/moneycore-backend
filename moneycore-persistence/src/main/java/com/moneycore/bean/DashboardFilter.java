package com.moneycore.bean;

public class DashboardFilter {
    private String institutionCode;
    private String branchCode;
    private String filterFromDate;
    private String filterToDate;

    public DashboardFilter(String institutionCode, String branchCode, String filterFromDate, String filterToDate) {
        this.institutionCode = institutionCode;
        this.branchCode = branchCode;
        this.filterFromDate = filterFromDate;
        this.filterToDate = filterToDate;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFilterFromDate() {
        return filterFromDate;
    }

    public void setFilterFromDate(String filterFromDate) {
        this.filterFromDate = filterFromDate;
    }

    public String getFilterToDate() {
        return filterToDate;
    }

    public void setFilterToDate(String filterToDate) {
        this.filterToDate = filterToDate;
    }
}
