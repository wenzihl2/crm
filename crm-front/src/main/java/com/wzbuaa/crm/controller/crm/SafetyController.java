package com.wzbuaa.crm.controller.crm;

import static com.wzbuaa.crm.domain.Constants.CURRENT_FRONT_USER;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain.EmailMsgType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.SmsMsgType;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.interceptor.TokenAnnotation;
import com.wzbuaa.crm.service.crm.EmailMsgService;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.service.crm.SmsMsgService;
import com.wzbuaa.crm.service.sso.PasswordService;

import framework.Message;
import framework.spring.mvc.bind.annotation.CurrentUser;

@Controller
@RequestMapping("/member/safe")
public class SafetyController extends BaseController {
	
	@Resource private MemberService memberService;
	@Resource private SmsMsgService smsMsgService;
	@Resource private EmailMsgService emailMsgService;
	@Resource private PasswordService passwordService;

	@RequestMapping(value = "success")
    public String success() {
        return viewName("success");
    }
	
	/**
	 * 安全中心页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String safety(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, Model model) {
		model.addAttribute("member", member);
		return viewName("safe");
	}
	
	/**
	 * 验证邮箱
	 */
	@RequestMapping(value = "{emailMsgType}/validEmail", method=RequestMethod.POST)
	public String validEmail(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			@PathVariable("emailMsgType") EmailMsgType emailMsgType, String recoverKey){
		EmailMsgDomain emailMsg = emailMsgService.findByValidMsg(emailMsgType, recoverKey, member);
		if(emailMsg == null || !emailMsg.isValid()) {
			return redirectToUrl(backURL);
		}
		if(emailMsgType == EmailMsgType.Modify) {
			emailMsg.setIsConfirm(true);
			emailMsgService.update(emailMsg);
		}
		return redirectToUrl("success");
	}

	/**
	 * 验证验证码
	 */
	@TokenAnnotation(valid=true)
	@RequestMapping(value = "{smsMsgType}/validSms", method=RequestMethod.POST)
	public String validSms(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			@PathVariable("smsMsgType") SmsMsgType smsMsgType, String smsRecoverKey){
		SmsMsgDomain smsMsg = smsMsgService.findByValidMsg(smsMsgType, smsRecoverKey, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl(backURL);
		}
		if(smsMsgType == SmsMsgType.passwordNotify) {
			return redirectToUrl("member/safe/password/updatePassword?r=" + smsMsg.getSn());
		} else if(smsMsgType == SmsMsgType.mobileNotify) {
			return redirectToUrl("member/safe/mobile/updateMobile?r=" + smsMsg.getSn());
		} else if(smsMsgType == SmsMsgType.emailNotify) {
			return redirectToUrl("member/safe/email/updateEmail?r=" + smsMsg.getSn());
		} else if(smsMsgType == SmsMsgType.payPasswordNotify) {
			return redirectToUrl("member/safe/payPassword/updatePayPassword?r=" + smsMsg.getSn());
		}
		return null;
	}

	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "{smsMsgType}/sendSms", method=RequestMethod.POST)
	@ResponseBody
	public Message sendSms(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@PathVariable("smsMsgType") SmsMsgType smsMsgType){
		Map<String, Object> param = new HashMap<String, Object>();
		smsMsgService.sendMessage(member, member.getMobile(), smsMsgType, param);
		return successMessage;
	}
	
	/**
	 * 密码修改，第一步先到手机验证页面
	 */
	@TokenAnnotation
	@RequestMapping(value = "updatePassword", method = RequestMethod.GET)
	public String updatePassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, Model model, String type) {
		model.addAttribute("member", member);
		model.addAttribute("smsMsgType", SmsMsgType.passwordNotify);
		if("email".equals(type)){
			return viewName("verifyEmail");
		} else if("payPassword".equals(type)) {
			return viewName("verifyPayPassword");
		}
		return viewName("verifyMobile");
	}

	/**
	 * 手机号码修改
	 */
	@TokenAnnotation
	@RequestMapping(value = "updateMobile", method = RequestMethod.GET)
	public String updateMobile(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, Model model, String type){
		model.addAttribute("member", member);
		model.addAttribute("smsMsgType", SmsMsgType.mobileNotify);
		if("email".equals(type)){
			return viewName("verifyEmail");
		} else if("payPassword".equals(type)) {
			return viewName("verifyPayPassword");
		}
		return viewName("verifyMobile");
	}
	
	/**
	 * 支付密码修改
	 */
	@TokenAnnotation
	@RequestMapping(value = "updatePayPassword", method = RequestMethod.GET)
	public String updatePayPassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, Model model, String type){
		model.addAttribute("member", member);
		model.addAttribute("smsMsgType", SmsMsgType.payPasswordNotify);
		if("email".equals(type)){
			return viewName("verifyEmail");
		} else if("payPassword".equals(type)) {
			return viewName("verifyPayPassword");
		}
		return viewName("verifyMobile");
	}
	
	/**
	 * 密码修改，第一步先到手机验证页面
	 */
	@TokenAnnotation
	@RequestMapping(value = "password/updatePassword", method = RequestMethod.GET)
	public String updatePassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String r, Model model) {
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.passwordNotify, r, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updatePassword");
		}
		return viewName("password");
	}
	
	/**
	 * 密码手机号，第一步先到手机验证页面
	 */
	@TokenAnnotation
	@RequestMapping(value = "mobile/updateMobile", method = RequestMethod.GET)
	public String updateMobile(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String r, Model model) {
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.mobileNotify, r, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updateMobile");
		}
		return viewName("mobile");
	}
	
	/**
	 * 密码手机号，第一步先到手机验证页面
	 */
	@TokenAnnotation
	@RequestMapping(value = "payPassword/updatePayPassword", method = RequestMethod.GET)
	public String updatePayPassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String r, Model model) {
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.payPasswordNotify, r, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updatePayPassword");
		}
		return viewName("payPassword");
	}

	@TokenAnnotation(valid=true)
	@RequestMapping(value = "mobile/updateMobile", method = RequestMethod.POST)
	public String updateMobile(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String smsRecoverKey, String r, String newMobile) {
		Assert.notNull(newMobile, "新的手机号不能为空!");
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.mobileNotify, r, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updateMobile");
		}
		
		SmsMsgDomain validMsg = smsMsgService.findByValidMsg(SmsMsgType.mobileVerify, smsRecoverKey, member);
		if(validMsg == null || !validMsg.isValid()) {
			return redirectToUrl("member/safe/updateMobile");
		}
		memberService.updateMobile(member, newMobile, r, smsRecoverKey);
		return redirectToUrl("success");
	}
	
	@TokenAnnotation(valid=true)
	@RequestMapping(value = "password/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String smsSn, String newSecPwd) {
		Assert.notNull(newSecPwd, "密码不能为空!");
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.passwordNotify, smsSn, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updatePassword");
		}
		memberService.updatePassword(member, newSecPwd, smsSn);
		return redirectToUrl("success");
	}

	@TokenAnnotation(valid=true)
	@RequestMapping(value = "payPassword/updatePayPassword", method = RequestMethod.POST)
	public String updatePayPassword(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, 
			@RequestParam(value = Constants.BACK_URL, required = false) String backURL,
			String r, String newSecPwd) {
		Assert.notNull(newSecPwd, "密码不能为空!");
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.payPasswordNotify, r, member);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("member/safe/updatePayPassword");
		}
		memberService.updatePayPassword(member, newSecPwd, r);
		return redirectToUrl("success");
	}
	
	/**
	 * 邮箱验证页面
	 * @return
	 */
	@TokenAnnotation
	@RequestMapping(value = "checkEmail", method=RequestMethod.GET)
	public String checkEmail(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, Model model){
		model.addAttribute("member", member);
		return viewName("checkEmail");
	}
	
	/**
	 * 更新邮件验证
	 * @param email 验证步骤
	 * @param member 会员
	 * @param response
	 * @return 跳转
	 */
	@TokenAnnotation(valid=true)
	@RequestMapping(value = "updateEmail", method=RequestMethod.POST)
	public String updateEmail(@CurrentUser(CURRENT_FRONT_USER) MemberDomain member, String email, String smsRecoverKey) {
		Assert.notNull(email, "邮箱不能为空!");
		Assert.notNull(smsRecoverKey, "手机验证码不能为空!");
		if(memberService.isExist("email", email)) {
			throw new ApplicationException("邮箱号:" + email + "已经存在!");
		}
		memberService.updateEmail(member, email, smsRecoverKey);
		return redirectToUrl("success");
	}

}
