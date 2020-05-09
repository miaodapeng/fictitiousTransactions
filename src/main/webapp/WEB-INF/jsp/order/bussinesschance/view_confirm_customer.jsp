
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认客户" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/confirm_customer.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic popups">
    
	<form action="" method="post" id="myform2">
		
		 <ul>
                <li>
                    <div class="infor_name mt4">
                        <span>*</span>
                        <lable id='test1'>客户名称</lable>
                        <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}">
                        <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
                        <input type="hidden" name="traderId" value="${traderCustomer.traderId}">
                        <input type="hidden" name="checkTraderName" value="${traderCustomer.name}">
                        <input type="hidden" name="checkTraderArea" value="${traderCustomer.address}">
                    </div>
                    <div class="f_left">
                        <label class="mt4">${traderCustomer.name}</label>
                        <a href="${pageContext.request.contextPath}/order/bussinesschance/confirmCustomer.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderId=${traderCustomer.traderId}" 
                        	class="setLayerWidth" setwidth='{"width":"60%"}' >
                        	<span class="bt-bg-style bg-light-blue bt-small">重新搜索</span>
                        </a>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left">
                    	<div class="f_left inputfloat">
                        <select name="traderContactId" id="traderContactId">
                        	<option value="">请选择</option>
                            <c:if test="${not empty contactList }">
	                    		<c:forEach items="${contactList }" var="ca">
	                    			<option value="${ca.traderContactId }" >${ca.name}<c:if test="${ca.telephone ne null}">/${ca.telephone}</c:if>/${ca.mobile}</option>
	                    		</c:forEach>
		                    </c:if>
                        </select>
                        <label class="mt4">
                            <a class="setLayerWidth" 
                            	href="${pageContext.request.contextPath}/order/bussinesschance/addConfirmCustomer.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderId=${traderCustomer.traderId}" 
                            	setwidth='{"width":"900","left":"20"}'>添加联系人</a>
                        </label>
                        </div>
                        <div id="traderContactId1" class="font-red " style="display: none">联系人不允许为空</div>
                    </div>
                    
                </li>
            </ul>
            <div class="add-tijiao tcenter">
                <button type="submit" id="sub">提交</button>
                <button type="button"  class="dele" id="close-layer">取消</button>
            </div>
	</form>
	</div>
</body>

</html>
