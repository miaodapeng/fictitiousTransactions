<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索售后对象" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" >
	$(function(){
		$("#realTraderType").change(function(){
			if($("#realTraderType").val() == 1){
				$("#trader").html("客户名称");
				$(".sup").addClass("none");
				$(".cus").removeClass("none");
				$(".pages_container").remove();
				$("#traderName").val("");
			}else{
				$("#trader").html("供应商名称");
				$(".sup").removeClass("none");
				$(".cus").addClass("none");
				$(".pages_container").remove();
				$("#traderName").val("");
			}
		})
		
		$("#search").click(function(){
			checkLogin();
			$(".warning").remove();
			$("input").removeClass("errorbor");
			if($("#traderName").val()==''){
				warnErrorTips("traderName","traderNameError","搜索内容不允许为空");//文本框ID和提示用语
				return false;
			}
			$("#myform").submit();
			return false;
		})
	})
	
	function selectObj(realTraderType,traderId,traderName){
		if(realTraderType == 1){
			$("#myform").addClass("none");
			$(".cus").addClass("none");
			
		}else{
			$("#myform").addClass("none");
			$(".sup").addClass("none");

			
		}
	}
</script>
<div class="formpublic">
    
	<form action="${pageContext.request.contextPath}/aftersales/order/getAfterTraderListPage.do" id="myform" method="post">	
		<div>
			<ul>
				<li>
					<div class="infor_name">
						<label>对象类型</label>	 
					</div>
					<div class='f_left'>
						<div class='inputfloat'>
							<select id="realTraderType" name="realTraderType" class="input-large">
								<option value="1" <c:if test="${ast.realTraderType eq 1 }">selected="selected"</c:if>>客户</option>
								<option value="2" <c:if test="${ast.realTraderType eq 2 }">selected="selected"</c:if>>供应商</option>
							</select>					
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<label id="trader">
							<c:if test="${ast.realTraderType eq null || ast.realTraderType eq 1 }">客户名称</c:if>
							<c:if test="${ast.realTraderType eq 2 }">供应商名称</c:if>
						</label>	 
					</div>
					<div class='f_left'>
						<div class='inputfloat'>
							<input type="text" name="traderName" id="traderName" value="${ast.traderName}" class="input-large">
							<input type="hidden" name="afterSalesId" value="${ast.afterSalesId}">
							<span class="bg-light-blue bt-bg-style bt-small"  id="search">搜索</span>					
						</div>
						<div id="traderNameError"></div>
					</div>
				</li>
			</ul>
		</div>
	</form>
	<div>
		<table class="table sup <c:if test="${ast.realTraderType eq null || ast.realTraderType eq 1 }">none</c:if>" >
			<thead>
				<tr>
					<th class="wid20">供应商名称</th>
					<th>地区</th>
					<th class="wid10">选择</th>
				</tr>
			</thead>
			<tbody class="employeestate">
				<c:if test="${not empty suplist}">
					<c:forEach items="${suplist}" var="tsv" varStatus="status">
						<tr>
							<td>${tsv.traderSupplierName }</td>
							<td>${tsv.traderSupplierAddress }</td>
							<td>
							<a class='setWidth' 
									href="${pageContext.request.contextPath}/aftersales/order/addAfterTraderPage.do?afterSalesId=${ast.afterSalesId}&traderId=${tsv.traderId}&realTraderType=2">选择</a>
								<%-- <span class="font-blue cursor-pointer" onclick="selectObj(2,${tsv.traderId},'${tsv.traderSupplierName }');">选择</span> --%>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty suplist }">
					<tr>
						<td colspan="3">查询无结果</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		
		<table class="table cus <c:if test="${ast.realTraderType eq 2 }">none</c:if>">
	          <thead>
	              <tr>
					<th class="wid20">客户名称</th>
					<th>地区</th>
					<th class="wid10">选择</th>
				</tr>
	          </thead>
	          <tbody>
				<c:if test="${not empty cuslist}">
					<c:forEach items="${cuslist}" var="tcv" varStatus="status">
						<tr>
							<td class="text-left">${tcv.name }</td>
							<td>${tcv.address }</td>
							<td>
							<a class='setWidth' 
									href="${pageContext.request.contextPath}/aftersales/order/addAfterTraderPage.do?afterSalesId=${ast.afterSalesId}&traderId=${tcv.traderId}&realTraderType=1">选择</a>
								<%-- <span class="font-blue cursor-pointer" onclick="selectObj(1,${traderCustomerVo.traderId},'${traderCustomerVo.name}');">选择</span> --%>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty cuslist}">
					<tr>
						<td colspan="3">查询无结果</td>
					</tr>
				</c:if>
	          </tbody>
	      </table>
		
		<tags:page page="${page}"/>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
