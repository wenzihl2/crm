<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		mainform.ligerForm();
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "company_list");
		});
		$(".qyrq").change(function() {
			if($(this).val() == "false") {
				$("#rqgs").attr("disabled", true);
			} else {
				$("#rqgs").removeAttr("disabled");
			}
		});
	});
</script>
</head>
[#if !company??]
	[#assign isAdd = true /]
[#else>
	[#assign isEdit = true /]
[/#if]
<body style="padding:10px;">
<form action="[#if isAdd??]add.jhtml[#else]edit.jhtml[/#if]" method="post" id="mainform">
<div id="tabcontainer" style="margin-bottom:50px;">
	<input type="hidden" name="isSystem" value="true" />
	[#if !isAdd??]
		<input type="hidden" name="id" value="${(company.id)!''}" />
	[/#if]
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<th class="l-table-edit-td">企业代码：</th>
			<td class="l-table-edit-td">
				<input type="text" name="code" class="{required: true, username: true}" value="${(company.code)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">企业名称：</th>
			<td class="l-table-edit-td">
				<input type="text" name="name" class="{required: true, username: true}" value="${(company.name)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">企业简称：</th>
			<td class="l-table-edit-td">
				<input type="text" name="short_name" class="{required: true, username: true}" value="${(company.short_name)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">行业：</th>
			<td class="l-table-edit-td">
				[@u.select_map headerKey="" headerValue="顶级分类" value="${(company.industry.id)!''}"
					headerButtom="false" list=DictionaryUtils.category("Industry") name="industry.id"/]
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">地址：</th>
			<td class="l-table-edit-td">
				<input type="text" name="address" value="${(company.address)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">电话：</th>
			<td class="l-table-edit-td">
				<input type="text" name="telephone" value="${(company.telephone)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">传真：</th>
			<td class="l-table-edit-td">
				<input type="text" name="fax" value="${(company.fax)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">注册日期：</th>
			<td class="l-table-edit-td">
				<input type="text" name="reg_date" value="${(company.reg_date)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">失效日期：</th>
			<td class="l-table-edit-td">
				<input type="text" name="expiry_date" value="${(company.expiry_date)!''}" />
			</td>
		</tr>
		
		<tr>
			<th class="l-table-edit-td">默认付款周期数：</th>
			<td class="l-table-edit-td">
				<input type="text" name="chargetimes" value="${(config.chargetimes)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">默认付款比例：</th>
			<td class="l-table-edit-td">
				<input type="text" name="chargeRate" value="${(config.chargeRate)!''}" />%
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">默认收款周期数：</th>
			<td class="l-table-edit-td">
				<input type="text" name="gathertimes" value="${(config.gathertimes)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">默认收款比例：</th>
			<td class="l-table-edit-td">
				<input type="text" name="gatherRate" value="${(config.gatherRate)!''}" />%
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">合同提醒时间：</th>
			<td class="l-table-edit-td">
				<input type="text" name="pact_alarm" value="${(config.pact_alarm)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">客户池回收周期：</th>
			<td class="l-table-edit-td">
				<input type="text" name="custpool_c" value="${(config.custpool_c)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">会员卡支付:</th>
			<td class="l-table-edit-td">[@u.radio name="memberCardPay" list={"true": "启用", "false": "不启用"} value="${(config.memberCardPay?string('true', 'false'))!'false'}"/]
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">销售订单启用审核：</th>
			<td class="l-table-edit-td">[@u.radio name="saleAudit" list={"true": "启用", "false": "不启用"} value="${(config.saleAudit?string('true', 'false'))!'false'}"/]
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">销售订单审核级次：</th>
			<td class="l-table-edit-td">[@u.radio name="saleAuditType" list={"true": "直接领导 ", "false": "直至顶级领导"} value="${(config.saleAuditType?string('true', 'false'))!'true'}"/]
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">销售退货启用审核：</th>
			<td class="l-table-edit-td">[@u.radio name="saletuiAudit" list={"true": "启用", "false": "不启用"} value="${(config.saletuiAudit?string('true', 'false'))!'false'}"/]
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>