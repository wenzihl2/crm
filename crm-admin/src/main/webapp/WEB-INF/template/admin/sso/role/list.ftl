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
		{display: '角色名称', name: 'name', align: 'left', width: 200 },
		{display: '角色标识', name: 'value', align: 'left', width: 100 },
        {display: '描述', name: 'description', align: 'left', width: 220},
        {display: '状态', name: 'show', align: 'left', width: 80, dict: 'common.is'}
        ],  pageSize:20, url: location.href,
		toolbar: {}, width: '100%', height:'100%'
    });
	$("#pageloading").hide();
	LG.loadToolbar(grid);
});

//添加
function add(){
	top.f_addTab(null, '新增角色', base + '/admin/sso/role/create.jhtml');
}; 	

//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("role_edit", row.name + '-修改', base + '/admin/sso/role/' + row.id + '/update.jhtml');
	}
}
function deleteList(){
	LG.deleteList(grid, base + '/admin/sso/role/batch/delete.jhtml');
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