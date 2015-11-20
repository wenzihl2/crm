<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
            {display: '编号', name: 'id', align: 'left', width: 80 },
			{display: '用户', name: 'username', align: 'left', width: 120 },
			{display: '状态', name: 'status.info', align: 'left', width: 100 },
            {display: '原因', name: 'reason', align: 'left', width: 150 },
            {display: '管理员', name: 'opUser.username', align: 'left', width: 150},
			{display: '操作时间', name: 'opDate', align: 'left', width: 100, dateFormat:'yyyy-MM-dd'}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>