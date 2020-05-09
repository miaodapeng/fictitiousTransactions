function applyValidCustomer(traderCustomerId,taskId){
	checkLogin();
	var formToken = $("input[name='formToken']").val();
	layer.confirm("您是否确认申请审核该用户？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editApplyValidCustomer.do",
				data: {'traderCustomerId':traderCustomerId,'taskId':taskId,'formToken':formToken},
				dataType:'json',
				success: function(data){
					if (data.code == 0) {
						window.location.reload();
					} else {
						layer.alert(data.message);
					}
					
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


function restVerify(traderCustomerId){
	checkLogin();
	if(traderCustomerId > 0){
		layer.confirm("您是否重置为待审核？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./restverify.do",
					data: {'traderCustomerId':traderCustomerId},
					dataType:'json',
					success: function(data){
						if (data.code == 0) {
							window.location.reload();
						} else {
							layer.alert(data.message);
						}
						
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