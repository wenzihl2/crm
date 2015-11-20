package com.wzbuaa.crm.controller.admin;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.component.entity.Movable;
import com.wzbuaa.crm.component.service.BaseMovableService;
import com.wzbuaa.crm.domain.BaseEntity;

import framework.Message;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-22 下午4:15
 * <p>Version: 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseMovableController<M extends BaseEntity & Movable, ID extends Serializable>
        extends BaseCRUDController<M, ID> {

    protected BaseMovableService<M, ID> getMovableService() {
        return (BaseMovableService<M, ID>) baseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(pageSize = 20, sort = "priority=desc")
    @Override
    public String list(Searchable searchable, Model model) {
        return super.list(searchable, model);
    }

    @RequestMapping(value = "{fromId}/{toId}/up")
    @ResponseBody
    public Message up(@PathVariable("fromId") ID fromId, @PathVariable("toId") ID toId) {

        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }
        try {
            getMovableService().up(fromId, toId);
        } catch (IllegalStateException e) {
        	return Message.error("move.not.enough", null);
        }
        return Message.success("移动位置成功", null);
    }

    @RequestMapping(value = "{fromId}/{toId}/down")
    @ResponseBody
    public Message down(@PathVariable("fromId") ID fromId, @PathVariable("toId") ID toId) {
        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }

        try {
            getMovableService().down(fromId, toId);
        } catch (IllegalStateException e) {
        	return Message.error("move.not.enough", null);
        }
        return Message.success("移动位置成功", null);
    }

    @RequestMapping(value = "reweight")
    @ResponseBody
    public Message reweight() {
        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }
        try {
            getMovableService().reweight();
        } catch (IllegalStateException e) {
            return Message.error("优化权重失败了！", null);
        }
        return Message.success("优化权重成功！", null);
    }

}
