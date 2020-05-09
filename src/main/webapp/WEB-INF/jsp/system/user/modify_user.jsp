<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增|编辑用户" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class='newemployee  formpublic'>
		<c:if test="${not empty allErrors }">
		<div class="service-return-error">
            <ul>
				<c:forEach items="${allErrors }" var="error" varStatus="status">
                <li>${status.count}、${error.defaultMessage }</li>
				</c:forEach>
            </ul>
        </div>
		</c:if>
		<form method="post"
			id="userform">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span> <label>员工姓名</label>
					</div>
					<div class="f_left">
						<input type="text" name="realName" id="realName"
							value="${user.userDetail.realName}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name sex_name">
						<label>性别</label>
					</div>
					<div class="sex">
						<input type='radio' name='sex' value="1"
							<c:if test="${empty user.userDetail || user.userDetail.sex==1}">checked='true'</c:if> />
						<label>男</label> <input type='radio' name="sex"
							value="0"
							<c:if test="${not empty user.userDetail && user.userDetail.sex==0}">checked='true'</c:if> />
						<label>女</label>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable for='staff'>是否为贝登员工</lable>
					</div>
					<div class="f_left" style="width: 75%">
						<div class="form-blanks" id="staff">
							<ul>
								<c:if test="${empty user.staff}">
									<input type='radio' name="staff" value="1" onclick="update(this)"/> <label>是</label>
									<input type='radio' name="staff" value="0" onclick="update(this)"/> <label>否</label>
								</c:if>
								<c:if test="${not empty user.staff}">
									<input type='radio' name="staff" value="1" <c:if test="${user.staff==1}">checked='true'</c:if> onclick="update(this)"/> <label>是</label>
									<input type='radio' name="staff" value="0" <c:if test="${user.staff==0}">checked='true'</c:if> onclick="update(this)"/> <label>否</label>
								</c:if>
							</ul>
						</div>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable for='roleName'>可登录系统</lable>
					</div>
					<div class="f_left" style="width: 75%">
						<div class="form-blanks" id="system">
							<ul>
								<c:if test="${not empty platformList }">
									<c:forEach items="${platformList}" var="platform" varStatus="index">
											<input type="checkbox" id="systemId${index.count}" name="systemId" value="${platform.platformId}"
												<c:forEach items="${loginSystemList}" var="loginSystem">
													<c:if test="${loginSystem == platform.platformId}">checked</c:if>
													<c:if test="${user.staff== 0}">
														<c:if test="${index.count != 3}">
															disabled="disabled"
														</c:if>
													</c:if>
												</c:forEach>
											/><label>${platform.platformName}</label>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
                    <li id="belongCompanyName"
                        <c:if test="${user.staff==1}">style="display: none"</c:if>
                    >
                        <div class="infor_name">
                            <span>*</span> <label>所属公司</label>
                        </div>
                        <div class="f_left">
                            <input type="text" id="showBelongCompanyName" name="belongCompanyName"
                                   value="${belongCompany}"/>
                        </div>
                        <div class="clear"></div>
                    </li>
				<li class="specialli mb0" id="orgPositLi">
					<div class="infor_name">
						<span>*</span> <label>部门职位</label>
					</div>
					<div class="readyadd f_left">
						<c:choose>
							<c:when test="${not empty orgPositList }">
								<c:forEach items="${orgPositList }" var="orgPosit"
									varStatus="status">
									<div class="career-one">
										<select class="career_left" name="orgId"
											onchange="initPosit(this);" id="firstOrg">
											<option value="0">请选择</option>
											<c:forEach items="${orgPosit.orgList }" var="org">
												<option value="${org.orgId }"
													<c:if test="${orgPosit.orgId != null and orgPosit.orgId == org.orgId}">selected="selected"</c:if>>
													${org.orgName }</option>
											</c:forEach>
										</select> <select class="career_right" name="positionId">
											<option selected="selected" value="0">请选择</option>
											<c:forEach items="${orgPosit.positList }" var="posit">
												<option value="${posit.positionId }"
													<c:if test="${orgPosit.positId != null and orgPosit.positId == posit.positionId}">selected="selected"</c:if>>${posit.positionName }</option>
											</c:forEach>
										</select>
										<c:choose>
											<c:when test="${status.count == orgPositList.size()}">
												<div class="f_left bt-bg-style bt-middle bg-light-blue"
													onclick="addOrgPosit(this);">添加</div>
											</c:when>
											<c:otherwise>
												<div class="f_left bt-bg-style bt-middle bg-light-red"
													onclick="delOrgPosit(this);">删除</div>
											</c:otherwise>
										</c:choose>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="career-one">
									<select class="career_left" name="orgId"
										onchange="initPosit(this);" id="firstOrg">
										<option value="0">请选择</option>
										<c:forEach items="${orgList }" var="org">
											<option value="${org.orgId }"
												<c:if test="${user.orgId != null and user.orgId == org.orgId}">selected="selected"</c:if>>
												${org.orgName }</option>
										</c:forEach>
									</select> <select class="career_right" name="positionId">
										<option selected="selected" value="0">请选择</option>
										<c:if test="${positList != null }">
											<c:forEach items="${positList }" var="posit">
												<option value="${posit.positionId }"
													<c:if test="${user.positionId != null and user.positionId == posit.positionId}">selected="selected"</c:if>>${posit.positionName }</option>
											</c:forEach>
										</c:if>
									</select>
									<div class="f_left bt-bg-style bt-middle bg-light-blue"
										onclick="addOrgPosit(this);">添加</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>直接上级</label>
					</div>
					<div class="f_left">
						<select  name="parentId">
							<option selected="selected" value="0">请选择</option>
							<c:if test="${not empty userList }">
								<c:forEach items="${userList }" var="p_user">
									<c:choose>
										<c:when
											test="${not empty user.userId && user.userId == p_user.userId }">
										</c:when>
										<c:otherwise>
											<option value="${p_user.userId }"
												<c:if test="${user.parentId == p_user.userId }">selected="selected"</c:if>>${p_user.username }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>用户名</label>
					</div>
					<div class="f_left">
						<input type="text" name="username" id="username"
							value="<c:if test="${not empty user}">${user.username}</c:if>" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>密码</label>
					</div>
					<div class="f_left">
						<input type="password" name="password" id="password" value="" readonly onfocus="this.removeAttribute('readonly');"
							maxlength="16" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>重复密码</label>
					</div>
					<div class="f_left">
						<input type="password" name="repassword" id="repassword" value="" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>邮箱</label>
					</div>
					<div class="f_left">
						<input type="text" name="email" id="email"
							value="${user.userDetail.email}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>手机</label>
					</div>
					<div class="f_left">
						<input type="text" name="mobile" id="mobile"
							value="${user.userDetail.mobile}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>电话</label>
					</div>
					<div class="f_left">
						<input type="text" name="telephone" id="telephone"
							value="${user.userDetail.telephone}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>工号</label>
					</div>
					<div class="f_left">
						<input type="text" name="number" id="number"
							value="${user.number }" oninput="checkNumber()"/>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>分机号</label>
					</div>
					<div class="f_left">
						<input type="text" name="ccNumber" id="ccNumber"
							value="${user.userDetail.ccNumber}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>传真</label>
					</div>
					<div class="f_left">
						<input type="text" name="fax" id="fax"
							value="${user.userDetail.fax}" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>生日</label>
					</div>
					<div class="f_left">
						<input class="Wdate" type="text"
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="birthday"
							id="birthday"
							value='<fmt:formatDate pattern="yyyy-MM-dd" value="${user.userDetail.birthday}"/>'>
					</div>
					<div class="clear"></div>
				</li>
				<li class="mb0">
					<div class="infor_name">
						<label>地区</label>
					</div> <select class="domain_right" name="province">
						<option value="0">请选择</option>
						<c:if test="${not empty provinceList }">
							<c:forEach items="${provinceList }" var="province">
								<option value="${province.regionId }"
									<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
							</c:forEach>
						</c:if>
				</select> <select class="domain_right" name="city">
						<option value="0">请选择</option>
						<c:if test="${not empty cityList }">
							<c:forEach items="${cityList }" var="city">
								<option value="${city.regionId }"
									<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
							</c:forEach>
						</c:if>
				</select> <select class="career_right" name="zone">
						<option value="0">请选择</option>
						<c:if test="${not empty zoneList }">
							<c:forEach items="${zoneList }" var="zone">
								<option value="${zone.regionId }"
									<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
							</c:forEach>
						</c:if>
				</select>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<label>地址</label>
					</div> <textarea class="address" name="address" id="address">${user.userAddress.address }</textarea>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name mt0">
						<span>*</span><label>设置角色</label>
					</div>
					<div class="inputfloat f_left" style="width: 80%;" >
						<div class="form-blanks" id="errorRole">
							<ul>
								<c:if test="${not empty roleList }">
									<c:forEach items="${roleList }" var="role">
										<li>
											<input type="checkbox" name="roleId" value="${role.roleId}"
											<c:if test="${not empty userRoles}">
												<c:forEach items="${userRoles}" var="userRole">
													<c:if test="${userRole.roleId==role.roleId }">checked="checked"</c:if>
												</c:forEach>
											</c:if>
											><label>${role.roleName }</label>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
	<div class="clear"></div>
	<div class="add-tijiao employeesubmit" style="margin-top: 6px;">
		<input type="hidden" name="formToken" value="${formToken}"/>
		<input type="hidden" name="beforeParams" value='${beforeParams}'/>
		<input type="hidden" name="userId" value="${user.userId }"> <input
			type="hidden" name="orgIds" value="${user.orgIds }"> <input
			type="hidden" name="positionIds" value="${user.positionIds }">
		<input type="hidden" name="roleIds" value="${user.roleIds }">
		<input type="hidden" name="systems" value="${user.systems }">
		<button id="submit" type="submit">提交</button>
	</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/user/modify.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>