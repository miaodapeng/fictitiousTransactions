<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="沟通及审核信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
<div class="content">
 	<div class="customer">
       <ul>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/viewNew.do?saleorderId=${saleorder.saleorderId }&companyId=${saleorder.companyId }">订单及基本信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getSaleAndExpress.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">交易及物流信息</a>
            </li>
            <li>
                <a class="customer-color"  href="${pageContext.request.contextPath}/order/saleorder/getContactAndCheckInfo.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">沟通及审核信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/order/saleorder/getAttachmentAndInstance.do?saleorderId=${saleorder.saleorderId }&status=${saleorder.status}&traderId=${saleorder.traderId}&advancePurchaseComments=${saleorder.advancePurchaseComments}&lockedStatus=${saleorder.lockedStatus}&invoiceComments=${saleorder.invoiceComments}&validStatus=${saleorder.validStatus}&invoiceMethod=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&deliveryStatus=${saleorder.deliveryStatus}&deliveryDirect=${saleorder.deliveryDirect}&purchase=${saleorder.purchase}&invoiceStatus=${saleorder.invoiceStatus}&paymentStatus=${saleorder.paymentStatus}&isCloseSale=${saleorder.isCloseSale}&companyId=${saleorder.companyId }&isOpenInvoice=${saleorder.isOpenInvoice}">合同及售后</a>
            </li>
        </ul>
  	</div>
   	<div class="parts">
   		<div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					沟通记录
                </div>
                <c:if test="${saleorder.status != 3}">
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录","link":"./addComrecord.do?saleorderId=${saleorder.saleorderId}&traderId=${saleorder.traderId}"}'>
					新增
                </div>
                </c:if>
		            </div>
		            <table class="table">
		                <thead>
		            	    <tr>
		                        <th class="wid9">沟通时间</th>
		                        <th class="wid8">录音</th>
		                        <th class="wid9">联系人</th>
		                        <th class="wid11">联系方式</th>
		                        <th class="wid9">沟通方式</th>
		                        <th class="wid9">沟通目的</th>
		                        <th>沟通内容</th>
		                        <th class="wid11">操作人</th>
		                        <th class="wid9">下次联系日期</th>
		                        <th>下次沟通内容</th>
		                        <th>备注</th>
		                        <!-- <th>创建时间</th> -->
		                        <th class="wid9">操作</th>
		                    </tr>
		            </thead>
		                <tbody>
		               
		                    <c:forEach var="list" items="${communicateList}" varStatus="status">
								<tr>
									<td><date:date value ="${list.begintime}" format="yyyy-MM-dd"/><br>
										<date:date value ="${list.begintime}" format="HH:mm"/>~<date:date value ="${list.endtime}" format="HH:mm"/>
									</td>
									<td><c:if test="${not empty list.coidUri }">${list.communicateRecordId}</c:if></td>
									<td>${list.contactName}</td>
									<td>${list.phone}</td>
									<td>${list.communicateModeName}</td>
									<td>${list.communicateGoalName}</td>
									<td>
										<ul class="communicatecontent ml0">
											<c:if test="${not empty list.tag }">
												<c:forEach items="${list.tag }" var="tag">
													<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
												</c:forEach>
											</c:if>
										</ul>
									</td>
									<td>${list.user.username}</td>
									<c:choose>
				                    	<c:when test="${list.isDone == 0 }">
					                   		<td class="font-red">${list.nextContactDate }</td>
				                    	</c:when>
				                    	<c:otherwise>
					                    	<td>${list.nextContactDate }</td>
				                    	</c:otherwise>
				                    </c:choose>
									<td>${list.nextContactContent}</td>
									<td>${list.comments}</td>
									<%-- <td><date:date value ="${list.addTime}"/></td> --%>
									<td class="caozuo">
										<c:if test="${saleorder.status != 3}">
				                        <span class="border-blue pop-new-data" layerParams='{"width":"850px","height":"460px","title":"编辑沟通记录","link":"./editCommunicate.do?communicateRecordId=${list.communicateRecordId}&traderId=${saleorder.traderId}"}'>编辑</span>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							  <c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
		           			<td colspan="12">暂无沟通记录。</td>
		           		</tr>
		        	</c:if>
                </tbody>
            </table>
        	<div class="clear"></div>
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
								
									 <c:if test="${empty verifyUsersList && empty hi.assignee}">
										${verifyUsers}
									</c:if>
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
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    订单修改申请
                </div>
            </div>
            <table class="table">
                <thead>
            	    <tr>
                        <th>订单修改申请单</th>
                        <th>申请人</th>
                        <th>审核状态</th>
                    </tr>
            	</thead>
                <tbody>
                	<c:forEach var="list" items="${saleorderModifyApplyList}" varStatus="num3">
						<tr>
							<td>
							<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleordermodifyapply${list.saleorderModifyApplyId}","link":"./order/saleorder/viewModifyApply.do?saleorderModifyApplyId=${list.saleorderModifyApplyId}&saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderModifyApplyNo}</a>
	                        </td>
							<td>${list.createName}</td>
							<td>
								<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
		                    </td>
						</tr>
					</c:forEach>
					<c:if test="${empty saleorderModifyApplyList}">
						<tr>
							<td colspan="3">暂无订单修改申请。</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    提前采购申请
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
                 <c:if test="${null!=historicActivityInstanceEarly}">
                    <c:forEach var="hie" items="${historicActivityInstanceEarly}" varStatus="status">
                    <c:if test="${not empty  hie.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hie.activityType == 'startEvent'}"> 
							${startUserEarly}
							</c:when>
							<c:when test="${hie.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceEarly.size() == status.count}">
									 <c:forEach var="vse" items="${verifyUsersListEarly}" varStatus="status">
									 	<c:if test="${fn:contains(verifyUserListEarly, vse)}">
									 		<span class="font-green">${vse}</span>&nbsp;
									 	</c:if>
									 	<c:if test="${!fn:contains(verifyUserListEarly, vse)}">
									 		<span>${vse}</span>&nbsp;
									 	</c:if>
									 </c:forEach>
									 
									 <c:if test="${empty verifyUsersListEarly && empty hi.assignee}">
										${verifyUsersEarly}
									</c:if>
								</c:if>
								<c:if test="${historicActivityInstanceEarly.size() != status.count}">
									${hie.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hie.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hie.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hie.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hie.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td>
                        	<c:choose>
	                        	<c:when test="${hie.activityName == '申请人'}"> 
									${saleorder.advancePurchaseComments}
								</c:when>
								<c:otherwise>   
									<span class="font-red">${commentMapEarly[hie.taskId]}</span>
								</c:otherwise>
                        	</c:choose>
                        </td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                  <!-- 查询无结果弹出 -->
           	
           			<c:if test="${empty historicActivityInstanceEarly}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
        
                </tbody>
            </table>
        </div>
         <c:if test="${saleorder.status == 2}">
	        <div class="tcenter mt-5 mb15">
	        	<%-- <input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
	        	<span class="bg-light-green bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }",
							"link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&
							comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span> --%>
				<c:if test="${saleorder.companyId != 1}">
				<span class="bg-light-green bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }",
					"link":"./order/saleorder/printSaleOrder.do?saleorderId=${saleorder.saleorderId}&
					comment=${saleorder.invoiceComments}","title":"打印订单"}'>打印订单</span>
				</c:if>
				<!-- 未锁定 -->
				<%-- <c:if test="${saleorder.lockedStatus eq 0 and saleorder.validStatus eq 1}">
					<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
					<c:if test="${saleorder.isOpenInvoice eq 1}">
						<button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data mr10" 
						layerParams='{"width":"600px","height":"300px","title":"申请开票",
						"link":"../../finance/invoice/openInvoiceApply.do?relatedId=${saleorder.saleorderId}&
						comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}'>申请开票</button>
					</c:if>
					<c:if test="${saleorder.isOpenInvoice eq 3}">
			            <button type="button" class="bt-bg-style bg-light-orange bt-small  pop-new-data mr10" 
			            layerParams='{"width":"600px","height":"350px","title":"申请提前开票",
			            "link":"../../finance/invoice/advanceOpenInvoiceApply.do?relatedId=${saleorder.saleorderId}&
			            comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&
			            invoiceType=${saleorder.invoiceType}"}' >申请提前开票</button>
					</c:if>
					<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
			            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
					</c:if>
				</c:if> --%>
	       </div>
        </c:if>
        
        <c:set var="isNotDelPriceZero" value="0"></c:set>
        <c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
        	<c:if test="${list.isDelete eq 0}">
        		<c:if test="${list.price == '0.00'}">
					<c:set var="isNotDelPriceZero" value="1"></c:set>
				</c:if>
        	</c:if>
        </c:forEach>
        <input type="hidden" value="${isNotDelPriceZero}" id="isNotDelPriceZero">
        <c:if test="${saleorder.status != 3 &&  saleorder.status != 2}"><!-- 订单状态为未关闭 -->
        <div class="tcenter mt-5 mb15">
        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        	<c:choose>
				<c:when test="${saleorder.validStatus eq 0}">
					<c:if test="${(null==taskInfo and null == taskInfo.getProcessInstanceId() and endStatus != '审核完成')or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10" 
						onclick="applyValidSaleorder(${saleorder.saleorderId},${taskInfo.id == null ?0: taskInfo.id},${isNotDelPriceZero})">申请审核</button>
						<%-- <button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" 
						tabTitle='{"num":"order_saleorder_edit<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"order/saleorder/edit.do?saleorderId=${saleorder.saleorderId}","title":"编辑订单"}'>编辑订单</button> --%>
					</c:if>
					<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:set var="shenhe" value="0"></c:set>
						<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
							<c:if test="${verifyUsernameInfo == curr_user.username}">
								<c:set var="shenhe" value="1"></c:set>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name != '产品线归属人审核'}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
							</c:when>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="checkCostPrice(this)" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
							
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:otherwise>
					<%-- <!-- button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="noValidSaleorder(${saleorder.saleorderId})">撤销生效</button-->
					<span class="bg-light-green bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span> --%>
					<c:if test="${saleorder.companyId != 1}">
					<span class="bg-light-green bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }","link":"./order/saleorder/printSaleOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印订单"}'>打印订单</span>
					</c:if>
					
					<!-- 未锁定 -->
					<c:if test="${saleorder.lockedStatus eq 0}">
						<%-- <c:if test="${customer.amount ne '0.00'}">
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"余额支付","link":"./initBalancePayment.do?saleorderId=${saleorder.saleorderId}"}'>余额支付</button>
						</c:if>
						<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
						<c:if test="${saleorder.isOpenInvoice eq 1}">
				            <button type="button" class="bt-bg-style bg-light-green bt-small pop-new-data mr10" layerParams='{"width":"600px","height":"300px","title":"申请开票","link":"../../finance/invoice/openInvoiceApply.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}'>申请开票</button>
						</c:if>
						<c:if test="${saleorder.isOpenInvoice eq 3}">
				            <button type="button" class="bt-bg-style bg-light-orange bt-small  pop-new-data mr10" layerParams='{"width":"600px","height":"350px","title":"申请提前开票","link":"../../finance/invoice/advanceOpenInvoiceApply.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}"}' >申请提前开票</button>
						</c:if>
						<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
				            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
						</c:if> --%>
						<!-- 	1、订单状态为进行中，必要条件；
								2、订单商品（不含有特殊类别商品如运费等）数量>0，必要条件；
								3、订单无异常状态（锁定等），必要条件；
								4、订单商品数量>已采购数量 && 订单商品数量>已发货数量，必要条件；
								5、订单发货状态不是已发货，必要条件；
								6、已到款额（不分交易方式）<预付款；
								7、销售订单含有直发商品时，不允许申请提前采购；
						 -->
						<c:if test="${saleorder.status eq 1 && saleorder.lockedStatus eq 0 && saleorder.deliveryStatus ne 2 && saleorder.deliveryDirect eq 0 && saleorder.purchase eq 1 && ((null==taskInfoEarly and null == taskInfoEarly.processInstanceId and endStatusEarly != '审核完成')or (null!=taskInfoEarly and taskInfoEarly.assignee==null and empty candidateUserMapEarly[taskInfoEarly.id]))}">
			           		<button type="button" class="bt-bg-style bg-light-orange bt-small pop-new-data mr10" 
			           				layerParams='{"width":"600px","height":"200px","title":"申请提前采购","link":"./applyPurchasePage.do?saleorderId=${saleorder.saleorderId}&taskId=${taskInfoEarly.id == null ?0: taskInfoEarly.id}"}'" >申请提前采购</button>
			            </c:if>
			            
			           <%--  <c:if test="${saleorder.deliveryDirect == 1}" >
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"confirm_arrival<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/saleorder/confirmArrivalInit.do?saleorderId=${saleorder.saleorderId}","title":"确认收货"}'>确认收货</button>
						</c:if> --%>
						
						<c:if test="${saleorder.status == 1 && saleorder.lockedStatus == 0}" >
			            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"modify_apply<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/saleorder/modifyApplyInit.do?saleorderId=${saleorder.saleorderId}","title":"申请修改"}'>申请修改</button>
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>
            <%-- <c:if test="${((saleorder.status == 0 or saleorder.status == 1) and saleorder.invoiceStatus == 0 and saleorder.deliveryStatus == 0 and saleorder.paymentStatus == 0 and saleorder.lockedStatus eq 0) || (saleorder.isCloseSale eq 1)}">
            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeSaleorder(${saleorder.saleorderId})">关闭订单</button>
            </c:if> --%>
        </div>
        
         <c:if test="${(null!=taskInfoEarly and null!=taskInfoEarly.processInstanceId and null!=taskInfoEarly.assignee) or !empty candidateUserMapEarly[taskInfoEarly.id]}">
			<div class="table-buttons">	
			<c:set var="shenhe" value="0"></c:set>
			<c:forEach items="${verifyUserListEarly}" var="verifyUsernameInfo">
				<c:if test="${verifyUsernameInfo == curr_user.username}">
					<c:set var="shenhe" value="1"></c:set>
				</c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${(taskInfoEarly.assignee == curr_user.username or candidateUserMapEarly['belong']) and shenhe!=1}">
				<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoEarly.id}&pass=true&type=1"}'>审核通过</button>
				<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoEarly.id}&pass=false&type=1"}'>审核不通过</button>
				</c:when>
				<c:otherwise>
      				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请提前采购</button>
				</c:otherwise>
			</c:choose>
			</div>
		</c:if>
        </c:if>
   </div>
   <input type="hidden" name="formToken" value="${formToken}"/>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>