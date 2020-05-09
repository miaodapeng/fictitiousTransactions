<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑安调信息" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#myform").submit(function(){
			checkLogin();
			var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			var refundFee = $("#serviceAmount").val();
			var msg ='';
			if($("#flag").val()=='th' || $("#flag").val()=='bth'){
				msg = "售后退换货手续费";
			}else if($("#flag").val()=='at'){
				msg = "安调费";
			}else if($("#flag").val()=='wx'){
				msg = "维修费";
			}
			if(refundFee ==''){
				warnErrorTips("serviceAmount","serviceAmountError",msg+"不允许为空");//文本框ID和提示用语
				return false;
			}
			if(!priceReg.test(refundFee) && refundFee !=0 && refundFee !=0.0 && refundFee !=0.00){
				warnErrorTips("serviceAmount","serviceAmountError",msg+"输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			if(Number(refundFee)>300000000){
				warnErrorTips("serviceAmount","serviceAmountError",msg+"不允许超过三亿！");
				return false;
			}
			if($("#flag").val()!='bth' && Number(refundFee) > 0){
				if($("#invoiceTraderContactIds").val() == ''){
					warnErrorTips("invoiceTraderContactIds","invoiceTraderContactIdError","收票联系人不允许为空");//文本框ID和提示用语
					return false;
				}
				if($("#invoiceTraderAddressIds").val() == ''){
					warnErrorTips("invoiceTraderAddressIds","invoiceTraderAddressIdError","收票地址不允许为空");//文本框ID和提示用语
					return false;
				}
				var traderAddressId = $("#invoiceTraderAddressIds").val().split("|")[0];
				var areaIds = $("#"+traderAddressId).val();
				if(areaIds != '' && areaIds.split(",").length != 3){
					warnErrorTips("invoiceTraderAddressIds","invoiceTraderAddressIdError","收票地址请补充完省市区");//文本框ID和提示用语
					return false;
				}
				if($("#invoiceComments").val() != '' && $("#invoiceComments").val().length > 256){
					warnErrorTips("invoiceComments","invoiceCommentsError","开票备注不允许超过256个字符");//文本框ID和提示用语
					return false;
				}
			}
			$.ajax({
				url:page_url+'/aftersales/order/saveEditInstallstion.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						window.parent.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
		})
	})


</script>
    <div class="form-list  form-tips8">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>
                        	<c:if test="${flag eq 'th' || flag eq 'bth'}">售后退换货手续费</c:if>
                        	<c:if test="${flag eq 'at' }">安调费</c:if>
                        	<c:if test="${flag eq 'wx' }">维修费</c:if>
                        </lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="input-small" name="serviceAmount" id="serviceAmount" value="${afterSalesDetail.serviceAmount}"/>
                        </div>
                      	<div id="serviceAmountError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>票种</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                           <select class="input-small" name="invoiceType" id="invoiceType">
                           		<option value="429" <c:if test="${afterSalesDetail.invoiceType eq 429}">selected="selected"</c:if>>17%增值税专用发票</option>
                           		<option value="430" <c:if test="${afterSalesDetail.invoiceType eq 430}">selected="selected"</c:if>>17%增值税普通发票</option>
                           		<option value="682" <c:if test="${afterSalesDetail.invoiceType eq 682}">selected="selected"</c:if>>16%增值税专用发票</option>
                           		<option value="681" <c:if test="${afterSalesDetail.invoiceType eq 681}">selected="selected"</c:if>>16%增值税普通发票</option>
                           		<option value="972" <c:if test="${afterSalesDetail.invoiceType eq 0 || afterSalesDetail.invoiceType eq 972}">selected="selected"</c:if>>13%增值税专用发票</option>
                           		<option value="971" <c:if test="${afterSalesDetail.invoiceType eq 971}">selected="selected"</c:if>>13%增值税普通发票</option>
                           		<option value="684" <c:if test="${afterSalesDetail.invoiceType eq 684}">selected="selected"</c:if>>6%增值税专用发票</option>
                           		<option value="683" <c:if test="${afterSalesDetail.invoiceType eq 683}">selected="selected"</c:if>>6%增值税普通发票</option>
                           		<option value="686" <c:if test="${afterSalesDetail.invoiceType eq 686}">selected="selected"</c:if>>3%增值税专用发票</option>
                           		<option value="685" <c:if test="${afterSalesDetail.invoiceType eq 685}">selected="selected"</c:if>>3%增值税普通发票</option>
                           		<option value="688" <c:if test="${afterSalesDetail.invoiceType eq 688}">selected="selected"</c:if>>0%增值税专用发票</option>
                           		<option value="687" <c:if test="${afterSalesDetail.invoiceType eq 687}">selected="selected"</c:if>>0%增值税普通发票</option>
                           </select>
                           <input type="checkbox" name="isSendInvoice" value="0" <c:if test="${afterSalesDetail.isSendInvoice eq 0}">checked="checked"</c:if>>不寄送
                        </div>
                        <div id="invoiceTypeError"></div>
                    </div>
                </li>
                <c:if test="${flag ne 'bth'}">
                	<li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>收票客户</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
	                        <c:if test="${afterSalesDetail.invoiceTraderId eq 0}">
	                        	${afterSalesDetail.traderName}
	                           <input type="hidden"  name="invoiceTraderName" id="invoiceTraderName" value="${afterSalesDetail.traderName}"/>
	                           <input type="hidden"  name="invoiceTraderId" id="invoiceTraderId" value="${afterSalesDetail.traderId}"/>
	                        </c:if>
	                        <c:if test="${afterSalesDetail.invoiceTraderId ne 0}">
	                        	${afterSalesDetail.invoiceTraderName}
	                           <input type="hidden"  name="invoiceTraderName" id="invoiceTraderName" value="${afterSalesDetail.invoiceTraderName}"/>
	                           <input type="hidden"  name="invoiceTraderId" id="invoiceTraderId" value="${afterSalesDetail.invoiceTraderId}"/>
	                        </c:if>
                        </div>
                        <div id=""></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                    	<span>*</span>
                        <lable>收票联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-largest" name="invoiceTraderContactIds" id="invoiceTraderContactIds">
                            	<option value="">请选择</option>	
                            	<c:forEach items="${afterSalesDetail.tcList }" var="tc">
	                            	
                            		<c:if test="${afterSalesDetail.invoiceTraderContactId ne 0 && afterSalesDetail.invoiceTraderContactId eq tc.traderContactId}">
                            			<option value="${tc.traderContactId}|${afterSalesDetail.invoiceTraderContactName}|${afterSalesDetail.invoiceTraderContactTelephone}|${afterSalesDetail.invoiceTraderContactMobile}" 
                            					selected="selected">${afterSalesDetail.invoiceTraderContactName}/${afterSalesDetail.invoiceTraderContactTelephone}/${afterSalesDetail.invoiceTraderContactMobile}</option>
                            		</c:if>
                            		<c:if test="${afterSalesDetail.invoiceTraderContactId eq 0 || afterSalesDetail.invoiceTraderContactId ne tc.traderContactId}">
                            			<option value="${tc.traderContactId}|${tc.name}|${tc.telephone}|${tc.mobile}" >${tc.name}/${tc.telephone}/${tc.mobile}</option>
                            		</c:if>
                            	</c:forEach>
                           </select>
                        </div>
                        <div id="invoiceTraderContactIdError"></div>
                        
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                    	<span>*</span>
                        <lable>收票地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-largest" name="invoiceTraderAddressIds" id="invoiceTraderAddressIds">
                            	<option value="">请选择</option>
                            	<c:forEach items="${afterSalesDetail.tavList }" var="ta">
                            		<c:if test="${afterSalesDetail.invoiceTraderAddressId ne 0 && afterSalesDetail.invoiceTraderAddressId eq ta.traderAddress.traderAddressId}">
                            			<option value="${ta.traderAddress.traderAddressId}|${afterSalesDetail.invoiceTraderArea}|${afterSalesDetail.invoiceTraderAddress}" 
                            					selected="selected">${afterSalesDetail.invoiceTraderArea}&nbsp;&nbsp;${afterSalesDetail.invoiceTraderAddress}</option>
                            		</c:if>
                            		<c:if test="${afterSalesDetail.invoiceTraderAddressId eq 0 || afterSalesDetail.invoiceTraderAddressId ne ta.traderAddress.traderAddressId}">
                            			<option value="${ta.traderAddress.traderAddressId}|${ta.area}|${ta.traderAddress.address}" >${ta.area}&nbsp;&nbsp;${ta.traderAddress.address}</option>
                            		</c:if>
                            	</c:forEach>
                           </select>
                           <c:forEach items="${afterSalesDetail.tavList}" var="tav">
	                            <input type="hidden" id="${tav.traderAddress.traderAddressId}" value="${tav.traderAddress.areaIds}"/>
	                         </c:forEach>
                        </div>
                        <div id="invoiceTraderAddressIdError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>开票备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input class="input-largest" name="invoiceComments" id="invoiceComments" value="${afterSalesDetail.invoiceComments }" placeholder="对内使用，适用于向财务部同事告知开票要求">
                        </div>
                        <div id="invoiceCommentsError"></div>
                        <div class="pop-friend-tips mt5">
                        	友情提示：
                     	<br/> 1、售后服务费指收取客户的费用；
                     	</div>
                    </div>
                </li>
                </c:if>
                
            </ul>
           <div class="add-tijiao tcenter">
               	<input type="hidden" name="afterSalesDetailId" value="${afterSalesDetail.afterSalesDetailId}"/>
               	<input type="hidden" name="afterSalesId" value="${afterSalesDetail.afterSalesId}"/>
               	<input type="hidden" name="refundAmount" value="${afterSalesDetail.refundAmount}"/>
               	<input type="hidden" name="realRefundAmount" value="${afterSalesDetail.realRefundAmount}"/>
               	<input type="hidden" name="flag" id="flag" value="${flag}"/>
                <button type="submit" id="add_submit">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>