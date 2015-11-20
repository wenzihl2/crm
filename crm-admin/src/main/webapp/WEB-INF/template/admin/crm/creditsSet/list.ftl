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
			{display: '名称', name: 'name', align: 'center', width: 150 },
			{display: '获取方式', name: 'getType', align: 'center', width: 150, i18n:'CreditsGetType'},
			{display: '开始日期', name: 'startDate', align: 'center', width: 100, dateFormat: 'yyyy-MM-dd'},
            {display: '结束日期', name: 'endDate', align: 'center', width: 100, dateFormat: 'yyyy-MM-dd'},
            {display: '等级积分', name: 'rankPoint', align: 'center', width: 100},
            {display: '消费积分', name: 'consumePoint', align: 'center', width: 100},
            {display: '是否开启', name: 'show', align: 'center', width: 80, dict:'common.is' }
            ], pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
        LG.loadToolbar(grid);
    });
    
    function add(){
    	top.f_addTab('creditsSet_add', '新增积分规则', base + '/admin/crm/creditsSet/create.jhtml');
    }
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab('creditsSet_edit', '修改积分规则', base + '/admin/crm/creditsSet/' + row.id + '/update.jhtml');
		}
		
	};
	
	function start(){
		jQuery.ligerDialog.confirm('确定开启此规则吗?', function(confirm) {
    		if (confirm){
    			var row = LG.getSelected(grid);
				if(row){
					LG.deleteList(grid, base + '/admin/crm/creditsSet/' + row.id + '/start.jhtml');
				}
			}
		});
	};
	
	function stop(){
		jQuery.ligerDialog.confirm('确定关闭此规则吗?', function(confirm) {
    		if (confirm){
    			var row = LG.getSelected(grid);
				if(row){
					LG.deleteList(grid, base + '/admin/crm/creditsSet/' + row.id + '/stop.jhtml');
				}
			}
		});
	};
	
	function deleteList(){
		jQuery.ligerDialog.confirm('确定删除吗?', function(confirm) {
    		if (confirm){
    			var row = LG.getSelected(grid);
				if(row){
					LG.deleteList(grid, base + '/admin/crm/creditsSet/' + row.id + '/delete.jhtml');
				}
			}
		});
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>