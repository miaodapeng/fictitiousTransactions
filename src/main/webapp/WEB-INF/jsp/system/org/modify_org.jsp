<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增|编辑部门" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="form-list">
	<div class="add-main adddepart">
		<form action="" method="post" id="orgform">
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>部门名称</lable>
					</div>
					<div class="f_left">
						<div class="">
							<input type="text" name="orgName" id="orgName"
								class="input-larger" value="${orgInfo.orgName }" />
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>上级部门</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<select name="parentId" id="parentId" class="input-larger">
								<option value="0">请选择</option>
								<c:forEach items="${orgList }" var="org">
									<option value="${org.orgId }"
										<c:if test="${orgInfo.parentId != null and orgInfo.parentId == org.orgId}">selected="selected"</c:if>>
										${org.orgName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>部门类型</lable>
					</div>
					<div class="f_left">
						<div class="form-blanks">
							<select name="type" class="input-small ">
								<option value="0">无</option>
								<c:forEach items="${listType}" var="list">
									<option value="${list.sysOptionDefinitionId}"
										<c:if test="${orgInfo.type == list.sysOptionDefinitionId}">selected="selected"</c:if>>
										${list.title}</option>
								</c:forEach>
							</select>
						<div id="typeDiv"></div>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<input type="hidden" name="beforeParams" value='${beforeParams}'/>
				<input type="hidden" name="orgId" value="${orgInfo.orgId }" />
				<button type="submit">提交</button>
				<button class="dele" id="close-layer" type="button">取消</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/org/modify.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>