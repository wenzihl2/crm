<!doctype html>
<html>
<head>
<#include "/admin/include/head.htm">
<script type="text/javascript">
 var grid;
 $(function () {
    grid = $("#maingrid").ligerGrid({
        columns: [
		{display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
		{display: '企业名称', name: 'name', align: 'left', width: 220 },
		{display: '所属行业', name: "industry.name", align: 'left', width: 120 },
        {display: '联系电话', name: 'telephone', align: 'left', width: 250 }
		],  pageSize:20, url: 'queryData.jhtml',
		toolbar: {}, width: '100%', height:'100%', enabledSort: true
    });
    $("#pageloading").hide();
	LG.loadToolbar(grid);
	
	//绑定搜索按钮
	//LG.search(grid);
});
//添加
function add(){
	top.f_addTab("company_add", '新增企业', base + '/admin/sso/sysparame/add.jhtml');
};

//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("company_edit", row.name + '-修改', base + '/admin/sso/sysparame/edit.jhtml?id=' + row.id);			
	}
}

function deleteList(){
	LG.deleteList(grid, base + '/admin/sso/sysparame/delete.jhtml');
}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>