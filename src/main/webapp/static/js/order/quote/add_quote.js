function subQuote(){
	checkLogin();
	var $form = $("#addQuoteForm");

	clearErroeMes();//清除錯誤提示信息
	
	$("#isPolicymaker").val($("input[name='isPolicymakerRad']:checked").val());//是否采购关键人
	$("#purchasingType").val($("input[name='purchasingTypeRad']:checked").val());//采购方式
	$("#paymentTerm").val($("input[name='paymentTermRad']:checked").val());//付款条件
	$("#purchasingTime").val($("input[name='purchasingTimeRad']:checked").val());//采购时间
	
	var traderContactInfo = $("#traderContactInfo").val();
	if (traderContactInfo.length==0) {
		warn2Tips("traderContactInfo","联系人不允许为空");//文本框ID和提示用语
		return false;
	}else{
		var obj = $("#traderContactInfo").val().split("!@#");
		$("#traderContactId").val(obj[0]);
		$("#traderContactName").val(obj[1]);
		$("#mobile").val(obj[2]);
		$("#telephone").val(obj[3]);
	}
	
	var address = $("#address").val();
	/*if (address==undefined || address.length==0) {
		warnTips("address","联系地址不允许为空");//文本框ID和提示用语
		return false;
	}else{
		$("#traderAddressId").val($("#address :selected").attr("id"));
	}*/
	if (address!=undefined || address.length!=0) {
		$("#traderAddressId").val($("#address :selected").attr("id"));
	}
	
	if ($("#isPolicymaker").val()=="") {
		warnTips("isPolicymaker","联系人情况不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#purchasingType").val()=="") {
		warnTips("purchasingType","采购方式不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#paymentTerm").val()=="") {
		warnTips("paymentTerm","付款条件不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#purchasingTime").val()=="") {
		warnTips("purchasingTime","采购时间不允许为空");//文本框ID和提示用语
		return false;
	}
	var projectProgress = $("#projectProgress").val();
	if(projectProgress!="" && projectProgress.length>256){
		warnTips("projectProgress","项目进展情况长度应该在0-256个字符之间");//文本框ID和提示用语
		return false;
	}
	
	/*if($("#customerNature").val() != 466){
		if($("#showTerminalInfo").is(":hidden")){
			warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
			return false;
		}
		
		var salesAreaId = $("#salesAreaId").val();
		if(salesAreaId==undefined || salesAreaId==""){
			warnTips("salesAreaDiv","销售区域不允许为空");//文本框ID和提示用语
			return false;
		}
		
		var salesArea = $("#salesArea").val();
		if(salesArea==undefined || salesArea==""){
			warnTips("salesAreaDiv","销售区域不允许为空");//文本框ID和提示用语
			return false;
		}
	}*/
	$form.submit();
	/*$.ajax({
		async:false,
		url:'./saveQuote.do',
		data:$form.serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				layer.alert('操作成功', {
					  closeBtn: 0,
					  btn: ['确定'] //按钮
					}, function(){
						window.location.href = page_url + '/order/quote/getQuoteDetail.do?quoteorderId='+data.data.quoteorderId;
					});
			}
		}
	})*/
	return false;
}


function searchTerminal(){
	checkLogin();
	clearErroeMes();//清除錯誤提示信息
	
	var searchTraderName = $("#searchTraderName").val()==undefined?"":$("#searchTraderName").val();
/*	if(searchTraderName==""){
		warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
		return false;
	}*/
	$("#terminalDiv").attr('layerParams','{"width":"70%","height":"80%","title":"报价终端","link":"'+ page_url+'/trader/customer/getTerminalList.do?searchTraderName=' + encodeURI(searchTraderName) + '&optType=addQuoteTerminal"}');
	$("#terminalDiv").click();
}

function agingSearchTerminal(){
	checkLogin();
	$("#searchTraderName").val("");
	$("#showTerminalInfo").find("input").each(function(i){
		$(this).val("");
	});
	$("#showTerminalInfo").hide();
	$("#searchTerminalInfo").show();
}
