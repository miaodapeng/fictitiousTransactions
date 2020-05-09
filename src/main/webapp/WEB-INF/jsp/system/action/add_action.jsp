<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/action/add_action.js?rnd=<%=Math.random()%>'></script>
<div class="addElement">
	<form action="" method="post" id="addActionform">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='parentId'>系统名称</lable>
				</div>
				<div class="f_left">
					<select name="platformId" id="platformId" class="input-middle">
						<option value="0">请选择</option>
						<c:forEach var="list" items="${platformsList}">
							<option value="${list.platformId}">${list.platformName}</option>
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
							<option value="${list.actiongroupId}">${list.name}</option>
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
					<input type="text" name='controllerName' id='controllerName' class="input-middle" maxlength="32"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionName'>功能名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='actionName' id='actionName' class="input-middle" maxlength="32"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='moduleName'>模块名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='moduleName' id='moduleName' class="input-middle"  />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionDesc' style="width:80px;">功能描述</lable>
				</div>
				<div class="f_left">
					<input type="text" name='actionDesc' id='actionDesc' class="input-middle" maxlength="128"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='actionDesc' style="width:80px;">排序</lable>
				</div>
				<div class="f_left">
					<input type="text" name='sort' id='sort' class="input-middle" maxlength="32"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> 
					<lable for='isMenu'>是否菜单</lable>
				</div>
				<div class="f_left inputfloat">
					<input type="hidden" name="isMenu" /> 
					<input type="radio" id='isMenu_y' name="menuYn" value="1" checked  style="width:12px; height:15px;"/>
						<label class='mr10'>是</label>
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<input type="radio" id='isMenu_n' name="menuYn" value="0" style="width:12px; height:15px;" />
						<label>否</label>
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
