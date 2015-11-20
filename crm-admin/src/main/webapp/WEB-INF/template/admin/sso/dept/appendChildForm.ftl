<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	var form = null;
	$(function(){
		var mainform = $("#mainform");
    	form = mainform.ligerForm();  
	});
</script>
</head>
<body>
[#include "/admin/sso/dept/nav.ftl"]
<div class="panel">
<form action="appendChild.jhtml" method="post" id="mainform">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<th class="l-table-edit-td" style="width:100px;">父节点名称:</th>
			<td class="l-table-edit-td">
				<i class="${parent.icon}"></i>
				${parent.name}
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">子节点名称:</th>
			<td class="l-table-edit-td"><input type="text" validate="{required: true,maxlength:50}" name="name"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">图标:</th>
			<td class="l-table-edit-td"><input type="text" name="icon"/></td>
		</tr>
		<tr>
	        <th class="l-table-edit-td">是否显示:</th>
	        <td class="l-table-edit-td inline-radio">[@u.radio name="show" list={"true": "是", "false": "否"} 
				value="false"/]
	        </td>
		</tr>
	</table>
	[#include "/admin/sso/dept/form-bar.ftl"]
</form>
</div>
</body>
</html>