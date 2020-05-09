<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="录音文件" scope="application" />
<%@ include file="../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/voiceToText/base.css?rnd=<%=Math.random()%>" />
<link rel="stylesheet" href="<%=basePath%>static/css/voiceToText/VoiceToText.css?rnd=<%=Math.random()%>" />
<div class="addElement">
	<div class="voice-to-text">
		<div class="content">
			<!-- 原文 -->
			<div class="before-content">
				<div class="before-content-title">原文
					<span class="play" onclick="playrecordNew('${cr.coidUri}');">播放</span>
				</div>
				<div class="before-content-word">
					<p>${phoneticWriting.originalContent}</p>
				</div>
			</div>
			<!-- 修改后 -->
			<div class="after-content">
				<div class="after-content-title">修改后</div>
				<div class="after-content-word">
					<p id="updatedContent">${phoneticWriting.updatedContent}</p>
					<%--<input type="hidden" id="updatedContent" name="updatedContent" value="${phoneticWriting.updatedContent}"/>--%>
				</div>
			</div>
		</div>
		<!-- 替换 -->
		<div class="content-change">
			<div class="before">
				<div class="change-before">修正前：</div>
				<input class="input-before" type="text" id="controversialContent" name="controversialContent">
			</div>
			<div class="after">
				<div class="change-after">修正后：</div>
				<input class="input-after" type="text" id="modifyContent" name="modifyContent">
			</div>
			<div class="change-button">替换</div>
		</div>
		<!-- 修正记录 -->
		<div class="change-record">
			<div class="record-title">修正记录</div>
			<!-- 表格 -->
			<table class="record-table">
				<!-- 表头th -->
				<tr>
					<th class="th-number">序号</th>
					<th class="th-before">修正前</th>
					<th class="th-after">修正后</th>
					<th class="th-type">替换类型</th>
					<th class="th-use">操作</th>
				</tr>
				<!-- 表格内容td -->
				<c:forEach var="modificationRecord" items="${mrList}" varStatus="num">
					<tr id="modificationRecordText">
						<td class="td-number">${num.count}</td>
						<td class="td-before">${modificationRecord.controversialContent}</td>
						<td class="td-after">${modificationRecord.modifyContent}</td>
						<td style="display: none"><input type="text" id="controversialContentText" value="${modificationRecord.controversialContent}"/></td>
						<td class="td-type">
							<c:if test="${modificationRecord.type eq 0}">本页替换</c:if>
							<c:if test="${modificationRecord.type eq 1}">全局替换</c:if>
						</td>
						<td class="td-use">
							<c:if test="${modificationRecord.type eq 0}">
								<span class="delete" onclick="delRecords('${modificationRecord.modificationRecordId}');">删除</span>
								<span class="submit" onclick="updateAllModificationRecord('${modificationRecord.modificationRecordId}','${modificationRecord.controversialContent}','${modificationRecord.relatedId}');">提交至全局</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty mrList}">
					<tr style="text-align: center">
						<td colspan="5">暂无记录</td>
					</tr>
				</c:if>
			</table>
		</div>
		<!-- 点评 -->
		<div class="review">
			<!-- 点评标题 -->
			<div class="review-title">
				点评（<span class="review-number">${num}</span>）
			</div>
			<!-- 点评内容 -->
			<div class="review-content">
				<c:forEach var="comment" items="${comList}" varStatus="num">
					<p class="review-one">
						<span class="review-person">${comment.userName}:${comment.content}</span>
						<span class="review-date"><date:date value ="${comment.addTime}" format="yyyy-MM-dd HH:mm:ss"/></span>
					</p>
				</c:forEach>
			</div>
			<!-- 增加点评 -->
			<div class="review-add">
				<input id ="commentContent" class="review-input" type="text" placeholder="为该录音添加点评">
				<div class="review-button" onclick="addComments();">点评</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="communicateRecordId" value="${communicateRecord.communicateRecordId}">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/phoneticTranscription/jquery3.3.1min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/phoneticTranscription/voiceToText.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>