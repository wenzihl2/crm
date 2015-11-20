var SelectDialog = {
	supplyDialog: function (key){
		var self = this;
		var req_url = base + '/admin/crm/supply/list_dialog.jhtml';
		if (key){
			req_url += "?key=" + encodeURI(key);
		}
    	$.ligerDialog.open({title:'选择供应商', url: req_url, height: 500, width:800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItem = dialog.frame.LG.getSelected(dialog.frame.grid);
					self._setValue(selectItem.id, selectItem.name);
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
       });
       return false;
    },
    customerDialog: function (key){
		var self = this;
		var req_url = base + '/admin/crm/customer/list_dialog.jhtml';
		if (key){
			req_url += "?key=" + encodeURI(key);
		}
    	$.ligerDialog.open({title:'选择客户', url: req_url, height: 500, width:800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItem = dialog.frame.LG.getSelected(dialog.frame.grid);
					self._setValue(selectItem.id, selectItem.name);
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
       });
       return false;
    },
    allDialog: function (key){
		var self = this;
		var req_url = base + '/admin/crm/all/list_dialog.jhtml';
		if (key){
			req_url += "?key=" + encodeURI(key);
		}
    	$.ligerDialog.open({title:'选择客户', url: req_url, height: 500, width:800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItem = dialog.frame.LG.getSelected(dialog.frame.grid);
					self._setValue(selectItem.id, selectItem.name);
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
       });
       return false;
    },
    pruductDialog: function(column){
    	var baseUrl = base + '/admin/scm/product/list_dialog.jhtml';
    	if(column.editor.getUrlParam) {
    		baseUrl += column.editor.getUrlParam();
    	}
    	$.ligerDialog.open({title:"选择商品", url: baseUrl, height: 500, width: 800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItems = dialog.frame.grid.getSelecteds();
					LG.addGridRow(AdvGrid.getGrid(), selectItems, column.editor.onChange, 'product_id');
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
            ], isResize: true
        });
    },
    goodsDialog: function(column){
    	var baseUrl = base + '/admin/scm/goods/list_dialog.jhtml';
    	if(column.editor.getUrlParam) {
    		baseUrl += column.editor.getUrlParam();
    	}
    	$.ligerDialog.open({title:"选择货号", url: baseUrl, height: 500, width: 800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItems = dialog.frame.grid.getSelecteds();
					LG.addGridRow(AdvGrid.getGrid(), selectItems, column.editor.onChange, 'goods_id');
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
            ], isResize: true
        });
    },
    workerDialog: function() {
    	var self = this;
    	$.ligerDialog.open({title:"选择员工", url: base + '/admin/sso/worker/list_dialog.jhtml', height: 500, width: 800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItem = dialog.frame.LG.getSelected(dialog.frame.grid);
					self._setValue(selectItem.id, selectItem.name);
					dialog.close();
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
        });
    	return false;
    },
    userBindDialog:function(worker_id){
    	$.ligerDialog.open({title:"选择账户", url: base + '/admin/sso/user/list_dialog.jhtml', height: 500, width: 800, 
    		buttons: [{ 
    			text: '确定', onclick: function (item, dialog) { 
    				var selectItem = dialog.frame.LG.getSelected(dialog.frame.grid);
    				if(selectItem["name"]) {
    					LG.tip("此账号已经绑定了职员，请重新选择!");
    					return;
    				}
    				
					LG.ajax({
						url:base + "/admin/sso/worker/bind.jhtml",
						data:{worker_id: worker_id, user_id: selectItem.id},
						success:function(data){
							var win = parent || window;
							if (data.type && data.type == "SUCCESS"){
								win.LG.showSuccess('操作成功', function () {
									dialog.close();
								});
							}
						}
					});
    			} 
    		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
             ], isResize: true
        });
    	return false;
    }
};