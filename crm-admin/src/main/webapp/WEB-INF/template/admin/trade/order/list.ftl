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
			{display: '订单编号', name: 'orderNo', align: 'center', width: 180 },
			{display: '支付编号', name: 'orderPay.tradeNo', align: 'center', width: 180,
				render:function(row){
					if(row["orderPay.tradeNo"]){
						return row["orderPay.tradeNo"];
					}
					return "--";
				}
			},
			{display: '来源',name: 'systemId', align: 'center', width: 150, i18n:"SystemID"},
			{display: '会员名称', name: 'member.username', align: 'center', width: 120 },
			{display: '订单金额（元）', name: 'amount', align: 'center', width: 100 },
			{display: '积分', name: 'credits', align: 'center', width: 100},
			{display: '余额（元）', name: 'deposit', align: 'center', width: 100},
			{display: '现金（元）', name: 'cash', align: 'center', width: 100},
			{display: '消费状态', name: 'consumeStatus', align: 'center', width: 100,i18n:'consumeStatus'},
			{display: '记录类型', name: 'recordType', align: 'center', width: 120 ,i18n:'recordType'},
			{display: '生成时间', name: 'createDate', align: 'center', width: 150},
			{display: '是否已退款', name: 'isRefund', align: 'center', width: 100,dict:'common.is'}
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
    });
	 //删除
  	function f_delete() {
		var selecteds = grid.getSelecteds();
	    if (selecteds.length > 0) {
			LG.ajax({
			    url: base + '/admin/order/order/delete.jhtml',
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
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>