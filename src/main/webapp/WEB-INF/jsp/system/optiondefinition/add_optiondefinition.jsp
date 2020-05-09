<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增数据字典" scope="application" />	
<%@ include file="../../common/common.jsp"%>
	<div class="form-list  form-tips8">
		<form method="post" id="myform">
			<ul>
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>作用域</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" class=" input-large" name="scope" id="scope"/>
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
				<button type="submit">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/optiondefinition/add_optiondefinition.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>