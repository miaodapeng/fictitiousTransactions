function search(){
	checkLogin();
	$(".warning").remove();
	$("input").removeClass("errorbor");
	if($("#searchName").val()==''){
		warnErrorTips("searchName","searchNameError","搜索内容不允许为空");//文本框ID和提示用语
		return false;
	}
	$("#myform").submit();
	return false;
}

function selectObj(engineerId,name,mobile){
	checkLogin();
	$("#searchName",window.parent.document).hide();
	$("#search1",window.parent.document).hide();
	$("#selname",window.parent.document).show().val(name+"/"+mobile);
	$("#search2",window.parent.document).show();
	$("#selname",window.parent.document).html(name+"/"+mobile);
	$("#engineerId",window.parent.document).val(engineerId);
	$("#name",window.parent.document).val(name);
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index);
}

