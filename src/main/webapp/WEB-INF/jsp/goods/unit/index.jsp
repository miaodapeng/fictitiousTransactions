<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品单位" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/unit/index.js?rnd=<%=Math.random()%>'></script>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/goods/unit/index.do" method="post" id="search">
				<ul>
	                <li>
	                    <label class="infor_name">单位名称</label>
	                    <input type="text" class="input-middle" name="unitName" id="" value="${unit.unitName}">
	                </li>
            	</ul>
            	<div class="tcenter">
	                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	                <span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
            		<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"250px","title":"新增单位","link":"./add.do"}'>新增单位</span>
            	</div>
			</form>
		</div>
		<div class='normal-list-page'>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="sorts">序号</th>
						<th>单位名称</th>
						<th>英文名称</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
			
				<tbody class="unit">
				<c:if test="${not empty unitList}">
                	<c:forEach items="${unitList }" var="list" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${list.unitName}</td>
	                        <td>${list.unitNameEn}</td>
	                        <td><date:date value ="${list.addTime}"/></td>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"250px","title":"编辑单位","link":"./edit.do?unitId=${list.unitId}"}'>
									编辑
								</span>
								<span class="delete" onclick="delUnit(${list.unitId});">删除</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
				</tbody>
			</table>
			<c:if test="${empty unitList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
        	<tags:page page="${page}"/>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>