<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<link rel="stylesheet" href="${base}/resource/css/bootstrap.css" type="text/css" />
</head>

<body>
<div data-table="table" class="panel">
    [#include "/admin/monitor/ehcache/nav.ftl"]
	[#--
    <ul class="nav nav-pills">
        <li ${empty param.sort ? "class='active'" : ""}><a href="?">默认排序</a></li>
        <li ${param.sort eq 'hitPercent' ? "class='active'" : ""}><a href="?sort=hitPercent">总命中率</a></li>
        <li ${param.sort eq 'objectCount' ? "class='active'" : ""}><a href="?sort=objectCount">内存中对象数</a></li>
    </ul>
	--]
    <table class="table table-bordered">
        <tbody>
        [#list cacheNames as cacheName]
            [#assign cache=cacheManager.getCache(cacheName) /]
	        [#assign totalCount=cache.statistics.cacheHits + cache.statistics.cacheMisses /]
	        [#if totalCount <= 0]
	        	[#assign totalCount=1 /]
	        [/#if]
	        [#assign cacheHitPercent=cache.statistics.cacheHits * 1.0 / totalCount /]
		
        <tr class="bold info">
            <td colspan="2">
            ${cacheName}
            <a href="${base}/admin/monitor/ehcache/${cacheName}/details.jhtml" class="btn btn-link no-padding pull-right">详细</a>
            </td>
        </tr>

        <tr>
            <td>总命中率:</td>
            <td>${cacheHitPercent}</td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>${cache.statistics.cacheHits}</td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>${cache.statistics.cacheMisses}</td>
        </tr>
        <tr>
            <td>缓存总对象数:</td>
            <td>${cache.statistics.objectCount}</td>
        </tr>
        <tr>
            <td>最后一秒查询完成的执行数:</td>
            <td>${cache.statistics.searchesPerSecond}</td>
        </tr>
        <tr>
            <td>最后一次采样的平均执行时间:</td>
            <td>${cache.statistics.averageSearchTime}毫秒</td>
        </tr>
        <tr>
            <td>平均获取时间:</td>
            <td>${cache.statistics.averageGetTime}毫秒</td>
        </tr>
		[/#list]
        </tbody>
    </table>
    <br/><br/>
</div>
</body>
</html>