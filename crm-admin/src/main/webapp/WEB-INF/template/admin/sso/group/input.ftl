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
			f_save(mainform, "group");
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
					<th class="l-table-edit-td">名称:</th>
					<td class="l-table-edit-td"><input type="text" validate="{required: true ,maxlength: 20}" value="${(m.name)!''}" name="name"/></td>
				</tr>
				<tr>
			        <th class="l-table-edit-td">分组类型:</th>
			        <td class="l-table-edit-td inline-radio">
						[@u.radio_map name="type" list=EnumUtils.enumProp2I18nMap("GroupType")
							value="${(m.type)!'user'}"/]
			        </td>
				</tr>
				<tr>
			        <th class="l-table-edit-td">是否默认:</th>
			        <td class="l-table-edit-td inline-radio">[@u.radio name="defaultGroup" list={"true": "是", "false": "否"} 
						value="${(m.defaultGroup?string('true', 'false'))!'false'}"/]
			        </td>
				</tr>
				<tr>
			        <th class="l-table-edit-td">是否有效:</th>
			        <td class="l-table-edit-td inline-radio">[@u.radio name="show" list={"true": "是", "false": "否"} 
						value="${(m.show?string('true', 'false'))!'false'}"/]
			        </td>
				</tr>
			</table>
		</div>
	</form>
</body>   
</html>