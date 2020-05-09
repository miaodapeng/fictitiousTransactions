function search2() {
	checkLogin();
	clearErroeMes();//清除錯誤提示信息
	if($("#searchContent").val()==undefined || $("#searchContent").val()==""){
		warnTips($("#search"),"errorMes","查询条件不能为空");//文本框ID和提示用语
		return false;
	}
	if(parent.$("#quotePayMoneForm").find("#quoteCustomerNature").val() == "465"){//客户为分销
		//终端信息
		var terminalTraderName = "";var terminalTraderId = "";var terminalTraderType ="";var salesArea = "";var salesAreaId = "";
		if(parent.$("#quotePayMoneForm").find("#terminalTraderName") != undefined && parent.$("#quotePayMoneForm").find("#terminalTraderName").val() != undefined && parent.$("#quotePayMoneForm").find("#terminalTraderName").val().length > 0){
			terminalTraderName = parent.$("#quotePayMoneForm").find("#terminalTraderName").val();
			terminalTraderId = parent.$("#quotePayMoneForm").find("#terminalTraderId").val();
			terminalTraderType = parent.$("#quotePayMoneForm").find("#terminalTraderType").val();
			salesArea = parent.$("#quotePayMoneForm").find("#salesArea").val();
			salesAreaId = parent.$("#quotePayMoneForm").find("#salesAreaId").val();
			$("#search").find("#salesAreaId").val(salesAreaId);
		}else{
			terminalTraderName = parent.$("#updateTerminalInfo").find("#searchTraderName").val().trim();
			var salesArea = (parent.$("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#province :selected").text()) + " " 
			+ (parent.$("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#city :selected").text()) + " " 
			+ (parent.$("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#zone :selected").text());
			var province = parent.$("#updateTerminalInfo").find("#province :selected").val()=="0"?"":parent.$("#updateTerminalInfo").find("#province :selected").val();
			var city = parent.$("#updateTerminalInfo").find("#city :selected").val();
			var zone = parent.$("#updateTerminalInfo").find("#zone :selected").val();
			$("#search").find("#salesAreaId").val((zone=="0"?(city=="0"?province:city):zone));
		}
	}
	$("#searchContent").val($("#searchContent").val().trim());
	$("#search").submit();
}

$(function() {
    $('.controller input:radio[name="abc"]').change(function() {
    	if(this.value=="input"){
    		$("#inputGoodsDiv").show();
    		$("#goodsListDiv").hide();
    		$("#confirmGoodsDiv").hide();
    	}else{
    		$("#inputGoodsDiv").hide();
    		$("#goodsListDiv").show();
    		$("#confirmGoodsDiv").hide();
    	}
    	$(".formpublic").find("input[type=text][type=hidden]").val("");

        /*var index = $(this).parents('li').index();
            $('.controlled').eq(index).show();
            $('.controlled').eq(index).siblings('.controlled').hide();*/
    })
    /*$('.choose').each(function(){
        $(this).click(function(){
           var productname=$(this).parents('tr').find('.productname1').html();
           $('.productname2').html(productname);
            $('.controlled').eq(0).hide();
            $('.controlled').eq(1).show();
        })
    })*/
    
})


function selectGoods(goodsId,sku,goodsName,brandName,model,unitName,goodsLevelName,goodsUser,verifyStatus,channelPrice){
	checkLogin();
	//验证该报价下是否存在重复产品
	$.ajax({
		async:false,
		url:'./vailQuoteGoodsRepeat.do',
		data:{"quoteorderId":$("#quoteorderId").val(),"goodsId":goodsId,"sku":sku},
		type:"GET",
		dataType : "json",
		success:function(data){
			if(data.code == 0){
				//操作显示/隐藏区域
				$("#inputGoodsDiv").hide();
				$("#goodsListDiv").hide();
				$("#confirmGoodsDiv").show();
				
				//赋值
				$("#confirmGoodsDiv").find("#confirmGoodsName").html(goodsName);
				$("#confirmGoodsDiv").find("#confirmGoodsName").attr("tabTitle",'{"num":"viewgoods'+goodsId+'","link":"'+page_url+'/goods/goods/viewbaseinfo.do?goodsId='+goodsId+'","title":"产品信息"}');
//				$("#confirmGoodsDiv").find("#confirmGoodsName").html(goodsName).attr("tabTitle",'{"num":"viewgoods'+goodsId+'","link":"'+page_url+'/goods/goods/viewbaseinfo.do?goodsId='+goodsId+'","title":"产品信息"}');
				$("#confirmGoodsDiv").find("#confirmGoodsBrandNameModel").html(brandName+"/"+model);
				var str = "待审核";
				if(verifyStatus == "0"){
					str = "审核中";
				}else if(verifyStatus == "1"){
					str = "审核通过";
				}else if(verifyStatus == "2"){
					str = "审核不通过";
				}
				$("#confirmGoodsDiv").find("#confirmGoodsContent").html(sku+" / "+goodsLevelName+" / "+ str +" / 产品负责人"+goodsUser);
				$("#confirmGoodsDiv").find("#confirmUnitName").html(unitName);
				
				$("#confirmGoodsDiv").find("#goodsId").val(goodsId);
				$("#confirmGoodsDiv").find("#sku").val(sku);
				$("#confirmGoodsDiv").find("#goodsName").val(goodsName);
				$("#confirmGoodsDiv").find("#brandName").val(brandName);
				$("#confirmGoodsDiv").find("#model").val(model);
				$("#confirmGoodsDiv").find("#unitName").val(unitName);
				
				var priceText = '无核价信息，请向产品部咨询';
				if(channelPrice!=''){
					priceText = '核价参考价格：' + channelPrice;
				}else{
					if(data.data == 0){
						priceText = '报价平均价（近12个月）：无平均价信息，请向产品部咨询';
					}else{
						priceText = '报价平均价（近12个月）：'+data.data;
					}
				}
				$("#confirmGoodsDiv").find("#goodsChannelPrice").text(priceText);
			}else{
				layer.alert(data.message, 
					{ icon: 2 },
					function (index) {
						layer.close(index);
					}
				);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	
}

function againSearch(){
	checkLogin();
//	$(".formpublic").find("input[type=text][type=hidden]").val("");
	$(".formpublic").find("input[type=text]").val("");
	$("#confirmTotalMoney").html("");
	$("#goodsListDiv").show();
	$("#confirmGoodsDiv").hide();
	$("#inputGoodsDiv").hide();
}

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
			warnTips($form,"priceError","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			$form.find("#price").focus();
			return false;
		}else if(Number(price)>=10000000){
			warnTips($form,"priceError","产品单价不允许超过一千万");//文本框ID和提示用语
			$form.find("#price").focus();
			return false;
		}
		if(num.length!=0 && num!="0" && re.test(num)){
			flag = true;
		}
	}else{
		if (num.length==0) {
			warnTips($form,"num","产品数量必须为正整数");//文本框ID和提示用语
			return false;
		}else{
			if(num=="0" || !re.test(num)){
				warnTips($form,"num","产品数量必须为正整数");//文本框ID和提示用语
				return false;
			}
		}
		if(Number(num)>=1000000){
			warnTips($form,"num","产品数量不允许超过一百万");//文本框ID和提示用语
			return false;
		}
		if(price.length>0 && reg.test(price)){
			flag = true;
		}
	}
	if((Number(num)*Number(price)) >= 10000000){
		warnTips($form,"num","产品总金额不允许超过一千万");//文本框ID和提示用语
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
	
	$form.find("form :input").parents('li').find('.warning').remove();
	$form.find("form :input").removeClass("errorbor");
	
	$("#haveInstallation").val($form.find("input[name='installation']:checked").val());
	/*if($form.find("#haveInstallation").val()==""){
		warnTips($form,"haveInstallation","请选择报价是否含安调");//文本框ID和提示用语
		return false;
	}*/

	
	var price = $form.find("#price").val().trim();
	
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(price.length>0 && !reg.test(price)){
		warnTips($form,"priceError","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}else if(Number(price)>=10000000){
		warnTips($form,"priceError","产品单价不允许超过一千万");//文本框ID和提示用语
		return false;
	}
	
	var num = $form.find("#num").val();
	if (num.length==0) {
		warnTips($form,"num","产品数量不允许为空");//文本框ID和提示用语
		return false;
	}else{
		var re = /^[0-9]+$/ ;
		if(num=="0" || !re.test(num)){
			warnTips($form,"num","产品数量必须为正整数");//文本框ID和提示用语
			return false;
		}
	}
	if(Number(num)>=1000000){
		warnTips($form,"num","产品数量不允许超过一百万");//文本框ID和提示用语
		return false;
	}
	if((Number(num)*Number(price)) >= 10000000){
		warnTips($form,"num","产品总金额不允许超过一千万");//文本框ID和提示用语
		return false;
	}
	
	var unitName = $form.find("#unitName").val();
	if (unitName.length==0) {
		warnTips($form,"unitName","产品单位不能为空");//文本框ID和提示用语
		return false;
	}
	if(unitName.length>20){
		warnTips($form,"unitName","产品单位长度应该在0-20个字符之间");
		return false;
	}
	/*var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
	if(!nameReg.test(unitName)){
		warnTips($form,"unitName","产品单位不允许使用特殊字符");
		return false;
	}*/

	
	var deliveryCycle = $form.find("#deliveryCycle").val();
	if (deliveryCycle.length!=0 && deliveryCycle.length>20) {
		warnTips($form,"deliveryCycle","货期长度应该在0-20个字符之间");//文本框ID和提示用语
		return false;
	}
	
	$("#deliveryDirect").val($form.find("input[name='deliveryDirectRad']:checked").val());
	if($form.find("#deliveryDirect").val()=="1"){
		var deliveryDirectComments = $form.find("#deliveryDirectComments").val().trim();
		if(deliveryDirectComments.length==0){
			warnTips($form,"deliveryDirectComments","直发原因不允许为空");
			return false;
		}
		if(deliveryDirectComments.length<2 || deliveryDirectComments.length>128){
			warnTips($form,"deliveryDirectComments","直发原因长度应该在2-128个字符之间");
			return false;
		}
	}
	
	var insideComments = $form.find("#insideComments").val();
	if(insideComments.length>512){
		warnTips($form,"insideComments","内部备注长度应该在0-512个字符之间");
		return false;
	}
	
	var goodsComments = $form.find("#goodsComments").val();
	if(goodsComments.length>512){
		warnTips($form,"goodsComments","产品备注长度应该在0-512个字符之间");
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
		$("#confirmForm").find("#salesAreaId").val((zone=="0"?(city=="0"?province:city):zone));
		
		$("#confirmForm").find("#terminalTraderName").val(terminalTraderName);
		$("#confirmForm").find("#terminalTraderId").val(terminalTraderId);
		$("#confirmForm").find("#terminalTraderType").val(terminalTraderType);
		$("#confirmForm").find("#salesArea").val(salesArea);
	}
	
	$.ajax({
		async:false,
		url:'./saveQuoteGoods.do',
		data:$("#confirmForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
//			refreshPageList(data);
			
			if(data.code==0){
				parent.$("#paymentType").val("419");
//				window.parent.location.reload();
				layerPFF(null);
			}else{
				layer.alert(data.message,{icon: 2});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}

//文本框验证
function warnTips(obj,id,txt){
	$(obj).find("form :input").parents('li').find('.warning').remove();
	$(obj).find("form :input").removeClass("errorbor");
	$(obj).find("#"+id).siblings('.warning').remove();
	$(obj).find("#"+id).after('<div class="warning">'+txt+'</div>');
	$(obj).find("#"+id).addClass("errorbor");
	$(obj).find("#"+id).focus();
	return false;
}

function inputTotalMoney(str){
	checkLogin();
	clearErroeMes();
	var $form = $("#inputGoodsDiv");
	$form.find("#inputTotalMoney").html("");
	
	var flag = false;
	
	var price = $form.find("#price").val().trim();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	
	var num = $form.find("#num").val().trim();
	var re = /^[0-9]+$/;
	
	if(str=="price"){
		if(price.length>0 && !reg.test(price)){
			warnTips($form,"price","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(price)>=10000000){
			warnTips($form,"price","产品单价不允许超过一千万");//文本框ID和提示用语
			return false;
		}
		if(num.length!=0 && num!="0" && re.test(num)){
			flag = true;
		}
	}else{
		if (num.length==0) {
			warnTips($form,"num","产品数量不允许为空");//文本框ID和提示用语
			return false;
		}else{
			if(num=="0" || !re.test(num)){
				warnTips($form,"num","产品数量必须为正整数");//文本框ID和提示用语
				return false;
			}
		}
		if(Number(num)>10000){
			warnTips($form,"num","产品数量不允许超过一万");//文本框ID和提示用语
			return false;
		}
		if(price.length>0 && reg.test(price)){
			flag = true;
		}
	}
	
	if(flag){
		if (num != undefined && num != "" && price != undefined && price != "") {
			/*var f = Number(num) * Number(price);
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
				while (s.length <= rs + 2) {
					s += '0';
				}
			}*/
			$form.find("#inputTotalMoney").html((Number(num) * Number(price)).toFixed(2));
		}
	}
	return true;
}

function inputSubmit(){
	checkLogin();
	var $form = $("#inputGoodsDiv");

	var goodsName = $form.find("#goodsName").val().trim();
	if (goodsName.length==0) {
		warnTips($form,"goodsName","产品名称不能为空");//文本框ID和提示用语
		return false;
	}
	if (goodsName.length<1 || goodsName.length >32) {
		warnTips($form,"goodsName","产品名称长度应该在1-100个字符之间");//文本框ID和提示用语
		return false;
	}
	
	var brandName = $form.find("#brandName").val().trim();
	if (brandName.length==0) {
		warnTips($form,"brandName","品牌不能为空");//文本框ID和提示用语
		return false;
	}
	if (brandName.length<1 || brandName.length >32) {
		warnTips($form,"brandName","品牌长度应该在1-32个字符之间");//文本框ID和提示用语
		return false;
	}
	
	var model = $form.find("#model").val().trim();
	if (model.length==0) {
		warnTips($form,"model","型号不能为空");//文本框ID和提示用语
		return false;
	}
	if (model.length<1 || model.length >64) {
		warnTips($form,"model","型号长度应该在1-64个字符之间");//文本框ID和提示用语
		return false;
	}
	
	
	var price = $form.find("#price").val().trim();
	
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(price.length>0 && !reg.test(price)){
		warnTips($form,"price","报价金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}else if(Number(price)>=10000000){
		warnTips($form,"price","产品单价不允许超过一千万");//文本框ID和提示用语
		return false;
	}
	
	var num = $form.find("#num").val();
	if (num.length==0) {
		warnTips($form,"num","产品数量不能为空");//文本框ID和提示用语
		return false;
	}else{
		var re = /^[0-9]+$/ ;
		if(num== "0" || !re.test(num)){
			warnTips($form,"num","产品数量必须为正整数");//文本框ID和提示用语
			return false;
		}
	}
	if(Number(num)>10000){
		warnTips($form,"num","产品数量不允许超过一万");//文本框ID和提示用语
		return false;
	}
	
	var unitName = $form.find("#unitName").val().trim();
	if (unitName.length==0) {
		warnTips($form,"unitName","单位不能为空");//文本框ID和提示用语
		return false;
	}
	if (unitName.length<1 || unitName.length >20) {
		warnTips($form,"unitName","单位长度应该在1-20个字符之间");//文本框ID和提示用语
		return false;
	}
	
	$form.find("#haveInstallation").val($form.find("input[name='installation']:checked").val());
	/*if($form.find("#haveInstallation").val()==""){
		warnTips($form,"haveInstallation","请选择报价是否含安调");//文本框ID和提示用语
		return false;
	}*/
	
	$form.find("#deliveryDirect").val($form.find("input[name='deliveryDirectRad']:checked").val());
	if($form.find("#deliveryDirect").val()=="1"){
		var deliveryDirectComments = $form.find("#deliveryDirectComments").val().trim();
		if(deliveryDirectComments.length==0){
			warnTips("deliveryDirectComments","直发原因不允许为空");
			return false;
		}
		if(deliveryDirectComments.length<2 || deliveryDirectComments.length>128){
			warnTips($form,"deliveryDirectComments","直发原因长度应该在2-128个字符之间");
			return false;
		}
	}
	
	/*var price = $form.find("#price").val();
	if (price.length==0) {
		warnTips($form,"price","产品报价不能为空");//文本框ID和提示用语
		return false;
	}else{
		var reg = /^[0-9]+(.[0-9]{2})?$/;
		if(!reg.test(price)){
			warnTips($form,"price","产品报价必须为正整数或两位小数的浮点类型");//文本框ID和提示用语
			return false;
		}
	}*/

	
	var deliveryCycle = $form.find("#deliveryCycle").val();
	if (deliveryCycle.length!=0 && deliveryCycle.length > 20) {
		warnTips($form,"deliveryCycle","货期长度应该在0-20个字符之间");
		return false;
	}
	
	var insideComments = $form.find("#insideComments").val();
	if(insideComments.length>512){
		warnTips($form,"insideComments","内部备注长度应该在0-512个字符之间");
		return false;
	}
	
	var goodsComments = $form.find("#goodsComments").val();
	if(goodsComments.length>512){
		warnTips($form,"goodsComments","产品备注长度应该在0-512个字符之间");
		return false;
	}
	if(parent.$("#quotePayMoneForm").find("#quoteCustomerNature").val() == "465"){//客户为分销
		//终端信息
		var terminalTraderName = "";var terminalTraderId = "";var terminalTraderType ="";var salesArea = "";var salesAreaId = "";
		if(parent.$("#quotePayMoneForm").find("#terminalTraderName") != undefined && parent.$("#quotePayMoneForm").find("#terminalTraderName").val() != undefined && parent.$("#quotePayMoneForm").find("#terminalTraderName").val().length > 0){
			terminalTraderName = parent.$("#quotePayMoneForm").find("#terminalTraderName").val();
			terminalTraderId = parent.$("#quotePayMoneForm").find("#terminalTraderId").val();
			terminalTraderType = parent.$("#quotePayMoneForm").find("#terminalTraderType").val();
			salesArea = parent.$("#quotePayMoneForm").find("#salesArea").val();
			salesAreaId = parent.$("#quotePayMoneForm").find("#salesAreaId").val();
			
			$("#inputForm").find("#salesAreaId").val(salesAreaId);
		}else{
			terminalTraderName = parent.$("#updateTerminalInfo").find("#searchTraderName").val().trim();
			var salesArea = (parent.$("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#province :selected").text()) + " " 
			+ (parent.$("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#city :selected").text()) + " " 
			+ (parent.$("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":parent.$("#updateTerminalInfo").find("#zone :selected").text());
			var province = parent.$("#updateTerminalInfo").find("#province :selected").val()=="0"?"":parent.$("#updateTerminalInfo").find("#province :selected").val();
			var city = parent.$("#updateTerminalInfo").find("#city :selected").val();
			var zone = parent.$("#updateTerminalInfo").find("#zone :selected").val();
			
			$("#inputForm").find("#salesAreaId").val((zone=="0"?(city=="0"?province:city):zone));
		}
		$("#inputForm").find("#terminalTraderName").val(terminalTraderName);
		$("#inputForm").find("#terminalTraderId").val(terminalTraderId);
		$("#inputForm").find("#terminalTraderType").val(terminalTraderType);
		$("#inputForm").find("#salesArea").val(salesArea);
		
	}
	$.ajax({
		async:false,
		url:'./saveQuoteGoods.do',
		data:$("#inputForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				parent.$("#paymentType").val("419");
				window.parent.location.reload();
			}else{
				layer.alert(data.message,{icon: 2});
			}
//			refreshPageList(data);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}

function uploadImgFtp(obj){
	checkLogin();
	var imgPath = $(obj).val();
	$("#fileUrl").val(imgPath);
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg') {
		$("#lwfile").val("");$("#fileUrl").val("");
		layer.alert("请选择jpg格式图片",{ icon: 2 });
		return;
	}
	if($(obj)[0].files[0].size > 2*1024*1024){//字节
		$("#lwfile").val("");$("#fileUrl").val("");
		layer.alert("图片大小应为2MB以内",{ icon: 2 });
		return;
	}
	
	$("#img_icon_wait").attr("class", "iconloading mt5").show();
	$("#img_opt_look").hide();
	$("#img_opt_del").hide();
	
	$.ajaxFileUpload({
		url :  './goodsImgUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				//給隱藏域附件信息賦值
				$("#imgName").val(data.fileName);
				$("#imgDomain").val(data.httpUrl);
				$("#imgUri").val(data.filePath+"/"+data.fileName);
				
				$("#img_opt_look").attr("href", 'http://' + data.httpUrl + data.filePath+"/"+data.fileName).show();
				$("#img_icon_wait").attr("class", "iconsuccesss mt5").show();
				$("#img_opt_del").show();
				/*layer.alert(data.message);
				layer.alert(data.message,{ icon: 1 });*/
			} else {
				layer.alert(data.message,{ icon: 2 });
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}else{
				layer.alert(data.responseText);
			}
		}
	})
}

function delProductImg() {
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$("#img_icon_wait").hide();
		$("#img_opt_look").hide();
		$("#img_opt_del").hide();

		$("#fileUrl").val("");
		$("#lwfile").val("");

		$("#imgName").val("");
		$("#imgDomain").val("");
		$("#imgUri").val("");
		layer.close(index);
	}, function() {
	});
}