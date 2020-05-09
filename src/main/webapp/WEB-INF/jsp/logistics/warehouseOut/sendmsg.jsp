<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="短信发送" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="logistics-warehousein-addWarehouseIn">
	  <div class="form-list  form-tips8">
        <form action="" method="post" id="sendmsgs">
            <ul>
                <li>
                    <div class="form-tips">
                       <input name="radiovaule" type="radio" id="takeTraderContactMobile" checked>收货联系人</input>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	 <input type="text" class="input-small" name="takeTraderContactMobile" disabled="disabled" id="takeTraderPhone"
                        	 <c:if test="${!empty saleorder.takeTraderContactMobile !=''}">
                           value= ${saleorder.takeTraderContactMobile}
                            </c:if>
                             <c:if test="${empty saleorder.takeTraderContactMobile ==''}">
                             value=${saleorder.takeTraderContactTelephone}
                            </c:if>
                            />
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <input name="radiovaule" type="radio"  id="traderContactMobile">合同联系人</input>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-small"  name="traderContactMobile" disabled="disabled" id="traderPhone"
                            <c:if test="${!empty saleorder.traderContactMobile}">
                            value= ${saleorder.traderContactMobile}
                            </c:if>
                             <c:if test="${empty saleorder.traderContactMobile ==''}">
                             value=${saleorder.traderContactTelephone}
                            </c:if> >
                        </div>
                    </div>
                </li>
                <li>
                <div class="form-tips">
                   <input name="radiovaule" type="radio" value="0" id="otherMobile">其他联系人</input>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                       <div class="form-blanks">
                            <input type="text" class="input-small" name="otherMobile" id="otherPhone"/>
                        </div>
                    </div>
                </div>
                </li>
                </ul>
           <div class="add-tijiao tcenter">
            <input id="saleorderNo" type="hidden" value="${saleorder.saleorderNo }" name="saleorderNo"/>
            <input id="logisticsName" type="hidden" value="${saleorderPam.logisticsName }" name="logisticsName"/>
            <input id="logisticsNo" type="hidden" value="${saleorderPam.logisticsNo }" name="logisticsNo"/>
			<button type="button" class="bt-bg-style bg-deep-green" onclick="sendmsgSubForm();">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		   </div>
		 </form> 
	</div>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
