<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="系统帮助" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/notice/helpNoticeListPage.do"
			method="get" id="search">
			<ul>
				<%-- <li><label class="infor_name">公告标题</label> <input type="text"
					class="input-middle" name="title" id="title"
					value="${notice.title}" /></li> --%>
				<li><label class="infor_name">公告类型</label> <select
					class="input-middle f_left" name="noticeCategory">
						<option value="0">请选择</option>
						<c:forEach items="${categoryList }" var="cate">
							<option value="${cate.sysOptionDefinitionId }"
								<c:if test="${notice.noticeCategory != null and notice.noticeCategory == cate.sysOptionDefinitionId}">selected="selected"</c:if>>
								${cate.title }</option>
						</c:forEach>
				</select></li>
				<%-- <li><label class="infor_name">状态</label> <select
					class="input-middle f_left" name="isEnable">
						<option value="-1">请选择</option>
						<option value="0"
							<c:if test="${notice.isEnable != null and notice.isEnable =='0'}">selected="selected"</c:if>>未发布</option>
						<option value="1"
							<c:if test="${notice.isEnable != null and notice.isEnable =='1'}">selected="selected"</c:if>>已发布</option>
				</select></li> --%>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style addtitle"
					tabTitle='{"num":"addnotice","link":"./system/notice/add.do","title":"新增公告"}'>新增公告</span> -->
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					
					<th class="wid3">公告类型</th>
					<th class="wid30">公告标题</th>
					<th class="wid4">更新时间</th>
					<th class="wid3">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty noticeList}">
					<c:forEach items="${noticeList}" var="list" varStatus="status">
						<tr>
							<td>${list.noticeCategoryName }</td>
							<td class="text-ellipsis" title="${list.title }">${list.title }</td>
							
							<td><date:date value="${list.addTime} " /></td>
							
							<td><span class="edit-user addtitle"
								tabTitle='{"num":"viewnotice${list.noticeId}","link":"./system/notice/view.do?noticeId=${list.noticeId}","title":"查看"}'>查看</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty noticeList}">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/notice/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>