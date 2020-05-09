<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票备注" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/invoice/send_sale_invoice.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips6">
		<form method="post" id="sendSaleInvoiceForm">
			<!-- 发票ID（批量寄送和单个寄送） -->
			<input type="hidden" name="relatedId" value="${invoice.invoiceId}" />
			<input type="hidden" name="invoiceIdArr" id="invoiceIdArr" value="${invoiceIdArr}"/>
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>快递公司</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<select name="logisticsId" id="logisticsId" class="input-small">
								<option value="">请选择</option>
								<c:forEach var="list" items="${logisticsList}" varStatus="num">
									<c:if test="${list.isEnable eq 1}">
										<option value="${list.logisticsId}">${list.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>快递单号</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class="input-large" name="logisticsNo" id="logisticsNo"/>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>备注</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class="input-large" name="logisticsComments" id="logisticsComments"/>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="btnSub();">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>