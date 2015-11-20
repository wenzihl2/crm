package com.wzbuaa.crm.controller.admin.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.base.PaymentTypeDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.plugin.freemarker.FreeMarkerPaser;
import com.wzbuaa.crm.plugin.payment.PaymentPluginBundle;
import com.wzbuaa.crm.repository.hibernate.type.JsonMap;
import com.wzbuaa.crm.service.base.PaymentTypeService;
import com.wzbuaa.crm.service.base.PluginService;

import framework.plugin.IPlugin;
import framework.util.RequestUtils;

/**
 * 后台Action类 - 支付方式
 */
@Controller
@RequestMapping("/admin/base/paymentType")
public class PaymentTypeController extends BaseCRUDController<PaymentTypeDomain, Long> {

	@Resource private PaymentTypeService paymentTypeService;
	@Resource private PaymentPluginBundle paymentPluginBundle;
	@Resource private PluginService pluginService;
	
	@Override
	protected void setCommonData(Model model) {
		List<IPlugin> pluginList = paymentPluginBundle.getPluginList();
		model.addAttribute("pluginList", pluginList);
		super.setCommonData(model);
	}
	
	@Override
	@RequestMapping(value = "create/discard", method = RequestMethod.POST)
	public String create(Model model, PaymentTypeDomain m,
			BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(PaymentTypeDomain m, Model model, BindingResult result, 
			RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = RequestUtils.getQueryParams(request);
		m.setConfig(new JsonMap<String, Object>(paramMap));
		return super.create(model, m, result, redirectAttributes, request, response);
		
	}
	
	@Override
	@RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
	public String update(Model model, PaymentTypeDomain m,
			BindingResult result, String backURL,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.POST)
	public String updatePayment(Model model, PaymentTypeDomain m,
			BindingResult result, String backURL,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paramMap = RequestUtils.getQueryParams(request);
		m.setConfig(new JsonMap<String, Object>(paramMap));
		return super.update(model, m, result, backURL, redirectAttributes, request, response);
	}
	/**
	 * 校验支付方式名称
	 * @return
	 */
	@RequestMapping(value="/checkName")
	@ResponseBody
	public String checkName(PaymentTypeDomain paymentType, String oldValue){
		String newValue = paymentType.getName();
		if (paymentTypeService.isUnique("name", oldValue, newValue)) {
			return "true";
		} else {
			return "false";
		}
    }
	
	@RequestMapping(value="getPluginInstallHtml", method=RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String getPluginInstallHtml(String pluginId, Long paymentId) {
		List<IPlugin> pluginList = paymentPluginBundle.getPluginList();
		IPlugin installPlugin = null;
		for(IPlugin plugin :pluginList){
			if(plugin.getId().equals(pluginId)){
				installPlugin = plugin;
				break;
			}
		}
		
		if(installPlugin==null) {
			throw new ApplicationException("plugin["+pluginId+"] not found!"); 
		}
		FreeMarkerPaser fp =  FreeMarkerPaser.getInstance();
		fp.setClazz(installPlugin.getClass());
		 
		if(paymentId != null){
			PaymentTypeDomain payment = paymentTypeService.findOne(paymentId);
			Map<String, Object> params = payment.getConfig().getMap();
			Iterator<String> keyIter  = params.keySet().iterator();
			
			while(keyIter.hasNext()) {
				 String key  = keyIter.next();
				 Object value = params.get(key);
				 fp.putData(key, value);
			}
		}
		return fp.proessClassPageContent();
	}
    
}