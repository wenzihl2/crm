package com.wzbuaa.crm.controller.admin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.domain.AbstractEntity;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;

/**
 * 基础CRUD 控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午1:20
 * <p>Version: 1.0
 */
public abstract class BaseCRUDController<M extends AbstractEntity, ID extends Serializable>
        extends BaseController<M, ID> {

    protected BaseService<M, ID> baseService;

    private boolean listAlsoSetCommonData = false;

    protected PermissionList permissionList = null;

    /**
     * 设置基础service
     *
     * @param baseService
     */
    @Autowired
    public void setBaseService(BaseService<M, ID> baseService) {
        this.baseService = baseService;
    }

    /**
     * 列表也设置common data
     */
    public void setListAlsoSetCommonData(boolean listAlsoSetCommonData) {
        this.listAlsoSetCommonData = listAlsoSetCommonData;
    }

    /**
     * 权限前缀：如sys:user
     * 则生成的新增权限为 sys:user:create
     */
    public void setResourceIdentity(String resourceIdentity) {
        if (!StringUtils.isEmpty(resourceIdentity)) {
            permissionList = PermissionList.newPermissionList(resourceIdentity);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Searchable searchable, Model model) {
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
        if (listAlsoSetCommonData) {
            setCommonData(model);
        }
        return viewName("list");
    }
    
    //获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "id=desc")
	public Pager<M> queryData(Searchable searchable) {
		Page<M> page= baseService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}

    /**
     * 仅返回表格数据
     *
     * @param searchable
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, headers = "table=true")
    @PageableDefaults(sort = "id=desc")
    public String listTable(Searchable searchable, Model model) {
        list(searchable, model);
        return viewName("listTable");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("id") ID id) {

        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }

        setCommonData(model);
        model.addAttribute("m", baseService.findOne(id));
        model.addAttribute(Constants.OP_NAME, "查看");
        return viewName("input");
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {

        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
//        if (!model.containsAttribute("m")) {
//            model.addAttribute("m", newModel());
//        }
        return viewName("input");
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(
            Model model, @Valid @ModelAttribute("m") M m, BindingResult result,
            RedirectAttributes redirectAttributes, 
            HttpServletRequest request, HttpServletResponse response) {

        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        if (hasError(m, result)) {
            return showCreateForm(model);
        }
        baseService.save(m);
        if(RequestUtils.isAjaxRequest(request)) {
        	return ResponseUtils.renderJSON(Message.success("新增成功", null), response);
        } else {
        	redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "新增成功");
	        return redirectToUrl(null);
        }
    }


    @RequestMapping(value = "{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") ID id, Model model) {

        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        M m = baseService.findOne(id);
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("m", m);
        return viewName("input");
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public String update(
            Model model, @Valid @ModelAttribute("m") M m, BindingResult result,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes, 
            HttpServletRequest request, HttpServletResponse response) {

        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        
        if (hasError(m, result)) {
            return showUpdateForm((ID)m.getId(), model);
        }
        baseService.update(m);
        if(RequestUtils.isAjaxRequest(request)) {
        	return ResponseUtils.renderJSON(Message.success("新增成功", null), response);
        } else {
        	redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "修改成功");
            return redirectToUrl(backURL);
        }
    }

    @RequestMapping(value = "{id}/deleteForm", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") M m, Model model) {

        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("m", m);
        return viewName("editForm");
    }

    @RequestMapping(value = "{id}/delete")
    public String delete(
            @PathVariable("id") ID id,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {


        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
        baseService.delete(baseService.findOne(id));
        
        if(RequestUtils.isAjaxRequest(request)) {
        	return ResponseUtils.renderJSON(Message.success("新增成功", null), response);
        } else {
        	redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "删除成功");
            return redirectToUrl(backURL);
        }
    }

    @RequestMapping(value = "batch/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteInBatch(
            @RequestParam(value = "ids", required = false) ID[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {


        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        baseService.delete(ids);

        if(RequestUtils.isAjaxRequest(request)) {
        	return ResponseUtils.renderJSON(Message.success("删除成功", null), response);
        } else {
        	redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "删除成功");
            return redirectToUrl(backURL);
        }
    }

}
