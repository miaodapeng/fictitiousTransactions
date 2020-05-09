function search() {
	checkLogin();
	if($("#searchContent").val()==undefined || $("#searchContent").val()==""){
		warnErrorTips("searchContent","searchNameError","查询条件不能为空");//文本框ID和提示用语
		return false;
	}
	$("#searchContent").val($("#searchContent").val().trim());
	$("#myform").submit();
}

function selectObj(rOrdergoodsLaunchGoodsJCategoryId,name,categoryNames){
	checkLogin();
	$("#goodsName",window.parent.document).html(name);
	$("#categoryNames",window.parent.document).html(categoryNames);
	$("#rOrdergoodsLaunchGoodsJCategoryId",window.parent.document).val(rOrdergoodsLaunchGoodsJCategoryId);
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index);
}