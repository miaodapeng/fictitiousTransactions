<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后新增发票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/after/add_after_invoice_tp.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips5" id="addAfterInvoiceTp">
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
						<c:if test="${invoiceList.sysOptionDefinitionId eq invoice.invoiceType}">
							${invoiceList.title}
							<input type="hidden" id="invoiceType" value="${invoice.invoiceType}"/><!-- 发票类型 -->
							<input type="hidden" id="ratio" value="${invoiceList.comments}"/><!-- 发票税率 -->
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
					<span id="invoiceAmount">0.00</span>
				</div>
				<div class="mt10">
					<table class="table">
						<thead>
							<tr>
								<th class="wid6">选择</th>
								<th class="wid20">产品名称</th>
								<th class="wid10">品牌</th>
								<th class="wid10">型号</th>
								<th class="wid8">单价</th>
								<th class="wid8">数量</th>
								<th class="wid10">总额</th>
								<th class="wid12">开票单价</th>
								<th class="wid18">开票数量/已开票数量</th>
								<th class="wid18">开票金额/已开票金额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${invoice.invoiceDetailList}" varStatus="num">
								<tr>
									<td><input type="checkbox" id="checkTpName${num.index}" name="checkTpName" alt="${num.index}" value="${list.detailgoodsId}" onclick="selectCheck(this,${num.index});"></td>
									<td>
										<div class="brand-color1">
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
										</div>
										<div>${list.sku}</div>
									</td>
									<td>${list.brandName}</td>
									<td>${list.model}</td>
									<td>
										<fmt:formatNumber type="number" value="${list.price}" pattern="0.00" maxFractionDigits="2" />
										<input type="hidden" id="price${num.index}" value="${list.price}"/>
									</td>
									<td><fmt:formatNumber type="number" value="${list.num}" pattern="0.00" maxFractionDigits="2" /></td>
									<td>
										<fmt:parseNumber value="${list.price * list.num}" type="number" var="amount" />
										<fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<fmt:formatNumber type="number" value="${amount/(ratio+1)}" pattern="0.00000000" maxFractionDigits="8" />
										<input type="hidden" id="ratio_price${num.index}" value="<fmt:formatNumber type='number' value='${amount/(ratio+1)}' pattern='0.00000000' maxFractionDigits='8' />"/>
									</td>
									<td>
										<input type="hidden" id="max_num${num.index}" value="${list.num - list.enterNum}"/>
										<input type="text" style="width: 80px;" id="in_num${num.index}" name="in_num" alt="${num.index}" onBlur="invoiceNumChange(${num.index},${list.num - list.enterNum},${list.price});" onkeyup="value=value.replace(/[^\d.]/g,'');" value="<fmt:formatNumber type='number' value='${list.num - list.enterNum}' pattern='0.00' maxFractionDigits='2'/>"/>
										&nbsp;/&nbsp;
										<fmt:formatNumber type="number" value="${list.enterNum==null?0:list.enterNum}" pattern="0.00" maxFractionDigits="2" />
									</td>
									<td>
										<input type="hidden" id="max_price${num.index}" value="${(list.price * list.num) - list.enterAmount}"/>
										<input type="text" style="width: 80px;" id="in_price${num.index}" name="in_price" alt="${num.index}" onBlur="invoicePriceChange(${num.index},${(list.price * list.num) - list.enterAmount},${list.price});" onkeyup="value=value.replace(/[^\d.]/g,'');" value="<fmt:formatNumber type='number' value='${(list.price * list.num) - list.enterAmount}' pattern='0.00' maxFractionDigits='2'/>"/>
										&nbsp;/&nbsp;
										<fmt:formatNumber type="number" value="${list.enterAmount==null?0:list.enterAmount}" pattern="0.00" maxFractionDigits="2" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</li>
	</ul>
	<form action="<%=basePath%>finance/after/saveAfterOpenInvoiceTp.do" method="post" id="saveAfterOpenInvoiceTp">
		<input type="hidden" name="relatedId" value="${invoice.afterId}"/><!-- 售后主键 -->
		<input type="hidden" name="colorType" value="2"/><!-- 红蓝字 1红2蓝 -->
		<input type="hidden" name="isEnable" value="1"/><!-- 是否有效 0否 1是 -->
		<input type="hidden" name="amount" id="amount" value="0.00"/><!-- 发票金额 -->
		<input type="hidden" name="validStatus" id="validStatus" value="1"/><!-- 售后开票默认审核通过 -->
		<input type="hidden" name="afterType" id="afterType" value="1"/><!-- 售后手续费开票 -->
		<div class="add-tijiao text-left f_left_wid90 " style='margin:-10px 0 0 110px;'>
			<input type="hidden" name="detailgoodsId" value="${afterSalesGoodsVo.afterSalesGoodsId}"/>
            <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="saveAfterOpenInvoiceTp();">提交</button>
        </div>
        <span id="dynamicParam"></span>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>
