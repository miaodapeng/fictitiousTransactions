$(document).ready(function(){

	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		
		if($("#traderName").val()==''){
			warnErrorTips("traderName","traderNameError","交易名称不允许为空");//文本框ID和提示用语
			return false;
		}
		var count=$('input:radio[name="isUseBalance"]:checked').val();
		if(count == 1){
			$("#traderMode").val(528);
		}else{
			$("#traderMode").val(521);
		}
		
		if(count == 2){
			var tf = $('input:radio[name="traderFinance"]:checked').val();
			if(tf == ''){
				$("#bankStrError").html("银行帐号不允许为空");
				return false;
			}
//			if($("#bank").val()==''){
//				warnErrorTips("bank","bankError","开户银行不允许为空");//文本框ID和提示用语
//				return false;
//			}
//			
//			if($("#bank").val().length > 256){
//				warnErrorTips("bank","bankError","开户银行不允许超过256个字符");//文本框ID和提示用语
//				return false;
//			}
//			
//			if($("#bankCode").val()!='' && $("#bankCode").val().length > 32){
//				warnErrorTips("bankCode","bankCodeError","开户行支付联行号不允许超过32个字符");//文本框ID和提示用语
//				return false;
//			}
//			var reg = /^\d{10,32}$/;
//			if($("#bankAccount").val()==''){
//				warnErrorTips("bankAccount","bankAccountError","银行帐号不允许为空");//文本框ID和提示用语
//				return false;
//			}
//			if(!reg.test($("#bankAccount").val())){
//				warnErrorTips("bankAccount","bankAccountError","银行帐号输入错误，10位到32位之间");//文本框ID和提示用语
//				return false;
//			}
		}
		var num = $("#num").val();
		var numReg = /^(0\.(0[1-9]|[1-9]{1,2}|[1-9]0)$)|^1$/;
		if(num == ''){
			layer.alert("申请数量不允许为空");
			return false;
		}else if(!numReg.test(num)){
			layer.alert("申请数量输入错误！仅允许使用数字，最多精确到小数后两位");
			return false;
		}else if(Number(num)+Number($("#payApplySum").val())>Number($("#sum").val())){
			layer.alert("申请数量与已申请数量和不允许大于"+$("#sum").val());
			return false;
		}
		var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		var totalAmount = $("#amount").val();
		if(totalAmount == ''){
			layer.alert("申请总额不允许为空");
			return false;
		}else if(!priceReg.test(totalAmount)){
			layer.alert("申请总额输入错误！仅允许使用数字，最多精确到小数后两位");
			return false;
		}else if(Number(totalAmount)>Number($("#traderAmount").val()) && count == 1){
			warnErrorTips("amount","amountError","申请付款金额不允许超出账户余额");
			return false;
		}else if(Number(totalAmount)+Number($("#payApplyTotalAmount").val())>Number($("#price").val())){
			layer.alert("申请总额与已申请总额和不允许大于"+$("#price").val());
			return false;
		}
		$("#totalAmount").val((Number(num)*Number($("#price").val())).toFixed(2));
		$("#amount").val(totalAmount);
		
	});

	$("#num").change(function(){
		var num = $("#num").val();
		var numReg = /^(0\.(0[1-9]|[1-9]{1,2}|[1-9]0)$)|^1$/;
		if(num == ''){
			layer.alert("申请数量不允许为空");
			return false;
		}else if(!numReg.test(num)){
			layer.alert("申请数量输入错误！仅允许使用数字，最多精确到小数后两位");
			return false;
		}else if(Number(num)+Number($("#payApplySum").val())>Number($("#sum").val())){
			layer.alert("申请数量与已申请数量和不允许大于"+$("#sum").val());
			return false;
		}
		$("#totalAmount").val((Number(num)*Number($("#price").val())).toFixed(2));
		var totalAmount = $("#totalAmount").val();
		$("#amount").val(totalAmount);
	})
	
	$('input:radio[name="isUseBalance"]').click(function(){
		var count=$('input:radio[name="isUseBalance"]:checked').val();
		if(count ==1){
			$("#li1").removeClass("none");
			$("#li2").addClass("none");
			$("#li3").addClass("none");
			$("#li4").addClass("none");
			$("#li5").addClass("none");
		}else{
			$("#li1").addClass("none");
			$("#li2").removeClass("none");
			$("#li3").removeClass("none");
			$("#li4").removeClass("none");
			$("#li5").removeClass("none");
		}
		
	})
	
	
});


