package com.wzbuaa.crm.service.base;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import framework.util.AppContext;
import framework.util.RSAUtils;

@Repository
public class RSAService {

	private static final String privateKey = "privateKey";

	public RSAPublicKey generateKey(HttpServletRequest request) {
		Assert.notNull(request);
		KeyPair keypair = RSAUtils.generateKeyPair();
		RSAPublicKey rsapublickey = (RSAPublicKey)keypair.getPublic();
		RSAPrivateKey rsaprivatekey = (RSAPrivateKey)keypair.getPrivate();
		HttpSession httpsession = request.getSession();
		httpsession.setAttribute(privateKey, rsaprivatekey);
		return rsapublickey;
	}

	public void removePrivateKey(HttpServletRequest request) {
		Assert.notNull(request);
		HttpSession httpsession = request.getSession();
		httpsession.removeAttribute(privateKey);
	}

	public String decryptParameter(String name, HttpServletRequest request) {
		Assert.notNull(request);
		if (name != null) {
			HttpSession httpsession = request.getSession();
			RSAPrivateKey rsaprivatekey = (RSAPrivateKey)httpsession.getAttribute(privateKey);
			String s = request.getParameter(name);
			if (rsaprivatekey != null && StringUtils.isNotEmpty(s)){
				return RSAUtils.decrypt(rsaprivatekey, s);
			}
		}
		return null;
	}
	public String decryptParameter(String name) {
		HttpServletRequest request = AppContext.getRequest();
		Assert.notNull(request);
		if (name != null) {
			HttpSession httpsession = request.getSession();
			RSAPrivateKey rsaprivatekey = (RSAPrivateKey)httpsession.getAttribute(privateKey);
			if (rsaprivatekey != null && StringUtils.isNotEmpty(name)){
				return RSAUtils.decrypt(rsaprivatekey, name);
			}
		}
		return null;
	}
}
