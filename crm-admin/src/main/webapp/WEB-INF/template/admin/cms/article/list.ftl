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
			{display: '标题', name: 'title', align: 'left', width: 350 },
			{display: '分类', name: 'category.name', align: 'left', width: 200, align: 'center' },
			{display: '是否发布', name: 'isPublication', align: 'center', dict:'common', width: 80},
			{display: '创建时间', name: 'createDate', align: 'center', width: 150, dateFormat: 'yyyy-MM-dd HH:mm:ss'}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
    
    function add(){
		top.f_addTab("article_add", '新增文章', base + '/admin/cms/article/create.jhtml');
	};
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("article_edit", row.title + '-修改', base + '/admin/cms/article/' + row.id + '/update.jhtml');
		}
	}
	
	function deleteList(){
		LG.deleteList(grid, base + '/admin/cms/article/batch/delete.jhtml');
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>