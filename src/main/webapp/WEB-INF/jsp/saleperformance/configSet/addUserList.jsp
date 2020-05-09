<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增人员" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/userConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="content">
			<c:set value="${flag}" var="flags"/>
			<c:set value="${salesPerformanceDeptId}" var="salesPerformanceDeptId"/>
            <input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
    	<div class="searchfunc">
			<ul class="searchTable">
           		<li>
           			<form method="post" id="search" action="<%=basePath%>sales/salesperformance/getUserListPage.do?flag=${flag}&salesPerformanceDeptId=${salesPerformanceDeptId}">
           				<input type="hidden" id="groupIds" name="groupIds" value="${groupId }">
            			<div class="f_left">
							<div>
								<input type="text" class="input-larger" placeholder="请输入人员关键字" id="username" name="username" value="${username}">
								<select class="input-middle" name="orgId" id="orgId">
									<option value=""></option>
									<c:forEach var="org" items="${orgList }">
										<option value="${org.orgId }">${org.orgName}</option>
									</c:forEach>
								</select>
								<span class="bt-bg-style bt-small bg-light-blue" onclick="search();">搜索</span>
							</div>
						</div>
					</form>
           		</li>
          	</ul>
		</div>
		<div class='normal-list-page list-page'>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr class="sort" style="font-weight: bold;">
						<th class="wid10" style="font-weight: bold;">选择</th>
						<th class="wid30" style="font-weight: bold;">人员</th>
						<th class="wid40" style="font-weight: bold;">三级部门</th>
					</tr>
				</thead>
				<tbody class="brand">
					<c:if test="${not empty userVoList}">
						<c:forEach items="${userVoList }" var="userVo" varStatus="status">
								<tr>
                                    <c:set var="userId" value="${userVo.userId}"/>
									<td>
                                        <c:if test="${ empty hasUser}">
                                            <input name="checkOne" value="${userVo.userId}" type="checkbox">
                                        </c:if>
                                        <c:set  var="flagHasUser" value="0" />
                                        <c:if test="${ not empty hasUser}">
                                            <c:forEach var="u" items="${hasUser}" varStatus="stu">
                                                <c:if test="${ u.userId  == userId }" >
                                                    <c:set  var="flagHasUser" value="${ flagHasUser + 1 }" />
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${flagHasUser == 0}">
                                                <input name="checkOne" value="${userVo.userId}" type="checkbox">
                                            </c:if>
                                        </c:if>
									</td>
									<td>${userVo.username}</td>
									<td>${userVo.orgName}</td>
								</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty userVoList}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan='3'>查询无结果！请尝试使用其他搜索条件。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<c:if test="${not empty userVoList}">
				<div class="inputfloat f_left">
					<input type="checkbox" class="mt6 mr4" name="checkAll"
						autocomplete="off"> <label class="mr10 mt4">全选</label>
				</div>
				<tags:page page="${page}" optpage="n" />
			</c:if>
		</div>
		<c:if test="${not empty userVoList}">
			<div class="add-tijiao tcenter">
				<c:if test="${flags eq 1 }">
					<button type="button" class="bt-bg-style bg-light-green" onclick="addUserConfigs(${salesPerformanceDeptId});"> 确认新增</button>
				</c:if>
				<c:if test="${flags != 1 }">
					<button type="button" class="bt-bg-style bg-light-green" onclick="addUserConfig();"> 确认新增</button>
				</c:if>
		 		<button class="dele" id="close-layer">取消</button>
			</div>
		</c:if>
	</div>
</body>
</html>