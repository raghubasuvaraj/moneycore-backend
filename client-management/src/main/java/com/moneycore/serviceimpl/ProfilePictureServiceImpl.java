package com.moneycore.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.ProfilePictureData;
import com.moneycore.entity.ProfilePicture;
import com.moneycore.repository.ProfilePictureRepository;
import com.moneycore.service.ProfilePictureService;

@Service("profilePictureService")
public class ProfilePictureServiceImpl implements ProfilePictureService {

	@Autowired
	ProfilePictureRepository profilePictureRepository;

	@Override
	public Optional<ProfilePicture> findByClientCode(int clientCode) {
		return profilePictureRepository.findByClientCode(clientCode);
	}

	@Override
	public ProfilePicture store(int clientCode, ProfilePictureData pictureData, ProfilePicture profilePicture) {
//		String fileName = StringUtils.cleanPath(pictureData.getProfilePic().getOriginalFilename());
			profilePicture.setClientCode(clientCode);
			profilePicture.setProfilePic(pictureData.getProfilePic());
//			profilePicture.setName(fileName);
//			profilePicture.setType(pictureData.getProfilePic().getContentType());
			return profilePictureRepository.save(profilePicture);
	}

}
