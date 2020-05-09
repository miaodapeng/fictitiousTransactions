<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加功能" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="content">
		<form method="post" id="addMenuForm">
			<input type="hidden" name="roleId" value="${role.roleId}">
			<c:forEach var="group" items="${groupList}" varStatus="i">
				<ul>
					<li>
						<div style="padding-left:${group.level * 10};" id="group" class="test">
							<input type="checkbox" value="${group.id}" id="group${i.index}" name="${group.str}" onchange="chooseGroup('${i.index}',this)" class="inp_group"> 
							<label>${group.text}</label>
						</div> 
						<c:if test="${!empty group.list}">
							<ul class="checkboxs">
								<c:forEach var="action" items="${group.list}" varStatus="j">
									<li style="padding-left:${(group.level * 10) + 20}" id="action">
										<input type="checkbox" value="${action.actionId}" id="action${i.index}" onchange="chooseAction('${i.index}',this)">
										<label>${action.actionDesc}</label>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
				</ul>
			</c:forEach>
			<div class="clear"></div>
			<input type="hidden" id="showInptId">
			<div class="add-tijiao employeesubmit">
				<button id="submit" type="submit">提交</button>
				<!-- <button class="dele">取消</button> -->
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>/static/js/role/add_menu.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>