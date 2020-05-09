<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="消息推送" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/invoice/invoice_message_send.js?rnd=<%=Math.random()%>'></script>
<div class="formpublic formpublic1">
	<div>
		<div class="controlled">
			<ul class="searchTable">
				<table class="table table-bordered table-striped table-condensed table-centered mb10">
						<thead>
							<th class="wid5">选择</th>
							<th>发票号</th>
							<th>红蓝字</th>
							<th>发票金额</th>
							<th class="wid16">操作时间</th>
						</thead>
						<tbody>
							<c:forEach var="list" items="${invoiceList}" varStatus="num">
								<tr>
									<td><input type="checkbox" name="invoiceId" id="invoiceId${num.index}" value="${list.invoiceId}" onclick="checkInput(this);"/></td>
									<td>${list.invoiceNo}</td>
									<td>
										<c:choose>
											<c:when test="${list.colorType eq 1}">
												<c:choose>
													<c:when test="${list.isEnable eq 0}">
														<span style="color: red">红字作废</span>
													</c:when>
													<c:otherwise>
														<span style="color: red">红字有效</span>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${list.isEnable eq 0}">
														<span style="color: red">蓝字作废</span>
													</c:when>
													<c:otherwise>
															蓝字有效
														</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
									<td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
									<td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss" /></td>
								</tr>
							</c:forEach>
							<c:if test="${empty invoiceList}">
								<!-- 查询无结果弹出 -->
								<tr>
									<td colspan='9'>查询无结果！请尝试使用其他搜索条件。</td>
								</tr>
							</c:if>
						</tbody>
					</table>
			</div>
		</div>
		<div>
			<div class="table-style4 f_left" style="margin-top:-5px;">
				<div class="allchose">
					<input type="checkbox" style="margin: 3px 5px 0 7px;" name="checkOptAll" id="checkOptAll" onclick="checkAllOpt(this);"><span>全选</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="font-grey9 mb15">
					友情提醒：<br/>
					1、电子票在开具之后，系统会自动发送邮件和短信给该订单的收票联系人<br/>
					2、使用本功能前请与客户确认好的确未收到电子发票链接的短信和邮箱，避免重复发送给客户带来困扰<br/>
			</div>
		</div>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
			<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="sendInvoiceSms();">确认发送</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</div>
</div>

	<%-- <form method="post" id="invoiceMessageSendForm">
			<input type="hidden" name="formToken" id="formToken" value="${formToken}"/>
			<ul>
				<li>
					<div class="form-tips" style="width:41px;">
						<lable>发票号</lable>
					</div>
				</li>
				<li>
					<div class="f_left ">
						<div class="form-blanks" style="margin-left:50px;">
							<ul>
								<c:forEach var="list" items="${invoiceList}" varStatus="num">
									<li style="margin-bottom:10px;">
										<input type="checkbox" name="invoiceId" id="invoiceId${num.index}" value="${list.invoiceId}" />
										<span onclick="checkInput(${num.index})">${list.invoiceNo}</span>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</li>
			</ul>
			<div class="add-tijiao tcenter">
				<button type="button" class="bt-bg-style bg-light-green bt-small" onclick="sendInvoiceSms();">提交</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form> --%>
<%@ include file="../../common/footer.jsp"%>