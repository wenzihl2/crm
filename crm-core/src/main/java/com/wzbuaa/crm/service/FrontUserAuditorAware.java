package com.wzbuaa.crm.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.FrontUserRealm.ShiroUser;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

@Service
public class FrontUserAuditorAware implements AuditorAware<ShiroUser> {

	@Override  
    public ShiroUser getCurrentAuditor() {  
        Subject subject = SecurityUtils.getSubject();  
        Object object = subject.getPrincipal();  
        return (ShiroUser) object;  
    } 

	
}
