<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
	/**
	 * 新增账号
	 */
	function add(){
		top.f_addTab(null, '新增账号', base + '/admin/sso/manager/add.jhtml');
	}
	function edit(){
		var row = LG.getSelected(grid);
		top.f_addTab(null, '修改管理员', base + '/admin/sso/manager/' + row.id + '/update.jhtml');
	}
	function addRole(){
		var row = LG.getSelected(grid);
		$.ligerDialog.open({title:"绑定角色", height: 500,width:800,url: base + '/admin/sso/manager/addRole.jhtml?s_user.id=' + row.id});
	}
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '所属企业', name: 'company.name', align: 'left', width: 220 },
			{display: '登陆账号', name: 'username', align: 'left', width: 120 },
			{display: '姓名', name: 'real_name', align: 'left', width: 100 },
            {display: '角色名称', name: 'roleNames', align: 'left', width: 250 },
			{display: '是否删除', name: 'deleted', align: 'center', width: 80, i18n:"deleted" }
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
  	
  	function deleteList(){
		LG.deleteList(grid, base + '/admin/sso/manager/batch/delete.jhtml');
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>