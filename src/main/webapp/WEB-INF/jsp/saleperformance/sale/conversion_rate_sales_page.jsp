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
	<div >
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">本月概况</div>
			</div>
			<table class="table">
				<tbody>
					<tr>
						<td class="wid10 table-smaller">项目</td>
						<td class="wid10" id="now_sales_name_td">${userName}</td>
						<td class="table-smaller">团队</td>
						<td class="wid10">榜首( <span id="first_one_span_name">-</span> )</td>
						<td class="wid10" >昨天</td>
						<td class="wid10">上月</td>
					</tr>
					<tr>
						<td>新成交询价数量</td>newDealToPriceNum_
						<td id="newDealToPriceNum_1"></td>
						<td id="newDealToPriceNum_2"></td>
						<td id="newDealToPriceNum_3"></td>
						<td id="newDealToPriceNum_4"></td>
						<td id="newDealToPriceNum_5"></td>
					</tr>
					<tr>
						<td>本月分配总机询价数量</td>
						<td id="monthToPriceNum_1"></td>
						<td id="monthToPriceNum_2"></td>
						<td id="monthToPriceNum_3"></td>
						<td id="monthToPriceNum_4"></td>
						<td id="monthToPriceNum_5"></td>
					</tr>
					<tr>
						<td>转化率(%)</td>
						<td id="rate_1"></td>
						<td id="rate_2"></td>
						<td id="rate_3"></td>
						<td id="rate_4"></td>
						<td id="rate_5"></td>
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
						<td id="">-</td>
						<td id="sort_3"></td>
						<td id="sort_4"></td>
						<td id="sort_5"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="table-friend-tip mb15" style="margin-top:0;">
			说明：<br/>
			1. 得分=个人转化率/部门平均转化率*加权值10<br/>
			2. 以上询价必须来源于市场部分配<br/>
			3. 排名均指在整个二级部门中的排名<br/>
    	</div>
    	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">本月新成交询价明细</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">序号</th>
						<th class="wid10">商机单号</th>
						<th class="wid10">订单单号</th>
						<th class="wid10">分配时间</th>
					</tr>
				</thead>
				<tbody id="detail_rate_tb">
					
				</tbody>
			</table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">过往数据</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">月份</th>
						<th class="wid10">新成交询价数量</th>
						<th class="wid10">本月分配总机询价数量</th>
						<th class="wid10">转化率</th>
						<th class="wid10">部门平均转化率</th>
						<th class="wid10">得分</th>
						<th class="wid10">排名</th>
					</tr>
				</thead>
				<tbody id="rate_histroy_tb">
					
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>