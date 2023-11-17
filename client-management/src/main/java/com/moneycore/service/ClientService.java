package com.moneycore.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.bean.*;
import com.moneycore.entity.Address;
import com.moneycore.entity.Client;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.MccList;
import com.moneycore.entity.Wallet;

public interface ClientService {

	public void saveClient(Client client, boolean b);

	public List<Client> getAllClients();

	public Client insert(ClientRegister clientRegister, Address address, InstitutionList institute);

	public List<Client> findAllClients();

	public Client findByClientByID(int id);

	public List<Client> findUserByContact(String contact);

	public List<Client> getMerchantByMccCode(String instituteCode, List mccCode);

	public List<Client> findUserByEmail(String email);

	public Client updateClient(Client client, ClientRegister clientRegister);

	public Client getClientByEmail(String email);

	public String randomPassword();

    public Client updateClientLanguage(Client client, String languageCode);

	public Client updateClientNationality(Client client, String nationalityCode);

	public Client updateClientCountry(Client client, String nationalityCode);

	public Client getClientByPhoneNumber(String phoneNumber);

	public Wallet getWallet(String walletId);
	public Client updateClient(Client client);

    int getTotalInstitutions(HttpServletRequest request);

	int getTotalBranch(HttpServletRequest request);

	Map<String,Long> getTotalClientsAndMerchnat(HttpServletRequest request);

	String getTotalWalletSpaceDetail(HttpServletRequest request);


	public DashboardInfo getDashboardInfo();
	public List<RegDataInfodtls> getRegDataInfo(String lastMonth);

	DashboardInfodtls getDashboardDetails(DashboardFilter dashboardFilter);

	public MccList getMccListByMccCode(String institutionCode, String mccCode);
	public void updateDeviceTokenBackOffice(Client client, String addressUpdate);
	public Client updateDeviceToken(Client client, String deviceToken);

	public List<String> getTokenDetails(List<String> institutionList
	,List<String> branchList,List<String> clientList);
	public void updateAddressToken(Client client, String addressUpdate);
	public String updateMpin(MpinUpdateInfo mpinUpdateInfo);
	String getWalletByClientCode(HttpServletRequest request, int clientCode);

	Wallet updateWallet(HttpServletRequest request, String walletId, String phoneNo);

	public void delete(int clientCode);

	public void hardDelete(int clientCode);
}
