var messages = {
	"admin.dialog.ok": "确&nbsp;&nbsp;定",
	"admin.dialog.cancel": "取&nbsp;&nbsp;消",
	"admin.dialog.deleteConfirm": "您确定要删除吗？",
	"admin.dialog.clearConfirm": "您确定要清空吗？",
	"admin.browser.title": "选择文件",
	"admin.browser.upload": "本地上传",
	"admin.browser.parent": "上级目录",
	"admin.browser.orderType": "排序方式",
	"admin.browser.component": "存储组件",
	"admin.browser.name": "名称",
	"admin.browser.size": "大小",
	"admin.browser.type": "类型",
	"admin.browser.date": "日期",
	"admin.browser.select": "选择文件",
	"admin.upload.sizeInvalid": "上传文件大小超出限制",
	"admin.upload.typeInvalid": "上传文件格式不正确",
	"admin.upload.invalid": "上传文件格式或大小不正确"
};
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
(function($) {
	var zIndex = 100;
	// 对话框
	$.dialog = function(options) {
		var settings = {
			width: 320,
			height: "auto",
			modal: true,
			ok: message("admin.dialog.ok"),
			cancel: message("admin.dialog.cancel"),
			onShow: null,
			onClose: null,
			onOk: null,
			onCancel: null
		};
		$.extend(settings, options);
		
		if (settings.content == null) {
			return false;
		}
		
		var $dialog = $('<div class="xxDialog"><\/div>');
		var $dialogTitle;
		var $dialogClose = $('<div class="dialogClose"><\/div>').appendTo($dialog);
		var $dialogContent;
		var $dialogBottom;
		var $dialogOk;
		var $dialogCancel;
		var $dialogOverlay;
		if (settings.title != null) {
			$dialogTitle = $('<div class="dialogTitle"><\/div>').appendTo($dialog);
		}
		if (settings.type != null) {
			$dialogContent = $('<div class="dialogContent dialog' + settings.type + 'Icon"><\/div>').appendTo($dialog);
		} else {
			$dialogContent = $('<div class="dialogContent"><\/div>').appendTo($dialog);
		}
		if (settings.ok != null || settings.cancel != null) {
			$dialogBottom = $('<div class="dialogBottom"><\/div>').appendTo($dialog);
		}
		if (settings.ok != null) {
			$dialogOk = $('<input type="button" class="button" value="' + settings.ok + '" \/>').appendTo($dialogBottom);
		}
		if (settings.cancel != null) {
			$dialogCancel = $('<input type="button" class="button" value="' + settings.cancel + '" \/>').appendTo($dialogBottom);
		}
		if (!window.XMLHttpRequest) {
			$dialog.append('<iframe class="dialogIframe"><\/iframe>');
		}
		$dialog.appendTo("body");
		if (settings.modal) {
			$dialogOverlay = $('<div class="dialogOverlay"><\/div>').insertAfter($dialog);
		}
		
		var dialogX;
		var dialogY;
		if (settings.title != null) {
			$dialogTitle.text(settings.title);
		}
		$dialogContent.html(settings.content);
		$dialog.css({"width": settings.width, "height": settings.height, "margin-left": - parseInt(settings.width / 2), "z-index": zIndex ++});
		dialogShow();
		
		if ($dialogTitle != null) {
			$dialogTitle.mousedown(function(event) {
				$dialog.css({"z-index": zIndex ++});
				var offset = $(this).offset();
				if (!window.XMLHttpRequest) {
					dialogX = event.clientX - offset.left;
					dialogY = event.clientY - offset.top;
				} else {
					dialogX = event.pageX - offset.left;
					dialogY = event.pageY - offset.top;
				}
				$("body").bind("mousemove", function(event) {
					$dialog.css({"top": event.clientY - dialogY, "left": event.clientX - dialogX, "margin": 0});
				});
				return false;
			}).mouseup(function() {
				$("body").unbind("mousemove");
				return false;
			});
		}
		
		if ($dialogClose != null) {
			$dialogClose.click(function() {
				dialogClose();
				return false;
			});
		}
		
		if ($dialogOk != null) {
			$dialogOk.click(function() {
				if (settings.onOk && typeof settings.onOk == "function") {
					if (settings.onOk($dialog) != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
				return false;
			});
		}
		
		if ($dialogCancel != null) {
			$dialogCancel.click(function() {
				if (settings.onCancel && typeof settings.onCancel == "function") {
					if (settings.onCancel($dialog) != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
				return false;
			});
		}
		
		function dialogShow() {
			if (settings.onShow && typeof settings.onShow == "function") {
				if (settings.onShow($dialog) != false) {
					$dialog.show();
					$dialogOverlay.show();
				}
			} else {
				$dialog.show();
				$dialogOverlay.show();
			}
		}
		function dialogClose() {
			if (settings.onClose && typeof settings.onClose == "function") {
				if (settings.onClose($dialog) != false) {
					$dialogOverlay.remove();
					$dialog.remove();
				}
			} else {
				$dialogOverlay.remove();
				$dialog.remove();
			}
		}
		return $dialog;
	}

	// 文件浏览
	$.fn.extend({
		browser: function(options) {
			var settings = {
				type: "image",
				createThumb: 0,
				title: message("admin.browser.title"),
				isUpload: true,
				browserUrl: base + "/admin/upload/browser.jhtml",
				uploadUrl: base + "/admin/upload/submit.jhtml",
				callback: null
			};
			$.extend(settings, options);
			
			var token = LG.cookies.get("token");
			var cache = new Array();
			return this.each(function() {
				var browserFrameId = "browserFrame" + (new Date()).valueOf() + Math.floor(Math.random() * 1000000);
				var $browserButton = $(this);
				$browserButton.bind("click", function() {
					var self = this;
					var $browser = $('<div class="xxBrowser"><\/div>');
					var $browserBar = $('<div class="browserBar"><\/div>').appendTo($browser);
					var $browserFrame;
					var $browserForm;
					var $browserUploadButton;
					var $browserUploadInput;
					var $browserParentButton;
					var $browserOrderType;
					var $browserComponent;//选择存储组件
					var $browserLoadingIcon;
					var $browserList;
					if (settings.isUpload) {
						$browserFrame = $('<iframe id="' + browserFrameId + '" name="' + browserFrameId + '" style="display: none;"><\/iframe>').appendTo($browserBar);
						$browserForm = $('<form action="' + settings.uploadUrl + '" method="post" encType="multipart/form-data" target="' + browserFrameId + '">' + 
								'<input type="hidden" name="token" value="' + token + '" \/><input type="hidden" name="fileType" value="' + settings.type + '" \/>' + 
								'<input type="hidden" name="componentId" id="componentId"/>' +
								'<input type="hidden" name="createThumb" id="createThumb"/>' +
								'<\/form>').appendTo($browserBar);
						$browserUploadButton = $('<a href="javascript:;" class="browserUploadButton button">' + message("admin.browser.upload") + '<\/a>').appendTo($browserForm);
						$browserUploadInput = $('<input type="file" name="file" \/>').appendTo($browserUploadButton);
					}
					$browserParentButton = $('<a href="javascript:;" class="button">' + message("admin.browser.parent") + '<\/a>').appendTo($browserBar);
					$browserBar.append(message("admin.browser.orderType") + ": ");
					$browserOrderType = $('<select name="orderType" class="browserOrderType"><option value="name">' + message("admin.browser.name") + '<\/option><option value="size">' + message("admin.browser.size") + '<\/option><option value="date">' + message("admin.browser.date") + '<\/option><option value="type">' + message("admin.browser.type") + '<\/option><\/select>').appendTo($browserBar);
					//增加存储组件的选择
					$browserBar.append(message("admin.browser.component") + ": ");
					$browserComponent = $('<select class="browserComponent"><option value="filePlugin">本地存储</option><option value="ftpPlugin">FTP存储</option><option value="ossPlugin">阿里云存储</option></select>').appendTo($browserBar);
					
					$browserLoadingIcon = $('<span class="loadingIcon" style="display: none;">&nbsp;<\/span>').appendTo($browserBar);
					$browserList = $('<div class="browserList"><\/div>').appendTo($browser);
	
					var $dialog = $.dialog({
						title: settings.title,
						content: $browser,
						width: 800,
						modal: true,
						isResize: true,
						ok: null,
						cancel: null
					});
					
					browserList("/");
					
					function browserList(path) {
						var key = settings.type + "_" + path + "_" + $browserOrderType.val() + "_" + $browserComponent.val();
						if (cache[key] == null) {
							$.ajax({
								url: settings.browserUrl,
								type: "GET",
								data: {fileType: settings.type, 
										orderType: $browserOrderType.val(), 
										path: path, 
										"componentId": $browserComponent.val()
									},
								dataType: "json",
								cache: false,
								beforeSend: function() {
									$browserLoadingIcon.show();
								},
								success: function(data) {
									createBrowserList(path, data);
									cache[key] = data;
								},
								complete: function() {
									$browserLoadingIcon.hide();
								}
							});
						} else {
							createBrowserList(path, cache[key]);
						}
					}
					
					function createBrowserList(path, data) {
						var browserListHtml = "";
						$.each(data, function(i, fileInfo) {
							var iconUrl;
							var title;
							if (fileInfo.isDirectory) {
								iconUrl = base + "/resource/images/folder_icon.gif";
								title = fileInfo.name;
							} else if (new RegExp("^\\S.*\\.(jpg|jpeg|bmp|gif|png)$", "i").test(fileInfo.name)) {
								iconUrl = fileInfo.url;
								title = fileInfo.name + " (" + Math.ceil(fileInfo.size / 1024) + "KB, " + new Date(fileInfo.lastModified).toLocaleString() + ")";
							} else {
								iconUrl = base + "/resource/images/file_icon.gif";
								title = fileInfo.name + " (" + Math.ceil(fileInfo.size / 1024) + "KB, " + new Date(fileInfo.lastModified).toLocaleString() + ")";
							}
							browserListHtml += '<div class="browserItem"><img src="' + iconUrl + '" title="' + title + '" url="' + fileInfo.url + '" store_path="' + fileInfo.storePath + '" isDirectory="' + fileInfo.isDirectory + '" \/><div>' + fileInfo.name + '<\/div><\/div>';
						});
						$browserList.html(browserListHtml);
						
						$browserList.find("img").bind("click", function() {
							var $this = $(this);
							var isDirectory = $this.attr("isDirectory");
							if (isDirectory == "true") {
								var name = $this.next().text();
								browserList(path + name + "/");
							} else {
								var store_path = $this.attr("store_path");
								var url = $this.attr("url");
								if (settings.input != null) {
									settings.input.val(url);
								} else if($browserButton.prev(":text").size() > 0){
									$browserButton.prev(":text").val(store_path);
								} else if($browserButton.parent().parent().find(":text").size() > 0){
									$browserButton.parent().parent().find(":text").val(store_path);
								}
								if (settings.callback != null && typeof settings.callback == "function") {
									settings.callback.call(self, url, store_path);
								}
								$browserButton.removeAttr("disabled"); 
								$dialog.next(".dialogOverlay").andSelf().remove();
							}
						});
						
						if (path == "/") {
							$browserParentButton.unbind("click");
						} else {
							var parentPath = path.substr(0, path.replace(/\/$/, "").lastIndexOf("/") + 1);
							$browserParentButton.unbind("click").bind("click", function() {
								browserList(parentPath);
							});
						}
						$browserOrderType.unbind("change").bind("change", function() {
							browserList(path);
						});
						
						$browserComponent.unbind("change").bind("change", function() {
							browserList("/");
						});
					}
					
					$browserUploadInput.change(function() {
						var allowedUploadExtensions;
						if (settings.type == "flash") {
							allowedUploadExtensions = setting.uploadFlashExtension;
						} else if (settings.type == "media") {
							allowedUploadExtensions = setting.uploadMediaExtension;
						} else if (settings.type == "file") {
							allowedUploadExtensions = setting.uploadFileExtension;
						} else {
							allowedUploadExtensions = setting.uploadImageExtension;
						}
						if ($.trim(allowedUploadExtensions) == "" || !new RegExp("^\\S.*\\.(" + allowedUploadExtensions.replace(/,/g, "|") + ")$", "i").test($browserUploadInput.val())) {
							$.message("warn", message("admin.upload.typeInvalid"));
							return false;
						}
						$browserLoadingIcon.show();
						//判断componentId的表单是否存在
						var componentIdInput = $("#componentId");
						componentIdInput.val($browserComponent.val());
						
						var createThumb = $("#createThumb");
						createThumb.val(settings.createThumb);
						$browserForm.submit();
					});
					
					$browserFrame.load(function() {
						var text;
						var io = document.getElementById(browserFrameId);
						if(io.contentWindow) {
							text = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML : null;
						} else if(io.contentDocument) {
							text = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML : null;
						}
						if ($.trim(text) != "") {
							$browserLoadingIcon.hide();
							var data = $.parseJSON(text);
							if (data.url != null) {
								if (settings.input != null) {
									settings.input.val(data.store_path);
								} else {
									$browserButton.prev(":text").val(data.store_path);
								}
								if (settings.callback != null && typeof settings.callback == "function") {
									settings.callback.call(self, data.url, data.store_path);
								}
								cache = new Array();
								$dialog.next(".dialogOverlay").andSelf().remove();
							} else {
								if (data.status && (data.status == "warn" || data.status == "error")) {
									alert(data.message);
								}
								//$.message(data);
							}
						}
					});
					
				});
			});
		}
	});
})(jQuery);

function initupload(setting, grid){
	var div =$("#" + setting.id);
	var submiturl = setting.submiturl || base+"/admin/upload/upload.jhtml";
	var pluginId = setting.pluginId;
	var btn = $("#" + setting.btnId);
	div.append('<form action="' + submiturl + '" method="post" encType="multipart/form-data" id="uploadForm" >'+
		'<input type="hidden" name="fileType" value="file"/>'+
		'<input type="hidden" name="componentId" value="filePlugin"/>'+
		'<input type="hidden" name="pluginId" value="' + pluginId + '"/>'+
		'<input type="hidden" name="createThumb" value="createThumb"/>'+
		'<input type="file" name="file"/>'+
	'</form>');
	if (btn){
		btn.click(upload);
	}
	$('#uploadForm').ajaxForm(function(data) {
		var win = parent || window;
		if(data.type && data.type == "ERROR"){
	   		 win.LG.showError(data.content, function () {
	   		 	if(data.errorFileUrl) {
	   				top.f_addTab("uploaderror", '错误信息', base + data.errorFileUrl);
	   			}
	         });
	   	} else {
	   		win.LG.showSuccess('上传成功', function () {
             	if (grid){
					grid.reload();
				}
			});
	   	}
        if (dialog){
        	dialog.hide();
        }
    });
}
function upload(){
	$("#uploadForm").ligerForm();
	dialog = $.ligerDialog.open({ title:"文件上传", target: $("#fileupload") ,
		buttons: [
          { text: '确定', onclick: function (item, dialog) { $('#uploadForm').submit();},cls:'l-dialog-btn-highlight' },
          { text: '取消', onclick: function (item, dialog) { dialog.hide(); } }
        ]
    });
};
