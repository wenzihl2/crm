(function ($) {

    //全局系统对象
    window['LG'] = {};
	var form = null;	
	var hideInput = new Array();
    LG.cookies = (function () {
        var fn = function () {
        };
        fn.prototype.get = function (name)
        {
            var cookieValue = "";
            var search = name + "=";
            if (document.cookie.length > 0)
            {
                offset = document.cookie.indexOf(search);
                if (offset != -1)
                {
                    offset += search.length;
                    end = document.cookie.indexOf(";", offset);
                    if (end == -1) end = document.cookie.length;
                    cookieValue = decodeURIComponent(document.cookie.substring(offset, end));
                }
            }
            return cookieValue;
        };
        fn.prototype.set = function (cookieName, cookieValue, DayValue) {
            var expire = "";
            var day_value = 1;
            if (DayValue != null)
            {
                day_value = DayValue;
            }
            expire = new Date((new Date()).getTime() + day_value * 86400000);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";path=/" + expire;
        };
        fn.prototype.remvoe = function (cookieName)
        {
            var expire = "";
            expire = new Date((new Date()).getTime() - 1);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + escape("") + ";path=/" + expire;
            /*path=/*/
        };

        return new fn();
    })();

    //右下角的提示框
    LG.tip = function (message) {
        if (LG.wintip) {
            LG.wintip.set('content', message);
            LG.wintip.show();
        } else {
            LG.wintip = $.ligerDialog.tip({ content: message });
        }
        setTimeout(function () {
            LG.wintip.hide();
        }, 4000);
    };

    //预加载图片
    LG.prevLoadImage = function (rootpath, paths) {
        for (var i in paths) {
            $('<img />').attr('src', rootpath + paths[i]);
        }
    };
    //显示loading
    LG.showLoading = function (message) {
        message = message || "正在加载中...";
        $('body').append("<div class='jloading'>" + message + "</div>");
        $.ligerui.win.mask();
    };
    //隐藏loading
    LG.hideLoading = function (message) {
        $('body > div.jloading').remove();
        $.ligerui.win.unmask({ id: new Date().getTime() });
    };
	LG.showLoading2 = function(message){
		message = message || "正在加载中...";
		$('body').append("<div class='l-grid-loading' style='display: block;'>" + message + "</div>");
		$.ligerui.win.mask();
	};
	LG.hideLoading2 = function(){
		$('body > div.l-grid-loading').remove();
        $.ligerui.win.unmask({ id: new Date().getTime() });
	};
    //显示成功提示窗口
    LG.showSuccess = function (message, callback) {
        if (typeof (message) == "function" || arguments.length == 0) {
            callback = message;
            message = "操作成功!";
        }
        $.ligerDialog.success(message, '提示信息', callback);
    };
    //显示失败提示窗口
    LG.showError = function (message, callback)
    {
        if (typeof (message) == "function" || arguments.length == 0)
        {
            callback = message;
            message = "操作失败!";
        }
        $.ligerDialog.error(message, '提示信息', callback);
    };


    //预加载dialog的图片
    LG.prevDialogImage = function (rootPath) {
        rootPath = rootPath || base;
        LG.prevLoadImage(rootPath + '/resource/thirdparty/liguerUI125/skins/Aqua/images/win/', ['dialog-icons.gif']);
        LG.prevLoadImage(rootPath + '/resource/thirdparty/liguerUI125/skins/Gray/images/win/', ['dialogicon.gif']);
    };

    //提交服务器请求
    //返回json格式
    //1,提交给类 options.type  方法 options.method 处理
    //2,并返回 AjaxResult(这也是一个类)类型的的序列化好的字符串
    LG.ajax = function (options) {
        var p = options || {};
        var url = p.url || base + "/" + p.action + "!" + p.method + ".action";
        $.ajax({
            cache: false,
            async: p.async == undefined ? true : p.async,
            url: url,
			traditional: true,
            data: p.data,
            dataType: 'json', 
            type: p.type == undefined ? 'post' : p.type,
            beforeSend: function () {
                LG.loading = true;
                if (p.beforeSend)
                    p.beforeSend();
                else
                    LG.showLoading(p.loading);
            },
            complete: function () {
                LG.loading = false;
                if (p.complete)
                    p.complete();
                else
                    LG.hideLoading();
            },
            success: function (result) {
                if (result == null) return;
				if (p.success)
					p.success(result);
            },
            error: function (result) {
				if(result.status == 404){
					LG.tip("请求路径" + url + "无法找到");
				} else if(result.status == 400){
					LG.tip(eval("(" + result.responseText + ")").content);
				} else {
					LG.tip('发现系统错误 <BR>错误码：' + result.status);
				}
            }
        });
    };

    //获取当前页面的MenuNo
    //优先级1：如果页面存在MenuNo的表单元素，那么加载它的值
    //优先级2：加载QueryString，名字为MenuNo的值
    LG.getPageMenuNo = function () {
        var menuno = $("#MenuNo").val();
        if (!menuno) {
            menuno = getQueryStringByName("MenuNo");
        }
        return menuno;
    };

    //创建按钮
    LG.createButton = function (options) {
        var p = $.extend({
            appendTo: $('body')
        }, options || {});
        var btn = $('<div class="l-button" style="width:60px"><span></span></div>');
        if (p.icon) {
            btn.removeClass("buttonnoicon");
            btn.append('<div class="button-icon"> <img src="../' + p.icon + '" /> </div> ');
        }
        //绿色皮肤
        if (p.green) {
            btn.removeClass("button2");
        }
        if (p.width) {
            btn.width(p.width);
        }
        if (p.click) {
            btn.click(p.click);
        }
        if (p.text) {
            $("span", btn).html(p.text);
        }
        if (typeof (p.appendTo) == "string") p.appendTo = $(p.appendTo);
        btn.appendTo(p.appendTo);
    };

    //附加表单搜索按钮：搜索、高级搜索
    LG.appendSearchButtons = function (form, grid) {
        if (!form) return;
        form = $(form);
        //搜索按钮 附加到第一个li  高级搜索按钮附加到 第二个li
        var container = $('<ul><li style="margin-right:8px"></li><li></li></ul><div class="l-clear"></div>').appendTo(form);
        LG.addSearchButtons(form, grid, container.find("li:eq(0)"), container.find("li:eq(1)"));
    };

    //创建表单搜索按钮：搜索、高级搜索
    LG.addSearchButtons = function (form, grid, btn1Container, btn2Container) {
        if (!form) return;
        if (btn1Container) {
            LG.createButton({
                appendTo: btn1Container,
                text: '搜索',
                click: function () {
                    var rule = LG.bulidFilterGroup(form);
                    if (rule.rules.length) {
                        grid.set('parms', { where: JSON2.stringify(rule) });
                    } else
                    {
                        grid.set('parms', {});
                    }
                    grid.loadData();
                }
            });
        }
        if (btn2Container) {
            LG.createButton({
                appendTo: btn2Container,
                width: 80,
                text: '高级搜索',
                click: function ()
                {
                    grid.showFilter();
                }
            });
        }
    };

    //快速设置表单底部默认的按钮:保存、取消
    LG.setFormDefaultBtn = function (cancleCallback, savedCallback) {
        //表单底部按钮
        var buttons = [];
        if (cancleCallback) {
            buttons.push({ text: '取消', onclick: cancleCallback });
        }
        if (savedCallback) {
            buttons.push({ text: '保存', onclick: function(){setTimeout(function(){savedCallback();}, 500);} });
        }
        LG.addFormButtons(buttons);
    };
    
    //快速设置表单底部审批按钮:保存、取消
    LG.setapprovalBtn = function (cancleCallback, approvalCallback, historyCallback) {
        //表单底部按钮
        var buttons = [];
        if (cancleCallback) {
            buttons.push({ text: '取消', onclick: cancleCallback });
        }
        if (approvalCallback) {
            buttons.push({ text: '审批', onclick: approvalCallback });
        }
        if (historyCallback) {
            buttons.push({ text: '历史审批', onclick: historyCallback });
        }
        LG.addFormButtons(buttons);
    };
    //增加表单底部按钮,比如：保存、取消
    LG.addFormButtons = function (buttons) {
        if (!buttons) return;
        var formbar = $("form > div.form-bar");
        if (formbar.length == 0)
            formbar = $('<div class="form-bar" id="bottomField"><div class="form-bar-inner"></div></div>').appendTo('body');
        if (!(buttons instanceof Array)) {
            buttons = [buttons];
        }
        $(buttons).each(function (i, o) {
            var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
            $("div.l-dialog-btn-inner:first", btn).html(o.text || "BUTTON");
            if (o.onclick) {
                btn.bind('click', function () {
                    o.onclick(o);
                });
            }
            if (o.width) {
                btn.width(o.width);
            }
            $("> div:first", formbar).append(btn);
        });
    };
    
    //填充表单数据
    LG.loadForm = function (mainform, options, callback)
    {
        options = options || {};
        if (!mainform)
            mainform = $("form:first");
        var p = $.extend({
            beforeSend: function () {
                LG.showLoading('正在加载表单数据中...');
            },
            complete: function () {
                LG.hideLoading();
            },
            success: function (data) {
                var preID = options.preID || "";
                //根据返回的属性名，找到相应ID的表单元素，并赋值
                for (var p in data)
                {
                    var ele = $("[name=" + (preID + p) + "]", mainform);
                    //针对复选框和单选框 处理
                    if (ele.is(":checkbox,:radio"))
                    {
                        ele[0].checked = data[p] ? true : false;
                    }
                    else
                    {
                        ele.val(data[p]);
                    }
                }
                //下面是更新表单的样式
                var managers = $.ligerui.find($.ligerui.controls.Input);
                for (var i = 0, l = managers.length; i < l; i++)
                {
                    //改变了表单的值，需要调用这个方法来更新ligerui样式
                    var o = managers[i];
                    o.updateStyle();
                    if (managers[i] instanceof $.ligerui.controls.TextBox)
                        o.checkValue();
                }
                if (callback)
                    callback(data);
            },
            error: function (message) {
                LG.showError('数据加载失败!<BR>错误信息：' + message);
            }
        }, options);
        LG.ajax(p);
    };

    //带验证、带loading的提交
    LG.submitForm = function (mainform, success, error, formData) {
        if (!mainform) {
        	mainform = $("form:first");
        }

        if(mainform.valid()){
            mainform.ajaxSubmit({
                dataType: 'json',
                success: success,
                traditional: true,
				data: formData,
                beforeSubmit: function (formData, mainform, options){
                   //针对复选框和单选框 处理
                   $(":checkbox", mainform).each(function (){
                        if (!existInFormData(formData, this.name)){
                            formData.push({ name: this.name, type: this.type, value: [] });
                        }
                   });
                   
                   for (var i = 0, l = formData.length; i < l; i++){
                        var o = formData[i];
                        if (o.type == "checkbox"){
                        	o.value = [];
                        	var items = $("input[name='" + o.name + "'][checked]", mainform);
                        	if(items.size() == 0)return;
                        	$.each(items, function(i, item){
	                        	o.value.push(item.value);
                        	});
                        }
                   }
                },
                beforeSend: function (a, b, c){
                    LG.showLoading('正在保存数据中...');
                },
                complete: function (result){
                    LG.hideLoading();
                },
                error: function (result){
					if(result.status == 400){
						LG.tip(eval("(" + result.responseText + ")").content);
					} else {
						LG.tip('发现系统错误 <BR>错误码：' + result.status);
					}
                }
            });
        } else {
            LG.showInvalid();
        }
        function existInFormData(formData, name){
            for (var i = 0, l = formData.length; i < l; i++){
                var o = formData[i];
                if (o.name == name) return true;
            }
            return false;
        }
    };

    //提示 验证错误信息
    LG.showInvalid = function (validator) {
        validator = validator || LG.validator;
        if (!validator) return;
        var message = '<div class="invalid">存在' + validator.errorList.length + '个字段验证不通过，请检查!</div>';
        $.ligerDialog.error(message);
    };

    //表单验证
    LG.validate = function (form, options) {
        if (typeof (form) == "string")
            form = $(form);
        else if (typeof (form) == "object" && form.NodeType == 1)
            form = $(form);

        options = $.extend({
            errorPlacement: function (lable, element)
            {
                if (!element.attr("id")) {
                    element.attr("id", new Date().getTime());
                }
                if (element.hasClass("l-textarea"))
                {
                    element.addClass("l-textarea-invalid");
                } 
//                else if (element.hasClass("l-text-field"))
                else if (element.hasClass("error"))
                {
                    element.parent().addClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
                $(element).attr("title", lable.html()).ligerTip({
                    distanceX: 5,
                    distanceY: -3,
                    auto: true
                });
            },
            success: function (lable) {
                if (!lable.attr("for")) return;
                var element = $("#" + lable.attr("for"));

                if (element.hasClass("l-textarea")) {
                    element.removeClass("l-textarea-invalid");
                } else if (element.hasClass("l-text-field")) {
                    element.parent().removeClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
            }
        }, options || {});
        LG.validator = form.validate(options);
        return LG.validator;
    };

    LG.loadToolbar = function (grid, toolbarBtnItemClick) {
        LG.ajax({
            loading: '正在加载工具条中...',
			url: base + '/admin/sso/resource/toolBarButton.jhtml',
            data: { HttpContext: true, url: location.pathname },
            success: function (data) {
                if (!grid.toolbarManager) return;
                if (!data || !data.length) return;
                var items = [];
                for (var i = 0, l = data.length; i < l; i++){
                    var o = data[i];
                    items[items.length] = {
                        click: toolbarBtnItemClick || LG.toolbarBtnItemClick,
                        text: o.name,
						html: o.html,
                        img: o.icon,
                        id: o.id,
						url: base + "/" + o.url
                    };
                    items[items.length] = { line: true };
                }
                grid.toolbarManager.set('items', items);
            }
        });
    };
	
	LG.toolbarBtnItemClick = function(item) {
		eval(item.html);
	};
	LG.getTabById = function (tabid){
		if(!tabid){
			alert("tab id 为空");
			return;
		}
		
		var tab = $("#framecenter > .l-tab-content > .l-tab-content-item[tabid=" + tabid + "]");
		if(!tab){
			alert("tabid【" + tabid + "】 无法找到")
			return;
		}
		return tab;
	};
	LG.getCurrentTabid = function(){
		var tabid = $("#framecenter > .l-tab-content > .l-tab-content-item:visible").attr("tabid");
		return tabid;
	};
    //关闭Tab项,如果tabid不指定，那么关闭当前显示的
	LG.getCurrentTab = function() {
        tabid = LG.getCurrentTabid();
		return LG.getTabById(tabid);
	};
	LG.getParentTabid = function(){
		var parentTabid = $("#framecenter > .l-tab-content > .l-tab-content-item:visible").attr("parenttabid");
		return parentTabid;
	};
    LG.closeCurrentTab = function (tabid) {
        if (!tabid) {
            tabid = LG.getCurrentTabid();
        }
        if (tab) {
            tab.removeTabItem(tabid);
        }
    };
     
    //关闭Tab项并且刷新父窗口
    LG.closeAndReloadParent = function (tabid, parentMenuNo) {
        LG.closeCurrentTab(tabid);
        LG.reloadParent(parentMenuNo);
    };
    //只刷新不做其他
    LG.reloadParent = function (parentMenuNo) {
    	var p_tab = $("#framecenter > .l-tab-content > .l-tab-content-item[tabid= "+parentMenuNo+"]");
    	if (p_tab.size() == 0){
    		return;
    	}
       var iframe = window.frames[parentMenuNo];
        if (iframe && iframe.f_reload) {
            iframe.f_reload();
        } else if (tab) {
            tab.reload(parentTabid);
        }
    };

    //覆盖页面grid的loading效果
    LG.overrideGridLoading = function () {
        $.extend($.ligerDefaults.Grid, {
            onloading: function () {
                LG.showLoading('正在加载表格数据中...');
            }, onloaded: function () {
                LG.hideLoading();
            }
        });
    };

    //根据字段权限调整 页面配置
    LG.adujestConfig = function (config, forbidFields) {
        if (config.Form && config.Form.fields) {
            for (var i = config.Form.fields.length - 1; i >= 0; i--) {
                var field = config.Form.fields[i];
                if ($.inArray(field.name, forbidFields) != -1)
                    config.Form.fields.splice(i, 1);
            }
        }
        if (config.Grid && config.Grid.columns) {
            for (var i = config.Grid.columns.length - 1; i >= 0; i--) {
                var column = config.Grid.columns[i];
                if ($.inArray(column.name, forbidFields) != -1)
                    config.Grid.columns.splice(i, 1);
            }
        }
        if (config.Search && config.Search.fields) {
            for (var i = config.Search.fields.length - 1; i >= 0; i--) {
                var field = config.Search.fields[i];
                if ($.inArray(field.name, forbidFields) != -1)
                    config.Search.fields.splice(i, 1);
            }
        }
    };

    //查找是否存在某一个按钮
    LG.findToolbarItem = function (grid, itemID) {
        if (!grid.toolbarManager) return null;
        if (!grid.toolbarManager.options.items) return null;
        var items = grid.toolbarManager.options.items;
        for (var i = 0, l = items.length; i < l; i++)
        {
            if (items[i].id == itemID) return items[i];
        }
        return null;
    };


    //设置grid的双击事件(带权限控制)
    LG.setGridDoubleClick = function (grid, btnID, btnItemClick) {
//        btnItemClick = btnItemClick || toolbarBtnItemClick;
//        if (!btnItemClick) return;
        grid.bind('dblClickRow', function (rowdata) {
            var item = LG.findToolbarItem(grid, btnID);
            if (!item) return;
            grid.select(rowdata);
			eval(item.html);
            //btnItemClick(item);
        });
    };

	//将选中的记录转换成ID数组
	LG.getIds = function (selected){
		if(!selected instanceof Array){
			alert("参数不为数组!");
			return;
		}
		var ids = new Array();
		for (var i = 0; i < selected.length; i++){
			ids.push(selected[i].id);
		};
		return ids;
	};
	
	/**
	 * 获取grid的数据，如果column，则只获取column的数据
	 * @param grid
	 * @param column
	 * @returns {Array}
	 */
	LG.getData = function (grid, column){
		var data = [];
		var vnames = {};
		var columns = grid.getColumns();
		for(var j=0; j<columns.length; j++) {
			if(columns[j].vname) {
				vnames[columns[j].name] = columns[j].vname;
			}
		}
        for (var i=0; i<grid.getData().length; i++) {
        	var p = grid.getData()[i];
        	delete p["__status"];
        	if($.isEmptyObject(p)) {
        		continue;
        	}
        	for(var prop in p) {
        		if(vnames[prop]) {
            		p[vnames[prop]] = p[prop];
            		delete p[prop];
            	}
        	}
        	
        	if(column && o == column) {
        		for(var o in p) {
        			if(p[o].trim() == "") {
        				continue;
        			}
        			data.push(p[o]);
        			break;
        		}
        	} else {
        		data.push(p);
        	}
        }
        return data;
	};
	
	//根据grid的列的值，和所对应的value，来获取行数据
	LG.addRow = function(options, column){
		for(var i =0; i<options.length; i++){
		    if(records[i][key] == value) {
		    	return records[i];
		    }
		}
	};
	
	//根据grid的列的值，和所对应的value，来获取行数据
	LG.getRow = function(key, value){
		var records = grid.records;
		for(var i in records){
		    if(records[i][key] == value) {
		    	return records[i];
		    }
		}
	};
	//批量删除
	LG.deleteList = function(grid, url) {
		var selecteds = grid.getSelecteds();
	 	if (selecteds) {
	 		LG.ajax({
	 			url: base + url,
	 			loading: '正在删除中...',
	 			data: {ids: LG.getIds(selecteds)},
	 			success: function(data){
	 				if(data.type && data.type == "SUCCESS"){
	 					LG.showSuccess('删除成功');
	 					f_reload();
	 				}else{
	 					LG.showError(data.content, function () {
	 				   		 
	 			        });
	 				}
	 			},
	 			error: function(message){
	 				LG.showError(message);
	 			}
	 		});
	 	} else {
	 		LG.tip('请选择行!');
	 	}
	};
	
	//获取选中的行，并且只能是一行
	LG.getSelected = function(grid){
	      var selecteds = grid.getSelecteds();
	      if (selecteds.length == 1) {
	           return selecteds[0];
	      } else if(selecteds.length == 0) {
	          LG.tip("请选择行!");
	          return null;
	      } else {
	          LG.tip("请选择一行数据!");
	          return null;
	      }
	};
	
	LG.searchRecord = function(divId, grid){
		var self = this;
		var container = $("." + divId);
		container.css({"width": $("#maingrid").width() - 20 + "px", "margin": "0px 0 5px 0"});
		var save_btn = '<input type="button" id="save_btn" class="button" value="保存"/>';
		var his_btn = '<input type="button" id="his_btn" class="button" value="历史"/>';
		var adv_btn = '<input type="button" id="adv_btn" class="button" value="高级"/>';
		$("#adv_btn").click(function(){
			if (adv_btn.val() =="显示高级查询") {
				LG.showSearch(hideInput);
				adv_btn.val("隐藏高级查询");
			}
			else{
				LG.hideSearch(hideInput);
					adv_btn.val("显示高级查询");					
			}		
		});
		$("#save_btn").click(function(){
			var tabURL = getURI();
			$.ligerDialog.prompt('请输入名称','', function (yes, value) {
				if(yes){
					var searchData = getWarpPara("mainsearch");
					delete searchData[""];
					var flag = false;
					var jsonStr = "";
					for (var key in searchData) {
						if (searchData[key].trim() != "") {
							jsonStr += '"' + key + '":"' + searchData[key].trim()+ '",';
						}
					}
					if (jsonStr != "") {
						jsonStr = "{" + jsonStr.substring(0, jsonStr.length-1) + "}";
						$.ajax({
							url:base + "/admin/base/searchRecord/add.jhtml",
							type:"POST",
							data:{tabURL:tabURL, name:value, searchJson:jsonStr},
							dataType:"JSON",
							success:function(data){
								LG.success("保存成功！");
							}
						});
					} else {
						LG.tip("您当前还没有输入查询条件！");
					}
				}
			});
		});
		/**
		$("#his_btn").click(function(){
			$.ligerDialog.open({ url: base + '/admin/base/searchRecord/list.jhtml', height: 400, width: 300, 
				buttons: [{text: '确定', 
					onclick: function (item, dialog) { 
						var selectItem = dialog.frame.getselectItem();
						if(!selectItem){
							LG.showError("请选择一条记录!");
							return;
						}
						$.ajax({
							url:base + "/admin/base/searchRecord/detail.jhtml",
							type:"GET",
							data:{id:selectItem.id},
							dataType:"JSON",
							success:function(data){
								data = eval(data);
								var inputs = $("#mainsearch").find("input, select");
								var attributes = eval("("+data.searchJson+")");
								for (var i = 0; i < inputs.length; i++) {
									var input = $(inputs[i]);
									var curName = input.attr("name");
									if (!curName){
										continue;
									}
									curName = curName.trim();
									if (attributes[curName]){
										input.val(attributes[curName]);	
									}
								}
							}
						});
						dialog.close();
					}},{ text: '取消', onclick: function (item, dialog) { dialog.close();}},
					{ text: '删除', onclick: function (item, dialog) {	
						var selectItem = dialog.frame.getselectItem();
						if(!selectItem){
							LG.showError("请选择一条记录!");
							return;
						}
						$.ligerDialog.confirm('确定要删除这个查询记录吗？', function (yes) { 
							$.ajax({
								url:base + "/admin/base/searchRecord/delete.jhtml",
								type:"GET",
								data:{id:selectItem.id},
								dataType:"JSON",
								success:function(data){}
							});
							dialog.close();
						});
					}}
				], isResize: true
		    });
		});
		**/
		$("#search").click(function(){
			self.parms = {}; // 最初的parms
			var searchData = getWarpPara("mainsearch");
			delete searchData[""];
			self.flag = false;
			var searchParam = [];
			for (var key in searchData) {
				if (searchData[key].trim() != "") {
					self.flag = true;
					searchParam.push({"name":key,"value":searchData[key]});
					//break;
				}
			}
			if (self.flag) {
				$("#search_btn_reset").css("display", "inline");
				self.parms = grid.options.parms; // 最初的parms
				grid.options.parms = searchParam;
				grid.options.newPage=1; //将搜索页变成第一页
				grid.loadData(true);
			}
		});
		// 如果存在导出设置，并且为true，则添加导出按钮
		if(typeof isexport != 'undefined' && isexport) {
			$("#searchBtn").append("<input type='button' id='export' class='button' value='导出'/>");
			$("#export").click(function(){
				window.open(location.href + "?exportXls=2&" + $.param(gridCondition(), true));
			});
		}
		
		// 如果存在导出设置，并且为true，则添加导出按钮
		if(typeof isimport != 'undefined' && isimport) {
			$("#searchBtn").append("<input type='button' id='import' class='button' value='导入'/>");
			initupload({id:"fileupload", pluginId:pluginId, btnId:"import"}, grid);
		}
	},
	
	LG.addGridRow = function(grid, datas, change, dataid){
		var rows = grid.rows;
		if (datas){
			for (j=0;j<datas.length;j++){
				var data = datas[j];
				// 寻找product_id为空的行，即空白行
				for (i=0; i<rows.length; i++){
					if (!rows[i][dataid]){
						var editParm = {newValue: data.id, rowindex: i, column: grid.getColumn("fullname"), async: false};
						change(editParm);
						checkgrid(grid);
						rows = grid.rows; // 表格变化了，行数等都变化，必须重新判断
						break;
					}
				}
			}
			AdvGrid.deductionChange();
		}
	},
	LG.searchTable = function(){
		var url = window.location.pathname;		
		$.ajax({
			url: base + "/admin/base/table/query.jhtml",
			type:"POST",
			data:{"url": url},
			dataType:"JSON",
			async: false,
			success:function(result){
				if(!result || result.length == 0) {
					return;
				}
				var showData = new Array();
				var advData = new Array();
				$.each(result, function(){ 
					//if (this.newline == true) this.newline = true;
//					else this.newline = false;
					var oldName = this.name;
					if(this.type == "categorySelect" || this.type == "productType") {
						this.name = "search_" + this.name + ".id_" + this.operator;
					} else {
						this.name = "search_" + this.name + "_" + this.operator;
					}
					
					if (this.type =="enumSelect" && this.data){					
						this.type = "select";
						this.comboboxName = this.name;
						this.editor = {"data": eval(this.data)} ;
					} else if (this.type =="autocomplete" && this.options){					
						this.type = "select";
						this.ltype = "combobox";
						this.options = eval("(" + this.options + ")");
						this.options.autocomplete=true;
					} else if(this.type=="select" && this.options) {
						this.type = "select";
						this.comboboxName = this.name;
						this.editor = {"data": eval("(" + this.options + ")")};
					} else if(this.type == "categorySelect") {
						this.type = "select";
						this.comboboxName = this.name;
						this.editor = {"data": eval(this.data)};
					} else if (this.type == "productType"){
						this.type = "select";
						this.comboboxName = this.name;
						this.editor = {"data": eval(this.data)};
					}
					if (this.advanced == true){					
						advData.push(this);
					} else {
						showData.push(this);
					}
					
					if(this.type == "dateRange") {
						this.type = "date";
						this.name = "search_" + oldName + "_gte";
						var rangeDate = $.extend({}, this);
						rangeDate.display = "到";
						rangeDate.name = "search_" + oldName + "_lt";
						rangeDate.labelWidth = '20';
						if (this.advanced == true){					
							advData.push(rangeDate);
						} else {
							showData.push(rangeDate);
						}
					}
				});
				var searchBtnWidth = $("#searchBtn").width();
			    form = $(".searchbox").ligerForm({
                	fields: showData,
                	width: $(window).width() - searchBtnWidth
            	});
            	advform = $(".advsearchbox").ligerForm({               
                	fields: advData,
                	width: $(window).width() - searchBtnWidth
            	});
            	$("#search_btn_reset").click(function(){
					$(".searchbox")[0].reset();
					$(".searchbox input").val("");
					$(".advsearchbox")[0].reset();
					$(".advsearchbox input").val("");
					grid.options.parms = self.parms||[];
					grid.loadData(true);
					$(this).css("display", "none");
				});
			}
		});	
	},
	LG.initIconList = function(input, callback) {

        if(!callback) {
            callback = function(cssClass) {
                input.next("i").remove();
                input.val(cssClass);
                input.after("<i class='" + cssClass + "' style='margin-left:10px;'></i>");
            }
        }

        input.off("click").on("click", function() {
            var iconList = $(".sys-icon-list");
            if(iconList.is(":visible")) {
                onBodyDown();
                return;
            }
            if(!iconList.length) {
                $.ajax({
                    type : "GET",
                    async : false,
                    url : base + "/admin/base/icon/select.jhtml",
                    success : function (html) {
                        $("body").append(html);
                    }
                });
                iconList = $(".sys-icon-list");

            }

            var inputOffset = input.offset();
            iconList.css({left: inputOffset.left + "px", top: inputOffset.top + input.outerHeight() + "px"}).slideDown("fast");
            iconList.find(".tab-pane").niceScroll({domfocus:true, hidecursordelay: 2000});
            iconList.find(".tab-pane").getNiceScroll().show();


            $("body").bind("mousedown", onBodyDown);

            function onBodyDown(event) {
                if(event) {
                    var target = $(event.target);
                    var btn = target.closest("a.sys-icon-btn");
                    if(btn.length) {
                        callback(btn.find("i").attr("class"));
                    }

                    if(!btn.length && target.closest(".sys-icon-list").length) {
                        return;
                    }
                }

                iconList.find(".tab-pane").getNiceScroll().hide();
                iconList.fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
        });
        

    };
	LG.hideSearch = function(input){
		form.setVisible(input,false);
	};
	LG.showSearch = function(input){
		form.setVisible(input,true);
	};
	// 判断行是否为空
	LG.isEmptyRow = function(row) {
		for(var s in row) {
			if(!s.startWith("__")) {
				return false;
			}
		}
		return true;
	}
	
})(jQuery);
$(document).ajaxComplete(function(event, request, settings) {
	var loginStatus = request.getResponseHeader("loginStatus");
	var tokenStatus = request.getResponseHeader("tokenStatus");
	
	if (loginStatus == "accessDenied") {
		$.redirectLogin(window.location.href, "请登录后再进行操作");
	} else if (tokenStatus == "accessDenied") {
		var token = getCookie("token");
		if (token != null) {
			$.extend(settings, {
				global: false,
				headers: {token: token}
			});
			$.ajax(settings);
		}
	}
});
//根据id清空搜索框
function clearSearchWeiget(id){
	var inputs = $("#"+id).find("input[type!='button'], select");
	for (var i = 0; i < inputs.size(); i++) {
		var input = $(inputs[i]);
		//如果是checkbox
		if(input.attr("type") == "checkbox" && input.val() != ''){
			input.attr("checked", false);
			continue;
		} else if(input.attr("type") == "radio"){
			if (input.attr("checked")) {
				input.attr("checked", false);
			}
			continue;
		} else if (input.val() != '') {
			input.val("");
			continue;
		}
	}
};

function checkgrid(grid){
	var rows = grid.rows;
	var flag = false;
	for (i=0; i<rows.length; i++){
		var row = rows[i];
    	if(LG.isEmptyRow(row)) {
    		flag = true;
    		break;
    	}
	}
	if(!flag) grid.addRows([{}, {}]);
}
function getURI() {
	var url = window.location.href;
	var s = url.split("//");
	return s[1].substring(s[1].indexOf("/"));
}