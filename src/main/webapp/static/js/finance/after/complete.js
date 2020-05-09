function complementTask(){
	checkLogin();
	var comment = $("input[name='comment']").val()
	var taskId = $("input[name='taskId']").val()
	var pass = $("input[name='pass']").val()
	var type = $("input[name='type']").val()
	var buyorderId = $("input[name='buyorderId']").val();
	if(pass =="false" && comment == ""){
		warnTips("comment","请填写备注");
		return false;
	}
	if(comment.length > 1024){
		warnTips("comment","备注内容不允许超过256个字符");
		return false;
	}
	if(type == 1){
		//订单审核
		$.ajax({
			type: "POST",
			url: "./complementTask.do",
			data: $('#complement').serialize(),
			dataType:'json',
			success: function(data){
				if (data.code == 0){
					if(data.status == 1){
						layer.close(index);
						window.parent.location.href = page_url + '/order/buyorder/viewBuyordersh.do?buyorderId='+data.data.buyorderId
					}else{
						window.parent.location.reload();
					}
				} else {
					layer.alert(data.message)
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else if(type == 2){
		//售后审核
		$.ajax({
			type: "POST",
			url: "./complementAfterSaleTask.do",
			data: $('#complement').serialize(),
			dataType:'json',
			success: function(data){
				refreshPageList(data)
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		
	}
		
		

}