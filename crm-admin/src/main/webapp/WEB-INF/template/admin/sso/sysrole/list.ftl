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
		{display: '角色名称', name: 'name', align: 'left', width: 100 },
		{display: '系统内置', name: 'isSystem', align: 'center', dict:'common', width: 60 },
        {display: '描述', name: 'description', align: 'center', width: 120},
        {display: '创建时间', name: 'createDate', align: 'center', width: 200, dateFormat:'yyyy-MM-dd'}
        ],  pageSize:20, url: location.href,
		toolbar: {}, width: '100%', height:'100%'
    });
	$("#pageloading").hide();
	LG.loadToolbar(grid);
});
function deleteList(){
	LG.deleteList(grid, base + '/admin/sso/role/batch/delete.jhtml');
}
//添加
function add(){
	top.f_addTab(null, '新增角色', base + '/admin/sso/sysrole/create.jhtml');
}; 	
//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("role_edit", row.name + '-修改', base + '/admin/sso/sysrole/' + row.id + '/update.jhtml');
	}
}
var dialog = null;
function roleMenu(){
	var selected = grid.getSelected();
	if (!selected) { LG.tip('请选择行!'); return; }
	dialog = $.ligerDialog.open({title:"绑定菜单", height: 500,width:800,
		url:base + '/admin/sso/role/roleMenu.jhtml?isEdit=true&id=' + selected.id, 
		buttons: [ { text: '确定', onclick: function (item, dialog) { 
			var dosubmit = dialog.frame.document.getElementById("doSubmit");
			dosubmit.click(); 
		}}, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
	});
}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>