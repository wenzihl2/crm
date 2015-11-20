package com.wzbuaa.crm.controller.admin.crm;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.trade.PayDetailDomain;
import com.wzbuaa.crm.service.trade.PayDetailService;

/**
 * 后台Action类 - 收支明细
 */
@Controller
@RequestMapping("admin/member/payment")
public class PaymentDetailController extends BaseCRUDController<PayDetailDomain, Long> {
	
	@Resource private PayDetailService paymentDetailService;

	/**
	 * 显示收款单详情
	 * @return
	 */
	@RequestMapping(value="{id}/detail", method=RequestMethod.GET)
	public String detail(@PathVariable Long id, Model model){
		PayDetailDomain paymentDetail = paymentDetailService.findOne(id);
		model.addAttribute("paymentDetail",paymentDetail);
		return viewName("detail");
	}

}
