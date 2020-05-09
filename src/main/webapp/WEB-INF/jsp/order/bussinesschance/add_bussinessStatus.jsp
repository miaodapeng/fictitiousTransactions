<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增商机情况" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_bussinessStatus.js?rnd=<%=Math.random()%>"></script>
<style>
.formpublic{
padding-top: 20px;
}
	.inputfloat li{
		margin:3px 10px 3px 0;
	}
	.inputfloat li input{
		margin: 2px 9px 0 0;
	}
</style>
    <div class="formpublic">
        <form method="post" action="" id="myform">
        	
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>商机等级</lable>
                    </div>
                    <div class="f_left">
                        <div>
                        <select class="input-middle" name="bussinessLevel" id="bussinessLevel">
								<option value="">请选择</option>
								<c:forEach items="${bussinessLevelList }" var="bussinessLevel">
									<option value="${bussinessLevel.sysOptionDefinitionId }"
										<c:if test="${bussinessLevel.sysOptionDefinitionId == bussinessChance.bussinessLevel}">selected="selected"</c:if>>${bussinessLevel.title }&nbsp;${bussinessLevel.comments }</option>
								</c:forEach>
						</select>
						</div>
						<div id="bussinessLevelError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>商机阶段</lable>
                    </div>
                    <div class="f_left">
                        <div>
                        <select class="input-middle" name="bussinessStage" id="bussinessStage">
								<option value="">请选择</option>
								<c:forEach items="${bussinessStageList }" var="bussinessStage">
									<option value="${bussinessStage.sysOptionDefinitionId }"
										<c:if test="${bussinessStage.sysOptionDefinitionId == bussinessChance.bussinessStage}">selected="selected"</c:if>>${bussinessStage.title }</option>
								</c:forEach>
						</select>
						</div>
						<div id="bussinessStageError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>询价类型</lable>
                    </div>
                    <div class="f_left inputfloat table-largest">
                    	<div>
                        <ul>
                            <li> 
                               <input type="radio" name="enquiryType" id="enquiryType" value="949" <c:if test="${(bussinessChance.enquiryType eq 949) or (empty bussinessChance.enquiryType ) or (bussinessChance.enquiryType == 0 )}">checked="checked"</c:if>/><label class="mr8">单一询价</label>
                               <input type="radio" name="enquiryType" id="enquiryType" value="950" <c:if test="${(bussinessChance.enquiryType eq 950)}">checked="checked"</c:if>/><label class="mr8">综合询价</label>
                            </li>
                        </ul>
                        </div>
                        <div id="enquiryTypeError" ></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <c:if test="${type eq 1 }">
                           <span>*</span>
                        </c:if>
                        <lable>成单几率</lable>
                    </div>
                    <div class="f_left">
                        <div>
                        <select class="input-middle" name="orderRate" id="orderRate">
								<option value="">请选择</option>
								<c:forEach items="${orderRateList }" var="orderRate">
									<option value="${orderRate.sysOptionDefinitionId }"
										<c:if test="${orderRate.sysOptionDefinitionId == bussinessChance.orderRate}">selected="selected"</c:if>>${orderRate.title }</option>
								</c:forEach>
						</select>
						</div>
						<div id="orderRateError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <c:if test="${type eq 1 }">
                           <span>*</span>
                        </c:if>
                        <lable>预计金额</lable>
                    </div>
                    <div class="f_left">
                        <c:if test="${bussinessChance.amount.unscaledValue() !=0}">
                        <input type="text" class="input-middle" name="amount" id="amount" value="${bussinessChance.amount }"/>
                    </c:if>
                        <c:if test="${bussinessChance.amount.unscaledValue() ==0 }">
                            <input type="text" class="input-middle" name="amount" id="amount" value="" placeholder="请输入金额"/>
                        </c:if>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <c:if test="${type eq 1 }">
                           <span>*</span>
                        </c:if>
                        <lable>预计成单时间</lable>
                    </div>
                    <div class="f_left">
                       <input class="Wdate input-small " type="hidden" placeholder="当前时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="nowDate" id="nowDate" value="<date:date value ='${nowDate} ' format="yyyy-MM-dd"/>"  />
                        <input class="Wdate input-small " type="text" placeholder="请选择时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'nowDate\')}'})" name="orderTimeStr" id="orderTimeStr" value="<date:date value ='${bussinessChance.orderTime} ' format="yyyy-MM-dd"/>" />
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
                <button type="submit" class="dele">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
            <input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
            <input type="hidden" name="type" id="type" value="${type}"/>
            <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
            <input type="hidden" name="traderId" value="${bussinessChance.traderId }" >
        </form>
    </div>
</body>

</html>
