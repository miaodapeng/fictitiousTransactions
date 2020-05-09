$(function(){
	var zone = $("#updateTerminalInfo").find("#zone :selected").val();
	if (zone == 1) {
		$("#updateTerminalInfo").find("#province").val(0);
	}
	$("#paymentType").change(function(){
		checkLogin();
		$("form :input").parents('li').find('.warning').remove();
		
		var num = $(this).val();
		if($(this).val()=="419"){
			$("#accountPeriodLi").hide();
			$("#retainageLi").hide();
		}else if($(this).val()=="424"){
			$("#accountPeriodLi").show();
			$("#retainageLi").show();
		}else{
			$("#accountPeriodLi").show();
			$("#retainageLi").hide();
		}
		
		$("#prepaidAmount").val('0.00');$("#accountPeriodAmount").val('0.00');
		$("#retainageAmount").val('0.00');


        //modify by tomcat.Hui 20190918 VDERP-1294 编辑订单金额错误  增加隐藏域，用于jQuery下拉框联动 start
        var totleMoney= $("#goodsTotleMoney").val();
		if ($("#goodsOrderType").val() == "5"){
            totleMoney = $("#goodsTotleAmount").val();
            console.log("money:" + totleMoney)
        }
        //modify by tomcat.Hui 20190918 VDERP-1294 编辑订单金额错误  增加隐藏域，用于jQuery下拉框联动 end

		switch (num) {
		case "419":
			$("#prepaidAmount").val(Number(totleMoney).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			break;
		case "420":
			$("#prepaidAmount").val((Number(totleMoney)*0.8).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.2).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "421":
			$("#prepaidAmount").val((Number(totleMoney)*0.5).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.5).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "422":
			$("#prepaidAmount").val((Number(totleMoney)*0.3).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.7).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "423":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			$("#prepaidAmount").val(0);
			$("#accountPeriodAmount").val(Number(totleMoney).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "424":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			//$("#prepaidAmount").val('');$("#accountPeriodAmount").val('');
			break;
		default:

		}
	})
	if($('.content2 .payplan .tips-all select').val()==1){
		$('.tips-error').show();
	}
	$('.content2 .payplan .tips-all select').change(function () {
		if($(this).val() === '1'){
			$('.tips-error').show();
		}else{
			$('.tips-error').hide();
		}
	})
	
})

function changeIsPrintout() {
	var isPrint = $("#is_print").val();
	var isScientificDept = $("#is_scientificDept").val();
	if (isPrint == 2){
		//不打印随货出库单
		$("#is_printout").val(0);
		if ($("#is_price").length == 1){
			$("#is_price_li").remove();
		}
	} else if (isPrint == 1){
        //判断打印出库单是否带价格
        if (isScientificDept == "true"){
            $("#is_printout").val(3);
        } else {
            if ($("#is_price").length == 0){
                $("#is_print_li").append("<li id='is_price_li'>" +
                    "<div style=\"height: 25px\">" +
                    "<span style=\"color: red;text-indent: 15px\">  *</span>" +
                    "<label style=\"width: 158px\">随货出库单是否自带价格</label>" +
                    "<select  id='is_price' name = \"isPrintout\" style='height: auto' onchange='changeIsPrice()' >" +
                    "<option value=\"0\">请选择</option>\n" +
                    "<option value=\"1\">是</option>\n" +
                    "<option value=\"2\">否</option>\n" +
                    "</select>" +
                    "</div>" +
					"<div id=\"isPriceMsg\" style=\"clear:both;\"></div>" +
                    "</li>");
				$("#is_printout").val(-2);
			}
        }
	} else if (isPrint == -1){
		$("#is_printout").val(-1);
		if ($("#is_price").length == 1){
			$("#is_price_li").remove();
		}
	}
}

function changeIsPrice() {
    var isPrice = $("#is_price").val();
    if (isPrice == 1){
        $("#is_printout").val(1);
    } else if (isPrice == 2){
        $("#is_printout").val(2);
    } else if (isPrice == 0){
		$("#is_printout").val(-2);
	}
}

function isSendInvoiceChecked(orderType) 
{
	checkLogin();
	// 
	var isSendInvoiceCheckbox = $("input[name='isSendInvoiceCheckbox']:checked").val();
	
	if(typeof(isSendInvoiceCheckbox) == "undefined") {
		$("input[name='isSendInvoice']").val(1);
		var invoiceType = $("select[name='invoiceType'] option:selected").val();
		if(invoiceType == "681" || invoiceType == "971"){
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option><option value="3">自动电子发票</option>');
		}else if(invoiceType == "682" || invoiceType == "972"){
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
		}else {
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option>');
		}
	} else {
		$("input[name='isSendInvoice']").val(0);
		var invoiceType = $("select[name='invoiceType'] option:selected").val();
		if(invoiceType == "681" || invoiceType == "971"){
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
		}
	}
}

function isDelayInvoiceChecked() {
	checkLogin();
	if(typeof($("input[name='isDelayInvoiceCheckbox']:checked").val()) == "undefined") {
		$("input[name='isDelayInvoice']").val(0);
	} else {
		$("input[name='isDelayInvoice']").val(1);
	}
}
	
function updatePayment(obj){
	checkLogin();
	if($(obj).val()=="419"){
		$("#accountPeriodLi").hide();
		$("#retainageLi").hide();
	}else if($(obj).val()=="424"){
		$("#accountPeriodLi").show();
		$("#retainageLi").show();
	}else{
		$("#accountPeriodLi").show();
		$("#retainageLi").hide();
	}
}

function updateInvoiceType(obj){
	checkLogin();
	if($(obj).val()=="681" || $(obj).val()=="971"){
		if($("input:checkbox[name='isSendInvoiceCheckbox']").is(":checked") && $("input[name='isSendInvoice']").val() == "0"){
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
		}else{
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option><option value="3">自动电子发票</option>');
		}
	}else if($(obj).val()=="682" || $(obj).val()=="972"){
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
	}else {
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option>');
	}
}

function logisticsCheck() {
	checkLogin();
	$("#logisticsCollection").val($("#logisticsCheckBox").is(":checked")==true?1:0);
}

function delSaleorderGoods(saleorderGoodsId, saleorderId){
	checkLogin();
	layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./delSaleorderGoodsById.do",
				data: {'saleorderGoodsId':saleorderGoodsId,'saleorderId':saleorderId,'isDelete':1},
				dataType:'json',
				success: function(data){
					layer.alert(data.message,{
						closeBtn: 0,
						btn: ['确定'] //按钮
					}, function(){
						window.location.reload();
					});
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
	});
}

function editSubmit(type){
	checkLogin();
	var $form = $("#editForm");
	$("form :input").parents('li').find('.warning').remove();
	
	var orderType = Number(type);
	// 不寄送
	var isSendInvoice = $("#isSendInvoice").val();
	
	if(orderType != 5)
	{
		if ($("select[name='traderContactId']").val() == 0) {
			warnTips("traderContactIdMsg","联系人不允许为空");
			return false;
		}
		if ($("select[name='traderAddressId']").val() == 0) {
			warnTips("traderAddressIdMsg","联系地址不允许为空");
			return false;
		}
		
		if ($("select[name='takeTraderContactId']").val() == 0) {
			warnTips("takeTraderContactIdMsg","收货联系人不允许为空");
			return false;
		}
		
		var traderContactIdText = $("select[name='traderContactId']").find("option:selected").text();
		var traderContactIdTextArr = traderContactIdText.split('/');
		$("input[name='traderContactName']").val(traderContactIdTextArr[0]);
		$("input[name='traderContactTelephone']").val(traderContactIdTextArr[1]);
		$("input[name='traderContactMobile']").val(traderContactIdTextArr[2]);
		var traderAddressIdText = $("select[name='traderAddressId']").find("option:selected").text();
		var traderAddressIdTextArr = traderAddressIdText.split('/');
		$("input[name='traderArea']").val(traderAddressIdTextArr[0]);
		$("input[name='traderAddress']").val(traderAddressIdTextArr[1]);
		var takeTraderContactIdText = $("select[name='takeTraderContactId']").find("option:selected").text();
		var takeTraderContactIdTextArr = takeTraderContactIdText.split('/');
		$("input[name='takeTraderContactName']").val(takeTraderContactIdTextArr[0]);
		$("input[name='takeTraderContactTelephone']").val(takeTraderContactIdTextArr[1]);
		$("input[name='takeTraderContactMobile']").val(takeTraderContactIdTextArr[2]);
		var takeTraderAddressIdText = $("select[name='takeTraderAddressId']").find("option:selected").text();
		var takeTraderAddressIdTextArr = takeTraderAddressIdText.split('/');
		$("input[name='takeTraderArea']").val(takeTraderAddressIdTextArr[0]);
		$("input[name='takeTraderAddress']").val(takeTraderAddressIdTextArr[1]);
		
		var takeAreaLength = takeTraderAddressIdTextArr[0].split(' ').length;
	/*	if(takeAreaLength != 3) {
			warnTips("takeTraderAddressIdMsg","收货地址请补充完省市区");
			return false;
		}*/
		
		if ($("select[name='takeTraderAddressId']").val() == 0) {
			warnTips("takeTraderAddressIdMsg","收货地址不允许为空");
			return false;
		}
		
		var logisticsComments = $("#logisticsComments").val();
		if(logisticsComments.length>256){
			warnTips("logisticsComments","物流备注长度应该在0-256个字符之间");
			return false;
		}
		
		if ($("select[name='invoiceTraderContactId']").val() == 0) {
			warnTips("invoiceTraderContactIdMsg","收票联系人不允许为空");
			return false;
		}
		
		var invoiceTraderContactIdText = $("select[name='invoiceTraderContactId']").find("option:selected").text();
		var invoiceTraderContactIdTextArr = invoiceTraderContactIdText.split('/');
		$("input[name='invoiceTraderContactName']").val(invoiceTraderContactIdTextArr[0]);
		$("input[name='invoiceTraderContactTelephone']").val(invoiceTraderContactIdTextArr[1]);
		$("input[name='invoiceTraderContactMobile']").val(invoiceTraderContactIdTextArr[2]);
		var invoiceTraderAddressIdText = $("select[name='invoiceTraderAddressId']").find("option:selected").text();
		var invoiceTraderAddressIdTextArr = invoiceTraderAddressIdText.split('/');
		$("input[name='invoiceTraderArea']").val(invoiceTraderAddressIdTextArr[0]);
		$("input[name='invoiceTraderAddress']").val(invoiceTraderAddressIdTextArr[1]);
		
		var invoiceAreaLength = invoiceTraderAddressIdTextArr[0].split(' ').length;
		if(invoiceAreaLength != 3) {
			warnTips("invoiceTraderAddressIdMsg","收票地址请补充完省市区");
			return false;
		}
		
		if ($("select[name='invoiceTraderAddressId']").val() == 0) {
			warnTips("invoiceTraderAddressIdMsg","收票地址不允许为空");
			return false;
		}
	}
	else
	{
		// 订单归属
		var ownerUserId = $("#ownerUserId").val();
		if(undefined == ownerUserId || '' == ownerUserId || 0 == ownerUserId)
		{
			layer.alert("请选择订单归属", { icon: 2 });	
			return false;
		}
		var traderContactName = $("#traderContactName_5").val().trim();
		// 联系人
		if (undefined == traderContactName || traderContactName == '') 
		{
			warnTips("5_traderContactNameMsg","联系人不允许为空");
			return false;
		}
		
		// 联系人手机号
		var traderContactMobile = $("#traderContactMobile_5").val().trim();
		if (!/^\d{11}$/.test(traderContactMobile)) 
		{
			warnTips("5_traderContactMobileMsg","联系人手机号格式错误");
			return false;
		}
		// traderAddressId
		var traderAddressId = $("#traderAddressId").val();
		if(undefined == traderAddressId || traderAddressId == 0 || traderAddressId == '') {
			warnTips("5_traderAddressMsg","联系地址请补充完省市区");
			return false;
		}
		// 联系地址
		var traderAddress = $("#traderAddress_5").val().trim();
		if (undefined == traderAddress || traderAddress == '') 
		{
			warnTips("5_traderAddressMsg","联系地址不可为空");
			return false;
		}
		
		// 收货联系人
		var takeTraderContactName = $("#takeTraderContactName_5").val().trim();
		if(undefined == takeTraderContactName || takeTraderContactName == '')
		{
			warnTips("5_takeTraderContactName","收货联系人不可为空");
			return false;
		}
		// 收货手机号
		var takeTraderContactMobile = $("#takeTraderContactMobile_5").val().trim();
		if(!/^\d{11}$/.test(takeTraderContactMobile))
		{
			warnTips("5_takeTraderContactMobile","收货手机号格式错误");
			return false;
		}
		// 收货地址
		var takeTraderAddressId = $("#takeTraderAddressId").val();
		if(undefined == takeTraderAddressId || '' == takeTraderAddressId || 0 == takeTraderAddressId)
		{
			warnTips("5_takeTraderAddress","收货地址请补充完省市区");
			return false;
		}
		var takeTraderAddress = $("#takeTraderAddress_5").val().trim();
		if(undefined == takeTraderAddress || '' == takeTraderAddress)
		{
			warnTips("5_takeTraderAddress","收货地址不可为空");
			return false;
		}
		
		var logisticsComments = $("#logisticsComments").val();
		if(logisticsComments.length>256){
			warnTips("logisticsComments","物流备注长度应该在0-256个字符之间");
			return false;
		}
		
		// 是否寄送 -- 寄送则校验
		if(isSendInvoice == 1 || isSendInvoice == '1')
		{
			// 收票联系人
			var invoiceTraderContactName = $("#invoiceTraderContactName_5").val().trim();
			if(undefined == invoiceTraderContactName || '' == invoiceTraderContactName)
			{
				warnTips("5_invoiceTraderContactName","收票联系人不可为空");
				return false;
			}

			var invoiceTraderContactMobile = $("#invoiceTraderContactMobile_5").val().trim();
			if(!/^\d{11}$/.test(invoiceTraderContactMobile))
			{
				warnTips("5_invoiceTraderContactMobile","收票手机号格式错误");
				return false;
			}
			
			var invoiceTraderAddressId = $("#invoiceTraderAddressId").val();
			if(undefined == invoiceTraderAddressId || '' == invoiceTraderAddressId || 0 == invoiceTraderAddressId)
			{
				warnTips("5_invoiceTraderAddress","收票地址请补充完省市区");
				return false;
			}
			var invoiceTraderAddress = $("#invoiceTraderAddress_5").val().trim();
			if(undefined == invoiceTraderAddress || '' == invoiceTraderAddress)
			{
				warnTips("5_invoiceTraderAddress","收票地址不可为空");
				return false;
			}
			
			var invoiceEmail = $("#invoiceEmail_5").val().trim();
			if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(invoiceEmail))
			{
				warnTips("5_invoiceEmail", "收票邮箱格式错误");
				return false;
			}
		}
		
	}
	
	if ($("select[name='invoiceType']").val() == 0) {
		warnTips("invoiceTypeMsg","发票类型不允许为空");
		return false;
	}
	if ($("select[name='invoiceMethod']").val() == 0) {
		warnTips("invoiceMethodMsg","开票方式不允许为空");
		return false;
	}
	//$form.find("#logisticsCollection").val($form.find("#logisticsCheckBox").is(":checked")==true?1:0);
	var invoiceComments = $("#invoiceComments").val();
	if(invoiceComments.length>256){
		warnTips("invoiceComments","开票备注长度应该在0-256个字符之间");
		return false;
	}	
	
	
	var num = $form.find("#paymentType").val();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if(num=="424"){//自定义
		var prepaidAmount = $("#prepaidAmount").val();
		if(prepaidAmount.length > 14){
			warnTips("prepaidAmountError","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		
		if(prepaidAmount!=""){
			if(!reg.test(prepaidAmount)){
				warnTips("prepaidAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("prepaidAmountError","预付金额不允许小于0");//文本框ID和提示用语
			return false;
		}
		
		var accountPeriodAmount = $("#accountPeriodAmount").val();
		if(accountPeriodAmount.length > 14){
			warnTips("accountPeriodAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		if(accountPeriodAmount!=""){
			if(!reg.test(accountPeriodAmount)){
				warnTips("accountPeriodAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			var accountPeriodLeft = Number($("#accountPeriodLeft").val());//账期最大限额
			var oldAccountPeriodAmount = Number($("#oldAccountPeriodAmount").val());//本订单占用额度
			if(Number(accountPeriodAmount) > 0 && Number(accountPeriodAmount) > accountPeriodLeft + Number(oldAccountPeriodAmount)){
				warnTips("accountPeriodAmountError","帐期支付金额超过帐期剩余额度:"+accountPeriodLeft);//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("accountPeriodAmountError","账期支付金额不允许小于0");//文本框ID和提示用语
			return false;
		}

		var retainageAmount = $("#retainageAmount").val();
		if(retainageAmount.length > 14){
			warnTips("retainageAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		if(retainageAmount!=""){
			if(!reg.test(retainageAmount)){
				warnTips("retainageAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}else if(Number(retainageAmount)<0){
				warnTips("retainageAmountError","尾款不允许小于0");//文本框ID和提示用语
				return false;
			}else if(Number(retainageAmount) > Number($("#goodsTotleMoney").val())*0.1){
				warnTips("retainageAmountError","尾款不允许超过合同金额10%");//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("retainageAmountError","尾款不允许小于0");//文本框ID和提示用语
			return false;
		}
		var retainageAmountMonth = $("#retainageAmountMonth").val();
		if(retainageAmountMonth.length>0){
			var re = /^[0-9]+$/ ;
			if(retainageAmountMonth=="0" || !re.test(retainageAmountMonth)){
				warnTips("retainageAmountError","数量必须为大于0的正整数");//文本框ID和提示用语
				return false;
			}else if(Number(retainageAmountMonth) > 24){
				warnTips("retainageAmountError","尾款期限不允许超过24个月");//文本框ID和提示用语
				return false;
			}
		}
		
		if(prepaidAmount!="" && accountPeriodAmount!="" && retainageAmount!=""){
			var goodsTotleMoney = $("#goodsTotleMoney").val();
			if(Number(prepaidAmount) + Number(accountPeriodAmount) + Number(retainageAmount) != Number(goodsTotleMoney)){
				warnTips("error_div","支付金额总额与总金额不符，请验证！");//文本框ID和提示用语
				return false;
			}
		}
	} else if(num!="419"){//419先货后款，预付100%
		var accountPeriodAmount = $("#accountPeriodAmount").val();
		if(accountPeriodAmount.length > 14){
			warnTips("accountPeriodAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		
		if(accountPeriodAmount!=""){
			if(!reg.test(accountPeriodAmount)){
				warnTips("accountPeriodAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			var accountPeriodLeft = Number($("#accountPeriodLeft").val());//账期最大限额
			var oldAccountPeriodAmount = Number($("#oldAccountPeriodAmount").val());//本订单占用额度
			if(Number(accountPeriodAmount) > 0 && Number(accountPeriodAmount) > accountPeriodLeft + Number(oldAccountPeriodAmount)){
				warnTips("accountPeriodAmountError","帐期支付金额超过帐期剩余额度:"+accountPeriodLeft);//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("accountPeriodAmountError","账期支付金额必须大于0");//文本框ID和提示用语
			return false;
		}
	}
	
	var paymentComments = $("#paymentComments").val();
	if(paymentComments.length > 256){
		warnTips("paymentComments","收款备注长度应该在2-256个字符之间");//文本框ID和提示用语
		return false;
	}
	
	var additionalClause = $("#additionalClause").val();
	if(additionalClause.length > 256){
		warnTips("additionalClause","附加条款长度应该在0-256个字符之间");//文本框ID和提示用语
		return false;
	}
	
	var comments = $("#comments").val();
	if(comments.length > 1024){
		warnTips("comments","内部备注长度应该在0-1024个字符之间");//文本框ID和提示用语
		return false;
	}	
	
	if ($("#updateTerminalInfo").find("#province :selected").val() == 0) {
		warnTips("sales_area_msg_div","销售区域不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#updateTerminalInfo").find("#city :selected").val() == 0) {
		warnTips("sales_area_msg_div","城市不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#updateTerminalInfo").find("#zone :selected").val() == 0) {
		warnTips("sales_area_msg_div","区域不允许为空");//文本框ID和提示用语
		return false;
	}
	var salesArea = ($("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#province :selected").text()) + " " 
	+ ($("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#city :selected").text()) + " " 
	+ ($("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#zone :selected").text());
	$("#quotePayMoneForm").find("#salesArea").val(salesArea);
	var province = $("#updateTerminalInfo").find("#province :selected").val()=="0"?"":$("#updateTerminalInfo").find("#province :selected").val();
	var city = $("#updateTerminalInfo").find("#city :selected").val();
	var zone = $("#updateTerminalInfo").find("#zone :selected").val();
	$form.find("#salesAreaId").val(zone=="0"?(city=="0"?province:city):zone);


	if(orderType != 5   ) {
		//校验随货出款单不能为空
		var isPrintout = $("#is_printout").val();
		if (isPrintout == -1 || isPrintout == '') {
			warnTips("isPrintoutMsg", "是否打印随货出库单不能为空");
			return false;
		}
		if (isPrintout == -2) {
			warnTips("isPriceMsg", "随货出库单是否自带价格不能为空");
			return false;
		}
	}
	$form.submit();
	//return false;
}

function searchTerminal(){
	checkLogin();
//	$("#updateTerminalInfo").find("#errorMes").removeClass("errorbor");
	delWarnTips("errorTxtMsg");
	
	var searchTraderName = $("#updateTerminalInfo").find("#searchTraderName").val()==undefined?"":$("#updateTerminalInfo").find("#searchTraderName").val();
/*	if(searchTraderName==""){
		warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
		return false;
	}*/
	$("#updateTerminalInfo").find("#terminalDiv").attr('layerParams','{"width":"70%","height":"80%","title":"报价终端","link":"'+ page_url+'/trader/customer/getTerminalList.do?searchTraderName=' + encodeURI(searchTraderName) + '&optType=addQuoteTerminal"}');
	$("#updateTerminalInfo").find("#terminalDiv").click();
}

function agingSearchTerminal(){
	checkLogin();
	$("#searchTraderName").val("");
	$("#terminalTraderNameDiv").html("");
	$("#quotePayMoneForm").find(".terminal").each(function(i){
		$(this).val("");
	});
	$("#terminalNameDetail").hide();
	$("#terminalNameCheck").show();
}

function goodsCheckClick(obj){
	if($(obj).is(":checked")){
		var num = 0;
		$("input:checkbox[name='goodsCheckName']").each(function(i){
			if($(this).is(":checked")){
				num ++;
			}
		});
		if(num == $("input:checkbox[name='goodsCheckName']").length){
			$("input:checkbox[name='goodsCheckAllName']").prop("checked",true);
		}
	}else{
		$("input:checkbox[name='goodsCheckAllName']").prop("checked",false);
	}
}

function goodsCheckAllClick(obj){
	if($(obj).is(":checked")){
		$("input:checkbox[name='goodsCheckName']").each(function(i){
			$(this).prop("checked",true);
		});
	}else{
		$("input:checkbox[name='goodsCheckName']").each(function(i){
			$(this).prop("checked",false);
		});
	}
}

function updateSaleGoodsInit(saleorderId){
	checkLogin();
	var saleorderGoodsIdArr = [];
	$("input:checkbox[name='goodsCheckName']:checked").each(function(i){
		saleorderGoodsIdArr.push($(this).val());
	});
	if(saleorderGoodsIdArr.length == 0){
		layer.alert("请选择要修改的商品！");
		return false;
	}
	$("#saleGoodsDeliveryDirect").attr('layerParams','{"width":"450px","height":"260px","title":"修改订单商品","link":"'+ page_url+'/order/saleorder/updateSaleGoodsInit.do?saleorderGoodsIdArr=' + saleorderGoodsIdArr + '&saleorderId='+saleorderId+'"}');
	$("#saleGoodsDeliveryDirect").click();
}

function changeOwnerUserId(obj)
{
	var ownerUserId = $(obj).val();
	
	$("#ownerUserId").val(ownerUserId);
	
}
