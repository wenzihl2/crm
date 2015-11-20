<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>会员中心</title>
		[#include "/default/common/include.ftl" /]
		<link href="${base}/theme/default/css/usercenter.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="login" mycenter="member_safe">
		[#include "/default/common/header.ftl"]
		<div class="blank"></div>
		<div id="content">
			<!--左边栏-->
			[#include "/default/common/member_center_left.ftl"]
			<!--右边栏-->
			<div class="right">
				<div class="mydd">
					<div class="mydd_m">
						验证手机
					</div>
					<div class="mydd_c" id="check">
						<div class="mc clearfix">
							<i class="icon-succ02"></i>
							<div class="success">
								<h3><span class="green">恭喜您，手机绑定成功！</span></h3>
							</div>
						</div>
					</div>
				</div>
				<!--猜你喜欢-->
			</div>
			<div class="clear"></div>
		</div>
		[#include "/default/common/foot.ftl"/]
	</body>
</html>