<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: '用户', name: 'username', align: 'left', width: 120 },
			{display: '用户主机IP', name: 'host', align: 'left', width: 100 },
            {display: '系统主机IP', name: 'systemHost', align: 'left', width: 150 },
            {display: '登录时间', name: 'startTimestamp', align: 'left', width: 150, dateFormat:'yyyy-MM-dd'},
			{display: '最后访问时间', name: 'lastAccessTime', align: 'left', width: 150, dateFormat:'yyyy-MM-dd'},
			{display: '状态', name: 'status', align: 'left', width: 80 },
			{display: 'User-Agent', name: 'userAgent', align: 'left', width: 250 }
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