<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后-退货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_buyorder.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/order/buyorder/saveAddAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=th&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" checked="true" name="type" value="546">
                                    <label>退货</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=hh&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="547">
                                    <label>换货</label>
                                </a>
                            </li>
                <!--             <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=tp&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="548">
                                    <label>退票</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=tk&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="549">
                                    <label>退款</label>
                                </a>
                            </li>--> 
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="" name="orderId" value="${buyorder.buyorderId}" />
            <input type="hidden" id="" name="orderNo" value="${buyorder.buyorderNo}" />
            <input type="hidden" id="" name="traderId" value="${buyorder.traderId}" />
            <input type="hidden" id="" name="thGoodsId" value="253620" />
            <input type="hidden" id="shtype" value="th" />
            <input type="hidden" name="formToken" value="${formToken}"/>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>售后原因</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul>
                        	<c:forEach items="${sysList}" var="sys" >
                        		<c:if test="${sys.relatedField eq 546 }">
                        			<li>
		                                <input type="radio" name="reason" value="${sys.sysOptionDefinitionId}">
		                                <label>${sys.title}</label>
		                            </li>
                        		</c:if>
                        	</c:forEach>
                        </ul>
                    </div>
                    <div id="reasonError" class="font-red " style="display: none">售后原因不允许为空</div>
                </div>
            </li>
            <li>
                <div class="parts">
                    <div class="title-container">
                        <div class="table-title nobor">
                            选择商品
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="wid5">选择</th>
                                <th class="wid18">产品名称</th>
                                <th class="wid10">品牌</th>
                                <th class="wid10">型号</th>
                                <th class="wid8">物料编码</th>
                                <th class="wid8">采购价</th>
                                <th class="wid8">数量</th>
                                <th class="wid6">单位</th>
                                <th class="wid12">售后数量/商品数量</th>
                                <th class="wid8">退货方式</th>
                                <th class="wid10">已收票数量</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${buyorder.bgvList}" var="bgv">
                        	<c:if test="${bgv.goodsId ne 140633 && bgv.goodsId ne 253620 && bgv.goodsId ne 251462 && bgv.goodsId ne 127063 && bgv.goodsId ne 251526 && bgv.goodsId ne 256675}"></c:if>
                        		 <tr>
	                                <td>
		                                <c:if test="${bgv.goodsId ne 253620}">
		                                	<input type="checkbox" name="oneSelect" alt="${bgv.buyorderGoodsId}">
		                                </c:if>
	                                	<input type="hidden" name="afterSaleNums" >
	                                	<input type="hidden" name="goodsIds" value="${bgv.goodsId}">
	                                	<input type="hidden" name="prices" value="${bgv.price}">
		                                <input type="hidden" name="refundAmount" id="refundAmount">
		                                <input type="hidden" name="receiveNums" id="receiveNums_${bgv.buyorderGoodsId}" value="${bgv.afterSaleUpLimitNum}">
	                                </td>
	                                 <td class="text-left">
	                                    <span class="font-blue cursor-pointer addtitle" 
	                                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${bgv.goodsName}</span>
	                                    <div>${bgv.sku}</div>
	                                </td>
	                                <td>${bgv.model}</td>
	                                <td>${bgv.brandName}</td>
	                                <td>${bgv.materialCode}</td>
	                                <td>${bgv.price}</td>
	                                <td>${bgv.num}</td>
	                                <td>${bgv.unitName}</td>
	                                <td><input type="text" style="width:50px;" alt1="${bgv.buyorderGoodsId}">/<span alt1="${bgv.buyorderGoodsId}">${bgv.afterSaleUpLimitNum}</span></td>
	                                <td>
	                                    <select id="${bgv.buyorderGoodsId}">
	                                        <option value="0" <c:if test ="${buyorder.deliveryDirect eq 0}">selected="selected"</c:if>>普发</option>
	                                        <option value="1" <c:if test ="${buyorder.deliveryDirect eq 1}">selected="selected"</c:if>>直发</option>
	                                    </select>
	                                </td>
	                                <td><fmt:formatNumber type="number" value="${bgv.invoiceNum}" pattern="0.00" maxFractionDigits="2" /></td>
	                            </tr>
	                            
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
            </li>
            <li style="margin-top:-13px;">
                 <div class="form-tips">
                     <span>*</span>
                     <lable>详情说明</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks ">
                         <textarea name="comments" id="comments" placeholder="请详述客户需求及退货要求以便售后同事联系" rows="5" class="wid90"></textarea>
                     </div>
                     <div id="commentsError"></div>
                 </div>
             </li>
             <li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>联系人</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks">
                         <select class="input-largest" name="traderContactId" id="traderContactId">
                          <c:forEach items="${buyorder.tcList}" var="tc">
                          	<option value="${tc.traderContactId}" 
                          		<c:if test="${buyorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                          </c:forEach> 
                         </select>
                     </div>
                     <div id="traderContactIdError"></div>
                 </div>
             </li>
             <li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>收货地址</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks">
                          <select class="input-largest" name="takeMsg" id="traderAddressId">
                          <c:forEach items="${buyorder.traderAddressVoList}" var="tav">
                          	<option value="${tav.traderAddress.traderAddressId}|${tav.traderAddress.areaId}|${tav.area}|${tav.traderAddress.address}" 
                          		<c:if test="${buyorder.takeTraderAddressId eq tav.traderAddress.traderAddressId}">selected="selected"</c:if>>${tav.area}/${tav.traderAddress.address}</option>
                          </c:forEach> 
                         </select>
                         <c:forEach items="${buyorder.traderAddressVoList}" var="tav">
	                            <input type="hidden" id="${tav.traderAddress.traderAddressId}" value="${tav.traderAddress.areaIds}"/>
	                         </c:forEach>
                     </div>
                     <div id="traderAddressIdError"></div>
                 </div>
             </li>
             <li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>款项退还</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks">
                         <ul>
                             <li>
                                 <input type="radio" name="refund" value="1" checked="checked">
                                 <label><span>退至公司账户</span><span class="font-grey9">(由财务结算)</span></label>
                             </li>
                             <li>
                                 <input type="radio" name="refund" value="2">
                                 <label>
                                 	<span>退至供应商余额</span><span class="font-grey9">(可用于支付该供应商其他订单)</span>
                                 </label>
                             </li>
                         </ul>
                     </div>
                     <div id="refundError" class="font-red " style="display: none">款项退还不允许为空</div>
                 </div>
             </li>
             <li>
                 <div class="form-tips">
                     <lable>上传附件</lable>
                 </div>
                 <input type="hidden" id="domain" name="domain" value="${domain}">
                 <div class="f_left ">
                     <div class="form-blanks">
                     	<div class="pos_rel f_left">
                            <input type="file" class="uploadErp" id='file_1' name="lwfile" onchange="uploadFile(this,1);">
                            <input type="text" class="input-largest" id="name_1" readonly="readonly"
                            	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
                            <input type="hidden" id="uri_1" name="fileUri" >
		    			</div>
                           <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                           <!-- 上传成功出现 -->
                        <div class="f_left">
                            <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_1">查看</a>
	                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
                    	</div>
                    	<div class='clear'></div>
                     </div>
                     <div class="mt8" id="conadd">
                         <span class="bt-border-style bt-small border-blue" onclick="conadd();">继续添加</span>
                     </div>
                     <div class="pop-friend-tips mt6">
                       	  退货须知：
                         <br/> 1、退货商品不得影响二次销售； 
                         <br/> 2、退货商品必须配件齐全（核对配置单包含电源线说明书在内的所有配件齐全）；
                         <br/> 3、退货商品必须内外包装完好无损；
                         <br/> 4、退货商品必须在有效期内；
                         <br/> 5、退货商品有封口的必须封口完好无损；
                         <br/> 6、已开票的产品必须将发票一并寄回；
                         <br/> 7、上传附件不得超过2M，可以上传jpg、png、word、pdf等格式；
                         <br/> 8、实际退款金额根据商品价值和退换货手续费决定；
                         <div class="add-tijiao text-left mt8">
                             <button type="submit" id="submit">提交</button>
                         </div>
                     </div>
                 </div>
             </li>
        </ul>
   </form>
</div>
<%@ include file="../../common/footer.jsp"%>