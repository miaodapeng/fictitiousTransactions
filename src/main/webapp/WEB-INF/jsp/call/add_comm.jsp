<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增联系" scope="application" />
<%@ include file="./common/header.jsp"%>
<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<div class="left-title">
			<c:choose>
				<c:when test="${callOut.callFrom == 0 }">
					<i class="iconcalltele"></i>
				</c:when>
				<c:otherwise>
					<i class="iconcallout"></i>
				</c:otherwise>
			</c:choose>
			<span>新增联系</span>
		</div>
		<div class="right-title">
			<i class="iconclosetitle" id="close-title"></i>
		</div>
	</div>
	<form method="post" action="" id="myform">
		<!-- 表格信息 -->
		<div class="content-box">
			<div class="content-colum content-colum2">
				<div class="content-item call-add-linker">
					<table class="table table-td-border1 ">
						<tbody>
							<tr>
								<td class="wid10">客户名称</td>
								<td>${trader.traderName }</td>
							</tr>
							<tr>
								<td class="wid10">销售人员</td>
								<td><c:if test="${not empty contactList}">
										<c:forEach items="${contactList }" var="contact">
											<c:choose>
												<c:when
													test="${not empty callOut.traderContactId and callOut.traderContactId > 0 }">
													<c:if
														test="${callOut.traderContactId == contact.traderContactId }">
			                    			${contact.name }
			                    			</c:if>
												</c:when>
												<c:otherwise>
													<c:if
														test="${callOut.phone == contact.telephone || callOut.phone == contact.mobile || callOut.phone == contact.mobile2 }">
			                    			${contact.name }
			                    			</c:if>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if></td>
							</tr>
							<tr>
								<td class="wid10">联系电话</td>
								<td>${callOut.phone }</td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>联系人</td>
								<td><select class="mr5" name="traderContactId"
									id="traderContactId" style="width: 200px;">
										<option value="0">请选择</option>
										<c:if test="${not empty contactList }">
											<c:forEach items="${contactList }" var="contact">
												<option value="${contact.traderContactId }"
													<c:choose>
							                    		<c:when test="${not empty callOut.traderContactId and callOut.traderContactId > 0 }">
							                    			<c:if test="${callOut.traderContactId == contact.traderContactId }">
							                    			selected="selected"
							                    			</c:if>
							                    		</c:when>
							                    		<c:otherwise>
							                    			<c:if test="${callOut.phone == contact.telephone || callOut.phone == contact.mobile || callOut.phone == contact.mobile2 }">
							                    			selected="selected"
							                    			</c:if>
							                    		</c:otherwise>
							                    	</c:choose>>
													${contact.name }
													<c:if
														test="${contact.telephone !='' and contact.telephone != null }">|${contact.telephone }</c:if>
													<c:if
														test="${contact.mobile !='' and contact.mobile != null }">|${contact.mobile }</c:if>
													<c:if
														test="${contact.mobile2 !=''and contact.mobile2 != null}">|${contact.mobile2 }</c:if>
												</option>
											</c:forEach>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td class="wid10">订单类型</td>
								<td><select class="mr5" name="communicateType"
									id="communicateType" style="width: 200px;">
										<option value="0">请选择</option>
										<c:forEach items="${communicateTypeList }"
											var="communicateType">
											<c:if
												test="${communicateType.sysOptionDefinitionId != 242 && communicateType.sysOptionDefinitionId != 243 }">
												<option value="${communicateType.sysOptionDefinitionId }">${communicateType.title }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="wid10">选择订单</td>
								<td><select class="mr5" name="relatedId" id="relatedId" style="width: 200px;">
										<option value="0">请选择</option>
								</select></td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>沟通时间</td>
								<td><input class="Wdate input-large mr8" type="text"
									placeholder="请选择时间"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									name="begin" id="begin" /> <input class="Wdate input-large"
									type="text" placeholder="请选择时间"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									name="end" id="end" />
									<div id="endDiv"></div></td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>沟通目的</td>
								<td>
									<div class="inputfloat">
										<ul>
											<c:if test="${not empty communicateGoalList }">
												<c:forEach items="${communicateGoalList }" var="cm">
													<li><input type="radio" name="communicateGoal"
														checked="checked" value="${cm.sysOptionDefinitionId }" />
														<label class="mr8">${cm.title }</label></li>
												</c:forEach>
											</c:if>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td class="wid10">沟通方式</td>
								<td><c:choose>
										<c:when test="${callOut.callFrom == 0 }">
											<input type="radio" name="communicateMode" value="249"
												checked="checked" />
											<label class="mr8">来电</label>
										</c:when>
										<c:when test="${callOut.callFrom == 1 }">
											<input type="radio" name="communicateMode" value="250"
												checked="checked" />
											<label class="mr8">去电</label>
										</c:when>
										<c:otherwise>

										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>沟通内容</td>
								<td class="table-largest">
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
											placeholder="如果标签中没有您所需要的，请自行填写" class="input-large">
										<div
											class="f_left bt-bg-style bg-light-blue bt-small  addbrand"
											onclick="addDefineTag(this);">添加</div>
									</div>
									<div class="addtags mt6">
										<ul id="tag_show_ul">
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td class="wid10">下次沟通时间</td>
								<td><input class="Wdate input-large" type="text"
									placeholder="请选择日期"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="nextDate" />
								</td>
							</tr>
							<tr>
								<td class="wid10">下次沟通内容</td>
								<td><input type="text" class="input-xxx"
									name="nextContactContent" id="nextContactContent"></td>
							</tr>
							<tr>
								<td class="wid10">备注</td>
								<td><input type="text" class="input-xxx" name="comments"
									id="comments"></td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="hidden" name="traderId"
										value="${trader.traderId }">
								 	<input type="hidden" name="traderType"
									value="${callOut.traderType }"> <input type="hidden"
									name="coid" value="${callOut.coid }"> <input
									type="hidden" name="phone" value="${callOut.phone }"> <input
									type="hidden" name="callFrom" value="${callOut.callFrom }">
									<button class="bt-bg-style bt-small bg-light-green"
										type="button" id="submit">提交</button>
									<button class="bt-bg-style bt-small bg-light-red"
										id="close-layer" type="button">取消</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/add_comm.js?rnd=<%=Math.random()%>"></script>
<%@ include file="./common/footer.jsp"%>
