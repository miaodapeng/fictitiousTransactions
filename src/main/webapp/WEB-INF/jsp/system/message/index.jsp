<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="消息列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
            <form action="${pageContext.request.contextPath}/system/message/index.do" method="post" id="search">
                <ul>
                    <li>
                        <label class="infor_name">消息类型</label>
                        <select class="input-middle" name="category">
                            <option selected="selected " value="-1">全部</option>
                            <c:forEach items="${categoryName}" var="cn">
	                    		<option value="${cn.sysOptionDefinitionId }" 
	                    			<c:if test="${message.category eq cn.sysOptionDefinitionId }">selected="selected"</c:if>>${cn.title}
	                    		</option>	
	                    	</c:forEach>
                        </select>
                    </li>
                    <li>
                        <label class="infor_name">状态</label>
                        <select class="input-middle" name="isView">
                            <option selected="selected" value="-1">全部</option>
                            <option value="1" <c:if test="${message.isView eq 1}">selected="selected"</c:if>>已读</option>
                            <option value="0" <c:if test="${message.isView eq 0}">selected="selected"</c:if>>未读</option>
                        </select>
                    </li>
                </ul>
                <div class="tcenter">
                    <span class="bt-small bg-light-blue bt-bg-style mr10" onclick="search();" id="searchSpan">搜索</span>
                    <span class="bt-small bg-light-blue bt-bg-style " onclick="reset();" id="resetSpan">重置</span>
                </div>
            </form>
        </div>
        <div>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 60px;">序号</th>
                        <th style="width: 150px;">发起人</th>
                        <th style="width: 150px;">消息类型</th>
                        <th style="width: 150px;">消息标题</th>
                        <th>消息内容</th>
                        <th style="width: 130px;">送达时间</th>
                        <th style="width: 60px;">状态</th>
                        <th style="width: 130px;">已读时间</th>
                    </tr>
                </thead>
                <tbody class="company">
                    	<c:if test="${not empty messageList}">
						<c:forEach items="${messageList}" var="messageVo" varStatus="status">
						 <tr>
	                        <td>${status.count}</td>
	                        <td>${messageVo.sourceName}</td>
	                        <td>${messageVo.categoryName}</td>
	                        <td>${messageVo.title}</td>
	                        <c:if test="${messageVo.messageUser.isView eq 0}">
							 <td class="font-blue">
							 <span class="addtitle"
					         tabTitle='{"num":"warehousesout<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"${messageVo.url }","title":"消息详情"}' onclick="read(${messageVo.messageUser.userId},${messageVo.messageUser.messageUserId})">${messageVo.content}</span>
							 </td>
							</c:if>
		                    <c:if test="${messageVo.messageUser.isView eq 1}">
		                     <td class="font-grey9">
		                     <span class="addtitle"
					         tabTitle='{"num":"warehousesout<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"${messageVo.url }","title":"消息详情"}'>${messageVo.content}</span>
		                     </td>
		                    </c:if>
	                        <td><date:date value ="${messageVo.addTime} "/></td>
	                        <td class= ${messageVo.messageUser.isView eq 0?"font-red":"font-green"}>
	                        	<c:if test="${messageVo.messageUser.isView eq 0}">未读</c:if>
								<c:if test="${messageVo.messageUser.isView eq 1}">已读</c:if>
	                        </td>
	                        <td><date:date value ="${messageVo.messageUser.viewTime} "/></td>
	                         </tr>
                        </c:forEach>
						</c:if>
                </tbody>
            </table>
            <c:if test="${empty messageList }">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
		<div class="mt-5">
				<c:if test="${!empty messageList }">
                <div class="f_left">
                    <span>当前${noReadCount}条未读</span>
                    <span class="bt-small bg-light-blue bt-bg-style" onclick="allRead(${userId})">全部标为已读</span>
                </div>
            </c:if>
            <tags:page page="${page}"/>
		</div>
        </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>static/js/message/index.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>
