function contractReturnDel(attachmentId){
	checkLogin();
	var formToken = $("input[name='formToken']").val();
	layer.confirm("确认删除该条合同回传吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url + '/order/saleorder/contractReturnDelNotoken.do',
				data: {'attachmentId':attachmentId,'formToken':formToken},
				dataType:'json',
				success: function(data){
					if (data.code == -1) {
						layer.alert(data.message);
					} else {
						window.location.reload();
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


function requestCheck(saleorderId,taskId,contractFileCount){
	$.ajax({
		type: "POST",
		url: page_url + "/order/saleorder/editApplyValidContractReturnNoto.do",
		data: {'saleorderId':saleorderId,'taskId':taskId},
		dataType:'json',
		success: function(data){
			if (data.code == 0) {
				layer.confirm("您是否确认申请审核合同？", {
					  btn: ['确定','取消'] //按钮
					}, function(){
						//提交审核方法
						var formToken = $("input[name='formToken']").val();
						$.ajax({
							type: "POST",
							url: page_url + "/order/saleorder/editApplyValidContractReturn.do",
							data: {'saleorderId':saleorderId,'taskId':taskId,'formToken':formToken},
							dataType:'json',
							success: function(data){
								if (data.code == 0) {
									window.location.reload();
								} else {
									layer.open({
									  type: 1
									  ,offset: 'auto' //具体配置参考：offset参数项
									  ,content: '<div style="padding: 20px 80px;">'+data.message+'</div>'
									  ,btn: '我知道了'
									  ,btnAlign: 'c' //按钮居中
									  ,shade: 0 //不显示遮罩
									  ,yes: function(){
									    layer.closeAll();
									  }
									});
									window.location.reload();
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
			} else {
				layer.open({
					  type: 1
					  ,offset: 'auto' //具体配置参考：offset参数项
					  ,content: '<div style="padding: 20px 80px;">'+data.message+'</div>'
					  ,btn: '我知道了'
					  ,btnAlign: 'c' //按钮居中
					  ,shade: 0 //不显示遮罩
					  ,yes: function(){
					    layer.closeAll();
					  }
					});
			}
			
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}



