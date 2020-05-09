<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑财务信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"
	src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/edit_finance.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
	<div class="form-list form-tips8">
		<form method="post" action="" id="myform">
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>开户银行</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<input type="text" class="input-largest errobor"  
							name="bank" id="bank" value="${traderFinance.bank}" />
						</div>
					</div>
				</li>

				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>开户行支付联行号</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<input type="text" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" 
								placeholder="账号只允许输入阿拉伯数字" class="input-largest errobor" name="bankCode" id="bankCode" value="${traderFinance.bankCode}" />
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>银行帐号</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<input type="text" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" 
								placeholder="账号只允许输入阿拉伯数字" class="input-largest errobor" name="bankAccount" id="bankAccount" value="${traderFinance.bankAccount}" />
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">

						<lable>备注</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<input type="text" class="input-largest errobor" name="comments"
								id="comments" value="${traderFinance.comments}" />
						</div>
					</div>
				</li>
			</ul>
			<input type="hidden" name="traderFinanceId"
				value="${traderFinance.traderFinanceId}" /> <input type="hidden"
				name="traderId" value="${traderFinance.traderId}" /> <input
				type="hidden" name="traderType" id="traderType" value="2" /> <input
				type="hidden" name="beforeParams" value='${beforeParams}'>
			<div class="add-tijiao tcenter">
				<button type="submit" id="submit">提交</button>
				<button type="button" class="dele" id="close-layer">取消</button>
			</div>
		</form>
	</div>
</div>
</body>

</html>
