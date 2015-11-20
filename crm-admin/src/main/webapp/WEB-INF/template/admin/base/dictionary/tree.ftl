<!doctype html>
<html>
<head>
[#include "/admin/include/treeHead.htm"]
</head>
<body>
<script type="text/javascript">
    $(function() {
        $.zTree.initSelectTree({
            zNodes : [],
            nodeType : "default",
        	fullName:true,
            urlPrefix : "${base}/admin/base/region/",
            async : true,
        	asyncLoadAll : false,
            onlyDisplayShow: true,
            lazy : true,
            select : {
	            btn : $("#selectOrganizationTree,#organizationName"),
	            id : "organizationId",
	            name : "organizationName",
	            includeRoot: true
	        },
            autocomplete : {
                enable : true
            },
            setting : {
                check : {
	                enable:true,
	                chkStyle:"checkbox",
	                chkboxType: { "Y": "", "N": "s" },
	                onlyCheckLeaf : false,
	                onlySelectLeaf : false
	            }
            }
        });

    });
</script>
<input type="hidden" id="organizationId" name="ids">
<input type="text" id="organizationName" class="input-medium" readonly="readonly">
</body>
</html>