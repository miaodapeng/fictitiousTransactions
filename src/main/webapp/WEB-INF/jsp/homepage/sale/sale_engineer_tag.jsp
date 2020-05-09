<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<div class="customer">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=1" 
            <c:if test="${empty accessType || accessType == 1 }">class="customer-color"</c:if> >客户沟通</a>
        </li>
        <li >
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=2"
            <c:if test="${accessType == 2 }">class="customer-color"</c:if> >商机跟进</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=3"
            <c:if test="${accessType == 3 }">class="customer-color"</c:if> >报价跟进</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=4"
            <c:if test="${accessType == 4 }">class="customer-color"</c:if> >本月数据</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=5"
            <c:if test="${accessType == 5}">class="customer-color"</c:if> >个人数据</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/home/page/index.do?accessType=6"
            <c:if test="${accessType == 6}">class="customer-color"</c:if> >合同上传数据</a>
        </li>
    </ul>
</div>