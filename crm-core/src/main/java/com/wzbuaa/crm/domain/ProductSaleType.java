package com.wzbuaa.crm.domain;

/**
 * 产品销售类型: 普通销售(默认)、团购、 促销、 捆绑、 普通商品、 赠品、 限时抢购
 * @author zhenglong
 */
public enum ProductSaleType {

	NORMAL, //商品
	GOUPYBUY, //团购 
	OFFLINE, //线下产品
	VIRTUAL, //虚拟
	PROMOTION, //促销
	BIND, //捆绑商品
	GIFT, //赠品
	TIMEDBUY;//限时抢购
	
}
