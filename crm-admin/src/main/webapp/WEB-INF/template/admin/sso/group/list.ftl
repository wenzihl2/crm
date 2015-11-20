<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
var grid;
$(function () {
    grid = $("#maingrid").ligerGrid({
        columns: [
		{name: 'id', hide: true, width:10, isAllowHide: false},
		{display: '分组名称', name: 'name', align: 'left', width: 120 },
        {display: '类型', name: 'type', align: 'left', width: 80, i18n: 'GroupType'},
        {display: '是否默认分组', name: 'defaultGroup', align: 'left', width: 80, dict: 'common.is'},
        {display: '是否有效', name: 'show', align: 'left', width: 80, dict: 'common.is'}
        ],  pageSize:20, url: location.href,
		toolbar: {}, width: '100%', height:'100%'
    });
	$("#pageloading").hide();
	LG.loadToolbar(grid);
});
function deleteList(){
	LG.deleteList(grid, base + '/admin/sso/group/delete.jhtml');
}
//添加
function add(){
	top.f_addTab("group_add", '新增', base + '/admin/sso/group/create.jhtml');
}; 	
//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("group_edit", row.name + '-修改', base + '/admin/sso/group/' + row.id + '/update.jhtml');
	}
}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>