
function confirmSubmit(){
	checkLogin();
	var rOrdergoodsLaunchGoodsJCategoryId = $("#rOrdergoodsLaunchGoodsJCategoryId").val();
	
	if(rOrdergoodsLaunchGoodsJCategoryId == ''){
		layer.alert("操作失败，请刷新后重试！");
		return false;
	}
	if($("input[name='categoryId']:checked").length == 0){
		layer.alert("对应的试剂分类不允许为空");
		return false;
	}
	
	var ordergoodsCategoryIds = "";
	$.each($("input[name='categoryId']:checked"),function(i,n){
		ordergoodsCategoryIds += $(this).val()+",";
	});
	$.ajax({
		async:false,
		url:'./saveeditequipmentcategory.do',
		data:{rOrdergoodsLaunchGoodsJCategoryId:rOrdergoodsLaunchGoodsJCategoryId,ordergoodsCategoryIds:ordergoodsCategoryIds},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				window.parent.location.reload();
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}

