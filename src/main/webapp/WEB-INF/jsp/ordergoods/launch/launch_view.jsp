<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }查看投放"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">投放信息</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="table-smallest">客户名称</td>
					<td colspan="3" class="text-left">${ordergoodsLaunchVo.traderName }</td>
				</tr>
				<tr>
					<td>设备名称</td>
					<td colspan="3" class="text-left">${ordergoodsLaunchVo.goodsName }
						[${ordergoodsLaunchVo.sku }]</td>
				</tr>
				<tr>
					<td>设备对应试剂分类</td>
					<td colspan="3" class="text-left">${ordergoodsLaunchVo.categoryNames }</td>
				</tr>
				<tr>
					<td>任务指标</td>
					<td colspan="3" class="text-left">
						<table class="table">
							<thead>
								<tr>
									<th class="wid6"></th>
									<th class="wid11">开始时间</th>
									<th class="wid11">结束时间</th>
									<th class="wid11">指标额（万）</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${goalList }" var="goal" varStatus="status">
									<tr>
										<td>第${status.count }年</td>
										<td><date:date value="${goal.startTime} "
												format="yyyy-MM-dd" /></td>
										<td><date:date value="${goal.endTime} "
												format="yyyy-MM-dd" /></td>
										<td>${goal.goalAmount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">完成进度</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>年份</th>
					<th>月平均目标额度（万）</th>
					<c:forEach begin="1" step="1" end="12" var="month">
						<th>第${month }月完成额(￥)</th>
					</c:forEach>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${goalList }" var="goal" varStatus="status">
					<tr>
						<td><date:date value="${goal.startTime} " format="yyyy-MM-dd" /><br>至<br>
						<date:date value="${goal.endTime} " format="yyyy-MM-dd" /></td>
						<td>${ordergoodsLaunchVo.monthAmount }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+1) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+2) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+3) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+4) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+5) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+6) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+7) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+8) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+9) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+10) }</td>
						<td>${monthOrdergoodsLaunch.get((status.count-1)+10*(status.count-1)+11) }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">相关订单</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>订单号</th>
					<th>生效时间</th>
					<th>订单金额(￥)</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty orderList }">
					<c:forEach items="${orderList }" var="order" varStatus="status">
						<tr>
							<td>${status.count }</td>
							<td>${order.saleorderNo }</td>
							<td><date:date value="${order.validTime} " format="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${order.totalAmount }</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty orderList}">
       			<!-- 查询无结果弹出 -->
          		<div class="noresult">查询无结果！</div>
       	</c:if>
	    <div class="parts">
	    	<tags:page page="${page}" />
	    </div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>