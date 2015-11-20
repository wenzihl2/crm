package com.wzbuaa.crm.controller.admin.sso;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.user.UserStatus;
import com.wzbuaa.crm.domain.sso.user.UserStatusHistory;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/user/statusHistory")
public class UserStatusHistoryController extends BaseCRUDController<UserStatusHistory, Long> {

    public UserStatusHistoryController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("sys:userStatusHistory");
    }


    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", UserStatus.values());
    }

}
