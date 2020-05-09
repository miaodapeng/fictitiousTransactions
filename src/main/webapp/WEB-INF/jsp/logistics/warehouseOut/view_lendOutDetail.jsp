<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="出库详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.layui-layer-iframe {
	width: 1000px !important;
	height: 500px !important;
} 
</style>
<div id='wrap'>
	<div class="main-container">
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">基本信息</div>
			</div>
			`
			<table class="table">
				<tbody>
					<tr>
						<td class="table-smaller">外借单号</td>
						<td>${lendout.lendOutNo}</td>
						<td class="table-smaller">外借状态</td>
						<td><c:choose>
								<c:when test="${lendout.lendOutStatus eq 0}">进行中</c:when>
								<c:when test="${lendout.lendOutStatus eq 1}">已完结</c:when>
								<c:when test="${lendout.lendOutStatus eq 2}">已关闭</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td>借用企业</td>
						<td>${lendout.traderName}</td>
						<td>借用原因</td>
						<td><c:choose>
								<c:when test="${lendout.lendOutType eq 1}">样品外借</c:when>
								<c:when test="${lendout.lendOutType eq 2}">售后垫货</c:when>
								<c:otherwise></c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<td>创建人</td>
						<td>${user.username}</td>
						<td>创建时间</td>
						<td><date:date value="${lendout.addTime}" /></td>
					</tr>
					<tr>
						<c:if test="${lendout.lendOutType eq 2}">
							<td>关联售后单</td>
							<td>${lendout.afterSalesNo}</td>
						</c:if>
						<td>预计归还时间</td>
						<td><date:date value="${returnTime}" /></td>
						<c:if test="${lendout.lendOutType ne 2}">
							<td></td>
							<td></td>
						</c:if>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">收货信息</div>
				<c:if test="${lendout.lendOutType eq 1 }">
					<%--  <span class="font-strange mr4"> <a class="edit-user addtitle"
				tabTitle='{"link":"/warehouse/warehousesout/searchLendOutDelivery.do?lendoutId=${lendout.lendOutId}","title":"编辑收货地址"}'>编辑</a>
			</span>  --%>
			<c:if test="${lendout.lendOutStatus eq 0}">
					<span class="font-strange pop-new-data mr4"
						layerParams='{"width":"600px","height":"200px","title":"编辑收货地址","link":"/warehouse/warehousesout/searchLendOutDelivery.do?lendoutId=${lendout.lendOutId}"}'>编辑</span>
			</c:if>
				</c:if>
			</div>
			<table class="table">
				<tbody>
					<tr>
						<td class="table-smaller">收货客户</td>
						<td>${trader.traderName}</td>
						<td class="table-smaller">收货联系人</td>
						<td id="tradercontact">${tradercontact.name}</td>
					</tr>
					<tr>
						<td>电话</td>
						<td><c:if test="${not empty tradercontact.telephone}">
								<i class="icontel cursor-pointer" title="点击拨号"
									onclick="callout('${tradercontact.telephone}',${lendout.traderId},${lendout.traderType},0,${lendout.lendOutId},${lendout.takeTraderContactId});"></i>
							</c:if> ${tradercontact.telephone}</td>
						<td>手机</td>
						<td><c:if test="${not empty tradercontact.mobile}">
								<i class="icontel cursor-pointer" title="点击拨号"
									onclick="callout('${tradercontact.mobile}',${lendout.traderId},${lendout.traderType},0,${lendout.lendOutId},${lendout.takeTraderContactId});"></i>
							</c:if> ${tradercontact.mobile}</td>
					</tr>
					<tr>
						<td>收货地区</td>
						<td>${area}</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>收货地址</td>
						<td colspan="3" id="traderAddress">${traderAddress.address}</td>
					</tr>
					<tr>
						<td>指定物流公司</td>
						<td><c:forEach var="list" items="${logisticsList}">
								<c:if test="${lendout.logisticsId == list.logisticsId}">${list.name}</c:if>
							</c:forEach></td>
						<td>运费说明</td>
						<td><c:forEach var="list" items="${freightDescriptions}">
								<c:if
									test="${lendout.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td>物流备注</td>
						<td colspan="3" class="warning-color1">${lendout.logisticsComments}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<c:set var="isUrgent" value="0"></c:set>
		<c:set var="isCold" value="0"></c:set>
		<c:forEach var="goods" items="${saleorderGoods}" varStatus="num">
			<c:if
				test="${(goods.num - goods.afterReturnNum>0) && goods.goodsId == '251526'}">
				<c:set var="isUrgent" value="1"></c:set>
			</c:if>
			<c:if
				test="${(goods.num - goods.afterReturnNum>0) && goods.goodsId == '256675'}">
				<c:set var="isCold" value="1"></c:set>
			</c:if>
		</c:forEach>

		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">产品信息</div>
			</div>
			<table class="table">

				<tr>
					<th class="wid5">选择</th>
					<th class="wid5">序号</th>
					<th class="wid10">订货号</th>
					<th>产品名称</th>
					<th class="wid10">品牌</th>
					<th class="wid10">型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid10">单位</th>
					<th class="">借用数量</th>
					<th>库位</th>
					<th>库存量</th>
					<th>出库数量</th>
					<th>归还数量</th>
					<th>可拣货库存</th>
				</tr>
				<tbody>
					<tr class="J-skuInfo-tr" skuId="${goods.goodsId}">
						<td><input type="checkbox" name="g_checknox"
							value="${goods.goodsId}" onclick="canelAll(this,'g_checknox')">
						</td>
						<td>1</td>
						<td class="JskuCode">${goods.sku}</td>
						<td class="text-left ">
							<div class="customername pos_rel">
                                 <a class="addtitle JskuName"
                                href="javascript:void(0);"
                                tabTitle='{"num":"viewgoods${goods.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}","title":"产品信息"}'>
                                ${goods.goodsName}

                                 </a> <i class="iconbluemouth"></i>
                                <div class="pos_abs customernameshow JskuInfo"   style="display: none;">
                                </div>
							</div>
						</td>
					<td class="JbrandName"> </td>
					<td  class="JskuModel"> </td>
					<td class="JmaterialCode"> </td>
						<td class="JskuUnit">${goods.unitName}</td>
						<td>${lendout.lendOutNum}</td>
						<%--库位--%>
						<c:if test="${storageLocation.get(goods.sku).storageLocationName != null}">
						<td>${storageLocation.get(goods.sku).storageLocationName}</td>
						</c:if>
						<c:if test="${storageLocation.get(goods.sku).storageLocationName == null}">
							<td>${storageLocation.get(goods.sku).comments}</td>
						</c:if>
						<%--库存量--%>
						<td class="JskuStock">${goodsStockNum}</td>
						<td>${lendout.deliverNum}</td>
						<td>${lendout.returnNum}</td>
						<td class="JskuAvailableStockNum">${goodsStockNum}</td>
					</tr>
				</tbody>
				</div>
			</table>
		</div>
		<c:if test="${lendout.lendOutStatus ne 2}">
			<div class="table-buttons">
				<%-- <button type="button" class="bg-light-blue bt-bg-style bt-small" onclick="viewLendOutPicking(${lendout.lendOutId});">拣货</button>
				  --%>
				 <c:if test="${houseoutNum ==0}"> 
				<button type="button" class="bg-light-blue bt-bg-style bt-small" onclick="closelendout(${lendout.lendOutId});">关闭外借单</button>
				</c:if> 
				<!-- <span class="bg-light-blue bt-bg-style bt-small" >查看入库条码</span> -->
				<c:if test="${lendout.barcodeNum > 0 }">
				<button type="button" class="bg-light-blue bt-bg-style bt-small addtitle" tabTitle='{"num":"addBarcode${lendout.lendOutId}_${lendout.goodsId}",
				"link":"./logistics/warehousein/addBarcode.do?goodsId=${lendout.goodsId}&afterSalesId=${lendout.lendOutId}&afterSalesGoodsId=${lendout.lendOutId}&type=4&businessType=9","title":"生成条形码"}'>查看入库条码</button>
				</c:if>
				<c:if test="${lendout.barcodeNum eq 0 }">
				<button type="button" class="bg-light-blue bt-bg-style bt-small" onclick="noBarcode();">查看入库条码</button>
				</c:if>
			<c:if test="${allPickCnt<goodsNum}">
				<c:choose>
					<c:when test="${tradercontact.name == null }">
					<span class="bg-light-blue bt-bg-style bt-small "  onclick="verify()" >拣货</span>
					</c:when>
					<c:when test="${traderAddress.address == null }">
					<span class="bg-light-blue bt-bg-style bt-small "  onclick="verify()">拣货</span>
					</c:when>
					<c:otherwise>
				 <span class="bg-light-blue bt-bg-style bt-small addtitle"  
					tabTitle='{"num":"jianhuo_${lendout.lendOutId}","link":"./warehouse/warehousesout/viewLendOutPicking.do?lendOutId=${lendout.lendOutId }&businessType=${businessType}","title":"开始拣货"}' onclick="verify()">拣货</span>
					</c:otherwise>
				</c:choose> 
  			 </c:if>
  			 <c:if test="${allPickCnt eq lendout.lendOutNum}">
				<c:if test="${allOutCnt < allPickCnt}">
				<span id="sm_db_span" class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"lendout_lendoutId_${lendout.lendOutId }","link":"./warehouse/warehousesout/warehouseSmLendOut.do?lendOutId=${lendout.lendOutId }&businessType=${businessType}","title":"扫码打包发货"}'>扫码出库</span>
				</c:if>
				</c:if>
			</div>
			</c:if>
		<input type="hidden" name="businessType" value="${businessType }">

		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">拣货记录</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th>订货号</th>
						<th>品牌/型号</th>
						<th class="wid10">物料编码</th>
						<th class="wid4">单位</th>
						<th>当次拣货数量</th>
						<th>拣货时间</th>
						<th class="wid8">操作人</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="listpick" items="${warehousePickList}"
						varStatus="num2">
						<tr>
							<td><c:if test="${listpick.cnt ==0}">
									<input type="checkbox" name="b_checknox" id="test"
										alt="<date:date value ="${listpick.addTime}" format="yyyy-MM-dd HH:mm:ss"/>"
										value="${listpick.warehousePickingId},${listpick.warehousePickingDetailId}"
										onclick="canelAll(this,'b_checknox')">
								</c:if> <c:if test="${ listpick.cnt >0}">
								</c:if></td>
							<td>${num2.count}</td>
							<td class="text-left"><a class="addtitle"
								href="javascript:void(0);"
								tabTitle='{"num":"viewgoods${listpick.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listpick.goodsId}","title":"产品信息"}'>${newSkuInfosMap[listpick.sku].SHOW_NAME}</a>
							</td>
							<td>${newSkuInfosMap[listpick.sku].SKU_NO}</td>
							<td>${newSkuInfosMap[listpick.sku].BRAND_NAME}<br />${newSkuInfosMap[listpick.sku].MODEL}</td>
							<td>${newSkuInfosMap[listpick.sku].MATERIAL_CODE}</td>
							<td>${newSkuInfosMap[listpick.sku].UNIT_NAME}</td>
							<td>${listpick.num}</td>
							<td><date:date value="${listpick.addTime}"
									format="yyyy-MM-dd HH:mm:ss" /></td>
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
			<c:if test="${not empty warehousePickList && pickFlag eq 1}">
				<div class="table-style4 ">
					<div class="allchose">
						<input type="checkbox" name="all_b_checknox"
							onclick="selectall(this,'pick_checkbox');" value="b_checknox">
						<span>全选</span>
					</div>
					<div class="times">
						<span class="mr10">请选择批次</span>
						<c:forEach var="tlist" items="${timeArray}" varStatus="num">
							<input type="checkbox" name="${tlist}" id="pick_checkbox"
								onclick="checkbarcode('${tlist}', this.checked,'b_checknox');">
							<span class="mr20">${tlist}</span>
						</c:forEach>
					</div>
					<div class="print-record">
						<span class="bt-border-style border-red"
							onclick="cancelWlogAll('b_checknox')">撤销拣货</span>
					</div>
				</div>
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
						<tr >
							<td><c:if test="${listout.lockedStatus == 1}">
									<font color="red">锁</font>
								</c:if> <c:if test="${listout.lockedStatus == 0}">
									<c:set var="lockedStatusOutFlag" value="1"></c:set>
									<input type="checkbox" name="c_checknox" class="J-select-item" data-num="${0-listout.num}"
										alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/>"
										value="${listout.warehouseGoodsOperateLogId}"
										onclick="canelAll(this,'c_checknox')">
								</c:if></td>
							<td>${num3.count}</td>
							<td>${newSkuInfosMap[listout.sku].SKU_NO}</td>
							<td class="text-left"><a class="addtitle"
								href="javascript:void(0);"
								tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${newSkuInfosMap[listout.sku].SHOW_NAME}</a>
							</td>
							<td>${newSkuInfosMap[listout.sku].BRAND_NAME}<br />${warehouseOutMap[listout.sku].MODEL}</td>
							<td>${newSkuInfosMap[listout.sku].MATERIAL_CODE}</td>
							<td>${newSkuInfosMap[listout.sku].UNIT_NAME}</td>
							<td>${ listout.barcode}</td>
							<td>${ listout.barcodeFactory}</td>
							<td>${ listout.batchNumber}</td>
							<td>${0-listout.num}</td>
							<td><date:date value="${listout.addTime}"
									format="yyyy-MM-dd HH:mm:ss" /></td>
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
							<input type="checkbox" name="all_c_checknox"
								onclick="selectall(this,'out_checkbox');" value="c_checknox">
							<span>全选</span>
						</div>
						<div class="times">
							<span class="mr10">请选择批次</span>
							<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">
								<input type="checkbox" name="${wtlist}" id="out_checkbox"
									onclick="checkbarcode('${wtlist}', this.checked,'c_checknox');">
								<span class="mr20">${wtlist}</span>
							</c:forEach>
						</div>
						<div class="print-record">
							<div>
								<form method="post" id="searchc"
									action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">
									<input type="hidden" name="orderId" id="orderId" value="${lendout.lendOutId }" /> 
									<input type="hidden" name="bussinessNo" id="bussinessNo" value="${lendout.lendOutNo }" /> 
									<input type="hidden"  id="kdNum" value="${kdNum }" /> 
									<input type="hidden"  id="houseoutNum" value="${houseoutNum }" /> 
									<input type="hidden" name="bussinessType" id="bussinessType" value="660" /> 
									<input type="hidden" name="wdlIds" id="wdlIds" value="" /> 
									<input type="hidden" name="type_f" id="type_f" value="" /> 
									<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" id="searchSpan">打印带效期出库单</span>
									 	<c:if test="${lendout.lendOutStatus != 1}"> 
										<span class="bt-border-style border-red"
										onclick="cancelWlogAll('c_checknox','3')">撤销出库</span>
									  </c:if> 
								</form>
							</div>
						</div>
					</div>
				</c:if>
			</c:if>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">归还产品信息</div>
			</div>
			<table class="table  table-style6">
				<thead>
					<tr>
						<th class="wid8">贝登条码</th>
						<th class="wid13">厂家条码</th>
						<th class="wid8">批次号</th>
						<th class="wid8">效期</th>
						<th class="wid6">归还数量</th>
						<th >存储位置</th>
						<th>仓存备注</th>
						<th class="wid10">操作人</th>
						<th class="wid10">操作时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${warehouseGoodsOperateLogList}">
						<tr>
						<td>${list.barcode}</td>
						<td>${list.barcodeFactory}</td>
						<td>${list.batchNumber}</td>
						<td><date:date value ="${list.expirationDate }" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${list.num}</td>
						<td>${list.warehouseArea}</td>
						<td>${list.comments}</td>
						<td>${list.operator}</td>
						<td><date:date value ="${list.addTime }" format="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">物流信息</div>
			<c:if
				test="${(not empty warehouseBarcodeOutList) || (not empty warehouseOutList)}">
				<c:if test="${lendout.lendOutStatus eq 0 && lockedStatusOutFlag eq 1}">
					<div class="title-click nobor addtitle"
						tabTitle='{"num":"addExpress${lendout.lendOutId}","link":"./warehouse/warehousesout/addExpress.do?saleorderId=${lendout.lendOutId}&bussinessType=3","title":" 新增快递"}'>
						新增快递</div>
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
					<th class="wid27">操作</th>
				</tr>
			</thead>
			<tbody id="wl">
				<c:forEach var="express" items="${expressList}">
					<tr>
						<td>${express.logisticsName}</td>
						<td>${express.logisticsNo}</td>

						<td><date:date value="${express.deliveryTime}"
								format="yyyy-MM-dd" /></td>
						<td><c:set var="amount" value="0.00"></c:set> <c:forEach
								var="expressDetails" items="${express.expressDetail}">
								<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
							</c:forEach> ${amount}</td>
						<td class="text-left"><c:forEach var="expressDetails"
								items="${express.expressDetail}">
								<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num}
									${expressDetails.unitName}</div>
							</c:forEach></td>
						<td>${express.j_num}</td>
						<td>${express.logisticsComments}</td>
						<td>${express.updaterUsername}</td>
						<td><c:if test="${express.arrivalStatus == 0}">
                        		未收货
                        	</c:if> <c:if test="${express.arrivalStatus == 1}">
                        		部分收货
                        	</c:if> <c:if test="${express.arrivalStatus == 2}">
                        		全部收货
                        	</c:if></td>
						<td><input type="hidden" id="companyId"
							value="1"> <%-- <c:if
								test="${express.logisticsName != '中通快递'}">
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','1','${lendout.lendOutId} ',9,'${express.logisticsNo}','${express.expressId}','','',0)">打印</span>
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','2','${lendout.lendOutId}',9,'${express.logisticsNo}','${express.expressId}','','',0)">打印直发</span>
							</c:if> <c:if test="${express.logisticsName == '中通快递'}">
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','1','${lendout.lendOutId} ',9,'${express.logisticsNo}','${express.expressId}','','',1)">打印1</span>
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','1','${lendout.lendOutId} ',9,'${express.logisticsNo}','${express.expressId}','','',2)">打印2</span>
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','2','${lendout.lendOutId}',9,'${express.logisticsNo}','${express.expressId}','','',1)">打印直发1</span>
								<span class="bt-smaller bt-border-style border-blue"
									onclick="printview('${express.logisticsName}','2','${lendout.lendOutId }',9,'${express.logisticsNo}','${express.expressId}','','',2)">打印直发2</span>
							</c:if>  --%>
								<c:if test="${express.arrivalStatus != 2}">
									<span class="bt-smaller bt-border-style border-blue addtitle"
										tabTitle='{"num":"editExpress","link":"./warehouse/warehousesout/editExpress.do?expressId=${express.expressId}&saleorderId=${lendout.lendOutId}&flag=1&bussinessType=3","title":" 编辑快递"}'>编辑</span>
								</c:if>
								<span class="bt-smaller bt-border-style border-red"
									onclick="delwl('${express.expressId}',660,${lendout.lendOutId})">删除</span>
							
							<div class="print-record" style="display: inline-block;">
								<form method="post" id="searchSh"
									action="<%= basePath %>warehouse/warehousesout/printShOutOrder.do">
									<input type="hidden" name="orderId" id="orderId"
										value="${lendout.lendOutId }" /> <input type="hidden"
										name="bussinessNo" id="bussinessNo"
										value="${lendout.lendOutNo }" /> <input type="hidden"
										name="btype_sh" id="btype_sh" value="" /> <input
										type="hidden" name="expressId_xs" id="expressId_xs" value="" />
									<span class="bt-border-style border-blue"
										onclick="printSHOutOrder('${express.expressId}',660,${lendout.lendOutId});"
										id="searchSpan">打印送货单</span>
								</form>
							</div>
							<%-- <div class="print-record" style="display: inline-block;">
								<input type="hidden" name="sendValue" id="sendValue"
									value="${express.sentSms }" /> <span
									class="bt-border-style border-blue" onclick="sendOutMsg(this)"
									layerParams='{"width":"500px","height":"240px","title":"操作确认","link":"./sendOutMsg.do?lendOutId=${lendout.lendOutId}&logisticsName=${express.logisticsName}&logisticsNo=${express.logisticsNo}"}'>发送短信</span>
							</div> --%>
							<div class="customername pos_rel">
								<div class="brand-color1">
									<i class="bt-smaller bt-border-style border-blue pop-new-data"
										layerParams='{"width":"40%","height":"420px","title":"查看物流","link":"./queryExpressInfo.do?logisticsNo=${express.logisticsNo}"}'>查看物流</i>

								</div>
								<div class="pos_abs customernameshow mouthControlPos">
									最新信息：${express.contentNew}</div>
							</div></td>
					</tr>
				</c:forEach>
				<c:if test="${!empty expressList}">
					<tr>
						<td colspan="10" class="allchosetr text-left">
							<!-- 总运费 --> <c:set var="allamount" value="0.00"></c:set> <!-- 总数量 -->
							<c:set var="allarrivalnum" value="0"></c:set> <c:forEach
								var="express" items="${expressList}">
								<c:set var="amount" value="0.00"></c:set>
								<c:set var="arrivalnum" value="0"></c:set>
								<c:forEach var="expressDetails" items="${express.expressDetail}">
									<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
									<c:set var="arrivalnum"
										value="${arrivalnum + expressDetails.num}"></c:set>
								</c:forEach>
								<c:set var="allamount" value="${allamount + amount}"></c:set>
								<c:set var="allarrivalnum" value="${allarrivalnum + arrivalnum}"></c:set>
							</c:forEach> <c:set var="allnum" value="0"></c:set> <c:forEach var="bgv"
								items="${saleorderGoodsList}" varStatus="num">
								<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
							</c:forEach> 运费总额：<span class="warning-color1 mr10">${allamount}</span>已发/商品总数:<span
							class="warning-color1">${allarrivalnum}/${lendout.lendOutNum}</span>
						</td>
					</tr>
				</c:if>
				<c:if test="${empty expressList}">
					<tr>
						<td colspan="10">暂无物流信息记录</td>
					</tr>
				</c:if>

			</tbody>
		</table>

		<div class="table-friend-tip">
			友情提示 <br />1、已拣货未出库允许撤销拣货； <br />2、已拣货已出库不允许撤销拣货；
		</div>
	</div>
</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewLendOut.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
		src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>