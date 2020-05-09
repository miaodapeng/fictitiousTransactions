<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }经销商列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/account/account_index.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">
            <form action="<%=basePath%>ordergoods/manage/getordergoodsaccount.do?ordergoodsStoreId=2" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">客户名称</label>
                        <input type="text" class="input-middle" name="traderName" value="${ordergoodsStoreAccountVo.traderName }"/>
                    </li>
                    <li>
                        <label class="infor_name">手机号码</label>
                        <input type="text" class="input-middle" name="mobile" value="${ordergoodsStoreAccountVo.mobile }"/>
                    </li>
                    <li>
                        <label class="infor_name">状态</label>
                        <select name="isEnable">
                        	<option value="-1">请选择</option>
                        	<option value="1" <c:if test="${ordergoodsStoreAccountVo.isEnable eq '1' }">selected="selected"</c:if>>有效</option>
                        	<option value="0" <c:if test="${ordergoodsStoreAccountVo.isEnable eq '0' }">selected="selected"</c:if>>无效</option>
                        </select>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
                    <span class="bg-light-blue bt-bg-style bt-small addtitle"
						tabTitle='{"num":"addordergoodsaccount${ordergoodsStore.ordergoodsStoreId }","link":"./ordergoods/manage/addordergoodsaccount.do?ordergoodsStoreId=${ordergoodsStore.ordergoodsStoreId }","title":"添加经销商"}'>添加经销商</span>
                </div>
            </form>
        </div>
        <div class="list-page normal-list-page">
        	<table class="table">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th>客户名称</th>
                        <th>所在地区</th>
                        <th>注册手机号</th>
                        <th class="wid6">状态</th>
                        <th class="wid10">归属</th>
                        <th class="wid30">操作</th>
                    </tr>
                </thead>
                <tbody>
        		<c:if test="${not empty ordergoodsAccountList}">
                	<c:forEach items="${ordergoodsAccountList }" var="list" varStatus="status">
	                    <tr>
	                        <td>${status.count}</td>
	                        <td>${list.traderName}</td>
							<td>${list.area}</td>
	                        <td>${list.mobile}</td>
	                        <td>
	                        <c:if test="${list.isEnable eq 1}"><span class="onstate">有效</span></c:if>
	                        <c:if test="${list.isEnable eq 0}"><span class="offstate">无效</span></c:if>
	                        </td>
	                        <td>${list.owner}</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.isEnable eq 0}">
										<%-- <span class="edit-user"
												onclick="setDisabled(${list.ordergoodsStoreAccountId},1);">启用</span> --%>
									</c:when>
									<c:when test="${list.isEnable eq 1}">
										<%-- <span class="forbid clcforbid"
												onclick="setDisabled(${list.ordergoodsStoreAccountId},0);">禁用</span> --%>
			                        	<span class="edit-user addtitle"
											tabTitle='{"num":"viewordergoodsaccount${ordergoodsStore.ordergoodsStoreId}${list.ordergoodsStoreAccountId}","link":"./ordergoods/manage/viewordergoodsaccount.do?ordergoodsStoreAccountId=${list.ordergoodsStoreAccountId}","title":"查看"}'>查看</span>
			                        	<span class="edit-user addtitle"
											tabTitle='{"num":"editordergoodsaccount${ordergoodsStore.ordergoodsStoreId}${list.ordergoodsStoreAccountId}","link":"./ordergoods/manage/editordergoodsaccount.do?ordergoodsStoreAccountId=${list.ordergoodsStoreAccountId}","title":"编辑"}'>编辑</span>
			                        	<c:if test="${ordergoodsStore.ordergoodsStoreId eq 2}">
			                        	<span class="edit-user addtitle"
											tabTitle='{"num":"viewordergoodsgoodsaccount${ordergoodsStore.ordergoodsStoreId}${list.webAccountId}","link":"./ordergoods/manage/viewordergoodsgoodsaccount.do?ordergoodsStoreId=${list.ordergoodsStoreId}&webAccountId=${list.webAccountId}","title":"查看"}'>产品</span>
			                        	</c:if>
			                        	<span class="edit-user" onclick="resetPassword(${list.ordergoodsStoreAccountId});">重置密码</span>
		                        	</c:when>
	                        	</c:choose>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty ordergoodsAccountList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！</div>
        	</c:if>
		    <div class="parts">
		    	<tags:page page="${page}" />
		    </div>
        </div>
    </div>
<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/account/account_index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

