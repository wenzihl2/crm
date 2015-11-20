package com.wzbuaa.crm.controller.admin.sso;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import framework.entity.enums.BooleanEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.controller.admin.PermissionList;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.user.GroupDomain;
import com.wzbuaa.crm.domain.sso.user.GroupType;
import com.wzbuaa.crm.service.sso.GroupRelationService;
import com.wzbuaa.crm.service.sso.GroupService;

import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/group")
public class GroupController extends BaseCRUDController<GroupDomain, Long> {

    @Autowired
    private GroupRelationService groupRelationService;

    public GroupController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("sys:group");
    }

    private GroupService getGroupService() {
        return (GroupService) baseService;
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", GroupType.values());
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(value = "{type}/list", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(@PathVariable("type") GroupType type, Searchable searchable, Model model) {

        searchable.addSearchParam("type_eq", type);

        return list(searchable, model);
    }

    @RequestMapping(value = "create/discard", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        throw new RuntimeException("discarded method");
    }


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateFormWithType(Model model) {
        return super.showCreateForm(model);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(
            Model model, @Valid @ModelAttribute("m") GroupDomain m, BindingResult result,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        return super.create(model, m, result, redirectAttributes, request, response);
    }


    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeShowStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
        	GroupDomain group = getGroupService().findOne(id);
            group.setShow(newStatus);
            getGroupService().update(group);
        }
        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


    @RequestMapping(value = "/changeDefaultGroup/{newStatus}")
    public String changeDefaultGroup(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
        	GroupDomain group = getGroupService().findOne(id);
            if (group.getType() != GroupType.user) {//只有用户组 可设置为默认 其他无效
                continue;
            }
            group.setDefaultGroup(newStatus);
            getGroupService().update(group);
        }
        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


    @RequestMapping("ajax/autocomplete")
    @PageableDefaults(pageSize = 30)
    @ResponseBody
    public Set<Map<String, Object>> autocomplete(
            Searchable searchable,
            @RequestParam("term") String term) {

        return getGroupService().findIdAndNames(searchable, term);
    }


    ///////////////////////////////分组 内//////////////////////////////////////
    @RequestMapping(value = "/{group}/listRelation", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String listGroupRelation(@PathVariable("group") GroupDomain group, Searchable searchable, Model model) {

        this.permissionList.assertHasViewPermission();

        searchable.addSearchParam("groupId_eq", group.getId());

        Page page = null;
        if (group.getType() == GroupType.user) {
            page = groupRelationService.findAll(searchable);
        }

        if (group.getType() == GroupType.dept) {
            page = groupRelationService.findAll(searchable);
        }

        model.addAttribute("page", page);

        return viewName("relation/relationList");
    }


    @RequestMapping(value = "/{group}/listRelation", headers = "table=true", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String listGroupRelationTable(@PathVariable("group") GroupDomain group, Searchable searchable, Model model) {

        this.permissionList.assertHasViewPermission();

        this.listGroupRelation(group, searchable, model);
        return viewName("relation/relationListTable");

    }

    @RequestMapping(value = "{group}/batch/delete", method = RequestMethod.GET)
    public String batchDeleteGroupRelation(
            @PathVariable("group") GroupDomain group,
            @RequestParam("ids") Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        this.permissionList.assertHasDeletePermission();

        if (group.getType() == GroupType.user) {
            groupRelationService.delete(ids);
        }

        if (group.getType() == GroupType.dept) {
            groupRelationService.delete(ids);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "删除成功");
        return redirectToUrl(backURL);

    }

    @RequestMapping(value = "{group}/batch/append", method = RequestMethod.GET)
    public String showBatchAppendGroupRelationForm(@PathVariable("group") GroupDomain group) {

        this.permissionList.assertHasAnyPermission(
                new String[]{PermissionList.CREATE_PERMISSION, PermissionList.UPDATE_PERMISSION});

        if (group.getType() == GroupType.user) {
            return viewName("relation/appendUserGroupRelation");
        }

        if (group.getType() == GroupType.dept) {
            return viewName("relation/appendOrganizationGroupRelation");
        }

        throw new RuntimeException("group type error");
    }

    @RequestMapping(value = "{group}/batch/append", method = RequestMethod.POST)
    public String batchAppendGroupRelation(
            @PathVariable("group") GroupDomain group,
            @RequestParam("ids") Long[] ids,
            //只有用户组 有fromIds endIds
            @RequestParam(value = "startIds", required = false) Long[] startIds,
            @RequestParam(value = "endIds", required = false) Long[] endIds,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        this.permissionList.assertHasAnyPermission(
                new String[]{PermissionList.CREATE_PERMISSION, PermissionList.UPDATE_PERMISSION});

        if (group.getType() == GroupType.dept) {
            groupRelationService.appendRelation(group.getId(), ids);
        }

        if (group.getType() == GroupType.user) {
            groupRelationService.appendRelation(group.getId(), ids, startIds, endIds);
        }


        redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "批量添加成功");

        return redirectToUrl(backURL);
    }


}
