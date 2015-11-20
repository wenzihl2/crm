package com.wzbuaa.crm.controller.admin.trade;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.service.trade.OrderService;

/**
 * 订单管理
 * @author liuzhenxing
 *
 */
@Controller
@RequestMapping("/admin/trade/order")
public class OrderController extends BaseCRUDController<OrderDomain, Long>{
	
	@Resource private OrderService orderService;
	
	public OrderController() {
        setResourceIdentity("sys:order");
    }
	
}
