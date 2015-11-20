<link rel="stylesheet" href="${base}/resource/css/album.css" type="text/css" />
<script type="text/javascript" src="${base}/resource/js/album.js"></script>
<div title="商品图片" class="tabContent" id="product">
	<div style="color:red;padding-top:10px;padding-bottom:10px;">为了防止展示图片失真，请上传尺寸为460*280的图片！</div>
	<div class="productImageArea">
		<div class="example"></div>
		<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
		<div class="scrollable">
			<ul class="items">
				<li>
					<div class="productImageBox">
						<div class="productImagePreview png">
							[#if goods?? && goods.image_default??]
								<img src="[@GoodsPic pic=goods.image_default/]" />
							[#else]
								主图片
							[/#if]
						</div>
						<div class="productImageOperate">
							<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
							<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
							<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
						</div>
						<a class="productImageUploadButton" href="javascript: void(0);">
							<input type="hidden" name="image_default" value="${(goods.image_default)!''}" />
							<input type="button" class="browserButton" hidefocus="true" />
							<div>上传新图片</div>
						</a>
					</div>
				</li>
				[#list (goods.image_files)! as image]
					<li>
						<div class="productImageBox">
							<div class="productImagePreview png">
								<img src="[@GoodsPic pic=image/]" >
							</div>
							<div class="productImageOperate">
								<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
								<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
								<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
							</div>
							<a class="productImageUploadButton" href="javascript: void(0);">
								<input type="hidden" name="image_files" value="${image}" />
								<input type="button" class="browserButton" hidefocus="true" />
								<div>上传新图片</div>
							</a>
						</div>
					</li>
				[/#list]
				<li>
					<div class="productImageBox">
						<div class="productImagePreview png">暂无图片</div>
						<div class="productImageOperate">
							<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
							<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
							<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
						</div>
						<a class="productImageUploadButton" href="javascript: void(0);">
							<input type="hidden" name="image_files"/>
							<input type="button" class="browserButton"/>
							<div>上传新图片</div>
						</a>
					</div>
				</li>
			</ul>
		</div>
		<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
		<div class="blank"></div>
	</div>
</div>