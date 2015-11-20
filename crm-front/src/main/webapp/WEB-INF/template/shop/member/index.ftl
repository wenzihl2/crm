<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-个人信息</title>
[#include "/shop/common/include.ftl" /]
<script type="text/javascript">
$().ready( function(){
	var $inputForm = $("#inputForm");
	var $mobile = $("#mobile");             //手机
	
	var $inputForm = $("#inputForm");
	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/common/area.jhtml"
	});
	// 表单验证
	$inputForm.validate({
		rules: {
			"realName": {
				required: true
			},
			"email": {
				email: true
			},
			"area.id": {
				required: true
			}
		},
		messages: {
			"realName": {
				required: "真实姓名不能为空"
			},
			"email": {
				email: "邮件格式不正确",
			},
			"area.id": {
				required: "地区必选",
			}			
		},
		errorPlacement: function(error, element){
			error.addClass('fieldError');
			if (element.is("[name='area.id']")) {
				error.appendTo($("#areaError"));
			}else {
				error.appendTo(element.parent());
			}
		},
		submitHandler: function(form) {
			$(inputForm).find(":submit").attr("disabled", true);
			inputForm.submit();
		}
	});
	$mobile.keypress(function(event) {
		var regExp = /[0-9]$/;
		var $this = $(this);
		var key = event.keyCode?event.keyCode:event.which;
		if (key == 13 || key == 8 || key == 116 || (key >= 48 && key <= 57) || (key == 46 && $this.val().indexOf(".") == -1)) {
			return true;
		}else {
			return false;
		}
	});
});
</script>
</head>
<body class="login" mycenter="member_profile">
	<div class="blank"></div>
	<div id="content">
		<!--左边栏-->
		[#include "/shop/common/member_left.ftl"]
		<!--右边栏-->
		<div class="right">
			<div class="mydd">
				<div class="mydd_m">个人信息</div>
				<div class="mydd_c">
					<form id="inputForm" action="${base}/member/update.jhtml" method="post" class="validate">
						<input type="hidden" name="token" value="${(token)!''}"/>
						<input type="hidden" name="id" value="${(member.id)!''}" />
						<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="liuyan">
							<tbody>
								<tr>
									<td width="28%" style="height:35px;" height="34" align="right" >您的真实姓名：</td>
									<td width="72%" >
										<p>
											<input type="text" name="realName" class="inputBg {required: true,minlength: 4, maxlength: 20}" value="${(member.realName)!''}" size="25" maxlength="10"/>
											<span class="red"> *</span>
										</p>
									</td>
								</tr>
								<tr>
									<td height="34" align="right" >性别：</td>
									<td >
										[#if member.gender??]
											<input type="radio" name="gender" [#if (member.gender==0)]checked[/#if] value="0" style="margin-bottom:5px"/>男 &nbsp;&nbsp;
											<input type="radio" name="gender" [#if (member.gender==1)]checked[/#if] value="1" style="margin-bottom:5px"/>女
										[#else]
											<input type="radio" name="gender" checked value="0" style="margin-bottom:5px"/>男 &nbsp;&nbsp;
											<input type="radio" name="gender" value="1" style="margin-bottom:5px"/>女
										[/#if]
									</td>
								</tr>
								<tr>
									<td height="34" align="right">所在地区：</td>
									<td >
										<label for="select"></label>
										<input type="hidden" name="area.id" treePath="${(member.area.path)!''}" class="areaSelect" value="${(member.area.id)!''}" />
										<span class="red" id="areaError"> *</span>
									</td>
								</tr>
								<tr>
									<td height="34" align="right">地址：</td>
									<td >
										<input type="text" name="address" treePath="${(member.address)!''}" size="25" class="inputBg" value="${(member.address)!''}" maxlength="32"/>
									</td>
								</tr>
								<tr>
									<td height="34" align="right" >E-mail：</td>
									<td >
										<input type="text" name="email" class="inputBg {required: true, email: true}" value="${(member.email)!''}" size="25" maxlength="32" disabled="disabled" style="float:left"/>
										[#if member.emailChecked??&&member.emailChecked==true]
											<a href="${base}/member/safe/checkEmail" class="lan" style="padding-left:5px;line-height:22px">修改</a>
										[#else]
											<a href="${base}/member/safe/checkEmail" class="lan" style="padding-left:5px;line-height:22px">立即验证</a>
										[/#if]
									</td>
								</tr>
								<tr>
									<td width="28%" style="height:35px;" height="34" align="right" >您的手机：</td>
									<td width="72%" >
										<input type="text" id="mobile" name="mobile" class="inputBg (required: true,mobile：true)" value="${(member.mobile)!''}" size="25" disabled="disabled" style="float:left"/>
										[#if member.mobileChecked??&&member.mobileChecked==true]
											<a href="${base}/member/safe/checkMobile" class="lan" style="padding-left:5px;line-height:22px">修改</a>
										[#else]
											<a href="${base}/member/safe/checkMobile" class="lan" style="padding-left:5px;line-height:22px">立即验证</a>
										[/#if]
									</td>
								</tr>
								<tr>
									<td width="32%" height="34" align="right" >出生日期：</td>
									<td width="68%" >
										<input type="text" name="birth" class="inputBg" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true,maxDate:'%y-%M-%d'})" value="${(member.birth?string('yyyy-MM-dd'))!''}" size="25"/>
									</td>
								</tr>
								<tr>
									<td width="28%" style="height:35px;" height="34" align="right" >邀请人：</td>
									<td width="72%" >
										<p>
											<input disabled="disabled"  type="text" class="inputBg" value="${(member.inviter.username)!''}" size="25" maxlength="20">
										</p>
									</td>
								</tr>
								<tr>
									<td height="34" align="right" ></td>
									<td >
										<input type="submit" value="提 交"  class="btn-input-red63" hidefocus="true">&nbsp;&nbsp;
										<input type="button" onclick="location.href='${shopUrl}member/index.jhtml'" value="返 回"  class="btn-input-black63" hidefocus="true">
									</td>
								</tr>
							</tbody>
						</table>
					</from>
				</div>
			</div>
			<!--猜你喜欢-->
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>