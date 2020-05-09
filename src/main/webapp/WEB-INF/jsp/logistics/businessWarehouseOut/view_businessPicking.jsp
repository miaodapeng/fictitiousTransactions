<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认数量" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="main-container">
		<div class="begin-jianhuo-container">
			<div class="begin-jianhuo">
				<ul>
					<li>
						<div class="iconidentity ">
							<div class="arriveThisStep hasArrive">
								<i class="iconconfirmnumber"></i>
							</div>
							<div>确认拣货数量</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep">
								<i class="iconwarehousecheck"></i>
							</div>
							<div>仓库拣货</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep">
								<i class="iconpackage"></i>
							</div>
							<div>打包发货</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep">
								<i class="iconprintoutwarehouse"></i>
							</div>
							<div>打印出库单、新增快递</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid10">业务单号</th>
					<th class="wid15">生效时间</th>
					<th>客户名称</th>
					<th class="wid8">创建者</th>
					<th>商品总数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				    <td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${afterSales.afterSalesId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${afterSales.afterSalesId}&traderType=2","title":"售后详情"}'>${afterSales.afterSalesNo}</a></td>
					<td><date:date value="${afterSales.validTime}" /></td>
					<td>
					<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${afterSales.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${afterSales.traderId}", "title":"客户信息"}'>${afterSales.traderName}</a>
					</td>
					<td>${afterSales.creatorName}</td>
					<td>${goodsNum }</td>
				</tr>
			</tbody>
		</table>
	</div>
	    <input type="hidden" id="goodsListNum" value="${fn:length(afterSales.afterSalesGoodsList)}"/>
		<div class="parts table-style10-1 ">
			<div class="title-container">
				<div class="table-title ">产品信息</div>
			</div>
			 <c:forEach var="list" items="${afterSales.afterSalesGoodsList}" varStatus="num">
			<table class="table  table-style10">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th class="wid15">品牌/型号</th>
						<th class="wid5">单位</th>
						<c:if test="${businessType == 2}">
							<th class="wid10">采购单价</th>
						</c:if>
						<c:if test="${businessType == 1}">
							<th class="wid10">销售单价</th>
						</c:if>
						<th class="wid8">已发/总数</th>
						<th class="wid8">拣货未发</th>
						<th>需拣货量/可拣货库存</th>
						<th class="wid8">本次拣货</th>
					</tr>
				</thead>
				<tbody>
				  
					<tr>
						<td >${num.count}</td>
						<td class="text-left">
	                        <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
	                          <input type="hidden" id="goodsId${num.count}" value="${list.goodsId}"/>
	                          <input type="hidden" id="afterSalesGoodsIds${num.count}" value="${list.afterSalesGoodsId}"/>
	                        </div>
	                        <div>${list.sku}</div>
	                    </td>
						<td>${list.brandName}${list.model}</td>
						<td>${list.unitName}</td>
						<td>${list.buyorderPrice}</td>
						<td>${list.deliveryNum}/${list.num}</td>
						
						<td class="warning-color1" >${list.pickCnt - list.deliveryNum}</td>
						<td>${list.num - list.pickCnt  }/${list.totalNum }</td>
						<!-- 未拣货库存 -->
						<td style="display: none;"><input type="text" id="pickNeed${num.count}" style="display: none;" value="${list.totalNum - (list.pickCnt-list.deliveryNum )}"/></td>
						<!-- 需拣货数  -->
						<td style="display: none;"><input  type="text" id="need${num.count}" style="display: none;" value="${list.num - list.pickCnt  }"></input></td>
						<td><input type="text" disabled="disabled" name="pickNumpickCnt${num.count }" id="pickNumpickCnt${num.count }"  value="${list.pCountAll }"></td>
					</tr>
					
					<tr>
						<td colspan="9" class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th class="wid12">拣货/总数</th>
										<th class="wid15">效期截止日期</th>
										<th class="wid15">入库时间</th>
										<th>批次号</th>
										<th>存储位置</th>
										<th>仓存备注</th>
										<th class="wid15">关联单号</th>
										<!-- <th>采购单价</th> -->
									</tr>
								</thead>
								<tbody>
								<input type="hidden" id="wlistNum${num.count}" value="${fn:length(list.wlist)}"/>
								   <c:forEach var="listw" items="${list.wlist}" varStatus="n">
									<tr>
										<td class="jianhuozongshu"><input type="text" name="pickCnt${num.count }" id="pickCnt${num.count }${n.count }" value="${listw.pCount }" oninput="this.value=this.value.replace(/\D/g,'').replace(/^0+(?=\d)/,'')"/>
											<span>/</span> <span id="pickCnt${num.count }${n.count }Num">${listw.cnt }</span></td>
										<td>
										<date:date value="${listw.expirationDate }" />
										<input id="expirationDate${num.count }${n.count }" type="hidden" value="${listw.expirationDate }"/>
										<input id="relatedId${num.count }${n.count }" type="hidden" value="${listw.buyorderId }"/>
										<input id="relatedType${num.count }${n.count }" type="hidden" value="${listw.operateType }"/>
										</td>
										<td><date:date value="${listw.addTime }" /></td>
										<td id="batchNumber${num.count }${n.count }">${listw.batchNumber }</td>
										<td>${listw.warehouseArea }</td>
										<td>${listw.comments }</td>
										<td>
										<c:if test="${ listw.operateType == 1}">
										<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${listw.buyorderId}","title":"订单信息"}'>${listw.buyorderNo}</a>
										</c:if>	
										<c:if test="${ listw.operateType == 3 or listw.operateType == 5}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewsaleorder${listw.buyorderId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${listw.buyorderId}&traderType=1","title":"售后详情"}'>${listw.buyorderNo}</span>
										</c:if>	
										<c:if test="${ listw.operateType == 8}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${listw.buyorderId}&traderType=2","title":"售后详情"}'>${listw.buyorderNo}</span>
										</c:if>
                                        </td>
										<%-- <td>${listw.price }</td> --%>
									</tr>
									</c:forEach>
									<c:if test="${empty list.wlist}">
										<!-- 查询无结果弹出 -->
										 <table class="table">
										 	<tbody>
												<tr>
						                        	<td colspan="10">暂无可拣货产品！</td>
						                    	</tr>
						                     </tbody>
						           		</table>
									  </c:if>
								</tbody>
							</table>
						</td>
					</tr>
					
				</tbody>
			</table>
			</c:forEach>
			<c:if test="${empty afterSales.afterSalesGoodsList}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="10">暂无可拣货产品！</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
			  <c:if test="${not empty afterSales.afterSalesGoodsList}">
			<div class="table-buttons">
			<form method="post" id="search" action="<%= basePath %>warehouse/businessWarehouseOut/viewBsPickingDetail.do">
			     <input type="hidden"  name="afterSalesId" id="afterSalesId" value="${afterSales.afterSalesId }"/>
			     <input type="hidden"  name="businessType" id="businessType" value="${businessType }"/>
			    <input type="hidden"  name="pickNums" id="pickNums" value=""/>
			    <input type="hidden"  name="afterSalesGoodsIds" id="afterSalesGoodsIds" value=""/>
			    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search_s();" id="searchSpan">确认数量</span>
			</form>
			</div>
			</c:if>
		</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/index_pincking.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>