<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="申请付款" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list form-tips8">
    <form method="post" id="addBhSaleorderForm" action="./saveApplyPayment.do" onsubmit="return checkForm();">
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
                                <input type="radio" name="isUseBalance" value="1" <c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) gt '0.00'}">checked="checked"</c:if> <c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) le '0.00'}">disabled="disabled"</c:if> onClick="isUseBalanceRadio(this);">
                                <label>使用（账户余额<fmt:formatNumber type="number" value="${supplierInfo.amount}" pattern="0.00" maxFractionDigits="2" />，
                                		当前占用余额<fmt:formatNumber type="number" value="${buyorderVo.occupyAmount}" pattern="0.00" maxFractionDigits="2" />，
                                		当前可申请余额<fmt:formatNumber type="number" value="${supplierInfo.amount - buyorderVo.occupyAmount}" pattern="0.00" maxFractionDigits="2" />）</label>
                            </li>
                            <li>
                                <input type="radio" name="isUseBalance" value="2" <c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) le '0.00'}">checked="checked"</c:if> onClick="isUseBalanceRadio(this);">
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
                    ${buyorderVo.traderName}
                    <input type="hidden" name="traderName" id="traderName" value="${buyorderVo.traderName}">
                    <span style="color: #fc5151;" id="traderNameError"></span>
                </div>
            </li>
            
            <li class="notUseBalance <c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) gt '0.00'}">none</c:if>">
                <div class="form-tips">
                	<span>*</span>
                    <lable>银行帐号</lable>
                </div>
                <div class="f_left ">
                	<c:forEach var="traderFinanceValue" items="${traderFinance}" varStatus="traderFinanceNum">
                    <div class="form-blanks <c:if test='${traderFinanceNum.count != 1}'>mt8</c:if>">
          			 <input value="${traderFinanceValue.traderFinanceId}" name="bank" type="radio" <c:if test='${traderFinanceNum.count == 1}'>checked="checked"</c:if>><label id="bank_str_${traderFinanceValue.traderFinanceId}">${traderFinanceValue.bank} / ${traderFinanceValue.bankAccount} / ${traderFinanceValue.bankCode} / ${traderFinanceValue.comments}</label>
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
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>付款备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	${buyorderVo.paymentComments}
                        <input type="text" class="input-largest" value="${buyorderVo.paymentComments}" name="comments" id="comments" />
                    </div>
                </div>
            </li>
        </ul>
        
        <div class="parts">
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                        <th>产品名称</th>
                        <th class="wid8">品牌</th>
                        <th class="wid8">型号</th>
                        <th class="wid7">单价</th>
                        <th class="wid5">数量</th>
                        <th class="wid4">单位</th>
                        <th>申请数量/已申请数量</th>
                        <th>申请总额/已申请总额</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
                    <tr>
                        <td>
                            <input type="checkbox" name="checkedOne" value="${bgv.buyorderGoodsId}" checked="checked">
                            <input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}">
                            <input type="hidden" name="price" value="${bgv.price}">
                        </td>
                        <td class="text-left">
                            <div class="brand-color1"><span class="font-blue cursor-pointer addtitle" 
			                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${bgv.goodsName}</span></div>
                            <div>${bgv.sku}</div>
                        </td>
                        <td>${bgv.brandName}</td>
                        <td>${bgv.model}</td>
                        <td id="price_${bgv.buyorderGoodsId}"><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td id="allnum_${bgv.buyorderGoodsId}">${bgv.num-bgv.afterSaleUpLimitNum}</td>
                        <td>${bgv.unitName}</td>
                        <td><input type="text" style="width:100px;" value="${bgv.num-bgv.afterSaleUpLimitNum-bgv.applyPaymentNum}" id="num_${bgv.buyorderGoodsId}" name="num" value="" onBlur="changeNum(this,${bgv.buyorderGoodsId});"> /<span id="applyPaymentNum_${bgv.buyorderGoodsId}">${bgv.applyPaymentNum}</span></td>
                        <td><input type="text" style="width:100px;" value="${bgv.price*(bgv.num-bgv.afterSaleUpLimitNum) - bgv.applyPaymentAmount}" id="totalAmount_${bgv.buyorderGoodsId}" name="totalAmount" value="" onBlur="changePrice(this,${bgv.buyorderGoodsId});"> /<span id="applyPaymentAmount_${bgv.buyorderGoodsId}">${bgv.applyPaymentAmount}</span></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="table-style4">
                <div class="allchose">
                    <input type="checkbox" name="checkedAll" checked="checked">
                    <span>全选</span>
                </div>
            </div>
        </div>
        
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="traderMode" id="traderMode" value="<c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) gt '0.00'}">528</c:if><c:if test="${(supplierInfo.amount - buyorderVo.occupyAmount) le '0.00'}">521</c:if>">
        	<input type="hidden" name="relatedId" id="relatedId" value="${buyorderVo.buyorderId}">
        	<input type="hidden" name="formToken" value="${formToken}"/>
        	<input type="hidden" name="bank" id="bank" value="">
        	<input type="hidden" name="bankCode" id="bankCode" value="">
        	<input type="hidden" name="bankAccount" id="bankAccount" value="">
        	<input type="hidden" name="supplyAmount" id="supplyAmount" value="${supplierInfo.amount}">
        	<input type="hidden" name="occupyAmount" id="occupyAmount" value="${buyorderVo.occupyAmount}">
        	<input type="hidden" name="traderSupplierId" id="traderSupplierId" value="${supplierInfo.traderSupplierId}">
            <button type="submit" id="apply_payment_submit">提交</button>
        </div>
    </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/apply_payment.js?rnd=<%=Math.random()%>'></script>
</body>
</html>