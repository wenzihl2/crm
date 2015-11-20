<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm" /]
[#include "/admin/include/upload.ftl"]
[#if !m??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
<script type="text/javascript">
$(function(){
	var mainform = $("#mainform");
    form = mainform.ligerForm(); 
	//表单底部按钮 
    LG.setFormDefaultBtn(f_cancel, function(){
		f_save(mainform, "advert");
	});
	$(".browserButton").browser();
});
</script>
</head>
<body style="padding:10px; ">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" class="validate" id="mainform">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}"/>
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<th class="l-table-edit-td">广告名称：</th>
				<td class="l-table-edit-td">
					<input type="text" name="name"  value="${(m.name)!''}"	
						class="{required: true, maxlength: 20}" />				
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">广告位置：</th>
				<td class="l-table-edit-td">
					[@u.select_map headerKey="" headerValue="请选择..." value="${(m.position.id)!''}"
							headerButtom="false" list=DictionaryUtils.category("advertPosition") validate="{required: true}"
							name="position.id" options="{width: 80}"/]
				</td>										
			</tr>
			<tr>
				<th class="l-table-edit-td">是否开启：</th>	
				<td class="l-table-edit-td">[@u.radio name= "isOpen" list={"true":"是", "false": "否"} 
						value="${(m.isOpen?string('true', 'false'))!'false'}"/]
				</td>									
			</tr>				
			<tr>
				<th class="l-table-edit-td">起始时间：</th>				
				<td class="l-table-edit-td">
					<input type="text" id="startTime" name="startTime" value="${(m.startTime?string('yyyy-MM-dd'))!''}"
					 	class="WdatePicker {dateFmt:'yyyy-MM-dd'}" />
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">终止时间：</th>
				<td class="l-table-edit-td">
					<input type="text" id="endTime" name="endTime" value="${(m.endTime?string('yyyy-MM-dd'))!''}"
					 	class="WdatePicker {dateFmt:'yyyy-MM-dd'}" />
				</td>			
			</tr>
			<tr>
				<th class="l-table-edit-td">广告链接：</th>
				<td class="l-table-edit-td">
					<input type="text"  name="url" value="${(m.url)!''}"/>
				</td>					
			</tr>
			<tr>
				<th class="l-table-edit-td">路径：</th>
				<td class="l-table-edit-td">
					<ul>
						<li class="l-fieldcontainer">
							<ul>
								<li><input type="text" value="${(m.logo)!''}" name="logo"></li>
								<li><input type="button" value="选择图片" class="button browserButton"></li>
							</ul>
						</li>
					</ul>
				</td>									
			</tr>
			<tr>
				<th class="l-table-edit-td">背景图片：</th>
				<td class="l-table-edit-td">
					<ul>
						<li class="l-fieldcontainer">
							<ul>
								<li><input type="text" value="${(m.background)!''}" name="background"></li>
								<li><input type="button" value="选择图片" class="button browserButton"></li>
							</ul>
						</li>
					</ul>
				</td>									
			</tr>
			<tr>
				<th class="l-table-edit-td">排序：</th>
				<td class="l-table-edit-td">
					<input type="text" value="${(m.priority)!''}" name="priority">
				</td>									
			</tr>
		</table>
	</form>
</body>
</html>