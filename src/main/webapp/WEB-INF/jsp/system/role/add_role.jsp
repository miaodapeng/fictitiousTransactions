<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/role/add_role.js?rnd=<%=Math.random()%>'></script>
<div class="addElement" style="height:100%">
	<form method="post" id="addRoleForm">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='roleName'>角色名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='roleName' id='roleName' class="input-middle"/>
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
									<input type="checkbox" name="platformId" value="${platform.platformId}"/><label>${platform.platformName}</label>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
