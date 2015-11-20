<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		LG.initIconList($("#icon"));
		
		[#if op == '删除']
			form = mainform.ligerForm({readonly:true});
        [#elseif op == '查看']
        	form = mainform.ligerForm({readonly:true});
        [#else]
        	form = mainform.ligerForm();
        [/#if]
	});
</script>
</head>
<body>
[#include "/admin/sso/dept/nav.ftl"]
<div class="panel">
	<form action='edit.jhtml' method="post" id="mainform">
		<input type="hidden" name="id" value="${m.id}">
		<input type="hidden" name="parentId" value="${m.parentId}">
		<input type="hidden" name="parentIds" value="${m.parentIds}">
		<input type="hidden" name="priority" value="${m.priority}">
		
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td">名称:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.name)!''}"
					validate="{required: true,maxlength:50}" name="name" placeholder="小于50个字符"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">图标:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.icon)!''}" name="icon" id="icon"/></td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">是否显示:</th>
		        <td class="l-table-edit-td inline-radio">[@u.radio name="show" list={"true": "是", "false": "否"} 
					value="${(m.show?string('true', 'false'))!'false'}"/]
		        </td>
			</tr>
		</table>
		[#include "/admin/sso/resource/form-bar.ftl"]
	</form>
</div>
</body>
</html>