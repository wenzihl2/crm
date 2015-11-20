package com.wzbuaa.crm.service.crm;

import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.domain.trade.PaymentLogDomain;
import com.wzbuaa.crm.repository.crm.PaymentLogRepository;
import com.wzbuaa.crm.service.BaseService;

@Repository
public class PaymentLogService extends BaseService<PaymentLogDomain, Long> {
	
	private PaymentLogRepository getPaymentLogRepository() {
		 return (PaymentLogRepository) baseRepository;
	}
	
	public boolean isPayReturn(String orderNo, String dealId) {
		PaymentLogDomain paymentLog = getPaymentLogRepository().findByOrderNoAndDealId(orderNo, dealId);
		return paymentLog == null ? true : false;
	}
}