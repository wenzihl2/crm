//类别、类型、品牌联动
var parameterForm, attributeForm;
var TypeCat={
	category_id: null,
	parameterTable: null,
	attributeTable: null,
	preVal:null,
	init: function(){
		var self  = this;
		this.parameterTable = $("#parameterTable");
		this.attributeTable = $("#attributeTable");
//		var brand_id = $("#brand_value").val();
//		this.loadBrandsInput(type_id, brand_id);
		//this.loadPropsInput(category_id);
		//this.loadParamsInput(category_id);
	},
	change: function() {
		var self  = this;
		var hasValue = false;
		self.parameterTable.find(":input").add(self.attributeTable.find("select")).each(function() {
			if ($.trim($(this).val()) != "") {
				hasValue = true;
				return false;
			}
		});
		
//		if(!self.preVal && self.preVal == $("#productCategoryId").val()) {
//			return;
//		}
		
		self.category_id = $("#productCategoryId").val();
		if (hasValue) {
			$.ligerDialog.confirm('修改商品分类后当前参数、属性数据将会丢失，是否继续？', function (yes) {
				self.loadPropsInput();
				self.loadParamsInput();
			}, function(){
				return false;
			});
		} else {
			self.loadPropsInput();
			self.loadParamsInput();
		}
		self.preVal = self.category_id;
	},
	//异步读取品牌输入项
//	loadBrandsInput: function(type_id, brand_id){
//		var self  = this;
//		$.ajax({
//			type: "get",
//			url: base + "/admin/goodsType/listBrand.jhtml",
//			data:"type_id=" + type_id +"&m=" + new Date().getTime(),
//			dataType:"html",
//			success:function(result){
//			   $("#brand_id").empty().append(result);
//			   if(brand_id){
//				   $("#brand_id").val(brand_id);
//			   }
//			}
//		});
//	},
	//加载参数输入项
	loadParamsInput: function(){
		var self  = this;
		$.ajax({
			url: "parameter_groups.jhtml",
			type: "GET",
			data: {id: self.category_id},
			dataType: "json",
			beforeSend: function() {
				self.parameterTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, parameterGroup) {
					trHtml += '<tr><td class="l-table-edit-td">参数组:<\/td><td class="l-table-edit-td"><strong>' + parameterGroup.name + '<\/strong><\/td><\/tr>';
					$.each(parameterGroup.parameters, function(i, parameter) {
							trHtml += '<tr><th class="l-table-edit-td">' + parameter.name + ': <\/th><td class="l-table-edit-td">' +
									'<input type="text" name="parameter_' + parameter.id + '" class="text" maxlength="200" \/>' +
								'<\/td><\/tr>';
					});
				});
				self.parameterTable.append(trHtml);
				self.parameterTable.find("input").ligerTextBox();
			}
		});
	},
	loadPropsInput: function(){
		var self  = this;
		$.ajax({
			url: "attributes.jhtml",
			type: "GET",
			data: {id: self.category_id},
			dataType: "json",
			beforeSend: function() {
				self.attributeTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, attribute) {
					var optionHtml = '<option value="">请选择...<\/option>';
					$.each(attribute.options, function(j, option) {
						optionHtml += '<option value="' + option + '">' + option + '<\/option>';
					});
					trHtml += '<tr><th class="l-table-edit-td">' + attribute.name + ': <\/th><td class="l-table-edit-td">' +
							'<select name="attribute_' + attribute.id + '">' + optionHtml + '<\/select>' +
						'<\/td><\/tr>';
				});
				self.attributeTable.append(trHtml);//.ligerForm();
				self.attributeTable.find("select").ligerComboBox();
				
			}
		});
	}
};