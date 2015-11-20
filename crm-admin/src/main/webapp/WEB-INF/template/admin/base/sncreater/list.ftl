<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
//设置为自动刷新
var grid;
$(function () {
    grid = $("#maingrid").ligerGrid({
        columns: [
		{name: 'id', hide: true, width:0, isAllowHide: false},
		{display: '编码', name: 'code', align: 'left', minWidth: 60 },
        {display: '名称', name: 'name', align: 'left', width: 120},
        {display: '前缀', name: 'qz', align: 'left', width: 60},
        {display: '启用日期', name: 'qyrq', align: 'left', width: 100, dict: 'common.is'},
        {display: '当前序号', name: 'dqxh', align: 'left', width: 100 },
        {display: '更新周期', name: 'gxzq', align: 'left', width: 100, i18n: 'UpdateCycle' },
        {display: '序号位数', name: 'ws', align: 'left', width: 100 },
        {display: '更新日期', name: 'modifyDate', align: 'left', width: 100, dateFormat:'yyyy-MM-dd'}
        ],  pageSize:20,
        url: 'queryData.jhtml',
		toolbar: {}, width: '100%', height:'100%',
		enabledSort: true
    });
	LG.loadToolbar(grid);
});
//添加
function add(){
	top.f_addTab("sncreater_add", '新增序号规则', base + '/admin/base/sncreater/add.jhtml');
}; 

//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("sncreater_edit", row.name + '-修改', base + '/admin/base/sncreater/' + row.id + '/edit.jhtml');			
	}
}

function deleteList(){
	LG.deleteList(grid, base + '/admin/base/sncreater/delete.jhtml');
}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>