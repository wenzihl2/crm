<!DOCTYPE HTML>
<html>
<head>
[#include "/shop/common/include.ftl" /]
<link href="${base}/theme/shop/css/help.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var timer;
	var count = 120;
	$(function(){
		$("#getCode").click(function(){
			var mobile = $("#mobile").val();
			if($.trim(mobile) == '' || mobile.length != 11){
				$.message("WARN","手机号码有误");
				return;
			}
			$.ajax({
				url : "${base}/register/sendSms",
				data: {"mobile": mobile},
				dataType: "json",
				type:"post",
				async: false,
				success: function(data) {
					var content = data.error;
					if(data.error == undefined && data.type != 'ERROR'){
						$("#getCode").attr("disabled",true);
						timer = setInterval(countDown,1000);
					}
					$.message(data);
				}
			});
		});
		
		jQuery.validator.addMethod("password", function(value, element) {   
		    return this.optional(element) || (checkPassword(value).success);
		}, "密码长度为6-20个字符，由字母、数字、符号的两种以上组合");
		
		$inputForm = $("#registForm");
		$inputForm.validate({
			rules: {
				"mobile": {
					required: true,
					digits:true,
					minlength:11,
					maxlength:11
				},
				"passwd": {
					required: true,
					password: true
				},
				"repasswd": {
					equalTo: "#passwd"
				},
				"code": {
					required: true
				}
			},
			messages: {
				"mobile": {
					required: "手机号码不能为空",
					digits:"手机号码错误",
					minlength:"手机号码错误",
					maxlength:"手机号码错误"
				},
				"passwd": {
					required: "密码不能为空"
				},
				"repasswd": {
					equalTo: "密码不一致"
				},
				"code": {
					required: "验证码不能为空"
				}
			},
			errorPlacement: function(error,element){
				$(element).parent().find(".kv-item-text").html(error);
			}
		});
		
		$("#submitBtn").click(function(){
			if($inputForm.valid()){
				$.ajax({
					url: "${base}/common/publicKey",
					type: "GET",
					dataType: "json",
					async : false,
					cache: false,
					success: function(data){
						var rsaKey = new RSAKey();
						rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
						var enPassword = hex2b64(rsaKey.encrypt($("#passwd").val()));
						$("#enPassword").val(enPassword);
					}
				});
				$("#passwd").attr("disabled",true);
				$("#repasswd").attr("disabled",true);
				$(this).attr("disabled", true);
				$inputForm.submit();
			}
		});
	});
	
	function countDown(){
		if(count <= 0){
			$("#getCode").removeAttr("disabled");
			clearInterval(timer);
			$("#codeInfo").html("您可以重新获取验证码，此服务免费");
		}else{
			$("#codeInfo").html(count + "秒后,您可以重新获取验证码，此服务免费");
		}
		count--;
	}
	
</script>
</head>
<body >
	[#include "/shop/common/head.ftl"/]
    <div id="o-header-2014" class="header-cheek">
		<div class="w clearfix" id="header-2014">
			<div id="logo-tuan" class="ld">
				<a class="link1" href="${shopUrl}" hidefocus="true"></a>
				<a class="link2" href="${shopUrl}" hidefocus="true"></a>
			</div>
			<span class="cl"></span>
		</div>
	</div> 
	<div class="container toppadding  bottompadding">
	<div class="col-sm-12">
    <form class="form-signin" action="" method="post" enctype="multipart/form-data">
		<p>已注册过？<a href="/login.jhtml">立即登录</a></p>
		<label for="username" class="sr-only">username</label>
		<input type="text" id="username" class="form-control" placeholder="手机号" required="" autofocus="">
		<label for="inputPassword" class="sr-only">Password</label>
		<input type="password" id="inputPassword" class="form-control" placeholder="密码" required="">
		<label for="reinputPassword" class="sr-only">Password</label>
		<input type="password" id="reinputPassword" class="form-control" placeholder="再次输入密码" required="">
		<div class="col-xs-6 padding-no" style="padding-right:15px;">
			<input type="text" id="code" class="form-control" placeholder="验证码">
		</div>
		<div class="col-xs-6 padding-no">
			<button class="btn btn-lg btn-primary btn-block">免费获取验证码</button>
		</div>
		<div class="col-xs-12 padding-no">
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">我以认真阅读并同意天天省的《用户注册协议》</label>
			</div>
		</div>
		<button class="btn btn-lg btn-primary btn-block">注册</button>
	</form>
	</div>
</div>
	[#include "/shop/common/foot.ftl"/]
</body>
</html>