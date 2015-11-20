<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
var fieldTypeData = ${fieldArr};
var operatorTypeData = ${operatorArr};
var grid;
//是否类型的模拟复选框的渲染函数
function checkboxRender(rowdata, rowindex, value, column) {
    var iconHtml = '<div class="chk-icon';
    if (value) iconHtml += " chk-icon-selected";
    iconHtml += '"';
    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
    iconHtml += ' gridid = "' + this.id + '"';
    iconHtml += ' columnname = "' + column.name + '"';
    iconHtml += ' onclick="chkIcon(this);"></div>';
    return iconHtml;
}
//字段类型渲染器
function fieldTypeRender(r, i, value) {
    for (var i = 0, l = fieldTypeData.length; i < l; i++) {
        var o = fieldTypeData[i];
        if (o.value == value) return o.text || o.name;
    }
    return "文本框";
}
//字段类型渲染器
function operatorTypeRender(r, i, value) {
    for (var i = 0, l = operatorTypeData.length; i < l; i++) {
        var o = operatorTypeData[i];
        if (o.value == value) return o.text || o.name;
    }
    return "等于";
}

function createGridToolbar(tName)
{
    var items = [];
    //items.push({ text: '保存', click: save, img: base + "/resource/images/icons/silkicons/add.png" });
    //items.push({ text: '上移', click: moveup, img: base + "/resource/thirdparty/liguerUI125/skins/icons/32X32/sign_up.gif" });
    //items.push({ text: '下移', click: movedown, img: base + "/resource/thirdparty/liguerUI125/skins/icons/32X32/arrow_down.gif" }); 
    return { items: items};

//    function save() {
//    	var rows = LG.getData(grid);
//    	var form = $("#mainform");
//      f_save(form, "table_list", {"fieldList": JSON.encode(rows)});
//    }
    function moveup() { 
        var selected = grid.getSelected();
        if (!selected) return;
        grid.up(selected);
    }
    function movedown() { 
        var selected = grid.getSelected();
        if (!selected) return;
        grid.down(selected);
    } 
}
//是否类型的模拟复选框的点击事件
function chkIcon(chkIcon) {
	var grid = $.ligerui.get($(chkIcon).attr("gridid"));
    var rowdata = grid.getRow($(chkIcon).attr("rowid"));
    var columnname = $(chkIcon).attr("columnname");
    var checked = rowdata[columnname];
    grid.updateCell(columnname, !checked, rowdata);
};

function deleteRows(){
	var selecteds = grid.getSelecteds();
	for(var i in selecteds) {
		grid.deleteRow(selecteds[i].__index);
	}
}

$(function(){
	grid = $("#maingrid").ligerGrid({
        columns: [
            { display: '基本信息', columns: [
            { display: '字段名', name: 'name', align: 'left', width: 110, minWidth: 30, editor: { type: 'text'} },
            { display: '显示名', name: 'display', align: 'left', width: 110, minWidth: 30, editor: { type: 'text'} },
            { display: '是否为空', name: 'allownull', width: 55, render: checkboxRender}]
            },
            { display: '表单页设置', columns: [
            //{ display: '表单显示', name: 'inform', width: 55,  editor: {type: 'checkbox'}},
	            { display: '控件类型', name: 'type', align: 'left', width: 80, minWidth: 30, editor: { type: 'select', data: fieldTypeData }, render: fieldTypeRender },
	            { display: '查询操作符', name: 'operator', align: 'left', width: 100, minWidth: 30, editor: { type: 'select', data: operatorTypeData }, render: operatorTypeRender },
	            { display: '标签宽度', name: 'labelWidth', align: 'right', width: 55, editor: { type: 'numberbox'} },
	            { display: '控件宽度', name: 'inputWidth', align: 'right', width: 55, editor: { type: 'numberbox'} },
	            { display: '间隔宽度', name: 'space', align: 'right', width: 55, editor: { type: 'numberbox'} },
	            { display: '是否新行', name: 'newline', width: 55, render: checkboxRender},
	            { display: '是否高级查询', name: 'advanced', width: 80, render: checkboxRender},
	            { display: '是否使用', name: 'used', width: 80, render: checkboxRender},
	            { display: '排序', name: 'priority', width: 55, editor: {type: 'numberbox'}}
	        ]},
	        { display: '配置项', name: 'options', width: 200, editor: {type:'textarea', height: 100}}
       ], url: location.href, usePager: false,  checkbox:false,frozenCheckbox:false,
       enabledEdit: true, clickToEdit: true, inWindow: true, rownumbers: true, height: '100%',
       heightDiff:-14, rowHeight: 24,
       onSuccess: function(data) {
       		data.list = data;
       }
    });
    
    LG.setFormDefaultBtn(f_cancel, function(){
    	var rows = LG.getData(grid);
    	var form = $("#mainform");
        f_save(form, "table_list", {"fieldList": JSON.encode(rows)});
	});
});
</script>
</head>
<body style="padding:10px; overflow:hidden;">
[#if table.name??]
表单名称:${(table.name)!''}
[/#if]
<input type="button" value="增加行" onclick="javascript:grid.addRow();">
<input type="button" value="删除行" onclick="javascript:deleteRows();">

<div id="maingrid"></div>
<form id="mainform" method="post" action="saveField.jhtml"></form>
</body>
</html>