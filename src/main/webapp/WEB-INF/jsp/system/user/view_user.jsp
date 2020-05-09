<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="查看员工" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="mt10 mb10">上次登录时间：<c:choose><c:when test="${user.lastLoginTime > 0 }"><date:date value ="${user.lastLoginTime} "/></c:when><c:otherwise>未登录</c:otherwise></c:choose>，状态：
	<c:choose>
   		<c:when test="${user.isDisabled == 1 }"><span class="offstate">禁用</span></c:when>
   		<c:otherwise><span class="onstate">启用</span></c:otherwise>
   	</c:choose>
   	<c:if test="${not empty user.disabledReason }">(${user.disabledReason })</c:if></div>
	<div class="parts">
		<table class="table">
			<thead>
				 <th class="table-smallest">员工姓名</th>
                    <th class="table-middle">${user.userDetail.realName}</th>
                    <th class="table-smallest">性别</th>
                    <th class="table-middle">
	                    <c:choose>
	                   		<c:when test="${not empty user.userDetail && user.userDetail.sex==0}">女</c:when>
	                   		<c:when test="${not empty user.userDetail && user.userDetail.sex==1}">男</c:when>
	                   		<c:otherwise>保密</c:otherwise>
	                   	</c:choose>
                    </th>
			</thead>
            <tbody>
                <tr>
                    <td class="table-smallest">工号</td>
                    <td class="table-middle">${user.number }</td>
                    <td class="table-smallest">创建时间</td>
                    <td class="table-middle"><date:date value ="${user.addTime} "/></td>
                </tr>
                <c:if test="${curr_user.isAdmin == 2}">
                <tr>
                    <td>分公司</td>
                    <td colspan="3" class="text-left">${user.companyName}</td>
                </tr>
                <tr>
                    <td>管理员</td>
                    <td colspan="3" class="text-left">
                    <c:choose>
						<c:when test="${user.isAdmin == 2 }">超级管理员</c:when>
						<c:when test="${user.isAdmin == 1 }">管理员</c:when>
						<c:otherwise>用户</c:otherwise>
					</c:choose>
                    </td>
                </tr>
                </c:if>
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
                    <td>可登陆系统</td>
                    <td colspan="3" class="text-left">
                        ${showSystems}
                    </td>
                </tr>
                <tr>
                    <td>是否贝登员工</td>
                    <td colspan="3" class="text-left">
                        <c:if test="${user.staff == 1}">是</c:if>
                        <c:if test="${user.staff == 0}">否</c:if>
                    </td>
                </tr>
                <tr>
                    <td>所属公司</td>
                    <td colspan="3" class="text-left">
                        ${belongCompany}
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
</div>
<%@ include file="../../common/footer.jsp"%>