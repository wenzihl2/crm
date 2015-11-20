var ligerDialog;
$(function(){
	Product.init();
});
var Product = {
	specMap: [], // 规格值容器
	putSpec: function(){// 已经选择的规格值放入容器
		var self=this;
		var datas = grid.getData();
		$.each(datas, function(i, data){
			var specValueStr = "";
			for(var s in data) {
				if(s.startWith("spec_")) {
					specValueStr += data[s];// + "-";
				}
			}
			self.specMap.push(specValueStr);
		});
	},
	init: function(){
		var self=this;
		$("#specOpenBtn").click(function(){
			Product.openSpecDialog();
		});
		$("#closeSpec").click(function(){
			Product.closeSpec();
		});
		//如果是编辑状态，则将规格值放入容器中
		//self.putSpec();
	},
	//打开选择规格的窗口
	openSpecDialog:function(){
		ligerDialog = $.ligerDialog.open({ width:700, height:550, url: 'spec_select.jhtml', title: "规格"});
	},
	closeSpec: function(){
		if(!confirm("关闭后现有已添加的货品数据将全部丢失,确定要关闭规格吗?")){
			return;
		}
		var records = grid.records;
		grid.removeRange(records);
		$("#haveSpec").val("false");
		$("#product-spec").hide();
		$("#no-product-spec").show();
		$("#no-product-spec input").attr("disabled", false);
		$("#maingrid").hide();
		$.each(grid.getColumns(), function(i, column){
			if(column.name && column.name.startWith("spec_")) {
				grid.toggleCol(column.name, false);
			}
		});
	},
	//生成货品
	createPros:function(specs, names){
		//隐藏销售价、市场价、货号、重量、库存等信息
		$("#no-product-spec").hide();
		$("#no-product-spec input").attr("disabled", true);
		$("#product-spec").show();
		$("#haveSpec").val("true");
		var self = this;
		self.doCreatePros(specs, names);
		$("#maingrid").show();
	},
	specChange: function(editParm){
		var datas = editParm.column.editor.data;
		$.each(datas, function(i, data){
			if(data.id == editParm.newValue) {
				var select = grid.getRow(editParm.rowindex);
				select.no = $("input[name='no']").val() + "-" + data.value;
			}
		})
	},
	doCreatePros:function(specs, names){
		var self =this;
		var productSpecs = combinationAr(specs);
		var no = $("input[name='no']").val();
		// 修改商品规格
		$.each(productSpecs[0], function(i, item){
			grid.toggleCol("spec_" + item.specid, true);
		});
		
		$.each(productSpecs, function(i, productSpec){
			var row = {};
			var specStr = "";
			$.each(productSpec, function(j, item){
				specStr += item.specvalue;
				if(j < productSpec.length - 1) {
					specStr += "-";
				}
				row["spec_" + item.specid] = item.specvid;
			});
			//增加小物料编码
			if(no && no != "") {
				row.no = no + "-" + specStr;
			}
			grid.addRow(row);
		});
	}
};
/**
* 将一个值放在一个数组未尾，形成新的数据
*/
function putAr(ar1,obj){
	var newar =[];
	for(var i=0;i<ar1.length;i++){
		newar[i] =ar1[i];
	}
	newar[ar1.length] = obj;
	return newar;
};

/*
 *
 * 组合两个数组
 * 如果第一个数组是二维数组，则调用putAr分别组合
 * 如果第一个数组是一维数组，则直接和ar2组合
 */
function combination(ar1, ar2){
	var ar = new Array();
	var k=0;
	if(!ar2) { //数组只有一唯的情况
		for(var i=0;i<ar1.length;i++){
			ar[k] = [ar1[i]];
			k++;
		}	
		return ar;
	}
 
	for(var i=0; i<ar1.length; i++){
		for(var j=0;j<ar2.length;j++){
			if(ar1[i].constructor == Array){
				ar[k]= putAr(ar1[i], ar2[j]);
			}else{
				ar[k] = [ar1[i], ar2[j]];
			}	 
			k++;
		}
	}
	return  ar;
};

function combinationAr(spec_ar){
	var ar;
	var m =0 ;

	if(spec_ar.length==1){ 
		return combination(spec_ar[0]);
	}

	while(m < spec_ar.length - 1){
		if(m==0){
			ar = spec_ar[0];
		}
		ar = combination(ar,spec_ar[m+1]);
		m++;
	};
	return ar;
};