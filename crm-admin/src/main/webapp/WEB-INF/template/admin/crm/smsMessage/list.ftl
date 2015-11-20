<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '编号', name: 'sn', align: 'left', width: 150 },
			{display: '手机号码', name: 'smsNumber', align: 'center', width: 100 },
			{display: '创建时间', name: 'createDate', align: 'center', width: 150 },
			{display: '发送时间', name: 'sendTime', align: 'center', width: 150 },
			{display: '返回信息', name: 'description', align: 'center', minWidth: 100 },
			{display: '短信类型', name: 'type', align: 'center', width: 100, i18n: 'MessageType'},
            {display: '状态', name: 'status', align: 'center', width: 100, i18n: 'MessageStatus'}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true, checkbox:false
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		//绑定搜索按钮
		LG.search(grid);
    });
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>