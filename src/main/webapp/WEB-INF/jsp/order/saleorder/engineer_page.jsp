<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索工程师" scope="application" />	  
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/engineer_page.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips5">
	<ul>
		<li>
		</li>
	</ul>
	<div class="form-tips">
	    <span>*</span>
	    <lable>工程师</lable>
	</div>
	<div class="f_left ">
	    <div class="form-blanks mb10">
	    	<form action="${pageContext.request.contextPath}/aftersales/order/getEngineerPage.do" method="post" id="myform">
	    		<input type="text" class="input-middle" name="searchName" id="searchName" value="${afterSales.searchName}"/>
		      	<span class="bt-small bt-bg-style bg-light-blue" onclick="search();" id="search1">搜索</span>
	    	</form>
	    	<div id="searchNameError"></div>
	  </div>
	  
	</div>
	  <div id="pages">
	      <table class="table">
	          <thead>
	              <tr>
	                  <th class="wid6">序号</th>
	                  <th class="wid10">姓名</th>
	                  <th>地区</th>
	                  <th class="wid12">手机号</th>
	                  <th class="wid8">服务次数</th>
	                  <th>维修产品</th>
	                  <th>备注</th>
	                  <th class="wid6">操作</th>
	              </tr>
	          </thead>
	          <tbody>
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="ev" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td class="text-left">${ev.name }</td>
							<td>${ev.areaStr }</td>
							<td>${ev.mobile}</td>
							<td>${ev.serviceTimes}</td>
							<td>${ev.serviceProducts}</td>
							<td>${ev.comments}</td>
							<td>
								<span class="font-blue cursor-pointer" onclick="selectObj(${ev.engineerId},'${ev.name}',${ev.mobile});">选择</span>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty list}">
					<tr>
						<td colspan="8">查询无结果</td>
					</tr>
				</c:if>
	          </tbody>
	      </table>
	      <tags:page page="${page}"/>
	    </div>
	
</div>
<%@ include file="../../common/footer.jsp"%>