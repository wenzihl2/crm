[#--
<ul class="nav nav-tabs">
    <li ${empty type ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/hibernate">
            <i class="icon-table"></i>
            Hibernate监控
        </a>
    </li>

    <li ${type eq 'secondLevelCache' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/hibernate/secondLevelCache">
            <i class="icon-table"></i>
            二级缓存统计
        </a>
    </li>

    <li ${type eq 'query' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/hibernate/queryCache">
            <i class="icon-table"></i>
            查询缓存统计
        </a>
    </li>

    <li ${type eq 'entityAndCollectionCRUDCount' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/hibernate/entityAndCollectionCRUDCount">
            <i class="icon-table"></i>
            实体 & 集合 CRUD统计
        </a>
    </li>
    <li ${type eq 'invalidate' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/hibernate/control">
            <i class="icon-table"></i>
            缓存控制
        </a>
    </li>
</ul>
--]