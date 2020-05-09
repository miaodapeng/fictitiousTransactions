<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购订单详情" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/view_buyorder_pf.js?rnd=<%=Math.random()%>'></script>
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
						<td class="table-smaller">采购单号</td>
						<td>${buyorderVo.buyorderNo}</td>
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
						<td>创建者</td>
						<td>${buyorderVo.createName}</td>
						<td>创建时间</td>
						<td><date:date value="${buyorderVo.addTime}" /></td>
					</tr>
					<tr>
						<td>部门</td>
						<td>${buyorderVo.buyDepartmentName}</td>
						<td>归属</td>
						<td>${buyorderVo.homePurchasing}</td>
					</tr>
					<tr>
						<td>是否直发</td>
						<c:if test="${buyorderVo.deliveryDirect eq 0}"><td>普发</td></c:if>
						<c:if test="${buyorderVo.deliveryDirect eq 1}"><td class="warning-color1">直发</td></c:if>
						<td>审核状态</td>
						<td>
							<c:if test="${empty buyorderVo.verifyStatus }">待审核</c:if>
							<c:if test="${buyorderVo.verifyStatus eq 0 }">审核中</c:if>
							<c:if test="${buyorderVo.verifyStatus eq 1 }">审核通过</c:if>
							<c:if test="${buyorderVo.verifyStatus eq 2 }">审核不通过</c:if>
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
						<td>生效时间</td>
						<td><date:date value="${buyorderVo.validTime}" /></td>
					</tr>
					<tr>
						<td>付款状态</td>
						<td>
							<c:if test="${buyorderVo.paymentStatus eq 0}">未付款</c:if>
							<c:if test="${buyorderVo.paymentStatus eq 1}">部分付款</c:if>
							<c:if test="${buyorderVo.paymentStatus eq 2}">全部付款</c:if>
						</td>
						<td>收票状态</td>
						<td>
							<c:if test="${buyorderVo.invoiceStatus eq 0}">未收票</c:if>
							<c:if test="${buyorderVo.invoiceStatus eq 1}">部分收票</c:if>
							<c:if test="${buyorderVo.invoiceStatus eq 2}">全部收票</c:if>
						</td>
					</tr>
					<tr>
						<td>到货状态</td>
						<td>
							<c:if test="${buyorderVo.arrivalStatus eq 0}">未收货</c:if>
							<c:if test="${buyorderVo.arrivalStatus eq 1}">部分收货</c:if>
							<c:if test="${buyorderVo.arrivalStatus eq 2}">全部收货</c:if>
						</td>
						<td>锁定状态</td>
						<td>
							<c:if test="${buyorderVo.lockedStatus eq 0}">未锁定</c:if>
							<c:if test="${buyorderVo.lockedStatus eq 1}">已锁定</c:if>
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
		<div class="parts content1 ">
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
							</div>
						</td>
						<td class="table-smaller">联系人</td>
						<td>${buyorderVo.traderContactName}</td>
					</tr>
					<tr>
						<td>电话</td>
						<td>${buyorderVo.traderContactTelephone}</td>
						<td>手机</td>
						<td>${buyorderVo.traderContactMobile}</td>
					</tr>
					<tr>
						<td>地区</td>
						<td>${buyorderVo.traderArea}</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>联系地址</td>
						<td colspan="3">${buyorderVo.traderAddress}</td>
					</tr>
					<tr>
						<td>供应商备注</td>
						<td colspan="3">${buyorderVo.traderComments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
<!-- ----------------------------------产品信息 ------------------------------------- -->
<div class="parts table-style77">
		<div class="title-container mb10  title-container-blue">
            <div class="table-title nobor">产品信息</div>
        </div>
		
		<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
		 	
	            <table class="table table-style7">
	                <thead>
	                    <tr>
	                    	<th class="wid4">序号</th>
	                        <th class="wid8">产品名称</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid8">物料编码</th>
							<th class="wid6">采购数量</th>
							<th class="wid4">单位</th>
							<th class="wid6">单价</th>
							<th class="wid6">总额</th>
							<th class="wid8">可用库存 /库存量</th>
							<th class="wid6">货期（天）</th>
							<th class="wid10">安调信息</th>
							<th class="wid10">内部备注</th>
							<th class="wid10">采购备注</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                    	<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
	                    	<td>${num.count}</td>
	                    	<td class="text-left">
			                    <div class="customername pos_rel">
		                    		<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}
		                    				&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>

									<c:set var="skuNo" value="${bgv.sku}"></c:set>
									<%@ include file="../../common/new_sku_common_tip.jsp" %>

									<div>${newSkuInfosMap[bgv.sku].SKU_NO}</div>
								</div>
	                        </td>
	                        <td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MODEL}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MATERIAL_CODE}</td>
	                        <td>
	                        	<div class="customername pos_rel">
		                        	<span alt="${bgv.goodsId}">
		                        		${bgv.num - bgv.afterSaleUpLimitNum}
		                        	</span>
	                        	</div>
	                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num}"/>
	                        </td>
	                        <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
	                        <td class="onePrice"><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}"><fmt:formatNumber type="number" value="${bgv.price*(bgv.num - bgv.afterSaleUpLimitNum)}" pattern="0.00" maxFractionDigits="2" /></span>
	                        </td>
	                        <td>
	                        <c:if test="${bgv.goodsId ne 127063}"></c:if>	
	                        	${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}/${bgv.goodsStock > 0 ? bgv.goodsStock : 0}
	                        
	                        </td>
	                        <td>${bgv.deliveryCycle}</td>
	                        <td>${bgv.installation}</td>
	                        <td>${bgv.goodsComments}</td>
	                        <td>${bgv.insideComments}</td>
	                    </tr>
	                    <c:if test="${bgv.goodsId ne 127063}"></c:if>
	                    <tr>
	                        <td colspan="14" class="table-container ">
	                            <table class="table">
	                                <thead>
	                                    <tr>
	                                        <th class="wid8">关联单号</th>
	                                        <th class="wid10 ">申请人</th>
	                                        <th class="wid15 ">采购数量/需采数量</th>
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
														<a class="addtitle" href="javascript:void(0);" 
														tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"订单信息",
														"link":"./order/saleorder/view.do?saleorderId=${saleorderGoods.saleorderId}"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
													<c:if test="${saleorderGoods.orderType eq 2}">
														<a class="addtitle" href="javascript:void(0);" 
														tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"订单信息",
														"link":"./order/saleorder/viewBhSaleorder.do?saleorderId=${saleorderGoods.saleorderId}"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
												</td>
												<td>${saleorderGoods.applicantName}</td>
												<td id="${num.count}_${saleorderGoods.saleorderId }_${status.index}">
													
													<c:if test="${buyorderVo.deliveryDirect eq 1}">
														/
													</c:if>
													<c:if test="${buyorderVo.deliveryDirect eq 0}">
														/
													</c:if>
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
               	 总件数<span class="warning-color1" id="zSum">${buyorderVo.buyorderSum}</span>，总金额<span class="warning-color1" id="zMoney"><fmt:formatNumber type="number" value="${buyorderVo.buyorderAmount}" pattern="0.00" maxFractionDigits="2" /></span>
            </div>
        </div>

		<div class="parts content1">
			<div class="title-container  title-container-blue">
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
							<td><fmt:formatNumber type="number" value="${buyorderVo.prepaidAmount}" pattern="0.00" maxFractionDigits="2" /></td>
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
							<td><fmt:formatNumber type="number" value="${buyorderVo.prepaidAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq buyorderVo.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td><fmt:formatNumber type="number" value="${buyorderVo.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>收货且收票后${buyorderVo.periodDay}天内支付</td>
						</tr>
					</c:if>
					<c:if test="${buyorderVo.paymentType eq 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td><fmt:formatNumber type="number" value="${buyorderVo.prepaidAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq quote.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td><fmt:formatNumber type="number" value="${buyorderVo.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>收货且收票后${buyorderVo.periodDay}天内支付</td>
						</tr>
						<tr>
							<td>第三期</td>
							<td>尾款</td>
							<td><fmt:formatNumber type="number" value="${buyorderVo.retainageAmount}" pattern="0.00" maxFractionDigits="2" /></td>
							<td>到货后${buyorderVo.retainageAmountMonth}个月内支付</td>
						</tr>
					</c:if>
					<tr>
						<td>付款备注</td>
						<td colspan="3" class="text-left">
							${buyorderVo.paymentComments}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="parts content1">
			<div class="title-container  title-container-blue">
				<div class="table-title nobor">收货信息</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">指定物流公司</td>
						<td>
							<div class="customername pos_rel">
								<span class="">${buyorderVo.logisticsName}</span>
							</div>
						</td>
						<td class="table-smaller">运费说明</td>
						<td>${buyorderVo.freightDes}</td>
					</tr>
					<c:if test="${buyorderVo.deliveryDirect eq 1}"></c:if>
						<tr>
							<td class="table-smaller">收货客户</td>
							<td>
								<div class="customername pos_rel">
									<span class="">${buyorderVo.takeTraderName}</span>
								</div>
							</td>
							<td class="table-smaller">收货联系人</td>
							<td>${buyorderVo.takeTraderContactName}</td>
						</tr>
						<tr>
							<td class="table-smaller">电话</td>
							<td>
								<div class="customername pos_rel">
									<span class="">${buyorderVo.takeTraderContactTelephone}</span>
								</div>
							</td>
							<td class="table-smaller">手机号</td>
							<td>${buyorderVo.takeTraderContactMobile}</td>
						</tr>
						<tr>
							<td class="table-smaller">收货地区</td>
							<td>
								<div class="customername pos_rel">
									<span class="">${buyorderVo.takeTraderArea}</span>
								</div>
							</td>
							<td class="table-smaller"></td>
							<td></td>
						</tr>
						<tr>
							<td class="table-smaller">收货地址</td>
							<td colspan="3">
								<div class="customername pos_rel">
									<span class="">${buyorderVo.takeTraderAddress}</span>
								</div>
							</td>
						</tr>
					
					<tr>
						<td>物流备注</td>
						<td colspan="3">${buyorderVo.logisticsComments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
		
		<div class="parts content1">
			<div class="title-container">
				<div class="table-title nobor  ">收票信息</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">发票种类</td>
						<td colspan="3">
							<div class="customername pos_rel">
								<span class="">${buyorderVo.invoiceTypeStr}</span>
							</div>
						</td>
					</tr>
					<tr>
						<td>收票备注</td>
						<td colspan="3">${buyorderVo.invoiceComments}</td>
					</tr>
					
				</tbody>
			</table>
		</div>
		
		<div class="parts content1">
			<div class="title-container  title-container-blue">
				<div class="table-title nobor ">其他信息</div>
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
					<tr>
						<td>预计到货时间<br>（工作日）</td>
						<td colspan="3">${buyorderVo.estimateArrivalTime}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tcenter mb15 mt-5">
            
            <c:if test="${buyorderVo.status eq 0 && buyorderVo.userId eq curr_user.userId}">
            	<input type="hidden" id="prepaidAmount" value="${buyorderVo.prepaidAmount }"/>
            	<input type="hidden" id="retainageAmount" value="${buyorderVo.retainageAmount }"/>
            	<input type="hidden" name="accountPeriodAmount" id="accountPeriodAmount" value="${buyorderVo.accountPeriodAmount }"/>
            	<input type="hidden" name="paymentType" value="${buyorderVo.paymentType }"/>
            	<input type="hidden" name="traderName" value="${buyorderVo.traderName }"/>
            	<input type="hidden" name="traderId" value="${buyorderVo.traderId }"/>
            	<input type="hidden" name="buyorderNo" value="${buyorderVo.buyorderNo}"/>
            	<input type="hidden" name="taskId" value="${taskInfo.id == null ?0: taskInfo.id}"/>
            	<c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId() )or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}"> 
            		<!-- 采购订单为待确认状态 -->
            		 <c:if test="${buyorderVo.userId eq curr_user.userId }">
			            <c:if test="${buyorderVo.deliveryDirect eq 0}"><!-- 普发 -->
			            <span class="bg-light-green bt-bg-style bt-small addtitle"
								tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=0","title":"打印"}'>打印预览</span>
			            </c:if>
			            <c:if test="${buyorderVo.deliveryDirect eq 1}"><!--  直发-->
			            <span class="bg-light-green bt-bg-style bt-small addtitle"
								tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=1","title":"打印"}'>打印预览</span>
			            </c:if>
			         </c:if>
	            	<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidBuyorder()">申请审核</button>
	            	<button type="button" class="bt-bg-style bg-light-orange bt-small" onclick="editBuyorder()">修改订单</button>
	            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeBuyorder(${buyorderVo.buyorderId})">关闭订单</button>
            	</c:if>
            </c:if>
            <c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
				<c:if test="${buyorderVo.deliveryDirect eq 0}"><!-- 普发 -->
		            <span class="bg-light-green bt-bg-style bt-small addtitle"
							tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=0","title":"打印"}'>打印预览</span>
	            </c:if>
	            <c:if test="${buyorderVo.deliveryDirect eq 1}"><!--  直发-->
		            <span class="bg-light-green bt-bg-style bt-small addtitle"
							tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=1","title":"打印"}'>打印预览</span>
	            </c:if>
				<c:choose>
					<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
					<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1&buyorderId=${buyorderVo.buyorderId}"}'>审核通过</button>
					<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1&buyorderId=${buyorderVo.buyorderId}"}'>审核不通过</button>
					</c:when>
					<c:otherwise>
       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
					</c:otherwise>
				</c:choose>
			</c:if>
            <c:if test="${buyorderVo.status ne 3 && (buyorderVo.validStatus eq 0 ||(buyorderVo.validStatus eq 1&&buyorderVo.lockedStatus eq 0 
            				&& buyorderVo.invoiceStatus eq 0 && buyorderVo.paymentStatus eq 0 && buyorderVo.arrivalStatus eq 0 
            				&& (buyorderVo.serviceStatus eq 0 ||buyorderVo.serviceStatus eq 3)))}">
            	
            </c:if>
        </div>
        </form>
        <div class="parts">
            <div class="title-container  title-container-blue">
                <div class="table-title nobor">
                    沟通记录
                </div>
                 <c:if test="${buyorderVo.status ne 3 && buyorderVo.userId eq curr_user.userId}">
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录","link":"./addCommunicatePagePf.do?buyorderId=${buyorderVo.buyorderId}&&traderId=${buyorderVo.traderId }&flag=wsx"}'>
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
		                        <td><date:date value ="${communicateRecord.addTime} "/></td>
		                        
		                        <td class="caozuo">
		                        <c:if test="${buyorderVo.userId eq curr_user.userId }">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录","link":"./editcommunicate.do?communicateRecordId=${communicateRecord.communicateRecordId}&buyorderId=${buyorderVo.buyorderId}&&traderId=${buyorderVo.traderId }&flag=wsx"}'>编辑</span></td>
		                    	</c:if>
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
            <div class="title-container ">
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
           
        	<div class="clear"></div>
        </div>
       
</body>

</html>