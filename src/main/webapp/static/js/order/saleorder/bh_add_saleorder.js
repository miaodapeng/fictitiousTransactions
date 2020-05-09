$(function(){
	
	
});


function checkAddBhSaleorderForm(){
	checkLogin();
	clearErroeMes();//清除錯誤提示信息
	
	var prepareComments = $("#prepareComments").val();
	if(prepareComments!="" && prepareComments.length>256){
		warnTips("prepareComments","申请原因长度应该在0-256个字符之间");
		return false;
	}
	
	var marketingPlan = $("#marketingPlan").val();
	if(marketingPlan!="" && marketingPlan.length>256){
		warnTips("marketingPlan","后期营销计划长度应该在0-256个字符之间");
		return false;
	}
	
	return true;
}