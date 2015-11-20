<!doctype html>
<html>
<head>
[#if !dictionary??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
[#include "/admin/include/head.htm"]
<script type="text/javascript">
// 创建表单结构
var mainform = null;
$(function () { 
	var mainform = $("#mainform");
    form = mainform.ligerForm();
	//表单底部按钮 
    jQuery.metadata.setType("attr", "validate"); 
   	LG.validate(mainform);
	LG.setFormDefaultBtn(f_cancel, function(){
		f_save(mainform, "dictionary");
	});
	
	var currentId = null;
	$("#type").change(function() {
		$.ligerui.remove("parentId"); // 移除ligerui管理器
		var _this = $(this);
		var type = _this.val();
		if(type != "") {
			$("#parent td").html("<input type='text' id='parentId'/>");
			$("#parentId").ligerComboBox({
					initValue: "${(dictionary.parent.id)!''}",
	        		initText: "${(dictionary.parent.name)!''}",
	                width: 180,
	                selectBoxWidth: 200,
	                selectBoxHeight: 200, valueField: 'id', textField: 'name', valueFieldID:'parentId',treeLeafOnly:false,
	                tree: { url: base + '/admin/base/dictionary/' + type + '/tree.jhtml', checkbox: false,
	            	parentIDFieldName :'pid', textFieldName: 'name'}
	        });
	        $("#parent").show();
		} else {
			$("#parent").hide();
		}
	})[#if isEdit??].trigger("change");[/#if];
});
</script>
</head>
<body style="padding:10px;">
<form id="mainform" action="[#if isAdd??]create.jhtml[#else]edit.jhtml[/#if]" method="post">
<div id="tabcontainer" style="margin-bottom:50px;">
	[#if !isAdd??]
		<input type="hidden" name="id" value="${(dictionary.id)!''}" />
	[/#if]
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<th class="l-table-edit-td">名称：</th>
			<td class="l-table-edit-td">
				<input type="text" name="name" validate="{required: true}" value="${(dictionary.name)!''}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">类别：</th>
			<td class="l-table-edit-td">
			[@u.select_map headerKey="" headerValue="顶级分类" id="type" value="${(dictionary.type)!''}"
					headerButtom="false" list=EnumUtils.enumProp2I18nMap("DictionaryType") name="type" validate="{required: true}"/]
			</td>
		</tr>
		<tr id="parent" style="display:none;">
			<th class="l-table-edit-td">父类别：</th>
			<td class="l-table-edit-td">
			</td>
		</tr>
		<tr>
			<td class="l-table-edit-td">排序：</td>
			<td class="l-table-edit-td">
				<input type="text" name="priority" class="{number: true}" value="${(dictionary.priority)!''}" />
			</td>
		</tr>
		<tr>
			<td class="l-table-edit-td">编码：</td>
			<td class="l-table-edit-td">
				<input type="text" name="code" value="${(dictionary.code)!''}" />
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>