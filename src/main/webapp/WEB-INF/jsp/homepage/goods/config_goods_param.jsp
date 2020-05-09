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
	                   	报价咨询默认负责人
	                </div>
	                <div class="title-click nobor  pop-new-data" 
	                		layerParams='{"width":"600px","height":"250px","title":"业务负责分配","link":"./editParamsConfigPage.do"}'>编辑</div>
	            </div>
	            <table class="table">
	                <tbody>
	                	<tr>
	                        <td>报价咨询默认负责人</td>
	                        <td>${user.username }</td>
	                        <td></td>
	                        <td></td>
	                    </tr>
	                </tbody>
	            </table>
	        </div>
    	</div>
   </form>
</div>
<%@ include file="../../common/footer.jsp"%>