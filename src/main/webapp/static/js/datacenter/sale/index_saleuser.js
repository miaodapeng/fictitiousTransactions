function userSearch() {
	/*clearErroeMes();//清除錯誤提示信息
	if($("#searchContent").val()==undefined || $("#searchContent").val()==""){
		warnTips("errorMes","查询条件不能为空");//文本框ID和提示用语
		$("#searchContent").addClass("errorbor");
		return false;
	}*/
	$("#userSearch").submit();
}

function exportAfterGoodsDetailList(){
	if(confirm("确定导出售后商品明细?")){
		checkLogin();
		location.href = page_url + '/report/service/exportAfterGoodsDetailList.do?startDate=' + $("#startDate").val() + "&startDate=" +$("#startDate").val();
	}
}

function exportBussinessDetailList(){
	if(confirm("确定导出商机明细?")){
		checkLogin();
		location.href = page_url + '/report/service/exportBussinessDetailList.do?startDate=' + $("#startDate").val() + "&endDate=" +$("#endDate").val();
	}
}

function exportGoodsCodeDetailList(){
	if(confirm("确定导出商品识别码维度明细表?")){
		checkLogin();
		location.href = page_url + '/report/service/exportGoodsCodeDetailList.do?startDate=' + $("#startDate").val() + "&endDate=" +$("#endDate").val();
	}
}