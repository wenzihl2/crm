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
[#include "/admin/sso/resource/nav.ftl"]
<div class="panel">
	<form action='[#if op == '删除']delete.jhtml[#else]edit.jhtml[/#if]' method="post" id="mainform">
		<input type="hidden" name="id" value="${m.id}">
		<input type="hidden" name="parentId" value="${m.parentId}">
		<input type="hidden" name="parentIds" value="${m.parentIds}">
		<input type="hidden" name="priority" value="${m.priority}">
		
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td" style="width:100px;">名称:</th>
				<td class="l-table-edit-td"><input type="text" validate="{required: true,maxlength:50}" value="${(m.name)!''}" 
					name="name" placeholder="小于50个字符"/>
				</td>
			</tr>
			<tr>
		    	<th class="l-table-edit-td">资源标识:</th>
		        <td class="l-table-edit-td"><input type="text" name="identity" validate="{username:true, maxlength:200, remote: '${base}/admin/sso/menu/checkMenuNo.jhtml?oldValue=${(menu.menuNo)!''}', messages: {remote: '菜单编号!'}}"
					value="${(m.identity)!''}" placeholder="用于权限验证"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">URL地址:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.url)!''}" name="url" placeholder="菜单跳转地址"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">图标:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.icon)!''}" name="icon" id="icon"/></td>
			</tr>
			<tr>
	        	<th class="l-table-edit-td">HTML:</th>
	        	<td class="l-table-edit-td"><textarea name="html" style="width:300px;height:56px">${(m.html)!''}</textarea></td>
	    	</tr>
	    	<tr>
		        <th class="l-table-edit-td">资源类型:</th>
		        <td class="l-table-edit-td inline-radio">[@u.radio_map name="type" list=EnumUtils.enumProp2I18nMap("ResourceType")
					value="${(m.type)!'Menu'}"/]
		        </td>
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