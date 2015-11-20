<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	var form = null;
	$(function(){
		var mainform = $("#mainform");
    	form = mainform.ligerForm();  
    	
    	// Tab效果
		tabs = $("ul.nav-tabs").tabs("#tabcontainer>div", {
			tabs: "li"
		});
		
		LG.initIconList($("#icon"));
    	[#--
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "s_user_list");
		});
		--]
	});
</script>
</head>
<body>
[#include "/admin/sso/dept/nav.ftl"]
<div id="tabcontainer" style="margin-bottom:50px;">
<form action="update.jhtml" method="post" id="mainform">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<input type="hidden" name="id" value="${m.id}">
		<input type="hidden" name="parentId" value="${m.parentId}">
		<input type="hidden" name="parentIds" value="${m.parentIds}">
		<input type="hidden" name="priority" value="${m.priority}">
		<tr>
			<th class="l-table-edit-td">部门名称:</th>
			<td class="l-table-edit-td"><input type="text" validate="{required: true,maxlength:50}" value="${(m.name)!''}" 
				name="name"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">图标:</th>
			<td class="l-table-edit-td"><input type="text" value="${(m.icon)!''}" name="icon" id="icon"/></td>
		</tr>
		<tr>
			<th class="l-table-edit-td">是否启用：</th>
			<td class="l-table-edit-td">[@u.radio name="show" list={"true": "是", "false": "否"} value="${(m.show)!'true'}"/]
			</td>
		</tr>
	</table>
	[#include "/admin/include/form-bar.ftl"]
</form>
</div>
</body>
</html>