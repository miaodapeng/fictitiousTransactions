<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑单位" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/unit/edit.js?rnd=<%=Math.random()%>'></script>
<div class="addElement">
	<form action="" method="post" id="editUnitform">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='unitGroupId'>单位分组</lable>
				</div>
				<div class="f_left">
					<select name="unitGroupId" id="unitGroupId" class="input-middle">
						<c:forEach var="list" items="${unitGroupList}" varStatus="status">
							<option value="${list.unitGroupId}" <c:if test="${list.unitGroupId eq unit.unitGroupId}">selected="selected"</c:if>>${list.groupName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='unitName'>单位名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='unitName' id='unitName' class="input-middle" value="${unit.unitName}" />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<lable for='unitNameEn'>英文名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='unitNameEn' id='unitNameEn' class="input-middle" value="${unit.unitNameEn}" />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='sort' style="width:80px;">排序</lable>
				</div>
				<div class="f_left">
					<input type="text" name='sort' id='sort' class="input-middle" value="${unit.sort}" />
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="unitId" value="${unit.unitId}"/>
			<input type="hidden" name="beforeParams" value='${beforeParams}'>
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>