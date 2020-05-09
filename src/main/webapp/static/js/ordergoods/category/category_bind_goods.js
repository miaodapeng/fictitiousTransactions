function search2() {
	checkLogin();
	if($("#searchContent").val()==undefined || $("#searchContent").val()==""){
		layer.alert("查询条件不能为空");//文本框ID和提示用语
		return false;
	}
	$("#searchContent").val($("#searchContent").val().trim());
	$("#search").submit();
}

//产品绑定分类
function selectGoods(ordergoodsCategoryId){
	checkLogin();
	if(ordergoodsCategoryId > 0){
		if($("input[name='checkOne']:checked").length == 0){
			layer.alert("产品不能为空");
			return false;
		}
		
		var ids = "";
		$.each($("input[name='checkOne']:checked"),function(i,n){
			ids += $(this).val()+",";
		});
		
		if(ids != ""){
			$.ajax({
				type : "POST",
				url : "./savebindcategorygoods.do",
				data : {
					'ordergoodsCategoryId' : ordergoodsCategoryId,
					'ids':ids
				},
				dataType : 'json',
				success : function(data) {
					if(data.code == 0){
						refreshPageList(data);
					}else{
						layer.alert("操作失败");
					}
				},
				error : function(data) {
					if (data.status == 1001) {
						layer.alert("当前操作无权限");
					}
				}
			});
		}
	}
}