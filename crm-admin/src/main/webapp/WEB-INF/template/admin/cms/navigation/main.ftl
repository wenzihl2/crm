<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm" /]
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:10, isAllowHide: false},
			{name: 'parentId', hide: true, width:10, isAllowHide: false},
			{display: '名称', name: 'name', align: 'center', width: 150 },
			{display: '位置', name: 'position', align: 'center', width: 150, i18n: 'NavigationPosition'},
			{display: '链接', name: 'url', align: 'left', width: 250 },
            {display: '显示', name: 'status', align: 'center', width: 50, i18n: 'ShowStatus'},
			{display: '排序', name: 'priority', align: 'center', width: 100 }
			],  pageSize:20, url: base + '/admin/cms/navigation.jhtml',
			onBeforeShowData: function(data) {
				data.list = assemblyTreeObj(data.list, {parentKey: "parentId", childsKey: "children"});
				return data;
			},
			tree: { columnName: 'name'},
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
    
    function add(){
		top.f_addTab("navigation_add", '新增导航', base + '/admin/cms/navigation/create.jhtml');
	};
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("navigation_edit", row.name + '-修改', base + '/admin/cms/navigation/' + row.id + '/edit.jhtml');
		}
	}
	
	function deleteList(){
		LG.deleteList(grid, base + '/admin/cms/navigation/delete.jhtml');
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>