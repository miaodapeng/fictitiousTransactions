<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售工程师个人首页" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/sale/sales_per_data.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
       <div class="mb7">销售工程师: <span>${userName}</span></div>
	   <!--   %@ include file="../../homepage/sale/sale_engineer_tag.jsp"% -->
       <input type="hidden" id="sortType" value="${sortType}" />
	   <input type="hidden" id="companyId" value="${companyId}" />
	  
	   <!-- 上级通过连接查询该userId的五行剑法页面-->
	   <input type="hidden" id="others_userId_id" value="${five_userId}" />
       <div class="showing-card">
           <ul>
               <li>
                   <div class="card-container personal-data">
                       <div class="card-title green-title">
                         	 五行剑法总成绩
                       </div>
                       <div class="card-content green-content">
                           <ul>
                         		<li>
                                   <span>总得分</span>
                                   <span id="total_score_five" class="num"></span>
                               </li>
                         		<li>
                                   <span>总排名</span>
                                   <span id="total_sort_five"  class="num"></span>
                               </li>
                           </ul>
                       </div>
                   </div>
               </li>
           </ul>
       </div>
   </div>
<%@ include file="../../common/footer.jsp"%>