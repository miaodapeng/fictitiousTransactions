<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title" value="部门配置" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
    <c:if test="${ not empty listGroup }">
        <c:forEach var="group" items="${listGroup}" varStatus="gg">
            <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">
                            ${group.groupName}
                    </div>
                    <div class="title-click nobor  pop-new-data" layerParams='{"width":"380px","height":"200px","title":"新增","link":"./teamNewSet.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}"}'>
                        新增小组
                    </div>
                </div>
                <div class='normal-list-page list-page'>
                    <table class="table">
                        <thead>
                        <tr style="background-color: white;">
                            <th class="wid60" style="font-weight: bold; ">小组</th>
                            <td class="wid60">负责人</td>
                            <th class="wid60" style="font-weight: bold;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <%--  统计团队下小组数量,默认0 --%>
                        <c:set var="deptFlagNum" value="0"/>
                        <c:forEach var="item" items="${listDeptMember}" varStatus="g">
                            <c:if test="${group.salesPerformanceGroupId  eq item.salesPerformanceGroupId}">
                                <c:set var="deptFlagNum" value="${deptFlagNum + 1}"/>
                                <tr style="background-color: white;">
                                    <td class="group">${item.deptName}</td>
                                    <td>
                                        <c:if test="${ not empty item.userList}">
                                            <c:forEach var="user" items="${item.userList}" varStatus="use">
                                                ${user.username}&nbsp;&nbsp;
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${ empty item.userList}">
                                            暂无负责人!
                                        </c:if>
                                    </td>
                                    <td>
                                     <span class="edit-user pop-new-data"
                                           layerparams='{"width":"380px","height":"400px","title":"编辑","link":"./getOneGroupConfigSetData.do?salesPerformanceGroupId=${groups.salesPerformanceDeptId}"}'>编辑</span>
                                        <span class="edit-user addtitles" onclick="deleteGroupById(${item.salesPerformanceDeptId});">移除</span>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:if test="${0  eq deptFlagNum}">
                            <tr>
                                <td colspan="${fn:length(configList)+5}">查询无结果！</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${ not empty listDeptMember }">
        <c:forEach var="groupTeam" items="${listDeptMember}" varStatus="ggg">
            <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">
                            ${groupTeam.deptName}
                    </div>
                    <div class="title-click nobor  pop-new-data" layerParams='{"width":"380px","height":"400px","title":"新增人员","link":"./addTeamPerson.do?salesPerformanceDeptId=${groupTeam.salesPerformanceDeptId}"}'>
                        新增人员
                    </div>
                </div>
                <div class='normal-list-page list-page'>
                    <table class="table">
                        <thead>
                        <tr style="background-color: white;">
                            <th style="font-weight: bold;">人员</th>
                            <td>三级部门</td>
                            <td>目标（万）</td>
                            <th class="wid20" style="font-weight: bold;">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="group" items="${groupList}" varStatus="g">
                            <tr style="background-color: white;">
                                <td id="${group.salesPerformanceGroupId }" class="group">${group.groupName}</td>
                                <c:if test="${not empty groupJConfigList}">
                                    <c:forEach var="groupJConfig" items="${groupJConfigList}" varStatus="status">
                                        <c:if test="${groupJConfig.salesPerformanceGroupId==group.salesPerformanceGroupId}">
                                            <td>${groupJConfig.weight} %</td>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${g.index>=(fn:length(groupJConfigList)/fn:length(configList))}">
                                        <c:forEach begin="0" end="${fn:length(configList)-1}" >
                                            <td>0 %</td>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty groupJConfigList}">
                                    <c:forEach begin="0" end="${fn:length(configList)-1}">
                                        <td>0 %</td>
                                    </c:forEach>
                                </c:if>
                                <td>负责人</td>
                                <td>
                            <span class="edit-user pop-new-data"
                                  layerparams='{"width":"380px","height":"400px","title":"编辑","link":"./getOneGroupConfigSetData.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}"}'>编辑</span>
                                    <span class="edit-user addtitles" onclick="deleteGroupById(${group.salesPerformanceGroupId});">删除</span>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty groupList}">
                            <tr>
                                <td colspan="${fn:length(configList)+5}">查询无结果！请尝试使用其他搜索条件。</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/configSet.js?rnd=<%=Math.random()%>"></script>