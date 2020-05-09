<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增分类" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>分类名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class="input-middle" name="categoryName" id="categoryName"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>上级分类</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select name="parentId" id="parentId">
							<option value="0">顶级分类</option>
							<c:if test="${not empty categoryList }">
								<c:forEach items="${categoryList }" var="cate">
									<c:if test="${cate.level lt 2 }">
									<option value="${cate.ordergoodsCategoryId }">${cate.categoryName }</option>
									</c:if>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>包邮最小起订金额</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class="input-middle" name="minAmount" id="minAmount" value="0"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>是否启用</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<ul>
							<li><input type="radio" name="status" value="1" checked="checked"> <label>启用</label></li>
							<li><input type="radio" name="status" value="0"> <label>禁用</label></li>
						</ul>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>排序</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class="input-smaller" name="sort" id="sort" value="100"/>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="ordergoodsStoreId" value="${ordergoodsCategory.ordergoodsStoreId }">
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/ordergoods/category/category_add.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>