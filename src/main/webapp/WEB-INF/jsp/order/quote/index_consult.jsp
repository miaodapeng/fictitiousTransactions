<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="报价咨询管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/quote/index_consult.js?rnd=<%=Math.random()%>'></script>
<div class="main-container">
	<div class="list-pages-search">
		<form method="post" id="search" action="<%=basePath%>order/quote/getQuoteConsultListPage.do">
			<ul>
				<li>
					<label class="infor_name">报价单号</label>
					<input type="text" class="input-middle" name="quoteorderNo" id="quoteorderNo" value="${quoteConsult.quoteorderNo}"/>
				</li>
				<li>
					<label class="infor_name">销售人员</label>
					<select class="input-middle" name="saleUserId" id="saleUserId">
						<option value="">全部</option>
						<c:forEach var="list" items="${quoteConsultUserList}" varStatus="status">
							<option value="${list.userId}" <c:if test="${quoteConsult.saleUserId eq list.userId}">selected</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				<%-- <li>
					<label class="infor_name">产品部门</label>
					<select class="input-middle" name="saleUserId" id="saleUserId">
						<option value="">全部</option>
						<c:forEach var="list" items="${productOrgList}" varStatus="status">
							<option value="${list.orgId}">${list.orgName}</option>
						</c:forEach>
					</select>
				</li> --%>
				<li>
					<label class="infor_name">产品归属</label>
					<select class="input-middle" name="goods.goodsUserId" id="goodsUserId">
						<option value="">全部</option>
						<c:forEach var="list" items="${productUserList}" varStatus="status">
							<option value="${list.userId}" <c:if test='${quoteConsult.goods.goodsUserId eq list.userId}'>selected</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goods.goodsName" id="goodsName" value="${quoteConsult.goods.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="goods.brandName" id="brandName" value="${quoteConsult.goods.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="goods.model" id="model" value="${quoteConsult.goods.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="goods.sku" id="sku" value="${quoteConsult.goods.sku}"/>
				</li>
				<li>
					<label class="infor_name">咨询答复</label>
					<select class="input-middle" name="consultStatus" id="consultStatus">
						<option value="">全部</option>
						<option <c:if test="${quoteConsult.consultStatus eq 1}">selected</c:if> value="1">未处理</option>
						<option <c:if test="${quoteConsult.consultStatus eq 2}">selected</c:if> value="2">处理中</option>
						<option <c:if test="${quoteConsult.consultStatus eq 3}">selected</c:if> value="3">已处理</option>
					</select>
				</li>
				<li>
					<label class="infor_name">报价来源</label>
					<select class="input-middle" name="sourceQuae" id="sourceQuae">
						<option value="">全部</option>
						<option value="BD" <c:if test="${quoteConsult.sourceQuae == 'BD'}">selected</c:if>>订单</option>
						<option value="VD" <c:if test="${quoteConsult.sourceQuae =='VD'}">selected</c:if>>商机</option>

					</select>
				</li>
				<li>
					<div class="infor_name specialinfor"> 
						<select class="input-smaller" name="type" id="type">
							<option value="1" <c:if test="${quoteConsult.type eq 1}">selected</c:if>>咨询时间</option>
							<option value="2" <c:if test="${quoteConsult.type eq 2}">selected</c:if>>答复时间</option>
						</select>
					</div>
					<!-- {dateFmt:'yyyy-MM-dd HH:mm:ss'} -->
					<input class="Wdate f_left input-smaller mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="searchBeginTime" value="${searchBeginTime}">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="searchEndTime" value="${searchEndTime}">
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div class="normal-list-page">
		<table class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid4">序号</th>
					<th class="wid10">报价单号</th>
					<th class="wid14">报价来源</th>
					<th class="wid12">咨询时间</th>
					<th class="wid8">销售人员</th>
					<%--<th class="wid8">产品归属</th>--%>
					<th class="wid8">问答次数</th>
					<th class="wid12">回复时间</th>
					<th class="wid10">咨询答复</th>
					<th class="wid20">咨询内容</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${quoteConsultList}" varStatus="num">
					<tr>
						<td>${num.count}</td>
						<td>
							<a class="addtitle" tabtitle='{"num":"quote_consult${list.quoteorderId}","link":"./order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&viewType=5","title":"编辑报价"}'>${list.quoteorderNo}</a>
						</td>
						<td><c:if test="${fn:substring(list.quoteorderNo,0,2) == 'VD'}">商机</c:if>
							<c:if test="${fn:substring(list.quoteorderNo,0,2) == 'BD'}">订单</c:if>
						</td>
						<td><date:date value="${list.sendTime}" /></td>
						<td>${list.saleUserName}</td>
						<%--<td>&lt;%&ndash; ${list.goodsUserNm} &ndash;%&gt;
						<c:if test="${not empty list.userList}">
							<c:forEach items="${list.userList }" var="goodsUser" varStatus="st"><!-- 商品归属人 -->
								<c:set var="abc" value="0"></c:set>
								<c:forEach items="${replyUserList}" var="replyUser"><!-- 咨询答复人 -->
									<c:if test="${goodsUser.userId eq replyUser.creator and list.quoteorderId eq replyUser.quoteorderId}">
										<c:set var="abc" value="1"></c:set>
									</c:if>
								</c:forEach>
								<c:if test="${abc eq 1}"><font color="blue">${goodsUser.username}</font></c:if>
								<c:if test="${abc eq 0}">${goodsUser.username}</c:if> 
								<c:set var="abc" value="0"></c:set>
								<c:if test="${st.count != list.userList.size() }">、</c:if>
							</c:forEach>
						</c:if>
						</td>--%>
						<td>${list.saleQuizNum} | ${list.purchaseReplyNum}</td>
						<td><date:date value ="${list.replayTime}"/></td>
						<td>
							<c:choose>
								<c:when test="${list.consultStatus eq 1}"><span style="color:red;">未处理</span></c:when>
								<c:when test="${list.consultStatus eq 2}"><span style="color:red;">处理中</span></c:when>
								<c:when test="${list.consultStatus eq 3}"><span style="color:green;">已处理</span></c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose>
						</td>
						<td>${list.content}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty quoteConsultList}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan='9'>查询无结果！请尝试使用其它搜索条件。</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<tags:page page="${page}" />
</div>
<%@ include file="../../common/footer.jsp"%>
