<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心</title>
[#include "/shop/common/include.ftl" /]
<link href="${base}/theme/default/css/usercenter.css" rel="stylesheet" type="text/css" />
</head>
	<!-- 邮箱是否已验证-->
	[#if member.emailChecked?? && member.emailChecked==true]
		[#assign emailChecked = true /]
	[#else]
		[#assign emailChecked = false /]
	[/#if]
	<!--手机是否已验证-->
	[#if member.mobileChecked?? && member.mobileChecked==true]
		[#assign mobileChecked = true /]
	[#else]
		[#assign mobileChecked = false /]
	[/#if]
	<!--支付密码是否已启用-->
	[#if member.isEnablepay_passwd?? && member.isEnablepay_passwd==true]
		[#assign isEnablepay_passwd = true /]
	[#else]
		[#assign isEnablepay_passwd = false /]
	[/#if]

<body class="login" mycenter="member_safe">
	<div class="blank"></div>
	<div id="content">
		<!--左边栏-->
		[#include "/shop/common/member_left.ftl"]
		<!--右边栏-->
		<div class="right">
			<div class="mydd">
				<div class="mydd_m">安全中心</div>
				<div class="mydd_c">
					<div class="mc jib">
						<strong class="fore">安全级别：
						[#if member.safeRank = '3'] <span class="green">强</span>[#elseif member.safeRank = '2']<span class="yellow">中 </span> [#else] <span class="red">弱 </span> [/#if] </strong>&nbsp;&nbsp;<span>建议您启动全部安全设置，以保障账户及资金安全。 </span>
					</div>
					<div class="m m5" id="safe05">
						<div class="mc">
							<div class="fore1">
								<s class="icon-03"></s><strong>登录密码</strong>
							</div>
							<div class="fore2">
								<span></span><span class="red2">互联网账号存在被盗风险，建议您定期更改密码以保护账户安全。</span>
							</div>
							<div class="fore3">
								<a href="safe/updatePassword" class="lan">立即修改</a>
							</div>
						</div>
						<!--邮箱验证-->
						<div class="mc">
							<div class="fore1">
								<s class="[#if emailChecked]icon-03[#else]icon-01[/#if]"></s><strong>邮箱绑定</strong>
							</div>
							<div class="fore2">
								[#if emailChecked == true]
									<span>您绑定的邮箱：</span>
									<strong>${(member.email)!''}</strong>
								[#else]
									<span>绑定后，可用于快速找回登录密码，接收账户余额变动提醒。</span>
								[/#if]
							</div>
							<div class="fore3">
								[#if emailChecked == true]
									<a href="safe/checkEmail" class="lan">立即修改</a>
								[#else]
									<a class="btn-red25" href="safe/checkEmail"><i></i>立即绑定</a>
								[/#if]
							</div>
						</div>
						<!--手机验证-->
						<div class="mc">
							<div class="fore1">
								<s class="[#if mobileChecked]icon-03[#else]icon-01[/#if]"></s><strong>手机绑定</strong>
							</div>
							<div class="fore2">
								[#if mobileChecked == true]
									<span>您绑定的手机：</span><strong>${(member.secretMobile)!''}</strong>
								[#else]
									<span>绑定后，可用于快速找回登录密码，接收账户余额变动提醒。</span>
								[/#if]
							</div>
							<div class="fore3">
								[#if mobileChecked == true]
									<a href="safe/updateMobile" class="lan">立即修改</a>
								[#else]
									<a class="btn-red25" href="safe/updateMobile"><i></i>立即绑定</a>
								[/#if]
							</div>
						</div>
						<!--支付密码-->
						<div class="mc">
							<div class="fore1">
								<s class="[#if isEnablepay_passwd]icon-03[#else]icon-01[/#if]"></s><strong>支付密码</strong>
							</div>
							<div class="fore2">
								启用支付密码后，在使用账户中余额或优惠劵等资产时，需输入支付密码。
							</div>
							<div class="fore3">
								[#if isEnablepay_passwd == true]
									<a href="safe/updatePayPassword" class="lan">立即修改</a>
								[#else]
									<a href="safe/updatePayPassword"><i></i>立即启用</a>
								[/#if]
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--猜你喜欢-->
		</div>
		<div class="clear"></div>
	</div>
	[#include "/shop/common/foot.ftl"]
</body>
</html>