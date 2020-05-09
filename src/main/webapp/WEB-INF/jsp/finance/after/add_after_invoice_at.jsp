<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后新增发票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/after/add_after_invoice_at.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips5">
	<form method="post" action="<%=basePath%>finance/after/saveAfterOpenInvoiceAt.do" id="addAfterInvoiceAt">
		<input type="hidden" name="relatedId" value="${afterSalesGoodsVo.afterSalesId}"/><!-- 售后主键 -->
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>发票号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class="input-middle" name="invoiceNo" id="invoiceNo" onkeyup="vailInvoiceNo(this);"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>票种</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<c:forEach var="invoiceList" items="${invoiceTypeList}">
							<c:if test="${invoiceList.sysOptionDefinitionId eq afterSalesGoodsVo.invoiceType}">
								${invoiceList.title}
								<input type="hidden" name="invoiceType" value="${afterSalesGoodsVo.invoiceType}"/><!-- 发票类型 -->
								<input type="hidden" name="ratio" value="${invoiceList.comments}"/><!-- 发票税率 -->
								<fmt:parseNumber value="${invoiceList.comments}" type="number" var="ratio" />
							</c:if>
						</c:forEach>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>红蓝字</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="radio" checked>蓝字有效
						<input type="hidden" name="colorType" value="2"/><!-- 红蓝字 1红2蓝 -->
						<input type="hidden" name="isEnable" value="1"/><!-- 是否有效 0否 1是 -->
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>发票金额</lable>
				</div>
				<div class="f_left f_left_wid90">
					<div class="form-blanks">
						<span id="invoiceAmount"><fmt:formatNumber type='number' value='${afterSalesGoodsVo.price - afterSalesGoodsVo.invoiceAmount}' pattern='0.00' maxFractionDigits='2'/></span>
						<input type="hidden" name="amount" id="amount"/><!-- 发票金额 -->
					</div>
					<div class="mt10">
						<table class="table">
							<thead>
								<tr>
									<th>产品名称</th>
									<th>单价</th>
									<th>数量</th>
									<th>总额</th>
									<th>开票单价</th>
									<th>开票数量/已开票数量</th>
									<th>开票金额/已开票金额</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${afterSalesGoodsVo.goodsName}</td>
									<td><fmt:formatNumber type="number" value="${afterSalesGoodsVo.price}" pattern="0.00" maxFractionDigits="2" /></td>
									<td><fmt:formatNumber type="number" value="${afterSalesGoodsVo.num}" pattern="0.00" maxFractionDigits="2" /></td>
									<td>
										<fmt:parseNumber value="${afterSalesGoodsVo.price * afterSalesGoodsVo.num}" type="number" var="amount" />
										<fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										
										<fmt:formatNumber type="number" value="${amount/(ratio+1)}" pattern="0.00000000" maxFractionDigits="8" />
										<input type="hidden" name="price" value="<fmt:formatNumber type='number' value='${amount/(ratio+1)}' pattern='0.00000000' maxFractionDigits='8' />"/>
									</td>
									<td>
										<input  type="hidden" id="max_num" value="${afterSalesGoodsVo.num - afterSalesGoodsVo.invoiceNum}"/>
										<input type="text" style="width: 60px;" id="in_num" name="num" onBlur="invoiceNumChange(this,${afterSalesGoodsVo.price});" onkeyup="value=value.replace(/[^\d.]/g,'');" value="<fmt:formatNumber type='number' value='${afterSalesGoodsVo.num - afterSalesGoodsVo.invoiceNum}' pattern='0.00' maxFractionDigits='2'/>"/>
										&nbsp;/&nbsp;
										<fmt:formatNumber type="number" value="${afterSalesGoodsVo.invoiceNum==null?0:afterSalesGoodsVo.invoiceNum}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<input  type="hidden" id="max_price" value="${afterSalesGoodsVo.price - afterSalesGoodsVo.invoiceAmount}"/>
										<input type="text" style="width: 60px;" id="in_price" name="totalAmount" onBlur="invoicePriceChange(this,${afterSalesGoodsVo.price});" onkeyup="value=value.replace(/[^\d.]/g,'');" value="<fmt:formatNumber type='number' value='${afterSalesGoodsVo.price - afterSalesGoodsVo.invoiceAmount}' pattern='0.00' maxFractionDigits='2'/>"/>
										&nbsp;/&nbsp;
										<fmt:formatNumber type="number" value="${afterSalesGoodsVo.invoiceAmount==null?0:afterSalesGoodsVo.invoiceAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao text-left f_left_wid90 " style='margin:-10px 0 0 110px;'>
			<input type="hidden" name="detailgoodsId" value="${afterSalesGoodsVo.afterSalesGoodsId}"/>
            <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="saveAfterOpenInvoiceAt();">提交</button>
        </div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>
