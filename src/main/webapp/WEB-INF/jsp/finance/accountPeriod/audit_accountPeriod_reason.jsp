<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="账期申请审核原因" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/accountPeriod/audit_accountPeriod_reason.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips7">
		<form method="post" id="auditAccountPeriodReason">
			<input type="hidden" name="traderAccountPeriodApplyId" value="${tapa.traderAccountPeriodApplyId}"><!-- 申请账期主键 -->
			<input type="hidden" name="traderId" value="${tapa.traderId}"><!-- 交易者ID -->
			<input type="hidden" name="traderType" value="${tapa.traderType}"><!-- 交易者类型 -->
			<input type="hidden" name="status" value="2"><!-- 结果 -->
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>审核不通过原因</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class="input-larger" name="auditReason" id="auditReason"/>
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