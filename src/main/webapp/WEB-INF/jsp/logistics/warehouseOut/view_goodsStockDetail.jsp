<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库存详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr class="J-skuInfo-tr" skuId="${goods.goodsId}">
					<td class="table-smaller">产品名称</td>
					<td>
                      <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}","title":"产品信息"}'>${goods.goodsName}</a>
                    </td>
					<td class="table-smaller">订货号</td>
					<td>${goods.sku}</td>
				</tr>
				<tr>
					<td >品牌</td>
					<td CLASS="JbrandName">${newSkuInfo.BRAND_NAME}</td>
					<td>型号</td>
					<td CLASS="JskuModel">${newSkuInfo.MODEL}</td>
				</tr>
				<tr>
					<td>物料编码</td>
					<td class="JmaterialCode">${newSkuInfo.MATERIAL_CODE}</td>
					<td>单位</td>
					<td class="JskuUnit">${newSkuInfo.UNIT_NAME}</td>
				</tr>
				<tr>
					<td>产品级别</td>
					<td class="JmanageLevel">${newSkuInfo.GOODS_LEVEL_NAME}</td>
					<td>添加时间</td>
					<td ><date:date value ="${goods.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td>所属分类</td>
					<td class="JskubaseCategoryName">${newSkuInfo.BASE_CATEGORY_NAME}</td>
					<td>商品归属</td>
					<td class="Jproductmanager">${newSkuInfo.USERNAME}<br>${newSkuInfo.ASSIS}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">在库列表</div>
			<a class="table-title nobor J-IsProblem-edit-btn" style="float: right;margin-right: 20px;color: #0f5abd">编辑</a>
			<a class="table-title nobor J-IsProblem-save-btn" style="float: right;margin-right: 20px;display: none;color: #0f5abd"">保存</a>
			<input type="hidden" id="goodId" value="${goods.goodsId}">
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid10">序号</th>
					<th class="">关联单号</th>
					<th>供应商</th>
					<th class="wid10">采购价</th>
					<th class="wid10">入库数量</th>
					<th>贝登条码</th>
					<th>厂商条码</th>
					<th>批次号</th>
					<th>生产日期</th>
					<th>效期</th>
					<th class="wid10">仓存位置</th>
					<th>仓存备注</th>
					<th>在库数量</th>
					<!-- <th>总价</th> -->
					<th>入库时间</th>
					<th>操作人</th>
					<th>是否有问题</th>
					<th>问题原因</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${wglList}" varStatus="num">
					<tr>
						<td>
							${num.count}
						</td>
					    <td>
					      <c:if test="${list.operateType eq 1}">
					      <a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${list.buyorderId}","title":"订单信息"}'>${list.buyorderNo}</a>
					      </c:if>
					      <c:if test="${(list.operateType eq 3) or (list.operateType eq 5)}">
					      <span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${list.buyorderId}&traderType=1","title":"售后详情"}'>${list.buyorderNo}</span>
					      </c:if>
					      <c:if test="${ list.operateType eq 9}">
					      <span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${list.buyorderId}","title":"外借详情页"}'>${list.buyorderNo}</span>
					     
					      </c:if>
					      <c:if test="${(wlist.ywType eq 8) }">
		                             <span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${list.buyorderId}&traderType=2","title":"售后详情"}'>${list.buyorderNo}</span>
		                  </c:if>
						</td>
						<td>${list.traderName}</td>
						<td>${list.cgprice}</td>
						<td>${list.num}</td>
						<td>${list.barcode}</td>
						<td>${list.barcodeFactory}</td>
						<td>${list.batchNumber}</td>
						<td><date:date value ="${list.productDate }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td><date:date value ="${list.expirationDate }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${list.warehouseArea}</td>
						<td>${list.comments}</td>
						<td>${list.zkCnt}</td>
						<%-- <td>${list.cgprice * list.num}</td> --%>
						<td><date:date value ="${list.addTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${list.operator}</td>
						<td>
							<div class="J-isProblem-show">
								<c:if test="${list.isProblem eq 1}">是</c:if>
								<c:if test="${list.isProblem eq 0}">否</c:if>
							</div>
							<div class="J-isProblem-edit" style="display: none;">
								<select name="isProblem" id="isProblem" class="isProblem" <c:if test="${currentTime>list.expirationDate&&list.expirationDate!=''}">disabled</c:if>>
									<option value="1" <c:if test="${list.isProblem eq 1 ||currentTime>list.expirationDate}" >selected="selected"</c:if>>是</option>
									<option value="0" <c:if test="${list.isProblem eq 0}">selected="selected"</c:if>>否</option>
								</select>
								<input type="hidden" value="${list.warehouseGoodsOperateLogId}" class="warehouseGoodsOperateLogId" name="warehouseGoodsOperateLogId" id="warehouseGoodsOperateLogId"/>
								<input type="hidden" id="path" value="${pageContext.request.contextPath}">
							</div>
						</td>
						<td>
							<input type="text" name="problemRemark" value="${list.problemRemark}" class="problemRemark J-reason-edit" style="display: none;" maxlength="20">
							<span class="J-reason-show">
                                <c:if test="${list.isProblem eq 1}">${list.problemRemark}</c:if>
                                   </span>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty wglList}">
					<tr>
						<td colspan="17">暂无在库记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">在售列表</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid8">付款顺序</th>
					<th class="wid15">订单号</th>
					<th>归属销售</th>
					<th>创建时间</th>
					<th >付款时间</th>
					<th class="wid10">订单需求数量</th>
					<th>订单占用数量</th>
					<th>已发货数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${sdList}" varStatus="num2">
					<tr>
						<td>${num2.count}</td>
						<td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderNo}</a></td>
						<td>${list.saleName }</td>
						<td><date:date value ="${list.addTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td><date:date value ="${list.paymentTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<c:if test="${list.needCnt < 0}">
							<td>0</td>
						</c:if>
						<c:if test="${list.needCnt >= 0}">
							<td>${list.needCnt }</td>
						</c:if>
						<td>${list.occupyNum }</td>
						<td>${list.deliveryNum }</td>
					</tr>
				</c:forEach>
				<c:if test="${empty sdList}">
					<tr>
						<td colspan="8">暂无在售记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<%--锁定库存模块--%>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">锁定库存</div>
		</div>
		<table class="table">
			<thead>
			<tr>
				<th class="wid20">订单号</th>
				<th class="wid10">归属销售</th>
				<th>创建时间</th>
				<th >付款时间</th>
				<th class="wid10">订单需求数量</th>
				<th>占用锁定库存量</th>
				<th>已发货数量</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="list" items="${actionLockList}" varStatus="num2">
				<tr>
					<td>${list.saleorderNo}</td>
					<td>${list.saleName}</td>
					<td><date:date value ="${list.addTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><date:date value ="${list.paymentTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
					<c:if test="${list.needCnt < 0}">
						<td>0</td>
					</c:if>
					<c:if test="${list.needCnt >= 0}">
						<td>${list.needCnt }</td>
					</c:if>
					<td>${list.actionOccupyNum }</td>
					<td>${list.deliveryNum }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty actionLockList}">
				<tr>
					<td colspan="7">暂无锁定库存记录</td>
				</tr>
			</c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">在途列表</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid8">序号</th>
					<th class="wid15">订单号</th>
					<th>生效时间</th>
					<th class="wid10">数量</th>
					<th >预计到达时间</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ not empty bvList}">
					<c:forEach var="list" items="${bvList}" varStatus="num3">
						<!-- 关闭的订单不展示 -->
						<c:if test="${List.status != 3}">
							<tr>
								<td>${num3.count}</td>
								   <td><a class="addtitle" href="javascript:void(0);" 
														tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
														"link":"./order/buyorder/viewBuyorder.do?buyorderId=${list.buyorderId}","title":"订单信息"}'>${list.buyorderNo}</a></td>
								<td><date:date value ="${list.validTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${list.onWayNum}</td>
								<td>
								<c:if test="${list.estimateArrivalTime eq 0 }">--</c:if>
								<c:if test="${list.estimateArrivalTime != 0 }">
								<date:date value ="${list.estimateArrivalTime}" format="yyyy-MM-dd HH:mm:ss"/>
								</c:if>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty bvList}">
					<tr>
						<td colspan="5">暂无在途记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">出入库统计</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid10">统计项</th>
					<c:forEach var="list" items="${dateMons}" varStatus="num2">
					   <th class="">${list }</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>
                                                                               入库量
                        </td>
					   <c:forEach var="list" items="${inNumList}" varStatus="num2">
					   <td class="">${list }</td>
					   </c:forEach>
					</tr>
					<tr>
						<td>
                                                                        出库量
                        </td>
						 <c:forEach var="list" items="${outNumList}" varStatus="num2">
					    <td class="">${list }</td>
					   </c:forEach>
					</tr>
					<tr>
						<td>
                                                                               报价量
                        </td>
						 <c:forEach var="list" items="${bjNumList}" varStatus="num2">
					    <td class="">${list }</td>
					   </c:forEach>
					</tr>
					<tr>
						<td>
                                                                              退货数
                        </td>
						 <c:forEach var="list" items="${thNumList}" varStatus="num2">
					   <td class="">${list }</td>
					   </c:forEach>
					</tr>
			</tbody>
		</table>
	</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
		src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>