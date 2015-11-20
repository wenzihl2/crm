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
import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.domain.base.PluginDomain.PluginType;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.plugin.freemarker.FreeMarkerPaser;
import com.wzbuaa.crm.plugin.payment.PaymentPluginBundle;
import com.wzbuaa.crm.plugin.storage.StoragePluginBundle;
import com.wzbuaa.crm.repository.hibernate.type.JsonMap;
import com.wzbuaa.crm.service.base.PluginService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.plugin.IPlugin;
import framework.util.RequestUtils;

@Controller
@RequestMapping("/admin/base/plugin")
public class PluginController extends BaseCRUDController<PluginDomain, String> {
	
	@Resource private PluginService pluginService;
	@Resource private PaymentPluginBundle paymentPluginBundle;
	@Resource private StoragePluginBundle storagePluginBundle;
	
	public PluginController() {
        setResourceIdentity("maintain:plugin");
    }
	
	@Override
	@RequestMapping(value = "create/discard", method = RequestMethod.POST)
	public String create(Model model, PluginDomain m, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public Message create(PluginDomain m, HttpServletRequest request){
		Map<String, Object> paramMap = RequestUtils.getQueryParams(request);
		m.setConfigparam(new JsonMap<String, Object>(paramMap));
    	pluginService.save(m);
		return successMessage;
    }
	
	@Override
	@RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
	public String update(Model model, PluginDomain m, BindingResult result,
			String backURL, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		throw new RuntimeException("discarded method");
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping(value="{id}/update", method=RequestMethod.POST)
	@ResponseBody
	public Message update(PluginDomain m, HttpServletRequest request){
		Map<String, Object> paramMap = RequestUtils.getQueryParams(request);
		m.setConfigparam(new JsonMap<String, Object>(paramMap));
		pluginService.update(m);
		return successMessage;
    }
	
	@RequestMapping(value="pluginList", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> pluginList(PluginType type) {
		List<IPlugin> pluginList = null;
		if(type == PluginType.payment) {
			pluginList = paymentPluginBundle.getPluginList();
		} else if(type  == PluginType.upload) {
			pluginList = storagePluginBundle.getPluginList();
		}
		
		return JsonDataHelper.parse("[{name: 'id'}, {name: 'name'}]", pluginList);
	}
	
	@RequestMapping(value="getPluginInstallHtml", method=RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String getPluginInstallHtml(PluginType type, String pluginId) {
		List<IPlugin> pluginList = null;
		if(type == PluginType.payment) {
			pluginList = paymentPluginBundle.getPluginList();
		} else if(type  == PluginType.upload) {
			pluginList = storagePluginBundle.getPluginList();
		}
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
	
		PluginDomain plugin = pluginService.findOne(pluginId);
		if(plugin != null){
			Map<String, Object> params = plugin.getConfigparam().getMap();
			Iterator<String> keyIter  = params.keySet().iterator();
			
			while(keyIter.hasNext()) {
				 String key  = keyIter.next();
				 Object value = params.get(key);
				 fp.putData(key, value);
			}
		}
		return fp.proessClassPageContent();
	}

    /**
     * 安装插件
     * @return
     */
	@RequestMapping(value="install", method=RequestMethod.POST)
	@ResponseBody
	public Message install(String id) {
		pluginService.install(id);
		return successMessage;
	}

	/**
	 * 卸载插件
	 * @return
	 */
	@RequestMapping(value="/unInstall", method=RequestMethod.POST)
	@ResponseBody
	public Message unInstall(String id) {
		pluginService.unInstall(id);
		return successMessage;
	}

	/**
	 * 启用插件
	 * @return
	 */
	@RequestMapping(value="/start", method=RequestMethod.POST)
	@ResponseBody
	public Message start(String id) {
		pluginService.start(id);
		return successMessage;
	}

	/**
	 * 停用插件
	 * @return
	 */
	@RequestMapping(value="/stop", method=RequestMethod.POST)
	@ResponseBody
	public Message stop(String id) {
		pluginService.stop(id);
		return successMessage;
	}
	
}
