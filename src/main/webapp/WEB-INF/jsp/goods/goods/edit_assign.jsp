<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑归属" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post" id="myform">
		<ul>
			<c:choose>
				<c:when test="${not empty users }">
					<c:forEach items="${users }" var="user" varStatus="status">
					<li>
						<div class="form-tips">
							<span>*</span>
							<lable>归属</lable>
						</div>
						<div class="f_left ">
							<div class="form-blanks">
								<select class="input-middle mr6" name="userId">
									<option value="0">请选择</option>
									<c:if test="${not empty productUserList }">
										<c:forEach items="${productUserList }" var="productUser">
											<option value="${productUser.userId }"
											<c:if test="${productUser.userId == user.userId }">
											selected
											</c:if>
												>${productUser.username }</option>
										</c:forEach>
									</c:if>
								</select>
								<c:choose>
									<c:when test="${status.count == users.size() }">
										<div class="f_left bt-bg-style bt-middle bg-light-blue" onclick="addOwner(this);">添加</div>
									</c:when>
									<c:otherwise>
										<div class="f_left bt-bg-style bt-middle bg-light-red" onclick="delOwner(this);">删除</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</li>
					</c:forEach>	
				</c:when>
				<c:otherwise>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>归属</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<select class="input-middle mr6" name="userId">
								<option value="0">请选择</option>
								<c:if test="${not empty productUserList }">
									<c:forEach items="${productUserList }" var="productUser">
										<option value="${productUser.userId }"
											>${productUser.username }</option>
									</c:forEach>
								</c:if>
							</select>
							<div class="f_left bt-bg-style bt-middle bg-light-blue" onclick="addOwner(this);">添加</div>
						</div>
					</div>
				</li>
				</c:otherwise>
			</c:choose>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="manageCategories" value="${manageCategories }">
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/goods/goods/edit_assign.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>