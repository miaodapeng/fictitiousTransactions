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
					<tbody>
						<tr>
							<td class="wid10 table-smaller">项目</td>
							<td class="wid10" id="now_sales_name_td">${userName}</td>
							<td class="wid10 table-smaller">团队</td>
							<td class="wid10">榜首( <span id="first_one_span_name">-</span> )</td>
							<td class="wid10">昨天</td>
							<td class="wid10">上月</td>
						</tr>
						<tr>
							<td>核心商品销售额<span id="">( 万元 )</span></td>
							<td id="performanceAmount_td_1"></td>
							<td id="performanceAmount_td_2"></td>
							<td id="performanceAmount_td_3"></td>
							<td id="performanceAmount_td_4"></td>
							<td id="performanceAmount_td_5"></td>
						</tr>
						<tr>
							<td>本月目标<span>( 万元 )</span></td>
							<td id="monthGoalAmount_td_1"></td>
							<td id="monthGoalAmount_td_2"></td>
							<td id="monthGoalAmount_td_3"></td>
							<td id="monthGoalAmount_td_4"></td>
							<td id="monthGoalAmount_td_5"></td>
						</tr>
						<tr>
							<td>核心商品完成度(%)</td>
							<td id="scheduleRate_td_1"></td>
							<td id="scheduleRate_td_2"></td>
							<td id="scheduleRate_td_3"></td>
							<td id="scheduleRate_td_4"></td>
							<td id="scheduleRate_td_5"></td>
						</tr>
						<tr>
							<td>得分</td>
							<td id="score_td_1"></td>
							<td id="score_td_2"></td>
							<td id="score_td_3"></td>
							<td id="score_td_4"></td>
							<td id="score_td_5"></td>
						</tr>
						<tr>
							<td>当前排名</td>
							<td id="sort_td_1"></td>
							<td id="">-</td>
							<td id="sort_td_3"></td>
							<td id="sort_td_4"></td>
							<td id="sort_td_5"></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			
			
			
			
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>
				1. 核心商品业绩额：被计入业绩额的订单中所包含的核心品牌商品的业绩额<br/>
				2. 核心品牌：ERP中定义的核心品牌且不为迈瑞<br/>
				3. 团队指整个整个二级部门中参与五行剑法计算的人员<br/>
				4. 得分=个人核心商品完成度/部门平均核心商品完成度*加权值（加权值=该项指标的考核比例*100）<br/>
         	</div>
         	
         	
         	
         	
         	
         	
			<!-- <div class="parts">
				<div class="title-container">
					<div class="table-title nobor">部门概况（近90天）</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th class="wid10">指定品牌</th>
							<th class="wid10">个人已合作金额<span id="">( 万元 )</span></th>
							<th class="wid10">已完成合作人数（ 总人数 <span id="brans_org_config_total_num"></span> ）</th>
							<th class="wid10">榜首</th>
							<th class="wid10">榜首金额<span id="">( 万元 )</span></th>
						</tr>
					</thead>
					<tbody id="brands_details_tb">
						
					</tbody>
				</table>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>
				1、表格中为本月所有指定的合作品牌<br/>
				2、绿色标注的为当前你已经完成合作的品牌<br/>
				3、品牌后的数量为整个部门当前已完成该品牌合作的同事数量<br/>
         	</div> -->
			<div class="parts">
				<div class="title-container">
					<div class="table-title nobor">过往数据</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th class="wid10">月份</th>
							<th class="wid10">本月目标<span id="">( 万元 )</span></th>
							<th class="wid10">核心商品销售额<span id="">( 万元 )</span></th>
							<th class="wid10">本月完成度</th>
							<th class="wid10">部门平均完成度</th>
							<th class="wid10">得分</th>
							<th class="wid10">排名</th>
						</tr>
					</thead>
					<tbody id="brands_histroy_tb">
					
						
					</tbody>
				</table>
			</div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>