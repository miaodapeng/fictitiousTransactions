<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="角色管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
        	<form action="${pageContext.request.contextPath}/system/role/index.do" method="post" id="search">
	            <ul>
	                <li>
	            		<label class="infor_name">角色名称</label>
	            		<input type="text" class="input-middle" name="roleName" id="roleName" value="${role.roleName}"/>
            		</li>
					<li>
						<label class="infor_name">应用系统</label>
						<select name="platformId" >
							<option value="">全部</option>
							<c:if test="${not empty platformList }">
								<c:forEach items="${platformList}" var="platform">
									<option value="${platform.platformId}"
											<c:if test="${choosedPlatform == platform.platformId}">selected="selected"</c:if>
									>${platform.platformName}</option>
								</c:forEach>
							</c:if>
						</select>
					</li>
           		</ul>
           		<div class="tcenter">
	            	<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
	            	<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
	            	<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"200px","title":"新增角色","link":"./addRole.do"}'>新增角色</span>
            	</div>
            </form>
        </div>
        <div  class="normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="sorts">序号</th>
                        <th>角色名称</th>
                        <th>应用系统</th>
                        <th style="width: 15%;">操作</th>
                    </tr>
                </thead>
                <tbody class="company">
        		<c:if test="${not empty roleList}">
                	<c:forEach items="${roleList }" var="role" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${role.roleName}</td>
							<td>
								<c:forEach items="${showRolePlatform}" var="chosedPlat" varStatus="status">
									<c:if test="${chosedPlat.roleId == role.roleId}">${chosedPlat.platformName}</c:if>
								</c:forEach>
							</td>
	                        <td>
								<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"200px","title":"编辑角色","link":"./editRole.do?roleId=${role.roleId}"}'>
									编辑
								</span>
								<span class="delete" onclick="delRole(${role.roleId});">删除</span>
								<span class="edit-user addtitle" tabTitle='{"num":"intoMenu${role.roleId}","link":"./system/role/intoMenu.do?roleId=${role.roleId}","title":"设置权限"}'>
									设置权限
								</span>
								<%-- <span class="edit-user addtitle" tabTitle='{"num":"user","link":"./system/role/editmenu.do?roleId=${role.roleId}","title":"添加功能"}'>
									编辑功能
								</span> --%>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty roleList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
	        <tags:page page="${page}"/>
        </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>static/js/role/index.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>
