<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票申请" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/invoice/sale_invoice_apply.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips7">
		<form method="post" id="openInvoiceApplyForm">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>开票方式</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<ul id="errTitle">
								<c:choose>
									<c:when test="${invoiceApply.isAuto eq 1}">
										<li><input type="radio" name="isAuto" id="autoN" <c:if test="${invoiceApply.isAuto eq 1}">checked</c:if> value="1"/><label>手动开票</label></li>
									</c:when>
									<c:when test="${invoiceApply.isAuto eq 2}">
										<li><input type="radio" name="isAuto" id="autoY" <c:if test="${invoiceApply.isAuto eq 2}">checked</c:if> value="2"/><label>自动开票</label></li>
									</c:when>
									<c:when test="${invoiceApply.isAuto eq 3}">
										<li><input type="radio" name="isAuto" id="autoN" <c:if test="${invoiceApply.isAuto eq 3}">checked</c:if> value="3"/><label>电子发票</label></li>
									</c:when>
									<c:otherwise>
										<li>
											<input type="radio" name="isAuto" id="autoY" checked value="2"/><label>自动开票</label>
										</li>
										&nbsp;&nbsp;
										<li>
											<input type="radio" name="isAuto" id="autoN" value="1"/><label>手动开票</label>
										</li>
										&nbsp;&nbsp;
										<li>
											<input type="radio" name="isAuto" id="autoN" value="3"/><label>电子发票</label>
										</li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</li>
				<!-- <li>
					<div class="form-tips">
						<span>*</span>
						<lable>开票类型</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<ul id="err2Title">
								<li>
									<input type="radio" name="invoiceProperty" id="invoicePropertyY" checked value="1"/><label>纸质发票</label>
								</li>
								&nbsp;&nbsp;
								<li>
									<input type="radio" name="invoiceProperty" id="invoicePropertyN" value="2"/><label>电子发票</label>
								</li>
							</ul>
						</div>
					</div>
				</li> -->
				<li>
					<div class="form-tips">
						<lable>开票备注</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks" id="errorTitle">
							<input type="text" id="comments" name="comments" value="${comment}" class="input-large">
						</div>
					</div>
				</li>
			</ul>
			<div class="pop-friend-tips">
				友情提醒<br />
				1、请与客户确认开票资料是否变化？开票的产品名称是否有特殊要求？如果几个订单合并开票，是否可以？收票人信息是否无变化？
				<br />
				2、与客户确认开票相关问题，是维护客户关系最自然的途径，所以，借此机会多问问题，多寒暄。
			</div>
			<div class="add-tijiao tcenter">
				<input type="hidden" id="invoiceType" value="${invoiceApply.invoiceType}" />
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="btnSub(${invoiceApply.relatedId});">确定</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>