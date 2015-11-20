<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
[#include "/admin/include/head.htm"]
<script type="text/javascript">
var mainform = null, form;
$(function(){
	var mainform = $("#mainform");
	form = mainform.ligerForm({inputWidth: 170, labelWidth: 50, space: 20, rightToken: ""});
	//表单底部按钮 
	LG.setFormDefaultBtn(f_cancel, function(){
		f_save(mainform, "table_list");
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
<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform">
<div id="tabcontainer" style="margin-bottom:50px;">
	[#if !isAdd??]
		<input type="hidden" name="id" value="${(m.id)!''}" />
	[/#if]
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<th class="l-table-edit-td">表名：</th>
			<td class="l-table-edit-td">
				<input type="text" name="name" class="{required: true, username: true}" value="${(m.name)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">关键词：</th>
			<td class="l-table-edit-td">
				<input type="text" name="keyword" class="{required: true}" value="${(m.keyword)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">类路径：</th>
			<td class="l-table-edit-td">
				<input type="text" name="classPath" class="{required: true}" value="${(m.classPath)!''}" options="{width: 400}"/>
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>