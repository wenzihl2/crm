<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm" /]
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
	LG.setFormDefaultBtn(f_cancel, function(){
		f_save(mainform, "navigation");
	});
	
	
	var currentId = null;
	$("#position").change(function() {
		$.ligerui.remove("parentId"); // 移除ligerui管理器
		var _this = $(this);
		var type = _this.val();
		if(type != "") {
			$("#parent td").html("<input type='text' id='parentId'/>");
			$("#parentId").ligerComboBox({
					initValue: "${(parent.id)!''}",
	        		initText: "${(parent.name)!''}",
	                width: 180,
	                selectBoxWidth: 200,
	                selectBoxHeight: 200, valueField: 'id', textField: 'name', valueFieldID:'parentId',treeLeafOnly:false,
	                tree: { url: base + '/admin/cms/navigation/' + type + '/tree.jhtml', checkbox: false,
	            	parentIDFieldName :'pId', textFieldName: 'name'}
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
	<form action="[#if isAdd??]create.jhtml[#else]edit.jhtml[/#if]" method="post" class="validate" id="mainform">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}"/>
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td">名称: </th>
				<td class="l-table-edit-td">
					<input type="text" name="name" class="{required: true,maxlength: 20}" value="${(m.name)!''}" />	 
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">链接地址: </th>
				<td class="l-table-edit-td">
					<input type="text" name="url" value="${(m.url)!''}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">位置:</th>
			    <td class="l-table-edit-td">
					[@u.select_map headerKey="" headerValue="请选择..." id="position" value="${(m.position)!''}"
							headerButtom="false" list=EnumUtils.enumProp2I18nMap("NavigationPosition") validate="{required: true}"
							name="position" options="{width: 80}"/]
				</td>
			</tr>
			<tr id="parent" style="display:none;">
				<th class="l-table-edit-td">父类别：</th>
				<td class="l-table-edit-td">
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">是否显示:</th>
				<td class="l-table-edit-td">
					[@u.radio_map name="status" list=EnumUtils.enumProp2I18nMap("ShowStatus") value="${(m.status)!'show'}"/]
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">在新窗口中打开:</th>
				<td class="l-table-edit-td">
					[@u.radio name="isBlankTarget" list={"true":"是", "false": "否"} value="${(m.isBlankTarget?string('true', 'false'))!'false'}"/]
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">是否热门:</th>
				<td class="l-table-edit-td">
					[@u.radio name="isHot" list={"true":"是", "false": "否"} value="${(m.isHot?string('true', 'false'))!'false'}"/]
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">排序:</th>
				<td class="l-table-edit-td">
					<input type="text" name="priority" value="${(m.priority)!''}" title="只允许输入零或正整数" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>