package com.moneycore.serviceimpl;

import com.moneycore.bean.ClientAddress;
import com.moneycore.entity.Address;
import com.moneycore.entity.CityList;
import com.moneycore.entity.CountryList;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.AddressRepository;
import com.moneycore.service.AddressService;
import com.moneycore.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	RestTemplate restTemplate;

//	@Override
//	public List<Address> findByClientCode(int clientCode) {
//		return addressRepository.findByClientCode(clientCode);
//	}

	@Override
	public Address saveAddress(ClientAddress address) {
		Address addressClient = new Address();
		addressClient.setAddressLine1(address.getAddress1());
		addressClient.setAddressLine2(address.getAddress2());
		addressClient.setAddressLine3(address.getAddress3());
		addressClient.setAddressLine4(address.getAddress4());
		addressClient.setCity(getCityByCode(address.getCity()));
		addressClient.setCountry(getCountryByCode(address.getCountry()));
		addressClient.setPostalCode(address.getPostalCode());
		return addressRepository.save(addressClient);
	}

	@Override
	public Address findAddress(int addressId) {
		Address address = null;
		Optional<Address> optional = addressRepository.findAddress(addressId);
		if (optional.isPresent()) {
			address = optional.get();
		}
		return address;
	}

	@Override
	public Address updateAddress(Address address, ClientAddress clientAddress) {
		address.setAddressLine1(clientAddress.getAddress1());
		address.setAddressLine2(clientAddress.getAddress2());
		address.setAddressLine3(clientAddress.getAddress3());
		address.setAddressLine4(clientAddress.getAddress4());
		address.setCity(getCityByCode(clientAddress.getCity()));
		address.setCountry(getCountryByCode(clientAddress.getCountry()));
		address.setPostalCode(clientAddress.getPostalCode());
		return addressRepository.save(address);
	}

	@Override
	public void delete(int addressId) {
		addressRepository.deleteById(addressId);
	}

//	@Override
//	public Address fetchAddress(int clientId) {
//		Address address = null;
//		Optional<Address> optional = addressRepository.findAddress(clientId);
//		if (optional.isPresent()) {
//			address = optional.get();
//		}
//		return address;
//	}
	private CountryList getCountryByCode(String countryCode) {
		ResponseModel responseModel = null;
		CountryList countryList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/country/"+ countryCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			countryList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CountryList.class);
		}
		return countryList;
	}
	private CityList getCityByCode(String cityCode) {
		ResponseModel responseModel = null;
		CityList cityList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/cities/"+ cityCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			cityList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CityList.class);
		}
		return cityList;
	}
}
