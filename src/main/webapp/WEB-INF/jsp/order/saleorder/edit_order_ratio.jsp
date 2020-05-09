<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="编辑订单税率" scope="application"/>
<%@ include file="../../common/common.jsp" %>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/edit_order_ratio.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic">
    <form method="post" action="<%=basePath%>order/saleorder/saveOrderRatioEdit.do" id="editOrderRatioEdit">
        <input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
        <ul>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>发票税率</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <select name="invoiceType" id="invoiceType">
                            <c:forEach var="list" items="${invoiceTypeList}">
                                <c:if test="${fn:contains('681,682,971,972', list.sysOptionDefinitionId)}">
                                    <option value="${list.sysOptionDefinitionId}"
                                            <c:if test="${list.sysOptionDefinitionId == invoiceType}">selected</c:if>>${list.title}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </li>
        </ul>
        <br>
        <div class="add-tijiao tcenter">
            <input type="hidden" name="orderId" value='${orderId}'>
            <button type="button" class="bg-light-blue" onclick="saveRatioEdit()">确定</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp" %>