<div class="left" id="memberCenterLeft">
	<div id="myws" class="m">
		<div class="mt">
			<h2>会员中心</h2>
		</div>
		<div class="mc">
			<ul>
				[@shop_navigationTop_list position="left"]
					[#list tag_list as tag]
						<li class="bt1" onClick="showMenu(a${tag_index}, this, event)">
							${tag.name}<em><img src="/theme/default/images/mc-left-down.gif" /></em>
						</li>
						[@shop_navigation_list position="left" parentId=tag.id]
							[#list tag_list as tag2]
								<li class="bt2"  id="a${tag2_index}">
									<div id="${tag2.id}" class='item'><a href="[#if tag2.url??][@tag2.url?interpret /][/#if]">${tag2.name}</a></div>
								</li>
							[/#list]
						[/@shop_navigation_list]
					[/#list]
				[/@shop_navigationTop_list]
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("#" + $("body").attr("mycenter")).addClass('curr');
	function showMenu(obj, ob){
		if(ob.tagName == "em") {
			ob = ob.parentNode.childNodes[1].childNodes[0]
		}
		if(ob.tagName == "A") {
			ob = ob.parentNode.parentNode.childNodes[1].childNodes[0]
		}
		if(obj.style.display =="") {
			obj.style.display = "none"
			ob.src="/theme/default/images/mc-left-up.gif"
		}
		else {
			obj.style.display = ""
			ob.src="/theme/default/images/mc-left-down.gif"
		}
	}
</script>
	