$(function(){
	//pagesContrlpages(false,false,true);
	var uri = $("input[name='uri']").val();
	var buyorderId = $("input[name='buyorderId']").val();
	var	url = uri+"?&buyorderId="+buyorderId+'&viewType=3';
//	var frameObj = window.frameElement;
//	if(frameObj.src.indexOf("buyorderId")<0){
//		frameObj.src = url;
//	}
//	var	url = page_url + '/order/quote/getQuoteDetail.do?quoteorderId='+$("#quoteorderId").val()+'&viewType=3';
	if($(window.frameElement).attr('src').indexOf("viewType=3")<0){
		$(window.frameElement).attr('data-url',url);
	}
	var allMoney = Number($("#zMoney").html());
	var pay = $("#paymentType").val();
	if(pay == 419){
		$("#prepaidAmount").val(Number(allMoney).toFixed(2));
	}else if(pay == 420){
		var money = (Number(allMoney)*Number(0.8)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 421){
		var money = (Number(allMoney)*Number(0.5)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 422){
		var money = (Number(allMoney)*Number(0.3)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 423){
		$("#prepaidAmount").val(0);
		$("#accountPeriodAmount").val((Number(allMoney)).toFixed(2));
	}
	
	$("#sub").click(function(){
		checkLogin();
		if($("#traderId").val()==0){
			$("#searchTraderName").parent("div").siblings("div").removeClass("none").html("未关联供应商");
			$("#searchTraderName").addClass("errorbor");
			return false;
		}else{
			$("#searchTraderName").parent("div").siblings("div").addClass("none");
			$("#searchTraderName").removeClass("errorbor");
		}
		if($("#traderContactId").val()==''){
			$("#traderContactId").parent("div").siblings("div").removeClass("none");
			$("#traderContactId").addClass("errorbor");
			return false;
		}else{
			$("#traderContactId").parent("div").siblings("div").addClass("none");
			$("#traderContactId").removeClass("errorbor");
		}
		if($("#traderAddressId").val()==''){
			$("#traderAddressId").parent("div").siblings("div").removeClass("none");
			$("#traderAddressId").addClass("errorbor");
			return false;
		}else{
			$("#traderAddressId").parent("div").siblings("div").addClass("none");
			$("#traderAddressId").removeClass("errorbor");
		}
		var numReg = /^([1]?\d{1,10})$/;
		var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		var flag1 =true;
		var flag2 =true;
		var flag3 =true;
		var flag6 =true;
		$.each($("input[name='xprice']"),function(i,n){
			var goodsId = $(this).attr("altTotal");
			var price = $(this).val();
			if(price ==''||price == undefined){
				$("input[alt='"+goodsId+"']").addClass("errorbor");
				flag1 = false;
				return false;
			}
			if(price.length > 0&&!priceReg.test(price)){
				$("input[alt='"+goodsId+"']").addClass("errorbor");
				flag2 = false;
				return false;
			}
			if(Number(price)>300000000){
				$("input[alt='"+goodsId+"']").addClass("errorbor");
				flag3 = false;
				return false;
			}
			var oneAllAmount = $("span[altGood = '"+goodsId+"']").html();
			if(Number(oneAllAmount)>300000000){
				$("input[alt='"+goodsId+"']").addClass("errorbor");
				flag6 = false;
				return false;
			}
		});
		if(!flag1){
			layer.alert("单价不允许为空");
			return false;
		}
		if(!flag2){
			layer.alert("单价输入错误！仅允许使用数字，最多精确到小数点后两位");
			return false;
		}
		if(!flag3){
			layer.alert("单价不允许超过三亿");
			return false;
		}
		if(!flag6){
			layer.alert("单个商品总价不允许超过三亿");
			return false;
		}
		var flag4=true;
		var flag5=true;
		var sum = Number(0);
		$.each($("input[name='saleorderGoodsNum']"),function(i,n){
			var goodsId = $(this).attr("alt1");
			var num = $(this).val();
			if(num ==''||num == undefined ||num ==0){
				$("input[alt1='"+goodsId+"']").addClass("errorbor");
				flag4 = false;
				return false;
			}
			if(!numReg.test(num)){
				$("input[alt1='"+goodsId+"']").addClass("errorbor");
				flag5 = false;
				return false;
			}
			sum += Number(num);
		});
		if(!flag4){
			layer.alert("数量不允许为空");
			return false;
		}
		if(!flag5){
			layer.alert("数量必须为大于等于1的正整数");
			return false;
		}
		if(sum==Number(0)){
			layer.alert("订单中应至少有一个商品数量大于0");
			return false;
		}
		var flag6=true;
		$.each($("textarea[name='insideCommentsDispaly']"),function(i,n){
			var val = $(this).val();
			if(val !=''&& val.length>512){
				flag6 = false;
				return false;
			}
		})
		if(!flag6){
			layer.alert("采购备注不允许超过512个字符");
			return false;
		}
		
		//货期货期（天） ： 最多输入10个字符；
		var flag611=true;
		$.each($("input[name='deliveryCycleDispaly']"),function(i,n){
			var val = $(this).val();
			if(val !=''&& val.length>10){
				flag611 = false;
				return false;
			}
		})
		if(!flag611){
			layer.alert("货期不允许超过10个字符");
			return false;
		}
		
		//安调信息： 最多输入20个字符；
		var flag622=true;
		$.each($("textarea[name='installationDispaly']"),function(i,n){
			var val = $(this).val();
			if(val !=''&& val.length>20){
				flag622 = false;
				return false;
			}
		})
		if(!flag622){
			layer.alert("安调信息不允许超过20个字符");
			return false;
		}
		
		//“内部备注”最多输入60个字符
		var flag633=true;
		$.each($("textarea[name='goodsCommentsDispaly']"),function(i,n){
			var val = $(this).val();
			if(val !=''&& val.length>60){
				flag633 = false;
				return false;
			}
		})
		if(!flag633){
			layer.alert("内部备注不允许超过60个字符");
			return false;
		}
		
		var prepaidAmount = $("#prepaidAmount").val();
		if(prepaidAmount.length > 14){
			warnErrorTips("prepaidAmount","prepaidAmountError","预付金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		if(prepaidAmount!=""){
			if(!priceReg.test(prepaidAmount)){
				warnErrorTips("prepaidAmount","prepaidAmountError","预付金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
		}else{
			warnErrorTips("prepaidAmount","prepaidAmountError","预付金额必须大于0");//文本框ID和提示用语
			return false;
		}
		var accountPeriodAmount = $("#accountPeriodAmount").val();
		var periodBalance = $("#periodBalance").val();
		if($("#paymentType").val()!=419&& accountPeriodAmount!=''&&!priceReg.test(accountPeriodAmount)){
			warnErrorTips("accountPeriodAmount","accountPeriodAmountError","账期支付输入错误！仅允许使用数字，最多精确到小数点后两位");
			return false;
		}else if($("#paymentType").val()!=419&& accountPeriodAmount!=''&& Number(accountPeriodAmount) > Number(periodBalance)){
			warnErrorTips("accountPeriodAmount","accountPeriodAmountError","账期余额不足");
			$("#accountPeriodAmount").val("0");
			return false;
		}
		if(accountPeriodAmount != "" && accountPeriodAmount != undefined && accountPeriodAmount != 0){
			$("#haveAccountPeriod").val(1);
		}else{
			$("#haveAccountPeriod").val(0);
		}
		var retainageAmount = $("#retainageAmount").val();
		if($("#paymentType").val()==424){
			if(retainageAmount.length > 14){
				warnErrorTips("retainageAmount","retainageAmountError","尾款输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
				return false;
			}
			if(retainageAmount!=""){
				if(!priceReg.test(retainageAmount)){
					warnErrorTips("retainageAmount","retainageAmountError","尾款输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
					return false;
				}
				if(Number(retainageAmount)<=0){
					warnErrorTips("retainageAmount","retainageAmountError","尾款必须大于0");//文本框ID和提示用语
					return false;
				}
			}else{
				warnErrorTips("retainageAmount","retainageAmountError","尾款必须大于0");//文本框ID和提示用语
				return false;
			}
			var retainageAmountMonth = $("#retainageAmountMonth").val();
			if(retainageAmountMonth.length>0){
				var re = /^[0-9]+$/ ;
				if(retainageAmountMonth=="0" || !re.test(retainageAmountMonth)){
					warnErrorTips("retainageAmountMonth","retainageAmountError","数量必须为大于0的正整数");//文本框ID和提示用语
					return false;
				}else if(Number(retainageAmountMonth) > 24){
					warnErrorTips("retainageAmountMonth","retainageAmountError","尾款期限不允许超过24个月");//文本框ID和提示用语
					return false;
				}
			}
			if(prepaidAmount!="" && accountPeriodAmount!="" && retainageAmount!=""){
				var goodsTotleMoney = $("#zMoney").html();
				if(Number(prepaidAmount) + Number(accountPeriodAmount) + Number(retainageAmount) != Number(goodsTotleMoney)){
					warnErrorTips("retainageAmount","pay","支付金额总额与总金额不符，请验证");//文本框ID和提示用语
					return false;
				}
			}
		}
		if($("#paymentComments").val()!='' && $("#paymentComments").val().length>256){
			warnErrorTips("paymentComments","paymentCommentsError","付款备注不允许超过256个字符");
			return false;
		}
		if($("#invoiceComments").val()!='' && $("#invoiceComments").val().length>256){
			warnErrorTips("invoiceComments","invoiceCommentsError","收票备注不允许超过256个字符");
			return false;
		}
		if($("#logisticsComments").val()!='' && $("#logisticsComments").val().length>256){
			warnErrorTips("logisticsComments","logisticsCommentsError","物流备注不允许超过256个字符");
			return false;
		}
		if($("#additionalClause").val()!='' && $("#additionalClause").val().length>256){
			warnErrorTips("additionalClause","additionalClauseError","补充条款不允许超过256个字符");
			return false;
		}
		if($("#comments").val()!='' && $("#comments").val().length>256){
			warnErrorTips("comments","commentsError","内部备注不允许超过256个字符");
			return false;
		}
		if($("#estimateArrivalTime").val()!='' && $("#estimateArrivalTime").val().length>32){
			warnErrorTips("estimateArrivalTime","estimateArrivalTimeError","预计到货时间不允许超过32个字符");
			return false;
		}
		var paymentType = $("select[name='paymentType']").val();
		var fk = Number(0);
		if(paymentType == 419){
			fk = Number(prepaidAmount);
			$("#accountPeriodAmount").val(0);
			$("#retainageAmount").val(0);
		}else if(paymentType == 420 || paymentType == 421 || paymentType == 422 || paymentType == 423){
			fk = Number(prepaidAmount) + Number(accountPeriodAmount);
			$("#retainageAmount").val(0);
		}else if(paymentType == 424){
			fk = Number(prepaidAmount) + Number(accountPeriodAmount) + Number(retainageAmount);
		}
		if(fk > Number($("#zMoney").html())){
			layer.confirm("付款金额大于订单金额请确认！", {
				  btn: ['确定','取消'] //按钮
				}, function(){
					$("#myform").submit();
				}, function(){
			});
		}else if(fk == Number($("#zMoney").html())){
			$("#myform").submit();
		}else{
			layer.alert("付款金额小于订单金额请确认！");
			return false;
		}
		
	})
	
	$("#traderContactId").change(function(){
		if($("#traderContactId").hasClass("errorbor")){
			$("#traderContactId").parent("div").siblings("div").addClass("none");
			$("#traderContactId").removeClass("errorbor");
		}
	})
	
	$("#traderAddressId").change(function(){
		if($("#traderAddressId").hasClass("errorbor")){
			$("#traderAddressId").parent("div").siblings("div").addClass("none");
			$("#traderAddressId").removeClass("errorbor");
		}
	})
	
})

function changPrice(obj,buyorderGoodsId,goodsId){
	checkLogin();
	var price = $(obj).val();
	var goodsId = $(obj).attr("altTotal");
	var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(price ==''){
		$("input[alt='"+goodsId+"']").addClass("errorbor");
		layer.alert("单价不允许为空");
		return false;
	}
	if(price !='' && ( price.length > 13 || !priceReg.test(price) )){
		$("input[alt='"+goodsId+"']").addClass("errorbor");
		layer.alert("单价输入错误！仅允许使用数字，最多精确到小数点后两位");
		return false;
	}
	if(Number(price)>300000000){
		$("input[alt='"+goodsId+"']").addClass("errorbor");
		layer.alert("单价不允许超过三亿");
		return false;
	}

	$(obj).siblings().val(buyorderGoodsId+"|"+price);
	
	var num = $("span[altTotal='"+goodsId+"']").html();
	$("span[alt='"+buyorderGoodsId+"']").html((Number(num)*Number(price)).toFixed(2));
	if((Number(num)*Number(price)) > 300000000){
		layer.alert("单个商品总价不允许超过三亿");
		return false;
	}
	
	//计算总金额
	var allMoney = Number(0);
	$.each($(".oneAllMoney"),function(i,n){
		allMoney += Number($(this).html());
	});
	$("#zMoney").html(allMoney.toFixed(2));
	var pay = $("#paymentType").val();
	if(pay == 419){
		$("#prepaidAmount").val(Number(allMoney).toFixed(2));
	}else if(pay == 420){
		var money = (Number(allMoney)*Number(0.8)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 421){
		var money = (Number(allMoney)*Number(0.5)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 422){
		var money = (Number(allMoney)*Number(0.3)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 423){
		$("#prepaidAmount").val(0);
		$("#accountPeriodAmount").val((Number(allMoney)).toFixed(2));
	}
	
}

function changComments(obj,buyorderGoodsId){
	
	checkLogin();
	var insideComments = $(obj).val();
	if(insideComments != undefined){
		$(obj).siblings().val(buyorderGoodsId+"|"+insideComments);
	}
	
}

function addNum(obj,num,buyorderGoodsId,saleorderGoodsId){
	checkLogin();
	var srnum = $(obj).val();
	var goodsId = $(obj).attr("alt1");
	var numReg = /^([1]?\d{1,10})$/;
	if(srnum==''){
		$("input[alt1='"+goodsId+"']").addClass("errorbor");
		layer.alert("数量不允许为空")
		return false;
	}
	if(srnum !='' && !numReg.test(srnum)){
		$("input[alt1='"+goodsId+"']").addClass("errorbor");
		layer.alert("数量必须为正整数且小于11位数字");
		return false;
	}
	if(Number(srnum)<1 || Number(srnum) > Number(num)){
		$("input[alt1='"+goodsId+"']").addClass("errorbor");
		layer.alert("数量必须大于等于1且小于等于"+num);
		$(obj).val(0);
		return false;
	}
	$(obj).siblings("input").val(buyorderGoodsId+"|"+saleorderGoodsId+"|"+srnum);
	var sum =Number(0);
	var goodsId = $(obj).attr("alt1");	
	$.each($("input[alt1='"+goodsId+"']"),function(i,n){
		sum += parseInt($(this).val());
	});
	$("span[altTotal='"+goodsId+"']").html(sum);
	$("span[altTotal='"+goodsId+"']").siblings("input").val(buyorderGoodsId+"|"+sum);
	//计算总件数
	var zSum = Number(0);
	$.each($(".buySum"),function(i,n){
		zSum += parseInt($(this).html());
	});
	$("#zSum").html(zSum);
	//计算单个总额
	var price = $("input[altTotal='"+goodsId+"']").val();
	if(price != undefined && price !=""){
		$("span[alt='"+buyorderGoodsId+"']").html((Number(sum)*Number(price)).toFixed(2));
	}
	if((Number(num)*Number(price)) > 300000000){
		layer.alert("单个商品总价不允许超过三亿");
		return false;
	}
	//计算总金额
	var allMoney = Number(0);
	$.each($(".oneAllMoney"),function(i,n){
		allMoney += Number($(this).html());
	});
	$("#zMoney").html(allMoney.toFixed(2));
	var pay = $("#paymentType").val();
	if(pay == 419){
		$("#prepaidAmount").val(Number(allMoney).toFixed(2));
	}else if(pay == 420){
		var money = (Number(allMoney)*Number(0.8)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 421){
		var money = (Number(allMoney)*Number(0.5)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 422){
		var money = (Number(allMoney)*Number(0.3)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
	}else if(pay == 423){
		$("#prepaidAmount").val(0);
		$("#accountPeriodAmount").val((Number(allMoney)).toFixed(2));
	}
	
}


function searchSupplier(){
	checkLogin();
	var searchTraderName = $("#searchTraderName").val()==undefined?"":$("#searchTraderName").val();
	
	if($("#searchTraderName").val()==''){
		$("#searchTraderName").parent("div").siblings("div").removeClass("none").html("查询条件不允许为空");
		$("#searchTraderName").addClass("errorbor");
		return false;
	}else{
		$("#searchTraderName").parent("div").siblings("div").addClass("none");
		$("#searchTraderName").removeClass("errorbor");
	}
	var searchUrl = page_url+"/order/buyorder/getSupplierByName.do?supplierName="+encodeURI(searchTraderName);
	$("#popSupplier").attr('layerParams','{"width":"800px","height":"500px","title":"搜索供应商","link":"'+searchUrl+'"}');
	$("#popSupplier").click();
	
}
function payment(obj){
	checkLogin();
	var pay = $(obj).val();
	var zonMoney = $("#zMoney").html();
	$("#prepaidAmount").val('0.00');
	$("#accountPeriodAmount").val('0.00');
	$("#retainageAmount").val('0.00');
	$("#retainageAmountMonth").val('');
	if(pay == 419){
		$("#prepaidAmount").val(Number(zonMoney).toFixed(2));
		$("#accountPeriodLi").hide();
		$("#retainageLi").hide();
	}else if(pay == 420){
		var money = (Number(zonMoney)*Number(0.8)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(zonMoney)-Number(money)).toFixed(2));
		$("#prepaidAmount").attr("readonly",true);
		$("#accountPeriodAmount").attr("readonly",true);
		$("#accountPeriodLi").show();
		$("#retainageLi").hide();
	}else if(pay == 421){
		var money = (Number(zonMoney)*Number(0.5)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(zonMoney)-Number(money)).toFixed(2));
		$("#prepaidAmount").attr("readonly",true);
		$("#accountPeriodAmount").attr("readonly",true);
		$("#accountPeriodLi").show();
		$("#retainageLi").hide();
	}else if(pay == 422){
		var money = (Number(zonMoney)*Number(0.3)).toFixed(2);
		$("#prepaidAmount").val(money);
		$("#accountPeriodAmount").val((Number(zonMoney)-Number(money)).toFixed(2));
		$("#prepaidAmount").attr("readonly",true);
		$("#accountPeriodAmount").attr("readonly",true);
		$("#accountPeriodLi").show();
		$("#retainageLi").hide();
	}else if(pay == 423){
		$("#prepaidAmount").attr("readonly",false);
		$("#accountPeriodAmount").attr("readonly",false);
		$("#accountPeriodAmount").val(Number(zonMoney));
		$("#prepaidAmount").attr("readonly",true);
		$("#prepaidAmount").val(0);
		$("#accountPeriodLi").show();
		$("#retainageLi").hide();
	}else if(pay == 424){
		$("#prepaidAmount").removeAttr("readonly");
		$("#accountPeriodAmount").removeAttr("readonly");
		$("#accountPeriodLi").show();
		$("#retainageLi").show();
		$("#prepaidAmount").val('0.00');
		$("#accountPeriodAmount").val('0.00');
		$("#retainageAmount").val('0.00');
		$("#retainageAmountMonth").val('');
	}
	
}


function changeTraderId(obj){
	checkLogin();
	var traderId = $("#traderId").val();
	$.ajax({
		url:page_url+'/order/buyorder/getContactsAddress.do',
		data:{"traderId":traderId},
		type:"POST",
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code ==0){
				$option1 = "<option value='0'>请选择</option>";
				$.each(data.data,function(i,n){
					$option1 += "<option value='"+data.data[i]['traderContactId']+"'>"+data.data[i]['name']+"</option>";
				});
				$("select[name='traderContactId'] option:gt(0)").remove();
				$("select[name='traderContactId'] option:gt(0)").html($option1);
				
				$option = "<option value='0'>请选择</option>";
				$.each(data.listData,function(i,n){
					$option += "<option value='"+data.listData[i]['traderAddress']['traderAddressId']+"'>"
								+data.listData[i]['area']+data.listData[i]['traderAddress']['address']+"</option>";
				});
				$("select[name='traderAddressId'] option:gt(0)").remove();
				$("select[name='traderAddressId'] option:gt(0)").html($option);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}

function research(){
	checkLogin();
	$("#searchTraderName").val("");
	$("#searchTraderName").show();
	$("#name").addClass("none");
	$("#errorMes").removeClass("none");
	$("#research").addClass("none");
}

function delBuyorderGoods(buyorderGoodsId){
	checkLogin();
	index = layer.confirm("您是否确认删除？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("table[altTable='"+buyorderGoodsId+"']").remove();
			//计算总件数
			var zSum = Number(0);
			$.each($(".buySum"),function(i,n){
				zSum += parseInt($(this).html());
			});
			$("#zSum").html(zSum);
			//计算总金额
			var allMoney = Number(0);
			$.each($(".oneAllMoney"),function(i,n){
				allMoney += Number($(this).html());
			});
			$("#zMoney").html(allMoney.toFixed(2));
			var pay = $("#paymentType").val();
			if(pay == 419){
				$("#prepaidAmount").val(Number(allMoney).toFixed(2));
			}else if(pay == 420){
				var money = (Number(allMoney)*Number(0.8)).toFixed(2);
				$("#prepaidAmount").val(money);
				$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
			}else if(pay == 421){
				var money = (Number(allMoney)*Number(0.5)).toFixed(2);
				$("#prepaidAmount").val(money);
				$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
			}else if(pay == 422){
				var money = (Number(allMoney)*Number(0.3)).toFixed(2);
				$("#prepaidAmount").val(money);
				$("#accountPeriodAmount").val((Number(allMoney)-Number(money)).toFixed(2));
			}else if(pay == 423){
				$("#prepaidAmount").val(0);
				$("#accountPeriodAmount").val((Number(allMoney)).toFixed(2));
			}
			var buyorderGoodsIds = $("input[name = 'delBuyGoodsIds']").val();
			if(buyorderGoodsIds == ''){
				buyorderGoodsIds = buyorderGoodsId + ",";
			}else{
				buyorderGoodsIds += buyorderGoodsId + ",";
			}
			$("input[name = 'delBuyGoodsIds']").val(buyorderGoodsIds);
			layer.close(index);
		}, function(){
		});
}

