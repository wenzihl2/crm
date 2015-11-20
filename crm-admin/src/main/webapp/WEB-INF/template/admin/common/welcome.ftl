<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>欢迎使用${setting.name}系统</title>
<#include "/admin/include/head.htm">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/resource/css/welcome.css" rel="stylesheet" type="text/css" />
<style>
.notebook{ border:1px solid #ddd;width: 100%; margin:10px auto; border-top:0}
.navbar{ height:auto; margin-bottom:0; margin-top:0; width:100%}
.listli{padding:10px 0 10px 0;}
.listli li{ height:30px; line-height:30px; border-bottom:1px solid #f0f0f0; padding:0 0 0 15px;}
.listli li:last-child{ border-bottom:0}
.listli li a{ text-decoration:none;color: #3186C8;}
.listli li a:hover{ text-decoration:none; color:#000}
</style>
</head>
<body style="padding:10px; overflow:auto; text-align:center;background:#FFFFFF;">
	<div class="navbar">
		<div class="navbar-inner" style="background:#ffffff;margin-left:0;"> 
	        <b><span id="labelusername">${(user.username)!''}<span>，</span><span id="labelwelcome"></span><span>欢迎使用${setting.name}系统</span></b>
	        <a href="javascript:void(0)" id="usersetup" style="display:none">账号设置</a>
        </div>
    </div>
	<div class="notebook">
	</div>
</body>
</html>