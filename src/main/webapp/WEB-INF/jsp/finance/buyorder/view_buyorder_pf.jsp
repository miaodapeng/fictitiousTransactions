<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购订单详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/buyorder/view_buyorder_pf.js?rnd=<%=Math.random()%>'></script>
	<form action="" method="post" id="myform">
	<div class="main-container">
		<div class="parts">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">基本信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">订单号</td>
						<td>
						<div class="customername pos_rel">
							${buyorderVo.buyorderNo}
							<i class="iconbluemouth"></i>
							<div class="pos_abs customernameshow mouthControlPos">
			                                      创建者：${buyorderVo.createName} <br>
			                                      创建时间：<date:date value="${buyorderVo.addTime}" /><br>
			                                      生效时间：<date:date value="${buyorderVo.validTime}" />
                            </div>
                        </div>
						</td>
						<td class="table-smaller">订单状态</td>
						<td>
							<c:choose>
								<c:when test="${buyorderVo.status eq 0}">
									<span >待确认</span>
								</c:when>
								<c:when test="${buyorderVo.status eq 1}">
									<span >进行中</span>
								</c:when>
								<c:when test="${buyorderVo.status eq 2}">
									<span >已完结</span>
								</c:when>
								<c:otherwise>
									<span >已关闭</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>部门</td>
						<td>${buyorderVo.buyDepartmentName}</td>
						<td>归属</td>
						<td>${buyorderVo.homePurchasing}</td>
					</tr>
					<tr>
						<td>是否直发</td>
						<td><c:if test="${buyorderVo.deliveryDirect eq 0}">普发</c:if>
							<c:if test="${buyorderVo.deliveryDirect eq 1}">直发</c:if></td>
						<td>审核状态</td>
						<td>
							<c:choose>
								<c:when test="${buyorderVo.verifyStatus == null}">待审核</c:when>
								<c:when test="${buyorderVo.verifyStatus eq 0}">审核中</c:when>
								<c:when test="${buyorderVo.verifyStatus eq 1}">审核通过</c:when>
								<c:when test="${buyorderVo.verifyStatus eq 2}">审核不通过</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>生效状态</td>
						<td><c:choose>
								<c:when test="${buyorderVo.validStatus eq 1}">
									<span >已生效</span>
								</c:when>
								<c:otherwise>
									<span >未生效</span>
								</c:otherwise>
							</c:choose></td>
						<td>付款状态</td>
						<td>
							<c:if test="${buyorderVo.paymentStatus eq 0}">未付款</c:if>
							<c:if test="${buyorderVo.paymentStatus eq 1}">部分付款</c:if>
							<c:if test="${buyorderVo.paymentStatus eq 2}">全部付款</c:if>
						</td>
					</tr>
					<tr>
						<td>到货状态</td>
						<td>
							<c:if test="${buyorderVo.arrivalStatus eq 0}">未收货</c:if>
							<c:if test="${buyorderVo.arrivalStatus eq 1}">部分收货</c:if>
							<c:if test="${buyorderVo.arrivalStatus eq 2}">全部收货</c:if>
						</td>
						<td>收票状态</td>
						<td>
							<c:if test="${buyorderVo.invoiceStatus eq 0}">未收票</c:if>
							<c:if test="${buyorderVo.invoiceStatus eq 1}">部分收票</c:if>
							<c:if test="${buyorderVo.invoiceStatus eq 2}">全部收票</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<input type="hidden" name="uri" value="${uri}"/>
		<input type="hidden" name="buyorderId" value="${buyorderVo.buyorderId}"/>
		<input type="hidden" name="status" value="${buyorderVo.status}"/>
		<input type="hidden" name="deliveryDirect" value="${buyorderVo.deliveryDirect}"/>
		<input type="hidden" name="validStatus" value="${buyorderVo.validStatus}">
		<input type="hidden" name="lockedStatus" value="${buyorderVo.lockedStatus}">
		<input type="hidden" name="userId" value="${buyorderVo.userId}">
		<input type="hidden" name="formToken" value="${formToken}"/>
		<input type="hidden" id="user_id" name="user_id" value="${curr_user.userId}">
		<div class="parts content1">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">供应商信息</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">供应商名称</td>
						<td>
							<div class="customername pos_rel">
								<span class="font-blue addtitle" 
								tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/supplier/baseinfo.do?traderId=${buyorderVo.traderId}","title":"供应商信息"}'>${buyorderVo.traderName}</span>
							
								<i class="iconbluemouth"></i><div class="pos_abs customernameshow mouthControlPos">
					                                    交易次数：${traderBaseInfo.orderCount} <br> 
					                                    供应商等级：
                                    <c:forEach var="gradeLi" items="${gradeList}" varStatus="">
										<c:if test="${gradeLi.sysOptionDefinitionId eq traderBaseInfo.grade}">${gradeLi.title}</c:if>
									</c:forEach>
                                </div>
							</div>
						</td>
						<td class="table-smaller"></td>
						<td></td>
					</tr>
					<tr>
						<td>供应商备注</td>
						<td colspan="3">${buyorderVo.traderComments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
<!-- ----------------------------------产品信息 ------------------------------------- -->
		<div class='parts'>
		 	<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
	            <table class="table table-style7">
	                <thead>
	                    <tr>
	                    	<th class="wid4">序号</th>
	                    	<th class="wid8">订货号</th>
	                        <th class="wid10">产品名称</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid9">物料编码</th>
							<th class="wid6">单位</th>
							<th class="wid11">可用库存 /库存量</th>
							<th class="wid8">采购数量</th>
							<th class="wid6">单价</th>
							<th class="wid6">总额</th>
							<th class="wid8">到货数量</th>
							<th>采购备注</th>
							<th>发票号</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                    <input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
	                    	<td>${num.count}</td>
	                    	<td>${bgv.sku}</td>
	                    	<td>
			                    <span class="font-blue cursor-pointer addtitle" 
			                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}</span>
	                        </td>
	                        <td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MODEL}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MATERIAL_CODE}</td>
	                        <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
	                        <td>${bgv.canUseGoodsStock}/${bgv.goodsStock}</td>
	                        <td>
	                        	<div class="customername pos_rel">
		                        	<span alt="${bgv.goodsId}">
		                        		${bgv.num - bgv.afterReturnNum}
		                        		<c:if test="${bgv.afterReturnNum > 0}">
		                        		<i class="iconredsigh ml4 contorlIcon"></i>
		                        		</c:if>
		                        	</span>
		                        	<c:if test="${bgv.afterReturnNum > 0}">
		                        		<div class="pos_abs customernameshow">原值：${bgv.num}</div>
		                        	</c:if>
	                        	</div>
	                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num}"/>
	                        </td>
	                        <td><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}"><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" />${bgv.oneBuyorderGoodsAmount}</span>
	                        </td>
	                        <td>${bgv.arrivalNum}</td>
	                        <td>${bgv.insideComments}</td>
	                        <td>${bgv.invoiceNoStr}</td>
	                    </tr>
	                    <tr>
	                        <td colspan="14" class="table-container ">
	                            <table class="table">
	                                <thead>
	                                    <tr>
	                                        <th class="wid8">关联单号</th>
	                                        <th class="wid10 ">申请人</th>
	                                        <th class="wid15 ">数量</th>
	                                        <th class="wid11 ">销售价</th>
	                                        <th class="wid10">销售货期</th>
	                                        <th class="wid12">内部备注</th>
	                                        <th class="wid12">产品备注</th>
	                                        <th class="wid12 ">终端客户名称</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach var="saleorderGoods" items="${bgv.saleorderGoodsVoList}" varStatus="status">
		                                    <tr>
												<td>
													<c:if test="${saleorderGoods.orderType ne 2}">
													<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder${saleorderGoods.saleorderId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${saleorderGoods.saleorderId}","title":"订单信息"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
													<c:if test="${saleorderGoods.orderType eq 2}">
													<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"order_saleorder_viewbhsaleorder${saleorderGoods.saleorderId}","link":"./order/saleorder/viewBhSaleorder.do?saleorderId=${saleorderGoods.saleorderId}","title":"备货信息"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
												</td>
												<td>${saleorderGoods.applicantName}</td>
												<td id="${num.count}_${saleorderGoods.saleorderId }_${status.index}">
													/
												</td>
												<td><fmt:formatNumber type="number" value="${saleorderGoods.price}" pattern="0.00" maxFractionDigits="2" /></td>
												<td>${saleorderGoods.deliveryCycle}</td>
												<td><span class="warning-color1">${saleorderGoods.insideComments}</span></td>
												<td>${saleorderGoods.goodsComments}</td>
												<td>${saleorderGoods.terminalTraderName}</td>
		                                    </tr>
	                                    </c:forEach>
	                                </tbody>
	                            </table>
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	        </c:forEach>
	         <div class="tablelastline">
               	 总件数<span class="warning-color1" id="zSum">${buyorderVo.buyorderSum}</span>，总金额<span class="warning-color1" id="zMoney">${buyorderVo.buyorderAmount}</span>
            </div>
        </div>

		<div class="parts content1">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">付款计划</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th>计划</th>
						<th>计划内容</th>
						<th>支付金额</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${buyorderVo.paymentType eq 419}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${buyorderVo.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq buyorderVo.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if
						test="${buyorderVo.paymentType ne 419 and buyorderVo.paymentType ne 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${buyorderVo.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq buyorderVo.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td>${buyorderVo.accountPeriodAmount}</td>
							<td>收货且收票后${buyorderVo.periodDay}天内支付</td>
						</tr>
					</c:if>
					<c:if test="${buyorderVo.paymentType eq 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${buyorderVo.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td>${buyorderVo.accountPeriodAmount}</td>
							<td>收货且收票后${buyorderVo.periodDay}天内支付</td>
						</tr>
						<tr>
							<td>第三期</td>
							<td>尾款</td>
							<td>${buyorderVo.retainageAmount}</td>
							<td>到货后${buyorderVo.retainageAmountMonth}个月内支付</td>
						</tr>
					</c:if>
					<tr style="background: #eaf2fd;">
						<td>付款备注</td>
						<td colspan="3" class="text-left">
							${buyorderVo.paymentComments}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="parts content1">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">其他信息</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td>补充条款</td>
						<td colspan="3">${buyorderVo.additionalClause}</td>
					</tr>
					<tr>
						<td>内部备注</td>
						<td colspan="3">${buyorderVo.comments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
		
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    付款申请
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>申请金额</th>
                        <th>申请时间</th>
                        <th>申请人</th>
                        <th>交易名称</th>
                        <th>开户行及联行号</th>
                        <th>银行帐号</th>
                        <th>使用余额</th>
                        <th>付款备注</th>
                        <th>审核状态</th>
                        <th>审核时间</th>
                        <th>审核人</th>
                        <th>查看</th>
                        <!-- <th>审核不通过原因</th>
                        <th>操作</th> -->
                       
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${not empty payApplyList}">
                		<c:forEach items="${payApplyList}" var="list" varStatus="">
                			<tr>
		                        <td>${list.amount }</td>
		                        <td><date:date value="${list.addTime}" /></td>
		                        <td>${list.creatorName}</td>
		                        <td>${list.traderName}</td>
		                        <td>${list.bank}<br>${list.bankCode}</td>
		                        <td>${list.bankAccount}</td>
		                        <td>
		                        	<c:choose>
										<c:when test="${list.traderMode eq 521}">
											不使用
										</c:when>
										<c:when test="${list.traderMode eq 528}">
											使用
										</c:when>
									</c:choose>
		                        </td>
		                        <td>${list.comments}</td>
		                        <td>
		                        	<c:choose>
										<c:when test="${list.validStatus eq 0}">
											待审核
										</c:when>
										<c:when test="${list.validStatus eq 1}">
											通过
										</c:when>
										<c:when test="${list.validStatus eq 2}">
											<span class="font-red">审核不通过</span>
										</c:when>
									</c:choose>
		                        </td>
		                        <td>
		                        	<c:if test="${list.validStatus != 0}">
		                        	<date:date value="${list.validTime}" />
		                        	</c:if>
		                        </td>
		                        <td>
		                        	<c:if test="${list.validStatus != 0}">
		                        	${list.updaterName}
		                        	</c:if>
		                        </td>
		                        <td>
		                        	<div class="caozuo">
		                        	<span class="caozuo-blue pop-new-data" layerparams='{"width":"50%","height":"30%","title":"付款申请审核信息","link":"<%=basePath%>finance/after/paymentVerify.do?payApplyId=${list.payApplyId}"}'>查看</span>
									</div>
                        		</td>
		                        <!-- <td>${list.validComments}</td>
		                        <td>
			                        <c:if test="${list.validStatus == 0 && buyorderVo.status != 3}">
			                        	<c:set var="payApplyId" value="${list.payApplyId}"></c:set>
				                        <span class="font-blue pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"./initPayApplyPass.do?id=${list.payApplyId}"}'>通过</span> 
				                        <span class="font-red pop-new-data" layerParams='{"width":"600px","height":"250px","title":"确认审核","link":"./initPayApplyNoPass.do?id=${list.payApplyId}"}'>不通过</span>
			                    	</c:if>
		                    	</td>
		                    	 -->
		                        
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty payApplyList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='12'>暂无付款申请</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
		        
         
         <c:if test="${(null!=taskInfoPay and null!=taskInfoPay.getProcessInstanceId() and null!=taskInfoPay.assignee) or !empty candidateUserMapPay[taskInfoPay.id]}">
				<div class="table-buttons">
				<c:if test="${endStatusPay ne '财务审核'}"> 
				
					<c:choose>
						<c:when test="${taskInfoPay.assignee == curr_user.username or candidateUserMapPay['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${taskInfoPay.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
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
						<button id="sub" type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"./initPayApplyPass.do?id=${payApplyId}&taskId=${taskInfoPay.id}"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
	       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
					</shiro:hasPermission>
					
				</c:if>
				</div>
			</c:if>
        
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
                    <c:forEach var="hi" items="${historicActivityInstancePay}" varStatus="status">
                    <c:if test="${not empty  hi.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hi.activityType == 'startEvent'}"> 
							${startUserPay}
							</c:when>
							<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstancePay.size() == status.count}">
									${verifyUsersPay}
								</c:if>
								<c:if test="${historicActivityInstancePay.size() != status.count}">
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
                        <td class="font-red">${commentMapPay[hi.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <c:if test="${null==historicActivityInstancePay or empty historicActivityInstancePay}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">暂无审核记录。</div>
        	</c:if>
        	<div class="clear"></div>
        </div>
        <div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
                    交易信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
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
                    <c:if test="${not empty capitalBillList}">
                		<c:forEach items="${capitalBillList}" var="list" varStatus="">
                			<tr>
		                        <td>${list.capitalBillNo}</td>
		                        <td>
		                        	<c:forEach var="typeList" items="${bussinessTypeList}" varStatus="">
										<c:if test="${typeList.sysOptionDefinitionId eq list.capitalBillDetail.bussinessType}">${typeList.title}</c:if>
									</c:forEach>
		                        </td>
		                        <td> <fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
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
		                        <td>${list.payee}</td>
		                        <td class="text-left">${list.comments}</td>
		                        <td>${list.creatorName}</td>
		                        <td>
		                        	<c:if test="${list.addTime != 0}">
		                        	<date:date value="${list.addTime}" />
		                        	</c:if>
		                        </td>
		                        <td>
		                        	<c:if test="${(list.traderType == 2 || list.traderType == 5) && list.bankBillId != 0}">
			                        	<div class="caozuo">
											<span class="caozuo-blue addtitle"   tabTitle='{"num":"credentials${list.bankBillId}", "link":"<%=basePath%>finance/capitalbill/credentials.do?bankBillId=${list.bankBillId}","title":"电子回执单"}'>查看</span>
										</div>
		                        	</c:if>
		                        </td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty capitalBillList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='11'>暂无交易信息</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts content1">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">收票信息</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">发票种类</td>
						<td>
							<div class="customername pos_rel">
								<span class="">${buyorderVo.invoiceTypeStr}</span>
							</div>
						</td>
						<td>收票状态</td>
						<td class="font-red">
							<c:choose>
								<c:when test="${buyorderVo.invoiceStatus eq 0}">
									未收票
								</c:when>
								<c:when test="${buyorderVo.invoiceStatus eq 1}">
									部分收票
								</c:when>
								<c:when test="${buyorderVo.invoiceStatus eq 2}">
									全部收票
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>收票备注</td>
						<td colspan="3" class="font-red">${buyorderVo.invoiceComments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
		
		<div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					发票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
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
                	<c:forEach var="list" items="${buyInvoiceList}" varStatus="num">
                		<tr>
	                        <td>${list.invoiceNo}</td>
	                        <td>
	                        	<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
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
												<span style="color: red">红字有效</span>
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
	                        <td>${list.creatorName}</td>
	                        <td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
	                        <td><date:date value="${list.validTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
	                        <td>${list.validUserName}</td>
	                    </tr>
                	</c:forEach>
                	<c:if test="${empty buyInvoiceList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='8'>暂无发票信息</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
		
		<div class="parts">
            <div class="title-container title-container-green">
                <div class="table-title nobor">
                    物流信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered" id="wulb">
                <thead>
                    <tr>
                        <th>快递单号</th>
                        <th>快递公司</th>
                        <th>发货时间</th>
                        <th>预计到达时间</th>
                        <th>运费</th>
                        <th>商品</th>
                        <th>备注</th>
                        <th>操作人</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <%-- <c:if test="${not empty communicateList}">
                		<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
                			<tr>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                        <td></td>
		                    </tr>
                		</c:forEach>
                		<tr >
		                        <td colspan="8">运费总额：31  商品总数：2997  已发货总数：2800  待发货数量：197 </td>
		                    </tr>
                	</c:if>
                	<c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='8'>暂无物流信息</td>
					    </tr>
		        	</c:if> --%>
                </tbody>
            </table>
        </div>
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">入库记录</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered" id="rk">
                <thead>
                    <tr>
                        <td>序号</td>
                        <td>入库时间</td>
                        <td>产品名称</td>
                        <td>品牌</td>
                        <td>型号</td>
                        <td>数量</td>
                        <td>单位</td>
                        <td>效期截止日期</td>
                        <td>批次号</td>
                        <td>入库操作人</td>
                    </tr>
                    </thead>
                    <tbody>
                    <%-- <c:if test="${not empty buyorderVo.warehouseGoodsOperateLogVoList}">
                		<c:forEach items="${buyorderVo.warehouseGoodsOperateLogVoList}" var="agolv" varStatus="status">
                			<tr>
                				<td>${status.count}</td>
		                        <td><date:date value ="${agolv.addTime} "/></td>
		                        <td class="text-left">
		                        	<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${agolv.goodsId}","title":"产品信息"}'>${agolv.goodsName}</span> 
		                    				</br>${agolv.sku}</br>${agolv.materialCode}
		                    	</td>
		                        <td>${agolv.brandName}</td>
		                        <td>${agolv.model}</td>
		                        <td>${agolv.num}</td>
		                        <td>${agolv.unitName}</td>
		                        <td><date:date value ="${agolv.expirationDate} "/></td>
		                        <td>${agolv.batchNumber}</td>
		                        <td>${agolv.operaterName}</td>
		                    </tr>
                		</c:forEach>
                	</c:if> --%>
                	<%-- <c:if test="${empty buyorderVo.warehouseGoodsOperateLogVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='10'>暂无记录！</td>
					    </tr>
		        	</c:if> --%>
                </tbody>
            </table>
        </div> 
		
        </form>
        
        

     </div>   
<%@ include file="../../common/footer.jsp"%>