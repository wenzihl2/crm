<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm" /]
<script type="text/javascript" src="${base}/resource/thirdparty/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resource/js/input.js"></script>
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
		f_save(mainform, "article");
	});
});
</script>
</head>
<body style="padding:10px;">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" class="validate" id="mainform">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${(m.id)!''}"/>
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="width:100%;">
			<tr>
				<th class="l-table-edit-td">文章标题:</th>
				<td class="l-table-edit-td">
					<input type="text" name="title" class="{required: true, maxlength: 20}" value="${(m.title)!''}" />	 
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">文章分类:</th>
				<td class="l-table-edit-td">
					<input type="text" ltype="select" id="categoryId" validate="{required: true}" options='{
							initValue: "${(m.category.id)!}",
			        		initText: "${(m.category.name)!}",
			                width: 180,
			                selectBoxWidth: 200,
			                selectBoxHeight: 200, valueField: "id", textField: "name", valueFieldID:"category.id",
			                treeLeafOnly:false,
			                tree: { url: base + "/admin/base/dictionary/article/tree.jhtml", checkbox: false,
			            	parentIDFieldName :"pId", textFieldName: "name"}}'>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">设置:</th>
				<td class="l-table-edit-td">
					<label>
						<input type="checkbox" name="isPublication" value="true"
							[#if !isAdd?? && m.isPublication] checked="checked"[/#if] />发布
					</label>
					<label>
						<input type="checkbox" name="isRecommend" value="true" 
							[#if !isAdd?? && m.isRecommend] checked="checked"[/#if] />推荐
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true"
							[#if !isAdd?? && m.isTop] checked="checked"[/#if] />置顶
					</label>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">内容:</th>
				<td class="l-table-edit-td">
					<textarea name="content" id="m.content" style="width: 100%;"
						class="editor">${(m.content)!''}</textarea>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>