<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>提示信息</title>
<#include "/admin/include/head.htm">
<style type="text/css">
<!--
*{ padding:0; margin:0; font-size:12px}
a:link,a:visited{text-decoration:none;color:#0068a6}
a:hover,a:active{color:#ff6600;text-decoration: underline}
.showMsg{border: 1px solid #1e64c8; zoom:1; width:450px; height:172px;position:absolute;top:44%;left:50%;margin:-87px 0 0 -225px}
.showMsg h5{background-repeat: no-repeat; color:#fff; padding-left:35px; height:25px; line-height:26px;*line-height:28px; overflow:hidden; font-size:14px; text-align:left}
.showMsg .content{ padding:46px 12px 10px 45px; font-size:14px; height:64px; text-align:left}
.showMsg .bottom{ background:#e4ecf7; margin: 0 1px 1px 1px;line-height:26px; *line-height:30px; height:26px; text-align:center}
.showMsg .ok,.showMsg .guery{}
.showMsg .guery{background-position: left -460px;}
-->
</style>
<script type="text/javascript">
	function closeAndReload(parentMenuNo){
		<#if refresh?? && refresh = 'true'>
			set_grid_auto_refresh();
		</#if>
		var win = parent || window;
		win.LG.closeAndReloadParent(null, parentMenuNo);
	}
</script>
</head>
<body>
<div class="showMsg" style="text-align:center">
	<h5>提示信息</h5>
    <div class="content guery" style="display:inline-block;display:-moz-inline-stack;zoom:1;*display:inline;max-width:330px">操作成功！</div>
    <div class="bottom">
    	您的操作已成功!<br>
    	<input type="button" class="formButton messageButton" <#if parentMenuNo??>onclick="closeAndReload('${parentMenuNo}');"</#if> value="确  定" hidefocus="true" />
	</div>
</div>
</body>
</html>