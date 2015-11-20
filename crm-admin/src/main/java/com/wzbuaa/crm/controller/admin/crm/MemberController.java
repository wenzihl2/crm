package com.wzbuaa.crm.controller.admin.crm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.service.sso.PasswordService;
import com.wzbuaa.crm.util.BeanUtils;

import framework.Message;

/**
 * 后台Action类 - 会员
 */
@Controller
@RequestMapping("/admin/crm/member")
public class MemberController extends BaseCRUDController<MemberDomain, Long> {
	
	@Resource private MemberService memberService;
	@Resource private PasswordService passwordService;
	
	public MemberController() {
        setResourceIdentity("sys:member");
    }
	
	@Override
	@RequestMapping(value="create/discard", method=RequestMethod.POST)
	public String create(Model model, MemberDomain m, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	// 保存
	@RequestMapping(value="create", method=RequestMethod.POST)
	@ResponseBody
	public Message create(MemberDomain member) {
		member.randomSalt();
		member.setPassword(passwordService.encryptPassword(member.getUsername(), member.getPassword(), member.getSalt()));
		member.setIsAccountLocked(false);	//初始锁定状态
		member.setUseFailureCount(0); // 连续使用失败的次数
		member.setLockedDate(null);
		//保存
		memberService.save(member);
		return successMessage;
	}
	
	@Override
	@RequestMapping(value="{id}/update/discard", method=RequestMethod.POST)
	public String update(Model model, MemberDomain m, BindingResult result,
			String backURL, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value="{id}/update",method=RequestMethod.POST)
	@ResponseBody
	public Message update(MemberDomain member) {
		MemberDomain persistent = memberService.findOne(member.getId());
		if (StringUtils.isNotBlank(member.getPassword())) {
			member.randomSalt();
			member.setPassword(passwordService.encryptPassword(member.getUsername(), member.getPassword(), member.getSalt()));
		}
		BeanUtils.copyNotNullProperties(member, persistent);
		memberService.update(persistent);
		return successMessage;
	}
    
	/**
	 * 锁定
	 * @return
	 */
	@RequestMapping(value="/forbid",method=RequestMethod.POST)
	@ResponseBody
	public Message forbid(Long[] ids, HttpServletResponse response){
		MemberDomain member = null;
		for(Long id : ids){
    		member=	memberService.findOne(id);
    		member.setIsAccountLocked(true);
    		memberService.update(member);
    	}
    	return successMessage;
    }
    
    /**
	 * 解锁
	 * @return
	 */
	@RequestMapping(value="/using",method=RequestMethod.POST)
	@ResponseBody
	public Message using(Long[] ids){
		MemberDomain member = null;
    	for(Long id : ids){
    		member = memberService.findOne(id);
    		member.setIsAccountLocked(false);
    		memberService.update(member);
    	}
    	return successMessage;
    }
}