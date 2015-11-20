<!doctype html>
<html>
<head>
<#include "/admin/include/head.htm">
<#include "/admin/include/upload.ftl">
<script type="text/javascript" src="${base}/resource/js/input.js"></script>
<title>站点设置</title>
<script type="text/javascript">
	$(function(){
		var $validateForm = $("#validateForm");   
		var $smtpFromMail = $("#smtpFromMail");
		var $smtpHost = $("#smtpHost");
		var $smtpPort = $("#smtpPort");
		var $smtpUsername = $("#smtpUsername");
		var $smtpPassword = $("#smtpPassword");
		var $toMailWrap = $("#toMailWrap");
		var $toMail = $("#toMail");
		var $mailTest = $("#mailTest");
		var $mailTestStatus = $("#mailTestStatus");
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			$validateForm[0].submit();
		});
		$("#tabcontainer").ligerTab();
		var $validateErrorContainer = $("#validateErrorContainer");
		var $validateErrorLabelContainer = $("#validateErrorContainer ul");
		
		$(".browserButton").browser();
		
		//邮件测试
		$mailTest.click(function() {
			var $this = $(this);
			if ($this.val() == '邮箱测试') {
				$this.val("发送邮件");
				$toMailWrap.css("display","");
			} else {
				function valid(element) {
					return $validateForm.validate().element(element);
				}
				$.ajax({
					url: "mailTest.jhtml",
					type: "POST",
					data: {smtpFromMail: $smtpFromMail.val(), smtpHost: $smtpHost.val(), smtpPort: $smtpPort.val(), smtpUsername: $smtpUsername.val(), smtpPassword: $smtpPassword.val(), toMail: $toMail.val()},
					dataType: "json",
					cache: false,
					beforeSend: function() {
						if (valid($smtpFromMail) & valid($smtpHost) & valid($smtpPort) & valid($smtpUsername) & valid($toMail)) {
							$mailTestStatus.html('<span class="loadingIcon">&nbsp;<\/span>');
							$mailTest.attr('disabled',"true");
						} else {
							return false;
						}
					},
					success: function(message) {
						if(message){
							$mailTestStatus.html('<span class="loadingIcon">邮件发送成功！<\/span>');
							$mailTest.attr('disabled',"true");
						}else{
							$mailTestStatus.html('<span class="loadingIcon">邮件发送失败！<\/span>');
							$mailTest.attr('disabled',"true");
						}
					}
				});
			}
		});
		
		// 表单验证
		$validateForm.validate({
			errorContainer: $validateErrorContainer,
			errorLabelContainer: $validateErrorLabelContainer,
			wrapper: "li",
			errorClass: "validateError",
			ignoreTitle: true,
			ignore: ".ignoreValidate",
			rules: {
				"name": "required",
				"url": "required",
				"logo": "imageFile",
				"email": "email",
				"loginFailureLockCount": {
					required: true,
					positiveInteger: true
				},
				"loginFailureLockTime": {
					required: true,
					digits: true
				},
				"smtpFromMail": {
					required: true,
					email: true
				},
				"smtpHost": "required",
				"smtpPort": {
					required: true,
					digits: true
				},
				"smtpUsername": "required"
			},
			messages: {
				"name": "请填写网店名称",
				"url": "请填写网店网址",
				"logo": "网店LOGO格式错误",
				"email": "E-mail格式不正确",
				"loginFailureLockCount": {
					required: "请填写连续登录失败最大次数",
					positiveInteger: "连续登录失败最大次数请输入合法的正整数"
				},
				"loginFailureLockTime": {
					required: "请填写自动解锁时间",
					digits: "自动解锁时间必须为零或正整数"
				},
				"smtpFromMail": {
					required: "请填写发件人邮箱",
					email: "发件人邮箱格式错误"
				},
				"smtpHost": "请填写SMTP服务器地址",
				"smtpPort": {
					required: "请填写SMTP服务器端口",
					digits: "SMTP服务器端口必须为零或正整数"
				},
				"smtpUsername": "请填写SMTP用户名"
			},
			submitHandler: function(form) {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		});
	});
</script>
<style type="text/css">
#tabcontainer .l-tab-links ul{margin-left:160px;}
#tabcontainer{padding-top:20px;}
.l-tab-links{background:none;}
.l-tab-links li{margin: 0 0 0 12px;}
</style>
</head>
<body>
<form action="update.jhtml" method="post" class="validate" id="validateForm" enctype="multipart/form-data">
<div class="validateErrorContainer" id="validateErrorContainer" style="display: none;">
	<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
	<ul style="display: none;"></ul>
</div>
<div id="tabcontainer" style="overflow:hidden;margin:3px;">
	<!-- 站点设置 begin -->
	<div title="基本设置">
		<table width="600" align="center" class="inputTable2" style="display:table;">
			<tr>
				<th>网站名称：</th>
				<td><input type="text" class="formText" value="${(setting.name)!''}" name="name"></td>
			</tr>
			<tr>
				<th>Logo: </th>
				<td>
					<div style="float:left;margin-right:20px;">
						<#--<input type="input" size="50" id="logo" name="logo" value="<@Pic pic=setting.logo />"/>-->
						<input type="button" value="选择图片" class="button browserButton">
					</div>
					<div style="margin-left:20px;">
						<#if setting.logo??>
						<a href="${base}${(setting.logo)!''}" target="_blank">
							<img alt="预览区" class="preImg" src="${base}${(setting.logo)!''}" />
						</a>
						</#if>
					</div>
				</td>
			</tr>
			<tr>
				<th>电话号码：</th>
				<td><input type="text" class="formText" value="${(setting.phone)!''}" name="phone"></td>
			</tr>
			<tr>
				<th>手机号码：</th>
				<td><input type="text" class="formText" value="${(setting.mobile)!''}" name="mobile"></td>
			</tr>
			<tr>
				<th>联系地址：</th>
				<td><input type="text" class="formText" value="${(setting.address)!''}" name="address"></td>
			</tr>
			<tr>
				<th>邮编：</th>
				<td><input type="text" class="formText" value="${(setting.zipCode)!''}" name="zipCode"></td>
			</tr>
			<tr>
				<th>联系人邮箱：</th>
				<td><input type="text" class="formText" value="${(setting.owner_email)!''}" name="owner_email"></td>
			</tr>
			<tr>
				<th>公司名称：</th>
				<td><input type="text" class="formText" value="${(setting.company)!''}" name="company"></td>
			</tr>
			<tr>
				<th>备案号：</th>
				<td><input type="text" class="formText" value="${(setting.certtext)!''}" name="certtext"></td>
			</tr>
			<tr>
				<th>是否开启网站:</th>
				<td><@u.radio name="isSiteEnabled" list={'true': '是', 'false':'否'} 
					value="${(setting.isSiteEnabled)?string('true', 'false')}"/></td>
			</tr>
			<tr>
				<th>域名：</th>
				<td><input type="text" class="formText" value="${(setting.domain)!''}" name="domain"></td>
			</tr>
			<tr>
				<th>部署路径：</th>
				<td><input type="text" class="formText" value="${(setting.contextPath)!''}" name="contextPath"></td>
			</tr>
			<tr>
				<th>端口号：</th>
				<td><input type="text" class="formText" value="${(setting.port)!''}" name="port"></td>
			</tr>
		</table>
	</div>
	
	<!-- 安全设置 -->
	<div title="安全设置">
		<table width="600" align="center" class="inputTable2" style="display:table;">
			<tr>
				<th>是否自动锁定账号：</th>
				<td><@u.radio name="isLoginFailureLock" list={"true": "是", "false": "否"}
					value="${(setting.isLoginFailureLock?string('true', 'false'))!'true'}" /></td>
			</tr>
			<tr>
				<th>连续登陆失败最大次数：</th>
				<td><input type="text" name="loginFailureLockCount" class="formText {required: true, positiveInteger: true}" 
					value="${(setting.loginFailureLockCount)!}" title="只允许输入正整数,当连续登录失败次数超过设定值时,系统将自动锁定该账号" /></td>
			</tr>
			<tr>
				<th>自动解锁时间：</th>
				<td><input type="text" name="loginFailureLockTime" class="formText {required: true, digits: true}" 
					value="${(setting.loginFailureLockTime)!}" title="只允许输入零或正整数,账号锁定后,自动解除锁定的时间,单位:分钟,0表示永久锁定" /></td>
			</tr>
			<tr>
				<th>允许上传图片扩展名：</th>
				<td><input type="text" name="uploadImageExtension" class="formText" 
					value="${(setting.uploadImageExtension)!}" title="多个扩展名请以(,)分隔" />
					(如果此处不填，则默认不能上传文件)
				</td>
			</tr>
			<tr>
				<th>允许上传Flash扩展名：</th>
				<td><input type="text" name="uploadFlashExtension" class="formText" 
					value="${(setting.uploadFlashExtension)!}" title="多个扩展名请以(,)分隔" />
					(如果此处不填，则默认不能上传文件)
				</td>
			</tr>
			<tr>
				<th>允许上传媒体扩展名：</th>
				<td><input type="text" name="uploadMediaExtension" class="formText" 
					value="${(setting.uploadMediaExtension)!}" title="多个扩展名请以(,)分隔" />
					(如果此处不填，则默认不能上传文件)
				</td>
			</tr>
			<tr>
				<th>允许上传文件扩展名：</th>
				<td><input type="text" name="uploadFileExtension" class="formText" 
					value="${(setting.uploadFileExtension)!}" title="多个扩展名请以(,)分隔" />
					(如果此处不填，则默认不能上传文件)
				</td>
			</tr>
			<tr>
				<th>图片上传路径：</th>
				<td><input type="text" style="width:300px" name="imageUploadPath" class="formText" 
					value="${(setting.imageUploadPath)!}"/>
				</td>
			</tr>
			<tr>
				<th>Flash上传路径：</th>
				<td><input type="text" style="width:300px" name="flashUploadPath" class="formText" 
					value="${(setting.flashUploadPath)!}"/>
				</td>
			</tr>
			<tr>
				<th>媒体上传路径：</th>
				<td><input type="text" style="width:300px" name="mediaUploadPath" class="formText" 
					value="${(setting.mediaUploadPath)!}" />
				</td>
			</tr>
			<tr>
				<th>文件上传路径：</th>
				<td><input type="text" style="width:300px" name="fileUploadPath" class="formText" 
					value="${(setting.fileUploadPath)!}" />
				</td>
			</tr>
			<tr>
				<th>文件上传最大值：</th>
				<td><@u.select list={"0":"禁止上传","512":"512KB","1024":"1M","2048":"2M","3072":"3M","5120":"5M","-1":"不限制"} 
					name="uploadLimit" value="${(setting.uploadLimit)!''}" />
				</td>
			</tr>
		</table>
	</div>
	<!-- 邮件设置 -->
	<div title="邮箱设置">
		<table width="600" align="center" class="inputTable2" style="display:table;">
			<tr>
				<th>发件人邮箱：</th>
				<td><input type="text" name="smtpFromMail" id="smtpFromMail"
					class="formText {required: true}" value="${(setting.smtpFromMail)!''}" /></td>
			</tr>
			<tr>
				<th>SMTP服务器地址：</th>
				<td><input type="text" name="smtpHost" id="smtpHost"
					class="formText {required: true}" value="${(setting.smtpHost)!''}" /></td>
			</tr>
			<tr>
				<th>SMTP服务器端口：</th>
				<td><input type="text" name="smtpPort" id="smtpPort"
					class="formText {required: true}" value="${(setting.smtpPort)!''}" /></td>
			</tr>
			<tr>
				<th>SMTP用户名：</th>
				<td><input type="text" name="smtpUsername" id="smtpUsername"
					class="formText {required: true}" value="${(setting.smtpUsername)!''}" /></td>
			</tr>
			<tr>
				<th>SMTP密码：</th>
				<td><input type="password" name="smtpPassword" id="smtpPassword"
					class="formText {required: true}" value="${(setting.smtpPassword)!''}" /></td>
			</tr>
			<tr>
				<th>邮箱配置测试：</th>
				<td>
					<span id="toMailWrap" style="display:none;">
					<div>收件人邮箱: </div>
					<input type="text" id="toMail" name="toMail" class="formText {email: true}" maxlength="200" />
					</span>
					<input type="button" id="mailTest" class="formButton" value="邮箱测试" hidefocus />
					<span id="mailTestStatus"></span> 
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</body>
</html>