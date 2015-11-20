<table id="attributeTable" class="l-table-edit">
	[#if product?? && product.productType??]
	[#list product.productType.attributes as attribute]
		<tr>
			<th class="l-table-edit-td">${attribute.name}:</th>
			<td class="l-table-edit-td">
				<select name="attribute_${attribute.id}">
					<option value="">${message("admin.common.choose")}</option>
					[#list attribute.options as option]
						<option value="${option}"[#if option == product.getAttributeValue(attribute)] selected="selected"[/#if]>${option}</option>
					[/#list]
				</select>
			</td>
		</tr>
	[/#list]
	[/#if]
</table>