<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		form = mainform.ligerForm();
	});
</script>
</head>
<body>
[#include "/admin/sso/dept/nav.ftl"]
<div class="panel">
	<form action='move.jhtml' method="post" id="mainform">
		<input type="hidden" id="moveType" name="moveType">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td" style="width:100px;">源节点:</th>
				<td class="l-table-edit-td">
					<i class="${source.icon}"></i>
					${source.name}
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">目标节点:</th>
				<td class="l-table-edit-td">
					<input type="text" ltype="select" validate="{required: true}" options='{
		                width: 180,
		                selectBoxWidth: 200,
		                selectBoxHeight: 200, valueField: "id", textField: "name", valueFieldID:"targetId",
		                treeLeafOnly:false,
		                tree: { url: base + "/admin/sso/dept/ajax/load.jhtml?asyncLoadAll=true", checkbox: false,
		            	parentIDFieldName :"pId", textFieldName: "name"}}'>
				</td>
			</tr>
		</table>
		[#include "/admin/sso/dept/form-bar.ftl"]
	</form>
</div>
</body>
</html>