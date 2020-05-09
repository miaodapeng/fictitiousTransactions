<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="工程师列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="searchfunc">
	<form method="post"
		action="${pageContext.request.contextPath}/aftersales/engineer/list.do"
		id="search">
		<ul>
			<li><label class="infor_name">姓名</label> <input type="text"
				class="input-middle" name="name" id="name"
				value="${engineer.name }" /></li>
			<li><label class="infor_name">公司名称</label> <input type="text"
				class="input-middle" name="company" id="company"
				value="${engineer.company }" /></li>
			<li><label class="infor_name">手机号</label> <input type="text"
				class="input-middle" name="mobile" id="mobile"
				value="${engineer.mobile }" /></li>
			<li><label class="infor_name">备注</label> <input type="text"
				class="input-middle" name="comments" id="comments"
				value="${engineer.comments }" /></li>
			<li><label class="infor_name">地区</label> <select
				name="province">
					<option value="0">请选择</option>
					<c:if test="${not empty provinceList }">
						<c:forEach items="${provinceList }" var="province">
							<option value="${province.regionId }"
								<c:if test="${ not empty engineer &&  province.regionId == engineer.province }">selected="selected"</c:if>>${province.regionName }</option>
						</c:forEach>
					</c:if>
			</select> <select name="city">
					<option value="0">请选择</option>
					<c:if test="${not empty cityList }">
						<c:forEach items="${cityList }" var="city">
							<option value="${city.regionId }"
								<c:if test="${ not empty engineer &&  city.regionId == engineer.city }">selected="selected"</c:if>>${city.regionName }</option>
						</c:forEach>
					</c:if>
			</select> <select name="zone">
					<option value="0">请选择</option>
					<c:if test="${not empty zoneList }">
						<c:forEach items="${zoneList }" var="zone">
							<option value="${zone.regionId }"
								<c:if test="${ not empty engineer &&  zone.regionId == engineer.zone }">selected="selected"</c:if>>${zone.regionName }</option>
						</c:forEach>
					</c:if>
			</select></li>
		</ul>
		<div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 "
				onclick="search();" id="searchSpan">搜索</span> <span
				class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			<span class="bt-small bg-light-blue bt-bg-style mr20 pop-new-data"
				layerParams='{"width":"850px","height":"450px","title":"新增工程师","link":"./add.do"}'>新增工程师</span>
			<span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span>
		</div>
	</form>
</div>
<div class="content">
	<div class="list-page normal-list-page">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid4">序号</th>
						<th class="wid10">姓名</th>
						<th>公司名称</th>
						<th>地区</th>
						<th class="wid10">手机号</th>
						<th class="wid10">服务次数</th>
						<th>维修产品</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty list}">
						<c:forEach items="${list }" var="engineer" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td><a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewengineer${engineer.engineerId}","link":"./aftersales/engineer/view.do?engineerId=${engineer.engineerId}","title":"查看工程师"}'>${engineer.name }</a></td>
								<td>${engineer.company }</td>
								<td>${engineer.areaStr }</td>
								<td>${engineer.mobile}</td>
								<td>${engineer.serviceTimes }</td>
								<td>${engineer.serviceProducts }</td>
								<td>${engineer.comments }</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty list }">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！</div>
			</c:if>
	</div>
	<div>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/list_engineer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>

<%@ include file="../../common/footer.jsp"%>
