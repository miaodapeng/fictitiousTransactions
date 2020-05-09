function subBtn() {
	checkLogin();
	var $form = $("#quoteFormalGoodsForm");
	
//	$form.submit(function() {
		
		$form.find(":input").parents('li').find('.warning').remove();
		$form.find(":input").removeClass("errorbor");
		
		$("#haveInstallation").val($form.find("input[name='installation']:checked").val());
		$("#deliveryDirect").val($form.find("input[name='deliveryDirectRad']:checked").val());
		var price = $form.find("#price").val().trim();
		
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if(price.length>0 && !reg.test(price)){
			warnTips("priceError","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			$("#price").focus();
			return false;
		}else if(Number(price)>=10000000){
			warnTips("priceError","产品单价不允许超过一千万");//文本框ID和提示用语
			return false;
		}
		
		var num = $("#num").val();
		if (num.length==0) {
			warnTips("num","产品数量不能为空");//文本框ID和提示用语
			return false;
		}else{
			var re = /^[0-9]+$/;
			if(num== "0" || !re.test(num)){
				warnTips("num","产品数量必须为大于0的正整数");//文本框ID和提示用语
				return false;
			}
		}
		if(Number(num)>=1000000){
			warnTips("num","产品数量不允许超过一百万");//文本框ID和提示用语
			return false;
		}
		if((Number(num)*Number(price)) >= 10000000){
			warnTips($form,"num","产品总金额不允许超过一千万");//文本框ID和提示用语
			return false;
		}
		
		var deliveryCycle = $form.find("#deliveryCycle").val();
		if (deliveryCycle.length!=0 && deliveryCycle.length>20) {
			warnTips("deliveryCycleErrMsg","货期长度应该在0-20个字符之间");//文本框ID和提示用语
			return false;
		}
		
		if($form.find("#deliveryDirect").val()=="1"){
			var deliveryDirectComments = $form.find("#deliveryDirectComments").val().trim();
			if(deliveryDirectComments.length==0){
				warnTips("deliveryDirectComments","直发原因不允许为空");
				return false;
			}
			if(deliveryDirectComments.length<2 || deliveryDirectComments.length>128){
				warnTips("deliveryDirectComments","直发原因长度应该在2-128个字符之间");
				return false;
			}
		}
		
		var insideComments = $form.find("#insideComments").val();
		if(insideComments.length>512){
			warnTips("insideComments","内部备注长度应该在0-512个字符之间");
			return false;
		}
		
		var goodsComments = $form.find("#goodsComments").val();
		if(goodsComments.length>512){
			warnTips("goodsComments","产品备注长度应该在0-512个字符之间");
			return false;
		}
		if(parent.$("#quotePayMoneForm").find("#quoteCustomerNature").val() == "465"){//客户为分销
			//终端信息
			var terminalTraderName = "";var terminalTraderId = "";var terminalTraderType ="";var salesArea = "";var salesAreaId = "";
			if(parent.$("#quotePayMoneForm").find("#terminalTraderName") != undefined && parent.$("#quotePayMoneForm").find("#terminalTraderName").val().length > 0){
				terminalTraderName = parent.$("#quotePayMoneForm").find("#terminalTraderName").val();
				terminalTraderId = parent.$("#quotePayMoneForm").find("#terminalTraderId").val();
				terminalTraderType = parent.$("#quotePayMoneForm").find("#terminalTraderType").val();
			}else{
				terminalTraderName = parent.$("#updateTerminalInfo").find("#searchTraderName").val().trim();
			}
			var salesArea = (parent.$("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#province :selected").text()) + " " 
				+ (parent.$("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#city :selected").text()) + " " 
				+ (parent.$("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#zone :selected").text());
				var province = parent.$("#updateTerminalInfo").find("#province :selected").val()=="0"?"":parent.$("#updateTerminalInfo").find("#province :selected").val();
				var city = parent.$("#updateTerminalInfo").find("#city :selected").val();
				var zone = parent.$("#updateTerminalInfo").find("#zone :selected").val();
			$("#quoteFormalGoodsForm").find("#salesAreaId").val((zone=="0"?(city=="0"?province:city):zone));
			
			$("#quoteFormalGoodsForm").find("#terminalTraderName").val(terminalTraderName);
			$("#quoteFormalGoodsForm").find("#terminalTraderId").val(terminalTraderId);
			$("#quoteFormalGoodsForm").find("#terminalTraderType").val(terminalTraderType);
			$("#quoteFormalGoodsForm").find("#salesArea").val(salesArea);
		}
		$.ajax({
			async:false,
			url:'./editQuoteGoods.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){
			},
			success:function(data){
				if(data.code==0){
					parent.$("#paymentType").val(parent.$("#paymentType").val()).change();
//					parent.location.reload();
					layerPFF(null);
					/*layer.alert(data.message, 
							{ icon: 1 },
							function () {
								$('#cancle').click();
								if(parent.layer!=undefined){
									parent.layer.close(index);
								}
								parent.location.reload();
							}
					);*/
				}else{
					layer.alert(data.message);
				}
//				refreshNowPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
//	});
};

function countTotalMoney(str){
	checkLogin();
	clearErroeMes();
	var $form = $("#quoteFormalGoodsForm");
	$form.find("#totalMoney").html("");
	
	var flag = false;
	
	var price = $form.find("#price").val().trim();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	
	var num = $form.find("#num").val().trim();
	var re = /^[0-9]+$/;
	
	if(str=="price"){
		if(price.length>0 && !reg.test(price)){
			warnTips("priceError","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			$("#price").focus();
			return false;
		}else if(Number(price)>=10000000){
			warnTips("priceError","产品单价不允许超过一千万");//文本框ID和提示用语
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
				warnTips("num","产品数量必须为正整数");//文本框ID和提示用语
				return false;
			}
		}
		if(Number(num)>=1000000){
			warnTips("num","产品数量不允许超过一百万");//文本框ID和提示用语
			return false;
		}
		if(price.length>0 && reg.test(price)){
			flag = true;
		}
	}
	if((Number(num)*Number(price)) >= 10000000){
		warnTips("num","产品总金额不允许超过一千万");//文本框ID和提示用语
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
			$form.find("#totalMoney").html((Number(num) * Number(price)).toFixed(2));
		}
	}
	return true;
}