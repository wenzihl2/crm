<!doctype html>
<html>
<head>
<title>管理登录</title>
[#include "/admin/include/head.htm"]
<link href="${base}/resource/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
// 登录页面若在框架内，则跳出框架
//if (self != top) {
//	top.location = self.location;
//};
$().ready( function() {
	
	var error = "${(error)!''}";
	if(error != ''){
		LG.showError(error);
	}

	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isSaveUsername = $("#isSaveUsername");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if($.cookie("adminUsername") != null) {
		$isSaveUsername.attr("checked", true);
		$username.val($.cookie("adminUsername"));
		$password.focus();
	} else {
		$isSaveUsername.attr("checked", false);
		$username.focus();
	}
	[#--
	/**提交表单验证,记住登录用户名
	$("#loginForm").submit( function() {
		if ($username.val() == "") {
			LG.showError("${message("admin.login.usernameRequired")}");
			return false;
		}
		if ($password.val() == "") {
			LG.showError("${message("admin.login.passwordRequired")}");
			return false;
		}
		if ($captcha.val() == "") {
			LG.showError("${message("admin.login.captchaRequired")}");
			return false;
		}
		
		if($isSaveUsername.attr("checked") == true) {
			$.cookie("adminUsername", $username.val(), {expires: 30});
		} else {
			$.cookie("adminUsername", null);
		}
		
		$.ajax({
			url: $loginForm.attr("action"),
			type: "POST",
			data: {
				username: $username.val(),
				enPassword: enPassword,
				captchaId: "${captchaId}",
				captcha: $captcha.val()
			},
			dataType: "json",
			cache: false,
			success: function(message) {
				if ($isSaveUsername.prop("checked")) {
					Cookie.set("adminUsername", $username.val(), 7 * 24 * 60 * 60, "/");
				} else {
					Cookie.remove("adminUsername");
				}
				$submit.prop("disabled", false);
				location.href = "${base}/admin/index.jhtml";
			},
			error: function(message) {
				$.message(message.content);
				$captcha.val("");
				$captchaImage.attr("src", "${base}/jcaptcha.jpg?timestamp=" + (new Date()).valueOf());
			}
		});
	});**/
	--]
	// 刷新验证码
	$captchaImage.click( function() {
		$captchaImage.attr("src", "${base}/jcaptcha.jpg?timestamp=" + (new Date()).valueOf());
	});
});
</script>
</head>
<body class="login">
	<div class="body">
		<div class="loginBox">
			<div class="boxTop"></div>
			<div class="boxMiddle">
				<form id="loginForm" action="${base}/admin/login.jhtml" class="form" method="post">
					<div class="loginLogo">
				    	<img src="${base}/resource/images/login_logo.png" />
				    </div>
		            <table class="loginTable">
		            	[#--
		            	<tr>
		                    <th>${message("admin.login.company")}:</th>
							<td>
		                    	<div class="formText">
		                    		<input type="text" id="company" name="company" />
		                    	</div>
		                    </td>
		                </tr>
		                --]
		            	<tr>
		                    <th>${message("admin.login.username")}:</th>
							<td>
		                    	<div class="formText">
		                    		<input type="text" id="username" name="username" />
		                    	</div>
		                    </td>
		                </tr>
		                <tr>
							<th>${message("admin.login.password")}:</th>
		                    <td>
		                    	<div class="formText">
		                    		<input type="password" id="password" name="password" />
		                    	</div>
		                    </td>
		                </tr>
		                [#--
		                <tr>
		                	<th>${message("admin.captcha.name")}:</th>
		                    <td>
		                    	<div class="formTextS">
		                    		<input type="text" id="captcha" name="jcaptchaCode" />
		                    	</div>
		                    	<div class="captchaImage">
		                   			<img id="captchaImage" src="${base}/jcaptcha.jpg" alt="换一张" />
		                   		</div>
		                    </td>
		                </tr>
		                --]
		                <tr>
		                	<th>&nbsp;</th>
		                    <td>
		                    	<input type="checkbox" name="rememberMe" value="true">
		                    	<label for="isSaveUsername">&nbsp;${message("admin.login.rememberUsername")}</label>
		                    </td>
		                </tr>
		                <tr>
		                	<th>&nbsp;</th>
		                    <td>
		                        <input type="button" class="homeButton ignoreForm" value="" onclick="window.open('${base}/')" hidefocus="true" />
		                        <input type="submit" class="submitButton ignoreForm" value="登 陆" hidefocus="true" />
		                        <input type="button" class="helpButton ignoreForm" value="帮 助" onclick="window.open('http://shop.zgwanshan.com')" hidefocus="true" />
		                    </td>
		                </tr>
		            </table>
		        </form>
			</div>
			<div class="boxBottom"></div>
		</div>
		<div class="blank"></div>
	</div>
</body>
</html>