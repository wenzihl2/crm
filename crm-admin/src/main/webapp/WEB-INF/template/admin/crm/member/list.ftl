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
			{display: '用户名',name: 'username', align: 'center', width: 150},
			{display: '注册时间',name: 'createDate', align: 'center', width: 150, dateFormat: 'yyyy-MM-dd HH:mm:ss'},
			{display: '来源',name: 'systemId', align: 'center', width: 150, i18n: "SystemID"},
			{display: '手机', name: 'mobile', align: 'center', minWidth: 100 },
			{display: 'E-mail', name: 'email', align: 'left', width: 150 },
			{display: '余额', name: 'deposit', align: 'center', width: 150},
			{display: '积分', name: 'credits', align: 'center', minWidth: 100 },
			{display: '是否禁用', name: 'isAccountLocked', align: 'center', width: 100, dict:'common.is'},
			{display: '操作', isAllowHide: false, width: 120,
				render: function (row){
					var name = row.name;
					if(name == undefined || name == ''){
						name = row.card_num;
					}
					if (row["id"]) {
						var html = '<a href="#" onclick="showPayDetail(\'' + row.id + '\',\'' + row.username + '\'); return false;">查看收支明细</a>';
						return html;
					}
                }
			}],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%'
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
    //新增
	function add(){
		top.f_addTab("member_add", '新增会员', base + '/admin/crm/member/create.jhtml');
	}
	
	//编辑账号
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab("member_edit", '编辑会员', base + '/admin/crm/member/' + row.id + '/update.jhtml');
		}
	}
	
	//查看用户收支明细
	function showPayDetail(id,userName){
		top.f_addTab("member"+id, '用户【' + userName + '】收支明细', base + '/admin/payment/list/'+id+'.jhtml?parentMenuNo=member_list');
	}
	
	//查看消费记录
	function usedLog(id,name){
		top.f_addTab("member"+id, '用户【' + name + '】消费明细', base + "/admin/order/list/"+id+".jhtml?parentMenuNo=member_list");
	}
	
	//删除
	function f_delete() {
		var selecteds = grid.getSelecteds();
	    if (selecteds.length > 0) {
			LG.ajax({
			    url: base + '/admin/member/delete.jhtml',
	            loading: '正在删除中...',
	            data: { ids: LG.getIds(selecteds) },
	            success: function () {
					LG.showSuccess('删除成功');
	                f_reload();
	            },
	            error: function (message) {
					LG.showError(message);
	            }
	        });
		} else {
			LG.tip('请选择行!');
	    }
	}
	
	function creditsModify(){
		var selecteds = grid.getSelecteds();
	    if (selecteds.length == 1 ) {
	    	top.f_addTab(null, '用户【' + selecteds[0].username + '】修改积分', base + "/admin/credits/add.jhtml?memberId=" + selecteds[0].id);
	    } else {
			LG.tip('请选择一行!');
	    }
	}
	
	function modifyDeposit(){
		var selecteds = grid.getSelecteds();
	    if (selecteds.length == 1 ) {
	    	top.f_addTab(null, '用户【' + selecteds[0].username + '】修改积分', base + "/admin/member/modifyDeposit.jhtml?id=" + selecteds[0].id);
	    } else {
			LG.tip('请选择一行!');
	    }
	}
	
	function relateInviterRule(){
		if(grid.getSelecteds().length > 0){
			$.ligerDialog.open({ 
				url: base + '${base}/admin/creditsSet/relateInviterRule.jhtml', 
				height: 600, 
				width: 800
	        });
		}else{
			LG.tip('请选择行!');
		}
	}

  function f_forbid(){//禁用
      var selecteds = grid.getSelecteds();
      if (selecteds.length > 0) {
          var arr = [];
          for (var i = 0; i < selecteds.length; i++) {
              arr.push(selecteds[i].id);
          }
          LG.ajax({
              url: base + '/admin/member/forbid.jhtml',
              loading: '正在操作中...',
              data: {
                  ids: arr
              },
              success: function(){
                  LG.showSuccess('禁用成功');
                  f_reload();
              },
              error: function(message){
                  LG.showError(message);
              }
          });
      }
      else {
          LG.tip('请选择行!');
      }
  }
  
  function f_using(){
      var selecteds = grid.getSelecteds();
      if (selecteds.length > 0) {
          var arr = [];
          for (var i = 0; i < selecteds.length; i++) {
              arr.push(selecteds[i].id);
          }
          LG.ajax({
              url: base + '/admin/member/using.jhtml',
              loading: '正在操作中...',
              data: {
                  ids: arr
              },
              success: function(){
                  LG.showSuccess('操作成功');
                  f_reload();
              },
              error: function(message){
                  LG.showError(message);
              }
          });
      }
      else {
          LG.tip('请选择行!');
      }
  }
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>
