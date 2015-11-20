package com.wzbuaa.crm.controller.admin.sso;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseTreeableController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.user.JobDomain;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/sso/job")
public class JobController extends BaseTreeableController<JobDomain, Long> {
	
	public JobController() {
        setResourceIdentity("sys:job");
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
            JobDomain job = baseService.findOne(id);
            job.setShow(newStatus);
            baseService.update(job);
        }
        redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }
}