<!doctype html>
<html>
<head>
<title>部门管理</title>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    $(document).ready(function () {
        $('body').layout({
                west__size:                    210
           ,    west__spacing_closed:        20
           ,    west__togglerLength_closed:    100
           ,    west__togglerContent_closed:"显示树"
           ,    west__togglerTip_closed:    "显示树"
           ,    west__sliderTip:            "显示树"
           ,    resizerTip:         "调整大小"
           ,    togglerTip_open: "隐藏树"
           ,    togglerTip_closed: "显示树"
           ,    maskContents:        true // IMPORTANT - enable iframe masking
           });
        });
</script>
</head>
<body>
<div class="ui-layout-west tree">
    <iframe name="treeFrame" width="100%" height="100%"  frameborder="0" scrolling="auto" src="${base}/admin/sso/dept/tree.jhtml"></iframe>
</div>
<div class="ui-layout-center tree">
    <iframe name="listFrame" width="100%" height="100%"  frameborder="0" scrolling="auto"></iframe>
</div>
</body>
</html>