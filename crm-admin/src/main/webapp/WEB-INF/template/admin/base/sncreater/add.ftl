<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
	var mainform = null;
	$(function(){
		mainform = $("#mainform").ligerForm({inputWidth: 170, labelWidth: 50, space: 20, rightToken: ""});
		var mainform = $("#mainform");  
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "sncreater_list");
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
<#if !sncreater??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<body style="padding:10px;">
<form action="<#if isAdd??>add.jhtml<#else>edit.jhtml</#if>" method="post" id="mainform" class="validate">
<div id="tabcontainer" style="margin-bottom:50px;">
	<#if !isAdd??>
		<input type="hidden" name="id" value="${(sncreater.id)!''}" />
	</#if>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<th class="l-table-edit-td">编码：</th>
			<td class="l-table-edit-td">
				<input type="text" name="code" class="{required: true, username: true}" value="${(sncreater.code)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">名称：</th>
			<td class="l-table-edit-td">
				<input type="text" name="name" class="{required: true, username: true}" value="${(sncreater.name)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">前缀：</th>
			<td class="l-table-edit-td">
				<input type="text" name="qz" class="{required: true, username: true}" value="${(sncreater.qz)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">是否启用：</th>
			<td class="l-table-edit-td"><@u.radio name="qyrq" list={"true": "是", "false": "否"} value="${(sncreater.qyrq)!'true'}" class="qyrq"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">日期格式：</th>
			<td class="l-table-edit-td">
				<input type="text" name="rqgs" id="rqgs" class="{required: true, username: true}" value="${(sncreater.rqgs)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">当前序号：</th>
			<td class="l-table-edit-td">
				<input type="text" name="dqxh" class="{required: true, number: true}" value="${(sncreater.dqxh)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">更新周期：</th>
			<td class="l-table-edit-td">
				<@u.select_map headerKey="" headerValue="顶级分类"
					headerButtom="false" list=EnumUtils.enumProp2I18nMap("UpdateCycle") name="gxzq"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">序号位数：</th>
			<td class="l-table-edit-td">
				<input type="text" name="ws" class="{required: true, number: true}" value="${(sncreater.ws)!''}" />
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>