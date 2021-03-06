$(function() {
	
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
		if (price.length == 0) {
			warnTips("priceError","备货价不允许为空");//文本框ID和提示用语
			return false;
		} else if(price.length>0 && !reg.test(price)){
			warnTips("priceError","备货价输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(price)>300000000){
			warnTips("priceError","备货价不允许超过三亿");//文本框ID和提示用语
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
	
	var price = $("#price").val().trim();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if (price.length == 0) {
		warnTips("price","备货价不允许为空");//文本框ID和提示用语
		return false;
	} else if(price.length>0 && !reg.test(price)){
		warnTips("price","备货价输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}else if(Number(price)>300000000){
		warnTips("price","备货价不允许超过三亿");//文本框ID和提示用语
		return false;
	}
	
	var num = $("#num").val();
	if (num.length==0) {
		warnTips("num","产品数量不允许为空");//文本框ID和提示用语
		return false;
	}else{
		var re = /^[0-9]+$/ ;
		if(!re.test(num)){
			warnTips("num","产品数量必须为大于0的整数");//文本框ID和提示用语
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
	
	var supplierName = $form.find("#supplierName").val();
	if(supplierName.length>256){
		warnTips("supplierName","供应商长度应该在0-256个字符之间");
		return false;
	}
	
	var insideComments = $("#insideComments").val();
	if(insideComments.length>512){
		warnTips("insideComments","内部备注长度应该在0-512个字符之间");
		return false;
	}
	
	$.ajax({
		async:false,
		url:'./saveSaleorderGoods.do',
		data:$("#saleorderFormalGoodsForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			$("#close-layer").click();
			window.parent.location.reload();
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}