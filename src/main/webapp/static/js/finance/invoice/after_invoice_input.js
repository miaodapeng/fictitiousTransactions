
var invoiceType = 0.00;
function changeInvoiceType(obj){
	var val = $(obj).find("option:selected").val();
	$("#buyorderInfo").find("input[type='checkbox'][name='selectInvoiceName']").each(function(){
		if($(this).is(":checked")){
//			if(val!=$(this).val()){
				$(this).prop("checked",false);
//			}
		}
	});
	invoiceType = $(obj).find("option:selected").attr("id");
	$("#invoiceForm").find("#ratio").val(invoiceType);
	$("#buyorderInfo").find("input[name='update_num_pice']").click();
	
	$("#selectNum").html(0.00);
	$("#selectPrice").html(0.00);
	$("#invoiceForm").find("#amount").val(0.00);
}

function updateInvoice(price,arrivalNum,inNum,buyNum,goodsNum){
	invoiceType = Number($("#invoiceForm #invoiceType").find("option:selected").attr("id")).toFixed(2)*1;
//	$("#buyorderInfo").find("#invoice_num"+buyNum+"_"+goodsNum).val((arrivalNum - inNum).toFixed(2));
//	$("#buyorderInfo").find("#invoice_totle_amount"+buyNum+"_"+goodsNum).val(((arrivalNum - inNum)*Number(price)).toFixed(2));
	
	var num = $("#buyorderInfo").find("#invoice_num"+buyNum+"_"+goodsNum).val();
	var totalPrice = $("#buyorderInfo").find("#invoice_totle_amount"+buyNum+"_"+goodsNum).val();
	var str = $("#invoiceInfo #invoiceType").val();
	if(str == 429 || str == 682 || str == 684 || str == 686 || str == 688 || str == 972){
//		$("#buyorderInfo").find("#invoice_price"+buyNum+"_"+goodsNum).html((totalPrice/num/(1+invoiceType)).toFixed(8));
		$("#buyorderInfo").find("#invoice_price"+buyNum+"_"+goodsNum).html((totalPrice/num).toFixed(8));
		$("#buyorderInfo").find("#invoice_no_tax"+buyNum+"_"+goodsNum).html((totalPrice/(1+invoiceType)).toFixed(8));
		$("#buyorderInfo").find("#invoice_tax"+buyNum+"_"+goodsNum).html( (totalPrice -(totalPrice/(1+invoiceType))).toFixed(8));
	}else{
//		$("#buyorderInfo").find("#invoice_price"+buyNum+"_"+goodsNum).html((totalPrice/num/(1+invoiceType)).toFixed(8));
		$("#buyorderInfo").find("#invoice_price"+buyNum+"_"+goodsNum).html((totalPrice/num).toFixed(8));
		$("#buyorderInfo").find("#invoice_no_tax"+buyNum+"_"+goodsNum).html((totalPrice/(1+invoiceType)).toFixed(8));
		$("#buyorderInfo").find("#invoice_tax"+buyNum+"_"+goodsNum).html(0.00);
	}
}

function selectBuyOrder(obj,price,maxNum,maxPrice,inNum,index){
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE || isIE11) {
    	$(obj).attr("disabled", true);
    	setTimeout(function() {
    		$(obj).attr("disabled", false);
    	}, 1000);
    }
	
	var id = $(obj).attr("id");
	if(Number(maxNum) == 0){
		layer.alert("已入库数量小于等于已录票数量，请验证！");
		$("#"+id).prop("checked",false);
		return false;
	}
	/*var val = $("#invoiceForm #invoiceType").find("option:selected").val();
	if(val != $(obj).val()){
		layer.alert("所选记录票种与发票票种不符，请重新确认！", {
			icon : 2
		}, function(lay) {
			$("#"+id).prop("checked",false);
			layer.close(lay);
		});
		return false;
	}*/

	//获取发票税额
	var	invoiceType = Number($("#invoiceForm #invoiceType").find("option:selected").attr("id")).toFixed(10)*1;
	var str = $("#invoiceInfo #invoiceType").val();
	//判断录票数量和录票总价不为0
	if(Number($("#invoice_num"+index).val()) != 0 && Number($("#invoice_totle_amount"+index).val()) != 0){
		var num = $("#invoice_num"+index).val();
		var totalPrice = $("#invoice_totle_amount"+index).val();
		if(str == 429 || str == 682 || str == 684 || str == 686 || str == 688 || str == 972){
			//修改录票单价
//			$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
			$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
			$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
			$("#invoice_tax"+index).html((totalPrice - (totalPrice/(1+invoiceType))).toFixed(9));
		}else{
			//修改录票单价
//			$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
			$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
			$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
			$("#invoice_tax"+index).html(0.00);
		}
		
	}
	
	var selectPrice = Number($("#invoice_totle_amount" + id).val());//当前选择的金额
	var totalAmount = Number($("#amount").val());//已选择金额
	if($(obj).is(':checked')){
		//验证数量----------------------------------------------------------------------------------------
		var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
		if(str == "2-1"){//蓝字有效
			//验证数字是否合法
			var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
			if(!reg.test($("#invoice_num"+id).val())){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			//本次录票数量超出最大限制
			var invoiceNum = Number($("#invoice_num"+id).val());
			if(invoiceNum > Number(maxNum)){//
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("（本次录票数量+已录票数量）不得大于已入库总数！", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
		}else{//非蓝字有效
			var invoiceNum = Number($("#invoice_num"+id).val());
			//验证数字是否合法
			var reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
			if(!reg.test($("#invoice_num"+id).val())){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			//非蓝字有效，只允许输入负数
			if(invoiceNum >= 0){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("只允许输入负数，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			//无已录入信息，不允许录入选择非蓝字有效
			if(inNum==0 && invoiceNum < 0){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("未检测到已录票信息，请验证！", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			//本次录票额（负数）绝对值大于已录入数量
			if(invoiceNum + Number(maxNum) < 0){//
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("（本次录票数量+已录票数量）不得小于零！", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
		}
		
		//验证金额-------------------------------------------------------------------------------------
		var invoiceTotleAmount = Number($("#invoice_totle_amount"+id).val());
		var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
		if(str == "2-1"){
			var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
			if(!reg.test($("#invoice_totle_amount"+id).val())){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			if(invoiceTotleAmount > maxPrice){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("本次录票总额不允许超出（已入库数量-已录票数量）*采购价", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				return false;
			}
		}else{
			if(inNum==0 && Number($("#invoice_totle_amount"+id)) < 0){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("未检测到已录票信息，请验证！", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			var reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
			if(!reg.test($("#invoice_totle_amount"+id).val())){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("只允许输入数字，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			if(invoiceTotleAmount >= 0){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("只允许负数数字，小数点后只允许保留八位", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				return false;
			}
			if(invoiceTotleAmount + maxPrice < 0){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("本次录票总额绝对值不允许低于已录金额", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				return false;
			}
		}
		
		//---------------------------------------------------------------------------------------------------
		var traderId = $(obj).attr("class");
		$("#buyorderInfo").find("input[name='selectInvoiceName']:checked").each(function(i){
			if(traderId!=$(this).attr("class")){
				layer.alert("所选数据中供应商不同，请重新确认！", {
					icon : 2
				}, function(lay) {
					$("#"+id).prop("checked",false);
					layer.close(lay);
				});
				return false;
			}
		})
		
		$("#amount").val((totalAmount + selectPrice).toFixed(9));
		
		$("#selectPrice").html((totalAmount + selectPrice).toFixed(9));
		$("#selectNum").html((Number($("#selectNum").html()) + Number($("#invoice_num"+$(obj).attr("id")).val())).toFixed(10));
	}else{
		$("#amount").val((totalAmount -selectPrice).toFixed(9));
		$("#selectPrice").html((totalAmount - selectPrice).toFixed(9));
		
		$("#selectNum").html((Number($("#selectNum").html()) - Number($("#invoice_num"+id).val())).toFixed(10));
	}
}

function invoiceNumChange(obj,price,maxNum){
	$(obj).removeClass("errorbor");
	/*if($("#"+id).is(":checked")){
		return false;
	}*/
	var reg;
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
	}
	if((!reg.test($(obj).val())) || $(obj).val().length > 15){
		$(obj).val("0.00");
		$(obj).addClass("errorbor");
		return false;
	}
	if(Number($(obj).val()) == 0 || Number($(obj).val()) > maxNum){
		$(obj).val("0.00");
		$(obj).addClass("errorbor");
		return false;
	}
	
//	判断本次录票总额是否为0
	if(Number($("#invoice_totle_amount"+index).val()) == 0){
		$("#invoice_totle_amount"+index).val("0.00");
		$("#invoice_totle_amount"+index).addClass("errorbor");
		return false;
	}	
	
	invoiceType = Number($("#invoiceForm #invoiceType").find("option:selected").attr("id")).toFixed(10)*1;
	var invoiceNum = Number($(obj).val());
	var index = $(obj).attr("alt");
//	$("#invoice_price"+index).html((price/(1+invoiceType)).toFixed(8));
//	$("#invoice_totle_amount"+index).val((price*invoiceNum).toFixed(2));
	
	var str = $("#invoiceInfo #invoiceType").val();
	//判断录票数量和录票总价不为0
	if(Number($(obj).val()) != 0 && Number($("#invoice_totle_amount"+index).val()) != 0){
		var num = $(obj).val();
		var totalPrice = $("#invoice_totle_amount"+index).val();
		if(str == 429 || str == 682 || str == 684 || str == 686 || str == 688 || str == 972){
			//修改录票单价
//			$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
			$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
			$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
			$("#invoice_tax"+index).html((totalPrice - (totalPrice/(1+invoiceType))).toFixed(9));
		}else{
			//修改录票单价
//			$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
			$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
			$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
			$("#invoice_tax"+index).html(0.00);
		}
		
	}
	
	
	
	var selectNum = 0.00;
	var selectPrice = 0.00;
	$("#buyorderInfo").find("input[type='checkbox']").each(function(){
		if($(this).is(':checked')){
			selectNum += Number($("#invoice_num"+$(this).attr("id")).val());
			selectPrice += Number($("#invoice_totle_amount"+$(this).attr("id")).val());
		}
	});
	$("#selectNum").html(selectNum.toFixed(10));
	$("#selectPrice").html(selectPrice.toFixed(9));
	$("#invoiceForm").find("#amount").val(selectPrice.toFixed(8));
}
function invoiceTotleAmountChange(obj,maxPrice,price){
	$(obj).removeClass("errorbor");
	var reg;
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,9}|[1-9][0-9]*\.\d{1,9})))$/;
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,9}|[1-9][0-9]*\.\d{1,9})))$/;
	}
	if(!reg.test($(obj).val())  || $(obj).val().length > 15){
		$(obj).val("0.00");
		$(obj).addClass("errorbor");
		return false;
	}
	if(Number($(obj).val()) == 0 || Number($(obj).val()) > maxPrice){
		$(obj).val("0.00");
		$(obj).addClass("errorbor");
		return false;
	}
	//判断本次录票数量是否为0
	if(Number($("#invoice_num"+index).val()) == 0){
		$("#invoice_num"+index).val("0.00");
		$("#invoice_num"+index).addClass("errorbor");
		return false;
	}
	
	invoiceType = Number($("#invoiceForm #invoiceType").find("option:selected").attr("id")).toFixed(10)*1;
	var invoiceTotleAmount = Number($(obj).val());
	var index = $(obj).attr("alt");
//	$("#invoice_price"+index).html((price/(1+invoiceType)).toFixed(8));
//	$("#invoice_num"+index).val((invoiceTotleAmount/price).toFixed(2));
	
	var str = $("#invoiceInfo #invoiceType").val();
	
	//判断录票数量和录票总价不为0
	if(Number($(obj).val()) != 0 && Number($("#invoice_totle_amount"+index).val()) != 0){
		var totalPrice = $(obj).val();
		var num = $("#invoice_num"+index).val();
	if(str == 429 || str == 682 || str == 684 || str == 686 || str == 688 || str == 972){
		//修改录票单价
//		$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
		$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
		$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
		$("#invoice_tax"+index).html((totalPrice - (totalPrice/(1+invoiceType))).toFixed(9));
	}else{
		//修改录票单价
//		$("#invoice_price"+index).html((totalPrice/num/(1+invoiceType)).toFixed(9));
		$("#invoice_price"+index).html((totalPrice/num).toFixed(9));
		$("#invoice_no_tax"+index).html((totalPrice/(1+invoiceType)).toFixed(9));
		$("#invoice_tax"+index).html(0.00);
	}
	}
	
	
	var selectNum = 0.00;
	var selectPrice = 0.00;
	$("#buyorderInfo").find("input[type='checkbox']").each(function(){
		if($(this).is(':checked')){
			selectNum += Number($("#invoice_num"+$(this).attr("id")).val());
			selectPrice += Number($("#invoice_totle_amount"+$(this).attr("id")).val());
		}
	});
	$("#selectNum").html(selectNum.toFixed(10));
	$("#selectPrice").html(selectPrice.toFixed(9));
	$("#invoiceForm").find("#amount").val(selectPrice.toFixed(8));
}
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

function updateInvoiceColor(){
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){
		$("#invoiceForm").find("#colorType").val("2");//蓝票
		$("#invoiceForm").find("#isEnable").val("1");//有效
	}else if(str == "2-0"){
		$("#invoiceForm").find("#colorType").val("2");
		$("#invoiceForm").find("#isEnable").val("0");//失效
	}else if(str == "1-1"){
		$("#invoiceForm").find("#colorType").val("1");//红票
		$("#invoiceForm").find("#isEnable").val("1");
	}
}

function addInvoice(){
	checkLogin();
	clear2ErroeMes();
	var invoiceNo = $("#invoiceForm").find("#invoiceNo").val().trim();
	if(invoiceNo.length == 0){
		warn2Tips("invoiceNo","发票号不允许为空");
		return false;
	}else{
		var regs = /^\d{8}$/;
		if(!regs.test(invoiceNo)){
			warn2Tips("invoiceNo","请输入正确的8位数字发票号");
			return false;
		}
	}
	var id = "";
	var invoice;var flag = true;
	var relatedIdArr = [];var invoiceNumArr = [];var invoicePriceArr = [];var invoiceTotleAmountArr = [];var buyorderGoodsIdArr = [];
	if($("#buyorderInfo").find("input[name='selectInvoiceName']:checked").length>0){
		$("#buyorderInfo").find("input[name='selectInvoiceName']:checked").each(function(i){
			id = $(this).attr("id");
			//采购单号
			relatedIdArr.push($("#relatedId"+id).val());
			//产品ID
			buyorderGoodsIdArr.push($("#buyorderInfo").find("#buyorderGoodsId"+id).val());
			//录票单价
			invoicePriceArr.push($("#invoice_price"+id).html());
			//本次录票数量----------------------------------------------------------------------
			invoice = $("#invoice_num"+id).val().trim();
			//已勾选项，不允许为空
			if(invoice.length == 0 || invoice == 0 || invoice.length > 15){
				$("#invoice_num"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位，最大允许15位", {
					icon : 2
				}, function(lay) {
					$("#invoice_num"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
				if(!reg.test($("#invoice_num"+id).val())){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留八位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量超出最大限制
				if(Number($("#invoice_num"+id).val()) > Number($("#max_num"+id).val())){//
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得大于已入库总数！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}else{//非蓝字有效
				//验证数字是否合法
				var reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
				if(!reg.test(invoice)){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("只允许输入数字，小数点后只允许保留八位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(Number(invoice) >= 0){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留八位！", {
						icon : 2
					}, function(lay) {
						$("#"+id).prop("checked",false);
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					return false;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#inNum"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("未检测到已录票信息，请验证！", {
						icon : 2
					}, function(lay) {
						$("#"+id).prop("checked",false);
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					return false;
				}
				//本次录票数量（负数）绝对值大于已录入数量
				if(Number(invoice) + Number($("#max_num"+id).val()) < 0){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得小于零！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}
			invoiceNumArr.push(invoice);
			//本次录票总金额------------------------------------------------------------------------------
			invoice = $("#invoice_totle_amount"+id).val();
			var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
			//已勾选项，不允许为空
			if(invoice.length == 0 || invoice == 0 || invoice.length > 15){
				$("#invoice_totle_amount"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位，最大允许15位", {
					icon : 2
				}, function(lay) {
					$("#invoice_totle_amount"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				var reg = /^(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
				if(!reg.test(invoice)){
					$("#invoice_totle_amount"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留八位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totle_amount"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//录入金额大于最大限制
				if(Number(invoice) > Number($("#max_price"+id).val())){
					$("#invoice_totle_amount"+id).addClass("errorbor");
					layer.alert("本次录票总额不允许超出（已入库数量-已录票数量）*采购价", {
						icon : 2
					}, function(lay) {
						$("#invoice_totle_amount"+id).focus();
						layer.close(lay);
					});
					return false;
				}
			}else{
				//验证数字是否合法
				var reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,10}|[1-9][0-9]*\.\d{1,10})))$/;
				if(!reg.test(invoice)){
					$("#invoice_totle_amount"+id).addClass("errorbor");
					layer.alert("只允许输入数字，小数点后只允许保留八位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totle_amount"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(invoice >= 0){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留八位！", {
						icon : 2
					}, function(lay) {
						$("#"+id).prop("checked",false);
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					return false;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#inNum"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_num"+id).addClass("errorbor");
					layer.alert("未检测到已录票信息，请验证！", {
						icon : 2
					}, function(lay) {
						$("#"+id).prop("checked",false);
						$("#invoice_num"+id).focus();
						layer.close(lay);
					});
					return false;
				}
				//本次录票额（负数）绝对值大于已录入金额
				if(Number(invoice) + Number($("#max_price"+id).val()) < 0){
					$("#invoice_totle_amount"+id).addClass("errorbor");
					layer.alert("本次录票总额绝对值不允许低于已录金额", {
						icon : 2
					}, function(lay) {
						$("#invoice_totle_amount"+id).focus();
						layer.close(lay);
					});
					return false;
				}
			}
			invoiceTotleAmountArr.push(invoice);
		});
		if(flag==false){
			return false;
		}
	}else{
		layer.alert("录票总件数/录票总额为0，不允许提交！", {icon : 2});
		return false;
	}
	if(relatedIdArr.length <= 0 || $("#invoiceForm").find("#amount").val()==0){
		layer.alert("参数错误", {icon : 2});
		return false;
	}
	
	$("#invoiceForm #hideValue").html("");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='ratio' value='"+$("#invoiceForm #invoiceType").find("option:selected").attr("id")+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='relatedIdArr' value='"+relatedIdArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='detailGoodsIdArr' value='"+buyorderGoodsIdArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoiceNumArr' value='"+invoiceNumArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoicePriceArr' value='"+invoicePriceArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoiceTotleAmountArr' value='"+invoiceTotleAmountArr+"'>");
	
	$.ajax({
		async:false,
		url:'./saveBuyorderInvoice.do',
		data:$("#invoiceForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			refreshNowPageList(data);
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}