package com.moneycore.service;

import com.moneycore.bean.ClientAddress;
import com.moneycore.entity.Address;

public interface AddressService {

//	public List<Address> findByClientCode(int clientCode);

	public Address saveAddress(ClientAddress address);

	public Address findAddress(int addressId);

	public Address updateAddress(Address address, ClientAddress clientAddress);

	public void delete(int addressId);

//	public Address fetchAddress(int clientId);

}
