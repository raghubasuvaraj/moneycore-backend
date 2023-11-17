package com.moneycore.service;

import java.util.Optional;

import com.moneycore.bean.ProfilePictureData;
import com.moneycore.entity.ProfilePicture;

public interface ProfilePictureService {

	ProfilePicture store(int clientCode, ProfilePictureData pictureData, ProfilePicture profilePicture);

	Optional<ProfilePicture> findByClientCode(int clientCode);

}
