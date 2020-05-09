<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="批量设置目标" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>/static/js/configset/userConfig.js?rnd=<%=Math.random()%>"></script>
		<div class="main-container">
			<div class="parts">
				 <div class="form-list  form-tips8">
			            <ul>
			                <li class="f_left">
			                    <div class="form-tips">
			                        <label>人员：</label>
			                    </div>
			                    <div class="f_left ">
			                        <div class="form-blanks" style="width:159px;">
			                            <span style="font-weight: bold;">${groupVo.userName }</span>
			                            <input type="hidden" id="nowYear" name="nowYear" value="${nowYear}">
			                            <input type="hidden" id="t_year" name="year" value="${groupVo.year}">
			                            <input type="hidden" id="nowMonth" name="month" value="${month}">
			                            <input type="hidden" id="userId" name="userId" value="${groupVo.userId}">
			                            <input type="hidden" id="yearId" name="yearId" value="${groupVo.salesPerformanceGoalYearId}">
			                            <input type="hidden" id="goalMonth" name="goalMonth" value="${groupVo.goalMonth}">
			                        </div>
			                    </div>
			                </li>
			                <li class="f_left">
			                	 <div class="form-tips">
			                        <label>${groupVo.year}年度目标：</label>
			                    </div>
			                    <div class="f_left ">
			                        <div class="form-blanks">
					                     <input oninput="notIeOnChange(event)" onpropertychange="ieOnChange(event)" class="errobor" type="text" id="goalYear"  name="goalYear" value="${groupVo.goalYear }" > 万
			                        </div>
			                    </div>
			                </li>
			                <c:forEach var="mg" items="${groupVo.monthGoals}" varStatus="status" >
			               	   <li class='f_left'>
				                 	<div class="form-tips">
				                        <label>${status.count}月目标：</label>
				                    </div>
				                    <div class="f_left ">
				                        <div class="form-blanks">
				                        	<!-- or (status.count == month and (null == mg || mg == '')) -->
				                            <c:choose>
					                        	<c:when test="${status.count >= month  }">
						                            <input oninput="notIeOnChange(event)" onpropertychange="ieOnChange(event)" class="errobor" type="text" id="goalMonth_${status.count}" name="goalMonth" value="${mg}"> 万
						                        </c:when>
						                        <c:otherwise>
					                            	<input class="errobor" type="text" id="goalMonth_${status.count}" name="goalMonth" value="${mg}" style="background:#CCCCCC" readonly="true" > 万
						                        </c:otherwise>
					                        </c:choose>
				                        </div>
				                    </div>
			                    </li>
							</c:forEach>
			                 <div id="warn" class="font-red" style="text-align:center;"></div>
			            </ul>
			            <div class="add-tijiao tcenter">
			                <span class="bt-large bt-bg-style bg-light-green" onclick="batchEditGoal();">确认编辑</span>
			                <button class="dele" id="close-layer">取消</button>
			            </div>
			     </div>
			</div>
		</div>
	</body>
</html>
