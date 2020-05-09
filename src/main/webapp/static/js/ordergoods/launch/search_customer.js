function search(){
	checkLogin();
	$(".warning").remove();
	$("input").removeClass("errorbor");
	if($("#searchName").val()==''){
		warnErrorTips("searchName","searchNameError","查询条件不能为空");//文本框ID和提示用语
		return false;
	}
	$("#myform").submit();
	return false;
}

function selectObj(traderCustomerId,name,traderId,area){
	checkLogin();
	$("#traderName",window.parent.document).html(name);
	$("#traderCustomerId",window.parent.document).val(traderCustomerId);
	$("#traderId",window.parent.document).val(traderId);
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	window.parent.init(traderCustomerId,traderId,area);
    parent.layer.close(index);
}