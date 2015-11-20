<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>菜单管理</title>
[#include "/admin/include/treeHead.htm"]
</head>
<body>
<div id="navtab">
	<div title="所有" tabid="show_all" [#if !RequestParameters['search.show_eq']??]lselected="true"[/#if]></div>
	<div title="显示的" tabid="show_eq" [#if RequestParameters['search.show_eq']??]lselected="true"[/#if]></div>
</div>
<script type="text/javascript">
    var async = ${(RequestParameters["async"]!false)?string};
    $(function() {
    	$("#navtab").ligerTab({
    		onBeforeSelectTabItem:function(tabid){
    			if(tabid == "show_all") {
    				location.href = "${base}/admin/sso/resource/tree.jhtml?async=" + async;
    			} else if(tabid == "show_eq") {
    				location.href = "${base}/admin/sso/resource/tree.jhtml?search.show_eq=true&async=" + async;
    			}
            }
        });
        var zNodes =[
        	[#list trees as m]
            	{ id:${m.id}, pId:${m.pId}, name:"${m.name}", iconSkin:"${m.iconSkin}", open: true, root : ${m.root?string},isParent:${m.isParent?string}}
            	[#if m_index < trees?size - 1],[/#if]
            [/#list]
        ];

        $.zTree.initMovableTree({
            zNodes : zNodes,
            urlPrefix : "${base}/admin/sso/resource",
            async : async,
            onlyDisplayShow:${(RequestParameters["search.show_eq"])!true},
            permission: {create: true, update: true, remove: true, move: true, end: true },
            autocomplete : {
                enable : true
            },
            setting : {
                callback : {
                    onClick: function(event, treeId, treeNode, clickFlag) {
                        parent.frames['listFrame'].location.href='${base}/admin/sso/resource/' + treeNode.id + "/editForm.jhtml?async=" + async;
                    }
                }
            }
        });

    });
</script>
</body>
</html>