<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="功能列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<body onload="sort('${action.sortField}','${action.order}')">
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/system/action/querylistpage.do" method="post" id="search">
				<ul>
	                <li>
	            		<label class="infor_name">控制器名称</label>
	            		<input type="text" class="input-middle" name="controllerName" id="controllerName" value="${action.controllerName }">
            		</li>
            		<li>
						<label class="infor_name">功能名称</label>
						<input type="text" class="input-middle" name="actionName" id="actionName" value="${action.actionName }">
            		</li>
            		<li>
						<label class="infor_name">模块名称</label>
						<input type="text" class="input-middle" name="moduleName" id="moduleName" value="${action.moduleName }">
            		</li>
            		<li>
						<label class="infor_name">功能描述</label>
						<input type="text" class="input-middle" name="actionDesc" id="actionDesc" value="${action.actionDesc }">
            		</li>
					<li>
						<label class="infor_name">系统名称</label>
						<select class="input-small f_left" name="platformId">
							<option value="">请选择</option>
							<c:forEach items="${platformsList}" var="platform">
								<option value="${platform.platformId}"
										<c:if test="${platform.platformId eq action.platformId}">selected="selected"</c:if>
								>${platform.platformName}</option>
							</c:forEach>
						</select>
					</li>
            		<li>
	            		<label class="infor_name">分组名称</label>
	            		<select class="input-small f_left" name="actiongroupId">
							<option value="0">请选择</option>
							<c:forEach items="${actiongroupList }" var="actiongroup">
							<option value="${actiongroup.actiongroupId }" <c:if test="${action.actiongroupId eq actiongroup.actiongroupId}">selected="selected"</c:if>>${actiongroup.nameArr }</option>
							</c:forEach>
					 	</select>
            		</li>
            		<li>
	            		<label class="infor_name">菜单</label>
	            		<select class="input-smaller f_left" name="isMenu">
							<option value="-1">请选择</option>
							<option value="0" <c:if test="${action.isMenu eq 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${action.isMenu eq 1}">selected="selected"</c:if>>是</option>
					 	</select>
            		</li>
           		</ul>
           		<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"350px","title":"新增功能","link":"./addAction.do"}'>新增功能</span>
					<span class="bg-light-blue bt-bg-style bt-small addtitle" tabtitle="{&quot;num&quot;:&quot;ueditorer&quot;,&quot;link&quot;:&quot;./fileUpload/uEditorer.do&quot;,&quot;title&quot;:&quot;上传测试&quot;}">上传测试</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="testQRcu()">二维码测试</span>
				</div>
			</form>
		</div>
		<div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr class="sort">
						<th class="sorts">序号</th>
						<th>系统名称</th>
						<th>分组名称</th>
						<th abbr="CONTROLLER_NAME">控制器名称</th>
						<th class="wid25" abbr="ACTION_NAME">功能名称</th>
						<th>模块名称</th>
						<th>功能描述</th>
						<th>排序</th>
						<th>菜单</th>
						<th>创建时间</th>
						<th class="wid15">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${actionList}" var="action" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${action.platformName}</td>
						<td>${action.groupName}--${action.actionId}</td>
						<td>${action.controllerName}</td>
						<td>${action.actionName}</td>
						<td>${action.moduleName }</td>
						<td>${action.actionDesc }</td>
						<td>${action.sort }</td>
						<td>
							<c:choose>
								<c:when test="${action.isMenu==1}">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<date:date value ="${action.addTime}"/>
						</td>
						<td>
							<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"350px","title":"编辑功能","link":"./editAction.do?actionId=${action.actionId}"}'>
									编辑
							</span>
							<span class="delete" onclick="delAction(${action.actionId});">删除</span>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty actionList }">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			<tags:page page="${page}"/>
		</div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/action/main_action.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>