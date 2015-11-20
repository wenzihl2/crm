<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		LG.initIconList($("#icon"));
		form = mainform.ligerForm();
	});
	
	function doSubmit(){
		LG.submitForm(null, function(data){
			window.parent.leftFrame.updateNode(data);
			LG.showSuccess("修改菜单成功!");
			$("form").clearForm();
		});
	}
</script>
</head>
<body>
[#include "/admin/sso/resource/nav.ftl"]
<div class="panel">
	<form action='appendChild.jhtml' method="post" id="mainform">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td" style="width:100px;">父节点名称:</th>
				<td class="l-table-edit-td">
					<i class="${parent.icon}"></i>
					${parent.name}
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td" style="width:100px;">子节点名称:</th>
				<td class="l-table-edit-td"><input type="text" validate="{required: true,maxlength:50}"
					name="name" placeholder="小于50个字符"/>
				</td>
			</tr>
			<tr>
		    	<th class="l-table-edit-td">资源标识:</th>
		        <td class="l-table-edit-td"><input type="text" name="identity" validate="{username:true, maxlength:200, remote: '${base}/admin/sso/menu/checkMenuNo.jhtml?oldValue=${(menu.menuNo)!''}', messages: {remote: '菜单编号!'}}"
					placeholder="用于权限验证"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">URL地址:</th>
				<td class="l-table-edit-td"><input type="text" name="url"/></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">图标:</th>
				<td class="l-table-edit-td"><input type="text" name="icon" id="icon"/></td>
			</tr>
			<tr>
	        	<th class="l-table-edit-td">HTML:</th>
	        	<td class="l-table-edit-td"><textarea name="html" style="width:300px;height:56px"></textarea></td>
	    	</tr>
	    	<tr>
		        <th class="l-table-edit-td">资源类型:</th>
		        <td class="l-table-edit-td inline-radio">[@u.radio_map name="type" list=EnumUtils.enumProp2I18nMap("ResourceType")
					value="${(m.type)!'Menu'}"/]
		        </td>
			</tr>
			<tr>
		        <th class="l-table-edit-td">是否显示:</th>
		        <td class="l-table-edit-td inline-radio">[@u.radio name="show" list={"true": "是", "false": "否"} 
					value="false"/]
		        </td>
			</tr>
		</table>
		[#include "/admin/sso/resource/form-bar.ftl"]
	</form>
</div>
</body>
</html>