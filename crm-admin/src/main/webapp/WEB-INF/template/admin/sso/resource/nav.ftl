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
    				location.href = "${base}/admin/sso/resource/${id}.jhtml?BackURL=[@BackURL/]"
    			} else if(tabid == "modify") {
    				location.href = "${base}/admin/sso/resource/${id}/editForm.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "delete") {
    				location.href = "${base}/admin/sso/resource/${id}/delete.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "appendChild") {
    				location.href = "${base}/admin/sso/resource/${id}/appendChildForm.jhtml?BackURL=[@BackURL/]";
    			} else if(tabid == "move") {
    				location.href = "${base}/admin/sso/resource/${id}/move.jhtml?BackURL=${base}/admin/sso/resource/${id}/move.jhtml";
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
		
		[#--
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">更多 <b class="caret"></b></a>
            <ul class="dropdown-menu">

                    <li [#if op == '查看子节点']class="active"[/#if]>
                        <a href="${base}/admin/sso/resource/${id}/children?BackURL=[@BackURL/]">
                            <i class="icon-list"></i>
                            查看子节点
                        </a>
                    </li>
                    <li>
                        <a href="${base}/admin/sso/resource/${m.parentId}/update?BackURL=[@BackURL/]">
                            <i class="icon-reply"></i>
                            父节点
                        </a>
                    </li>
            </ul>
        </li>
        --]
        [@shiro.hasPermission name="tree:update"]多对多[/@shiro.hasPermission]
    [/#if]
</div>