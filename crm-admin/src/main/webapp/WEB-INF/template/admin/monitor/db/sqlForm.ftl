<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<link rel="stylesheet" href="${base}/resource/css/bootstrap.css" type="text/css" />
<script type="text/javascript">
$(function(){
	$(".btn-pre-page").click(function() {
        var $pn = $("[name='page.pageNumber']");
        $pn.val(parseInt($pn.val()) - 1);
        $("form")[0].submit();
    });
    $(".btn-next-page").click(function() {
        var $pn = $("[name='page.pageNumber']");
        if(!$pn.val()) {
            $pn.val(1);
        } else {
            $pn.val(parseInt($pn.val()) + 1);
        }
        $("form")[0].submit();
    });
});
</script>
</head>
<body>
<div data-table="table" class="panel">
	[#assign type="sql" /]
    [#include "/admin/monitor/db/nav.ftl"]

    <form method="post" class="form-inline">
    	<input type="hidden" name="page.pageNumber" value="${(resultPage.number)!''}">
        <label>请输入SQL(不支持DDL/DCL执行)：</label><br/>
        <textarea name="sql" style="width: 500px;height: 160px">${(sql)!''}</textarea>
        <input type="submit" class="btn" value="执行">
    </form>

    <div id="result">
    	[#if error??]
            出错了：<br/>
            ${error}<br/>
        [/#if]
        [#if updateCount??]更新了${updateCount}行[/#if]
        [#if resultPage??]
	        [#if resultPage.totalElements == 0]没有结果[/#if]
	        [#if resultPage.totalElements > 0]
	            当前第${resultPage.number+1}页，总共${resultPage.totalPages}页/${resultPage.totalElements}条记录
	            [#if resultPage.hasPreviousPage()]
	                <a class="btn btn-link btn-pre-page">上一页</a>
	            [/#if]
	            [#if resultPage.hasNextPage()]
	            	<a class="btn btn-link btn-next-page">下一页</a>
	            [/#if]
	            <br/>
	            <table class="table table-bordered table-hover">
	                <thead>
	                    <tr>
	                    	[#list columnNames! as columnName]
	                    		<th>${columnName}</th>
	                    	[/#list]
	                    </tr>
	                </thead>
	                <tbody>
	                	[#list resultPage.getContent()! as o]
	                    	<tr>
	                        [#if !(o.class.isArray??)]
	                            <td>${o}</td>
	                        [#else]
	                            [#list o! as c]
	                               <td>${c}</td>
	                            [/#list]
	                        [/#if]
	                        </tr>
	                    [/#list]
	                </tbody>
	            </table>
	         [/#if]
	     [/#if]
    </div>

</div>
</body>
</html>