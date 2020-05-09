<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="总监五行" scope="application" />	
<%@ include file="../../common/common.jsp"%>

	
	<div class="main-container">
			<div class="mb10">销售总监</div>
			<input type="hidden" id="groupId" value="${groupId }">
			<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	部门各小组本月五行剑法概况
                </div>
            </div>
            <table class="table">
                <thead>
                     <tr>
                        <th style="width:60px;">人员</th>
                        <th style="width:95px;">本月目标（万）</th>
                        <th style="width:107px;">本月业绩额（万）</th>
						<th style="width: 74px;">本月完成度</th>
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
                		<c:if test="${(groupId == 1 and vo.orgId3 != 44 and vo.orgId3 != 45) or (groupId == 2 and (vo.orgId3 == 44 or vo.orgId3 == 45)) }">
	                	<tr>
	                		<td class="font-blue"> 
							<a class="addtitle" href="javascript:void(0);" 
	                		tabtitle='{"num":"sales_five_pingtai_zhuguan_${vo.orgId3}","link":"./director/performance/getgroupmembersdetails.do?orgId3=${vo.orgId3}&groupId=${groupId}","title":"主管五行"}'>${vo.orgName3}</a>
							</td>
	                		<td>
	                			<fmt:formatNumber type="number" value="${null == vo.goalMonth ? 0.00 : vo.goalMonth.movePointLeft(4)}" pattern="0.00" maxFractionDigits="2" />
	                		</td>
	                		<td>
	                			<fmt:formatNumber type="number" value="${null == vo.amount ? 0.00 : vo.amount.movePointLeft(4)}" pattern="0.00" maxFractionDigits="2" />
	                		</td>
	                		
	                		<c:choose>
	                			<c:when test="${vo.deptAmountRate eq null}">
	                				<td>0.00</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.deptAmountRate }</td>
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
	                			<c:when test="${vo.deptAvgBrandNum eq null}">
	                				<td>0</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.deptAvgBrandNum }</td>
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
	                			<c:when test="${vo.deptAvgCustomerNum eq null}">
	                				<td>0</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.deptAvgCustomerNum }</td>
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
	                			<c:when test="${vo.deptAvgCommLength eq null}">
	                				<td>0秒</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.deptAvgCommLength }</td>
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
	                			<c:when test="${vo.deptAvgBussinessChanceRate eq null}">
	                				<td>0.00</td>
	                			</c:when>
	                			<c:otherwise>
	                				<td>${vo.deptAvgBussinessChanceRate }</td>
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
                	</c:if> 	
                	</c:forEach>
                <c:if test="${empty reulsts}">
                	<tr><td colspan="14">查询无结果</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
        
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>
			
			1. 本月目标、本月业绩额为各个小组内所有参与五行剑法计算的成员的本月目标总和、本月业绩额总和<br/>

			2. 已合作品牌数为各个小组内所有参与五行剑法计算的成员的已合作品牌数平均值<br/>
			
			3. 已合作客户数为各个小组内所有参与五行剑法计算的成员的已合作客户数平均值<br/>
			
			4. 通话时长为各个小组内所有参与五行剑法计算的成员的通话时长平均值<br/>
			
			5. 转化率为各个小组内所有参与五行剑法计算的成员的转化率平均值<br/>
			
			6. 上表所有排名均以小组为单位，将各个小组的平均成绩进行排名得出
         </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	部门本月五行剑法概况
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="">时间</th>
                        <th>本月目标（万）</th>
                        <th>本月业绩额（万）</th>
						<th>本月完成度</th>
						<th class="wid18">已合作品牌数（近90天）</th>
						<th class="wid18">已合作客户数（近90天）</th>
						<th>本月通话时长</th>
						<th>本月转化率（%）</th>
                    </tr>
                </thead> 
                <tbody id="historyDatas">
                
                </tbody>
            </table>
        </div>
        <div class="table-friend-tip mb15" style="margin-top:0;">
                  
			说明：<br/>
			
			1. 本月目标、本月业绩额为部门内所有参与五行剑法计算的成员的本月目标总和、本月业绩额总和<br/>

			2. 已合作品牌数为部门内所有参与五行剑法计算的成员的已合作品牌数平均值<br/>
			
			3. 已合作客户数为部门内所有参与五行剑法计算的成员的已合作客户数平均值<br/>
			
			4. 通话时长为部门内所有参与五行剑法计算的成员的通话时长平均值<br/>
			
			5. 转化率为部门内所有参与五行剑法计算的成员的转化率平均值     
                    
         </div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/manager/chief_performance.js?rnd=<%=Math.random()%>'></script>
</body>
</html>