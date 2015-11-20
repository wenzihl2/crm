<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
[#if !m??]
	[#assign isAdd = true /]
[#else]
	[#assign isEdit = true /]
[/#if]
[#if !type??]
	[#assign type = m.type /]
[/#if]
<style type="text/css">
    .middle input {
        display: block;width:30px; margin:2px;
    }
</style>
<script type="text/javascript">
	[#if isEdit??]
	function initDate() {
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        box1.setValue(${(m.roleIds)!''}.concat(", "))
        moveToRight();
    }
    [/#if]
	function moveToLeft() {
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box2.getSelectedItems();
        if (!selecteds || !selecteds.length) return;
        box2.removeItems(selecteds);
        box1.addItems(selecteds);
    }
    function moveToRight() {
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box1.getSelectedItems();
        if (!selecteds || !selecteds.length) return;
        box1.removeItems(selecteds);
        box2.addItems(selecteds);
    }
    function moveAllToLeft() { 
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box2.data;
        if (!selecteds || !selecteds.length) return;
        box1.addItems(selecteds);
        box2.removeItems(selecteds); 
    }
    function moveAllToRight() { 
        var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
        var selecteds = box1.data;
        if (!selecteds || !selecteds.length) return;
        box2.addItems(selecteds);
        box1.removeItems(selecteds);
       
    }
    [#if type == 'user']
	function getGridOptions(checkbox) {
	    var options = {
	        columns: [
	        {display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '账号', name: 'username', align: 'left', width: 120 },
			{display: '姓名', name: 'name', align: 'left', width: 100 }
	        ], switchPageSizeApplyComboBox: false,
	        pageSize:20, url: base + "/admin/sso/user.jhtml",
	        checkbox: checkbox
	    };
	    return options;
	}
	var condition = { fields: [{ name: 'name', label: '姓名',width:90, type:'text' }] };
	[/#if]
	
	[#if type == 'user_group' || type == 'dept_group']
	function getGridOptions(checkbox) {
	    var options = {
	        columns: [
	        {display: 'ID', name: 'id', width:30, align: 'left', isAllowHide: false},
			{display: '分组名称', name: 'name', align: 'left', width: 220 }
	        ], switchPageSizeApplyComboBox: false,
	        pageSize:20, url: base + "/admin/sso/group.jhtml?search.type_eq=[#if type == 'user_group']user[#elseif  type == 'dept_group']dept[/#if]",
	        checkbox: checkbox
	    };
	    return options;
	}
	var condition = {fields: [{ name: 'name', label: '分组名称',width:90, type:'text'}]};
	[/#if]
	$(function () {
		var mainform = $("#mainform");
		[#if type == 'user']
        $("#user").ligerComboBox({
            width: 300,
            slide: true,
            selectBoxWidth: 300,
            selectBoxHeight: 240,
            valueField: 'id',
            textField: 'name',
            split: ",",
            grid: getGridOptions(true),
            condition: condition,
            valueFieldID: 'userIds'
        });
        [/#if]
		
		[#if type == 'user_group']
		$("#user_group").ligerComboBox({
            width: 300,
            slide: true,
            selectBoxWidth: 300,
            selectBoxHeight: 240,
            valueField: 'id',
            textField: 'name',
            split: ",",
            grid: getGridOptions(true),
            condition: condition,
            valueFieldID: 'groupIds'
        });
        [/#if]
        
        [#if type == 'dept_group']
        $("#dept_group").ligerComboBox({
            width: 300,
            slide: true,
            selectBoxWidth: 300,
            selectBoxHeight: 240,
            valueField: 'id',
            textField: 'name',
            split: ",",
            grid: getGridOptions(true),
            condition: condition,
            valueFieldID: 'groupIds'
        });
        [/#if]
        
        [#if type == 'dept_job']
        form = mainform.ligerForm();
        grid = $("#dept_job").ligerGrid({
	        columns: [
	        {display: '', width:80, isSort: false, align: 'center', width: 80, render: function (rowdata, rowindex, value){
				var h = '<div data-id="9" class="operating">';
	            h += '<span title="删除行" class="ui-icon ui-icon-trash" onclick="javascript:grid.deleteRow(' + rowindex + ');">&nbsp;&nbsp;</span>';
	            h += '</div>';
	            return h;
			}},
			{ display: '', name: 'jobId', width:10, hide: true},
			{ display: '', name: 'deptId', width:10, hide: true},
			{ display: '组织机构', name: 'dept', width:120},
	        { display: '工作职务', name: 'job', width:120}
	        ],
	        enabledEdit: true, isScroll: false, checkbox:false, usePager:false, width: 500
	    });
	    /**
	    if(grid.getData().length < 5) {
	    	var len = grid.getData().length;
	    	for(var i=0; i< 5 - len; i++) {
	    		grid.addEditRow();
	    	}
	    }**/
	    [/#if]
        $("#listbox1, #listbox2").ligerListBox({
        	textField: 'name',
        	split: ",",
            isShowCheckBox: false,
            isMultiSelect: true,
            height: 140,
            valueFieldID: 'roleIds'
        });
        liger.get("listbox1").setData(${roles});
        [#if isEdit??]
		initDate();
		[/#if]
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
    		liger.get("listbox2").selectAll();
			f_save(mainform, "auth"[#if type == 'dept_job' && isAdd??], getDeptJob()[/#if]);
		});
	});
	
	function addDeptJob() {
		var dept = liger.get("dept");
		var job = liger.get("job");
		if(dept.getValue() == "" && job.getValue() == "") {
			alert("请选择组织机构或工作职务!");
			return false;
		}
		grid.addEditRow({jobId: job.getValue(), job: job.getText(), deptId:dept.getValue(), dept:dept.getText()});
		dept.clear();
		job.clear();
	}
	
	function getDeptJob() {
		var deptJobs = LG.getData(grid);
		alert(deptJobs.length);
    	if(deptJobs.length == 0) {
    		alert("请添加组织机构和工作职务!");
    		return false;
    	}
    	var deptIds = []; var jobIds = [];
    	for(var i=0; i<deptJobs.length; i++) {
    		var deptJob = deptJobs[i];
    		deptIds.push(deptJob.deptId);
    		jobIds.push(deptJob.jobId);
    	}
    	return {deptIds: deptIds, jobIds: jobIds};
	}
</script>
</head>
<body style="padding:10px;">
	<form action="[#if isAdd??]create.jhtml[#else]update.jhtml[/#if]" method="post" id="mainform">
		[#if !isAdd??]
			<input type="hidden" name="id" value="${m.id}">
			<input type="hidden" name="type" value="${type}">
		[/#if]
		
		[#if type == 'dept_job' && isAdd??]
		<div class="l-group l-group-hasicon">
			<img src="${base}/resource/thirdparty/liguerUI125/skins/icons/communication.gif">
			<span style="width: 300px;">请选择组织机构和工作职务</span>
		</div>
		<ul>
			<li class="l-fieldcontainer">
				<ul>
					<li style="width:90px;text-align:left;">组织机构:</li>
					<li style="width:170px;text-align:left;">
						<input type="text" ltype="select" id="dept" validate="{required: true}" options='{
			                width: 180,
			                selectBoxWidth: 200,
			                selectBoxHeight: 200, valueField: "id", textField: "name",
			                treeLeafOnly:false,
			                tree: { url: base + "/admin/sso/dept/ajax/load.jhtml", checkbox: false,
			            	parentIDFieldName :"pId", textFieldName: "name"}}'>
		            </li>
		        </ul>
		    </li>
		    <li class="l-fieldcontainer">
				<ul>
		            <li style="width:90px;text-align:left;">工作职务:</li>
		         	<li style="width:170px;text-align:left;">
						<input type="text" ltype="select" id="job" validate="{required: true}" options='{
			                width: 180,
			                selectBoxWidth: 200,
			                selectBoxHeight: 200, valueField: "id", textField: "name",
			                treeLeafOnly:false,
			                tree: { url: base + "/admin/sso/job/ajax/load.jhtml", checkbox: false,
			            	parentIDFieldName :"pId", textFieldName: "name"}}'>
		         	</li>
		         </ul>
		    </li>
         	<li style="width:90px;text-align:left;"><input type="button" value="添加" onclick="addDeptJob();"></li>
         	<div class="l-clear"></div>
        </ul>
        <div class="l-group l-group-hasicon">
			<img src="${base}/resource/thirdparty/liguerUI125/skins/icons/communication.gif">
			<span style="width: 300px;">已选择的组织机构和工作职务</span>
		</div>
		<div id="dept_job"></div>
		<div class="l-group l-group-hasicon">
			<img src="${base}/resource/thirdparty/liguerUI125/skins/icons/communication.gif">
			<span style="width: 300px;">角色信息</span>
		</div>
		[/#if]
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			[#if type == 'user']
			<tr>
				<th class="l-table-edit-td">用户信息:</th>
				<td class="l-table-edit-td">
					[#if isAdd??]<input type="text" id="user" style="width: 500px;"/>[/#if]
					[#if isEdit??]
						[@DataConvert convertType="user" convertKey="username" value=m.userId /]
						<input type="hidden" name="userId" value="${m.userId}"/>
					[/#if]
				</td>
			</tr>
			[/#if]
			[#if type == 'user_group']
			<tr>
				<th class="l-table-edit-td">用户组信息:</th>
				<td class="l-table-edit-td">
					[#if isAdd??]<input type="text" id="user_group" style="width: 500px;"/>[/#if]
					[#if isEdit??]
						[@DataConvert convertType="group" convertKey="name" value=m.groupId /]
						<input type="hidden" name="groupId" value="${m.groupId}"/>
					[/#if]
				</td>
			</tr>
			[/#if]
			
			[#if type == 'dept_job' && isEdit??]
			<tr>
				<th class="l-table-edit-td">组织机构:</th>
				<td class="l-table-edit-td">
					[@DataConvert convertType="deptTree" value=m.deptId /]
					<input type="hidden" name="deptId" value="${m.deptId}"/>
				</td>
			</tr>
			<tr>
				<th class="l-table-edit-td">工作职务:</th>
				<td class="l-table-edit-td">
					[@DataConvert convertType="jobTree" value=m.jobId /]
					<input type="hidden" name="jobId" value="${m.jobId}"/>
				</td>
			</tr>
			[/#if]
			
			[#if type == 'dept_group']
			<tr>
				<th class="l-table-edit-td">组织机构组:</th>
				<td class="l-table-edit-td">
					[#if isAdd??]<input type="text" id="dept_group" style="width: 500px;"/>[/#if]
					[#if isEdit??]
						[@DataConvert convertType="group" convertKey="name" value=m.groupId /]
						<input type="hidden" name="groupId" value="${m.groupId}"/>
					[/#if]
				</td>
			</tr>
			[/#if]
			<tr>
				<th class="l-table-edit-td">角色列表:</th>
				<td class="l-table-edit-td">
					 <div style="margin:4px;float:left;">
         				<div id="listbox1"></div>  
				     </div>
				     <div style="margin:4px;float:left;" class="middle">
				         <input type="button" onclick="moveToLeft()" value="<" />
				          <input type="button" onclick="moveToRight()" value=">" />
				          <input type="button" onclick="moveAllToLeft()" value="<<" />
				         <input type="button" onclick="moveAllToRight()" value=">>" />
				     </div>
				    <div style="margin:4px;float:left;">
				        <div id="listbox2"></div> 
				    </div>
				</td>
			</tr>
		</table>
	</form>
</body>   
</html>