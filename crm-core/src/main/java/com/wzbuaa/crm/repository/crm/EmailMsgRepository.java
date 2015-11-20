package com.wzbuaa.crm.repository.crm;

import com.wzbuaa.crm.domain.crm.EmailMsgDomain;
import com.wzbuaa.crm.domain.crm.EmailMsgDomain.EmailMsgType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.repository.BaseRepository;

/**
 *	邮件
 * <p>User: zhenglong
 * <p>Date: 2015年11月14日
 * <p>Version: 1.0
 */
public interface EmailMsgRepository extends BaseRepository<EmailMsgDomain, Long> {
	
	public EmailMsgDomain findByMessageTypeAndConfirmationAndMember(EmailMsgType messageType, String confirmation, MemberDomain member);
	
}
