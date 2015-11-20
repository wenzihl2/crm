<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
	var grid;
	$(function(){
		 grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '流水号',name:'paymentNo' ,align: 'center', width: 200},
			{display: '支付方式', name: 'paymentTypeName', align: 'center', width: 80,
				render : function(row, rowindex, value, column){
					if(value == undefined || value == ''){
						return "余额支付";
					}
					return value;
				}
			},
			{display: '交易类型', name: 'detailType', align: 'center', width: 90, i18n:'DetailType'},
			{display: '生成日期', name: 'createDate', align: 'center', width: 150, dateFormat: 'yyyy-MM-dd'},
			{display: '支付前金额', name: 'before_amount', align: 'center', width: 80},
			{display: '收支金额(元)', name: 'amount', align: 'center', width: 80},
			{display: '支付后金额', name: 'after_amount', align: 'center', width: 80},
			{display: '会员名称', name: 'member.username', align: 'center', width: 120},
			{display: '操作员', name: 'operator', align: 'center', width: 150},
			{display: '备注', name: 'remark', align: 'center', width: 150}
			],  pageSize:20, url: location.href, 
			checkbox: false, width: '100%', height:'100%', enabledSort: true,toolbar:{}
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
	});
	
	function billSet(id){
		$.ligerDialog.open({title:"收支明细详情", height: 400,width:400,url: base + '/admin/payment/detail/'+id+'.jhtml'});
	}
	
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>