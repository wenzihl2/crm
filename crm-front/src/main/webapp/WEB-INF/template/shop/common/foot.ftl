<div class=" bg-hui toppadding bottompadding"></div>
<div class=" bg-hui toppadding bottompadding hidden-xs"></div>
<div class="container">
    <div class="row">
        <img class="img-responsive " src="${base}/theme/shop/image/bottom.png">
    </div>
</div>
<div class="bg-hui toppadding bottompadding">
    <div class="container">
        <div class="row">
			[@shop_article_list ids="1,2,3"]
				[#list tag_list as tag]
					<div class="col-xs-12 col-sm-3 col-md-1"> <a class="btn-sharmoon">${tag.title}</a></div>
				[/#list]
			[/@shop_article_list]
	        <div class="float-right col-xs-12 col-sm-3 col-md-1"> <img src="${base}/theme/shop/image/icon-weibo.png"></div>
    	</div>
    </div>
</div>

<div class="table-bordered"></div>
<div class="bg-hui toppadding bottompadding">
    <div class="container">
        <div class="col-xs-6 col-md-4"><img class="img-responsive  img-rounded" src="${base}/theme/shop/image/icon-foot.png"></div>
    </div>
</div>
<script type="text/javascript" src="${base}/theme/thirdparty/js/bootstrap.js"></script>