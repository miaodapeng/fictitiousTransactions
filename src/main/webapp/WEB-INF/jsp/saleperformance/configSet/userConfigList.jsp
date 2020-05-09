<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="人员列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/userConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="main-container">
			<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	五行剑法- ${groupName} -人员
                </div>
                <div class="title-click nobor  pop-new-data" layerparams='{"width":"822px","height":"500px","title":"新增人员","link":"./getUserListPage.do?groupId=${groupId}"}'>
                                  	新增
            	</div>
            </div>
           <div class='normal-list-page list-page'>
	            <table class="table">
	                <thead>
	                    <tr style="background-color: white;font-weight: bold;">
	                        <th style="font-weight: bold;">序号</th>
	                        <th style="font-weight: bold;">人员</th>
	                        <th style="font-weight: bold;">三级部门</th>
	                        <th style="font-weight: bold;">年度目标（万）</th>
	                        <c:if test="!${month eq 0}">	                        
	                        	<th style="font-weight: bold;">${month }月目标（万）</th>
	                        </c:if>
							<th style="font-weight: bold;">${month+1 }月目标（万）</th>
							<c:choose>
	                        	<c:when test="${(month+2) <= 12  }">
	                        		<th style="font-weight: bold;">${month+2 }月目标（万）</th>
		                        </c:when>
	                        </c:choose>
							<th class="wid20" style="font-weight: bold;">操作</th>
	                    </tr>
	                </thead>
	                <tbody>
	                   <c:forEach var="group" items="${groupUserList}" varStatus="num" >
		                 	 <tr style="background-color: white;">
								<td>${num.count}</td>
								<td>${group.userName}</td>
								<td>${group.orgName }</td>
								<td>${group.goalYear }</td>
								<c:if test="!${month eq 0}">	
									<td>${group.monthGoals[month-1] }</td>
								</c:if>
								<td>${group.goalMonth }</td>
								<c:choose>
		                        	<c:when test="${(month+2) <= 12  }">
		                        		<td>${group.monthGoals[month+1] }</td>
			                        </c:when>
	                        	</c:choose>
								<td>
									<span class="edit-user pop-new-data" 
										layerparams='{"width":"620px","height":"380px","title":"批量编辑","link":"./batchEditUserPage.do?rSalesPerformanceGroupJUserId=${group.rSalesPerformanceGroupJUserId }&goalYear=${group.goalYear }&goalMonth=${group.goalMonth}&salesPerformanceGoalYearId=${group.salesPerformanceGoalYearId }&salesPerformanceGroupId=${groupId}&userId=${group.userId}&userName=${group.userName}"}'>编辑</span>
									<!--  span class="edit-user pop-new-data" 
										layerparams='{"width":"360px","height":"220px","title":"编辑","link":"./openUserPage.do?orgId=${group.orgId }&rSalesPerformanceGroupJUserId=${group.rSalesPerformanceGroupJUserId }&salesPerformanceGoalYearId=${group.salesPerformanceGoalYearId }&salesPerformanceGoalMonthId=${group.salesPerformanceGoalMonthId }&salesPerformanceGroupId=${groupId}&userId=${group.userId}&userName=${group.userName}"}'>编辑old</span -->
									<span class="forbid clcforbid" onclick="delUserConfig(${groupId},${group.userId})">移除</span>
								</td>
							</tr>
						</c:forEach>
						 <c:if test="${empty groupUserList}">
							<!-- 查询无结果弹出 -->
							<tr>
								<td colspan="8">查询无结果！请尝试使用其他搜索条件。</td>
							</tr>
						</c:if>
	                </tbody>
	            </table>
			</div>
        </div>
	</div>
</body>
</html>