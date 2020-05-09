<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="沟通记录" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="supplier_tag.jsp"%>
<div class="content parts">
	<div class="title-container">
		<div class="table-title nobor">沟通记录</div>
		<c:if test="${supplierInfoByTraderSupplier.isEnable == 1 && ((supplierInfoByTraderSupplier.verifyStatus != null && supplierInfoByTraderSupplier.verifyStatus != 0 )|| supplierInfoByTraderSupplier.verifyStatus == null)}">
		<div class="title-click nobor"
			onclick="goUrl('${pageContext.request.contextPath}/trader/supplier/addcommunicate.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}');">
			新增</div>
		</c:if>
	</div>
	<div class="fixdiv">
		<div class="superdiv">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="table-smallestx">时间</th>
						<th  class="wid12">单号</th>
						<th>录音</th>
						<th>联系人</th>
						<th>联系方式</th>
						<th>沟通方式</th>
						<th>沟通目的</th>
						<th class="linebreak table-smaller">沟通内容</th>
						<th>操作人</th>
						<th class="table-smallestx">下次联系日期</th>
						<th class="linebreak table-smallest">下次沟通内容</th>
						<th class="linebreak table-smallest">备注</th>
						<th class="table-smallestx">创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty communicateRecordList}">
						<c:forEach items="${communicateRecordList }"
							var="communicateRecord">
							<tr>
								<td><date:date value="${communicateRecord.begintime} " />~<date:date
										value="${communicateRecord.endtime}" format="HH:mm:ss" /></td>
								<td>
									<c:choose>
										
										<c:when test="${communicateRecord.communicateType == 247 }">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${communicateRecord.relatedId}","title":"订单信息"}'>${communicateRecord.buyorderNo}</a>
										</c:when>
										<c:when test="${communicateRecord.communicateType == 248 }">
											<a class="addtitle" href="javascript:void(0);"
												tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${communicateRecord.relatedId}","title":"售后"}'>${communicateRecord.aftersalesNo}</a>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
								<td>${communicateRecord.contactName}</td>
								<td>${communicateRecord.phone}</td>
								<td>${communicateRecord.communicateModeName}</td>
								<td>${communicateRecord.communicateGoalName}</td>
								<td class="linebreak ">
									<ul class="communicatecontent ml0">
										<c:if test="${not empty communicateRecord.tag }">
											<c:forEach items="${communicateRecord.tag }" var="tag">
												<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
											</c:forEach>
										</c:if>
									</ul>
								</td>
								<td>${communicateRecord.user.username}</td>
								<c:choose>
									<c:when test="${communicateRecord.isDone == 0 }">
										<td class="font-red">${communicateRecord.nextContactDate }</td>
									</c:when>
									<c:otherwise>
										<td>${communicateRecord.nextContactDate }</td>
									</c:otherwise>
								</c:choose>
								<td>${communicateRecord.nextContactContent }</td>
								<td>${communicateRecord.comments}</td>
								<td><date:date value="${communicateRecord.addTime} " /></td>
								<td class="caozuo">
								<c:if test="${supplierInfoByTraderSupplier.isEnable == 1 }">
								<span class="caozuo-blue"
									onclick="goUrl('${pageContext.request.contextPath}/trader/supplier/editcommunicate.do?communicateRecordId=${communicateRecord.communicateRecordId}&traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}')">编辑</span>
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty communicateRecordList }">
						<!-- 查询无结果弹出 -->
						<td class="tcenter" colspan="14">查询无结果！</td>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<tags:page page="${page}" />
</div>
<%@ include file="../../common/footer.jsp"%>

