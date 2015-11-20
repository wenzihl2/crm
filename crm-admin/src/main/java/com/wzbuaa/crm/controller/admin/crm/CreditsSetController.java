package com.wzbuaa.crm.controller.admin.crm;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.crm.CreditsSetDomain;
import com.wzbuaa.crm.service.crm.CreditsSetService;

import framework.Message;
import framework.validation.annotation.RequiredFieldValidator;
import framework.validation.annotation.Validations;

/**
 * 后台Action类 - 转入设置
 */
@Controller
@RequestMapping("/admin/crm/creditsSet")
public class CreditsSetController extends BaseCRUDController<CreditsSetDomain, Long> {

	@Resource private CreditsSetService creditsSetService;
	
	public CreditsSetController() {
        setResourceIdentity("sys:creditsSet");
    }
	
	@RequestMapping(value="{id}/stop", method=RequestMethod.POST)
	@ResponseBody
	public Message stop(@PathVariable("id") Long id) {
		CreditsSetDomain m = creditsSetService.findOne(id);
		if(m.getShow() == Boolean.FALSE) {
			return Message.warn("此规则已经关闭，此操作非法!", null);
		}
		m.setShow(Boolean.FALSE);
		creditsSetService.update(m);
		return successMessage;
	}
	
	@RequestMapping(value="{id}/start", method=RequestMethod.POST)
	@ResponseBody
	public Message start(@PathVariable("id") Long id) {
		CreditsSetDomain m = creditsSetService.findOne(id);
		if(m.getShow() == Boolean.TRUE) {
			return Message.warn("此规则已经开启，此操作非法!", null);
		}
		m.setShow(Boolean.TRUE);
		creditsSetService.update(m);
		return successMessage;
	}

	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "ids", message = "ids非法"),
			@RequiredFieldValidator(fieldName = "id", message = "id非法")
		}
	)
	@RequestMapping(value="/relateInviterRule", method=RequestMethod.POST)
	@ResponseBody
	public Message realteInviterRule(Long[] ids, Long id, Boolean isInviter){
		if(isInviter){
			creditsSetService.realteInviterRule(ids, id);
		}else{
			creditsSetService.realteRule(ids, id);
		}
		return successMessage;
	}
}