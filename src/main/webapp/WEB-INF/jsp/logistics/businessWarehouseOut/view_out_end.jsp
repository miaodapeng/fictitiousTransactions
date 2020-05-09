<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="出库完成" scope="application" />
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
							<div class="arriveThisStep hasArrive">
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
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">出库产品信息</div>
			</div>
			<table class="table" id="jh_table">
				<thead>
					<tr>
						<th class="wid5">选择</th>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th>品牌/型号</th>
						<th class="wid15">物料编码</th>
						<th class="wid12">单位</th>
						<th>本次出库数量</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="listout" items="${warehouseOutList}"
					varStatus="num3">
					<tr>
						<td>
                            <input type="checkbox" name="c_checknox" alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd"/>" value="${listout.warehouseGoodsOperateLogId}" onclick="canelAll(this,'c_checknox')">
                        </td>
						<td>${num3.count}</td>
						<td class="text-left">
	                        <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${listout.goodsName}</a>
	                        </div>
	                        <div>${listout.sku}</div>
	                    </td>
						<td>${listout.brandName}<br />${listout.model}</td>
						<td>${listout.materialCode}</td>
						<td>${listout.unitName}</td>
						<td>${0-listout.num}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty warehouseOutList}">
					<tr>
						<td colspan="7">暂无出库记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="table-style4">
			<div class="allchose">
			<input type="checkbox" name="all_c_checknox" onclick="selectall(this);" value="c_checknox"> <span>全选</span>
			</div>
			<div class="print-record">
			    <form method="post" id="searchc" action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">
				    <input type="hidden"  name="orderId" id="orderId" value="${afterSales.orderId }"/>
				    <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${afterSales.afterSalesNo }"/>
				     <input type="hidden"  name="bussinessType" id="bussinessType" value="${afterSales.type }"/>
				    <input type="hidden"  name="wdlIds" id="wdlIds" value=""/>
				    <input type="hidden"  name="type_f" id="type_f" value=""/>
				    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" id="searchSpan">打印出库单</span>
				    <span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" id="searchSpan">打印带效期出库单</span>
			    </form>
			</div>
		</div>
	</div>
		<div class="tcenter">
			 <span class="bt-bg-style bg-light-blue bt-small addtitle"
					tabTitle='{"num":"warehousesout_index_${afterSales.afterSalesId}","link":"./warehouse/businessWarehouseOut/viewBusinessWdetail.do?afterSalesId=${afterSales.afterSalesId}&businessType=${businessType }","title":"新增快递"}'>发货结束，点击新增快递单</span>
		</div>
		<div><br/></div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>