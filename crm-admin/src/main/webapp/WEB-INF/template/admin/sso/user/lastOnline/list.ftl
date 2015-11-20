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
			{display: '用户会话ID', name: 'host', align: 'left', width: 100 },
            {display: '用户主机IP', name: 'host', align: 'left', width: 150 },
            {display: '系统主机IP', name: 'systemHost', align: 'left', width: 150},
			{display: 'User-Agent', name: 'userAgent', align: 'left', width: 250},
			{display: '最后登录时间', name: 'lastLoginTimestamp', align: 'left', width: 100, dateFormat:'yyyy-MM-dd' },
			{display: '最后退出时间', name: 'lastStopTimestamp', align: 'left', width: 100, dateFormat:'yyyy-MM-dd'},
			{display: '登录次数', name: 'loginCount', align: 'left', width: 80 },
			{display: '总在线时长', name: 'totalOnlineTime', align: 'left', width: 80 }
			
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