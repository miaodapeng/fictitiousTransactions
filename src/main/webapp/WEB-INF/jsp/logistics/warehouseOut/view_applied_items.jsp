<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>

	<div class="main-container">
		<div id="selectTerminalInfo">
			<input type="hidden" id="invoiceApplyId_input" value="${invoiceApply.invoiceApplyId}">
			<input type="hidden" id="saleorderId_input" value="${invoiceApply.relatedId}">
			<input type="hidden" name="formToken" value="${formToken}"/>
            <table class="table"  style="margin-bottom:0px;">
                <thead>
                    <tr>
						<th>订货号</th>
                        <th  class="wid16">产品名称</th>
                        <th>单位</th>
                        <th>单价</th>
						<th>开票数量</th>
                    	<th>本次开票金额</th>
                    </tr>
                </thead>
                <tbody  class="goodsOpt" id="table_items">
					<c:set var="totalNum" value="0.00"></c:set>
					<c:set var="totalAmount" value="0.00"></c:set>
					<c:forEach items="${invoiceApply.invoiceApplyDetails}" var="detail" >
						<tr name="data_tr">
							<td name="sku">${detail.sku}</td>
							<td name="goodsName">${detail.goodsName}</td>
							<td name="unitName">${detail.unitName}</td>
							<td><fmt:formatNumber type="number" value="${detail.price}" pattern="0.0000" maxFractionDigits="2" ></fmt:formatNumber></td>
							<td name="unitName">${detail.num}</td>
							<td name="applyAmount">${detail.totalAmount}</td>
						</tr>
						<c:set var="totalNum" value="${totalNum + detail.num}"></c:set>
						<c:set var="totalAmount" value="${totalAmount + detail.totalAmount}"></c:set>
					</c:forEach>
					<c:if test="${empty invoiceApply or empty invoiceApply.invoiceApplyDetails}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="6">暂无商品信息</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="6" style="text-align:center; background: #eaf2fd;">
							本次开票数量：<span id="apply_totalNum">${totalNum}</span>
							&nbsp;&nbsp;
							本次开票金额：<fmt:formatNumber type="number" value="${totalAmount}" pattern="0.00" maxFractionDigits="2" />
						</td>
					</tr>
                </tbody>
            </table>
    	</div>

		<div class="searchfunc"  style="margin-bottom: -8px;">
			<ul>
				<div id="comments">
					<li style="float:none">
						<div class="infor_name">
							<span>*</span>
							<label>开票备注</label>
						</div>
						<div class='f_left inputfloat'>
							<div>
								<input type="text" class="input-larger" id="invoiceComments"  value="${invoiceApply.comments}" style="margin-left:10px;" disabled="true">
							</div>
							<div id="advanceTaxWarn"></div>
						</div>
					</li>
				</div>
			</ul>
		</div>

        <div class='clear'></div>
         <div id="" class="mb15" style="margin-top: 12px;">
         	<div class="add-tijiao tcenter">
                <button class="dele" type="button" id="close-layer">取消</button>
				<button type="submit" onclick="EInvoiceCheck('${invoiceApply.invoiceApplyId}','${invoiceApply.relatedId}','${formToken}')">确认提交</button>
            </div>
         </div>
    </div>


<%@ include file="../../common/footer.jsp"%>
