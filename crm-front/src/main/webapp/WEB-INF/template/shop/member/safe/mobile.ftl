<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-绑定手机</title>
[#include "/shop/common/include.ftl" /]
<script type="text/javascript">
//发送短信
function sendMessage() {
	if (!checkMobile()) {
		return;
	}
	$.ajax({
		url : "${base}/member/safe/mobileVerify/sendSms",
		type : 'post',
		success : function(data) {
			if (data.type == "SUCCESS") {
				$("#captchaButton").hide();
				$("#jumpToButton").css("display", "inline");
				$("#member_mobile").prop("readonly",true);
				countDown(120);
				return;
			} else {
				$("#captchaButton").show();
				$("#jumpToButton").css("display", "inine");
				$.message(data);
			}
		}
	});
}

//倒计时控制
function countDown(secs) {
	$('#jumpTo').html(secs);
	if (--secs > 0) {
		setTimeout("countDown(" + secs + ")", 1000);
	} else {
		$("#captchaButton").show();
		$("#jumpToButton").css("display", "none");
		$("#member_mobile").removeProp("readonly");
	}
}

//验证手机验证码
function checkRecoverKey() {
	var recoverKey = $("#member_recoverKey").val();
	if ($.trim(recoverKey) == "") {
		$("#recoverKey_message").html("请输入验证码！");
		$("#recoverKey_message").css("display", "");
		return false;
	}
	var reg = /^[0-9]{6}$/;
	if (!reg.test(recoverKey)) {
		$("#recoverKey_message").html("请输入正确的验证码！");
		$("#recoverKey_message").css("display", "");
		return false;
	}
	$("#recoverKey_message").css("display", "none");
	return true;
}

//验证手机
function checkMobile() {
	var member_mobile = $("#member_mobile").val();
	if ($.trim(member_mobile) == "") {
		$("#mobile_message").html("请输入手机号码！");
		$("#mobile_message").css("display", "");
		return false;
	}
	var reg = /^0?1[3458]\d{9}$/;
	if (!reg.test(member_mobile)) {
		$("#mobile_message").html("请正确填写手机号码！");
		$("#mobile_message").css("display", "");
		return false;
	}
	$("#mobile_message").css("display", "none");
	return true;
}
</script>
</head>
<body class="login" mycenter="member_safe">
	[#include "/shop/common/head.ftl"]
	<div class="blank"></div>
	<div id="content">
		<!--左边栏-->
		[#include "/shop/common/member_left.ftl"]
		<!--右边栏-->
		<div class="right">
			<div class="mydd">
				<div class="mydd_m">绑定手机</div>
				<div class="mydd_c">
					<div id="step2" class="step">
						<ul>
							<li class="fore2">1.安全验证</li>
							<li class="fore1">2.修改绑定<b></b></li>
							<li class="fore2">3.完成</li>
						</ul>
					</div>
					<form id="inputForm" class="validate" action="updateMobile" method="post">
						[#assign token_name = "${crm_wzbuzz_token_name}" /]
						<input type="hidden" name="crm_wzbuzz_token_name" value="${token_name}"/>
						<input type="hidden" name="${token_name}" value="${(Request["${token_name}"])!''}"/>
						<input type="hidden" name="r" value="${(RequestParameters["r"])!''}"/>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="liuyan">
							<tbody>
								<tr>
									<td align="right">我的手机号：</td>
									<td>
										<input name="newMobile" id="member_mobile" type="text" size="30" class="inputBg" maxlength="13">
										<span id="mobile_message" class="ts_on" style="display:none; color:red">请正确填写手机格式！</span>
									</td>
								</tr>
								<tr>
									<td align="right">&nbsp;</td>
									<td height="45">
										<div id="jumpToButton" style="display:none">
											验证码已发送...<span id="jumpTo">60</span>秒后可以重新发送&nbsp;
										</div><a id="captchaButton" href="javascript:sendMessage()" class="btn-black"><i></i>获取短信验证码</a>
									</td>
								</tr>
								<tr>
									<td align="right">请填写手机验证码：</td>
									<td>
										<input name="smsRecoverKey" id="member_recoverKey" type="text" size="20" class="inputBg" maxlength="13">
										<span id="recoverKey_message" class="ts_on" style="display:none; color:red">请正确填写手机格式</span>
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">&nbsp;</td>
									<td height="45">
										<input class="btn-input-red63" type="submit" value="提 交"/>
										&nbsp;&nbsp;
										<input class="btn-input-black63" type="button" onclick="location.href='${base}/member/safe'" value="返  回">
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--猜你喜欢-->
		</div>
		<div class="clear"></div>
	</div>
	[#include "/shop/common/foot.ftl"]
</body>
</html>