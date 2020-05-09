<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript"></script>
<script type="text/javascript"
	src='<%=basePath%>static/js/customer/search_customer_list.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
		<c:if test="${lendOut eq 1 }">
			<form  class="J-add-search_customer-search">
				<div>
					<!-- 初始模式 -->
					<ul class="searchTable visible">
						<li>
							<div class="infor_name  infor_name6 ">
								<span>*</span> <label>客户名称</label>
							</div>

							<div class="form-blanks">
								<label>
								 <input type="radio" class="input-middle J-type"
									name="test" checked value="1" /><span>客户 </span>
								</label> 
								<label> 
								<input type="radio" class="input-middle J-type" id="radio_2"
									name="test" value="2" /><span>供应商 </span>
								</label>
							</div>

							<div class="f_left table-larger">
								<div class="mb10">
									<input type="text" class="input-larger mr5 J-searchTraderName"
										name="searchTraderName" value="${traderCustomerVo.searchTraderName}">
									<span class="bt-bg-style bt-small bg-light-blue J-block1 J-search-btn">搜索</span>
									<span class="bt-bg-style bt-small bg-light-blue J-block2 J-search-btn">搜索</span>
									<div class="warning J-error" style="display: none;">查询条件不允许为空</div>
								</div>
							</div> <!-- <div class="J-block1" style="display: none;"> -->
							<div>
								<table
									class="table table-bordered table-striped table-condensed table-centered mb10">
									<thead>
										<th>客户名称</th>
										<th>地区</th>
										<th>创建时间</th>
										<th>归属人员</th>
										<th class="table-smallest6">选择</th>
									</thead>
									<tbody>

										<c:if test="${not empty traderList}">
											<c:forEach items="${traderList}" var="list"
												varStatus="status">
												<tr>
													<td>${list.traderName}</td>
													<td>${list.address}</td>
													<td><date:date value="${list.addTime}" /></td>
													<td>${list.owner}</td>
													<td width="5%" style="text-align: center"><a
														href="javaScript:void(0)"
														onclick="selectlendOut('${list.traderId}','${list.traderName}','${list.traderType}')">选择</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty traderList}">
											<tr>
												<td colspan="5">查询无结果！请尝试使用其他关键词搜索。</td>
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
				<input type="hidden" value="${block}" id="block_2">
				</form>
		</c:if>
		<c:if test="${lendOut ne 1 }">
			<form method="post" id="search"
				action="<%= basePath %>trader/customer/searchCustomerList.do?indexId=${indexId}">
				<div>
					<!-- 初始模式 -->
					<ul class="searchTable visible">
						<li>
							<div class="infor_name  infor_name6 ">
								<span>*</span> <label>客户名称</label>
							</div>

							<div class="f_left table-larger">
								<div class="mb10">
									<input type="text" class="input-larger mr5"
										name="searchTraderName" id="searchTraderName"
										value="${traderCustomerVo.searchTraderName}"> <span
										class="bt-bg-style bt-small bg-light-blue" onclick="search();"
										id="searchError">搜索</span>

								</div>
							</div>
							<div>
								<table
									class="table table-bordered table-striped table-condensed table-centered mb10">
									<thead>

										<th>客户名称</th>
										<th>地区</th>
										<th>创建时间</th>
										<th>归属销售</th>
										<th class="table-smallest6">选择</th>
									</thead>
									<tbody>

										<c:if test="${not empty searchCustomerList}">
											<c:forEach items="${searchCustomerList}" var="list"
												varStatus="status">
												<tr>
													<td>${list.traderName}</td>
													<td>${list.address}</td>
													<td><date:date value="${list.addTime}" /></td>
													<td>${list.personal}</td>
													<td width="5%" style="text-align: center"><a
														href="javaScript:void(0)"
														onclick="selectCustomer('${indexId}','${list.traderId}','${list.traderName}','${list.traderCustomerId}','${list.customerType}', '${list.customerNature}')">选择</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty searchCustomerList}">
											<tr>
												<td colspan="5">查询无结果！请尝试使用其他关键词搜索。</td>
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
		</c:if>
	</div>
</div>
</body>
</html>
