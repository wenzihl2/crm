<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
$(function(){
	var mainform = $("#mainform");
	mainform.ligerForm(); 
	//表单底部按钮 
    LG.setFormDefaultBtn(f_cancel, function(){
		f_save(mainform, "credits");
	});
});
</script>
</head>
<body style="padding:10px;">
	<form id="mainform" class="validate" action="create.jhtml" method="post">
		<input type="hidden" name="username" value="${(member.username)!''}"/>
		<table class="table">
			<tr>
				<th>类型 :</th>
				<td >
					[@u.select_map headerKey="" class="{required: true}"
							headerButtom="false" list=DictionaryUtils.getAllCreditsType()
							name="creditsType" /]
				</td>
			</tr>
			<tr>
				<th>金额:</th>
				<td >
					<input type="text" name="amount" class="formText {required: true, min:0 , digits: true}"/>
				</td>
			</tr>
			<tr>
				<th>备注 :</th>
				<td >
					<input type="text" name="remark" class="formText {required: true, }"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>