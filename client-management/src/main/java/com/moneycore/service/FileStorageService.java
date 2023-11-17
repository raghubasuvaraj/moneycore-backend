package com.moneycore.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.moneycore.bean.ClientDocument;
import com.moneycore.entity.FileDB;
import com.moneycore.repository.FileDBRepository;

@Service
public class FileStorageService {

	@Autowired
	private FileDBRepository fileDBRepository;

	public FileDB store(MultipartFile file, int clientID) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), clientID);
		return fileDBRepository.save(FileDB);
	}

	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}

	public List<FileDB> getAllListFiles() {
		return fileDBRepository.findAll();
	}

	public Optional<FileDB> findDocument(int clientCode, String idType) {
		return fileDBRepository.findDocument(clientCode,idType);
	}

	public FileDB update(FileDB fileDB, int clientCode, String idType, String idNumber, String frontImage,
			String backImage) throws IOException {
//		String FrontFileName = StringUtils.cleanPath(frontImage.getOriginalFilename());
//		String BackFileName = StringUtils.cleanPath(backImage.getOriginalFilename());
		fileDB.setClientId(clientCode);
//		fileDB.setType(frontImage.getContentType() + " " + backImage.getContentType());
		fileDB.setIdType(idType);
		fileDB.setIdNumber(idNumber);
		fileDB.setBackData(backImage);
		fileDB.setFrontData(frontImage);
//		fileDB.setBackName(BackFileName);
//		fileDB.setFrontName(FrontFileName);
		return fileDBRepository.save(fileDB);
	}

	public List<FileDB> findByClientCode(int clientCode) {
		return fileDBRepository.findByClientCode(clientCode);
	}

	public FileDB saveDocument(int clientCode, ClientDocument document) {
		FileDB fileDB = new FileDB();
		fileDB.setClientId(clientCode);
		fileDB.setFrontData(document.getIdFrontImage());
		fileDB.setBackData(document.getIdBackImage());
		fileDB.setIdType(document.getIdType());
		fileDB.setIdNumber(document.getIdNo());
		fileDB.setIdIssuingDate(document.getIdIssuingDate());
		fileDB.setIdValidityDate(document.getIdValidityDate());
		return fileDBRepository.save(fileDB);
	}

	public void deleteDocument(int clientCode, String idType) {
		fileDBRepository.delete(clientCode, idType);
	}

	public boolean checkFileExist(String idNumber){
		return  fileDBRepository.existsByIdNumber(idNumber);
	}
}
