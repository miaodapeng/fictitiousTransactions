<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="业务出库详情" scope="application" />
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
					<th class="wid10">业务单号</th>
					<th class="wid15">生效时间</th>
					<th>客户名称</th>
					<th class="wid8">创建者</th>
					<th>商品总数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				    <td>
				    <input type="hidden" id="companyId" value="${afterSales.companyId}">
				     <c:if test="${(afterSales.type eq 540) or (afterSales.type eq 539)}">
				      <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${afterSales.afterSalesId}&traderType=1","title":"售后详情"}'>${afterSales.afterSalesNo}</a>
				     </c:if>
				     <c:if test="${(afterSales.type eq 546) or (afterSales.type eq 547)}">
				      <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${afterSales.afterSalesId}&traderType=2","title":"售后详情"}'>${afterSales.afterSalesNo}</a>
				     </c:if>
				    </td>
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
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">收货信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smaller">收货客户</td>
					<td>${afterSales.traderName}</td>
					<td class="table-smaller">收货联系人</td>
					<td>${afterSales.traderContactName}</td>
				</tr>
				<tr>
					<td>电话</td>
					<td><c:if
							test="${not empty afterSales.traderContactTelephone}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${afterSales.traderContactTelephone}',${afterSales.traderId},1,2,${afterSales.saleorderId},${afterSales.traderContactId});"></i>
						</c:if> ${afterSales.traderContactTelephone}</td>
					<td>手机</td>
					<td><c:if
							test="${not empty afterSales.traderContactMobile}">
							<i class="icontel cursor-pointer" title="点击拨号"
								onclick="callout('${afterSales.traderContactMobile}',${afterSales.traderId},1,2,${afterSales.saleorderId},${afterSales.traderContactId});"></i>
						</c:if> ${afterSales.traderContactMobile}</td>
				</tr>
				<tr>
					<td>收货地区</td>
					<td>${afterSales.area}</td>
					 <c:if test="${(afterSales.type eq 540) or (afterSales.type eq 539)}">
					<td>发货方式</td>
					<td><c:choose>
							<c:when test="${afterSales.deliveryType eq 481}">
								一次发货
							</c:when>
							<c:when test="${afterSales.deliveryType eq 482}">
								多次发货
							</c:when>
						</c:choose></td>
					</c:if>
					<c:if test="${(afterSales.type eq 546) or (afterSales.type eq 547)}">
					<td></td><td></td>
					</c:if>
				</tr>
				<tr>
					<td>收货地址</td>
					<td colspan="3">${afterSales.address}</td>
				</tr>
				<tr>
					<td>指定物流公司</td>
					<td><c:forEach var="list" items="${logisticsList}">
							<c:if test="${afterSales.logisticsId == list.logisticsId}">${list.name}</c:if>
						</c:forEach></td>
					<td>运费说明</td>
					<td><c:forEach var="list" items="${freightDescriptions}">
							<c:if
								test="${afterSales.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
						</c:forEach></td>
				</tr>
				<tr>
					<td>物流备注</td>
					<td colspan="3">${afterSales.logisticsComments}</td>
				</tr>
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
					<th>产品名称</th>
					<th>订货号</th>
					<th class="wid10">品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid10">单位</th>
					<th>库位</th>
					<th>库存量</th>
					<th>总数</th>
					<th>已发</th>
					<!-- <th>拣货未发数量</th> -->
					<th><!-- 需拣货量/ -->可拣货库存</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${afterSales.afterSalesGoodsList}" varStatus="num">
					<tr>
					   <td>
					        <c:if test="${list.deliveryStatus != 2}">
	                       		<input type="checkbox" name="g_checknox"  value="${list.goodsId}" onclick="canelAll(this,'g_checknox')">
	                       	</c:if>
                       	</td>
						<td>${num.count}</td>
						<td class="text-left">
							<c:choose>
								<c:when test="${list.isActionGoods > 0}">
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'><font color="red">[活动]</font>${list.goodsName}</a>
								</c:when>
								<c:otherwise>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td>${list.sku}</td>
						<td>${list.brandName}<br />${list.model}</td>
						<td>${list.materialCode}</td>
						<td>${list.unitName}</td>
							<%--库位--%>
						<c:if test="${storageLocation.get(goods.sku).storageLocationName != null}">
							<td>${storageLocation.get(goods.sku).storageLocationName}</td>
						</c:if>
						<c:if test="${storageLocation.get(goods.sku).storageLocationName == null}">
							<td>${storageLocation.get(goods.sku).comments}</td>
						</c:if>
							<%--库存量--%>
						<td>${stockInfo.get(list.sku).stockNum}</td>
						<td>${list.num}</td>
						<td>${list.deliveryNum}</td>
						<%-- <td>
						${list.pickCnt-list.deliveryNum }
						</td> --%>
						<td>
						<%-- ${list.num - list.pickCnt  }/ --%><%--${list.totalNum}--%>
							<c:choose>
								<c:when test="${list.isActionGoods > 0}">
									${list.totalNum} <%--活动商品可拣货库存量 = 库存量--%>
								</c:when>
								<c:otherwise>
									${list.totalNum-list.actionLockCount}  <%--  普通商品可拣货库存量=库存量-活动锁定库存 --%>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty afterSales.afterSalesGoodsList}">
					<tr>
						<td colspan="10">暂无记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
			<div class="table-style4 " style="margin-left: 17px;">
					<div class="allchose">
					    <input type="checkbox" name="all_g_checknox" onclick="selectall(this,'');" value="g_checknox"> <span>全选</span>
					</div>
			</div>
			<c:if test="${allPickCnt eq 0}">
			<div class="table-buttons">
				 <span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
				 <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
						layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"${pageContext.request.contextPath}/warehouse/warehousesout/batchAddWarehouseOut.do?orderId=${afterSales.afterSalesId}&businessType=${afterSales.type}"}'>批量出库</button>
				<span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_afterSalesId_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/viewBusinessPicking.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"开始拣货"}'>拣货</span>
			</div>
			</c:if>
			<c:if test="${0<allPickCnt && allPickCnt<goodsNum}">
			<div class="table-buttons">
			<span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
			 <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
					layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"${pageContext.request.contextPath}/warehouse/warehousesout/batchAddWarehouseOut.do?orderId=${afterSales.afterSalesId}&businessType=${afterSales.type}"}'>批量出库</button>
				<span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_afterSalesId_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/viewBusinessPicking.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"开始拣货"}'>拣货</span>
				<c:if test="${allOutCnt < allPickCnt}">
				     <%-- <span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_afterSalesId_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/viewBsOutDetail.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"开始出库"}'>手动出库</span> --%>
				       <span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_warehouseSmOut_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/warehouseSmOut.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"扫码打包发货"}'>扫码出库</span>
				</c:if>
			</div>
			</c:if>
			<c:if test="${allPickCnt eq goodsNum}">
				<c:if test="${allOutCnt < allPickCnt}">
					<div class="table-buttons">
					<span class="bg-light-blue bt-bg-style bt-small " onclick="exportOutBarcodeList();">导出未出库条码</span>
			 <button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
					layerparams='{"width":"500px","height":"200px","title":"批量出库","link":"${pageContext.request.contextPath}/warehouse/warehousesout/batchAddWarehouseOut.do?orderId=${afterSales.afterSalesId}&businessType=${afterSales.type}"}'>批量出库</button>
						 <%-- <span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_afterSalesId_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/viewBsOutDetail.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"开始出库"}'>手动出库</span> --%>
					      <span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_warehouseSmOut_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/warehouseSmOut.do?afterSalesId=${afterSales.afterSalesId }&businessType=${businessType }","title":"扫码打包发货"}'>扫码出库</span>
					</div>
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
						<td>
						    <c:if test="${listpick.cnt ==0}">
                        		<input type="checkbox" name="b_checknox" alt="<date:date value ="${listpick.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listpick.warehousePickingId},${listpick.warehousePickingDetailId}" onclick="canelAll(this,'b_checknox')">
                        	</c:if>
                        	<c:if test="${ listpick.cnt >0}">
                        	</c:if>
							<input type="hidden" name="batchNumber" id="batchNumber" value="${listpick.batchNumber}">
							<input type="hidden" name="expirationDate" id="expirationDate" value="${listpick.expirationDate}">
							<input type="hidden" name="goodsId" id="goodsId" value="${listpick.goodsId}">
							<input type="hidden" name="num" id="num" value="${listpick.num}">
                            <input type="hidden" name="formToken" id="formToken" value="${formToken}">
							<input type="hidden" name="warehousePickingDetailId" id="warehousePickingDetailId" value="${listpick.warehousePickingDetailId}">
                        </td>
						<td>${num2.count}</td>
						<td class="text-left">
	                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listpick.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listpick.goodsId}","title":"产品信息"}'>${listpick.goodsName}</a>
	                    </td>
	                    <td>${listpick.sku}</td>
						<td>${listpick.brandName}<br />${listpick.model}</td>
						<td>${listpick.materialCode}</td>
						<td>${listpick.unitName}</td>
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
				<span class="bt-border-style border-blue bt-small" onclick="autoWarehouse(${businessType},${afterSales.afterSalesId})">商品出库</span>
                <input type="hidden" name="bussinessType" id="bussinessType" value="${afterSales.type}">
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
					<th>产品名称</th>
					<th>订货号</th>
					<th>品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid4">单位</th>
					<th>贝登条码</th>
					<th>厂商条码</th>
					<th>出库数量</th>
					<th>出库时间</th>
					<th class="wid8">操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="listout" items="${warehouseOutList}"
					varStatus="num3">
					<tr>
						<td>
                            <input type="checkbox" name="c_checknox" alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listout.warehouseGoodsOperateLogId}" onclick="canelAll(this,'c_checknox')">
                        </td>
						<td>${num3.count}</td>
						<td class="text-left">
	                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${listout.goodsName}</a>
	                    </td>
	                    <td>${listout.sku}</td>
						<td>${listout.brandName}<br />${listout.model}</td>
						<td>${listout.materialCode}</td>
						<td>${listout.unitName}</td>
						<td>${ listout.barcode}</td>
						<td>${ listout.barcodeFactory}</td>
						<td>${0-listout.num}</td>
						<td><date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${ listout.opName}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty warehouseOutList}">
					<tr>
						<td colspan="12">暂无出库记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<c:if test="${not empty warehouseOutList}">
		<div class="table-style4">
			<div class="allchose">
			<input type="checkbox" name="all_c_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>
			</div>
			<div class="times">
				<span class="mr10">请选择批次</span>
				<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">
                 <input type="checkbox" name="${wtlist}" id="out_checkbox" onclick="checkbarcode('${wtlist}', this.checked,'c_checknox');">
                 <span class="mr20">${wtlist}</span>
                </c:forEach>
			</div>
			<div class="print-record">
			 <div>
				<form method="post" id="searchc" action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">
					    <input type="hidden"  name="orderId" id="orderId" value="${afterSales.afterSalesId }"/>
					    <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${afterSales.afterSalesNo }"/>
					     <input type="hidden"  name="bussinessType" id="bussinessType" value="${afterSales.type }"/>
					    <input type="hidden"  name="wdlIds" id="wdlIds" value=""/>
					    <input type="hidden"  name="type_f" id="type_f" value=""/>
					    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" id="searchSpan">打印出库单</span>
					    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" id="searchSpan">打印带效期出库单</span>
					    <span class=" bt-border-style border-red" onclick="cancelWlogAll('c_checknox','4')">撤销出库</span>
				</form>
			</div>
			<div>
			    <form method="post" id="searchall" action="<%= basePath %>warehouse/warehousesout/printAllOutOrder.do">
					   <input type="hidden"  name="orderId" id="orderId" value="${afterSales.afterSalesId }"/>
						<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${afterSales.afterSalesNo }"/>
						<input type="hidden"  name="bussinessType" id="bussinessType" value="${afterSales.type }"/>
					    <span class="bt-border-style border-blue" onclick="printAllOutOrder();" id="searchSpan">打印全部出库记录</span>
				</form>
			</div>
		</div>
		</c:if>
	</div>
	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 物流信息
                </div>
                <c:if test="${(not empty warehouseBarcodeOutList) || (not empty warehouseOutList)}">
                <c:if test="${afterSales.type==546 or afterSales.type == 547}">
                 <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress${afterSales.afterSalesId }","link":"./warehouse/warehousesout/addExpress.do?bussinessId=${afterSales.afterSalesId }&bussinessType=2&shType=${afterSales.type }","title":" 新增快递"}'>
                   	 新增快递
                </div>
                </c:if>
                <c:if test="${afterSales.type==540}">
                <div class="title-click nobor addtitle" tabTitle='{"num":"addExpress${afterSales.afterSalesId }","link":"./warehouse/warehousesout/addExpress.do?bussinessId=${afterSales.afterSalesId }&bussinessType=2&shType=${afterSales.type }","title":" 新增快递"}'>
                   	 新增快递
                </div>
                </c:if>
                
                </c:if>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid10">快递公司</th>
                        <th class="wid10">快递单号</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th>件数</th>
                        <th>备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="wl">
                	<c:forEach var="express" items="${expressList}">
                     <tr>
                        <td>${express.logisticsName}</td>
                        <td>${express.logisticsNo}</td>
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
                               <c:if test="${afterSales.type==546 or afterSales.type == 547}">
                                <c:if test="${express.logisticsName != '中通快递'}">
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','2','${afterSales.afterSalesId }',0)">打印</span>
                                </c:if>
                               <c:if test="${express.logisticsName == '中通快递'}">
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','2','${afterSales.afterSalesId }',1)">打印1</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','2','${afterSales.afterSalesId }',2)">打印2</span>
                                </c:if>
                                <c:if test="${express.arrivalStatus != 2}">
                                <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./warehouse/warehousesout/editExpress.do?expressId=${express.expressId}&bussinessId=${afterSales.afterSalesId }&bussinessType=2&flag=1&shType=${afterSales.type }","title":" 编辑快递"}'>编辑</span>
                                </c:if>
                               </c:if>
                                <c:if test="${afterSales.type==540}">
                                <c:if test="${express.logisticsName != '中通快递'}">
                                <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',0)">打印</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${afterSales.orderId }',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',0)">打印直发</span>
                                </c:if>
                                 <c:if test="${express.logisticsName == '中通快递'}">
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',1)">打印1</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','1','${afterSales.orderId} ',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',2)">打印2</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${afterSales.orderId }',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',1)">打印直发1</span>
                                 <span class="bt-smaller bt-border-style border-blue" onclick="printview('${express.logisticsName}','2','${afterSales.orderId }',4,'${express.logisticsNo}','${express.expressId}','1','${afterSales.afterSalesId }',2)">打印直发2</span>
                                 </c:if>
                                 <c:if test="${express.arrivalStatus != 2}">
                                 <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./warehouse/warehousesout/editExpress.do?expressId=${express.expressId}&bussinessId=${afterSales.afterSalesId }&bussinessType=2&flag=1&shType=${afterSales.type }","title":" 编辑快递"}'>编辑</span>
                                 </c:if>
                               </c:if>
                               <span class="bt-smaller bt-border-style border-red" onclick="delwl('${express.expressId}')">删除</span>
                               <div class="print-record">
									<form method="post" id="searchSh" action="<%= basePath %>warehouse/warehousesout/printShOutOrder.do">
										 <input type="hidden"  name="orderId" id="orderId" value="${afterSales.afterSalesId }"/>
										 <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${afterSales.afterSalesNo }"/>
										 <input type="hidden"  name="btype_sh" id="btype_sh" value=""/>
										 <input type="hidden"  name="expressId_xs" id="expressId_xs" value=""/>
										 <span class="bt-border-style border-blue" onclick="printSHOutOrder('${express.expressId}',540,${afterSales.afterSalesId},0);" id="searchSpan">打印送货单</span>
									</form>
							   </div>
                          </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="10" class="allchosetr text-left">
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
	                        	<c:forEach var="bgv" items="${afterSales.afterSalesGoodsList}" varStatus="num">
		                        		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
		                        </c:forEach>
                            	 运费总额：<span class="warning-color1 mr10">${allamount}</span>已发/商品总数:<span class="warning-color1">${allarrivalnum}/${allnum}</span>
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
                                          友情提示
            <br/>1、已拣货未出库允许撤销拣货；
            <br/>2、已拣货已出库不允许撤销拣货；
        </div>
	</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>