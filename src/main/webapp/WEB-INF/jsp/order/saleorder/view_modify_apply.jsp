<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单修改详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
	<div class="content mt10 ">
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">基本信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">修改申请单号</td>
                        <td>${saleorderModifyApplyInfo.saleorderModifyApplyNo}</td>
                        <td class="table-smaller">销售订单</td> 
                        <td>
                            ${saleorder.saleorderNo}
                        </td>
                    </tr>
                    <tr>
                        <td>创建时间</td>
                        <td><date:date value ="${saleorderModifyApplyInfo.addTime}"/></td>
                        <td>审核状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorderModifyApplyInfo.verifyStatus == null}">待审核</c:when>
								<c:when test="${saleorderModifyApplyInfo.verifyStatus eq 0}">审核中</c:when>
								<c:when test="${saleorderModifyApplyInfo.verifyStatus eq 1}">审核通过</c:when>
								<c:when test="${saleorderModifyApplyInfo.verifyStatus eq 2}">审核不通过</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收货信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收货客户</td>
                        <td>${saleorderModifyApplyInfo.takeTraderName}</td>
                        <td class="table-smaller">收货联系人</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.takeTraderContactName}
                                
                                <c:if test="${saleorderModifyApplyInfo.takeTraderContactName ne saleorderModifyApplyInfo.oldTakeTraderContactName}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldTakeTraderContactName}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.takeTraderContactTelephone}
                                
                                <c:if test="${saleorderModifyApplyInfo.takeTraderContactTelephone ne saleorderModifyApplyInfo.oldTakeTraderContactTelephone}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldTakeTraderContactTelephone}</div>
                                </c:if>
                            </div>
                        </td>
                        <td>手机</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.takeTraderContactMobile}
                                
                                <c:if test="${saleorderModifyApplyInfo.takeTraderContactMobile ne saleorderModifyApplyInfo.oldTakeTraderContactMobile}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldTakeTraderContactMobile}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地区</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.takeTraderArea}
                                
                                <c:if test="${saleorderModifyApplyInfo.takeTraderArea ne saleorderModifyApplyInfo.oldTakeTraderArea}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldTakeTraderArea}</div>
                                </c:if>
                            </div>
                        </td>
                        <td></td>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地址</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.takeTraderAddress}
                                
                                <c:if test="${saleorderModifyApplyInfo.takeTraderAddress ne saleorderModifyApplyInfo.oldTakeTraderAddress}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldTakeTraderAddress}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>物流备注</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.logisticsComments}
                                
                                <c:if test="${saleorderModifyApplyInfo.logisticsComments ne saleorderModifyApplyInfo.oldLogisticsComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldLogisticsComments}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收票客户</td>
                        <td>${saleorderModifyApplyInfo.invoiceTraderName}</td>
                        <td class="table-smaller">收票联系人</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceTraderContactName}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceTraderContactName ne saleorderModifyApplyInfo.oldInvoiceTraderContactName}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceTraderContactName}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceTraderContactTelephone}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceTraderContactTelephone ne saleorderModifyApplyInfo.oldInvoiceTraderContactTelephone}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceTraderContactTelephone}</div>
                                </c:if>
                            </div>
                        </td>
                        <td>手机</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceTraderContactMobile}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceTraderContactMobile ne saleorderModifyApplyInfo.oldInvoiceTraderContactMobile}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceTraderContactMobile}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>收票地区</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceTraderArea}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceTraderArea ne saleorderModifyApplyInfo.oldInvoiceTraderArea}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceTraderArea}</div>
                                </c:if>
                            </div>
                        </td>
                        <td>发票类型</td>
                        <td>
                        	<c:if test="${saleorderModifyApplyInfo.oldInvoiceType != 0}">
                        	<div class="customername pos_rel">
                        		<span>
	                        	<c:forEach var="list" items="${invoiceTypes}">
			                    	<c:if test="${saleorderModifyApplyInfo.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
			                    </c:forEach>
		                    	<c:if test="${saleorderModifyApplyInfo.invoiceType ne saleorderModifyApplyInfo.oldInvoiceType}">
		                    		<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：
	                                <c:forEach var="list" items="${invoiceTypes}">
				                    	<c:if test="${saleorderModifyApplyInfo.oldInvoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
				                    </c:forEach>
	                                </div>
		                    	</c:if>
		                    </div>
		                    </c:if>
		                    
		                    <c:if test="${saleorderModifyApplyInfo.oldIsSendInvoice != -1}">
		                    <div class="customername pos_rel">
		                    	<span>
			                    <c:choose>
			                    	<c:when test="${saleorderModifyApplyInfo.isSendInvoice eq 0}">（不寄送）</c:when>
			                    	<c:otherwise>（寄送）</c:otherwise>
			                    </c:choose>
			                    <c:if test="${saleorderModifyApplyInfo.isSendInvoice ne saleorderModifyApplyInfo.oldIsSendInvoice}">
			                    	<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：
	                                <c:choose>
				                    	<c:when test="${saleorderModifyApplyInfo.oldIsSendInvoice eq 0}">（不寄送）</c:when>
				                    	<c:otherwise>（寄送）</c:otherwise>
				                    </c:choose>
	                                </div>
			                    </c:if>
		                    </div>
		                    </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>收票地址</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceTraderAddress}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceTraderAddress ne saleorderModifyApplyInfo.oldInvoiceTraderAddress}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceTraderAddress}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${saleorderModifyApplyInfo.invoiceComments}
                                
                                <c:if test="${saleorderModifyApplyInfo.invoiceComments ne saleorderModifyApplyInfo.oldInvoiceComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${saleorderModifyApplyInfo.oldInvoiceComments}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td>开票方式</td>
                    	<td>
		                    <div class="customername pos_rel">
		                    	<span>
			                    <c:choose>
			                    	<c:when test="${saleorderModifyApplyInfo.invoiceMethod eq 1}">手动纸质开票</c:when>
			                    	<c:when test="${saleorderModifyApplyInfo.invoiceMethod eq 2}">自动纸质开票</c:when>
			                    	<c:when test="${saleorderModifyApplyInfo.invoiceMethod eq 3}">自动电子发票</c:when>
			                    </c:choose>
			                    <c:if test="${saleorderModifyApplyInfo.invoiceMethod ne saleorderModifyApplyInfo.oldInvoiceMethod}">
			                    	<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：
	                                <c:choose>
				                    	<c:when test="${saleorderModifyApplyInfo.oldInvoiceMethod eq 1}">手动纸质开票</c:when>
			                    		<c:when test="${saleorderModifyApplyInfo.oldInvoiceMethod eq 2}">自动纸质开票</c:when>
			                    		<c:when test="${saleorderModifyApplyInfo.oldInvoiceMethod eq 3}">自动电子发票</c:when>
				                    </c:choose>
	                                </div>
			                    </c:if>
		                    </div>
                    	</td>
                    	<td>暂缓开票</td>
                    	<td>
		                    <div class="customername pos_rel">
		                    	<span>
			                    <c:choose>
			                    	<c:when test="${saleorderModifyApplyInfo.isDelayInvoice eq 0}">否</c:when>
			                    	<c:otherwise>是</c:otherwise>
			                    </c:choose>
			                    <c:if test="${saleorderModifyApplyInfo.isDelayInvoice ne saleorderModifyApplyInfo.oldIsDelayInvoice}">
			                    	<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：
	                                <c:choose>
				                    	<c:when test="${saleorderModifyApplyInfo.oldIsDelayInvoice eq 0}">否</c:when>
				                    	<c:otherwise>是</c:otherwise>
				                    </c:choose>
	                                </div>
			                    </c:if>
		                    </div>
                    	</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">产品信息</div>
            </div>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width:40px">序号</th>
                        <th style="width:150px">产品名称</th>
                        <th style="width:80px">品牌</th>
                        <th style="width:70px">型号</th>
                        <th style="width:80px">单价</th>
                        <th style="width:35px">数量</th>
                        <th style="width:35px">单位</th>
                        <th style="width:80px">总额</th>
                        <th style="width:70px">货期</th>
                        <th style="width:50px">直发</th>
                        <th style="width:60px">直发备注</th>
                        <th>产品备注</th>
                        <th>内部备注</th>
                        <th>采购状态</th>
                        <th>发货状态</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="num" value="0"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
					<c:set var="isNotDelPriceZero" value="0"></c:set>
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
							<c:if test="${list.price == '0.00'}">
								<c:set var="isNotDelPriceZero" value="1"></c:set>
							</c:if>
						</c:if>
	                    <tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if>>
	                        <td>${staut.count}</td>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
	                                <c:choose>
										<c:when test="${list.isDelete eq 1}">
											<span>${newSkuInfosMap[list.sku].SHOW_NAME} <br/></span>
			                                <span>${newSkuInfosMap[list.sku].SKU_NO}<br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>
										</c:when>
										<c:otherwise>
											<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${newSkuInfosMap[list.sku].SHOW_NAME}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
			                                <span>${newSkuInfosMap[list.sku].SKU_NO} <br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>

											<c:set var="skuNo" value="${list.sku}"></c:set>
											<%@ include file="../../common/new_sku_common_tip.jsp" %>

			                                <%--<div class="pos_abs customernameshow">--%>
												<%--物料编码：${newSkuInfosMap[list.sku].MATERIAL_CODE} <br>--%>
												<%--注册证号：${newSkuInfosMap[list.sku].REGISTRATION_NUMBER} <br>--%>
												<%--管理类别：${newSkuInfosMap[list.sku].MANAGE_CATEGORY_LEVEL} <br>--%>
												<%--产品负责人：${newSkuInfosMap[list.sku].PRODUCTMANAGER} <br>--%>
												<%--&lt;%&ndash;采购提醒：${list.goods.purchaseRemind} <br>&ndash;%&gt;--%>
												<%--包装清单：${newSkuInfosMap[list.sku].PACKING_LIST} <br>--%>
												<%--质保年限：${newSkuInfosMap[list.sku].QA_YEARS} <br>--%>
												<%--库存：<span>${newSkuInfosMap[list.sku].STOCKNUM}</span> <br>--%>
												<%--可用库存：<span>${newSkuInfosMap[list.sku].AVAILABLESTOCKNUM}</span><br>--%>
												<%--订单占用：<span>${newSkuInfosMap[list.sku].OCCUPYNUM}</span><br>--%>
												<%--可调剂：<span>${list.goods.adjustableNum}</span> <br>--%>
												<%--审核状态：${newSkuInfosMap[list.sku].CHECK_STATUS}--%>
											<%--</div>--%>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[list.sku].MODEL}</td>
	                        <td>${list.price}</td>
	                        <td>${list.num}</td>
	                        <td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
	                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>${list.deliveryCycle}</td>
	                        <td>
	                        	<c:set var="oldDeliveryDirect" value=""></c:set>
	                        	<c:set var="oldDeliveryDirectComments" value=""></c:set>
	                        	<c:set var="oldGoodsComments" value=""></c:set>
	                        	<c:set var="newDeliveryDirect" value=""></c:set>
	                        	<c:set var="newDeliveryDirectComments" value=""></c:set>
	                        	<c:set var="newGoodsComments" value=""></c:set>
	                        	<c:forEach var="modifyApplylist" items="${saleorderModifyApplyGoodsList}" varStatus="modifyStaut">
	                        		<c:if test="${modifyApplylist.saleorderGoodsId == list.saleorderGoodsId}">
	                        		<c:set var="oldDeliveryDirect" value="${modifyApplylist.oldDeliveryDirect}"></c:set>
	                        		<c:set var="oldDeliveryDirectComments" value="${modifyApplylist.oldDeliveryDirectComments}"></c:set>
	                        		<c:set var="oldGoodsComments" value="${modifyApplylist.oldGoodsComments}"></c:set>
	                        		<c:set var="newDeliveryDirect" value="${modifyApplylist.deliveryDirect}"></c:set>
	                        		<c:set var="newDeliveryDirectComments" value="${modifyApplylist.deliveryDirectComments}"></c:set>
	                        		<c:set var="newGoodsComments" value="${modifyApplylist.goodsComments}"></c:set>
	                        		</c:if>
	                        	</c:forEach>
	                            <div class="customername pos_rel">
	                                <span>
	                                <c:choose>
										<c:when test="${newDeliveryDirect eq 0}">否
											<c:if test="${oldDeliveryDirect ne newDeliveryDirect}">
												<i class="iconbluesigh ml4 contorlIcon"></i></span>
				                                <div class="pos_abs customernameshow">原值：
				                                <c:choose>
													<c:when test="${oldDeliveryDirect eq 0}">否</c:when>
													<c:otherwise>
													是
													</c:otherwise>
												</c:choose>
				                                </div>
			                                </c:if>
										</c:when>
										<c:otherwise>
										是
											<c:if test="${oldDeliveryDirect ne newDeliveryDirect}">
												<i class="iconbluesigh ml4 contorlIcon"></i></span>
				                                <div class="pos_abs customernameshow">原值：
				                                <c:choose>
													<c:when test="${oldDeliveryDirect eq 0}">否</c:when>
													<c:otherwise>
													是
													</c:otherwise>
												</c:choose>
				                                </div>
			                                </c:if>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>
	                        	<div class="customername pos_rel">
                                <span>${newDeliveryDirectComments}
                                
                                <c:if test="${oldDeliveryDirectComments ne newDeliveryDirectComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${oldDeliveryDirectComments}</div>
                                </c:if>
                            </div>
	                        </td>
	                        <td>
	                        	<div class="customername pos_rel">
                                <span>${newGoodsComments}
                                
                                <c:if test="${oldGoodsComments ne newGoodsComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${oldGoodsComments}</div>
                                </c:if>
	                        </td>
	                        <td>${list.insideComments}</td>
	                        <td>
	                        	<c:choose>
									<c:when test="${(empty list.buyNum) or (list.buyNum eq 0)}">
										未采购
									</c:when>
									<c:when test="${list.buyNum < list.num}">
										部分采购
									</c:when>
									<c:when test="${list.buyNum == list.num}">
										已采购
									</c:when>
								</c:choose>
	                        </td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.deliveryStatus eq 0}">
										未发货
									</c:when>
									<c:when test="${list.deliveryStatus eq 1}">
										部分发货
									</c:when>
									<c:when test="${list.deliveryStatus eq 2}">
										全部发货
									</c:when>
								</c:choose>
	                        </td>
	                    </tr>
                    </c:forEach>
                    <tr style="background: #eaf2fd;">
                        <td colspan="15" class="text-left">
                        	<input type="hidden" value="${isNotDelPriceZero}" id="isNotDelPriceZero">
                        	总件数<span class="font-red">${num}</span>， 总金额
                            <span class="font-red"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="table-buttons">
        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        	<c:choose>
				<c:when test="${saleorderModifyApplyInfo.validStatus eq 0}">
					
					<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:set var="shenhe" value="0"></c:set>
						<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
							<c:if test="${verifyUsernameInfo == curr_user.username}">
								<c:set var="shenhe" value="1"></c:set>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${endStatus == '审核完成'}">
						<!--  <button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="validSaleorder(${saleorder.saleorderId})">订单生效</button> -->
					</c:if>
				
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
            
        </div>
        
	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table">
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
									<c:forEach var="vs" items="${verifyUsersList}" varStatus="status">
									 	<c:if test="${fn:contains(verifyUserList, vs)}">
									 		<span class="font-green">${vs}</span>&nbsp;
									 	</c:if>
									 	<c:if test="${!fn:contains(verifyUserList, vs)}">
									 		<span>${vs}</span>&nbsp;
									 	</c:if>
									 </c:forEach>
									 <c:if test="${empty verifyUsersList && empty hi.assignee}">
										${verifyUsers}
									</c:if>
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
                    <!-- 查询无结果弹出 -->
           	
           		<c:if test="${empty historicActivityInstance}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
        
                </tbody>
            </table>
           
       			
        	
        </div>
       
    </div>
    <input type="hidden" value="${saleorderModifyApplyInfo.saleorderModifyApplyId}" id="saleorderModifyApplyId"/>
    <input type="hidden" value="${saleorderModifyApplyInfo.saleorderId}" id="saleorderId"/>
    <script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/modify_apply_view.js?rnd=<%=Math.random()%>'></script>
    <script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>