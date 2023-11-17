package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.StatusReasonCodeInfo;
import com.moneycore.entity.StatusList;
import com.moneycore.entity.StatusReasonCode;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.StatusReasonCodeRepository;
import com.moneycore.service.StatusReasonCodeService;
import com.moneycore.util.CommonUtil;

@Service("statusReasonCodeService")
@Transactional
public class StatusReasonCodeServiceImpl implements StatusReasonCodeService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	StatusReasonCodeRepository statusReasonCodeRepository;

	public boolean findstatusdup(String StatusResName,String institutionCode ){

		return  statusReasonCodeRepository.existsByStatusReasonNameAndInstitutionCode(StatusResName,institutionCode);

	}
	public List<StatusReasonCode> findstatusdup(String institutionCode,String StatusResName ,String StatusResCode){

		return  statusReasonCodeRepository.findByStatusReasonName(institutionCode,StatusResName,StatusResCode);

	}
	@Override
	public StatusReasonCode findStatusReason(String institutionCode, String statusCode, String statusReasonCode) {
		StatusReasonCode code = null;
		Optional<StatusReasonCode> optional = statusReasonCodeRepository.find(institutionCode, statusCode,
				statusReasonCode);
		if (optional.isPresent()) {
			code = optional.get();
		}
		return code;
	}

	@Override
	public StatusReasonCode save(StatusReasonCode statusReasonCode, StatusReasonCodeInfo statusReasonCodeInfo) {
		statusReasonCode = new StatusReasonCode();
		statusReasonCode.setInstitutionCode(statusReasonCodeInfo.getInstitutionCode());
		statusReasonCode.setStatusCode(getStatus(statusReasonCodeInfo.getStatusCode()));
		statusReasonCode.setStatusReasonCode(statusReasonCodeInfo.getStatusReasonCode());
		statusReasonCode.setStatusReasonName(statusReasonCodeInfo.getStatusReasonName());
		statusReasonCode.setAbrvWording(statusReasonCodeInfo.getAbbreviation());
		statusReasonCode.setWording(statusReasonCodeInfo.getWording());
		statusReasonCode.setUserCreate(statusReasonCodeInfo.getUserCreate());
		statusReasonCode.setDateCreate(new Date());
		return statusReasonCodeRepository.save(statusReasonCode);
	}

	@Override
	public StatusReasonCode update(StatusReasonCode statusReasonCode, StatusReasonCodeInfo statusReasonCodeInfo) {
		StatusReasonCode statusReasonCode2 = new StatusReasonCode();
		statusReasonCode2.setInstitutionCode(statusReasonCode.getInstitutionCode());
		statusReasonCode2.setStatusCode(getStatus(statusReasonCodeInfo.getStatusCode()));
		statusReasonCode2.setStatusReasonCode(statusReasonCodeInfo.getStatusReasonCode());
		statusReasonCode2.setStatusReasonName(statusReasonCodeInfo.getStatusReasonName());
		statusReasonCode2.setAbrvWording(statusReasonCodeInfo.getAbbreviation());
		statusReasonCode2.setWording(statusReasonCodeInfo.getWording());
		statusReasonCode2.setUserCreate(statusReasonCode.getUserCreate());
		statusReasonCode2.setDateCreate(statusReasonCode.getDateCreate());
		statusReasonCode2.setUserModif(statusReasonCodeInfo.getUserModif());
		statusReasonCode2.setDateModif(new Date());
		statusReasonCodeRepository.delete(statusReasonCode);
		return statusReasonCodeRepository.save(statusReasonCode2);
	}

	@Override
	public List<StatusReasonCode> fetchStatusReason(String institutionCode, String statusCode) {
		if (statusCode != null) {
			return statusReasonCodeRepository.findList(institutionCode, statusCode);
		}
		return statusReasonCodeRepository.findListIC(institutionCode);
	}

	@Override
	public StatusReasonCode findStatusReasonByCode(String statusReasonCode) {
		StatusReasonCode code = null;
		Optional<StatusReasonCode> optional = statusReasonCodeRepository.find(statusReasonCode);
		if (optional.isPresent()) {
			code = optional.get();
		}
		return code;
	}

	@Override
	public StatusReasonCode findStatusReasonCodeByStatusCode(String institutionCode, String statusCode) {
		StatusReasonCode code = null;
		Optional<StatusReasonCode> optional = statusReasonCodeRepository.findStatusReasonCodeByStatusCode(institutionCode, statusCode);
		if (optional.isPresent()) {
			code = optional.get();
		}
		return code;
	}

	private StatusList getStatus(String statusCode) {
		ResponseModel responseModel = null;
		StatusList statusList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/status/" + statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			statusList = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusList.class);
		}
		return statusList;
	}


	public void delete(String statusReasonCode) {
		statusReasonCodeRepository.deleteById(statusReasonCode);
	}

}
