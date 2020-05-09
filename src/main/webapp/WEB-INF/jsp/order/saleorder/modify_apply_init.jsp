<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="申请修改" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/modify_apply_init.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
	<div class="content mt10 ">
        <form action="${pageContext.request.contextPath}/order/saleorder/modifyApplySave.do" method="post" id="editForm">
			<!--  add by Tomcat.Hui 2019/12/3 19:28 .Desc: VDERP-1325 分批开票 有条件展示 start -->
			<input type="hidden" id="invoiceModifyflag" value="${invoiceModifyflag}" >
            <div class="parts content1 " <c:if test="${invoiceModifyflag eq 1}">style="display: none" </c:if>>
                <div class="formtitle mt10">
                    收货信息
                </div>
                <ul class="payplan">
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收货客户</label>
                        </div>
                        <div class="f_left inputfloat">
                            <span class=" mr10 mt3" id="trader_name_span_1">${saleorder.takeTraderName}</span>
                            <input type="hidden" name="takeTraderId" id="trader_id_1" value="${saleorder.takeTraderId}">
                            <input type="hidden" name="takeTraderName" id="trader_name_1" value="${saleorder.takeTraderName}">
                        </div>
                    </li>
                    <c:choose>
                    	<c:when test="${saleorder.orderType eq 5 }">
                    		<li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收货联系人</label>
		                        </div>
		                        <div class="f_left">
		                        	<input maxLength="64" type="text" id="takeTraderContactName_5" name="takeTraderContactName" value="${saleorder.takeTraderContactName}" placeholder="请输入收货联系人" />
		                        	<div id="5_takeTraderContactName" style="clear:both"></div>
		                        </div>
		                    </li>
		                    <li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收货手机号</label>
		                        </div>
		                        <div class="f_left">
		                        	<input maxLength="11" type="text" id="takeTraderContactMobile_5" name="takeTraderContactMobile" value="${saleorder.takeTraderContactMobile}" placeholder="请输入收货手机号" />
		                        	<div id="5_takeTraderContactMobile" style="clear:both"></div>
		                        </div>
		                    </li>
	                    	<li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收货地址</label>
		                        </div>
		                        <div class="f_left">
		                        	<select  id="takeTraderAddressId-province" onchange="changeArea('takeTraderAddressId', 'takeTraderArea_5', 1)" >
			                        	<option value="0">请选择</option>
				                    	<c:if test="${not empty provinceList }">
				                    		<c:forEach items="${provinceList }" var="prov">
				                    			<option value="${prov.regionId }" <c:if test="${takeTraderAddressIdProvince eq prov.regionId }">selected="selected"</c:if> >${prov.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
									<select  id="takeTraderAddressId-city" onchange="changeArea('takeTraderAddressId', 'takeTraderArea_5', 2)">
			                        	<option value="0">请选择</option>
			                        	<c:if test="${not empty takeTraderAddressCityList }">
				                    		<c:forEach items="${takeTraderAddressCityList }" var="cy">
				                    			<option value="${cy.regionId }" <c:if test="${takeTraderAddressIdCity eq cy.regionId }">selected="selected"</c:if> >${cy.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
			                        <select  id="takeTraderAddressId-zone" onchange="changeArea('takeTraderAddressId', 'takeTraderArea_5', 3)">
			                        	<option value="0">请选择</option>
			                        	<c:if test="${not empty takeTraderAddressZoneList }">
				                    		<c:forEach items="${takeTraderAddressZoneList }" var="zo">
				                    			<option value="${zo.regionId }" <c:if test="${takeTraderAddressIdZone eq zo.regionId }">selected="selected"</c:if> >${zo.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
									<input type="hidden" id="takeTraderArea_5" name="takeTraderArea" value="${saleorder.takeTraderArea}" />
									<input type="hidden" id="takeTraderAddressId" name="takeTraderAreaId" value="${takeTraderAddressIdZone}" />
		                        	<input maxLength="256" class="input-xx" id="takeTraderAddress_5" type="text" name="takeTraderAddress" value="${saleorder.takeTraderAddress}" placeholder="请输入收货地址" />
		                        	<div id="5_takeTraderAddress" style="clear:both"></div>
		                        </div>
		                    </li>
                    	</c:when>
                    	<c:otherwise>
		                    <li>
		                        <div class="infor_name infor_name72">
		                            <span>*</span>
		                            <label>收货联系人</label>
		                        </div>
		                        <div class="f_left inputfloat">
		                            <select class="input-xx" id="contact_1" name="takeTraderContactId">
		                                <option value="0">请选择</option>
		                                <c:if test="${not empty takeTraderContactList}">
											<c:forEach items="${takeTraderContactList}" var="list" varStatus="status">
											<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.takeTraderContactId}">selected="selected"</c:if>>${list.name}/${list.telephone}/${list.mobile}</option>
											</c:forEach>
						                </c:if>
		                            </select>
		                            <input type="hidden" name="takeTraderContactName">
		                            <input type="hidden" name="takeTraderContactTelephone">
		                            <input type="hidden" name="takeTraderContactMobile">
		                        	<div id="takeTraderContactIdMsg" style="clear:both"></div>
		                        </div>
		                    </li>
		                    <li>
		                        <div class="infor_name infor_name72">
		                            <span>*</span>
		                            <label>收货地址</label>
		                        </div>
		                        <div class="f_left inputfloat">
		                            <select class="input-xx" id="address_1" name="takeTraderAddressId">
		                                <option value="0">请选择</option>
		                                <c:if test="${not empty takeTraderAddressList}">
											<c:forEach items="${takeTraderAddressList}" var="list" varStatus="status">
											<option value="${list.traderAddress.traderAddressId}" <c:if test="${list.traderAddress.traderAddressId eq saleorder.takeTraderAddressId}">selected="selected"</c:if>>${list.area}/${list.traderAddress.address}</option>
											</c:forEach>
						                </c:if>
		                            </select>
		                            <input type="hidden" name="takeTraderArea">
		                           	<input type="hidden" name="takeTraderAddress">
		                        	<div id="takeTraderAddressIdMsg" style="clear:both"></div>
		                        </div>
		                    </li>
                    	</c:otherwise>
                    </c:choose>

					<c:if test="${saleorder.orderType != 5}">
						<c:choose>
							<c:when test="${saleorder.isPrintout == -1}">
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1" selected = selected>请选择</option>
												<option value="1">是</option>
												<option value="2">否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>
							</c:when>

							<c:when test="${saleorder.isPrintout == 0}">
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1">请选择</option>
												<option value="1">是</option>
												<option value="2" selected = selected>否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>
							</c:when>

							<c:when test="${saleorder.isPrintout == 1}">
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1">请选择</option>
												<option value="1" selected = selected>是</option>
												<option value="2">否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>

								<li id='is_price_li'>
									<div style="height: 25px">
										<span style="color: red;text-indent: 15px">*</span>
										<label style="width: 150px">随货出库单是否自带价格</label>
										<select  id='is_price' name = "isPrintout" style='height: auto' onchange='changeIsPrice()' >
											<option value="0">请选择</option>
											<option value="1" selected = selected>是</option>
											<option value="2">否</option>
										</select>
										<div id="isPriceMsg" style="clear:both;"></div>
									</div>
								</li>
							</c:when>

							<c:when test="${saleorder.isPrintout == 2}">
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1">请选择</option>
												<option value="1" selected = selected>是</option>
												<option value="2">否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>

								<li id='is_price_li'>
									<div style="height: 25px">
										<span style="color: red;text-indent: 15px">*</span>
										<label style="width: 150px">随货出库单是否自带价格</label>
										<select  id='is_price' name = "isPrintout" style='height: auto' onchange='changeIsPrice()' >
											<option value="0">请选择</option>
											<option value="1">是</option>
											<option value="2" selected = selected>否</option>
										</select>
									</div>
									<div id="isPriceMsg" style="clear:both;"></div>
								</li>
							</c:when>

							<c:when test="${saleorder.isPrintout == 3}">
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1">请选择</option>
												<option value="1" selected = selected>是</option>
												<option value="2">否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>
							</c:when>
							<c:otherwise>
								<li id="is_print_li">
									<div>
										<div style="height: 25px">
											<span style="color: red;text-indent: 15px">  *</span>
											<label style="width: 150px">是否打印随货出库单</label>
											<select  id="is_print" style="height: auto" onchange="changeIsPrintout()" >
												<option value="-1" selected = selected>请选择</option>
												<option value="1">是</option>
												<option value="2">否</option>
											</select>
										</div>
										<input type="hidden" id="is_scientificDept" value="${isScientificDep}">
										<div id="isPrintoutMsg" style="clear:both;"></div>
									</div>
									<input id="is_printout" type="hidden" name="isPrintout" value="${saleorder.isPrintout}">
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>

                    <li>
                        <div class="infor_name infor_name72">
                            <label>物流备注</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="text" name="logisticsComments" id="logisticsComments" value="${saleorder.logisticsComments}" placeholder="对内使用，适用于向物流部同事告知发货要求，默认同步客户信息中物流备注" class="input-xx" />
						</div>
                    </li>
                </ul>
            </div>
			<!--  add by Tomcat.Hui 2019/12/3 19:28 .Desc: VDERP-1325 分批开票 有条件展示 end. -->

            <div class="parts content2">
                <div class="formtitle mt10">
                    收票信息
                </div>
                <ul class="payplan">
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收票客户</label>
                        </div>
                        <div class="f_left inputfloat">
                            <span class="mr10 mt3" id="trader_name_span_2">${saleorder.invoiceTraderName}</span>
                            <input type="hidden" name="invoiceTraderId" id="trader_id_2" value="${saleorder.invoiceTraderId}">
                            <input type="hidden" name="invoiceTraderName" id="trader_name_2" value="${saleorder.invoiceTraderName}">
                        </div>
                    </li>
					<!--  add by Tomcat.Hui 2019/12/3 19:28 .Desc: VDERP-1325 分批开票 -->
					<div id="invoiceMethodDiv" <c:if test="${invoiceapplyFlag ne 0}">style="display: none" </c:if>>
						<li >
							<div class="infor_name infor_name72">
								<span>*</span>
								<label>开票方式</label>
							</div>
							<div class="f_left inputfloat tips-all">
								<c:choose>
									<c:when test="${saleorder.isHaveInvoiceApply ==1 && invoiceApply !=null }">
										<select class="input-middle" name="invoiceMethod" >
											<c:if test="${invoiceApply.isAuto == 1}">
												<option value="1">手动纸质开票</option>
											</c:if>
											<c:if test="${invoiceApply.isAuto == 2}">
												<option value="2">自动纸质开票</option>
											</c:if>
											<c:if test="${invoiceApply.isAuto == 3}">
												<option value="3">自动电子发票</option>
											</c:if>
										</select>
									</c:when>
									<c:otherwise>
										<select class="input-middle" name="invoiceMethod" ${saleorder.isHaveInvoiceApply ==1?'readonly="readonly"':''}>
											<option value="0">请选择</option>
											<c:if test="${saleorder.invoiceType == 681 or saleorder.invoiceType == 971}">
												<option value="1" <c:if test="${saleorder.invoiceMethod == 1}">selected</c:if>>手动纸质开票</option>
												<option value="2" <c:if test="${saleorder.invoiceMethod == 2}">selected</c:if>>自动纸质开票</option>
												<option value="3" <c:if test="${saleorder.invoiceMethod == 3}">selected</c:if>>自动电子发票</option>
											</c:if>
											<c:if test="${saleorder.invoiceType == 682  or saleorder.invoiceType == 972}">
												<option value="1" <c:if test="${saleorder.invoiceMethod == 1}">selected</c:if>>手动纸质开票</option>
												<option value="2" <c:if test="${saleorder.invoiceMethod == 2}">selected</c:if>>自动纸质开票</option>
											</c:if>
										</select>
									</c:otherwise>
								</c:choose>

								<div id="invoiceMethodMsg" style="clear:both"></div>
							</div>
							<div class="tips-error" style="display: none;color:red;line-height: 26px;">
								“手动纸质开票”的订单，不在自动开票推送的范围内，后期需要手动申请开票。
							</div>
						</li>
					</div>


<li>
<c:if test="${invoiceapplyFlag eq 0}">
	<div class="infor_name infor_name72">
		<span>*</span>
		<label>发票类型</label>
	</div>
</c:if>
<!-- 获取当前日期 -->
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" type="both" dateStyle="long" var="today" pattern="yyyy-MM-dd"/>
	<div class="f_left inputfloat">
<c:if test="${invoiceapplyFlag eq 0}">
	<c:choose>
		<c:when test="${saleorder.isHaveInvoiceApply ==1 && invoiceApply !=null }">
			<select class="input-middle" name="invoiceType" onchange="updateInvoiceType(this);">
				<c:forEach var="list" items="${invoiceTypes}">
					<c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">
						<option value="${list.sysOptionDefinitionId}">${list.title}</option>
					</c:if>
				</c:forEach>
			</select>
		</c:when>
		<c:otherwise>
			<select class="input-middle" name="invoiceType" onchange="updateInvoiceType(this);">
				<option value="0">请选择</option>
				<!-- 4月1号后税率只有13% -->
				<c:choose>
					<c:when test="${today >= '2019-04-01'}">
						<c:forEach var="list" items="${invoiceTypes}">
							<c:if test="${list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="list" items="${invoiceTypes}">
							<c:if test="${list.sysOptionDefinitionId eq 681 or list.sysOptionDefinitionId eq 682 or list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}">
								<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
		</c:otherwise>
	</c:choose>
</c:if>
	<input type="checkbox" name="isSendInvoiceCheckbox" class="mt5" onclick="isSendInvoiceChecked();" <c:if test="${saleorder.isSendInvoice == 0}">checked="checked"</c:if> >
	<input type="hidden" name="isSendInvoice" value="${saleorder.isSendInvoice}">
		<input type="hidden" id="invoiceapplyFlag" name="invoiceapplyFlag" value="${invoiceapplyFlag}">
	<span class="mt3">不寄送</span>
	<div id="invoiceTypeMsg" style="clear:both"></div>
	</div>
	</li>
	<c:choose>
                    	<c:when test="${saleorder.orderType eq 5 }">
                    		<li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收票联系人</label>
		                        </div>
		                        <div class="f_left">
		                        	<input maxLength="64" type="text" id="invoiceTraderContactName_5" name="invoiceTraderContactName" value="${saleorder.invoiceTraderContactName}" placeholder="请输入收票联系人" />
		                        	<div id="5_invoiceTraderContactName" style="clear:both"></div>
		                        </div>
		                    </li>
	                    	<li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收票手机号</label>
		                        </div>
		                        <div class="f_left">
		                        	<input maxLength="11" type="text" id="invoiceTraderContactMobile_5" name="invoiceTraderContactMobile" value="${saleorder.invoiceTraderContactMobile}" placeholder="请输入收票手机号" />
		                        	<div id="5_invoiceTraderContactMobile" style="clear:both"></div>
		                        </div>
		                    </li>
	                    	<li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收票地址</label>
		                        </div>
		                        <div class="f_left">
		                        	<select id="invoiceTraderAddressId-province" onchange="changeArea('invoiceTraderAddressId', 'invoiceTraderArea_5', 1)" >
			                        	<option value="0">请选择</option>
				                    	<c:if test="${not empty provinceList }">
				                    		<c:forEach items="${provinceList }" var="prov">
				                    			<option value="${prov.regionId }" <c:if test="${invoiceTraderAddressIdProvince eq prov.regionId }">selected="selected"</c:if> >${prov.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
									<select id="invoiceTraderAddressId-city" onchange="changeArea('invoiceTraderAddressId', 'invoiceTraderArea_5', 2)" >
			                        	<option value="0">请选择</option>
			                        	<c:if test="${not empty invoiceCityList }">
				                    		<c:forEach items="${invoiceCityList }" var="cy">
				                    			<option value="${cy.regionId }" <c:if test="${invoiceTraderAddressIdCity eq cy.regionId }">selected="selected"</c:if> >${cy.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
			                        <select id="invoiceTraderAddressId-zone" onchange="changeArea('invoiceTraderAddressId', 'invoiceTraderArea_5', 3)" >
			                        	<option value="0">请选择</option>
			                        	<c:if test="${not empty invoiceZoneList }">
				                    		<c:forEach items="${invoiceZoneList }" var="zo">
				                    			<option value="${zo.regionId }" <c:if test="${invoiceTraderAddressIdZone eq zo.regionId }">selected="selected"</c:if> >${zo.regionName }</option>
				                    		</c:forEach>
				                    	</c:if>
									</select>
									<input type="hidden" name="invoiceTraderArea" id="invoiceTraderArea_5" value="${saleorder.invoiceTraderArea}"/>
									<input type="hidden" name="invoiceTraderAreaId" id="invoiceTraderAddressId" value="${invoiceTraderAddressIdZone}"/>
		                        	<input maxLength="256" type="text" id="invoiceTraderAddress_5" class="input-xx" name="invoiceTraderAddress" value="${saleorder.invoiceTraderAddress}" placeholder="请输入收票地址" />
		                        	<div id="5_invoiceTraderAddress" style="clear:both"></div>
		                        </div>
		                    </li>
		                    <li>
		                        <div class="infor_name infor_name72 mt0">
		                            <span>*</span>
		                            <label>收票邮箱</label>
		                        </div>
		                        <div class="f_left">
		                        	<input maxLength="64" class="input-xx" id="invoiceEmail_5" type="text" name="invoiceEmail" value="${saleorder.invoiceEmail}" placeholder="请输入收票邮箱" />
		                        	<div id="5_invoiceEmail" style="clear:both"></div>
		                        </div>
		                    </li>
                    	</c:when>
                    	<c:otherwise>
		                    <li>
		                        <div class="infor_name infor_name72">
		                            <span>*</span>
		                            <label>收票联系人</label>
		                        </div>
		                        <div class="f_left inputfloat">
		                            <select class="input-xx" id="contact_2" name="invoiceTraderContactId">
		                                <option value="0">请选择</option>
		                                <c:if test="${not empty invoiceTraderContactList}">
											<c:forEach items="${invoiceTraderContactList}" var="list" varStatus="status">
											<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.invoiceTraderContactId}">selected="selected"</c:if>>${list.name}/${list.telephone}/${list.mobile}</option>
											</c:forEach>
						                </c:if>
		                            </select>
		                            <input type="hidden" name="invoiceTraderContactName">
		                            <input type="hidden" name="invoiceTraderContactTelephone">
		                            <input type="hidden" name="invoiceTraderContactMobile">
		                        	<div id="invoiceTraderContactIdMsg" style="clear:both"></div>
		                        </div>
		                    </li>
		                    <li>
		                        <div class="infor_name infor_name72">
		                            <span>*</span>
		                            <label>收票地址</label>
		                        </div>
		                        <div class="f_left inputfloat">
		                            <select class="input-xx" id="address_2" name="invoiceTraderAddressId">
		                                <option value="0">请选择</option>
		                                <c:if test="${not empty invoiceTraderAddressList}">
											<c:forEach items="${invoiceTraderAddressList}" var="list" varStatus="status">
											<option value="${list.traderAddress.traderAddressId}" <c:if test="${list.traderAddress.traderAddressId eq saleorder.invoiceTraderAddressId}">selected="selected"</c:if>>${list.area}/${list.traderAddress.address}</option>
											</c:forEach>
						                </c:if>
		                            </select>
		                            <input type="hidden" name="invoiceTraderArea">
		                           	<input type="hidden" name="invoiceTraderAddress">
		                        	<div id="invoiceTraderAddressIdMsg" style="clear:both"></div>
		                        </div>
		                    </li>
                    	</c:otherwise>
                    </c:choose>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>开票备注</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="text" name="invoiceComments" id="invoiceComments" value="${saleorder.invoiceComments}" placeholder="对内使用，适用于向财务部同事告知开票要求" class="input-xx" />
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>暂缓开票</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="checkbox" name="isDelayInvoiceCheckbox" class="mt5" onclick="isDelayInvoiceChecked();"<c:if test="${saleorder.isDelayInvoice == 1}">checked="checked"</c:if>>
                            <input type="hidden" name="isDelayInvoice" value="${saleorder.isDelayInvoice}">
                        </div>
                    </li>
                </ul>
            </div>
		<c:if test="${invoiceModifyflag ne 1}">
            <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">产品信息</div>
                </div>
                <table class="table  table-bordered table-striped table-condensed table-centered">
                    <thead>
                        <tr>
                            <th style="width:50px">序号</th>
	                        <th style="width:140px">产品名称</th>
	                        <th style="width:80px">品牌</th>
	                        <th style="width:70px">制造商型号</th>
	                        <th style="width:80px">单价</th>
	                        <th style="width:35px">数量</th>
	                        <th style="width:35px">单位</th>
	                        <th style="width:80px">总额</th>
	                        <th style="width:70px">货期</th>
	                        <th style="width:50px">含安调</th>
	                        <th>产品备注</th>
	                        <th>内部备注</th>
	                        <th style="width:150px">直发</th>
                        </tr>
                    </thead>
                    <tbody>
	                	<c:set var="num" value="0"></c:set>
	                	<c:set var="count" value="0"></c:set>
						<c:set var="totleMoney" value="0.00"></c:set>
						<c:set var="id_str" value=""></c:set>
	                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
	                		<c:if test="${list.isDelete eq 0}">
	                			<c:set var="count" value="${count+1}"></c:set>
								<c:set var="num" value="${num + list.num}"></c:set>
								<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
								<c:set var="id_str" value="${id_str}_${list.saleorderGoodsId}"></c:set>
		                    <tr>
		                    	<c:set var="lwNum" value="0"></c:set>
		                        <td style="overflow:hidden;padding-left:12px;">
		                        	<c:if test="${(empty list.buyNum) or (list.buyNum eq 0)}">
		                        		<c:set var="lwNum" value="${lwNum + 1}"></c:set>
			                        	<input type="checkbox" name="goodsCheckName" onclick="goodsCheckClick(this);" value="${list.saleorderGoodsId}" class="f_left" autocomplete="off"/>
		                        	</c:if>
		                        	<span class="f_left">${staut.count}</span>
		                        </td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
		                            	<c:choose>
											<c:when test="${list.isDelete eq 1}">
												<span>${newSkuInfosMap[list.sku].SHOW_NAME}<br/></span>
				                                <span>${newSkuInfosMap[list.sku].SKU_NO} <br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>
											</c:when>
											<c:otherwise>
												<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${newSkuInfosMap[list.sku].SHOW_NAME}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
				                                <span>${newSkuInfosMap[list.sku].SKU_NO} <br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>

												<c:set var="skuNo" value="${list.sku}"></c:set>
												<%@ include file="../../common/new_sku_common_tip.jsp" %>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
		                        <td>${newSkuInfosMap[list.sku].MODEL}</td>
		                        <td>${list.price}</td>
		                        <td>${list.num}</td>
		                        <td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
		                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>${list.deliveryCycle}</td>
		                        <td>
		                        	<c:choose>
										<c:when test="${list.haveInstallation eq 0}">否</c:when>
										<c:otherwise>是</c:otherwise>
									</c:choose>
		                        </td>
		                        <td>
		                        	<textarea rows="3" cols="20" name="goodsComments_${list.saleorderGoodsId}"  maxlength="60">${list.goodsComments}</textarea>
		                        	<input type="hidden" name="oldGoodsComments_${list.saleorderGoodsId}" value="${list.goodsComments}">
		                        </td>
		                        <td>${list.insideComments}</td>
	                            <td>
	                            	<c:choose>
										<c:when test="${((empty list.buyNum) or (list.buyNum eq 0)) and list.deliveryStatus eq 0}">
											<select style="width:50px;" name="deliveryDirect_${list.saleorderGoodsId}" id="deliveryDirect_${list.saleorderGoodsId}" autocomplete="off">
			                            		<option <c:if test="${list.deliveryDirect eq 1}">selected="selected"</c:if> value="1">是</option>
			                            		<option <c:if test="${list.deliveryDirect eq 0}">selected="selected"</c:if> value="0">否</option>
			                            	</select>
			                            	<input style="margin-top:10px;" type="text" name="deliveryDirectComments_${list.saleorderGoodsId}" id="deliveryDirectComments_${list.saleorderGoodsId}" value="${list.deliveryDirectComments}" autocomplete="off">
				                        	<div id="commentsMsg_${list.saleorderGoodsId}"></div>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="deliveryDirect_${list.saleorderGoodsId}" value="${list.deliveryDirect}">
		                        			<input type="hidden" name="deliveryDirectComments_${list.saleorderGoodsId}" value="${list.deliveryDirectComments}">
											<div id="commentsMsg_${list.saleorderGoodsId}"></div>
										</c:otherwise>
									</c:choose>
		                        	<input type="hidden" name="oldDeliveryDirect_${list.saleorderGoodsId}" value="${list.deliveryDirect}">
		                        	<input type="hidden" name="oldDeliveryDirectComments_${list.saleorderGoodsId}" value="${list.deliveryDirectComments}">
		                        </td>
		                    </tr>
		                    </c:if>
	                    </c:forEach>
	                    <tr style="background: #eaf2fd;">
	                        <td colspan="13" class="text-left">
	                        	<c:if test="${lwNum > 0}">
		                        	<input type="checkbox" name="goodsCheckAllName" id="goodsCheckAllName" onclick="goodsCheckAllClick(this);" autocomplete="off"/><span>全选</span>
			                    	<span style="display: none;"> <!-- 弹框 -->
										<div class="title-click nobor  pop-new-data" id="saleGoodsDeliveryDirect"></div>
									</span>
			                    	<a onclick="updateSaleGoodsInit(${saleorder.saleorderId});">批量修改</a>
			                    	&nbsp;&nbsp;
	                        	</c:if>
	                                                                  总件数<span class="font-red">${num}</span>， 总金额
	                            <span class="font-red"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
	                            <input type="hidden" id="goodsTotleMoney"  value="${totleMoney}">
	                        </td>
	                    </tr>
	                </tbody>
                </table>
            </div>
		</c:if>
            <div class="font-grey9 mt-5 mb10">友情提示<br/>1、仅限未采购商品可以修改普发/直发；
            </div>
            <div class="add-tijiao" style="margin-bottom:50px;">
            	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
            	<input type="hidden" id="id_str" name="id_str" value="${id_str}">
            	<input type="hidden" name="formToken" value="${formToken}"/>
                <button type="button" class="bt-bg-style bg-deep-green" onclick="editSubmit(${saleorder.orderType});">提交审核</button>
                <button class="dele" id="close-layer" type="button" onclick="closeGoBack();">取消</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>