<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增编辑消息模板" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class='newemployee  formpublic'>
		<form method="post"
			action='<c:choose><c:when test="${not empty messageTemplate and messageTemplate.messageTemplateId > 0 }">${pageContext.request.contextPath}/system/messagetemplate/saveedit.do</c:when><c:otherwise>${pageContext.request.contextPath}/system/messagetemplate/saveadd.do</c:otherwise></c:choose>'
			id="myform">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span> <label>消息模板用途</label>
					</div>
					<div class="f_left">
						<select class="input-middle" name="type" id="type">
                            <option selected="selected" value="-1">全部</option>
                            <option value="0" <c:if test="${messageTemplate.type eq 0}">selected="selected"</c:if>>站内信</option>
		                     <option value="1" <c:if test="${messageTemplate.type eq 1}">selected="selected"</c:if>>邮件</option>
		                     <option value="2" <c:if test="${messageTemplate.type eq 2}">selected="selected"</c:if>>短信</option>
                        </select>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>消息模板标题</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name="title" id="title"
							value="${messageTemplate.title}" />
					</div>
					<div class="clear"></div>
				</li>
				<li class="mt5">
					<div class="infor_name mt0">
						<span>*</span> <label>消息模板内容</label>
					</div>
					<div class="f_left">
						<c:if test="${not empty messageTemplate.content}">
						<textarea rows="6" cols="61" name="content" id="content">${messageTemplate.content}</textarea>
						</c:if>
						<c:if test="${empty messageTemplate.content}">
						<textarea rows="6" cols="61" name="content" id="content"></textarea>
						</c:if>
					</div>
				</li>
			</ul>
			<div class="add-tijiao employeesubmit">
				<c:if test="${not empty messageTemplate and messageTemplate.messageTemplateId > 0 }">
					<input type="hidden" name="messageTemplateId" value="${messageTemplate.messageTemplateId }">
				</c:if>
				<button id="submit" type="submit">提交</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/messagetemplate/modify.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>