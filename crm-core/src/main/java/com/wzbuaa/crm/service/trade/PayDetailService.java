package com.wzbuaa.crm.service.trade;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wzbuaa.crm.domain.trade.PayDetailDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.repository.trade.PayDetailRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.util.DateHelper;

@Service 
public class PayDetailService extends BaseService<PayDetailDomain, Long> {

	private PayDetailRepository getPaymentDetailRepository() {
		 return (PayDetailRepository) baseRepository;
	}
	
	public PayDetailDomain getPayDetailByPaymentNo(String paymentNo, Long memberId) {
		PayDetailDomain paymentDetail = getPaymentDetailRepository().findByPaymentNoAndMember(paymentNo, memberId);
		return paymentDetail;
	}

	public String getPaymemntDetailInfo(String paymentNo, Long memberId) {
		PayDetailDomain paymentDetail = getPayDetailByPaymentNo(paymentNo, memberId);
		if(paymentDetail == null){
			throw new ApplicationException("收支明细不存在!");
		}
		JSONObject jObj = new JSONObject();
		jObj.put("remarks", paymentDetail.getRemark());
		jObj.put("type", paymentDetail.getDetailType());
		jObj.put("amount", paymentDetail.getAmount());
		jObj.put("paymentTypeName", paymentDetail.getPaymentTypeName());
		jObj.put("createDate", DateHelper.date2String(paymentDetail.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		jObj.put("balance", paymentDetail.getBalance());
		return jObj.toString();
	}

}
