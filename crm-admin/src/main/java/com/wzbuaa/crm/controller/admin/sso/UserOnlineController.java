package com.wzbuaa.crm.controller.admin.sso;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.eis.OnlineSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.user.UserOnline;

import framework.Message;
import framework.Pager;
import framework.entity.search.Searchable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/user/online")
public class UserOnlineController extends BaseCRUDController<UserOnline, String> {

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    public UserOnlineController() {
    }


    @Override
    public Pager<UserOnline> queryData(Searchable searchable) {
//        if (!SecurityUtils.getSubject().isPermitted("sys:userOnline:view or monitor:userOnline:view")) {
//            throw new UnauthorizedException(Message.warn("no.view.permission", new String[]{"sys:userOnline:view或monitor:userOnline:view"}).getContent());
//        }
        return super.queryData(searchable);
    }

    @RequestMapping("/forceLogout")
    public String forceLogout(@RequestParam(value = "ids") String[] ids) {

        if (!SecurityUtils.getSubject().isPermitted("sys:userOnline or monitor:userOnline")) {
            throw new UnauthorizedException(Message.warn("no.view.permission", new String[]{"sys:userOnline或monitor:userOnline"}).getContent());
        }

        for (String id : ids) {
            UserOnline online = baseService.findOne(id);
            if (online == null) {
                continue;
            }
            OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
            if (onlineSession == null) {
                continue;
            }
            onlineSession.setStatus(OnlineSession.OnlineStatus.force_logout);
            online.setStatus(OnlineSession.OnlineStatus.force_logout);
            baseService.update(online);
        }
        return redirectToUrl(null);
    }

}
