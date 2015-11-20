<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<style>
    .detail {
        word-break: break-all;
    }
</style>
</head>
<body style="padding:5px;">
<div data-table="table" class="panel">
    [#include "/admin/monitor/ehcache/nav.ftl"]
    <a href="[@BackURL/]" class="btn btn-link pull-right">返回</a>
    <a href="?BackURL=[@BackURL/]" class="btn btn-link pull-right">刷新</a>
    <a class="btn btn-link pull-right btn-clear">清空</a>

    <table class="table table-bordered">
        <tbody>

        <tr class="bold info">
            <td colspan="2">
                ${cacheName} 键列表
                <esform:input path="searchText" class="input-medium pull-right no-margin" placeholder="回车模糊查询"/>
            </td>
        </tr>

        [#list keys as key]
            <tr>
                <td style="width: 40%">${key}</td>
                <td data-key="${key}">
                    <a class="btn btn-link no-padding btn-details">查看详细</a>
                    <a class="btn btn-link no-padding btn-delete">删除</a>
                </td>
            </tr>
        [/#list]

        </tbody>
    </table>
    <br/><br/>
</div>
<script type="text/javascript">
$(function() {
    $("#searchText").keyup(function(event) {
        if(event.keyCode == 13) {
            window.location.href = "?searchText=" + this.value + "&BackURL=[@BackURL/]";
        }
    });

    $(".btn-details").click(function() {
        var td = $(this).closest("td");
        var key = td.data("key");
        var url = "${base}/admin/monitor/ehcache/${cacheName}/" + key + "/details.jhtml";
        $.getJSON(url, function(data) {
            var detail = td.find(".detail");
            if(detail.length) {
                detail.remove();
            }
            detail = $("<div class='detail alert alert-block alert-info in fade'><button type='button' class='close' data-dismiss='alert'>&times;</button></div>");

            var detailInfo = "";
            detailInfo += "命中次数:" + data.hitCount;
            detailInfo +=" | ";
            detailInfo += "大小:" + data.size;
            detailInfo +=" | ";
            detailInfo += "最后创建/更新时间:" + data.latestOfCreationAndUpdateTime;
            detailInfo +=" | ";
            detailInfo += ",最后访问时间:" + data.lastAccessTime;
            detailInfo +=" | ";
            detailInfo += "过期时间:" + data.expirationTime;
            detailInfo +=" | ";
            detailInfo += "timeToIdle(秒):" + data.timeToIdle;
            detailInfo +=" | ";
            detailInfo += "timeToLive(秒):" + data.timeToLive;
            detailInfo +=" | ";
            detailInfo += "version:" + data.version;

            detailInfo +="<br/><br/>";
            detailInfo +="值:" + data.objectValue;

            detail.append(detailInfo);
            td.append(detail);

            td.find(".alert").alert();

        });
    });

    $(".btn-delete").click(function() {

            var td = $(this).closest("td");
            var key = td.data("key");
            var url = "${base}/admin/monitor/ehcache/${cacheName}/" + key + "/delete.jhtml";
            $.get(url, function(data) {
                td.closest("tr").remove();
            });

    });

    $(".btn-clear").click(function() {
    	$.ligerDialog.confirm('确认清空整个缓存吗？',  function(){
    		var url = "${base}/admin/monitor/ehcache/${cacheName}/clear.jhtml";
            LG.ajax({
            	url: url, 
            	success: function(data) {
                	window.location.reload();
            	}
            });
    	});
    });
});

</script>
</body>
</html>