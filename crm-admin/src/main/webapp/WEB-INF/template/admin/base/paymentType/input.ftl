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
			f_save(mainform, "group");
		});
		jQuery.metadata.setType("attr", "validate"); 
 		LG.validate(mainform);
 		
 		$("#pluginList").change(function() {
 			var pluginid = $(this).val();
 			var name = $("#pluginList option:selected").text();
 			loadHtml("", pluginid, name);
 		});
 		[#if isEdit??]
 		loadHtml('${m.id}','${m.type}','${m.name}');
 		[/#if]
	});
	
	function loadHtml(paymentid, pluginid, name) {
		if (pluginid && pluginid != '') {
			$.ajax({
				url : base + '/admin/base/paymentType/getPluginInstallHtml.jhtml',
				data: {pluginId: pluginid, paymentId: paymentid},
				dataType: 'html',
				type: 'post',
				success : function(html) {
					//cleanTr();
					$("#pluginTable tr[id=paymentNameTr]").after(html);
					$("#paymentName").val(name);
					
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
<body style="padding:10px;">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform">
		[#if !isAdd??]
		<input type="hidden" name="id" value="${m.id}">
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" id="pluginTable">
			<tr>
				<th>插件类型：</th>
				<td>
					[@u.select headerKey="" headerValue="请选择"  listKey="id" listValue="name"
						headerButtom="false" list=bundleList value="${(component.componentBundle.id)!''}"
						name="componentBundle.id" class="{required: true}"/]
				</td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">支付方式</th>
		        <td class="l-table-edit-td inline-radio">
					[@u.select headerKey="" headerValue="请选择" listKey="id" id="pluginList" listValue="name" class="{required: true}"
										headerButtom="false" list=pluginList
										name="type" value="${(m.type)!''}"/]
		        </td>
			</tr>
			<tr id="paymentNameTr">
				<th class="l-table-edit-td">支付方式名称:</th>
				<td class="l-table-edit-td">
					<input type="text" validate="{required: true ,maxlength: 20}" id="paymentName" value="${(m.name)!''}" name="name"/>
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