package com.wzbuaa.crm.service.crm;

import static com.wzbuaa.crm.domain.Constants.USERNAME_PREFIX;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wzbuaa.crm.bean.Setting;
import com.wzbuaa.crm.bean.SystemID;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain.EmailMsgType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.SmsMsgType;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.crm.MemberRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.RSAService;
import com.wzbuaa.crm.service.base.TemplateService;
import com.wzbuaa.crm.service.sso.PasswordService;
import com.wzbuaa.crm.service.trade.PayDetailService;
import com.wzbuaa.crm.util.RegexUtils;
import com.wzbuaa.crm.util.SettingUtils;

@Service
public class MemberService extends BaseService<MemberDomain, Long> {
	
	public static final int HASH_INTERATIONS = 1024;
	@Resource public PasswordService passwordService;
	@Resource private TemplateService templateService;
	@Resource private CreditsDetailService creditsDetailService;
	@Resource private CreditsSetService creditsSetService;
	@Resource private PayDetailService paymentDetailService;
	@Resource private SmsMsgService smsMsgService;
	@Resource private EmailMsgService emailMsgService;
	@Resource private RSAService rSAService;
	
	private MemberRepository getMemberRepository() {
		 return (MemberRepository) baseRepository;
	}
	
	public MemberDomain findByUsername(String username) {
		return getMemberRepository().findByUsername(username);
	}
	
	public MemberDomain findByMobile(String mobile) {
		return getMemberRepository().findByMobile(mobile);
	}

	public MemberDomain findByEmail(String email) {
		return getMemberRepository().findByEmail(email);
	}
	
	public MemberDomain save(MemberDomain member) {
		member.randomSalt();
		member.setPassword(passwordService.encryptPassword(member.getUsername(), member.getPassword(), member.getSalt()));
        super.save(member);
        return member;
    }
	
	public MemberDomain register(String mobile, String password, String code, String registIP){
		if(isExist("mobile", mobile)){
			throw new ApplicationException("手机号已注册");
		}
		
		SmsMsgDomain smsMsg = smsMsgService.findByValidMsg(SmsMsgType.register, mobile, code);
		smsMsg.setIsConfirm(true);
		smsMsgService.update(smsMsg);
		
		MemberDomain member = new MemberDomain();
		password = rSAService.decryptParameter(password);
		member.setPassword(password);
		member.setCompany(1L);//默认的企业为1
		member.setMobile(mobile);
		member.setRegisterIp(registIP);
		member.setLoginDate(new Date());
		member.setMobileChecked(true);
		member.setUsername(USERNAME_PREFIX + mobile);
		if(member.getSystemId() == null){
			member.setSystemId(SystemID.CRM);
		}
		save(member);
		return member;
	}
	
	public MemberDomain getMbrByInput(String inputName) {
		MemberDomain member = null;
		if(RegexUtils.regexEmail(inputName)) {//邮箱
			member = getMemberRepository().findByEmail(inputName);
		} else if(RegexUtils.regexMobile(inputName)) {//手机
			member = getMemberRepository().findByMobile(inputName);
		} else {
			member = getMemberRepository().findByUsername(inputName);
		}
		return member;
	}

	public MemberDomain login(String username, String password) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			throw new ApplicationException("您输入的密码和账户名不匹配，请重新输入");
		}
		MemberDomain member = getMbrByInput(username);
		if(member == null){
			throw new ApplicationException("您输入的密码和账户名不匹配，请重新输入");
		}
		if(member.getIsAccountLocked() != null 
				&& member.getIsAccountLocked()
				&& member.getLockedDate() != null
				&& DateUtils.addMinutes(member.getLockedDate(), SettingUtils.get().getLoginFailureLockTime()).compareTo(new Date()) > 0){
			throw new ApplicationException("该账户已被锁定，请稍后再试");
		}
		String persistPassword = passwordService.encryptPassword(member.getUsername(), password, member.getSalt());
		
		if(!persistPassword.equals(member.getPassword())){
			throw new ApplicationException("您输入的密码和账户名不匹配，请重新输入");
		}
		member.setLoginDate(new Date());
		member.setUseFailureCount(0);
		update(member);
		return member;
	}
	
	/**
	 * 更新会员的密码
	 * @param member
	 * @param email
	 * @param smsRecoverKey
	 */
	public void updatePassword(MemberDomain member, String newSecPwd, String smsSn) {
		
		member.setEmailChecked(false);
		SmsMsgDomain smsMsg = smsMsgService.findBySn(SmsMsgType.passwordNotify, smsSn, member);
		smsMsg.setIsConfirm(true);
		smsMsgService.update(smsMsg);
		
		String newPassword = rSAService.decryptParameter(newSecPwd);
		String newSecPwdEncypt = passwordService.encryptPassword(member.getUsername(), newPassword, member.getSalt());
		member.setPassword(newSecPwdEncypt);
		// 更新会员的邮箱
		this.update(member);
		// 发送验证邮件
		Map<String, Object> param = new HashMap<String, Object>();
		emailMsgService.sendMail(member, member.getEmail(), "验证邮箱", EmailMsgType.PasswordNotify, param);
	}
	
	/**
	 * 更新会员的邮箱
	 * @param member
	 * @param email
	 * @param smsRecoverKey
	 */
	public void updateEmail(MemberDomain member, String email, String smsRecoverKey) {
		SmsMsgDomain smsMessage = smsMsgService.findByValidMsg(SmsMsgType.emailVerify, smsRecoverKey, member);
		member.setEmail(email);
		member.setEmailChecked(false);
		
		// 更新短信验证码
		smsMessage.setIsConfirm(true);
		smsMsgService.update(smsMessage);
		// 更新会员的邮箱
		this.update(member);
		// 发送验证邮件
		Map<String, Object> param = new HashMap<String, Object>();
		emailMsgService.sendMail(member, email, "验证邮箱", EmailMsgType.PasswordNotify, param);
	}
	
	/**
	 * 更新手机
	 * @param member
	 * @param mobile
	 * @param smsRecoverKey
	 */
	public void updateMobile(MemberDomain member, String mobile, String smsMsg, String validMsg) {
		SmsMsgDomain smsMessage = smsMsgService.findBySn(SmsMsgType.mobileNotify, smsMsg, member);
		// 更新短信验证码
		smsMessage.setIsConfirm(true);
		smsMsgService.update(smsMessage);
		
		SmsMsgDomain validMsgDomain = smsMsgService.findByValidMsg(SmsMsgType.mobileVerify, validMsg, member);
		validMsgDomain.setIsConfirm(true);
		smsMsgService.update(validMsgDomain);
		
		member.setMobileChecked(true);
		member.setMobile(mobile);
		update(member);
		
		// 发送验证邮件
		Map<String, Object> param = new HashMap<String, Object>();
		emailMsgService.sendMail(member, member.getEmail(), "手机号修改", EmailMsgType.MobileNotify, param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void loginFail(MemberDomain member) {
		if(member == null || member.getId() == null){
			return;
		}
		Integer loginFailCount = member.getUseFailureCount();
		if(loginFailCount == null){
			loginFailCount = 1;
		}else{
			loginFailCount++;
		}
		member.setUseFailureCount(loginFailCount);
		Setting setting = SettingUtils.get();
		if(loginFailCount >= setting.getLoginFailureLockCount()
				&& setting.getIsLoginFailureLock()){
			member.setLockedDate(new Date());
			member.setIsAccountLocked(true);
		}
		update(member);
	}

	/**
	 * 更新支付密码
	 * @param memberId
	 * @param code
	 * @param newPwd
	 */
	public void updatePayPassword(MemberDomain member, String newPwd, String smsRecoverKey) {
		String payPassword = passwordService.encryptPassword(member.getUsername(), newPwd, member.getSalt());
		SmsMsgDomain smsMessage = smsMsgService.findBySn(SmsMsgType.payPasswordNotify, smsRecoverKey, member);
		// 更新短信验证码
		smsMessage.setIsConfirm(true);
		smsMsgService.update(smsMessage);
		member.setPay_passwd(payPassword);
		member.setIsEnablepay_passwd(true);
		// 更新会员的邮箱
		this.update(member);
		// 发送验证邮件
		Map<String, Object> param = new HashMap<String, Object>();
		emailMsgService.sendMail(member, member.getEmail(), "验证邮箱", EmailMsgType.PayPasswordNofity, param);
	}

	@Transactional(readOnly=true)
	public MemberDomain checkUsernameForRecovery(String username) {
		MemberDomain member = getMbrByInput(username);
		if(member == null) {
			throw new ApplicationException("请确认输入的用户名，系统没有该用户名！");
		}
		if((member.getMobileChecked() == null || !member.getMobileChecked()) 
				&& (member.getEmailChecked() == null || !member.getEmailChecked())) {
			throw new ApplicationException("您未进行手机和邮箱验证，请联系商城客服找回密码！");
		}
		return member;
	}

//	@Transactional(readOnly=true)
//	public MemberDomain verifyForgetCaptcha(String mobile, String smsRecoverKey) {
//		MemberDomain member = getMbrByInput(mobile);
//		if(member == null){
//			throw new ApplicationException("账号不存在!");
//		}
//		String error = VerifyUtil.checkMobileVerify(mobile,
//				member.getSmsRecoverKey(), smsRecoverKey, VERIFY_CODE_KEY_PERIOD);
//		if (StringUtils.isNotBlank(error)) {
//			throw new ApplicationException(error);
//		}
//		return member;
//	}
	
//	/**
//	 * 发送邮件
//	 * @param toEmail
//	 * @param member
//	 * @param emailMessageType
//	 * @return
//	 */
//	public void sendEmail(String toEmail, MemberDomain member, EmailMessageType emailMessageType) {
//		Assert.notNull(toEmail, "电子不能为空!");
//		String code = CommonUtil.getUUID();
//		EmailMessageDomain emailMessage = new EmailMessageDomain(member, toEmail, emailMessageType, code);
//		emailMessage.setValidTimeRang(60 * 60L);
//		emailMsgService.save(emailMessage);
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("crmUrl", "");
//		params.put("username", member.getUsername());
//		params.put("recoveryKey", code);
//		mailService.sendMail(emailMessageType.name(), toEmail, "邮箱验证", params);
//	}
	
//	/**
//	 * 发送手机验证码
//	 * @param mobile
//	 * @param member
//	 * @param msmMessageType
//	 * @return
//	 */
//	public String sendMobile(String mobile, MemberDomain member, MsmMessageType msmMessageType) {
//		Assert.notNull(mobile, "手机号不能为空!");
//		String validateCode = CommonUtil.getRandomNum(6);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("key", validateCode);
//		msmMsgService.sendMessageTemplateName(mobile, templateName, map, null, messageType);
//		if(member != null){
//			String recoverKey = VerifyUtil.createMobileVerifyCodeKey(mobile, validateCode);
//			member.setSmsRecoverKey(recoverKey);
//			update(member);
//		}
//		return validateCode;
//	}
	
}
