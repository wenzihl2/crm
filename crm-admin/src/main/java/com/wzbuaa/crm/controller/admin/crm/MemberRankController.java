package com.wzbuaa.crm.controller.admin.crm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.crm.MemberRankDomain;

/**
 * 会员等级
 */
@Controller
@RequestMapping("/admin/crm/memberRank")
public class MemberRankController extends BaseCRUDController<MemberRankDomain, Long> {
	
	public MemberRankController() {
        setResourceIdentity("sys:memberRank");
    }
	
}