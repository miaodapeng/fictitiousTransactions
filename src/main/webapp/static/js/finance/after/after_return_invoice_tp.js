function checkPartReturnInvoice(obj){
	$("#afterReturnInvoiceAmount").html(0.00);
	$("#afterGoodsListId").find("input[name='selectInvoiceGoodsLine']").each(function(){
		$(this).attr("checked",false);
		$(this).attr("disabled",false);
	});
	$("#afterGoodsListId").find("input[name='returnInvoiceNum']").each(function(){
		$(this).attr("readonly",false);
	});
	$("#afterGoodsListId").find("input[name='returnInvoiceAmount']").each(function(){
		$(this).attr("readonly",false);
	});
}

function checkAllReturnInvoice(obj){
	var index = "",amount = 0.00;
	$("#afterGoodsListId").find("input[name='selectInvoiceGoodsLine']").each(function(){
		$(this).prop("checked",false);
		$(this).attr("disabled",true);
	});
	$("#afterGoodsListId").find("input[name='returnInvoiceNum']").each(function(){
		$(this).attr("readonly",false);
		index = $(this).attr("alt");
		$(this).val(Number($("#hideReturnInvoiceNum_"+index).val()).toFixed(2));
	});
	$("#afterGoodsListId").find("input[name='returnInvoiceAmount']").each(function(){
		$(this).attr("readonly",false);
		index = $(this).attr("alt");
		$(this).val(Number($("#hideReturnInvoiceAmount_"+index).val()).toFixed(2));
		amount = (Number(amount) + Number($("#hideReturnInvoiceAmount_"+index).val())).toFixed(2);
	});
	$("#afterReturnInvoiceAmount").html(amount);
}

function updateReturnInvoiceInfo(obj,str){
	$(obj).removeClass("errorbor");
	var	reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,10})))$/;
	if(!reg.test($(obj).val())  || $(obj).val().length > 15){
		$(obj).val("0.00");
		$(obj).addClass("errorbor");
		return false;
	}
	if(str == 'num'){
		var index = $(obj).attr("alt");
		var num = $(obj).val();
		//获取税率
		var tax = Number($("#tax").val());
//		取录入总额的值
		var totalPrice = $("#returnInvoiceAmount_"+index).val();
		if(Number(num) == 0 || Number(num) > Number($("#hideReturnInvoiceNum_"+index).val()) || Number(totalPrice) == 0){
//			$(obj).val("0.00");
			$(obj).addClass("errorbor");
//			$("#returnInvoiceAmount_"+index).val(0.00);
			return false;
		}
//		var price = $("#hideReturnInvoicePrice_"+index).val();
//		如果数量跟总额都有值，则将退票单价修改掉(保留9位)
		$("#invoicePrice_"+index).html(Number(totalPrice/num/(1+tax)).toFixed(9));
	}else{
		var index = $(obj).attr("alt");
		var amount = $(obj).val();
		//获取税率
		var tax = Number($("#tax").val());
		//获取退票数量
		var num = $("#returnInvoiceNum_"+index).val();
		if(Number(amount) == 0 || Number(amount) > Number($("#hideReturnInvoiceAmount_"+index).val()) || Number(num) == 0){
//			$(obj).val("0.00");
			$(obj).addClass("errorbor");
//			$("#returnInvoiceNum_"+index).val(0.00);
			return false;
		}
//		var price = $("#hideReturnInvoicePrice_"+index).val();
//		如果数量跟总额都有值，则将退票单价修改掉(保留9位)
		$("#invoicePrice_"+index).html(Number(amount/num/(1+tax)).toFixed(9));
	}
	var totleAmount = 0;
	$("#afterGoodsListId").find("input[name='selectInvoiceGoodsLine']").each(function(n){
		if($(this).prop("checked") == true){
			totleAmount = Number(totleAmount) + Number($("#returnInvoiceAmount_" + $(this).attr("id")).val());
		}
	});
	$("#afterReturnInvoiceAmount").html(totleAmount);
}

function selectInvoiceGoods(obj){
	var totleAmount = 0;
	$("#afterGoodsListId").find("input[name='selectInvoiceGoodsLine']").each(function(n){
		if($(this).prop("checked") == true){
			totleAmount = Number(totleAmount) + Number($("#returnInvoiceAmount_" + $(this).attr("id")).val());
		}
	});
	$("#afterReturnInvoiceAmount").html(totleAmount);
	
	var index = $(obj).attr("alt");
	//获取退票数量
	var num = $("#returnInvoiceNum_"+index).val();
//	取录入总额的值
	var totalPrice = $("#returnInvoiceAmount_"+index).val();
	$("#invoicePrice_"+index).html(Number(totalPrice/num/(1+tax)).toFixed(9));
}

function vailInvoiceNo(obj){
	clear2ErroeMes();
	$(obj).removeClass("errorbor");
	var invoiceNo = $(obj).val().trim();
	if(invoiceNo.length == 0){
		warn2Tips("in_invoiceNo","发票号不允许为空");
		return false;
	}else{
		var reg = /^\d{8}$/;
		if(!reg.test(invoiceNo)){
			warn2Tips("in_invoiceNo","请输入正确的8位数字发票号");
			return false;
		}
	}
}

function addAfterReturnInvoiceTp(){
	checkLogin();
	if($("#currentMonthInvoice").val() == "0"){//非当月发票
		clear2ErroeMes();
		var invoiceNo = $("#addAfterCapitalBillForm #in_invoiceNo").val().trim();
		if(invoiceNo.length == 0){
			warn2Tips("in_invoiceNo","发票号不允许为空");
			return false;
		}else{
			var reg = /^\d{8}$/;
			if(!reg.test(invoiceNo)){
				warn2Tips("in_invoiceNo","请输入正确的8位数字发票号");
				return false;
			}
		}
		$("#invoiceNo").val(invoiceNo);
		var abc = $("input[type='radio'][name='returnInvoiceCheck']:checked").val();
		if(abc == 1){
			var selectNum = 0;
			$("#afterGoodsListId").find("input[name='selectInvoiceGoodsLine']").each(function(n){
				if($(this).prop("checked") == true){
					selectNum ++;
				}
			});
			if(selectNum == 0){
				layer.alert("请选择需要退票的商品记录", {
					icon : 2
				}, function(lay) {
					layer.close(lay);
				});
				return false;
			}
		}
		
		
		$("#returnInvoiceForm").find("#dynamicParameter").html("");//清空参数，防止提交失败后再次提交参数重复
		var invoiceNumArr = [];
		var invoicePriceArr = [];
		var invoiceAmountArr = [];
		var detailGoodsIdArr = [];
		$("#addAfterCapitalBillForm").find("input[name='selectInvoiceGoodsLine']").each(function(n){
			if(abc == 1 && $(this).prop("checked") == true){//部分退
				var id = $(this).attr("id");
				invoiceNumArr.push($("#returnInvoiceNum_"+id).val().trim());//此次退票数量
				invoicePriceArr.push($("#hideReturnInvoicePrice_"+id).val().trim());//退票产品单价
				invoiceAmountArr.push($("#returnInvoiceAmount_"+id).val().trim());//退票总额
				detailGoodsIdArr.push($("#detailGoodsId_"+id).val().trim());//售后单-销售单产品详细表ID
			}else if(abc == 2){
				var id = $(this).attr("id");
				invoiceNumArr.push($("#returnInvoiceNum_"+id).val().trim());//此次退票数量
				invoicePriceArr.push($("#hideReturnInvoicePrice_"+id).val().trim());//退票产品单价
				invoiceAmountArr.push($("#returnInvoiceAmount_"+id).val().trim());//退票总额
				detailGoodsIdArr.push($("#detailGoodsId_"+id).val().trim());//售后单-销售单产品详细表ID
			}
		});
		if(invoiceNumArr.length == invoicePriceArr.length && invoicePriceArr.length == invoiceAmountArr.length && invoiceAmountArr.length == detailGoodsIdArr.length){
			$("#returnInvoiceForm").find("#dynamicParameter").html(
					"<input name='invoiceNumArr' type='hidden' value='"+invoiceNumArr+"'/>" +
					"<input name='invoicePriceArr' type='hidden' value='"+invoicePriceArr+"'/>" +
					"<input name='invoiceAmountArr' type='hidden' value='"+invoiceAmountArr+"'/>" +
					"<input name='detailGoodsIdArr' type='hidden' value='"+detailGoodsIdArr+"'/>");
		}else{
			layer.alert("参数获取错误，请刷新当前页重试或联系管理员！", {
				icon : 2
			}, function(lay) {
				layer.close(lay);
			});
		}
	}
	$("#returnInvoiceForm").submit();
	

}