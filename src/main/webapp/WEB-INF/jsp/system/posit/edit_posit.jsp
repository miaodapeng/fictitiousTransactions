<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/posit/edit_posit.js?rnd=<%=Math.random()%>'></script>
<div class="editElement">
	<form action="" method="post" id="editPositForm">
		<input type="hidden" name="positionId" value="${position.positionId}"/>
		<ul class="edit-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='positionName'>职位名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='positionName' id='positionName' class="input-middle" value="${position.positionName}" />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='orgId'>所属部门</lable>
				</div>
				<div class="f_left">
					<select name="orgId" id="orgId" class="input-middle">
						<option value="0">请选择</option>
						<c:forEach items="${orgList }" var="org">
							<option value="${org.orgId }"  <c:if test="${position.orgId != null and position.orgId == org.orgId}">selected="selected"</c:if>>
							${org.orgName }
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='positionName'>职位级别</lable>
				</div>
				<div class="f_left">
					<select id="level" name="level" class="input-middle">
						<option value="">请选择</option>
						<c:forEach var="list" items="${positLevelList}">
							<option value="${list.sysOptionDefinitionId}" <c:if test="${position.level eq list.sysOptionDefinitionId}">selected</c:if>>${list.title}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name mt0">
					<lable for='positionName'>职位类型</lable>
				</div>
				<div class="f_left">				
					<select id="level" name="type" class="input-middle">
						<option value="0">无</option>
						<c:forEach var="list" items="${listType}">
							<option value="${list.sysOptionDefinitionId}" <c:if test="${position.type eq list.sysOptionDefinitionId}">selected</c:if>>${list.title}</option>
						</c:forEach>
					</select>	
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
