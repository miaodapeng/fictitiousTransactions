<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后-退货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/order/saleorder/saveEditAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" checked="true" name="type" value="539" readonly="readonly">
                                    <label>退货</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="540" readonly="readonly">
                                    <label>换货</label>
                                </a>
                            </li>

                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="545" readonly="readonly">
                                    <label>其他</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="" name="orderId" value="${afterSales.orderId}" />
            <input type="hidden" id="" name="saleorderId" value="${afterSales.orderId}" />
            <input type="hidden" id="" name="orderNo" value="${afterSales.orderNo}" />
            <input type="hidden" id="" name="thGoodsId" value="253620" />
            <input type="hidden" id="shtype" value="th" />
            <input type="hidden" name="sku2" value="${afterSales.sku}" />
            <input type="hidden" name="afterSalesId" value="${afterSales.afterSalesId}" />
            <input type="hidden" name="afterSalesDetailId" value="${afterSales.afterSalesDetailId}" />
            <input type="hidden" name="subjectType" value="${afterSales.subjectType}" />
            <input type="hidden" name="type" value="${afterSales.type}" />
            <input type="hidden" name="refundAmount" id="refundAmount">
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>售后原因</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul>
                            <c:forEach items="${sysList}" var="sys" >
                                <c:if test="${sys.relatedField eq 539 }">
                                    <li>
                                        <input type="radio" name="reason" value="${sys.sysOptionDefinitionId}"
                                               <c:if test="${sys.sysOptionDefinitionId eq afterSales.reason }">checked="checked"</c:if>>
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
                            <th >产品名称</th>
                            <th >品牌</th>
                            <th >型号</th>
                            <th class="wid12">物料编码</th>
                            <th >销售价</th>
                            <th>数量</th>
                            <th class="wid6">单位</th>
                            <th class="wid12">售后数量/上限</th>
                            <th class="wid8">退货方式</th>
                            <th class="wid10">已发货数量</th>
                            <th class="wid10">已收货数量</th>
                            <th class="wid10">已开票数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${saleorder.sgvList}" var="sgv">
                            <c:set var="contains" value="false" />
                            <c:set var="num" value="0" />
                            <c:forEach items="${afterSales.afterSalesGoodsList}" var="asg">
                                <c:if test="${sgv.saleorderGoodsId eq asg.orderDetailId}">
                                    <c:set var="contains" value="true" />
                                    <c:set var="num" value="${asg.num}" />
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${contains == true }">
                                    <tr>
                                        <td>
                                            <c:if test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}"></c:if>
                                            <input type="checkbox" name="oneSelect" alt="${sgv.saleorderGoodsId}" checked="checked">

                                            <input type="hidden" name="afterSaleNums" >
                                            <input type="hidden" name="goodsIds" value="${sgv.goodsId}">
                                            <input type="hidden" name="prices" value="${sgv.price}">
                                            <input type="hidden" name="sku" >
                                            <input type="hidden" name="skus" value="${sgv.sku}" />
                                            <input type="hidden" name="deliveryNums" id="deliveryNums_${sgv.saleorderGoodsId}" value="${sgv.deliveryNum}">
                                        </td>
                                        <td class="text-left">
		                                    <span class="font-blue cursor-pointer addtitle"
                                                  tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${sgv.goodsId}","title":"产品信息"}'>${sgv.goodsName}</span>
                                            <div>${sgv.sku}</div>
                                        </td>
                                        <td>${sgv.model}</td>
                                        <td>${sgv.brandName}</td>
                                        <td>${sgv.materialCode}</td>
                                        <td>${sgv.price}</td>
                                        <td>${sgv.num}</td>
                                        <td>${sgv.unitName}</td>
                                        <td>
                                            <input type="text" style="width:50px;" alt1="${sgv.saleorderGoodsId}" value="${num}">/
                                            <span alt1="${sgv.saleorderGoodsId}">${sgv.afterSaleUpLimitNum+num}</span>
                                        </td>
                                        <td>
                                            <select id="${sgv.saleorderGoodsId}">
                                                <option value="0" <c:if test ="${sgv.deliveryDirect eq 0}">selected="selected"</c:if>>普发</option>
                                                <option value="1" <c:if test ="${sgv.deliveryDirect eq 1}">selected="selected"</c:if>>直发</option>

                                            </select>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">
                                                    ${sgv.deliveryNum}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${saleorder.deliveryStatus eq 0}">0</c:if>
                                                    <c:if test="${saleorder.deliveryStatus ne 0}">${sgv.num}</c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">
                                                    ${sgv.receiveNum}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${saleorder.arrivalStatus eq 0}">0</c:if>
                                                    <c:if test="${saleorder.arrivalStatus ne 0}">${sgv.num}</c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatNumber type="number" value="${sgv.invoiceNum}" pattern="0.00" maxFractionDigits="2" /></td>

                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>
                                            <c:if test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">	</c:if>
                                            <input type="checkbox" name="oneSelect" alt="${sgv.saleorderGoodsId}" >

                                            <input type="hidden" name="afterSaleNums" >
                                            <input type="hidden" name="goodsIds" value="${sgv.goodsId}">
                                            <input type="hidden" name="prices" value="${sgv.price}">
                                            <input type="hidden" name="sku" >
                                            <input type="hidden" name="skus" value="${sgv.sku}" />
                                            <input type="hidden" name="deliveryNums" id="deliveryNums_${sgv.saleorderGoodsId}" value="${sgv.deliveryNum}">
                                        </td>
                                        <td class="text-left">
		                                    <span class="font-blue cursor-pointer addtitle"
                                                  tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
			                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${sgv.goodsId}","title":"产品信息"}'>${sgv.goodsName}</span>
                                            <div>${sgv.sku}</div>
                                        </td>
                                        <td>${sgv.model}</td>
                                        <td>${sgv.brandName}</td>
                                        <td>${sgv.materialCode}</td>
                                        <td>${sgv.price}</td>
                                        <td>${sgv.num}</td>
                                        <td>${sgv.unitName}</td>
                                        <td>
                                            <input type="text" style="width:50px;" alt1="${sgv.saleorderGoodsId}" value="">/
                                            <span alt1="${sgv.saleorderGoodsId}">${sgv.afterSaleUpLimitNum}</span>
                                        </td>
                                        <td>
                                            <select id="${sgv.saleorderGoodsId}">
                                                <option value="0" <c:if test ="${sgv.deliveryDirect eq 0}">selected="selected"</c:if>>普发</option>
                                                <option value="1" <c:if test ="${sgv.deliveryDirect eq 1}">selected="selected"</c:if>>直发</option>
                                            </select>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">
                                                    ${sgv.deliveryNum}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${saleorder.deliveryStatus eq 0}">0</c:if>
                                                    <c:if test="${saleorder.deliveryStatus ne 0}">${sgv.num}</c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sgv.goodsId ne 140633 && sgv.goodsId ne 253620 && sgv.goodsId ne 251462 && sgv.goodsId ne 127063 && sgv.goodsId ne 251526 && sgv.goodsId ne 256675}">
                                                    ${sgv.receiveNum}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${saleorder.arrivalStatus eq 0}">0</c:if>
                                                    <c:if test="${saleorder.arrivalStatus ne 0}">${sgv.num}</c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatNumber type="number" value="${sgv.invoiceNum}" pattern="0.00" maxFractionDigits="2" /></td>

                                    </tr>
                                </c:otherwise>
                            </c:choose>
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
                        <textarea name="comments" id="comments" placeholder="请详述客户需求及退货要求以便售后同事联系" rows="5" class="wid90">${afterSales.comments}</textarea>
                    </div>
                    <div id="commentsError"></div>
                </div>
            </li>

            <c:if test="${afterSales.type eq 909}">
                <li>
                    <div class="form-tips">
                        <lable>客户</lable>
                    </div>
                    <div class="f_left">
                        XXXXXXX
                    </div>
                </li>
            </c:if>

            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>联系人</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <select class="input-largest" name="traderContactId" id="traderContactId">
                            <c:if test="${empty afterSales.traderContactId}">
                                <c:forEach items="${saleorder.tcList}" var="tc">
                                    <option value="${tc.traderContactId}"
                                            <c:if test="${saleorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty afterSales.traderContactId}">
                                <c:forEach items="${saleorder.tcList}" var="tc">
                                    <option value="${tc.traderContactId}"
                                            <c:if test="${afterSales.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                    <div id="traderContactIdError"></div>
                </div>
            </li>
            <!--
             <li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>款项退还</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks">
                         <ul>
                             <li>

                                 <input type="radio" name="refund" value="1" <c:if test="${afterSales.refund eq 1}">checked="checked"</c:if>>
                                 <label><span>退到客户余额</span><span class="font-grey9">(可用于支付该客户其他订单)</span></label>
                             </li>
                             <li>
                                 <input type="radio" name="refund" value="2" <c:if test="${afterSales.refund eq 2}">checked="checked"</c:if>>
                                 <label>
                                 	<span>退给客户</span><span class="font-grey9">(由财务退还到客户账户)</span>
                                 </label>
                             </li>
                         </ul>
                     </div>
                     <div id="refundError" class="font-red " style="display: none">款项退还不允许为空</div>
                 </div>
             </li>
             <li <c:if test="${afterSales.refund ne 2}">class="none"</c:if> id="li1">
               <div class="form-tips">
                   <span>*</span>
                   <lable>交易主体</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <ul>
                            <li>
                                <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 1}">checked="checked"</c:if> value="1">
                                <label>对公</label>
                            </li>
                            <li>
                               <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 2}">checked="checked"</c:if> value="2">
                               <label>对私</label>
                            </li>
                       </ul>
                   </div>
                   <div id="traderSubjectError" ></div>
               </div>
           </li>
			<li <c:if test="${afterSales.refund ne 2}">class="none"</c:if> id="li2">
               <div class="form-tips">
                   <span>*</span>
                   <lable>收款名称</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                   		<input type="text" name="payee" id="payee"  class="input-largest" value="${afterSales.payee}"/>
                   		<input type="hidden"  id="payee1"  value="${afterSales.payee}"/>
                   </div>
                   <div id="payeeError"></div>
               </div>
            </li>
            <li <c:if test="${afterSales.refund ne 2}">class="none"</c:if> id="li3">
               <div class="form-tips">
                   <span>*</span>
                   <lable>开户银行</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bank" id="bank"  class="input-largest" value="${afterSales.bank}"/>
                       <input type="hidden"  id="bank1"  value="${afterSales.bank}"/>
                   </div>
                   <div id="bankError"></div>
               </div>
            </li>
            <li <c:if test="${afterSales.refund ne 2}">class="none"</c:if> id="li4">
               <div class="form-tips">
                   <span>*</span>
                   <lable>银行账号 </lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bankAccount" id="bankAccount"  class="input-largest" value="${afterSales.bankAccount}"/>
                       <input type="hidden" id="bankAccount1" value="${afterSales.bankAccount}"/>
                   </div>
                   <div id="bankAccountError"></div>
               </div>
            </li>
            <li <c:if test="${afterSales.refund ne 2}">class="none"</c:if> id="li5">
               <div class="form-tips">
                   <lable>开户行支付联行号</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bankCode" id="bankCode" value="${afterSales.bankCode}" class="input-largest"/>
                       <input type="hidden" id="bankCode1" value="${afterSales.bankCode}" />
                   </div>
                   <div id="bankCodeError"></div>
               </div>
            </li>
             -->
            <li>
                <div class="form-tips">
                    <lable>上传附件</lable>
                </div>
                <input type="hidden" id="domain" name="domain" value="${domain}">
                <div class="f_left ">
                    <c:if test="${empty afterSales.attachmentList }">
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
                    </c:if>
                    <c:if test="${not empty afterSales.attachmentList}">
                        <c:forEach items="${afterSales.attachmentList}" var="att" varStatus="status">
                            <div class="form-blanks <c:if test='${status.count ne 1}'>mt10</c:if>">
                                <div class="pos_rel f_left">
                                    <input type="file" class="uploadErp" id='file_${status.count}' name="lwfile" onchange="uploadFile(this,${status.count});">
                                    <input type="text" class="input-largest" id="name_${status.count}" readonly="readonly"
                                           placeholder="请上传附件" name="fileName" onclick="file_${status.count}.click();" value="${att.name}">
                                    <input type="hidden" id="uri_${status.count}" name="fileUri" value="${att.uri}">
                                </div>
                                <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_${status.count}').click();">浏览</label>
                                <!-- 上传成功出现 -->
                                <div class="f_left">
                                    <i class="iconsuccesss mt3" id="img_icon_${status.count}"></i>
                                    <a href="http://${att.domain}${att.uri}" target="_blank" class="font-blue cursor-pointer mt3" id="img_view_${status.count}">查看</a>
                                    <span class="font-red cursor-pointer mt3" onclick="del(${status.count})" id="img_del_${status.count}">删除</span>
                                </div>
                                <div class='clear'></div>
                            </div>
                        </c:forEach>
                    </c:if>
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
