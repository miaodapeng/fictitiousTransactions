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
function selectCheck(obj,id){	
	if($(obj).is(":checked")){
		var maxNum = $("#max_num"+id).val();
		var maxPrice = $("#max_price"+id).val();
		if(checkNum(id,maxNum,$("#in_num"+id).val().trim(),0) == true && checkPrice(id,maxPrice,$("#in_price"+id).val().trim(),0) == true){
			$("#invoiceAmount").html((Number($("#amount").val()) + Number($("#in_price"+id).val())).toFixed(2));
			$("#amount").val((Number($("#amount").val()) + Number($("#in_price"+id).val())).toFixed(2));
		}else{
			$(obj).prop("checked",false);
		}
	}else{
		$("#invoiceAmount").html((Number($("#amount").val()) - Number($("#in_price"+id).val())).toFixed(2));
		$("#amount").val((Number($("#amount").val()) - Number($("#in_price"+id).val())).toFixed(2));
	}
}

function invoiceNumChange(id,maxNum,price){
	var num = $("#in_num"+id).val().trim();
	if(checkNum(id,maxNum,num,0)){
		$("#in_price"+id).val((Number(num)*Number(price)).toFixed(2));
		if($("#checkTpName"+id).is(":checked")){
			var totleAmount = 0;
			$("#addAfterInvoiceTp").find("input[name='checkTpName']").each(function(i){//循环记录
				if($(this).is(":checked")){
					totleAmount = totleAmount + Number($("#in_price"+$(this).attr("alt")).val());
				}
			})
			$("#invoiceAmount").html(Number(totleAmount).toFixed(2));
			$("#amount").val(Number(totleAmount).toFixed(2));
		}
	}
}
function checkNum(id,maxNum,num,index){
	var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(!reg.test(num)){
		$("#in_num"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#in_num"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	if(num.length == 0 || Number(num) <= 0){
		$("#in_num"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#in_num"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	if(Number(num) > Number(maxNum)){
		$("#in_num"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("开票数量不允许超出最大开票数量", {
				icon : 2
			}, function(lay) {
				$("#in_num"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	return true;
}

function invoicePriceChange(id,maxPrice,price){
	var totleAmount = $("#in_price"+id).val().trim();
	if(checkPrice(id,maxPrice,totleAmount,0)){//效验金额是否符合要求
		$("#in_num"+id).val((Number(totleAmount)/Number(price)).toFixed(2));//重置数量
		if($("#checkTpName"+id).is(":checked")){//当前记录是否选择
			var totleAmount = 0;
			$("#addAfterInvoiceTp").find("input[name='checkTpName']").each(function(i){//循环记录
				if($(this).is(":checked")){
					totleAmount = totleAmount + Number($("#in_price"+$(this).attr("alt")).val());
				}
			})
			$("#invoiceAmount").html(Number(totleAmount).toFixed(2));
			$("#amount").val(Number(totleAmount).toFixed(2));
		}
	}
}
function checkPrice(id,maxPrice,totleAmount,index){
	var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	if(!reg.test(totleAmount)){
		$("#in_price"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#in_price"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	if(totleAmount.length == 0 || Number(totleAmount) <= 0){
		$("#in_price"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("开票数量必须为大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#in_price"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	if(Number(totleAmount) > Number(maxPrice)){
		$("#in_price"+id).addClass("errorbor");
		if(index == 1){
			layer.alert("开票数量不允许超出最大开票数量", {
				icon : 2
			}, function(lay) {
				$("#in_price"+id).focus();
				layer.close(lay);
			});
		}
		return false;
	}
	return true;
}

function saveAfterOpenInvoiceTp(){
	checkLogin();
	$("#saveAfterOpenInvoiceTp").find("#dynamicParam").html("");
	clear2ErroeMes();
	var invoiceNo = $("#addAfterInvoiceTp #invoiceNo").val().trim();
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
	//------------------------------订单详情产品ID------------------------------------------
	var detailGoodsIdArr = [];
	$("#addAfterInvoiceTp").find("input[name='checkTpName']").each(function(i){
		if($(this).is(":checked")){
			detailGoodsIdArr.push($(this).val());
		}
	})
	var flag = true;
	//-----------------------------开票数量-----------------------------------------------
	var invoiceNumIdArr = []; var invoicePriceIdArr = [];
	$("#addAfterInvoiceTp").find("input[name='in_num']").each(function(i){
		var id = $(this).attr("alt");
		if($("#checkTpName"+id).is(":checked")){
			var num = $(this).val().trim();
			if(checkNum(id,$("#max_num"+id).val(),num,1)){
				invoiceNumIdArr.push(Number(num).toFixed(2));
				invoicePriceIdArr.push((Number($("#ratio_price"+id).val()) * Number(num)).toFixed(2));
			}else{
				flag = false;
			}
		}
	})
	if(flag==false){
		return false;
	}
	//--------------------------------开票金额--------------------------------------------
	var totlePrice = 0;var invoiceTotleAmountArr = [];
	$("#addAfterInvoiceTp").find("input[name='in_price']").each(function(i){
		var id = $(this).attr("alt");
		if($("#checkTpName"+id).is(":checked")){
			var price = $(this).val();
			if(checkPrice(id,$("#max_price"+id).val(),price,1)){
				totlePrice = totlePrice + Number(price);
				invoiceTotleAmountArr.push(Number(totlePrice).toFixed(2));
			}else{
				flag = false;
			}
		}
	})
	if(flag==false){
		return false;
	}
	if(!(invoiceNumIdArr.length == invoicePriceIdArr.length && invoiceTotleAmountArr.length > 0)){
		layer.alert("请选择需要开票的产品", {
			icon : 2
		}, function(lay) {
			layer.close(lay);
		});
		return false;
	}
	//------------------------------------------------------------------
	$("#invoiceAmount").html(Number(totlePrice).toFixed(2));
	$("#amount").val(Number(totlePrice).toFixed(2));
	//------------------------------------------------------------------
	$("#saveAfterOpenInvoiceTp").find("#dynamicParam").html("<input type='hidden' name='invoiceNo' value='"+invoiceNo+"'>" +
															"<input type='hidden' name='invoiceType' value='"+$("#invoiceType").val()+"'>" +
															"<input type='hidden' name='ratio' value='"+$("#ratio").val()+"'>" +
															"<input type='hidden' name='detailGoodsIdArr' value='"+detailGoodsIdArr+"'>" +
															"<input type='hidden' name='invoiceNumIdArr' value='"+invoiceNumIdArr+"'>" +
															"<input type='hidden' name='invoicePriceIdArr' value='"+invoicePriceIdArr+"'>" +
															"<input type='hidden' name='invoiceTotleAmountArr' value='"+invoiceTotleAmountArr+"'>");
	$("#saveAfterOpenInvoiceTp").submit();
}