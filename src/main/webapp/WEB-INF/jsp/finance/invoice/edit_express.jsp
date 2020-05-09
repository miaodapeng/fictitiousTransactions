<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="编辑快递单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />

<div class=" logistics-warehousein-addWarehouseIn">
	  <div class="form-list  form-tips8">
        <form method="post" action="" id="">
            <ul>
                <li>
                    <div class="form-tips">
                        <span >*</span>
                        <lable>快递单号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	 <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo" value="${expressList.logisticsNo}"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>快递公司</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select name="logisticsId" id="logisticsId">
                            <c:forEach var="logistics" items="${logisticsList}">
                             	<option  value="${logistics.logisticsId}" <c:if test="${logistics.logisticsId == expressList.logisticsId}">selected</c:if>>${logistics.name}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>发货时间</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<input id="deliveryTimes" class="Wdate f_left input-smaller96 mr5" type="text" name="deliveryTimes" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="<date:date value ="${expressList.deliveryTime}" format="yyyy-MM-dd"/>"  format="yyyy-MM-dd"/>
                        </div>
                    </div>
                </li>
                <!--  
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>运费</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<c:set var="amount" value="0.00"></c:set>
                        	<c:forEach var="expressDetails" items="${expressList.expressDetail}">
                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
                        	</c:forEach>
                            <input type="text" class="input-small" name="amount" id="amount" value="${amount}"/>
                        </div>
                    </div>
                </li>  -->
                 <li>
                    <div class="form-tips">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments" value="${expressList.logisticsComments}"/>
                        </div>
                    </div>
                </li>
            </ul>
          	<div class="add-tijiao tcenter">
              	<input type="hidden" name="beforeParams" value='${beforeParams}'>
          		<input type="hidden" name="expressId" value="${expressList.expressId}"/>
              	<button type="button" class="bg-light-blue" onclick="editExpress('3', '${invoiceId}')">确定</button>
          	</div>
        </form>
    </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/addExpress.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
