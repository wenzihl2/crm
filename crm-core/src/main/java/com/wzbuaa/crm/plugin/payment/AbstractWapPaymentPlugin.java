package com.wzbuaa.crm.plugin.payment;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.wzbuaa.crm.domain.SnCreateType;
import com.wzbuaa.crm.domain.base.SnCreaterDomain;
import com.wzbuaa.crm.service.base.PaymentTypeService;
import com.wzbuaa.crm.service.base.PluginService;
import com.wzbuaa.crm.service.base.SnCreaterService;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.service.crm.PaymentLogService;
import com.wzbuaa.crm.util.ShopUtil;

import framework.plugin.AutoRegisterPlugin;

/**
 * 支付插件基类
 */
public abstract class AbstractWapPaymentPlugin extends AutoRegisterPlugin {
	
	@Resource protected PaymentLogService paymentLogService;
	@Resource protected MemberService memberService;
	@Resource protected PaymentTypeService paymentTypeService;
	@Resource protected PluginService pluginService;
	@Resource private SnCreaterService snCreaterService;

	/**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(String params, String strMethod, String signMsg, String action) {
        //待请求参数数组
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"paysubmit\" name=\"alipaysubmit\" action=\"" + action + "\" method=\"" + strMethod + "\">");

        if(StringUtils.isNotBlank(params)){
             for (String param : params.split("&")) {
            	 String[] detail = param.split("=");
            	 if(detail.length == 2){
                     sbHtml.append("<input type=\"hidden\" name=\"" + detail[0] + "\" value=\"" + detail[1] + "\"/>");
            	 }
             }
        }
        if(!StringUtils.isEmpty(signMsg)){
        	sbHtml.append("<input type=\"hidden\" name=\"signMsg\" value=\"" + signMsg + "\"/>");
        }
        sbHtml.append("<input type=\"submit\" value=\"提交\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['paysubmit'].submit();</script>");

        return sbHtml.toString();
    }

	protected boolean isRecharge(String tradeNo){
		SnCreaterDomain snCreater = snCreaterService.findById(SnCreateType.PURCHASE, ShopUtil.getUser().companyId);
		return tradeNo.indexOf(snCreater.getQz()) == 0;
	}

	public String getRequstType() {
		return "form";
	}

	public String getEncode(){
		return "utf8";
	}
}
