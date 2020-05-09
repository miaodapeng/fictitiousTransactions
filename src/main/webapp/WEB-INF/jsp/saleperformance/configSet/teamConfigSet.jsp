<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title" value="${groupName}小组配置" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
    <c:if test="${ not empty listGroup }">
        <c:forEach var="group" items="${listGroup}" varStatus="g">
            <c:set var="salesPerformanceGroupId" value="${group.salesPerformanceGroupId}"/>
            <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">
                            ${group.groupName}
                    </div>
                    <div class="title-click nobor  pop-new-data" layerParams='{"width":"380px","height":"400px","title":"新增","link":"./teamNewSet.do?salesPerformanceGroupId=${group.salesPerformanceGroupId}"}'>
                        新增小组
                    </div>
                </div>
                <div class=' list-page'>
                    <table class="table">
                        <thead>
                                    <tr style="background-color: white;">
                                        <th  style="font-weight: bold; ">小组</th>
                                        <td >负责人</td>
                                        <th style="font-weight: bold;">操作</th>
                                    </tr>
                        </thead>
                        <tbody>
                      <%--  统计团队下小组数量,默认0 --%>
                        <c:set var="deptFlagNum" value="0"/>
                        <c:forEach var="item" items="${listDeptMember}" varStatus="g1">
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
                                      layerparams='{"width":"380px","height":"400px","title":"编辑","link":"./editOneGroupConfigSetData.do?salesPerformanceDeptId=${item.salesPerformanceDeptId}"}'>编辑</span>
                                        <span class="edit-user addtitles font-red" onclick="deleteDeptById(${item.salesPerformanceDeptId});">移除</span>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:if test="${0  eq deptFlagNum}">
                            <tr>
                                <td colspan="${3}">查询暂无团队人员！</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${ not empty listDeptMember }">
        <c:forEach var="groupTeam" items="${listDeptMember}" varStatus="g2">
            <c:if test="${salesPerformanceGroupId eq groupTeam.salesPerformanceGroupId }">
                <div class="parts">
                    <div class="title-container">
                        <div class="table-title nobor">
                                ${groupTeam.deptName}
                        </div>
                        <div class="title-click nobor  pop-new-data" layerparams='{"width":"822px","height":"580px","title":"新增人员","link":"./getUserListPage.do?groupId=${groupId}&flag=1&salesPerformanceDeptId=${groupTeam.salesPerformanceDeptId}"}'>
                            新增人员
                        </div>
                    </div>
                    <div class=' list-page'>
                        <table class="table">
                            <thead>
                                <tr style="background-color: white;">
                                    <td >序号</td>
                                    <th  style="font-weight: bold; ">人员</th>
                                    <td >部门</td>
                                    <td  >目标（万）</td>
                                    <th style="font-weight: bold;  ">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:set var="deptUserNum" value="0"/>
                            <c:forEach var="groupDeptUser" items="${getDeptUserList}" varStatus="g3">
                                <c:if test="${ groupTeam.salesPerformanceDeptId eq groupDeptUser.salesPerformanceDeptId }">
                                    <c:set var="deptUserNum" value="${deptUserNum + 1}"/>
                                    <tr style="background-color: white;">
                                        <td>${deptUserNum}</td>
                                        <td>${groupDeptUser.username }</td>
                                        <td>${groupDeptUser.orgName }</td>
                                        <td>${groupDeptUser.goal }</td>
                                        <td>
                              <span class="edit-user pop-new-data"
                                    layerparams='{"width":"380px","height":"200px","title":"编辑","link":"./editDeptPerson.do?salesPerformanceDeptUserId=${groupDeptUser.salesPerformanceDeptUserId}&username=${groupDeptUser.username}&goal=${groupDeptUser.goal }"}'>编辑</span>
                                            <span class="edit-user addtitles font-red" onclick="deleteDeptUserById(${groupDeptUser.salesPerformanceDeptUserId});">删除</span>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:if test="${ 0 eq deptUserNum}">
                                <tr>
                                    <td colspan="${5}">查询暂无小组人员！</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/configSet.js?rnd=<%=Math.random()%>"></script>