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
				<div class="table-title nobor">近90天概况</div>
			</div>
			<table class="table">
				<tbody>
					<tr>
						<td class="wid10 table-smaller">项目</td>
						<td class="wid10" id="now_sales_name_td">${userName}</td>
						<td class="table-smaller">团队</td>
						<td class="wid10">榜首( <span id="first_one_span_name">-</span> )</td>
						<td class="wid10">昨天</td>
						<td class="wid10">上月</td>
					</tr>
					<tr>
						<td>已合作客户数量</td>
						<td id="customerCopNum_1"></td>
						<td id="customerCopNum_2"></td>
						<td id="customerCopNum_3"></td>
						<td id="customerCopNum_4"></td>
						<td id="customerCopNum_5"></td>
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
			1. 已合作客户：被计入业绩额的订单所对应的客户<br/>
			2. 团队指整个二级部门中参与五行剑法计算的人员<br/>
			3. 得分=个人合作数量/部门平均合作数量*加权值（加权值=该项指标的考核比例*100）<br/>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">近90天合作客户详情</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">已合作公司</th>
						<th class="wid10">有效订单数</th>
						<th class="wid10">最近一次下单时间</th>
						<th class="wid10">最近一次沟通时间</th>
						<th class="wid10">距今天数</th>
						<th class="wid10">有效成交总额<span id="">( 万元 )</span></th>
						<th class="wid10">是否老客户</th>
					</tr>
				</thead>
				<tbody id="detail_customer_tb_90">
					
				</tbody>
			</table>
		</div>
		<div class="table-friend-tip mb15" style="margin-top:0;">
			说明：<br/>
			1、已合作的客户的定义为：近90天内，成交过有效订单的客户<br/>
			2、有效订单的定义：不包含账期的对公交易额>0或对私交易额>200 的订单<br/>
	    </div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">近180天流失客户详情</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">已合作公司</th>
						<th class="wid10">有效订单数</th>
						<th class="wid10">最近一次下单时间</th>
						<th class="wid10">最近一次沟通时间</th>
						<th class="wid10">距今天数</th>
						<th class="wid10">有效成交总额<span id="">( 万元 )</span></th>
						<th class="wid10">是否老客户</th>
					</tr>
				</thead>
				<tbody id="detail_customer_tb_150">
					
				</tbody>
			</table>
		</div>
		
		
		<!-- <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">过往数据</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th class="wid10">月份</th>
						<th class="wid10">已合作客户数量</th>
						<th class="wid10">部门平均合作客户数量</th>
						<th class="wid10">得分</th>
						<th class="wid10">排名</th>
					</tr>
				</thead>
				<tbody id="customer_history_tb">
					
				</tbody>
			</table>
		</div> -->
		
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>