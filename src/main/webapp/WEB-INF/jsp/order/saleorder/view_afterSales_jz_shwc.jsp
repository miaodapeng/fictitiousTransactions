<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-技术咨询-审核完成" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/view_afterSales.js?rnd=<%=Math.random()%>'></script>
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
                    <tr>
                    	<td>完结/关闭人员</td>
                    	<td colspan="3" class="text-left">${afterSalesVo.afterSalesStatusUserName }</td>
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
                        <td class="wid20">联系人</td>
                        <td>${afterSalesVo.traderContactName}</td>
                        <td>电话</td>
                        <td>
                        	${afterSalesVo.traderContactTelephone}
                        	<c:if test="${not empty afterSalesVo.traderContactTelephone}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactTelephone}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>手机</td>
                        <td>
	                        ${afterSalesVo.traderContactMobile}
	                        <c:if test="${not empty afterSalesVo.traderContactMobile}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactMobile}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
		                    </c:if>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                     <tr>
                        <td>详情说明</td>
                        <td colspan="3" class="text-left">${afterSalesVo.comments}</td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" class="text-left">
                            <%@ include file="view_afterSales_files.jsp"%>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   所属订单
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">订单号</td>
                        <td >
                        	<div class="customername pos_rel">
                               <span class="brand-color1 addtitle" style="float:none;" tabTitle='{"num":"viewsaleorder${afterSalesVo.orderNo}","title":"订单信息",
                               		"link":"./order/saleorder/view.do?saleorderId=${afterSalesVo.orderId}"}'>${afterSalesVo.orderNo}</span><i class="iconbluemouth"></i>
                               <div class="pos_abs customernameshow" style="display: none;">
                                        付款状态：<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
						<c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if><br> 
                                        发货状态：<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
						<c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if><br>
                                        开票状态：<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
						<c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if><br>
                                        收货状态：<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
						<c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
                               </div>
                            </div>
                        </td>
                        <td class="wid20">订单金额</td>
                        <td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
                    </tr>
                    <tr>
                        <td>部门</td>
                        <td>${afterSalesVo.orgName}</td>
                        <td>归属销售</td>
                        <td>${afterSalesVo.userName}</td>
                    </tr>
                     <tr>
                        <td>订单状态</td>
                        <td>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 0}">待确认</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 1}">进行中</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 2}">已完结</c:if>
                        	<c:if test="${afterSalesVo.saleorderStatus eq 3}">已关闭</c:if>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${afterSalesVo.saleorderValidTime}"/></td>
                    </tr>
                     <tr>
                        <td>客户名称</td>
                        <td>
                        	<div class="customername pos_rel">
                                  <span class="brand-color1 addtitle"style="float:none;" tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"客户信息",
										"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}</span><i class="iconbluemouth"></i>
                                   <div class="pos_abs customernameshow" style="display: none;">
                                            客户性质：<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
                          <c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if><br> 
                                            交易次数：${afterSalesVo.orderCount}<br>
                                            交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
                                            上次交易日期：<date:date value ="${afterSalesVo.lastOrderTime}"/>   
                                        </div>
                            </div>
                        </td>
                        <td>客户等级</td>
                        <td>
                        	<c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 147}">B</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 148}">C</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 149}">D</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 150}">E</c:if>
                        	<c:if test="${afterSalesVo.customerLevel eq 931}">S</c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="table-buttons">
	           	<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
	                <form action="" method="post" id="myform">
	             		<input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
	             		<input type="hidden" name="orderId" value="${afterSalesVo.orderId}"/>
	             		<input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
	             		<input type="hidden" name="type" value="${afterSalesVo.type}"/>
	             		<input type="hidden" name="formToken" value="${formToken}"/>
	             		<!-- <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="applyAudit();">打印合同</button> -->
	             		<c:if test="${(null==taskInfoOver and null==taskInfoOver.getProcessInstanceId())or (null!=taskInfoOver and taskInfoOver.assignee==null and empty candidateUserMapOver[taskInfoOver.id])}">
		             		<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="confirmComplete();">确认完成</button>
		             		<c:if test="${afterSalesVo.status ne 1 && afterSalesVo.closeStatus eq 1}">
			                	<button type="button" class="bt-bg-style bg-light-orange bt-small" onclick="colse();">关闭订单</button>
			                </c:if>
						</c:if>
						<c:if test="${(null!=taskInfoOver and null!=taskInfoOver.getProcessInstanceId() and null!=taskInfoOver.assignee) or !empty candidateUserMapOver[taskInfoOver.id]}">
						<c:choose>
							<c:when test="${taskInfoOver.assignee == curr_user.username or candidateUserMapOver['belong']}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoOver.id}&pass=true&type=2"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoOver.id}&pass=false&type=2"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
						</c:if>
	                </form>
                </c:if>
            </div>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">产品</div>
            </div>
            <table class="table  table-style10">
                <thead>
                     <tr>
                       <th class="wid18">产品名称</th>
                       <th class="wid10">品牌</th>
                       <th class="wid8">型号</th>
                       <th class="wid8">物料编码</th>
                       <th class="wid8">采购订单</th>
                       <th class="wid5">销售价</th>
                       <th class="wid8">数量</th>
                       <th class="wid5">单位</th>
                      </tr>
                   </thead>
                   <tbody>
                       <c:if test="${not empty afterSalesVo.afterSalesGoodsList}">
               		<c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
                		<tr>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
                                    <c:if test="${asg.isActionGoods==1}"><span style="color:red;">【活动】</span></c:if>
                                       <span class="brand-color1 addtitle" style="float:none;"
                                       		tabTitle='{"num":"viewgoods${list.goodsId}","link":"<%= basePath %>/goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}",
                                       					"title":"产品信息"}'>${asg.goodsName}</span>
                                       <br>${asg.sku}
                            	</div>
	                        </td>
	                        <td>${asg.brandName}</td>
	                        <td>${asg.materialCode}</td>
	                        <td>${asg.model}</td>
	                        <td>
	                        
	                        
	                         	<c:if test="${not empty asg.buyorderNos}">
		                        	<c:forEach items="${asg.buyorderNos}" var="buyorder">
		                        		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buyorder.buyorderId}","title":"订单信息"}'>${buyorder.buyorderNo}</a><br>
		                        	</c:forEach>
		                        </c:if>
		                        
		                        
	                        </td>
							<td>${asg.saleorderPrice}</td>
	                        <td>${asg.saleorderNum}</td>
	                        <td>${asg.unitName}</td>
	                    </tr>
                	</c:forEach>
                   </c:if>
                   <c:if test="${empty afterSalesVo.afterSalesGoodsList}">
                    <tr>
                        <td colspan="8">暂无记录</td>
                    </tr>
                   </c:if>
                 </tbody>
            </table>
           </div>
	
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后过程
                </div>
            </div>
            <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                <div class="title-click nobor  pop-new-data" 
                	layerParams='{"width":"700px","height":"250px","title":"新增售后过程","link":"./addAfterSalesRecordPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增
                </div>
             </c:if>
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
		                        <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"700px","height":"250px","title":"编辑售后过程",
		                        			"link":"./editAfterSalesRecordPage.do?afterSalesRecordId=${asi.afterSalesRecordId}"}'>编辑</span>
		                        </c:if>
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
        </div>
        
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    售后对象
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
                	<div class="title-click nobor pop-new-data" layerParams='{"width":"650px","height":"500px","title":"新增",
                					"link":"./searchAfterTraderPage.do?afterSalesId=${afterSalesVo.afterSalesId}"}'>新增</div>
               	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid25">售后对象名称</th>
                        <th class="wid10">对象类型</th>
                        <th >备注</th>
                        <th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${afterSalesVo.afterSalesTraderList}" var="ast" varStatus="status">
						<tr>
	                        <td class="font-blue">
		                        <c:if test="${ast.realTraderType eq 1}">
		                        	<span class="brand-color1 addtitle" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"客户信息",
											"link":"./trader/customer/baseinfo.do?traderId=${ast.traderId}"}'>${ast.traderName}</span>
								</c:if>	
								<c:if test="${ast.realTraderType eq 2}">
									<span class="brand-color1 addtitle"  tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"供应商信息",
										"link":"./trader/supplier/baseinfo.do?traderId=${ast.traderId}"}'>${ast.traderName}</span>
								</c:if>		
							</td>
	                        <td>
	                        	<c:if test="${ast.traderType eq 1}">客户</c:if>
	                       	 	<c:if test="${ast.traderType eq 2}">供应商</c:if>
	                        </td>
	                        <td>${ast.comments}</td>
	                        <td>
	                        	<c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2}">
		                            <div class="caozuo">
		                            <span class="border-blue pop-new-data" 
		                            			layerParams='{"width":"650px","height":"500px","title":"编辑","link":"./editAfterTraderPage.do?afterSalesTraderId=${ast.afterSalesTraderId}"}'>编辑</span>
		                                <span class="caozuo-red" onclick="delAfterTrader(${ast.afterSalesTraderId})">删除</span>
		                            </div>
	                            </c:if>
	                        </td>
	                    </tr>
                   	</c:forEach>
                   	<c:if test="${empty afterSalesVo.afterSalesTraderList}">
						<tr>
							<td colspan="4">暂无记录！</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>
        <c:if test="${sessionScope.curr_user.positType eq 312}">
        <!-- 售后费用表格 -->
        <div class="parts">
        	<div class="title-container">
                <div class="table-title nobor">售后费用</div>
              	<c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
	                <div class="title-click nobor pop-new-data" 
	                	layerParams='{"width":"780px","height":"310px","title":"新增售后费用类型","link":"./addCostType.do?afterSalesId=${afterSalesVo.afterSalesId}&&costType=724"}'>新增
	                </div>
               	</c:if>
            </div>
            <table class="table">
            	<thead>
            		<tr>
            			<th>费用类型</th>
            			<th>费用</th>
            			<th>备注说明</th>
            			<th>操作时间</th>
            			<th>操作人</th>
            			<c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
            				<th>操作</th>
            			</c:if>
            		</tr>
            	</thead>
            	
            	<tbody>
            		<c:forEach var="list" items="${costList}" >
            		<tr>
            			<td>${list.costTypeName }</td>
            			<td>${list.amount }</td>
            			<td>${list.comments }</td>
            			<td>${list.lastModTime }</td>
            			<td>${list.userName }</td>
            			<c:if test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
	            			<td class="caozuo">
		            			<span class="caozuo-red" onclick="delectCostTypeList(${list.afterSalesCostId})">删除</span>
		            			<span class="border-blue pop-new-data" layerParams='{"width":"780px","height":"310px","title":"编辑售后费用",
				                        			"link":"./getCostTypeById.do?afterSalesCostId=${list.afterSalesCostId}&&costType=724"}'>编辑</span>
	            			</td>
	            		</c:if>
            		</tr>
            		</c:forEach>
            		<c:if test="${empty costList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <c:choose>
		           				<c:when test="${afterSalesVo.atferSalesStatus ne 3 and afterSalesVo.atferSalesStatus ne 2}">
		           					<td colspan='6'>暂无记录！</td>	
		           				</c:when>
		           				<c:otherwise>
		           					<td colspan='5'>暂无记录！</td>	
		           				</c:otherwise>
		           			</c:choose>
					    </tr>
		        	</c:if>
            	</tbody>
            </table>
        </div>
<!--        售后费用表格结束  -->
        </c:if>
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
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	售后单完结审核记录
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
                    <c:if test="${null!=historicActivityInstanceOver}">
                    <c:forEach var="hio" items="${historicActivityInstanceOver}" varStatus="status">
                    <c:if test="${not empty  hio.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hio.activityType == 'startEvent'}"> 
							${startUserOver}
							</c:when>
							<c:when test="${hio.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceOver.size() == status.count}">
									${verifyUsersOver}
								</c:if>
								<c:if test="${historicActivityInstanceOver.size() != status.count}">
									${hio.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hio.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hio.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hio.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hio.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapOver[hio.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceOver}">
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