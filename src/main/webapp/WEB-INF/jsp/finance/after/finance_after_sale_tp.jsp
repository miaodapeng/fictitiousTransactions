<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="退票详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/finance_after_sales.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
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
					<td>
						<c:forEach var="user" items="${afterSalesVo.userList}">
							<c:if test="${user.userId eq afterSalesVo.creator}">${user.username}</c:if>
						</c:forEach>
					</td>
					<td>创建时间</td>
					<td><date:date value="${afterSalesVo.addTime}" /></td>
				</tr>
				<tr>
					<td>生效状态</td>
					<td><c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if> 
						<c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
					</td>
					<td>生效时间</td>
					<td><date:date value="${afterSalesVo.validTime}" /></td>
				</tr>
				<tr>
					<td>审核状态</td>
					<td>
						<c:if test="${afterSalesVo.status eq 0}">待确认</c:if> 
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

	<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}" />
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后申请</div>
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
					<td>${afterSalesVo.traderContactTelephone}</td>
					<td>手机</td>
					<td>${afterSalesVo.traderContactMobile}</td>
				</tr>
				<tr>
					<td>款项退还</td>
					<td>
						<c:if test="${afterSalesVo.refund eq 0}">无</c:if> 
						<c:if test="${afterSalesVo.refund eq 1}">退到客户余额</c:if> 
						<c:if test="${afterSalesVo.refund eq 2}">退给客户</c:if>
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
						<%@ include file="../../order/saleorder/view_afterSales_files.jsp"%>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">所属订单</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">订单号</td>
					<td>
						<div class="customername pos_rel">
							<span style="float:none" class="brand-color1 addtitle" tabTitle='{"num":"viewfinancesaleorder${afterSalesVo.orderId}","title":"订单信息",
                               	"link":"./finance/invoice/viewSaleorder.do?saleorderId=${afterSalesVo.orderId}"}'>
                               	${afterSalesVo.orderNo}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								付款状态：
								<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
								<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if>
								<br> 发货状态：
								<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
								<c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
								<c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if>
								<br> 开票状态：
								<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
								<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if>
								<br> 收货状态：
								<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
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
					<td>
						<c:forEach var="user" items="${afterSalesVo.userList}">
							<c:if test="${user.userId eq afterSalesVo.userId}">${user.username}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>订单状态</td>
					<td><c:if test="${afterSalesVo.saleorderStatus eq 0}">未生效</c:if>
						<c:if test="${afterSalesVo.saleorderStatus eq 1}">已生效</c:if></td>
					<td>生效时间</td>
					<td><date:date value="${afterSalesVo.saleorderValidTime}" /></td>
				</tr>
				<tr>
					<td>客户名称</td>
					<td>
						<div class="customername pos_rel">
							<span style="float:none" class="brand-color1 addtitle" tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"客户信息",
								"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}
							</span>
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow" style="display: none;">
								客户性质：
								<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
								<c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if>
								<br> 交易次数：${afterSalesVo.orderCount}<br>
								交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
								上次交易日期：<date:date value="${afterSalesVo.lastOrderTime}" format="yyyy.MM"/>
							</div>
						</div>
					</td>
					<td>客户等级</td>
					<td><c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
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
			<div class="table-title nobor">退票记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>发票代码</th>
					<th>开票日期</th>
					<th>发票金额</th>
					<th>票种</th>
					<th>寄送状态</th>
					<th>是否需寄回</th>
					<th>退票状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<!-- 根据退票状态，判断是否能开票 -->
				<c:set var="isOpenInvoice" value="0"></c:set>
				<c:if test="${not empty afterSalesVo.afterSalesInvoiceVoList}">
					<c:forEach items="${afterSalesVo.afterSalesInvoiceVoList}" var="asi">
						<tr>
							<td>${asi.invoiceNo}</td>
							<td>${asi.invoiceCode}</td>
							<td><date:date value="${asi.addTime}" format="yyyy.MM.dd"/></td>
							<td>${asi.amount}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${asi.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:choose>
									<c:when test="${(empty asi.expressId) or (asi.expressId eq 0)}">未寄送</c:when>
									<c:when test="${(not empty asi.expressId) and (asi.expressId ne 0)}">已寄送</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${asi.isSendInvoice eq 0}">否</c:if> 
								<c:if test="${asi.isSendInvoice eq 1}">是</c:if>
							</td>
							<td>
								<span style="color: red">
									<c:if test="${asi.status eq 0}">未退票</c:if> 
									<c:if test="${asi.status eq 1}">已退票</c:if>
								</span>
							</td>
							<td>
								<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 售后单進行中 -->
									<!-- isRefundInvoice：是否需要退票；；status：退票状态 -->
									<c:choose>
										<c:when test="${asi.isRefundInvoice eq 1}">
											<c:if test="${asi.status eq 0}">
												<c:if test="${asi.invoiceProperty eq 2}"><!-- 电子发票 -->
													<span class="forbid clcforbid" onclick="cancelEInvoicePush(${asi.invoiceId},${afterSalesVo.afterSalesId})">作废电子发票</span>
												</c:if>
												<c:if test="${asi.invoiceProperty eq 1}"><!-- 纸质发票 -->
													<span class="font-blue addtitle" tabtitle='{"num":"after_return_invoice_after${asi.afterSalesId}","title":"售后退票",
														"link":"${pageContext.request.contextPath}/finance/after/getAfterReturnInvoiceInfo.do?afterSalesId=${afterSalesVo.afterSalesId}&afterSalesInvoiceId=${asi.afterSalesInvoiceId}"}'>确认退票</span>
												</c:if>
											</c:if>
											<c:if test="${asi.status eq 1}">
												<!-- isRefundInvoice：是否需退票0否1是 	status：退票状态0未退票1已退票 -->
												<c:set var="isOpenInvoice" value="${isOpenInvoice+1}"></c:set>
											</c:if>
										</c:when>
										<c:otherwise>
											<span style="color: red">无需退票</span>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterSalesInvoiceVoList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='9'>查询无结果！</td>
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
					<th>票种</th>
					<th>红蓝字</th>
					<th>发票金额</th>
					<th>开票日期</th>
					<th>开票人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterReturnInvoiceVoList}">
					<c:forEach items="${afterSalesVo.afterReturnInvoiceVoList}" var="asi">
						<tr>
							<td>${asi.invoiceNo}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${asi.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:choose>
									<c:when test="${asi.colorType eq 1}">
										<c:choose>
											<c:when test="${asi.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												<span style="color: red">红字有效</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${asi.isEnable eq 0}">
												<span style="color: red">蓝字作废</span>
											</c:when>
											<c:otherwise>
												蓝字有效
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${asi.amount}</td>
							<td><date:date value="${asi.addTime}" format="yyyy.MM.dd"/></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq asi.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterReturnInvoiceVoList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='6'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- 注：售后订单中只开具退换货手续费的发票，其他发票放在销售订单中 -->
	<%-- <div class="parts">
		<div class="title-container">
			<div class="table-title nobor">发票记录</div>
			<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 進行中 -->
				<c:if test="${isOpenInvoice eq fn:length(afterSalesVo.afterSalesInvoiceVoList)}"><!-- 全部退票 -->
					<div class="title-click nobor addtitle" tabTitle='{"num":"at_add_invoice${afterSalesVo.afterSalesId}","title":"退票新增发票",
						"link":"${pageContext.request.contextPath}/finance/after/addAfterInvoiceTp.do?afterId=${afterSalesVo.afterSalesId}&relatedId=${afterSalesVo.orderId}&afterType=${afterSalesVo.type}"}'>
						新增发票
					</div>
				</c:if>
			</c:if>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>票种</th>
					<th>红蓝字</th>
					<th>发票金额</th>
					<th>操作人</th>
					<th>操作时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterOpenInvoiceList}">
					<c:forEach items="${afterSalesVo.afterOpenInvoiceList}" var="aoi">
						<tr>
							<td>${aoi.invoiceNo}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${aoi.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
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
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aoi.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aoi.addTime}" /></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterOpenInvoiceList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='6'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div> --%>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">退票材料</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>材料</th>
					<th>操作人</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterInvoiceAttachmentList}">
					<c:forEach items="${afterSalesVo.afterInvoiceAttachmentList}" var="aia">
						<tr>
							<td class="font-blue"><a href="http://${aia.domain}${aia.uri}" target="_blank">${aia.name}</a></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aia.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aia.addTime}" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">沟通记录</div>
		</div>
		<table class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid18">沟通时间</th>
					<th class="wid8">录音</th>
					<th class="wid8">联系人</th>
					<th class="wid10">联系方式</th>
					<th class="wid6">沟通方式</th>
					<th>沟通内容</th>
					<th>操作人</th>
					<th>下次联系日期</th>
					<th>下次沟通内容</th>
					<th>备注</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty communicateList}">
					<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
						<tr>
							<td><date:date value="${communicateRecord.begintime} " />~<date:date value="${communicateRecord.endtime}" format="HH:mm:ss" /></td>
							<td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
							<td>${communicateRecord.contactName}</td>
							<td>${communicateRecord.phone}</td>
							<td>${communicateRecord.communicateModeName}</td>
							<td>
								<ul class="communicatecontent ml0">
									<c:if test="${not empty communicateRecord.tag }">
										<c:forEach items="${communicateRecord.tag }" var="tag">
											<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
										</c:forEach>
									</c:if>
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
							<td><date:date value="${communicateRecord.addTime} " /></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty communicateList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='11'>查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后过程</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>沟通时间</th>
					<th>操作人</th>
					<th>售后内容</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
					<c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi">
						<tr>
							<td><date:date value="${asi.addTime} " /></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq asi.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td>${asi.content}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
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
</div>
<%@ include file="../../common/footer.jsp"%>
