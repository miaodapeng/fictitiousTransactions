<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/action/edit_action.js?rnd=<%=Math.random()%>'></script>
<div class="editElement">
	<form action="" method="post" id="editActionForm">
		<input type="hidden" name="actionId" value="${action.actionId}">
		<ul class="edit-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='parentId'>系统名称</lable>
				</div>
				<div class="f_left">
					<select name="platformId" id="platformId" class="input-middle">
						<option value="0">请选择</option>
						<c:forEach var="list" items="${platformsList}">
							<option value="${list.platformId}" <c:if test="${list.platformId eq action.platformId}">selected="selected"</c:if>
							>${list.platformName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actiongroupId'>功能分组</lable>
				</div>
				<div class="f_left">
					<select name="actiongroupId" id="actiongroupId" class="input-middle">
						<c:forEach var="list" items="${groupList}" varStatus="status">
							<option value="${list.actiongroupId}" <c:if test="${list.actiongroupId eq action.actiongroupId}">selected</c:if>>${list.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='controllerName'>控制器名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='controllerName' id='controllerName' value="${action.controllerName}" class="input-middle" maxlength="32"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionName'>功能名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='actionName' id='actionName' value="${action.actionName}" class="input-middle" maxlength="64"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='moduleName'>模块名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='moduleName' id='moduleName' value="${action.moduleName}" class="input-middle"  />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionDesc'>功能描述</lable>
				</div>
				<div class="f_left">
					<input type="text" name='actionDesc' id='actionDesc' value="${action.actionDesc}" class="input-middle" maxlength="128"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionDesc' style="width:80px;">排序</lable>
				</div>
				<div class="f_left">
					<input type="text" name='sort' id='sort' class="input-middle" value="${action.sort}" maxlength="6"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='isMenu'>是否菜单</lable>
				</div>
				<div class="f_left inputfloat">
					<input type="hidden" name="isMenu" /> 
					<input type="radio" id='isMenu_y' name="menuYn" <c:if test="${action.isMenu eq 1}">checked</c:if> />
						<label class='mr10'>是</label>
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" id='isMenu_n' name="menuYn" <c:if test="${action.isMenu eq 0}">checked</c:if> />
						<label>否</label>
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="edit-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
