<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
</head>
<body>
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '名称', name: 'name', align: 'center', width: 150 },
			{display: '是否默认', name: 'isDefault', align: 'center', width: 50,dict:'common.is' },
			{display: '注册赠送积分', name: 'registCredits', align: 'center', width: 100 },
            {display: '签到赠送积分', name: 'signInCredits', align: 'center', width: 100},
            {display: '邀请赠送积分', name: 'inviteCredits', align: 'center', width: 100},
            {display: '评论赠送积分', name: 'reviewCredits', align: 'center', width: 100},
            {display: '购物赠送积分比例', name: 'buyCredits', align: 'center', width: 100}],  
            pageSize:20, url: '${base}/admin/creditsSet/queryData.jhtml',
			toolbar: {
				items:[{
					click: function(){
						var selecteds = grid.getSelecteds();
						if(selecteds.length == 1){
							LG.ajax({
							    url: base + '/admin/creditsSet/relateInviterRule.jhtml',
				
					            loading: '正在关联中...',
					            data: { 
					            	id: selecteds[0].id,
					            	ids: LG.getIds(parent.grid.getSelecteds()),
					            	isInviter:false
					            },
					            success: function () {
									LG.showSuccess('绑定成功');
					                f_reload();
					            },
					            error: function (message) {
									LG.showError(message);
					            }
					        });
						}else{
							LG.tip("请选择一条记录!");
						}
					},
		            text: '绑定积分规则',
		            img: base + "/resource/images/icons/silkicons/tick.png"
				},{
					click: function(){
						var selecteds = grid.getSelecteds();
						if(selecteds.length == 1){
							LG.ajax({
							    url: base + '/admin/creditsSet/relateInviterRule.jhtml',
				
					            loading: '正在关联中...',
					            data: { 
					            	id: selecteds[0].id,
					            	ids: LG.getIds(parent.grid.getSelecteds()),
					            	isInviter:true
					            },
					            success: function () {
									LG.showSuccess('绑定成功');
					                f_reload();
					            },
					            error: function (message) {
									LG.showError(message);
					            }
					        });
						}else{
							LG.tip("请选择一条记录!");
						}
					},
		            text: '绑定邀请规则',
		            img: base + "/resource/images/icons/silkicons/tick.png"
				}]
			}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
    });
    

</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="functions">
		<div class="clearfix">
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>