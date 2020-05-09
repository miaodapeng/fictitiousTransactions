<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <table class="table  table-style10">
                <thead>
                     <tr>
                         <th class="wid8">SKU</th>
                       <th class="wid18">产品名称</th>
                       <th class="wid10">品牌</th>
                       <th class="wid8">型号</th>
                       <th class="wid8">物料编号</th>
                       <th class="wid8">采购订单</th>
                       <th class="wid5">销售价</th>
                       <th class="wid8">数量</th>
                       <th class="wid5">单位</th>
                      </tr>
                   </thead>
                   <tbody>
                       <c:if test="${not empty afterSalesGoodsVoListPage}">
               		<c:forEach items="${afterSalesGoodsVoListPage}" var="asg" varStatus="sttaus">
                        <tr class="J-skuInfo-tr" skuId="${asg.goodsId}">

                            <td class="JskuCode"> </td>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
                                    <c:if test="${asg.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
                                       <span class="brand-color1 addtitle JskuName" style="float:none;"
                                       		tabTitle='{"num":"viewgoods${list.goodsId}","link":"<%= basePath %>/goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}",
                                       					"title":"产品信息"}'>${asg.goodsName}</span>
                                    <i class="iconbluemouth"></i>

                                    <div class="pos_abs customernameshow JskuInfo" style="display: none;">

                                    </div>
                            	</div>
	                        </td>
                            <td class="JbrandName">  </td>
                            <td  class="JskuModel">
                            </td>
                            <td class="JmaterialCode"> </td>
	                        <td>
	                        
	                        
	                         	<c:if test="${not empty asg.buyorderNos}">
		                        	<c:forEach items="${asg.buyorderNos}" var="buyorder">
		                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a><br>
		                        	</c:forEach>
		                        </c:if>
		                        
		                        
	                        </td>
							<td><fmt:formatNumber type="number" value="${asg.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>${asg.saleorderNum}</td>
	                        <td class="JskuUnit">${asg.unitName}</td>
	                    </tr>
                	</c:forEach>
                   </c:if>
                   <c:if test="${empty afterSalesGoodsVoListPage}">
                    <tr>
                        <td colspan="9">暂无记录</td>
                    </tr>
                   </c:if>
                 </tbody>
            </table>
<script type="text/javascript" src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
