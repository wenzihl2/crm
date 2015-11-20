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
			f_save(mainform, "icon");
		});
		
		$("input[id^=IconType]").click(function(){
			if($(this).val() == "css_class") {
				$(".css_class").show();
				$(".css_sprite").hide();
				$(".upload_file").hide();
				$(".file").hide();
			} else if($(this).val() == "css_sprite") {
				$(".css_sprite").show();
				$(".css_class").hide();
				$(".upload_file").hide();
				$(".file").hide();
			} else if($(this).val() == "upload_file") {
				$(".css_class").hide();
				$(".css_sprite").hide();
				$(".file").show();
				$(".upload_file").show();
			}
		});
		[#if isEdit??]
			$("input[id^=IconType]").attr("disabled", true);
		[/#if]
		
		$("input[value=${(m.type)!'upload_file'}]").click();
		
	});
</script>
</head>
<body>
<div class="panel">
	<form action='[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]?BackURL=[@BackURL/]' method="post" id="mainform" enctype="multipart/form-data">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}" />
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td">标识符:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.identity)!''}"
					validate="{required: true,maxlength:50}" name="identity" placeholder="用于表示图标的唯一标识"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">大小:</th>
				<td class="l-table-edit-td">
					宽:<input type="text" value="${(m.width)!'13'}" validate="{required: true}" name="width"/>
					高:<input type="text" value="${(m.height)!'13'}" validate="{required: true}" name="height"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">其他css属性:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.style)!''}" name="style"/></td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">分组类型:</th>
		        <td class="l-table-edit-td inline-radio">
					[@u.radio_map name="type" list=EnumUtils.enumProp2I18nMap("IconType")
						value="${(m.type)!'upload_file'}" id="IconType"/]
		        </td>
			</tr>
			
			[#if isEdit??]
			<tr class="upload_file" style="display:none;">
				<th class="l-table-edit-td">图标文件:</th>
				<td class="l-table-edit-td"><input type="text" name="imgSrc" value="${(m.imgSrc)!''}"/></td>
			</tr>
			[#else]
			<tr class="file" style="display:none;">
				<th class="l-table-edit-td">图标文件:</th>
				<td class="l-table-edit-td"><input type="file" name="file"/></td>
			</tr>
			[/#if]
			<tr class="css_class" style="display:none;">
				<th class="l-table-edit-td">css类:</th>
				<td class="l-table-edit-td"><input type="text" name="cssClass" value="${(m.cssClass)!''}"/></td>
			</tr>
			<tr class="css_sprite" style="display:none;">
				<th class="l-table-edit-td">图标文件:</th>
				<td class="l-table-edit-td"><input type="text" name="spriteSrc" value="${(m.spriteSrc)!''}"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">描述:</th>
				<td class="l-table-edit-td"><input type="text" value="${(m.description)!''}" name="description"/></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>