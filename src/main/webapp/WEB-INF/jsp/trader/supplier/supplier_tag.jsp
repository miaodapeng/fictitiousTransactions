<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<div class="customer">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/trader/supplier/baseinfo.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}" 
            <c:if test="${method == 'baseinfo' }">class="customer-color"</c:if> >供应商审核信息</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/trader/supplier/manageinfo.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"
            <c:if test="${method == 'manageinfo' }">class="customer-color"</c:if> >管理信息</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/trader/supplier/getFinanceAndAptitude.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"
            <c:if test="${method == 'financeandaptitude' }">class="customer-color"</c:if> >财务信息</a>
        </li>
        <%--<li>--%>
            <%--<a href="${pageContext.request.contextPath}/trader/supplier/getContactsAddress.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"--%>
            <%--<c:if test="${method == 'contactsaddress' }">class="customer-color"</c:if> >联系人与地址</a>--%>
        <%--</li>--%>
        <li>
            <a href="${pageContext.request.contextPath}/trader/supplier/businesslist.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"
            <c:if test="${method == 'buyorder' }">class="customer-color"</c:if> >交易记录</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/trader/supplier/communicaterecord.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}"
            <c:if test="${method == 'communicaterecord' }">class="customer-color"</c:if> >沟通记录</a>
        </li>
    </ul>
</div>