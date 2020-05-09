<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="出库详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="main-container">
	<div class="parts">
	    <div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid10">订单号</th>
					<th class="wid15">付款时间</th>
					<th class="wid15">可发货时间</th>
					<th >客户名称</th>
					<th class="">销售部门</th>
					<th >归属销售</th>
					<th class="wid8">订单总额</th>
					<th >已付款金额</th>
					<th>商品总数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				    <td>
						<c:choose>
							<c:when test="${saleorder.orderType eq 5}">
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.saleorderId}","link":"./order/hc/hcOrderDetailsPage.do?saleorderId=${saleorder.saleorderId}","title":"订单信息"}'>${saleorder.saleorderNo}</a>
							</c:when>
							<c:otherwise>
								<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${saleorder.saleorderId}","title":"订单信息"}'>
										${saleorder.saleorderNo}
								</a>
							</c:otherwise>
						</c:choose>
						<c:if test="">
						</c:if>
					</td>
					<td><date:date value="${saleorder.paymentTime}" /></td>
					<td><date:date value="${saleorder.satisfyDeliveryTime}" /></td>
					<td>
					<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${saleorder.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${saleorder.traderId}", "title":"客户信息"}'>${saleorder.traderName}</a>
					</td>
					<td>${saleorder.salesDeptName}</td>
					<td>${saleorder.optUserName}</td>
					<td><fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
					<c:if test="${saleorder.totalAmount > (saleorder.accountPayable+saleorder.paymentAmount)}">
					   <td class="warning-color1"><fmt:formatNumber type="number" value="${saleorder.accountPayable+saleorder.paymentAmount }" pattern="0.00" maxFractionDigits="2" /></td>
					</c:if>
					<c:if test="${saleorder.totalAmount == (saleorder.accountPayable+saleorder.paymentAmount)}">
					   <td><fmt:formatNumber type="number" value="${saleorder.accountPayable+saleorder.paymentAmount }" pattern="0.00" maxFractionDigits="2" /></td>
					</c:if>
					<c:if test="${saleorder.totalAmount < (saleorder.accountPayable+saleorder.paymentAmount)}">
					   <td><fmt:formatNumber type="number" value="${saleorder.accountPayable+saleorder.paymentAmount }" pattern="0.00" maxFractionDigits="2" /></td>
					</c:if>
					<td>${goodsNum }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">收货信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smaller">收货客户</td>
					<td>${saleorder.takeTraderName}</td>
					<td class="table-smaller">收货联系人</td>
					<td>${saleorder.takeTraderContactName}</td>
				</tr>
				<tr>
					<td>电话</td>
					<td><c:if
							test="${not empty saleorder.takeTraderContactTelephone}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${saleorder.takeTraderContactTelephone}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
						</c:if> ${saleorder.takeTraderContactTelephone}</td>
					<td>手机</td>
					<td><c:if
							test="${not empty saleorder.takeTraderContactMobile}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${saleorder.takeTraderContactMobile}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
						</c:if> ${saleorder.takeTraderContactMobile}</td>
				</tr>
				<tr>
					<td>收货地区</td>
					<td>${saleorder.takeTraderArea}</td>
					<td>发货方式</td>
					<td><c:choose>
							<c:when test="${saleorder.deliveryType eq 481}">
								一次发货
							</c:when>
							<c:when test="${saleorder.deliveryType eq 482}">
								多次发货
							</c:when>
						</c:choose></td>
				</tr>
				<tr>
					<td>收货地址</td>
					<td colspan="3">${saleorder.takeTraderAddress}</td>
				</tr>
				<tr>
					<td>指定物流公司</td>
					<td><c:forEach var="list" items="${logisticsList}">
							<c:if test="${saleorder.logisticsId == list.logisticsId}">${list.name}</c:if>
						</c:forEach></td>
					<td>运费说明</td>
					<td><c:forEach var="list" items="${freightDescriptions}">
							<c:if
								test="${saleorder.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
						</c:forEach></td>
				</tr>
				<tr>
					<td>物流备注
						<%--<c:if test="${first}==0 && ${fn:substring('${saleorder.saleorderId}', 0, 2)}=='HC'">
							sssssssssss
						</c:if>--%>

					</td>
					<td colspan="3" class="warning-color1">${saleorder.logisticsComments}<c:if test="${(first == 1) && (fn:substring(saleorder.saleorderNo, 0, 2) eq 'HC')}">
						首次合作客户，请随货邮寄资质清单
						</c:if>
					<input type="hidden" id="fth" value="${first}">
					<input type="hidden" id="sno" value="${saleorder.saleorderNo}">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<c:set var="isUrgent" value="0"></c:set>
	<c:set var="isCold" value="0"></c:set>
	<c:forEach var="goods" items="${saleorderGoods}" varStatus="num">
		<c:if test="${(goods.num - goods.afterReturnNum>0) && goods.goodsId == '251526'}">
				<c:set var="isUrgent" value="1"></c:set>
		</c:if>
		<c:if test="${(goods.num - goods.afterReturnNum>0) && goods.goodsId == '256675'}">
				<c:set var="isCold" value="1"></c:set>
		</c:if>
	</c:forEach>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">其它信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">附加条款</td>
					<td>${saleorder.additionalClause }</td>
				</tr>
				<tr>
					<td>内部备注</td>
					<td>${saleorder.comments }</td>
				</tr>
				<c:if test="${saleorder.orderType ==3}">
                    <tr>
                        <td class="table-smaller">订货备注</td>
                        <td  class="text-left">
                        <c:if test="${isUrgent == 1}">
                        	加急&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${isCold == 1}">
                        	使用冷链&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${saleorder.freightDescription == 474}">
                        	快递到付&nbsp;&nbsp;
                        </c:if>
                        </td>
                    </tr>
                </c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">产品信息</div>
		</div>
		<table class="table">
			<thead>
				<tr>
				    <th class="wid5">选择</th>
					<th class="wid5">序号</th>
					<th class="wid10">订货号</th>
					<th>产品名称</th>
					<th class="wid10">品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid10">单位</th>
					<th class="">发货状态</th>
					<th >关联单号</th>
					<th >库房</th>
					<th >货区</th>
					<th >货架</th>
					<th >库位</th>
					<th >仓存备注</th>
					<th>已发/总数</th>
					<th >库存量</th>
					<!-- <th>已发</th> -->
					<!-- <th>拣货未发数量</th>需拣货量/ -->
					<th>可拣货库存</th>
				</tr>
			</thead>
			<tbody>
			    <c:set var="flag" value="-1"></c:set>
				<c:forEach var="list" items="${saleorder.goodsList}" varStatus="num">
					<tr>
					    <td>
					        <c:if test="${list.lockedStatus == 1}">
								<font color="red">锁</font>
					        </c:if>
					        <c:if test="${list.lockedStatus == 0}">
								<c:if test="${list.deliveryStatus != 2}">
									<!-- 已拣货数量 -->
									<%--<c:set var="picGoodsNum" value="0"></c:set>
									<c:forEach var="listpick" items="${warehousePickList}" varStatus="num2">
										<c:if test="${listpick.goodsId eq list.goodsId}">
											<c:set var="picGoodsNum" value="${picGoodsNum + listpick.num}"></c:set>
										</c:if>
									</c:forEach>

									<c:if test="${picGoodsNum ne list.num}">
										<c:set var="flag" value="1"></c:set>
										<input type="checkbox" name="g_checknox"  value="${list.goodsId}" onclick="canelAll(this,'g_checknox')">
									</c:if>--%>
									<c:set var="flag" value="1"></c:set>
									<input type="checkbox" name="g_checknox"  value="${list.goodsId}" onclick="canelAll(this,'g_checknox')">
								</c:if>
					        </c:if>
                       	</td>
						<td>${num.count}</td>
						<td>${list.sku}</td>
						<c:choose>
							<c:when test="${list.isActionGoods > 0}">
								<td class="text-left">
									<div>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'><font color="red">[活动]</font>${newSkuInfosMap[list.sku].SHOW_NAME}</a>
									</div>
								</td>
							</c:when>
							<c:otherwise>
								<td class="text-left">
									<div>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'> ${newSkuInfosMap[list.sku].SHOW_NAME}</a>
									</div>
								</td>
							</c:otherwise>
						</c:choose>
						<td>${newSkuInfosMap[list.sku].BRAND_NAME}<br />${newSkuInfosMap[list.sku].MODEL}</td>
						<td>${newSkuInfosMap[list.sku].MATERIAL_CODE}</td>
						<td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
						<c:choose>
							<c:when test="${list.deliveryStatus eq 0}">
								<td class="warning-color1">未发货</td>
							</c:when>
							<c:when test="${list.deliveryStatus eq 1}">
								<td class="warning-color1">部分发货</td>
							</c:when>
							<c:when test="${list.deliveryStatus eq 2}">
								<td>全部发货</td>
							</c:when>
							<c:otherwise>
							<td></td>
							</c:otherwise>
						</c:choose>
						<td>
						  <c:forEach items="${list.buyorderList}" var="addr" begin="0" 
							  end="${fn:length(list.buyorderList)}" varStatus="stat">
							  <a class="addtitle" href="javascript:void(0);" tabtitle="{&quot;num&quot;:&quot;viewbuyorder${addr.buyorderId}&quot;,
							&quot;link&quot;:&quot;./order/buyorder/viewBuyorder.do?buyorderId=${addr.buyorderId}&quot;,&quot;title&quot;:&quot;采购订单信息&quot;}">
								${addr.buyorderNo}
							</a>
							<br/>
							</c:forEach>
						</td>
						<%--库房--%>
						<td>${storageLocation.get(list.sku).storageroomName}</td>
						<%--货区--%>
						<td>${storageLocation.get(list.sku).storageAreaName}</td>
						<%--货架--%>
						<td>${storageLocation.get(list.sku).storageRackName}</td>
						<%--库位--%>
						<td>${storageLocation.get(list.sku).storageLocationName}</td>
						<%--仓存备注--%>
						<td>${storageLocation.get(list.sku).comments}</td>
						<td>${list.deliveryNum}/${list.num}</td>
						<%--库存量--%>
						<td>${stockInfo.get(list.sku).stockNum}</td>
						<%-- <td>${list.deliveryNum}</td> --%>
						<%-- <td>
						${list.pickCnt-list.deliveryNum }${list.num - list.pickCnt  }/
						</td> --%>
						<td>
							<c:choose>
								<c:when test="${list.isActionGoods > 0}">
									${list.totalNum} <%--活动商品可拣货库存量 = 库存量--%>
								</c:when>
								<c:otherwise>
									${list.totalNum-list.actionLockCount}  <%--  普通商品可拣货库存量=库存量-活动锁定库存 --%>
								</c:otherwise>
							</c:choose>
						<%--<c:if test="${list.totalNum < 0}">
						0
						</c:if>
						<c:if test="${list.totalNum >= 0}">
						${list.totalNum}
						</c:if>--%>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${flag eq 1}">
			<div class="table-style4 " style="margin-left: 17px;">
				<div class="allchose">
					<input type="checkbox" name="all_g_checknox" onclick="selectall(this,'');" value="g_checknox"> <span>全选</span>
				</div>
			</div>
		</c:if>
		<c:if test="${saleorder.show eq 0}">
			<c:if test="${allPickCnt eq 0}">
				<div class="table-buttons">
					 <span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
					 <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
							layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"./batchAddWarehouseOut.do?orderId=${saleorder.saleorderId}&businessType=0"}'>批量出库</button>
					<span class="bg-light-blue bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }","link":"./warehouse/warehousesout/viewPicking.do?saleorderId=${saleorder.saleorderId }","title":"开始拣货"}'>拣货</span>
				</div>
			</c:if>
			<c:if test="${0<allPickCnt && allPickCnt<goodsNum}">
				<div class="table-buttons">
					 <span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
					 <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
							layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"./batchAddWarehouseOut.do?orderId=${saleorder.saleorderId}&businessType=0"}'>批量出库</button>
					<span class="bg-light-blue bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }","link":"./warehouse/warehousesout/viewPicking.do?saleorderId=${saleorder.saleorderId }","title":"开始拣货"}'>拣货</span>
					<c:if test="${allOutCnt < allPickCnt}">
						<%--  <span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./warehouse/warehousesout/viewOutDetail.do?saleorderId=${saleorder.saleorderId }","title":"开始出库"}'>手动出库</span> --%>
						   <span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_warehouseSmOut_${saleorder.saleorderId }","link":"./warehouse/warehousesout/warehouseSmOut.do?saleorderId=${saleorder.saleorderId }","title":"扫码打包发货"}'>扫码出库</span>
					</c:if>
				</div>
			</c:if>
			<c:if test="${allPickCnt eq goodsNum}">
				<c:if test="${allOutCnt < allPickCnt}">
					<div class="table-buttons">
						<span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
						<button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
							layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"./batchAddWarehouseOut.do?orderId=${saleorder.saleorderId}&businessType=0"}'>批量出库</button>
							<%--  <span class="bg-light-blue bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./warehouse/warehousesout/viewOutDetail.do?saleorderId=${saleorder.saleorderId }","title":"开始出库"}'>手动出库</span> --%>
						<span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_warehouseSmOut_${saleorder.saleorderId }","link":"./warehouse/warehousesout/warehouseSmOut.do?saleorderId=${saleorder.saleorderId }","title":"扫码打包发货"}'>扫码出库</span>
					</div>
				</c:if>
			</c:if>
		</c:if>
		
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">拣货记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
				     <%-- <c:if test="${saleorder.isAftersale ==0}"> --%>
				     <th class="wid5">选择</th>
				   <%--   </c:if> --%>
					<th class="wid5">序号</th>
					<th class="wid10">订货号</th>
					<th>产品名称</th>
					<th>品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid4">单位</th>
					<th>当次拣货数量</th>
					<th>拣货时间</th>
					<th class="wid8">操作人</th>
				</tr>
			</thead>
			<tbody>
			 <c:set var="lockedStatusFlag" value="-1"></c:set>
				<c:forEach var="listpick" items="${warehousePickList}"
					varStatus="num2">
					<tr>
					<td>
					 <c:if test="${listpick.lockedStatus == 1}">
					  <font color="red">锁</font>
					 </c:if>
					 <c:if test="${listpick.lockedStatus == 0}">
					 <c:set var="lockedStatusFlag" value="1"></c:set>
						    <c:if test="${listpick.cnt ==0}">
                        		<input type="checkbox" name="b_checknox" alt="<date:date value ="${listpick.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listpick.warehousePickingId},${listpick.warehousePickingDetailId}" onclick="canelAll(this,'b_checknox')">


                        	</c:if>
                        	<c:if test="${ listpick.cnt >0}">
                        	</c:if>

                        </c:if>
						<input type="hidden" name="batchNumber" id="batchNumber" value="${listpick.batchNumber}">
						<input type="hidden" name="expirationDate" id="expirationDate" value="${listpick.expirationDate}">
						<input type="hidden" name="goodsId" id="goodsId" value="${listpick.goodsId}">
						<input type="hidden" name="num" id="num" value="${listpick.num}">
						<input type="hidden" name="warehousePickingDetailId" id="warehousePickingDetailId" value="${listpick.warehousePickingDetailId}">

                         </td>
						<td>${num2.count}</td>
						<td>${listpick.sku}</td>
						<td class="text-left">
	                         <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listpick.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listpick.goodsId}","title":"产品信息"}'>${newSkuInfosMap[listpick.sku].SHOW_NAME}</a>
	                    </td>
						<td>${newSkuInfosMap[listpick.sku].BRAND_NAME}<br />${newSkuInfosMap[listpick.sku].MODEL}</td>
						<td>${newSkuInfosMap[listpick.sku].MATERIAL_CODE}</td>
						<td>${newSkuInfosMap[listpick.sku].UNIT_NAME}</td>
						<td>${listpick.num}</td>
						<td><date:date value ="${listpick.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${listpick.operator}</td>
					</tr>
				</c:forEach>

				<c:if test="${empty warehousePickList}">
					<tr>
						<td colspan="10">暂无拣货记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<c:if test="${lockedStatusFlag eq 1}">
		<c:if test="${not empty warehousePickList && pickFlag eq 1}">
		<div class="table-style4 ">
				<div class="allchose">
					<input type="checkbox" name="all_b_checknox" onclick="selectall(this,'pick_checkbox');" value="b_checknox"> <span>全选</span>
				</div>
				<div class="times">
					<span class="mr10">请选择批次</span>
					<c:forEach var="tlist" items="${timeArray}" varStatus="num">
                     <input type="checkbox" name="${tlist}" id="pick_checkbox" onclick="checkbarcode('${tlist}', this.checked,'b_checknox');">
                     <span class="mr20">${tlist}</span>
                    </c:forEach>
				</div>
				<div class="print-record">
					<span class="bt-border-style border-red" onclick="cancelWlogAll('b_checknox')">撤销拣货</span>
				</div>

				<div class="print-record">
					<span class="bt-border-style border-blue bt-small" onclick="autoWarehouse(0,${saleorder.saleorderId})">商品出库</span>
				</div>

		</div>
		</c:if>
		</c:if>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">出库记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid5">选择</th>
					<th class="wid5">序号</th>
					<th class="wid10">订货号</th>
					<th>产品名称</th>
					<th>品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid4">单位</th>
					<th>贝登条码</th>
					<th>厂商条码</th>
					<th>批次号</th>
					<th>出库数量</th>
					<th>出库时间</th>
					<th class="wid12">操作人</th>
				</tr>
			</thead>
			<tbody>
			 <c:set var="lockedStatusOutFlag" value="-1"></c:set>
				<c:forEach var="listout" items="${warehouseOutList}"
					varStatus="num3">
					<tr>
						<td>
						<c:if test="${listout.lockedStatus == 1}">
						  <font color="red">锁</font>
						 </c:if>
						 <c:if test="${listout.lockedStatus == 0}">
						 <c:set var="lockedStatusOutFlag" value="1"></c:set>
						 <input type="checkbox" name="c_checknox" alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listout.warehouseGoodsOperateLogId}" onclick="canelAll(this,'c_checknox')">
						 </c:if>
                        </td>
						<td>${num3.count}</td>
	                    <td>${listout.sku}</td>
						<td class="text-left">
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${newSkuInfosMap[listout.sku].SHOW_NAME}</a>
	                    </td>
						<td>${newSkuInfosMap[listout.sku].BRAND_NAME}<br />${newSkuInfosMap[listout.sku].MODEL}</td>
						<td>${newSkuInfosMap[listout.sku].MATERIAL_CODE}</td>
						<td>${newSkuInfosMap[listout.sku].UNIT_NAME}</td>
						<td>${ listout.barcode}</td>
						<td>${ listout.barcodeFactory}</td>
						<td>${ listout.batchNumber}</td>
						<td>${0-listout.num}</td>
						<td><date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ listout.opName}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty warehouseOutList}">
					<tr>
						<td colspan="13">暂无出库记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<c:if test="${lockedStatusOutFlag eq 1}">
		<c:if test="${not empty warehouseOutList}">
		<div class="table-style4">
			<div class="allchose">
			<input type="checkbox" name="all_c_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>
			</div>
			<div class="times">
				<span class="mr10">请选择批次</span>
				<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">
                 <input type="checkbox" name="${wtlist}" id="out_checkbox" onclick="checkbycode('${wtlist}', this.checked,'c_checknox');">
                 <span class="mr20">${wtlist}</span>
                </c:forEach>
			</div>
			<div class="print-record">
			<div>
				<form method="post" id="searchc" action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">
					    <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
						<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
						<input type="hidden"  name="bussinessType" id="bussinessType" value="496"/>
					    <input type="hidden"  name="wdlIds" id="wdlIds" value=""/>
					    <input type="hidden"  name="type_f" id="type_f" value=""/>
					   <!--  <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" id="searchSpan">打印出库单</span> -->
					   <c:if test="${HCType ne 1}">
					  	<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" id="searchSpan">打印出库单</span>
					    </c:if>
					    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" id="searchSpan">打印带效期出库单</span>
					    <c:if test="${HCType eq 1}">
					    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','2');" id="searchSpan">医械购出库单</span>
					    </c:if>
						<c:choose>
							<c:when test="${saleFlag}">
								<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','4');" id="searchSpan">科研购出库单</span>
								<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','5');" id="searchSpan">带价格出库单</span>
								<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','6');" id="searchSpan"> 不带价格出库单</span>
							</c:when>
							<c:otherwise>
								<input type="button" class="bt-border-style border-gray" onclick="alert('非销售人员不可打印出库单')" value="科研购出库单"/>
								<input type="button" class="bt-border-style border-gray" onclick="alert('非销售人员不可打印出库单')" value="带价格出库单"/>
								<input type="button" class="bt-border-style border-gray" onclick="alert('非销售人员不可打印出库单')" value="不带价格出库单"/>
							</c:otherwise>
						</c:choose>
					    <%-- <c:if test="${saleorder.isAftersale ==0}"> --%>
					      <span class="bt-border-style border-red" onclick="cancelWlogAll('c_checknox')">撤销出库</span>
					   <%--  </c:if> --%>
				</form>
				</div>
				<div>
				<form method="post" id="searchall" action="<%= basePath %>warehouse/warehousesout/printAllOutOrder.do">
					   <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
						<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
						<input type="hidden"  name="bussinessType" id="bussinessType" value="496"/>
					   <!--  <span class="bt-border-style border-blue" onclick="printAllOutOrder();" id="searchSpan">打印全部出库记录</span> -->
				</form>
				</div>
			</div>
		</div>
		</c:if>
		</c:if>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">备注信息</div>
			<div class="title-click nobor">
		        <span class="font-blue pop-new-data" layerParams='{"width":"47%","height":"30%","title":"新增消息","link":"./addOreditComments.do?saleorderId=${saleorder.saleorderId}"}'>新增</span>		
		    </div>
		</div>
		<table class="table">
			<thead>
				<tr>
				     <th class="">备注内容</th>
					<th class="wid10">备注人</th>
					<th class="wid20">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${listComments}" varStatus="num">
					<tr>
						<td>${list.comments }</td>
						<td>${list.opterName }</td>
						<td>
						<span class="font-blue pop-new-data" layerParams='{"width":"47%","height":"30%","title":"编辑","link":"./addOreditComments.do?saleorderWarehouseCommentsId=${list.saleorderWarehouseCommentsId}"}'>编辑</span>		
						<span class="font-red" style="cursor:pointer;"  onclick="delComments('${list.saleorderWarehouseCommentsId}');">删除</span>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty listComments}">
					<tr>
						<td colspan="3">暂无备注记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 物流信息
                </div>
                <c:if test="${(not empty warehouseBarcodeOutList) || (not empty warehouseOutList)}">
               <c:if test="${lockedStatusOutFlag eq 1}">
                <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress${saleorder.saleorderId}","link":"./warehouse/warehousesout/addExpress.do?saleorderId=${saleorder.saleorderId}&bussinessType=1","title":" 新增快递"}'>
                   	 新增快递
                </div>
                </c:if>
                </c:if>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid8">快递公司</th>
                        <th class="wid13">快递单号</th>
                        <th class="wid8">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th class="wid6">件数</th>
                        <th>备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th>
						<th class="wid12">打印出库单</th>
						<!-- add by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. start -->
						<th class="wid13">发票操作</th>
						<!-- add by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. end -->
                        <th class="wid27">操作</th>
                    </tr>
                </thead>
                <tbody id="wl">
                	<c:forEach var="express" items="${expressList}">
                     <tr>
                        <td>${express.logisticsName}</td>
                        <td>
                            ${express.logisticsNo}
                        </td>
                        
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
                            	<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div>
                            </c:forEach>
                        </td>
                        <td>${express.j_num}</td>
                        <td>${express.logisticsComments}</td>
                        <td>${express.updaterUsername}</td>
                        <td>
                            <c:if test="${express.arrivalStatus == 0}">
                        		未收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 1}">
                        		部分收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 2}">
                        		全部收货
                        	</c:if>
                        </td>
						<td>
							<c:if test="${HCType eq 1}">
								<span class="font-blue pop-new-data" layerParams='{"width":"90%","height":"90%","title":"医械购出库单",
								"link":"/warehouse/warehousesout/printOutOrder.do?expressId=${express.expressId}&type_f=3&orderId=${saleorder.saleorderId}&bussinessType=496&bussinessNo=${saleorder.saleorderNo}"}'>医械购出库单</span>
							</c:if>
							<c:choose>
								<c:when test="${logisticeFlag}">
                                    <c:if test="${saleorder.isPrintout > 0 && KYGorgFlag}">
									<span class="font-blue pop-new-data" layerParams='{"width":"90%","height":"90%","title":"科研购出库单",
								"link":"/warehouse/warehousesout/printOutOrder.do?expressId=${express.expressId}&type_f=7&orderId=${saleorder.saleorderId}&bussinessType=496&bussinessNo=${saleorder.saleorderNo}"}'>科研购出库单</span>
                                    </c:if>
                                    <c:if test="${!KYGorgFlag}">
                                        <button class="bt-border-style border-gray" onclick="alert('该订单不是科研购订单')">科研购出库单</button>
                                    </c:if>
                                    <c:if test="${KYGorgFlag && (saleorder.isPrintout <= 0)}">
                                        <button class="bt-border-style border-gray" onclick="alert('该订单不需要打印随货出库单')">科研购出库单</button>
                                    </c:if>
                                    <c:if test="${saleorder.isPrintout eq 1}">
									<span class="font-blue pop-new-data" layerParams='{"width":"90%","height":"90%","title":"带价格出库单",
								"link":"/warehouse/warehousesout/printOutOrder.do?expressId=${express.expressId}&type_f=8&orderId=${saleorder.saleorderId}&bussinessType=496&bussinessNo=${saleorder.saleorderNo}"}'>带价格出库单</span>
                                    </c:if>
                                    <c:if test="${saleorder.isPrintout ne 1 && (saleorder.isPrintout <= 0)}">
                                        <button class="bt-border-style border-gray" onclick="alert('该订单不需要打印随货出库单')">带价格出库单</button>
                                    </c:if>
									<c:if test="${saleorder.isPrintout ne 1 && (saleorder.isPrintout > 0)}">
										<button class="bt-border-style border-gray" onclick="alert('该订单不可打印带价格随货出库单')">带价格出库单</button>
									</c:if>
                                    <c:if test="${saleorder.isPrintout eq 2}">
									<span class="font-blue pop-new-data" layerParams='{"width":"90%","height":"90%","title":"不带价格出库单",
								"link":"/warehouse/warehousesout/printOutOrder.do?expressId=${express.expressId}&type_f=9&orderId=${saleorder.saleorderId}&bussinessType=496&bussinessNo=${saleorder.saleorderNo}"}'>不带价格出库单</span>
                                    </c:if>
                                    <c:if test="${saleorder.isPrintout ne 2&& (saleorder.isPrintout <= 0)}">
                                        <button class="bt-border-style border-gray" onclick="alert('该订单不需要打印随货出库单')">不带价格出库单</button>
                                    </c:if>
									<c:if test="${saleorder.isPrintout ne 2&& (saleorder.isPrintout > 0)}">
										<button class="bt-border-style border-gray" onclick="alert('该订单不可打印不带价格随货出库单')">不带价格出库单</button>
									</c:if>
								</c:when>
								<c:otherwise>
                                    <input type="button" class="bt-border-style border-gray" onclick="alert('非物流人员不可打印随货出库单')" value="科研购出库单"/>
                                    <input type="button" class="bt-border-style border-gray" onclick="alert('非物流人员不可打印随货出库单')" value="带价格出库单"/>
                                    <input type="button" class="bt-border-style border-gray" onclick="alert('非物流人员不可打印随货出库单')" value="不带价格出库单"/>
								</c:otherwise>
							</c:choose>
						</td>
						 <!-- add by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. start -->
						 <td>
							 <c:if test="${not empty express.invoiceApply}">
								 <c:set var="invoiceApply" value="${express.invoiceApply}"></c:set>
								 <c:choose>
									 <c:when test="${invoiceApply.validStatus eq 0}">
										 <!-- 审核中 未开票-->
										 <span  class="bt-smaller bt-border-style border-blue pop-new-data"   layerParams='{"width":"800px","height":"340px","title":"确认开票商品","link":"./viewAppliedItems.do?expressId=${express.expressId}"}'>开具电子发票</span>
										 <span class="pop-new-data delete"  layerparams='{"width":"600px","height":"220px","title":"确定驳回该开票申请吗？","link":"/finance/invoice/auditOpenInvoice.do?invoiceApplyId=${invoiceApply.invoiceApplyId}&isAdvance=0"}'>驳回</span>
									 </c:when>
									 <c:when test="${invoiceApply.validStatus eq 1}">
										 <!-- 审核通过 已开票-->
										 <c:forEach var="invoice" items="${express.invoiceList}" varStatus="index">
											 <c:if test="${empty invoice.invoiceNo}">
												 <span class="edit-user" onclick="batchDownEInvoice()">下载电子票-${index.count}</span>
											 </c:if>
											 <c:if test="${invoice.invoiceProperty eq 2 and not empty invoice.invoiceNo}">
												 <a href= "${invoice.invoiceHref}" target="_blank">查看发票-${index.count}</a><br />
											 </c:if>
										 </c:forEach>
									 </c:when>
								 </c:choose>
							 </c:if>
						 </td>
						 <!-- add by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. end -->

                          <td> 
                               <input type="hidden" id="companyId" value="${saleorder.companyId }">
                               <c:if test="${express.logisticsName != '中通快递'}">
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${saleorder.saleorderId} ',1,'${express.logisticsNo}','${express.expressId}','','',0)">打印</span>
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${saleorder.saleorderId }',1,'${express.logisticsNo}','${express.expressId}','','',0)">打印直发</span>
                               </c:if>
                               <c:if test="${express.logisticsName == '中通快递'}">
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${saleorder.saleorderId} ',1,'${express.logisticsNo}','${express.expressId}','','',1)">打印1</span>
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${saleorder.saleorderId} ',1,'${express.logisticsNo}','${express.expressId}','','',2)">打印2</span>
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${saleorder.saleorderId }',1,'${express.logisticsNo}','${express.expressId}','','',1)">打印直发1</span>
                               <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${saleorder.saleorderId }',1,'${express.logisticsNo}','${express.expressId}','','',2)">打印直发2</span>
                               </c:if>
                               <c:if test="${saleorder.isAftersale ==0}">
	                               <c:if test="${express.arrivalStatus != 2}">
	                                 <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./warehouse/warehousesout/editExpress.do?expressId=${express.expressId}&saleorderId=${saleorder.saleorderId}&flag=1&bussinessType=1&expressId=${express.isInvoicing}","title":" 编辑快递"}'>编辑</span>
	                               </c:if>
								   <!-- modified by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. start -->
	                               <button class="bt-smaller bt-border-style border-red " onclick="delwl('${express.expressId}',496,'${saleorder.saleorderId}','${express.logisticsName}','${express.logisticsNo}','${express.invoiceApply.validStatus}')">删除</button>
								   <!-- modified by Tomcat.Hui 2020/1/3 13:38 .Desc: VDERP-1039 票货同行. end -->
                               </c:if>
                               <div class="print-record" style="display:inline-block;">
									<form method="post" id="searchSh" action="<%= basePath %>warehouse/warehousesout/printShOutOrder.do">
										 <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
										 <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
										 <input type="hidden"  name="btype_sh" id="btype_sh" value=""/>
										 <input type="hidden"  name="expressId_xs" id="expressId_xs" value=""/>
										 <span class="bt-border-style border-blue" onclick="printSHOutOrder('${express.expressId}',496,${saleorder.saleorderId});" id="searchSpan">打印送货单</span>
									</form>
							   </div>
							    <div class="print-record" style="display:inline-block;">
							        <input type="hidden"  name="sendValue" id="sendValue" value="${express.sentSms }"/>
									<span  class="bt-border-style border-blue"  onclick="sendOutMsg(this)" layerParams='{"width":"500px","height":"240px","title":"操作确认","link":"./sendOutMsg.do?saleorderId=${saleorder.saleorderId}&logisticsName=${express.logisticsName}&logisticsNo=${express.logisticsNo}"}'>发送短信</span>
							   </div>
							    <div class="customername pos_rel">
		                            <div class="brand-color1">
		                            <i class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"40%","height":"420px","title":"查看物流","link":"./queryExpressInfo.do?logisticsNo=${express.logisticsNo}"}'>查看物流</i>
		                            	
		                            </div>
		                             <div class="pos_abs customernameshow mouthControlPos">
											最新信息：${express.contentNew}
									</div>
								</div>
                          </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="12" class="allchosetr text-left">
                        		<!-- 总运费 -->
                        		<c:set var="allamount" value="0.00"></c:set>
                        		<!-- 总数量 -->
                        		<c:set var="allarrivalnum" value="0"></c:set>
	                        	<c:forEach var="express" items="${expressList}">
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
	                        	<c:forEach var="bgv" items="${saleorderGoodsList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="warning-color1 mr10">${allamount}</span>已发/商品总数:<span class="warning-color1">${allarrivalnum}/${allnum}</span>
                        </td>
                    </tr>
                   </c:if>
                    <c:if test="${empty expressList}">
                     <tr>
                        <td colspan="11">暂无物流信息记录</td>
                    </tr>
                   </c:if>
                   
                </tbody>
            </table>
		<font color="red">因售后发生金额不准确的问题,如需打印,需要自行复制到本地,并编辑相关金额</font>
		<div class="table-friend-tip">
                                          友情提示
            <br/>1、已拣货未出库允许撤销拣货；
            <br/>2、已拣货已出库不允许撤销拣货；
        </div>
	</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>