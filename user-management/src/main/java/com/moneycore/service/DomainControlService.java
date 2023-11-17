package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.DomainControlInfo;
import com.moneycore.entity.DomainControl;

public interface DomainControlService {

	DomainControl save(DomainControlInfo domainControlInfo);

	List<DomainControl> fetchDomainControl(String institutionCode, String controlIndex);

	DomainControl update(DomainControl domainControl, DomainControlInfo domainControlInfo);

	DomainControl fetch(String institutionCode, String controlIndex);

	DomainControl findDefaultCheck(String institutionCode);

	boolean checkIfClientHasDomainControl(String institutionCode, String clientCode);
	public void delete(String code);

	DomainControl findcontrolIndex(String controlIndex);
}
