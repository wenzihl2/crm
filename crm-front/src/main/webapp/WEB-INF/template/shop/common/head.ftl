<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container" >
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation1</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html"><img src="${base}/theme/shop/image/logo.png"></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="/">商城首页</a></li>
                <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">在线购买</a>
	                <ul class="dropdown-menu">
	                	[@shop_goodsCategoryTop_list]
							[#list goodsCategories as bean]
								<li><a href="#">${bean.name}</a></li>
							[/#list]
						[/@shop_goodsCategoryTop_list]
	                </ul>
                </li>
                <li><a href="#">风尚专栏</a></li>

                <li><a href="#">专业服务</a></li>
                <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">企业信息</a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
                <li><a href="account.html" class="visible-xs">我的账户</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right hidden-xs hidden-sm">
                <li><a href="account.html">我的账户</a></li>
                <li><a href="#">搜索</a></li>
                <li><a href="#"class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">购物车</a>

                    <ul class="dropdown-menu">
                        <div>

                            <div>asdsadsadsa  dsa</div>
                            <div>asdsadsadsa  dsa</div>
                            <div>asdsadsadsa  dsa</div>
                            <div>asdsadsadsa  dsa</div>
                            <div>asdsadsadsa  dsa</div>
                            <div>asdsadsadsa  dsa</div>
                        </div>
                    </ul>

                </li>

            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>