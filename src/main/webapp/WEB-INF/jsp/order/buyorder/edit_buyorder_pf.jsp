<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑采购" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/add_buyorder_pf.js?rnd=<%=Math.random()%>'></script>
	<form action="${pageContext.request.contextPath}/order/buyorder/saveEditBuyorder.do" method="post" id="myform">
	<div class="pb100 main-container pt10" > 
		<!-- ----------------------------------基本信息 ------------------------------------- -->
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">基本信息</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<tbody>
					<tr>
						<td class="table-smaller">采购单号</td>
						<td>${buyorderVo.buyorderNo}</td>
						<td class="table-smaller">订单状态</td>
						<td>
							<c:choose>
								<c:when test="${buyorderVo.status eq 0}">
									<span >待确认</span>
								</c:when>
								<c:when test="${buyorderVo.status eq 1}">
									<span >进行中</span>
								</c:when>
								<c:when test="${buyorderVo.status eq 2}">
									<span >已完结</span>
								</c:when>
								<c:otherwise>
									<span >已关闭</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>创建者</td>
						<td>${buyorderVo.createName}</td>
						<td>创建时间</td>
						<td><date:date value="${buyorderVo.addTime}" /></td>
					</tr>
					<tr>
						<td>部门</td>
						<td>${buyorderVo.buyDepartmentName}</td>
						<td>归属</td>
						<td>${buyorderVo.homePurchasing}</td>
					</tr>
					<tr>
						<td>是否直发</td>
						<c:if test="${buyorderVo.deliveryDirect eq 0}"><td>普发</td></c:if>
						<c:if test="${buyorderVo.deliveryDirect eq 1}"><td class="warning-color1">直发</td></c:if>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input type="hidden" name="beforeParams" value='${beforeParams}'/>
		<input type="hidden" name="uri" value="${uri}"/>
		<input type="hidden" name="deliveryDirect" id ="deliveryDirect" value="${buyorderVo.deliveryDirect}"/>
		<input type="hidden" name="buyorderId" value="${buyorderVo.buyorderId}"/>
		<input type="hidden" name="status" value="${buyorderVo.status}"/>
		<input type="hidden" name="goodsIds" value="${buyorderVo.goodsIds}"/>
		<input type="hidden" name="saleorderGoodsIds" value="${buyorderVo.saleorderGoodsIds}"/>
		<input type="hidden" name="formToken" value="${formToken}"/>
		<input type="hidden" name="delBuyGoodsIds" value=""/>
		<div class="parts content1 ">
			<div class="formtitle mt10">供应商信息</div>
				<ul class="payplan" style="margin-bottom:0;">
					<li>
						<div class="infor_name mt0">
							<span>*</span>
							<label>供应商</label>
						</div>
						<div class="f_left">
							<div class='inputfloat'>
								<c:if test="${buyorderVo.traderId ne 0}">
									<span id="name">${buyorderVo.traderName}</span>
									<label class="bt-bg-style bg-light-blue bt-small" onclick="research();" id="research">重新搜索</label>
									<input type="text" placeholder="请输入供应商名称" class="input-largest none" name="searchTraderName" id="searchTraderName" value="${buyorderVo.traderName}">
									<label class="bt-bg-style bg-light-blue bt-small none" onclick="searchSupplier();" id="errorMes">搜索</label>
								</c:if>
								<c:if test="${buyorderVo.traderId eq 0}">
									<span class="none" id="name">${buyorderVo.traderName}</span>
									<label class="bt-bg-style bg-light-blue bt-small none" onclick="research();" id="research">重新搜索</label>
									<input type="text" placeholder="请输入供应商名称" class="input-largest" name="searchTraderName" id="searchTraderName" value="${buyorderVo.traderName}">
									<label class="bt-bg-style bg-light-blue bt-small" onclick="searchSupplier();" id="errorMes">搜索</label>
								</c:if>
								<span style="display:none;">
									<!-- 弹框 -->
									<div class="title-click nobor  pop-new-data" id="popSupplier"></div>
								</span>
								<input type="hidden" id ="traderSupplierId" name="traderSupplierId" />
								<input type="hidden" id="traderId" name="traderId" value="${buyorderVo.traderId}"/>
								<input type="hidden" id="traderName" name="traderName" value="${buyorderVo.traderName}"/>
								<input type="hidden" id="periodBalance" name="periodBalance" value="${buyorderVo.periodBalance}"/>
							</div>
							<div class="font-red none"></div>
						</div>
						
					</li>
					
					<li <c:if test="${buyorderVo.traderContactId eq 0}">class="none "</c:if> id="contact">
						<div class="infor_name">
							<span>*</span>
							<label>联系人</label>
						</div>
						<div class="f_left">
							<div class='inputfloat'>
								<select id="traderContactId" name="traderContactStr"  class="input-largest">
									<option value="" >请选择</option>
									<c:forEach var="contact" items="${contactList}" varStatus="status">
										<option value="${contact.traderContactId}|${contact.name}|${contact.mobile}|${contact.telephone}" 
											<c:if test="${contact.traderContactId eq buyorderVo.traderContactId }">selected="selected"</c:if>>${contact.name}/${contact.mobile}/${contact.telephone}</option>
									</c:forEach>
								</select>
							</div>
							<div class="font-red none">联系人不允许为空</div>
						</div>
					</li>
					<li <c:if test="${buyorderVo.traderAddressId eq 0}">class="none "</c:if> id="address">
						<div class="infor_name">
							<span>*</span>
							<label>联系地址</label>
						</div>
						<div class="f_left">
							<div class='inputfloat'>
								<select id="traderAddressId" name="traderAddressStr"  class="input-largest">
									<option value="" >请选择</option>
									<c:forEach var="add" items="${tarderAddressList}" varStatus="status">
										<option value="${add.traderAddress.traderAddressId}|${add.area}|${add.traderAddress.address}" 
											<c:if test="${add.traderAddress.traderAddressId eq buyorderVo.traderAddressId }">selected="selected"</c:if>>${add.area}&nbsp;&nbsp;${add.traderAddress.address}</option>
									</c:forEach>
								</select>
							</div>
							<div class="font-red none">联系地址不允许为空</div>
						</div>
					</li>
					<li <c:if test="${buyorderVo.traderContactId eq 0}">class="none "</c:if> id="comment">
							<div class="infor_name mt0">
								<label>供应商备注</label>
							</div>
							<div class="f_left" id="supplierComments">
							</div>
							<input type="hidden" id="traderComments" name="traderComments" value="${buyorderVo.traderComments}"/>
					</li>
					
				</ul>
			
		</div>
		<!-- ----------------------------------产品信息 ------------------------------------- -->
		<div class='parts table-style77'>
			<div class="title-container mb10">
                <div class="table-title nobor">产品信息</div>
                <div class="title-click nobor  pop-new-data" 
                			layerParams='{"width":"500px","height":"300px","title":"添加运费","link":"./addFreightPage.do?buyorderId=${buyorderVo.buyorderId}&goodsId=127063"}'>
	                   	 添加运费
	            </div>
            </div>
	 	<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
            <table class="table table-style7" <c:if test="${bgv.goodsId eq 127063}">id="yf"</c:if> altTable="${bgv.buyorderGoodsId}" >
                <thead>
                    <tr>
                    	<th class="wid4">序号</th>
                    	<th class="wid6">订货号</th>
                        <th class="wid10">产品名称</th>
						<th class="wid8">品牌</th>
						<th class="wid6">型号</th>
						<th class="wid6">物料编码</th>
						<th class="wid6">单位</th>
						<th class="wid6">可用库存/库存量</th>
						<th class="wid6">采购数量</th>
						<th class="wid7">单价</th>
						<th class="wid7">总额</th>
						<th class="wid6">货期（天）</th>
						<th class="wid10">安调信息</th>
						<th class="wid10">内部备注</th>
						<th class="wid10">采购备注</th>
						<th class="wid6">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    	<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
                    	<td>${num.count}</td>
                    	<td>${newSkuInfosMap[bgv.sku].SKU_NO}</td>
                    	<td class="text-left">
                    		<div class="customername pos_rel">
		                    <span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
								<c:set var="skuNo" value="${bgv.sku}"></c:set>
								<%@ include file="../../common/new_sku_common_tip.jsp" %>
							</div>
                        </td>
						<td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
						<td>${newSkuInfosMap[bgv.sku].MODEL}</td>
						<td>${newSkuInfosMap[bgv.sku].MATERIAL_CODE}</td>
						<td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
                        <td>
	                        <c:if test="${bgv.goodsId ne 127063}"></c:if>
	                        ${bgv.canUseGoodsStock != null ? bgv.canUseGoodsStock : 0}/${bgv.goodsStock == null ? 0 : bgv.goodsStock}
	                        
                        </td>
                        <td>
	                        <span altTotal="${bgv.goodsId}" class="buySum">
	                        <c:if test="${bgv.num ==0}">${bgv.buySum}</c:if>
	                        <c:if test="${bgv.num !=0}">${bgv.num}</c:if>
	                        </span>
	                        <c:if test="${bgv.goodsId ne 127063}"></c:if>
		                        <c:if test="${bgv.num ==0}">
		                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.buySum}"/>
		                        </c:if>
	                        	<c:if test="${bgv.num !=0}">
		                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num}"/>
		                        </c:if>
	                        
                        </td>
                        <td>
	                        <c:if test="${bgv.goodsId ne 127063}"></c:if>
	                        	<input type="text" altTotal="${bgv.goodsId}" class="wid11 warning-color1" name="xprice" value='<fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" />' autocomplete="off"
	                        			onblur="changPrice(this,${bgv.buyorderGoodsId},${bgv.goodsId});">
	                        	<input type="hidden" name="price" alt="${bgv.goodsId}" value="${bgv.buyorderGoodsId}|${bgv.price}">
                        	<!-- 
                        	<c:if test="${bgv.goodsId eq 127063}">
	                        ${bgv.price}
	                        </c:if>
	                         -->
                        </td>
                        <td>
                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}" altGood="${bgv.goodsId}"><fmt:formatNumber type="number" value="${bgv.oneBuyorderGoodsAmount}" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                        <td>
							<input name="deliveryCycleDispaly" alt="${bgv.buyorderGoodsId}" type="text" placeholder="如3~5天" class="wid8" value="${bgv.deliveryCycle}" onblur="changComments(this,${bgv.buyorderGoodsId});">
							<input type="hidden" name="deliveryCycle" value="${bgv.buyorderGoodsId}|${bgv.deliveryCycle}">
	                    </td>
	                    <td>
                        	<textarea name="installationDispaly" placeholder="是否含安调" alt="${bgv.buyorderGoodsId}" class="wid10" onblur="changComments(this,${bgv.buyorderGoodsId});">${bgv.installation}</textarea>
                        	<input type="hidden" name="installation" value="${bgv.buyorderGoodsId}|${bgv.installation}">
                        </td>
	                     <td>
                        	<textarea name="goodsCommentsDispaly" alt="${bgv.buyorderGoodsId}" class="wid10" onblur="changComments(this,${bgv.buyorderGoodsId});">${bgv.goodsComments}</textarea>
                        	<input type="hidden" name="goodsComments" value="${bgv.buyorderGoodsId}|${bgv.goodsComments}">
                        </td>
                        <td>
                        	<textarea name="insideCommentsDispaly" alt="${bgv.buyorderGoodsId}" class="wid10" onblur="changComments(this,${bgv.buyorderGoodsId});">${bgv.insideComments}</textarea>
                        	<input type="hidden" name="insideComments" value="${bgv.buyorderGoodsId}|${bgv.insideComments}">
                        </td>
                        <td>
	                        <span class="edit-user forbid clcforbid" onclick="delBuyorderGoods(${bgv.buyorderGoodsId});">删除</span>
	                    </td>
                    </tr>
                    <tr>
                        <td colspan="16" class="table-container ">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="wid8">关联单号</th>
                                        <th class="wid10 ">申请人</th>
                                        <th class="wid15 ">数量</th>
                                        <th class="wid11 ">销售价</th>
                                        <th class="wid10">销售货期</th>
                                        <th class="wid12">内部备注</th>
                                        <th class="wid12">产品备注</th>
                                        <th class="wid12 ">终端客户名称</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="saleorderGoods" items="${bgv.saleorderGoodsVoList}">
	                                    <tr>
											<td>${saleorderGoods.saleorderNo}</td>
											<td>${saleorderGoods.applicantName}</td>
											<td>
												<c:if test="${buyorderVo.deliveryDirect eq 1}">
													<c:if test="${saleorderGoods.saleBuyNum ne 0}">
														<input type="hidden" name="dbBuyNum" 
															value="${bgv.buyorderGoodsId}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.saleBuyNum}">
														<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
															value="${saleorderGoods.saleBuyNum}" autocomplete="off"
															onblur="addNum(this,${saleorderGoods.num-saleorderGoods.buyNum},${bgv.buyorderGoodsId},${saleorderGoods.saleorderGoodsId})">/
														${saleorderGoods.num - saleorderGoods.buyNum+saleorderGoods.saleBuyNum}
													</c:if>
													<c:if test="${saleorderGoods.saleBuyNum eq 0}">
														<input type="hidden" name="dbBuyNum" 
															value="${bgv.buyorderGoodsId}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.num-saleorderGoods.buyNum+saleorderGoods.saleBuyNum}">
														<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
															value="${saleorderGoods.num-saleorderGoods.buyNum+saleorderGoods.saleBuyNum}" autocomplete="off"
															onblur="addNum(this,${saleorderGoods.num-saleorderGoods.buyNum},${bgv.buyorderGoodsId},${saleorderGoods.saleorderGoodsId})">/
														${saleorderGoods.num - saleorderGoods.buyNum+saleorderGoods.saleBuyNum}
													
													</c:if>
												</c:if>
												<c:if test="${buyorderVo.deliveryDirect eq 0}">
													<c:if test="${saleorderGoods.saleBuyNum ne 0}">
														<input type="hidden" name="dbBuyNum" 
															value="${bgv.buyorderGoodsId}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.saleBuyNum}">
														<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
															value="${saleorderGoods.saleBuyNum}" autocomplete="off"
															onblur="addNum(this,${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum},${bgv.buyorderGoodsId},${saleorderGoods.saleorderGoodsId})">/
														${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum}
													</c:if>
													<c:if test="${saleorderGoods.saleBuyNum eq 0}">
														<input type="hidden" name="dbBuyNum" 
															value="${bgv.buyorderGoodsId}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum}">
														<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
															value="${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum}" autocomplete="off"
															onblur="addNum(this,${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum},${bgv.buyorderGoodsId},${saleorderGoods.saleorderGoodsId})">/
														${saleorderGoods.needBuyNum+saleorderGoods.saleBuyNum}
													</c:if>
												</c:if>
											<%-- <input type="hidden" name="dbBuyNum" 
												value="${bgv.buyorderGoodsId}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.saleBuyNum}">
											<input type="text" style="width: 60px" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum" value="${saleorderGoods.saleBuyNum}" autocomplete="off"
												onblur="addNum(this,${saleorderGoods.num-saleorderGoods.saleAfterNum-saleorderGoods.buyNum+saleorderGoods.buyAfterNum+saleorderGoods.saleBuyNum >0?saleorderGoods.num-saleorderGoods.saleAfterNum-saleorderGoods.buyNum+saleorderGoods.buyAfterNum+saleorderGoods.saleBuyNum:0},${bgv.buyorderGoodsId},${saleorderGoods.saleorderGoodsId})">/
											${saleorderGoods.num-saleorderGoods.saleAfterNum-saleorderGoods.buyNum+saleorderGoods.buyAfterNum+saleorderGoods.saleBuyNum >0?saleorderGoods.num-saleorderGoods.saleAfterNum-saleorderGoods.buyNum+saleorderGoods.buyAfterNum+saleorderGoods.saleBuyNum:0} --%>
											</td>
											<td><fmt:formatNumber type="number" value="${saleorderGoods.price}" pattern="0.00" maxFractionDigits="2" /></td>
											<td>${saleorderGoods.deliveryCycle}</td>
											<td><span class="warning-color1">${saleorderGoods.insideComments}</span></td>
											<td>${saleorderGoods.goodsComments}</td>
											<td>${saleorderGoods.terminalTraderName}</td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </c:forEach>
         <div class="tablelastline">
               	 总件数<span class="warning-color1" id="zSum">${buyorderVo.buyorderSum}</span>，总金额<span class="warning-color1" id="zMoney" >${buyorderVo.buyorderAmount}</span>
            </div>
        </div>
		<!-- ----------------------------------付款计划 ------------------------------------- -->
		<div class="parts content1">
				<div class="formtitle mt10">付款信息</div>
				<ul class="payplan" id="payplan">
					<li>
						<div class="infor_name infor_name72">
							<label>付款方式</label>
						</div>
						<div class="f_left inputfloat">
							<select id="paymentType" name="paymentType"  class="input-middle" onchange="payment(this);">
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<option value="${list.sysOptionDefinitionId}" 
										<c:if test="${list.sysOptionDefinitionId eq buyorderVo.paymentType }">selected="selected"</c:if>>${list.title}</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li>
						<div class="infor_name infor_name72 ">
							<label>预付金额</label>
						</div>
						
						<div class="f_left">
							<div class='inputfloat'>
								<input type="text" class="input-middle" id="prepaidAmount" name="prepaidAmount" value="${buyorderVo.prepaidAmount}" readonly="readonly"/> 
							</div>
							<div id="prepaidAmountError"></div>
						</div>
						
					</li>
					<li id="accountPeriodLi" <c:if test="${(buyorderVo.paymentType eq 419) or (buyorderVo.paymentType eq 0)}">style="display:none"</c:if>><!-- 419先款后货100%预付:0默认不显示 -->
						<div class="infor_name infor_name72 ">
							<label>账期支付</label>
						</div>
						<div class="f_left inputfloat">
							<div class='inputfloat'>
								<input type="text" class="input-middle" name="accountPeriodAmount" id="accountPeriodAmount" value="${buyorderVo.accountPeriodAmount=='0.00'?'':buyorderVo.accountPeriodAmount}"> 
								<input type="hidden" name="haveAccountPeriod" id="haveAccountPeriod" value="${buyorderVo.haveAccountPeriod}">
							</div>
							<div id="accountPeriodAmountError"></div>
						</div>
						<div id="accountPeriodAmountError"></div>
					</li>
					<li id="retainageLi" <c:if test="${buyorderVo.paymentType ne 424}">style="display:none"</c:if>><!-- 424先货后款自定义 -->
						<div class="infor_name infor_name72">
							<label>尾款</label>
						</div>
						<div class="f_left ">
							<div class='inputfloat'>
								<input type="text" class="input-middle" name="retainageAmount" id="retainageAmount" value="${buyorderVo.retainageAmount=='0.00'?'':buyorderVo.retainageAmount}"> 
								<label class="ml10 mr10 mt4">尾款期限</label> 
								<input type="text" class="input-smaller" name="retainageAmountMonth" id="retainageAmountMonth" value="${buyorderVo.retainageAmountMonth==0?'':buyorderVo.retainageAmountMonth}"> 
								<label class='mt4'>个月</label>
							</div>
							<div id="retainageAmountError"></div>
						</div>
						
					</li>
					<li>
                        <div class="infor_name infor_name72">
                            <label>付款备注</label>
                        </div>
                        <div class="f_left">
                        	<div class='inputfloat'>
                        		<input type="text" placeholder="对内使用，适用于向财务部同事告知付款要求" 
                            		class="input-xx" name="paymentComments" id="paymentComments" value="${buyorderVo.paymentComments}"/>
                        	</div>
                            <div id="paymentCommentsError"></div>
                        </div>
                    </li>
				<div class="font-grey9 ml90 ">
					供应商当前帐期剩余额度<span id="periodAmount">${buyorderVo.periodSurplusAmount}</span>元，帐期天数<span id="periodDay">${buyorderVo.periodDay}</span>天；如需更改帐期，您需要在供应商详情财务信息中申请帐期；
				</div>
				<div id="pay"></div>
			</ul>
		</div>
		<div class="parts content1">
				<div class="formtitle mt10">收票信息</div>
				<ul class="payplan" id="payplan">
					<li>
						<div class="infor_name infor_name72">
							<label>收票种类</label>
						</div>
						<!-- 获取当前日期 -->
						<jsp:useBean id="now" class="java.util.Date" />
						<fmt:formatDate value="${now}" type="both" dateStyle="long" var="today" pattern="yyyy-MM-dd"/>
						<div class="f_left inputfloat">
							<select id="invoiceType" name="invoiceType" onchange="" class="input-middle">
								<!-- 4月1号后税率只有13% -->
								<c:choose>
									<c:when test="${today >= '2019-04-01'}">
										<c:forEach var="list" items="${receiptTypes}" varStatus="status">
											<c:if test="${list.sysOptionDefinitionId ne 429 and list.sysOptionDefinitionId ne 430}"><!-- 屏蔽17%税率 -->
												<option value="${list.sysOptionDefinitionId}" id="${list.comments}"
													<c:if test="${list.sysOptionDefinitionId eq (buyorderVo.invoiceType==0?972:buyorderVo.invoiceType)}">selected</c:if>>${list.title}
												</option>
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach var="list" items="${receiptTypes}" varStatus="status">
											<option value="${list.sysOptionDefinitionId}" 
												<c:if test="${list.sysOptionDefinitionId eq (buyorderVo.invoiceType==0?682:buyorderVo.invoiceType)}">selected</c:if>>${list.title}
										</c:forEach>
									</c:otherwise>
								</c:choose>
							
							
								<%-- <c:if test="${ buyorderVo.invoiceType ne 0}">
									<c:forEach var="list" items="${receiptTypes}" varStatus="status">
										<option value="${list.sysOptionDefinitionId}" 
											<c:if test="${list.sysOptionDefinitionId eq buyorderVo.invoiceType }">selected="selected"</c:if>>${list.title}</option>
									</c:forEach>
								</c:if>
								<c:if test="${ buyorderVo.invoiceType eq 0}">
									<c:forEach var="list" items="${receiptTypes}" varStatus="status">
										<option value="${list.sysOptionDefinitionId}" 
											<c:if test="${list.sysOptionDefinitionId eq 972 }">selected="selected"</c:if>>${list.title}</option>
									</c:forEach>
								</c:if> --%>
							</select>
						</div>
					</li>
					
					<li>
                        <div class="infor_name infor_name72">
                            <label>收票备注</label>
                        </div>
                        <div class="f_left">
                        	<div class='inputfloat'>
	                            <input type="text" placeholder="对内使用，适用于向财务部同事告知收票要求" id="invoiceComments"
	                            	class="input-xx" name="invoiceComments" value="${buyorderVo.invoiceComments }"/>
                            </div>
                            <div id="invoiceCommentsError"></div>
                        </div>
                    </li>
				</ul>
			</div>
		 <div class="parts content1 ">
                <div class="formtitle mt10">
                    收货信息
                </div>
                <ul class="payplan">
                	<c:if test="${buyorderVo.deliveryDirect eq 1}">
	                    <li>
	                        <div class="infor_name infor_name72">
	                            <span>*</span>
	                            <label>收货客户</label>
	                        </div>
	                        <div class="f_left inputfloat">${buyorderVo.takeTraderName}</div>
	                    </li>
	                    <li>
	                        <div class="infor_name infor_name72">
	                            <span>*</span>
	                            <label>收货联系人</label>
	                        </div>
	                        <div class="f_left inputfloat">
	                            ${buyorderVo.takeTraderContactName}/${buyorderVo.takeTraderContactTelephone}/${buyorderVo.takeTraderContactMobile}
	                        </div>
	                    </li>
	                    <li>
	                        <div class="infor_name infor_name72">
	                            <span>*</span>
	                            <label>收货地区</label>
	                        </div>
	                        <div class="f_left inputfloat">
	                            ${buyorderVo.takeTraderArea}
	                        </div>
	                    </li>
	                    <li>
	                        <div class="infor_name infor_name72">
	                            <label>收货地址</label>
	                        </div>
	                        <div class="f_left inputfloat">
	                            ${buyorderVo.takeTraderAddress}
	                        </div>
	                    </li>
                    </c:if>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>指定物流公司</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="logisticsId">
                                <option value="0">请选择</option>
                                <c:forEach var="list" items="${logisticsList}">
                                	<c:if test="${list.isEnable eq 1}">
                                		<option value="${list.logisticsId}" <c:if test="${list.logisticsId eq buyorderVo.logisticsId }">selected="selected"</c:if>>${list.name}</option>
                                	</c:if>
			                    	
			                    </c:forEach>
                            </select>
                        </div>
                    </li>
                    <c:if test="${buyorderVo.deliveryDirect eq 0}">
	                    <li>
	                        <div class="infor_name infor_name72">
	                            <label>收货地址</label>
	                        </div>
	                        <div class="f_left inputfloat">
	                            <select class="input-middle" name="takeAddressId">
	                                <option value="0">请选择</option>
	                                <c:forEach var="list" items="${addressList}">
	                                	<option value="${list.addressId}|${list.companyName}|${list.areas}" 
				                    		<c:if test="${(buyorderVo.takeTraderAddressId eq 0 && list.isDefault eq 1) || (buyorderVo.takeTraderAddressId ne 0 && list.addressId eq buyorderVo.takeTraderAddressId)}">selected="selected"</c:if> >
				                    			${list.companyName}/${list.areas}/${list.address}/${list.contactName}/${list.mobile}</option>
				                    	
				                    </c:forEach>
	                            </select>
	                        </div>
	                    </li>
                    </c:if>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>运费说明</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="freightDescription">
                                <c:forEach var="list" items="${freightDescriptions}">
			                    	<option value="${list.sysOptionDefinitionId}" 
			                    			<c:if test="${list.sysOptionDefinitionId eq buyorderVo.freightDescription }">selected="selected"</c:if>>${list.title}</option>
			                    </c:forEach>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>物流备注</label>
                        </div>
                        <div class="f_left ">
                        	<div class="inputfloat">
                        		<input type="text" name="logisticsComments"  placeholder="对内使用，适用于向物流部同事告知发货要求，默认同步客户信息中物流备注" 
                            		id="logisticsComments" class="input-xx" value="${buyorderVo.logisticsComments }"/>
                        	</div>
                            <div id="logisticsCommentsError"></div>
                        </div>
                    </li>
                </ul>
            </div>
		
		<!-- ----------------------------------其他信息 ------------------------------------- -->
		<div class="parts content1 ">
			<div class="formtitle mt10">其他信息</div>
			<ul class="payplan"> 
				<li>
                    <div class="infor_name infor_name72">
                        <label>补充条款</label>
                    </div>
                    <div class="f_left">
                    	<div class="inputfloat">
	                        <input type="text" name="additionalClause" id="additionalClause"
	                        	placeholder="对外使用，适用于向供应商填写补充条款" class="input-xx" value="${buyorderVo.additionalClause }"/>
                      	</div>
                      	<div id="additionalClauseError"></div>
                    </div>
                </li>    
				<li>
                    <div class="infor_name infor_name72">
                        <label>内部备注</label>
                    </div>
                    <div class="f_left ">
                    	<div class="inputfloat">
                    		<input type="text" name="comments" id="comments" 
                        	placeholder="对内使用，客户不可见,可用作自己的备注" class="input-xx" value="${buyorderVo.comments }"/>
                    	</div>
                        <div id="commentsError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name infor_name72">
                        <label>预计到货时间<br>（工作日）</label>
                    </div>
                    <div class="f_left">
                    	<div class="inputfloat">
	                        <input type="text" name="estimateArrivalTime" id="estimateArrivalTime"
	                        	placeholder="请输入预计多少工作日可以到货，如：5、3~7" class="input-xx" value="${buyorderVo.estimateArrivalTime }"/>
                      	</div>
                      	<div id="estimateArrivalTimeError"></div>
                    </div>
                </li> 
            </ul>
		</div>
		<div class="tcenter mb10">
	         <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="sub">确定</button>
	     </div>
		</form>
	</body>
	
</html>