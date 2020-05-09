<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="申请付款" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/apply_payment.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/apply_pay_buyorder.js?rnd=<%=Math.random()%>'></script>
<div class="form-list form-tips8">
    <form method="post"  action="<%= basePath %>/aftersales/order/saveApplyPay.do" >
        <ul>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>使用余额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                        	<li>
                                <input type="radio" name="isUseBalance" value="1" <c:if test="${supplierInfo.amount ne '0.00'}">checked="checked"</c:if> <c:if test="${supplierInfo.amount eq '0.00'}">disabled="disabled"</c:if> onClick="isUseBalanceRadio(this);">
                                <label>使用（账户余额<fmt:formatNumber type="number" value="${supplierInfo.amount}" pattern="0.00" maxFractionDigits="2" />）</label>
                                <input type="hidden" name="traderAmount" id="traderAmount" value="${supplierInfo.amount}" >
                            </li>
                            <li>
                                <input type="radio" name="isUseBalance" value="2" <c:if test="${supplierInfo.amount eq '0.00'}">checked="checked"</c:if> onClick="isUseBalanceRadio(this);">
                                <label>不使用</label>
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
                            <li>
                                <input type="radio" name="traderSubject" value="1" checked="checked">
                                <label>对公</label>
                                <input type="hidden" name="traderMode" id="traderMode">
                            </li>
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
                    ${afterSales.traderName}
                    	<input type="hidden" name="formToken" value="${formToken}"/>
                    	<input type="hidden" name="relatedId" value="${afterSales.afterSalesId}">
                    	<input type="hidden" name="traderType" id="2">
                    	<input type="hidden" name="traderName" id="traderName" value="${afterSales.traderName}">
                    	<input type="hidden" name=traderSupplierId value="${supplierInfo.traderSupplierId}">
                    </div>
                    <div id="tarderNameError"></div>
                </div>
            </li>
            
            <li class="notUseBalance <c:if test="${supplierInfo.amount ne '0.00'}">none</c:if>">
                <div class="form-tips">
                	<span>*</span>
                    <lable>银行帐号</lable>
                </div>
                <div class="f_left ">
                	<c:forEach var="traderFinanceValue" items="${traderFinance}" varStatus="traderFinanceNum">
	                    <div class="form-blanks <c:if test='${traderFinanceNum.count != 1}'>mt8</c:if>">
	          			 <input value="${traderFinanceValue.bank},${traderFinanceValue.bankAccount},${traderFinanceValue.bankCode},${traderFinanceValue.comments},${traderFinanceValue.traderFinanceId}" 
	          			 		name="traderFinance" type="radio" <c:if test='${traderFinanceNum.count == 1}'>checked="checked"</c:if>>
	          			 	<label id="bank_str_${traderFinanceValue.traderFinanceId}">${traderFinanceValue.bank} / ${traderFinanceValue.bankAccount} / ${traderFinanceValue.bankCode} / ${traderFinanceValue.comments}</label>
	                    </div>
                    </c:forEach>
                    <span style="color: #fc5151; clear:both;" id="bankStrError"></span>
                </div>
            </li>
           
            <li>
                <div class="form-tips">
                    <lable>申请付款金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-small" name="amount" id="amount" readonly="readonly" />
                    </div>
                    <div id="amountError"></div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>付款备注</lable>
                </div>
                <div class="f_left " style='width:80%;'>
                    <div class="form-blanks">
                        <input type="text" class="input-largest"  name="comments" id="comments" />
                    </div>
                    <div id="commentsError"></div>
                   	<table class="table mt10">
                       <thead>
                           <tr>
                               <th class="wid15">产品名称</th>
                               <th class="wid8">单价</th>
                               <th class="wid8">数量</th>
                               <th class="wid13">申请数量/已申请数量</th>
                               <th class="wid13">申请总额/已申请总额</th>
                           </tr>
                       </thead>
                       <tbody>
                       	<c:forEach items="${afterSales.afterSalesGoodsList}" var="asg">
                       		 <tr>
                                 <td class="text-left">
                                    <span class="font-blue cursor-pointer addtitle" 
                                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
	                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}","title":"产品信息"}'>${asg.goodsName}</span>
                                    <div>${asg.sku}</div>
                                    <input type="hidden" name="detailgoodsId" value="${asg.afterSalesGoodsId}" />
                                </td>
                                <td><span id="engPrice">${asg.price}</span>
                                	<input type="hidden" name="price" value="${asg.price}" id="price"/>
                                </td>
                                <td>${asg.num}
                                	<input type="hidden" id="sum" value="${asg.num}"/>
                                </td>
                                <td>
                                	<input type="text" style="width:100px;" name="num" id="num" >/${asg.payApplySum == null ? 0 : asg.payApplySum }
                                	<input type="hidden" id="payApplySum" value="${asg.payApplySum}"/>
                                </td>
                                <td>
                                	<input type="text" style="width:100px;" name="totalAmount" id="totalAmount" readonly="readonly">/${asg.payApplyTotalAmount == null ? 0 : asg.payApplyTotalAmount}
                                	<input type="hidden" id="payApplyTotalAmount" value="${asg.payApplyTotalAmount}"/>
                                </td>
                            </tr>
                       	</c:forEach>
                       </tbody>
                   </table>
                   <div class="pop-friend-tips mt6">
                        <div class="add-tijiao text-left mt8">
                            <button type="submit" id="submit">提交</button>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>

</body>
</html>