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
		mainform.ligerForm(); 
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "plugin");
		});
		jQuery.metadata.setType("attr", "validate"); 
 		LG.validate(mainform);
 		
 		$("#typeList").change(function() {
 			var pluginid = $(this).val();
 			pluginList(pluginid);
 			//var name = $("#pluginList option:selected").text();
 			//loadHtml("", pluginid, name);
 		});
 		/**
 		$("#pluginId select").live("change", function() {
 			var type = $("#typeList").val();
 			var pluginid = $("#pluginId select").val();
 			var name = $("#pluginId select option:selected").text();
 			loadHtml(type, pluginid, name);
 		});**/
 		[#if isEdit??]
 		loadHtml('${m.type}','${m.id}', '${m.name}');
 		[/#if]
	});
	function pluginList(pluginType){
		$.ajax({
			url: base + '/admin/base/plugin/pluginList.jhtml',
			type: "post",
			data: {type: pluginType},
			dataType: "json",
			beforeSend: function() {
				//self.attributeTable.empty();
			},
			success: function(result) {
				var optionHtml = '<select name="id"><option value="">请选择...<\/option>';
				$.each(result, function(j, option) {
					optionHtml += '<option value="' + option.id + '">' + option.name + '<\/option>';
				});
				optionHtml += '</select>';
				$("#pluginId").append(optionHtml);
				$("#pluginId select").ligerComboBox({
					onSelected: function (newValue, newText) {
	                    var type = $("#typeList").val();
			 			loadHtml(type, newValue, newText);
	                }
                });
			}
		});
	}
	function loadHtml(type, pluginid, name) {
		if (pluginid && pluginid != '') {
			$.ajax({
				url : base + '/admin/base/plugin/getPluginInstallHtml.jhtml',
				data: {pluginId: pluginid, type: type},
				dataType: 'html',
				type: 'post',
				success : function(html) {
					//cleanTr();
					$("#pluginTable tr[id=pluginNameTr]").after(html);
					$("#pluginName").val(name);
					
					$("#pluginTable td:not('inline-radio')").children("input").ligerTextBox();
					$("#pluginTable td:not('inline-radio')").children("select").ligerComboBox();
				},
				error : function() {
					$.Loading.error("出错了");
				}
			});
		}
	}
</script>
</head>
<body>
<form id="mainform" name="mainform" action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" class="validate">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" id="pluginTable">
		<tr>
			<th class="l-table-edit-td">插件类型：</th>
			<td class="l-table-edit-td">
				[@u.select_map headerKey="" headerValue="请选择" id="typeList"
					headerButtom="false" list=EnumUtils.enumProp2I18nMap("PluginType") value="${(m.type)!''}"
					name="type"/]
			</td>
		</tr>
		<tr>
	        <th class="l-table-edit-td">插件</th>
	        <td class="l-table-edit-td inline-radio" id="pluginId">
	        </td>
		</tr>
		<tr id="pluginNameTr">
			<th class="l-table-edit-td">插件名称：</th>
			<td class="l-table-edit-td">
				<input type="text" name="name" value="${(m.name)!''}" id="pluginName"
					class="formText {required: true, minlength: 2, maxlength: 20}" />
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">介绍:</th>
			<td class="l-table-edit-td">
				<textarea name="description"/>${(m.description)!''}</textarea>
			</td>
		</tr>
	</table>
</form>
</body>
</html>