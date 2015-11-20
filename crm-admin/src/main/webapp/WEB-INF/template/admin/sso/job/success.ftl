<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    function refreshTree() {
        parent.frames['treeFrame'].location.reload();
    }
</script>
</head>
<body>
<p>修改树后不会自动刷新，如需要刷新，请点击<a class='btn btn-danger' onclick='refreshTree();'>刷新树</a></p>
[@flash_message /]
</body>
</html>