function vailInvoiceNo(obj){
	clear2ErroeMes();
	var invoiceNo = $(obj).val().trim();
	if(invoiceNo.length == 0){
		warn2Tips("invoiceNo","发票号不允许为空");
		return false;
	}else{
		var reg = /^\d{8}$/;
		if(!reg.test(invoiceNo)){
			warn2Tips("invoiceNo","请输入正确的8位数字发票号");
			return false;
		}
	}
}

function invoiceNumChange(obj,price){
	var num = $(obj).val().trim();
	var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(!reg.test(num)){
		$(obj).val(0.00);
		$(obj).addClass("errorbor");
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
//			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
//	if(num.length == 0 || Number(num) <= 0){
	if(Number(num) < 0){
		$(obj).addClass("errorbor");$(obj).val(0.00);
		layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
//			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
	if(Number(num) > Number($("#max_num").val())){
		$(obj).addClass("errorbor");
		layer.alert("开票数量不允许超出最大开票数量", {
			icon : 2
		}, function(lay) {
			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
	$("#in_price").val((Number(num)*Number(price)).toFixed(2));
	$("#invoiceAmount").html((Number(num)*Number(price)).toFixed(2));
	$("#amount").val((Number(num)*Number(price)).toFixed(2));
}

function invoicePriceChange(obj,price){
	var totleAmount = $(obj).val().trim();
	var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(!reg.test(totleAmount)){
		$(obj).addClass("errorbor");$(obj).val(0.00);
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
//			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
//	if(totleAmount.length == 0 || Number(totleAmount) <= 0){
	if(Number(totleAmount) < 0){
		$(obj).addClass("errorbor");$(obj).val(0.00);
		layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
//			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
	if(Number(totleAmount) > Number($("#max_price").val())){
		$(obj).addClass("errorbor");
		layer.alert("开票数量不允许超出最大开票数量", {
			icon : 2
		}, function(lay) {
			$(obj).focus();
			layer.close(lay);
		});
		return false;
	}
	$("#in_num").val((Number(totleAmount)/Number(price)).toFixed(2));
	$("#invoiceAmount").html(Number(totleAmount).toFixed(2));
	$("#amount").val(Number(totleAmount).toFixed(2));
}


function saveAfterOpenInvoiceAt(){
	checkLogin();
	clear2ErroeMes();
	var invoiceNo = $("#addAfterInvoiceAt #invoiceNo").val().trim();
	if(invoiceNo.length == 0){
		warn2Tips("invoiceNo","发票号不允许为空");
		return false;
	}else{
		var reg = /^\d{8}$/;
		if(!reg.test(invoiceNo)){
			warn2Tips("invoiceNo","请输入正确的8位数字发票号");
			return false;
		}
	}
	//-----------------------------开票数量-----------------------------------------------
	var num = $("#in_num").val().trim();
	var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(!reg.test(num)){
		$("#in_num").addClass("errorbor");
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#in_num").focus();
			layer.close(lay);
		});
		return false;
	}
	if(num.length == 0 || Number(num) <= 0){
		$("#in_num").addClass("errorbor");
		layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#in_num").focus();
			layer.close(lay);
		});
		return false;
	}
	if(Number(num) > Number($("#max_num").val())){
		$("#in_num").addClass("errorbor");
		layer.alert("开票数量不允许超出最大开票数量", {
			icon : 2
		}, function(lay) {
			$("#in_num").focus();
			layer.close(lay);
		});
		return false;
	}
	//--------------------------------开票金额--------------------------------------------
	var price = $("#in_price").val();
	if(!reg.test(price)){
		$("#in_price").addClass("errorbor");
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#in_price").focus();
			layer.close(lay);
		});
		return false;
	}
	if(price.length == 0 || Number(price) <= 0){
		$("#in_price").addClass("errorbor");
		layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#in_price").focus();
			layer.close(lay);
		});
		return false;
	}
	if(Number(price) > Number($("#max_price").val())){
		$("#in_price").addClass("errorbor");
		layer.alert("开票金额不允许超出最大开票金额", {
			icon : 2
		}, function(lay) {
			$("#in_price").focus();
			layer.close(lay);
		});
		return false;
	}
	//---------------------------------------------------------------
	$("#invoiceAmount").html(Number(price).toFixed(2));
	$("#amount").val(Number(price).toFixed(2));
	
	$("#addAfterInvoiceAt").submit();
}