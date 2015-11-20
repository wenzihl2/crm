<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<#include "/admin/include/head.htm">
</head>
<body style="padding:10px; overflow:hidden;">
    <div class="input">
        <table class="table">
            <tbody>
            	<#if paymentDetail.detailType != 'recharge'>
	                <tr>
	                    <th>订单编号：</th>
	                    <td>${(paymentDetail.orderNo)!''}</td>
	                </tr>
				</#if>
				<tr>
					<th>交易类型：</th>
					<td>${DictionaryUtils.getDetailType(paymentDetail.detailType)}</td>
				</tr>
				<tr>
                    <th>支付类型：</th>
                    <td>${(paymentStatus)!'网上支付'}</td>
                </tr>
                <tr>
                    <th>支付方式：</th>
                    <td>${(paymentDetail.paymentTypeName)!'余额支付'}</td>
                </tr>
                <tr>
                    <th>流水号：</th>
                    <td>${(paymentDetail.paymentNo)!''}</td>
                </tr>
                <tr>
                    <th>生成时间：</th>
                    <td>${(paymentDetail.createDate?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
                </tr>
                <tr>
                    <th>操作员：</th>
                    <td>${(paymentDetail.operator)!''}</td>
                </tr>
                <tr>
                    <th>收支金额：</th>
                    <td>${(setting.currencySign)!''}${(paymentDetail.amount?string('0.00'))!''}</td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="table" style="margin: 0pt;">
            <tbody>
                <tr>
                    <th>备注：</th>
					<td>${(paymentDetail.remarks)!''}</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>