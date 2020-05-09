<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/firstengage/category/add_category.js?rnd=<%=Math.random()%>'></script>
<div class="addElement">
	<form action="" method="post" id="addCategoryform">
		<input type="text" name='categoryParentId' id='categoryParentId' value="${category.categoryId}" class="input-middle" />
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='controllerName'>分类名称</lable>
				</div>
				<div class="f_left">
					<input type="text" name='categoryName' id='categoryName' class="input-middle" />
				</div>
				<div class="clear"></div>
			</li>
			<li id ="category_bigdiv">
				<div class="infor_name">
					<span>*</span> 
					<lable for='parentId'>上级分类</lable>
				</div>
				<div class="f_left" id="category_div">
                    	<select id='categoryOpt' name='categoryOpt' onchange="updateCategory(this);">
							<option value="0" id="0">顶级</option>
							<c:forEach var="list" items="${categoryList}" varStatus="status">
								<option value="${list.categoryId}" id="${list.level}" alt="${list.level}"  pid="${list.parentId}">${list.categoryName}</option>
							</c:forEach>
						</select>
				</div>
				<input type="hidden" name="parentId" id="parentId" value="0" />
				<div class="clear"></div>
			</li>
			<!-- <li>
				<div class="infor_name">
					<span>*</span> 
					<lable for='sort'>排序</lable>
				</div>
				<div class="f_left">
					<input type="text" name='sort' id='sort' class="input-middle" maxlength="10"/>
				</div>
				<div class="clear"></div>
			</li> -->
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</form>
</div>
