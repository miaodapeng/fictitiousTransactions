<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增|编辑部门" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/finance/invoice/invoice_audit_reason.js?rnd=<%=Math.random()%>'></script>
<div class="form-list">
	<div class="add-main">
		<form action="./saveInvoiceAudit.do" method="post" id="auditInvoiceForm">
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>审核备注</lable>
					</div>
					<div class="f_left">
						<div class="">
							<textarea cols="20" rows="4" name="validComments" id="validComments" class="input-larger" ></textarea>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<input type="hidden" name="invoiceId" value="${invoice.invoiceId}"/>
				<input type="hidden" name="invoiceNo" value="${invoice.invoiceNo}"/>
				<input type="hidden" name="validStatus" value="2"/>
				<input type="hidden" name="colorType" value="${invoice.colorType}"/>
				<input type="hidden" name="isEnable" value="${invoice.isEnable}"/>
				<input type="hidden" name="type" value="${invoice.type}"/>
				<button type="submit">提交</button>
				<button class="dele" id="close-layer" type="button">取消</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>