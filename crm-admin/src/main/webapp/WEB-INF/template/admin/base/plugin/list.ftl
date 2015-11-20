<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	var grid;
	$(function(){
		 grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '名称', name: 'name', align: 'left', width: 200 },
			{display: '插件类型', name: 'type', align: 'left', width: 100, i18n: 'PluginType'},
			{display: '启用状态', name: 'isEnabled', align: 'center', width: 100, 
				render: function(row){
					if(row.enableState == '1'){
						return '已启用';
					}else{
						return '未启用';
					}
				}
			},
			{display: '操作', isAllowHide: false, 
				render: function (row){
					var html;
					if(row.enableState != '1'){
						html= '<a href="#" onclick="operate(1,\'' + row.id + '\');return false;">安装</a>';
					}else if(row.enableState != '1'){
						html= '<a href="#" onclick="operate(2,\'' + row.id + '\');return false;">启用</a>&nbsp;'
							+ '<a href="#" onclick="operate(3,\'' + row.id + '\');return false;">卸载</a>';
					}else if(row.installState == '1' && row.enableState == '1'){
						html= '<a href="#" onclick="operate(4,\'' + row.id + '\');return false;">停用</a>';
					}
					return html;
                }
			}],  pageSize:20, url: location.href, checkbox:false,
			groupColumnName:'type', groupColumnDisplay:'类型',
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
	});
	
	function operate(type,id){
		var path;
		var msg;
		if(type == '1'){
			path = base + '/admin/component/install.jhtml?id=' + id;
			msg = '安装';
		}else if(type == '2'){
			path = base + '/admin/component/start.jhtml?id=' + id;
			msg = '启用';
		}else if(type == '3'){
			path = base + '/admin/component/unInstall.jhtml?id=' + id;
			msg = '卸载';
		}else if(type == '4'){
			path = base + '/admin/component/stop.jhtml?id=' + id;
			msg = '停用';
		}
		LG.ajax({
              url: path,
              loading: '正在' + msg + '中...',
              data: { },
              success: function () {
                  LG.showSuccess(msg + '成功');
                  f_reload();
              },
              error: function (message) {
                  LG.showError(message);
              }
          });
	}
	//添加
	function add(){
		top.f_addTab("plugin_add", '新增', base + '/admin/base/plugin/create.jhtml');
	}; 	
	//修改
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("plugin_edit", row.name + '-修改', base + '/admin/base/plugin/' + row.id + '/update.jhtml');
		}
	}
	function deleteList(){
		var row = LG.getSelected(grid);
		if(row){
			LG.deleteList(grid, base + '/admin/base/plugin/delete.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid"></div>
</body>
</html>