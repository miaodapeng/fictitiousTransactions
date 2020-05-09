<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="支付宝提现资金流水" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list">
    <form method="post" id="addAlipayCapitalBillForm" action="">
        <ul>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <c:forEach var="list" items="${traderModeList}">
                            <c:if test="${list.sysOptionDefinitionId == 520 || list.sysOptionDefinitionId == 522}">
		                    	<li>
	                                <input type="radio" name="traderMode" value="${list.sysOptionDefinitionId}" 
	                                <c:if test="${(saleorder.payType == 1 && list.sysOptionDefinitionId == 520)||(saleorder.payType == 2 && list.sysOptionDefinitionId == 522)||(saleorder.payType != 1 && saleorder.payType != 2 && list.sysOptionDefinitionId == 520)}">
		                                checked="checked"
	                                </c:if>
	                                >
	                                <label>${list.title}</label>
	                            </li>
		                    </c:if>
		                    </c:forEach>
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
                            <!-- <li>
                                <input type="radio" name="traderSubject" value="1" checked="checked">
                                <label>对公</label>
                            </li> -->
                            <li>
                                <input type="radio" name="traderSubject" value="2" checked="checked">
                                <label>对私</label>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
			<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>业务类型</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                        	<c:forEach var="list" items="${bussinessTypeList}">
                        	<c:if test="${list.sysOptionDefinitionId == 679}">
		                    	<li>
	                                <input type="hidden" name="capitalBillDetail.bussinessType" value="${list.sysOptionDefinitionId}">
	                                <label>${list.title}</label>
	                            </li>
	                        </c:if>
		                    </c:forEach>
                        </ul>
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
                	<span>*</span>
                    <lable>交易金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="amount" id="amount" value="" />
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
        	<input type="hidden" name="capitalBillDetail.relatedId" value="${saleorder.saleorderId}"/>
        	<input type="hidden" name="capitalBillDetail.orderNo" value="${saleorder.saleorderNo}"/>
        	<input type="hidden" name="capitalBillDetail.orderType" value="1"/><!-- 销售订单 -->
        	<input type="hidden" name="capitalBillDetail.traderId" value="${saleorder.traderId}"/>
        	<input type="hidden" name="capitalBillDetail.traderType" value="1"/><!-- 客户 -->
        	<input type="hidden" name="capitalBillDetail.userId" value="${saleorder.userId}"/>
        	<input type="hidden" name="formToken" value="${formToken}"/>
            <button type="submit">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/finance/capitalbill/add_alipay_capital_bill.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>