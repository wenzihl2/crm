<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	//设置为自动刷新
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '表名称', name: 'name', align: 'left', width: 150},
			{display: '关键词', name: 'keyword', align: 'left', width: 250},
            {display: '类路径', name: 'classPath', align: 'left', width: 320}
            ],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
		LG.loadToolbar(grid);
    });
    
    //添加
	function add(){
		top.f_addTab("table_add", '新增表', base + '/admin/base/table/create.jhtml');
	};
	
	//字段
	function fieldList(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("table_fieldList", '新增表单列表', base + '/admin/base/table/' + row.id + '/fieldList.jhtml');
		}
	}; 
	
	//修改
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("table_edit", row.name + '-修改', base + '/admin/base/table/' + row.id + '/update.jhtml');			
		}
	}
	
	function deleteList(){
		LG.deleteList(grid, base + '/admin/base/table/delete.jhtml');
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>