<!doctype html>
<html>
<head>
<title>模板管理</title>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	//设置为自动刷新
    var grid;
    $(function () {
    	$("#navtab2").ligerTab({
    		onBeforeSelectTabItem:function(tabid){
    			if(tabid == "email") {
    				location.href = "${base}/admin/base/template.jhtml?search.type_eq=email"
    			} else if(tabid == "mobil") {
    				location.href = "${base}/admin/base/template.jhtml?search.type_eq=mobil";
    			} else if(tabid == "js") {
    				location.href = "${base}/admin/base/template.jhtml?search.type_eq=js";
    			} else if(tabid == "print") {
    				location.href = "${base}/admin/base/template.jhtml?search.type_eq=print";
    			} else if(tabid == "article") {
    				location.href = "${base}/admin/base/template.jhtml?search.type_eq=article";
    			} else if(tabid == "all") {
    				location.href = "${base}/admin/base/template.jhtml";
    			}
            }
    	});
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: '编号', name: 'id', align: 'left', width: 80 },
			{display: '名称', name: 'name', align: 'left', width: 100},
            {display: '标识符', name: 'identity', align: 'left', width: 250}
            ],  pageSize:20, url: location.href, toolbar: {}, width: '100%', height:'100%',
			enabledSort: true
        });
		LG.loadToolbar(grid);
    });
    //添加
	function add(){
		top.f_addTab("template_add", '新增图标', base + '/admin/base/template/create.jhtml');
	}; 
	
	//修改
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("template_edit", row.identity + '-修改', base + '/admin/base/template/' + row.id + '/update.jhtml');			
		}
	}
	
	function deleteList(){
		var row = LG.getSelected(grid);
		if(row){
			LG.deleteList(grid, base + '/admin/base/template/' + row.id + '/delete.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
    <div id="navtab2">
		<div title="所有模板列表" [#if !RequestParameters['search.type_eq']??]class="active"[/#if] tabid="all"></div>
		[#list types! as t]
		<div title="${t.info}" [#if RequestParameters['search.type_eq']?? && RequestParameters['search.type_eq'] == t]lselected="true"[/#if] tabid="${t}"></div>
		[/#list]
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>