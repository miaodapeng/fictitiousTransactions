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
			if(refundFee ==''){
				warnErrorTips("serviceAmount","serviceAmountError","售后退换货手续费不允许为空");//文本框ID和提示用语
				return false;
			}
			if(!priceReg.test(refundFee)&&refundFee !=0 && refundFee !=0.0 && refundFee !=0.00){
				warnErrorTips("serviceAmount","serviceAmountError","售后退换货手续费输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			var invoiceType = $("#invoiceType").val();
			if(invoiceType ==''){
				warnErrorTips("invoiceTypeAmount","invoiceTypeError","票种不允许为空");//文本框ID和提示用语
				return false;
			}
			var reason=$('input:radio[name="isSendInvoice"]:checked').val();
			if(reason==undefined||reason==""){
				warnErrorTips("isSendInvoice","isSendInvoiceError","是否寄送不允许为空");
				return false;
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
    <div class="form-list  form-tips5">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>售后退换货手续费</lable>
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
                           </select>
                        </div>
                        <div id="invoiceTypeError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>是否寄送</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul class="aftersales">
	                            <li>
                                    <input type="radio" name="isSendInvoice" value="1" <c:if test="${afterSalesDetail.isSendInvoice eq 1}">checked="checked"</c:if>>
                                    <label>是</label>
	                            </li>
	                            <li>
                                    <input type="radio" name="isSendInvoice" value="0" <c:if test="${afterSalesDetail.isSendInvoice eq 0}">checked="checked"</c:if>>
                                    <label>否</label>
	                            </li>
                            </ul>
                        </div>
                        <div id="isSendInvoiceError"></div>
                        <div class="pop-friend-tips mt5">
                        	友情提示：
                     	<br/> 1、售后服务费指收取客户的费用；
                     </div>
                    </div>
                </li>
            </ul>
           <div class="add-tijiao tcenter">
               	<input type="hidden" name="afterSalesDetailId" value="${afterSalesDetail.afterSalesDetailId}"/>
                <button type="submit" id="add_submit">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>