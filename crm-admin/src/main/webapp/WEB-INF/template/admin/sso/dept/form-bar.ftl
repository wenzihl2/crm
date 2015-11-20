<script type="text/javascript">
	$(function(){
		var formBar = $(".form-bar")
		LG.addFormButtons([
		[#if op == '修改']
		{
			text:'修改',
			appendTo: formBar,
			onclick: function(){
				mainform.submit();
			}
		}
		[/#if]
		
		[#if op == '删除']
		{
			text:'删除',
			appendTo: formBar,
			onclick: function(){
				mainform.submit();
			}
		}
		[/#if]
		
		[#if op == '添加子节点']
		{
			text:'添加子节点',
			appendTo: formBar,
			onclick: function(){
				mainform.submit();
			}
		}
		[/#if]
		
		[#if op == '移动节点']
		{
			text:'孩子节点',
			appendTo: formBar,
			onclick: function(){
				$("#moveType").val("inner");
				mainform.submit();
			}
		}, {
			text:'之后',
			appendTo: formBar,
			onclick: function(){
				$("#moveType").val("next");
				mainform.submit();
			}
		}, {
			text:'之前',
			appendTo: formBar,
			onclick: function(){
				$("#moveType").val("prev");
				mainform.submit();
			}
		}
		[/#if]
		]);
	});
</script>
<div class="form-bar"><div class="form-bar-inner"></div></div>