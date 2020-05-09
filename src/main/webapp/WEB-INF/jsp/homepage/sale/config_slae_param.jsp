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
	                   	参数设置
	                </div>
	                <div class="title-click nobor  pop-new-data" layerParams='{"width":"600px","height":"250px","title":"编辑信息",
			                  "link":"./editParamsConfigPage.do"}'>编辑</div>
	            	
	            </div>
	            <table class="table">
	                <tbody>
	                	<tr>
	                        <td>报价自动关闭时限</td>
	                        <td>${quote.paramsValue }天</td>
	                        <td>订单自动关闭时限</td>
	                        <td>${sale.paramsValue }天</td>
	                    </tr>
	                    <tr>
	                        <td>报价单有效期</td>
	                        <td>${quoteorder.paramsValue }天</td>
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