<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '账号', name: 'username', align: 'left', width: 120 },
			{display: '姓名', name: 'name', align: 'left', width: 100 },
            {display: '部门', name: 'dept.name', align: 'left', width: 150 },
            {display: '角色名称', name: 'roleNames', align: 'left', width: 250 }
			],  pageSize:20, url: base + "/admin/sso/user.jhtml",
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
    });
  	
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>