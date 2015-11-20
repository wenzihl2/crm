package com.wzbuaa.crm.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.jcaptcha.JCaptcha;

import framework.Message;

/**
 * 后台Action类 - 首页
 */
@Controller
@RequestMapping("/admin/common")
public class CommonController extends BaseController {
	 
	@RequestMapping(value="/jcaptcha-validate", method = RequestMethod.GET)
    @ResponseBody
    public Message jqueryValidationEngineValidate(
            HttpServletRequest request,
            @RequestParam(value = "fieldId", required = false) String fieldId,
            @RequestParam(value = "fieldValue", required = false) String fieldValue) {

        if (JCaptcha.hasCaptcha(request, fieldValue) == false) {
        	return Message.error("jcaptcha.validate.error", null);
        } else {
        	return Message.success("jcaptcha.validate.success", null);
        }
    }
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
	public String error(HttpServletRequest request, RedirectAttributes redirectAttributes){
		RequestAttributes requestattributes = RequestContextHolder.currentRequestAttributes();
		//model.addAttribute("errors", request.getAttribute("errors"));
		requestattributes.setAttribute("errors", request.getAttribute("errors"), 0);
		return "/admin/common/error";
	}
}