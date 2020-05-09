
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="终端列表" scope="application" />
<%@ include file="../../common/common.jsp"%>

	<script type="text/javascript"></script>
	<script type="text/javascript" src='<%= basePath %>static/js/customer/list_terminal.js?rnd=<%=Math.random()%>'></script>
	<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
    <div class="content pb20">
    	<div class="searchfunc">	
			<form method="post" id="search" action="./getTerminalList.do">
		   		<input type="hidden" name="optType" id="optType" value="${traderCustomerVo.optType}"/>
				<ul>
            		<li>
						<label class="infor_name">关键词</label>
						<div class=f_left>
							<div class='inputfloat' id="searchError">
								<input type="text" class="input-larger" name="searchTraderName" id="searchTraderName" value="${traderCustomerVo.searchTraderName}" placeholder="请输入客户名称">
								<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" >搜索</span>
							</div>
							
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
                        <th>归属销售</th>
                    	<th width="5%">选择</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
        		<c:if test="${not empty terminalList}">
                	<c:forEach items="${terminalList}" var="list" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${list.traderName}</td>
	                        <td>${list.address}</td>
							<td>${list.personal}</td>
	                    	<td width="5%" style="text-align:center">
	                    		<a href="javaScript:void(0)" onclick="selectTerminal('${list.areaId}','${list.address}','${list.traderId}','${list.traderName}','${list.customerType}','${list.customerTypeStr}')">选择</a>
	                    	</td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
			<c:choose>
				<c:when test="${empty terminalList}">
	       			<!-- 查询无结果弹出 -->
	           		<div class="noresult">查询无结果，请更换关键词重新查询或切换为
	           			<a href="javascript:void(0)" onclick="switchInput()">输入模式</a>
	           		</div>
	        	</c:when>
	        	<c:otherwise>
	        		<a href="javascript:void(0)" onclick="switchInput()">切换为输入模式</a>
	        	</c:otherwise>
        	</c:choose>
        	<tags:page page="${page}" optpage="n"/>
        </div>
        <div id="InputTerminalInfo" class="formpublic formpublic1" style="display:none">
			<ul>
				<li>
					<div class="infor_name">
						<label>终端名称</label>
					</div>
					<div class="f_left">
						<input type="text" placeholder="终端名称" class="input-larger" name="terminalTraderName" id="terminalTraderName" onblur="clearErrMes();">
					</div>
				</li>
				<li>
					<div class="infor_name">
						<label>终端类型</label>
					</div>
					<div class="f_left">
						<select class="input-small f_left" id="terminalTraderType" name="terminalTraderType" onchange="clearErrMes();">
							<option value="">请选择</option>
							<c:forEach var="list" items="${terminalTypeList}" varStatus="status">
								<option value="${list.sysOptionDefinitionId}">${list.title}</option>
							</c:forEach>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name">
					</div>
					<div class="f_left inputfloat">
						切换为<a class="font-blue" href="javascript:void(0)" onclick="switchSelect()">搜索模式</a>
					</div>
				</li>
			</ul>
			 <div class="add-tijiao tcenter">
				<button type="button" class="bt-bg-style bg-deep-green" onclick="saveTerminal()">提交</button>
				<button type="button" class="dele" id="cancle">取消</button>
			</div>
		</div>
    </div>
</body>
</html>

