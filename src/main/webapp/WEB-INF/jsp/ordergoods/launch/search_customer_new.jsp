<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="搜索客户" scope="application" />	  
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/ordergoods/launch/search_customer.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
		<div class="controlled" id="goodsListDiv">
			<!-- 搜索表格出来 -->
			<ul class="searchTable">
				<li>
					<form method="post" id="myform" action="${pageContext.request.contextPath}/ordergoods/manage/searchtradercustomernew.do?ordergoodsStoreId=${ordergoodsStoreId }">
					<div class="infor_name ">
							<span>*</span> <label>客户名称</label>
						</div>
						<div class="f_left table-larger">
							<div class="">
								<input type="text" class="input-larger mr5"
									placeholder="请输入客户名称" id="searchName"
									name="searchName" value="${searchName}"> 
								<span
									class="bt-bg-style bt-small bg-light-blue"
									onclick="search();" >搜索</span>
							</div>
						<div id="searchNameError"></div>
						</div>
					</form>
				</li>
			</ul>
		</div>
		  <div>
		      <table class="table table-bordered table-striped table-condensed table-centered mb10">
		          <thead>
		              <tr>
						<th>客户名称</th>
						<th>地区</th>
						<th>创建时间</th>
						<th>归属销售</th>
						<th>选择</th>
					</tr>
		          </thead>
		          <tbody>
					<c:if test="${not empty list}">
						<c:forEach items="${list}" var="trader" varStatus="status">
							<tr>
								<td class="text-left">${trader.traderName }</td>
								<td>${trader.area }</td>
								<td><date:date value ="${trader.addTime} "/></td>
								<td>${trader.owner}</td>
								<td>
									<c:if test="${trader.openStore ne 1 }">
										<span class="font-blue cursor-pointer" onclick="selectObj(${trader.traderCustomerId},'${trader.traderName}',${trader.traderId},'${trader.area }');">选择</span>
									</c:if>
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
		    </div>
		    <div>
				<tags:page page="${page}" optpage="n" />
			</div>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>