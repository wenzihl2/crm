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
			{display: '生成日期',name: 'createDate', align: 'center', width: 150, dateFormat: 'yyyy-MM-dd HH:mm:ss'},
			{display: '会员名称', name: 'member.username', align: 'center', width: 120 },
			{display: '类型', name: 'creditsType', align: 'center', width: 100,i18n:'CreditsType'},
			{display: '金额', name: 'amount', align: 'center', width: 80 },
			{display: '余额', name: 'remaind', align: 'center', width: 80},
			{display: '备注', name: 'remark', align: 'center', width: 150},
			{display: '操作员', name: 'operator', align: 'center', width: 150}],
			 pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
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
