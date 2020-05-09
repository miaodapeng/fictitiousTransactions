<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="文件寄送详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="pt10 content">
	<!-- ----------------------------------客户信息 ------------------------------------- -->
	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">客户信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">客户名称</td>
						<td>
							${fileDelivery.traderName}
						</td>
						<td class="table-smaller">客户类别</td>
						<td>
							<c:choose>
					       <c:when test="${fileDelivery.traderType == 1}">
					       		客户
					       </c:when>
					       <c:when test="${fileDelivery.traderType == 2}">
					       		供应商
					       </c:when>
					      </c:choose>
						</td>
					</tr>
					<tr>
						<td>客户性质</td>
						<td>
						<c:if test="${fileDelivery.traderType eq 1}">
							<c:if test="${traderCustomerInfo.customerNature eq 465 }">分销</c:if>
							<c:if test="${traderCustomerInfo.customerNature eq 466 }">终端</c:if>
						</c:if>
						</td>
						<td>合作次数</td>
						<td>${traderCustomerInfo.buyCount}</td>
					</tr>
					<tr>
						<td>客户归属</td>
						<td>${traderCustomerInfo.personal}</td>
						<td>联系人</td>
						<td>${fileDelivery.traderContactName}</td>
					</tr>
					<tr>
						<td>联系人电话</td>
						<td>${fileDelivery.telephone}</td>
						<td>联系人手机</td>
						<td>${fileDelivery.mobile}</td>
					</tr>
					<tr>
						<td>收件地区</td>
						<td>${fileDelivery.area}</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>收件地址</td>
						<td colspan="3">${fileDelivery.address}--${fileDelivery.traderAddressId}</td>
					</tr>
				</tbody>
			</table>
	</div>
	<!-- ----------------------------------客户信息 ------------------------------------- -->
	
	<!-- ----------------------------------申请信息------------------------------------- -->
	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">申请信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">申请人</td>
						<td>${userInfo.username }</td>
						<td class="table-smaller">申请时间</td>
						<td><date:date value="${fileDelivery.addTime}" /></td>
					</tr>
					<tr>
						<td>寄送形式</td>
						<td><font color="red">${fileDelivery.sendTypeName}</font></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>寄送内容</td>
						<td colspan="3"><font color="red">${fileDelivery.content}</font></td>
					</tr>
				</tbody>
			</table>
	</div>
	<!-- ----------------------------------申请信息 ------------------------------------- -->
	
	<!-- ----------------------------------审核记录 ------------------------------------- -->
	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">审核记录</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
					<thead>
					<tr>
						<th class="table-smaller">操作人</th>
						<th class="table-smaller">操作时间</th>
						<th class="table-smaller">操作事项</th>
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
					<c:if test="${fileDelivery.isClosed eq 1 }">
						<tr>
							<td>${fileDelivery.updaterName}</td>
							<td><date:date value="${fileDelivery.modTime}" /></td>
							<td>关闭</td>
							<td>关闭原因:${fileDelivery.closedComments}</td>
						</tr>
					</c:if>
                    <c:if test="${null==historicActivityInstance}">
       					<!-- 查询无结果弹出 -->
       					<tr>
       						<td colspan=4>暂无审核记录。</td>
       					</tr>
           				
        			</c:if>
				</tbody>
			</table>
			
        	<div class="clear"></div>
	</div>
	<!-- ----------------------------------审核记录 ------------------------------------- -->
	
	<!-- ----------------------------------物流信息 ------------------------------------- -->
	<c:if test="${expressId == null and expressDetailId == null and !empty(expressInfo)}">
	<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">物流信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
					<thead>
					<tr>
						<th class="table-smaller">寄送状态</th>
						<th class="table-smaller">寄送时间</th>
						<th class="table-smaller">快递</th>
						<th class="table-smaller">快递单号</th>
						<th class="table-smaller">计重数量</th>
						<th class="table-smaller">费用</th>
						<th class="table-smaller">操作人</th>
						<th class="table-smaller">快递状态</th>
						<th class="table-smaller">备注</th>
						<th class="table-smaller">操作</th>
					</tr>
					</thead>
					<c:if test="${fileDelivery.deliveryStatus == 1}">
				<tbody>
					<c:forEach var="list" items="${expressInfo}" varStatus="num">
					<tr>
						<td>
						<c:choose>
							<c:when test="${fileDelivery.deliveryStatus == 0}">
								未寄送
							</c:when>
							<c:when test="${fileDelivery.deliveryStatus == 1}">
								已寄送
							</c:when>
						</c:choose>
						</td>
						<td>
							<date:date value ="${fileDelivery.deliveryTime}"/>
						</td>
						<td>${list.logisticsName}</td>
						<td>${list.logisticsNo}</td>
						<td>${list.expressDetail[0].num}</td>
						<td><font color="red">${list.expressDetail[0].amount}</font></td>
						<td>${dedeliveryUserInfo.username}</td>
						<td>
							<c:if test="${list.arrivalStatus == 0}">
								未收货
							</c:if>
							<c:if test="${list.arrivalStatus == 1}">
								部分收货
							</c:if>
							<c:if test="${list.arrivalStatus == 2}">
								全部收货
							</c:if>
						</td>
						<td>${list.logisticsComments}</td>
						<td class="caozuo">
						<shiro:hasPermission name="/logistics/filedelivery/addExpress.do">
							<a class="bt-smaller bt-border-style border-blue " onclick="editExpress(${fileDelivery.fileDeliveryId},${list.expressDetail[0].expressId},${list.expressDetail[0].expressDetailId})">编辑</a>
						</shiro:hasPermission>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</c:if>
			</table>
			<c:if test="${empty expressInfo}">
				<div class="noresult">尚未寄送，无信息</div>
			</c:if>
	</div>
	</c:if>
	<!-- ----------------------------------物流信息 ------------------------------------- -->
	<input type="hidden" id="companyId" value="${fileDelivery.companyId }">
	<c:if test="${(fileDelivery.deliveryStatus == 0 and fileDelivery.verifyStatus == 2  and fileDelivery.isClosed eq 0 ) or (expressId !=null and expressDetailId !=null and fileDelivery.isClosed eq 0 )}">
	<shiro:hasPermission name="/logistics/filedelivery/addExpress.do">
			<!-- ----------------------------------物流信息 ------------------------------------- -->
			<div class="">
		            <div class="title-container">
		                <div class="table-title nobor">
		                    	物流信息
		                </div>
		            </div>
		            <table class="table">
		                <tbody>
		                    <tr>
		                        <td>
		                            <div class="form-list">
		                            <form method="post" id="addExpressForm" action="${pageContext.request.contextPath}/logistics/filedelivery/saveExpress.do">
		                               
		                               <c:choose>
		                                <c:when test="${fileDelivery.sendType == 489}">
		                                
		                                	<c:if test="${empty expressInfo}">
		                                	<ul>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>快递</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <ul>
		                                                       <c:forEach var="list" items="${logisticsList}" varStatus="num">
			                                                       	<c:if test="${list.isEnable == 1}">
									                            		<li>
									                            			<input type="radio" value="${list.logisticsId}" onclick="changeFree(this, ${regionId})" name="logisticsId" alt="${list.name}" <c:if test="${list.logisticsId == 2}">checked</c:if>>
									                                    	<label>${list.name}</label>
									                                	</li>
									                                </c:if>
								                            	</c:forEach>
		                                                    </ul>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span id="lno">*</span>
		                                                <lable>快递单号</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>计重数量</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                	<!-- 默认是1 -->
		                                                    <input type="text" class="input-middle" name="num" id="num" value="1"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>费用</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-middle" name="amount" id="amount" value="${deliverFree }"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <lable>物流备注</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments"/>
		                                                	<input type="hidden" name="deliveryStatus" value="1"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                    </ul>
		                                    </c:if>
		                                    
		                                    <c:if test="${!empty expressInfo}">
		                                    <c:forEach var="list" items="${expressInfo}" varStatus="num">
		                                    	<ul>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>快递</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <ul>
		                                                       <c:forEach var="list_l" items="${logisticsList}" varStatus="num">
		                                                       	<c:if test="${list_l.isEnable == 1}">
								                            		<li>
								                            			<c:choose>
							                            					<%-- 如果是顺丰速运，选中 --%>
							                            					<c:when test="${!empty list.logisticsId && list.logisticsId == 5}">
							                            						<input type="radio" value="${list_l.logisticsId}" onclick="changeFree(this, ${regionId})" name="logisticsId" alt="${list_l.name}" <c:if test="${5 == list_l.logisticsId}">checked</c:if>>
							                            					</c:when>
							                            					<%-- 如果不是顺丰速运，其他的都选中中通 --%>
							                            					<c:otherwise>
							                            						<input type="radio" value="${list_l.logisticsId}" onclick="changeFree(this, ${regionId})" name="logisticsId" alt="${list_l.name}" <c:if test="${2 == list_l.logisticsId}">checked</c:if>>
							                            					</c:otherwise>
							                            				</c:choose>
								                                    	<label>${list_l.name}</label>
								                                	</li>
								                                </c:if>
								                            	</c:forEach>
		                                                    </ul>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span id="lno">*</span>
		                                                <lable>快递单号</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-middle" name="logisticsNo" id="logisticsNo" value="${list.logisticsNo}"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>计重数量</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-middle" name="num" id="num" value="${list.expressDetail[0].num}"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>费用</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-middle" name="amount" id="amount" value="${list.expressDetail[0].amount}"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        <li>
		                                            <div class="form-tips">
		                                                <lable>物流备注</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <input type="text" class="input-largest" name="logisticsComments" id="logisticsComments" value="${list.logisticsComments}"/>
		                                                	<input type="hidden" name="deliveryStatus" value="1"/>
		                                                </div>
		                                            </div>
		                                        </li>
		                                    </ul>
		                                    </c:forEach>
		                                    </c:if>
		                                </c:when>
		                                
		                                
		                                <c:when test="${fileDelivery.sendType != 489}">
		                                    <ul>
		                                        <li>
		                                            <div class="form-tips">
		                                                <span>*</span>
		                                                <lable>确认结果</lable>
		                                            </div>
		                                            <div class="f_left ">
		                                                <div class="form-blanks">
		                                                    <ul>
							                            		<li>
							                                    	<input type="radio" value="1" name="deliveryStatus" checked>
							                                    	<label>已寄送</label>
							                                	</li>
		                                                    </ul>
		                                                </div>
		                                            </div>
		                                        </li>
		                                        
		                                    </ul>
		                                   </c:when>
		                                   </c:choose>
		                                  
		                                    <div class="add-tijiao">
		                                    	<input type="hidden" id="sendType" value="${fileDelivery.sendType}"/>
		                                    	<input type="hidden" name="relatedId" value="${fileDelivery.fileDeliveryId}"/>
		                                    	<input type="hidden" name="expressId" value="${expressId}"/>
		                                    	<input type="hidden" name="expressDetailId" value="${expressDetailId}"/>
		                                        <button type="submit"  class="bt-bg-style bg-deep-green">提交</button>
		                                    </div>
		                                </form>
		                            </div>
		                        </td>
		                    </tr>
		                </tbody>
		            </table>
		        </div>
			<!-- ----------------------------------物流信息 ------------------------------------- -->
			</shiro:hasPermission>
	</c:if>
	<shiro:hasPermission name="/logistics/filedelivery/addExpress.do">
	<c:if test="${fileDelivery.verifyStatus == 2 and fileDelivery.deliveryStatus == 1 and fileDelivery.sendType == 489and expressId == null and expressDetailId == null}">
	<div class="add-tijiao tcenter">
	    <c:if test="${expressName != '中通快递'}">
	    <button type="submit" onclick="printview('${expressName}','1',${epInfo.relatedId},-1,'',${expressInfo[0].expressId},'','',0)" class="mt-5 mb15">打印快递单</button>
	    </c:if>
	    <c:if test="${expressName == '中通快递'}">
	    <button type="submit" onclick="printview('${expressName}','1',${epInfo.relatedId},-1,'',${expressInfo[0].expressId},'','',1)" class="mt-5 mb15">打印快递单1</button>
        <button type="submit" onclick="printview('${expressName}','1',${epInfo.relatedId},-1,'',${expressInfo[0].expressId},'','',2)" class="mt-5 mb15">打印快递单2</button>
	    </c:if>
   	</div>
   	</c:if>
   	</shiro:hasPermission>
	<c:if test="${fileDelivery.verifyStatus != 2 and fileDelivery.isClosed eq 0}">
		<div class="add-tijiao tcenter mt-5">
			<c:choose>
				<c:when test="${fileDelivery.verifyStatus != 2}">
					<c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId() and endStatus != '审核完成')or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidSaleorder(${fileDelivery.fileDeliveryId},${taskInfo.id == null ?0: taskInfo.id})">申请审核</button>
					</c:if>
					<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:choose>
							<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
								<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" 
									layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true"}'>审核通过</button>
								<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data"
									layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10 mt-5">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	
	<c:if test="${ fileDelivery.isClosed eq 1 }">
		<div class="add-tijiao tcenter mt-5">
			<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10 mt-5">已关闭</button>
		</div>
	</c:if>
	
	<input type="hidden" id="companyId" value="${fileDelivery.companyId}">
	<input type="hidden" name="formToken" value="${formToken}"/>
	 <script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/fileDelivery/view_filedelivery.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>