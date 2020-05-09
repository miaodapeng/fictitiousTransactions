<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
        	<form action="${pageContext.request.contextPath}/el/trader/index.do" method="post" id="search">
        		<ul>
	                <li>
	            		<label class="infor_name">客户名称</label>
	            		<input type="text" class="input-middle" name="traderName" id="traderName" value="${trader.traderName }"/>
            		</li>
           		</ul>
           		<div class="tcenter">
	            	<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
	            	<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./el/trader/addTrader.do?flag=at","title":"新增客户"}'>新增客户</span>
            	</div>
            </form>
        </div>
        <div  class="normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="sorts">序号</th>
                        <th class="wid15">客户名称</th>
                        <th class="wid10">添加时间</th>
						<th class="wid10">归属销售人</th>
                    </tr>
                </thead>
                <tbody class="company">
					<c:if test="${not empty traderList}">
						<c:forEach items="${traderList}" var="trader" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>
									${trader.traderName}
								</td>
								<%--<td>--%>
									<%--${trader.contactName}--%>
								<%--</td>--%>
								<%--<td>--%>
									<%--${trader.contactNumber1}--%>
								<%--</td>--%>
								<%--<td>--%>
									<%--${trader.address}--%>
								<%--</td>--%>
								<td>
									<date:date value="${trader.addTime} " />
								</td>
								<td>
									${trader.ownerName}
								</td>
							</tr>
						</c:forEach>
					</c:if>
                </tbody>
            </table>
        	<c:if test="${empty traderList}">
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
	        <tags:page page="${page}"/>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/posit/index.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>