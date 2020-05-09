<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售退款-售后" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%-- <script type="text/javascript" src='<%= basePath %>static/js/finance/after/finance_after_sales.js'></script> --%>
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
					<td>联系人</td>
					<td>${afterSalesVo.traderContactName}</td>
					<td>电话</td>
					<td>${afterSalesVo.traderContactTelephone}</td>
				</tr>
				<tr>
					<td>手机</td>
					<td>${afterSalesVo.traderContactMobile}</td>
					<td>订单已付金额</td>
                    <td><fmt:formatNumber type="number" value="${afterSalesVo.payAmount}" pattern="0.00" maxFractionDigits="2" /></td>
				</tr>
				<tr>
					<td>退款金额</td>
					<td class="warning-color1"><fmt:formatNumber type="number" value="${afterSalesVo.refundAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<td>交易名称</td>
					<td>${afterSalesVo.payee}</td>
				</tr>
				<tr>
					<td>交易主体</td>
					<td><c:if test="${afterSalesVo.traderSubject eq 1}">对公</c:if>
						<c:if test="${afterSalesVo.traderSubject eq 2}">对私</c:if></td>
					<td>开户银行</td>
					<td>${afterSalesVo.bank}</td>
				</tr>
				<tr>
					<td>开户行支付联行号</td>
					<td>${afterSalesVo.bankCode}</td>
					<td>银行账号</td>
					<td>${afterSalesVo.bankAccount}</td>
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
					<th class="wid6">申请金额</th>
					<th class="wid18">申请时间</th>
					<th class="wid12">申请人</th>
					<th class="wid8">交易名称</th>
					<th class="wid8">开户行及联行号</th>
					<th class="wid10">银行帐号</th>
					<th class="wid10">付款备注</th>
					<th class="wid5">审核状态</th>
					<th class="wid5">查看</th>
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
			<!-- 
				<div class="title-click nobor  pop-new-data" layerParams='{"width":"35%","height":"40%","title":"新增交易记录",
					"link":"${pageContext.request.contextPath}/finance/after/addFinanceAfterCapital.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增交易记录</div>
			 -->
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
	
</div>
<%@ include file="../../common/footer.jsp"%>
