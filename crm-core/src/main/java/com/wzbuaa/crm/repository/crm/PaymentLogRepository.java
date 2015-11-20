package com.wzbuaa.crm.repository.crm;

import com.wzbuaa.crm.domain.trade.PaymentLogDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface PaymentLogRepository extends BaseRepository<PaymentLogDomain, Long> {
	
	public PaymentLogDomain findByOrderNoAndDealId(String orderNo, String dealId);
}
