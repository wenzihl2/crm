package com.wzbuaa.crm.controller.admin.sso;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.wzbuaa.crm.controller.admin.BaseTreeableController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.Resource;
import com.wzbuaa.crm.domain.sso.bean.MenuPID;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.ResourceService;
import framework.spring.mvc.bind.annotation.CurrentUser;
import framework.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/resource")
public class ResourceController extends BaseTreeableController<Resource, Long> {
	
	@Autowired private ResourceService resourceService;
   
	public ResourceController() {
        setResourceIdentity("sys:resource");
    }
    
    @RequestMapping(value="/menu", method=RequestMethod.GET)
	@ResponseBody
	public List<MenuPID> menu(@CurrentUser S_userDomain user, HttpServletRequest request) {
		if(user == null){
			return null;
		}
		List<Resource> models = resourceService.findMenusPID(user);
		return resourceService.convertToMenuPID(models);
	}
    
    @RequestMapping(value="/toolBarButton", method=RequestMethod.POST)
	@ResponseBody
	public List<MenuPID> toolBarButton(@CurrentUser S_userDomain user, HttpServletRequest request) {
		if(user == null){
			return null;
		}
		String url = RequestUtils.getQueryParam(request, "url");
		Resource resource = resourceService.findByUrl(url);
		if(resource == null) {
			return null;
		}
		List<Resource> models = resourceService.findBarButton(user, resource);
		return resourceService.convertToMenuPID(models);
	}

    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids,
            RedirectAttributes redirectAttributes
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
            Resource resource = baseService.findOne(id);
            resource.setShow(newStatus);
            baseService.update(resource);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

}
