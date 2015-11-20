<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/admin/include/head.htm">
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");  
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "member_list");
		});
	});
</script>
</head>
<body style="padding:10px;">
<div id="tabcontainer" style="overflow:hidden;margin:3px;">
	<div id="tabcontainer" style="overflow:hidden;margin:3px;">
		<form id="mainform" class="validate" action="${base}/admin/member/modify.jhtml" method="post">
			<input type="hidden" name="memberId" value="${(id)!''}" />
			<table class="table">
				<tr>
					<th>类型 :</th>
					<td >
						<@u.select_map headerKey="" class="{required: true}"
											headerButtom="false" list=DictionaryUtils.getDetailTypeDictionaryData()
											name="detailType" />
					</td>
				</tr>
				<tr>
					<th>金额：</th>
					<td>
						<input type="text" name="amount" class="formText {required: true, number:true}" value=""/>
					</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td>
						<input type="text" name="remarks" class="formText" value=""/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>