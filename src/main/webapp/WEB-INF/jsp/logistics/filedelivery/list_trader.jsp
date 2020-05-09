<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content pb20">
    	<div class="searchfunc">	
			<form method="post" id="search" action="./getTraderList.do">
		   		<input type="hidden" name="optType" id="optType" value="${traderCustomerVo.optType}"/>
				<ul>
            		<li>
            			<div class="infor_name">
                          <span>*</span>
                          <label>客户名称</label>
                      	</div>
						<div class='f_left inputfloat'>
						<div>
						<input type="text" class="input-larger" name="searchTraderName" id="searchTraderName" value="${searchTraderName}" placeholder="请输入客户名称">
						<input type="hidden" class="input-larger" name="traderType" value="${traderType}">
						<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchError">搜索</span>
						</div>
						<div id="traderNameWarn"></div>
						</div>
            		</li>
           		</ul>
			</form>
		</div>
        <div id="selectTerminalInfo">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th>客户名称</th>
                        <th>地区</th>
                        <th>创建时间</th>
                        <th>归属销售</th>
                    	<th width="5%">选择</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
        		<c:if test="${not empty traderList}">
        			<c:choose>
					       <c:when test="${traderType==1}">
					       		<c:forEach items="${traderList}" var="list" varStatus="status">
				                    <tr>
				                        <td>${status.count}</td>
				                        <td>${list.traderName}</td>
				                        <td>${list.address}</td>
				                        <td><date:date value ="${list.addTime}"/></td>
										<td>${list.personal}</td>
				                    	<td width="5%" style="text-align:center">
				                    		<c:if test="${list.personal == username || positType == 3}">
				                    		<a href="javaScript:void(0)" onclick="selectTrader(${list.traderId},${traderType},'${list.traderName}')">选择</a>
				                    		</c:if>
				                    	</td>
				                    </tr>
			                	</c:forEach>
					       </c:when>
					       
					       <c:when test="${traderType==2}">
					       		<c:forEach items="${traderList}" var="list" varStatus="status">
				                    <tr>
				                        <td>${status.count}</td>
				                        <td>${list.traderSupplierName}</td>
				                        <td>${list.traderSupplierAddress}</td>
				                        <td><date:date value ="${list.addTime}"/></td>
										<td>${list.personal}</td>
				                    	<td width="5%" style="text-align:center">
				                    		<a href="javaScript:void(0)" onclick="selectTrader(${list.traderId},${traderType},'${list.traderSupplierName}')">选择</a>
				                    	</td>
				                    </tr>
			                	</c:forEach>
					       </c:when>
					</c:choose>
        		</c:if>
                </tbody>
            </table>
			<c:choose>
				<c:when test="${empty traderList && traderType==1}">
	       			<!-- 查询无结果弹出 -->
	           		<div class="noresult">
	           			查询无结果!请使用其他搜索条件<!--或
	           			<a class="addtitle1"
					tabTitle='{"num":"customer","link":"./trader/customer/add.do","title":"新增客户"}'
						href="javascript:void(0)"
						>新增客户</a>-->
	           		</div>
	        	</c:when>
	        	<c:when test="${empty traderList && traderType==2}">
	       			<!-- 查询无结果弹出 -->
	           		<div class="noresult">
	           			查询无结果!请使用其他搜索条件<!-- 或
	           			<a class="addtitle1"
					tabTitle='{"num":"supplier","link":"./trader/supplier/add.do","title":"新增供应商"}'
						href="javascript:void(0)"
						>新增客户</a> -->
	           		</div>
	        	</c:when>
        	</c:choose>
        	<tags:page page="${page}" optpage="n"/>
        </div>
    </div>
    <script type="text/javascript" src='${pageContext.request.contextPath}/static/js/logistics/fileDelivery/list_trader.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

