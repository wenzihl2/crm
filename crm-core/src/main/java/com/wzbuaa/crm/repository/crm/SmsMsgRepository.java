package com.wzbuaa.crm.repository.crm;

import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.MessageStatus;
import com.wzbuaa.crm.domain.crm.SmsMsgDomain.SmsMsgType;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 *	短信
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
public interface SmsMsgRepository extends BaseRepository<SmsMsgDomain, Long> {
	
	public SmsMsgDomain findByMsgTypeAndConfirmationAndMemberAndStatus(SmsMsgType msgType, String confirmation, MemberDomain member, MessageStatus status);
	
	public SmsMsgDomain findByMsgTypeAndSnAndMemberAndStatus(SmsMsgType msgType, String sn, MemberDomain member, MessageStatus status);
		
	public SmsMsgDomain findByMsgTypeAndMobileAndConfirmationAndStatus(SmsMsgType msgType, String mobile, String confirmation, MessageStatus status);
}
