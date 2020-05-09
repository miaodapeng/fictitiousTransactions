<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="手动打包发货" scope="application" />
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
					<th class="wid10">订单号</th>
					<th class="wid15">生效时间</th>
					<th class="wid15">付款时间</th>
					<th class="wid20">客户名称</th>
					<th class="">销售部门</th>
					<th class="wid7">归属销售</th>
					<th class="wid8">订单总额</th>
					<th class="">付款金额</th>
					<th>商品总数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${saleorder.saleorderId}","title":"订单信息"}'>${saleorder.saleorderNo}</a></td>
					<td><date:date value="${saleorder.validTime}" /></td>
					<td><date:date value="${saleorder.paymentTime}" /></td>
					<td>
					<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${saleorder.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${saleorder.traderId}", "title":"客户信息"}'>${saleorder.traderName}</a>
					</td>
					<td>${saleorder.salesDeptName}</td>
					<td>${saleorder.optUserName}</td>
					<td>${saleorder.totalAmount}</td>
					<td>${saleorder.accountPayable+saleorder.paymentAmount }</td>
					<td>${goodsNum }</td>
				</tr>
				<tr>
				   <td>物流备注</td>
				   <td colspan="8" class="warning-color1" style="text-align: left;">${saleorder.logisticsComments}</td>
				</tr>
			</tbody>
		</table>
	</div>
        <input type="hidden" id="goodsListNum" value="${fn:length(saleorder.goodsList)}"/>
		<div class="parts table-style10-1 ">
			<div class="title-container">
				<div class="table-title ">出库产品信息</div>
			</div>
			 <c:forEach var="list" items="${saleorder.goodsList}" varStatus="num">
			<table class="table  table-style10">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th class="wid30">品牌/型号</th>
						<th class="wid20">单位</th>
						<th class="wid10">拣货未发数量</th>
						<th class="wid10">出库数量</th>
					</tr>
				</thead>
				<tbody>
				  
					<tr>
						<td>${num.count}</td>
						<td class="text-left">
							<div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
	                          <input type="hidden" id="goodsId${num.count}" value="${list.goodsId}"/>
	                        </div>
	                        <div>${list.sku}</div>
						</td>
						<td>${list.brandName}${list.model}</td>
						<td>${list.unitName}</td>
						<td style="display: none;"><input  type="text" style="display: none;" id="jh${num.count}" value="${list.pickCnt-list.deliveryNum}"></input></td>
						<td >${list.pickCnt-list.deliveryNum}</td>
						<td style="display: none;"><input type="text" style="display: none;" id="ch${num.count}" value=""></input></td>
						<td><input type="text" disabled="disabled" name="pickNumpickCnt${num.count }" id="pickNumpickCnt${num.count }"  value="0"></td>
					</tr>
					
					<tr>
						<td colspan="6" class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th class="wid12">出库数量/总数</th>
										<th class="wid15">效期截止日期</th>
										<th class="wid15">入库时间</th>
										<th>批次号</th>
										<th>存储位置</th>
										<th>仓存备注</th>
										<th class="wid10">关联采购单</th>
										<th>采购单价</th>
									</tr>
								</thead>
								<tbody>
								   <input type="hidden" id="wlistNum${num.count}" value="${fn:length(list.wlist)}"/>
								   <c:forEach var="listw" items="${list.wlist}" varStatus="n">
									<tr>
										<td class="jianhuozongshu"><input type="text" name="pickCnt${num.count }" id="pickCnt${num.count }${n.count }" value="0" oninput="this.value=this.value.replace(/\D/g,'').replace(/^0+(?=\d)/,'')"/>
											<span>/</span> <span id="pickCnt${num.count }${n.count }Num">${listw.cnt - listw.outGoodsCnt}</span></td>
										<td><date:date value="${listw.expirationDate }" /></td>
										<td><date:date value="${listw.addTime }" /></td>
										<td>${listw.batchNumber }</td>
										<td>${listw.warehouseArea }</td>
										<td>${listw.comments }</td>
										<td><a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${listw.buyorderId}","title":"订单信息"}'>${listw.buyorderNo}</a></td>
										<td>${listw.price }</td>
									</tr>
									</c:forEach>
									<c:if test="${empty list.wlist}">
				                     <tr>
				                        <td colspan="8">暂无可出库商品</td>
				                    </tr>
				                   </c:if>
								</tbody>
							</table>
						</td>
					</tr>
					
				</tbody>
			</table>
			</c:forEach>
			<div class="table-buttons">
				<form method="post" id="search" action="<%= basePath %>warehouse/warehousesout/warehouseEnd.do">
			    <input type="hidden"  name="saleorderId" id="saleorderId" value="${saleorder.saleorderId }"/>
			    <input type="hidden"  name="pickNums" id="pickNums" value=""/>
			    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="searchOut();" id="searchSpan">确认出库</span>
			</form>
			</div>
		</div>
	</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/index_outGoods.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>