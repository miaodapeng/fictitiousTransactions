<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/configset/userConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="main-container">
			<div class="parts">
				 <div class="form-list  form-tips8">
			        <form method="post" id="submit">
			            <ul>
			                <li>
			                    <div class="form-tips">
			                        <label>人员：</label>
			                    </div>
			                    <div class="f_left ">
			                        <div class="form-blanks">
			                            <span style="font-weight: bold;">${groupVo.userName }</span>
			                            <input type="hidden" id="userId" name="userId" value="${groupVo.userId }">
			                            <input type="hidden" id="orgId" name="orgId" value="${groupVo.orgId }">
			                            <input type="hidden" id="salesPerformanceGroupId" name="salesPerformanceGroupId" value="${groupVo.salesPerformanceGroupId }">
			                            <input type="hidden" id="salesPerformanceGoalYearId" name="salesPerformanceGoalYearId" value="${groupVo.salesPerformanceGoalYearId }">
			                            <input type="hidden" id="salesPerformanceGoalMonthId" name="salesPerformanceGoalMonthId" value="${groupVo.salesPerformanceGoalMonthId }">
			                            <input type="hidden" id="rSalesPerformanceGroupJUserId" name="rSalesPerformanceGroupJUserId" value="${groupVo.rSalesPerformanceGroupJUserId }">
			                        </div>
			                    </div>
			                </li>
			                <li>
			                    <div class="form-tips">
			                        <label>年度目标：</label>
			                    </div>
			                    <div class="f_left ">
			                        <div class="form-blanks">
			                           <input class="errobor" type="text" id="goalYear" name="goalYear" value="${groupVo.goalYear }"> 万
			                        </div>
			                    </div>
			                </li>
			                <li>
			                    <div class="form-tips">
			                        <label>本月目标：</label>
			                    </div>
			                    <div class="f_left ">
			                        <div class="form-blanks">
			                            <input class="errobor" type="text" id="goalMonth" name="goalMonth" value="${groupVo.goalMonth }"> 万
			                        </div>
			                    </div>
			                </li>
			                 <div id="warn" class="font-red" style="text-align:center;"></div>
			            </ul>
			            <div class="add-tijiao tcenter">
			                <button type="button" class="bt-large bt-bg-style bg-light-green" id="sub" onclick="submitData();">提交</button>
			                <button class="dele" id="close-layer">取消</button>
			            </div>
			        </form>
			     </div>
			</div>
		</div>
	</body>
</html>
