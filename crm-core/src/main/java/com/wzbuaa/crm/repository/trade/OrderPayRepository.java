package com.wzbuaa.crm.repository.trade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wzbuaa.crm.bean.PayStatus;
import com.wzbuaa.crm.domain.trade.OrderPayDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface OrderPayRepository extends BaseRepository<OrderPayDomain, Long> {
	
	/**
	 * 根据会员和交易编号查找订单付款记录
	 * @param tradeNo
	 * @param member
	 * @return
	 */
	@Query("from OrderPayDomain bean where bean.tradeNo=:tradeNo and bean.member.id=:member_id")
	public OrderPayDomain findByTradeNoAndMember(@Param("tradeNo") String tradeNo, @Param("member_id") Long member_id);
	
	
	/**
	 * 根据会员和交易编号查找订单付款记录
	 * @param tradeNo
	 * @param member
	 * @return
	 */
	@Query("from OrderPayDomain bean where bean.tradeNo=:tradeNo and bean.member.id=:member_id and bean.payStatus=:payStatus")
	public OrderPayDomain findByTradeNoAndMemberAndPayStatus(@Param("tradeNo") String tradeNo, 
			@Param("member_id") Long member_id, @Param("payStatus") PayStatus payStatus);
}
