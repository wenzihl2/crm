<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-修改密码</title>
[#include "/shop/common/include.ftl" /]
<script type="text/javascript">
	$(function() {
		var $inputForm = $("#inputForm");
		var $password = $("#password");
		var $repassword = $("#repassword");
		$("#p_msg").html("6-20个字符，由字母、数字、符号的两种以上组合").css('color', '');
		//事件
		$password.blur(checkPass).focus(checkPass);
		$repassword.blur(checkRepPassword).focus(checkRepPassword);
		$inputForm.submit(function() {
			if (!checkPass() | !checkRepPassword()) {
				return false;
			}
			$("#doSubmit").attr("disabled", true);
			$.ajax({
				url : "${base}/common/publicKey",
				type : "GET",
				dataType : "json",
				async : false,
				cache : false,
				success : function(data) {
					var rsaKey = new RSAKey();
					rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
					enPassword = hex2b64(rsaKey.encrypt($("#password").val()));
					$("#newSecPwd").val(enPassword);
				}
			});
			return true;
		});
	});

	//检查密码
	function checkPass() {
		var error = checkPassword($.trim($("#password").val()));
		if (isEmptyPassword($.trim($("#password").val()))) {
			$("#p_msg").html("6-20个字符，由字母、数字、符号的两种以上组合").css('color', '');
			return false;
		}
		if (!error.success) {
			$("#p_msg").html(error.msg).css('color', 'red');
			return false;
		} else {
			$("#p_msg").html("6-20个字符，由字母、数字、符号的两种以上组合").css('color', '');
		}
		return error.success;
	}

	//检查重复密码
	function checkRepPassword() {
		if (isEmptyPassword($.trim($("#repassword").val()))) {
			$("#rep_msg").html("请再次输入您的新密码!");
			return false;
		}
		if ($.trim($("#password").val()) != $.trim($("#repassword").val())) {
			$("#rep_msg").html("两次密码输入不一致，请重新输入!").css('color', 'red');
			return false;
		}
		$("#rep_msg").html('');
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
					<div class="mydd_m">密码修改</div>
					<div class="mydd_c">
						<form id="inputForm" action="updatePassword" method="post">
							[#assign token_name = "${crm_wzbuzz_token_name}" /]
							<input type="hidden" name="crm_wzbuzz_token_name" value="${token_name}"/>
							<input type="hidden" name="${token_name}" value="${(Request["${token_name}"])!''}"/>
							<input type="hidden" name="newSecPwd" id="newSecPwd"/>
							<input type="hidden" name="r" value="${(RequestParameters["r"])!''}"/>
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="liuyan">
								<tbody>
									<tr>
										<td width="28%" align="right"  >新密码：</td>
										<td align="left"  >
											<input type="password" id="password" class="inputBg" size="25"/>
											<span id="p_msg"></span>
										</td>
									</tr>
									<tr>
										<td width="28%" align="right">确认密码：</td>
										<td align="left" >
											<input type="password" id = "repassword" class="inputBg" size="25"/>
											<span id="rep_msg"></span>
										</td>
									</tr>
									<tr>
										<td width="28%" align="right"></td>
										<td height="40" align="left" >
											<input id="doSubmit" type="submit" value="提交" class="btn-input-red63">
											&nbsp;&nbsp;
											<input class="btn-input-black63" type="button" onclick="location.href='${base}/member/safe.jhtml'" value="返  回">
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