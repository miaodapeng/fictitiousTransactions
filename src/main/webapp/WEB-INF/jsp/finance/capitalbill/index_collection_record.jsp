<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="收款记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/capitalbill/index_collection_record.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		
		/* var	url = page_url + '/order/saleorder/view.do?saleorderId='+saleorderId;
		if($(window.frameElement).attr('src').indexOf("saleorder/view")<0){
			$(window.frameElement).attr('data-url',url);
		} */
		var saleOrderIdArr = [];
		$("input[id^='buyAmount_").each(function(i){
			saleOrderIdArr.push($(this).val());
		});
		if(saleOrderIdArr == "" || saleOrderIdArr.length <= 0){
			return false;
		}
		//补订单产品详情相关数据
		$.ajax({
			async:true,
			url:page_url+'/finance/capitalbill/getCollectionRecordInfoAjax.do',
			data:{"saleOrderIdArr":JSON.stringify(saleOrderIdArr)},//销售订单对应的采购订单总额（采购订单商品单价*销售数量）
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					for (var i = 0; i < data.data.length; i++) {
						if(data.data[i].buyAmount != undefined && data.data[i].buyAmount != null && data.data[i].buyAmount > 0){
							$("div [id=buyTotleAmount_"+data.data[i].relatedId+"]").each(function(){
								$(this).html((data.data[i].buyAmount).toFixed(2));
							})
						}
					}
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	})
</script>
	<div class="content">
		<div class="searchfunc">		
			<form action="${pageContext.request.contextPath}/finance/capitalbill/getCollectionRecordListPage.do" method="post" id="search">
				<ul>
					<li>
	                    <label class="infor_name">订单号</label>
	                    <input type="text" class="input-middle" name="capitalBillDetail.orderNo" id="" value="${capitalBill.capitalBillDetail.orderNo}">
	                </li>
	                <li>
	                    <label class="infor_name">客户名称</label>
	                    <input type="text" class="input-middle" name="saleorder.traderName" id="" value="${capitalBill.saleorder.traderName}">
	                </li>
	                <li>
	                    <label class="infor_name">交易名称</label>
	                    <input type="text" class="input-middle" name="payer" id="" value="${capitalBill.payer}">
	                </li>
	                <li>
	                    <label class="infor_name">交易备注</label>
	                    <input type="text" class="input-middle" name="comments" id="" value="${capitalBill.comments}">
	                </li>
                    <li>
	                    <label class="infor_name">交易主体</label>
	                    <select class="input-middle f_left" name="traderSubject">
	                    	<option value="-1">全部</option>
	                    	<option value="1" <c:if test="${capitalBill.traderSubject == 1}">selected="selected"</c:if> >对公</option>
	                    	<option value="2" <c:if test="${capitalBill.traderSubject == 2}">selected="selected"</c:if> >对私</option>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">交易方式</label>
	                    <select class="input-middle f_left" name="traderMode">
	                    	<option value="-1">全部</option>
	                    	<c:forEach var="list" items="${traderModeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${capitalBill.traderMode == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
                    <li>
	                    <label class="infor_name">业务类型</label>
	                    <select class="input-middle f_left" name="capitalBillDetail.bussinessType">
	                    	<option value="-1">全部</option>
		                    <c:forEach var="list" items="${bussinessTypeList}">
		                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${capitalBill.capitalBillDetail.bussinessType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                    </c:forEach>
	                    </select>
                    </li>
	                <li>
						<label class="infor_name">销售部门</label>
						<select class="input-middle" name="orgId" id="orgId">
							<option value="-1">全部</option>
							<c:forEach items="${orgList}" var="org">
								<option value="${org.orgId}" <c:if test="${capitalBill.orgId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">归属销售</label>
						<select class="input-middle" name="optUserId" id="optUserId">
							<option value="-1">全部</option>
							<c:forEach items="${userList}" var="list">
								<option value="${list.userId}" <c:if test="${capitalBill.optUserId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
							</c:forEach>
						</select>
					</li>
	                <li>
						<label class="infor_name">发货状态</label>
						<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
							<option value="-1">全部</option>
							<option <c:if test="${capitalBill.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
							<option <c:if test="${capitalBill.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
							<option <c:if test="${capitalBill.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
						</select>
					</li>
					<li>
						<label class="infor_name">到货状态</label>
						<select class="input-middle" name="arrivalStatus" id="arrivalStatus">
							<option value="-1">全部</option>
							<option <c:if test="${capitalBill.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
							<option <c:if test="${capitalBill.arrivalStatus eq 1}">selected</c:if> value="1">部分收货</option>
							<option <c:if test="${capitalBill.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
						</select>
					</li>
					<li>
						<label class="infor_name">交易金额</label>
						<input class="f_left input-smaller96 mr5" type="text" name="searchBeginAmount" id="searchBeginAmount" value='${capitalBill.searchBeginAmount}'>
	                    <div class="gang">-</div>
	                    <input class="f_left input-smaller96" type="text" name="searchEndAmount" id="searchEndAmount" value='${capitalBill.searchEndAmount}'>
					</li>
	                <li>
						<div class="infor_name">
							交易时间
						</div>
						<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<c:choose>
							<c:when test="${capitalBill.searchBegintime > 0}">
								<date:date value ="${capitalBill.searchBegintime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${capitalBill.searchBegintime == null}">
							</c:when>
							<c:otherwise>
								${pre1MonthDate}
							</c:otherwise>
						</c:choose>'>
	                    <div class="gang">-</div>
	                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<c:choose>
							<c:when test="${capitalBill.searchEndtime > 0}">
								<date:date value ="${capitalBill.searchEndtime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${capitalBill.searchEndtime == null}">
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
					</li>
					
            	</ul>
            	<div class="tcenter">
            		<input type="hidden" name="pre1MonthDate" value="${pre1MonthDate}">
            		<input type="hidden" name="nowDate" value="${nowDate}">
	                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	                <span class="bt-small bg-light-blue bt-bg-style" onclick="searchReset();">重置</span>
            		<!-- <span class="bg-light-blue bt-bg-style bt-small" onclick="exportCapitalBillList()">导出列表</span>
            		<span class="bg-light-blue bt-bg-style bt-small" onclick="exportCapitalDetailList()">导出详细</span> -->
            	</div>
			</form>
		</div>
		<div class="fixdiv">
        <div class="superdiv" style="width:2000px;">
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
                        <th class="wid11">订单号</th>
                        <th class="">客户名称</th>
                        <th class="">部门</th>
                        <th class="">归属销售</th>
                        <th class="">订单金额</th>
                        <th class="wid10">订单实际金额</th>
                        <th class="wid8">已到款</th>
                        <th class="wid8">本次到款</th>
                        <th>交易名称</th>
                        <th class="wid8">交易主体</th>
                        <th class="wid8">交易方式</th>
                        <th class="wid8">业务类型</th>
                        <th>交易备注</th>
                        <th class="wid8">到款时间</th>
                        <th class="wid10">操作人员</th>
                        <th class="wid8">发货状态</th>
                        <th class="wid8">发货时间</th>
                        <th class="wid8">收货状态</th>
                        <th class="wid8">收货时间</th>
                        <th class="">采购总额</th>
                        <th class="wid11">关联单号</th>
					</tr>
				</thead>
			
				<tbody class="goods">
				<c:if test="${not empty capitalBillList}">
					<c:set var="pageNum" value="0"></c:set>
					<c:set var="pageAmount2" value="0.00"></c:set>
                	<c:forEach items="${capitalBillList}" var="list" varStatus="status">
                		<c:set var="pageNum" value="${status.count}"></c:set>
						<c:set var="pageAmount2" value="${pageAmount2 + list.amount}"></c:set>
	                    <tr>
	                        <td>
	                        	<c:if test="${list.operationType eq 1}">
                        			<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
                        				"link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.orderNo}</a>
                        		</c:if>
	                        	<c:if test="${list.operationType eq 2}">
                        			<span class="font-blue addtitle" tabtitle='{"num":"view_invoice_after${list.relatedId}",
										"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedId}","title":"财务售后订单"}'>
										${list.orderNo}
									</span>
                        		</c:if>
	                        </td>
	                        <td>
	                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewcustomer${list.traderId}",
									"link":"./trader/customer/baseinfo.do?traderId=${list.traderId}","title":"客户信息"}'>${list.traderName}</a>
	                        </td>
	                        <td>${list.salesDeptName}</td>
	                        <td>${list.optUserName}</td>
	                        <td><fmt:formatNumber type="number" value="${list.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.realAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.orderPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>${list.payer}</td>
	                        <td>
	                        	<c:if test="${list.traderSubject eq 1}">对公</c:if>
	                        	<c:if test="${list.traderSubject eq 2}">对私</c:if>
	                        </td>
	                        <td>
	                        	<c:forEach var="modeList" items="${traderModeList}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>
	                        	<c:forEach var="typeList" items="${bussinessTypeList}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq list.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>${list.comments}</td>
	                        <td><date:date value ="${list.traderTime}"/></td>
	                        <td>${list.creatorName}</td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.deliveryStatus eq 0}">
										未发货
									</c:when>
									<c:when test="${list.deliveryStatus eq 1}">
										部分发货
									</c:when>
									<c:when test="${list.deliveryStatus eq 2}">
										全部发货
									</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>
	                        	<c:if test="${list.deliveryTime > 0 }">
	                        	<date:date value ="${list.deliveryTime}"/>
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.arrivalStatus eq 0}">
										未收货
									</c:when>
									<c:when test="${list.arrivalStatus eq 1}">
										部分收货
									</c:when>
									<c:when test="${list.arrivalStatus eq 2}">
										全部收货
									</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>
	                        	<c:if test="${list.arrivalTime > 0 }">
	                        	<date:date value ="${list.arrivalTime}"/>
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.operationType eq 1}"><!-- 销售（售后订单无成本） -->
			                        	<input type="hidden" id="buyAmount_${list.relatedId}" value="${list.relatedId}"/>
			                        	<div id="buyTotleAmount_${list.relatedId}">
				                        	<fmt:formatNumber type="number" value="${list.buyAmount}" pattern="0.00" maxFractionDigits="2" />
			                        	</div>
	                        		</c:when>
	                        		<c:otherwise>
	                        			0.00
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
	                    	<td>
	                    		<!-- operationType:1此条记录是销售，关联单号为售后，反之：2，此条记录是售后，关联单号为销售 -->
	                    		<c:if test="${list.operationType eq 2}">
                        			<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewinvoicesaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
                        				"link":"./finance/invoice/viewSaleorder.do?saleorderId=${list.relatedOrderId}","title":"订单信息"}'>${list.relatedOrderNo}</a>
                        		</c:if>
	                        	<c:if test="${list.operationType eq 1}">
                        			<span class="font-blue addtitle" tabtitle='{"num":"view_invoice_after${list.relatedOrderId}",
										"link":"./finance/after/getFinanceAfterSaleDetail.do?afterSalesId=${list.relatedOrderId}","title":"财务售后订单"}'>
										${list.relatedOrderNo}
									</span>
                        		</c:if>
	                    	</td>
	                    </tr>
                	</c:forEach>
        		</c:if>
				</tbody>
			</table>
			
			<c:if test="${empty capitalBillList}">
				<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			</div>
		</div>
		<div>
			<tags:page page="${page}"/>
			<div class="clear"></div>
			<c:if test="${not empty capitalBillList}">
				<div class="fixtablelastline">
					【全部结果 订单数：${page.totalRecord} 已到款总额：<fmt:formatNumber type="number" value="${capitalBill.orderPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 本次到款总额：<fmt:formatNumber type="number" value="${capitalBill.capitalBillCollectionAmount}" pattern="0.00" maxFractionDigits="2" />】
					【本页统计 订单数：${pageNum} 已到款总额：<fmt:formatNumber type="number" value="${capitalBill.thisPaymentTotalAmount}" pattern="0.00" maxFractionDigits="2" /> 本次到款总额：<fmt:formatNumber type="number" value="${pageAmount2}" pattern="0.00" maxFractionDigits="2" />】
				</div>
			</c:if>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>