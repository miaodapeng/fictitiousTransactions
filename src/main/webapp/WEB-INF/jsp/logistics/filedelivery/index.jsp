<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="文件寄送列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>logistics/filedelivery/index.do">
			<ul>
				<li>
					<label class="infor_name">单号</label>
					<input type="text" class="input-middle" placeholder="单号" name="fileDeliveryNo"  value="${fileDelivery.fileDeliveryNo}"/>
				</li>
				<li>
					<label class="infor_name">寄送状态</label>
					<select class="input-middle" name="deliveryStatus" >
						<option value="-1">全部</option>
						<option value="0" <c:if test="${0 eq fileDelivery.deliveryStatus}">selected</c:if>>未寄送</option>
						<option value="1" <c:if test="${1 eq fileDelivery.deliveryStatus}">selected</c:if>>已寄送</option>
					</select>
				</li>
				<li>
					<label class="infor_name">快递</label>
					<select class="input-middle" name="logisticsId" >
						<option value="-1">全部</option>
						<c:forEach items="${logisticsList}" var="logistics">
							<option value="${logistics.logisticsId}" <c:if test="${logistics.logisticsId eq fileDelivery.logisticsId}">selected</c:if>>${logistics.name}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">快递单号</label>
					<input type="text" class="input-middle" placeholder="快递单号" name="logisticsNo"  value="${fileDelivery.logisticsNo}"/>
				</li>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" placeholder="客户名称" name="traderName"  value="${fileDelivery.traderName}"/>
				</li>
				<li>
					<label class="infor_name">申请人</label>
					<select class="input-middle" name="creatorId">
						<option value="">全部</option>
						<c:forEach var="netAllUser" items="${netAllUserList}" >
							<option value="${netAllUser.userId}" <c:if test="${netAllUser.userId eq fileDelivery.creator}">selected</c:if>>${netAllUser.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus">
						<option value="">全部</option>
						<option value="0" <c:if test="${0 eq fileDelivery.verifyStatus}">selected</c:if>>待审核</option>
						<option value="1" <c:if test="${1 eq fileDelivery.verifyStatus}">selected</c:if>>审核中</option>
						<option value="2" <c:if test="${2 eq fileDelivery.verifyStatus}">selected</c:if>>审核通过</option>
						<option value="3" <c:if test="${3 eq fileDelivery.verifyStatus}">selected</c:if>>审核不通过</option>
					</select>
				</li>

				<li>
					<label class="infor_name">关闭状态</label>
					<select class="input-middle" name="isClosed" >
						<option value="-1" <c:if test="${fileDelivery.isClosed eq -1}">selected</c:if>>全部</option>
						<option value="0" <c:if test="${fileDelivery.isClosed eq 0}">selected</c:if>>未关闭</option>
						<option value="1" <c:if test="${fileDelivery.isClosed eq 1}">selected</c:if>>已关闭</option>
					</select>
				</li>

				<li>
					<label class="infor_name">寄送形式</label>
					<select class="input-middle" name="sendType">
						<option value="-1">全部</option>
						<c:forEach var="stn" items="${sendTypeName}">
							<option value="${stn.sysOptionDefinitionId }" 
	                    			<c:if test="${fileDelivery.sendType eq stn.sysOptionDefinitionId }">selected="selected"</c:if>>${stn.title}
	                    	</option>	
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">操作人</label>
					<select class="input-middle" name="deliveryUserId" >
						<option value="">全部</option>
						<c:forEach var="logisticsUser" items="${logisticsUserList}" >
							<option value="${logisticsUser.userId}" <c:if test="${logisticsUser.userId eq fileDelivery.deliveryUserId}">selected</c:if>>${logisticsUser.username}</option>
						</c:forEach>
					</select>
				</li>
				
				   <li>
                    <div class="infor_name specialinfor">
                        <select name="type">
                            <option value="1" <c:if test="${fileDelivery.type eq 1}">selected</c:if>>申请时间</option>
                            <option value="2" <c:if test="${fileDelivery.type eq 2}">selected</c:if>>寄送时间</option>
                        </select>
                    </div>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchBeginTime" value="${searchBeginTime}">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchEndTime" value="${searchEndTime}">
                </li>
                <li>
					<label class="infor_name">承运部门</label>
					<select class="input-middle" name="deliveryDept">
						<option value="">全部</option>
						<option value="1" <c:if test="${1 eq fileDelivery.deliveryDept}">selected</c:if>>财务部门</option>
						<option value="2" <c:if test="${2 eq fileDelivery.deliveryDept}">selected</c:if>>物流部门</option>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20 addtitle" tabTitle='{"num":"addFileDelivery","link":"./logistics/filedelivery/addFileDelivery.do","title":"新增寄送"}'>新增寄送</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportFileDeliverylist();">导出列表</span>
			</div>
		</form>
	</div>
	<div class="content">
		<table class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid4">序号</th>
					<th class="wid10">单号</th>
					<th class="wid10">申请人</th>
					<th>申请时间</th>
					<th>客户名称</th>
					<th class="wid6">寄送形式</th>
					<th class="wid6">审核状态</th>
					<th class="wid6">寄送状态</th>
					<th class="wid6">关闭状态</th>
					<th class="wid6">寄送时间</th>
					<th class="wid6">快递公司</th>
					<th>快递单号</th>
					<th class="wid10">承运部门</th>
					<th class="wid6">计重数量</th>
					<th class="wid6">费用</th>
					<th class="wid10">操作人</th>
					<th class="wid10">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${fileDeliveryList}" varStatus="num">
					<c:set var="shenhe" value="0"></c:set>
					<c:if test="${list.verifyStatus==1 && null!=list.verifyUsernameList}">
						<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
							<c:if test="${verifyUsernameInfo == loginUser.username}">
								<c:set var="shenhe" value="1"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
					<tr>
						<td>${num.count}</td>
						<td><a class="addtitle" tabtitle="{&quot;num&quot;:&quot;getFileDeliveryDetail${list.fileDeliveryId}&quot;,&quot;link&quot;:&quot;./logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId=${list.fileDeliveryId}&quot;,&quot;title&quot;:&quot;文件寄送详情&quot;}">${list.fileDeliveryNo}</a>${shenhe == 1 ?"<font color='red'>[审]</font>":""}</td>
						<td>
							${list.creatorUsername}
						</td>
						<td><date:date value="${list.addTime}" /></td>
						<td>${list.traderName}</td>
						<td>${list.sendTypeName}</td>
						<td>${list.verifyStatusName}</td>
						<td>
							<c:if test="${list.deliveryStatus == 1}">
								${list.deliveryStatusName}
							</c:if>
							<c:if test="${list.deliveryStatus == 0}">
								<font color="red">${list.deliveryStatusName}</font>
							</c:if>
						</td>
						<td>
							<c:if test="${list.isClosed == 1}">
								已关闭
							</c:if>
							<c:if test="${list.isClosed == 0}">
								<font color="red">未关闭</font>
							</c:if>
						</td>
						<td><date:date value ="${list.deliveryTime}"/></td>
						<td>
							${list.logisticsName}
						</td>
						<td>${list.logisticsNo}</td>
						<td>
						    <c:if test="${list.deliveryDept == 1}">
								财务部门
							</c:if>
							<c:if test="${list.deliveryDept == 2}">
								物流部门
							</c:if>
						</td>
						<td>${list.num}</td>
						<td>${list.amount}</td>
						<td>${list.deliveryUsername}</td>
						<td>
							<c:if test="${list.isClosed eq 0 and list.deliveryStatus eq 0}">
								<c:if test="${ (list.verifyStatus eq 0 or list.verifyStatus eq 1 or list.verifyStatus eq 2 ) and role eq true }">
									<a class="  bt-small mr10 pop-new-data" layerParams='{"width":"330px","height":"230px","title":"文件寄送关闭","link":"${pageContext.request.contextPath}/logistics/filedelivery/closeFileDelivery.do?fileDeliveryId=${list.fileDeliveryId}"}'>关闭</a>
								</c:if>
								<c:if test="${list.verifyStatus eq 3 and (list.creator eq userId or role eq true )}">
									<a class="  bt-small mr10 pop-new-data" layerParams='{"width":"330px","height":"230px","title":"文件寄送关闭","link":"${pageContext.request.contextPath}/logistics/filedelivery/closeFileDelivery.do?fileDeliveryId=${list.fileDeliveryId}"}'>关闭</a>
								</c:if>
							</c:if>
							<a class="addtitle" tabtitle="{&quot;num&quot;:&quot;getFileDeliveryDetail${list.fileDeliveryId}&quot;,&quot;link&quot;:&quot;./logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId=${list.fileDeliveryId}&quot;,&quot;title&quot;:&quot;文件寄送详情&quot;}">查看</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty fileDeliveryList}">
					<tr>
						<td colspan="16">查询无结果！请尝试使用其他搜索条件。</td>
					</tr>
					<!-- 查询无结果弹出 -->
					<!-- <div class="noresult"></div> -->
				</c:if>
			</tbody>
		</table>
	<tags:page page="${page}" />
	</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/fileDelivery/index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
