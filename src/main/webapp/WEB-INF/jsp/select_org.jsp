<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>职位选择</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src='${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	function check() {
		if ($("input[name='orgId']:checked").length > 0) {
			return true;
		}
		return false;
	}
</script>
</head>

<body>
	<div class="bg-black">
		<div class="pos_abs changeCareerPos none" id="none_div">
			<div class="">
				<div class="changeTitle">切换职位</div>
				<div class="add-main pr15">
					<form action="${pageContext.request.contextPath}/saveselectorg.do"
						method="post" onsubmit="return check();">
						<ul class="add-detail pl15 pt10">
							<li>
								<div class="f_left mt0 ">
									<lable for='name'>选择职位</lable>
								</div>
								<div class="f_left inputfloat">
									<c:forEach items="${user.positions }" var="list">
										<div class="overflow-hidden mb10">
											<input type="radio" name="orgId" value="${list.orgId }">
											<label> <c:forEach items="${orgList }" var="org">
													<c:if test="${org.orgId eq list.orgId}">
					                        	${org.orgNames}<%-- ${position.orgName} --%>
													</c:if>
												</c:forEach> ${list.positionName }
											</label>
										</div>
									</c:forEach>
									<div class="font-grey9 mt10">
										友情提示: <br> 职位的切换不影响账号权限的使用； <br>
										职位影响该账号操作单据时保存的部门和职位，请谨慎选择；
									</div>
								</div>
							</li>
						</ul>
						<div class="add-tijiao pb15">
							<button type="submit">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type='text/javascript'>
	$(function() {
		var changeCareerPos = $('.changeCareerPos');
		var l = $(changeCareerPos).outerWidth() / 2;
		changeCareerPos.css({
			'margin-left' : '-' + l + 'px'
		});
		
		$("#none_div").show();
	})
</script>
</html>