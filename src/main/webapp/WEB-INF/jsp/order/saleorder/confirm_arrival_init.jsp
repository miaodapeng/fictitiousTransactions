<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认收货" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content mt10 ">
		<form method="post" id="addBhSaleorderForm" action="./confirmArrival.do">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">产品信息</div>
            </div>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid15">产品名称</th>
                        <th class="wid8">品牌</th>
                        <th class="wid8">型号</th>
                        <th class="wid8">单价</th>
                        <th class="wid5">数量</th>
                        <th class="wid7">单位</th>
                        <th class="wid10">总额</th>
                        <th class="wid6">直发</th>
                        <th class="wid8">产品备注</th>
                        <th class="wid8">内部备注</th>
                        <th class="wid8">采购状态</th>
                        <th class="wid8">收货状态</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="num" value="0"></c:set>
					<c:set var="id_str" value=""></c:set>
					<c:set var="isNotDelPriceZero" value="0"></c:set>
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<%-- <c:if test="${list.isDelete eq 0 && list.deliveryDirect eq 1}">   直发运费也能确认收货--%>
                		<c:if test="${list.isDelete eq 0 && list.deliveryDirect eq 1 && list.goodsId ne 127063 && list.goodsId ne 256675 && list.goodsId ne 251526 && list.goodsId ne 253620  && list.goodsId ne 251462 && list.goodsId ne 140633 }">
	                		<c:set var="num" value="${num+1}"></c:set>
	                		<c:set var="id_str" value="${id_str}_${list.saleorderGoodsId}"></c:set>
	                		
		                    <tr>
		                        <td>${num}</td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
		                                <c:choose>
											<c:when test="${list.isDelete eq 1}">
												<span>${list.goodsName}<br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
											</c:when>
											<c:otherwise>
												<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
				                                <div class="pos_abs customernameshow">
												物料编码：${list.goods.materialCode} <br> 
												注册证号：${list.registrationNumber} <br>
												管理类别：${list.manageCategoryName} <br>
												产品负责人：<c:if test="${not empty list.goods.userList }">
															<c:forEach items="${list.goods.userList }" var="user" varStatus="st">
																${user.username } 
															<c:if test="${st.count != list.goods.userList.size() }">、</c:if>
															</c:forEach>
														</c:if> <br> 
												采购提醒：${list.goods.purchaseRemind} <br>
												包装清单：${list.goods.packingList} <br> 
												服务条款：${list.goods.tos} <br>
												库存：${list.goods.stockNum} <br>
												可用库存：${list.goods.stockNum-list.goods.orderOccupy} <br>
												订单占用：${list.goods.orderOccupy}<br> 
												可调剂：${list.goods.adjustableNum} <br> 
												审核状态：---
												</div>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${list.brandName}</td>
		                        <td>${list.model}</td>
		                        <td>${list.price}</td>
		                        <td>${list.num}</td>
		                        <td>${list.unitName}</td>
		                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>
		                            <div class="customername pos_rel">
		                                <span>
		                                <c:choose>
											<c:when test="${list.deliveryDirect eq 0}">否</c:when>
											<c:otherwise>
											是
											<i class="iconbluesigh ml4 contorlIcon"></i></span>
			                                <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${list.goodsComments}</td>
		                        <td>${list.insideComments}</td>
		                        <td>
		                        	<c:if test="${list.buyorderStatus eq null || list.buyorderStatus eq 0}">未采购</c:if>
		                        	<c:if test="${list.buyorderStatus eq 1}">部分采购</c:if>
		                        	<c:if test="${list.buyorderStatus eq 2}">全部采购</c:if>
		                        </td>
		                        <td>
	                        		<select style="width:100%;" name="arrivalStatus_${list.saleorderGoodsId}">
		                        		<c:if test="${list.buyorderStatus eq 0 || list.buyorderStatus eq 1}">
		                        			<option <c:if test="${list.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
		                        		</c:if>
		                        		<c:if test="${list.buyorderStatus eq 2}">
		                        			<option <c:if test="${list.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
											<option <c:if test="${list.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
		                        		</c:if>
									</select>
									
		                        </td>
		                    </tr>
	                    
	                    </c:if>
	                    
	                    <%-- <c:if test="${list.isDelete eq 0 && (list.goodsId eq 127063 || list.goodsId eq 256675 || list.goodsId eq 251526 || list.goodsId == 253620 || list.goodsId == 251462 || list.goodsId == 140633)}">
	                		<c:set var="num" value="${num+1}"></c:set>
	                		<c:set var="id_str" value="${id_str}_${list.saleorderGoodsId}"></c:set>
	                		
		                    <tr>
		                        <td>${num}</td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
		                                <c:choose>
											<c:when test="${list.isDelete eq 1}">
												<span>${list.goodsName}<br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
											</c:when>
											<c:otherwise>
												<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
				                                <div class="pos_abs customernameshow">
												物料编码：${list.goods.materialCode} <br> 
												注册证号：${list.registrationNumber} <br>
												管理类别：${list.manageCategoryName} <br>
												产品负责人：<c:if test="${not empty list.goods.userList }">
															<c:forEach items="${list.goods.userList }" var="user" varStatus="st">
																${user.username } 
															<c:if test="${st.count != list.goods.userList.size() }">、</c:if>
															</c:forEach>
														</c:if> <br> 
												采购提醒：${list.goods.purchaseRemind} <br>
												包装清单：${list.goods.packingList} <br> 
												服务条款：${list.goods.tos} <br>
												库存：${list.goods.stockNum} <br>
												可用库存：${list.goods.stockNum-list.goods.orderOccupy} <br>
												订单占用：${list.goods.orderOccupy}<br> 
												可调剂：${list.goods.adjustableNum} <br> 
												审核状态：---
												</div>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${list.brandName}</td>
		                        <td>${list.model}</td>
		                        <td>${list.price}</td>
		                        <td>${list.num}</td>
		                        <td>${list.unitName}</td>
		                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>
		                            <div class="customername pos_rel">
		                                <span>
		                                <c:choose>
											<c:when test="${list.deliveryDirect eq 0}">否</c:when>
											<c:otherwise>
											是
											<i class="iconbluesigh ml4 contorlIcon"></i></span>
			                                <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${list.goodsComments}</td>
		                        <td>${list.insideComments}</td>
		                        <td>
		                        	<c:if test="${list.buyorderStatus eq null || list.buyorderStatus eq 0}">未采购</c:if>
		                        	<c:if test="${list.buyorderStatus eq 1}">部分采购</c:if>
		                        	<c:if test="${list.buyorderStatus eq 2}">全部采购</c:if>
		                        </td>
		                        <td>
									<select style="width:100%;" name="arrivalStatus_${list.saleorderGoodsId}">
                        				<option <c:if test="${list.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
										<option <c:if test="${list.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
									</select>
		                        </td>
		                    </tr>
	                    
	                    </c:if> --%>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="saleorderId" id="saleorderId" value="${saleorder.saleorderId}">
        	<input type="hidden" name="id_str" value="${id_str}">
            <button type="submit" id="apply_payment_submit">提交</button>
        </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>