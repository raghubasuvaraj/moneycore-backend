package com.moneycore.service;

import com.moneycore.entity.NotoficationService;

import java.util.List;
import java.util.Optional;

public interface NotificationServiceDefWall {

	public List<NotoficationService> findAllNotificationServices();

	public NotoficationService saveNotification(NotoficationService notification);

	public Optional<NotoficationService> findNotification(int addressId);

	public void delete(int notificationId);

	public List<NotoficationService> findbystatus();

	public List<NotoficationService> findbyclientcode(int clientCode);
}