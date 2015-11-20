[#if !goods??]
	[#assign isAdd = true /]
	[#assign haveSpec=false /]
[#else]
	[#assign isEdit = true /]
	[#assign haveSpec=goods.have_spec /]
[/#if]
<script type="text/javascript">
var grid = null;
$(function(){
	grid = $("#maingrid").ligerGrid({
        columns: [
		{display: '', width:80, isSort: false, align: 'center', render: function (rowdata, rowindex, value){
			var h = '<div data-id="9" class="operating">';
            h += '<span title="删除行" class="ui-icon ui-icon-trash" onclick="javascript:grid.deleteRow(' + rowindex + ');">&nbsp;&nbsp;</span>';
            h += '</div>';
            return h;
		}},
		{display: '商品编码', name: 'no', align: 'left', width: 150, editor: { type: 'text' }},
		{display: '条码', name: 'barCode', align: 'left', width: 200, editor: { type: 'text' }},
		[#list allSpecList! as spec]
		{display: '${spec.name}[#if spec.memo??][${spec.memo}][/#if]', name: 'spec_${spec.id}', isAllowHide: true, [#if isEdit?? && goods.specIds?seq_contains(spec.id)] hide: false,[#else]hide: true,[/#if] 
			align: 'left', width:100, editor: { type: 'select', data: ${spec.specValueJson},
		onChange: Product.specChange,
		}, render: function (item) {
        	var list = ${spec.specValueJson};
            for (var i = 0; i < list.length; i++) {
                if (list[i]['id'] == item['spec_${spec.id}']){
                    return list[i]['value'];
               	}
            }
        }},
		[/#list]
		{name: 'product_id', hide: true, width:10, isAllowHide: true}
		], [#if isEdit??]data: {list: ${goodsSpecs}},[/#if]
		toolbar: {items:[{text:"新增规格商品", click: function(){grid.addRow();}, img: 'btn_add'}]}, 
		enabledEdit: true, enabledSort: true, usePager:false, checkbox:false, width: '99%'
    });
});
function getGridVal() {
	var gdatas = grid.getData();
	$.each(gdatas, function(j, gdata){
		var specValueIdMap = {};
		for(var s in gdata){
			if(s.startWith("spec_")) {
				specValueIdMap[s] = gdata[s];
				delete(gdata[s]);
			}
		}
		gdata.specValueIdMap = specValueIdMap;
	});
	return gdatas;
}
</script>
<div title="商品规格" class="tabContent">
	<input type="hidden" name="have_spec" id="haveSpec" value="${haveSpec!'false'}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" id="no-product-spec" [#if haveSpec]style="display:none;"[/#if]>
		<tr>
			<th class="l-table-edit-td">规格：</th>
			<td class="l-table-edit-td">
				<input type="button" value="开启规格" id="specOpenBtn"/>
				(开启规格前先填写以上价格等信息，可自动复制信息到货品)
			</td>
		</tr>
	</table>
	
	<div id="product-spec" [#if !haveSpec]style="display:none;"[/#if]>
		<div style="border:none; color:#666; border: 2px solid #ccc; background: rgb(239, 239, 239);padding:0.8em; margin:1em 0;">
			<input type="button" value="关闭规格" class="formButton" id="closeSpec"/>
		</div>
	</div>
	
	<div id="maingrid" style="[#if !haveSpec]display:none;[/#if]margin:10px 0px; padding:0;"></div>
</div>
<script type="text/javascript" src="${base}/resource/js/plugin/spec.js"></script>