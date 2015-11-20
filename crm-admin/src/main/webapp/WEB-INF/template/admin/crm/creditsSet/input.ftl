<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		mainform.ligerForm();
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "creditsSet");
		});
	});
</script>
</head>
[#if !m??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
<body style="padding:10px;">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform" class="validate">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}" />
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<th class="l-table-edit-td">名称: </th>
				<td class="l-table-edit-td"><input type="text" id="name" name="name" class="formText {required: true,maxlength: 20}" value="${(m.name)!''}" />	 
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">获取方式: </th>
				<td class="l-table-edit-td">
					 [@u.select_map headerKey="" headerValue="请选择"
						headerButtom="false" list=EnumUtils.enumProp2I18nMap("CreditsGetType") value="${(m.getType)!''}"
						name="getType"/]
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">等级积分: </th>
				<td class="l-table-edit-td">
					<input type="text" name="rankPoint" class="formText {required: true,digits:true,min:0}" 
					value="${(m.rankPoint)!'0'}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">消费积分: </th>
				<td class="l-table-edit-td">
					<input type="text" name="consumePoint" class="formText {required: true,digits:true,min:0}" 
					value="${(m.consumePoint)!'0'}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">开始日期：</th>
				<td class="l-table-edit-td">
			   		<input type="text" name="startDate" class="formText WdatePicker {dateISO: true}" value="${((m.startDate)?string('yyyy-MM-dd'))!''}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">结束日期：</th>
				<td class="l-table-edit-td">
			   		<input type="text" name="endDate" class="formText WdatePicker {dateISO: true}" value="${((m.endDate)?string('yyyy-MM-dd'))!''}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">是否开启: </th>
				<td class="l-table-edit-td">
					[@u.radio name="show" list={'true': '是', 'false':'否'} value="${((m.show)?string('true', 'false'))!'false'}"/]
				</td>
			</tr>
		</table>
	</form>
</body>
</html>