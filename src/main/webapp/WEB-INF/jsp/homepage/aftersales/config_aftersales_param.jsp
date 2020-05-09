<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="参数设置" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/home/page/config_sale_goal.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/home/page/saveConfigSaleGoal.do">
        <div class="main-container">
        	 <div class="parts">
	            <div class="title-container">
	                <div class="table-title nobor">
	                   	业务负责分配
	                </div>
	                <div class="title-click nobor  pop-new-data" 
	                		layerParams='{"width":"450px","height":"500px","title":"业务负责分配","link":"./editParamsConfigPage.do"}'>编辑</div>
	            </div>
	            <table class="table">
	            	<thead>
	                    <tr>
	                        <th class="wid15">销售部门</th>
	                        <th class="wid15">负责人</th>
	                    </tr>
	                </thead>
	                <tbody>
		                <c:if test="${not empty saledefaultList }">
		                	<c:forEach items="${saledefaultList }" var="pa">
			                	<tr>
			                        <td>${pa.comments}</td>
			                        <td>${pa.paramsValue}</td>
			                    </tr>
			                </c:forEach>
		                </c:if>
			            <c:if test="${empty saledefaultList }">
			            	<tr>
		                        <td colspan="2">暂无数据</td>
		                    </tr>
			            </c:if>   
	                </tbody>
	            </table>
	        </div>
    	</div>
   </form>
</div>
<%@ include file="../../common/footer.jsp"%>