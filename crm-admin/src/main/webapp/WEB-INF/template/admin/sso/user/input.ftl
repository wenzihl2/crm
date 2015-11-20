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
		f_save(mainform, "s_user_list");
	});
	
	mainform.validationEngine('attach', { 
		validationEventTriggers:"keyup blur",
		promptPosition: 'bottomRight'
	});
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
			<th class="l-table-edit-td">登陆账号：</th>
			<td class="l-table-edit-td">
				<input type="text" class="validate[required]" 
					name="username" value="${(s_user.username)!''}" placeholder="5到20个汉字、字母、数字或下划线"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">邮箱：</th>
			<td class="l-table-edit-td">
				<input type="text" class="validate[required]" 
					name="email" value="${(s_user.email)!''}" placeholder="如zhang@163.com"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">手机号：</th>
			<td class="l-table-edit-td">
				<input type="text" class="validate[required]" 
					name="mobilePhoneNumber" value="${(s_user.mobilePhoneNumber)!''}" placeholder="如13512345678"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">部门：</th>
			<td class="l-table-edit-td">
				<input type="text" ltype="select" options='{
	                initValue: "${(s_user.dept.id)!}",
	                initText: "${(s_user.dept.name)!}",
	                width: 180,
	                selectBoxWidth: 200,
	                selectBoxHeight: 200, valueField: "id", textField: "name", valueFieldID:"sysDept.id", treeLeafOnly:false, 
	                tree: { url: base + "/admin/sso/dept/ajax/load.jhtml", checkbox: false,
	            	parentIDFieldName :"pid", textFieldName: "name"}}'>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">登陆密码：</th>
			<td class="l-table-edit-td"><input type="password" name="password" id="password" placeholder="请输入至少5位的初始密码"/></td>
		</tr>
		<tr>
			<th class="l-table-edit-td">重复密码：</th>
			<td class="l-table-edit-td"><input type="password" name="repassword"
			 	validate="{equalTo: '#password', messages:{equalTo: '重复密码不一致'}}"/></td>
		</tr>
		<tr>
	        <th class="l-table-edit-td">是否管理员:</th>
	        <td class="l-table-edit-td inline-radio">[@u.radio name="admin" list={"true": "是", "false": "否"} 
				value="${(m.admin?string('true', 'false'))!'true'}"/]
	        </td>
		</tr>
	</table>
</form>
</body>
</html>