<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${exist.platformName}平台修改权限" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/base.css?rnd=<%=Math.random()%>" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/index.css?rnd=<%=Math.random()%>" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/admin/role/list.css?rnd=<%=Math.random()%>" />
	<div class="content mt10">
		<form method="post" id="editMenuForm" action="${pageContext.request.contextPath}/system/role/savemenu.do">
			<input type="hidden" name="roleId" value="${role.roleId}">
			<input type="hidden" name="platformId" value="${platformId}">
			<section>
				<div class="listBox">
					<c:forEach items="${allGroupList}" var="group" varStatus="groupIndex">
						<!--根节点-->
						<c:if test="${group.level == 1}">
						<div class="listCloum">
							<h3>
								<input type="checkbox" value="${group.actiongroupId}" id="group-${groupIndex.index}"
									   class="inp_group" onchange="chooseMutiGroup(this)"
										<c:forEach items="${listGroup}" var="choosedGroup">
											<c:if test="${choosedGroup.actiongroupId==group.actiongroupId}"> checked="checked"</c:if>
										</c:forEach>
								/>-${group.name}
							</h3>
							<div class="details">
								<!--下级分组存在-->
								<c:if test="${not empty group.childNode}">
									<c:forEach items="${group.childNode}" var="childNode" varStatus="childNodeIndex">
										<div class="detailsCloum">
												<p><input type="checkbox" value="${childNode.actiongroupId}"
														  id="group-${groupIndex.index}-${childNodeIndex.index}"
														  onchange="chooseMutiGroup(this)"
														<c:forEach items="${listGroup}" var="choosedGroup">
															<c:if test="${choosedGroup.actiongroupId==childNode.actiongroupId}"> checked="checked"</c:if>
														</c:forEach>
												/>${childNode.actiongroupId}-${childNode.name}  ：</p>
												<c:if test="${not empty childNode.actionList}">
													<ul>
														<!--功能菜单-->
														<c:forEach items="${childNode.actionList}" var="eachAction" varStatus="actionIndex">
															<li>
															<input type="checkbox" value="${eachAction.actionId}" id="action-${groupIndex.index}-${childNodeIndex.index}-${actionIndex.index}"
																   onchange="chooseMutiAction(this)"
																	<c:forEach items="${listAction}" var="choosedAction">
																		<c:if test="${choosedAction.actionId==eachAction.actionId}"> checked="checked"</c:if>
																	</c:forEach>
															/>
															${eachAction.actionId}-${eachAction.actionDesc}
															</li>
														</c:forEach>
													</ul>
												</c:if>
										</div>
									</c:forEach>
								</c:if>
								<!--只有一级分组的情况 -->
								<c:if test="${not empty group.actionList}">
									<ul>
									<!--功能菜单-->
									<c:forEach items="${group.actionList}" var="oldAction" varStatus="action">
										<li>
											<input type="checkbox" value="${oldAction.actionId}"
												   id="action-${groupIndex.index}-${action.index}"
												   onchange="chooseMutiAction(this)"
													<c:forEach items="${listAction}" var="choosedAction">
														<c:if test="${choosedAction.actionId==oldAction.actionId}"> checked="checked"</c:if>
													</c:forEach>
											/>
										${oldAction.actionId}-${oldAction.actionDesc}
										</li>
									</c:forEach>
									</ul>
								</c:if>
							</div>
						</div>
						</c:if>
					</c:forEach>
				</div>
			</section>
			<div class="clear"></div>
			<input type="hidden" id="showInptId">
			<div class="edit-tijiao employeesubmit mt10 mb15">
				<button id="btn" type="button" onclick="btnSubmit();" class="bt-bg-style bg-deep-green">提交</button>
				<!-- <button class="dele">取消</button> -->
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>/static/js/role/edit_menu.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>
