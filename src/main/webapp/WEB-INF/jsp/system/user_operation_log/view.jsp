<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<div>
	<ul class="new-table">
		<li>
			<div class="bor infor_name">操作者</div>
			<div class="f_left ">${userOperationLog.username}</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">行为</div>
			<div class="f_left">
				<c:choose>
					<c:when test="${userOperationLog.operationType == 0}">新增</c:when>
					<c:when test="${userOperationLog.operationType == 1}">修改</c:when>
					<c:when test="${userOperationLog.operationType == 2}">删除</c:when>
					<c:when test="${userOperationLog.operationType == 3}">导出</c:when>
				</c:choose>
			</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">功能点</div>
			<div class="f_left ">${userOperationLog.actionName}</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">IP地址</div>
			<div class="f_left">${userOperationLog.accessIp}</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">备注说明</div>
			<div class="f_left">${userOperationLog.desc}</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">操作结果</div>
			<div class="f_left">
				<c:choose>
					<c:when test="${userOperationLog.resultStatus == 0}"><span class="onstate">成功</span></c:when>
					<c:when test="${userOperationLog.resultStatus == -1}"><span class="offstate">失败</span></c:when>
				</c:choose>
			</div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">时间</div>
			<div class="f_left"><date:date value="${userOperationLog.accessTime}" /></div>
			<div class="clear"></div>
		</li>
		<li>
			<div class="bor infor_name">操作详情</div>
			<div class="f_left"></div>
			<div class="clear"></div>
		</li>
	</ul>
</div>
