<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="设定销售目标" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/home/page/config_sale_goal.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/home/page/saveConfigSaleGoal.do">
        <div class="main-container">
        <div class="parts set-sales-target">
            <table class="table">
                <thead>
                    <tr>
                    	<c:if test="${curr_user.positLevel eq 441 || curr_user.positLevel eq 442}">
	                        <th class="wid15">部门</th>
	                        <th class="wid10">负责人</th>
                        </c:if>
                        <c:if test="${curr_user.positLevel eq 444}">
	                        <th class="wid10">销售人员</th>
                        </c:if>
                        <th>1月份</th>
                        <th>2月份</th>
                        <th>3月份</th>
                        <th>4月份</th>
                        <th>5月份</th>
                        <th>6月份</th>
                        <th>7月份</th>
                        <th>8月份</th>
                        <th>9月份</th>
                        <th>10月份</th>
                        <th>11月份</th>
                        <th>12月份</th>
                        <c:if test="${curr_user.positLevel eq 441 || curr_user.positLevel eq 442}">
                        	<th>部门合计</th>
                        </c:if>
                        <c:if test="${curr_user.positLevel eq 444}">
	                        <th class="">个人合计</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty orgList}" var="org" >
                		<c:forEach items="${orgList}" var="org" >
                			<tr>
	                			<c:if test="${curr_user.positLevel eq 441 || curr_user.positLevel eq 442}">
			                        <td>${org.orgName }</td>
			                        <td>${org.orgLeader }</td>
		                        </c:if>
		                        <c:if test="${curr_user.positLevel eq 444}">
			                        <td>${org.orgLeader }</td>
		                        </c:if>
		                        <c:if test="${not empty org.salesGoalSettingList}">
		                        	<c:set var="sum" value="0" /> 
		                        	<c:forEach items="${org.salesGoalSettingList}" var="sg" varStatus="status">
		                        		<c:set var="sum" value="${sum + sg.goal}" /> 
		                        		<td>
		                        			<c:if test="${status.count lt month }">
		                        				${sg.goal}
		                        			</c:if>
		                        			<c:if test="${status.count ge month }">
		                        				<input type="text" value="${sg.goal}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_${status.count}" 
		                        						id="month_${status.count}_${org.orgId}" onblur="updateTotal(this,${org.orgId},${org.orgLeaderId},${sg.salesGoalSettingId});">
		                        				<input type="hidden" value="" name="month_${status.count}">		
		                        			</c:if>
				                        </td>
		                        	</c:forEach>
		                        	<td>${sum}</td>
		                        </c:if>
		                        <c:if test="${empty org.salesGoalSettingList}">
		                        	<td>
		                        		<input type="text" id="month_1_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_1"
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">	
		                        		<input type="hidden" value="" name="month_1">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_2_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_2"
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_2">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_3_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_3" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_3">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_4_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_4"
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">	
		                        		<input type="hidden" value="" name="month_4">
				                    </td>
				                    <td>
		                        		<input type="text" id="month_5_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_5" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_5">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_6_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}"  alt2="mon_6"
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_6">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_7_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_7" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_7">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_8_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_8" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_8">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_9_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_9" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_9">	
				                    </td>
				                    <td>
		                        		<input type="text" id="month_10_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_10" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">
		                        		<input type="hidden" value="" name="month_10">	
				                    </td>
				                    <td>
		                        		<input type="text"  id="month_11_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_11"
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">	
		                        		<input type="hidden" value="" name="month_11">
				                    </td>
				                    <td>
		                        		<input type="text"  id="month_12_${org.orgId}" alt ="${org.orgId}" alt1="${org.orgLeaderId}" alt2="mon_12" 
		                        				onblur="saveTotal(this,${org.orgId},${org.orgLeaderId});">	
		                        		<input type="hidden" value="" name="month_12">
				                    </td>
				                    <td id="deptSum_${org.orgId}"></td>
		                        </c:if>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${curr_user.positLevel eq 441}"><!-- 销售总监 -->
                		<tr>
	                        <td>月份合计</td>
	                        <td></td>
	                        <c:if test="${not empty totalMonths}">
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<td id="total_${status.count}" class="total">${total}</td>
	                        	</c:forEach>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
		                        <td id="total_1" class="total"></td>
		                        <td id="total_2" class="total"></td>
		                        <td id="total_3" class="total"></td>
		                        <td id="total_4" class="total"></td>
		                        <td id="total_5" class="total"></td>
		                        <td id="total_6" class="total"></td>
		                        <td id="total_7" class="total"></td>
		                        <td id="total_8" class="total"></td>
		                        <td id="total_9" class="total"></td>
		                        <td id="total_10" class="total"></td>
		                        <td id="total_11" class="total"></td>
		                        <td id="total_12" class="total"></td>
	                        </c:if>
	                        <td></td>
	                    </tr>
	                    <tr>
	                    	<c:if test="${not empty totalMonths}">
	                    		<c:set var="sum" value="0"></c:set>
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<c:set var="sum" value="${sum+total}"></c:set>
	                        	</c:forEach>
	                        	<td colspan="15">当前，全年目标<span id="totalGoal">${sum}</span>万元</td>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
	                        	<td colspan="15">当前，全年目标<span id="totalGoal">0</span>万元</td>
	                        </c:if>
	                    </tr>
                	</c:if>
                    <c:if test="${curr_user.positLevel eq 442}"><!-- 高级销售主管 -->
                		<tr>
	                        <td>合计</td>
	                        <td></td>
	                        <c:if test="${not empty totalMonths}">
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<td id="total_${status.count}" class="total">${total}</td>
	                        	</c:forEach>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
		                        <td id="total_1" class="total"></td>
		                        <td id="total_2" class="total"></td>
		                        <td id="total_3" class="total"></td>
		                        <td id="total_4" class="total"></td>
		                        <td id="total_5" class="total"></td>
		                        <td id="total_6" class="total"></td>
		                        <td id="total_7" class="total"></td>
		                        <td id="total_8" class="total"></td>
		                        <td id="total_9" class="total"></td>
		                        <td id="total_10" class="total"></td>
		                        <td id="total_11" class="total"></td>
		                        <td id="total_12" class="total"></td>
	                        </c:if>
	                    </tr>
	                    <tr>
	                        <td>目标</td>
	                        <td></td>
	                        <c:if test="${not empty goalMonths}">
	                        	<c:forEach items="${goalMonths}" var="gogal" varStatus="status">
	                        		<td id="gogal_${status.count}" class="gogal">${gogal}</td>
	                        	</c:forEach>
	                        </c:if>
	                        <c:if test="${empty goalMonths}">
		                        <td id="gogal_1" class="gogal"></td>
		                        <td id="gogal_2" class="gogal"></td>
		                        <td id="gogal_3" class="gogal"></td>
		                        <td id="gogal_4" class="gogal"></td>
		                        <td id="gogal_5" class="gogal"></td>
		                        <td id="gogal_6" class="gogal"></td>
		                        <td id="gogal_7" class="gogal"></td>
		                        <td id="gogal_8" class="gogal"></td>
		                        <td id="gogal_9" class="gogal"></td>
		                        <td id="gogal_10" class="gogal"></td>
		                        <td id="gogal_11" class="gogal"></td>
		                        <td id="gogal_12" class="gogal"></td>
	                        </c:if>
	                    </tr>
	                    <tr>
	                    	<c:if test="${not empty totalMonths}">
	                    		<c:set var="sum" value="0"></c:set>
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<c:set var="sum" value="${sum+total}"></c:set>
	                        	</c:forEach>
	                        	<td colspan="15">当前，全年目标<span id="totalGoal">${sum}</span>万元</td>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
	                        	<td colspan="15">当前，全年目标<span id="totalGoal">0</span>万元</td>
	                        </c:if>
	                    </tr>
                	</c:if>
                	<c:if test="${curr_user.positLevel eq 444}"><!-- 销售主管 -->
                		<tr>
	                        <td>合计</td>
	                        <c:if test="${not empty totalMonths}">
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<td id="total_${status.count}" class="total">${total}</td>
	                        	</c:forEach>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
		                        <td id="total_1" class="total"></td>
		                        <td id="total_2" class="total"></td>
		                        <td id="total_3" class="total"></td>
		                        <td id="total_4" class="total"></td>
		                        <td id="total_5" class="total"></td>
		                        <td id="total_6" class="total"></td>
		                        <td id="total_7" class="total"></td>
		                        <td id="total_8" class="total"></td>
		                        <td id="total_9" class="total"></td>
		                        <td id="total_10" class="total"></td>
		                        <td id="total_11" class="total"></td>
		                        <td id="total_12" class="total"></td>
	                        </c:if>
	                        <td></td>
	                    </tr>
	                    <tr>
	                        <td>目标</td>
	                        <c:if test="${not empty goalMonths}">
	                        	<c:forEach items="${goalMonths}" var="gogal" varStatus="status">
	                        		<td id="gogal_${status.count}" class="gogal">${gogal}</td>
	                        	</c:forEach>
	                        </c:if>
	                        <c:if test="${empty goalMonths}">
		                        <td id="gogal_1" class="gogal"></td>
		                        <td id="gogal_2" class="gogal"></td>
		                        <td id="gogal_3" class="gogal"></td>
		                        <td id="gogal_4" class="gogal"></td>
		                        <td id="gogal_5" class="gogal"></td>
		                        <td id="gogal_6" class="gogal"></td>
		                        <td id="gogal_7" class="gogal"></td>
		                        <td id="gogal_8" class="gogal"></td>
		                        <td id="gogal_9" class="gogal"></td>
		                        <td id="gogal_10" class="gogal"></td>
		                        <td id="gogal_11" class="gogal"></td>
		                        <td id="gogal_12" class="gogal"></td>
	                        </c:if>
	                        <td></td>
	                    </tr>
	                    <tr>
	                    	<c:if test="${not empty totalMonths}">
	                    		<c:set var="sum" value="0"></c:set>
	                        	<c:forEach items="${totalMonths}" var="total" varStatus="status">
	                        		<c:set var="sum" value="${sum+total}"></c:set>
	                        	</c:forEach>
	                        	<td colspan="14">当前，全年目标<span id="totalGoal">${sum}</span>万元</td>
	                        </c:if>
	                        <c:if test="${empty totalMonths}">
	                        	<td colspan="14">当前，全年目标<span id="totalGoal">0</span>万元</td>
	                        </c:if>
	                    </tr>
                	</c:if>
                    
                </tbody>
            </table>
        </div>
         <div class="add-tijiao tcenter">
         		<input type="hidden" name="positLevel" value="${curr_user.positLevel}">
         		<input type="hidden" name="beforeParams" value='${beforeParams}'>
                <button type="submit" id="submit">提交</button>
            </div>
    </div>
   </form>
</div>
<%@ include file="../../common/footer.jsp"%>