[#--
<ul class="nav nav-tabs">
    <li ${type eq 'sql' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/db/sql">
            <i class="icon-table"></i>
            执行SQL
        </a>
    </li>
    <li ${type eq 'ql' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/monitor/db/ql">
            <i class="icon-table"></i>
            执行JPA QL
        </a>
    </li>
</ul>
--]