<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索供应商" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/goods/goods/search_supplier.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
    
	<form action="${pageContext.request.contextPath}/goods/goods/getSupplierByName.do" id="myform1" method="post">
		
	<div>
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<label>供应商名称</label>	 
				</div>
				<div class='f_left'>
					<div class='inputfloat'>
						<input type="text" name="supplierName" id="supplierName" value="${supplierName}" class="input-largest">
						<span class="bg-light-blue bt-bg-style bt-small"  id="search">搜索</span>					
					</div>
					<div id="none" class="font-red none">查询条件不允许为空</div>
				</div>
			</li>
		</ul>
	</div>
	<input type="hidden" name="goodsId" value="${goodsId}">
	</form>
	<div>
		<table class="table table-bordered table-striped table-condensed table-centered" id="cus">
			<thead>
				<tr>
					<th class="text-left wid15">供应商名称</th>
					<th class="wid13">地区</th>
					<th class="wid12">创建时间</th>
					<th class="wid8">归属</th>
					<th class="wid6">选择</th>
				</tr>
			</thead>
			<tbody class="employeestate">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="tsv" varStatus="status">
						<tr>
							<td>${tsv.traderSupplierName }</td>
							<td>${tsv.traderSupplierAddress }</td>
							<td><date:date value ="${tsv.addTime} "/></td>
							<td>${tsv.personal}</td>
							<td>
									<a class='setWidth' href="javascript:void(0)"
										onclick="addSelectSupplier(${goodsId}, ${tsv.traderSupplierId});">选择</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty list }">
			<!-- 查询无结果弹出 -->
         	<div class="noresult"> 查询无结果！请尝试使用其他搜索条件
		</c:if>
		<tags:page page="${page}"/>
	</div>
    </div>
</body>

</html>
