<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="供应商列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="list-pages-search">
		<form action="${pageContext.request.contextPath}/trader/supplier/index.do" method="post" id="search">
			<input type="hidden" name="search" value="click">
			<ul>
				<li><label class="infor_name">供应商名称</label> <input type="text"
					class="input-middle" name="traderSupplierName"
					id="traderSupplierName"
					value="${traderSupplierVo.traderSupplierName}"></li>
				<li><label class="infor_name">归属采购</label> <select
					class="input-middle f_left" name="homePurchasing">
						<option value="">全部</option>
						<c:if test="${not empty userList }">
							<c:forEach items="${userList }" var="user">
								<option value="${user.userId }"
									<c:if test="${traderSupplierVo.homePurchasing eq user.userId }">selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</c:if>
				</select></li>

				<li><label class="infor_name">有合作</label> <select
					class="input-middle f_left" name="cooperate">
						<option value="" >全部</option>
						<option value="1" <c:if test="${traderSupplierVo.cooperate eq 1 }">selected="selected"</c:if>>是</option>
						<option value="2" <c:if test="${traderSupplierVo.cooperate eq 2 }">selected="selected"</c:if>>否</option>
				</select></li>
				<li><label class="infor_name">供应商等级</label> <select
					class="input-middle f_left" name="grade">
						<option value="">全部</option>
						<option value="59" <c:if test="${traderSupplierVo.grade eq 59 }">selected="selected"</c:if>>核心供应商</option>
						<option value="60" <c:if test="${traderSupplierVo.grade eq 60 }">selected="selected"</c:if>>普通供应商</option>
						<option value="62" <c:if test="${traderSupplierVo.grade eq 62 }">selected="selected"</c:if>>临时供应商</option>
				</select></li>
				<li><label class="infor_name">联系方式</label> <input type="text"
					class="input-middle" placeholder="电话/手机/QQ/微信/邮箱" name="contactWay"
					id="contactWay" value="${traderSupplierVo.contactWay}"></li>
				<li><label class="infor_name">审核状态</label> <select
					class="input-middle f_left" name="traderSupplierStatus">
						<option value="">全部</option>
						<option value="3" <c:if test="${traderSupplierVo.traderSupplierStatus eq 3 }">selected="selected"</c:if>>待审核</option>
						<option value="0" <c:if test="${traderSupplierVo.traderSupplierStatus eq 0 }">selected="selected"</c:if>>审核中</option>
						<option value="1" <c:if test="${traderSupplierVo.traderSupplierStatus eq 1 }">selected="selected"</c:if>>审核通过</option>
						<option value="2" <c:if test="${traderSupplierVo.traderSupplierStatus eq 2 }">selected="selected"</c:if>>审核不通过</option>
				</select></li>
				<li>
					<label class="infor_name">信息搜索</label> 
					<input type="text" class="input-middle" placeholder="沟通记录等" name="searchMsg" id="" value="${traderSupplierVo.searchMsg }">
				</li>
				<li>
					<div class="infor_name specialinfor"> 
						<select name="timeType">
							<option value="1" <c:if test="${traderSupplierVo.timeType eq 1 }">selected="selected"</c:if>>创建时间</option>
							<option value="2" <c:if test="${traderSupplierVo.timeType eq 2 }">selected="selected"</c:if>>交易时间</option>
							<option value="3" <c:if test="${traderSupplierVo.timeType eq 3 }">selected="selected"</c:if>>更新时间</option>
						</select>
					</div> 
					<input class="Wdate f_left input-smaller96 mr5" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"
						name="queryStartTime" id="startTime" value=<date:date value ="${traderSupplierVo.startTime} "/>><div class="gang">-</div>
					<input class="Wdate f_left input-smaller96" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})"
						name="queryEndTime" id="endTime" value=<date:date value ="${traderSupplierVo.endTime} "/>>
				</li>
				<li>
					<label class="infor_name">地区</label> 
					<ul class="inputfloat f_left">
						<li>
							<select class="wid9" name="province" id="province">
								<option value="0">全部</option>
								<c:if test="${not empty provinceList }">
									<c:forEach items="${provinceList }" var="prov">
										<option value="${prov.regionId }"
											<c:if test="${traderSupplierVo.province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
						</li> 
						<li>
							<select class="wid9" name="city" id="city">
									<option value="0">全部</option>
									<c:if test="${not empty cityList }">
										<c:forEach items="${cityList }" var="cy">
											<option value="${cy.regionId }"
												<c:if test="${traderSupplierVo.city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
										</c:forEach>
									</c:if>
							</select>
						</li> 
						<li>
							<select class="wid9" name="zone" id="zone">
								<option value="0">全部</option>
								<c:if test="${not empty zoneList }">
									<c:forEach items="${zoneList }" var="zo">
										<option value="${zo.regionId }"
											<c:if test="${traderSupplierVo.zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
						</li>
					</ul>	
				</li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" id="searchSpan" onclick="search();">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"supplier","link":"./trader/supplier/add.do","title":"新增供应商"}'>新增供应商</span>
				<span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span>					
			</div>
			<input type="hidden" id="cityid" value="${city}" /> <input
				type="hidden" id="zoneid" value="${zone}" />
		</form>
	</div>
	<div class="fixdiv">
		<div class="superdiv">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="sorts">序号</th>
						<th>供应商名称</th>
						<th>地区</th>
						<th>供应商等级</th>
						<th>采购次数</th>
						<th>采购金额</th>
						<th>创建时间</th>
						<th>归属采购</th>
						<th>更新时间</th>
						<th>审核状态</th>
						<th>操作</th>
					</tr>
				</thead>

				<tbody class="employeestate">
					<c:if test="${not empty list}">
						<c:forEach items="${list}" var="traderSupplierVo"
							varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td class="text-left">
									<c:if test="${curr_user.positType eq 311}"></c:if>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"./trader/supplier/baseinfo.do?traderId=${traderSupplierVo.traderId}","title":"供应商信息"}'>${traderSupplierVo.traderSupplierName}</a>
										${traderSupplierVo.verifyStatus eq 0 && fn:contains(traderSupplierVo.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}
										
									
									<!-- 
									<c:if test="${curr_user.positType ne 311}">
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsupplier<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
										"link":"./trader/supplier/baseinfo.do?traderId=${traderSupplierVo.traderId}","title":"供应商信息"}'>${traderSupplierVo.traderSupplierName}</a>
										${traderSupplierVo.verifyStatus eq 0 && fn:contains(traderSupplierVo.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}
									</c:if> -->
								</td>
								<td>${traderSupplierVo.traderSupplierAddress }</td>
								<td><c:if test="${traderSupplierVo.grade eq 59}">核心供应商</c:if>
									<c:if test="${traderSupplierVo.grade eq 60}">普通供应商</c:if> 
									<c:if test="${traderSupplierVo.grade eq 62}">临时供应商</c:if></td>
								<td>${traderSupplierVo.buyCount}</td>
								<td>${traderSupplierVo.buyMoney}</td>
								<td><date:date value="${traderSupplierVo.addTime} " /></td>
								<td>${traderSupplierVo.personal}</td>
								<td><date:date value="${traderSupplierVo.modTime} " /></td>
								<td>
									<c:if test="${empty traderSupplierVo.verifyStatus or traderSupplierVo.verifyStatus eq 3}">待审核</c:if>
									<c:if test="${traderSupplierVo.verifyStatus eq 0}">审核中</c:if>
									<c:if test="${traderSupplierVo.verifyStatus eq 1}">审核通过</c:if>
									<c:if test="${traderSupplierVo.verifyStatus eq 2}">审核不通过</c:if>
								</td>
								<td>
									<c:if test="${traderSupplierVo.isView eq 1}"></c:if>
									<c:choose>
										<c:when test="${traderSupplierVo.isTop eq 0}">
											<span class="edit-user"
												onclick="stick(${traderSupplierVo.traderSupplierId},1);">置顶</span>
										</c:when>
										<c:otherwise>
											<span class="edit-user"
												onclick="stick(${traderSupplierVo.traderSupplierId},0);">取消置顶</span>
										</c:otherwise>
									</c:choose> <c:choose>
										<c:when test="${traderSupplierVo.isEnable eq 0}">
											<span class="edit-user"
												onclick="setDisabled(${traderSupplierVo.traderSupplierId},1);">启用</span>
										</c:when>
										<c:otherwise>
											<span class="forbid clcforbid pop-new-data"
												layerParams='{"width":"600px","height":"200px","title":"禁用","link":"./initDisabledPage.do?id=${traderSupplierVo.traderSupplierId}"}'>禁用</span>
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty list }">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
		</div>
	</div>
	<tags:page page="${page}" />
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/supplier/index.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>