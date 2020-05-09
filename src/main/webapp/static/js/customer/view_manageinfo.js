/*
 * 启用
 */
function setDisabled(traderCustomerId,traderId,status){
	checkLogin();
	if(traderCustomerId > 0&&status==1){
		layer.confirm("是否启用该客户？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/customer/isDisabledCustomer.do",
				data: {'traderCustomerId':traderCustomerId,'isEnable':status,'traderId':traderId},
				dataType:'json',
				success: function(data){
					var st=data.data.split(",");
					var str=page_url+"/trader/customer/manageinfo.do?traderId="+st[0]+"&traderCustomerId="+st[1];
					self.location.href=str;
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

