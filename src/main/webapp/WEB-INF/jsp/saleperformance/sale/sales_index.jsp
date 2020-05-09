<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="首页" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/home_page.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/sale/sales_five_dept.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<!--  %@ include file="../../homepage/sale/sale_engineer_tag.jsp"% -->
	<input type="hidden" id="accessType_id" value="${accessType}" />
	<input type="hidden" id="sortType" value="${sortType}" />
	<input type="hidden" id="companyId" value="${companyId}" />
	<!-- 上级通过连接查询该userId的五行剑法页面-->
	<input type="hidden" id="others_userId_id" value="${five_userId}" />
	
	<div class="five-elements">
      	<span id="five_sale_span_button" tabtitle='{"num":"sales_five_${five_userId}", "link":"./sales/fiveSales/detailsPage.do?sortType=1&userId=${five_userId}&companyId=${companyId}&userFlag=${userFlag}", "title":"五行剑法"}' href="javascript:void(0);" class="bg-light-blue bt-bg-style addtitle">五行剑法<i class="icon iconfiveelements"></i></span>
      	<span tabtitle='{"num":"sales_index_${five_userId}", "link":"./home/page/index.do?accessType=1", "title":"今日任务"}' href="javascript:void(0);" class="bg-light-blue bt-bg-style addtitle" >今日任务<i class="icon icontodaytask"></i></span>
      	<span tabtitle='{"num":"sales_index_${five_userId}", "link":"./home/page/contact.do", "title":"跨部门联系人"}' href="javascript:void(0);" class="bg-light-blue bt-bg-style addtitle" >跨部门联系人<i class="icon iconlinker"></i></span>
    </div>
    <div class="parts">
    	<br/>
    </div>
	<!-- 部门销售 大致数据展示 -->
	<div class="parts" id="five_sale_div_show">
		<!-- <div class="title-container">
			<div class="table-title nobor">部门本月概况</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="">人员</th>
					<th class="">业绩得分</th>
					<th class="">业绩排名</th>
					<th class="">品牌得分</th>
					<th class="">品牌排名</th>
					<th class="">客户得分</th>
					<th class="">客户排名</th>
					<th class="">通话得分</th>
					<th class="">通话排名</th>
					<th class="">转化率得分</th>
					<th class="">转化率排名</th>
					<th class="">总得分</th>
					<th class="">总排名</th>					
					<th class="">昨天总排名</th>					
					<th class="">上月总排名</th>					
				</tr>
			</thead>
			<tbody id="dept_details_tb">
				
			</tbody>
		</table> -->
		
	</div>
	
	
</div>
<%@ include file="../../common/footer.jsp"%>