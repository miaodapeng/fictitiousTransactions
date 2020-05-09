<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<div class="customer">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/trader/customer/baseinfo.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}" 
            <c:if test="${method == 'baseinfo' }">class="customer-color"</c:if> >基本信息</a>
        </li>
        <li >
            <a href="${pageContext.request.contextPath}/trader/customer/manageinfo.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"
            <c:if test="${method == 'manageinfo' }">class="customer-color"</c:if> >管理信息</a>
        </li>
        <li>
            <a id="finace" href="${pageContext.request.contextPath}/trader/customer/getFinanceAndAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}&aptitudeStatus=${traderCustomerVo.aptitudeStatus}"
            <c:if test="${method == 'financeandaptitude' }">class="customer-color"</c:if> >财务与资质信息</a>
        </li>
        <li>
            <a id="contact" href="${pageContext.request.contextPath}/trader/customer/getContactsAddress.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}"
            <c:if test="${method == 'contactsaddress' }">class="customer-color"</c:if> >联系人与地址</a>
        </li>
        <li >
            <a href="${pageContext.request.contextPath}/order/bussinesschance/saleindex.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}"
            <c:if test="${method == 'bussinesschance' }">class="customer-color"</c:if> >商机/报价/订单</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/trader/customer/businesslist.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}"
            <c:if test="${method == 'saleorder' }">class="customer-color"</c:if>>交易记录</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/trader/customer/communicaterecord.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderCustomerId=${traderCustomer.traderCustomerId}&customerNature=${traderCustomer.customerNature}"
            <c:if test="${method == 'communicaterecord' }">class="customer-color"</c:if> >沟通记录</a>
        </li>
        <!-- <li >
            <a  href="customeranalysis.html">客户分析</a>
        </li> -->
    </ul>
</div>