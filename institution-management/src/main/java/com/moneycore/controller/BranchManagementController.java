package com.moneycore.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.InstitutionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneycore.entity.Branch;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.BranchService;

@RestController
@RequestMapping("/api/institutionmanagement")
public class BranchManagementController {

	@Autowired
	private BranchService branchService;

	@RequestMapping(value = { "/internal/branch/{branchcode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getAddress(HttpServletRequest request,
			@PathVariable("branchcode") String branchCode) {
		Optional<Branch> optionalBranch = branchService.find(branchCode);
		Branch branch = null;
		if (optionalBranch.isPresent()) {
			branch = optionalBranch.get();
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("branch.branchcode", null), branch);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/branch/register" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerInstitution(@Valid @RequestBody Branch newBranch) {
		Boolean branchdup=branchService.findbranchdup(newBranch.getBranchName());

		if (branchdup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "branch name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Branch branchList = branchService.insert(newBranch);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("branch.branchcode", null), branchList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/branches" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> branchList(HttpServletRequest request,
			@RequestParam(required = false) String institutionCode) {
		List<Branch> branch = branchService.findByIC(institutionCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("branch.branchcode", null), branch);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/branch/{branchcode}" }, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateBranch(@PathVariable("branchcode") String branchCode,
			@RequestBody Branch branch) {
		Optional<Branch> optionalBranch = branchService.find(branchCode);
		if (!optionalBranch.isPresent()) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("branch.branchcodeinvalid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		List<Branch> branchdup=branchService.findbranchdup(branch.getBranchName(),branch.getInstitutionList().getInstitutionCode(),branchCode);

		if (branchdup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "branch name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		branch.setBranchCode(branchCode);
		branch.setDateModif(new Date());
		Branch branchResponse = branchService.update(branch);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("branch.branchcodeupdated", null), branchResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/branch/delete/{branchcode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletebranchcode(@PathVariable("branchcode") String branchcode) {
		Branch BranchList = branchService.findbranchCode(branchcode);
		if ( BranchList == null) {
			ResponseModel responseModel = new ResponseModel(true, "Branch is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		branchService.delete(branchcode);
		ResponseModel responseModel = new ResponseModel(true, " Branch  deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}
}
