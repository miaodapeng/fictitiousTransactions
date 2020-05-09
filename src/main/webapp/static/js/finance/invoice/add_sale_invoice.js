var ratio = 0.00;
function changeInvoiceType(obj){
	ratio = $(obj).find("#ratio").val();
	$("#invoiceForm").find("#ratio").val(ratio);
	$("#saleorderInfo").find("input[name='update_num_pice']").click();
}

function updateInvoice(price,num){
	ratio = Number($("#invoiceForm").find("#ratio").val());
	$("#saleorderInfo").find("#invoice_price_"+num).html((price/(1+ratio)).toFixed(8));
	$("#saleorderInfo").find("#invoice_totalAmount_"+num).html((price/(1+ratio)).toFixed(2));
}

function invoiceNumChange(obj,price,maxNum,inNum){
	$(obj).removeClass("errorbor");
	//红蓝字
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		if(!reg.test($(obj).val())  || $(obj).val().length > 15){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
		var invoiceNum = Number($(obj).val());
		if(invoiceNum == 0 || invoiceNum > maxNum){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		if(!reg.test($(obj).val())  || $(obj).val().length > 15){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
		var invoiceNum = Number($(obj).val());
		if(invoiceNum == 0 || (invoiceNum + maxNum) < 0){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
	}

	var index = $(obj).attr("alt");
	$("#invoice_totalAmount_"+index).val((price*invoiceNum).toFixed(2));
	
	var selectPrice = 0.00;
	$("#saleorderInfo").find("input[type='checkbox'][name='checkName']").each(function(){
		if($(this).is(':checked')){
			selectPrice += Number($("#invoice_totalAmount_"+$(this).attr("id")).val());
		}
	});
	$("#invoiceForm").find("#amount").val(Number(selectPrice).toFixed(2));
}

function invoiceTotleAmountChange(obj,maxPrice,price){
	$(obj).removeClass("errorbor");
	//红蓝字
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		if(!reg.test($(obj).val())  || $(obj).val().length > 15){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
		var invoiceTotleAmount = Number($(obj).val());
		if(invoiceTotleAmount == 0 || invoiceTotleAmount > maxPrice){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		if(!reg.test($(obj).val())  || $(obj).val().length > 15){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
		var invoiceTotleAmount = Number($(obj).val());
		if(invoiceTotleAmount == 0 || (invoiceTotleAmount + maxPrice) < 0){
			$(obj).val("0.00");
			$(obj).addClass("errorbor");
			return false;
		}
	}
	
	var index = $(obj).attr("alt");
	$("#invoice_num_"+index).val(Number(invoiceTotleAmount/price).toFixed(2));
	
	var selectPrice = 0;
	$("#saleorderInfo").find("input[type='checkbox'][name='checkName']").each(function(){
		if($(this).is(':checked')){
			selectPrice += Number($("#invoice_totalAmount_"+$(this).attr("id")).val());
		}
	});
	$("#invoiceForm").find("#amount").val(selectPrice.toFixed(2));
}

function selectObj(obj, num) {
	//红蓝字
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	}
	var id = $(obj).attr("id");
	// -------------录票数量------------------------------------------------------
	invoice = $("#invoice_num_" + id).val().trim();
	// 已勾选项，不允许为空
	if (invoice.length == 0 || invoice == 0) {
		$("#invoice_num_" + id).addClass("errorbor");
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#invoice_num_" + id).focus();
			layer.close(lay);
		});
		$(obj).prop("checked",false);
		return false;
	}
	if (str == "2-1") {// 蓝字有效
		// 验证数字是否合法
		if (!reg.test(invoice)) {
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 本次录票数量超出最大限制
		if (Number(invoice) > Number($("#max_num_" + id).val())) {//
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
	} else {// 非蓝字有效
		if (!reg.test(invoice)) {
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("只允许输入负数，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 非蓝字有效，只允许输入负数
		if (Number(invoice) >= 0) {
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("只允许输入负数，小数点后只允许保留两位！", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 无已录入信息，不允许录入选择非蓝字有效
		if (Number($("#in_num_" + id).val()) == 0 && Number(invoice) < 0) {
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("已开票数量不符，请验证！", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 本次录票数量（负数）绝对值大于已录入数量
		if (Number(invoice) + Number($("#in_num_" + id).val()) < 0) {
			$("#invoice_num_" + id).addClass("errorbor");
			layer.alert("（本次录票数量+已录票数量）不得小于零！", {
				icon : 2
			}, function(lay) {
				$("#invoice_num_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
	}
	// --------本次录票总金额-----------------------------------------------
	invoice = $("#invoice_totalAmount_" + id).val().trim();
	if (invoice.length == 0 || invoice == 0) {
		$("#invoice_totalAmount_" + id).addClass("errorbor");
		layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
			icon : 2
		}, function(lay) {
			$("#invoice_totalAmount_" + id).focus();
			layer.close(lay);
		});
		$(obj).prop("checked",false);
		return false;
	}

	if (str == "2-1") {// 蓝字有效
		// 验证数字是否合法
		if (!reg.test(invoice)) {
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 本次录票金额超出最大限制
		if (Number(invoice) > Number($("#max_price_" + id).val())) {//
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
	} else {// 非蓝字有效
		if (!reg.test(invoice)) {
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("只允许输入负数，小数点后只允许保留两位", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 非蓝字有效，只允许输入负数
		if (Number(invoice) >= 0) {
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("只允许输入负数，小数点后只允许保留两位！", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 无已录入信息，不允许录入选择非蓝字有效
		if (Number($("#in_price_" + id).val()) == 0 && Number(invoice) < 0) {
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("已开票金额不符，请验证！", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
		// 本次录票数量（负数）绝对值大于已录入数量
		if (Number(invoice) + Number($("#in_price_" + id).val()) < 0) {
			$("#invoice_totalAmount_" + id).addClass("errorbor");
			layer.alert("（本次录票金额+已录票金额）不得小于零！", {
				icon : 2
			}, function(lay) {
				$("#invoice_totalAmount_" + id).focus();
				layer.close(lay);
			});
			$(obj).prop("checked",false);
			return false;
		}
	}

	if ($(obj).is(":checked")) {
		$("#amount").val((Number($("#amount").val()) + Number($("#invoice_totalAmount_" + num).val())).toFixed(2));
		// 判断是否需要全选
		var n = 0;
		$("#saleorderInfo").find("input[type='checkbox'][name='checkName']").each(function() {
			if ($(this).is(":checked")) {
				n++;
			} else {
				return false;
			}
		});
		if ($("#saleorderInfo").find("input[type='checkbox'][name='checkName']").length == n) {
			$("#saleorderInfo").find("input[type='checkbox'][name='checkAllOpt']").prop("checked", true);
		}
	} else {
		$("#amount").val((Number($("#amount").val()) - Number($("#invoice_totalAmount_" + num).val())).toFixed(2));
		//取消全选
		$("#saleorderInfo").find("input[type='checkbox'][name='checkAllOpt']").prop("checked", false);
	}
}

function checkAllOpt(obj){
	var totalAmount = 0.00;var flag = true;
	if($(obj).is(":checked")){
		//红蓝字
		var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
		if(str == "2-1"){//蓝字有效
			reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		}else{
			reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
		}
		$("#saleorderInfo").find("input[type='checkbox'][name='checkName']").each(function(){
			id = $(this).attr("id");
			//-------------录票数量------------------------------------------------------
			invoice = $("#invoice_num_"+id).val().trim();
			//已勾选项，不允许为空
			if(invoice.length == 0 || invoice == 0){
				$("#invoice_num_"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
					icon : 2
				}, function(lay) {
					$("#invoice_num_"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				if(!reg.test(invoice)){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量超出最大限制
				if(Number(invoice) > Number($("#max_num_"+id).val())){//
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}else{//非蓝字有效
				if(!reg.test(invoice)){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(Number(invoice) >= 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留两位！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#in_num_"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("已开票数量不符，，请验证！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量（负数）绝对值大于已录入数量
				if(Number(invoice) + Number($("#in_num_"+id).val()) < 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得小于零！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}
			//--------本次录票总金额-----------------------------------------------
			invoice = $("#invoice_totalAmount_"+id).val().trim();
			if(invoice.length == 0 || invoice == 0){
				$("#invoice_totalAmount_"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
					icon : 2
				}, function(lay) {
					$("#invoice_totalAmount_"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				if(!reg.test(invoice)){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票金额超出最大限制
				if(Number(invoice) > Number($("#max_price_"+id).val())){//
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}else{//非蓝字有效
				if(!reg.test(invoice)){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(Number(invoice) >= 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留两位！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#in_price_"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("已开票金额不符，请验证！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量（负数）绝对值大于已录入数量
				if(Number(invoice) + Number($("#in_price_"+id).val()) < 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("（本次录票金额+已录票金额）不得小于零！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}
			
			$(this).prop("checked",true);
			totalAmount += Number($("#invoice_totalAmount_"+$(this).attr("id")).val());
		});
		if(flag == false){
			$(this).prop("checked",false);
		}
	}else{
		$("#saleorderInfo").find("input[type='checkbox'][name='checkName']").each(function(){
			$(this).prop("checked",false);
		});
	}
	if(flag == false){
		$(obj).prop("checked",false);
	}
	if(isNaN(totalAmount)){
		$("#amount").val(0.00);
	}else{
		$("#amount").val(totalAmount.toFixed(2));
	}
	return false;
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

// add by Tomcat.Hui 2019/11/22 13:31 .Desc:VDERP-1325 分批开票 发票代码 . start
function vailInvoiceCode(obj){
	clear2ErroeMes();
	var invoiceCode = $(obj).val().trim();
	if(invoiceCode.length == 0){
		warn2Tips("invoiceCode","发票代码不允许为空");
		return false;
	}else{
		var reg = /^[0-9]*$/;
		if(!reg.test(invoiceCode)){
			warn2Tips("invoiceCode","请输入数字发票代码");
			return false;
		}
	}
}
// add by Tomcat.Hui 2019/11/22 13:31 .Desc:VDERP-1325 分批开票 发票代码 . end


function addSaleInvoice(){
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

	// add by Tomcat.Hui 2019/11/22 13:31 .Desc:VDERP-1325 分批开票 发票代码 . start
	var invoiceCode = $("#invoiceForm").find("#invoiceCode").val().trim();
	if(invoiceCode.length == 0){
		warn2Tips("invoiceCode","发票代码不允许为空");
		return false;
	}else{
		var reg = /^[0-9]*$/;
		if(!reg.test(invoiceCode)){
			warn2Tips("invoiceCode","请输入数字发票代码");
			return false;
		}
	}
	// add by Tomcat.Hui 2019/11/22 13:31 .Desc:VDERP-1325 分批开票 发票代码 . end
	
	var detailGoodsIdArr = [];var priceArr = [];var numArr = [];var totalAmountArr = [];var relatedIdArr = [];
	var id = "";var invoice;
	
	//红蓝字
	var str = $("#invoiceForm").find("input[type='radio'][name='invoiceColor']:checked").val();
	$("#colorType").val(str.split("-")[0]);
	$("#isEnable").val(str.split("-")[1]);
	if(str == "2-1"){//蓝字有效
		reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	}else{
		reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
	}
	var flag = true;
	if($("#saleorderInfo").find("input[type='checkbox'][name='checkName']:checked").length>0){
		$("#saleorderInfo").find("input[type='checkbox'][name='checkName']:checked").each(function(){
			id = $(this).attr("id");
			//-------------录票数量------------------------------------------------------
			invoice = $("#invoice_num_"+id).val().trim();
			//已勾选项，不允许为空
			if(invoice.length == 0 || invoice == 0 || invoice.length > 15){
				$("#invoice_num_"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位，最大允许15位", {
					icon : 2
				}, function(lay) {
					$("#invoice_num_"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				if(!reg.test(invoice)){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量超出最大限制
				if(Number(invoice) > Number($("#max_num_"+id).val())){//
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}else{//非蓝字有效
				if(!reg.test(invoice)){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(Number(invoice) >= 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留两位！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#in_num_"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("已开票数量不符，，请验证！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量（负数）绝对值大于已录入数量
				alert(Number($("#max_num_"+id).val()));
				if(Number(invoice) + Number($("#max_num_"+id).val()) < 0){
					$("#invoice_num_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得小于零！", {
						icon : 2
					}, function(lay) {
						$("#invoice_num_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}
			numArr.push(invoice);
			
			
			detailGoodsIdArr.push($(this).val());
			relatedIdArr.push($("#relatedId"+id).val());
			priceArr.push($("#invoice_price_"+id).html().trim());
			
			
			//--------本次录票总金额-----------------------------------------------
			invoice = $("#invoice_totalAmount_"+id).val().trim();
			//已勾选项，不允许为空
			if(invoice.length == 0 || invoice == 0){
				$("#invoice_totalAmount_"+id).addClass("errorbor");
				layer.alert("只允许输入大于零的数字，小数点后只允许保留两位，最大允许15位", {
					icon : 2
				}, function(lay) {
					$("#invoice_totalAmount_"+id).focus();
					layer.close(lay);
				});
				flag = false;
				return flag;
			}
			
			if(str == "2-1"){//蓝字有效
				//验证数字是否合法
				if(!reg.test(invoice)){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票金额超出最大限制
				if(Number(invoice) > Number($("#max_price_"+id).val())){//
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("（本次录票数量+已录票数量）不得大于产品数量！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}else{//非蓝字有效
				if(!reg.test(invoice)){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入大于零的数字，小数点后只允许保留两位", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//非蓝字有效，只允许输入负数
				if(Number(invoice) >= 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("只允许输入负数，小数点后只允许保留两位！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//无已录入信息，不允许录入选择非蓝字有效
				if(Number($("#in_price_"+id).val())==0 && Number(invoice) < 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("已开票金额不符，请验证！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
				//本次录票数量（负数）绝对值大于已录入数量
				if(Number(invoice) + Number($("#max_price_"+id).val()) < 0){
					$("#invoice_totalAmount_"+id).addClass("errorbor");
					layer.alert("（本次录票金额+已录票金额）不得小于零！", {
						icon : 2
					}, function(lay) {
						$("#invoice_totalAmount_"+id).focus();
						layer.close(lay);
					});
					flag = false;
					return flag;
				}
			}
			totalAmountArr.push(invoice);
		});
		if(flag==false){
			return false;
		}
	}else{
		layer.alert("产品信息未选择无法提交！", {icon : 2});
		return false;
	}
	if(relatedIdArr.length <= 0 || $("#invoiceForm").find("#amount").val()==0){
		layer.alert("参数错误:未选择数据", {icon : 2});
		return false;
	}
	$("#button_id").attr('disabled',true);
	$("#invoiceForm #hideValue").html("");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='relatedIdArr' value='"+relatedIdArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='detailGoodsIdArr' value='"+detailGoodsIdArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoicePriceArr' value='"+priceArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoiceNumArr' value='"+numArr+"'>");
	$("#invoiceForm #hideValue").append("<input type='hidden' name='invoiceTotleAmountArr' value='"+totalAmountArr+"'>");
	//$("#invoiceForm").submit();
	// $("#close-layer").click();
	//window.location.reload();
	//return false;
	$.ajax({
		async:false,
		url:'./saveSaleorderInvoice.do',
		data:$("#invoiceForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				$("#close-layer").click();
				var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
				parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
				parent.location.reload();
				return false;
			}else{
				layer.alert(data.message,{icon:2});
				warn2Tips("errorTitle",data.message);//文本框ID和提示用语
			}
		}
	})
}