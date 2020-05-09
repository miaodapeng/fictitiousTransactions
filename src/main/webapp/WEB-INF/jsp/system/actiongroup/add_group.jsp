<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/ationgroup/add_group.js?rnd=<%=Math.random()%>'></script>
<div class="addElement">
	<form method="post" id="addActionGroupform">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='parentId'>系统名称</lable>
				</div>
				<div class="f_left">
					<select name="platformId" id="platformId" class="input-middle">
						<option value="0">请选择</option>
						<c:forEach var="list" items="${platformList}">
							<option value="${list.platformId}">${list.platformName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='parentId'>功能分组</lable>
				</div>
				<div class="f_left">
					<select name="parentId" id="parentId" class="input-middle">
						<option value="0">一级菜单</option>
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
					<lable for='name'>功能分组名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='name' id='name' class="input-middle"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='orderNo' style="width:80px;">排序</lable>
				</div>
				<div class="f_left">
					<input type="text" name='orderNo' id='orderNo' class="input-middle" />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<lable for='orderNo' style="width:80px;">图标</lable>
				</div>
				<div class="f_left">
					<input type="text" name='iconStyle' id='iconStyle' class="input-middle" />
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit" >提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
