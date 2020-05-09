<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="付款申请审核原因" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/payapply_audit_reason.js?rnd=<%=Math.random()%>'></script>
	<div class="form-list form-tips5">
		<form method="post" id="auditPayApplyReason">
			<input type="hidden" name="payApplyId" value="${payApply.payApplyId}"><!-- 申请付款主键 -->
			<input type="hidden" name="relatedId" value="${payApply.relatedId}"><!-- 关联ID -->
			<input type="hidden" name="validStatus" value="2"><!-- 结果 -->
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>不通过原因</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class="input-larger" name="validComments" id="validComments"/>
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