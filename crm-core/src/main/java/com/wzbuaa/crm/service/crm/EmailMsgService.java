package com.wzbuaa.crm.service.crm;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain.EmailMsgType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.crm.EmailMsgRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.TemplateService;
import com.wzbuaa.crm.util.SettingUtils;
import com.wzbuaa.crm.util.SysUtil;
import com.wzbuaa.crm.util.email.MailSenderInfo;
import com.wzbuaa.crm.util.email.SimpleMailSender;

import framework.util.CommonUtil;

/**
 */
@Service
public class EmailMsgService extends BaseService<EmailMsgDomain, Long> {
	
	@Resource private TemplateService templateService;
	
	private EmailMsgRepository getEmailMsgRepository() {
		 return (EmailMsgRepository) baseRepository;
	}
	
	/**
	 * 根据邮箱验证码查找邮件
	 * @param msgType
	 * @param validSn
	 * @param member
	 * @return
	 */
	public EmailMsgDomain findByValidMsg(EmailMsgType msgType, String validSn, MemberDomain member) {
		EmailMsgDomain emailMsg = getEmailMsgRepository().findByMessageTypeAndConfirmationAndMember(msgType, validSn, member);
		if(emailMsg == null) {
			throw new ApplicationException("手机验证码非法!");
		}
		if(!emailMsg.isValid()) {
			throw new ApplicationException("手机验证码失效!");
		}
		if(emailMsg.getIsConfirm()) {
			throw new ApplicationException("此手机验证码已经验证过!");
		}
		return emailMsg;
	}
	
	/**
	 * 发送测试邮件
	 * @param smtpFromMail 发件人邮箱
	 * @param smtpHost SMTP服务器地址
	 * @param smtpPort SMTP服务器端口
	 * @param smtpUsername SMTP用户名
	 * @param smtpPassword SMTP密码
	 * @param toMail 收邮件人地址
	 */
	@Transactional(propagation=Propagation.NEVER)
	public Boolean sendTestMail(String smtpFromMail, String smtpHost, String smtpPort, 
			String smtpUsername, String smtpPassword, String toMail){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shopUrl", SettingUtils.get().getUrlBuff(true));
		param.put("shopName", SettingUtils.get().getName());
		TemplateDomain templateBean = templateService.findByTypeAndIdentity(TemplateType.email, "smtpTest", SysUtil.getUser().companyId);
		// 解析模板并替换动态数据，最终数据将替换模板文件中的${}标签。
		String subject = "邮件设置测试";
		// 封装数据
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setFromAddress(smtpFromMail);
		mailInfo.setMailServerHost(smtpHost);
		mailInfo.setMailServerPort(smtpPort);
		mailInfo.setUserName(smtpUsername);
		mailInfo.setPassword(smtpPassword);
		mailInfo.setToAddress(toMail);
		mailInfo.setSubject(subject);
		mailInfo.setContent(templateService.parseContent(templateBean, param));
		// 发送html格式
		return SimpleMailSender.sendHtmlMail(mailInfo);
	}

	/**
	 * 发送邮件, 邮件发送成功之后，则将发送内容保存至数据库
	 * @param member //发送至
	 * @param toEmail // 发送至
	 * @param title // 邮件title
	 * @param emailMessageType // 邮件模板
	 * @param param // 模板参数
	 */
	public void sendMail(MemberDomain member, String toEmail, String title, 
			EmailMsgType emailMessageType, Map<String, Object> param) {
		Assert.notNull(toEmail, "邮件地址不能为空");
		Assert.notNull(emailMessageType, "邮件模板名不能为空!");
		Assert.notNull(param, "参数不能为空");
		
		TemplateDomain templateBean = templateService.findByTypeAndIdentity(TemplateType.email, emailMessageType.name(), member.getCompany());
		String code = CommonUtil.getUUID();
		param.put("code", code);
		
		String content = templateService.parseContent(templateBean, param);
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(toEmail);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		// 发送html格式
		SimpleMailSender.sendHtmlMail(mailInfo);
		
		// 邮件发送成功之后，则将发送内容保存至数据库
		EmailMsgDomain emailMessage = new EmailMsgDomain(member, toEmail, emailMessageType, code);
		emailMessage.setValidTimeRang(60 * 60 * 1000L);
		this.save(emailMessage);
	}
	
}
