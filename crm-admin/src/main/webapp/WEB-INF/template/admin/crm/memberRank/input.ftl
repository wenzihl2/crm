<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
[#if !m??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");  
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "memberRank");
		});
	});
</script>
</head>
<body style="overflow:hidden;padding:10px;">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform" class="validate">
		[#if isEdit??]
			<input type="hidden" name="id" value="${(m.id)!''}" />
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<th class="l-table-edit-td">等级名称: </th>
				<td class="l-table-edit-td">
					<input type="text" name="name" class="{required: true}" value="${(m.name)!''}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">优惠百分比:</th>
				<td class="l-table-edit-td">
					<input type="text" name="scale" class="{required: true, digits: true, min:0,max:100}" 
						value="${(m.scale)!''}" 
						title="单位: %, 若输入90,表示该会员等级以商品价格的90%进行销售" />%
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">所需积分:</th>
				<td class="l-table-edit-td">
					<input type="text" name="point" class="{required: true, digits: true, min:0}" value="${(m.point)!''}" title="只允许输入零或正整数" />
					 
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">设置默认:</th>
				<td class="l-table-edit-td">
					[@u.radio name="isDefault" list={"true": "是", "false":"否"} value="${(m.isDefault)!'1'}"/]
				</td>
			</tr>
		</table>
	</form>
</body>
</html>