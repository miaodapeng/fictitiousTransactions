<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单录保卡" scope="application" />
<%@ include file="../../common/common.jsp"%>

<div class="main-container">
	<div class="list-page normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>产品名称</th>
                        <th>品牌</th>
                        <th>型号</th>
                        <th>贝登条码</th>
                        <th>厂商条码</th>
                        <th>录保卡时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
                		<c:when test="${not empty goodsWarrantys }">
                			<c:forEach items="${goodsWarrantys }" var="goodsWarranty" varStatus="st">
                				<c:choose>
                					<c:when test="${goodsWarranty.num > 1  }">
                						<c:forEach begin="1" end="${goodsWarranty.num}" step="1" varStatus="st1">
	                						<tr>
				                				<td class="text-left">
				                					<div class="brand-color1"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goodsWarranty.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsWarranty.goodsId}","title":"产品信息"}'>${goodsWarranty.goodsName }</a></div>
													<div>${goodsWarranty.sku }</div>
													<div>${goodsWarranty.materialCode }</div>
				                				</td>
				                				<td>${goodsWarranty.brandName }</td>
				                				<td>${goodsWarranty.model }</td>
				                				<td>${goodsWarranty.barcode }</td>
				                				<td>${goodsWarranty.barcodeFactory }</td>
				                				<td>
				                				<c:if test="${not empty goodsWarranty.warranties }">
		                							<c:forEach items="${goodsWarranty.warranties }" var="warranty" varStatus="stt">
		                							<c:if test="${st1.count == stt.count }">
					                					<date:date value ="${warranty.addTime}"/>
		                							</c:if>
		                							</c:forEach>
		                						</c:if>
				                				</td>
				                				<td>
				                					<c:choose>
				                						<c:when test="${not empty goodsWarranty.warranties }">
				                							<c:forEach items="${goodsWarranty.warranties }" var="warranty" varStatus="stt">
				                							<c:if test="${st1.count == stt.count }">
							                					<span class="edit-user addtitle"
																	tabTitle='{"num":"viewwarranty${warranty.saleorderGoodsWarrantyId }","link":"./order/saleorder/viewgoodswarranty.do?saleorderGoodsWarrantyId=${warranty.saleorderGoodsWarrantyId}","title":"查看保卡"}'>查看</span>
				                							</c:if>
				                							</c:forEach>
				                							<c:if test="${st1.count > goodsWarranty.warranties.size() }">
				                								<span class="edit-user addtitle"
																	tabTitle='{"num":"addwarranty${goodsWarranty.saleorderGoodsId}${goodsWarranty.warehouseGoodsOperateLogId }","link":"./order/saleorder/addgoodswarranty.do?saleorderGoodsId=${goodsWarranty.saleorderGoodsId}&warehouseGoodsOperateLogId=${goodsWarranty.warehouseGoodsOperateLogId }","title":"新增保卡"}'>新增</span>
				                							</c:if>
				                						</c:when>
				                						<c:otherwise>
						                					<span class="edit-user addtitle"
																tabTitle='{"num":"addwarranty${goodsWarranty.saleorderGoodsId}${goodsWarranty.warehouseGoodsOperateLogId }","link":"./order/saleorder/addgoodswarranty.do?saleorderGoodsId=${goodsWarranty.saleorderGoodsId}&warehouseGoodsOperateLogId=${goodsWarranty.warehouseGoodsOperateLogId }","title":"新增保卡"}'>新增</span>
				                						</c:otherwise>
				                					</c:choose>
				                				</td>
			                				</tr>
                						</c:forEach>
                					</c:when>
                					<c:otherwise>
		                				<tr>
			                				<td class="text-left">
			                					<div class="brand-color1"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goodsWarranty.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsWarranty.goodsId}","title":"产品信息"}'>${goodsWarranty.goodsName }</a></div>
												<div>${goodsWarranty.sku }</div>
												<div>${goodsWarranty.materialCode }</div>
			                				</td>
			                				<td>${goodsWarranty.brandName }</td>
			                				<td>${goodsWarranty.model }</td>
			                				<td>${goodsWarranty.barcode }</td>
			                				<td>${goodsWarranty.barcodeFactory }</td>
			                				<td>
			                				<c:if test="${not empty goodsWarranty.warranties }">
	                							<c:forEach items="${goodsWarranty.warranties }" var="warranty" varStatus="stt">
	                							<c:if test="${stt.count ==1}">
				                					<date:date value ="${warranty.addTime}"/>
	                							</c:if>
	                							</c:forEach>
	                						</c:if>
			                				</td>
			                				<td>
			                					<c:choose>
			                						<c:when test="${not empty goodsWarranty.warranties }">
			                							<c:forEach items="${goodsWarranty.warranties }" var="warranty" varStatus="stt">
			                							<c:if test="${stt.count == 1 }">
						                					<span class="edit-user addtitle"
																tabTitle='{"num":"viewwarranty${warranty.saleorderGoodsWarrantyId }","link":"./order/saleorder/viewgoodswarranty.do?saleorderGoodsWarrantyId=${warranty.saleorderGoodsWarrantyId}","title":"查看保卡"}'>查看</span>
			                							</c:if>
			                							</c:forEach>
			                						</c:when>
			                						<c:otherwise>
					                					<span class="edit-user addtitle"
															tabTitle='{"num":"addwarranty${goodsWarranty.saleorderGoodsId}${goodsWarranty.warehouseGoodsOperateLogId }","link":"./order/saleorder/addgoodswarranty.do?saleorderGoodsId=${goodsWarranty.saleorderGoodsId}&warehouseGoodsOperateLogId=${goodsWarranty.warehouseGoodsOperateLogId }","title":"新增保卡"}'>新增</span>
			                						</c:otherwise>
			                					</c:choose>
			                				</td>
		                				</tr>
                					</c:otherwise>
                				</c:choose>
                			</c:forEach>
                		</c:when>
                		<c:otherwise>
                			<tr>
		                        <td colspan="7">查询无结果！</td>
		                    </tr>
                		</c:otherwise>
                	</c:choose>
                </tbody>
            </table>
        </div>
</div>
<%@ include file="../../common/footer.jsp"%>