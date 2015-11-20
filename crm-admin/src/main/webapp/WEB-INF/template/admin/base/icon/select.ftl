<script type="text/javascript">
	$(function(){
		// Tab效果
		$("#navtab3").ligerTab({});
	});
</script>
<div class="sys-icon-list" style="height:200px; overflow:hidden; border:1px solid #A3C0E8; ">
    <div id="navtab3" style="height:200px; ">
		[#list types! as t]
		<div title="${t.info}" tabid="${t}">
			<div class="tab-pane [#if t_index ==0] lselected="true"[/#if]" id="${t}"  style="height:300px">
            	[#list icons! as icon]
            		[#if icon.type == t]
                    <a class="btn sys-icon-btn">
                        <i class="${icon.identity}"></i>
                    </a>
                    [/#if]
                [/#list]
            </div>
		</div>
		[/#list]
	</div>
</div>