<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false}
			],  pageSize:20, url: 'queryData.jhtml',
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		//LG.search(grid);
    });
	
	//添加
	function add(){
		top.f_addTab("dept_add", '新增', base + '/admin/sso/dept/add.jhtml');
	}; 
	
	//修改
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("dept_edit", row.name + '-修改', base + '/admin/sso/dept/edit.jhtml?id=' + row.id);			
		}
	}
	
	function deleteList(){
		LG.deleteList(grid, base + '/admin/sso/dept/delete.jhtml');
	}	
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>