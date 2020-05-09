<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="五行详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/home_page.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/sale/five_sales.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<%@ include file="../../homepage/sale/five_sale_engineer_tag.jsp"%>
	<input type="hidden" id="sortType" value="${sortType}" />
	
	<input type="hidden" id="companyId" value="${companyId}" />
	<!-- 上级通过连接查询该userId的五行剑法页面-->
	<input type="hidden" id="others_userId_id" value="${five_userId}" />
	<!-- 默认展示近1年的历史数据 -->
	<input type="hidden" id="historyMonthNum_id" value="12" />
	<div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">本月概况</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10 table-smaller">项目</th>
						<th class="wid10" id="now_sales_name_td">${userName}</th>
						<th class="wid10 table-smaller">团队</th>
						<th class="wid10">榜首( <span id="first_one_span_name">-</span> )</th>
						<th class="wid10">昨天</th>
						<th class="wid10">上月</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>通话时长</td>
						<td id="callTime_1"></td>
						<td id="callTime_2"></td>
						<td id="callTime_3"></td>
						<td id="callTime_4"></td>
						<td id="callTime_5"></td>
					</tr>
					<tr>
						<td>得分</td>
						<td id="score_1"></td>
						<td id="score_2"></td>
						<td id="score_3"></td>
						<td id="score_4"></td>
						<td id="score_5"></td>
					</tr>
					<tr>
						<td>当前排名</td>
						<td id="sort_1"></td>
						<td>-</td>
						<td id="sort_3"></td>
						<td id="sort_4"></td>
						<td id="sort_5"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="table-friend-tip mb15" style="margin-top:0;">
			说明：<br/>
			1. 得分=个人通话时长/部门平均通话时长*加权值（加权值=该项指标的考核比例*100）<br/>
			2. 通话时长为callcenter登录情况下的呼入时长+呼出时长<br/>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">详细数据</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">-</th>
						<th class="wid10">数量</th>
						<th class="wid10">呼入次数</th>
						<th class="wid10">呼入时长</th>
						<th class="wid10">呼入平均时长</th>
						<th class="wid10">呼出次数</th>
						<th class="wid10">呼出时长</th>
						<th class="wid10">呼出平均时长</th>
					</tr>
				</thead>
				<tbody id="call_details_tb">
					
				</tbody>
			</table>
		</div>
		<div class="table-friend-tip mb15" style="margin-top:0;">
			说明：<br/>
			得分=个人通话时长/部门平均通话时长*加权值10<br/>
    	</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">过往数据</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">月份</th>
						<th class="wid10">通话时长</th>
						<th class="wid10">部门平均通话时长</th>
						<th class="wid10">部门平均合作数量</th>
						<th class="wid10">得分</th>
						<th class="wid10">排名</th>
					</tr>
				</thead>
				<tbody id="call_history_tb">
					
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>