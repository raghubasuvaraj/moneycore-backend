package com.moneycore.serviceimpl;

import com.moneycore.entity.NotoficationService;
import com.moneycore.repository.NotificationRepository;
import com.moneycore.service.NotificationServiceDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationServiceDef {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<NotoficationService> findAllNotificationServices() {
		List<NotoficationService> list=notificationRepository.findAll();
		return list;
	}
	public List<NotoficationService> findbyclientcode(int clientCode) {
		List<NotoficationService> list=notificationRepository.findclientcode(clientCode);
		return list;
	}

	public List<NotoficationService> findbystatus() {
		List<NotoficationService> list=notificationRepository.findstatus();
		return list;
	}

	@Override
	public NotoficationService saveNotification(NotoficationService notification) {
		NotoficationService service = notificationRepository.save(notification);
		return null;
	}

	@Override
	public Optional<NotoficationService> findNotification(int notificationId) {
		Optional<NotoficationService> service = notificationRepository.findById(notificationId);
		return service;
	}

	@Override
	public void delete(int notificationId) {
		notificationRepository.deleteById(notificationId);
	}

}
