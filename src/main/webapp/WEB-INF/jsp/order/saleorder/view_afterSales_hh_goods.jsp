<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid6">序号</th>
						<th class="wid8">SKU</th>
                        <th class="wid40">产品名称</th>
                        <th >品牌</th>
                        <th class="wid8">型号</th>
						<th class="wid12">物料编号</th>
                        <th class="wid8">单价</th>
                        <th class="wid8">数量</th>
                        <th class="wid5">单位</th>
                        <th class="wid5">直发</th>
                        <th>已采购数量</th>
                        <th>已发货数量</th>
                        <th>已收货数量</th>
                        <th>换货数量</th>
                        <th>换货方式</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty afterSalesGoodsVoListPage}">
                		<c:forEach items="${afterSalesGoodsVoListPage}" var="asg" varStatus="sttaus">
	                		<tr class="J-skuInfo-tr" skuId="${asg.goodsId}">
		                        <td>${sttaus.count }</td>
								<td class="JskuCode"> </td>
		                         <td class="text-left">
			                            <div class="customername pos_rel">
											<c:if test="${asg.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
		                                       <span class="brand-color1 addtitle JskuName" style="float:none;" tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                                       		"link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}","title":"产品信息"}'>${asg.goodsName}</span><i class="iconbluemouth"></i>

		                                   <div class="pos_abs customernameshow JskuInfo" style="display: none;">

		                                        </div>
		                            </div>
		                        </td>
								<td class="JbrandName">  </td>
								<td class="JskuModel">
								</td>
								<td class="JmaterialCode"> </td>
								<td><fmt:formatNumber type="number" value="${asg.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>${asg.saleorderNum}</td>
		                        <td class="JskuUnit"> </td>
		                        <td>
		                        	<c:if test="${asg.saleorderDeliveryDirect eq 0}">否</c:if>
		                        	<c:if test="${asg.saleorderDeliveryDirect eq 1}">是</c:if>
		                        </td>
		                        <td>${asg.buyNum}</td>
		                        <td>${asg.deliveryNum}</td>
		                        <td>${asg.receiveNum}</td>
		                        <td class="warning-color1">${asg.num}</td>
		                        <td class="warning-color1">
		                        	<c:if test="${asg.deliveryDirect eq 0}">普发</c:if>
		                        	<c:if test="${asg.deliveryDirect eq 1}">直发</c:if>
		                        </td>
		                    </tr>
	                	</c:forEach>
                     <tr>
                        <td colspan="15" class="allchosetr text-left">
                        	<c:set var="sum" value="0"></c:set>
                        	<c:forEach items="${afterSalesGoodsVoListPage}" var="asg">
                        		<c:set var="sum" value="${sum+asg.num}"></c:set>
                        	</c:forEach>
                            	 换货总件数:<span class="warning-color1 mr10">${sum}</span>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${empty afterSalesGoodsVoListPage}">
	                    <tr>
	                        <td colspan="15">暂无记录</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>
<script type="text/javascript" src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
