<table id="parameterTable" class="l-table-edit">
	[#if goods?? && goods.productType??]
	[#list goods.productType.parameterGroups! as paramGroup]
		<tr>
			<td class="l-table-edit-td">参数组:</td>
			<td class="l-table-edit-td"><strong style="font-weight:bold;">${(paramGroup.name)!''}</strong></td>
		</tr>
		[#list paramGroup.parameters as param]
		<tr>
			<th class="l-table-edit-td">${param.name}:</th>
			<td class="l-table-edit-td">
				<input type="text" name="parameter_${param.id}" class="text" value="${paramMap.get(param.id)}" maxlength="200" />
			</td>
		</tr>
		[/#list]
	[/#list]
	[/#if]
</table>