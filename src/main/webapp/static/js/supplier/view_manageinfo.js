/*
 * 启用
 */
function setDisabled(userId,status){
	checkLogin();
	if(userId > 0&&status == 1){
		layer.confirm("是否启用该供应商？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/supplier/isDisabledSupplier.do",
				data: {'id':userId,'isDisabled':status},
				dataType:'json',
				success: function(data){
					if(data.code != 0){
						var msg = data.message != '' ? data.message : '操作失败';
//						$("form :input").eq(0).focus();
						layer.alert(msg);
						return false;
					}
					
					location.reload();
					
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