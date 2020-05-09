function setDisabled(id,status){
	checkLogin();
	if(id > 0){
		var msg = "";
		if(status == 1){
			msg = "是否启用该账号？";
		}
		if(status == 0){
			msg = "是否禁用该账号？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changeaccountenable.do",
				data: {'ordergoodsStoreAccountId':id},
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

//重置密码
function resetPassword(id){
	checkLogin();
	if(id > 0){
		layer.confirm("确定重置密码？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				var div = '<div class="tip-loadingNewData" id="loadingNewDataResetPassword" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			    $("body").prepend(div); //jq获取上一页框架的父级框架；
				$.ajax({
					type: "POST",
					url: "./resetpassword.do",
					data: {'ordergoodsStoreAccountId':id},
					dataType:'json',
					success: function(data){
						$("#loadingNewDataResetPassword").remove();
						if(data.code == 0){
							layer.alert("重置密码成功");
						}else{
							layer.alert("操作失败");
						}
					},
					error:function(data){
						$("#loadingNewDataResetPassword").remove();
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			}, function(){
			});
	}
}