[#if !goods??]
	[#assign isAdd = true /]
	[#assign haveSpec=false /]
[#else]
	[#assign isEdit = true /]
	[#assign haveSpec=goods.have_spec /]
[/#if]
<script type="text/javascript">
var grid, $specificationIds;
 $(function () {
    grid = $("#maingrid").ligerGrid({
        columns: [
		{name: 'product_id', hide: true, width:0, isAllowHide: true},
		{display: '', width:80, isSort: false, align: 'center', width: 80, render: function (rowdata, rowindex, value){
			var h = '<div data-id="9" class="operating">';
            h += '<span title="新增行" class="ui-icon ui-icon-plus" onclick="addProductSpec();">&nbsp;&nbsp;</span>';
            h += '<span title="删除行" class="ui-icon ui-icon-trash" onclick="javascript:grid.deleteRow(' + rowindex + ');">&nbsp;&nbsp;</span>';
            h += '</div>';
            return h;
		}},
		{display: '商品编码', name: 'sn', align: 'left', width: 120, editor: { type: 'text' }},
		[#list allSpecList! as spec]
		{display: '${spec.name}[#if spec.memo??][${spec.memo}][/#if]', name: 'spec_${spec.id}', [#if isEdit?? && goods.specIds?seq_contains(spec.id)] hide: false,[#else]hide: true,ddd:3,[/#if] 
			align: 'left', width:100, editor: { type: 'select', data: ${spec.specValueJson}, valueColumnName:'id', displayColumnName: 'value'
		}, render: function (item) {
        	var list = ${spec.specValueJson};
            for (var i = 0; i < list.length; i++)
            {
                if (list[i]['id'] == item['spec_${spec.id}'])
                    return list[i]['value']
            }
            return item['spec_${spec.id}'];
        }},
		[/#list]
		{display: '销售价', name: 'sale_price', align: 'left', width: 120, editor: {type:'numberbox', precision: 2}},
		{display: '采购价', name: 'purchase_price', align: 'left', minWidth: 120, editor: {type:'numberbox', precision: 2}},
        {display: '重量', name: 'weight', align: 'left', width: 120, editor: {type:'numberbox', precision: 2}}
		],  pageSize:20, [#if isEdit??]data: {list: ${productSpecs}},[/#if]
		toolbar: {items:[{text:"新增规格商品", click: addProductSpec, img: base + '/resource/images/icons/silkicons/add.png'}]}, 
		enabledEdit: true, width: '100%', enabledSort: true, usePager:false, checkbox:false
    });
    
    // 增加规格商品
    $specificationIds = $("#specificationSelect :checkbox");
    // 修改商品规格
	$specificationIds.change(function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			//$specificationProductTable.find("td.specification_" + $this.val()).show().find("select").prop("disabled", false);
			grid.toggleCol("spec_" + $this.val(), true);
		} else {
			//$specificationProductTable.find("td.specification_" + $this.val()).hide().find("select").prop("disabled", true);
			grid.toggleCol("spec_" + $this.val(), false);
		}
	});
	
	
	$("#specOpenBtn").click(function(){
		if($("#haveSpec").val() == false) {
			//隐藏销售价、市场价、货号、重量、库存等信息
			$("#no-product-spec").hide();
			$("#no-product-spec input").attr("disabled", true);
			$("#product-spec").show();
			$("#haveSpec").val("true");
			
			
			$("#specificationSelect").show();
			$("#maingrid").show();
		}
	});
 });
 
function addProductSpec() {
	if ($specificationIds.filter(":checked").size() == 0) {
		$.message("warn", "${message("admin.product.specificationRequired")}");
		return false;
	}
    var purchase_price = $("#purchase_price").val();
    var sale_price = $("#sale_price").val();
    var weight = $("#weight").val();
    var row = {};
	$.each($specificationIds, function(i, data){
		row["spec_" + data.id] = "";
	});
	row["sale_price"] = sale_price;
	row["purchase_price"] = purchase_price;
	row["weight"] = weight;
	grid.addRow(row);
}

function getGridVal() {
	var gdatas = grid.getData();
	var specificationIds = new Array();
	$.each($specificationIds.filter(":checked"), function(i, specificationId){
		specificationIds.push(specificationId.value);
	});
	$.each(gdatas, function(j, gdata){
		if(!gdata.sns) {
			gdata.sns = "";
		}
		for(var s in gdata){
			if(s.startWith("spec_") && $.inArray(s.split("_")[1], specificationIds) == -1) {
				delete(gdata[s]);
			}
		}
	});
	return gdatas;
}
</script>
<div title="商品规格" class="tabContent">
	<input type="hidden" name="have_spec" id="haveSpec" value="${haveSpec!'false'}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" id="no-product-spec" style="[#if haveSpec]display:none;[/#if]">
		<tr>
			<th>规格：</th>
			<td>
				<input type="button" value="开启规格" id="specOpenBtn"/>
				(开启规格前先填写以上价格等信息，可自动复制信息到货品)
			</td>
		</tr>
	</table>
	<div id="specificationSelect" class="specificationSelect" style="margin:10px 0px; [#if !haveSpec]display:none;[/#if]">
		<ul>
			[#list allSpecList as spec]
				<li>
					<input type="checkbox" name="spec_ids" value="${spec.id}" [#if isEdit?? && product.specIds?seq_contains(spec.id)] checked="checked"[/#if]/>${spec.name}
					[#if spec.memo??]
						<span class="gray">[${spec.memo}]</span>
					[/#if]
				</li>
			[/#list]
		</ul>
	</div>
	<div id="maingrid" style="[#if !haveSpec]display:none;[/#if]"></div>
</div>