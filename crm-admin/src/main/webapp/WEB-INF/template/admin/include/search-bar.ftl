<script type="text/javascript">
$(function () {
	LG.searchRecord('searchbox', grid);
	LG.searchTable();
});
</script>
<style type="text/css">
#mainsearch {padding-left:10px; position:relative;}
#mainsearch img{ width:22px; height:22px; position:absolute; left:0; top:0;}
#mainsearch span{ font-size:14px;}
#mainsearch .togglebtn-down{float:right; background:url(/resource/images/icons/toggle.gif) no-repeat 0px -10px; height:10px; width:9px; cursor:pointer;}
#mainsearch .togglebtn{background-position:0px 0px;}
#mainsearch #searchBtn {position:absolute;top:5px; right:0; }
</style>
<!-- 
<div id="searchbar">
	<span id="searchbox">
	</span>
	<div class="togglebtn"></div> 
</div>
-->

<div id="mainsearch" style=" width:98%">
    <form class="searchbox"></form>
    <div id='searchBtn'>
    	<input type='button' id='search' class='button' value='搜索'/>
    	<a href='#' id='search_btn_reset' face='Wingdings 3'>解除搜索</a>
    	<div class="togglebtn-down"></div> 
    </div>
    <form class="advsearchbox" style="display:none;"></form>
</div>