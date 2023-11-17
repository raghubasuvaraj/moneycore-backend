package com.moneycore.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import com.moneycore.entity.StaticInfoDTO;
import com.moneycore.service.*;
import com.moneycore.util.CommonUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneycore.model.ResponseModel;

@RestController
@RequestMapping("/api/menumanagement")
public class ScreenManagementController {

	@Autowired
	private ScreenService screenService;


    @Autowired
	private StaticInfoService staticInfoService;
    @Autowired
	private FaqInfoService faqInfoService;

	@RequestMapping(value = {
			"/screen/register" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerInstitution(@Valid @RequestBody Screen newscreen) {
        Screen screeenSave = new Screen();
    try {
        if(!NumberUtils.isParsable(newscreen.getScreenId())){
            ResponseModel responseModel = new ResponseModel(false, "invalid screen id . only allow numeric value ", null);
            return ResponseEntity.accepted().body(responseModel);
        }
        List<Screen> screen = screenService.findByScreenCode(newscreen.getScreenCode());

        Boolean checkExistsByName=screenService.existsByName(newscreen.getName());
        Boolean checkExistsByScreenId =screenService.existsByScreenId(newscreen.getScreenId());
        Boolean checkExistsByScreenCode = screenService.existsByScreenCode(newscreen.getScreenCode());
        Boolean checkExistsByUrl =screenService.existsByUrl(newscreen.getUrl());

        if (checkExistsByScreenId.equals(true) ) {
            ResponseModel responseModel = new ResponseModel(false, "screen id is already exists", null);
            return ResponseEntity.accepted().body(responseModel);
        }

        if (checkExistsByScreenCode.equals(true) ) {
            ResponseModel responseModel = new ResponseModel(false, "screen code is already exists", null);
            return ResponseEntity.accepted().body(responseModel);
        }

        if (checkExistsByName.equals(true) ) {
            ResponseModel responseModel = new ResponseModel(false, "screen name is already exists", null);
            return ResponseEntity.accepted().body(responseModel);
        }

        if (checkExistsByUrl.equals(true) ) {
            ResponseModel responseModel = new ResponseModel(false, "screen url is already exists", null);
            return ResponseEntity.accepted().body(responseModel);
        }

        if (screen.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.codeexist", null),
					null);
			return ResponseEntity.accepted().body(responseModel);
		}
            screeenSave = screenService.insert(newscreen);

        }catch(Exception e){
            ResponseModel responseModel = new ResponseModel(false, "trying to process incorrect data", null);
            return ResponseEntity.ok().body(responseModel);
        }
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.code", null), screeenSave);
		return ResponseEntity.accepted().body(responseModel);
	}



    @RequestMapping(value = { "/screens" }, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> screenList(HttpServletRequest request) {
        List<Screen> screen=null;
        try {
        screen = screenService.findAll();
        }catch(Exception e){
            ResponseModel responseModel = new ResponseModel(false, "screen failed to load", null);
            return ResponseEntity.ok().body(responseModel);
        }
        ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.code", null), screen);
        return ResponseEntity.accepted().body(responseModel);
    }

    @RequestMapping(value = {"/screens/addAboutApp", "/screens/addPrivacyPolicy", "/screens/addTermsAndCondition", "/screens/addSupport"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<?> addStaticData(@Valid @RequestBody StaticInfoDTO staticInfoDTO) {
        String institutionCode = staticInfoDTO.getInstitutionCode();
        String aboutContent = staticInfoDTO.getAboutContent();
        String privacyPolicy = staticInfoDTO.getPrivacyPolicy();
        String termsAndCondition = staticInfoDTO.getTermsAndCondition();
        String support = staticInfoDTO.getSupport();
        String userCreate = staticInfoDTO.getUserCreate();
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        ResponseModel responseModel=  null;
        if (staticInfoList != null && staticInfoList.size() > 0) {
            if (aboutContent != null && staticInfoList.get(0).getAboutApp() != null) {
                String abc =Translator.toLocale("about.app.already.present",null);
                responseModel = new ResponseModel(false, abc, null);
                return ResponseEntity.accepted().body(responseModel);
            } else if (privacyPolicy != null && staticInfoList.get(0).getPrivacyPolicy() != null) {
                responseModel = new ResponseModel(false, Translator.toLocale("screen.policy", null), null);
                return ResponseEntity.accepted().body(responseModel);
            } else if (termsAndCondition != null && staticInfoList.get(0).getTermsAndCondition() != null) {
                responseModel = new ResponseModel(false, Translator.toLocale("screen.terms", null), null);
                return ResponseEntity.accepted().body(responseModel);
            } else if (support != null && staticInfoList.get(0).getSupport() != null) {
                responseModel = new ResponseModel(false, "support already Present.", null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }
        StaticInfo staticInfo;
        if (staticInfoList.size() > 0) {
            staticInfo = staticInfoList.get(0);
        } else {
            staticInfo = new StaticInfo();
        }
        String msg = "";
        if (aboutContent != null) {
            staticInfo.setAboutApp(aboutContent);
            msg = Translator.toLocale("about.app",null);
        } else if (privacyPolicy != null) {
            staticInfo.setPrivacyPolicy(privacyPolicy);
            msg = "Privacy policy";
        } else if (termsAndCondition != null) {
            staticInfo.setTermsAndCondition(termsAndCondition);
            msg = "Terms and condition";
        } else if (support != null) {
            staticInfo.setSupport(support);
            msg = Translator.toLocale("screen.supportview", null);
        }
        staticInfo.setInstitutionCode(institutionCode);
        staticInfo.setUserCreate(userCreate);
        staticInfo.setDateCreate(new Date());
        staticInfoService.saveOrUpdate(staticInfo);
        responseModel = new ResponseModel(true,
                Translator.toLocale("added.success",new Object[]{msg}), null);
        return ResponseEntity.accepted().body(responseModel);
    }

    @RequestMapping(value = {"/screens/updateAboutApp","/screens/updateTermsAndCondition","/screens/updatePrivacyPolicy","/screens/updateSupport"}, method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> updateStaticData(@Valid @RequestBody StaticInfoDTO staticInfoDTO) {
        String institutionCode = staticInfoDTO.getInstitutionCode();
        String aboutContent = staticInfoDTO.getAboutContent();
        String privacyPolicy = staticInfoDTO.getPrivacyPolicy();
        String termsAndCondition = staticInfoDTO.getTermsAndCondition();
        String support = staticInfoDTO.getSupport();
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null ||  staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            StaticInfo staticInfo = staticInfoList.get(0);
            String msg = "";
            if (aboutContent != null) {
                staticInfo.setAboutApp(aboutContent);
                msg = Translator.toLocale("screen.aboutapp", null);
            } else if (privacyPolicy != null) {
                staticInfo.setPrivacyPolicy(privacyPolicy);
                msg = Translator.toLocale("screen.privacypolicy", null);
            } else if (termsAndCondition != null) {
                staticInfo.setTermsAndCondition(termsAndCondition);
                msg = Translator.toLocale("screen.termscondition", null);
            } else if (support != null) {
                staticInfo.setSupport(support);
                msg = Translator.toLocale("screen.supportview", null);
            }
            staticInfo.setDateModif(new Date());
            staticInfoService.saveOrUpdate(staticInfo);
            ResponseModel responseModel = new ResponseModel(true, msg+" updated successfully", null);
            return ResponseEntity.accepted().body(responseModel);
        }
    }

    @RequestMapping(value = {"/screens/deleteAboutApp/{institutionCode}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> deleteAboutApp(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            if(staticInfoList.get(0).getAboutApp() != null){
                StaticInfo staticInfo = staticInfoList.get(0);
                staticInfo.setAboutApp(null);
                staticInfo.setDateModif(new Date());
                staticInfoService.saveOrUpdate(staticInfo);
                ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.appdeleted", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }else{
                ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }
    }

    @RequestMapping(value = {"/screens/getAboutApp/{institutionCode}"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAboutApp(@Valid  @PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            StaticInfo staticInfo = staticInfoList.get(0);
            Map<String, String> mp = null;
            if(staticInfo.getAboutApp() != null){
                mp = new HashMap<>();
                mp.put("institutionCode", institutionCode);
                mp.put("aboutContent", staticInfo.getAboutApp());
            }
            ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("about.app",null), mp);
            return ResponseEntity.accepted().body(responseModel);
        }
    }

    @RequestMapping(value = {"/screens/deletePrivacyPolicy/{institutionCode}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> deletePrivacyPolicy(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            if(staticInfoList.get(0).getPrivacyPolicy() != null){
                StaticInfo staticInfo = staticInfoList.get(0);
                staticInfo.setPrivacyPolicy(null);
                staticInfo.setDateModif(new Date());
                staticInfoService.saveOrUpdate(staticInfo);
                ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.policydeleted", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }else{
                ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }
    }

    @RequestMapping(value = {"/screens/getPrivacyPolicy/{institutionCode}"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getPrivacyPolicy(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            StaticInfo staticInfo = staticInfoList.get(0);
            Map<String, String> mp = null;
            if(staticInfo.getPrivacyPolicy() != null){
                mp = new HashMap<>();
                mp.put("institutionCode", institutionCode);
                mp.put("privacyPolicy", staticInfo.getPrivacyPolicy());
            }
            ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.privacypolicyview", null), mp);
            return ResponseEntity.accepted().body(responseModel);
        }
    }

    @RequestMapping(value = {"/screens/deleteTermsAndCondition/{institutionCode}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> deleteTermsAndCondition(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            if(staticInfoList.get(0).getTermsAndCondition() != null){
                StaticInfo staticInfo = staticInfoList.get(0);
                staticInfo.setTermsAndCondition(null);
                staticInfo.setDateModif(new Date());
                staticInfoService.saveOrUpdate(staticInfo);
                ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.termsdeleted", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }else{
                ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }
    }

    @RequestMapping(value = {"/screens/getTermsAndCondition/{institutionCode}"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getTermsAndCondition(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if(staticInfoList == null || staticInfoList.size() == 0){
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else{
            StaticInfo staticInfo = staticInfoList.get(0);
            Map<String, String> mp = null;
            if(staticInfo.getTermsAndCondition() != null){
                mp = new HashMap<>();
                mp.put("institutionCode", institutionCode);
                mp.put("termsAndCondition", staticInfo.getTermsAndCondition());
            }
            ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.termscondition", null), mp);
            return ResponseEntity.accepted().body(responseModel);
        }
    }


    @RequestMapping(value = {"/screens/deleteSupport/{institutionCode}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<?> deleteSupport(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if (staticInfoList == null || staticInfoList.size() == 0) {
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        } else {
            if (staticInfoList.get(0).getSupport() != null) {
                StaticInfo staticInfo = staticInfoList.get(0);
                staticInfo.setSupport(null);
                staticInfo.setDateModif(new Date());
                staticInfoService.saveOrUpdate(staticInfo);
                ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.supportdelete", null), null);
                return ResponseEntity.accepted().body(responseModel);
            } else {
                ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }
    }

    @RequestMapping(value = {"/screens/getSupport/{institutionCode}"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getSupport(@PathVariable String institutionCode) {
        List<StaticInfo> staticInfoList = staticInfoService.fetch(institutionCode);
        if (staticInfoList == null || staticInfoList.size() == 0) {
            ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("screen.nodatafound", null), null);
            return ResponseEntity.accepted().body(responseModel);
        } else {
            StaticInfo staticInfo = staticInfoList.get(0);
            Map<String, String> mp = null;
            if(staticInfo.getTermsAndCondition() != null){
                mp = new HashMap<>();
                mp.put("institutionCode", institutionCode);
                mp.put("support", staticInfo.getSupport());
            }
            ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.supportview", null), mp);
            return ResponseEntity.accepted().body(responseModel);
        }
    }

    @RequestMapping(value = {"/screens/addFaq"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> addFaqData(@Valid @RequestBody StaticInfoDTO staticInfoDTO) {
        String institutionCode = staticInfoDTO.getInstitutionCode();
        List<Map<String, String>> faqInList = staticInfoDTO.getFaq();
        String userCreate = staticInfoDTO.getUserCreate();

        if(faqInList!= null && faqInList.size()>0){
            for(Map<String, String> faqMp : faqInList) {
                FaqInfo faqInfo = new FaqInfo();
                faqInfo.setAnswer(faqMp.get("answer"));
                faqInfo.setQuestion(faqMp.get("question"));
                faqInfo.setInstitutionCode(institutionCode);
                faqInfo.setUserCreate(userCreate);
                faqInfo.setDateCreate(new Date());
                 faqInfoService.saveOrUpdate(faqInfo);
            }
        }
        ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("screen.faq", null), null);
        return ResponseEntity.accepted().body(responseModel);
    }

    @RequestMapping(value = {"/screens/updateFaq"}, method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<?> updateFaqData(@Valid  @RequestBody Map<String, String> requestbody) {
        String institutionCode = requestbody.get("institutionCode");
        int id = Integer.parseInt(requestbody.get("id"));
        String answer = requestbody.get("answer");
        String question = requestbody.get("question");
        List<FaqInfo> faqInfoList = faqInfoService.fetch(id, institutionCode);
        ResponseModel responseModel = null;
        if (faqInfoList == null || faqInfoList.size() == 0) {
            responseModel = new ResponseModel(false, Translator.toLocale("screen.nodata", null), null);
            return ResponseEntity.accepted().body(responseModel);
        } else {
            for (FaqInfo faqInfo : faqInfoList) {
                faqInfo.setAnswer(answer);
                faqInfo.setQuestion(question);
                faqInfo.setInstitutionCode(institutionCode);
                faqInfo.getDateModif(new Date());
                faqInfoService.saveOrUpdate(faqInfo);
            }
        }
        responseModel = new ResponseModel(true, Translator.toLocale("screen.faqupdate", null), null);
        return ResponseEntity.accepted().body(responseModel);
    }

    @RequestMapping(value = {"/screens/deleteFaq/{institutionCode}/{id}"}, method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> deleteFaqData(@PathVariable String institutionCode, @PathVariable int id) {
        List<FaqInfo> faqInfoList = faqInfoService.fetch(id, institutionCode);
        ResponseModel responseModel = null;
        if (faqInfoList == null || faqInfoList.size() == 0) {
            responseModel = new ResponseModel(false, Translator.toLocale("screen.nodata", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else {
            for (FaqInfo faqInfo : faqInfoList) {
                faqInfoService.delete(faqInfo);
            }
        }
        responseModel = new ResponseModel(true, Translator.toLocale("screen.faqdelete", null), null);
        return ResponseEntity.accepted().body(responseModel);
    }
    @RequestMapping(value = {"/screens/getFaq/{institutionCode}"}, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getFaqData(@PathVariable String institutionCode) {
        List<FaqInfo> faqInfoList = faqInfoService.fetch(institutionCode);
        ResponseModel responseModel = null;
        if (faqInfoList == null || faqInfoList.size() == 0) {
            responseModel = new ResponseModel(false, Translator.toLocale("screen.instinodate", null), null);
            return ResponseEntity.accepted().body(responseModel);
        }else {
            responseModel = new ResponseModel(true, Translator.toLocale("screen.faqview", null), faqInfoList);
        }
        return ResponseEntity.accepted().body(responseModel);
    }

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = {
            "/screens/delete/{screenCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> deleteMenu(@PathVariable("screenCode") String screenCode) {
        Optional<Screen> screen = screenService.find(screenCode);
        try {
            if (screen == null) {
                ResponseModel responseModel = new ResponseModel(false, "No Screen found with this screen code", null);
                return ResponseEntity.status(200).body(responseModel);
            }

            List<GrantPermission> permissionCheck = screenService.findByScreen(screenCode);
            long getPermiSize = 0;
            if (permissionCheck.size() > 0) {
                getPermiSize = permissionCheck.stream().filter(permi -> permi.getScreenFk() != null && screenCode.equalsIgnoreCase(permi.getScreenFk().getScreenCode())).count();
            }
            if (getPermiSize > 0) {
                ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  Screen Permission You Can't Delete Screen", null);
                return ResponseEntity.status(200).body(responseModel);
            }
            List<Menu> listMenu = menuService.findAll();
            long getMenuSize = 0;

            if (listMenu.size() > 0) {
                getMenuSize = listMenu.stream().filter(menu -> menu.getScreenReferedFk() != null && screenCode.equalsIgnoreCase(menu.getScreenReferedFk().getScreenCode())).count();
            }
            if (getMenuSize > 0) {
                ResponseModel responseModel = new ResponseModel(false, "Without Deleting the Child Menu You Can't Delete Main Screen", null);
                return ResponseEntity.status(200).body(responseModel);
            } else {
                screenService.delete(screenCode);
                ResponseModel responseModel = new ResponseModel(true, "Screen deleted successfully", null);
                return ResponseEntity.accepted().body(responseModel);
            }
        }catch(Exception e){
            ResponseModel responseModel = new ResponseModel(false, "error occurred in delete", null);
            return ResponseEntity.ok().body(responseModel);
        }

    }
}
