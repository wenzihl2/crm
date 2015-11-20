<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
	/**
	 * 新增账号
	 */
	function add(){
		top.f_addTab('auth_${type}_add', '新增授权实体', base + '/admin/sso/auth/${type}/create.jhtml');
	}
	
	function edit(){
		var row = LG.getSelected(grid);
		if(row){
			top.f_addTab('auto_${type}_edit', '修改授权实体', base + '/admin/sso/auth/' + row.id + '/update.jhtml');
		}
	}
	
	function addRole(){
		var row = LG.getSelected(grid);
		if(row){
			$.ligerDialog.open({title:"绑定角色", height: 500, width:800,url: base + '/admin/sso/user/addRole.jhtml?s_user.id=' + row.id});
		}
	}
	
    $(function () {
    	$("#navtab2").ligerTab({
    		onBeforeSelectTabItem:function(tabid){
    			location.href = "${base}/admin/sso/auth.jhtml?search.type_eq=" + tabid;
            }
    	});
    	
        grid = $("#maingrid").ligerGrid({
            columns: [
			{display: '编号', name: 'id', width:50, align: 'left', isAllowHide: false},
			[#if type == "user"]
			{display: '用户名', name: 'userId', align: 'left', width: 120, convertType:'user', convertKey:'username'},
			[/#if]
			
			[#if type == "user_group"]
			{display: '用户组名称', name: 'groupId', align: 'left', width: 120, convertType:'group', convertKey:'name'},
			[/#if]
			
			[#if type == "dept_job"]
			{display: '组织机构', name: 'deptId', align: 'left', width: 120, convertType:'deptTree'},
			{display: '工作职务', name: 'jobId', align: 'left', width: 120, convertType:'jobTree'},
			[/#if]
			
			[#if type == "dept_group"]
			{display: '组织机构组', name: 'groupId', align: 'left', width: 120, convertType:'group', convertKey:'name'},
			[/#if]
			
			{display: '角色列表', name: 'roleIds', align: 'left', width: 300, convertType:'role' }
			],  pageSize:20, url: location.href,
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
    });
  	
  	function deleteList(){
  		var row = LG.getSelected(grid);
		if(row){
			LG.deleteList(grid, base + '/admin/sso/auth/' + row.id + '/delete.jhtml');
		}
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="navtab2">
		<div title="用户授权列表" [#if type == "user"]lselected="true"[/#if] tabid="user"></div>
		<div title="用户组授权列表" [#if type == "user_group"]lselected="true"[/#if] tabid="user_group"></div>
		<div title="组织机构和工作职务授权列表" [#if type == "dept_job"]lselected="true"[/#if] tabid="dept_job"></div>
		<div title="组织机构组授权列表" [#if type == "dept_group"]lselected="true"[/#if] tabid="dept_group"></div>
	</div>
	[#include "/admin/include/search-bar.ftl"]
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>