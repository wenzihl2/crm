<!doctype html>
<html>
<head>
[#include "/admin/include/treeHead.htm"]
<style type="text/css">.noDisplayBtn{display:none;}</style>
<script type="text/javascript">
	function checkNodeTree(){
		[#if isEdit?? && isEdit == "true"]
		toCheckNode("${(RequestParameters["id"])!''}");
		[/#if]
	}
	
	function save(){
		var checkedMenuNodes = getCheckedMenuNodes();
		var checkedOperateNodes = getCheckedOperateNodes();
		var menuIds = [], operateIds = [];
		for(var i=0; i<checkedMenuNodes.length; i++){
			menuIds[i] = checkedMenuNodes[i].id;
		} 
		for(var i=0; i<checkedOperateNodes.length; i++){
			operateIds[i] = checkedOperateNodes[i].id;
		}
		
		LG.ajax({
			url: base + "/admin/sso/role/editRoleMenu.jhtml",
			data: {"id": getQueryStringByName("id"), 
				menuIds:menuIds, operateIds: operateIds},
			success: function(result){
				LG.showSuccess(result.content, function(){
					parent.dialog.close();
				});
			}
		});
	}
</script>
<script type="text/javascript" src="${base}/resource/js/roleTree.js"></script>
</head>
<body>
	<div id="roleTree">
		<ul id="treeDemo" class="ztree" style="overflow:hidden;"></ul>
	</div>
	<div class="noDisplayBtn">
		<input type="button" id="doSubmit" onclick="save();"/>
	</div>
</body>
</html>