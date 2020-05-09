function search() {
	checkLogin();
	if($("#searchTraderName").val()==undefined || $("#searchTraderName").val()==""){
		warnTips("searchError","请输入查询条件");//文本框ID和提示用语
		return false;
	}
	$("#searchTraderName").val($("#searchTraderName").val().trim());
	$("#search").submit();
}

/* salesAreaId销售区域ID；salesArea销售区域；terminalTraderId终端ID，terminalTraderName终端名称，terminalTraderType终端类型*/
function selectTerminal(salesAreaId,salesArea,terminalTraderId,terminalTraderName,terminalTraderType,terminalTraderTypeStr){
	checkLogin();
	
	parent.$("#updateTerminalInfo").find("#terminalTraderNameDiv").html(terminalTraderName);
//	parent.$("#terminalInfo").find("#terminalTraderTypeDiv").html(terminalTraderTypeStr);
//	parent.$("#terminalInfo").find("#salesAreaDiv").html(salesArea);
	
	parent.$("#quotePayMoneForm").find("#terminalTraderId").val(terminalTraderId);
	parent.$("#quotePayMoneForm").find("#terminalTraderName").val(terminalTraderName);
	parent.$("#quotePayMoneForm").find("#terminalTraderType").val(terminalTraderType);
	
	parent.$("#editForm").find("#terminalTraderId").val(terminalTraderId);
	parent.$("#editForm").find("#terminalTraderName").val(terminalTraderName);
	parent.$("#editForm").find("#terminalTraderType").val(terminalTraderType);
	
//	parent.$("#terminalInfo").find("#salesArea").val(salesArea);
//	parent.$("#terminalInfo").find("#salesAreaId").val(salesAreaId);
	parent.$("#updateTerminalInfo").find("#terminalNameDetail").show();
	parent.$("#updateTerminalInfo").find("#terminalNameCheck").hide();
	
	if(parent.layer!=undefined){
		parent.layer.close(index);
	}
	
	/*var optType = $("#optType").val();
	if(optType=="editQuoteTerminal"){
		$.ajax({
			async:false,
			url: page_url + '/order/quote/updateQuoteTerminal.do',
			data:{"quoteorderId":parent.$("#quoteorderId").val(),"salesAreaId":salesAreaId,"salesArea":salesArea,"terminalTraderType":terminalTraderType,"terminalTraderId":terminalTraderId,"terminalTraderName":terminalTraderName},
			type:"POST",
			dataType : "json",
			success:function(data){
				parent.location.reload();
			}
		})
		return false;
	}else{
		parent.$("#showTerminalInfo").find("#terminalTraderNameDiv").html(terminalTraderName);
		parent.$("#showTerminalInfo").find("#terminalTraderTypeDiv").html(terminalTraderTypeStr);
		parent.$("#showTerminalInfo").find("#salesAreaDiv").html(salesArea);
		
		parent.$("#showTerminalInfo").find("#terminalTraderId").val(terminalTraderId);
		parent.$("#showTerminalInfo").find("#terminalTraderName").val(terminalTraderName);
		parent.$("#showTerminalInfo").find("#terminalTraderType").val(terminalTraderType);
		
		parent.$("#showTerminalInfo").find("#salesArea").val(salesArea);
		parent.$("#showTerminalInfo").find("#salesAreaId").val(salesAreaId);
		parent.$("#showTerminalInfo").show();
		parent.$("#searchTerminalInfo").hide();
		
		if(parent.layer!=undefined){
			parent.layer.close(index);
		}
	}*/
	
}

function switchInput(){
	checkLogin();
	$(".searchfunc").hide();
	$("#selectTerminalInfo").hide();
	$("#InputTerminalInfo").show();
}

function switchSelect(){
	checkLogin();
	$("#selectTerminalInfo").show();
	$(".searchfunc").show();
	$("#InputTerminalInfo").hide();
}

function saveTerminal(){
	checkLogin();
	var terminalTraderName = $("#terminalTraderName").val();
	var terminalTraderType = $("#terminalTraderType").val();
	var terminalTraderTypeStr = $("#terminalTraderType :selected").text()=="请选择"?"":$("#terminalTraderType").text();
	
	parent.layer.close(index);
	
	parent.$("#updateTerminalInfo").find("#terminalTraderNameDiv").html(terminalTraderName);
	
	parent.$("#quotePayMoneForm").find("#terminalTraderName").val(terminalTraderName);
	parent.$("#quotePayMoneForm").find("#terminalTraderId").val("");
	parent.$("#quotePayMoneForm").find("#terminalTraderType").val(terminalTraderType);
	
	parent.$("#updateTerminalInfo").find("#terminalNameDetail").show();
	parent.$("#updateTerminalInfo").find("#terminalNameCheck").hide();
}

function clearErrMes(){
	checkLogin();
	$("#InputTerminalInfo :input").parents('li').find('.warning').remove();
	$("#InputTerminalInfo :input").removeClass("errorbor");
	
}