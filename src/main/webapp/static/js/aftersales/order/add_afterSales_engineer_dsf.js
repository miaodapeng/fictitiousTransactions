$(document).ready(function(){
	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		if($("#engineerId").val()==''){
			warnErrorTips("searchName","searchNameError","工程师不允许为空");//文本框ID和提示用语
			return false;
		}
		if($("#serviceTime").val()==''){
			warnErrorTips("serviceTime","serviceTimeError","服务时间不允许为空");//文本框ID和提示用语
			return false;
		}
		var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		var engineerAmount = $("#engineerAmount").val();
		if(engineerAmount ==''){
			warnErrorTips("engineerAmount","engineerAmountError","工程师酬金不允许为空");//文本框ID和提示用语
			return false;
		}
		if(!priceReg.test(engineerAmount) && engineerAmount != 0 && engineerAmount != 0.0 && engineerAmount != 0.00){
			warnErrorTips("engineerAmount","engineerAmountError","工程师酬金输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}
		
	});

});

function search(){
	checkLogin();
	var searchName = $("#searchName").val()==undefined?"":$("#searchName").val();
	var areaId = $("#areaId").val();
	if($("#searchName").val()==''){
		warnErrorTips("searchName","searchNameError","搜索内容不允许为空");//文本框ID和提示用语
		return false;
	}
	var searchUrl = page_url+"/aftersales/order/getEngineerPage.do?searchName="+encodeURI(searchName)+"&areaId="+areaId;
	$("#popEngineer").attr('layerParams','{"width":"900px","height":"500px","title":"搜索工程师","link":"'+searchUrl+'"}');
	$("#popEngineer").click();
	
}

function research(){
	checkLogin();
	$("#searchName").show();
	$("#search1").show();
	$("#selname").hide();
	$("#search2").hide();
	$("#selname").val("");
	$("#engineerId").val("");
	$("#name").val("");
}

function research1(){
	checkLogin();
	$("#inp").show();
	$("#search1").show();
	$("#selname").hide();
	$("#search2").hide();
	$("#selname").val("");
	$("#engineerId").val("");
	$("#name").val("");
}
