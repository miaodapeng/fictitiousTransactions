<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="用户日志" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/system/loginlog/index.do" method="post" id="search">
				<ul>
	                <li>
	            		<label class="infor_name">用户名</label>
	            		<input type="text" class="input-middle" name="username" id="username" value="${userLoginLog.username}">
	            	</li>
            	</ul>
	            <div class="tcenter">	
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="sorts">序号</th>
						<th>用户名</th>
						<th>访问类型</th>
						<th>访问时间</th>
						<th>终端信息</th>
						<th>IP</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty userLoginLogList}">
						<c:forEach items="${userLoginLogList}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${item.username}</td>
							<td>${item.accessType}</td>
							<td><date:date value ="${item.accessTime}"/></td>
							<td>${item.agentInfo}</td>
							<td>${item.ip}</td>
						</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty userLoginLogList }">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			<tags:page page="${page}"/>
		</div>
	</div>
	
	<%@ include file="../../common/footer.jsp"%>
