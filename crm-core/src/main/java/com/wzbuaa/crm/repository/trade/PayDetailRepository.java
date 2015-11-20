package com.wzbuaa.crm.repository.trade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wzbuaa.crm.domain.trade.PayDetailDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface PayDetailRepository extends BaseRepository<PayDetailDomain, Long> {
	
	@Query("from PayDetailDomain bean where bean.paymentNo=:paymentNo and bean.member.id=:memberId")
	public PayDetailDomain findByPaymentNoAndMember(@Param("paymentNo") String paymentNo, @Param("memberId") Long memberId);
}
