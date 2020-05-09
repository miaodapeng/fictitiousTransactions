<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="集中开票客户维护" scope="application"/>
<%@ include file="../../common/common.jsp" %>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/collect_invoice_traderName.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
    <div class="list-pages-search">
        <form method="post" id="search" action="<%=basePath%>finance/invoice/getCollectInvoiceTraderName.do">
            <ul>
                <li>
                    <label class="infor_name">客户公司</label>
                    <input type="text" class="input-large" name="traderName" id="traderName" value="${traderName}"/>
                    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
                    <span class="bt-small bg-light-blue bt-bg-style mr20" onclick="resetCollectPage();">重置</span>
                </li>
            </ul>
            <div style="text-align: left;">
                <%--<span class="bt-small bg-light-blue bt-bg-style" onclick="exportInvoiceApplyList()">新增</span>--%>
                <span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerparams='{"width":"500px","height":"200px","title":"新增多个客户","link":"./batchCollectTraderInit.do"}'>新增多个</span>
                <span class="bt-small bg-light-blue bt-bg-style" onclick="delCollectInvoiceTrader()">删除</span>
            </div>
        </form>
    </div>
    <div class="list-page">
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th class="wid4">选择</th>
                    <th class="wid4">序号</th>
                    <th class="wid22">客户名称</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="list" items="${traderCustomerList}" varStatus="num">
                    <tr>
                        <td><input type="checkbox" name="traderCustomerId" id="traderCustomerId" value="${list.traderCustomerId}" alt="${list.traderName}"/></td>
                        <td>${num.count}</td>
                        <td>${list.traderName}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty traderCustomerList}">
                    <tr>
                        <td colspan="3">
                            查询无结果！
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../../common/footer.jsp" %>
