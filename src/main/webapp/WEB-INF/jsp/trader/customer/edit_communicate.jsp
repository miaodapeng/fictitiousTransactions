<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑沟通记录" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="customer_tag.jsp"%>
<div class="baseinforcontainer" style="padding-bottom: 15px;">
	<div class="border">
		<div class="baseinfor f_left">沟通记录</div>
		<div class="clear"></div>
	</div>
	<div class="baseinforeditform">
		<form method="post"
			action="${pageContext.request.contextPath}/trader/customer/saveeditcommunicate.do"
			id="form">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>联系人</lable>
					</div>
					<div class="f_left  ">
						<select class="mr5" name="traderContactId" id="traderContactId">
							<option selected="selected" value="0">请选择</option>
							<c:if test="${not empty contactList }">
								<c:forEach items="${contactList }" var="contact">
									<option value="${contact.traderContactId }"
										<c:if test="${contact.traderContactId == communicateRecord.traderContactId }">selected="selected" </c:if>>
										${contact.name }
										<c:if
											test="${contact.telephone !='' and contact.telephone != null }">|${contact.telephone }</c:if>
										<c:if
											test="${contact.mobile !='' and contact.mobile != null }">|${contact.mobile }</c:if>
										<c:if
											test="${contact.mobile2 !=''and contact.mobile2 != null}">|${contact.mobile2 }</c:if>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>沟通时间</lable>
					</div>
					<div class="f_left inputfloat ">
						<input class="Wdate input-small mr0" type="text"
							placeholder="请选择时间"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end\')}'})"
							name="begin" id="begin"
							value="<date:date value ="${communicateRecord.begintime} "/>" />
						<div class="gang">-</div>
						<input class="Wdate input-small ml5" type="text" placeholder="请选择时间"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'begin\')}',maxDate:'#F{$dp.$D(\'nextDate\')'})" name="end"
							id="end"
							value="<date:date value ="${communicateRecord.endtime} "/>" />
							<input type="hidden" name="beforeParams" value='${beforeParams}'>
					</div>
				</li>
				<li class="bt">
					<div class="infor_name mt0">
						<span>*</span>
						<lable>沟通目的</lable>
					</div>
					<div class="f_left inputfloat table-largest">
						<ul id="communicateGoalDiv">
							<c:if test="${not empty communicateGoal }">
								<c:forEach items="${communicateGoal }" var="goal">
									<c:if test="${goal.sysOptionDefinitionId != 271 }">
										<li><input type="radio" name="communicateGoal"
											value="${goal.sysOptionDefinitionId }"
											<c:if test="${communicateRecord.communicateGoal== goal.sysOptionDefinitionId}">
		                                checked="checked"
		                                </c:if> />
											<label class="mr8">${goal.title }</label></li>
									</c:if>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</li>
				<li class="bt">
					<div class="infor_name mt0">
						<span>*</span>
						<lable>沟通方式</lable>
					</div>
					<div class="f_left inputfloat table-largest">
						<ul id="communicateModeDiv">
							<c:if test="${not empty communicateMode }">
								<c:forEach items="${communicateMode }" var="mode">
									<li><input type="radio" name="communicateMode"
										value="${mode.sysOptionDefinitionId }"
										<c:if test="${communicateRecord.communicateMode== mode.sysOptionDefinitionId}">
		                                checked="checked"
		                                </c:if> />
										<label class="mr8">${mode.title }</label></li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>沟通内容</lable>
					</div>
					<div class="f_left table-largest">
						<div class="inputfloat manageaddtag">
							<label class="mt4 mr8">您可以从这些标签中选择</label>
							<c:if test="${not empty tagList }">
								<c:forEach items="${tagList }" var="tag">
									<span onclick="addTag(${tag.tagId},'${tag.tagName }',this);">${tag.tagName }</span>
								</c:forEach>
							</c:if>
							<c:if test="${page.totalPage > 1}">
								<div class="change"
									onclick="changeTag(${page.totalPage},10,this,32);">
									<span class="m0">换一批(</span><span class="m0" id="leftNum">${page.totalPage}</span><span
										class="m0">)</span> <input type="hidden" id="pageNo"
										value="${page.pageNo}">
								</div>
							</c:if>
						</div>
						<div class="inputfloat <c:if test="${empty tagList }">mt8</c:if>">
							<input type="text" id="defineTag"
								placeholder="如果标签中没有您所需要的，请自行填写" class="input-large " style='height:26px;'>
							<div class="f_left bt-bg-style bg-light-blue bt-small  addbrand"
								onclick="addDefineTag(this);">添加</div>
						</div>
						<div class="addtags mt6 <c:if test='${empty communicateRecord.tag }'>none</c:if>">
							<ul id="tag_show_ul">
								<c:if test="${not empty communicateRecord.tag }">
									<c:forEach items="${communicateRecord.tag}" var="tag">
										<li class="bluetag" title="${tag.tagName}">${tag.tagName}<input type="hidden"
											name="tagId" alt="${tag.tagName}" value="${tag.tagId }"><i
											class="iconbluecha" onclick="delTag(this);"></i></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name">
						<lable>下次沟通时间</lable>
					</div>
					<div class="f_left ">
						<input class="Wdate input-small" type="text" placeholder="请选择日期"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="nextDate" id="nextDate"
							value="${communicateRecord.nextContactDate }" />
					</div>
				</li>
				<li>
					<div class="infor_name">
						<lable>下次沟通内容</lable>
					</div>
					<div class="f_left  ">
						<input type="text" class="input-xxx" name="nextContactContent"
							id="nextContactContent"
							value="${communicateRecord.nextContactContent }">
					</div>
				</li>
				<li>
					<div class="infor_name">
						<lable>备注</lable>
					</div>
					<div class="f_left  ">
						<input type="text" class="input-xxx" name="comments" id="comments"
							value="${communicateRecord.comments }">
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter ">
				<input type="hidden" name="traderType" value="1">
				<input type="hidden" name="communicateRecordId"
					value="${communicateRecord.communicateRecordId }"> <input
					type="hidden" name="traderId"
					value="${communicateRecord.traderId }"> <input
					type="hidden" name="traderCustomerId"
					value="${communicateRecord.traderCustomerId }">
				<input type="hidden" name="beforeParams" value='${beforeParams}'/>
				<button type="submit">提交</button>
				<button class="dele" id="close-layer" type="button"
					onclick="goUrl('${pageContext.request.contextPath}/trader/customer/communicaterecord.do?traderId=${communicateRecord.traderId}&traderCustomerId=${communicateRecord.traderCustomerId}')">取消</button>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/add_communicate.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
