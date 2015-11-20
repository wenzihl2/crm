package com.wzbuaa.crm.controller.admin;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.wzbuaa.crm.bean.ZTree;
import com.wzbuaa.crm.component.entity.Treeable;
import com.wzbuaa.crm.component.service.BaseTreeableService;
import com.wzbuaa.crm.domain.BaseEntity;
import com.wzbuaa.crm.domain.Constants;

import framework.Message;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public abstract class BaseTreeableController<M extends BaseEntity<ID> & Treeable<ID>, ID extends Serializable>
        extends BaseController<M, ID> {

    protected BaseTreeableService<M, ID> baseService;

    protected PermissionList permissionList = null;

    @Autowired
    public void setBaseService(BaseTreeableService<M, ID> baseService) {
        this.baseService = baseService;
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

    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    public String main() {
        if (permissionList != null) {
            permissionList.assertHasViewPermission();
        }
        return viewName("main");
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    public String tree(
            HttpServletRequest request,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @RequestParam(value = "type", required = false) String type,
            Searchable searchable, Model model) {

        if (permissionList != null) {
            permissionList.assertHasViewPermission();
        }

        List<M> models = null;

        if (!StringUtils.isEmpty(searchName)) {
            searchable.addSearchParam("name_like", searchName);
            models = baseService.findAllByName(searchable, null);
            if (!async) { //非异步 查自己和子子孙孙
                searchable.removeSearchFilter("name_like");
                List<M> children = baseService.findChildren(models, searchable);
                models.removeAll(children);
                models.addAll(children);
            } else { //异步模式只查自己

            }
        } else {
            if (!async) {  //非异步 查自己和子子孙孙
                models = baseService.findAllWithSort(searchable);
            } else {  //异步模式只查根 和 根一级节点
                models = baseService.findRootAndChild(searchable);
            }
        }

        model.addAttribute("trees",
                convertToZtreeList(
                        request.getContextPath(),
                        models,
                        async,
                        true));
        model.addAttribute("type", type);
        return viewName("tree");
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") ID id, Model model) {
        if (permissionList != null) {
            permissionList.assertHasViewPermission();
        }

        M m = baseService.findOne(id);
        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "查看");
        return viewName("editForm");
    }

    @RequestMapping(value = "{id}/editForm", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") ID id, Model model, RedirectAttributes redirectAttributes) {

    	M m = baseService.findOne(id);
    	
        if (permissionList != null) {
            permissionList.assertHasUpdatePermission();
        }


        if (m == null) {
            redirectAttributes.addFlashAttribute(Constants.FLASHMESSAGE, "您修改的数据不存在！");
            return redirectToUrl(viewName("success.jhtml"));
        }


        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "修改");
        return viewName("editForm");
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.POST)
    public String edit(Model model, @ModelAttribute("m") M m, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (permissionList != null) {
            permissionList.assertHasUpdatePermission();
        }


        if (result.hasErrors()) {
            return editForm(m.getId(), model, redirectAttributes);
        }

        baseService.update(m);
        redirect(redirectAttributes, Message.success("修改成功", null));
        return redirectToUrl(viewName("success.jhtml"));
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
    public String deleteForm(@PathVariable("id") ID id, Model model) {


        if (permissionList != null) {
            permissionList.assertHasDeletePermission();
        }

        model.addAttribute("m", baseService.findOne(id));
        model.addAttribute(Constants.OP_NAME, "删除");
        return viewName("editForm");
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String deleteSelfAndChildren(
            Model model,
            @PathVariable("id") ID id,
            RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            permissionList.assertHasDeletePermission();
        }
        M m = baseService.findOne(id);
        if (m.isRoot()) {
            //result.reject("您删除的数据中包含根节点，根节点不能删除");
            return deleteForm(id, model);
        }

        baseService.deleteSelfAndChild(m);
        redirect(redirectAttributes, Message.success("删除成功", null));
        return redirectToUrl(viewName("success.jhtml"));
    }


    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(
            @RequestParam(value = "ids", required = false) ID[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {


        if (permissionList != null) {
            permissionList.assertHasDeletePermission();
        }

        //如果要求不严格 此处可以删除判断 前台已经判断过了
        Searchable searchable = Searchable.newSearchable().addSearchFilter("id", SearchOperator.in, ids);
        List<M> mList = baseService.findAllWithNoPageNoSort(searchable);
        for (M m : mList) {
            if (m.isRoot()) {
            	redirect(redirectAttributes, Message.warn("您删除的数据中包含根节点，根节点不能删除", null));
                return redirectToUrl(backURL);
            }
        }

        baseService.deleteSelfAndChild(mList);
        redirect(redirectAttributes, Message.success("删除成功", null));
        return redirectToUrl(backURL);
    }


    @RequestMapping(value = "{parent}/appendChildForm", method = RequestMethod.GET)
    public String appendChildForm(@PathVariable("parent") ID parent, Model model) {
        if (permissionList != null) {
            permissionList.assertHasCreatePermission();
        }
        
        M m = baseService.findOne(parent);
        model.addAttribute("parent", m);

        if (!model.containsAttribute("child")) {
            model.addAttribute("child", newModel());
        }

        model.addAttribute(Constants.OP_NAME, "添加子节点");

        return viewName("appendChildForm");
    }

    @RequestMapping(value = "{parent}/appendChild", method = RequestMethod.POST)
    public String appendChild(Model model, @PathVariable("parent") ID parent,
            @ModelAttribute("child") M child, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (permissionList != null) {
            permissionList.assertHasCreatePermission();
        }

        if (result.hasErrors()) {
            return appendChildForm(parent, model);
        }
        M parentModel = baseService.findOne(parent);
        baseService.appendChild(parentModel, child);

        redirect(redirectAttributes, Message.success("添加子节点成功", null));
        return redirectToUrl(viewName("success.jhtml"));
    }

    @RequestMapping(value = "{source}/move", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    public String showMoveForm(
            HttpServletRequest request,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @PathVariable("source") ID id,
            Searchable searchable,
            Model model) {

//        if (this.permissionList != null) {
//            this.permissionList.assertHasEditPermission();
//        }

        List<M> models = null;

        //排除自己及子子孙孙
        M source = baseService.findOne(id);
        searchable.addSearchFilter("id", SearchOperator.ne, source.getId());
        searchable.addSearchFilter(
                "parentIds",
                SearchOperator.notLike,
                source.makeSelfAsNewParentIds() + "%");

        if (!async) {
            models = baseService.findAllWithSort(searchable);
        } else {
            models = baseService.findRootAndChild(searchable);
        }
        model.addAttribute("source", source);
        model.addAttribute("trees", convertToZtreeList(
                request.getContextPath(),
                models,
                async,
                true));

        model.addAttribute(Constants.OP_NAME, "移动节点");

        return viewName("moveForm");
    }

    @RequestMapping(value = "{source}/move", method = RequestMethod.POST)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    public String move(
            HttpServletRequest request,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @PathVariable("source") ID id,
            @RequestParam("targetId") ID targetId,
            @RequestParam("moveType") String moveType,
            Searchable searchable,
            Model model,
            RedirectAttributes redirectAttributes) {

//        if (this.permissionList != null) {
//            this.permissionList.assertHasEditPermission();
//        }
    	M source = baseService.findOne(id);
    	M target = baseService.findOne(targetId);
        if (target.isRoot() && !moveType.equals("inner")) {
            model.addAttribute(Constants.ERROR, "不能移动到根节点之前或之后");
            return showMoveForm(request, async, source.getId(), searchable, model);
        }
        baseService.move(source, target, moveType);
        redirect(redirectAttributes, Message.success("移动节点成功", null));
        return redirectToUrl(viewName("success.jhtml"));
    }

    @RequestMapping(value = "{parent}/children", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    public String list(
            HttpServletRequest request,
            @PathVariable("parent") M parent,
            Searchable searchable, Model model) throws UnsupportedEncodingException {
        if (permissionList != null) {
            permissionList.assertHasViewPermission();
        }
        if (parent != null) {
            searchable.addSearchFilter("parentId", SearchOperator.eq, parent.getId());
        }
        model.addAttribute("page", baseService.findAll(searchable));
        return viewName("listChildren");
    }

    /**
     * 仅返回表格数据
     *
     * @param searchable
     * @param model
     * @return
     */
    @RequestMapping(value = "{parent}/children", headers = "table=true", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    public String listTable(
            HttpServletRequest request,
            @PathVariable("parent") M parent,
            Searchable searchable, Model model) throws UnsupportedEncodingException {

        list(request, parent, searchable, model);
        return viewName("listChildrenTable");

    }

    /////////////////////////////////////ajax///////////////////////////////////////////////

    @RequestMapping(value = "ajax/load")
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
    @ResponseBody
    public Object load(
            HttpServletRequest request,
            @RequestParam(value = "async", defaultValue = "true") boolean async,
            @RequestParam(value = "asyncLoadAll", defaultValue = "false") boolean asyncLoadAll,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "id", required = false) ID parentId,
            @RequestParam(value = "excludeId", required = false) ID excludeId,
            @RequestParam(value = "onlyCheckLeaf", required = false, defaultValue = "false") boolean onlyCheckLeaf,
            Searchable searchable) {


        M excludeM = baseService.findOne(excludeId);

        List<M> models = null;

        if (!StringUtils.isEmpty(searchName)) {//按name模糊查
            searchable.addSearchParam("name_like", searchName);
            models = baseService.findAllByName(searchable, excludeM);
            if (!async || asyncLoadAll) {//非异步模式 查自己及子子孙孙 但排除
                searchable.removeSearchFilter("name_like");
                List<M> children = baseService.findChildren(models, searchable);
                models.removeAll(children);
                models.addAll(children);
            } else { //异步模式 只查匹配的一级

            }
        } else { //根据有没有parentId加载

            if (parentId != null) { //只查某个节点下的 异步
                searchable.addSearchFilter("parentId", SearchOperator.eq, parentId);
            }

            if (async && !asyncLoadAll) { //异步模式下 且非异步加载所有
                //排除自己 及 子子孙孙
                baseService.addExcludeSearchFilter(searchable, excludeM);

            }

            if (parentId == null && !asyncLoadAll) {
                models = baseService.findRootAndChild(searchable);
            } else {
                models = baseService.findAllWithSort(searchable);
            }
        }

        return convertToZtreeList(
                request.getContextPath(),
                models,
                async && !asyncLoadAll && parentId != null,
                onlyCheckLeaf);
    }

    @RequestMapping(value = "ajax/{parent}/appendChild", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxAppendChild(HttpServletRequest request, @PathVariable("parent") ID parent) {
        if (permissionList != null) {
            permissionList.assertHasCreatePermission();
        }

        M child = newModel();
        child.setName("新节点");
        baseService.appendChild(baseService.findOne(parent), child);
        return convertToZtree(child, true, true);
    }

    @RequestMapping(value = "ajax/{id}/delete", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxDeleteSelfAndChildren(@PathVariable("id") ID id) {
        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }

        M tree = baseService.findOne(id);
        baseService.deleteSelfAndChild(tree);
        return tree;
    }

    @RequestMapping(value = "ajax/{id}/rename", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxRename(HttpServletRequest request, @PathVariable("id") ID id, @RequestParam("newName") String newName) {
        if (permissionList != null) {
            permissionList.assertHasUpdatePermission();
        }
        M tree = baseService.findOne(id);
        tree.setName(newName);
        baseService.update(tree);
        return convertToZtree(tree, true, true);
    }


    @RequestMapping(value = "ajax/{sourceId}/{targetId}/{moveType}/move", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxMove(
            @PathVariable("sourceId") ID sourceId, @PathVariable("targetId") ID targetId,
            @PathVariable("moveType") String moveType) {


        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }
        M source = baseService.findOne(sourceId);
        M target = baseService.findOne(targetId);
        baseService.move(source, target, moveType);

        return source;
    }


    @RequestMapping("ajax/autocomplete")
    @PageableDefaults(pageSize = 30)
    @ResponseBody
    public Set<String> autocomplete(
            Searchable searchable,
            @RequestParam("key") String key,
            @RequestParam(value = "excludeId", required = false) ID excludeId) {

        return baseService.findNames(searchable, key, excludeId);
    }


    @RequestMapping(value = "success")
    public String success() {
        return viewName("success");
    }

    @Override
    protected String redirectToUrl(String backURL) {
        if (!StringUtils.isEmpty(backURL)) {
            return super.redirectToUrl(backURL);
        }
        return super.redirectToUrl(viewName("success.jhtml"));
    }

    protected List<ZTree<ID>> convertToZtreeList(String contextPath, List<M> models, boolean async, boolean onlySelectLeaf) {
        List<ZTree<ID>> zTrees = Lists.newArrayList();

        if (models == null || models.isEmpty()) {
            return zTrees;
        }

        for (M m : models) {
            ZTree zTree = convertToZtree(m, !async, onlySelectLeaf);
            zTrees.add(zTree);
        }
        return zTrees;
    }

    private ZTree convertToZtree(M m, boolean open, boolean onlyCheckLeaf) {
        ZTree<ID> zTree = new ZTree<ID>();
        zTree.setId(m.getId());
        zTree.setpId(m.getParentId());
        zTree.setName(m.getName());
        zTree.setIconSkin(m.getIcon());
        zTree.setOpen(open);
        zTree.setRoot(m.isRoot());
        zTree.setIsParent(m.getHasChildren());

        if (onlyCheckLeaf && zTree.isIsParent()) {
            zTree.setNocheck(true);
        } else {
            zTree.setNocheck(false);
        }

        return zTree;
    }
}