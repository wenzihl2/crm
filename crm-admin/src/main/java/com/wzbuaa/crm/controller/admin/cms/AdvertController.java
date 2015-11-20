package com.wzbuaa.crm.controller.admin.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseMovableController;
import com.wzbuaa.crm.domain.cms.AdvertDomain;

@Controller
@RequestMapping("/admin/cms/advert")
public class AdvertController extends BaseMovableController<AdvertDomain, Long>  {

}
