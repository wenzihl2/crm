<%@ page import="org.apache.commons.lang3.ArrayUtils" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.metadata.ClassMetadata" %>
<%@ page import="org.springframework.beans.BeanWrapperImpl" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ page import="java.beans.PropertyDescriptor" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<!doctype html>
<html>
<head>
[#include "/admin/include/head.htm"]
<link rel="stylesheet" href="${base}/resource/css/bootstrap.css" type="text/css" />
<script type="text/javascript">
    $(".btn-pre-page").click(function() {
        var $pn = $("[name='page.pn']");
        $pn.val(parseInt($pn.val()) - 1);
        $pn.closest("form").submit();
    });
    $(".btn-next-page").click(function() {
        var $pn = $("[name='page.pn']");
        if(!$pn.val()) {
            $pn.val(1);
        } else {
            $pn.val(parseInt($pn.val()) + 1);
        }
        $pn.closest("form").submit();
    });
</script>
</head>
<div data-table="table" class="panel">
    <c:set var="type" value="ql"/>
    [#include "/admin/monitor/db/nav.ftl"]

    <form method="post" class="form-inline">
    	<input type="hidden" value="page.pn">
        <label>请输入QL：</label><br/>
        <textarea name="ql" style="width: 500px;height: 160px"></textarea>
        <input type="submit" class="btn" value="执行">
    </form>

    <div id="result">
        [#if error??]
            出错了：<br/>
            ${error}<br/>
        [/#if]
        [#if updateCount??]更新了${updateCount}行[/#if]
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
            <%
                SessionFactory sessionFactory = (SessionFactory)pageContext.findAttribute("sessionFactory");
                List result = resultPage.getContent();
                Object obj = result.get(0);
                ClassMetadata metadata = sessionFactory.getClassMetadata(obj.getClass());
                String[] propertyNames = Arrays.copyOf(metadata.getPropertyNames(), metadata.getPropertyNames().length);
                ArrayUtils.reverse(propertyNames);
                if(metadata != null) {
                    BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);
            %>
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <%
                                out.write("<th>" + metadata.getIdentifierPropertyName() + "</th>");
                                for(String propertyName : propertyNames) {
                                    out.write("<th>" + propertyName + "</th>");
                                }
                            %>
                        </tr>
                    </thead>
                    <tbody>

                            <%
                                for(Object o : result) {
                                    out.write("<tr>");

                                    PropertyDescriptor identifierPropertyDescriptor =
                                            beanWrapper.getPropertyDescriptor(metadata.getIdentifierPropertyName());

                                    out.write("<td>" + identifierPropertyDescriptor.getReadMethod().invoke(o) + "</td>");

                                    for(String propertyName : propertyNames) {
                                        PropertyDescriptor propertyDescriptor =
                                                beanWrapper.getPropertyDescriptor(propertyName);
                                        out.write("<td>" + propertyDescriptor.getReadMethod().invoke(o) + "</td>");
                                    }
                                    out.write("</tr>");
                                }
                            %>
                    </tbody>
                </table>
            <%
            } else {
                out.write("未知实体类型，直接循环输出<br/>");
                for(Object r : result) {
                    String str = r.toString();
                    if(r.getClass().isArray()) {
                        str = Arrays.toString((Object[])r);
                    }
                    out.write(str + "<br/>");
                }
            }
            %>
        </c:if>
    </div>

</div>
</body>
</html>