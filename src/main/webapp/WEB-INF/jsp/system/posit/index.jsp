<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="职位管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
        	<form action="${pageContext.request.contextPath}/system/posit/index.do" method="post" id="search">
        		<ul>
	                <li>
	            		<label class="infor_name">职位名称</label>
	            		<input type="text" class="input-middle" name="positionName" id="positionName" value="${position.positionName }"/>
            		</li>
            		<li>
			            <label class="infor_name">所属部门</label>
						<select class="input-middle f_left" name="orgId" onchange="initPosit();">
							<option value="0">请选择</option>
							<c:forEach items="${orgList }" var="org">
								<option value="${org.orgId }"  <c:if test="${position.orgId != null and position.orgId == org.orgId}">selected="selected"</c:if>>
								${org.orgName }
								</option>
							</c:forEach>
						</select>
            		</li>
           		</ul>
           		<div class="tcenter">
	            	<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
	            	<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
	            	<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"550px","height":"250px","title":"新增职位","link":"./addposition.do"}'>新增职位</span>
            	</div>
            </form>
        </div>
        <div  class="normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="sorts">序号</th>
                        <th class="wid15">职位名称</th>
                        <th class="wid10">职位类型</th>
                        <th class="wid10">职位级别</th>
                        <th>所属部门</th>
                        <th style="width: 15%;">操作</th>
                    </tr>
                </thead>
                <tbody class="company">
        		<c:if test="${not empty positionList}">
                	<c:forEach items="${positionList }" var="position" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>
		                        ${position.positionName}
	                        </td>
	                         <td>
		                        ${position.typeName}
	                        </td>
	                         <td>
		                        ${position.levelName}
	                        </td>
	                        <td>
	                        	<c:forEach items="${orgList }" var="org">
		                        	<c:if test="${org.orgId eq position.orgId}">
			                        	${org.orgNames}<%-- ${position.orgName} --%>
		                        	</c:if>
		                        </c:forEach>
	                       </td>
	                        <td>
								<span class="edit-user pop-new-data" layerParams='{"width":"550px","height":"250px","title":"编辑职位","link":"./editposition.do?positionId=${position.positionId}"}'>
									编辑
								</span>
								<span class="delete" onclick="delPosit(${position.positionId});">删除</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty positionList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
	        <tags:page page="${page}"/>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/posit/index.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>