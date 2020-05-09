function searchTerminal(){
	checkLogin();
//	$("#updateTerminalInfo").find("#errorMes").removeClass("errorbor");
	delWarnTips("errorTxtMsg");
	
	var searchTraderName = $("#updateTerminalInfo").find("#searchTraderName").val()==undefined?"":$("#updateTerminalInfo").find("#searchTraderName").val();
/*	if(searchTraderName==""){
		warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
		return false;
	}*/
	$("#updateTerminalInfo").find("#terminalDiv").attr('layerParams','{"width":"800px","height":"300px","title":"客户信息","link":"'+ page_url+'/trader/customer/searchCustomerList.do?searchTraderName=' + encodeURI(searchTraderName) + '&indexId=1"}');
	$("#updateTerminalInfo").find("#terminalDiv").click();
}


function addSubmit(){
	checkLogin();
	var $form = $("#addForm");
	$("form :input").parents('li').find('.warning').remove();
	
	if ($("select[name='traderContactId']").val() == 0) {
		warnTips("traderContactIdMsg","联系人不允许为空");
		return false;
	}
	if ($("select[name='traderAddressId']").val() == 0) {
		warnTips("traderAddressIdMsg","联系地址不允许为空");
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
	$form.submit();
	//return false;
}
