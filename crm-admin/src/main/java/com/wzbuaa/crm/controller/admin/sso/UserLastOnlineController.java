package com.wzbuaa.crm.controller.admin.sso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.user.UserLastOnline;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/user/lastOnline")
public class UserLastOnlineController extends BaseCRUDController<UserLastOnline, Long> {

    public UserLastOnlineController() {
        setResourceIdentity("sys:userLastOnline");
    }
}
