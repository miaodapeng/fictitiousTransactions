$(function(){
	if($('.content2 .payplan .tips-all select').val() == 1){
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


function editSubmit(type){
	checkLogin();
	var $form = $("#editForm");
	$("form").find('.warning').remove();
	
	var orderType = Number(type);
	
	if(orderType != 5)
	{
		if ($("select[name='takeTraderContactId']").val() == 0) {
			warnTips("takeTraderContactIdMsg","收货联系人不允许为空");
			return false;
		}
		if ($("select[name='takeTraderAddressId']").val() == 0) {
			warnTips("takeTraderAddressIdMsg","收货地址不允许为空");
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
		if(takeAreaLength != 3) {
			warnTips("takeTraderAddressIdMsg","收货地址请补充完省市区");
			return false;
		}
	}
	else
	{
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
	}	
	
	var logisticsComments = $("#logisticsComments").val();
	if(logisticsComments.length>256){
		warnTips("logisticsComments","物流备注长度应该在0-256个字符之间");
		return false;
	}
	
	if ($("select[name='invoiceType']").val() == 0) {
		warnTips("invoiceTypeMsg","发票类型不允许为空");
		return false;
	}
	if ($("select[name='invoiceMethod']").val() == 0) {
		warnTips("invoiceMethodMsg","开票方式不允许为空");
		return false;
	}
	
	if(orderType != 5)
	{
		if ($("select[name='invoiceTraderContactId']").val() == 0) {
			warnTips("invoiceTraderContactIdMsg","收票联系人不允许为空");
			return false;
		}
		if ($("select[name='invoiceTraderAddressId']").val() == 0) {
			warnTips("invoiceTraderAddressIdMsg","收票地址不允许为空");
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
	}
	else
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
		
		
	var invoiceComments = $("#invoiceComments").val();
	if(invoiceComments.length>256){
		warnTips("invoiceComments","开票备注长度应该在0-256个字符之间");
		return false;
	}	
	
	var idStrArr = $("#id_str").val().split("_");
	for (var i = 0; i < idStrArr.length; i++) {
		if (idStrArr[i] == '') continue;
		if ($("#deliveryDirect_" + idStrArr[i]).val() == 1 && $("#deliveryDirectComments_" + idStrArr[i]).val() == '') {
			warnTips("commentsMsg_" + idStrArr[i] ,"直发备注不允许为空");
			return false;
		}
	}
	if(orderType != 5 && $("#invoiceModifyflag").val()!=1 ){
		//校验随货出款单不能为空
		var isPrintout = $("#is_printout").val();
		if (isPrintout == -1 || isPrintout == ''){
			warnTips("isPrintoutMsg" ,"是否打印随货出库单不能为空");
			return false;
		}
		if (isPrintout == -2){
			warnTips("isPriceMsg" ,"随货出库单是否自带价格不能为空");
			return false;
		}
	}
	$form.submit();
	//return false;
}


function isSendInvoiceChecked() {
	checkLogin();
	/*if(typeof($("input[name='isSendInvoiceCheckbox']:checked").val()) == "undefined") {
		$("input[name='isSendInvoice']").val(1);
	} else {
		$("input[name='isSendInvoice']").val(0);
	}*/
	var invoiceapplyFlag = $("#invoiceapplyFlag").val();
	if(invoiceapplyFlag == 0) {
		if (typeof ($("input[name='isSendInvoiceCheckbox']:checked").val()) == "undefined") {
			$("input[name='isSendInvoice']").val(1);
			var invoiceType = $("select[name='invoiceType'] option:selected").val();
			if (invoiceType == "681" || invoiceType == "971") {
				$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option><option value="3">自动电子发票</option>');
			} else if (invoiceType == "682" || invoiceType == "972") {
				$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
			} else {
				$("select[name='invoiceMethod']").html('<option value="0">请选择</option>');
			}
		} else {
			$("input[name='isSendInvoice']").val(0);
			var invoiceType = $("select[name='invoiceType'] option:selected").val();
			if (invoiceType == "681" || invoiceType == "971") {
				$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
			}
		}
	}else{
		// add by Tomcat.Hui 2019/12/30 15:21 .Desc: VDERP-1811 将销售订单的发票不寄送申请修改为寄送后，寄送状态未生效. start
		if (typeof ($("input[name='isSendInvoiceCheckbox']:checked").val()) == "undefined") {
			$("input[name='isSendInvoice']").val(1);
		}else {
			$("input[name='isSendInvoice']").val(0);
		}
		// add by Tomcat.Hui 2019/12/30 15:21 .Desc: VDERP-1811 将销售订单的发票不寄送申请修改为寄送后，寄送状态未生效. end
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

function updateInvoiceType(obj){
	checkLogin();
	/*if($(obj).val()=="681"){
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option><option value="3">自动电子发票</option>');
	}else if($(obj).val()=="682"){
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
	}else {
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option>');
	}*/
	if($(obj).val()=="681" || $(obj).val()=="971"){
		if($("input:checkbox[name='isSendInvoiceCheckbox']").is(":checked") && $("input[name='isSendInvoice']").val() == "0"){
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
		}else{
			$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option><option value="3">自动电子发票</option>');
		}
	}else if($(obj).val() == "682" || $(obj).val() == "972"){
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option><option value="1">手动纸质开票</option><option value="2">自动纸质开票</option>');
	}else {
		$("select[name='invoiceMethod']").html('<option value="0">请选择</option>');
	}
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
					"<option value=\"0\">请选择</option>" +
					"<option value=\"1\">是</option>" +
					"<option value=\"2\">否</option>" +
					"</select>" +
					"</div>" +
                    "<div id=\"isPriceMsg\" style=\"clear:both;\"></div>" +
					"</li>");
                $("#is_printout").val(-2);
            }
		}
	}else if (isPrint == -1){
		$("#is_printout").val(-1);
		if ($("#is_price").length == 1){
			$("#is_price_li").remove();
		};
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
