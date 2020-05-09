<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑管理信息" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="customer_tag.jsp"%>
<div class="baseinforcontainer" style="padding-bottom: 15px;">
	<div class="border">
		<div class="baseinfor f_left">管理信息</div>
		<div class="clear"></div>
	</div>
	<div class="baseinforeditform">
		<form method="post"
			action="${pageContext.request.contextPath}/trader/customer/saveeditmanageinfo.do"
			id="customerForm">
			<ul>
				<li class="bt" style="margin-bottom:0;">
					<div class="infor_name mt0">
						<span>*</span>
						<lable>客户来源</lable>
					</div>
					<div class="f_left inputfloat table-largest">
						<ul>
							<c:forEach items="${customerFrom}" var="option">
								<li><input type="radio" name="customerFrom"
									value="${option.sysOptionDefinitionId }"
									<c:if test="${option.sysOptionDefinitionId == traderCustomer.customerFrom}">checked</c:if> />
									<label class="mr8">${option.title }</label></li>
							</c:forEach>
						</ul>
					</div>
				</li>
				<li class="bt"  style="margin-bottom:0;">
					<div class="infor_name mt0">
						<span>*</span>
						<lable>首次询价方式</lable>
					</div>
					<div class="f_left inputfloat table-largest">
						<ul>
							<c:forEach items="${firstEnquiryType}" var="option">
								<li><input type="radio" name="firstEnquiryType"
									value="${option.sysOptionDefinitionId }"
									<c:if test="${option.sysOptionDefinitionId == traderCustomer.firstEnquiryType}">checked</c:if> />
									<label class="mr8">${option.title }</label></li>
							</c:forEach>
						</ul>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>销售评级</lable>
					</div>
					<div class="f_left inputfloat">
						<c:forEach items="${userEvaluate}" var="option">
							<input type="radio" name="userEvaluate"
								value="${option.sysOptionDefinitionId }"
								<c:if test="${option.sysOptionDefinitionId == traderCustomer.userEvaluate}">checked</c:if> />
							<label class="mr30">${option.title }</label>
						</c:forEach>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>贝登VIP</lable>
					</div>
					<div class="f_left inputfloat">
						<input type="radio" name="isVip" value="1"
							<c:if test="${traderCustomer.isVip == 1}">checked</c:if> /> <label
							class="mr30">是</label> <input type="radio" name="isVip" value="0"
							<c:if test="${traderCustomer.isVip == 0}">checked</c:if> /> <label
							class="mr30">否</label>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>战略合作伙伴</lable>
					</div>
					<div class="f_left inputfloat">
						<c:forEach items="${zlhz}" var="option">
							<input name="attributeId" type="checkbox"
								value="${option.sysOptionDefinitionId }"
								<c:forEach items="${traderCustomer.traderCustomerAttributes }" var="attr">
                        		<c:if test="${attr.attributeId == option.sysOptionDefinitionId}">checked</c:if>
                        	</c:forEach> />
							<label class="mr30">${option.title }</label>
						</c:forEach>
					</div>
				</li>
				<li>
					<div class="infor_name <c:if test="${not empty tagList }">mt4</c:if>"  <c:if test="${ empty tagList }">style="margin-top:0;"</c:if>>
						<lable>客户标签</lable>
					</div>
					<div class="f_left table-largest">
						<div class="inputfloat manageaddtag">
							<label class="<c:if test="${not empty tagList }">mt4</c:if> mr8 mb8">您可以从这些标签中选择</label>
							<c:if test="${not empty tagList }">
								<c:forEach items="${tagList }" var="tag">
									<span onclick="addTag(${tag.tagId},'${tag.tagName }',this);">${tag.tagName }</span>
								</c:forEach>
							</c:if>
							<c:if test="${page.totalPage > 1}">
								<div class="change"
									onclick="changeTag(${page.totalPage},10,this,30);">
									<span class="m0">换一批(</span><span class="m0" id="leftNum">${page.totalPage}</span><span
										class="m0">)</span> <input type="hidden" id="pageNo"
										value="${page.pageNo}">
								</div>
							</c:if>
						</div>
						<div class="inputfloat ">
							<input type="text" id="defineTag"
								placeholder="如果标签中没有您所需要的，请自行填写" class="input-large ">
							<div class="f_left bt-bg-style bg-light-blue bt-small  addbrand"
								onclick="addDefineTag(this);">添加</div>
						</div>
						<div
							class="addtags mt6 <c:if test="${empty traderCustomer.tag }">none</c:if>">
							<ul id="tag_show_ul">
								<c:if test="${not empty traderCustomer.tag}">
									<c:forEach items="${traderCustomer.tag }" var="tag">
										<li class="bluetag">${tag.tagName}<input type="hidden"
											name="tagId" alt="${tag.tagName}" value="${tag.tagId }"><i
											class="iconbluecha" onclick="delTag(this);"></i></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>备注</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx" name="comments" id="comments"
							value="${traderCustomer.comments }" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>物流信息备注</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx" name="logisticsComments"
							id="logisticsComments"
							value="${traderCustomer.logisticsComments }" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>财务信息备注</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx" name="financeComments"
							id="financeComments" value="${traderCustomer.financeComments }" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>简介</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="brief" id="brief">${traderCustomer.brief }</textarea>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>历史背景</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="history" id="history">${traderCustomer.history }</textarea>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>现在生意情况</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="businessCondition"
							id="businessCondition">${traderCustomer.businessCondition }</textarea>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>公司发展计划</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="businessPlan" id="businessPlan">${traderCustomer.businessPlan }</textarea>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>公司优势</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="advantage" id="advantage">${traderCustomer.advantage }</textarea>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>面临主要问题</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="primalProblem"
							id="primalProblem">${traderCustomer.primalProblem }</textarea>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="traderId"
					value="${traderCustomer.traderId }"> <input type="hidden"
					name="traderCustomerId" value="${traderCustomer.traderCustomerId }">
				<input type="hidden" name="beforeParams" value='${beforeParams}'/>
				<button type="submit">提交</button>
				<button type="button" class="dele"
					onclick="goUrl('${pageContext.request.contextPath}/trader/customer/manageinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}')">取消</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/edit_manageinfo.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
