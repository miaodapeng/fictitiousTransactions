<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="主管五行" scope="application" />	
<%@ include file="../../common/common.jsp"%>

	<div class="main-container">
			<div class="mb10">销售主管</div>
			<input type="hidden" id="pageOrgId" value="${orgId3 }">
			<input type="hidden" id="groupId" value="${groupId }">
			<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	小组成员本月五行剑法概况
                </div>
            </div>
            <table class="table">
                <thead>
                     <tr>
                        <th style="width:60px;">人员</th>
                        <th style="width:95px;">本月目标（万）</th>
                        <th style="width:107px;">本月业绩额（万）</th>
						<th style="width: 74px;">本月完成度（%）</th>
						<th style="width: 60px;">业绩排名</th>
						<th style="width: 146px;">已合作品牌数（近90天）</th>
						<th style="width: 59px;">品牌排名</th>
						<th style="width: 146px;">已合作客户数（近90天）</th>
						<th style="width: 59px;">客户排名</th>
						<th style="width: 84px;">本月通话时长</th>
						<th style="width: 59px;">话务排名</th>
						<th style="width: 81px;">本月转化率（%）</th>
						<th style="width: 72px;">转化率排名</th>
						<th style="width: 60px;">综合排名</th>
                    </tr>
                </thead> 
                <tbody>
                <c:forEach var="vo" items="${reulsts}" >
                	<tr>
                		<td class="font-blue">
							<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"sale_five_per_${vo.userId}", "link":"./sales/fiveSales/detailsPage.do?sortType=1&userId=${vo.userId}&companyId=1","title":"销售五行"}'>${vo.username}</a>
						</td>
                		<td>
                			<fmt:formatNumber type="number" value="${null == vo.goalMonth ? 0.00 : vo.goalMonth.movePointLeft(4)}" pattern="0.00" maxFractionDigits="2" />
                		</td>
                		<td>
                			<fmt:formatNumber type="number" value="${null == vo.amount ? 0.00 : vo.amount.movePointLeft(4) }" pattern="0.00" maxFractionDigits="2" />
                		</td>
                		<c:choose>
	                			<c:when test="${vo.amountRate eq null}">
	                				<td>0.00</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.amountRate }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
                		<c:choose>
	                			<c:when test="${vo.sort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.sort }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.brandNum eq null}">
	                				<td>0</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.brandNum }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
                		<c:choose>
	                			<c:when test="${vo.brandSort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.brandSort }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.customerNum eq null}">
	                				<td>0</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.customerNum }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.customerSort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.customerSort }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.commLength eq null}">
	                				<td>0秒</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.commLength }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.commSort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.commSort }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.bussinessChanceRate eq null}">
	                				<td>0.00</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.bussinessChanceRate }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.bussinessChanceRateSort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.bussinessChanceRateSort }</td>
	                			</c:otherwise>
	                		</c:choose>
	                		
	                		<c:choose>
	                			<c:when test="${vo.totalSort eq null}">
	                				<td>-</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.totalSort }</td>
	                			</c:otherwise>
	                		</c:choose>
                	</tr>  	
                </c:forEach>
                <c:if test="${empty reulsts}">
                	<tr><td colspan="14">查询无结果</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
        
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>
			
			1. 上表所有排名均为个人在整个二级部门中的排名<br/>
         </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	小组本月五行剑法概况
                </div>
            </div>
            <table class="table">
                <thead>
                 
                   
                    <tr>
                        <th style="width:60px;">时间</th>
                        <th style="width:95px;">本月目标（万）</th>
                        <th style="width:107px;">本月业绩额（万）</th>
						<th style="width: 74px;">本月完成度 （%）</th>
						<th style="width: 60px;">业绩排名</th>
						<th style="width: 146px;">已合作品牌数（近90天）</th>
						<th style="width: 59px;">品牌排名</th>
						<th style="width: 146px;">已合作客户数（近90天）</th>
						<th style="width: 59px;">客户排名</th>
						<th style="width: 84px;">本月通话时长</th>
						<th style="width: 59px;">话务排名</th>
						<th style="width: 81px;">本月转化率（%）</th>
						<th style="width: 72px;">转化率排名</th>
						<th style="width: 60px;">综合排名</th>
                    </tr>
                </thead> 
                <tbody id="historyDatas">

                </tbody>
            </table>
        </div>
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>
			
			1. 本月目标、本月业绩额为小组内所有参与五行剑法计算的成员的本月目标总和、本月业绩额总和<br/>
			
			2. 已合作品牌数为小组内所有参与五行剑法计算的成员的已合作品牌数平均值<br/>
			
			3. 已合作客户数为小组内所有参与五行剑法计算的成员的已合作客户数平均值<br/>
			
			4. 通话时长为小组内所有参与五行剑法计算的成员的通话时长平均值<br/>
			
			5. 转化率为小组内所有参与五行剑法计算的成员的转化率平均值<br/>
			
			6. 上表所有排名均以小组为单位，将各个小组的平均成绩进行排名       
                    
         </div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/manager/director_performance.js?rnd=<%=Math.random()%>'></script>
</body>
</html>