<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑批准公示" scope="application" />	
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/approval/approval_modifyrecord.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="content">
	<div class='newemployee  formpublic'>
		<form action="${pageContext.request.contextPath}/approval/approval/modifyrecord.do" method="post" id="addApprovalForm">
			<div class="main-container">
				<h1>跟进记录</h1>
			<ul>
				<div class='ml70 follow-record'>
           				<textarea cols="f_left" id="recordContent" name="recordContent" placeholder="请输入跟进内容,最多不超过500字" rows=3 maxLength="500">${approvalRecordEntity.recordContent}</textarea>
           			</div>
				
			</ul>
		</div>
			
			<!-- 保存按钮的div -->
			 <div class="add-tijiao employeesubmit">
				<c:if test="${not empty approvalRecordEntity and approvalRecordEntity.recordId > 0 }">
					<input type="hidden" name="recordId" value="${approvalRecordEntity.recordId }">
				</c:if>
				<button id="submit" type="submit">保存</button>
				<button type="button" class="dele" id="cancle" onclick='pagesContrlpages(true,false,false)'>返回</button>
			</div> 
			
		</form>
		
	</div>
</div>

<%@ include file="../common/footer.jsp"%>





