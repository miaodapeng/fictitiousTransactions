<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/role/edit_role.js?rnd=<%=Math.random()%>'></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/editRole.css?rnd=<%=Math.random()%>" />

<c:forEach items="${rolePlatformList}" var="rolePlatform">
	<input type="hidden" name="choosedId" value="${rolePlatform.platformId}">
</c:forEach>

<input type="hidden" name="erp" value="${erp}">
<input type="hidden" name="biz" value="${biz}">
<input type="hidden" name="yxg" value="${yxg}">

<div class="editElement" style="height:100%">
	<form action="" method="post" id="editRoleForm">
		<input type="hidden" name="roleId" value="${role.roleId}">
		<ul class="edit-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='roleName'>角色名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='roleName' id='roleName' class="input-middle" value="${role.roleName}"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='roleName'>应用系统</lable>
				</div>
				<div class="f_left" style="width: 75%">
					<div class="form-blanks" id="platform">
						<ul>
							<c:if test="${not empty platformList }">
								<c:forEach items="${platformList}" var="platform">
									<input type="checkbox" name="platformId" value="${platform.platformId}"
											<c:forEach items="${rolePlatformList}" var="rolePlatform">
												<c:if test="${rolePlatform.platformId==platform.platformId}">checked="checked"</c:if>
											</c:forEach>
									/><label>${platform.platformName}</label>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<a  href="javaScript:;" class="button" onclick="subRolex()">提交</a>
			<a href="javaScript:;" type="button" class="dele" id="cancle">取消</a>
		</div>
	</form>
</div>
