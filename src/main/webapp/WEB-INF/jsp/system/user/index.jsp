<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="用户管理" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/system/user/index.do" method="post" id="search">
			<input type="hidden" name="flag" value="1">
			<ul>
				<li><label class="infor_name">员工姓名</label> <input type="text"
					class="input-middle" name="realName" id="realName"
					value="${user.realName }"></li>
				<li><label class="infor_name">用户名</label> <input type="text"
					class="input-middle" name="username" id="username"
					value="${user.username }"></li>
				<c:if test="${curr_user.isAdmin == 2}">
				<li><label class="infor_name">分公司</label> <select
					class="input-middle f_left" name="companyId">
						<option value="0">请选择</option>
						<c:forEach items="${companyList }" var="company">
							<option value="${company.companyId }"
								<c:if test="${user.companyId != null and user.companyId == company.companyId}">selected="selected"</c:if>>
								${company.companyName }</option>
						</c:forEach>
				</select></li>
				</c:if>
				<c:if test="${curr_user.isAdmin != 2}">
				<li><label class="infor_name">所属部门</label> <select
					class="input-middle f_left" name="orgId" onchange="initPosit();">
						<option value="0">请选择</option>
						<c:forEach items="${orgList }" var="org">
							<option value="${org.orgId }"
								<c:if test="${user.orgId != null and user.orgId == org.orgId}">selected="selected"</c:if>>
								${org.orgName }</option>
						</c:forEach>
				</select></li>
				<li><label class="infor_name">职位</label> <select
					class="input-middle f_left" name="positionId">
						<option selected="selected" value="0">请选择</option>
						<c:if test="${positList != null }">
							<c:forEach items="${positList }" var="posit">
								<option value="${posit.positionId }"
									<c:if test="${user.positionId != null and user.positionId == posit.positionId}">selected="selected"</c:if>>${posit.positionName }</option>
							</c:forEach>
						</c:if>
				</select></li>
				</c:if>
				<li><label class="infor_name">状态</label> <select
					class="input-middle f_left" name="isDisabled">
						<option selected="selected" value="-1">请选择</option>
						<option value="0"
							<c:if test="${user.isDisabled != null and user.isDisabled=='0'}">selected="selected"</c:if>>启用</option>
						<option value="1"
							<c:if test="${user.isDisabled != null and user.isDisabled=='1'}">selected="selected"</c:if>>禁用</option>
				</select></li>
				<li><label class="infor_name">是否为贝登员工</label>
					<select class="input-middle f_left" name="staff">
						<option value="">请选择</option>
						<option value="1" <c:if test="${user.staff == '1'}">selected="selected"</c:if>>是</option>
						<option value="0" <c:if test="${user.staff == '0'}">selected="selected"</c:if>>否</option>
					</select>
				</li>
				<li><label class="infor_name">所属公司</label>
					<input type="text" class="input-middle" name="belongCompanyName" id="belongCompanyName" value="${user.belongCompanyName}">
				</li>
				<li><label class="infor_name">可登录系统</label>
					<select class="input-middle f_left" name="systems">
						<option value="">请选择</option>
						<c:forEach items="${platformList}" var="each">
							<option value="${each.platformId}"
									<c:if test="${user.systems == each.platformId}">selected="selected"</c:if>
							>${each.platformName}</option>
						</c:forEach>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span> 
				<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<c:choose>
					<c:when test="${curr_user.isAdmin == 2}">
						<span class="bt-small bg-light-blue bt-bg-style pop-new-data" layerParams='{"width":"600px","height":"300px","title":"新增管理员","link":"./addmanager.do"}'>新增管理员</span>
					</c:when>
					<c:otherwise>
						<span class="bg-light-blue bt-bg-style bt-small addtitle" tabTitle='{"num":"addsystemuser","link":"./system/user/modifyuser.do","title":"新增员工"}'>新增员工</span>
					</c:otherwise>
				</c:choose>
				<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"550px","height":"250px","title":"列表配置","link":"./define.do"}'>列表配置</span>
			</div>
		</form>
	</div>
	<div  class="normal-list-page">
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="sorts">序号</th>
					<th>所属公司</th>
					<th>可登录系统</th>
					<th>员工姓名</th>
					<th>用户名</th>
					<c:if test="${curr_user.isAdmin == 2}">
					<th>分公司</th>
					<th>管理员</th>
					</c:if>
					<th <define:define bussiness="/system/user/index.do" field="orgName" />>所属部门</th>
					<th <define:define bussiness="/system/user/index.do" field="positionName" />>职位</th>
					<th <define:define bussiness="/system/user/index.do" field="pUsername" />>直接上级</th>
					<th <define:define bussiness="/system/user/index.do" field="number" />>工号</th>
					<th <define:define bussiness="/system/user/index.do" field="ccNumber" />>分机号</th>
					<th <define:define bussiness="/system/user/index.do" field="isDisabled" />>状态</th>
					<th <define:define bussiness="/system/user/index.do" field="addTime" />>创建时间</th>
					<th <define:define bussiness="/system/user/index.do" field="lastLoginTime" />>上次登录时间</th>
					<th class="wid15">操作</th>
				</tr>
			</thead>

			<tbody class="employeestate">
				<c:if test="${not empty users}">
					<c:forEach items="${users}" var="user" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${user.belongCompanyName}</td>
							<td>${user.systems}</td>
							<td>${user.userDetail.realName }</td>
							<td>${user.username}</td>
							<c:if test="${curr_user.isAdmin == 2}">
								<td>${user.companyName}</td>
								<td>
									<c:choose>
										<c:when test="${user.isAdmin == 2 }">超级管理员</c:when>
										<c:when test="${user.isAdmin == 1 }">管理员</c:when>
										<c:otherwise>用户</c:otherwise>
									</c:choose>
								</td>
							</c:if>
							<td <define:define bussiness="/system/user/index.do" field="orgName" /> title="${user.orgName}">${user.orgName}</td>
							<td <define:define bussiness="/system/user/index.do" field="positionName" /> title="${user.positionName}">${user.positionName}</td>
							<td <define:define bussiness="/system/user/index.do" field="pUsername" />>${user.pUsername}</td>
							<td <define:define bussiness="/system/user/index.do" field="number" />>${user.number }</td>
							<td <define:define bussiness="/system/user/index.do" field="ccNumber" />>${user.userDetail.ccNumber }</td>
							<td <define:define bussiness="/system/user/index.do" field="isDisabled" />>
								<c:choose>
									<c:when test="${user.isDisabled==1}">
										<span class="offstate">禁用</span>
									</c:when>
									<c:otherwise>
										<span class="onstate">启用</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td <define:define bussiness="/system/user/index.do" field="addTime" />><date:date value="${user.addTime} " /></td>
							<td <define:define bussiness="/system/user/index.do" field="lastLoginTime" />><date:date value="${user.lastLoginTime} " /></td>
							<td>
								<span class="edit-user addtitle" tabTitle='{"num":"viewuser${user.userId}","link":"./system/user/viewuser.do?userId=${user.userId}","title":"查看员工"}'>查看</span>
								<c:choose>
									<c:when test="${curr_user.isAdmin == 2 && user.isAdmin != 0}">
										<span class="edit-user pop-new-data" layerParams='{"width":"600px","height":"300px","title":"编辑管理员","link":"./editmanager.do?userId=${user.userId}"}'>编辑</span>
									</c:when>
									<c:otherwise>
										<c:if test="${user.isAdmin == 0 }">
											<span class="edit-user addtitle" tabTitle='{"num":"modifyuser${user.userId}","link":"./system/user/modifyuser.do?userId=${user.userId}","title":"编辑员工"}'>编辑</span>
										</c:if>
									</c:otherwise>
								</c:choose>
								<c:if test="${user.isAdmin == 0 || curr_user.isAdmin == 2}">
									<c:choose>
										<c:when test="${user.isDisabled == 1}">
											<span class="edit-user" onclick="setDisabled(${user.userId},0);">启用</span>
										</c:when>
										<c:otherwise>
											<span class="forbid clcforbid" onclick="setDisabled(${user.userId},1);">禁用</span>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty users }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/user/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
