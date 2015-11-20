<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<#include "/admin/include/head.htm">
<script type="text/javascript">
	var grid;
	$(function(){
		 grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '插件类型', name: 'name', align: 'left', width: 200 },
			{display: '插件桩ID', name: 'bundleId', align: 'center', minWidth: 200},
			{display: '操作', isAllowHide: false,
				render : function(row){
					var html = '<a href="#" onclick="deleteBundle(\'' + row.id + '\');return false;">删除</a>';
					return html;
				}
			}],  pageSize:20, url: '${base}/admin/component/queryBundleData.jhtml',checkbox:false,
			toolbar:  null, width: '100%', height:'100%', enabledSort: true
        });
        //双击事件(id:为点击操作的id)
      	LG.setGridDoubleClick(grid, 445);
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
	});
	
	function deleteBundle(id){
		LG.ajax({
          url: base + '/admin/component/deleteBundle.jhtml',
          loading: '正在删除中...',
          data: { ids: [id] },
          success: function () {
              LG.showSuccess('删除成功');
              f_reload();
          },
          error: function (message) {
              LG.showError(message);
          }
      });
	}
</script>
</head>
<body style="padding:10px;">
	<div id="tabcontainer" style="margin:0; padding:0">
		<form id="mainform" name="mainform" class="validate" action="saveBundle.jhtml" method="post" class="validate">
			<table class="table">
				<tr>
					<th>插件类型名称：</th>
					<td>
						<input type="text" name="name" value="${(componentBundle.name)!''}" 
							class="formText {required: true, minlength: 2, maxlength: 50, remote: 'checkBundleName.jhtml?oldValue=${(componentBundle.name)!}', messages: {remote: '插件类型名称已存在!'}}" />
					</td>
					<td>&nbsp;</td>
					<th>插件桩ID：</th>
					<td>
						<input type="text" name="bundleId" value="${(componentBundle.bundleId)!''}" 
							class="formText {required: true, maxlength: 50}" />
					</td>
					<td>&nbsp;</td>
					<td>
						<input type="submit" value="新增"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>