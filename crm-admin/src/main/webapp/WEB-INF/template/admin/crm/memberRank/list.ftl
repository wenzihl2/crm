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
			{display: '等級名称', name: 'name', align: 'center', width: 250 },
			{display: '优惠百分比', name: 'preferentialScale', align: 'center', minWidth: 250 },
			{display: '所需积分', name: 'point', align: 'center', width: 250 },
            {display: '默认等级', name: 'isDefault', align: 'center', width: 100,dict:'common'}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
    
    //新增
	function add(){
		top.f_addTab("memberRank_add", '新增会员等级', base + '/admin/crm/memberRank/create.jhtml');
	}
	
	//编辑
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("memberRank_edit", '编辑会员等级', base + '/admin/crm/memberRank/' + row.id + '/update.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="functions">
		<div class="clearfix">
				<span id="searchDefault">
					会员等级:<input type="text" class="formText" name="search_LIKE_name"/>&nbsp;
				</span>
				<input type="button" id="search" class="button" value="搜索"/>
				<a href="#" id="search_btn_reset" face="Wingdings 3">清除本次搜索</a>
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>