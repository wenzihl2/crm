[#if !product??]
	[#assign isAdd = true /]
	[#assign haveSpec=false /]
[#else]
	[#assign isEdit = true /]
	[#assign haveSpec=product.have_spec /]
[/#if]
<div title="商品规格" class="tabContent">
	<input type="hidden" name="have_spec" id="haveSpec" value="${haveSpec!'false'}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" id="no-product-spec" style="[#if haveSpec]display:none;[/#if]">
		<tr>
			<th class="l-table-edit-td">采购价：</th>
			<td class="l-table-edit-td"><input type="text" name="purchase_price" value="${(product.purchase_price)!}" 
				class="{required: true, maxlength: 10}" id="purchase_price"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">销售价[挂牌价]：</th>
			<td class="l-table-edit-td"><input type="text" name="sale_price" value="${(product.sale_price)!}" 
				class="{required: true, maxlength: 10}" id="sale_price"/>
			</td>
		</tr>
		<tr>
			<th class="l-table-edit-td">重量：</th>
			<td class="l-table-edit-td"><input type="text" name="weight" id="weight" value="${(product.weight)!}" 
				class="{required: true, number: true, messages: {required: '重量为必填项', number: '重量必须为数字'}}"/>
				[#--
				[@u.select_map headerKey="" headerValue="请选择..." value="${(product.unit.id)!}"
						headerButtom="false" list=DictionaryUtils.category("unit")
						name="unit.id"/]
						--]
			</td>
		</tr>
		<tr>
			<th>规格：</th>
			<td>
				<input type="button" value="开启规格" id="specOpenBtn"/>
				(开启规格前先填写以上价格等信息，可自动复制信息到货品)
			</td>
		</tr>
	</table>
	
	<div id="product-spec" [#if !haveSpec]style="display:none;"[/#if]>
		<div style="border:none; color:#666; border: 2px solid #ccc; background: rgb(239, 239, 239);padding:0.8em; margin:1em 0;">
			<input type="button" value="关闭规格" class="formButton" id="closeSpec"/>
		</div>
		<table class="tableManage" style="width:100%;" id="productTable">
			<tr id="productTitle">
				<th style="width: 150px;">货号</th>
				<th style="width: 160px;" id="priceTh">销售价</th>
				<th style="width: auto;">采购价</th>
				<th style="width: auto;">重量</th>
				<th style="width: auto;">操作</th>
			</tr>
			<tbody>
			[#if product && product.have_spec?? && product.have_spec]
				[#list productList! as specProduct]
					[#assign specids = '']
					[#assign specvids = '']
					<tr class="productTr">
						<td>
							<input type="text" name="sns" class="text" style="width: 125px;" value="${specProduct.no}" title="留空则由系统自动生成" />
							<input type="hidden" name="productids" value="${specProduct.id}"/>
						</td>
						[#list specProduct.specValueList! as spec_value]
							[#if specProduct_index==0]<script>$("#priceTh").before("<th>${(spec_value.spec.name)!''}</th>");</script>[/#if]
							
							[#if spec_value_index!=0]
								[#assign specids = specids + ',']
								[#assign specvids = specvids + ',']
							[/#if]
							[#assign specids = specids + spec_value.spec.id]
							[#assign specvids = specvids + spec_value.id]		
							
							<td>
								
								<div class="select-spec-unselect spec_selected">
									[#if spec_value.specType == "image"]
										<center>
											<img height="20" width="20" src="${spec_value.image}"
											alt="${spec_value.value}" title="${spec_value.value}">
										</center>
										<input type="hidden" value="image" name="spectype_${product_index}">
									[#elseif spec_value.specType == "text"]
										<span>${spec_value.value}</span>
										<input type="hidden" value="text" name="spectype_${product_index}">
									[/#if]
								</div>
								<input type="hidden" name="specvalue_${product_index}" value="${spec_value.spec.name}" />
								<input type="hidden" name="specimage_${product_index}" value="/upload/image/default_small.gif">
							</td>
						[/#list]
					<td>
						<input type="hidden" name="specids" value="${specids}">
						<input type="hidden" name="specvids" value="${specvids}">
						<input type="text" name="sale_prices" value="${(specProduct.sale_price)!''}" size="8"
							class="text prices {required: true, number: true, messages: {required: '货品价格为必填项', number: '货品价格必须为数字'}}"/>
					</td>
					<td><input type="text" name="purchase_prices" value="${specProduct.purchase_price}" style="width: 50px;"
						class="text {required: true, number: true, messages: {required: '采购价为必填项', number: '采购价必须为数字'}}"/>
					</td>
					<td><input type="text" name="weights" value="${specProduct.weight}" style="width: 50px;"
							class="text {required: true, number: true, messages: {required: '重量为必填项', number: '重量必须为数字'}}"/>
					</td>
					<td><a href="javascript:;" ><img class="delete" src="${base}/resource/images/transparent.gif" productid="${product.id}"></a></td>
				</tr>
				[/#list]
			[/#if]
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript" src="${base}/resource/js/plugin/spec.js"></script>
<script type="text/javascript" src="${base}/resource/js/trimpath-template-1.0.38.js"></script>
[#noparse]
<textarea id="product_jst" style="display: none;">

{for productSpec in productSpecs}
	{var i = productSpec_index}
	{var specvids = ""}
	{var specids = ""}
	<tr>
		<td>
			<input type="text" name="sns" class="text" style="width:125px">
			<input type="hidden" name="productids">
		</td>
		{for spec in productSpec}
			{var j = spec_index}
			{if j != 0}
				{var specvids = specvids + "," }
				{var specids = specids + ","}
			{/if}
			{var specvids = specvids + spec.specvid}
			{var specids = specids + spec.specid}
			<td>
				{if spec.spectype == "text"}
					<div class='select-spec-unselect spec_selected'><span>${spec.specvalue}</span></div>
				{else}
					<div class="select-spec-unselect spec_selected">
						<img height="20" width="20" title="${spec.specvalue}" alt="${spec.specvalue}" src="${spec.specimage}">
					</div>
				{/if}
				<input type="hidden" name="spectype_${i}" value='${spec.spectype}' />
				<input type="hidden" name="specimage_${i}" value='${spec.specimage}' />
				<input type="hidden" name="specvalue_${i}" value="${spec.specvalue}" />
			</td>
		{/for}
		<td>
			<input type='hidden' name='specids' value='${specids}' />
			<input type='hidden' name='specvids' value='${specvids}' />
			<input type="text" name="sale_prices" value="${sale_price}">
		</td>
		<td><input type="text" name="purchase_prices" value="${purchase_price}"></td>
		<td><input type="text" name="weights" value="${weight}"></td>
		<td><a href="javascript:;" >
			<img class="delete" src= base + "/resource/images/transparent.gif" productid="0"></a>
		</td>
	</tr>
{/for}
</textarea>
[/#noparse]