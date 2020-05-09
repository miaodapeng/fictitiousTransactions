$(function(){
	$('input:radio[name="deliveryDirect"]').click(function(){
		checkLogin();
		$(".warning").remove();
		var isUpdateDeliveryDirect=$('#isUpdateDeliveryDirect').val();
		var oldDeliveryDirect = $("input[name='oldDeliveryDirect']").val();
		var deliveryDirect=$('input:radio[name="deliveryDirect"]:checked').val();
		if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 1){
			warnErrorTips("isUpdateDeliveryDirect","isUpdateDeliveryDirectError","采购单关联多个销售单不允许改成直发");
			$('input:radio:first').attr('checked', 'true');
			return false;
		}else if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 2){
			warnErrorTips("isUpdateDeliveryDirect","isUpdateDeliveryDirectError","采购单关联的销售商品含普发商品不允许改成直发");
			$('input:radio:first').attr('checked', 'true');
			return false;
		}else if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 0){
			$("li.ptz").removeClass("none");
			$("li.pf").addClass("none");
			
        	/*<input type="hidden" id="ptz_takeTraderId" value="${bv.saleTakeTraderId}">
        	<input type="hidden" id="ptz_takeTraderName" value="${bv.saleTakeTraderName}">
        	<input type="hidden" id="ptz_takeTraderContactId" value="${bv.saleTakeTraderContactId}">
        	<input type="hidden" id="ptz_takeTraderContactName" value="${bv.saleTakeTraderContactName}">
        	<input type="hidden" id="ptz_takeTraderContactMobile" value="${bv.saleTakeTraderContactMobile}">
        	<input type="hidden" id="ptz_takeTraderContactTelephone" value="${bv.saleTakeTraderContactTelephone}">
        	<input type="hidden" id="ptz_takeTraderAddressId" value="${bv.saleTakeTraderAddressId}">
        	<input type="hidden" id="ptz_takeTraderArea" value="${bv.saleTakeTraderArea}">
        	<input type="hidden" id="ptz_takeTraderAddress" value="${bv.saleTakeTraderAddress}">*/
			
			$("input[name='takeTraderId']").val($("#ptz_takeTraderId").val());
			$("input[name='takeTraderName']").val($("#ptz_takeTraderName").val());
			$("input[name='takeTraderContactId']").val($("#ptz_takeTraderContactId").val());
			$("input[name='takeTraderAddressId']").val($("#ptz_takeTraderAddressId").val());
			$("input[name='takeTraderArea']").val($("#ptz_takeTraderArea").val());
			$("input[name='takeTraderAddress']").val($("#ptz_takeTraderAddress").val());
			$("input[name='takeTraderContactName']").val($("#ptz_takeTraderContactName").val());
			$("input[name='takeTraderContactMobile']").val($("#ptz_takeTraderContactMobile").val());
			$("input[name='takeTraderContactTelephone']").val($("#ptz_takeTraderContactTelephone").val());
			
		}else if(oldDeliveryDirect == 0 && deliveryDirect == 0){
			$("li.ptz").addClass("none");
			$("li.pf").removeClass("none");
			var str = $("#address_pf").val();
			if(str != undefined && str != ''){
				$("input[name='takeTraderName']").val($("#companyName").val());
				$("input[name='takeTraderAddressId']").val(str.split('|')[0]);
				$("input[name='takeTraderArea']").val(str.split('|')[1]);
				$("input[name='takeTraderAddress']").val(str.split('|')[2]);
				$("input[name='takeTraderContactName']").val(str.split('|')[3]);
				$("input[name='takeTraderContactMobile']").val(str.split('|')[4]);
				$("input[name='takeTraderContactTelephone']").val(str.split('|')[5]);
			}
		}else if(oldDeliveryDirect == 1 && deliveryDirect == 0){
			$("li.zf").addClass("none");
			$("li.ztp").removeClass("none");
			var str = $("#address_pf").val();
			if(str != undefined && str != ''){
				$("input[name='takeTraderName']").val($("#companyName").val());
				$("input[name='takeTraderAddressId']").val(str.split('|')[0]);
				$("input[name='takeTraderArea']").val(str.split('|')[1]);
				$("input[name='takeTraderAddress']").val(str.split('|')[2]);
				$("input[name='takeTraderContactName']").val(str.split('|')[3]);
				$("input[name='takeTraderContactMobile']").val(str.split('|')[4]);
				$("input[name='takeTraderContactTelephone']").val(str.split('|')[5]);
			}
		}else if(oldDeliveryDirect == 1 && deliveryDirect == 1){
			$("li.zf").removeClass("none");
			$("li.ztp").addClass("none");
			$("input[name='takeTraderId']").val($("input[name='oldTakeTraderId']").val());
			$("input[name='takeTraderName']").val($("input[name='oldTakeTraderName']").val());
			$("input[name='takeTraderContactId']").val($("input[name='oldTakeTraderContactId']").val());
			$("input[name='takeTraderAddressId']").val($("input[name='oldTakeTraderAddressId").val());
			$("input[name='takeTraderArea']").val($("input[name='oldTakeTraderArea']").val());
			$("input[name='takeTraderAddress']").val($("input[name='oldTakeTraderAddress']").val());
			$("input[name='takeTraderContactName']").val($("input[name='oldTakeTraderContactName']").val());
			$("input[name='takeTraderContactMobile']").val($("input[name='oldTakeTraderContactMobile']").val());
			$("input[name='takeTraderContactTelephone']").val($("input[name='oldTakeTraderContactTelephone']").val());
		}
	})
	
})

function changComments(obj,buyorderGoodsId) {
	checkLogin();
	var insideComments = $(obj).val();
	if(insideComments != ''&& insideComments != undefined){
		$(obj).siblings("input[name='insideCommentsArray']").val(buyorderGoodsId+"|"+insideComments);
	}
	
}

function editSubmit(){
	checkLogin();
	var $form = $("#myform");
	$("form").find('.warning').remove();
	var isUpdateDeliveryDirect=$('#isUpdateDeliveryDirect').val();
	var oldDeliveryDirect = $("input[name='oldDeliveryDirect']").val();
	var deliveryDirect=$('input:radio[name="deliveryDirect"]:checked').val();
	if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 1){
		warnErrorTips("isUpdateDeliveryDirect","isUpdateDeliveryDirectError","采购单关联多个销售单不允许改成直发");
		$('input:radio:first').attr('checked', 'true');
		return false;
	}else if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 2){
		warnErrorTips("isUpdateDeliveryDirect","isUpdateDeliveryDirectError","采购单关联的销售商品含普发商品不允许改成直发");
		$('input:radio:first').attr('checked', 'true');
		return false;
	}else if(oldDeliveryDirect == 0 && deliveryDirect == 1 && isUpdateDeliveryDirect == 0){
		$("li.ptz").removeClass("none");
		$("li.pf").addClass("none");
		
    	/*<input type="hidden" id="ptz_takeTraderId" value="${bv.saleTakeTraderId}">
    	<input type="hidden" id="ptz_takeTraderName" value="${bv.saleTakeTraderName}">
    	<input type="hidden" id="ptz_takeTraderContactId" value="${bv.saleTakeTraderContactId}">
    	<input type="hidden" id="ptz_takeTraderContactName" value="${bv.saleTakeTraderContactName}">
    	<input type="hidden" id="ptz_takeTraderContactMobile" value="${bv.saleTakeTraderContactMobile}">
    	<input type="hidden" id="ptz_takeTraderContactTelephone" value="${bv.saleTakeTraderContactTelephone}">
    	<input type="hidden" id="ptz_takeTraderAddressId" value="${bv.saleTakeTraderAddressId}">
    	<input type="hidden" id="ptz_takeTraderArea" value="${bv.saleTakeTraderArea}">
    	<input type="hidden" id="ptz_takeTraderAddress" value="${bv.saleTakeTraderAddress}">*/
		
		$("input[name='takeTraderId']").val($("#ptz_takeTraderId").val());
		$("input[name='takeTraderName']").val($("#ptz_takeTraderName").val());
		$("input[name='takeTraderContactId']").val($("#ptz_takeTraderContactId").val());
		$("input[name='takeTraderAddressId']").val($("#ptz_takeTraderAddressId").val());
		$("input[name='takeTraderArea']").val($("#ptz_takeTraderArea").val());
		$("input[name='takeTraderAddress']").val($("#ptz_takeTraderAddress").val());
		$("input[name='takeTraderContactName']").val($("#ptz_takeTraderContactName").val());
		$("input[name='takeTraderContactMobile']").val($("#ptz_takeTraderContactMobile").val());
		$("input[name='takeTraderContactTelephone']").val($("#ptz_takeTraderContactTelephone").val());
		
	}else if(oldDeliveryDirect == 0 && deliveryDirect == 0){
		$("li.ptz").addClass("none");
		$("li.pf").removeClass("none");
		var str = $("#address_pf").val();
		if(str != undefined && str != ''){
			$("input[name='takeTraderName']").val($("#companyName").val());
			$("input[name='takeTraderAddressId']").val(str.split('|')[0]);
			$("input[name='takeTraderArea']").val(str.split('|')[1]);
			$("input[name='takeTraderAddress']").val(str.split('|')[2]);
			$("input[name='takeTraderContactName']").val(str.split('|')[3]);
			$("input[name='takeTraderContactMobile']").val(str.split('|')[4]);
			$("input[name='takeTraderContactTelephone']").val(str.split('|')[5]);
		}
	}else if(oldDeliveryDirect == 1 && deliveryDirect == 0){
		$("li.zf").addClass("none");
		$("li.ztp").removeClass("none");
		var str = $("#address_pf").val();
		if(str != undefined && str != ''){
			$("input[name='takeTraderName']").val($("#companyName").val());
			$("input[name='takeTraderAddressId']").val(str.split('|')[0]);
			$("input[name='takeTraderArea']").val(str.split('|')[1]);
			$("input[name='takeTraderAddress']").val(str.split('|')[2]);
			$("input[name='takeTraderContactName']").val(str.split('|')[3]);
			$("input[name='takeTraderContactMobile']").val(str.split('|')[4]);
			$("input[name='takeTraderContactTelephone']").val(str.split('|')[5]);
		}
	}else if(oldDeliveryDirect == 1 && deliveryDirect == 1){
		$("li.zf").removeClass("none");
		$("li.ztp").addClass("none");
		$("input[name='takeTraderId']").val($("input[name='oldTakeTraderId']").val());
		$("input[name='takeTraderName']").val($("input[name='oldTakeTraderName']").val());
		$("input[name='takeTraderContactId']").val($("input[name='oldTakeTraderContactId']").val());
		$("input[name='takeTraderAddressId']").val($("input[name='oldTakeTraderAddressId").val());
		$("input[name='takeTraderArea']").val($("input[name='oldTakeTraderArea']").val());
		$("input[name='takeTraderAddress']").val($("input[name='oldTakeTraderAddress']").val());
		$("input[name='takeTraderContactName']").val($("input[name='oldTakeTraderContactName']").val());
		$("input[name='takeTraderContactMobile']").val($("input[name='oldTakeTraderContactMobile']").val());
		$("input[name='takeTraderContactTelephone']").val($("input[name='oldTakeTraderContactTelephone']").val());
	}
		
	var logisticsComments = $("#logisticsComments").val();
	if(logisticsComments != '' && logisticsComments.length>256){
		warnTips("logisticsComments","物流备注不允许超过256个字符");
		return false;
	}
	
	var invoiceComments = $("#invoiceComments").val();
	if(invoiceComments != '' && invoiceComments.length>256){
		warnTips("invoiceComments","开票备注不允许超过256个字符");
		return false;
	}	
	
	$form.submit();
}

