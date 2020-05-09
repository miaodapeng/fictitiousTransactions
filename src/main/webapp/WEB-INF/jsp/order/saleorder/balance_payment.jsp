<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="余额支付" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
$(function(){
	$("#AddCapitalBillForm").submit(function(){
		checkLogin();
		clearErroeMes();//清除錯誤提示信息
		var amount = $("#amount").val().trim();
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if (amount.length == 0) {
			warnTips("amount","交易金额不允许为空");//文本框ID和提示用语
			return false;
		} else if(amount.length>0 && !reg.test(amount)){
			warnTips("amount","交易金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(amount)>300000000){
			warnTips("amount","交易金额不允许超过三亿");//文本框ID和提示用语
			return false;
		}
		
		
		var balance_amount = $("#balance_amount").val().trim();
		if (amount*100 > balance_amount*100) {
			warnTips("amount","交易金额不允许超过可用余额");//文本框ID和提示用语
			return false;
		}
		
		var payer = $("#payer").val();
		if(payer!="" && payer.length>128){
			warnTips("payer","交易名称长度应该在0-128个字符之间");
			return false;
		}
		
		var comments = $("#comments").val();
		if(comments!="" && comments.length>512){
			warnTips("comments","交易备注长度应该在0-512个字符之间");
			return false;
		}
		
		$.ajax({
			url:page_url+'/finance/capitalbill/saveAddCapitalBill.do',
			data:$('#AddCapitalBillForm').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				if (data.code == 0) {
					window.parent.location.reload();
				} else {
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
<div class="form-list">
    <form method="post" id="AddCapitalBillForm" action="">
        <ul>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
	                    	<li>
	                    		<input type="radio" name="traderMode" value="528" checked="checked">
	                    		<input type="hidden" name="balance_amount" id="balance_amount" value="${traderCustomerInfo.amount}">
                                <label>余额（可用余额<fmt:formatNumber type="number" value="${traderCustomerInfo.amount}" pattern="0.00" maxFractionDigits="2" />）</label>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易主体</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <li>
                            	<input type="radio" name="traderSubject" value="1" checked="checked">
                                <label>对公</label>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	<input type="text" class="input-middle" name="amount" id="amount" value="" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易名称</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	<input type="text" class="input-largest" name="payer" id="payer" value="${saleorder.traderName}" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>交易备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="comments" id="comments" value="" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>收款备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	${saleorder.paymentComments}
                    </div>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="capitalBillDetail.relatedId" value="${saleorder.saleorderId}"/>
        	<input type="hidden" name="capitalBillDetail.orderNo" value="${saleorder.saleorderNo}"/>
        	<input type="hidden" name="capitalBillDetail.orderType" value="1"/>
        	<input type="hidden" name="capitalBillDetail.traderId" value="${saleorder.traderId}"/>
        	<input type="hidden" name="capitalBillDetail.traderType" value="1"/>
        	<input type="hidden" name="capitalBillDetail.bussinessType" value="526"/>
        	<input type="hidden" name="capitalBillDetail.userId" value="${saleorder.userId}"/>
        	<input type="hidden" name="traderType" value="4"/>
        	<input type="hidden" name="formToken" value="${formToken}"/>
            <button type="submit">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp"%>