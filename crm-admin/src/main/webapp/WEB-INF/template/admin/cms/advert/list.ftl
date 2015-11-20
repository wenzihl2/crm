<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm" /]
<script type="text/javascript">
	var grid;
	$(function(){
		 grid = $("#maingrid").ligerGrid({
            columns: [
			{display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '广告名称', name: 'name', align: 'left', width: 150 },
			{display: '广告位', name: 'position.name', align: 'left', width: 150 },
			{display: '广告位编码', name: 'position.code', align: 'left', width: 150 },
			{display: 'LOGO', name: 'realPath', align: 'left', width: 100, 
			 	render: function (row){				
					if (row["realPath"]) {
						var html = '<a href="' + base + row["realPath"] + '" class="imagePreview" target="_blank">查看</a>';
						return html;
					}  											
            	}		
			},
			{display: '开始时间', name: 'startTime', align: 'left', width: 100,
				render:function(row){
					if(row.startTime)
					return row.startTime.split(" ")[0];
				}
			},
			{display: '截止时间', name: 'endTime', align: 'left', width: 100,
				render:function(row){
					if(row.endTime)
					return row.endTime.split(" ")[0];
				}
			},
			{display: '是否开启', name: 'isOpen', align: 'center', width: 100 ,dict:'common'},
			{display: '排序', name: 'priority', align: 'center', width: 100}
			],  pageSize:20, url: location.href, toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });  
		
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		//绑定搜索按钮
		LG.search(grid);
	});
	
	function add(){
		top.f_addTab("advert_add", '新增广告', base + '/admin/cms/advert/create.jhtml');
	};
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("advert_edit", row.name + '-修改', base + '/admin/cms/advert/' + row.id + '/update.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>