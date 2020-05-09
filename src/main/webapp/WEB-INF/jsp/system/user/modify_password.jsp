<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="修改密码" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="addElement">
	<div class="add-main">
		<form action="" method="post" id="myform">
			<ul class="add-detail">
				<li>
					<div class="infor_name">
						<lable>旧密码</lable>
					</div>
					<div class="f_left">
						<input type="password" class="input-larger" name="oldpassword" readonly onfocus="this.removeAttribute('readonly');"
							id="oldpassword" value="" maxlength="16" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<lable>新密码</lable>
					</div>
					<div class="f_left">
						<input type="password" class="input-larger" name="password"
							id="password" value="" maxlength="16" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<lable>确认新密码</lable>
					</div>
					<div class="f_left">
						<input type="password" class="input-larger" name="repassword"
							id="repassword" value="" />
					</div>
					<div class="clear"></div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="userId" value="${user.userId }">
				<button type="submit" id="submit">提交</button>
				<button type="button" class="dele " id="close-layer">取消</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/user/modify_password.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>