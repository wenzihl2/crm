package com.wzbuaa.crm.controller.admin.base;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.base.TemplateDomain;
import com.wzbuaa.crm.domain.base.TemplateDomain.TemplateType;
import com.wzbuaa.crm.service.base.TemplateService;

/**
 * 后台Action类 - 模板
 */
@Controller
@RequestMapping("/admin/base/template")
public class TemplateController  extends BaseCRUDController<TemplateDomain, Long> {

	@Resource private TemplateService templateService;
	
	public TemplateController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("maintain:template");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", TemplateType.values());
    }
}