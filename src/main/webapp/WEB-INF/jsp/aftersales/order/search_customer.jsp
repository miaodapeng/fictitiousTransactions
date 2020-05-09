<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索客户" scope="application" />	  
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/search_customer.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips5">
	<ul>
		<li>
		</li>
	</ul>
	<div class="form-tips">
	    <span>*</span>
	    <lable>客户</lable>
	</div>
	<div class="f_left ">
	    <div class="form-blanks mb10">
	    	<form action="${pageContext.request.contextPath}/aftersales/order/getCustomerPage.do" method="post" id="myform">
	    		<input type="text" class="input-middle" name="searchName" id="searchName" value="${searchName}"/>
		      	<span class="bt-small bt-bg-style bg-light-blue" onclick="search();" id="search1">搜索</span>
	    	</form>
	    	<div id="searchNameError"></div>
	  </div>
	  
	</div>
	  <div id="pages">
	      <table class="table">
	          <thead>
	              <tr>
					<th class="wid20">客户名称</th>
					<th>地区</th>
					<th class="wid15">创建时间</th>
					<th>归属销售</th>
					<th class="wid10">选择</th>
				</tr>
	          </thead>
	          <tbody>
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="traderCustomerVo" varStatus="status">
						<tr>
							<td class="text-left">${traderCustomerVo.name }</td>
							<td>${traderCustomerVo.address }</td>
							<td><date:date value ="${traderCustomerVo.addTime} "/></td>
							<td>${traderCustomerVo.personal}</td>
							<td>
								<span class="font-blue cursor-pointer" onclick="selectObj(${traderCustomerVo.traderId},'${traderCustomerVo.name}');">选择</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty list}">
					<tr>
						<td colspan="5">查询无结果</td>
					</tr>
				</c:if>
	          </tbody>
	      </table>
	      <tags:page page="${page}"/>
	    </div>
	
</div>
<%@ include file="../../common/footer.jsp"%>