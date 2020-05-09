<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加联系人" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/add_contact.js?rnd=<%=Math.random()%>'></script>
	<div class="addElement">
        <div class="add-main">
            <form action="${pageContext.request.contextPath}/trader/customer/addSaveContact.do" method="post" id="confirmForm">
                <ul class="add-detail">
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>姓名</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle " name="name" id="name" />
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>手机</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" name="mobile" id="mobile" />
                        </div>
                    </li>
                    <!-- li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>邮箱</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" name="email" id="email" />
                        </div>
                    </li-->
                </ul>
                <div class="add-tijiao tcenter">
                	<input type="hidden" name="traderId" value="${traderId}" id="traderId">
		            <input type="hidden" name="traderCustomerId" value="${traderCustomerId}">
		        	<input type="hidden" name="traderType" value="1">
		        	<input type="hidden" name="indexId" value="${indexId}" id="indexId">
		        	<input type="hidden" name="formToken" value="${formToken}"/>
                    <button type="submit" id="add_submit">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>