
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后单列表" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript"></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/aftersales/order/search_afterorder_list.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
			<form action="">
				<div>
					<!-- 初始模式 -->
					<ul class="searchTable visible">
						<li>
							<div class="infor_name  infor_name6 ">
								<span>*</span> <label>售后单号</label>
							</div>

							<div class="f_left table-larger">
								<div class="mb10">
									<input type="text" class="input-larger mr5 J-searchAfterSalesNo"
										name="searchTraderName" value="${afterSalesVo.afterSalesNo}">
									<span class="bt-bg-style bt-small bg-light-blue J-block1 J-search-btn">搜索</span>
									<div class="warning J-error" style="display: none;">查询条件不允许为空</div>
								</div>
							</div> <!-- <div class="J-block1" style="display: none;"> -->
							<div>
								<table
									class="table table-bordered table-striped table-condensed table-centered mb10">
									<thead>
										<th>售后单号</th>
										<th>产品名称</th>
										<th>品牌</th>
										<th>型号</th>
										<th>换货数量</th>
										<th>已退回数量</th>
										<th>已重发数量</th>
										<th class="table-smallest6">选择</th>
									</thead>
									<tbody>

										<c:if test="${not empty list}">
											<c:forEach items="${list}" var="item"
												varStatus="status">
												<tr>
													<td>${item.afterSalesNo}</td>
													<td>${item.goodsName}</td>
													<td>${item.brandName}</td>
													<td>${item.model}</td>
													<td>${item.afterGoodsNum}</td>
													<td>${item.exchangeReturnNum}</td>
													<td>${item.exchangeDeliverNum}</td>
													<td width="5%" style="text-align: center">
													<c:if test="${item.eFlag eq 1 }">
													<a href="javaScript:void(0)"
														onclick="selectlendOut('${item.afterSalesNo}','${item.goodsName}','${item.afterGoodsNum}','${item.exchangeDeliverNum}','${item.goodsStockNum}','${item.goodsId}')">选择</a>
													</c:if>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty list}">
											<tr>
												<td colspan="8">查询无结果！请尝试使用其他关键词搜索。</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</li>
						<li class="visible"><tags:page page="${page}" />
							<div class="clear"></div></li>
						<div class="clear"></div>
					</ul>
					<!-- 搜索最后结果lastResult -->
				</div>
			</form>
	</div>
</div>
</body>
</html>
