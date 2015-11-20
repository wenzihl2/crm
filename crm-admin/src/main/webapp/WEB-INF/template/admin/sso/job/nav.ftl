[#if source??]
    [#assign m = source]
[/#if]
[#if parent??]
    [#assign m = parent]
[/#if]
[#assign id = m.id ]
<script type="text/javascript">
    var async = ${(RequestParameters["async"]!false)?string};
    $(function() {
    	$("#navtab2").ligerTab({
    		onBeforeSelectTabItem:function(tabid){
    			if(tabid == "view") {
    				location.href = "${base}/admin/sso/job/${id}.jhtml?BackURL=[@BackURL/]"
    			} else if(tabid == "modify") {
    				location.href = "${base}/admin/sso/job/${id}/editForm.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "delete") {
    				location.href = "${base}/admin/sso/job/${id}/delete.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "appendChild") {
    				location.href = "${base}/admin/sso/job/${id}/appendChildForm.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "move") {
    				location.href = "${base}/admin/sso/job/${id}/move.jhtml?BackURL=${base}/admin/sso/job/${id}/move.jhtml";
    			}
            }
    	});
    });
</script>
<div id="navtab2">
	[#if id??]
		<div title="查看" [#if op == '查看']lselected="true"[/#if] tabid="view"></div>
		<div title="修改" [#if op == '修改']lselected="true"[/#if] tabid="modify"></div>
		<div title="删除" [#if op == '删除']lselected="true"[/#if] tabid="delete"></div>
		<div title="添加子节点" [#if op == '添加子节点']lselected="true"[/#if] tabid="appendChild"></div>
		<div title="移动节点" [#if op == '移动节点']lselected="true"[/#if] tabid="move"></div>
	[/#if]
</div>