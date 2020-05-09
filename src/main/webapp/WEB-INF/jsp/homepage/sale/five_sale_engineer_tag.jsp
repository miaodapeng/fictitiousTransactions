<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<div class="customer">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/sales/fiveSales/detailsPage.do?sortType=1&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}"
            	<c:if test="${empty sortType || sortType == 1 }">class="customer-color"</c:if> >五行剑法-业绩
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/sales/fiveSales/detailsPage.do?sortType=7&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}" 
            	<c:if test="${sortType == 7 }">class="customer-color"</c:if> >五行剑法-核心商品
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/sales/fiveSales/detailsPage.do?sortType=3&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}" 
            	<c:if test="${sortType == 3 }">class="customer-color"</c:if> >五行剑法-客户
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/sales/fiveSales/detailsPage.do?sortType=4&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}" 
            	<c:if test="${sortType == 4 }">class="customer-color"</c:if> >五行剑法-通话时长
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/sales/fiveSales/detailsPage.do?sortType=8&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}"
            	<c:if test="${sortType == 8 }">class="customer-color"</c:if> >五行剑法-BD新客数
            </a>
        </li>
    </ul>
</div>