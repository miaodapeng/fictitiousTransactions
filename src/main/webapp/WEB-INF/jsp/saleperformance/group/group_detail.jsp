<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="团队详情页" scope="application" />	
<%@ include file="../../common/common.jsp"%>


	
	<div class="main-container" style='padding-top:15px;'>
			<div class="parts">
			
			
			<c:if test="${empty resultList}">
				<div class="title-container">
	                <div class="table-title nobor">
	                   	部门本月五行剑法概况
	                </div>
	            </div>
	            <table class="table">
	                <thead>
	                  <tr>
                        <th style="width:60px;">人员</th>
                        <th>本月目标（万）</th>
                        <th>本月业绩额（万）</th>
						<th>本月完成度 （%）</th>
						<th>业绩排名</th>
						<th style="width: 146px;">小组核心商品完成度均值（%）</th>
						<th>核心商品排名</th>
						<th style="width: 146px;">小组合作客户数均值（近90天）</th>
						<th>客户排名</th>
						<th style="width: 84px;">小组通话时长均值</th>
						<th>话务排名</th>
						<th style="width: 81px;">本月新客数</th>
						<th>BD新客数排名</th>
						<th>小组综合得分均值</th>
						<th>综合排名</th>
	                  </tr>
	                </thead> 
	            	<tr><td colspan="15">查询无结果</td></tr>
	            </table>
            </c:if>
            
            
            
			<c:if test="${!empty resultList}">
            	<c:forEach var="vo" items="${resultList}" >
		            <div class="title-container">
		                <div class="table-title nobor">
		                   	${vo.groupName }
		                </div>
		            </div>
		            <table class="table">
		            	<input type="hidden" class="groupIdClass" value="${vo.groupId }"/>
		                <thead>
		                  <tr>
		                        <th style="width:60px;">人员</th>
		                        <th>本月目标（万）</th>
		                        <th>本月业绩额（万）</th>
								<th>本月完成度 （%）</th>
								<th>业绩排名</th>
								<th style="width: 146px;">小组核心商品完成度均值（%）</th>
								<th>核心商品排名</th>
								<th style="width: 146px;">小组合作客户数均值（近90天）</th>
								<th>客户排名</th>
								<th style="width: 84px;">小组通话时长均值</th>
								<th>话务排名</th>
								<th style="width: 81px;">本月BD新客数</th>
								<th>BD新客数排名</th>
								<th>小组综合得分均值</th>
								<th>综合排名</th>
		                    </tr>
		                </thead> 
	                	<c:if test="${empty vo.deptList }">
	                		<tr><td colspan="15">查询无结果</td></tr>
	                	</c:if>
	                	<c:if test="${!empty vo.deptList }">
		                	<tbody>
		                		<c:forEach var="dept" items="${vo.deptList}">
		                			<tr>
				                		<td class="font-blue">
				                		<a class="addtitle" href="javascript:void(0);" 
	                		tabtitle='{"num":"sales_five_pingtai_zhuguan_${dept.deptId}","link":"./salesperformance/group/getDeptDetail.do?groupId=${vo.groupId }&deptId=${dept.deptId}","title":"小组详情页"}'>${dept.deptName }</a>
				                		</td>
				                		<td>${dept.monthGoal }</td>
				                		<td>${dept.yjThisMonth }</td>
				                		<td>${dept.avgFullfill }</td>
				                		<td>${dept.yjSort }</td>
				                		<td>${dept.transactionAmount }</td>
				                		<td>${dept.goodsSort }</td>
				                		<td>${dept.tradersCount }</td>
				                		<td>${dept.khSort }</td>
				                		<td>${dept.callTotalTime }</td>
				                		<td>${dept.thSort }</td>
				                		<td>${dept.bdCount }</td>
				                		<td>${dept.bdSort}</td>
				                		<td>${dept.avgScore }</td>
				                		<td>${dept.zhSort }</td>
			                		</tr> 
		                		</c:forEach>
			                </tbody>
		                </c:if> 	
		            </table>
	            </c:forEach>
            </c:if>
        </div>
        
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>

			1. 本月目标、本月业绩额为各个小组内所有参与五行剑法计算的成员的本月目标总和、本月业绩额总和<br/>

			2. 核心商品数为各个小组内所有参与五行剑法计算的成员的核心商品数平均值<br/>

			3. 已合作客户数为各个小组内所有参与五行剑法计算的成员的已合作客户数平均值<br/>

			4. 通话时长为各个小组内所有参与五行剑法计算的成员的通话时长平均值<br/>

			5. BD新客数为各个小组内所有参与五行剑法计算的成员的BD新客数平均值<br/>

			6. 上表所有排名均以小组为单位，将各个小组的平均成绩进行排名得出<br/>

		</div>
        
        <div class="parts" id="historyDatas1">
            
        </div>
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>

			1. 本月目标、本月业绩额为部门内所有参与五行剑法计算的成员的本月目标总和、本月业绩额总和<br/>

			2. 核心商品数为部门内所有参与五行剑法计算的成员的核心商品数平均值<br/>

			3. 已合作客户数为部门内所有参与五行剑法计算的成员的已合作客户数平均值<br/>

			4. 通话时长为部门内所有参与五行剑法计算的成员的通话时长平均值<br/>

			5. BD新客数为部门内所有参与五行剑法计算的成员的BD新客数平均值<br/>

		</div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/group/group_detail.js?rnd=<%=Math.random()%>'></script>
</body>
</html>