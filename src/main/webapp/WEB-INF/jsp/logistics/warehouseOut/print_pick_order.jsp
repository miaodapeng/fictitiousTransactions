<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印拣货单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<div id="printdiv">
			 <c:forEach var="list" items="${warehousePickList}" varStatus="num">
			<table class="table  table-style10">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th class="wid15">品牌</th>
						<th class="wid15">型号</th>
						<th class="">物料编码</th>
						<th class="">数量单位</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${num.count}</td>
						<td class="text-left">
	                        <div >
	                          ${list.goodsName}
	                        </div>
	                    </td>
						<td>${list.brandName}</td>
						<td>${list.model}</td>
						<td>${list.materialCode}</td>
						<td>${list.num}${list.unitName}</td>
					</tr>
					<tr>
						<td colspan="6" class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th class="wid12">拣货数</th>
										<th class="wid15">效期截止日期</th>
										<th class="wid15">入库时间</th>
										<th>批次号</th>
										<th>存储位置</th>
										<th>仓存备注</th>
									</tr>
								</thead>
								<tbody>
								   <c:forEach var="listw" items="${list.pdList}" varStatus="num2">
									<tr>
										<td>${listw.num}</td>
										<td><date:date value="${listw.expirationDate }" /></td>
										<td><date:date value="${listw.addTime }" /></td>
										<td>${listw.batchNumber }</td>
										<td>${listw.warehouseArea }</td>
										<td>${listw.comments }</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					
				</tbody>
			</table>
			</c:forEach>
			</div>
			<div class="table-buttons">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="preview('printdiv')" id="searchSpan">打印</span>
			</div>
			
<script type="text/javascript" src='<%= basePath %>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>