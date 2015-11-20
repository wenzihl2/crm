<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<link rel="stylesheet" href="${base}/resource/css/bootstrap.css" type="text/css" />
</head>

<body>
<div data-table="table" class="panel">
    [#include "/admin/monitor/hibernate/nav.ftl"]

    <table class="table table-bordered">
        <tbody>
        <tr class="bold info">
            <td colspan="2">事务统计</td>
        </tr>
        <tr>
            <td style="width: 30%">执行的事务次数:</td>
            <td>${statistics.transactionCount}</td>
        </tr>
        <tr>
            <td>成功的事务次数:</td>
            <td>${statistics.successfulTransactionCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">连接统计</td>
        </tr>

        <tr>
            <td>数据库连接次数:</td>
            <td>
                ${statistics.connectCount}
                平均${statistics.connectCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>数据库prepareStatement获取次数:</td>
            <td>${statistics.prepareStatementCount}</td>
        </tr>
        <tr>
            <td>数据库prepareStatement关闭次数:</td>
            <td>${statistics.closeStatementCount} （Hibernate4 bug 没有记录）</td>
        </tr>
        <tr class="error">
            <td>optimistic lock失败次数:</td>
            <td>${statistics.optimisticFailureCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">Session统计</td>
        </tr>
        <tr>
            <td>Session打开的次数:</td>
            <td>
                ${statistics.sessionOpenCount}
                平均${statistics.sessionOpenCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>Session关闭的次数:</td>
            <td>
                ${statistics.sessionCloseCount}
                &nbsp;&nbsp;
                <span class="muted">因为OpenSessionInView，所以少一次</span>
            </td>
        </tr>
        <tr>
            <td>Session flush的次数:</td>
            <td>${statistics.flushCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">二级缓存统计（实体&集合缓存，不包括查询）</td>
        </tr>
        [#assign secondLevelCacheCount = statistics.secondLevelCacheHitCount + statistics.secondLevelCacheMissCount /]
        [#if secondLevelCacheCount <= 0]
        	[#assign secondLevelCacheCount = 1 /]
        [/#if]
        [#assign secondLevelCacheHitPercent = statistics.secondLevelCacheHitCount * 1.0 / (secondLevelCacheCount) /]
        <tr>
            <td>总命中率:</td>
            <td>${secondLevelCacheHitPercent}</td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.secondLevelCacheHitCount}
                &nbsp;&nbsp;
                平均:${statistics.secondLevelCacheHitCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.secondLevelCacheMissCount}
                &nbsp;&nbsp;
                平均:${statistics.secondLevelCacheMissCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>被缓存的个数:</td>
            <td>${statistics.secondLevelCachePutCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">内存情况</td>
        </tr>
        <tr>
            <td style="width: 30%">当前系统使用内存:</td>
            <td>
                ${PrettyMemoryUtils.prettyByteSize(usedSystemMemory)}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">系统总内存:</td>
            <td>
            	${PrettyMemoryUtils.prettyByteSize(maxSystemMemory)}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">二级缓存使用内存:</td>
            <td>
            	${PrettyMemoryUtils.prettyByteSize(totalMemorySize)}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">内存中的总实体数:</td>
            <td>
                ${totalMemoryCount}
            </td>
        </tr>
        <tr>
            <td style="width: 30%">磁盘中的总实体数:</td>
            <td>
                ${totalDiskCount}
            </td>
        </tr>


        <tr class="bold info">
            <td colspan="2">实体CRUD统计</td>
        </tr>
        <tr>
            <td>实体delete次数:</td>
            <td>${statistics.entityDeleteCount}</td>
        </tr>
        <tr>
            <td>实体insert次数:</td>
            <td>${statistics.entityInsertCount}</td>
        </tr>
        <tr>
            <td>实体update次数:</td>
            <td>${statistics.entityUpdateCount}</td>
        </tr>
        <tr>
            <td>实体load次数:</td>
            <td>${statistics.entityLoadCount}</td>
        </tr>
        <tr>
            <td>实体fetch次数:</td>
            <td>${statistics.entityFetchCount}</td>
        </tr>

        <tr class="bold info">
            <td colspan="2">集合CRUD统计</td>
        </tr>
        <tr>
            <td>集合remove次数:</td>
            <td>${statistics.collectionRemoveCount}（当inverse="true"）</td>
        </tr>
        <tr>
            <td>集合insert次数:</td>
            <td>${statistics.entityInsertCount}</td>
        </tr>
        <tr>
            <td>集合update次数:</td>
            <td>${statistics.collectionUpdateCount}</td>
        </tr>
        <tr>
            <td>集合load次数:</td>
            <td>${statistics.collectionLoadCount}</td>
        </tr>
        <tr>
            <td>集合fetch次数:</td>
            <td>${statistics.collectionFetchCount}</td>
        </tr>
        <tr>
            <td>集合recreated次数:</td>
            <td>${statistics.collectionRecreateCount}</td>
        </tr>


        <tr class="bold info">
            <td colspan="2">查询缓存统计</td>
        </tr>
        <tr>
            <td>查询总执行次数:</td>
            <td>${statistics.queryExecutionCount}</td>
        </tr>
        <tr>
            <td>最慢查询执行时间:</td>
            <td>${statistics.queryExecutionMaxTime}毫秒</td>
        </tr>
        <tr>
            <td>最慢查询:</td>
            <td>${statistics.queryExecutionMaxTimeQueryString}</td>
        </tr>
        [#assign queryCacheCount = statistics.queryCacheHitCount + statistics.queryCacheMissCount /]
        [#if queryCacheCount <= 0]
        	[#assign queryCacheCount = 1 /]
        [/#if]
        [#assign queryCacheHitPercent = statistics.queryCacheHitCount * 1.0 / (queryCacheCount) /]
        <tr>
            <td>总命中率:</td>
            <td>${queryCacheHitPercent}</td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.queryCacheHitCount}
                &nbsp;&nbsp;
                平均:${statistics.queryCacheHitCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.queryCacheMissCount}
                &nbsp;&nbsp;
                平均:${statistics.queryCacheMissCount/upSeconds}次/秒
            </td>
        </tr>
        <tr>
            <td>被缓存的查询个数:</td>
            <td>
                ${statistics.queryCachePutCount}
            </td>
        </tr>

        <tr class="bold info">
            <td colspan="2">UpdateTimestamp缓存统计</td>
        </tr>
        [#assign updateTimestampsCacheCount = statistics.updateTimestampsCacheHitCount + statistics.updateTimestampsCacheMissCount /]
        [#if updateTimestampsCacheCount <= 0]
        	[#assign updateTimestampsCacheCount = 1 /]
        [/#if]
        [#assign updateTimestampsCacheHitPercent = statistics.updateTimestampsCacheHitCount * 1.0 / (queryCacheCount) /]
        <tr>
            <td>总命中率:</td>
            <td>${updateTimestampsCacheHitPercent}</td>
        </tr>
        <tr>
            <td>命中次数:</td>
            <td>
                ${statistics.updateTimestampsCacheHitCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheHitCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>
        <tr>
            <td>失效次数:</td>
            <td>
                ${statistics.updateTimestampsCacheMissCount}
                &nbsp;&nbsp;
                平均:<fmt:formatNumber value="${statistics.updateTimestampsCacheMissCount/upSeconds}" maxFractionDigits="2"/>次/秒
            </td>
        </tr>

        <tr class="bold info">
            <td colspan="2">jpa配置</td>
        </tr>
        [#list properties?keys as entity]
        	[#if entity?index_of("hibernate") == 0 || entity?index_of("javax") == 0
        		|| entity?index_of("net") == 0]
            <tr>
                <td>${entity}:</td>
                <td>
                    ${properties.get(entity)}
                </td>
            </tr>
            [/#if]
        [/#list]

        <tr class="bold info">
            <td colspan="2">加载的实体</td>
        </tr>
        [#list sessionFactory.allClassMetadata?keys as entity]
        <tr>
            <td>${entity}:</td>
            <td>
            	
                实体名:
                [#if sessionFactory.allClassMetadata.get(entity)??]
                ${sessionFactory.allClassMetadata.get(entity).entityName}<br/>
                [/#if]
                标识符名：
                [#if sessionFactory.allClassMetadata.get(entity)??]
                ${sessionFactory.allClassMetadata.get(entity).identifierPropertyName}
                [/#if]
            </td>
        </tr>
        [/#list]

        </tbody>
    </table>
    <br/><br/>
</div>
</body>
</html>