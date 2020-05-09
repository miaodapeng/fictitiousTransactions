<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>


	<div class="main-container">
		<div id="selectTerminalInfo">

            <table class="table"  style="margin-bottom:0px;">
                <thead>
                    <tr>
						<th hidden="hidden">商品ID</th>
                        <th  class="wid16">产品名称</th>
                        <th>开票数量</th>
                        <th>单位</th>
                    	<th>本次开票金额</th>
                    	<th class="wid32"  <c:if test="${saleOrder.invoiceMethod ne 1}"> hidden="hidden"</c:if>>变更后开票产品名称</th>
                    </tr>
                </thead>
                <tbody  class="goodsOpt" id="table_items">
					<c:forEach items="${saleOrder.goodsList}" var="good" >
						<c:forEach items="${invoice.invoiceDetailList}" var="invoiceDetail" >
							<c:if test="${good.saleorderGoodsId eq invoiceDetail.detailgoodsId}">
								<tr name="data_tr">
									<td name="saleorderGoodsId" hidden="hidden">${good.saleorderGoodsId}</td>
									<td name="goodsName">${good.goodsName}</td>
									<td><fmt:formatNumber type="number" value="${invoiceDetail.num}" pattern="0.0000" maxFractionDigits="2" ></fmt:formatNumber></td>
									<td name="unitName">${good.unitName}</td>
									<td name="applyAmount">${invoiceDetail.totalAmount}</td>
									<td  <c:if test="${saleOrder.invoiceMethod ne 1}"> hidden="hidden"</c:if>>${invoiceDetail.changedGoodsName}</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
					<c:if test="${empty saleOrder.goodsList or empty invoice.invoiceDetailList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="<c:if test="${saleOrder.invoiceMethod ne 1}">4</c:if><c:if test="${saleOrder.invoiceMethod eq 1}">5</c:if>">暂无商品信息</td>
						</tr>
					</c:if>
                </tbody>
            </table>
    	</div>

        <div class='clear'></div>
         <div id="" class="mb15" style="margin-top: 12px;">
         	<div class="add-tijiao tcenter">
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
         </div>
    </div>
    <input type="hidden" name="formToken" value="${formToken}"/>

<%@ include file="../../common/footer.jsp"%>
