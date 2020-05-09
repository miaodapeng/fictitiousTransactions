<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="跨部门联系人" scope="application" />
<%@ include file="../../common/common.jsp"%>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">联系人列表</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="">部门</th>
					<th class="">英文名</th>
					<th class="">负责日常工作</th>
					<th class="">手机号码</th>				
				</tr>
			</thead>
			<tbody >
				<td>财务部</td>
				<td>Elaine</td>
				<td>负责财务部特殊情况的处理</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('15950502146',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>
			<tbody>
				<td>财务部</td>
				<td>Alin</td>
				<td>负责报销、收票</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('13814110543',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>	
			<tbody>
				<td>财务部</td>
				<td>Lily</td>
				<td>负责结款、付款</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('13834969427',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>
			<tbody>
				<td>财务部</td>
				<td>Hebe</td>
				<td>负责开票、寄票</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('17633950413',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>	
			<tbody>
				<td>物流部</td>
				<td>Nick</td>
				<td>斯坦德仓库对外负责人</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('18551411721',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>	
			<tbody>
				<td>物流部</td>
				<td>Frank</td>
				<td>分仓对外负责人</td>
				<td><i class="icontel cursor-pointer" onclick="calloutNoScreen('13002520627',0,0,0,0,0);"></i>点击拨打电话</td>
			</tbody>	
		</table>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/call/call.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>