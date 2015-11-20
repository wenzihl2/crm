<table id="attributeTable" class="l-table-edit">
	[#if goods?? && goods.productType??]
	[#list goods.productType.attributes as attribute]
		<tr>
			<th class="l-table-edit-td">${attribute.name}:</th>
			<td class="l-table-edit-td">
				<select name="attribute_${attribute.id}">
					<option value="">${message("admin.common.choose")}</option>
					[#list attribute.options as option]
						<option value="${option}"[#if option == goods.getAttributeValue(attribute)] selected="selected"[/#if]>${option}</option>
					[/#list]
				</select>
			</td>
		</tr>
	[/#list]
	[/#if]
</table>