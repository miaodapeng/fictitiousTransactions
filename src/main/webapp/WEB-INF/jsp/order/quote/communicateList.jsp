<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">沟通记录</div>
				<div class="title-click nobor  pop-new-data" layerParams='{"width":"500px","height":"400px","title":"编辑客户信息","link":"editpages/addcomrecord.html"}'>
					新增</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td>沟通时间</td>
						<td>单号</td>
						<td>录音</td>
						<td>联系人</td>
						<td>联系方式</td>
						<td>沟通方式</td>
						<td>沟通目的</td>
						<td>沟通内容</td>
						<td>操作人</td>
						<td>下次联系日期</td>
						<td>下次沟通内容</td>
						<td>备注</td>
						<td>创建时间</td>
						<td>操作</td>
					</tr>
					<c:forEach var="list" items="${communicateList}" varStatus="status">
						<td><date:date value ="${list.begintime}"/> - <date:date value ="${list.endtime}"/></td>
						<td>${list.relatedId}</td>
						<td>--</td>
						<td>${list.contactName}</td>
						<td>${list.phone}</td>
						<td>${list.communicateModeName}</td>
						<td>${list.communicateGoalName}</td>
						<td>--</td>
						<td>${list.creatorName}</td>
						<td>${list.nextContactDate}</td>
						<td>${list.nextContactContent}</td>
						<td>${list.comments}</td>
						<td><date:date value ="${list.addTime}"/></td>
						<td>--</td>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty communicateList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
        	<tags:page page="${page}"/>
		</div>
