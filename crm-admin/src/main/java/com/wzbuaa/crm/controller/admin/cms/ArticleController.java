package com.wzbuaa.crm.controller.admin.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.cms.ArticleDomain;

@Controller
@RequestMapping("/admin/cms/article")
public class ArticleController extends BaseCRUDController<ArticleDomain, Long>  {

}
