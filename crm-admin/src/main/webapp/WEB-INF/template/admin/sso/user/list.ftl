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
		top.f_addTab('user_add', '新增账号', base + '/admin/sso/user/add.jhtml');
	}
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab('user_edit', '修改管理员', base + '/admin/sso/user/' + row.id + '/update.jhtml');
		}
	}
	
	function addRole(){
		var row = LG.getSelected(grid);
		if(row){
			$.ligerDialog.open({title:"绑定角色", height: 500, width:800,url: base + '/admin/sso/user/addRole.jhtml?s_user.id=' + row.id});
		}
	}
	
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '登陆账号', name: 'username', align: 'left', width: 120 },
			{display: '姓名', name: 'name', align: 'left', width: 100 },
            {display: '部门', name: 'dept.name', align: 'left', width: 150 },
            {display: '角色名称', name: 'roleNames', align: 'left', width: 250 },
			{display: '使用状态', name: 'status', align: 'left', width: 100, i18n: "UserStatus"}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
  	
  	function deleteList(){
		LG.deleteList(grid, base + '/admin/sso/user/batch/delete.jhtml');
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>