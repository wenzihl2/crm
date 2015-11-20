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
			f_save(mainform, "role_list");
		});
		jQuery.metadata.setType("attr", "validate"); 
 		LG.validate(mainform);
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
		[#if !isAdd??]
		<input type="hidden" name="id" value="${m.id}">
		[/#if]
		<div id="tabcontainer" style="margin-bottom:50px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
					<th class="l-table-edit-td">角色名称:</th>
					<td class="l-table-edit-td"><input type="text" validate="{required: true, remote: '[#if isEdit??]../[/#if]checkName.jhtml?oldValue=${(m.name)!}', messages: {remote: '角色名称已存在!'},maxlength: 20}" value="${(m.name)!''}" name="name"/></td>
				</tr>
				<tr>
					<th class="l-table-edit-td">角色标识:</th>
					<td class="l-table-edit-td">
						<input type="text" name="value" validate="{required: true, minlength: 6,maxlength: 20, prefix: 'ROLE_', remote: '[#if isEdit??]../[/#if]checkValue.jhtml?oldValue=${(role.value)!}', messages: {remote: '角色标识已存在!'}}" 
							value="${(m.value)!'ROLE_'}" title="角色标识长度不能小于6,且必须以ROLE_开头" />
					</td>
				</tr>
				<tr>
					<th class="l-table-edit-td">描述:</th>
					<td class="l-table-edit-td"><input type="text" name="description" validate="{maxlength:127}" value="${(m.description)!}" />
					</td>
				</tr>										
			</table>
		</div>
	</form>
</body>   
</html>