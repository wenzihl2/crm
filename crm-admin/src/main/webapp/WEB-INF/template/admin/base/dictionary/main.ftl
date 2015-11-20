<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
var grid;
$(function () {
    grid = $("#maingrid").ligerGrid({
        columns: [
		{display: 'ID', name: 'id', align: 'left', width: 100},
		{name: 'parentId', hide: true, width:10, isAllowHide: false},
		{display: '名称', name: 'name', align: 'left', width: 150 },
		{display: '类别', name: 'type', align: 'left', minWidth: 120, i18n: 'DictionaryType'},
        {display: '排序号', name: 'priority', align: 'left', width: 100 },
        {display: '编码', name: 'code', align: 'left', width: 100 }
		],  pageSize:20, url: base + "/admin/base/dictionary.jhtml",
		onBeforeShowData: function(data) {
			data.list = assemblyTreeObj(data.list, {childsKey: "children"});
			return data;
		}, 
		tree: { columnName: 'name'},
		toolbar: {}, width: '100%', height:'100%', enabledSort: true
    });
    $("#pageloading").hide();
	LG.loadToolbar(grid);
	
	//绑定搜索按钮
	//LG.search(grid);
	LG.searchRecord('searchbar', grid);
});
//添加
function add(){
	top.f_addTab("dictionary_add", '新增数据字典', base + '/admin/base/dictionary/create.jhtml');
}; 

//修改
function edit(){
	var row = LG.getSelected(grid);
	if(row){
		top.f_addTab("dictionary_edit", row.name + '-修改', base + '/admin/base/dictionary/' + row.id + '/edit.jhtml');			
	}
}
function deleteList(){
	LG.deleteList(grid, base + '/admin/base/dictionary/delete.jhtml');
}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="mainsearch" style=" width:98%">
		<form class="searchbox">
			类别:[@u.select_map headerKey="" headerValue="请选择..."
			headerButtom="false" list=EnumUtils.enumProp2I18nMap("DictionaryType")
			name="search_type_eq"/]
		</form>
		 <div id='searchBtn'>
	    	<input type='button' id='search' class='button' value='搜索'/>
	    	<a href='#' id='search_btn_reset' face='Wingdings 3'>解除搜索</a>
	    	<div class="togglebtn-down"></div> 
	    </div>
	</div>
	<div id="maingrid" style="margin:0; padding:0;"></div>
</body>
</html>