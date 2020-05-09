<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增沟通记录 - 选择通话录音" scope="application" />
<%@ include file="../../common/common.jsp"%>

<div class="form-list  form-tips5">
	<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>录音ID</th>
					<th>公司名称</th>
					<th>号码</th>
					<th class="wid8">通话时间</th>
					<th class="wid8">操作</th>
				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty recordList}">
					<c:forEach items="${recordList }" var="list">
						<tr>
							<td>${list.communicateRecordId }</td>
							<td>${list.traderName}</td>
							<td>${list.phone}</td>
							<td><date:date value="${list.addTime} " /></td>
							<td class="font-blue"><a href="<%= basePath %>aftersales/order/addNewCommunicatePage.do?afterSalesId=${afterSalesId}&traderId=${traderId }&traderType=1&communicateRecordId=${list.communicateRecordId }">选择</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty recordList }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！</div>
		</c:if>
		<div>
			<tags:page page="${page}" />
		</div>
		<div class='clear'></div>
		<div class="table-friend-tip mb15" style="margin-top:0;">
			提示：若无录音，直接点击“下一步”
		</div>
		<div class="add-tijiao tcenter" id="myform">
			<a style="color:#fff; display:inline-block;padding:3px 7px; background: #5cb85c;border: 1px solid #4cae4c;margin-right: 6px;border-radius:2px;" href="<%= basePath %>aftersales/order/addNewCommunicatePage.do?afterSalesId=${afterSalesId}&traderId=${traderId }&traderType=1&communicateRecordId=-1">下一步</a>
            <button class="dele" type="button" id="close-layer">取消</button>
		</div>
</div>
<%@ include file="../../common/footer.jsp"%>