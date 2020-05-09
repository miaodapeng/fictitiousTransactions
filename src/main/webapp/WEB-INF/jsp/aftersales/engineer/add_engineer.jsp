<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增工程师" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<input id="companyName" type="hidden" value="${companyName }">
	<form method="post" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>性质</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<ul>
							<li><input type="radio" name="owner" value="1"
								checked="checked"> <label>内部</label></li>
							<li><input type="radio" name="owner" value="2"> <label>外部</label>
							</li>
						</ul>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>姓名</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="name" id="name" />
					</div>
				</div>
			</li>
			<li class="none">
				<div class="form-tips">
					<lable>性别</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<ul>
							<li><input type="radio" name="sex" value="1"
								checked="checked"> <label>男</label></li>
							<li><input type="radio" name="sex" value="0"> <label>女</label>
							</li>
						</ul>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>手机号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="mobile" id="mobile" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>地区</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select class="input-middle mr6" name="province">
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="province">
									<option value="${province.regionId }">${province.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="city">
							<option value="0">请选择</option>
						</select> <select class="input-middle mr6" name="zone" id="zone">
							<option value="0">请选择</option>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>公司名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks" id="inputName">
						${companyName }
					</div>
					<div class="form-blanks none" id="inputDiv">
						<input type="text" class=" input-largest " name="company"
							id="company" value="${companyName }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>维修产品</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<textarea rows="" cols="98" name="serviceProducts"
							id="serviceProducts"></textarea>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>备注</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<textarea rows="" cols="98" name="comments" id="comments"></textarea>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/add_engineer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>