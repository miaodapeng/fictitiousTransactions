<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-退货_审核完成" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   基本信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td>${afterSalesVo.afterSalesNo}</td>
                        <td class="wid20">订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.atferSalesStatus eq 0}">待确认</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 1}">进行中</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 2}">已完结</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 3}">已关闭</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${afterSalesVo.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${afterSalesVo.addTime}"/></td>
                    </tr>
                    <tr>
                        <td>生效状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if>
                        	<c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.validTime}"/></td>
                    </tr>
                    <tr>
                        <td>审核状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.status eq 0}">待审核</c:if>
							<c:if test="${afterSalesVo.status eq 1}">审核中</c:if>
							<c:if test="${afterSalesVo.status eq 2}">审核通过</c:if>
							<c:if test="${afterSalesVo.status eq 3}">审核不通过</c:if>
                        </td>
                        <td>售后类型</td>
                        <td class="warning-color1">${afterSalesVo.typeName}</td>
                    </tr>
                    <tr>
                    	<td>完结/关闭原因</td>
                    	<td>${afterSalesVo.afterSalesStatusResonName}</td>
                    	<td>完结/关闭人员</td>
                    	<td>${afterSalesVo.afterSalesStatusUserName }</td>
                    </tr>
                    <tr>
                    	<td>完结/关闭备注</td>
                    	<td colspan="3" class="text-left">${afterSalesVo.afterSalesStatusComments }</td>
                    </tr>
                </tbody>
            </table>
        </div>
		
		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
		<form action="./editAfterSalesApplyPage.do" id="editAfterSaleOrderForm">
			<input type="hidden" name="afterSalesDetailId" value="${afterSalesVo.afterSalesDetailId}">
			<input type="hidden" name="refund" value="${afterSalesVo.refund}">
			<input type="hidden" name="traderSubject" value="${afterSalesVo.traderSubject}">
			<input type="hidden" name="payee" value="${afterSalesVo.payee}">
			<input type="hidden" name="bank" value="${afterSalesVo.bank}">
			<input type="hidden" name="bankCode" value="${afterSalesVo.bankCode}">
			<input type="hidden" name="bankAccount" value="${afterSalesVo.bankAccount}">
			<input type="hidden" name="traderMode" value="${afterSalesVo.traderMode}">
			<input type="hidden" name="refundAmountStatus" value="${afterSalesVo.refundAmountStatus}">
			<input class="child" type="hidden" id="layerIndex">
			<input type="hidden" id="typeFlag" value="0">
		</form>
		 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后申请
                </div>
                <c:if test="${[]==historicActivityInstancePay ||null!=payApply&&(payApply.validStatus==2)}">
					<c:if test="${flag==1}">
                 	<div class="title-click nobor" onclick="editAfterSaleOrder()">编辑</div>
					</c:if>
					<c:if test="${flag==2}">
            		<div class="title-click nobor" onclick="editError()">编辑</div>
					</c:if>
				</c:if>
	</div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">售后原因</td>
                        <td>${afterSalesVo.reasonName}</td>
                        <td class="wid20">联系人</td>
                        <td>${afterSalesVo.traderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        ${afterSalesVo.traderContactTelephone}
                        <c:if test="${not empty afterSalesVo.traderContactTelephone}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactTelephone}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                        <td>手机</td>
                        <td>
                        ${afterSalesVo.traderContactMobile}
                        <c:if test="${not empty afterSalesVo.traderContactMobile}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactMobile}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                    </tr>
                     <tr>
                        <td>款项退还</td>
                        <td>
                        	<c:if test="${afterSalesVo.refund eq 0}">无</c:if>
							<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if>
							<c:if test="${afterSalesVo.refund eq 2}">退给客户</c:if>
                        </td>
                        <td>交易方式</td>
                        <td>
                        	<c:if test="${afterSalesVo.traderMode eq 520}">支付宝</c:if>
                        	<c:if test="${afterSalesVo.traderMode eq 521}">银行</c:if>
                        </td>
                    </tr>
                     <tr>
                        <td>交易主体</td>
                        <td>
                        	<c:if test="${afterSalesVo.traderSubject eq 1}">对公</c:if>
                        	<c:if test="${afterSalesVo.traderSubject eq 2}">对私</c:if>
                        </td>
                        <td>收款名称</td>
                        <td>${afterSalesVo.payee}</td>
                    </tr> 
                    <tr>
                        <td>开户银行</td>
                        <td>${afterSalesVo.bank}</td>
                        <td>银行/支付宝账号</td>
                        <td>${afterSalesVo.bankAccount}</td>
                    </tr>
                     <tr>
                        <td>开户行支付联行号</td>
                        <td>${afterSalesVo.bankCode}</td>
                        <td></td>
                        <td></td>
                    </tr>
                     <tr>
                        <td>详情说明</td>
                        <td colspan="3" class="text-left">${afterSalesVo.comments}</td>
                      
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" class="text-left">
							<%@ include file="view_afterSales_files.jsp"%>
                        </td>
                      
                    </tr>
                </tbody>
            </table>
        </div>
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   所属订单
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td >
                        	<div class="customername pos_rel">
                               <span class="brand-color1 addtitle" style="float:none;" tabTitle='{"num":"viewsaleorder${afterSalesVo.orderNo}","title":"订单信息",
                               		"link":"./order/saleorder/view.do?saleorderId=${afterSalesVo.orderId}"}'>${afterSalesVo.orderNo}</span><i class="iconbluemouth"></i>
                               <div class="pos_abs customernameshow" style="display: none;">
                                        付款状态：<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if><br> 
                                        发货状态：<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if><br>
                                        开票状态：<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if><br>
                                        收货状态：<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
                               </div>
                            </div>
                        </td>
                        <td class="wid20">订单金额</td>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                    </tr>
                    <tr>
                        <td>部门</td>
                        <td>${afterSalesVo.orgName}</td>
                        <td>归属销售</td>
                        <td>${afterSalesVo.userName}</td>
                    </tr>
                     <tr>
                        <td>订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 0}">待确认</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 1}">进行中</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 2}">已完结</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 3}">已关闭</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.saleorderValidTime}"/></td>
                    </tr>
                     <tr>
                        <td>客户名称</td>
                        <td>
                        	<div class="customername pos_rel">
                                  <span class="brand-color1 addtitle" style="float:none;" tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"客户信息",
										"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}</span><i class="iconbluemouth"></i>
                                   <div class="pos_abs customernameshow" style="display: none;">
                                            客户性质：<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
                          <c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if><br> 
                                            交易次数：${afterSalesVo.orderCount}<br>
                                            交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
                                            上次交易日期：<date:date value ="${afterSalesVo.lastOrderTime}"/>   
                                        </div>
                            </div>
                        </td>
                        <td>客户等级</td>
                        <td>
                        	<c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 147}">B</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 148}">C</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 149}">D</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 150}">E</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 931}">S</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">退货信息</div>
            </div>
			<c:set var="afterSalesGoodsVoListPage" value="${afterSalesVo.afterSalesGoodsList}"></c:set>
			<%@ include file="view_afterSales_th_goods.jsp"%>

        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后服务费
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.isModifyServiceAmount eq 1 && afterSalesVo.refundAmountStatus ne 4 && afterSalesVo.refundAmountStatus ne 3 }">
                 	<div class="title-click nobor  pop-new-data" layerParams='{"width":"600px","height":"400px","title":"编辑售后服务费",
		                  "link":"./editInstallstionPage.do?afterSalesDetailId=${afterSalesVo.afterSalesDetailId}&&afterSalesId=${afterSalesVo.afterSalesId}&&serviceAmount=${afterSalesVo.serviceAmount}&&isSendInvoice=${afterSalesVo.isSendInvoice}&&invoiceType=${afterSalesVo.invoiceType}&flag=th"}'>编辑</div>
            	</c:if>
            </div>
            <table class="table">
                <tbody>
                	<tr>
                        <td>退换货手续费</td>
                        <td>${afterSalesVo.serviceAmount }</td>
                        <td>票种</td>
                        <td>
                        	<c:if test="${afterSalesVo.invoiceType eq 429}">17%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 430}">17%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 682}">16%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 681}">16%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 972}">13%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 971}">13%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 683}">6%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 684}">6%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 685}">3%增值税普用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 686}">3%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 687}">0%增值税普通发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        	<c:if test="${afterSalesVo.invoiceType eq 688}">0%增值税专用发票
                        		<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        		<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                        	</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>收票客户</td>
                        <td>${afterSalesVo.invoiceTraderName }</td>
                        <td>收票联系人</td>
                        <td>${afterSalesVo.invoiceTraderContactName }</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>${afterSalesVo.invoiceTraderContactTelephone }</td>
                        <td>手机</td>
                        <td>${afterSalesVo.invoiceTraderContactMobile }</td>
                    </tr>
                    <tr>
                        <td>收票地区</td>
                        <td>${afterSalesVo.invoiceTraderArea }</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>收票地址</td>
                        <td colspan="3">${afterSalesVo.invoiceTraderAddress }</td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3">${afterSalesVo.invoiceComments }</td>
                    </tr>
                </tbody>
            </table>
            <div class="table-buttons">
	            <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
	                <form action="" method="post" id="myform">
	             		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
	             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
	             		<input type="hidden" name="orderId" value="${afterSalesVo.orderId}"/>
	             		<input type="hidden" name="traderId" value="${afterSalesVo.traderId}"/>
	             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
	             		<input type="hidden" name="formToken" value="${formToken}"/>
	             		<!-- <button type="button" class="bt-bg-style bg-light-green bt-small" >打印合同</button> -->
	             		<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
						<c:if test="${afterSalesVo.isCanApplyInvoice eq 1 }">
				            <button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data mr10" 
				            		layerParams='{"width":"600px","height":"300px","title":"申请开票",
				            					"link":"./openInvoiceApply.do?relatedId=${afterSalesVo.afterSalesId}"}'>申请开票</button>
						</c:if>
						<c:if test="${afterSalesVo.isCanApplyInvoice eq 0 && afterSalesVo.serviceAmount gt 0 && afterSalesVo.isModifyServiceAmount eq 0}">
				            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
						</c:if>

				<!--//退货总数量=入库数量加上拦截数量 //退款状态不为已退款或退款中  //有退还方式且预付款大于0  或者  无退款方式且账期支付大于0-->
	             		<c:if test="${sum eq incntNum + systemInterceptNum
	             		&& afterSalesVo.refundAmountStatus ne 4
	             		&& afterSalesVo.refundAmountStatus ne 3
	             		&& ((afterSalesVo.preAnnealing gt 0 && afterSalesVo.refund ne 0) || (afterSalesVo.payPeriodAmount gt 0 && afterSalesVo.refund eq 0))}">

	             			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="executeRefundOperation();">执行退款运算</button>
	             		</c:if>
						<div style="display:none" sum="${sum}" incntNum="${incntNum}" systemInterceptNum="${systemInterceptNum}"
							 refundAmountStatus="${afterSalesVo.refundAmountStatus}"
							 preAnnealing="${afterSalesVo.preAnnealing}"
							 refund="${afterSalesVo.refund}"
						></div>
	             		<c:if test="${afterSalesVo.realRefundAmount gt 0 && afterSalesVo.refund eq 2 && afterSalesVo.refundAmountStatus eq 4}">
	             			<input type="hidden" name="relatedId" value="${afterSalesVo.afterSalesId}"/>
	             			<input type="hidden" name="realRefundAmount" value="${afterSalesVo.realRefundAmount}"/>
							<input type="hidden" name="traderMode" value="${afterSalesVo.traderMode}"/>
							<c:if test="${isPayApplySh == 0}">
								<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="applyPay();">申请付款</button>
							</c:if>
						</c:if>
						<c:if test="${(null==taskInfoOver and null==taskInfoOver.getProcessInstanceId())or (null!=taskInfoOver and taskInfoOver.assignee==null and empty candidateUserMapOver[taskInfoOver.id])}">
							<c:if test="${afterSalesVo.refundAmountStatus eq 0 || afterSalesVo.refundAmountStatus eq 3 }">
								<button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data"
										layerParams='{"width":"780px","height":"310px","title":"选择售后原因","link":"../order/saleorderComplete.do?afterSalesId=${afterSalesVo.afterSalesId}&afterSalesType=756&type=${afterSalesVo.type}&orderId=${afterSalesVo.orderId }&subjectType=${afterSalesVo.subjectType }&formToken=${formToken }&traderId=${afterSalesVo.traderId }"}'>确认完成</button>
							</c:if>
							<c:if test="${afterSalesVo.status ne 1 && afterSalesVo.closeStatus eq 1}">
								<button type="button" class="bt-bg-style bg-light-orange bt-small pop-new-data"
										layerParams='{"width":"780px","height":"310px","title":"选择售后原因","link":"../order/causeOfAfterSales.do?afterSalesId=${afterSalesVo.afterSalesId}&afterSalesType=756&type=${afterSalesVo.type}&orderId=${afterSalesVo.orderId }&subjectType=${afterSalesVo.subjectType }&formToken=${formToken }&traderId=${afterSalesVo.traderId }"}'>关闭订单</button>
							</c:if>
						</c:if>
						<c:if test="${(null!=taskInfoOver and null!=taskInfoOver.getProcessInstanceId() and null!=taskInfoOver.assignee) or !empty candidateUserMapOver[taskInfoOver.id]}">
							<c:choose>
								<c:when test="${taskInfoOver.assignee == curr_user.username or candidateUserMapOver['belong']}">
									<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoOver.id}&pass=true&type=2&sku=${sku}&orderId=${afterSalesVo.orderId}&afterSalesId=${afterSalesVo.afterSalesId}"}'>审核通过</button>
									<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoOver.id}&pass=false&type=2"}'>审核不通过</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
								</c:otherwise>
							</c:choose>
						</c:if>
	                </form>
	            </c:if>
            </div>
        </div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">退货入库记录</div>
			</div>
			<table class="table  table-style6">
				<thead>
					<tr>
						<th class="wid6">序号</th>
						<th class="wid18">产品名称</th>
						<th class="wid12">品牌</th>
						<th class="wid8">型号</th>
						<th class="wid8">数量</th>
						<th class="wid5">单位</th>
						<th class="wid10">贝登条码</th>
						<th class="wid10">厂商条码</th>
						<th class="wid12">入库时间</th>
						<th class="wid6">操作人</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty afterSalesVo.afterReturnInstockList}">
						<c:forEach items="${afterSalesVo.afterReturnInstockList}" var="arg" varStatus="num_index">
							<tr>
								<td>${num_index.count }</td>
								<td class="text-left">
									<div class="customername pos_rel">
										<span class="brand-color1 addtitle"
											tabTitle='{"num":"viewgoods${arg.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${arg.goodsId}", "title":"产品信息"}'>
											${arg.goodsName}
										</span>
										<br>${arg.sku}
										<br>${arg.materialCode}
									</div>
								</td>
								<td>${arg.brandName}</td>
								<td>${arg.model}</td>
								<td>${arg.num}</td>
								<td>${arg.unitName}</td>
								<td>${arg.barcode}</td>
								<td>${arg.barcodeFactory}</td>
								<td><date:date value="${arg.addTime}" /></td>
								<td>${arg.operator}</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty afterSalesVo.afterReturnInstockList}">
						<tr>
							<td colspan="10">暂无记录</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
         
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   退票记录
                </div>
            </div>
            <table class="table">
                <thead>
                
                    <tr>
                        <th>发票号</th>
                        <th>发票代码</th>
                        <th>发票金额</th>
                        <th>票种</th>
                        <th>寄送状态</th>
                        <th>是否需寄回</th>
                        <th>退票状态</th>
                    </tr>
                </thead>
                <tbody>
	                <c:if test="${not empty afterSalesVo.afterSalesInvoiceVoList}">
	               		<c:forEach items="${afterSalesVo.afterSalesInvoiceVoList}" var="asi" >
		               		<tr>
		                        <td>${asi.invoiceNo}</td>
		                        <td>${asi.invoiceCode}</td>
		                        <td><fmt:formatNumber type="number" value="${asi.amount}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>
		                        	<c:if test="${asi.invoiceType eq 429}">17%增值税专用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 430}">17%增值税普通发票</c:if> 
		                        	<c:if test="${asi.invoiceType eq 682}">16%增值税专用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 681}">16%增值税普通发票</c:if> 
		                        	<c:if test="${asi.invoiceType eq 972}">13%增值税专用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 971}">13%增值税普通发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 683}">6%增值税普通发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 684}">6%增值税专用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 685}">3%增值税普用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 686}">3%增值税专用发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 687}">0%增值税普通发票</c:if>
		                        	<c:if test="${asi.invoiceType eq 688}">0%增值税专用发票</c:if>
		                        </td>
		                        <td>
		                        	<c:if test="${asi.expressId != null && asi.expressId != 0}">已寄送</c:if>
		                        	<c:if test="${asi.expressId == null || asi.expressId == 0}">未寄送</c:if>
		                        </td>
		                        <td>
		                        	<c:if test="${asi.isSendInvoice eq 1}">是</c:if>
		                        	<c:if test="${asi.isSendInvoice eq 0}">否</c:if>
		                        </td>
		                        <td>
		                        	<c:if test="${asi.status eq 1}">已退票</c:if>
		                        	<c:if test="${asi.status eq 0}">未退票</c:if>
		                        </td>
		                    </tr>
	               		</c:forEach>
	               	</c:if>
                    <c:if test="${empty afterSalesVo.afterSalesInvoiceVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='7'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">发票记录</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>发票号</th>
						<th>发票代码</th>
						<th>票种</th>
						<th>红蓝字</th>
						<th>发票金额</th>
						<th>操作人</th>
						<th>操作时间</th>
						<th>快递公司</th>
						<th>快递单号</th>
						<th>快递状态</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty afterSalesVo.afterOpenInvoiceList}">
						<c:forEach items="${afterSalesVo.afterOpenInvoiceList}" var="aoi">
							<tr>
								<td>${aoi.invoiceNo}</td>
								<td>${aoi.invoiceCode}</td>
								<td>
									<c:if test="${aoi.invoiceType eq 429}">17%增值税专用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 430}">17%增值税普通发票</c:if>
									<c:if test="${aoi.invoiceType eq 682}">16%增值税专用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 681}">16%增值税普通发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 972}">13%增值税专用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 971}">13%增值税普通发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 683}">6%增值税普通发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 684}">6%增值税专用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 685}">3%增值税普用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 686}">3%增值税专用发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 687}">0%增值税普通发票</c:if>
		                        	<c:if test="${aoi.invoiceType eq 688}">0%增值税专用发票</c:if>
								</td>
								<td>
									<c:choose>
										<c:when test="${aoi.colorType eq 1}">
											<c:choose>
												<c:when test="${aoi.isEnable eq 0}">
													<span style="color: red">红字作废</span>
												</c:when>
												<c:otherwise>
													<span style="color: red">红字有效</span>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${aoi.isEnable eq 0}">
													<span style="color: red">蓝字作废</span>
												</c:when>
												<c:otherwise>
													蓝字有效
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</td>
								<td><fmt:formatNumber type="number" value="${aoi.amount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>${aoi.creatorName}</td>
								<td><date:date value="${aoi.addTime}" /></td>
								<td>${aoi.logisticsName}</td>
								<td>${aoi.logisticsNo}</td>
								<td>
									<c:choose>
										<c:when test="${aoi.arrivalStatus eq 0}">
											未收货
										</c:when>
										<c:when test="${aoi.arrivalStatus eq 1}">
											部分收货
										</c:when>
										<c:when test="${aoi.arrivalStatus eq 2}">
											全部收货
										</c:when>
									</c:choose>
								</td>
								<td>${aoi.logisticsComments}</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty afterSalesVo.afterOpenInvoiceList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan='11'>暂无记录！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   退款信息
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>已付款金额（不含信用支付）</th>
                        <th>退货金额</th>
                        <th>偿还账期</th>
                        <th>售后退货手续费</th>
                        <th>款项退还</th>
                        <th>实退金额</th>
                        <th>已退金额</th>
                        <th>退款状态</th>
                    </tr>
                </thead>
                <tbody>
              		<tr>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.payAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.refundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="warning-color1"><fmt:formatNumber type="number" value="${afterSalesVo.payPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="warning-color1"><fmt:formatNumber type="number" value="${afterSalesVo.serviceAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="warning-color1">
                        	<c:if test="${afterSalesVo.refund eq 0}">无</c:if>
							<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if>
							<c:if test="${afterSalesVo.refund eq 2}">退给客户</c:if>
                        </td>
                        <td class="warning-color1" id="realRefundAmount"><fmt:formatNumber type="number" value="${afterSalesVo.realRefundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="warning-color1">
                        <c:if test="${afterSalesVo.haveRefundAmount ne 0 }">
                        	<fmt:formatNumber type="number" value="${afterSalesVo.realRefundAmount}" pattern="0.00" maxFractionDigits="2" />
                        </c:if>
                        <c:if test="${afterSalesVo.haveRefundAmount eq 0 }">
                        	0
                        </c:if>
                        </td>
                        <td class="warning-color1">
                        	<c:if test="${afterSalesVo.refundAmountStatus eq 0}">无退款</c:if>
                        	<c:if test="${afterSalesVo.refundAmountStatus eq 1}">未退款</c:if>
                        	<c:if test="${afterSalesVo.refundAmountStatus eq 2}">部分退款</c:if>
                        	<c:if test="${afterSalesVo.refundAmountStatus eq 3}">已退款</c:if>
                        	<c:if test="${afterSalesVo.refundAmountStatus eq 4}">退款中</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">交易信息</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>记账编号</th>
						<th>业务类型</th>
						<th>交易金额</th>
						<th>交易时间</th>
						<th>交易主体</th>
						<th>交易方式</th>
						<th>交易名称</th>
						<th>交易备注</th>
						<th>操作人</th>
						<th>操作时间</th>
						<th>电子回执单</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty afterSalesVo.afterCapitalBillList}">
						<c:forEach items="${afterSalesVo.afterCapitalBillList}" var="acb">
							<tr>
								<td>${acb.capitalBillNo}</td>
								<td>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 525}">订单付款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 526}">订单收款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 531}">退款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 532}">资金转移</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 533}">信用还款</c:if>
								</td>
								<td><fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>
									<c:if test="${acb.traderTime != 0}">
										<date:date value="${acb.traderTime}" />
									</c:if>
								</td>
								<td>
									<c:if test="${acb.traderSubject == 1}">
		                        		对公
		                        	</c:if> 
		                        	<c:if test="${acb.traderSubject == 2}">
		                        		对私
		                        	</c:if>
		                        </td>
								<td>
									<c:if test="${acb.traderMode eq 520}">支付宝</c:if>
									<c:if test="${acb.traderMode eq 521}">银行</c:if>
									<c:if test="${acb.traderMode eq 522}">微信</c:if>
									<c:if test="${acb.traderMode eq 522}">现金</c:if>
									<c:if test="${acb.traderMode eq 527}">信用支付</c:if>
									<c:if test="${acb.traderMode eq 528}">余额支付</c:if>
									<c:if test="${acb.traderMode eq 529}">退还信用</c:if>
									<c:if test="${acb.traderMode eq 530}">退还余额</c:if>
								</td>
								<td>${acb.payer}</td>
								<td class="text-left">${acb.comments}</td>
								<td>${acb.creatorName}</td>
								<td>
									<c:if test="${acb.addTime != 0}">
										<date:date value="${acb.addTime}" />
									</c:if>
								</td>
								<td>
		                        	<c:if test="${(acb.traderType == 2 || acb.traderType == 5) && acb.bankBillId != 0}">
			                        	<div class="caozuo">
											<span class="caozuo-blue addtitle"   tabTitle='{"num":"credentials${acb.bankBillId}", "link":"<%=basePath%>finance/capitalbill/credentials.do?bankBillId=${acb.bankBillId}","title":"电子回执单"}'>查看</span>
										</div>
		                        	</c:if>
		                        </td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${ empty afterSalesVo.afterCapitalBillList}">
						<tr>
							<td colspan="11">暂无记录！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">退票材料</div>
				<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
					<div class="title-click nobor pop-new-data" layerParams='{"width":"600px","height":"300px","title":"退票材料",
                					"link":"./ticketReturnInit.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>上传</div>
                </c:if>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>材料</th>
						<th>操作人</th>
						<th>时间</th>
						<th class="table-smallest5"> 操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty afterSalesVo.afterInvoiceAttachmentList}">
						<c:forEach items="${afterSalesVo.afterInvoiceAttachmentList}" var="aia">
							<tr>
								<td class="font-blue"><a href="http://${aia.domain}${aia.uri}" target="_blank">${aia.name}</a></td>
								<td>${aia.username}</td>
								<td><date:date value="${aia.addTime}" /></td>
								<td>
								<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                            <div class="caozuo">
		                                <span class="caozuo-red" onclick="contractReturnDel2(${aia.attachmentId})">删除</span>
		                            </div>
		                            </c:if>
		                        </td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty afterSalesVo.afterInvoiceAttachmentList}">
						<tr>
							<td colspan="4">暂无记录！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
		<div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    合同回传
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                	<div class="title-click nobor pop-new-data" layerParams='{"width":"600px","height":"300px","title":"合同回传",
                					"link":"./contractReturnInit.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>上传</div>
               	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>合同</th>
                        <th class="table-small">操作人</th>
                        <th class="table-small">时间</th>
                        <th class="table-smallest5"> 操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${afterSalesVo.afterContractAttachmentList}" var="list" varStatus="status">
						<tr>
	                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
	                        <td>${list.username}</td>
	                        <td><date:date value ="${list.addTime}"/></td>
	                        <td>
	                        	<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
	                            <div class="caozuo">
	                                <span class="caozuo-red" onclick="contractReturnDel(${list.attachmentId})">删除</span>
	                            </div>
	                            </c:if>
	                        </td>
	                    </tr>
                   	</c:forEach>
                   	<c:if test="${empty afterSalesVo.afterContractAttachmentList}">
						<tr>
							<td colspan="4">暂无记录！</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>
        
       <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    售后对象
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                	<div class="title-click nobor pop-new-data" layerParams='{"width":"650px","height":"500px","title":"新增",
                					"link":"./searchAfterTraderPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增</div>
               	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid25">售后对象名称</th>
                        <th class="wid10">对象类型</th>
                        <th >备注</th>
                        <th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${afterSalesVo.afterSalesTraderList}" var="ast" varStatus="status">
						<tr>
	                        <td class="font-blue">
		                        <c:if test="${ast.realTraderType eq 1}">
		                        	<span class="brand-color1 addtitle" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"客户信息",
											"link":"./trader/customer/baseinfo.do?traderId=${ast.traderId}"}'>${ast.traderName}</span>
								</c:if>	
								<c:if test="${ast.realTraderType eq 2}">
									<span class="brand-color1 addtitle"  tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"供应商信息",
										"link":"./trader/supplier/baseinfo.do?traderId=${ast.traderId}"}'>${ast.traderName}</span>
								</c:if>		
							</td>
	                        <td>
	                        	<c:if test="${ast.traderType eq 1}">客户</c:if>
	                       	 	<c:if test="${ast.traderType eq 2}">供应商</c:if>
	                        </td>
	                        <td>${ast.comments}</td>
	                        <td>
	                        	<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                            <div class="caozuo">
		                            <span class="border-blue pop-new-data" 
		                            			layerParams='{"width":"650px","height":"500px","title":"编辑","link":"./editAfterTraderPage.do?afterSalesTraderId=${ast.afterSalesTraderId}"}'>编辑</span>
		                                <span class="caozuo-red" onclick="delAfterTrader(${ast.afterSalesTraderId})">删除</span>
		                            </div>
	                            </c:if>
	                        </td>
	                    </tr>
                   	</c:forEach>
                   	<c:if test="${empty afterSalesVo.afterSalesTraderList}">
						<tr>
							<td colspan="4">暂无记录！</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>


<!-- 沟通记录只能让售后看 -->
 <c:if test="${sessionScope.curr_user.positType eq 312}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    沟通记录
                </div>
                 <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                 <!--  
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>
	                   	 新增
	                </div>
	               --> 
	                
	                <!-- 2018-08-08 新增沟通记录添加通话记录 -->
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"560px","height":"80%","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/getCommunicateRecordList.do?afterSalesId=${afterSalesVo.afterSalesId}&traderId=${afterSalesVo.traderId }&traderType=1"}'>
	                   	 新增
	                </div>
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid10">沟通时间</th>
                        <th class="">录音</th>
                        <th class="">售后对象</th>
                        <th class="">联系人</th>
                        <th class="">联系方式</th>
                        <th class="">沟通方式</th>
                        <th class="wid30">沟通内容</th>
                        <th class="">操作人</th>
                        <th class="wid8">下次联系日期</th>
                        <th class="wid15">下次沟通内容</th>
                        <th class="">备注</th>
                        <th class="wid10">创建时间</th>
                        <th class="wid6">操作</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${not empty communicateList}">
                		<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
                			<tr>
		                        <td><date:date value ="${communicateRecord.begintime} "/>~<date:date value ="${communicateRecord.endtime}" format="HH:mm:ss"/></td>
		                        <td ><span class="font-blue" style = "cursor:pointer;" onclick="checkcoidURI(${communicateRecord.relateCommunicateRecordId});" id="${communicateRecord.relateCommunicateRecordId}" name="record">${communicateRecord.communicateRecordId }</span></td>
		                        <td>${communicateRecord.afterSalesTraderName }</td>
		                        <td>${communicateRecord.contactName}</td>
		                        <td>${communicateRecord.phone}</td>
		                        <td>${communicateRecord.communicateModeName}</td>
		                        <td>
		                        	<ul class="communicatecontent ml0">
		                        		<c:choose>
		                        			<c:when test="${not empty communicateRecord.tag }">
												<c:forEach items="${communicateRecord.tag }" var="tag">
													<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
												</c:forEach>
											</c:when>
											<c:otherwise>
													<li>${communicateRecord.contactContent }</li>
											</c:otherwise>
		                        		</c:choose>
									</ul>
								</td>
		                        <td>${communicateRecord.user.username}</td>
		                        <c:choose>
			                    	<c:when test="${communicateRecord.isDone == 0 }">
				                   		<td class="font-red">${communicateRecord.nextContactDate }</td>
			                    	</c:when>
			                    	<c:otherwise>
				                    	<td>${communicateRecord.nextContactDate }</td>
			                    	</c:otherwise>
			                    </c:choose>
			                    <td>${communicateRecord.nextContactContent}</td>
		                        <td>${communicateRecord.comments}</td>
		                        <td><date:date value ="${communicateRecord.addTime} "/></td>
		                        
		                        <td class="caozuo">
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"890px","height":"63%","title":"编辑沟通记录",
		                        		"link":"<%= basePath %>/aftersales/order/editcommunicate.do?flag=${afterSalesVo.status }&communicateRecordId=${communicateRecord.communicateRecordId}&relateCommunicateRecordId=${communicateRecord.relateCommunicateRecordId }&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&traderContactId=${communicateRecord.traderContactId }&traderType=1"}'>编辑</span>
		                        </c:if>
		                        </td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='13'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
  </c:if>      
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后过程
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"./addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
	                </div>
                </c:if>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>沟通时间</th>
                        <th>操作人</th>
                        <th>售后内容</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
	               		<c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi" >
	               			<tr>
		                        <td><date:date value ="${asi.addTime} "/></td>
		                        <td>${asi.optName}</td>
		                        <td>${asi.content}</td>
		                        <td class="caozuo">
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"./editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span>
		                        </c:if>
		                        </td>
		                    </tr>
	               		</c:forEach>
	               	</c:if>
	               	<c:if test="${empty afterSalesVo.afterSalesRecordVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>  
        </div>
       
        <c:if test="${sessionScope.curr_user.positType eq 312}">
<!-- 售后费用表格 -->
        <div class="parts">
        	<div class="title-container">
                <div class="table-title nobor">售后费用</div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"780px","height":"310px","title":"新增售后费用类型","link":"./addCostType.do?afterSalesId=${afterSalesVo.afterSalesId}&&costType=719"}'>新增
	                </div>
	           </c:if>
               
            </div>
            <table class="table">
            	<thead>
            		<tr>
            			<th>费用类型</th>
            			<th>费用</th>
            			<th>备注说明</th>
            			<th>操作时间</th>
            			<th>操作人</th>
            			<c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
            				<th>操作</th>
            			</c:if>
            		</tr>
            	</thead>
            	
            	<tbody>
            		<c:forEach var="list" items="${costList}" >
            		<tr>
            			<td>${list.costTypeName }</td>
            			<td>${list.amount }</td>
            			<td>${list.comments }</td>
            			<td>${list.lastModTime }</td>
            			<td>${list.userName }</td>
            			<c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
	            			<td class="caozuo">
		            			<span class="caozuo-red" onclick="delectCostTypeList(${list.afterSalesCostId})">删除</span>
		            			<span class="border-blue pop-new-data" layerParams='{"width":"780px","height":"310px","title":"编辑售后费用",
			                        			"link":"./getCostTypeById.do?afterSalesCostId=${list.afterSalesCostId}&&costType=719"}'>编辑</span>
	            			</td>
	            		</c:if>
            		</tr>
            		</c:forEach>
            		<c:if test="${empty costList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
		           			<c:choose>
		           				<c:when test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
		           					<td colspan='6'>暂无记录！</td>	
		           				</c:when>
		           				<c:otherwise>
		           					<td colspan='5'>暂无记录！</td>	
		           				</c:otherwise>
		           			</c:choose>
					    </tr>
		        	</c:if>
            	</tbody>
            </table>
        </div>
<!--        售后费用表格结束  -->
	    </c:if>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${null!=historicActivityInstance}">
                    <c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
                    <c:if test="${not empty  hi.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hi.activityType == 'startEvent'}"> 
							${startUser}
							</c:when>
							<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstance.size() == status.count}">
									${verifyUsers}
								</c:if>
								<c:if test="${historicActivityInstance.size() != status.count}">
									${hi.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hi.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hi.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMap[hi.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstance}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后单完结审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${null!=historicActivityInstanceOver}">
                    <c:forEach var="hio" items="${historicActivityInstanceOver}" varStatus="status">
                    <c:if test="${not empty  hio.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hio.activityType == 'startEvent'}"> 
							${startUserOver}
							</c:when>
							<c:when test="${hio.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceOver.size() == status.count}">
									${verifyUsersOver}
								</c:if>
								<c:if test="${historicActivityInstanceOver.size() != status.count}">
									${hio.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hio.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hio.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hio.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hio.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapOver[hio.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceOver}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	付款审核记录 
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
					<c:if test="${null!=historicActivityInstancePay}">
                    <c:forEach var="hip" items="${historicActivityInstancePay}" varStatus="status">
                    <c:if test="${not empty  hip.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hip.activityType == 'startEvent'}"> 
							${startUser}
							</c:when>
							<c:when test="${hip.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstancePay.size() == status.count}">
									${verifyUsersPay}
								</c:if>
								<c:if test="${historicActivityInstancePay.size() != status.count}">
									${hip.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hip.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hip.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hip.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hip.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapPay[hip.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstancePay}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>
        <c:if test="${invoiceApply != null && companyId != 1}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	开票审核记录 
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
					<c:if test="${null!=historicActivityInstanceInvoice}">
                    <c:forEach var="hii" items="${historicActivityInstanceInvoice}" varStatus="status">
                    <c:if test="${not empty  hii.activityName}">
                    <tr>
                    	<td>
                    		<c:choose>
							<c:when test="${hii.activityType == 'startEvent'}"> 
							${startUserInvoice}
							</c:when>
							<c:when test="${hii.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceInvoice.size() == status.count}">
									${verifyUsersInvoice}
								</c:if>
								<c:if test="${historicActivityInstanceInvoice.size() != status.count}">
									${hii.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hii.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hii.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hii.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hii.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapInvoice[hii.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceInvoice}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>
		</c:if>
     </div>   
</body>

</html>
