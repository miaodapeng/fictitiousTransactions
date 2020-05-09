<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购订单审核" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/view_buyorder_sx.js?rnd=<%=Math.random()%>'></script>
	<form action="" method="post" id="myform">
	<div class="pt10 content">
		<div class="parts">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">基本信息</div>
				<input type="hidden" id="user_id" name="user_id" value="${curr_user.userId}">
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
							<c:if test="${buyorderVo.lockedStatus eq 1}">已锁定（<span class="font-red">${lockedReason}</span>）</c:if>
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
	<div class="parts table-style77 product">
		<div class="title-container mb10 title-container-blue">
            <div class="table-title nobor">产品信息</div>
        </div>
		<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
	            <table class="table table-style7">
	                <thead>
	                    <tr>
	                    	<th class="wid4">序号</th>
	                        <th class="wid10">产品名称</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid9">物料编码</th>
							<th class="wid8">采购数量</th>
							<th class="wid6">单位</th>
							<th class="wid10">单价</th>
							<th class="wid8">总额</th>
							<th class="wid8">到货数量</th>
							<th class="wid8">收票数量</th>
							<th class="wid6">货期（天）</th>
							<th class="wid10">安调信息</th>
							<th class="wid10">内部备注</th>
							<th class="wid10">采购备注</th>
							
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                    	<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
	                    	<td class="num">${num.count}</td>
	                    	<td class="text-left">
			                    <div class="customername pos_rel">
		                    		<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}
		                    				&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>

									<c:set var="skuNo" value="${bgv.sku}"></c:set>
									<%@ include file="../../common/new_sku_common_tip.jsp" %>

                    				<%--<div class="pos_abs customernameshow">--%>
									<%--物料编码： ${bgv.materialCode}<br> --%>
									<%--注册证号： ${bgv.registrationNumber}<br>--%>
									<%--管理类别：  ${bgv.manageCategoryName}<br>--%>
									<%--产品归属：<c:if test="${not empty bgv.userList }">--%>
												<%--<c:forEach items="${bgv.userList }" var="user"--%>
													<%--varStatus="st">--%>
											<%--${user.username } <c:if--%>
														<%--test="${st.count != bgv.userList.size() }">、</c:if>--%>
												<%--</c:forEach>--%>
											<%--</c:if>  <br> --%>
									<%--库存：${bgv.goodsStock}<br> --%>
									<%--可用库存：${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}<br> --%>
									<%--订单占用：${bgv.orderOccupy}<br>--%>
									<%--</div>--%>
									<div>${newSkuInfosMap[bgv.sku].SKU_NO}</div>
								</div>
	                        </td>
	                        <td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MODEL}</td>
	                        <td>${newSkuInfosMap[bgv.sku].MATERIAL_CODE}</td>
	                        <td>
                        		<c:choose>
                        			<c:when test="${bgv.afterSaleUpLimitNum > 0}">
                        				<div class="customername pos_rel">
	                        				<span alt="${bgv.goodsId}"> ${bgv.num - bgv.afterSaleUpLimitNum} <i class="iconredsigh ml4 contorlIcon"></i></span>
	                        				<div class="pos_abs customernameshow">原值：${bgv.num}</div>
	                        			</div>
                        			</c:when>
                        			<c:otherwise>
                        				${bgv.num - bgv.afterSaleUpLimitNum}
                        			</c:otherwise>
                        		</c:choose>
	                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num}"/>
	                        </td>
	                        <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
	                        <td><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}"><fmt:formatNumber type="number" value="${bgv.price*(bgv.num - bgv.afterSaleUpLimitNum)}" pattern="0.00" maxFractionDigits="2" /></span>
	                        </td>
	                        <td class="warning-color1">${bgv.arrivalNum}</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${bgv.invoiceNum >0}">
	                        			<div class="customername pos_rel ">
	                        				<div>
	                        				${bgv.invoiceNum}<i class="iconbluesigh "></i>
	                        				</div>
		                        			<div class="pos_abs customernameshow " style="display: none; ">
		                        				<c:forEach items="${bgv.invoiceList}" var="inv">
													发票号：${inv.invoiceNo }</br>
													票种：
							                        	<c:if test="${inv.invoiceType eq 429}">17%增值税专用发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 430}">17%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 681}">16%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 682}">16%增值税专用发票</c:if>
							                        	
							                        	<c:if test="${inv.invoiceType eq 971}">13%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 972}">13%增值税专用发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 683}">6%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 684}">6%增值税专用发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 685}">3%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 686}">3%增值税专用发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 687}">0%增值税普通发票</c:if>
							                        	<c:if test="${inv.invoiceType eq 688}">0%增值税专用发票</c:if>
													</br>
													红蓝字：
														<c:choose>
															<c:when test="${inv.colorType eq 1}">
																<c:choose>
																	<c:when test="${inv.isEnable eq 0}">
																		<span style="color: red">红字作废</span>
																	</c:when>
																	<c:otherwise>
																		<span style="color: red">红字有效</span>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${inv.isEnable eq 0}">
																		<span style="color: red">蓝字作废</span>
																	</c:when>
																	<c:otherwise>
																		蓝字有效
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose></br>
													金额：${inv.amount }</br>
													开票数量：${inv.num}</br>
													日期：<date:date value="${inv.addTime}" />
											</c:forEach>
			                        	</div>
			                       </div>
		                        </c:when>
		                        <c:otherwise>
		                        	0
		                        </c:otherwise>
	                        </c:choose>
                        </td>
                        
                       	<td>${bgv.deliveryCycle}</td>
                        <td>${bgv.installation}</td>
                        <td>${bgv.goodsComments}</td>
                        <td>${bgv.insideComments}</td>
	                   </tr>
	                    <tr>
	                        <td colspan="15" class="table-container ">
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
	         <div class="tablelastline-load">
               	 总件数<span class="warning-color1" id="zSum">${buyorderVo.buyorderSum}</span>，总金额<span class="warning-color1" id="zMoney"><fmt:formatNumber type="number" value="${buyorderVo.buyorderAmount}" pattern="0.00" maxFractionDigits="2" /></span>
               	<%--  <input type="hidden" id="page" value="${page.pageNo}">
               	 <input type="hidden" id="totalRecord" value="${page.totalRecord}">
               	 <input type="hidden" id="totalPage" value="${page.totalPage}">
               	 <input type="hidden" id="pageSize" value="${page.pageSize}"> --%>
              <%--  	 <c:if test="${page.pageNo!=page.totalPage}">
		        	<span id="addMore" class="bt-bg-style bg-light-blue bt-small ml20" onclick="moreAuto(${buyorderVo.buyorderId })">加载更多</span>
		        </c:if> --%>
            </div>
        
		</div> 
		<%-- <iframe src="./getBuyorderGoodsVoListPage.do?buyorderId=${buyorderVo.buyorderId}" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no">
         </iframe> --%>
		<!-- /**********************付款计划********************************************/ -->
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
		<!-- /*********************************收货信息*************************************/ -->
		<div class="parts content1">
			<div class="title-container title-container-blue">
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
		<!-- /****************************************售票信息****************************************************/ -->
		<div class="parts content1">
			<div class="title-container title-container-blue">
				<div class="table-title nobor">收票信息</div>
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
		<!-- /***************************************其他信息**************************************************/ -->
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
					<tr>
						<td>预计到货时间<br>（工作日）</td>
						<td colspan="3">${buyorderVo.estimateArrivalTime}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tcenter mb15 mt-5">
            <c:if test="${buyorderVo.userId eq curr_user.userId }">
	            <c:if test="${buyorderVo.deliveryDirect eq 0}"><!-- 普发 -->
	            <span class="bg-light-green bt-bg-style bt-small addtitle"
						tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=0","title":"打印"}'>打印</span>
	            </c:if>
	            <c:if test="${buyorderVo.deliveryDirect eq 1}"><!--  直发-->
	            <span class="bg-light-green bt-bg-style bt-small addtitle"
						tabTitle='{"num":"order_buyorder_${buyorderVo.buyorderId }","link":"./order/buyorder/printOrder.do?buyorderId=${buyorderVo.buyorderId }&deliveryDirect=1","title":"打印"}'>打印</span>
	            </c:if>	
	            
	            <c:if test="${buyorderVo.deliveryDirect == 1 && buyorderVo.lockedStatus eq 0 }" >
					<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" 
							tabTitle='{"num":"buyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/buyorder/confirmArrivalInit.do?buyorderId=${buyorderVo.buyorderId}","title":"确认收货"}'>确认收货</button>
				</c:if>
	            
	            <c:if test="${buyorderVo.status eq 1 && buyorderVo.lockedStatus eq 0}">
	            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" 
	            				tabTitle='{"num":"modify_apply<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/buyorder/modifyApplyInit.do?buyorderId=${buyorderVo.buyorderId}","title":"申请修改"}'>申请修改</button>
	            </c:if>
	            <c:if test="${buyorderVo.status ne 3 and isPayApplySh == 0 && buyorderVo.lockedStatus ne 1}">
	            	<button type="button" class="bt-bg-style bg-light-orange bt-small addtitle" 
	            		tabTitle='{"num":"orderBuyorderApplyPayment${buyorderVo.buyorderId}","link":"./order/buyorder/applyPayment.do?buyorderId=${buyorderVo.buyorderId}","title":"申请付款"}'>申请付款</button>
	            </c:if>
	            <c:if test="${buyorderVo.status ne 3 && (buyorderVo.validStatus eq 0 ||(buyorderVo.validStatus eq 1 && buyorderVo.lockedStatus eq 0 
	            				&& buyorderVo.invoiceStatus eq 0 && buyorderVo.paymentStatus eq 0 && buyorderVo.arrivalStatus eq 0 
	            				&& (buyorderVo.serviceStatus eq 0 ||buyorderVo.serviceStatus eq 3 ))
	            				|| (buyorderVo.validStatus eq 1  && buyorderVo.lockedStatus eq 0 && buyorderVo.aftersaleCanClose eq 1 && (buyorderVo.serviceStatus eq 2 || buyorderVo.serviceStatus eq 0)))}">
	            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeBuyorder(${buyorderVo.buyorderId});">关闭订单</button>
	            </c:if>
            </c:if>
        </div>
        </form>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    沟通记录
                </div>
                 <c:if test="${buyorderVo.status ne 3 && buyorderVo.lockedStatus ne 1 && buyorderVo.userId eq curr_user.userId }">
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录","link":"./addCommunicatePagePf.do?buyorderId=${buyorderVo.buyorderId}&&traderId=${buyorderVo.traderId }&flag=sx"}'>
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
			                        <c:if test="${buyorderVo.status ne 3 && buyorderVo.lockedStatus ne 1 && buyorderVo.userId eq curr_user.userId }">
			                        	<span class="border-blue pop-new-data" 
			                        		layerParams='{"width":"850px","height":"460px","title":"编辑沟通记录","link":"./editcommunicate.do?communicateRecordId=${communicateRecord.communicateRecordId}&buyorderId=${buyorderVo.buyorderId}&&traderId=${buyorderVo.traderId }&flag=sx"}'>编辑</span>
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
                </tbody>
            </table>
            <c:if test="${null==historicActivityInstance or empty historicActivityInstance}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">暂无审核记录。</div>
        	</c:if>
        	<div class="clear"></div>
        </div>
        <div class="parts">
            <div class="title-container ">
                <div class="table-title nobor">
                    订单修改申请
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                         <th>订单修改申请单</th>
                        <th>申请人</th>
                        <th>审核状态</th>
                    </tr>
                 </thead>
                 <tbody>   
                   <c:forEach var="list" items="${buyorderModifyApplyList}" varStatus="num3">
                		<tr>
							<td>
							<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyordermodifyapply${list.buyorderModifyApplyId}","link":"./order/buyorder/viewModifyApply.do?buyorderModifyApplyId=${list.buyorderModifyApplyId}","title":"采购单修改信息"}'>${list.buyorderModifyApplyNo}</a>
	                        </td>
							<td>${list.creatorName}</td>
							<td>
								<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
		                    </td>
						</tr>
                	</c:forEach>
                	<c:if test="${empty buyorderModifyApplyList}">
						<tr>
							<td colspan="3">暂无订单修改申请。</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container ">
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
                        <th>交易方式</th>
                        <th>付款备注</th>
                        <th>审核状态</th>
                       	<th>查看</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${not empty payApplyList}">
                		<c:forEach items="${payApplyList}" var="list" varStatus="">
                			<tr>
		                        <td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td><date:date value="${list.addTime}" /></td>
		                        <td>${list.creatorName}</td>
		                        <td>${list.traderName}</td>
		                        <td>
			                        <c:forEach var="modeList" items="${traderModeList}" varStatus="">
										<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
									</c:forEach>
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
		                        	<div class="caozuo">
		                        	<span class="caozuo-blue pop-new-data" layerparams='{"width":"50%","height":"30%","title":"付款申请审核信息","link":"<%=basePath%>finance/after/paymentVerify.do?payApplyId=${list.payApplyId}"}'>查看</span>
									</div>
                        		</td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty payApplyList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='8'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
      <div class="tcenter mb15 mt-5">
        		<c:if test="${(null!=taskInfoPay and null!=taskInfoPay.getProcessInstanceId() and null!=taskInfoPay.assignee) or !empty candidateUserMapPay[taskInfoPay.id]}">
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
						<button id="sub" type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"新增交易记录","link":"${pageContext.request.contextPath}/finance/buyorder/initPayApplyPass.do?id=${payApplyId}&taskId=${taskInfoPay.id}"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"${pageContext.request.contextPath}/order/buyorder/complement.do?taskId=${taskInfoPay.id}&pass=false&type=1"}'>审核不通过</button>
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
                        <th>交易金额</th>
                        <th>交易时间</th>
                        <th>业务类型</th>
                        <th>交易类型</th>
                        <th>交易主体</th>
                        <th>交易方式</th>
                        <th>付款方</th>
                        <th>收款方</th>
                        <th>交易备注</th>
                        <th>电子回执单</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${not empty buyorderVo.capitalBillList}">
                		<c:forEach items="${buyorderVo.capitalBillList}" var="acb" >
                			<tr>
								<td>${acb.capitalBillNo}</td>
								<td><fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" /></td>
								<td>
									<c:if test="${acb.traderTime != 0}">
										<date:date value="${acb.traderTime}" />
									</c:if>
								</td>
								<td>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 525}">订单付款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 526}">订单收款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 531}">退款</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 532}">资金转移</c:if>
									<c:if test="${acb.capitalBillDetail.bussinessType eq 533}">信用还款</c:if>
								</td>
								<td>
									<c:if test="${acb.traderType eq 1}">收入</c:if>
									<c:if test="${acb.traderType eq 2}">支出</c:if>
									<c:if test="${acb.traderType eq 3}">转移</c:if>
									<c:if test="${acb.traderType eq 4}">转入</c:if>
									<c:if test="${acb.traderType eq 5}">转出</c:if>
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
								<td>${acb.payee}</td>
								<td>${acb.comments}</td>
								<td>
		                        	<c:if test="${(acb.traderType == 2 || acb.traderType == 5) && acb.bankBillId != 0}">
			                        	<div class="caozuo">
											<span class="caozuo-blue addtitle"   tabTitle='{"num":"credentials${acb.bankBillId}", "link":"<%=basePath%>finance/capitalbill/credentials.do?bankBillId=${acb.bankBillId}","title":"电子回执单"}'>查看</span>
										</div>
		                        	</c:if>
		                        </td>
							</tr>
                		</c:forEach>
                			<tr>
		                        <td class="text-left" colspan="11">订单金额：<fmt:formatNumber type="number" value="${buyorderVo.totalAmount}" pattern="0.00" maxFractionDigits="2" />
		                         订单实际金额：<fmt:formatNumber type="number" value="${buyorderVo.realAmount}" pattern="0.00" maxFractionDigits="2" />
		                         已付款金额：<fmt:formatNumber type="number" value="${buyorderVo.paymentAmount+buyorderVo.periodAmount}" pattern="0.00" maxFractionDigits="2" /> 
		                         &nbsp;&nbsp;&nbsp;&nbsp;
                        	<span style="color:red">未付金额：<fmt:formatNumber type="number" value='${buyorderVo.realAmount - buyorderVo.paymentAmount - buyorderVo.periodAmount }' pattern="0.00" maxFractionDigits="2" /></span>
                        	&nbsp;=&nbsp;
                        	订单实际金额：<fmt:formatNumber type="number" value='${buyorderVo.realAmount}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;-&nbsp;
                        	客户实付金额：<fmt:formatNumber type="number" value='${buyorderVo.paymentAmount +buyorderVo.periodAmount }' pattern="0.00" maxFractionDigits="2" /> 
		                        </td>
		                    </tr>
                	</c:if>
                	<c:if test="${empty buyorderVo.capitalBillList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='11'>暂无记录！</td>
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
                <c:if test="${buyorderVo.validStatus eq 1 && buyorderVo.lockedStatus ne 1 && buyorderVo.status != 3 && buyorderVo.userId eq curr_user.userId}">
                <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress","link":"./order/buyorder/addExpress.do?buyorderId=${buyorderVo.buyorderId}","title":" 新增快递"}'>
                   	 新增快递
                </div>
                </c:if>
            </div>
            <table class="table  table-style6" id="wulb">
                <thead>
                    <tr>
                        <th class="wid10">快递单号</th>
                        <th class="wid10">快递公司</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th class="wid15">备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid8">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="express" items="${buyorderVo.expressList}">
                     <tr>
                        <td>${express.logisticsNo}</td>
                        <td>${express.logisticsName}</td>
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
                            	<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div><br/>
                            </c:forEach>
                        </td>
                        <td>${express.logisticsComments}</td>
                        <td>${express.updaterUsername}</td>
                        <td>
                        	<c:if test="${buyorderVo.validStatus eq 1 && buyorderVo.lockedStatus ne 1 && buyorderVo.status != 3 && buyorderVo.userId eq curr_user.userId}">
                            	<span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./order/buyorder/editExpress.do?expressId=${express.expressId}&buyorderId=${buyorderVo.buyorderId}","title":" 编辑快递"}'>编辑</span>
                        	</c:if>
                        </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty buyorderVo.expressList}">
                    <tr>
                        <td colspan="8" class="allchosetr text-left">
                        		<!-- 总运费 -->
                        		<c:set var="allamount" value="0.00"></c:set>
                        		<!-- 总数量 -->
                        		<c:set var="allarrivalnum" value="0"></c:set>
	                        	<c:forEach var="express" items="${buyorderVo.expressList}">
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
	                        	<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="warning-color1 mr10">${allamount}</span>已发/商品总数:<span class="warning-color1">${allarrivalnum}/${allnum}</span>
                        </td>
                    </tr>
                   </c:if>
                   <%--  <c:if test="${empty buyorderVo.expressList}">
                     <tr>
                        <td colspan="8">暂无记录！</td>
                    </tr>
                   </c:if> --%>
                   
                </tbody>
            </table>
        </div>
        
         <%-- <iframe src="./getWarehouseGoodsOperateLogVoListPage.do?buyorderId=${buyorderVo.buyorderId}" frameborder="0" style="width: 100%;border: 0; " onload="setIframeHeight(this)" scrolling="no">
         </iframe> --%>
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
                        <td>生产日期</td>
                        <td>效期截止日期</td>
                        <td>批次号</td>
                        <td>入库操作人</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty buyorderVo.warehouseGoodsOperateLogVoList}">
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
		                        <td><date:date value ="${agolv.productDate} "/></td>
		                        <td><date:date value ="${agolv.expirationDate} "/></td>
		                        <td>${agolv.batchNumber == null ? '' : agolv.batchNumber}</td>
		                        <td>${agolv.operaterName}</td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<%-- <c:if test="${empty buyorderVo.warehouseGoodsOperateLogVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='10'>暂无记录！</td>
					    </tr>
		        	</c:if> --%>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 入库附件
                </div>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid6">序号</th>
                        <th>附件名称</th>
                        <th>操作人</th>
                        <th>上传时间</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty AttachmentList }">
                		<c:forEach var="att" items="${AttachmentList}" varStatus="status">
                			<tr>
	                			<td>${status.count}</td>
	                			<td><a href="http://${att.domain}${att.uri}" target="_blank">${att.name}</a></td>
	                			<td>${att.username}</td>
	                			<td><date:date value ="${att.addTime}"/></td>
	                		</tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty AttachmentList }">
                		<td colspan="4">暂无入库附件记录</td>
                	</c:if>
                </tbody>
           </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">发票列表</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <td>发票号</td>
                        <td>票种</td>
                        <td>红蓝字</td>
                        <td>发票金额</td>
                        <td>操作人</td>
                        <td>操作时间</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty buyorderVo.invoiceList}">
                    	<c:set var="haveMoney" value="0"></c:set>
                		<c:forEach items="${buyorderVo.invoiceList}" var="vr" varStatus="">
                			<c:set var="haveMoney" value="${haveMoney + vr.amount}"></c:set>
                			<tr>
                				<td>${vr.invoiceNo }</td>
		                        <td>
		                        	<c:forEach var="invoiceList" items="${invoiceTypes}" varStatus="status">
										<c:if test="${invoiceList.sysOptionDefinitionId eq vr.invoiceType}">${invoiceList.title}</c:if>
									</c:forEach>
		                        </td>
		                        <td>
		                        	<c:if test="${vr.colorType eq 1 and vr.isEnable eq 1}">红字有效</c:if>
		                        	<c:if test="${vr.colorType eq 1 and vr.isEnable eq 0}">红字作废</c:if>
		                        	<c:if test="${vr.colorType eq 2 and vr.isEnable eq 1}">蓝字有效</c:if>
		                        	<c:if test="${vr.colorType eq 2 and vr.isEnable eq 0}">蓝字作废</c:if>
		                        </td>
		                        <td><fmt:formatNumber type="number" value="${vr.amount }" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>${vr.creatorName }</td>
		                        <td><date:date value ="${vr.addTime}"/></td>
		                    </tr>
                		</c:forEach>
                		<tr>
					       <td colspan='6'>已开票总额：${haveMoney} &nbsp;&nbsp;   待开票总额：${buyorderVo.totalAmount-haveMoney}</td>
					    </tr>
                	</c:if>
                	<c:if test="${empty buyorderVo.invoiceList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='6'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
         <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">售后列表</div>
                <c:if test="${addAfterSales eq 1 and buyorderVo.status != 0 and buyorderVo.status != 3 && buyorderVo.lockedStatus != 1 && buyorderVo.userId eq curr_user.userId}">
                	 <div class="title-click nobor addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./order/buyorder/addAfterSalesPage.do?flag=th&&buyorderId=${buyorder.buyorderId}","title":"新增售后"}'>新增售后</div>					
                </c:if>

            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid15">售后单号</th>
						<th class="wid15">售后类型</th>
                        <th class="wid20">创建时间</th>
                        <th class="wid10">创建人</th>
                        <th class="wid10">订单状态</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${asList}" var="aftersales" varStatus="status">
						<tr>
	                        <td>
	                        	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}&traderType=2","title":"售后详情"}'>${aftersales.afterSalesNo}</span>
	                        </td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${aftersales.type eq 546}">采购退货</c:when>
	                        		<c:when test="${aftersales.type eq 547}">采购换货</c:when>
	                        		<c:when test="${aftersales.type eq 548}">采购退票</c:when>
	                        		<c:when test="${aftersales.type eq 549}">采购退款</c:when>
	                        	</c:choose>
	                        </td>
	                        <td><date:date value ="${aftersales.addTime}"/></td>
	                        <td>${aftersales.creatorName}</td>
	                        <td>
	                            <c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 1}">进行中</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 2}">已完结</c:if>
								<c:if test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if>
	                        </td>
	                    </tr>
                   	</c:forEach>
                   	<c:if test="${empty asList}">
	                     <tr>
	                        <td colspan="5">暂无记录！</td>
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
                <c:if test="${buyorderVo.status ne 3 && buyorderVo.lockedStatus ne 1 && buyorderVo.userId eq curr_user.userId}">
                <div class="title-click nobor pop-new-data" layerParams='{"width":"600px","height":"300px","title":"合同回传","link":"./contractReturnInit.do?buyorderId=${buyorderVo.buyorderId}"}'>上传</div>
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
                	<c:if test="${not empty buyorderVo.attachmentList}">
	                	<c:forEach items="${buyorderVo.attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentFunction == 514}">
								<tr>
			                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
			                        <td>${list.username}</td>
			                        <td><date:date value ="${list.addTime}"/></td>
			                        <td>
			                            <div class="caozuo">
			                            	<c:if test="${buyorderVo.status ne 3 && buyorderVo.lockedStatus ne 1 && buyorderVo.userId eq curr_user.userId}">
			                                	<span class="caozuo-red" onclick="contractReturnDel(${list.attachmentId})">删除</span>
			                                </c:if>
			                            </div>
			                        </td>
			                    </tr>
	                   		</c:if>
	                   	</c:forEach>
                   	</c:if>
                   	<c:if test="${empty buyorderVo.attachmentList}">
                   		<tr>
	                        <td colspan="4">暂无记录！</td>
	                    </tr>
                   	</c:if>
                </tbody>
            </table>
        </div>

</body>

</html>