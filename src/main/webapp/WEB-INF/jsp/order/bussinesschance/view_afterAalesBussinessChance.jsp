
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加售后商机" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_afterAalesBussinessChance.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/order/bussinesschance/toAfterSalesDetailPage.do?bussinessChanceId='+$("#bussinessChanceId").val();
		if($(window.frameElement).attr('src').indexOf("toAfterSalesDetailPage")<0){
			$(window.frameElement).attr('data-url',url);
		}
	});
</script>
	<div class="content  mt10 pb0">
        <div class="parts">
            <div class="title-container">
                <div class="table-title ">
                    基本信息
                </div>
                <div class="title-click">
	                <c:if test="${empty bussinessChanceVo.quoteorderNo  && empty bussinessChanceVo.closeReason  && bussinessChanceVo.type == 391}">
	                	 <span class="font-blue" onclick="goUrl('${pageContext.request.contextPath}/order/bussinesschance/toAfterSalesEditPage.do?bussinessChanceId=${bussinessChanceVo.bussinessChanceId}');">编辑</span>
	                </c:if>
	            </div>
              </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                    	<input type="hidden" id="bussinessChanceId" value="${bussinessChanceVo.bussinessChanceId}">
                        <td class="table-smaller">商机编号</td>
                        <td>${bussinessChanceVo.bussinessChanceNo}</td>
                        <td class="table-smaller">商机类型</td>
                        <td>${bussinessChanceVo.typeName} </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${bussinessChanceVo.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${bussinessChanceVo.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <td>商机状态</td>
                        <td>
                        	<span class="font-red">
                        		<c:if test="${bussinessChanceVo.status eq 0}">未处理</c:if>
                        		<c:if test="${bussinessChanceVo.status eq 1}">报价中</c:if>
                        		<c:if test="${bussinessChanceVo.status eq 2}">已报价</c:if>
                        		<c:if test="${bussinessChanceVo.status eq 3}">已订单</c:if>
                        		<c:if test="${bussinessChanceVo.status eq 4}">已关闭</c:if>
                        	</span>
                        </td>
                        <td>商机时间</td>
                        <td><date:date value ="${bussinessChanceVo.receiveTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <td>归属销售</td>
                        <td>${bussinessChanceVo.salerName}</td>
                        <td>分配时间</td>
                        <td><date:date value ="${bussinessChanceVo.assignTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                   
                    <tr>
                        <td>商机来源</td>
                        <td>${bussinessChanceVo.sourceName}</td>
                        <td>询价方式</td>
                        <td>${bussinessChanceVo.communicationName}</td>
                    </tr>
                    <tr>
                        <td>咨询入口</td>
                        <td>${bussinessChanceVo.entranceName}</td>
                        <td>功能</td>
                        <td>${bussinessChanceVo.functionName}</td>
                    </tr>

                    <tr>
                        <td>产品分类</td>
                        <td>${bussinessChanceVo.goodsCategoryName}</td>
                        <td>产品品牌</td>
                        <td>${bussinessChanceVo.goodsBrand}</td>
                    </tr>
                    <tr>
                        <td>产品名称</td>
                        <td>${bussinessChanceVo.goodsName}</td>
                        <td>附件</td>
                        <td> 
                        	<c:if test="${!empty bussinessChanceVo.attachmentUri}">
                        		<a href="http://${bussinessChanceVo.attachmentDomain}${bussinessChanceVo.attachmentUri}" target="_blank">查看</a> 
                        	</c:if>
                        	<c:if test="${empty bussinessChanceVo.attachmentUri}">
                        		 
                        	</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>客户名称</td>
                        <td>${bussinessChanceVo.traderName}</td>
                        <td>地区</td>
                        <td>${bussinessChanceVo.areas}</td>
                    </tr>
                    <tr>
                        <td>联系人</td>
                        <td>${bussinessChanceVo.traderContactName}</td>
                        <td>手机号</td>
                        <td>
                        <c:if test="${not empty bussinessChanceVo.mobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${bussinessChanceVo.mobile}',0,1,1,${bussinessChanceVo.bussinessChanceId},0);"></i>
                        </c:if>
                        ${bussinessChanceVo.mobile}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty bussinessChanceVo.telephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${bussinessChanceVo.telephone}',0,1,1,${bussinessChanceVo.bussinessChanceId},0);"></i>
                        </c:if>
                        ${bussinessChanceVo.telephone}</td>
                        <td>其他联系方式</td>
                        <td>${bussinessChanceVo.otherContact}</td>
                    </tr>
                    <tr>
                        <td>询价产品</td>
                        <td colspan="3">${bussinessChanceVo.content}</td>
                    </tr>
                </tbody>
            </table>
      
    	</div> 
        <div class="parts">
            <div class="title-container">
                <div class="table-title ">
                    客户信息
                </div>
                <!-- 
                <div class="title-click   pop-new-data" layerParams='{"width":"60%","height":"40%","title":"确认客户","link":"popups/confirmcustomer.html"}'>
                    确认客户
                </div> -->
            </div>
            <table class="table">
               
                <thead>
                	<tr>
                        <th  class="table-middle">客户名称</th>
                        <th>地区</th>
                        <th>联系人</th>
                        <th>电话</th>
                        <th>手机</th>
                    </tr>
                 </thead>   
                 <tbody>
                	<c:if test="${bussinessChanceVo.traderId ne null && bussinessChanceVo.traderId ne 0}">
                		 <tr>
	                        <td>
	                           <span class="font-blue">
	                           		<a class="addtitle" href="javascript:void(0);" 
										tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/customer/baseinfo.do?traderId=${bussinessChanceVo.traderId}",
											"title":"客户信息"}'>${bussinessChanceVo.checkTraderName}</a>
	                           </span>
	                        </td>
	                        <td>${bussinessChanceVo.checkTraderArea}</td>
	                        <td>${bussinessChanceVo.checkTraderContactName}</td>
	                        <td>
	                        <c:if test="${not empty bussinessChanceVo.checkTraderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${bussinessChanceVo.checkTraderContactTelephone}',${bussinessChanceVo.traderId},1,1,${bussinessChanceVo.bussinessChanceId},${bussinessChanceVo.traderContactId});"></i>
	                        </c:if>
	                        ${bussinessChanceVo.checkTraderContactTelephone}</td>
	                        <td>
	                        <c:if test="${not empty bussinessChanceVo.checkMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${bussinessChanceVo.checkMobile}',${bussinessChanceVo.traderId},1,1,${bussinessChanceVo.bussinessChanceId},${bussinessChanceVo.traderContactId});"></i>
	                        </c:if>
	                        ${bussinessChanceVo.checkMobile}</td>
	                    </tr>
                	</c:if>
                    <c:if test="${ empty bussinessChanceVo.traderId}">
                    <tr>
                        <td colspan="5">暂无记录</td>
                    </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title ">
                    报价信息
                </div>
                <!-- 
                <div class="title-click   pop-new-data" layerParams='{"width":"60%","height":"40%","title":"新增报价","link":"popups/addquote.html"}'>
                    新增报价
                </div> -->
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                	<tr>
                        <th class="table-smaller10">报价单号</th>
                        <th>报价时间</th>
                        <th>报价金额</th>
                        <th>报价单状态</th>
                        <th>订单号</th>
                        <th>订单时间</th>
                        <th>订单金额</th>
                        <th>订单状态</th>
                    </tr>
                </thead>    
                    <tbody>
                    <c:if test="${ not empty bussinessChanceVo.quoteorderNo}">
                    <tr>
                        <td>
                            <span class="font-blue">
                                    ${bussinessChanceVo.quoteorderNo}
                                </span>
                        </td>
                        <td><date:date value ="${bussinessChanceVo.quoteorderAddTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${bussinessChanceVo.quoteorderTotalAmount}</td>
                        <td>
                        	<c:if test="${bussinessChanceVo.quoteorderStatus eq 0}">跟单中</c:if>
                        	<c:if test="${bussinessChanceVo.quoteorderStatus eq 1}">成单</c:if>
                        	<c:if test="${bussinessChanceVo.quoteorderStatus eq 2}">失单</c:if>
                        </td>
                        <td> <span class="font-blue">
                                    ${bussinessChanceVo.saleorderNo}
                                </span></td>
                        <td><date:date value ="${bussinessChanceVo.saleorderAddTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${bussinessChanceVo.saleorderTotalAmount}</td>
                         <td>
                         	<c:if test="${bussinessChanceVo.saleorderstatus eq 0}">待确认</c:if>
                        	<c:if test="${bussinessChanceVo.saleorderstatus eq 1}">进行中</c:if>
                        	<c:if test="${bussinessChanceVo.saleorderstatus eq 2}">已完结</c:if>
                        	<c:if test="${bussinessChanceVo.saleorderstatus eq 3}">已关闭</c:if>
                        </td>       
                    </tr>
                    </c:if>
                    <c:if test="${ empty bussinessChanceVo.quoteorderNo}">
                    <tr>
                        <td colspan="8">暂无记录</td>
                    </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title ">
                    关闭商机
                </div>
                <div class="title-click">
                <c:if test="${bussinessChanceVo.closeReason eq null }"> 
	                <span class="font-blue pop-new-data" layerParams='{"width":"600px","height":"200px","title":"编辑备注","link":"./editCommentsPage.do?bussinessChanceId=${bussinessChanceVo.bussinessChanceId}&&comments=${bussinessChanceVo.comments }"}'>
	                    	编辑备注
	                </span>
                </c:if>
                <c:if test="${bussinessChanceVo.quoteorderNo eq null && (bussinessChanceVo.salerName eq null || bussinessChanceVo.userId eq curr_user.userId)&& bussinessChanceVo.closeReason eq null}">   
                    <span class="font-red pop-new-data" layerParams='{"width":"600px","height":"250px","title":"关闭商机","link":"./toCloseSalesPage.do?bussinessChanceId=${bussinessChanceVo.bussinessChanceId}"}'>关闭商机</span>
                </c:if>
                </div>
            </div>
            <table class="table">
                 <tbody>
                 	<tr>
                    	<td>关闭原因</td>
                        <td>${bussinessChanceVo.closeReason}</td>
                        <td>关闭时间</td>
                        <td>
                        	<c:if test="${bussinessChanceVo.closeReason ne null}">
                        		<date:date value ="${bussinessChanceVo.modTime}" format="yyyy-MM-dd HH:mm:ss"/>
                        	</c:if>
                        </td>
                    </tr>
                	<tr>
                        <td>备注</td>
                        <td colspan="3" class="font-red">
                        	${bussinessChanceVo.comments}
                        </td>
                    </tr>
                    <tr>
                        <td>申请关闭备注</td>
                        <td colspan="3" >
                        	${bussinessChanceVo.closedComments}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title ">
                    沟通记录
                </div>
                <!-- 
                <div class="title-click   pop-new-data" layerParams='{"width":"60%","height":"40%","title":"新增沟通","link":"popups/addcontacts.html"}'>
                    新增沟通
                </div>
                 -->
            </div>
            <table class="table">
                <thead>
                   <tr>
                        <th class="wid10">沟通时间</th>
                        <th class="">录音</th>
                       <th>录音内容</th>
                        <th class="">联系人</th>
                        <th class="">联系方式</th>
                        <th class="">沟通方式</th>
                        <th class="wid30">沟通内容</th>
                        <th class="">操作人</th>
                        <th class="wid8">下次联系日期</th>
                        <th class="wid15">下次沟通内容</th>
                        <th class="">备注</th>
                        <th class="wid10">创建时间</th>
                        <!-- <th class="wid6">操作</th> -->
                    </tr>
                </thead>
                <tbody>
                 	
                	<c:if test="${not empty communicateList}">
                		<c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
                			<tr>
		                        <td><date:date value ="${communicateRecord.begintime} "/>~<date:date value ="${communicateRecord.endtime}" format="HH:mm:ss"/></td>
		                        <td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
                                <td><c:if test="${not empty communicateRecord.coidUri}">
                                    <c:if test="${communicateRecord.isTranslation eq 1}">
									  <span class="edit-user pop-new-data"
                                            layerParams='{"width":"90%","height":"90%","title":"查看详情","link":"${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewContent.do?communicateRecordId=${communicateRecord.communicateRecordId}"}'>查看</span>
                                    </c:if>
                                    <span class="edit-user"
                                          onclick="playrecord('${communicateRecord.coidUri}');">播放</span>
                                </c:if></td>
		                        <td>${communicateRecord.contactName}</td>
		                        <td>${communicateRecord.phone}</td>
		                        <td>${communicateRecord.communicateModeName}</td>
		                        <td>
		                        	<ul class="communicatecontent ml0">
			                        	<c:if test="${not empty communicateRecord.tag }">
			                        		<c:forEach items="${communicateRecord.tag }" var="tag">
					                            <li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
			                        		</c:forEach>
			                        	</c:if>
		                        	</ul>
		                        </td>
		                        <td><span class="font-red">${communicateRecord.user.username}</span></td>
		                        <c:choose>
			                    	<c:when test="${communicateRecord.isDone == 0 }">
				                   		<td class="font-red">${communicateRecord.nextContactDate }</td>
			                    	</c:when>
			                    	<c:otherwise>
				                    	<td>${communicateRecord.nextContactDate }</td>
			                    	</c:otherwise>
			                    </c:choose>
			                    <td>${communicateRecord.nextContactContent}</td>
		                        <td>${communicateRecord.comments}</td>
		                        <td><date:date value ="${communicateRecord.addTime} "/></td>
		                        <!-- 
		                        <td class="caozuo"><span class="border-blue  addtitle" tabTitle='{"num":"shangji","link":"pages/popups/addcompany.html","title":"编辑沟通记录"}'>编辑</span></td>
		                    	 -->
		                    </tr>
                		</c:forEach>
                	</c:if>
                    <c:if test="${ empty communicateList}">
	                    <tr>
	                        <td colspan="12">暂无记录</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
    </div>
</body>

</html>
