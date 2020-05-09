<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="第三方安调" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%-- <script type="text/javascript" src='<%= basePath %>static/js/finance/after/finance_after_other.js'></script> --%>
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
						<c:forEach items="${afterSalesVo.userList}" var="user">
							<c:if test="${afterSalesVo.creator eq user.userId}">${user.username}</c:if>
						</c:forEach>
					</td>
					<td>创建时间</td>
					<td><date:date value="${afterSalesVo.addTime}" /></td>
				</tr>
				<tr>
					<td>生效状态</td>
					<td>
						<c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if> 
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
					<td class="wid20">客户名称</td>
					<td>${afterSalesVo.traderName}</td>
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
					<td>售后地区</td>
					<td colspan="3" class="text-left">${afterSalesVo.area}</td>
				</tr>
				<tr>
					<td>售后地址</td>
					<td colspan="3" class="text-left">${afterSalesVo.address}</td>
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
			<div class="table-title nobor">产品与工程师</div>
		</div>
		<table class="table  table-style10">
			<thead>
				<tr>
					<th class="wid3">序号</th>
					<th class="wid10">工程师</th>
					<th class="wid10">手机</th>
					<th class="wid10">服务时间</th>
					<th class="wid10">工程师酬金</th>
					<th class="wid10">上次通知时间</th>
					<th class="wid10">服务评分</th>
					<th class="wid10">技术评分</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterSalesInstallstionVoList}">
					<c:forEach items="${afterSalesVo.afterSalesInstallstionVoList}" var="asg" varStatus="sttaus">
						<tr>
							<td>${sttaus.count }</td>
							<td>${asg.name }</td>
							<td>${asg.mobile }</td>
							<td><date:date value="${asg.serviceTime}" /></td>
							<td>${asg.engineerAmount }</td>
							<td><date:date value="${asg.lastNoticeTime}" /></td>
							<td>${asg.serviceScore }</td>
							<td>${asg.skillScore }</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterSalesInstallstionVoList}">
					<tr>
						<td colspan="8">暂无数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">售后服务费</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td>安调费</td>
					<td><fmt:formatNumber type="number" value="${afterSalesVo.serviceAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<td>票种</td>
					<td>
						<c:forEach var="list" items="${invoiceTypeList}">
							<c:if test="${afterSalesVo.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
						</c:forEach>
						<c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                    	<c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
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
                    <td>税务登记号</td>
                    <td>${afterSalesVo.taxNum }</td>
                    <td>注册地址</td>
                    <td>${afterSalesVo.regAddress }</td>
                </tr>
                <tr>
                    <td>注册电话</td>
                    <td>${afterSalesVo.regTel }</td>
                    <td>开户银行</td>
                    <td>${afterSalesVo.bank }</td>
                </tr>
                <tr>
                	<td>银行账号</td>
                    <td>${afterSalesVo.bankAccount }</td>
                    <td>收票地区</td>
                    <td>${afterSalesVo.invoiceTraderArea }</td>
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
	</div>
	
	<div class="tcenter mb15 mt-5">
       		<c:if test="${(null!=taskInfoPay and null!=taskInfoPay.getProcessInstanceId() and null!=taskInfoPay.assignee) or !empty candidateUserMapPay[taskInfoPay.id]}">
				<c:if test="${endStatusPay ne '财务审核'}">
					<c:choose>
						<c:when test="${taskInfoPay.assignee == curr_user.username or candidateUserMapPay['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
	       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${endStatusPay eq '财务审核'}">
				<shiro:hasPermission name="/finance/capitalbill/showPaymentVerify.do">
					<c:choose>
						<c:when test="${taskInfoPay.assignee == curr_user.username or candidateUserMapPay['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"./addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&billType=2&payApplyId=${payApplyId}&taskId=${taskInfoPay.id}&pageType=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
	       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</shiro:hasPermission>
				</c:if>
			</c:if>
        </div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">付款申请</div>
			<%-- <span class="title-click nobor  pop-new-data" layerParams='{"width":"45%","height":"45%","title":"新增交易记录",
				"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}&payApplyId=16133&billType=2"}'>新增交易记录</span> --%>
		</div>
		<table class="table  table-style6">
			<thead>
				<tr>
					<th class="wid8">申请金额</th>
					<th>申请时间</th>
					<th>申请人</th>
					<th>交易名称</th>
					<th>开户行及联行号</th>
					<th>银行帐号</th>
					<th>付款备注</th>
					<th class="wid8">审核状态</th>
					<th class="wid8">查看</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterPayApplyList}">
					<c:forEach items="${afterSalesVo.afterPayApplyList}" var="apal" varStatus="num_index">
						<tr>
							<td><fmt:formatNumber type="number" value="${apal.amount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td><date:date value="${apal.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq apal.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td>${apal.traderName}</td>
							<td>
								${apal.bank}<br/>${apal.bankCode}
							</td>
							<td>${apal.bankAccount}</td>
							<td>${apal.comments}</td>
							<td>
								<c:choose>
									<c:when test="${apal.validStatus eq 0}">待审核</c:when>
									<c:when test="${apal.validStatus eq 1}">通过</c:when>
									<c:when test="${apal.validStatus eq 2}">不通过</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
							<td>
		                        	<div class="caozuo">
		                        	<span class="caozuo-blue pop-new-data" layerparams='{"width":"50%","height":"30%","title":"付款申请审核信息","link":"<%=basePath%>finance/after/paymentVerify.do?payApplyId=${apal.payApplyId}"}'>查看</span>
									</div>
                        		</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterPayApplyList}">
					<tr>
						<td colspan="9">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">交易记录</div>
			<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 進行中 -->
				<c:if test="${afterSalesVo.capitalTotalAmount < afterSalesVo.serviceAmount}"><!-- 已收金额小于售后服务费 -->
					<div class="title-click nobor  pop-new-data" layerparams='{"width":"45%","height":"50%","title":"新增交易记录",
						"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增交易记录</div>
				</c:if>
			</c:if>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid18">记账编号</th>
					<th class="wid8">业务类型</th>
					<th class="wid8">交易金额</th>
					<th class="wid13">交易时间</th>
					<th class="wid8">交易主体</th>
					<th class="wid8">交易方式</th>
					<th class="wid18">交易名称</th>
					<th class="wid14">交易备注</th>
					<th class="wid8">操作人</th>
					<th class="wid13">操作时间</th>
					<th>电子回执单</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterCapitalBillList}">
					<c:forEach items="${afterSalesVo.afterCapitalBillList}" var="acb">
						<tr>
							<td>${acb.capitalBillNo}</td>
							<td>
								<c:forEach var="typeList" items="${bussinessTypes}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq acb.capitalBillDetail.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" />
							</td>
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
								<c:forEach var="modeList" items="${traderModes}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq acb.traderMode}">${modeList.title}</c:if>
								</c:forEach>
							</td>
							<td>${acb.payer}</td>
							<td class="text-left">${acb.comments}</td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq acb.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
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
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">开票信息</div>
			<c:if test="${afterSalesVo.atferSalesStatus eq 1}"><!-- 進行中 -->
				<c:set var="invoice_totle_amount" value="0"></c:set>
				<c:if test="${not empty afterSalesVo.afterOpenInvoiceList}">
					<c:forEach items="${afterSalesVo.afterOpenInvoiceList}" var="aoi">
						<c:set var="invoice_totle_amount" value="${invoice_totle_amount + aoi.amount}"></c:set>
					</c:forEach>
				</c:if>
				<!-- 售后详情表服务费和售后产品表特殊产品费用相同 -->
				<c:if test="${(invoice_totle_amount eq 0) or (invoice_totle_amount < afterSalesVo.serviceAmount)}">
					<c:if test="${((afterSalesVo.isCanApplyInvoice eq 1 and afterSalesVo.companyId eq 1) or (invoiceApply.validStatus eq 1 and afterSalesVo.companyId ne 1)) and afterSalesVo.eFlag eq 'cw'}">
						<div class="title-click nobor addtitle" tabTitle='{"num":"at_add_invoice${afterSalesVo.afterSalesId}","title":"安调新增发票",
							"link":"${pageContext.request.contextPath}/finance/after/addAfterInvoiceAt.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>
							新增发票
						</div>
					</c:if>
				</c:if>
			</c:if>
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
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterOpenInvoiceList}">
					<c:forEach items="${afterSalesVo.afterOpenInvoiceList}" var="aoi">
						<tr>
							<td>${aoi.invoiceNo}</td>
							<td>${aoi.invoiceCode}</td>
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
												<span style="color: blue;">蓝字有效</span>
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
							<!--快递公司名 -->
							<td>${aoi.logisticsName}</td>
							<!--快递单号 -->
							<td>${aoi.logisticsNo}</td>
							<!--快递状态 -->
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
							<!--物流备注 -->
							<td>${aoi.logisticsComments}</td>
							<!--操作 -->
							<td>
	                        	<c:choose>
	                        		<c:when test="${empty aoi.expressId}">
	                        				<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"../../finance/invoice/sendSaleInvoice.do?invoiceId=${aoi.invoiceId}"}'>寄送发票</a>
	                        				<%-- <c:choose>
												<c:when test="${list.type eq 504}"><!-- 售后发票 -->
													<!-- 售后单2：已完结，3：已关闭 -->
													<c:if test="${(list.atferStatus eq 2) or (list.atferStatus eq 3)}">
							                        	<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
												</c:otherwise>
											</c:choose> --%>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<!-- 未签收,则可以编辑 -->
	                        			<c:choose>
	                        				<c:when test="${aoi.arrivalStatus != 2}">
	                        					<a class="pop-new-data" layerParams='{"width":"570px","height":"300px","link":"../../finance/invoice/editExpressView.do?expressId=${aoi.expressId}&invoiceId=${aoi.invoiceId}&invoiceNo=${aoi.invoiceNo}","title":" 编辑快递"}'>编辑</a>
	                        				</c:when>
	                        				<c:otherwise>
	                        					已寄送
	                        				</c:otherwise>
	                        			</c:choose>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterOpenInvoiceList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='12'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">录票记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>发票号</th>
					<th>发票金额</th>
					<th>票种</th>
					<th>红蓝字</th>
					<th>录票人员</th>
					<th>申请日期</th>
					<th>审核日期</th>
					<th>审核人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty afterSalesVo.afterInInvoiceList}">
					<c:forEach items="${afterSalesVo.afterInInvoiceList}" var="aiil">
						<tr>
							<td>${aiil.invoiceNo}</td>
							<td>${aiil.amount}</td>
							<td>
								<c:forEach var="list" items="${invoiceTypeList}">
									<c:if test="${aiil.invoiceType eq list.sysOptionDefinitionId}">${list.title}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:choose>
									<c:when test="${aiil.colorType eq 1}">
										<c:choose>
											<c:when test="${aiil.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												<span style="color: red">红字有效</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${aiil.isEnable eq 0}">
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
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aiil.creator}">${user.username}</c:if>
								</c:forEach>
							</td>
							<td><date:date value="${aiil.addTime}" format="yyyy.MM.dd"/></td>
							<td><date:date value="${aiil.validTime}" format="yyyy.MM.dd"/></td>
							<td>
								<c:forEach var="user" items="${afterSalesVo.userList}">
									<c:if test="${user.userId eq aiil.validUserId}">${user.username}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty afterSalesVo.afterInInvoiceList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='8'>查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<c:if test="${invoiceApply != null && companyId != 1}">
	<div class="tcenter mb15 mt-5">
        		<c:choose>
				<c:when test="${invoiceApply.validStatus == null || invoiceApply.validStatus != 1}">
				<c:if test="${(null!=taskInfoInvoice and null!=taskInfoInvoice.getProcessInstanceId() and null!=taskInfoInvoice.assignee) or !empty candidateUserMapInvoice[taskInfoInvoice.id]}">
					<c:choose>
						<c:when test="${taskInfoInvoice.assignee == curr_user.username or candidateUserMapInvoice['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"../../finance/invoice/complement.do?taskId=${taskInfoInvoice.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"../../finance/invoice/complement.do?taskId=${taskInfoInvoice.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
        						<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:when>
			</c:choose>
    </div>
    </c:if>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">沟通记录</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
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
								<c:forEach items="${afterSalesVo.userList}" var="user">
									<c:if test="${asi.creator eq user.userId}">${user.username}</c:if>
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
<%@ include file="../../common/footer.jsp"%>
