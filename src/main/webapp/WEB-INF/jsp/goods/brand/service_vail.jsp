<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!-- 验证标签 -->
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../../common/common.jsp"%>
<%-- <script type="text/javascript" src='<%= basePath %>static/js/goods/brand/service_vail.js?rnd=<%=Math.random()%>'></script> --%>
<div class="addElement">
	<!-- 此处只能使用form表单提交，若使用异步提交，返回错误直接到ajax：error中不能展示在页面上 -->
	<sf:form modelAttribute="brand" method="post" id="serviceVailform"
		action="./saveTestVail.do">
		<%--全部错误信息提示 --%>
		<sf:errors path="*"></sf:errors>
		<ul class="add-detail">
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='brandName'>品牌名称</lable>
				</div>
				<div class="f_left">
					<sf:input path="brandName" name="brandName" />
					<sf:errors path="brandName" />
					<!-- brandName字段错误信息提示 -->
					<!-- <input type="text" name='brandName' id='brandName' class="input-middle" /> -->
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='brandWebsite'>品牌商</lable>
				</div>
				<div class="f_left">
					<sf:input path="owner" name="owner" />
					<sf:errors path="owner" />
				</div>
				<div class="clear"></div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable for='brandWebsite'>品牌连接</lable>
				</div>
				<div class="f_left">
					<sf:input path="brandWebsite" name="brandWebsite" />
					<sf:errors path="brandWebsite" />
				</div>
				<div class="clear"></div>
			</li>
			<div class="clear"></div>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
	</sf:form>
</div>
