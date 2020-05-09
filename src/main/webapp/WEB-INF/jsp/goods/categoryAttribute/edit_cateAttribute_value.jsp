<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/categoryAttribute/edit_cateAttribute_value.js?rnd=<%=Math.random()%>'></script>
<div class="form-list">
	<form method="post" id="editCateAttributeValueForm">
		<input type="hidden" name="beforeParams" value='${beforeParams}'><!-- 日志 -->
		<input type="hidden" name="categoryAttributeId" id="categoryAttributeId" value="${categoryAttributeValue.categoryAttributeId}"/>
		<div class="ml20">
			<span class="mr4"> 属性名称： </span> <span  style="font-size:14px;font-weight: 600;">${categoryAttributeValue.categoryAttributeName}</span>
		</div>
		<ul class="zhi">
		<c:choose>
			<c:when test="${empty cateAttrValueList}">
				<li>
					<div id="liId0">
						<input type="hidden" id="dataType" name="dataType" value="insert"/><!-- insert插入 -->
						<input type="hidden" id="categoryAttributeValueId" name="categoryAttributeValueId"><!-- 添加默认空值 -->
						<span class="font-red">*</span> 
						<label>值</label>
						<input type="text" class="input-middle mr6" name="attrValue" id="0"> 
						<label>排序</label>
						<input type="text" class="input-smaller" value="100" name="sort" id="0">
						<span class="deleattribute" onclick="delUl(this)" style='margin-left:7px;'>删除</span>
					</div>
				</li>
			</c:when>
			<c:otherwise>
				<c:forEach var="list" items="${cateAttrValueList}" varStatus="status">
					<li>
						<div id="liId${status.index}">
							<input type="hidden" id="dataType" name="dataType" value="edit"/><!-- edit编辑，del删除：默认编辑，点击删除的时候修改此值 -->
							<input type="hidden" id="categoryAttributeValueId" name="categoryAttributeValueId" value="${list.categoryAttributeValueId}">
							<span class="font-red">*</span> 
							<label>值</label>
							<input type="text" class="input-middle mr6" name="attrValue" id="${status.index}" value="${list.attrValue}"/> 
							<label>排序</label>
							<input type="text" class="input-smaller" name="sort" id="${status.index}" value="${list.sort}">
							<span class="deleattribute" onclick="delUl(this)" style='margin-left:7px;'>删除</span>
						</div>
					</li>
				</c:forEach>
			</c:otherwise>
		</c:choose>
				
		</ul>
		<span id="spanCateId"></span>
		<div class="bg-style bg-light-blue addattribute" style='margin: -5px 0 8px 73px;' onclick="addCateValue();">继续添加</div>
		<div class="font-grey9 mt10" style='margin-left:74px;'>
			友情提示：排序越大，优先级越高
		</div>
		<div class="add-tijiao tcenter mt10">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="close-layer">取消</button>
		</div>
	</form>

</div>
