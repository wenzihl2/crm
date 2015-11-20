<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提示信息</title>
[#include "/shop/common/include.ftl" /]
</head>
<body class="login">
	[#include "/shop/common/head.ftl"]
	<div class="blank"></div>
	<div id="content">
		<!--左边栏-->
		[#include "/shop/common/member_left.ftl"]
		<!--右边栏-->
		<div class="right">
			<div class="mydd" style="border: 1px solid #FFD9A9;">
				<div class="mydd_c" id="success">
					<div class="mc" style="padding-left: 300px;">
						<i class="icon-succ02"></i>
						<div class="success">
							<h3><span class="green"> 您的操作已成功! </span></h3>
							<p class="fh">
								<input type="button" value="确认"  class="btn-input-red63" [#if (RequestParameters["redirectionUrl"])??]onclick="window.location.href='${(RequestParameters["redirectionUrl"])!''}'"[#else]onclick="window.history.back(); return false;"[/#if] value="确  定">
							</p>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<!--猜你喜欢-->
		</div>
		<div class="clear"></div>
	</div>
	[#include "/shop/common/footer.ftl"]
</body>
</html>