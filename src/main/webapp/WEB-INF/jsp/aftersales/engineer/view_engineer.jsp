<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="工程师详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">基本信息</div>
			<c:choose>
				<c:when test="${engineer.isEnable eq 0 }">
					<div class="title-click f_right mr8"
							onclick="setEnable(${engineer.engineerId});">启用</div>
				</c:when>
				<c:otherwise>
					<div class="font-red f_right mr8 pop-new-data" 
						layerParams='{"width":"600px","height":"200px","title":"禁用工程师","link":"${pageContext.request.contextPath}/aftersales/engineer/changeenable.do?engineerId=${engineer.engineerId}"}'>禁用</div>
				</c:otherwise>
			</c:choose>
			<c:if test="${engineer.isEnable == 1}">
			<div class="title-click nobor pop-new-data" 
				layerParams='{"width":"850px","height":"520px","title":"编辑工程师","link":"./edit.do?engineerId=${engineer.engineerId }"}'>编辑</div>
			</c:if>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">姓名</td>
					<td>${engineer.name }</td>
					<td class="wid20">性质</td>
					<td>
						<c:choose>
							<c:when test="${engineer.owner == 1 }">
							内部
							</c:when>
							<c:when test="${engineer.owner == 2 }">
							外部
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>手机号</td>
					<td>${engineer.mobile }</td>
					<td>电话</td>
					<td>${engineer.telephone }</td>
				</tr>
				<tr>
					<td>微信</td>
					<td>${engineer.weixin }</td>
					<td>从业年份</td>
					<td>${engineer.workYear } 年</td>
				</tr>
				<tr>
					<td>公司名称</td>
					<td>${engineer.company }</td>
					<td>地区</td>
					<td>${engineer.areaStr }</td>
				</tr>
				<tr>
					<td>服务次数</td>
					<td>${engineer.serviceTimes }</td>
					<td>添加时间</td>
					<td><date:date value="${engineer.addTime} " /></td>
				</tr>
				<tr>
					<td>禁用状态</td>
					<td>
						<c:choose>
                     	<c:when test="${engineer.isEnable == 1}">
                     	未禁用
                     	</c:when>
                     	<c:otherwise><span class="font-red">已禁用</span></c:otherwise>
                    	</c:choose>
					</td>
					<td>禁用时间</td>
					<td><date:date value ="${engineer.disableTime} "/></td>
				</tr>
				<tr>
                    <td>禁用原因</td>
                    <td colspan="3" class="text-left">${engineer.disableReason}</td>
                </tr>
				<tr>
					<td>维修产品</td>
					<td colspan="3" class="text-left">${engineer.serviceProducts }</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3" class="text-left">${engineer.comments }</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">财务信息</div>
			<c:if test="${engineer.isEnable == 1}">
			<div class="title-click nobor pop-new-data"
				layerParams='{"width":"500px","height":"210px","title":"编辑财务信息","link":"./editfinance.do?engineerId=${engineer.engineerId}"}'>编辑</div>
			</c:if>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">开户银行</td>
					<td>${engineer.bank }</td>
					<td class="wid20">银行账号</td>
					<td>${engineer.bankAccount }</td>
				</tr>
				<tr>
					<td>开户行支行联号</td>
					<td colspan="3" class="text-left">${engineer.bankCode }</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">服务评分</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">服务评分</td>
					<td>
					<c:if test="${engineer.serviceScore > 0}">${engineer.serviceScore }</c:if>
					</td>
					<td class="wid20">技能评分</td>
					<td>
					<c:if test="${engineer.skillScore > 0}">${engineer.skillScore }</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">服务历史</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="wid5">序号</th>
					<th>售后单号</th>
					<th>订单状态</th>
					<th>服务时间</th>
					<th>工程师酬金</th>
					<th>服务评分</th>
					<th>技能评分</th>
					<th>评分备注</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty engineer.afterSalesInstallstions }">
						<c:forEach items="${engineer.afterSalesInstallstions }" var="afterSalesInstallstion" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td>${afterSalesInstallstion.afterSalesNo }</td>
								<td>
								<c:choose>
									<c:when test="${afterSalesInstallstion.atferSalesStatus == 0 }">
									待确认
									</c:when>
									<c:when test="${afterSalesInstallstion.atferSalesStatus == 1 }">
									进行中
									</c:when>
									<c:when test="${afterSalesInstallstion.atferSalesStatus == 2 }">
									已完结
									</c:when>
									<c:when test="${afterSalesInstallstion.atferSalesStatus == 3 }">
									已关闭
									</c:when>
								</c:choose>
								</td>
								<td><date:date value ="${afterSalesInstallstion.serviceTime} "/></td>
								<td>${afterSalesInstallstion.engineerAmount }</td>
								<td>
								<c:if test="${afterSalesInstallstion.serviceScore > 0}">${afterSalesInstallstion.serviceScore }</c:if>
								</td>
								<td>
								<c:if test="${afterSalesInstallstion.skillScore > 0}">${afterSalesInstallstion.skillScore }</c:if>
								</td>
								<td>${afterSalesInstallstion.scoreComments }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
	            			<td colspan="8">服务历史暂无信息</td>
	            		</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/aftersales/engineer/view_engineer.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>