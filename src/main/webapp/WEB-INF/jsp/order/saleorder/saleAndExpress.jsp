<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="交易及物流信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
<div class="content">
 	<div class="customer">
       <ul>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/viewNew.do?saleorderId=${saleorder.saleorderId }&companyId=${saleorder.companyId }">订单及基本信息</a>
            </li>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/order/saleorder/getSaleAndExpress.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">交易及物流信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getContactAndCheckInfo.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">沟通及审核信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getAttachmentAndInstance.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">合同及售后</a>
            </li>
        </ul>
  	</div>
   	<div class="parts">
   		<!-- 非南京总部公司 -->
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
        <!-- 南京总部展示开票申请 -->
        <c:if test="${curr_user.companyId == 1}">
        <div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					开票申请
                </div>
            </div>
            <table class="table">
            
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>是否提前开票</th>
                        <th>提前开票原因</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="list" items="${saleInvoiceApplyList}" varStatus="num">
                		<tr>
	                        <td>${list.creatorNm}
	                        	<c:if test="${list.creator eq 0}">
	                        	自动申请
	                        	</c:if>
	                        </td>
	                        <td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
	                         <td>
								<c:choose>
	                        		<c:when test="${list.isAdvance eq 0}">否</c:when>
	                        		<c:otherwise>是</c:otherwise>
	                        	</c:choose>
							</td>
							<td>${list.comments}</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.isAdvance eq 1}">
			                        	<c:choose>
			                        		<c:when test="${list.advanceValidStatus eq 0}">提前开票审核中</c:when>
			                        		<c:when test="${list.advanceValidStatus eq 1}">提前开票审核通过<br/>
			                        			<c:choose>
					                        		<c:when test="${list.yyValidStatus eq 0}">运营开票申请审核中</c:when>
					                        		<c:when test="${list.yyValidStatus eq 1}">运营开票申请审核通过<br/>
					                        			<c:choose>
							                        		<c:when test="${list.validStatus eq 0}">开票申请审核中</c:when>
							                        		<c:when test="${list.validStatus eq 1}">开票申请审核通过</c:when>
							                        		<c:when test="${list.validStatus eq 2}">开票申请审核不通过</c:when>
							                        	</c:choose>
					                        		</c:when>
					                        		<c:when test="${list.yyValidStatus eq 2}">运营开票申请审核不通过</c:when>
					                        	</c:choose>
			                        		</c:when>
			                        		<c:when test="${list.advanceValidStatus eq 2}">提前开票审核不通过</c:when>
			                        	</c:choose>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<c:choose>
			                        		<c:when test="${list.yyValidStatus eq 0}">运营开票申请审核中</c:when>
			                        		<c:when test="${list.yyValidStatus eq 1}">运营开票申请审核通过<br/>
			                        			<c:choose>
					                        		<c:when test="${list.validStatus eq 0}">开票申请审核中</c:when>
					                        		<c:when test="${list.validStatus eq 1}">开票申请审核通过</c:when>
					                        		<c:when test="${list.validStatus eq 2}">开票申请审核不通过</c:when>
					                        	</c:choose>
			                        		</c:when>
			                        		<c:when test="${list.yyValidStatus eq 2}">运营开票申请审核不通过</c:when>
			                        	</c:choose>
	                        		</c:otherwise>
	                        	</c:choose>
							</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.isAdvance eq 1}">
	                        			${list.advanceValidComments}
	                        			<c:if test="${list.yyValidStatus ne 0}">
	                        				<br/>${list.yyValidComments}
	                        			</c:if>
	                        			<c:if test="${list.validStatus ne 0}">
	                        				<br/>${list.validComments}
	                        			</c:if>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<c:if test="${list.yyValidStatus ne 0}">
	                        				<br/>${list.yyValidComments}
	                        			</c:if>
	                        			<c:if test="${list.validStatus ne 0}">
	                        				<br/>${list.validComments}
	                        			</c:if>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
	                    </tr>
                	</c:forEach>
                	<c:if test="${empty saleInvoiceApplyList}">
	                	<tr>
	           			<td colspan="6">暂无开票申请。</td>
	           			</tr>
	           		</c:if>
                </tbody>
            </table>
        </div>
        </c:if>
        <div class="parts content1">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					交易信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width: 100px">财务流水号</th>
                        <th style="width: 80px">业务类型</th>
                        <th style="width: 100px">交易金额</th>
                        <th style="width: 130px">交易时间</th>
                        <th style="width: 80px">交易主体</th>
                        <th style="width: 80px">交易方式</th>
                        <th>交易名称</th>
                        <th style="width: 200px">交易备注</th>
                        <th style="width: 100px">操作人</th>
                        <th style="width: 130px">操作时间</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty capitalBillList}">
                		<c:forEach items="${capitalBillList}" var="list" varStatus="">
	                    <tr>
	                        <td>${list.capitalBillNo}</td>
	                        <td>
	                        	<c:forEach var="typeList" items="${bussinessTypes}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq list.capitalBillDetail.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<c:if test="${list.traderTime != 0}">
	                        	<date:date value="${list.traderTime}" />
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:if test="${list.traderSubject == 1}">
	                        	对公
	                        	</c:if>
	                        	<c:if test="${list.traderSubject == 2}">
	                        	对私
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:forEach var="modeList" items="${traderModes}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>${list.payer}</td>
	                        <td class="text-left">${list.comments}</td>
	                        <td>${list.creatorName}</td>
	                        <td>
	                        	<c:if test="${list.addTime != 0}">
	                        	<date:date value="${list.addTime}" />
	                        	</c:if>
	                        </td>
	                    </tr>
	                    </c:forEach>
                    </c:if>
                	<c:if test="${empty capitalBillList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='10'>暂无交易信息</td>
					    </tr>
		        	</c:if>
		        	<tr>
                        <td colspan="10" style="text-align: left; background: #eaf2fd;">
                        	<!-- 判断客户是否触发账期 -->
                        	<c:set var="accountPeriodAmount" value="0"></c:set>
                        	<c:if test="${saleorderDataInfo['paymentAmount'] >= saleorder.accountPeriodAmount}">
                        		<c:set var="accountPeriodAmount" value="${saleorder.accountPeriodAmount}"></c:set>
                        	</c:if>
                        	订单金额：<fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                        	<%-- 客户已付款金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["receivedAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp; --%>
                        	<span style="color:red">未收金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"] - (saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"]  - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"])}' pattern="0.00" maxFractionDigits="2" /></span>
                        	&nbsp;=&nbsp;
                        	订单实际金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;-&nbsp;
                        	客户实付金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"] - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					发票信息
                </div>
                <c:set var="exitFor" value="0"></c:set>
				<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
					<c:if test="${list.invoiceProperty eq 2 and exitFor eq 0}">
						<c:set var="exitFor" value="1"></c:set>
						<%-- <span class="title-click nobor" onclick="sendInvoiceSms(${list.invoiceId},${saleorder.saleorderId})">推送</span> --%>
						<div class="title-click nobor pop-new-data" layerparams='{"width":"480px","height":"260px","title":"消息推送","link":"<%=basePath%>finance/invoice/invoiceSmsAndMailInit.do?relatedId=${saleorder.saleorderId}"}'>推送</div>
					</c:if>
				</c:forEach>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>发票号</th>
                        <th>票种</th>
                        <th style="width: 80px">红蓝字</th>
                        <th style="width: 80px">发票金额</th>
                        <th style="width: 130px">操作人</th>
                        <th style="width: 130px">操作时间</th>
                        <th>快递公司</th>
                        <th>快递单号</th>
                        <th style="width: 80px">快递状态</th>
                        <th style="width: 120px">操作</th>
                    </tr>
                </thead>
				<tbody>
                	<c:set var="invoiceAmount" value="0.00"></c:set><!-- 已开票总额 -->
                	<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
                		<tr>
	                        <td>
	                        	<c:if test="${empty list.invoiceNo}">
	                        		<span class="bt-small bg-light-blue bt-bg-style" onclick="batchDownEInvoice()">下载电子发票</span>
	                        	</c:if>
	                        	<c:if test="${not empty list.invoiceNo}">
	                        		${list.invoiceNo}
	                        		<c:if test="${list.invoiceProperty eq 2}">
										<font color='red'>[电]</font>
									</c:if>
	                        	</c:if>
	                        </td>
	                        <td>
								<c:forEach var="invoiceList" items="${invoiceTypes}" varStatus="status">
									<c:if test="${invoiceList.sysOptionDefinitionId eq list.invoiceType}">${invoiceList.title}</c:if>
								</c:forEach>
							</td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.colorType eq 1}">
										<c:choose>
											<c:when test="${list.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												红字有效
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${list.isEnable eq 0}">
												<span style="color: red">蓝字作废</span>
											</c:when>
											<c:otherwise>
												蓝字有效
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
	                        <td>
	                        	<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
	                        	<c:set var="invoiceAmount" value="${invoiceAmount + list.amount}"></c:set>
	                        </td>
	                        <td>${list.creatorName}</td>
	                        <td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
	                        <td>${list.express.logisticsCompanyName}</td>
	                        <td>${list.express.logisticsNo}</td>
	                        <td>
								<c:choose>
	                        		<c:when test="${list.express.arrivalStatus eq 0}">未收货</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 0}">部分收货</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 0}">全部收货</c:when>
	                        	</c:choose>
							</td>
	                        <td>
	                        	<c:if test="${list.invoiceProperty eq 2 and not empty list.invoiceNo}">
	                        		<a href= "${list.invoiceHref}" target="_blank">下载</a>
		                        	<%-- &nbsp;&nbsp;
		                        	<span class="edit-user clcforbid" onclick="sendInvoiceSms(${list.invoiceId})">推送</span> --%>
	                        	</c:if>
	                        </td>
	                    </tr>
                	</c:forEach>
                	<tr>
                        <td colspan="10" style="text-align: left; background: #eaf2fd;">
                        	已开票总额：<fmt:formatNumber type="number" value="${invoiceAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                        	<span style="color:red">未开票总额：<fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount'] - invoiceAmount}" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
       	<div class="parts">
            <div class="title-container title-container-green">
                <div class="table-title nobor">
                   	 物流信息
                </div>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="">快递公司</th>
                        <th class="">快递单号</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th>备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="wl">
                	<c:forEach var="express" items="${expressList}">
                     <tr>
                        <td>${express.logisticsName}</td>
                        <td>${express.logisticsNo}</td>
                        <td><date:date value ="${express.deliveryTime}" format="yyyy-MM-dd"/></td>
                        <td>
                        	<c:set var="amount" value="0.00"></c:set>
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
                        	</c:forEach>
                        	${amount}
                        </td>
                        <td class="text-left">
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                            	<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div>
                            </c:forEach>
                        </td>
                        <td>${express.logisticsComments}</td>
                        <td>${express.updaterUsername}</td>
                        <td>
                        	<c:if test="${express.arrivalStatus == 0}">
                        		未收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 1}">
                        		部分收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 2}">
                        		全部收货
                        	</c:if>
                        </td>
                        <td>
                          <div class="print-record">
								<form method="post" id="searchSh" action="<%= basePath %>warehouse/warehousesout/printShOutOrder.do">
									 <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
									 <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
									 <input type="hidden"  name="btype_sh" id="btype_sh" value=""/>
									 <input type="hidden"  name="expressId_xs" id="expressId_xs" value=""/>
									 <span class="bt-border-style border-blue" onclick="printSHOutOrder('${express.expressId}',496,${saleorder.saleorderId});" id="searchSpan">打印送货单</span>
								</form>
						   </div>
                        </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="9" class="allchosetr text-left">
                        		<!-- 总运费 -->
                        		<c:set var="allamount" value="0.00"></c:set>
                        		<!-- 总数量 -->
                        		<c:set var="allarrivalnum" value="0"></c:set>
	                        	<c:forEach var="express" items="${expressList}">
	                        		<c:set var="amount" value="0.00"></c:set>
	                        		<c:set var="arrivalnum" value="0"></c:set>
		                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
		                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
		                        		<c:set var="arrivalnum" value="${arrivalnum + expressDetails.num}"></c:set>
		                        	</c:forEach>
		                        	<c:set var="allamount" value="${allamount + amount}"></c:set>
		                        	<c:set var="allarrivalnum" value="${allarrivalnum + arrivalnum}"></c:set>
	                        	</c:forEach>
	                        	<c:set var="allnum" value="0"></c:set>
	                        	<c:forEach var="bgv" items="${saleorderGoodsList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        		<c:set var="allDeliveryNum" value="${allDeliveryNum + bgv.deliveryNum}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="mr10">${allamount}</span>商品总数：<span class="">${allnum}</span>
                            	 已发货总数：<span class="mr10">${allDeliveryNum}</span><span class="warning-color1">待发货数量：${allnum-allDeliveryNum}</span>
                        </td>
                    </tr>
                   </c:if>
                    <c:if test="${empty expressList}">
                     <tr>
                        <td colspan="9">暂无物流信息记录</td>
                    </tr>
                   </c:if>
                   
                </tbody>
            </table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">出库记录</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th>品牌/型号</th>
						<th class="wid10">物料编码</th>
						<th class="wid4">单位</th>
						<th>贝登条码</th>
						<th>厂商条码</th>
						<th>出库数量</th>
						<th>出库时间</th>
						<th class="wid12">操作人</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="listout" items="${warehouseOutList}"
						varStatus="num3">
						<tr>
							<td>
	                            <input type="checkbox" name="c_checknox" alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listout.warehouseGoodsOperateLogId}" onclick="canelAll(this,'c_checknox')">
	                        </td>
							<td>${num3.count}</td>
							<td class="text-left">
		                        <div >
		                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${listout.goodsName}</a>
		                        </div>
		                        <div>${listout.sku}</div>
		                    </td>
							<td>${listout.brandName}<br />${listout.model}</td>
							<td>${listout.materialCode}</td>
							<td>${listout.unitName}</td>
							<td>${ listout.barcode}</td>
							<td>${ listout.barcodeFactory}</td>
							<td>${0-listout.num}</td>
							<td><date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${ listout.opName}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty warehouseOutList}">
						<tr>
							<td colspan="11">暂无出库记录</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<c:if test="${not empty warehouseOutList}">
				<div class="table-style4">
					<div class="allchose">
					<input type="checkbox" name="all_c_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>
					</div>
					<div class="times">
						<span class="mr10">请选择批次</span>
						<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">
		                 <input type="checkbox" name="${wtlist}" id="out_checkbox" onclick="checkbarcode('${wtlist}', this.checked,'c_checknox');">
		                 <span class="mr20">${wtlist}</span>
		                </c:forEach>
					</div>
					<div class="print-record">
						<form method="post" id="searchc" action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">
							    <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
								<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
								<input type="hidden"  name="bussinessType" id="bussinessType" value="496"/>
							    <input type="hidden"  name="wdlIds" id="wdlIds" value=""/>
							    <input type="hidden"  name="type_f" id="type_f" value=""/>
							    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" id="searchSpan">打印出库单</span>
							    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" id="searchSpan">打印带效期出库单</span>
						</form>
					    
					</div>
				</div>
			</c:if>
		</div>
		 <c:if test="${saleorder.status == 2}">
		 	 <div class="tcenter mt-5 mb15">
		 	 	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
		 	 	<c:if test="${saleorder.lockedStatus eq 0 and saleorder.validStatus eq 1}">
					<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
					<c:if test="${saleorder.isOpenInvoice eq 1}">
						<button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data mr10" 
						layerParams='{"width":"600px","height":"300px","title":"申请开票",
						"link":"../../finance/invoice/openInvoiceApply.do?relatedId=${saleorder.saleorderId}&
						comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}'>申请开票</button>
					</c:if>
					<c:if test="${saleorder.isOpenInvoice eq 3}">
			            <button type="button" class="bt-bg-style bg-light-orange bt-small  pop-new-data mr10" 
			            layerParams='{"width":"600px","height":"350px","title":"申请提前开票",
			            "link":"../../finance/invoice/advanceOpenInvoiceApply.do?relatedId=${saleorder.saleorderId}&
			            comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&
			            invoiceType=${saleorder.invoiceType}"}' >申请提前开票</button>
					</c:if>
					<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
			            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
					</c:if>
				</c:if>
		 	 </div>
		 </c:if>
		 <c:if test="${saleorder.status != 3 &&  saleorder.status != 2}">
		 	<div class="tcenter mt-5 mb15">
		 		<c:if test="${saleorder.validStatus ne 0 }">
	        		<c:if test="${saleorder.lockedStatus eq 0}">
						<c:if test="${customer.amount ne '0.00'}">
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"余额支付","link":"./initBalancePayment.do?saleorderId=${saleorder.saleorderId}"}'>余额支付</button>
						</c:if>
						<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
						<c:if test="${saleorder.isOpenInvoice eq 1}">
				            <button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data mr10" layerParams='{"width":"600px","height":"300px","title":"申请开票","link":"../../finance/invoice/openInvoiceApply.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}'>申请开票</button>
						</c:if>
						<c:if test="${saleorder.isOpenInvoice eq 3}">
				            <button type="button" class="bt-bg-style bg-light-orange bt-small  pop-new-data mr10" layerParams='{"width":"600px","height":"350px","title":"申请提前开票","link":"../../finance/invoice/advanceOpenInvoiceApply.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}' >申请提前开票</button>
						</c:if>
						<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
				            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
						</c:if>
						<c:if test="${saleorder.deliveryDirect == 1}" >
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"confirm_arrival<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/saleorder/confirmArrivalInit.do?saleorderId=${saleorder.saleorderId}","title":"确认收货"}'>确认收货</button>
						</c:if>
					</c:if>
				</c:if>
        	</div>
		 </c:if>
		 
   </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>