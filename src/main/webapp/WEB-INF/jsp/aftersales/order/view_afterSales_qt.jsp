<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-其他" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/aftersales/order/viewAfterSalesDetail.do?afterSalesId='+$("#afterSalesId").val();
		if($(window.frameElement).attr('src').indexOf("viewAfterSalesDetail")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
	<div class="main-container">
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   基本信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td>${afterSalesVo.afterSalesNo}</td>
                        <td class="wid20">订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.atferSalesStatus eq 0}">待确认</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 1}">进行中</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 2}">已完结</c:if>
							<c:if test="${afterSalesVo.atferSalesStatus eq 3}">已关闭</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${afterSalesVo.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${afterSalesVo.addTime}"/></td>
                    </tr>
                    <tr>
                        <td>生效状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if>
                        	<c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.validTime}"/></td>
                    </tr>
                    <tr>
                        <td>审核状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.status eq 0}">待审核</c:if>
							<c:if test="${afterSalesVo.status eq 1}">审核中</c:if>
							<c:if test="${afterSalesVo.status eq 2}">审核通过</c:if>
							<c:if test="${afterSalesVo.status eq 3}">审核不通过</c:if>
                        </td>
                        <td>售后类型</td>
                        <td class="warning-color1">${afterSalesVo.typeName}</td>
                    </tr>
                </tbody>
            </table>
        </div>

		 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   售后申请
                </div>
            </div>
            <table class="table">
                <tbody>
                     <tr>
                        <td>详情说明</td>
                        <td colspan="3" class="text-left">${afterSalesVo.comments}</td>
                      
                    </tr>
                </tbody>
            </table>
        </div>
        
       <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后过程
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
	                <div class="title-click nobor  pop-new-data" 
	                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"./addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
	                </div>
	             </c:if>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>沟通时间</th>
                        <th>操作人</th>
                        <th>售后内容</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
	               		<c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi" >
	               			<tr>
		                        <td><date:date value ="${asi.addTime} "/></td>
		                        <td>${asi.optName}</td>
		                        <td>${asi.content}</td>
		                        <td class="caozuo">
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"./editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span></c:if>
		                        </td>
		                    </tr>
	               		</c:forEach>
	               	</c:if>
	               	<c:if test="${empty afterSalesVo.afterSalesRecordVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
             <div class="table-buttons">
             <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
             	<form action="" method="post" id="myform">
             		<input type="hidden" name="afterSalesId" id="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
             		<input type="hidden" name="formToken" value="${formToken}"/>
	                <c:if test="${afterSalesVo.status eq 0 || afterSalesVo.status eq 3}">
	                	<input type="hidden" name="taskId" value="${taskInfo.id == null ?0: taskInfo.id}"/>
             			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="applyAudit();">申请审核</button>
             			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="editAfterSales(3);">编辑</button>
             		</c:if>
             		<c:if test="${afterSalesVo.status ne 1 && afterSalesVo.closeStatus eq 1}">
                	<button type="button" class="bt-bg-style bg-light-orange bt-small" onclick="colse();">关闭订单</button>
                </c:if>
                </form></c:if>
            </div>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${null!=historicActivityInstance}">
                    <c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
                    <c:if test="${not empty  hi.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hi.activityType == 'startEvent'}"> 
							${startUser}
							</c:when>
							<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstance.size() == status.count}">
									${verifyUsers}
								</c:if>
								<c:if test="${historicActivityInstance.size() != status.count}">
									${hi.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hi.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hi.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMap[hi.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstance}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>
		
     </div>   
</body>

</html>