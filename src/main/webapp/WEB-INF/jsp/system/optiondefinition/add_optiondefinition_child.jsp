<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增子集数据字典" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="form-list  form-tips8">
		<form method="post" id="myform">
			<ul>
				<li>
					<div class="form-tips">
						<lable>父级名称</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							${sysOptionDefinition.title }
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>名称</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class=" input-large" name="title" id="title"/>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>类别</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class=" input-large" name="optionType" id="optionType"/>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>排序</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class=" input-large" name="sort" id="sort" value="100"/>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>备注</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<textarea rows="" cols="35" name="comments" id="comments"></textarea>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="parentId" value="${sysOptionDefinition.sysOptionDefinitionId }">
				<button type="submit">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/optiondefinition/add_optiondefinition_child.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>