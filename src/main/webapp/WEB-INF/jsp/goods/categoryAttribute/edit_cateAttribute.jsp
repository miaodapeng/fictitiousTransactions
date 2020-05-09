<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/categoryAttribute/edit_cateAttribute.js?rnd=<%=Math.random()%>'></script>
<div class="editElement">
	<form action="" method="post" id="editCateAttributeForm">
		<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
		<input type="hidden" name="categoryAttributeId" id="categoryAttributeId" value="${categoryAttribute.categoryAttributeId}"/>
		<ul class="edit-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='categoryAttributeName'>属性名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='categoryAttributeName' id='categoryAttributeName' class="input-larger" value="${categoryAttribute.categoryAttributeName}"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='categoryId'>产品分类</lable>
				</div>
				<div class="f_left inputfloat" style='width:76%'>
					<%-- <select style="width: 100px;" id='categoryOpt' name='categoryOpt' onchange="updateCategory(this);">
						<option value="-1" id="1">请选择</option>
						<c:forEach var="list" items="${categoryList}" varStatus="status">
							<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
						</c:forEach>
					</select> --%>
					<span id="spanHtmlId"></span>
					<input type="hidden" name="categoryId" id="categoryId" value="${categoryAttribute.categoryId}"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='inputType'>数据类型</lable>
				</div>
				<div class="f_left">
					<select name="inputType" id="inputType" class="input-middle">
						<option value="0" <c:if test="${categoryAttribute.inputType eq 0}">selected</c:if>>固定值单选</option>
						<option value="1" <c:if test="${categoryAttribute.inputType eq 1}">selected</c:if>>固定值复选</option>
						<%-- <option value="2" <c:if test="${categoryAttribute.inputType eq 2}">selected</c:if>>文本输入</option> --%>
					</select>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> 
					<lable for='isSelected'>是否必填</lable>
				</div>
				<div class="f_left inputfloat">
					<input type="hidden" name="isSelected" id="isSelected" value="${categoryAttribute.isSelected}"/> 
					<input type="radio" id='isSelected_y' name="isSelectedRad" value="1" <c:if test="${categoryAttribute.isSelected eq 1}">checked</c:if> />
					<label class='mr10'>是</label>
					<input type="radio" id='isSelected_n' name="isSelectedRad" value="0" <c:if test="${categoryAttribute.isSelected eq 0}">checked</c:if> />
					<label>否</label>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name mt0">
					<span>*</span> 
					<lable for='isFilter'>检索条件</lable>
				</div>
				<div class="f_left inputfloat">
					<input type="hidden" name="isFilter" id="isFilter" value="${categoryAttribute.isFilter}"/> 
					<input type="radio" id='isFilter_y' name="isFilterRad" value="1" <c:if test="${categoryAttribute.isFilter eq 1}">checked</c:if> />
					<label class='mr10'>是</label>
					<input type="radio" id='isFilter_n' name="isFilterRad" value="0" <c:if test="${categoryAttribute.isFilter eq 0}">checked</c:if> />
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
