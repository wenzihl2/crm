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
		f_save(mainform, "manager_list");
	});
	jQuery.metadata.setType("attr", "validate"); 
	LG.validate(mainform);
});
</script>
</head>
[#if !s_user??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
<body style="padding:10px;">
<form action="[#if isAdd??]add.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform">
	[#if !isAdd??]
		<input type="hidden" name="id" value="${(s_user.id)!''}"/>
	[/#if]
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<th class="l-table-edit-td">所属企业：</th>
			<td class="l-table-edit-td" style="width:180px;">
				<input type="text" ltype="autocomplete" id="customer" validate="{required: true}" value='${(s_user.company.name)!}' options="{
						 initValue: '${(s_user.company.id)!}',
	        			 initText: '${(s_user.company.name)!}',
						 width: 150,
		                 selectBoxWidth: 200,
		                 selectBoxHeight: 200,
		                 valueField: 'id', textField: 'name', valueFieldID: 'company_id',
		                 url: base + '/admin/sso/sysparame/search.jhtml'
	        	}"/>
			</td>
		<tr>
			<th class="l-table-edit-td">登陆账号：</th>
			<td class="l-table-edit-td">
				[#if isAdd??]
				<input type="text" validate="{required: true, remote: 'checkUsername.jhtml?oldValue=${(s_user.username)!}', messages: {remote: '登陆账号已存在!'}}" 
					name="username" value="${(s_user.username)!''}" />
				[#else]
					${(s_user.username)!''}
				[/#if]
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">登陆密码：</th>
			<td class="l-table-edit-td"><input type="password" name="password" id="password"/></td>
		</tr>
		<tr>
			<th class="l-table-edit-td">重复密码：</th>
			<td class="l-table-edit-td"><input type="password" name="repassword"
			 	validate="{equalTo: '#password', messages:{equalTo: '重复密码不一致'}}"/></td>
		</tr>
		<tr>
			<th class="l-table-edit-td">角色：</th>
			<td class="l-table-edit-td">
				[#if isEdit?? && allRoles??]
					[@u.checkboxlist listKey="id" listValue="name" name="roleIds" list=allRoles valueList=s_user.roleListIds/]
				[#elseif allRoles??]
					[@u.checkboxlist listKey="id" listValue="name" name="roleIds" list=allRoles/]
				[/#if]
			</td>
		</tr>
	</table>
</form>
</body>
</html>