<tr>
	 <th class="l-table-edit-td">支付宝合作伙伴id ：</th>
	 <td class="l-table-edit-td">
 	 <input type="text" name="partner" value="${partner!''}"/>
	</td>
</tr>
<tr>
	 <th class="l-table-edit-td"> 支付宝安全校验码：</th>
	 <td class="l-table-edit-td">
 	 <input type="text" name="key"  value="${key!''}"/>
	</td>
</tr>
<tr>
	 <th class="l-table-edit-td"> 卖家支付宝帐户：</th>
	 <td class="l-table-edit-td">
 	 <input type="text" name="seller_email"  value="${seller_email!''}"/>
	</td>
</tr>
<tr>
	 <th class="l-table-edit-td">&nbsp;</th>
	 <td class="l-table-edit-td">
 	 下面两个参数定义接收支付宝回调通知和成功返回页面时的参数编码<br>
 	 如果编码不正确会导至签名验证失败<br>
 	 请根据Web服务器的的配置情况选择<br>
	</td>
</tr>
<tr>
	 <th class="l-table-edit-td"> notify编码：</th>
	 <td class="l-table-edit-td">
 	 	<select name="callback_encoding">
 	 		<option value="">不编码</option>
 	 		<option value="UTF-8" <#if callback_encoding?exists && callback_encoding=='UTF-8'>selected</#if>>UTF-8</option>
 	 		<option value="GBK" <#if callback_encoding?exists && callback_encoding=='GBK'>selected</#if>>GBK</option>
 	 	</select>
	</td>
</tr>
<tr>
	 <th class="l-table-edit-td">return编码：</th>
	 <td class="l-table-edit-td">
 	 	<select name="return_encoding">
 	 		<option value="">不编码</option>
 	 		<option value="UTF-8" <#if return_encoding?exists &&  return_encoding=='UTF-8'>selected</#if>>UTF-8</option>
 	 		<option value="GBK" <#if  return_encoding?exists && return_encoding=='GBK'>selected</#if>>GBK</option>
 	 	</select>
	</td>
</tr>