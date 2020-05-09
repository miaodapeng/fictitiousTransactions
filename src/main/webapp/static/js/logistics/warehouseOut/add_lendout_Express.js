$(document).ready(function(e) {
	var logisticsName = $("#logisticsId").find("option:selected").text();
	if(logisticsName=="顺丰速运" || logisticsName=="中通快递"){
		if(logisticsName=="顺丰速运"){
			$("#sfkd").show();
		}else{
			$("#sfkd").hide();
		}
		$("#lno").hide();
	}else{
		$("#sfkd").hide();
		$("#lno").show();
	}
	$("#logisticsId").change(function(){
		var logisticsName = $("#logisticsId").find("option:selected").text();
		if(logisticsName=="顺丰速运" || logisticsName=="中通快递"){
			if(logisticsName=="顺丰速运"){
				$("#sfkd").show();
			}else{
				$("#sfkd").hide();
			}
			$("#lno").hide();
		}else{
			$("#sfkd").hide();
			$("#lno").show();
		}
    });
	
	/*******编辑快递页面初始化********/
	var _paymentType = $("#_paymentType").val();
	if(_paymentType!=""){
		$("#paymentType"+_paymentType).attr('checked','true');
	}
	var _business_type = $("#_business_type").val();
	if(_business_type!= undefined){
		var add = $('#business_type').val(_business_type);
		add.attr('selected',true);
	}
	var _isProtectPrice = $("#_isProtectPrice").val();
	if(_isProtectPrice!=""){
		$("#isProtectPrice"+_isProtectPrice).attr('checked','true');
	}
	var _isReceipt = $("#_isReceipt").val();
	if(_isReceipt!=""){
		$("#isReceipt"+_isReceipt).attr('checked','true');
	}
	
	$("#isProtectPrice1").click(function(){
		$("#bjje").show();
	});
	$("#isProtectPrice0").click(function(){
		$("#bjje").hide();
	});
})

var status = 0;
$('#deliveryTimes').blur(function () {  
	var deliveryTimes = $("input[name='deliveryTimes']").val();
	if(deliveryTimes == undefined || deliveryTimes == ""){
		warnTips("deliveryTimes", "发货时间不允许为空");
		return false;
	} 
});  
if($("input[name='b_checknox']").length > 0){
	$.each($("input[name='b_checknox']").not(':disabled'),function(){
		if($(this).is(":checked")==false){
			status = 1;
		}
	});
	if(status == 1){
		
		$("input[name='all_b_checknox']").prop('checked',false);
	}else{
		$("input[name='all_b_checknox']").prop("checked",true);
	}
}
function addExpress(ywType,orderId){
	checkLogin();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var logisticsNo = $("input[name='logisticsNo']").val();

	var isInvoicing = $("input[name='isInvoicing']").val();
	var logisticsId = $("#logisticsId").val();
	var lendOutId = $("input[name='lendOutId']").val();
	var deliveryTimes = $("input[name='deliveryTimes']").val();
	var amount = $("input[name='amount']").val();
	var logisticsComments = $("input[name='logisticsComments']").val();
	var expressId = $("input[name='expressId']").val();
	var beforeParams = $("input[name='beforeParams']").val(); 
	var cardnumber = $("#cardnumber").val();
	var paymentType = $("input[name='paymentType']:checked").val();
	var business_type = $("#business_type").val();
	var realWeight = $("#realWeight").val();
	var _num = $("#_num").val();
	var amountWeight = $("#amountWeight").val();
	var mailGoods = $("#mailGoods").val();
	var mailGoodsNum = $("#mailGoodsNum").val();
	var isProtectPrice = $("input[name='isProtectPrice']:checked").val();
	var protectPrice = $("#protectPrice").val();
	var isReceipt = $("input[name='isReceipt']:checked").val();
	var mailCommtents =$("#mailCommtents").val();
	var logisticsName = $("#logisticsId").find("option:selected").text();
	if(logisticsName!="顺丰速运"){
		cardnumber=paymentType= business_type=realWeight=realWeight=realWeight=amountWeight=mailGoods=mailGoodsNum=isProtectPrice=protectPrice=isReceipt=mailCommtents="";
	}
	if(logisticsName!="顺丰速运" && logisticsName!="中通快递" ){
		if (logisticsNo == undefined || logisticsNo == ""){
			warnTipss("logisticsNo", "快递单号不允许为空");
			return false;
		}
		if (logisticsNo.length > 32 ){
			warnTips("logisticsNo", "快递单号不允许超过32个字符");
			return false;
		}
	}
	/*if(deliveryTimes == undefined || deliveryTimes == ""){
		warnTips("deliveryTimes", "发货时间不允许为空");
		return false;
	}*/
	if (amount == undefined || amount == "") {
		warn2Tips("amount", "费用不允许为空");
		return false;
	}
	if(Number(amount)>10000000){
		warnTips("amount","费用不允许超过一千万");//文本框ID和提示用语
		return false;
	}
	if(amount.length>0 && !reg.test(amount)){
		warn2Tips("amount","费用输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		return false;
	}
	if (logisticsComments.length > 1024 ){
		warn2Tips("logisticsComments", "物流备注不允许超过1024个字符");
		return false;
	}
	if(logisticsName=="顺丰速运" && cardnumber==""){
		warn2Tips("cardnumber", "月结卡号不允许为空");
		return false;
	}
	if(logisticsName=="顺丰速运" && realWeight==""){
		warn2Tips("realWeight", "实际重量不允许为空");
		return false;
	}
	if( _num==""){
		warn2Tips("_num", "件数不允许为空");
		return false;
	}
	if(logisticsName=="顺丰速运" && amountWeight==""){
		warn2Tips("amountWeight", "计费重量不允许为空");
		return false;
	}
	if(logisticsName=="顺丰速运" && mailGoods==""){
		warn2Tips("mailGoods", "托寄物不允许为空");
		return false;
	}
	if(logisticsName=="顺丰速运" && mailGoodsNum==""){
		warn2Tips("mailGoodsNum", "托寄物数量不允许为空");
		return false;
	}
	if(logisticsName=="顺丰速运" && isProtectPrice=="1"){
		if(protectPrice==""){
			warn2Tips("protectPrice", "报价金额不允许为空");
			return false;
		}
	}else if(logisticsName=="顺丰速运" && isProtectPrice=="0"){
		protectPrice ="";
	}
	var id_num_price = "";
	$.each($("input[name='b_checknox']:checked"),function(){
		id_num_price += $(this).val()+"|"+$(this).parent().nextAll().find("input[name='num']").val()+"|"+$(this).parent().nextAll().find("input[name='price']").val()+"_";
	});
	if(id_num_price == ""){
		$("form :input").parents('li').find('.warning').remove();
		$("form :input").removeClass("errorbor");
		layer.alert("请选择产品");
		return false;
	}
	var formToken = $("input[name='formToken']").val();
	$.ajax({
		async : false,
		url : page_url + '/logistics/warehousein/saveExpress.do',
		data : {
			"logisticsNo" : logisticsNo,
			"isInvoicing" : isInvoicing,
			"logisticsId" : logisticsId,
			"logisticsComments" : logisticsComments,
			"deliveryTimes" : deliveryTimes,
			"amount" : amount,
			"id_num_price" : id_num_price,
			"expressId" : expressId,
			"flag":ywType,
			"orderId":lendOutId,
			"beforeParams" : beforeParams,
			"cardnumber":cardnumber,
			"paymentType":paymentType,
			"business_Type":business_type,
			"realWeight":realWeight,
			"j_num":_num,
			"amountWeight":amountWeight,
			"mailGoods":mailGoods,
			"mailGoodsNum":mailGoodsNum,
			"isProtectPrice":isProtectPrice,
			"protectPrice":protectPrice,
			"isReceipt":isReceipt,
			"mailCommtents":mailCommtents,
			"formToken": formToken
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				var msg = "美年订单发送成功";
				if (data.status == 1){
					msg = "美年订单发送失败";
				}
				layer.alert("操作成功！", { icon : 1},function(index){
					pagesContrlpages(true,false,true)
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}
			
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

function checkNum(obj,allnum,addnum,nownum){
	checkLogin();
	var re = /^[0-9]+$/;
	var num = $(obj).val();
	if (num == undefined || num == "") {
		layer.alert("快递发货数量不允许为空");
		$(obj).val(0);
		return false;
	}
	if(!re.test(num)){
		layer.alert("快递发货数量必须为正整数");//文本框ID和提示用语
		$(obj).val(0);
		return false;
	}
	if(num>(allnum)){
		layer.alert("超出快递最大发货数量"+allnum+"，请修改！");//文本框ID和提示用语
		$(obj).val(nownum);
		return false;
	}
}
//全选
function selectall(obj){
	checkLogin();
	if($(obj).is(":checked")){
		$("input[type='checkbox']").not(':disabled').prop("checked",true);
		if($("input[alt='mqixie']:checked").length > 1){
			layer.alert("美年的器械设备只能一个一个的增加快递");
			$("input[alt='mqixie']").prop('checked', false);
			$(obj).prop('checked', false);
			return false;
		}
		}else{
			$("input[type='checkbox']").not(':disabled').prop('checked',false);
	}
}
function canelAll(obj,name) {
	checkLogin();
	
	var status = 0;
	$.each($("input[name='"+name+"']"), function() {
		if ($(this).is(":checked") == false) {
			status = 1;
		}
	});
	if (status == 1) {
		$("input[name='all_"+name+"']").prop('checked', false);
	} else {
		if($("input[alt='mqixie']:checked").length > 1){
			layer.alert("美年的器械设备只能一个一个的增加快递");
			$(obj).prop('checked', false);
			return false;
		}
		$("input[name='all_"+name+"']").prop("checked", true);
	}

}
function checkbarcode(saleorderId){
	checkLogin();
	var date="";
	$("input[id='out_checkbox']").each(function(){
		date += $(this).attr("name")+"_";
	});
	$.ajax({
		async : false,
		url : page_url + '/warehouse/warehousesout/getGoodsExpress.do',
		data : {
			"outGoodsTime" : date,
			"saleorderId" : saleorderId
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			var list = data.data;
			console.log(data);
			console.log(list);
			/*if(data.code == 0){
				layer.alert("操作成功", { icon : 1},function(index){
					pagesContrlpages(true,false,true)
				});
			}else{
				layer.alert(data.message, { icon : 2});
			}*/
			
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}
function warnTipss(id,txt){
	checkLogin();
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
	$("#"+id).after('<br/><div class="warning" style="margin-top: 3px;">'+txt+'</div>');
	$("#"+id).focus();
	$("#"+id).addClass("errorbor");
	return false;
}


function editExpress(type, invoiceId)
{
	checkLogin();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	// 快递编号
	var logisticsNo = $("input[name='logisticsNo']").val();
	
	var logisticsName = $("#logisticsId").find("option:selected").text();
	// 快递公司
	var logisticsId = $("#logisticsId").val();
	// 发货时间
	var deliveryTimes = $("input[name='deliveryTimes']").val();
	// 快递费用
//	var amount = $("input[name='amount']").val();
	// 快递备注
	var logisticsComments = $("input[name='logisticsComments']").val();
	// 快递编号Id
	var expressId = $("input[name='expressId']").val();
	// 之前参数
//	var beforeParams = $("input[name='beforeParams']").val();
	if(type == '3'){
        if (logisticsNo == undefined || logisticsNo == ""){
            warnTipss("logisticsNo", "快递单号不允许为空");
            return false;
        }
        if (logisticsNo.length > 32 ){
            warnTips("logisticsNo", "快递单号不允许超过32个字符");
            return false;
        }
	}else if(logisticsName!="顺丰速运" && logisticsName!="中通快递" ){
		if (logisticsNo == undefined || logisticsNo == "")
		{
			warnTipss("logisticsNo", "快递单号不允许为空");
			return false;
		}
		if (logisticsNo.length > 32 ){
			warnTips("logisticsNo", "快递单号不允许超过32个字符");
			return false;
		}
	}
    
//	if (amount == undefined || amount == "") 
//	{
//		warn2Tips("amount", "费用不允许为空");
//		return false;
//	}
//	if(Number(amount)>10000000)
//	{
//		warnTips("amount","费用不允许超过一千万");//文本框ID和提示用语
//		return false;
//	}
//	if(amount.length>0 && !reg.test(amount))
//	{
//		warn2Tips("amount","费用输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
//		return false;
//	}
	if (logisticsComments.length > 1024 )
	{
		warn2Tips("logisticsComments", "物流备注不允许超过1024个字符");
		return false;
	}
	
	$.ajax({
		async : false,
		url : page_url + '/finance/invoice/updateExpress.do',
		data : {
			"logisticsNo" : logisticsNo,
			"logisticsId" : logisticsId,
			"logisticsComments" : logisticsComments,
			"deliveryTimes" : deliveryTimes,
			"invoiceId" : invoiceId,
			"expressId" : expressId
		},
		type : "POST",
		dataType : "json",
		success : function(data) 
		{
			refreshPageList(data);
			/*if(data.code == 0){
				layer.alert("操作成功");
				pagesContrlpages(true, false, true);
				
			}else{
				layer.alert(data.message, { icon : 2});
			}*/
			
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

