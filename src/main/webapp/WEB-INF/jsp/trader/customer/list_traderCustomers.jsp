<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <c:if test="${ empty code }">
    <div class="form-list" style="width:450px;">
        <form method="post" <%-- action="${pageContext.request.contextPath}/trader/customer/eyeCheckInfo.do" --%> style="width: 400px;margin: 0 auto;">
        <c:forEach var="list" items="${TraderCustomerVoList}" varStatus="num">
            <div class="form-blanks mb4">
                <input type="radio" name="traderName" id="traderName" value="${list.traderName}" >
                <label>
                    ${list.traderName}                              
                </label>
            </div>
           </c:forEach>
           <div class="add-tijiao text-left">
               <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="searchTyc();" id="searchSpan">提交</span>
                <button class="dele" id="close-layer" type="button">
                                                         返回
                </button>
            </div>
        </form>
    </div>
    </c:if>
    <c:if test="${ code eq 300000 }">
              暂无数据！
    </c:if>
<script type="text/javascript"
	src='<%= basePath %>static/js/customer/list_traderCustomer.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

