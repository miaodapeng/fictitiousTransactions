<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid8">SKU</th>

                        <th class="wid20">产品名称</th>
                        <th class="wid10">品牌</th>
                        <th class="wid8">制造商型号</th>
                        <th class="wid8">物料编码</th>
                        <th class="wid8">单价</th>
                        <th>数量</th>
                        <th>单位</th>
                        <th class="wid9">合计</th>
                        <th class="wid5">直发</th>
                        <th class="wid10">采购单号</th>
                        <th>已采购数量</th>
                        <th>已发货数量</th>
                        <th>已收货数量</th>
                        <th>退货数量</th>
                        <th>退货金额</th>
                        <th>已退货数量</th>
                        <th class="wid6">退货方式</th>
                        <th class="wid9">直发确认收货</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="sum" value="0"></c:set>
                	<c:if test="${not empty afterSalesGoodsVoListPage}">
                		<c:forEach items="${afterSalesGoodsVoListPage}" var="asg" varStatus="sttaus">
                            <c:set var="sum" value="${sum+asg.num}"></c:set>
	                		<tr class="J-skuInfo-tr" skuId="${asg.goodsId}">
		                        <td>${sttaus.count }</td>
                                <td class="JskuCode"> </td>

		                         <td class="text-left">
			                            <div class="customername pos_rel">
                                            <c:if test="${asg.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
		                                       <span class="brand-color1 addtitle JskuName" style="float:none;" tabTitle='{"num":"viewgoods${asg.goodsId}",
		                                       		"link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}","title":"产品信息"}'> </span><i class="iconbluemouth"></i>

		                                   <div class="pos_abs customernameshow JskuInfo"   style="display: none;">
<%--		                                            注册证：${asg.registrationNumber}<br>--%>
<%--		                                            管理类别：${asg.manageCategoryName}<br>--%>
<%--		                                            产品归属：<c:if test="${not empty asg.userList }">--%>
<%--                                        <c:forEach items="${asg.userList }" var="user" varStatus="st">--%>
<%--										${user.username } <c:if test="${st.count != asg.userList.size() }">、</c:if>--%>
<%--									</c:forEach>--%>
<%--								  </c:if> <br>--%>
<%--&lt;%&ndash;		                                            采购提醒：${asg.purchaseRemind}<br>&ndash;%&gt;--%>
<%--		                                            包装清单：${asg.packingList}<br>--%>
<%--		                                           审核状态：${asg.tos}<br>--%>
<%--&lt;%&ndash;		                               view_afterSales_th.jsp             订单占用：${asg.orderOccupy}<br>&ndash;%&gt;--%>
<%--&lt;%&ndash;		                                            可调剂：${asg.adjustableNum}<br>&ndash;%&gt;--%>
<%--&lt;%&ndash;		                                            库存：${asg.goodsStock}&ndash;%&gt;--%>
		                                        </div>
		                            </div>
		                        </td>
                                <td class="JbrandName"> </td>
                                <td  class="JskuModel"> </td>
                                <td class="JmaterialCode"> </td>
								<td><fmt:formatNumber type="number" value="${asg.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>${asg.saleorderNum}</td>
		                        <td class="JskuUnit"> </td>
		                        <td><fmt:formatNumber type="number" value="${asg.saleorderNum * asg.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>
		                        	<c:if test="${asg.saleorderDeliveryDirect eq 0}">否</c:if>
		                        	<c:if test="${asg.saleorderDeliveryDirect eq 1}">是</c:if>
		                        </td>
		                        <td>
		                        	<c:forEach items="${asg.buyorderNos}" var="buyorder">
		                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a><br>
		                        	</c:forEach>
		                        </td>
		                        <td>${asg.buyNum}</td>
		                        <td>${asg.deliveryNum}</td>
		                        <td>${asg.receiveNum}</td>
		                        <td class="warning-color1">${asg.num}</td>
                                <td>
                                    <c:if test="${asg.fag==1}"><fmt:formatNumber type="number" value="${asg.skuRefundAmount}" pattern="0.00" maxFractionDigits="2" /></c:if>
                                    <c:if test="${asg.fag==2}">
                                        <div class="customername pos_rel">
				                        	<span>
				                        		<fmt:formatNumber type="number" value="${asg.skuRefundAmount}" pattern="0.00" maxFractionDigits="2" />
				                        		<i class="iconredsigh ml4 contorlIcon"></i>
				                        	</span>
                                            <div class="pos_abs customernameshow">原值：<fmt:formatNumber type="number" value="${asg.skuOldRefundAmount}" pattern="0.00" maxFractionDigits="2" /></div>
                                        </div>
                                    </c:if>
                                </td>
                                <td class="warning-color1">
                                    <c:if test="${asg.goodsId ne 140633 && asg.goodsId ne 253620 && asg.goodsId ne 251462 && asg.goodsId ne 127063 && asg.goodsId ne 251526 && asg.goodsId ne 256675 }">
                                        <c:if test="${asg.deliveryDirect eq 0}">
                                            ${asg.incnt}<input type="hidden" name="prepareDeliveryNum" id="" value="${asg.incnt}">
                                            <c:set var="incntNum" value="${incntNum+asg.incnt}"></c:set>
                                        </c:if>
                                        <c:if test="${asg.deliveryDirect eq 1 && asg.arrivalStatus eq 2}">
                                            ${asg.num}<input type="hidden" name="prepareDeliveryNum" id="" value="${asg.num}">
                                            <c:set var="incntNum" value="${incntNum+asg.num}"></c:set>
                                        </c:if>
                                        <c:if test="${asg.deliveryDirect eq 1 && asg.arrivalStatus eq 0 }">
                                            0
                                        </c:if>
                                    </c:if>
                                    <c:if test="${asg.goodsId eq 140633 || asg.goodsId eq 253620 || asg.goodsId eq 251462 || asg.goodsId eq 127063 || asg.goodsId eq 251526 || asg.goodsId eq 256675}">
                                        <c:if test="${afterSalesVo.deliveryStatus eq 0 }">
                                            <c:choose >
                                                <c:when test="${asg.deliveryNum eq 1}"> 1<c:set var="incntNum" value="1"></c:set></c:when>
                                                <c:when test="${asg.deliveryNum ne 1}"> 0</c:when>
                                            </c:choose>
                                        </c:if>
                                        <c:if test="${afterSalesVo.deliveryStatus ne 0 }">
                                            ${asg.num} <input type="hidden" name="prepareDeliveryNum" id="" value="${asg.num}">
                                            <c:set var="incntNum" value="${incntNum+asg.num}"></c:set>
                                        </c:if>
                                    </c:if>
                                </td>

		                        <td class="warning-color1">
                                    <c:if test="${asg.deliveryDirect eq 0}">普发
                                        <c:set var="deliveryNum" value="${deliveryNum + asg.deliveryNum}"></c:set>
                                        <c:set var="nowSalesNum" value="${nowSalesNum + asg.nowSalesNum}"></c:set>
                                    </c:if>
                                    <c:if test="${asg.deliveryDirect eq 1}">直发<c:set var="directNum" value="${directNum+asg.num}"></c:set></c:if>
		                        </td>
                                <td>
                                    <c:if test="${asg.deliveryDirect eq 1 && asg.arrivalStatus eq 0}">
                                        <span class="edit-user" onclick="updateArrival(${asg.afterSalesGoodsId},${asg.num});">全部收货</span>
                                    </c:if>
                                </td>
		                    </tr>
	                	</c:forEach>
                     <tr>
                        <td colspan="20" class="allchosetr text-left">

                            	 退货总件数:<span class="warning-color1 mr10">${sum}</span>
                            系统拦截出库数量:<span class="warning-color1 mr10"  directNum="${directNum}"
                                           deliveryNum="${deliveryNum}"  nowSalesNum="${nowSalesNum}"
                                >
                            	 <c:set var="systemInterceptNum" value="0"></c:set>
                            	 <c:if test="${sum - directNum + deliveryNum le nowSalesNum }">${sum - directNum}<c:set var="systemInterceptNum" value="${sum - directNum}"></c:set></c:if>
                            	 <c:if test="${sum - directNum + deliveryNum gt nowSalesNum }">${nowSalesNum-deliveryNum > 0 ? nowSalesNum-deliveryNum : 0}
                                     <c:set var="systemInterceptNum" value="${nowSalesNum-deliveryNum > 0 ? nowSalesNum-deliveryNum : 0}"></c:set>
                                 </c:if>
                            	 </span>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${empty afterSalesGoodsVoListPage}">
	                    <tr>
	                        <td colspan="20">暂无记录</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>

<script type="text/javascript" src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
