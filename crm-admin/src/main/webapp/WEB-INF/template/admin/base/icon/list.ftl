<!doctype html>
<html>
<head>
<title>图标管理</title>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	//设置为自动刷新
    var grid;
    $(function () {
    	$("#navtab2").ligerTab({
    		onBeforeSelectTabItem:function(tabid){
    			if(tabid == "css_class") {
    				location.href = "${base}/admin/base/icon.jhtml?search.type_eq=css_class"
    			} else if(tabid == "upload_file") {
    				location.href = "${base}/admin/base/icon.jhtml?search.type_eq=upload_file";
    			} else if(tabid == "css_sprite") {
    				location.href = "${base}/admin/base/icon.jhtml?search.type_eq=css_sprite";
    			} else if(tabid == "all") {
    				location.href = "${base}/admin/base/icon.jhtml";
    			}
            }
    	});
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: '编号', name: 'id', align: 'left', width: 80 },
            {display: '标识符', name: 'identity', align: 'left', width: 250},
            {display: '图标', name: 'identity', align: 'center', width: 80, 
            	render:function(row){
					return '<i class="' + row.identity + '" title="生成后的图标"></i>';
				}
            },
            {display: '描述', name: 'description', align: 'left', width: 100}
            ],  pageSize:20, url: location.href, toolbar: {}, width: '100%', height:'100%',
			enabledSort: true
        });
		LG.loadToolbar(grid);
    });
    //添加
	function add(){
		top.f_addTab("icon_add", '新增图标', base + '/admin/base/icon/create.jhtml');
	}; 
	
	//修改
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("icon_edit", row.identity + '-修改', base + '/admin/base/icon/' + row.id + '/update.jhtml');			
		}
	}
	
	function deleteList(){
		var row = LG.getSelected(grid);
		if(row){
			LG.deleteList(grid, base + '/admin/base/icon/' + row.id + '/delete.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
    <div id="navtab2">
		<div title="所有图标列表" [#if !RequestParameters['search.type_eq']??]class="active"[/#if] tabid="all"></div>
		[#list types! as t]
		<div title="${t.info}" [#if RequestParameters['search.type_eq']?? && RequestParameters['search.type_eq'] == t]lselected="true"[/#if] tabid="${t}"></div>
		[/#list]
	</div>
	${(search.type_eq)!''}
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>