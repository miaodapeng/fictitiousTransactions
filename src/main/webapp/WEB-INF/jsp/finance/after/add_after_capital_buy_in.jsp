<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增交易方式" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/add_after_capital_sale_sk.js?rnd=<%=Math.random()%>'></script>
<div class="form-list">
    <form method="post" id="addAfteCapitalSaleSkForm">
    	<input type="hidden" name="formToken" value="${formToken}"/>
    	<!-- 售后订单收付款类型 1：收款	 2/null：付款 -->
    	<input type="hidden" name="afterSalesPaymentType" id="afterSalesPaymentType" value="1">
        <ul>
        	<input type="hidden" name="capitalBillDetail.bussinessType" value="531">
        	<%-- <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>业务类型</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
	                    	<li>
								<!-- 业务类型:采购订单退货 -->
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
            </li> --%>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <c:forEach var="list" items="${traderModeList}">
	                            <c:if test="${list.sysOptionDefinitionId == 521 or list.sysOptionDefinitionId == 520 or list.sysOptionDefinitionId == 523}">
			                    	<li>
		                                <input type="radio" name="fkfs" id="traderModeRad" value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId == 521}">checked="checked"</c:if>>
		                                <label>${list.title}</label>
		                            </li>
			                    </c:if>
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
                            <li>
                                <input type="hidden" name="traderSubject" value="1">
                                <label>对公</label>
                            </li>
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
                    	<c:set var="receiptAmount" value="${(afterSalesDetailVo.realRefundAmount - afterSalesDetailVo.capitalTotalAmount)<0?-(afterSalesDetailVo.realRefundAmount - afterSalesDetailVo.capitalTotalAmount):(afterSalesDetailVo.realRefundAmount - afterSalesDetailVo.capitalTotalAmount)}"></c:set>
                        <input type="text" class="input-largest" name="amount" id="amount" onkeyup="value=value.replace(/[^\d.]/g,'');"
                        	value="<fmt:formatNumber type='number' value='${receiptAmount}' pattern='0.00' maxFractionDigits='2' />" />
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
                        <input type="text" class="input-largest" name="payer" id="payer" value="" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>开户银行</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="payerBankName" id="payerBankName" value="" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>银行账号</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="payerBankAccount" id="payerBankAccount" value="" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>交易备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="comments" id="comments" value="" />
                    </div>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="traderType" value="1"/> <!-- 收入 -->
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