<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="报价列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>static/js/order/quote/index_quote.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
		<form method="post" id="search" action="<%=basePath%>order/quote/index.do">
			<ul>
				<li>
					<label class="infor_name">报价单号</label> 
					<input type="text" class="input-middle" name="quoteorderNo" id="quoteorderNo" value="${quote.quoteorderNo}" />
				</li>
				<li>
					<label class="infor_name">生效状态</label> 
					<select class="input-middle" name="validStatus" id="validStatus">
						<option value="">全部</option>
						<option <c:if test="${quote.validStatus eq 0}">selected</c:if> value="0">未生效</option>
						<option <c:if test="${quote.validStatus eq 1}">selected</c:if> value="1">已生效</option>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus">
						<option value="">全部</option>
						<option <c:if test="${quote.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
						<option <c:if test="${quote.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
						<option <c:if test="${quote.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
						<option <c:if test="${quote.verifyStatus eq 2}">selected</c:if> value="2">审核不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">跟单状态</label> 
					<select class="input-middle" name="followOrderStatus" id="followOrderStatus">
						<option value="-1">全部</option>
						<option	<c:if test="${quote.followOrderStatus eq 0}">selected</c:if> value="0">跟单中</option>
						<option <c:if test="${quote.followOrderStatus eq 1}">selected</c:if> value="1">已成单</option>
						<option	<c:if test="${quote.followOrderStatus eq 2}">selected</c:if> value="2">已失单</option>
					</select>
				</li>
				<li>
					<label class="infor_name">客户名称</label> 
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${quote.traderName}" />
				</li>
				<li>
					<label class="infor_name">客户性质</label> 
					<select class="input-middle" name="customerNature" id="customerNature">
						<option value="">全部</option>
						<c:forEach items="${customerNatureList}" var="cnl">
							<option value="${cnl.sysOptionDefinitionId}"
								<c:if test="${quote.customerNature eq cnl.sysOptionDefinitionId}">selected="selected"</c:if>>${cnl.title}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">销售部门</label> 
					<select class="input-middle" name="orgId" id="orgId">
						<!-- onchange="getDeptUser(this);" -->
						<option value="">全部</option>
						<c:forEach items="${orgList}" var="org">
							<option value="${org.orgId}"
								<c:if test="${quote.orgId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<%-- <li>
					<label class="infor_name">销售人员</label>
					<select class="input-middle" name="userId" id="userId">
						<option value="">全部</option>
						<c:forEach var="list" items="${quoteUserList}" varStatus="status">
							<option value="${list.userId}" <c:if test="${quote.userId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li> --%>
				<li>
					<label class="infor_name">订货号</label> 
					<input type="text" class="input-middle" name="sku" id="sku" value="${quote.sku}" />
				</li>
				<li>
					<label class="infor_name">产品名称</label> 
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${quote.goodsName}" />
				</li>
				<li>
					<label class="infor_name">品牌</label> 
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${quote.brandName}" />
				</li>
				<li>
					<label class="infor_name">型号</label> 
					<input type="text" class="input-middle" name="model" id="model" value="${quote.model}" />
				</li>
				<li>
					<label class="infor_name">联系人信息</label> 
					<input type="text" class="input-middle" name="traderContact" id="traderContact" placeholder="联系人姓名/电话/手机" value="${quote.traderContact}" />
				</li>
				<li>
					<label class="infor_name">有沟通</label> 
					<select class="input-middle" name="haveCommunicate" id="haveCommunicate">
						<option value="">全部</option>
						<option <c:if test="${quote.haveCommunicate eq 0}">selected</c:if> value="0">无</option>
						<option <c:if test="${quote.haveCommunicate eq 1}">selected</c:if> value="1">有</option>
					</select>
				</li>
				<li>
					<label class="infor_name">咨询答复</label> 
					<select class="input-middle" name="consultStatus" id="consultStatus">
						<option value="">全部</option>
						<option <c:if test="${quote.consultStatus eq 1}">selected</c:if> value="1">未处理</option>
						<option <c:if test="${quote.consultStatus eq 2}">selected</c:if> value="2">处理中</option>
						<option <c:if test="${quote.consultStatus eq 3}">selected</c:if> value="3">已处理</option>
					</select>
				</li>
				<li>
					<label class="infor_name">报价来源</label>
					<select class="input-middle" name="sourceQuae" id="sourceQuae">
						<option value="">全部</option>
						<option value="BD" <c:if test="${quote.sourceQuae == 'BD'}">selected</c:if>>订单</option>
						<option value="VD" <c:if test="${quote.sourceQuae =='VD'}">selected</c:if>>商机</option>

					</select>
				</li>
				<li>
					<label class="infor_name">归属销售</label> 
					<select class="input-middle" name="optUserId" id="optUserId">
						<option value="">全部</option>
						<c:forEach items="${userList}" var="list">
							<option value="${list.userId}"
								<c:if test="${quote.optUserId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">商机来源</label> 
					<select class="input-middle" name="source" id="source">
						<option value="">全部</option>
						<c:forEach items="${bussSource}" var="buss">
							<option value="${buss.sysOptionDefinitionId}"
								<c:if test="${quote.source eq buss.sysOptionDefinitionId}">selected="selected"</c:if>>${buss.title}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="timeType" id="timeType">
							<option value="3" <c:if test="${quote.timeType eq 3}">selected</c:if>>创建时间</option>
							<option value="1" <c:if test="${quote.timeType eq 1}">selected</c:if>>生效时间</option>
							<option value="2" <c:if test="${quote.timeType eq 2}">selected</c:if>>沟通时间</option>
							<option value="4" <c:if test="${quote.timeType eq 4}">selected</c:if>>成单时间</option>
							<option value="5" <c:if test="${quote.timeType eq 5}">selected</c:if>>失单时间</option>
						</select>
					</div> 
					<input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="beginTime" value="${beginTime}">
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endTime" value="${endTime}">
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue"	onclick="search();" id="searchSpan">搜索</span> 
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportQuote()">导出列表</span>  -->
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportQuoteDetail()">导出报价明细</span>  -->
				<!-- <span class="bt-small bg-light-blue bt-bg-style addtitle"
					tabtitle="{&quot;num&quot;:&quot;user&quot;,&quot;link&quot;:&quot;./order/quote/addQuote.do?traderId=586&bussinessChanceId=100&quot;,&quot;title&quot;:&quot;新增报价&quot;}">测试新增报价</span> -->
			</div>
		</form>
	</div>
	<div class="list-page">
		<div class="fixdiv">
			<div class="superdiv">
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid6">序号</th>
							<th class="wid14">报价单号</th>
							<th class="wid14">报价来源</th>
							<th class="wid18">创建时间</th>
							<th>客户名称</th>
							<th class="wid10">客户性质</th>
							<th class="wid10">新/老客户</th>
							<th class="wid10">客户等级</th>
							<th class="table-smallest6">报价金额</th>
							<th class="table-smallest10">销售部门</th>
							<th class="table-smallest5">销售人员</th>
							<th class="table-smallest5">归属销售</th>
							<th class="wid10">沟通次数</th>
							<th class="wid10">生效状态</th>
							<th class="wid10">审核状态</th>
							<th class="wid10">跟单状态</th>
							<th class="wid10">咨询答复</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${quoteList}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td class="text-left">
									<div class="font-blue">
										<c:choose>
											<c:when test="${list.followOrderStatus eq 0}">
												<span class="greencircle"></span>
											</c:when>
											<c:when test="${list.followOrderStatus eq 1}">
												<span class="bluecircle"></span>
											</c:when>
											<c:when test="${list.followOrderStatus eq 2}">
												<span class="greycircle"></span>
											</c:when>
											<c:otherwise>
												<span class="orangecircle"></span>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${list.validStatus eq 0}">

												<c:set var="shenhe" value="0"></c:set>
												<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
													<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
														<c:if test="${verifyUsernameInfo == loginUser.username}">
															<c:set var="shenhe" value="1"></c:set>
														</c:if>
													</c:forEach>
												</c:if>
												<a class="addtitle"
												   tabtitle="{&quot;num&quot;:&quot;viewQuote2<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&viewType=2&quot;,&quot;title&quot;:&quot;编辑报价&quot;}">${list.quoteorderNo}</a>${list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
												<%--<a class="addtitle"
													tabtitle="{&quot;num&quot;:&quot;viewQuote2<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link &quot;:&quot;${pageContext.request.contextPath}/order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&viewType=2&quot;,&quot;title&quot;:&quot;编辑报价&quot;}">${list.quoteorderNo}</a>${list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
--%>
												<!-- 未生效 -->
												<%-- <c:choose>
													<c:when test="${list.paymentType eq 0}">
														<!-- 无付款方式 -->
														<a class="addtitle"
															tabtitle="{&quot;num&quot;:&quot;viewQuote<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&quoteSource=edit1&quot;,&quot;title&quot;:&quot;修改报价&quot;}">${list.quoteorderNo}</a>${shenhe == 1 ?"<font color='red'>[审]</font>":""}
													</c:when>
													<c:otherwise>
														<a class="addtitle"
															tabtitle="{&quot;num&quot;:&quot;viewQuote2<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&viewType=2&quot;,&quot;title&quot;:&quot;编辑报价&quot;}">${list.quoteorderNo}</a>${shenhe == 1 ?"<font color='red'>[审]</font>":""}
													</c:otherwise>
												</c:choose> --%>
											</c:when>
											<c:otherwise>
												<a class="addtitle"
													tabtitle="{&quot;num&quot;:&quot;viewQuote3<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.quoteorderId}&viewType=3&quot;,&quot;title&quot;:&quot;报价信息&quot;}">${list.quoteorderNo}</a>
											</c:otherwise>
										</c:choose>
									</div>
								</td>
								<td><c:if test="${fn:substring(list.quoteorderNo,0,2) == 'VD'}">商机</c:if>
									<c:if test="${fn:substring(list.quoteorderNo,0,2) == 'BD'}">订单</c:if>
								</td>
								<td><date:date value="${list.addTime}" /></td>
								<td>
									<div class="font-blue">
										<a class="addtitle" href="javascript:void(0);"
											tabtitle='{"num":"view_quote_customer${list.traderId}",
											"link":"./trader/customer/baseinfo.do?traderId=${list.traderId}","title":"客户信息"}'>${list.traderName}</a>
									</div>
								</td>
								<td>${list.customerNatureStr}</td>
								<td><c:choose>
										<c:when test="${list.isNewCustomer eq 0}">
											否
										</c:when>
										<c:when test="${list.isNewCustomer eq 1}">
											是
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose></td>
								<td>${list.customerLevel}</td>
								<td>${list.totalAmount}</td>
								<td>${list.salesDeptName}</td>
								<td>${list.salesName}</td>
								<td>${list.optUserName}</td>
								<td>${list.communicateNum}</td>
								<td>
									<c:choose>
										<c:when test="${list.validStatus eq 0}">
											<span style="color: red">未生效</span>
										</c:when>
										<c:when test="${list.validStatus eq 1}">
											已生效
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.verifyStatus eq 0}">
											审核中
										</c:when>
										<c:when test="${list.verifyStatus eq 1}">
											审核通过
										</c:when>
										<c:when test="${list.verifyStatus eq 2}">
											审核不通过
										</c:when>
										<c:otherwise>
											待审核
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.followOrderStatus eq 0}">
											<span style="color: red">跟单中</span>
										</c:when>
										<c:when test="${list.followOrderStatus eq 1}">
											已成单
										</c:when>
										<c:when test="${list.followOrderStatus eq 2}">
											已失单
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.consultStatus eq 0}">
											无
										</c:when>
										<c:when test="${list.consultStatus eq 1}">
											<span style="color: red">未处理</span>
										</c:when>
										<c:when test="${list.consultStatus eq 2}">
											<span style="color: red">处理中</span>
										</c:when>
										<c:when test="${list.consultStatus eq 3}">
											已处理
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty quoteList}">
							<tr>
								<td colspan="16">
									<!-- 查询无结果弹出 --> 查询无结果！请尝试使用其他搜索条件。
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<tags:page page="${page}" />
	</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
