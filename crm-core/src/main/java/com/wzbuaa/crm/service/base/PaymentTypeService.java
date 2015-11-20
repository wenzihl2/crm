package com.wzbuaa.crm.service.base;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.wzbuaa.crm.domain.base.PaymentTypeDomain;
import com.wzbuaa.crm.plugin.payment.PaymentPluginBundle;
import com.wzbuaa.crm.repository.base.PaymentTypeRepository;
import com.wzbuaa.crm.service.BaseService;
import framework.plugin.IPlugin;


@Repository
public class PaymentTypeService extends BaseService<PaymentTypeDomain, Long> {
	
	@Resource private PaymentPluginBundle paymentPluginBundle;
	
	private PaymentTypeRepository getPaymentTypeRepository() {
		 return (PaymentTypeRepository) baseRepository;
	}
	
	/**
	 * 读取支付方式插件列表
	 * @return 系统中注册的所有支付方式插件
	 */
	public  List<IPlugin> listAvailablePlugins() {
		return paymentPluginBundle.getPluginList();
	}
}
