<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑财务信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>开户银行</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="bank" id="bank" value="${engineer.bank }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>银行帐号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" 
							placeholder="账号只允许输入阿拉伯数字" class=" input-large" name="bankAccount" id="bankAccount" value="${engineer.bankAccount }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>开户行联行号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')" 
							placeholder="账号只允许输入阿拉伯数字" class=" input-large" name="bankCode" id="bankCode" value="${engineer.bankCode }"/>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="engineerId" value="${engineer.engineerId }">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/edit_finance.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>