<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="耗材客户列表" scope="application" />
<%@ include file="../../../common/common.jsp"%>
<div class="main-container">
	<div class="list-pages-search">
		<form action="${pageContext.request.contextPath}/trader/customer/hcTraderListPage.do" method="post" id="search">
			<input type="hidden" name="search" value="click">
			<input type="hidden" name="pageType" value="2">
			<ul>
				<li>
					<label class="infor_name">客户名称</label> 
					<input type="text" class="input-middle" name="name" id="name" value="${traderCustomerVo.name}">
				</li>
				<li>
					<label class="infor_name">归属销售</label>
					<select class="input-middle f_left" name="homePurchasing">
						<option value="">全部</option>
						<c:if test="${not empty userList }">
							<c:forEach items="${userList }" var="user">
								<option value="${user.userId }" <c:if test="${traderCustomerVo.homePurchasing eq user.userId }">selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</c:if>
					</select>
				</li>

				<li><label class="infor_name">客户类型</label> <select
					class="input-middle f_left" name="customerType" >
						<option value="0">全部</option>
						<option value="427"
							<c:if test="${traderCustomerVo.customerType eq 427 }">selected="selected"</c:if>>临床医疗</option>
						<option value="426"
							<c:if test="${traderCustomerVo.customerType eq 426 }">selected="selected"</c:if>>科研医疗</option>
				</select></li>
				<li>
					<label class="infor_name">客户性质</label> 
					<select class="input-middle f_left" name="customerNature">
						<option value="0">全部</option>
						<option value="465" <c:if test="${traderCustomerVo.customerNature eq 465 }">selected="selected"</c:if>>分销</option>
						<option value="466" <c:if test="${traderCustomerVo.customerNature eq 466 }">selected="selected"</c:if>>终端</option>
					</select>
					
				</li>
				<li><label class="infor_name">有合作</label> <select
					class="input-middle f_left" name="cooperate">
						<option value="">全部</option>
						<option value="1"
							<c:if test="${traderCustomerVo.cooperate eq 1 }">selected="selected"</c:if>>是</option>
						<option value="2"
							<c:if test="${traderCustomerVo.cooperate eq 2 }">selected="selected"</c:if>>否</option>
				</select></li>
				<li><label class="infor_name">有报价</label> <select
					class="input-middle f_left" name="quote">
						<option value="0">全部</option>
						<option value="1"
							<c:if test="${traderCustomerVo.quote eq 1 }">selected="selected"</c:if>>是</option>
						<option value="2"
							<c:if test="${traderCustomerVo.quote eq 2 }">selected="selected"</c:if>>否</option>
				</select></li>
				<li><label class="infor_name">信息搜索</label> <input type="text"
					class="input-middle" placeholder="沟通记录/商机信息" name="searchMsg" id=""
					value="${traderCustomerVo.searchMsg}"></li>
				<li><label class="infor_name">客户等级</label> <select
					class="input-middle f_left" name="customerLevel">
						<option value="0">全部</option>
						<c:forEach items="${customerLevers}" var="cl">
							<option value="${cl.sysOptionDefinitionId }"
								<c:if test="${traderCustomerVo.customerLevel eq cl.sysOptionDefinitionId }">selected="selected"</c:if>>${cl.title}</option>
						</c:forEach>

				</select></li>
				<li><label class="infor_name">销售评级</label> <select
					class="input-middle f_left" name="userEvaluate">
						<option value="0">全部</option>
						<c:forEach items="${salersLevers}" var="sl">
							<option value="${sl.sysOptionDefinitionId }"
								<c:if test="${traderCustomerVo.userEvaluate eq sl.sysOptionDefinitionId }">selected="selected"</c:if>>${sl.title}</option>
						</c:forEach>

				</select></li>
				<li><label class="infor_name">战略合作伙伴</label> <select
					class="input-middle f_left" name="partnerId">
						<option value="0">全部</option>
						<c:forEach items="${partners}" var="ps">
							<option value="${ps.sysOptionDefinitionId }"
								<c:if test="${traderCustomerVo.partnerId eq ps.sysOptionDefinitionId }">selected="selected"</c:if>>${ps.title}</option>
						</c:forEach>

				</select></li>
				<li><label class="infor_name">联系方式</label> <input type="text"
					class="input-middle" placeholder="电话/手机/QQ/微信/邮箱" name="contactWay"
					id="contactWay" value="${traderCustomerVo.contactWay}"></li>
				<li><label class="infor_name">基层医疗经销商</label> <select
					class="input-middle f_left" name="basicMedicalDealer">
						<option value="-1">全部</option>
						<option value="1"
							<c:if test="${traderCustomerVo.basicMedicalDealer eq 1 }">selected="selected"</c:if>>是</option>
						<option value="0"
							<c:if test="${traderCustomerVo.basicMedicalDealer eq 0 }">selected="selected"</c:if>>否</option>
				</select></li>
				<li><label class="infor_name">审核状态</label> <select
					class="input-middle f_left" name="customerStatus">
						<option value="">全部</option>
						<option value="3" <c:if test="${traderCustomerVo.customerStatus eq 3 }">selected="selected"</c:if>>待审核</option>
						<option value="0" <c:if test="${traderCustomerVo.customerStatus eq 0 }">selected="selected"</c:if>>审核中</option>
						<option value="1" <c:if test="${traderCustomerVo.customerStatus eq 1 }">selected="selected"</c:if>>审核通过</option>
						<option value="2" <c:if test="${traderCustomerVo.customerStatus eq 2 }">selected="selected"</c:if>>审核不通过</option>
				</select></li>
				<li><label class="infor_name">微信状态</label> <select
					class="input-middle f_left" name="wxStatus">
						<option value="0" <c:if test="${traderCustomerVo.wxStatus eq 0 }">selected="selected"</c:if>>全部</option>
						<option value="1" <c:if test="${traderCustomerVo.wxStatus eq 1 }">selected="selected"</c:if>>已添加</option>
						<option value="2" <c:if test="${traderCustomerVo.wxStatus eq 2 }">selected="selected"</c:if>>未添加</option>
				</select></li>
				<li>
					<div class="infor_name">客户得分</div> 
					<input type="text" class="f_left" name="smallScore" value="${traderCustomerVo.smallScore}" style='width:86px;'><div class="gang">-</div>
					<input type="text" class="f_left" name="bigScore" value="${traderCustomerVo.bigScore}" style='width:86px;'>
				</li>
				<li>
					<div class="infor_name" style='margin-top:1px;'> 
						<select name="timeType">
							<option value="1" <c:if test="${traderCustomerVo.timeType eq 1 }">selected="selected"</c:if>>创建时间</option>
							<option value="2" <c:if test="${traderCustomerVo.timeType eq 2 }">selected="selected"</c:if>>交易时间</option>
							<option value="3" <c:if test="${traderCustomerVo.timeType eq 3 }">selected="selected"</c:if>>更新时间</option>
						</select>
					</div>	 
					<input class="Wdate f_left input-smaller96 mr5" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"
						name="queryStartTime" id="startTime" value=<date:date value ="${traderCustomerVo.startTime} "/>><div class="gang">-</div>
					<input class="Wdate f_left input-smaller96" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})"
						name="queryEndTime" id="endTime" value=<date:date value ="${traderCustomerVo.endTime} "/>>
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
											<c:if test="${traderCustomerVo.province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
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
											<c:if test="${traderCustomerVo.city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
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
											<c:if test="${traderCustomerVo.zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
						</li>
					</ul>
				</li>

				<li><label class="infor_name">客户分类</label> <select
						class="input-middle f_left" name="customerCategory">
					<option value="">全部</option>
					<option value="0" <c:if test="${traderCustomerVo.customerCategory eq 0 }">selected="selected"</c:if>>未成交客户</option>
					<option value="1" <c:if test="${traderCustomerVo.customerCategory eq 1 }">selected="selected"</c:if>>新客户</option>
					<option value="3" <c:if test="${traderCustomerVo.customerCategory eq 3 }">selected="selected"</c:if>>留存客户</option>
					<option value="2" <c:if test="${traderCustomerVo.customerCategory eq 2 }">selected="selected"</c:if>>流失客户</option>
				</select></li>

				<li>
					<label class="infor_name">税号审核状态</label>
					<select class="input-middle f_left" name="financeCheckStatus">
						<option value="">全部</option>
						<option value="0" <c:if test="${traderCustomerVo.financeCheckStatus eq 0 }">selected="selected"</c:if>>审核中</option>
						<option value="1" <c:if test="${traderCustomerVo.financeCheckStatus eq 1 }">selected="selected"</c:if>>审核通过</option>
						<option value="2" <c:if test="${traderCustomerVo.financeCheckStatus eq 2 }">selected="selected"</c:if>>审核不通过</option>
					</select>
				</li>

				<li>
					<label class="infor_name">归属平台</label>
					<select class="input-middle f_left" name="belongPlatform">
						<option value="">全部</option>
						<option value="1" <c:if test="${traderCustomerVo.belongPlatform eq 1 }">selected="selected"</c:if>>贝登医疗</option>
						<option value="2" <c:if test="${traderCustomerVo.belongPlatform eq 2 }">selected="selected"</c:if>>医械购</option>
						<option value="3" <c:if test="${traderCustomerVo.belongPlatform eq 3 }">selected="selected"</c:if>>科研购</option>
						<option value="5" <c:if test="${traderCustomerVo.belongPlatform eq 5 }">selected="selected"</c:if>>其他</option>
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" id="searchSpan" onclick="search();">搜索</span> 
				<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<!-- 
				<span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"customer","link":"./trader/customer/add.do","title":"新增客户"}'>新增客户</span>
				<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"批量新增客户","link":"./uplodebatchcustomer.do"}'>批量新增客户</span>
         		<span class="bg-light-blue bt-bg-style bt-small" onclick="exportList()">导出列表</span>
				 -->				
			</div>
			<input type="hidden" id="cityid" value="${city}" /> 
			<input type="hidden" id="zoneid" value="${zone}" />
		</form>
	</div>
	<div class="fixdiv">
		<div class="superdiv">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="sorts">序号</th>
						<th class="wid22">客户名称</th>
						<th class="wid20">地区</th>
						<th class="wid10">客户分类</th>
						<th class="wid8">客户类型</th>
						<th class="wid7">客户性质</th>
						<th class="wid10">贝登商城会员</th>
						<th class="wid7">交易次数</th>
						<th class="wid8">交易金额</th>
						<th class="wid7">报价次数</th>
						<th class="wid7">沟通次数</th>
						<th class="wid7">客户等级</th>
						<th class="wid7">客户得分</th>
						<th class="wid13">战略合作伙伴</th>
						<th class="wid10">基层医疗经销商</th>
						<th class="wid15">归属销售</th>
						<th  class="wid15">更新时间</th>
						<th class="wid10">审核状态</th>
						<th class="wid15">操作</th>
					</tr>
				</thead>

				<tbody class="employeestate">
					<c:if test="${not empty list}">
						<c:forEach items="${list}" var="traderCustomerVo"
							varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td class="text-left">
										<c:if test="${traderCustomerVo.isView eq 1 && curr_user.positType eq 310}">
											<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/customer/baseinfo.do?traderId=${traderCustomerVo.traderId}",
											"title":"客户信息"}'>${traderCustomerVo.name}</a>
											
										</c:if>
										<c:if test="${traderCustomerVo.isView ne 1 && curr_user.positType eq 310}">
											${traderCustomerVo.name}
										</c:if>
										<c:if test="${curr_user.positType ne 310}">
											<a class="addtitle" href="javascript:void(0);"
										tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./trader/customer/baseinfo.do?traderId=${traderCustomerVo.traderId}",
											"title":"客户信息"}'>${traderCustomerVo.name}</a>
											${traderCustomerVo.verifyStatus == 0 && fn:contains(traderCustomerVo.verifyUsername, curr_user.username) ?"<font color='red'>[审]</font>":""}
										</c:if>
									
								</td>
								<td>${traderCustomerVo.address }</td>
								<td>
									<c:if test="${traderCustomerVo.customerCategory == null }">-</c:if>
									<c:if test="${traderCustomerVo.customerCategory eq 0 }">未成交客户</c:if>
									<c:if test="${traderCustomerVo.customerCategory eq 1 }">新客户</c:if>
									<c:if test="${traderCustomerVo.customerCategory eq 2 }">流失客户</c:if>
									<c:if test="${traderCustomerVo.customerCategory eq 3 }">留存客户</c:if>
								</td>
								<td>${traderCustomerVo.customerTypeStr }</td>
								<td>${traderCustomerVo.customerNatureStr }</td>
								<td>${traderCustomerVo.isVedengMember==1?"是":"否"}</td>
								<td>${traderCustomerVo.buyCount}</td>
								<td>${traderCustomerVo.buyMoney}</td>
								<td>${traderCustomerVo.quoteCount}</td>
								<td>${traderCustomerVo.communcateCount}</td>
								<td><c:if test="${traderCustomerVo.customerLevel eq 146}">A</c:if>
									<c:if test="${traderCustomerVo.customerLevel eq 147}">B</c:if>
									<c:if test="${traderCustomerVo.customerLevel eq 148}">C</c:if>
									<c:if test="${traderCustomerVo.customerLevel eq 149}">D</c:if>
									<c:if test="${traderCustomerVo.customerLevel eq 150}">E</c:if>
									<c:if test="${traderCustomerVo.customerLevel eq 931}">S</c:if>
								</td>
								<td>${traderCustomerVo.customerScore}</td>
								<td>${traderCustomerVo.partners}</td>
								<td><c:if
										test="${traderCustomerVo.basicMedicalDealer eq 0}">否</c:if> <c:if
										test="${traderCustomerVo.basicMedicalDealer eq 1}">是</c:if></td>
								<!-- 	<td><date:date value ="${traderCustomerVo.addTime} "/></td> -->
								<td>${traderCustomerVo.personal}</td>

								<td><date:date value="${traderCustomerVo.modTime} " /></td>
								<td>
									<c:if test="${empty traderCustomerVo.verifyStatus || traderCustomerVo.verifyStatus eq 3}">待审核</c:if>
									<c:if test="${traderCustomerVo.verifyStatus eq 0}">审核中</c:if>
									<c:if test="${traderCustomerVo.verifyStatus eq 1}">审核通过</c:if>
									<c:if test="${traderCustomerVo.verifyStatus eq 2}">审核不通过</c:if>
								</td>
								<td>
									<c:if test="${traderCustomerVo.isView eq 1}">
									<c:choose>
										<c:when test="${traderCustomerVo.isTop eq 0}">
											<span class="edit-user"
												onclick="stick(${traderCustomerVo.traderCustomerId},1);">置顶</span>
										</c:when>
										<c:otherwise>
											<span class="edit-user"
												onclick="stick(${traderCustomerVo.traderCustomerId},0);">取消置顶</span>
										</c:otherwise>
									</c:choose> <c:choose>
										<c:when test="${traderCustomerVo.isEnable eq 0}">
											<span class="edit-user"
												onclick="setDisabled(${traderCustomerVo.traderCustomerId},1);">启用</span>
										</c:when>
										<c:otherwise>
											<span class="forbid clcforbid pop-new-data"
												layerParams='{"width":"600px","height":"200px","title":"禁用","link":"./initDisabledPage.do?traderCustomerId=${traderCustomerVo.traderCustomerId}"}'>禁用</span>
										</c:otherwise>
									</c:choose>
									</c:if>
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
	src="<%=basePath%>/static/js/customer/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="<%=basePath%>/static/js/region/index.js?rnd=<%=Math.random()%>"></script>	
<%@ include file="../../../common/footer.jsp"%>