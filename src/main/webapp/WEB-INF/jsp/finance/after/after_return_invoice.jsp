<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后退票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/finance/after/after_return_invoice.js?rnd=<%=Math.random()%>'></script>
<div class="form-list form-tips4" >
    <div id="addAfterCapitalBillForm">
        <ul>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>发票号</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	<c:if test="${afterInvoice.currentMonthInvoice eq 1}">${afterInvoice.invoiceNo}</c:if>
						<c:if test="${afterInvoice.currentMonthInvoice eq 0}">
							<input type="text" id="in_invoiceNo" class="input-middle" onkeyup="vailInvoiceNo(this);"/>
						</c:if>
                        
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>发票金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks" id="afterReturnInvoiceAmount">
                    	<%-- <fmt:formatNumber type="number" value="${afterInvoice.amount}" pattern="0.00" maxFractionDigits="2" /> --%>
                    </div>
                    <input type="hidden" value="${afterInvoice.amount}" id="afterReturnInvoiceAmountHidden">
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>票种</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
						<c:forEach var="invoiceList" items="${invoiceTypeList}">
							<c:if test="${invoiceList.sysOptionDefinitionId eq afterInvoice.invoiceType}">${invoiceList.title}</c:if>
						</c:forEach>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>红蓝字</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
						<c:if test="${afterInvoice.currentMonthInvoice eq 1}"><font color="blue">蓝字作废</font></c:if>
						<c:if test="${afterInvoice.currentMonthInvoice eq 0}"><font color="red">红字有效</font></c:if>
                    </div>
                </div>
            </li>
            <li>
            	<c:choose>
            		<c:when test="${afterInvoice.currentMonthInvoice eq 0}"><!-- 非当月发票 -->
	            		<div class="form-tips">
		                    <lable>退票范围</lable>
		                </div>
		                <div class="f_left f_left_wid90">
		                    <div class="form-blanks ">
		                    	<%-- <c:choose>
		                    		<c:when test="${afterInvoice.afterType eq 542}"><!-- 销售退票 -->
										<input type="radio" name="returnInvoiceCheck" checked onclick="checkReturnInvoice(this,1);"><label>全部商品退票</label>
		                    		</c:when>
		                    		<c:otherwise>
										<input type="radio" name="returnInvoiceCheck" checked onclick="checkReturnInvoice(this,0);"><label>仅退货商品部分退票</label>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" name="returnInvoiceCheck" onclick="checkReturnInvoice(this,1);"><label>全部商品退票</label>
		                    		</c:otherwise>
		                    	</c:choose> --%>
		                    	<c:if test="${empty afterInvoice.amountCount or afterInvoice.amountCount eq 0 }">
								<input type="radio" name="returnInvoiceCheck" value="1" onclick="checkReturnInvoice(this,1);"><label>全部商品退票</label>
								&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
		                    	<input type="radio" name="returnInvoiceCheck" value="0" checked onclick="checkReturnInvoice(this,0);"><label>仅退货商品部分退票</label>
		                    </div>
            		</c:when>
            		<c:otherwise>
            			<div class="f_left" style='margin-left:70px;'>
            		</c:otherwise>
            	</c:choose>
                    <div class="mt10">
                    	<table class="table" id="afterGoodsListId">
			                <thead>
			                    <tr>
			                        <th class="wid6">序号</th>
			                        <th class="wid25">产品名称</th>
			                        <th class="wid15">品牌</th>
			                        <th class="wid12">型号</th>
			                        <th class="wid8">单价</th>
			                        <th class="wid8">订单数量</th>
			                        <th class="wid6">单位</th>
		                    		<c:if test="${afterInvoice.afterType ne 542}"><!-- 不是销售退票 -->
				                        <th class="wid10">退货数量</th>
		                    		</c:if>
			                        <th class="wid10">退票数量</th>
			                    </tr>
			                </thead>
							<tbody>
								<c:forEach var="goodslist" items="${afterInvoice.afterGoodsList}" varStatus="listNum">
									<input type="hidden" name="detailGoodsId" value="${goodslist.orderDetailId}"/>
									<input type="hidden" name="orderPrice" value="${goodslist.orderPrice}"/>
									<tr>
										<td>${listNum.count}</td>
										<td class='text-left'>
											<span class="font-blue cursor-pointer addtitle" tabtitle='{"num":"viewgoods${goodslist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodslist.goodsId}","title":"产品信息"}'>
		                    					${goodslist.goodsName}
		                    				</span><br/>
											${goodslist.sku}<br/>
											${goodslist.materialCode}
										</td>
										<td>${goodslist.brandName}</td>
										<td>${goodslist.model}</td>
										<td><fmt:formatNumber type="number" value="${goodslist.orderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
										<input type="hidden" id="price_${listNum.index}"value="${goodslist.orderPrice}">
										<td><fmt:formatNumber type="number" value="${goodslist.orderNum}" pattern="0.00" maxFractionDigits="2" /></td>
										<td>${goodslist.unitName}</td>
										<c:if test="${afterInvoice.afterType ne 542}"><!-- 不是销售退票 -->
											<td>
												<input type="hidden" name="hideReturnNum" id="${listNum.index}" value="${goodslist.num==null?0:goodslist.num}"/>
												<span id="spanReturnNum${listNum.index}">
													<fmt:formatNumber type="number" value="${goodslist.num==null?0:goodslist.num}" pattern="0.00" maxFractionDigits="2" />
												</span>
											</td>
										</c:if>
										<td align="center">
											<input type="hidden" name="hideInvoiceNum" id="${listNum.index}" value="${goodslist.invoiceNum==null?0:goodslist.invoiceNum}"/>
											<!-- 如果是蓝字作废退票数量取退货数量 -->
											<div id="spanInvoiceNum${listNum.index}">
											<c:choose >
												<c:when test="${afterInvoice.currentMonthInvoice eq 1}">
													<fmt:formatNumber type="number" value="${goodslist.invoiceNum}" pattern="0.00" maxFractionDigits="2" />
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" value="${(goodslist.num le goodslist.invoiceNum)?goodslist.num:goodslist.invoiceNum}" pattern="0.00" maxFractionDigits="2" />
												</c:otherwise>
											</c:choose>
											</div>
											<!-- 计算发票总额 -->
											<input type="hidden" name="hideReturnInvoiceAmount" id="hideReturnInvoiceAmount_${listNum.index}" value="${(goodslist.invoiceNum==null?0:goodslist.invoiceNum) * goodslist.orderPrice}"/>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
                    </div>
                </div>
            </li>
        </ul>
		<form method="post" action="<%= basePath %>/finance/after/saveAfterReturnInvoice.do" id="returnInvoiceForm">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<input type="hidden" name="currentMonthInvoice" id="currentMonthInvoice" value="${afterInvoice.currentMonthInvoice}"/><!-- 是否为当月发票1是：0否 -->
			<input type="hidden" name="invoiceId" value="${afterInvoice.invoiceId}"><!-- 发票主表ID -->
			<input type="hidden" name="afterSalesId" value="${afterInvoice.afterSalesId}"><!-- 售后主表ID -->
			<input type="hidden" name="relatedId" id="relatedId" value="${afterInvoice.orderId}"/><!-- 销售、采购ID -->
			<input type="hidden" name="invoiceNo" id="invoiceNo" /><!-- 发票号码 -->
			<input type="hidden" name="invoiceType" id="invoiceType" value="${afterInvoice.invoiceType}"/><!-- 发票类型 -->
			<input type="hidden" name="ratio" id="ratio" value="${afterInvoice.ratio}"/><!-- 发票税率 -->
			<c:if test="${afterInvoice.currentMonthInvoice eq 1}"><!-- 蓝字作废 -->
				<input type="hidden" name="colorType" id="colorType" value="2"/><!-- 红蓝字 1红2蓝 -->
				<input type="hidden" name="isEnable" id="isEnable" value="0"/><!-- 是否有效 0否 1是 -->
			</c:if>
			<c:if test="${afterInvoice.currentMonthInvoice eq 0}"><!-- 红字有效 -->
				<input type="hidden" name="colorType" id="colorType" value="1"/><!-- 红蓝字 1红2蓝 -->
				<input type="hidden" name="isEnable" id="isEnable" value="1"/><!-- 是否有效 0否 1是 -->
			</c:if>
			<input type="hidden" name="validStatus" id="validStatus" value="1"/>
			<c:choose>
				<c:when test="${afterInvoice.subjectType eq 536}"><!-- 采购 -->
					<input type="hidden" name="type" id="type" value="503"/><!-- 采购 -->
					<input type="hidden" name="tag" id="tag" value="2"/><!-- 采购 -->
				</c:when>
				<c:otherwise>
					<input type="hidden" name="type" id="type" value="505"/><!-- 销售 -->
					<input type="hidden" name="tag" id="tag" value="1"/><!-- 销售 -->
				</c:otherwise>
			</c:choose>
	        <div class="add-tijiao text-left f_left_wid90 " style='margin:-10px 0 0 110px;'>
	            <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="addAfterReturnInvoice();">提交</button>
	            <!-- <button class="dele" type="button" id="close-layer">取消</button> -->
	        </div>
	        <span id="dynamicParameter"></span><!-- jQuery动态参数 -->
	        <input type="hidden" name="afterId" id="afterId" value="${afterInvoice.afterSalesInvoiceId}"/><!-- 售后发票ID -->
	    </form>
    </div>
</div>
<%@ include file="../../common/footer.jsp"%>