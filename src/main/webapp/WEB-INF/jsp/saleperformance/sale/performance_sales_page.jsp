<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="五行详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/home_page.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/sale/five_sales.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<%@ include file="../../homepage/sale/five_sale_engineer_tag.jsp"%>
	<input type="hidden" id="accessType_id" value="${accessType}" />
	<input type="hidden" id="companyId" value="${companyId}" />
	<input type="hidden" id="sortType" value="${sortType}" />
	<!-- 上级通过连接查询该userId的五行剑法页面-->
	<input type="hidden" id="others_userId_id" value="${five_userId}" />
	<!-- 默认展示近1年的历史数据 -->
	<input type="hidden" id="historyMonthNum_id" value="12" />
	<div class="">
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
							<td>业绩到款额<span id="">( 万元 )</span></td>
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
							<td>业绩完成度(%)</td>
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
				1. 计入本月业绩额的订单须符合以下要求：订单实际到款95%以上；本月有到款记录；订单未计算过业绩额<br/>
				2. 业绩额是根据订单实际金额和毛利率计算而出<br/>
				3. 业绩完成度=业绩额/本月目标<br/>
				4. 团队指整个平台业务部或科研业务部中参与五行剑法计算的人员<br/>
				5. 得分=个人业绩完成率/团队平均业绩完成率*加权值（加权值=该项指标的考核比例*100）<br/>
				6. 页面中所有数据在计算过程中由于四舍五入等原因在展示时会与实际数据有细微差别，所有数据均以运营部提供的最终数据为准<br/>
			</div>
			<div class="clear parts">
				<div class="title-container">
					<div class="table-title nobor">本月业绩明细</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th class="wid10">订单号</th>
							<th class="wid10">客户名称</th>
							<th class="wid10">生效时间</th>
							<th class="wid10">订单金额<span id="">( 元 )</span></th>
							<th class="wid10">到款金额<span id="">( 元 )</span></th>
						</tr>
					</thead>
					<tbody id="perf_details_tb">
						
					</tbody>
				</table>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>  
				1. 以上订单为当月已计入业绩额的订单明细<br/>                
				2. 订单金额为订单原始金额，即不排除退货退款<br/>                
				3. 到款金额为实际到款金额，不包含账期<br/>                
         	</div>
         	<div class="parts">
				<div >
					<div class="title-container">
						<div class="table-title nobor">已计入业绩&未到全款</div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th class="wid10">订单号</th>
								<th class="wid10">客户名称</th>
								<th class="wid10">生效时间</th>
								<th class="wid10">订单金额<span id="">( 元 )</span></th>
								<th class="wid10">到款金额<span id="">( 元 )</span></th>
							</tr>
						</thead>
						<tbody id="perf_yes_1">
							
						</tbody>
					</table>
				</div>
				<div id="perf_yes_1_page">
				</div>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>  
				1. 以上订单为已全部发货但订单实际到款率小于100%的全部订单明细<br/>                
				2. 订单金额为订单实际金额，即去除退货金额<br/>                
				3. 到款金额为实际到款金额，不包含账期<br/>                
         	</div>
         	
         	
         	
         	
         	
         	
         	<div class="parts">
				<div >
					<div class="title-container">
						<div class="table-title nobor">可发货&未计入业绩</div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th class="wid10">订单号</th>
								<th class="wid10">客户名称</th>
								<th class="wid10">生效时间</th>
								<th class="wid10">订单金额<span id="">( 元 )</span></th>
								<th class="wid10">到款金额<span id="">( 元 )</span></th>
							</tr>
						</thead>
						<tbody id="perf_no_undeliver_1">
							
						</tbody>
					</table>
				</div>
				<div id="perf_no_undeliver_1_page">
				
				</div>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>  
				1. 以上订单为可发货未计入业绩全部订单明细<br/>                
				2. 订单金额为订单实际金额，即去除退货金额<br/>                
				3. 到款金额为实际到款金额，不包含账期<br/>                
         	</div> 
         	
         	
         	
			<!-- <div class="parts">
				<div >
					<div class="title-container">
						<div class="table-title nobor">未计入业绩&已发货&未到全款</div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th class="wid10">订单号</th>
								<th class="wid10">客户名称</th>
								<th class="wid10">生效时间</th>
								<th class="wid10">订单金额<span id="">( 元 )</span></th>
								<th class="wid10">到款金额<span id="">( 元 )</span></th>
							</tr>
						</thead>
						<tbody id="perf_no_1">
							
						</tbody>
					</table>
				</div>
				<div id="perf_no_1_page">
				</div>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>  
				1. 以上订单为已全部发货或部分发货，但订单实际到款率小于100%的全部订单明细<br/>                
				2. 订单金额为订单实际金额，即去除退货金额<br/>                
				3. 到款金额为实际到款金额，不包含账期<br/>                
         	</div> -->
         	
         	
			<!-- <div class="parts">
				<div >
					<div class="title-container">
						<div class="table-title nobor">已生效&未付款</div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th class="wid10">订单号</th>
								<th class="wid10">客户名称</th>
								<th class="wid10">生效时间</th>
								<th class="wid10">订单金额<span id="">( 元 )</span></th>
								<th class="wid10">到款金额<span id="">( 元 )</span></th>
							</tr>
						</thead>
						<tbody id="perf_no_2">
							
						</tbody>
					</table>
				</div>
				<div id="perf_no_2_page">
				</div>
			</div>
			<div class="table-friend-tip mb15" style="margin-top:0;">
				说明：<br/>  
				1. 以上订单为15天内生效但未付款的全部订单明细<br/>                
				2. 订单金额为订单实际金额，即去除退货金额<br/>                
				3. 到款金额为实际到款金额，不包含账期<br/>                
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
							<th class="wid10">本月业绩额<span id="">( 万元 )</span></th>
							<th class="wid10">本月完成度</th>
							<th class="wid10">部门平均完成度</th>
							<th class="wid10">得分</th>
							<th class="wid10">排名</th>
						</tr>
					</thead>
					<tbody id="perf_histroy_tb">
						
					</tbody>
				</table>
			</div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>