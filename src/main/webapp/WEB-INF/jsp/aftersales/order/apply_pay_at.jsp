<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="申请付款-安调" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/apply_pay.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/aftersales/order/saveApplyPay.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>请选择工程师</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales selectedRadio">
                        	<c:forEach items="${afterSales.afterSalesInstallstionVoList }" var="asi" varStatus="status">
                        		<li>
	                                 <input type="radio" <c:if test="${status.count eq 1 }">checked="true"</c:if> name="name" value="${status.count}">
	                                 <input type="hidden" id="name_${status.count}" value="${asi.name}">
	                                 <input type="hidden" id="company_${status.count}" value="${asi.company}">
	                                 <input type="hidden" id="bank_${status.count}" value="${asi.bank}">
	                                 <input type="hidden" id="bankCode_${status.count}" value="${asi.bankCode}">
	                                 <input type="hidden" id="bankAccount_${status.count}" value="${asi.bankAccount}">
	                                 <input type="hidden" class="engineerId" id="engineerId_${status.count}" value="${asi.engineerId}">
	                                 <input type="hidden" id="engineerAmount_${status.count}" value="${asi.engineerAmount}">
	                                 <input type="hidden" class="afterSalesInstallstionId" id="afterSalesInstallstionId_${status.count}" value="${asi.afterSalesInstallstionId}">
	                                 <input type="hidden" id="payApplyTotalAmount_${status.count}" value="${asi.payApplyTotalAmount}">
	                                 <input type="hidden" id="payApplySum_${status.count}" value="${asi.payApplySum}">
	                                 <input type="hidden" name="formToken" value="${formToken}"/>
	                                 <label>${asi.name}</label>
	                            </li>
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
                            <li>
                                <input type="radio" name="traderSubject" value="1">
                                <label>对公</label>
                            </li>
                            <li>
                               <input type="radio" name="traderSubject" value="2" checked="checked">
                               <label>对私</label>
                            </li>
                       </ul>    
                   </div>
                   <div id="traderSubjectError" class="font-red none" >交易主体不允许为空</div>
               </div>
           </li>
            <li>
            	<div class="form-tips">
                    <span>*</span>
                    <lable>交易名称</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                    	<input type="hidden" name="engineerId" id="engineerId">
                    	<input type="hidden" name="traderMode" value="521">
                    	<input type="hidden" name="isUseBalance" value="0">
                    	<input type="hidden" name="relatedId" value="${afterSales.afterSalesId}">
                    	<input type="hidden" name="traderType" id="1">
                    	<select name="traderName" id="traderName">
                    	</select>
                    </div>
                    <div id="tarderNameError"></div>
                </div>
            </li>
           	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>开户银行</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <input type="text" class="input-largest" id="bank" name="bank"> 
                    </div>
                    <div id="bankError"></div>
                </div>
            </li>
                <li>
                    <div class="form-tips">
                        <lable>开户行支付银行号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-largest" id="bankCode" name="bankCode" > 
                        </div>
                        <div id="bankCodeError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>银行账号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                             <input type="text" class="input-largest" id="bankAccount" name="bankAccount" > 
                        </div>
                        <div id="bankAccountError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>申请付款金额</lable>
                    </div>
                    <div class="f_left" style='width:80%;'>
                        <div class="form-blanks">
                             <input type="text" class="input-largest" id="amount" name="amount" readonly="readonly"> 
                        </div>
                        <div id="totalAmountError"></div>
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
			                                <td>
			                                	<span id="engPrice"></span>
			                                	<input type="hidden" name="price" id="price"/>
			                                </td>
			                                <td>
			                                	<input type="hidden" id="sum" value="${asg.num}"/>${asg.num}
			                                </td>
			                                <td>
			                                	<input type="text" style="width:100px;" name="num" id="num" >/<span id="paySum"></span>
			                                	<input type="hidden" id="payApplySum" value=""/>
			                                </td>
			                                <td>
			                                	<input type="text" style="width:100px;" name="totalAmount" id="totalAmount" readonly="readonly">/<span id="payTotalAmount"></span>
			                                	<input type="hidden" id="payApplyTotalAmount" value=""/>
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
<%@ include file="../../common/footer.jsp"%>