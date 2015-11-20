<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
[#if !m??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");
		mainform.ligerForm(); 
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "member");
		});
	});
	/**
	//会员卡字符串验证
	jQuery.validator.addMethod("mbrcard", function(value, element) {
		return this.optional(element) || /^\d{8}$/.test(value);
	}, "会员名由8位数字组成!");
	//会员密码字符串验证
	jQuery.validator.addMethod("password", function(value, element) {
		if(isAllSameChars(value)){
			return false;
		}
		return this.optional(element) || /^\d{6}$/.test(value) ;
	}, "密码只能为6位数字!");
   
    //判断字符串是否为同一字符
   	function isAllSameChars(d) {
		if (d == null || d.length < 2) {
			return false;
		}
		var c = d.charCodeAt(0);
		var b = 1;
		for (b = 1; b < d.length; b++) {
			var a = d.charCodeAt(b);
			if (a != c) {
				return false;
			}
		}
		return true;
	}**/
</script>
</head>
<body style="padding:10px;">
	<form id="mainform" class="validate" action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post">
		[#if isEdit??]
			<input type="hidden" name="id" value="${(m.id)!''}" />
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td colspan="2"><h2>基本信息</h2></td>
			</tr>
			<tr>
				<th class="l-table-edit-td">用户名：</th>
				<td class="l-table-edit-td">
					<input type="text" name="username" class="formText {required: true, minlength: 2, maxlength: 20, username: true}" value="${(m.username)!''}"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">密码：</th>
				<td class="l-table-edit-td">
					[#if isAdd??]
					<input type="password" name="passwd" class="formText {required: true, minlength: 6, maxlength: 6, password: true}" value=""/>
					[#else]
					<input type="password" name="passwd" class="formText {minlength: 6, maxlength: 6, password: true}" value=""/>
					[/#if]
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">手机：</th>
				<td class="l-table-edit-td">
					<input type="text" name="mobile" class="formText (phone:true)" value="${(m.mobile)!''}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">E-mail：</th>
				<td class="l-table-edit-td">
					<input type="text" name="email" class="formText {email: true}" value="${(m.email)!''}" />
				</td>
			</tr>
        	<tr>
          		<th class="l-table-edit-td">性别：</th>
          		<td class="l-table-edit-td">
          			[@u.radio name="gender" list={"0": "男", "1":"女"} value="${(m.gender)!'1'}"/]
		  		</td>
        	</tr>
			<tr>
				<th class="l-table-edit-td">出生日期：</th>
				<td class="l-table-edit-td">
			   		<input type="text" name="birth" class="formText WdatePicker {dateISO: true}" value="${((m.birth)?string('yyyy-MM-dd'))!''}" />
				</td>
			</tr>
			[#if isEdit??]
			<tr>
				<th class="l-table-edit-td">积分：</th>
				<td class="l-table-edit-td">
					<input type="text" name="credits" class="formText" value="${(m.credits)!'0'}"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">禁用状态：</th>
				<td class="l-table-edit-td">[@u.radio name="isAccountLocked" list={"true":"禁用", "false": "未禁用"} 
					value="${(m.isAccountLocked?string('true', 'false'))!'false'}"/]
				</td>
			</tr>
			[/#if]
		</table>
	</form>
</body>
</html>