<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="申请修改" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/modify_apply_init.js?rnd=<%=Math.random()%>'></script>
	 <div class="form-list  form-tips5">
        <form method="post" action="./saveBuyorderApplyUpdate.do" id="myform">
        	 <div class="formtitle mt10">
                  		  收货信息
               </div>
            <ul>
                <li>
                    <div class="form-tips">
                        <lable>是否直发</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul>
                                <li>
	                              	 <input type="hidden"  id="isUpdateDeliveryDirect" value="${bv.isUpdateDeliveryDirect}">
	                                 <input type="radio" name="deliveryDirect" value="0" <c:if test="${bv.deliveryDirect eq 0}">checked="checked"</c:if>>
	                                 <label><span>普发</span></label>
	                             </li>
	                             <li>
	                                 <input type="radio" name="deliveryDirect" value="1"<c:if test="${bv.deliveryDirect eq 1}">checked="checked"</c:if>>
	                                 <label><span>直发</span></label>
	                             </li>
                            </ul>
                             <div id="isUpdateDeliveryDirectError" ></div>
                        </div>
                    </div>
                </li>
                <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货客户</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.takeTraderName}</span>
                        </div>
                    </div>
                </li>
                <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.takeTraderContactName}/${bv.takeTraderContactTelephone}/${bv.takeTraderContactMobile}</span>
                        </div>
                    </div>
                </li>
                <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货地区</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.takeTraderArea}</span>
                        </div>
                    </div>
                </li>
                <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.takeTraderAddress}</span>
                        </div>
                    </div>
                </li>
                
                <li class="ptz none">
                    <div class="form-tips">
                        <lable>收货客户</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.saleTakeTraderName}</span>
                        </div>
                    </div>
                    
                </li>
                <li class="ptz none">
                    <div class="form-tips">
                        <lable>收货联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.saleTakeTraderContactName}/${bv.saleTakeTraderContactTelephone}/${bv.saleTakeTraderContactMobile}</span>
                        </div>
                    </div>
                </li>
                <li class="ptz none">
                    <div class="form-tips">
                        <lable>收货地区</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.saleTakeTraderArea}</span>
                        </div>
                    </div>
                </li>
                <li class="ptz none">
                    <div class="form-tips">
                        <lable>收货地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.saleTakeTraderAddress}</span>
                        </div>
                    </div>
                </li>
                
                <li class="pf <c:if test="${bv.deliveryDirect eq 1}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货客户</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${bv.takeTraderName}</span>
                        </div>
                    </div>
                    <%-- <input type="hidden" name="takeTraderId" value="0">
	            	<input type="hidden" name="takeTraderName" value="${bv.takeTraderName}">
	            	<input type="hidden" name="takeTraderContactId" value="0">
	            	<input type="hidden" name="takeTraderContactName" value="${bv.takeTraderContactName}">
	            	<input type="hidden" name="takeTraderContactMobile" value="${bv.takeTraderContactMobile}">
	            	<input type="hidden" name="takeTraderContactTelephone" value="${bv.takeTraderContactTelephone}">
	            	<input type="hidden" name="takeTraderAddressId" value="${bv.takeTraderAddressId}">
	            	<input type="hidden" name="takeTraderArea" value="${bv.takeTraderArea}">
	            	<input type="hidden" name="takeTraderAddress" value="${bv.takeTraderAddress}"> --%>
                </li>
                <li class="pf <c:if test="${bv.deliveryDirect eq 1}">none</c:if>">
                    <div class="form-tips">
                        <lable>收货地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-xx" id="address_pf" >
                                <c:forEach var="list" items="${addressList}">
			                    	<option value="${list.addressId}|${list.areas}|${list.address}|${list.contactName}|${list.mobile}|${list.telephone}" 
			                    			<c:if test="${list.addressId eq bv.takeTraderAddressId }">selected="selected"</c:if>>
			                    			${list.areas}/${list.address}/${list.contactName}/${list.mobile}</option>
			                    </c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li class="ztp none">
                    <div class="form-tips">
                        <lable>收货客户</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <span>${companyName}</span>
                        </div>
                    </div>
                    
                </li>
                <li class="ztp none">
                    <div class="form-tips">
                        <lable>收货地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-xx" id="address_pf" >
                                <c:forEach var="list" items="${addressList}">
			                    	<option value="${list.addressId}|${list.areas}|${list.address}|${list.contactName}|${list.mobile}|${list.telephone}" 
			                    			<c:if test="${list.addressId eq bv.takeTraderAddressId }">selected="selected"</c:if>>
			                    			${list.areas}/${list.address}/${list.contactName}/${list.mobile}</option>
			                    </c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>物流备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" name="logisticsComments"  placeholder="对内使用，适用于向物流部同事告知发货要求，默认同步客户信息中物流备注" 
                            		id="logisticsComments" class="input-xx" value="${bv.logisticsComments }"/>
                        </div>
                        <div id="logisticsCommentsError"></div>
                    </div>
                </li>
             </ul>
             <div class="formtitle ">
                  		  收票信息
               </div>
              <ul>
                <li>
                    <div class="form-tips">
                        <lable>收票类型</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-middle" name="invoiceType">
                                <c:forEach var="list" items="${receiptTypes}" varStatus="status">
									<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq bv.invoiceType}">selected="selected"</c:if>>${list.title}</option>
								</c:forEach>
                            </select>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>收票备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" placeholder="对内使用，适用于向财务部同事告知收票要求" id="invoiceComments"
	                            	class="input-xx" name="invoiceComments" value="${bv.invoiceComments }"/>
                        </div>
                        <div id="invoiceCommentsError"></div>
                    </div>
                </li>
             </ul>
             <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">产品信息</div>
                </div>
                <table class="table  table-bordered table-striped table-condensed table-centered">
                    <thead>
                        <tr>
                            <th class="wid2">序号</th>
	                        <th class="wid15">产品名称</th>
	                        <th class="wid8">品牌</th>
	                        <th class="wid8">型号</th>
	                        <th class="wid8">单价</th>
	                        <th class="wid5">数量</th>
	                        <th class="wid8">单位</th>
	                        <th class="wid25">采购备注</th>
                        </tr>
                    </thead>
                    <tbody>
	                	<c:forEach var="bgv" items="${bv.bgvList}" varStatus="staut">
		                    <tr>
		                        <td>${staut.count}</td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
										<span class="font-blue">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewgoods${bgv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[bgv.sku].SHOW_NAME}</a>&nbsp;
												<i class="iconbluemouth contorlIcon"></i><br/>
										</span>

                                        <c:set var="skuNo" value="${bgv.sku}"></c:set>
                                        <%@ include file="../../common/new_sku_common_tip.jsp" %>

		                                <%--<div class="pos_abs customernameshow">--%>
											<%--物料编码： ${bgv.materialCode}<br> --%>
											<%--注册证号： ${bgv.registrationNumber}<br>--%>
											<%--管理类别：  ${bgv.manageCategoryName}<br>--%>
											<%--产品归属：<c:if test="${not empty bgv.userList }">--%>
														<%--<c:forEach items="${bgv.userList }" var="user"--%>
															<%--varStatus="st">--%>
													<%--${user.username } <c:if--%>
																<%--test="${st.count != bgv.userList.size() }">、</c:if>--%>
														<%--</c:forEach>--%>
													<%--</c:if>  <br> --%>
											<%--库存：${bgv.goodsStock}<br> --%>
											<%--可用库存：${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}<br> --%>
											<%--订单占用：${bgv.orderOccupy == null ? 0 : bgv.orderOccupy}<br>--%>
										<%--</div>--%>
										<div>${bgv.sku}</div>
		                            </div>
		                        </td>
		                        <td>${newSkuInfosMap[bgv.sku].BRAND_NAME}</td>
		                        <td>${newSkuInfosMap[bgv.sku].MODEL}</td>
		                        <td>${bgv.price}</td>
		                        <td>${bgv.num}</td>
		                        <td>${newSkuInfosMap[bgv.sku].UNIT_NAME}</td>
		                        <td>
		                        	<textarea onblur="changComments(this,${bgv.buyorderGoodsId});" style="margin:0px;width: 100%;">${bgv.insideComments}</textarea>
		                        	<input type="hidden" name="oldInsideCommentsArray" value="${bgv.buyorderGoodsId}|${bgv.insideComments}">
		                        	<input type="hidden" name="insideCommentsArray" value="">
		                        </td>
	                            
		                    </tr>
	                    </c:forEach>
	                </tbody>
                </table>
            </div>
            <div class="font-grey9 mt-5 mb10">友情提示<br/>1、普发改直发仅限当前采购商品关联的销售商品全为直发状态；
            </div>
            <div class="add-tijiao" style="margin-bottom:50px;">
            
            	<input type="hidden" name="takeTraderId" value="">
            	<input type="hidden" name="takeTraderName" value="">
            	<input type="hidden" name="takeTraderContactId" value="0">
            	<input type="hidden" name="takeTraderContactName" value="">
            	<input type="hidden" name="takeTraderContactMobile" value="">
            	<input type="hidden" name="takeTraderContactTelephone" value="">
            	<input type="hidden" name="takeTraderAddressId" value="">
            	<input type="hidden" name="takeTraderArea" value="">
            	<input type="hidden" name="takeTraderAddress" value="">
            	
            	<input type="hidden" id="companyName" value="${companyName}">
            	
            	<input type="hidden" id="ptz_takeTraderId" value="${bv.saleTakeTraderId}">
            	<input type="hidden" id="ptz_takeTraderName" value="${bv.saleTakeTraderName}">
            	<input type="hidden" id="ptz_takeTraderContactId" value="${bv.saleTakeTraderContactId}">
            	<input type="hidden" id="ptz_takeTraderContactName" value="${bv.saleTakeTraderContactName}">
            	<input type="hidden" id="ptz_takeTraderContactMobile" value="${bv.saleTakeTraderContactMobile}">
            	<input type="hidden" id="ptz_takeTraderContactTelephone" value="${bv.saleTakeTraderContactTelephone}">
            	<input type="hidden" id="ptz_takeTraderAddressId" value="${bv.saleTakeTraderAddressId}">
            	<input type="hidden" id="ptz_takeTraderArea" value="${bv.saleTakeTraderArea}">
            	<input type="hidden" id="ptz_takeTraderAddress" value="${bv.saleTakeTraderAddress}">
            	
            	<input type="hidden" name="formToken" value="${formToken}"/>
            	
            	<input type="hidden" name="buyorderId" value="${bv.buyorderId}">
            	<input type="hidden" name="buyorderNo" value="${bv.buyorderNo}">
            	<input type="hidden" name="oldDeliveryDirect" value="${bv.deliveryDirect}">
            	<input type="hidden" name="oldTakeTraderId" value="${bv.takeTraderId}">
            	<input type="hidden" name="oldTakeTraderName" value="${bv.takeTraderName}">
            	<input type="hidden" name="oldTakeTraderContactId" value="${bv.takeTraderContactId}">
            	<input type="hidden" name="oldTakeTraderContactName" value="${bv.takeTraderContactName}">
            	<input type="hidden" name="oldTakeTraderContactMobile" value="${bv.takeTraderContactMobile}">
            	<input type="hidden" name="oldTakeTraderContactTelephone" value="${bv.takeTraderContactTelephone}">
            	<input type="hidden" name="oldTakeTraderAddressId" value="${bv.takeTraderAddressId}">
            	<input type="hidden" name="oldTakeTraderArea" value="${bv.takeTraderArea}">
            	<input type="hidden" name="oldTakeTraderAddress" value="${bv.takeTraderAddress}">
            	<input type="hidden" name="oldLogisticsComments" value="${bv.logisticsComments}">
            	<input type="hidden" name="oldInvoiceType" value="${bv.invoiceType}">
            	<input type="hidden" name="oldInvoiceComments" value="${bv.invoiceComments}">
            	
                <button type="button" class="bt-bg-style bg-deep-green" onclick="editSubmit();">提交审核</button>
            </div>
          </form>
     </div>


	<%-- <div class="content mt10 ">
        <form action="${pageContext.request.contextPath}/order/buyorder/modifyApplySave.do" method="post" id="editForm">
            <div class="parts content1 ">
                <div class="formtitle mt10">
                    收货信息
                </div>
                <ul class="payplan">
                	<li>
		                 <div class="form-tips">
		                     <lable>是否直发</lable>
		                 </div>
		                 <div class="f_left ">
		                     <div class="form-blanks">
		                         <ul>
		                             <li>
		                              	 <input type="hidden"  id="isUpdateDeliveryDirect" value="${bv.isUpdateDeliveryDirect}">
		                                 <input type="radio" name="deliveryDirect" value="0" <c:if test="${bv.deliveryDirect eq 0}">checked="checked"</c:if>>
		                                 <label><span>普发</span></label>
		                             </li>
		                             <li>
		                                 <input type="radio" name="deliveryDirect" value="1"<c:if test="${bv.deliveryDirect eq 1}">checked="checked"</c:if>>
		                                 <label><span>直发</span></label>
		                             </li>
		                         </ul>
		                     </div>
		                     <div id="isUpdateDeliveryDirectError" ></div>
		                 </div>
		            </li>
		            <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货客户</label>
                        </div>
                        <div class="f_left inputfloat">${bv.takeTraderName}</div>
                    </li>
                    <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货联系人</label>
                        </div>
                        <div class="f_left inputfloat">
                            ${bv.takeTraderContactName}/${bv.takeTraderContactTelephone}/${bv.takeTraderContactMobile}
                        </div>
                    </li>
                    <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货地区</label>
                        </div>
                        <div class="f_left inputfloat">
                            ${bv.takeTraderArea}
                        </div>
                    </li>
                    <li class="zf <c:if test="${bv.deliveryDirect eq 0}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货地址</label>
                        </div>
                        <div class="f_left inputfloat">
                            ${bv.takeTraderAddress}
                        </div>
                    </li>
                    
                    <li class="pf <c:if test="${bv.deliveryDirect eq 1}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货客户</label>
                        </div>
                        <div class="f_left inputfloat">
                            <span class=" mr10 mt3" id="trader_name_span_1">${bv.takeTraderName}</span>
                            <input type="hidden" name="takeTraderId" id="trader_id_1" value="${bv.takeTraderId}">
                            <input type="hidden" name="takeTraderName" id="trader_name_1" value="${bv.takeTraderName}">
                        </div>
                    </li>
                    <li class="pf <c:if test="${bv.deliveryDirect eq 1}">none</c:if>">
                        <div class="infor_name infor_name72">
                            <label>收货地址</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx" id="address_1" name="takeTraderAddressId">
                                <c:forEach var="list" items="${addressList}">
			                    	<option value="${list.addressId}|${list.companyName}|${list.areas}" <c:if test="${list.addressId eq bv.takeTraderAddressId }">selected="selected"</c:if>>
			                    			${list.areas}/${list.address}/${list.contactName}/${list.mobile}</option>
			                    </c:forEach>
                            </select>
                            <input type="hidden" name="takeTraderArea">
                           	<input type="hidden" name="takeTraderAddress">
                        	<div id="takeTraderAddressIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="parts content1 ">
                <div class="formtitle mt10">
                    收票信息
                </div>
                <ul class="payplan">
                   
                    <li>
                        <div class="infor_name infor_name72">
                        	<span>*</span>
                            <label>发票类型</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="invoiceType">
                                <c:forEach var="list" items="${receiptTypes}" varStatus="status">
									<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq bv.invoiceType}">selected="selected"</c:if>>${list.title}</option>
								</c:forEach>
                            </select>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">产品信息</div>
                </div>
                <table class="table  table-bordered table-striped table-condensed table-centered">
                    <thead>
                        <tr>
                            <th class="wid2">序号</th>
	                        <th class="wid15">产品名称</th>
	                        <th class="wid8">品牌</th>
	                        <th class="wid8">型号</th>
	                        <th class="wid8">单价</th>
	                        <th class="wid5">数量</th>
	                        <th class="wid8">单位</th>
	                        <th class="wid20">采购备注</th>
                        </tr>
                    </thead>
                    <tbody>
	                	<c:forEach var="bgv" items="${bv.bgvList}" varStatus="staut">
		                    <tr>
		                        <td>${staut.count}</td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
										<span class="font-blue">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewgoods${bgv.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${bgv.goodsName}</a>&nbsp;
												<i class="iconbluemouth contorlIcon"></i><br/>
										</span>
		                                <div class="pos_abs customernameshow">
											物料编码： ${bgv.materialCode}<br> 
											注册证号： ${bgv.registrationNumber}<br>
											管理类别：  ${bgv.manageCategoryName}<br>
											产品归属：<c:if test="${not empty bgv.userList }">
														<c:forEach items="${bgv.userList }" var="user"
															varStatus="st">
													${user.username } <c:if
																test="${st.count != bgv.userList.size() }">、</c:if>
														</c:forEach>
													</c:if>  <br> 
											库存：${bgv.goodsStock}<br> 
											可用库存：${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}<br> 
											订单占用：${bgv.orderOccupy}<br>
										</div>
										<div>${bgv.sku}</div>
		                            </div>
		                        </td>
		                        <td>${bgv.brandName}</td>
		                        <td>${bgv.model}</td>
		                        <td>${bgv.price}</td>
		                        <td>${bgv.num}</td>
		                        <td>${bgv.unitName}</td>
		                        <td>
		                        	<textarea alt="${bgv.buyorderGoodsId}" class="wid25" onblur="changComments(${bgv.buyorderGoodsId});">${bgv.insideComments}</textarea>
		                        	<input type="hidden" name="oldInsideComments" value="${bgv.buyorderGoodsId}|${bgv.insideComments}">
		                        	<input type="hidden" name="insideComments" value="">
		                        </td>
	                            
		                    </tr>
	                    </c:forEach>
	                </tbody>
                </table>
            </div>
            <div class="font-grey9 mt-5 mb10">友情提示<br/>1、普发改直发仅限当前采购商品关联的销售商品全为直发状态；
            </div>
            <div class="add-tijiao" style="margin-bottom:50px;">
            	<input type="hidden" name="formToken" value="${formToken}"/>
            	<input type="hidden" name="buyorderId" value="${bv.buyorderId}">
            	<input type="hidden" name="oldDeliveryDirect" value="${bv.deliveryDirect}">
            	<input type="hidden" name="oldTakeTraderId" value="${bv.takeTraderId}">
            	<input type="hidden" name="oldTakeTraderName" value="${bv.takeTraderName}">
            	<input type="hidden" name="oldTakeTraderContactId" value="${bv.takeTraderContactId}">
            	<input type="hidden" name="oldTakeTraderContactName" value="${bv.takeTraderContactName}">
            	<input type="hidden" name="oldTakeTraderContactMobile" value="${bv.takeTraderContactMobile}">
            	<input type="hidden" name="oldTakeTraderContactTelephone" value="${bv.takeTraderContactTelephone}">
            	<input type="hidden" name="oldTakeTraderAddressId" value="${bv.takeTraderAddressId}">
            	<input type="hidden" name="oldTakeTraderArea" value="${bv.takeTraderArea}">
            	<input type="hidden" name="oldTakeTraderAddress" value="${bv.takeTraderAddress}">
            	<input type="hidden" name="oldLogisticsComments" value="${bv.logisticsComments}">
            	<input type="hidden" name="oldInvoiceType" value="${bv.invoiceType}">
            	<input type="hidden" name="oldInvoiceComments" value="${bv.invoiceComments}">
                <button type="button" class="bt-bg-style bg-deep-green" onclick="editSubmit();">提交审核</button>
                <button class="dele" id="close-layer" type="button" onclick="goUrl('${pageContext.request.contextPath}/order/saleorder/view.do?saleorderId=${saleorder.saleorderId}')">取消</button>
            </div>
        </form>
    </div> --%>
<%@ include file="../../common/footer.jsp"%>