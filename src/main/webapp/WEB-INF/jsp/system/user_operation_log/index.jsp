<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="操作日志" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content">
		<div class="searchfunc employeesearch">
			<form
				action="${pageContext.request.contextPath}/system/useroperationlog/index.do"
				method="post" id="search">
				<ul>
	                <li>
	            		<label class="infor_name">用户名</label>
						<input type="text" class="input-middle" name="username" id="username" value="${userOperationLog.username}"> 
					</li>
            	</ul>
	            <div class="tcenter">	
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="sorts">序号</th>
						<th>操作者</th>
						<th>行为</th>
						<th>功能点</th>
						<th>IP地址</th>
						<th>备注说明</th>
						<th>操作结果</th>
						<th>时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty userOperationLogList}">
						<c:forEach items="${userOperationLogList}" var="item"
							varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${item.username}</td>
								<td>
									<c:choose>
										<c:when test="${item.operationType == 0}">新增</c:when>
										<c:when test="${item.operationType == 1}">修改</c:when>
										<c:when test="${item.operationType == 2}">删除</c:when>
										<c:when test="${item.operationType == 3}">导出</c:when>
									</c:choose>
								</td>
								<td>${item.actionName}</td>
								<td>${item.accessIp}</td>
								<td>${item.desc}</td>
								<td>
									<c:choose>
										<c:when test="${item.resultStatus == 0}"><span class="onstate">成功</span></c:when>
										<c:when test="${item.resultStatus == -1}"><span class="offstate">失败</span></c:when>
									</c:choose>
								</td>
								<td><date:date value="${item.accessTime}" /></td>
								<td><span
										class="edit-user pop-new-data"
										layerParams='{"width":"750px","height":"450px","title":"日志详情","link":"./viewUserOperationLog.do?userOperationLogId=${item.userOperationLogId}"}'>
											查看 </span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty userOperationLogList }">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			<tags:page page="${page}" />
		</div>
	</div>
	<%@ include file="../../common/footer.jsp"%>
