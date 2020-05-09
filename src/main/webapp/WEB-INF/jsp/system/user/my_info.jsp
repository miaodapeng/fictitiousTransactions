<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="个人设置" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="mt10 mb10">上次登录时间：<date:date value ="${user.lastLoginTime} "/>，如非本人登录，请<a  class="pop-new-data" layerParams='{"width":"500px","height":"230px","title":"修改密码","link":"${pageContext.request.contextPath}/system/user/modifypassword.do?userId=${user.userId}"}'>修改密码</a>。</div>
	<div>
		<table class="table table-bordered table-striped table-condensed table-centered">
            <tbody>
                <tr>
                    <td class="table-smallest">员工姓名</td>
                    <td class="table-middle">${user.userDetail.realName}</td>
                    <td class="table-smallest">性别</td>
                    <td class="table-middle">
                    <c:choose>
                   		<c:when test="${not empty user.userDetail && user.userDetail.sex==0}">女</c:when>
                   		<c:when test="${not empty user.userDetail && user.userDetail.sex==1}">男</c:when>
                   		<c:otherwise>保密</c:otherwise>
                   	</c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">工号</td>
                    <td class="table-middle">${user.number }</td>
                    <td class="table-smallest">创建时间</td>
                    <td class="table-middle"><date:date value ="${user.addTime} "/></td>
                </tr>
                <tr>
                    <td>部门职位</td>
                    <td colspan="3" class="text-left">${user.orgName}&nbsp;&nbsp;${user.positionName}</td>
                </tr>
                <tr>
                    <td>角色</td>
                    <td colspan="3" class="text-left">
                    	<c:if test="${not empty userRoles}">
                    		<c:forEach items="${userRoles }" var="role">
                    			${role.roleName }&nbsp;&nbsp;
                    		</c:forEach>
                    	</c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">用户名</td>
                    <td class="table-middle">${user.username}</td>
                    <td class="table-smallest">直接上级</td>
                    <td class="table-middle">${user.pUsername }</td>
                </tr>
                <tr>
                    <td class="table-smallest">手机</td>
                    <td class="table-middle">${user.userDetail.mobile}</td>
                    <td class="table-smallest">电话</td>
                    <td class="table-middle">${user.userDetail.telephone}</td>
                </tr>
                <tr>
                    <td class="table-smallest">分机号</td>
                    <td class="table-middle">${user.userDetail.ccNumber}</td>
                    <td class="table-smallest">邮箱</td>
                    <td class="table-middle">${user.userDetail.email}</td>
                </tr>
                <tr>
                    <td class="table-smallest">传真</td>
                    <td class="table-middle">${user.userDetail.fax}</td>
                    <td class="table-smallest">生日</td>
                    <td class="table-middle"><fmt:formatDate value="${user.userDetail.birthday}" pattern="yyyy-MM-dd" /></td>
                </tr>
                <tr>
                    <td>地区</td>
                    <td colspan="3" class="text-left"><c:if test="${not empty region }">${region }</c:if></td>
                </tr>
                <tr>
                    <td>地址</td>
                    <td colspan="3" class="text-left">${user.userAddress.address }</td>
                </tr>
			</tbody>
		</table>
	</div>
	<div class="pb50 font-grey9">
		友情提醒： <br />
		1、如果您的信息不正确，请联系管理员修改。
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
