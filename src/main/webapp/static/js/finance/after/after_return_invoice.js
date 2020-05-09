$(function(){
	if($("#currentMonthInvoice").val()!=1){
		var returnNum = "",returnId = "",amount = 0, indx = "";
		$("#afterGoodsListId").find("input[type='hidden'][name='hideReturnNum']").each(function(i){//循环售后退货(票)数量
			returnNum = $(this).val();returnId = $(this).attr("id");
			var indx = $(this).attr("id");
			var price = Number($("#price_"+indx).val());
			amount = (Number(amount) + Number(returnNum*price)).toFixed(2);
		})
		$("#afterReturnInvoiceAmount").html(amount);
	}else{
		var returnNum = "",returnId = "",amount = 0, indx = "";
		$("#afterGoodsListId").find("input[type='hidden'][name='hideReturnInvoiceAmount']").each(function(i){//循环售后退货(票)数量
			amount = (Number(amount) + Number($(this).val())).toFixed(2);
		})
		$("#afterReturnInvoiceAmount").html(amount);
		
	}
})
function checkReturnInvoice(obj,index){
	if(index == 0){
		if($(obj).is(":checked")){//选中
			var returnNum = "",returnId = "",amount = 0, indx = "";
			$("#afterGoodsListId").find("input[type='hidden'][name='hideReturnNum']").each(function(i){//循环售后退货(票)数量
				returnNum = $(this).val();returnId = $(this).attr("id");
				$("#afterGoodsListId").find("input[type='hidden'][name='hideInvoiceNum']").each(function(j){//循环财务已开发票
					if(i == j){
						if(Number(returnNum) <= Number($(this).val())){ //退货(票)数量小于等于已开票数量
							$("#spanInvoiceNum"+$(this).attr("id")).html(Number(returnNum).toFixed(2));
						}else{
							$("#spanInvoiceNum"+$(this).attr("id")).html(Number($(this).val()).toFixed(2));
						}
					}
				})
					var indx = $(this).attr("id");
					var price = Number($("#price_"+indx).val());
					amount = (Number(amount) + Number(returnNum*price)).toFixed(2);
			})
			
			$("#afterReturnInvoiceAmount").html(amount);
		}
	}else{
		if($(obj).is(":checked")){//选中
			var hiddenAmount = $("#afterReturnInvoiceAmountHidden").val();
			$("#afterGoodsListId").find("input[type='hidden'][name='hideInvoiceNum']").each(function(j){//循环财务已开发票
				$("#spanInvoiceNum"+$(this).attr("id")).html(Number($(this).val()).toFixed(2));
			})
			$("#afterReturnInvoiceAmount").html(hiddenAmount);
		}
	}
}


function vailInvoiceNo(obj){
	clear2ErroeMes();
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

function addAfterReturnInvoice(){
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
		$("#returnInvoiceForm").find("#dynamicParameter").html("");//清空参数，防止提交失败后再次提交参数重复
		var invoiceNumArr = [];
		var invoiceAmountArr = [];
		var invoicePriceArr = [];
		var detailGoodsIdArr = [];
		//此次退票数量(红字发票退票数量改成退货数量) 2018-9-27
		var returnInvoiceAll = $("input[type='radio'][name='returnInvoiceCheck']:checked").val();
		if(returnInvoiceAll ==1){
			$("#afterGoodsListId").find("input[name='hideInvoiceNum']").each(function(i){
					invoiceNumArr.push($(this).val().trim());
			});
			$("#afterGoodsListId").find("input[name='hideReturnInvoiceAmount']").each(function(i){
					invoiceAmountArr.push($(this).val().trim());
			});
			//退票产品单价
			$("#addAfterCapitalBillForm").find("input[name='orderPrice']").each(function(n){
					invoicePriceArr.push($(this).val().trim());
			});
			//售后单-销售单产品详细表ID
			$("#addAfterCapitalBillForm").find("input[name='detailGoodsId']").each(function(j){
					detailGoodsIdArr.push($(this).val().trim());
			});

		}else{
			$("#afterGoodsListId").find("input[name='hideReturnNum']").each(function(i){
				if($("#afterGoodsListId").find("input[name='hideReturnNum'][id='"+i+"']").val() != 0){
					invoiceNumArr.push($(this).val().trim());
				}
			});
			var invoicePriceArr = [];
			//退票产品单价
			$("#addAfterCapitalBillForm").find("input[name='orderPrice']").each(function(n){
				if($("#afterGoodsListId").find("input[name='hideReturnNum'][id='"+n+"']").val() != 0){
					invoicePriceArr.push($(this).val().trim());
				}
			});
			var detailGoodsIdArr = [];
			//售后单-销售单产品详细表ID
			$("#addAfterCapitalBillForm").find("input[name='detailGoodsId']").each(function(j){
				if($("#afterGoodsListId").find("input[name='hideReturnNum'][id='"+j+"']").val() != 0){
					detailGoodsIdArr.push($(this).val().trim());
				}
			});
		}
		
		if(invoiceNumArr.length != invoicePriceArr.length || invoicePriceArr.length != detailGoodsIdArr.length){
			
			layer.alert("参数获取错误，请刷新当前页重试或联系管理员！", {
				icon : 2
			}, function(lay) {
				layer.close(lay);
			});
		}
		
		$("#returnInvoiceForm").find("#dynamicParameter").html(
				
				"<input name='invoiceNumArr' type='hidden' value='"+invoiceNumArr+"'/>" +
						"<input name='invoicePriceArr' type='hidden' value='"+invoicePriceArr+"'/>" +
						(returnInvoiceAll == 1 ? ("<input name='invoiceAmountArr' type='hidden' value='"+invoiceAmountArr+"'/>"):"")+
								"<input name='detailGoodsIdArr' type='hidden' value='"+detailGoodsIdArr+"'/>");
	}
	$("#returnInvoiceForm").submit();
	
}