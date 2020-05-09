<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="五行总览" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/sale/five_total_view_page.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	
	<!-- 部门销售 大致数据展示 -->
	<div class="parts" id="five_sale_div_show">
		<div class="title-container">
			<div class="table-title nobor">五行本月概况</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid4">序号</th>
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
			<tbody id="five_total_page_tb">
				
			</tbody>
		</table>
	</div>
	
	
</div>
<%@ include file="../../common/footer.jsp"%>