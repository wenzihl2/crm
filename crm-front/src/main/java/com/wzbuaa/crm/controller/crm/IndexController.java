package com.wzbuaa.crm.controller.crm;

//import static com.wzbuaa.crm.domain.Constants.CURRENT_FRONT_USER;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.SmsMsgType;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.service.crm.SmsMsgService;

import framework.Message;
import framework.spring.mvc.bind.annotation.CurrentUser;
import framework.util.RequestUtils;
/**
 * 前台 类
 *
 */
@Controller
public class IndexController extends BaseController {

	@Resource private MemberService memberService;
	@Resource private SmsMsgService smsMsgService;
	
	@RequestMapping(value = "success")
    public String success() {
        return viewName("success");
    }
	
	@RequestMapping(value = "error")
    public String error() {
        return viewName("error");
    }
	
	@RequestMapping(value="index", method = RequestMethod.GET)
	public String index() {
		return viewName("index");
	}
	
	/**
	 * 发送验证码
	 */
	@RequestMapping(value = "{smsMsgType:register|passwordRecover}/sendSms", method=RequestMethod.POST)
	@ResponseBody
	public Message sendSms(@PathVariable("smsMsgType") SmsMsgType smsMsgType, String mobile){
		Map<String, Object> param = new HashMap<String, Object>();
		SmsMsgDomain smsMsg = smsMsgService.sendMessage(null, mobile, smsMsgType, param);
		if(smsMsg == null || !smsMsg.isValid()) {
			return Message.error("短信发送失败", null);
		}
		return successMessage;
	}
	
	/**
	 * 注册页面
	 */
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String register(){
		return viewName("register");
	}
	
	/**
	 * 会员注册
	 */
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(String password, String mobile,
			String code, HttpServletRequest request, HttpServletResponse response){
		SmsMsgDomain smsMsg = smsMsgService.findByValidMsg(SmsMsgType.register, mobile, code);
		if(smsMsg == null || !smsMsg.isValid()) {
			return redirectToUrl("login");
		}
		memberService.register(mobile, password, code, RequestUtils.getIpAddr(request));
		return redirectToUrl("login");
	}
	
}
