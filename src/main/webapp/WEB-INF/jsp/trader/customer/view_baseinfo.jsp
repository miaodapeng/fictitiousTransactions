<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="title" value="基本信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ include file="customer_tag.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/customer/view_baseinfo.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var	url = page_url + '/trader/customer/baseinfo.do?traderId='+$("#traderIdValue").val()+'&traderCustomerId='+$("#traderCustomerIdValue").val();
		if($(window.frameElement).attr('src').indexOf("baseinfo")<0){
			$(window.frameElement).attr('data-url',url);
		}

	});
</script>
<div class="content">
	<div class="parts">
		<div class="title-container">
            <div class="table-title">基本信息</div>
            <c:if test="${traderCustomer.isEnable == 1 && ((traderCustomer.verifyStatus != null && traderCustomer.verifyStatus != 0 )|| traderCustomer.verifyStatus == null) && ((null==taskInfoName and null==taskInfoName.getProcessInstanceId() and null==taskInfoName.assignee) or empty candidateUserMapName[taskInfoName.id])}">
            <div class="title-click" onclick="goUrl('${pageContext.request.contextPath}/trader/customer/editbaseinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}');">编辑</div>
            </c:if>
        </div>
		<table class="table table-bordered table-striped table-condensed table-centered">
            <tbody>
                <tr>
                    <td class="table-smallest"><c:if test="${traderInfo!=null}">原</c:if>客户名称</td>
                    <td class="table-middle">${traderCustomer.trader.traderName }</td>
                    <td class="table-smallest">客户id</td>
                    <td class="table-middle">${traderCustomer.trader.traderId }</td>
                    <td class="table-smallest">地区</td>
                    <td class="table-middle">${region}</td>
                </tr>
                <c:if test="${traderInfo!=null}">
					<tr>
						<td class="table-smallest">现客户名称</td>
						<td class="table-middle font-red">${traderInfo.traderName}</td>
						<td class="table-smallest"></td>
						<td class="table-middle"></td>
						<td class="table-smallest"></td>
						<td class="table-middle"></td>
					</tr>
                </c:if>
                <tr>
                    <td>客户类型</td>
                    <td colspan="5" class="text-left">
                    	<c:forEach items="${traderCustomer.customerCategories}" var="category">
						${category.customerCategoryName }&nbsp;&nbsp;
						</c:forEach>
						<c:if test="${empty traderCustomer.customerCategories}">
							<c:if test="${traderCustomer.customerType eq 426}">科研医疗</c:if>
							<c:if test="${traderCustomer.customerType eq 427}">临床医疗</c:if>&nbsp;&nbsp;
							<c:if test="${traderCustomer.customerNature eq 465}">分销</c:if>
							<c:if test="${traderCustomer.customerNature eq 466}">终端</c:if>
						</c:if>
                    </td>
                </tr>
                <c:if test="${not empty traderCustomer.ownershipStr }">
	                <tr>
	                    <td>所有制</td>
	                    <td colspan="5" class="text-left">${traderCustomer.ownershipStr }</td>
	                </tr>
                </c:if>
                <c:if test="${not empty traderCustomer.medicalTypeStr }">
	                <tr>
	                    <td>医学类型</td>
	                    <td colspan="5" class="text-left">${traderCustomer.medicalTypeStr }</td>
	                </tr>
                </c:if>
                <c:if test="${not empty traderCustomer.hospitalLevelStr }">
	                <tr>
	                    <td>医院等级</td>
	                    <td colspan="5" class="text-left">${traderCustomer.hospitalLevelStr }</td>
	                </tr>
                </c:if>
                <c:if test="${not empty traderCustomer.attributeMap}">
					<c:forEach items="${traderCustomer.attributeMap}" var="attibute">
						<tr>
		                    <td>${attibute.key }</td>
		                    <td colspan="5" class="text-left">
		                    	<c:forEach items="${attibute.value }" var="attr">
									${attr.sysOptionDefinition.title }
									<c:if test="${not empty attr.attributeOther }">
									(${attr.attributeOther })
									</c:if>
									、
								</c:forEach>
							</td>
		                </tr>
	                </c:forEach>
				</c:if>
				<c:if test="${show_fenxiao}">
					<tr>
	                    <td>经营区域</td>
	                    <td colspan="5" class="text-left">
	                    	<c:forEach var="bussinessArea" items="${traderCustomer.traderCustomerBussinessAreas}">
								${bussinessArea.areaStr }、
							</c:forEach>
	                    </td>
	                </tr>
				</c:if>
				<c:if test="${show_fenxiao}">
					<tr>
	                    <td>经营品牌</td>
	                    <td colspan="5" class="text-left">
	                    	<c:forEach var="bussinessBrand" items="${traderCustomer.traderCustomerBussinessBrands}">
								${bussinessBrand.brand.brandName }、
							</c:forEach>
	                    </td>
	                </tr>
				</c:if>
				<c:if test="${show_fenxiao}">
					<tr>
	                    <td>代理品牌</td>
	                    <td colspan="5" class="text-left">
	                    	<c:forEach var="agentBrand" items="${traderCustomer.traderCustomerAgentBrands}">
								${agentBrand.brand.brandName }、
							</c:forEach>
	                    </td>
	                </tr>
				</c:if>
				<c:if test="${show_fenxiao}">
					<tr>
	                    <td class="table-smallest">业务模式</td>
	                    <td class="table-middle">
		                    <c:if test="${not empty traderCustomer.directSelling 
								|| not empty traderCustomer.wholesale}">
	                    		直销比例：${traderCustomer.directSelling }%；批发比例：${traderCustomer.wholesale }%
                    		</c:if>
                   		</td>
	                    <td class="table-smallest">销售模式</td>
	                    <td class="table-middle" colspan="3">${traderCustomer.salesModelStr }</td>
	                </tr>
				</c:if>
				<tr>
                    <td class="table-smallest">注册年份</td>
                    <td class="table-middle"><fmt:formatDate value="${traderCustomer.registeredDate }" pattern="yyyy-MM-dd" /></td>
                    <td class="table-smallest">注册资金</td>
                    <td class="table-middle" colspan="3">${traderCustomer.registeredCapital }</td>
                </tr>
                <tr>
                    <td class="table-smallest">员工人数</td>
                    <td class="table-middle">${traderCustomer.employeesStr }</td>
                    <td class="table-smallest">年销售额</td>
                    <td class="table-middle" colspan="3">${traderCustomer.annualSalesStr }</td>
                </tr>
                 <tr>
                    <td class="table-smallest">历史名称</td>
                    <td class="table-middle">${traderCustomer.historyNames }</td>
                    <td class="table-smallest"></td>
                    <td class="table-middle" colspan="3"></td>
                </tr>
                <tr>
                    <td class="table-smallest">经营范围</td>
                    <td class="table-middle" colspan="5">${traderCustomer.businessScope }</td>
                </tr>
				<tr>
					<td class="table-smallest">美年采购单位编码</td>
					<td class="table-middle text-left" colspan="5">${traderCustomer.meiNianCode }</td>
				</tr>
				<tr>
					<td class="table-smallest">归属平台</td>
					<td class="table-middle text-left" colspan="5">
						<c:choose>
							<c:when test="${traderCustomer.belongPlatform==1}">
								贝登医疗
							</c:when>
							<c:when test="${traderCustomer.belongPlatform==2}">
								医械购
							</c:when>
							<c:when test="${traderCustomer.belongPlatform==3}">
								科研购
							</c:when>
							<c:otherwise>
								其他
							</c:otherwise>
						</c:choose>
					</td>
				</tr>

			</tbody>
		</table>
	</div>
	<input id= "traderName" type="hidden" value="${traderCustomer.trader.traderName }"/>
	<input type="hidden" name="formToken" value="${formToken}"/>
        <div class="tcenter mb15 mt-5">
        	<c:choose>
				<c:when test="${traderCustomer.verifyStatus == null || traderCustomer.verifyStatus != 1}">
				<c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId() )or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
					<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidCustomer(${traderCustomer.traderCustomerId},${taskInfo.id == null ?0: taskInfo.id})">申请审核</button>
					<button type="button" class="confSearch bt-small bt-bg-style bg-light-blue" onclick="searchTyc();" id="searchSpan">天眼查同步</button>
					<%--Bert需要新增页面输入客户编码--%>

				</c:if>
				<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
					<c:choose>
						<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:when>
				<c:when test="${traderCustomer.verifyStatus != null && traderCustomer.verifyStatus == 1}">
					<button type="button" class="confSearch bt-small bt-bg-style bg-light-blue" onclick="restVerify(${traderCustomer.traderCustomerId});">重置为待审核</button>
					<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"830px","height":"530px","title":"请选择客户","link":"${pageContext.request.contextPath}/order/miannian/bingingView.do?traderCustomerId=${traderCustomer.traderCustomerId}"}'>绑定美年采购单位编码</button>
				</c:when>
			</c:choose>
        </div>
<c:if test="${not empty tycInfo }">
<div class="parts " id="eyeDiv">
	<div class="title-container">
		<div class="table-title nobor">天眼查同步信息</div>
	</div>
	<table class="table">
		<tbody>
			<tr>
				<td>
					<div class="form-list form-tips7">
						<form method="post" action="${pageContext.request.contextPath}/trader/customer/saveeditbaseinfo.do" >
							<ul>
								<%-- <li>
									<div class="form-tips">
										<lable>营业期限</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks" style="width: 400px;">
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" value=<date:date value="${tycInfo.fromTime}" format="yyyy-MM-dd" />>
											<div style="margin: 1px 1px 0 -8px; float: left;">-</div>
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" value=<date:date value="${tycInfo.toTime}" format="yyyy-MM-dd" />>
										</div>
									</div>
								</li> --%>
								<li>
									<div class="form-tips">
										<lable>注册地址</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderFinance.regAddress" value="${tycInfo.regLocation }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>税务登记号</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderFinance.taxNum" value="${tycInfo.taxNumber }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>组织机构代码</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderCustomer.orgNumber"  value="${tycInfo.orgNumber }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>统一社会信用代码</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderCustomer.creditCode" value="${tycInfo.creditCode }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>注册日期</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" name="traderCustomer.registeredDateStr" value=<date:date value="${tycInfo.estiblishTime}" format="yyyy-MM-dd" />>
										</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>注册资金</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-middle" name="traderCustomer.registeredCapital" value="${tycInfo.regCapital }"> <!-- <span>万</span> -->
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>经营范围</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<textarea class="input-largest"
												rows="6"  name="traderCustomer.businessScope">${tycInfo.businessScope }</textarea>
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>历史名称</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<span name="traderCustomer.businessScope">
											<c:if test="${not empty tycInfo.historyNames }">
											${tycInfo.historyNames }
											</c:if>
											<c:if test="${ empty tycInfo.historyNames }">
											--
											</c:if>
											</span>
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>天眼查更新时间</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<span><date:date value ="${tycInfo.updateTime}"/></span>
										</div>
									</div>
								</li>
							</ul>
							<div class="add-tijiao">
							    <input id="traderId" name="traderId" type="hidden" value="${traderCustomer.traderId }"/>
							    <input id="traderName" name="traderName" type="hidden" value="${traderCustomer.trader.traderName }"/>
							    <input id="traderCustomer.traderCustomerId" name="traderCustomer.traderCustomerId" type="hidden" value="${traderCustomer.traderCustomerId }"/>
                                <button type="submit">提交</button>
                                <span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="hideDiv();" id="searchSpan">取消</span>
                            </div>
						</form>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
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
                        <td>操作人</td>
                        <td>操作时间</td>
                        <td>操作事项</td>
                        <td>备注</td>
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
                    <c:if test="${empty historicActivityInstance}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
                </tbody>
            </table>

        	<div class="clear"></div>
        </div>
        <div class="tcenter mb15 mt-5">
        <c:if test="${(null!=taskInfoName and null!=taskInfoName.getProcessInstanceId() and null!=taskInfoName.assignee) or !empty candidateUserMapName[taskInfoName.id]}">
					<c:choose>
						<c:when test="${taskInfoName.assignee == curr_user.username or candidateUserMapName['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoName.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoName.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
		</c:if>
		</div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    	客户名称审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <td>操作人</td>
                        <td>操作时间</td>
                        <td>操作事项</td>
                        <td>备注</td>
                    </tr>
                </thead>
                <tbody>    
                    <c:if test="${null!=historicActivityInstanceName}">
                    <c:forEach var="hin" items="${historicActivityInstanceName}" varStatus="status">
                    <c:if test="${not empty  hin.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hin.activityType == 'startEvent'}"> 
							${startUserName}
							</c:when>
							<c:when test="${hin.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceName.size() == status.count}">
									${verifyUsersName}
								</c:if>
								<c:if test="${historicActivityInstanceName.size() != status.count}">
									${hin.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hin.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hin.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hin.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hin.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapName[hin.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                    <c:if test="${empty historicActivityInstanceName}">
		       			<!-- 查询无结果弹出 -->
		       			<tr>
		       				<td colspan="4">暂无审核记录。</td>
		       			</tr>
		        	</c:if>
                </tbody>
            </table>

        	<div class="clear"></div>
        </div>
	<!-- 
	<div class="parts content1">
		<div class="title-container">
            <div class="table-title nobor">
                公司关系
            </div>
            <div class="title-click" onclick="">编辑</div>
        </div>
        
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>客户名称</th>
                    <th>地区</th>
                    <th>关系</th>
                    <th>交易次数</th>
                    <th>归属销售</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                	<td colspan="6" class="tcenter">暂无信息</td>
                </tr>
            </tbody>
        </table>
	</div>
	 
	<div class="parts">
		<div class="title-container">
            <div class="table-title nobor">
               供应信息
            </div>
        </div>
        
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>采购次数</th>
                    <th>采购金额</th>
                    <th>状态</th>
                    <th>归属采购</th>
                </tr>
            </thead>
            <tbody>
            	<c:choose>
            		<c:when test="${not empty  traderCustomer.traderSupplierVo}">
            			<tr>
            				<td>${traderCustomer.traderSupplierVo.buyCount }</td>
            				<td>${traderCustomer.traderSupplierVo.buyMoney }</td>
            				<td>
            				<c:choose>
	                     	<c:when test="${traderCustomer.traderSupplierVo.isEnable == 1}">
	                     	未禁用
	                     	</c:when>
	                     	<c:otherwise><span class="font-red">已禁用</span></c:otherwise>
	                    	</c:choose>
            				</td>
            				<td>${traderCustomer.traderSupplierVo.ownerSale }</td>
            			</tr>
            		</c:when>
            		<c:otherwise>
	                <tr>
	                	<td colspan="4" class="tcenter">暂无信息</td>
	                </tr>
            		</c:otherwise>
            	</c:choose>
            </tbody>
        </table>
	</div>
	<div class="pb50 font-grey9">
		友情提醒： <br />
		1、如果该客户同时也是供应商，则显示供应商相关数据，否则供应信息为空；采购次数和采购金额指我司向其采购订单的次数和金额。
	</div>
	-->
</div>
<input id="traderIdValue" name="traderIdValue" type="hidden" value="${traderCustomer.traderId }"/>
<input id="traderNameValue" name="traderNameValue" type="hidden" value="${traderCustomer.trader.traderName }"/>
<input id="traderCustomerIdValue" name="traderCustomerIdValue" type="hidden" value="${traderCustomer.traderCustomerId }"/>
<script type="text/javascript"
	src='<%= basePath %>static/js/customer/list_traderCustomer.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
