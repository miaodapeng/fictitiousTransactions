<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="title" value="团队配置" scope="application" />
<%@ include file="../../common/common.jsp"%>
		<div class="main-container">
			<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	五行剑法部门设置
                </div>
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"380px","height":"550px","title":"新增","link":"./insertGroupConfigSetData.do"}'>
                    新增
                </div>
            </div>
            <div class='normal-list-page list-page'>
	            <table class="table">

						<thead>
						<tr style="background-color: white;">
							<th style="font-weight: bold;">团队</th>
							<c:forEach items="${configList}" var="config">
								<th class="config" style="font-weight: bold;">${config.item }</th>
							</c:forEach>
							<td>负责人</td>
							<th class="wid20" style="font-weight: bold;">操作</th>
						</tr>
						</thead>
						<c:if test="${ not empty groupList }" >

							<tbody>
							<c:forEach var="group" items="${groupList}" varStatus="g">
								<tr style="background-color: white;">
									<td class="group">${group.groupName}</td>

									<c:if test="${not empty group.configList}">
										<c:forEach var="groupJConfig" items="${group.configList}" varStatus="status">
											<td>${groupJConfig.weight} %</td>
										</c:forEach>
									</c:if>
									<c:if test="${empty group.configList}">
										<c:forEach begin="0" end="${fn:length(group.configList)-1}">
											<td>0 %</td>
										</c:forEach>
									</c:if>
									<td>
										<c:if test="${ not empty group.userList}">
										    <c:forEach var="groupUser" items="${group.userList}" varStatus="us">
												${groupUser.username}&nbsp;&nbsp;
											</c:forEach>
										</c:if>
										<c:if test="${ empty group.userList}">
											暂无负责人!
										</c:if>
									</td>
									<td>
										<span class="edit-user addtitle"
											  tabTitle='{"num":"openBrandPageById${group.groupName}","link":"./sales/salesperformance/teamSet.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}&groupName=${group.groupName}","title":"团队详情"}'>详情</span>
										<span class="edit-user addtitle"
											  tabTitle='{"num":"openBrandPageById${group.salesPerformanceGroupId}","link":"./sales/salesperformance/getBrandConfigListPage.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}","title":"品牌列表"}'>品牌</span>

										<span class="edit-user pop-new-data"
											  layerparams='{"width":"380px","height":"550px","title":"编辑","link":"./getOneGroupConfigSetData.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}"}'>编辑</span>

										<span class="edit-user addtitles font-red" onclick="deleteGroupById(${group.salesPerformanceGroupId});">删除</span>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty groupList}">
								<tr>
									<td colspan="${fn:length(configList)+5}">查询无结果！请尝试使用其他搜索条件。</td>
								</tr>
							</c:if>
							</tbody>
						</c:if>
	            </table>
			</div>
        </div>
	</div>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/configSet.js?rnd=<%=Math.random()%>"></script>