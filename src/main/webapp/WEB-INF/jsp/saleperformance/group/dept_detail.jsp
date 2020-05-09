<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="小组详情页" scope="application" />	
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
                        <th style="width:95px;">本月目标（万）</th>
                        <th style="width:107px;">本月业绩额（万）</th>
						<th style="width: 74px;">本月完成度 （%）</th>
						<th>业绩排名</th>
						<th style="width: 146px;">核心商品完成度（%）</th>
						<th style="width: 59px;">核心商品排名</th>
						<th style="width: 146px;">已合作客户数（近90天）</th>
						<th>客户排名</th>
						<th style="width: 84px;">本月通话时长</th>
						<th>话务排名</th>
						<th style="width: 81px;">本月BD新客数</th>
						<th>BD新客数排名</th>
						<th>个人总得分</th>
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
		                   	${vo.deptName }
		                </div>
		            </div>
		            <table class="table">
		            	<input type="hidden" class="deptIdClass" value="${vo.deptId }"/>
		                <thead>
		                  <tr>
		                        <th style="width:60px;">人员</th>
		                        <th style="width:95px;">本月目标（万）</th>
		                        <th style="width:107px;">本月业绩额（万）</th>
								<th style="width: 74px;">本月完成度 （%）</th>
								<th>业绩排名</th>
								<th style="width: 146px;">核心商品完成度（%）</th>
								<th style="width: 59px;">核心商品排名</th>
								<th style="width: 146px;">已合作客户数（近90天）</th>
								<th>客户排名</th>
								<th style="width: 84px;">本月通话时长</th>
								<th>话务排名</th>
								<th style="width: 81px;">本月BD新客数</th>
								<th>BD新客数排名</th>
								<th>个人总得分</th>
								<th>综合排名</th>
		                    </tr>
		                </thead> 
	                	<c:if test="${empty vo.userResList }">
	                		<tr><td colspan="14">查询无结果</td></tr>
	                	</c:if>
	                	<c:if test="${!empty vo.userResList }">
		                	<tbody>
		                		<c:forEach var="dept" items="${vo.userResList}">
		                			<tr>
				                		<td class="font-blue"><a class="addtitle" href="javascript:void(0);" 
	                		tabtitle='{"num":"sales_five_pingtai_zhuguan_${dept.userId}","link":"./sales/fiveSales/detailsPage.do?sortType=1&userId=${dept.userId}&companyId=1&userFlag=1", "title":"五行剑法"}'>${dept.userName }</a></td>
				                		<td>${dept.goal }</td>
				                		<td>
				                			<c:if test="${null ne dept.perYjcount}"><fmt:formatNumber value="${dept.perYjcount/10000}" pattern="##0.00" /></c:if>
				                			<c:if test="${null eq dept.perYjcount}">0</c:if>
				                		</td>
				                		<td>${dept.wcDegree }</td>
				                		<td>${dept.yjSort }</td>
				                		<td>${dept.perGoods }</td>
				                		<td>${dept.goodsSort }</td>
				                		<td>${dept.khCount }</td>
				                		<td>${dept.khSort }</td>
				                		<td>${dept.thCount }</td>
				                		<td>${dept.thSort }</td>
				                		<td>${dept.bdCount }</td>
				                		<td>${dept.bdSort }</td>
				                		<td>${dept.yjScore }</td>
				                		<td>${dept.totalSort }</td>
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
        
        <div class="parts" id="historyDatas">
            
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