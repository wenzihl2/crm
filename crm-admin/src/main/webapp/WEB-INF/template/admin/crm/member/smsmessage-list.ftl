<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
</head>
<body>
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '编号', name: 'sn', align: 'left', width: 150 },
			{display: '手机号码', name: 'smsNumber', align: 'center', width: 100 },
			{display: '创建时间', name: 'createDate', align: 'center', width: 150 },
			{display: '发送时间', name: 'sendTime', align: 'center', width: 150 },
			{display: '返回信息', name: 'description', align: 'center', minWidth: 100 },
			{display: '短信类型', name: 'type', align: 'center', width: 100,i18n:'MessageType'},
            {display: '状态', name: 'status', align: 'center', width: 100,i18n:'MessageStatus'}
			],  pageSize:20, url: '/admin/member/SmsMessage/queryData.jhtml',
			toolbar: {}, width: '100%', height:'100%', enabledSort: true,checkbox:false
        });
        //双击事件(id:为点击操作的id)
      	LG.setGridDoubleClick(grid, 445);
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		//绑定搜索按钮
		LG.search(grid);
    });
	//编辑账号
	function editMember(member_id){
		top.f_addTab(null, '编辑会员',  '/admin/member/edit.jhtml?id=' + member_id + "&parentMenuNo=member_list");
	};
	
	//删除
     function f_delete() {
      var selected = grid.getSelected();
      if (selected) {
          LG.ajax({
              url:  '/admin/member/delete.jhtml',
              loading: '正在删除中...',
              data: { ids: selected.id },
              success: function () {
                  LG.showSuccess('删除成功');
                  f_reload();
              },
              error: function (message) {
                  LG.showError(message);
              }
          });
      } else {
          LG.tip('请选择行!');
      }
  }
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="functions">
		<div class="clearfix">
			<div style="float:left;">
				<span id="searchDefault">
					编号：<input type="text" class="formText" name="search_LIKE_sn"/>&nbsp;
				</span>
				<span id="searchDefault">
					手机号码：<input type="text" class="formText" name="search_LIKE_smsNumber"/>&nbsp;
				</span>
				<span>
					状态：<@u.select_map headerKey="" headerValue="全部" id="status"
					headerButtom="false" list=DictionaryUtils.getAllMessageStatus()
					name="search_EQ_status"/>
				</span>
				<span>
					短信类型：<@u.select_map headerKey="" headerValue="全部" id="type"
					headerButtom="false" list=DictionaryUtils.getAllMessageType()
					name="search_EQ_type"/>
				</span>				
				<input type="button" id="search" class="button" value="搜索"/>
				<a href="#" id="search_btn_reset" face="Wingdings 3">清除本次搜索</a>
			</div>
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>