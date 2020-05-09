<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-退货_审核中" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales_buyorder.js?rnd=<%=Math.random()%>'></script>
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
                </tbody>
            </table>
        </div>
		
		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
		 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   售后申请
                </div>
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
							<c:if test="${afterSalesVo.refund eq 1}">退至公司账户</c:if>
							<c:if test="${afterSalesVo.refund eq 2}">退至供应商余额</c:if>
                        </td>
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
                        	<c:if test="${not empty afterSalesVo.attachmentList }">
                        		<c:forEach items="${afterSalesVo.attachmentList }" var="att">
                        			<a href="http://${att.domain}${att.uri}" target="_blank">${att.name}</a>&nbsp;&nbsp;
                        		</c:forEach>
                        	</c:if>
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
                               <span class="brand-color1 addtitle"style="float:none;"  tabTitle='{"num":"viewsaleorder${afterSalesVo.orderNo}","title":"订单信息",
                               		"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${afterSalesVo.orderId}"}'>${afterSalesVo.orderNo}</span><i class="iconbluemouth"></i>
                               <div class="pos_abs customernameshow" style="display: none;">
                                             付款状态：<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if><br> 
                                        收货状态：<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if><br>
                                        收票状态：<c:if test="${afterSalesVo.invoiceStatus eq 0}">未收票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分收票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部收票</c:if>
                               </div>
                            </div>
                        </td>
                        <td class="wid20">订单金额</td>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                    </tr>
                    <tr>
                        <td>部门</td>
                        <td>${afterSalesVo.orgName}</td>
                        <td>归属采购</td>
                        <td>${afterSalesVo.userName}</td>
                    </tr>
                     <tr>
                        <td>订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.buyorderStatus eq 0}">待确认</c:if>
                        	<c:if test="${afterSalesVo.buyorderStatus eq 1}">进行中</c:if>
                        	<c:if test="${afterSalesVo.buyorderStatus eq 2}">已完结</c:if>
                        	<c:if test="${afterSalesVo.buyorderStatus eq 3}">已关闭</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.buyorderValidTime}"/></td>
                    </tr>
                     <tr>
                        <td>供应商名称</td>
                        <td>
                        	<div class="customername pos_rel">
                                  <span class="brand-color1 addtitle" style="float:none;"  tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"供应商信息",
										"link":"./trader/supplier/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}</span><i class="iconbluemouth"></i>
                                   <div class="pos_abs customernameshow" style="display: none;">
                                            交易次数：${afterSalesVo.orderCount}<br>
                                            交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
                                            上次交易日期：<date:date value ="${afterSalesVo.lastOrderTime}"/>   
                                        </div>
                            </div>
                        </td>
                        <td>供应商等级</td>
                        <td>
                        	<c:if test="${afterSalesVo.grade eq 59}">核心供应商</c:if>
                        	<c:if test="${afterSalesVo.grade eq 60}">普通供应商</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">退货信息</div>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid6">序号</th>
                        <th class="wid18">产品名称</th>
                        <th >品牌</th>
                        <th class="wid8">型号</th>
                        <th class="wid8">单价</th>
                        <th class="wid8">数量</th>
                        <th class="wid5">单位</th>
                        <th class="wid5">直发</th>
                        <th>已收货数量</th>
                        <th>退货数量</th>
                        <th>退货方式</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty afterSalesVo.afterSalesGoodsList}">
                		<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
	                		<tr>
		                        <td>${sttaus.count }</td>
		                         <td class="text-left">
			                            <div class="customername pos_rel">
		                                       <span class="brand-color1 addtitle" style="float:none;"
                                                     tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}",
		                                       					"title":"产品信息"}'>${newSkuInfosMap[asg.sku].SHOW_NAME}</span><i class="iconbluemouth"></i>
		                                       <br>${asg.sku}<br>${newSkuInfosMap[asg.sku].MATERIAL_CODE}
                                            <c:set var="skuNo" value="${asg.sku}"></c:set>
                                            <%@ include file="../../common/new_sku_common_tip.jsp" %>
		                            </div>
		                        </td>
                                <td>${newSkuInfosMap[asg.sku].BRAND_NAME}</td>
                                <td>
                                        ${newSkuInfosMap[asg.sku].MODEL}
                                </td>
								<td>${asg.buyorderPrice}</td>
		                        <td>${asg.buyorderNum}</td>
                                <td>${newSkuInfosMap[asg.sku].UNIT_NAME}</td>
		                        <td>
		                        	<c:if test="${asg.buyorderDeliveryDirect eq 0}">否</c:if>
		                        	<c:if test="${asg.buyorderDeliveryDirect eq 1}">是</c:if>
		                        </td>
		                        <td>${asg.arrivalNum}</td>
		                        <td class="warning-color1">${asg.num}</td>
		                        <td class="warning-color1">
		                        	<c:if test="${asg.deliveryDirect eq 0}">普发</c:if>
		                        	<c:if test="${asg.deliveryDirect eq 1}">直发</c:if>
		                        </td>
		                    </tr>
	                	</c:forEach>
                     <tr>
                        <td colspan="11" class="allchosetr text-left">
                        	<c:set var="sum" value="0"></c:set>
                        	<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg">
                        		<c:set var="sum" value="${sum+asg.num}"></c:set>
                        	</c:forEach>
                            	 退货总件数:<span class="warning-color1 mr10">${sum}</span>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${empty afterSalesVo.afterSalesGoodsList}">
	                    <tr>
	                        <td colspan="11">暂无记录</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后服务费
                </div>
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
							<c:if test="${afterSalesVo.refund eq 1}">退至公司账户</c:if>
							<c:if test="${afterSalesVo.refund eq 2}">退至供应商余额</c:if>
                        </td>
                        <td class="warning-color1"><fmt:formatNumber type="number" value="${afterSalesVo.realRefundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                        <td class="warning-color1"><fmt:formatNumber type="number" value="${afterSalesVo.haveRefundAmount < 0 ? -afterSalesVo.haveRefundAmount : afterSalesVo.haveRefundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
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
                <div class="table-title nobor">
                   退票信息
                </div>
            </div>
            <table class="table">
                <thead>
                
                    <tr>
                        <th>发票号</th>
                        <th>发票金额</th>
                        <th>票种</th>
                        <th>退票状态</th>
                    </tr>
                </thead>
                <tbody>
	                <c:if test="${not empty afterSalesVo.afterSalesInvoiceVoList}">
	               		<c:forEach items="${afterSalesVo.afterSalesInvoiceVoList}" var="asi" >
		               		<tr>
		                        <td>${asi.invoiceNo}</td>
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
		                    </tr>
	               		</c:forEach>
	               	</c:if>
                    <c:if test="${empty afterSalesVo.afterSalesInvoiceVoList}">
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
                    沟通记录
                </div>
                 <c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.verifyStatus ne 0}">
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=2"}'>
	                   	 新增
	                </div>
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                         <th class="wid10">沟通时间</th>
                        <th class="">录音</th>
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
		                        <td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
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
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录",
		                        		"link":"<%= basePath %>/aftersales/order/editcommunicate.do?orderFlag=${afterSalesVo.atferSalesStatus }&flag=${afterSalesVo.status }&communicateRecordId=${communicateRecord.communicateRecordId}&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=2"}'>编辑</span>
		                        </c:if>
		                        </td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='12'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后过程
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.verifyStatus ne 0}">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"<%= basePath %>/aftersales/order/addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
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
		                        <c:if test="${afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"<%= basePath %>/aftersales/order/editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span>
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
             <div class="table-buttons">
                <form action="" method="post" id="myform">
             		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
             		<input type="hidden" name="orderId" value="${afterSalesVo.orderId}"/>
             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
             		<input type="hidden" name="formToken" value="${formToken}"/>
             		<c:if test="${(null!=taskInfo and null!=taskInfo.processInstanceId and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:choose>
							<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=2"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=2"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
                </form>
            </div>
        </div>
        
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
                     <c:if test="${not empty historicActivityInstance}">
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
                	<c:if test="${empty historicActivityInstance}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>
		
     </div>   
</body>

</html>