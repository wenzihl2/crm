<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-添加银行账号</title>
[#include "/shop/common/include.ftl" /]
<link href="${base}/theme/default/css/usercenter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/theme/default/js/jquery.validate.js"></script>
<script type="text/javascript">
	$(function() {
		$("#J-back").click(function(){
			window.location = "${base}/member/account";
		});
		
		jQuery.validator.addMethod("isIdCard", function(value, element) {   
		    var regex1 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
		    var regex2 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		    return this.optional(element) || (regex1.test(value)) || (regex2.test(value));
		}, "请正确填写您的身份证号码");
		
		$inputForm = $("#accountForm");
		
		$inputForm.validate({
			rules: {
				"userName": {
					required: true
				},
				"idCard": {
					required: true,
					isIdCard: true
				},
				"cardNum": {
					required: true,
					digits:true
				},
				"decode": {
					required: true
				},
				"organization": {
					required: true
				}
			},
			messages: {
				"userName": {
					required: "真实姓名不能为空"
				},
				"idCard": {
					required: "身份证不能为空"
				},
				"cardNum": {
					required: "银行卡号不能为空",
					digits:"请输入正确的银行卡号"
				},
				"decode": {
					required: "所属银行不能为空"
				},
				"organization": {
					required: "开户行不能为空"
				}		
			},
			submitHandler: function(form) {
				$inputForm.find(":submit").attr("disabled", true);
				inputForm.submit();
			}
		});
	});
</script>
</head>
<body mycenter="member_bank">
	<div id="content">
		[#include "/shop/common/member_left.ftl"]
		<div class="right">
			<div class="mydd">
				<div class="mydd_m">
					<span class="zxkf"></span>[#if !account??]添加[#else]编辑[/#if]银行卡
				</div>
				<div class="mydd_c">
					<div class="ui-grid-19">
						<div class="main-form">
							<form id="accountForm" class="ui-form ui-form-bg fn-mt30 validate" action="create" method="post">
								<input type="hidden" name="id" value="${(account.id)!''}" />
								<input type="hidden" name="entry" value="${(entry)!''}"/>
								<fieldset>
									<div class="ui-tiptext-container ui-tiptext-container-message fn-ml20">
										<p class="ui-tiptext ui-tiptext-message">
											<i class="ui-tiptext-icon iconfont" title="提示"></i>
											请填写以下信息，用于提现等功能。
										</p>
									</div>
									<div class="ui-form-item ui-form-name">
										<label class="ui-label">真实姓名</label>
										<input name="userName" value="${(account.userName)!''}" class="ui-input ui-input-pl10" />
									</div>
									<div class="ui-form-item">
										<label class="ui-label">身份证号</label>
										<input name="idCard" value="${(account.idCard)!''}" class="ui-input ui-input-pl10" />
									</div>
									<div class="ui-form-item ui-bank-con">
										<label for="J-bankid" class="ui-label">银行卡卡号</label>
										<input class="ui-input ui-input-bankid J-input-zoom" value="${(account.cardNum)!''}" name="cardNum" type="text">
									</div>
									<div class="ui-form-item">
										<label for="J-mobilenum" class="ui-label">所属银行</label>
										<select class="ui-select ui-select-pl10" name="code" id="code">
											[#list banks as bank]
												<option value="${bank.code}" [#if account?? && account.attribution = bank.name]selected="true"[/#if]>${bank.name}</option>
											[/#list]
										</select>
									</div>
									<div class="ui-form-item">
										<label for="J-mobilenum" class="ui-label">开户行</label>
										<input name="organization" class="ui-input ui-input-pl10" type="text" value="${(account.organization)!''}" />
									</div>
									<div class="ui-form-item fn-pt20">
										<div id="submitBtn" class="ui-button ui-button-lorange">
											<input id="J-submit" type="submit" class="ui-button-text" value="确定">
										</div>
										<div id="backBtn" class="ui-button ui-button-lorange">
											<input id="J-back" type="button" class="ui-button-text" value="返回">
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>