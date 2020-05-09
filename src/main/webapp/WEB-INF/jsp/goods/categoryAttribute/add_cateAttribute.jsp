<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/categoryAttribute/add_cateAttribute.js?rnd=<%=Math.random()%>'></script>
<div class="addElement">
	<form action="" method="post" id="addCateAttributeForm">
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='categoryAttributeName'>属性名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='categoryAttributeName' id='categoryAttributeName' class="input-larger"/>
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='categoryId'>产品分类</lable>
				</div>
				<div class="f_left special ">
					<select style="width: 100px;" id='categoryOpt' name='categoryOpt' onchange="updateCategory(this);" >
						<option value="-1" id="1">请选择</option>
						<c:forEach var="list" items="${categoryList}" varStatus="status">
							<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
						</c:forEach>
					</select>
					<span id="spanId" class='ml5'></span>
					<input type="hidden" name="categoryId" id="categoryId"/>
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
						<option value="-1">请选择</option>
						<option value="0">固定值单选</option>
						<option value="1">固定值复选</option>
						<!-- <option value="2">文本输入</option> -->
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
					<input type="hidden" name="isSelected" id="isSelected"/> 
					<input type="radio" id='isSelected_y' name="isSelectedRad" value="1" checked />
						<label class='mr10'>是</label>
					<input type="radio" id='isSelected_n' name="isSelectedRad" value="0" />
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
					<input type="hidden" name="isFilter" id="isFilter"/> 
					<input type="radio" id='isFilter_y' name="isFilterRad" value="1" checked />
						<label class='mr10'>是</label>
					&nbsp;&nbsp;
					<input type="radio" id='isFilter_n' name="isFilterRad" value="0" />
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
