package com.wzbuaa.crm.controller.admin.crm;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.service.crm.SmsMsgService;

/**
 * 短信
 * @author zhenglong
 */
@Controller
@RequestMapping("/admin/crm/smsMessage")
public class SmsMessageController extends BaseCRUDController<SmsMsgDomain, Long> {

	@Resource private SmsMsgService messageSendService;

}
