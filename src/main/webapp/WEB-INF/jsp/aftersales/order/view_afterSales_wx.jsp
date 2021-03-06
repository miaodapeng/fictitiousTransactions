<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-维修" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/aftersales/order/viewAfterSalesDetail.do?afterSalesId='+$("#afterSalesId").val();
		if($(window.frameElement).attr('src').indexOf("viewAfterSalesDetail")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
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
		<input type="hidden" name="afterSalesId" id="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
		 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   售后申请
                </div>
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
                    	<td>手机</td>
                        <td>
                        ${afterSalesVo.traderContactMobile}
                        <c:if test="${not empty afterSalesVo.traderContactMobile}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactMobile}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                    	<td>电话</td>
                        <td>${afterSalesVo.traderContactTelephone}
                        <c:if test="${not empty afterSalesVo.traderContactTelephone}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactTelephone}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                    </tr>
                     <tr>
                        <td>售后地区</td>
                        <td>${afterSalesVo.area} </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>售后地址</td>
                        <td colspan="3">${afterSalesVo.address}</td>
                      
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
                <div class="table-title nobor">产品与工程师</div>
                <div class="title-click nobor addtitle" tabTitle='{"num":"viewafterSalesId<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"新增",
	                "link":"<%= basePath %>/aftersales/order/addEngineerPage.do?afterSalesId=${afterSalesVo.afterSalesId}&&areaId=${afterSalesVo.areaId}&&subjectType=${afterSalesVo.subjectType}"}'>新增</div>
            </div>
            <table class="table  table-style10">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th class="wid15">工程师</th>
                        <th class="wid15">手机</th>
                        <th class="wid15">服务时间</th>
                        <th class="wid10">工程师酬金</th>
                        <th class="wid10">上次通知时间</th>
                        <th class="wid10">服务评分</th>
                        <th class="wid10">技术评分</th>
                        <th class="wid8">操作</th>
                    </tr>
                </thead>
                <tbody>
	                <c:if test="${not empty afterSalesVo.afterSalesInstallstionVoList}">
		                <c:forEach items="${afterSalesVo.afterSalesInstallstionVoList}" var="asg" varStatus="sttaus">
		                    <tr>
		                        <td>${sttaus.count }</td>
		                        <td>${asg.name }</td>
		                        <td>${asg.mobile }</td>
		                        <td><date:date value ="${asg.serviceTime}" format="yyyy-MM-dd"/></td>
		                        <td>${asg.engineerAmount }</td>
		                        <td><date:date value ="${asg.lastNoticeTime}"/></td>
		                        <td>${asg.serviceScore }</td>
		                        <td>${asg.skillScore }</td>
		                        <td class="caozuo">
			                     	<span class="border-blue addtitle" tabTitle='{"num":"viewafterSalesId<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
				                "link":"<%= basePath %>/aftersales/order/editEngineerPage.do?afterSalesInstallstionId=${asg.afterSalesInstallstionId}&&afterSalesId=${afterSalesVo.afterSalesId}&&areaId=${afterSalesVo.areaId}&&subjectType=${afterSalesVo.subjectType}","title":"编辑产品与工程师"}'>编辑 </span>
				                	<span class="edit-user pop-new-data"
										layerParams='{"width":"500px","height":"250px","title":"编辑评价","link":"<%= basePath %>/aftersales/engineer/editscore.do?afterSalesInstallstionId=${asg.afterSalesInstallstionId}"}'>评价</span>
			                     </td>
	                   	 	</tr>
	                    </c:forEach> 
                    </c:if>
                    <c:if test="${empty afterSalesVo.afterSalesInstallstionVoList}">
                    	<tr>
                    		<td colspan="9">暂无记录！</td>
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
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                 	<div class="title-click nobor  pop-new-data" layerParams='{"width":"600px","height":"400px","title":"编辑维修信息",
		                  "link":"<%= basePath %>aftersales/order/editInstallstionPage.do?afterSalesDetailId=${afterSalesVo.afterSalesDetailId}&&afterSalesId=${afterSalesVo.afterSalesId}&&serviceAmount=${afterSalesVo.serviceAmount}&&isSendInvoice=${afterSalesVo.isSendInvoice}&&invoiceType=${afterSalesVo.invoiceType}&flag=wx"}'>编辑</div>
		        </c:if>
            </div>
            <table class="table">
                <tbody>
                	<tr>
                        <td>维修费</td>
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
        </div>
        
        <c:if test="${afterSalesVo.status eq 2}">
        	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	付款申请
                </div>
            </div>
            <table class="table">
            	<thead>
                   <tr>
					<th class="wid6">申请金额</th>
					<th class="wid8">申请时间</th>
					<th class="wid12">申请人</th>
					<th class="wid8">交易名称</th>
					<th class="wid15">开户行及联行号</th>
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
							<td>${apal.creatorName}</td>
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
								<td>${acb.amount}</td>
								<td><date:date value="${acb.traderTime}" /></td>
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
								<td>
									${acb.creatorName}
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
					<c:if test="${empty afterSalesVo.afterCapitalBillList}">
						<tr>
							<td colspan="11">暂无收款交易信息</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
        
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">开票信息</div>
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
													<span style="color: blue">蓝字作废</span>
												</c:when>
												<c:otherwise>
													<span style="color: blue">蓝字有效</span>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${aoi.amount}</td>
								<td>${aoi.creatorName}</td>
								<td><date:date value="${aoi.addTime}" /></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty afterSalesVo.afterOpenInvoiceList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan='6'>暂无记录！</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			</div>
        </c:if>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    沟通记录
                </div>
                 <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>
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
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录",
		                        		"link":"<%= basePath %>/aftersales/order/editcommunicate.do?orderFlag=${afterSalesVo.atferSalesStatus }&flag=${afterSalesVo.status }&communicateRecordId=${communicateRecord.communicateRecordId}&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>编辑</span>
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
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"<%= basePath %>aftersales/order/addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
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
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"<%= basePath %>aftersales/order/editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span></c:if>
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
             <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                <form action="" method="post" id="myform">
             		<input type="hidden" name="afterSalesId" id="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
             		<input type="hidden" name="formToken" value="${formToken}"/>
             		<c:if test="${afterSalesVo.status eq 0 || afterSalesVo.status eq 3}">
             			<input type="hidden" name="taskId" value="${taskInfo.id == null ?0: taskInfo.id}"/>
             			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="applyAudit();">申请审核</button>
             			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="editAfterSales(3);">编辑</button>
             		</c:if>
             		<c:if test="${afterSalesVo.status ne 1 && afterSalesVo.closeStatus eq 1}">
	                	<button type="button" class="bt-bg-style bg-light-orange bt-small" onclick="colse();">关闭订单</button>
	                </c:if>
                </form></c:if>
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
</body>

</html>