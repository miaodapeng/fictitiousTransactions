<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增交易方式" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/add_after_capital_other_out.js?rnd=<%=Math.random()%>'></script>
<div class="form-list">
    <form method="post" id="addAfteCapitalPayForm" action="<%=basePath%>finance/capitalbill/saveAddCapitalBill.do">
        <ul>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>业务类型</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
	                    	<li>
                   				<input type="radio" name="capitalBillDetail.bussinessType" value="531" checked="checked">
                                <c:forEach var="list" items="${bussinessTypeList}">
                                	<c:if test="${list.sysOptionDefinitionId == 531}"><!-- 退款 -->
		                                <label>${list.title}</label>
                                	</c:if>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
	                    	<li>
	                    		<input type="radio" name="traderMode" id="traderMode" value="521" checked="checked">
                                <c:forEach var="list" items="${traderModeList}">
                                	<c:if test="${list.sysOptionDefinitionId == 521}"><!-- 银行 -->
		                                <label>${list.title}</label>
                                	</c:if>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易主体</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
							<input type="hidden" name="traderSubject" value="${afterSalesDetailVo.traderSubject}">
                        	<c:if test="${afterSalesDetailVo.traderSubject eq 1}">
                        		对公
                        	</c:if>
                            <c:if test="${afterSalesDetailVo.traderSubject eq 2}">
                        		对私
                        	</c:if>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	<fmt:formatNumber type="number" value="${afterSalesDetailVo.realRefundAmount}" pattern="0.00" maxFractionDigits="2" />
                        <input type="hidden" name="amount" id="amount" value="${afterSalesDetailVo.realRefundAmount}" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易名称</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	${afterSalesDetailVo.payee}
                        <input type="hidden" name="payer" id="payer" value="${afterSalesDetailVo.payee}" />
                    </div>
                </div>
            </li>
             <!-- 如果付款方式是银行时 -->
              <li id="payTypeName" hidden>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>银企直联</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul>
                            <c:forEach var="stn" items="${payTypeName}">
                            <li>
                                 <input type="radio" name="paymentType" value="${stn.sysOptionDefinitionId}" ${stn.sysOptionDefinitionId == 642?"checked":""}>
                                 <label>${stn.title}</label>
                            </li>
							</c:forEach>
                            </ul>
                        </div>
                    </div>
                </li>
            <!-- 开户银行 -->
            <input type="hidden" name="payerBankName" id="payerBankName" value="${afterSalesDetailVo.bank}" />
            <!-- 银行账号 -->
            <input type="hidden" name="payerBankAccount" id="payerBankAccount" value="${afterSalesDetailVo.bankAccount}"/>
            <!-- 开户行支付联行号 -->
            <input type="hidden" name="bankCode" id="bankCode" value="${afterSalesDetailVo.bankCode}"/>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="traderType" value="2"/> <!-- 支出 -->
        	<input type="hidden" name="formToken" value="${formToken}"/>
        	<input type="hidden" name="payApplyId" value="${payApplyId}"/><!-- 付款申请ID -->
        	<input type="hidden" name="taskId" value="${taskId}"/><!-- 流程节点ID -->
        	<input type="hidden" name="capitalBillDetail.relatedId" value="${afterSalesDetailVo.afterSalesId}"/><!-- 售后ID -->
        	<input type="hidden" name="capitalBillDetail.orderNo" value="${afterSalesDetailVo.afterSalesNo}"/><!-- 售后单号 -->
        	<input type="hidden" name="capitalBillDetail.orderType" value="3"/><!-- 订单类型 1销售订单 2采购订单 3售后订单 -->
        	<input type="hidden" name="capitalBillDetail.traderId" value="${afterSalesDetailVo.traderId}"/><!-- 交易者ID -->
        	<c:choose>
        		<c:when test="${afterSalesDetailVo.subjectType eq 535}"><!-- 销售 -->
        			<input type="hidden" name="capitalBillDetail.traderType" value="1"/><!--  1::经销商（包含终端）2:供应商 -->
        		</c:when>
        		<c:when test="${afterSalesDetailVo.subjectType eq 536}"><!-- 采购 -->
        			<input type="hidden" name="capitalBillDetail.traderType" value="2"/><!--  1::经销商（包含终端）2:供应商 -->
        		</c:when>
        	</c:choose>
            <button type="submit">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp"%>