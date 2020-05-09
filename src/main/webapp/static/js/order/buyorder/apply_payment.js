$(function(){
	var totalAmountArr = new Array();
	var totalAmountStr = 0; 
	$("input[name='totalAmount']").each(function(i){
		if($(this).parent().parent().find("input[name='checkedOne']").is(':checked')){
			totalAmountArr[i] = $(this).val();
			totalAmountStr += $(this).val()*100;
		}
	});
	$("#amount").val((totalAmountStr/100).toFixed(2));
	
	$("input[name='checkedAll']").click(function(){
		checkLogin();
		if($(this).is(':checked')){
			$("input[name='checkedOne']").each(function(){
				$(this).prop("checked",true);
			});
		}else{
			$("input[name='checkedOne']").each(function(){
				$(this).prop("checked",false);
			});
		}
		getAmount();
	});
	
	$("input[name='checkedOne']").click(function(){
		checkLogin();
		var isCheckAll = 1;
		$("input[name='checkedOne']").each(function(){
			if(!$(this).is(':checked')){
				isCheckAll = 0;
			}
		});
		if (isCheckAll == 1) {
			$("input[name='checkedAll']").prop("checked",true);
		} else {
			$("input[name='checkedAll']").prop("checked",false);
		}
		getAmount();
	});
})

function changeNum(e, buyorderGoodsId) {
	checkLogin();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var price = $("#price_" + buyorderGoodsId).html();
	var allnum = $("#allnum_" + buyorderGoodsId).html();
	var applyPaymentAmount = $("#applyPaymentAmount_"+buyorderGoodsId).html();
	
	if($(e).val().length>0 && !reg.test($(e).val())){
		var num = 0.00;
	} else {
		var num = ($(e).val()*1).toFixed(2);
	}
	var thisValue = num*price > ((price * allnum) - applyPaymentAmount) ? ((price * allnum) - applyPaymentAmount) : num*price;
	$("#totalAmount_" + buyorderGoodsId).val(thisValue.toFixed(2));
	$("#num_" + buyorderGoodsId).val(isNaN(thisValue/price) ? '0.00' : (thisValue/price).toFixed(2));
	getAmount();
}

function changePrice(e, buyorderGoodsId) {
	checkLogin();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var price = $("#price_" + buyorderGoodsId).html();
	var allnum = $("#allnum_" + buyorderGoodsId).html();
	var applyPaymentAmount = $("#applyPaymentAmount_"+buyorderGoodsId).html();
	if($(e).val().length>0 && !reg.test($(e).val())){
		var amount = 0.00;
	} else {
		var amount = ($(e).val()*1).toFixed(2);
	}
	var thisValue = amount > ((price * allnum) - applyPaymentAmount) ? ((price * allnum) - applyPaymentAmount) : amount;
	$(e).val(thisValue);
	$("#num_" + buyorderGoodsId).val(isNaN(thisValue/price) ? '0.00' : (thisValue/price).toFixed(2));
	getAmount();
}

function getAmount() {
	checkLogin();
	var amount = 0;
	$("input[name='checkedOne']").each(function(){
		if($(this).is(':checked')){
			amount += $("#totalAmount_" + $(this).val()).val()*100;
		}
	});
	$("#amount").val((amount/100).toFixed(2));
}

function isUseBalanceRadio(e) {
	checkLogin();
	if ($(e).val() == 1) {
		$("#traderMode").val(528);
		$(".notUseBalance").hide();
	} else {
		$("#traderMode").val(521);
		$(".notUseBalance").show();
	}
}


function checkForm() {
	checkLogin();
	$("#traderNameError").html("");
	$("#bankStrError").html("");
	var traderMode = $("#traderMode").val();
	var traderName = $("#traderName").val();
	var supplyAmount = $("#supplyAmount").val();
	var occupyAmount = $("#occupyAmount").val();
	if (traderMode == 521) {
		if (traderName == '' || traderName==undefined) {
			$("#traderNameError").html("交易名称不能为空");
			return false;
		}
		
		var bankValue = $("input[name='bank']:checked").val();
		if($("#bank_str_" + bankValue).html() == undefined){
			$("#bankStrError").html("开户银行及银行帐号不能为空");
			return false;
		}
		var bankValueArr = $("#bank_str_" + bankValue).html().split(" / ");
		if (bankValueArr[0] == '' || bankValueArr[0]==undefined) {
			$("#bankStrError").html("开户银行不能为空");
			return false;
		}		
		if (bankValueArr[1] == '' || bankValueArr[1]==undefined) {
			$("#bankStrError").html("银行帐号不能为空");
			return false;
		}
		$("#bank").val(bankValueArr[0]);
		$("#bankAccount").val(bankValueArr[1]);
		$("#bankCode").val(bankValueArr[2]);
	}
	
	var flag = true;
	
	//产品详情ID
	var buyorderGoodsIdArr = new Array();
	$("input[name='buyorderGoodsId']").each(function(i){
		if($(this).parent().find("input[name='checkedOne']").is(':checked')){
			buyorderGoodsIdArr[i] = $(this).val();
		}
		/*if(!vailNum(this)){
			flag = false;
			return flag;
		}*/
	});
	if (buyorderGoodsIdArr.length == 0) {
		layer.alert("请先选择产品");
		return false;
	}
	if(flag==false){
		return false;
	}
	
	//价格
	var priceArr = new Array();
	$("input[name='price']").each(function(i){
		if($(this).parent().find("input[name='checkedOne']").is(':checked')){
			priceArr[i] = $(this).val();
		}
		/*if(!vailNum(this)){
			flag = false;
			return flag;
		}*/
	});
	if(flag==false){
		return false;
	}
	
	//申请数量
	var numArr = new Array();
	$("input[name='num']").each(function(i){
		if($(this).parent().parent().find("input[name='checkedOne']").is(':checked')){
			numArr[i] = $(this).val();
		}
		/*if(!vailNum(this)){
			flag = false;
			return flag;
		}*/
	});
	if(flag==false){
		return false;
	}
	
	//申请总额
	var totalAmountArr = new Array();
	var totalAmountStr = 0; 
	$("input[name='totalAmount']").each(function(i){
		if($(this).parent().parent().find("input[name='checkedOne']").is(':checked')){
			if ($(this).val() == '' || $(this).val() == '0.00' || $(this).val() == '0') {
				layer.alert("选择产品中申请总额不能为空");
				return false;
			}
			totalAmountArr[i] = $(this).val();
			totalAmountStr += $(this).val()*10000;
		}
		/*if(!vailNum(this)){
			flag = false;
			return flag;
		}*/
	});
	if(flag==false){
		return false;
	}
	if(totalAmountStr * 1 > 0){
		totalAmountStr = totalAmountStr/10000
	}
	if (traderMode == 528 && totalAmountStr > (supplyAmount - occupyAmount)) {
		layer.alert("可申请余额不足");
		return false;
	} 
	
	var formToken = $("input[name='formToken']").val();
	$.ajax({
		async : false,
		url : './saveApplyPayment.do',
		data : {
			"amount":$("#amount").val(),
			"comments":$("#comments").val(),
			"traderSubject":$("input[name='traderSubject']:checked").val(),
			"isUseBalance":$("input[name='isUseBalance']:checked").val(),
			"traderMode":$("#traderMode").val(),
			"bank":$("#bank").val(),
			"bankCode":$("#bankCode").val(),
			"bankAccount":$("#bankAccount").val(),
			"traderName":$("#traderName").val(),
			"relatedId":$("#relatedId").val(),
			"traderSupplierId":$("#traderSupplierId").val(),
			"priceArr":JSON.stringify(priceArr),
			"numArr":JSON.stringify(numArr),
			"totalAmountArr":JSON.stringify(totalAmountArr),
			"buyorderGoodsIdArr":JSON.stringify(buyorderGoodsIdArr),
			"formToken":formToken
			},
		type : "POST",
		dataType : "json",
		success : function(data) {
			layer.alert(data.message, {	icon : (data.code == 0 ? 1 : 2)},
				function(index) {
					if(data.code == 0){
						//location.reload();
						pagesContrlpages(true,false,true);
					}else{
						layer.close(index);
					}
				}
			);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}
