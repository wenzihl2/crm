package com.wzbuaa.crm.controller.admin.trade;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.trade.PayDetailDomain;
import com.wzbuaa.crm.service.trade.PayDetailService;

/**
 * 交易明细
 * @author zhenglong
 *
 */
@Controller
@RequestMapping("/admin/trade/payDetail")
public class PayDetailController extends BaseCRUDController<PayDetailDomain, Long>{
	
	@Resource private PayDetailService payDetailService;
	
}
