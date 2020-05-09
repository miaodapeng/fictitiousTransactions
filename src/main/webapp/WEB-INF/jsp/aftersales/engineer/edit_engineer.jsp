<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑工程师" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<input id="companyName" type="hidden" value="${companyName }">
	<input id="oldCompanyName" type="hidden" value="${engineer.company }">
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
								<c:if test="${engineer.owner == 1 }">checked="checked"</c:if>> <label>内部</label></li>
							<li><input type="radio" name="owner" value="2"
								<c:if test="${engineer.owner == 2 }">checked="checked"</c:if>> <label>外部</label>
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
						<input type="text" class=" input-large" name="name" id="name" value="${engineer.name }"/>
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
								<c:if test="${engineer.sex == 1 }">checked="checked"</c:if>> <label>男</label></li>
							<li><input type="radio" name="sex" value="0" <c:if test="${engineer.sex == 0 }">checked="checked"</c:if>> <label>女</label>
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
						<input type="text" class=" input-large" name="mobile" id="mobile" value="${engineer.mobile }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>电话</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="telephone" id="telephone" value="${engineer.telephone }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>微信</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="weixin" id="weixin" value="${engineer.weixin }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>从业年份</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="workYear" id="workYear" value="<c:if test="${engineer.workYear > 0 }">${engineer.workYear }</c:if>"/>
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
									<option value="${province.regionId }"
										<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="city">
							<option value="0">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="city">
									<option value="${city.regionId }"
										<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="zone" id="zone">
							<option value="0">请选择</option>
							<c:if test="${not empty zoneList }">
								<c:forEach items="${zoneList }" var="zone">
									<option value="${zone.regionId }"
										<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>公司名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks <c:if test="${engineer.owner == 2 }">none</c:if>" id="inputName">
						${companyName }
					</div>
					<div class="form-blanks <c:if test="${engineer.owner == 1 }">none</c:if>" id="inputDiv">
						<input type="text" class=" input-largest " name="company"
							id="company" value="${engineer.company }"/>
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
							id="serviceProducts">${engineer.serviceProducts }</textarea>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>备注</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<textarea rows="" cols="98" name="comments" id="comments">${engineer.comments }</textarea>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="engineerId" value="${engineer.engineerId }">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/edit_engineer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>