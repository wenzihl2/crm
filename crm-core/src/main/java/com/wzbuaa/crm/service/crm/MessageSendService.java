package com.wzbuaa.crm.service.crm;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.SnCreateType;
import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.domain.crm.SmsMessageDomain;
import com.wzbuaa.crm.domain.crm.SmsMessageDomain.MessageStatus;
import com.wzbuaa.crm.domain.crm.SmsMessageDomain.MessageType;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.SnCreaterService;
import com.wzbuaa.crm.service.base.TemplateService;
import com.wzbuaa.crm.util.DateHelper;
import com.wzbuaa.crm.util.VerifyUtil;

import framework.util.FreemarkerUtils;
import framework.util.PropertiesHelper;
/**
 * 短信
 */
@Service
public class MessageSendService extends BaseService<SmsMessageDomain, Long> {

	@Resource private SnCreaterService snCreaterService;
	@Resource private TemplateService templateService;
	
	/**
	 * 根据模板名称， 路径是指定的，发送短信接口(单条发送)
	 * @param phoneNum 短信号码	
	 * @param template_name 模板名称
	 * @return 返回发送状态
	 * @throws IOException 
	 */
	public Boolean sendMessageTemplateName(String phoneNum, String template_name, Map<String, Object> map, 
			Date sendTime, MessageType type) throws IOException {
		 
		if(StringUtils.isEmpty(phoneNum)){
			return false;
		}
		
		if(!VerifyUtil.verifyMobile(phoneNum, false)){
			return false;
		}
		TemplateDomain template = templateService.findByTypeAndIdentity(TemplateType.mobil, type.name());
		String content = FreemarkerUtils.process(template.getContent(), map);
		return this.sendSimpleMessage(phoneNum, content, type);
	}
	
	/**
	 * 发送短信接口(单条发送)
	 * @param phoneNum 短信号码	
	 * @param content 短信内容
	 * @return 返回发送状态
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public Boolean sendSimpleMessage(String phoneNum, String content, MessageType type) throws IOException {
		Boolean sendStatus = false;
		
		SmsMessageDomain message = new SmsMessageDomain();
		String sn = snCreaterService.create(SnCreateType.SmsMessage);
		message.setSn(sn);//设置
		message.setType(type);
		message.setSmsNumber(phoneNum);
		Date sendDate = new Date();
		message.setSendTime(sendDate);
		message.setSmsContent(content);
		message.setStatus(MessageStatus.UNSEND);//状态未发送
		this.save(message);
		String ScheduleTime = DateHelper.date2String(sendDate, "yyyyMMddhhmmss");
		String infor = doSendMessage(phoneNum, content, sn, ScheduleTime);
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
				message.setStatus(MessageStatus.SUCCESS);
				message.setDescription(description);
				sendStatus = true;
			} else {
				message.setStatus(MessageStatus.FIALURE);
				message.setDescription(description);
				sendStatus = false;
			}
			this.update(message);
		} else {
			sendStatus= false;
		}
		return sendStatus;
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
	 * 
	 * @param phoneNum
	 *            短信号码
	 * @param content
	 *            短信内容
	 * @param SerialNumber
	 *            流水号
	 * @param ScheduleTime
	 *            发送时间
	 * @return
	 */
	public String doSendMessage(String phoneNum, String content,
			String SerialNumber, String ScheduleTime) {
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
