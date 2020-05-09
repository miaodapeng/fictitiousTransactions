$(document).ready(function(){
	
	$("#traderName").empty();
	var opt = '';
	if($("#name_1").val()){
		opt += '<option value="'+$("#name_1").val()+'">'+$("#name_1").val()+'</option>';
	}
	if($("#company_1").val()){
		opt += '<option value="'+$("#company_1").val()+'">'+$("#company_1").val()+'</option>';
	}
	$("#traderName").append(opt);
	$("#bank").val($("#bank_1").val());
	$("#bankCode").val($("#bankCode_1").val());
	$("#bankAccount").val($("#bankAccount_1").val());
	$("#engineerId").val($("#engineerId_1").val());
	$("#engPrice").html($("#engineerAmount_1").val())
	$("input[name='price']").val($("#engineerAmount_1").val());
	$("input[name='detailgoodsId']").val($("#afterSalesInstallstionId_1").val());
	$("#paySum").html($("#payApplySum_1").val());
	$("#payTotalAmount").html($("#payApplyTotalAmount_1").val());
	$("#payApplySum").val($("#payApplySum_1").val());
	$("#payApplyTotalAmount").val($("#payApplyTotalAmount_1").val());

	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		
		//将选中的工程师的id和售后单号添加name，传到后台
		var selectRadio = $('.form-blanks .selectedRadio').find('li').find('input:radio[name="name"]:checked');
		$(selectRadio).siblings('.engineerId').removeAttr('name').attr('name', 'engineerId');
		$(selectRadio).siblings('.afterSalesInstallstionId').removeAttr('name').attr('name', 'detailgoodsId');
		
		var traderSubject=$('input:radio[name="traderSubject"]:checked').val();
		if(traderSubject=='' || traderSubject==undefined){
			$("#traderSubjectError").removeClass("none");//文本框ID和提示用语
			return false;
		}else{
			$("#traderSubjectError").addClass("none");
		}
		
		if($("#traderName").val()=='' || $("#traderName").val() == undefined || $("#traderName").val() == null){
			warnErrorTips("traderName", "tarderNameError", "交易名称不允许为空");//文本框ID和提示用语
			return false;
		}
		
		if($("#bank").val()==''){
			warnErrorTips("bank","bankError","开户银行不允许为空");//文本框ID和提示用语
			return false;
		}
		if($("#bank").val().length > 256){
			warnErrorTips("bank","bankError","开户银行不允许超过256个字符");//文本框ID和提示用语
			return false;
		}
		if($("#bankCode").val()!='' && $("#bankCode").val().length > 32){
			warnErrorTips("bankCode","bankCodeError","开户行支付联行号不允许超过32个字符");//文本框ID和提示用语
			return false;
		}
		var reg = /^\d*$/;
		if($("#bankAccount").val()==''){
			warnErrorTips("bankAccount","bankAccountError","银行帐号不允许为空");//文本框ID和提示用语
			return false;
		}
		if(!reg.test($("#bankAccount").val())){
			warnErrorTips("bankAccount","bankAccountError","银行帐号输入错误，只能输入数字");//文本框ID和提示用语
			return false;
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
		var totalAmount = $("#totalAmount").val();
		if(totalAmount == ''){
			layer.alert("申请总额不允许为空");
			return false;
		}else if(!priceReg.test(totalAmount)){
			layer.alert("申请总额输入错误！仅允许使用数字，最多精确到小数后两位");
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
	
	$('input:radio[name="name"]').click(function(){
		var count=$('input:radio[name="name"]:checked').val();
		$("#traderName").empty();
		var opt = '<option value="'+$("#name_"+count).val()+'">'+$("#name_"+count).val()+'</option>'+
					'<option value="'+$("#company_"+count).val()+'">'+$("#company_"+count).val()+'</option>'
		$("#traderName").append(opt);
		$("#bank").val($('#bank_'+count).val());
		$("#bankCode").val($('#bankCode_'+count).val());
		$("#bankAccount").val($('#bankAccount_'+count).val());
		$("#engineerId").val($("#engineerId_"+count).val());
		$("#engPrice").html($("#engineerAmount_"+count).val());
		$("#price").val($("#engineerAmount_"+count).val());
		if($("#num").val() != '' && $("#price").val() != ''){
			$("#totalAmount").val((Number($("#num").val())*Number($("#price").val())).toFixed(2));
		}
		$("input[name='detailgoodsId']").val($("#afterSalesInstallstionId_"+count).val());
		$("#paySum").html($("#payApplySum_"+count).val());
		$("#payTotalAmount").html($("#payApplyTotalAmount_"+count).val());
		$("#payApplySum").val($("#payApplySum_"+count).val());
		$("#payApplyTotalAmount").val($("#payApplyTotalAmount_"+count).val());
	})
	
	
});


