<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品外借记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content logistics-warehousein-index" style='margin-top: 20px;'>
<div class="searchfunc">
<form method="post" id="search" action="<%=basePath%>logistics/warehousein/lendoutListView.do">
	<ul style='display: flex;justify-content: space-around;'>
		<li>
			<label class="infor_name">关键词搜索</label>
			<input type="text" class="input-middle" placeholder="输入外借单号、借用企业、产品名称、关联售后单查找" name="searchStr"  value="${lendout.searchStr}"/>
		</li>
		<li>
			<label class="infor_name">借用原因</label>
			<select class="input-middle" name="lendOutType" id="type">
						<option value="">全部</option>
						<option <c:if test="${lendout.lendOutType eq 1}">selected</c:if> value="1">样品外借</option>
						<option <c:if test="${lendout.lendOutType eq 2}">selected</c:if> value="2">售后垫货</option>
	</select>
	</li>
	<li><label class="infor_name">发货状态</label> <select
		class="input-middle" name="deliveystatus" id="returntime">
			<option value="">全部</option>
			<option <c:if test="${lendout.deliveystatus eq 1}">selected</c:if>
				value="1">未发货</option>
			<option <c:if test="${lendout.deliveystatus eq 2}">selected</c:if>
				value="2">部分发货</option>
			<option <c:if test="${lendout.deliveystatus eq 3}">selected</c:if>
				value="3">全部发货</option>	
	</select></li>
	<li><label class="infor_name">外借状态</label> <select
		class="input-middle" name="lendOutStatus" id="returntime">
			<option value="">全部</option>
			<option <c:if test="${lendout.lendOutStatus eq 0}">selected</c:if>
				value="0">进行中</option>
			<option <c:if test="${lendout.lendOutStatus eq 1}">selected</c:if>
				value="1">已完结</option>
			<option <c:if test="${lendout.lendOutStatus eq 2}">selected</c:if>
				value="2">已关闭</option>	
	</select></li>
	</ul>
	<div class="tcenter" style='margin-top: 20px;margin-bottom: 10px;'>
		<span class="confSearch bt-small bt-bg-style bg-light-blue"
			onclick="search();" id="searchSpan">搜索</span> <span
			class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
	</div>
	</form>
</div>
<div class="table-style5">
	<table class="table">
		<thead>
			<tr>
				<th class="wid3">序号</th>
				<th class="wid9">外借单号</th>
				<th class="wid9">外借状态</th>
				<th class="wid8">借用原因</th>
				<th class="wid15">借用企业</th>
				<th class="wid10">产品名称</th>
				<th class="wid10">借用数量</th>
				<th class="wid8">归还数量</th>
				<th class="wid8">发货状态</th>
				<th class="wid8">创建人</th>
				<th class="wid8">创建时间</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lendoutlist}" varStatus="num">
			<tr>
				<td>${num.count}</td>
				<td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"lendOutdetailJump${list.lendOutId}","link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${list.lendOutId}","title":"外借详情页"}'>${list.lendOutNo}</a></td>
				<td>
				<c:if test="${list.lendOutStatus eq 0}">进行中</c:if>
				<c:if test="${list.lendOutStatus eq 1}">已完结</c:if>
				<c:if test="${list.lendOutStatus eq 2}">已关闭</c:if>
				</td>
				<td>
				<c:if test="${list.lendOutType eq 1}">样品外借</c:if>
				<c:if test="${list.lendOutType eq 2}">售后垫货</c:if>
				</td>
				<td>
				<c:if test="${list.traderType eq 1 }">
				<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
				</c:if> <c:if test="${list.traderType eq 2 }">
				<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsupplier${list.traderId}", "link":"./trader/supplier/baseinfo.do?traderId=${list.traderId}", "title":"供应商信息"}'>${list.traderName}</a>
				</c:if>
				</td>
				<td> <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${spuMap[list.goodsId]}</a></td>
				<td>${list.lendOutNum}</td>
				<td>${list.returnNum}</td>
				<td>
				<c:if test="${list.deliveystatus eq 1}">未发货</c:if>
				<c:if test="${list.deliveystatus eq 2}">部分发货</c:if>
				<c:if test="${list.deliveystatus eq 3}">全部发货</c:if>
				</td>
				<td>${list.creatorName}</td>
				<td><date:date value ="${list.addTime}"/></td>
			</tr>
			</c:forEach>
			<c:if test="${empty lendoutlist}">
				<tr>
					<td colspan="11">查询无结果！请尝试使用其他搜索条件。</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<div class="mt-5">
		<tags:page page="${page}" />
	</div>
</div>
</div>
<script type="text/javascript"
	src='<%=basePath%>static/js/logistics/warehouseIn/lendout_index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>