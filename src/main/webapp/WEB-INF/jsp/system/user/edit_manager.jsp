<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑分公司管理员" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8 trader-customer-accountperiodapply">
	<form method="post" action="" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>分公司</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select style="width: 248px;" name="companyId" id="companyId">
							<option value="0">请选择</option>
							<c:forEach items="${companyList }" var="company">
								<option value="${company.companyId }" 
								<c:if test="${user.companyId == company.companyId }">selected="selected"</c:if>
								>${company.companyName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>员工姓名</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" name="realName" id="realName"
							class="input-large" value="${user.userDetail.realName}" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>用户名</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" name="username" id="username"
							class="input-large"
							value="${user.username}" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>密码</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="password" name="password" id="password" value="" readonly onfocus="this.removeAttribute('readonly');"
							class="input-large" maxlength="16" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>重复密码</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="password" name="repassword" id="repassword" value=""
							class="input-large" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span> <label>设置角色</label>
				</div>
				<div class="f_left" style="width: 75%">
					<div class="form-blanks" id="roleDiv">
						<ul>
							<c:if test="${not empty roleList }">
								<c:forEach items="${roleList }" var="role">
									<li><input type="checkbox" name="roleId"
										value="${role.roleId}"
										<c:if test="${not empty userRoles}">
					                        	<c:forEach items="${userRoles}" var="userRole">
					                        		<c:if test="${userRole.roleId==role.roleId }">checked="checked"</c:if>
											</c:forEach>
										</c:if>
										> <label>${role.roleName }</label>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<input type="hidden" name="userId" value="${user.userId }">
			<input type="hidden" name="roleIds" value="${user.roleIds }">
			<button type="submit" id="submit">提交</button>
			<button class="dele" id="close-layer" type="button">取消</button>
		</div>
	</form>
</div>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/user/edit_manager.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>