var wsshop = {
	base: ""
};

var setting = {
	priceScale: "2",
	priceRoundType: "roundHalfUp",
	currencySign: "￥",
	currencyUnit: "元",
	uploadImageExtension: "jpg,jpge,bmp,gif,png",
	uploadFlashExtension: "",
	uploadMediaExtension: "",
	uploadFileExtension: ""
};

var messages = {
	"shop.message.success": "操作成功",
	"shop.message.error": "操作错误",
	"shop.dialog.ok": "shop.dialog.ok",
	"shop.dialog.cancel": "shop.dialog.cancel",
	"shop.dialog.deleteConfirm": "shop.dialog.deleteConfirm",
	"shop.dialog.clearConfirm": "shop.dialog.clearConfirm",
	"shop.validate.required": "shop.validate.required",
	"shop.validate.email": "shop.validate.email",
	"shop.validate.url": "shop.validate.url",
	"shop.validate.date": "shop.validate.date",
	"shop.validate.dateISO": "shop.validate.dateISO",
	"shop.validate.pointcard": "shop.validate.pointcard",
	"shop.validate.number": "shop.validate.number",
	"shop.validate.digits": "shop.validate.digits",
	"shop.validate.minlength": "shop.validate.minlength",
	"shop.validate.maxlength": "shop.validate.maxlength",
	"shop.validate.rangelength": "shop.validate.rangelength",
	"shop.validate.min": "shop.validate.min",
	"shop.validate.max": "shop.validate.max",
	"shop.validate.range": "shop.validate.range",
	"shop.validate.accept": "shop.validate.accept",
	"shop.validate.equalTo": "shop.validate.equalTo",
	"shop.validate.remote": "shop.validate.remote",
	"shop.validate.integer": "shop.validate.integer",
	"shop.validate.positive": "shop.validate.positive",
	"shop.validate.negative": "shop.validate.negative",
	"shop.validate.decimal": "shop.validate.decimal",
	"shop.validate.pattern": "shop.validate.pattern",
	"shop.validate.extension": "shop.validate.extension"
};

// 添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
	}
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}

// 货币格式化
function currency(value, showSign, showUnit) {
	if (value != null) {
		var price;
		if (setting.priceRoundType == "roundHalfUp") {
			price = (Math.round(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		} else if (setting.priceRoundType == "roundUp") {
			price = (Math.ceil(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		} else {
			price = (Math.floor(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		}
		if (showSign) {
			price = setting.currencySign + price;
		}
		if (showUnit) {
			price += setting.currencyUnit;
		}
		return price;
	}
}

// 多语言
function message(code) {
	if (code != null) {
		var content = messages[code] != null ? messages[code] : code;
		if (arguments.length == 1) {
			return content;
		} else {
			if ($.isArray(arguments[1])) {
				$.each(arguments[1], function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			} else {
				$.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			}
		}
	}
}
// 令牌
$().ready(function() {

	$("form").submit(function() {
		var $this = $(this);
		if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
			var token = getCookie("token");
			if (token != null) {
				$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
			}
		}
	});

});

// 验证消息
if ($.validator != null) {
	$.extend($.validator.messages, {
		required: message("shop.validate.required"),
		email: message("shop.validate.email"),
		url: message("shop.validate.url"),
		date: message("shop.validate.date"),
		dateISO: message("shop.validate.dateISO"),
		pointcard: message("shop.validate.pointcard"),
		number: message("shop.validate.number"),
		digits: message("shop.validate.digits"),
		minlength: $.validator.format(message("shop.validate.minlength")),
		maxlength: $.validator.format(message("shop.validate.maxlength")),
		rangelength: $.validator.format(message("shop.validate.rangelength")),
		min: $.validator.format(message("shop.validate.min")),
		max: $.validator.format(message("shop.validate.max")),
		range: $.validator.format(message("shop.validate.range")),
		accept: message("shop.validate.accept"),
		equalTo: message("shop.validate.equalTo"),
		remote: message("shop.validate.remote"),
		integer: message("shop.validate.integer"),
		positive: message("shop.validate.positive"),
		negative: message("shop.validate.negative"),
		decimal: message("shop.validate.decimal"),
		pattern: message("shop.validate.pattern"),
		extension: message("shop.validate.extension")
	});
	
	$.validator.setDefaults({
		errorClass: "fieldError",
		ignore: ".ignore",
		ignoreTitle: true,
		errorPlacement: function(error, element) {
			var fieldSet = element.closest("span.fieldSet");
			if (fieldSet.size() > 0) {
				error.appendTo(fieldSet);
			} else {
				error.insertAfter(element);
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").prop("disabled", true);
			form.submit();
		}
	});
}
//=========================================================================
//URL操作工具(zhenglong) BEGIN
//=========================================================================
var searchFields = ["cat", "gb", "brand", "price", "sort", "prop", "page", "tag"];
var Search_prefix = "list-";
var Search_prefix5 = "plaza-";
var Search_prefix1 = "exchangeList-";
var Search_prefix2 = "timedBuy-";
var Search_prefix3 = "groupBuy-";
function getParamStr(str){
	var pattern1 = new RegExp(Search_prefix + "(.*)" + ".jhtml");
	var temp = str;
	if(pattern1.test(temp)){
		return RegExp.$1;
	}
	
	var pattern2 = new RegExp(Search_prefix1 + "(.*)" + ".jhtml");
	if(pattern2.test(temp)){
		return RegExp.$1;
	}
	
	var pattern3 = new RegExp(Search_prefix2 + "(.*)" + ".jhtml");
	if(pattern3.test(temp)){
		return RegExp.$1;
	}
	
	var pattern4 = new RegExp(Search_prefix3 + "(.*)" + ".jhtml");
	if(pattern4.test(temp)){
		return RegExp.$1;
	}
	
	var pattern5 = new RegExp(Search_prefix5 + "(.*)" + ".jhtml");
	if(pattern5.test(temp)){
		return RegExp.$1;
	}
	return temp;
};

function getParamStringValue(param, name){
	var param = getParamStr(param);
	var pattern = new RegExp("(.*)" + name + "\\-(.[^\\-]*)(.*)");
	if(pattern.test(param)){
		return RegExp.$2;
	}
};

function addUrlParam(url, name, value, prefix){
	if(!name || name == "" || !value || value == ""){
		return;
	}
	var paramStr = getParamStr(url);
	if(!paramStr || paramStr == ""){
		return url;
	}

	var newUrl = prefix?prefix:"list";
	for(var field in searchFields){
		var temp = getParamStringValue(paramStr, searchFields[field]);
		//添加之前的参数值，如果参数已经存在，则不处理
		if(temp && temp != "" && searchFields[field] != name){
			newUrl += "-" + searchFields[field] + "-" + temp;
		}
		//增加新的参数值
		if(name && name == searchFields[field]){
			var rel_value = "";
			if(value instanceof Array){
				rel_value = value[0];
				for(var i=1; i<value.length; i++){
					rel_value += "_" + value[i];
				}
			}else{
				rel_value = value;
			}
			newUrl += "-" + searchFields[field] + "-" + rel_value;
		}
	}
	return newUrl + ".jhtml";
};
function removeUrlParam(url, name, prefix){
	var paramStr = getParamStr(url);
	if(!paramStr || paramStr == ""){
		return url;
	}
	var newUrl = prefix?prefix:"list";
	for(var field in searchFields){
		if(searchFields[field] == name){
			continue;
		}
		var temp = getParamStringValue(paramStr, searchFields[field]);
		if(temp && temp != ""){
			newUrl += "-" + searchFields[field] + "-" + temp;
		}
		if(name && name == searchFields[field]){
			newUrl += "-" + searchFields[field] + "-" + value;
		}
	}
	return newUrl  + ".jhtml";
};