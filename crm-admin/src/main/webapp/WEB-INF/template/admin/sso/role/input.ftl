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
			f_save(mainform, "role");
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
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<th class="l-table-edit-td">角色名称:</th>
				<td class="l-table-edit-td"><input type="text" validate="{required: true, maxlength: 20}" 
					value="${(m.name)!''}" name="name" placeholder="角色描述名"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">角色标识:</th>
				<td class="l-table-edit-td">
					<input type="text" name="value" validate="{required: true, minlength: 6,maxlength: 20}" 
						value="${(m.value)!''}" placeholder="程序中使用的名称"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">描述:</th>
				<td class="l-table-edit-td"><input type="text" name="description" validate="{maxlength:127}" value="${(m.description)!}" />
				</td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">是否显示:</th>
		        <td class="l-table-edit-td inline-radio">[@u.radio name="show" list={"true": "是", "false": "否"} 
					value="${(m.show?string('true', 'false'))!'true'}"/]
		        </td>
			</tr>										
		</table>
	</form>
</body>   
</html>