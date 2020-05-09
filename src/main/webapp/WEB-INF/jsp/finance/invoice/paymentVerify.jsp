<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="付款审核记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
</head>
<body>
	<div class="formpublic formpublic1">
		<div>
			<!-- ------------产品数据列表--------start------- -->
			<div class="controlled" id="goodsListDiv">
				<!-- 搜索表格出来 -->
				<ul class="searchTable">
					<li>
						<div>
							<table class="table table-bordered table-striped table-condensed table-centered mb10">
								<thead>
									<th>操作人</th>
                        			<th>操作时间</th>
                        			<th>操作事项</th>
                        			<th>备注</th>
								</thead>
								<tbody>
									  <c:if test="${null!=historicActivityInstancePay}">
					                    <c:forEach var="hi" items="${historicActivityInstancePay}" varStatus="status">
					                    <c:if test="${not empty  hi.activityName}">
					                    <tr>
					                    	<td>
					                    	<c:choose>
												<c:when test="${hi.activityType == 'startEvent'}"> 
												${startUserPay}
												</c:when>
												<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
												</c:when>
												<c:otherwise>
													<c:if test="${historicActivityInstancePay.size() == status.count}">
														${verifyUsersPay}
													</c:if>
													<c:if test="${historicActivityInstancePay.size() != status.count}">
														${hi.assignee}  
													</c:if>   
												</c:otherwise>
											</c:choose>
					                    	
					                    	
					                    	</td>
					                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					                        <td>
					                        <c:choose>
														<c:when test="${hi.activityType == 'startEvent'}"> 
												开始
												</c:when>
														<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
												结束
												</c:when>
												<c:otherwise>   
												${hi.activityName}  
												</c:otherwise>
											</c:choose>
											</td>
					                        <td class="font-red">${commentMapPay[hi.taskId]}</td>
					                    </tr>
					                    </c:if>
					                    </c:forEach>
					                    </c:if>
									<c:if test="${null==historicActivityInstancePay or empty historicActivityInstancePay}">
									<!-- 查询无结果弹出 -->
									<tr>
										<td colspan='4'>
											查询无结果！请尝试使用其他搜索条件。
										</td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</li>
					
				</ul>
			</div>
			
	
		</div>
	</div>
</body>
</html>