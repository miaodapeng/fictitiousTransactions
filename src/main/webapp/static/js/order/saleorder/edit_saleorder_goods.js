$(function() {
	/**
	 * 耗材订单需要判断是否能够更改价格
	 * @type {Window.art.fn.init|jQuery|HTMLElement}
	 */
	var orderType = $("#orderType").val();
	if (orderType == 5){
		var isCoupons = $("#isCoupons").val();
		var actionId = $("#actionId").val();
		var sku = $("#sku").val();
		var paymentStatus = $("#paymentStatus").val();
		if (sku == 'V127063'){
			$("#priceMsg").html("运费商品无法修改价格");
			$("#price").attr("disabled",true);
		} else if (isCoupons == 1 || actionId != 0){
			$("#priceMsg").html("订单已使用优惠，无法修改价格");
			$("#price").attr("disabled",true);
		} else if (paymentStatus != 0){
			$("#priceMsg").html("订单已收款，无法修改价格");
			$("#price").attr("disabled",true);
		}
	}


});

function confirmTotalMoney(str){
	checkLogin();
	clearErroeMes();
	var $form = $("#confirmGoodsDiv");
	$form.find("#confirmTotalMoney").html("");
	
	var flag = false;
	
	var price = $form.find("#price").val().trim();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	
	var num = $form.find("#num").val().trim();
	var re = /^[0-9]+$/;
	
	if(str=="price"){
		if(price.length>0 && !reg.test(price)){
			warnTips("priceError","单价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(price)>300000000){
			warnTips("priceError","产品单价不允许超过三亿");//文本框ID和提示用语
			return false;
		}
		if(num.length!=0 && num!="0" && re.test(num)){
			flag = true;
		}
	}else{
		if (num.length==0) {
			warnTips("num","产品数量不允许为空");//文本框ID和提示用语
			return false;
		}else{
			if(num=="0" || !re.test(num)){
				warnTips("num","产品数量必须为大于0的整数");//文本框ID和提示用语
				return false;
			}
		}
		if(Number(num)>100000000){
			warnTips("num","产品数量不允许超过一亿");//文本框ID和提示用语
			return false;
		}
		if(price.length>0 && reg.test(price)){
			flag = true;
		}
	}
	if((Number(num)*Number(price)) > 300000000){
		warnTips("num","产品总金额不允许超过三亿");//文本框ID和提示用语
		return false;
	}
	
	if(flag){
		if (num != undefined && num != "" && price != undefined && price != "") {
			/*var f = Number(num) * Number(price);
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + 2) {
				s += '0';
			}*/
			$form.find("#confirmTotalMoney").html((Number(num) * Number(price)).toFixed(2));
		}
	}
	return true;
}

function confirmSubmit(){
	checkLogin();
	var $form = $("#confirmGoodsDiv");

	clearErroeMes();
	
	$("#haveInstallation").val($("input[name='installation']:checked").val());

	var price = $("#price").val().trim();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(price.length>0 && !reg.test(price)){
		warnTips("price","单价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}else if(Number(price)>300000000){
		warnTips("price","产品单价不允许超过三亿");//文本框ID和提示用语
		return false;
	}
	
	if($("#price").val().trim()==""){
		$("#price").val(0);
	}
	
	var num = $("#num").val();
	if (num.length==0) {
		warnTips("num","产品数量不能为空");//文本框ID和提示用语
		return false;
	}else{
		var re = /^[0-9]+$/ ;
		if(!re.test(num)){
			warnTips("num","数量必须为大于0的整数");//文本框ID和提示用语
			return false;
		}
	}
	if(Number(num)>100000000){
		warnTips("num","产品数量不允许超过一亿");//文本框ID和提示用语
		return false;
	}
	if((Number(num)*Number(price)) > 300000000){
		warnTips("num","产品总金额不允许超过三亿");//文本框ID和提示用语
		return false;
	}
	var deliveryCycle = $("#deliveryCycle").val();
	if (deliveryCycle.length!=0 && deliveryCycle.length>20) {
		warnTips("deliveryCycleDiv","货期长度应该在0-20个字符之间");//文本框ID和提示用语
		return false;
	}
	
	$("#deliveryDirect").val($("input[name='deliveryDirectRad']:checked").val());
	if($("#deliveryDirect").val()=="1"){
		var deliveryDirectComments = $form.find("#deliveryDirectComments").val();
		if(deliveryDirectComments.length==0){
			warnTips("deliveryDirectCommentsDiv","直发原因不允许为空");
			return false;
		}
		if(deliveryDirectComments.length<2 || deliveryDirectComments.length>128){
			warnTips("deliveryDirectCommentsDiv","直发原因长度应该在2-128个字符之间");
			return false;
		}
	}
	
	var insideComments = $("#insideComments").val();
	if(insideComments.length>512){
		warnTips("insideComments","内部备注长度应该在0-512个字符之间");
		return false;
	}
	
	var goodsComments = $("#goodsComments").val();
	if(goodsComments.length>512){
		warnTips("goodsComments","产品备注长度应该在0-512个字符之间");
		return false;
	}

	$("#num").removeAttr("disabled");

    /**
     * 若当前订单为耗材订单且订单中的SKU价格修改时 验证订单的付款状态
     */
	if ($("#priceUpdateStatus").val() == 1){
		var saleorderId = $("#saleorderId").val();
		$.ajax({
			url:'./getBaseSaleorder.do?saleorderId=' + saleorderId,
			type:"GET",
			dataType : "json",
			success:function (resultMsg) {
				if (resultMsg.code == 0){
					var paymentStatus = resultMsg.data.paymentStatus;
					if (paymentStatus == 1 || paymentStatus == 2){
						layer.alert('收款状态已更新，无法修改价格',function () {
							$("#close-layer").click();
							window.parent.location.reload();
							return false;
						});
					} else {
						saveOrderGoods();
					}
				} else {
					layer.alert('请求失败');
					return false;
				}
			},
			error:function (data) {
				if (data.status == 1001){
					layer.alert('当前操作无权限');
				}
			}
		})
	} else {
		saveOrderGoods();
	}
}

/**
 * 监控耗材订单SKU价格是否改变从而验证订单付款状态
 */
function changePriceStatus() {
	var orderType = $("#orderType").val();
	if (orderType == 5){
		$("#priceUpdateStatus").val(1);
	}
}

/**
 * 保存修改的订单商品
 * @returns {boolean}
 */
function saveOrderGoods() {
	$.ajax({
		async:false,
		url:'./saveSaleorderGoods.do',
		data:$("#saleorderFormalGoodsForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			layerPFF(null);
			$("#close-layer").click();
//			window.parent.location.reload();
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}