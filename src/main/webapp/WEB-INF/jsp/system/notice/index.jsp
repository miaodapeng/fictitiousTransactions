<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="公告管理" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/notice/index.do"
			method="get" id="search">
			<ul>
				<li><label class="infor_name">公告标题</label> <input type="text"
					class="input-middle" name="title" id="title"
					value="${notice.title}" /></li>
				<li><label class="infor_name">公告类型</label> <select
					class="input-middle f_left" name="noticeCategory">
						<option value="0">请选择</option>
						<c:forEach items="${categoryList }" var="cate">
							<option value="${cate.sysOptionDefinitionId }"
								<c:if test="${notice.noticeCategory != null and notice.noticeCategory == cate.sysOptionDefinitionId}">selected="selected"</c:if>>
								${cate.title }</option>
						</c:forEach>
				</select></li>
				<li><label class="infor_name">状态</label> <select
					class="input-middle f_left" name="isEnable">
						<option value="-1">请选择</option>
						<option value="0"
							<c:if test="${notice.isEnable != null and notice.isEnable =='0'}">selected="selected"</c:if>>未发布</option>
						<option value="1"
							<c:if test="${notice.isEnable != null and notice.isEnable =='1'}">selected="selected"</c:if>>已发布</option>
				</select></li>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style addtitle"
					tabTitle='{"num":"addnotice","link":"./system/notice/add.do","title":"新增公告"}'>新增公告</span>
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="sorts">序号</th>
					<th>公告类型</th>
					<th>公告标题</th>
					<th>状态</th>
					<th>置顶</th>
					<th>添加时间</th>
					<th>添加人</th>
					<th style="width: 20%">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty noticeList}">
					<c:forEach items="${noticeList}" var="list" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${list.noticeCategoryName }</td>
							<td class="text-ellipsis" title="${list.title }">${list.title }</td>
							<td><c:choose>
									<c:when test="${list.isEnable == 1 }">
										<span class="onstate">已发布</span>
									</c:when>
									<c:otherwise>
										<span class="offstate">未发布</span>
									</c:otherwise>
								</c:choose></td>
							<td><c:if test="${list.isTop == 1 }">
									<span class="offstate">置顶</span>
								</c:if></td>
							<td><date:date value="${list.addTime} " /></td>
							<td>${list.creatorName }</td>
							<td><span class="edit-user addtitle"
								tabTitle='{"num":"editnotice${list.noticeId}","link":"./system/notice/edit.do?noticeId=${list.noticeId}","title":"编辑公告"}'>编辑</span>
								<c:choose>
									<c:when test="${list.isEnable == 0}">
										<span class="edit-user"
											onclick="setEnable(${list.noticeId},0);">发布</span>
									</c:when>
									<c:otherwise>
										<span class="forbid clcforbid"
											onclick="setEnable(${list.noticeId},1);">取消发布</span>
									</c:otherwise>
								</c:choose> <c:choose>
									<c:when test="${list.isTop == 0}">
										<span class="edit-user" onclick="setTop(${list.noticeId},0);">置顶</span>
									</c:when>
									<c:otherwise>
										<span class="forbid clcforbid"
											onclick="setTop(${list.noticeId},1);">取消置顶</span>
									</c:otherwise>
								</c:choose></td>
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