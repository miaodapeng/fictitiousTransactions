<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>


	<div class="main-container">
		<div id="selectTerminalInfo">

			<table class="table" id="invoiceInfo">
				<tbody>
					<tr>
						<td>
							<div class="form-list" style="padding-bottom:0; margin-bottom:-7px;">
								<input type="hidden" name="formToken" value="${formToken}"/>
								<input type="hidden" name="saleorderId" id="saleorderId" value="${invoice.relatedId}"/>
								<ul>
									<li>
										<div class="form-tips">
											<lable>发票类型：</lable>
										</div>
										<div class="f_left">
											<div class="form-blanks">
												<c:forEach var="list" items="${invoiceTypes}">
													<c:if test="${list.sysOptionDefinitionId eq saleOrder.invoiceType}">
														<lable>${list.title}</lable>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</li>
									<li>
										<div class="form-tips">
											<lable>开票方式：</lable>
										</div>
										<div class="f_left">
											<div class="form-blanks">
												<c:if test="${saleOrder.invoiceMethod == 1}"><lable>手动纸质开票</lable></c:if>
												<c:if test="${saleOrder.invoiceMethod == 2}"><lable>自动纸质开票</lable></c:if>
												<c:if test="${saleOrder.invoiceMethod == 3}"><lable>自动电子发票</lable></c:if>
											</div>
										</div>
									</li>
								</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			<input type="hidden" value="${saleOrder.isOpenInvoice}" id="isAdvanceTax">
			<input type="hidden" value="${saleOrder.invoiceMethod}" id="invoiceMethod">
			<input type="hidden" value="${saleOrder.invoiceType}" id="invoiceType">

			<span id="span_isAdvanceTax" style="margin-bottom: 12px;float:none">
				<c:if test="${saleOrder.meetInvoiceConditions == 1}">注：订单款项已满足开票条件</c:if>
				<c:if test="${empty saleOrder.meetInvoiceConditions or saleOrder.meetInvoiceConditions != 1}"><font color="red">注：订单款项未满足开票条件</font></c:if>
			</span>

            <table class="table"  style="margin-bottom:0px;">
                <thead>
                    <tr>
						<th hidden="hidden">商品ID</th>
                        <th  class="wid16">产品名称</th>
                        <th>数量</th>
                        <th>单位</th>
                        <th>单价</th>
                        <th>总额</th>
                    	<th>已申请数量</th>
                    	<th>满足开票条件数量</th>
                    	<th>本次申请</th>
                    	<th>本次开票金额</th>
                    	<th class="wid32" <c:if test='${saleOrder.invoiceMethod ne 1}'>hidden="hidden"</c:if>>变更后开票产品名称</th>
                    </tr>
                </thead>
                <tbody  class="goodsOpt" id="table_items">
					<c:set var="alreadyAppliedAmount" value="0.00"></c:set>
					<c:set var="toApplyAmount" value="0.00"></c:set>
					<c:set var="appliedNum" value="0"></c:set>
					<c:set var="appliedAmount" value="0.00"></c:set>
					<c:set var="total_alreadyAppliedAmount" value="0.00"></c:set>
					<c:forEach items="${saleOrder.goodsList}" var="good" varStatus="status_good">
						<c:set var="total_alreadyAppliedAmount" value="${good.appliedAmount + good.invoicedAmount + total_alreadyAppliedAmount}"></c:set>
						<c:forEach items="${applyRecord.invoiceApplyDetails}" var="apply" varStatus="status_apply">
							<c:if test="${good.saleorderGoodsId eq apply.detailgoodsId}">
								<tr name="data_tr">
									<td name="saleorderGoodsId" hidden="hidden">${good.saleorderGoodsId}</td>
									<td name="goodsName">
										<c:if test="${list.isActionGoods eq 1}"><span style="color:red;">【活动】</span></c:if>
											${newSkuInfosMap[good.sku].SHOW_NAME}
									</td>
									<td>${good.num - good.afterReturnNum}</td>
									<td name="unitName">${newSkuInfosMap[good.sku].UNIT_NAME}</td>
									<td name="price">${good.price}</td>
									<td><fmt:formatNumber type="number" value="${saleOrder.orderType ne 5 ? (good.price * (good.num - good.afterReturnNum)) : (good.maxSkuRefundAmount - good.afterReturnAmount)}" pattern="0.0000" maxFractionDigits="2" /></td>
									<td>${good.appliedNum + good.invoicedNum}</td>
									<td>${good.canInvoicedNum}</td>
									<td name="td_nums"><c:if test='${good.appliedNum + good.invoicedNum > good.canInvoicedNum}'><font color="red">[!]</font></c:if>${apply.num}</td>
									<td name="applyAmount">${apply.totalAmount}</td>
									<td <c:if test='${saleOrder.invoiceMethod ne 1}'>hidden="hidden"</c:if>>${apply.changedGoodsName}</td>
								</tr>
                                <c:set var="alreadyAppliedAmount" value="${alreadyAppliedAmount + good.appliedAmount + good.invoicedAmount}"></c:set>
                                <c:set var="toApplyAmount" value="${toApplyAmount + (good.num - good.afterReturnNum - good.invoicedNum - good.appliedNum) * good.price}"></c:set>
                                <c:set var="appliedNum" value="${appliedNum + apply.num}"></c:set>
                                <c:set var="appliedAmount" value="${appliedAmount + apply.totalAmount}"></c:set>
							</c:if>
						</c:forEach>
					</c:forEach>
					<c:if test="${empty saleOrder.goodsList or empty applyRecord.invoiceApplyDetails}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="<c:if test='${saleOrder.invoiceMethod ne 1}'>9</c:if><c:if test='${saleOrder.invoiceMethod eq 1}'>10</c:if>">暂无商品信息</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="<c:if test='${saleOrder.invoiceMethod ne 1}'>9</c:if><c:if test='${saleOrder.invoiceMethod eq 1}'>10</c:if>" style="text-align:center; background: #eaf2fd;">
							已申请开票金额：<fmt:formatNumber type="number" value="${total_alreadyAppliedAmount}" pattern="0.00" maxFractionDigits="2" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							未申请开票金额：<fmt:formatNumber type="number" value="${saleorderDataInfo.realAmount - total_alreadyAppliedAmount}" pattern="0.00" maxFractionDigits="2" />
							&nbsp;&nbsp;
							【本次开票申请数量：<span id="apply_totalNum"><fmt:formatNumber type="number" value="${appliedNum}" pattern="0.00" maxFractionDigits="2" /></span>
							&nbsp;&nbsp;
							本次开票申请金额：<span id="apply_totalAmount"><fmt:formatNumber type="number" value="${appliedAmount}" pattern="0.00" maxFractionDigits="2" /></span>】
						</td>
					</tr>
                </tbody>
            </table>
    	</div>
    
    	<div class="searchfunc"  style="margin-bottom: -8px;">
			<ul>
				<c:if test="${applyRecord.isAdvance == 1}">
					<li style="float:none">
						<div class="infor_name">
						  <span>*</span>
						  <label>提前开票原因</label>
						</div>
						<div class='f_left inputfloat'>
						<div>
							<input type="text" disabled="disabled" class="input-larger" id="advanceInvoiceReason"  style="margin-right:10px;" value="${applyRecord.comments}">
						</div>
						<div id="advanceTaxWarn"></div>
						</div>
					</li>
				</c:if>
				<c:if test="${applyRecord.isAdvance == 0 and not empty applyRecord.comments}">
					<li style="float:none">
						<div class="infor_name">
							<label>开票备注</label>
						</div>
						<div class='f_left inputfloat'>
							<div>
								<input type="text" disabled="disabled" class="input-larger" id="invoiceComments"  value="${applyRecord.comments}" style="margin-right:10px;">
							</div>
						</div>
					</li>
				</c:if>
			</ul>
		</div>

		<div class="pop-friend-tips">
			说明：<br />
			1. 本次申请开票的数量，默认带入“满足开票条件数量-已申请数量”
			<br />
			2.本次申请开票的数量，不得大于可申请数量 【可申请数量=商品数量-已申请开票数量】
			<br />
			3.满足开票条件数量：指订单款项满足条件的前提下，“已发货数量、已收货数量” 中的较小值
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
