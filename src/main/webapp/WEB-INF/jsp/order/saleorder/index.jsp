<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${salerorderOnline eq 1 }">
	<c:set var="title" value="线上订单" scope="application" />
</c:if>
<c:if test="${saleorder.orderType eq 3}">
	<c:set var="title" value="订货列表" scope="application" />
</c:if>
<c:if test="${saleorder.orderType eq 0 or saleorder.orderType eq 1}">
	<c:set var="title" value="销售列表" scope="application" />
</c:if>
<c:if test="${salerorderIndex eq 1}">
	<c:set var="title" value="销售列表" scope="application" />
</c:if>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
    //form表单重置
    function reset(){
        $("form").find("input[type='text']").val('');

        $.each($("form select"),function(i,n){
            $(this).children("option:first").prop("selected",true);
        });
        //客户性质重置
        if($("select[name='customerProperty']") != undefined){
            $("select[name='customerProperty'] option:gt(0)").remove();
        }
        $("#cusProperty").val("");
        //地区市 区信息重置
        if($("select[name='city']") != undefined){
            $("select[name='city'] option:gt(0)").remove();
        }
        if($("select[name='zone']") != undefined){
            $("select[name='zone'] option:gt(0)").remove();
        }

        if($("#select[name='productBelongUserId']") != undefined){
            $("#productBelongUserId").val('${loginUser.userId}');
        }
    }
</script>
<c:if test="${not empty method}">
<%@ include file="../../trader/customer/customer_tag.jsp"%>
</c:if>
	<div class="searchfunc" <c:if test="${not empty method}">style="padding-top:0px;"</c:if>>
		 <form method="post" id="search" 
	<c:if test="${salerorderOnline!=1 }">
		<c:choose>
			<c:when test="${not empty from }">
				action="<%=basePath%>ordergoods/order/index.do"
			</c:when>
			<c:otherwise>
				action="<%=basePath%>order/saleorder/index.do"
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${salerorderOnline==1 }">
		<c:choose>
			<c:when test="${not empty from }">
				action="<%=basePath%>ordergoods/order/indexOnline.do"
			</c:when>
			<c:otherwise>
				action="<%=basePath%>order/saleorder/indexOnline.do"
			</c:otherwise>
		</c:choose>
		</c:if>> 
		<!--  <form method="post" id="search"> -->
			<ul>
				<li>
					<label class="infor_name">订单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorder.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="status" id="status">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.status eq 0}">selected</c:if> value="0">待确认</option>
						<!-- changed by Tomcat.Hui 2020/3/11 1:13 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分 删除待用户确认下拉框. -->
						<%-- <option <c:if test="${saleorder.status eq 4}">selected</c:if> value="4">待用户确认</option>--%>
						<option <c:if test="${saleorder.status eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${saleorder.status eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${saleorder.status eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">生效状态</label>
					<select class="input-middle" name="validStatus" id="validStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.validStatus eq 0}">selected</c:if> value="0">未生效</option>
						<option <c:if test="${saleorder.validStatus eq 1}">selected</c:if> value="1">已生效</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收款状态</label>
					<select class="input-middle" name="paymentStatus" id="paymentStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.paymentStatus eq 0}">selected</c:if> value="0">未收款</option>
						<option <c:if test="${saleorder.paymentStatus eq 1}">selected</c:if> value="1">部分收款</option>
						<option <c:if test="${saleorder.paymentStatus eq 2}">selected</c:if> value="2">全部收款</option>
					</select>
				</li>
				<li>
					<label class="infor_name">发货状态</label>
					<select class="input-middle" name="deliveryStatus" id="deliveryStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.deliveryStatus eq 0}">selected</c:if> value="0">未发货</option>
						<option <c:if test="${saleorder.deliveryStatus eq 1}">selected</c:if> value="1">部分发货</option>
						<option <c:if test="${saleorder.deliveryStatus eq 2}">selected</c:if> value="2">全部发货</option>
					</select>
				</li>
				<li>
					<label class="infor_name">收货状态</label>
					<select class="input-middle" name="arrivalStatus" id="arrivalStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.arrivalStatus eq 0}">selected</c:if> value="0">未收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 1}">selected</c:if> value="1">部分收货</option>
						<option <c:if test="${saleorder.arrivalStatus eq 2}">selected</c:if> value="2">全部收货</option>
					</select>
				</li>
				<li>
					<label class="infor_name">开票申请</label>
					<select class="input-middle" name="openInvoiceApply" id="openInvoiceApply">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.openInvoiceApply eq 1}">selected</c:if> value="1">审核中</option>
					</select>
				</li>
				<li>
					<label class="infor_name">开票状态</label>
					<select class="input-middle" name="invoiceStatus" id="invoiceStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.invoiceStatus eq 0}">selected</c:if> value="0">未开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 1}">selected</c:if> value="1">部分开票</option>
						<option <c:if test="${saleorder.invoiceStatus eq 2}">selected</c:if> value="2">全部开票</option>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="verifyStatus" id="">
						<option value="">全部</option>
						<option <c:if test="${saleorder.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
						<option <c:if test="${saleorder.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
						<option <c:if test="${saleorder.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
						<option <c:if test="${saleorder.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">售后状态</label>
					<select class="input-middle" name="serviceStatus" id="serviceStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.serviceStatus eq 0}">selected</c:if> value="0">无</option>
						<option <c:if test="${saleorder.serviceStatus eq 1}">selected</c:if> value="1">售后中</option>
						<option <c:if test="${saleorder.serviceStatus eq 2}">selected</c:if> value="2">售后完成</option>
						<option <c:if test="${saleorder.serviceStatus eq 3}">selected</c:if> value="3">售后关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">锁定状态</label>
					<select class="input-middle" name="lockedStatus" id="lockedStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.lockedStatus eq 0}">selected</c:if> value="0">未锁定</option>
						<option <c:if test="${saleorder.lockedStatus eq 1}">selected</c:if> value="1">已锁定</option>
					</select>
				</li>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${saleorder.traderName}"/>
				</li>
				<li>
					<label class="infor_name">客户性质</label>
					<select class="input-middle" name="customerNature" id="customerNature">
						<option value="-1">全部</option>
						<c:forEach var="list" items="${customerNatures}">
	                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.customerNature == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
	                    </c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">联系人</label>
					<input type="text" class="input-middle" placeholder="请填写联系人或联系方式" name="traderContact" id="traderContact" value="${saleorder.traderContact}"/>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorder.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorder.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${saleorder.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${saleorder.sku}"/>
				</li>
				<li>
					<label class="infor_name">销售部门</label>
					<select class="input-middle" name="orgId" id="orgId">
						<option value="-1">全部</option>
						<c:forEach items="${orgList}" var="org">
							<option value="${org.orgId}" <c:if test="${saleorder.orgId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">归属销售</label>
					<select class="input-middle" name="optUserId" id="optUserId">
						<option value="-1">全部</option>
						<c:forEach items="${userList}" var="list">
							<option value="${list.userId}" <c:if test="${saleorder.optUserId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">直发</label>
					<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.deliveryDirect eq 0}">selected</c:if> value="0">否</option>
						<option <c:if test="${saleorder.deliveryDirect eq 1}">selected</c:if> value="1">是</option>
					</select>
				</li>
				<li>
					<label class="infor_name">提前采购</label>
					<select class="input-middle" name="haveAdvancePurchase" id="haveAdvancePurchase">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.haveAdvancePurchase eq 0}">selected</c:if> value="0">否</option>
						<option <c:if test="${saleorder.haveAdvancePurchase eq 1}">selected</c:if> value="1">是</option>
					</select>
				</li>
				<li>
					<label class="infor_name">有沟通</label>
					<select class="input-middle" name="haveCommunicate" id="haveCommunicate">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.haveCommunicate eq 0}">selected</c:if> value="0">无</option>
						<option <c:if test="${saleorder.haveCommunicate eq 1}">selected</c:if> value="1">有</option>
					</select>
				</li>
				<li>
					<label class="infor_name">账期未还</label>
					<select class="input-middle" name="accountPeriod" id="accountPeriod">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.accountPeriod eq 0}">selected</c:if> value="0">是</option>
					</select>
				</li>
				<li>
					<label class="infor_name">订单来源</label>
					<c:if test="${salerorderOnline!=1 }">
					<select class="input-middle" name="orderType" id="orderType">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.orderType eq 4}">selected</c:if> value="4">经销商</option>
						<option <c:if test="${saleorder.orderType eq 6}">selected</c:if> value="6">艾迪康</option>
						<option <c:if test="${saleorder.orderType eq 1}">selected</c:if> value="1">贝登前台</option>
						<option <c:if test="${saleorder.orderType eq 0}">selected</c:if> value="0">贝登ERP</option>
					</select>
					</c:if>
					<c:if test="${salerorderOnline==1 }">
					<select class="input-middle" name="orderType" id="orderType">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.orderType eq 1}">selected</c:if> value="1">贝登前台</option>
					</select>
					</c:if>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="searchDateType" id="searchDateType" style="width:75px;">
							<option value="1" <c:if test="${saleorder.searchDateType == 1}">selected="selected"</c:if> >创建时间</option>
							<option value="2" <c:if test="${saleorder.searchDateType == 2}">selected="selected"</c:if> >生效时间</option>
							<option value="3" <c:if test="${saleorder.searchDateType == 3}">selected="selected"</c:if> >付款时间</option>
							<option value="4" <c:if test="${saleorder.searchDateType == 4}">selected="selected"</c:if> >发货时间</option>
							<option value="5" <c:if test="${saleorder.searchDateType == 5}">selected="selected"</c:if> >收货时间</option>
							<option value="6" <c:if test="${saleorder.searchDateType == 6}">selected="selected"</c:if> >开票时间</option>
						</select>
					</div>
					<input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
                    <div class="gang">-</div>
                    <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
				</li>
				<li>
					<label class="infor_name">计入业绩</label>
					<select class="input-middle" name="isSalesPerformance" id="isSalesPerformance">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.isSalesPerformance eq 1}">selected</c:if> value="1">已计入</option>
						<option <c:if test="${saleorder.isSalesPerformance eq 0}">selected</c:if> value="0">未计入</option>
					</select>
				</li>
				<li>
					<label class="infor_name">合同回传</label>
					<select class="input-middle" name=isContractReturn id="isContractReturn">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.isContractReturn eq 1}">selected</c:if> value="1">已回传</option>
						<option <c:if test="${saleorder.isContractReturn eq 0}">selected</c:if> value="0">未回传</option>
					</select>
				</li>
				<li>
					<label class="infor_name">送货单</label>
					<select class="input-middle" name="isDeliveryOrderReturn" id="isDeliveryOrderReturn">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.isDeliveryOrderReturn eq 1}">selected</c:if> value="1">已回传</option>
						<option <c:if test="${saleorder.isDeliveryOrderReturn eq 0}">selected</c:if> value="0">未回传</option>
					</select>
				</li>
				<li>
					<label class="infor_name">合同审核状态</label>
					<select class="input-middle" name="contractStatus" id="contractStatus">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.contractStatus eq 3}">selected</c:if> value="3">待审核</option>
						<option <c:if test="${saleorder.contractStatus eq 0}">selected</c:if> value="0">审核中</option>
						<option <c:if test="${saleorder.contractStatus eq 1}">selected</c:if> value="1">合格</option>
						<option <c:if test="${saleorder.contractStatus eq 2}">selected</c:if> value="2">不合格</option>
					</select>
				</li>
				<c:if test="${position == 311}">
				<li>
					<label class="infor_name">产品成本</label>
					<select class="input-middle" name="isReferenceCostPrice" id="isReferenceCostPrice">
						<option value="-1">全部</option>
						<option <c:if test="${saleorder.isReferenceCostPrice eq 0}">selected</c:if> value="0">未填写</option>
						<option <c:if test="${saleorder.isReferenceCostPrice eq 1}">selected</c:if> value="1">已填写</option>
						<option <c:if test="${saleorder.isReferenceCostPrice eq 2}">selected</c:if> value="2">无需填写</option>
					</select>
				</li>

				</c:if>

				<c:if test="${hasProductRole == true}">
					<li>
						<label class="infor_name">产品归属</label>
						<select name="productBelongUserId" id="productBelongUserId" class="J-select">
							<option value="-1" <c:if test="${user.userId == -1}"> selected </c:if>>全部</option>
							<c:forEach var="user" items="${assUser}">
								<option value="${user.userId}" <c:if test="${user.userId == saleorder.productBelongUserId}"> selected </c:if>>${user.username}</option>
							</c:forEach>
						</select>
					</li>
				</c:if>
			</ul>

			<div class="tcenter">
				<c:if test="${not empty traderId}">
					<input type="hidden" name="traderId" value="${traderId }" >
				</c:if>
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<c:if test="${salerorderOnline != 1 }">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="exportSaleOrderList();">导出列表</span>
				<span class="bt-small bg-light-blue bt-bg-style" onclick="exportSaleOrderDetailList();">导出明细</span>
				<span class="bt-small bg-light-blue bt-bg-style" onclick="synchronizingMeinianOrder();">同步美年订单</span>
				</c:if>
			</div>
		</form>
	</div>
	<div class="content">
		<div class="fixdiv">
			<div class="superdiv" style='width:2000px;'>
				<table class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<!-- th class="wid6">序号</th-->
							<th class="wid10">订单号</th>
							<th class="wid16">客户名称</th>
							<th class="wid8">归属销售</th>
							<th class="wid8">订单原金额</th>
							<th class="wid8">订单实际金额</th>
							<th class="wid6">未还账期</th>
							<th class="wid6">收款状态</th>
							<th class="wid6">采购状态</th>
							<th class="wid6">开票状态</th>
							<th class="wid6">发货状态</th>
							<th class="wid6">收货状态</th>
							<th class="wid5">可否开票</th>
							<th class="wid6">客户性质</th>
							<th class="wid10">销售部门</th>
							<th class="wid6">直发</th>
							<th class="wid6">售后状态</th>
							<th class="wid5">沟通次数</th>
							<th class="wid8">客户实付金额</th>
							<%--<th class="wid6">已收款金额</th>--%>
							<th class="wid6">提前采购</th>
							<th class="wid6">类别管制</th>
							<th class="wid5">合同回传</th>
							<th class="wid7">合同审核状态</th>
							<th class="wid6">送货单回传</th>
						</tr>
					</thead>
					<tbody>
						<!-- 本页条数 -->
						<c:set var="pageNum" value="0"></c:set>
						<!-- 本页总金额数 -->
						<c:set var="currentCount" value="0"></c:set>
						<c:forEach var="list" items="${saleorderList}" varStatus="num">
							<c:set var="pageNum" value="${num.count}"></c:set>
							<c:set var="currentCount" value="${currentCount + list.totalAmount}"></c:set>
							<tr>
								<!-- td>${num.count}</td-->
								<td>
									<c:choose>
										<c:when test="${list.status eq 0}">
											<span class="orangecircle"></span>
										</c:when>
										<c:when test="${list.status eq 1}">
											<span class="greencircle"></span>
										</c:when>
										<c:when test="${list.status eq 2}">
											<span class="bluecircle"></span>
										</c:when>
										<c:when test="${list.status eq 3}">
											<span class="greycircle"></span>
										</c:when>
									</c:choose>
									
									<c:if test="${list.validStatus eq 0}">
										<c:set var="shenhe" value="0"></c:set>
										<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
											<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
												<c:if test="${verifyUsernameInfo == loginUser.username}">
													<c:set var="shenhe" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									</c:if>
									<c:set var="costPriceEmpty" value="0"></c:set>
										<c:if test="${list.costUserIdsList!=null && null!=list.costUserIdsList}">
											<c:forEach items="${list.costUserIdsList}" var="costUserIds">
												<c:if test="${costUserIds == loginUser.userId}">
													<c:set var="costPriceEmpty" value="1"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
									<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.saleorderId}","link":"./order/saleorder/view.do?saleorderId=${list.saleorderId}","title":"订单信息"}'>
										${list.saleorderNo}
									</a>
									${list.validStatus == 0 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
									${list.loginUserBelongToProductManager == 1 && list.paymentStatus == 2 && list.status != 3 ?"<font color='red'>[填]</font>":""}
								</td>
								<td>
									<c:choose>
										<c:when test="${empty list.traderId || list.traderId == 0}">
											${list.traderName}
										</c:when>
										<c:otherwise>
											<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${list.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${list.traderId}", "title":"客户信息"}'>${list.traderName}</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${list.optUserName}</td>
								<td>${list.totalAmount}</td>
								<td>${list.realAmount}</td>
								<td>
									<c:forEach var="capital" items="${capitalBillList}">
										<c:if test="${list.saleorderId eq capital.saleorderId}">
											<fmt:formatNumber type="number" value="${capital.lackAccountPeriodAmount}" pattern="0.00" maxFractionDigits="2" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.paymentStatus eq 0}">
											未收款
										</c:when>
										<c:when test="${list.paymentStatus eq 1}">
											部分收款
										</c:when>
										<c:when test="${list.paymentStatus eq 2}">
											全部收款
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.purchaseStatus eq 0}">
											未采购
										</c:when>
										<c:when test="${list.purchaseStatus eq 1}">
											部分采购
										</c:when>
										<c:when test="${list.purchaseStatus eq 2}">
											已采购
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.invoiceStatus eq 0}">
											<span style="color: red">未开票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 1}">
											<span style="color: red">部分开票</span>
										</c:when>
										<c:when test="${list.invoiceStatus eq 2}">
											全部开票
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.deliveryStatus eq 0}">
											<span style="color: red">未发货</span>
										</c:when>
										<c:when test="${list.deliveryStatus eq 1}">
											<span style="color: red">部分发货</span>
										</c:when>
										<c:when test="${list.deliveryStatus eq 2}">
											全部发货
										</c:when>
									</c:choose>
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
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.isOpenInvoice eq 1 or list.isOpenInvoice eq 3}">
											是
										</c:when>
										<c:otherwise>
											<span style="color: red">否</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${list.customerNatureStr}</td>
								<td>${list.salesDeptName}</td>
								<td>
									<c:choose>
										<c:when test="${list.deliveryDirect eq 0}">
											否
										</c:when>
										<c:when test="${list.deliveryDirect eq 1}">
											<span style="color: red">是</span>
										</c:when>
										<c:when test="${list.deliveryDirect eq 3}">
											<span style="color: red"></span>
										</c:when>
										
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.serviceStatus eq 0}">
											无
										</c:when>
										<c:when test="${list.serviceStatus eq 1}">
											售后中
										</c:when>
										<c:when test="${list.serviceStatus eq 2}">
											售后完成
										</c:when>
										<c:when test="${list.serviceStatus eq 3}">
											售后关闭
										</c:when>
									</c:choose>
								</td>
								<td>${list.communicateNum}</td>
								<td>
									<!-- changed by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start -->
									<c:forEach var="capital" items="${capitalBillList}">
										<c:if test="${list.saleorderId eq capital.saleorderId}">
											<fmt:formatNumber type="number" value="${capital.paymentAmount + capital.periodAmount - capital.lackAccountPeriodAmount - capital.refundBalanceAmount}" pattern="0.00" maxFractionDigits="2" />
											<%--<fmt:formatNumber type="number" value="${capital.paymentAmount + capital.periodAmount - capital.lackAccountPeriodAmount}" pattern="0.00" maxFractionDigits="2" />--%>
										</c:if>
									</c:forEach>
									<!-- changed by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end -->
								</td>
								<td>
									<c:choose>
										<c:when test="${list.haveAdvancePurchase eq 0}">
											否
										</c:when>
										<c:when test="${list.haveAdvancePurchase eq 1}">
											<span style="color: red">是</span>
										</c:when>
									</c:choose>
								</td>
								<td>---</td>
								<td>
									<c:choose>
										<c:when test="${list.isContractReturn eq 1}">
											是
										</c:when>
										<c:otherwise>
											<span style="color: red">否</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.contractStatus eq 3}">
											待审核
										</c:when>
										<c:when test="${list.contractStatus eq 0}">
											审核中
										</c:when>
										<c:when test="${list.contractStatus eq 1}">
											<span style="color: green;">合格</span>
										</c:when>
										<c:when test="${list.contractStatus eq 2}">
											<span style="color: red;">不合格</span>
										</c:when>
										<c:otherwise>
											--
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${list.isDeliveryOrderReturn eq 1}">
											是
										</c:when>
										<c:otherwise>
											<span style="color: red">否</span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty saleorderList}">
			      			<!-- 查询无结果弹出 -->
			          		<tr>
			          			<td colspan="22">查询无结果！请尝试使用其他搜索条件。</td>
			          		</tr>
				       	</c:if>
					</tbody>
				</table>
			</div>
		</div>
       	<tags:page page="${page}"/>
       	<div>
			<div class="clear"></div>
			<c:if test="${not empty saleorderList}">
				<div class="fixtablelastline">
					【全部结果 条目：${page.totalRecord} 订单金额总计：<span class="success-color1"><fmt:formatNumber type="number" value="${total_amount}" pattern="0" maxFractionDigits="2" /></span>】
					【本页统计 条目：${pageNum} 订单金额总计：<span class="success-color1"><fmt:formatNumber type="number" value="${currentCount}" pattern="0" maxFractionDigits="2" /></span>】
				</div>
			</c:if>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>