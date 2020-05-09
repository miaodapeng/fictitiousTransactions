<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票申请审核原因" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/invoice/audit_sale_invoice_reason.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips7">
		<form method="post" id="auditOpenInvoiceReason">
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>审核不通过原因</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class="input-larger" name="comments" id="comments"/>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="btnSub(${invoiceApply.invoiceApplyId},2,${invoiceApply.isAdvance});">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>