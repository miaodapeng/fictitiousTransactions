<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="总经理五行" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<div class="customer">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/director/performance/gettotaldetails.do?groupId=1" >平台业务部</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/director/performance/gettotaldetails.do?groupId=2"  class="customer-color">科研业务部</a>
            <input type="hidden" id="pageOrgId" value="36">
        </li>
    </ul>
</div>
	
	<div class="main-container" style='padding-top:0px;'>
			
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
                <c:if test="${fn:length(areaData) > 0}">
                <c:forEach var="list" items="${areaData}" >
<%--                 	<c:forEach var="user" items="${list.users}"> --%>
                	<tr>
                		<td class="font-blue">
						<a class="addtitle" href="javascript:void(0);" 
                		tabtitle='{"num":"sales_five_keyan_zhuguan_${fn:split(list.orgName,",")[1]}","link":"./director/performance/getgroupmembersdetails.do?orgId=${fn:split(list.orgName,",")[1]}","title":"主管五行"}'>${fn:split(list.orgName,',')[0]}</a>
						</td>
                		<td>${list.goalMonth }</td>
                		<td>${list.orderDetails }</td>
                		<td>${list.completionDegree }</td>
                		<td>${list.orderSort }</td>
                		<td>${list.brandCount }</td>
                		<td>${list.brandSort }</td>
                		<td>${list.traderCount }</td>
                		<td>${list.traderSort }</td>
                		<td>${list.commCount }</td>
                		<td>${list.commSort }</td>
                		<td>${list.rate }</td>
                		<td>${list.rateSort }</td>
                		<td>${list.totalSort }</td>
                	</tr>  	
<%--                 	</c:forEach> --%>
                </c:forEach>
                </c:if>
                <c:if test="${empty areaData}">
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
	<script type="text/javascript" src='<%= basePath %>static/js/saleperformance/manager/science_performance.js?rnd=<%=Math.random()%>'></script>
</body>
</html>