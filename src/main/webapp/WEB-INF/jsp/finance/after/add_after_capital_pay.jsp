<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增交易方式" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/add_after_capital_pay.js?rnd=<%=Math.random()%>'></script>
<div class="form-list">
    <form method="post" id="addAfteCapitalPayForm" ><!-- action="finance/capitalbill/saveAddCapitalBill.do" -->
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
	                    		<c:choose>
	                    			<c:when test="${(afterSalesDetailVo.afterType eq 539) or (afterSalesDetailVo.afterType eq 551)}"><!-- 539销售订单退货   551第三方退款-->
	                    				<input type="radio" name="capitalBillDetail.bussinessType" value="531" checked="checked">
		                                <c:forEach var="list" items="${bussinessTypeList}">
		                                	<c:if test="${list.sysOptionDefinitionId == 531}"><!-- 退款 -->
				                                <label>${list.title}</label>
		                                	</c:if>
		                                </c:forEach>
	                    			</c:when>
	                    			<c:otherwise>
		                                <input type="radio" name="capitalBillDetail.bussinessType" value="525" checked="checked">
		                                <c:forEach var="list" items="${bussinessTypeList}">
		                                	<c:if test="${list.sysOptionDefinitionId == 525}"><!-- 订单付款 -->
				                                <label>${list.title}</label>
		                                	</c:if>
		                                </c:forEach>
	                    			</c:otherwise>
	                    		</c:choose>
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
                            <c:forEach var="list" items="${traderModeList}">
                            	<c:choose>
                            		<c:when test="${(afterSalesDetailVo.afterType eq 550) or (afterSalesDetailVo.afterType eq 585)}"><!-- 550：第三方安调；585维修 -->
                            			<c:if test="${list.sysOptionDefinitionId eq 521}">
					                    	<li>
				                                <input type="radio" name="fkfs" id="traderModeRad" value="${list.sysOptionDefinitionId}" checked="checked">
				                                <label>${list.title}</label>
				                            </li>
					                    </c:if>
                            		</c:when>
                            		<c:when test="${payApply.traderMode eq 528}"><!--余额支付 -->
                            			<c:if test="${list.sysOptionDefinitionId eq 528}">
					                    	<li>
				                                <input type="radio" name="fkfs" id="traderModeRad" value="${list.sysOptionDefinitionId}" checked="checked">
				                                <label>${list.title}</label>
				                            </li>
					                    </c:if>
                            		</c:when>
                            		<c:otherwise>
			                            <c:if test="${(list.sysOptionDefinitionId eq 520) or (list.sysOptionDefinitionId eq 521) or (list.sysOptionDefinitionId eq 523)}">
			                            	<c:choose>
			                            		<c:when test="${(empty payApply.traderMode) or (payApply.traderMode eq 0)}">
							                    	<li>
							                    		<!-- 默认521银行 -->
						                                <input type="radio" name="fkfs" id="traderModeRad" value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId == 521}">checked="checked"</c:if>>
						                                <label>${list.title}</label>
						                            </li>
			                            		</c:when>
			                            		<c:otherwise>
			                            			<li>
						                                <input type="radio" name="fkfs" id="traderModeRad" value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId == payApply.traderMode}">checked="checked"</c:if>>
						                                <label>${list.title}</label>
						                            </li>
			                            		</c:otherwise>
			                            	</c:choose>
					                    </c:if>
                            		</c:otherwise>
                            	</c:choose>
		                    </c:forEach>
		                    <input type="hidden" name="traderMode" id="traderMode"/>
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
							<input type="hidden" name="traderSubject" value="${payApply.traderSubject}">
                        	<c:if test="${payApply.traderSubject eq 1}">
                        		对公
                        	</c:if>
                            <c:if test="${payApply.traderSubject eq 2}">
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
                    	<fmt:formatNumber type="number" value="${payApply.amount}" pattern="0.00" maxFractionDigits="2" />
                        <input type="hidden" name="amount" id="amount" value="${payApply.amount}" />
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
                    	${payApply.traderName}
                        <input type="hidden" name="payee" id="payee" value="${payApply.traderName}" />
                    </div>
                </div>
            </li>
               <!-- 如果付款方式是银行时 -->
            <c:if test="${payApply.getTraderMode() eq 521}">
              <li>
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
              </c:if>
            <!-- 开户银行 -->
            <input type="hidden" name="payeeBankName" id="payeeBankName" value="${payApply.bank}" />
            <!-- 银行账号 -->
            <input type="hidden" name="payeeBankAccount" id="payeeBankAccount" value="${payApply.bankAccount}"/>
            <!-- 开户行支付联行号 -->
            <input type="hidden" name="bankCode" id="bankCode" value="${payApply.bankCode}"/>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="payApplyId" value="${payApply.payApplyId}"/><!-- 付款申请表ID -->
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