<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单修改详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" >
$(function(){
	var buyorderModifyApplyId = $("#buyorderModifyApplyId").val();
	var	url = page_url+"/order/buyorder/viewModifyApply.do?buyorderModifyApplyId="+buyorderModifyApplyId;
	if($(window.frameElement).attr('src').indexOf("viewModifyApply")<0){
		$(window.frameElement).attr('data-url',url);
	}
});

function validApplyBuyorderUpdate(buyorderModifyApplyId){
	checkLogin();
	layer.confirm('是否确认提交审核？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./saveApplyBuyorderModfiy.do",
				data: {"buyorderModifyApplyId":buyorderModifyApplyId},
				dataType:'json',
				success: function(data){
					if (data.code == 0) {
						window.location.reload();
					} else {
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
	});
}
</script>
	 <div class="content mt10 ">
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">基本信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">修改申请单号</td>
                        <td>${bmav.buyorderModifyApplyNo}</td>
                        <td class="table-smaller">采购订单</td> 
                        <td>
                            ${bmav.buyorderNo}
                        </td>
                        <input type="hidden" id="buyorderModifyApplyId" value="${bmav.buyorderModifyApplyId}"/>
                    </tr>
                    <tr>
                        <td>创建时间</td>
                        <td><date:date value ="${bmav.addTime}"/></td>
                        <td>审核状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${bmav.verifyStatus == null}">待审核</c:when>
								<c:when test="${bmav.verifyStatus eq 0}">审核中</c:when>
								<c:when test="${bmav.verifyStatus eq 1}">审核通过</c:when>
								<c:when test="${bmav.verifyStatus eq 2}">审核不通过</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收货信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收货客户</td>
                        <td>${bmav.takeTraderName}</td>
                        <td class="table-smaller">收货联系人</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${bmav.takeTraderContactName}
                                
                                <c:if test="${bmav.takeTraderContactName ne bmav.oldTakeTraderContactName}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldTakeTraderContactName}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${bmav.takeTraderContactTelephone}
                                
                                <c:if test="${bmav.takeTraderContactTelephone ne bmav.oldTakeTraderContactTelephone}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldTakeTraderContactTelephone}</div>
                                </c:if>
                            </div>
                        </td>
                        <td>手机</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${bmav.takeTraderContactMobile}
                                
                                <c:if test="${bmav.takeTraderContactMobile ne bmav.oldTakeTraderContactMobile}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldTakeTraderContactMobile}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地区</td>
                        <td>
                        	<div class="customername pos_rel">
                                <span>${bmav.takeTraderArea}
                                
                                <c:if test="${bmav.takeTraderArea ne bmav.oldTakeTraderArea}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldTakeTraderArea}</div>
                                </c:if>
                            </div>
                        </td>
                        <td></td>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地址</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${bmav.takeTraderAddress}
                                
                                <c:if test="${bmav.takeTraderAddress ne bmav.oldTakeTraderAddress}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldTakeTraderAddress}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>是否直发</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>
                                	<c:if test="${bmav.deliveryDirect eq 0}">普发</c:if>
                                	<c:if test="${bmav.deliveryDirect eq 1}">直发</c:if>
                                <c:if test="${bmav.deliveryDirect ne bmav.oldDeliveryDirect}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：<c:if test="${bmav.oldDeliveryDirect eq 0}">普发</c:if>
                                												<c:if test="${bmav.oldDeliveryDirect eq 1}">直发</c:if></div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>物流备注</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${bmav.logisticsComments}
                                
                                <c:if test="${bmav.logisticsComments ne bmav.oldLogisticsComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldLogisticsComments}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                    收票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td>发票类型</td>
                        <td>
                        	<c:if test="${bmav.oldInvoiceType != 0}">
	                        	<div class="customername pos_rel">
	                        		<span>
		                        	<c:forEach var="list" items="${invoiceTypes}">
				                    	<c:if test="${bmav.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
				                    </c:forEach>
			                    	<c:if test="${bmav.invoiceType ne bmav.oldInvoiceType}">
			                    		<i class="iconbluesigh ml4 contorlIcon"></i>
		                                <div class="pos_abs customernameshow">原值：
			                                <c:forEach var="list" items="${invoiceTypes}">
						                    	<c:if test="${bmav.oldInvoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
						                    </c:forEach>
		                            	</div>
		                            </c:if>
		                            </span>
	                            </div>
		                    </c:if>
		                    
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>收票备注</td>
                        <td colspan="3">
                        	<div class="customername pos_rel">
                                <span>${bmav.invoiceComments}
                                
                                <c:if test="${bmav.invoiceComments ne bmav.oldInvoiceComments}">
									<i class="iconbluesigh ml4 contorlIcon"></i></span>
	                                <div class="pos_abs customernameshow">原值：${bmav.oldInvoiceComments}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div> 
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">产品信息</div>
            </div>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid2">序号</th>
                     <th class="wid15">产品名称</th>
                     <th class="wid8">品牌</th>
                     <th class="wid8">型号</th>
                     <th class="wid8">单价</th>
                     <th class="wid5">数量</th>
                     <th class="wid8">单位</th>
                     <th class="wid25">采购备注</th>
                    </tr>
                </thead>
                <tbody>
             	<c:forEach var="bgv" items="${bmav.bgvList}" varStatus="staut">
                  <tr>
                      <td>${staut.count}</td>
                      <td class="text-left">
                          <div class="customername pos_rel">
						<span class="font-blue">
							<a class="addtitle" href="javascript:void(0);" 
								tabTitle='{"num":"viewgoods${bgv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}</a>&nbsp;
								<i class="iconbluemouth contorlIcon"></i><br/>
						</span>
                              <c:set var="skuNo" value="${bgv.sku}"></c:set>
                              <%@ include file="../../common/new_sku_common_tip.jsp" %>
						<div>${bgv.sku}</div>
                          </div>
                      </td>
                      <td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
                      <td>${newSkuInfosMap[bgv.sku].MODEL}</td>
                      <td>${bgv.price}</td>
                      <td>${bgv.num}</td>
                      <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
                      <td>
                      	<div class="customername pos_rel">
                             <span>${bgv.newInsideComments}
                      	<c:if test="${bgv.newInsideComments ne bgv.insideComments}">
							<i class="iconbluesigh ml4 contorlIcon"></i></span>
                              <div class="pos_abs customernameshow">原值：${bgv.insideComments}</div>
                        </c:if>
                        </div>
                      </td>
                         
                  </tr>
                 </c:forEach>
             </tbody>
            </table>
        </div>  
        
         <div class="table-buttons">
        	 <input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        	<c:choose>
				<c:when test="${bmav.validStatus eq 0}">
					
					<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:set var="shenhe" value="0"></c:set>
						<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
							<c:if test="${verifyUsernameInfo == curr_user.username}">
								<c:set var="shenhe" value="1"></c:set>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${endStatus == '审核完成'}">
						<!--  <button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="validSaleorder(${saleorder.saleorderId})">订单生效</button> -->
					</c:if>
				
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose> 
        </div> 
	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table">
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
									<c:forEach var="vs" items="${verifyUsersList}" varStatus="status">
									 	<c:if test="${fn:contains(verifyUserList, vs)}">
									 		<span class="font-green">${vs}</span>&nbsp;
									 	</c:if>
									 	<c:if test="${!fn:contains(verifyUserList, vs)}">
									 		<span>${vs}</span>&nbsp;
									 	</c:if>
									 </c:forEach>
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
                    <!-- 查询无结果弹出 -->
           	
           		<c:if test="${empty historicActivityInstance}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
       
                </tbody>
            </table>
           
       			
        	
        </div>
       
    </div>

<%@ include file="../../common/footer.jsp"%>