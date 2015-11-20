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
		form = mainform.ligerForm();
		//表单底部按钮 
	    jQuery.metadata.setType("attr", "validate"); 
	   	LG.validate(mainform);
		LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "template");
		});
		
		$("input[value=${(m.type)!'email'}]").click();
		
	});
</script>
</head>
<body>
<div class="panel">
	<form action='[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]?BackURL=[@BackURL/]' method="post" id="mainform" enctype="multipart/form-data">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}" />
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="width: 100%;">
			<tr>
				<th class="l-table-edit-td">模板名称:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.name)!''}"
					validate="{required: true,maxlength:50}" name="name"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">标识符:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.identity)!''}"
					validate="{required: true,maxlength:50}" name="identity" placeholder="用于表示图标的唯一标识"/>
				</td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">模板类型:</th>
		        <td class="l-table-edit-td inline-radio">
					[@u.radio_map name="type" list=EnumUtils.enumProp2I18nMap("TemplateType")
						value="${(m.type)!''}" id="TemplateType"/]
		        </td>
			</tr>
			<tr>
				<th class="l-table-edit-td">描述:</th>
				<td class="l-table-edit-td"><textarea name="content" class="editor" style="width: 100%;height:500px;">${(m.content)!''}</textarea></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>