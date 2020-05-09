<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="SPD退货明细" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
    <br>
    <div  class="normal-list-page">

        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
            <tr>
                <th class="sorts">序号</th>
                <th class="wid10">订单号</th>
                <th class="wid10">订货号</th>
                <th class="wid10">产品名称</th>
                <th class="wid10">退货数量</th>
            </tr>
            </thead>
            <tbody class="company">
            <c:if test="${not empty details}">
                <c:forEach items="${details}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${item.saleorderNo}</td>
                        <td>${item.skuNo}</td>
                        <td>${item.skuName}</td>
                        <td>${item.afterSaleCount}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <c:if test="${empty details}">
            <div class="noresult">查询无结果！</div>
        </c:if>
    </div>
</div>

<%@ include file="../../common/footer.jsp"%>