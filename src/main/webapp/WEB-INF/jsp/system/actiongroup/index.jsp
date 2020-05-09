<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="功能分组管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<body onload="sort('${actiongroup.sortField}','${actiongroup.order}');">
    <div class="content">
        <div class="searchfunc searchfuncpl0">
            <form action="${pageContext.request.contextPath}/system/actiongroup/index.do" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">系统名称</label>
                        <select name="platformId" >
                            <option value="">请选择</option>
                            <c:if test="${not empty platformList }">
                                <c:forEach items="${platformList}" var="platform">
                                    <option value="${platform.platformId}"
                                            <c:if test="${actiongroup.platformId == platform.platformId}">selected="selected"</c:if>
                                    >${platform.platformName}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
                    <span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
                    <span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"260px","title":"新增分组","link":"./addGroup.do"}'>新增分组</span>
                </div>
            </form>
        </div>
        <div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr class="sort">
                        <th class="sorts">序号</th>
                        <th abbr="NAME">系统名称</th>
                        <th abbr="NAME">功能分组名称</th>
                        <th abbr="LEVEL">级别</th>
                        <th abbr="ORDER_NO">排序</th>
                        <th style="width: 15%;">操作</th>
                    </tr>
                </thead>
                <tbody class="company">
        		<c:if test="${not empty actiongroupList}">
                	<c:forEach items="${actiongroupList }" var="actiongroup" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${actiongroup.platformName}</td>
	                        <td class="textindent8">${actiongroup.nameArr}</td>
	                        <td>${actiongroup.level}</td>
	                        <td>${actiongroup.orderNo}</td>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"260px","title":"编辑分组","link":"./editGroup.do?actiongroupId=${actiongroup.actiongroupId}"}'>
									编辑
								</span>
								<span class="delete" onclick="delActiongroup(${actiongroup.actiongroupId});">删除</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty actiongroupList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
        </div>
    </div>
    <script type="text/javascript" src='<%= basePath %>static/js/ationgroup/index.js?rnd=<%=Math.random()%>'></script>
    <%@ include file="../../common/footer.jsp"%>
