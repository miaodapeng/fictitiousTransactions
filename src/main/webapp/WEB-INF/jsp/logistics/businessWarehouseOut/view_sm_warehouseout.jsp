<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="扫码出库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
							<div class="arriveThisStep hasArrive">
								<i class="iconwarehousecheck"></i>
							</div>
							<div>仓库拣货</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep hasArrive">
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
							<div>新增快递</div>
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
				    <td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${afterSales.afterSalesId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${afterSales.afterSalesId}&traderType=1","title":"售后详情"}'>${afterSales.afterSalesNo}</a></td>
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
		<!-- 订单下的商品 -->
		<div id="googsListDiv">
            <c:choose>
                <c:when test="${businessType eq 546}">
                    <c:forEach var="list" items="${afterSales.afterSalesGoodsList}" varStatus="num">
                        <c:if test="${(list.num - list.deliveryNum)<0}">
                            <input id="${list.goodsId}" type="hidden" value="0" data-flag="${list.isActionGoods}"/>
                        </c:if>
                        <c:if test="${(list.num - list.deliveryNum)>=0}">
                            <input id="${list.goodsId}" type="hidden" value="${list.num - list.deliveryNum}" data-flag="${list.isActionGoods}"/>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="list" items="${afterSales.afterSalesGoodsList}" varStatus="num">
                        <c:if test="${(list.arrivalNum - list.deliveryNum)<0}">
                            <input id="${list.goodsId}" type="hidden" value="0" data-flag="${list.isActionGoods}"/>
                        </c:if>
                        <c:if test="${(list.arrivalNum - list.deliveryNum)>=0}">
                            <input id="${list.goodsId}" type="hidden" value="${list.arrivalNum - list.deliveryNum}" data-flag="${list.isActionGoods}"/>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
		</div>

		<div class="parts table-style10-1 ">
			<div class="title-container">
				<div class="table-title ">扫码出库</div>
			</div>
			<table class="table  table-style10">
				<tbody>
					<tr>
					<td>
                                                                    请使用扫码枪扫描&nbsp;&nbsp;<input  id="smgoods"></input>				
                    </td>		
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">条码出库记录</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid5">序号</th>
					<th>产品名称</th>
					<th>品牌/型号</th>
					<th class="wid4">单位</th>
					<th>贝登条码</th>
					<th>厂商条码</th>
					<th class="wid8">出库数量</th>
					<th >还需出库数量</th>
					<th class="wid10">采购单价</th>
					<th class="wid8">销售单价</th>
					<th class="wid">操作</th>
				</tr>
			</thead>
			<tbody id="outRecode">
				<tr id="blank">
						<td colspan="11" >暂无出库记录</td>
					</tr>
			</tbody>
		</table>
		<div class="table-buttons">
				<form method="post" id="search" action="<%= basePath %>warehouse/businessWarehouseOut/warehouseSMEnd.do">
			    <%--  <input type="hidden" name="formToken" value="${formToken}"/> --%>
			    <input type="hidden"  name="afterSalesId" id="afterSalesId" value="${afterSales.afterSalesId }"/>
			     <input type="hidden"  name="businessType" id="businessType" value="${businessType }"/>
			    <input type="hidden"  name="bussinessType" id="bussinessType" value="${businessType }"/>
			    <input type="hidden"  name="scenesPageType" id="scenesPageType" value="1"/>
			    <input type="hidden"  name="idCnt" id="idCnt" value=""/>
			    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search_sm();" id="searchSpan">确认出库</span>
			</form>
			</div>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">本次拣货出库对照表</div>
		</div>
		<table class="table" id="pickTable">
			<thead>
				<tr>
					<th class="wid5">序号</th>
					<th>产品名称</th>
					<th>品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid4">单位</th>
					<th class="wid14">已拣货数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="listBarcode" items="${warehouseBarcodeOutList}"
					varStatus="num4">
					<tr>
						<td>${num4.count}</td>
						<td class="text-left">
	                        <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listBarcode.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listBarcode.goodsId}","title":"产品信息"}'>${listBarcode.goodsName}</a>
	                        </div>
	                        <div>${listBarcode.sku}</div>
	                    </td>
						<td>${listBarcode.brandName}<br />${listBarcode.model}</td>
						<td>${listBarcode.materialCode}</td>
						<td>${listBarcode.unitName}</td>
						<td>${listBarcode.num}</td>
						<td style="display: none;">${listBarcode.goodsId}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty warehouseBarcodeOutList}">
					<tr>
						<td colspan="6">暂无出库记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/index_sm.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>