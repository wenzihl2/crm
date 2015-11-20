package com.wzbuaa.crm.service.crm;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.MessageStatus;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.SmsMsgType;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.crm.SmsMsgRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.SnCreaterService;
import com.wzbuaa.crm.service.base.TemplateService;
import com.wzbuaa.crm.util.DateHelper;
import com.wzbuaa.crm.util.VerifyUtil;

import framework.util.CommonUtil;
import framework.util.FreemarkerUtils;
import framework.util.PropertiesHelper;
/**
 * 短信
 */
@Service
public class SmsMsgService extends BaseService<SmsMsgDomain, Long> {

	@Resource private SnCreaterService snCreaterService;
	@Resource private TemplateService templateService;
	
	private SmsMsgRepository getSmsMsgRepository() {
		 return (SmsMsgRepository) baseRepository;
	}
	
	/**
	 * 根据短信徐浩查找
	 * @param msgType
	 * @param sn
	 * @param member
	 * @return
	 */
	public SmsMsgDomain findBySn(SmsMsgType msgType, String sn, MemberDomain member) {
		return getSmsMsgRepository().findByMsgTypeAndSnAndMemberAndStatus(msgType, sn, member, MessageStatus.SUCCESS);
	}
	
	/**
	 * 根据验证码查找
	 * @param msgType
	 * @param confirmation
	 * @param member
	 * @return
	 */
	public SmsMsgDomain findByValidMsg(SmsMsgType msgType, String confirmation, MemberDomain member) {
		SmsMsgDomain smsMessage = getSmsMsgRepository().findByMsgTypeAndConfirmationAndMemberAndStatus(msgType, 
				confirmation, member, MessageStatus.SUCCESS);
		if(smsMessage == null || !smsMessage.isValid() || smsMessage.getIsConfirm()) {
			throw new ApplicationException("无效的验证码!");
		}
		return smsMessage;
	}
	
	/**
	 * 根据验证码查找
	 * @param msgType
	 * @param confirmation
	 * @param member
	 * @return
	 */
	public SmsMsgDomain findByValidMsg(SmsMsgType msgType, String mobile, String confirmation) {
		SmsMsgDomain smsMessage = getSmsMsgRepository().findByMsgTypeAndMobileAndConfirmationAndStatus(msgType, mobile,
					confirmation, MessageStatus.SUCCESS);
		if(smsMessage == null || !smsMessage.isValid() || smsMessage.getIsConfirm()) {
			throw new ApplicationException("无效的验证码!");
		}
		return smsMessage;
	}
	
	/**
	 * 根据模板名称， 路径是指定的，发送短信接口(单条发送)
	 * @param mobile 短信号码	
	 * @param template_name 模板名称
	 * @return 返回发送状态
	 * @throws IOException 
	 */
	public SmsMsgDomain sendMessage(MemberDomain member, String mobile, SmsMsgType smsMsgType, Map<String, Object> param) {
		Assert.notNull(mobile, "手机号不能为空!");
		Assert.notNull(param, "参数不能为空");
		Assert.notNull(smsMsgType, "模板类型不能为空");
		
		if(!VerifyUtil.verifyMobile(mobile, false)){
			return null;
		}
		TemplateDomain template = null;
		if(member == null) {
			template = templateService.findByTypeAndIdentity(TemplateType.mobil, smsMsgType.name());
		} else {
			template = templateService.findByTypeAndIdentity(TemplateType.mobil, smsMsgType.name(), member.getCompany());
		}
		String code = CommonUtil.getRandomNum(4);
		param.put("code", code);
		String content = FreemarkerUtils.process(template.getContent(), param);
		
		String uuid = UUID.randomUUID().toString();
		String sn = "sms" + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
		Date sendDate = new Date();
		// 短信发送成功之后，则将发送内容保存至数据库
		SmsMsgDomain smsMessage = new SmsMsgDomain(sn, mobile, content, 
				code, smsMsgType, member);
		smsMessage.setValidTimeRang(30 * 60 * 1000L);
		String scheduleTime = DateHelper.date2String(sendDate, "yyyyMMddhhmmss");
		String infor = this.doSendMessage(mobile, content, sn, scheduleTime);
		if(StringUtils.isNotEmpty(infor)){
			String result = "";//返回状态值
			String description = "";//返回状态信息
			String faillist = "";//发送失败号码列表
			String[] infoArray = infor.split("&");
			for(String item : infoArray){
				String[] items = item.split("=");
				if (items[0].equals("result")){
					result=items[1];
				} else if (items.length > 1 && items[0].equals("description")){
					description=items[1];
				} else if (items.length > 1 && items[0].equals("faillist")){
					faillist = items[1];
				}
			}
			//发送成功
			if(StringUtils.equals(result, "0")){
				smsMessage.setStatus(MessageStatus.SUCCESS);
				smsMessage.setResult(description);
			} else {
				smsMessage.setStatus(MessageStatus.FIALURE);
				smsMessage.setResult(description);
			}
			getSmsMsgRepository().save(smsMessage);
		}
		return smsMessage;
	}

	/**
	 * 群发短信接口
	 * @param phoneNum 短信号码	
	 * @param content 短信内容
	 * @return 返回发送状态
	 */
	public String sendMessages(String phoneNum, String content) {
		return null;
	}
	
	/**
	 * 执行短信发送并且得到返回信息
	 * @param phoneNum 短信号码
	 * @param content 短信内容
	 * @param SerialNumber 流水号
	 * @param ScheduleTime 发送时间
	 * @return
	 */
	private String doSendMessage(String phoneNum, String content, String SerialNumber, String ScheduleTime) {
		String info = "";
		try {
			HttpClient httpclient = new HttpClient();
			String url = PropertiesHelper.getPropertiesValue("/config", "sms_message_sendurl");
			String SpCode = PropertiesHelper.getPropertiesValue("/config", "sms_message_spcode");
			String LoginName = PropertiesHelper.getPropertiesValue("/config", "sms_message_loginname");
			String password = PropertiesHelper.getPropertiesValue("/config", "sms_message_password");
			PostMethod post = new PostMethod(url);// 发送地址
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
			post.addParameter("SpCode",SpCode );// SP号
			post.addParameter("LoginName", LoginName);// 登陆号
			post.addParameter("Password", password);// 登陆密码
			post.addParameter("MessageContent", content);
			post.addParameter("UserNumber", phoneNum);
			post.addParameter("SerialNumber", SerialNumber);
			if (StringUtils.isEmpty(ScheduleTime)) {
				post.addParameter("ScheduleTime", "");
			} else {
				post.addParameter("ScheduleTime", ScheduleTime);
			}
			post.addParameter("f", "1");
			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "gbk");
		} catch (Exception e) {

		}
		return info;
	}

}
