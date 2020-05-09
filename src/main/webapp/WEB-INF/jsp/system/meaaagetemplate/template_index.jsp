<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="消息模板" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/messagetemplate/index.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">消息模板标题</label> <input type="text"
					class="input-middle" name="title" id="title"
					value="${messageTemplate.title}" /></li>
				<li><label class="infor_name">消息模板用途</label>
				   <select class="input-middle" name="type">
                     <option selected="selected" value="-1">全部</option>
                     <option value="0" <c:if test="${messageTemplate.type eq 0}">selected="selected"</c:if>>站内信</option>
                     <option value="1" <c:if test="${messageTemplate.type eq 1}">selected="selected"</c:if>>邮件</option>
                     <option value="2" <c:if test="${messageTemplate.type eq 2}">selected="selected"</c:if>>短信</option>
                   </select>
                </li>
			</ul>
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style addtitle"
					tabTitle='{"num":"addmessagetemplate","link":"./system/messagetemplate/add.do","title":"新增消息模板"}'>新增消息模板</span>
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid4">序号</th>
					<th class="wid10">消息模板ID</th>
					<th class="wid10">消息模板用途</th>
					<th>消息模板标题</th>
					<th class="wid14">添加时间</th>
					<th class="wid8">添加人</th>
					<th class="wid12">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty messageTemplateList}">
					<c:forEach items="${messageTemplateList}" var="list" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${list.messageTemplateId }</td>
							<td><c:choose>
									<c:when test="${list.type == 0 }">
										<span>站内信</span>
									</c:when>
									<c:when test="${list.type == 1 }">
										<span>邮件</span>
									</c:when>
									<c:when test="${list.type == 2 }">
										<span>短信</span>
									</c:when>
								</c:choose></td>
							<td class="text-left">
	                            <div class="customername pos_rel">
	                            <div>${list.title } <i class="iconbluemouth"></i> <br></div>
	                            <div class="pos_abs customernameshow mouthControlPos">
										参数：${list.params } <br>
								</div>
								</div>
                            </td>
							<td><date:date value="${list.addTime} " /></td>
							<td>${list.creatorName }</td>
							<td><span class="edit-user addtitle"
								tabTitle='{"num":"editmessagetemplate${list.messageTemplateId}","link":"./system/messagetemplate/edit.do?messageTemplateId=${list.messageTemplateId}","title":"编辑消息模板"}'>编辑</span>
								<span class="bt-smaller bt-border-style border-red" onclick="delwl('${list.messageTemplateId}')">删除</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty messageTemplateList}">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/messagetemplate/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>