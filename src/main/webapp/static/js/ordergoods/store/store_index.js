function setDisabled(ordergoodsStoreId,status){
	checkLogin();
	if(ordergoodsStoreId > 0){
		var msg = "";
		if(status == 1){
			msg = "是否启用该店铺？";
		}
		if(status == 0){
			msg = "是否禁用该店铺？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changeenable.do",
				data: {'ordergoodsStoreId':ordergoodsStoreId},
				dataType:'json',
				success: function(data){
					refreshNowPageList(data)
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}, function(){
		});
	}
	
}