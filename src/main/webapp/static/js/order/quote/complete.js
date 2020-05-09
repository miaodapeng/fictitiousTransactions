function complementTask(){
	checkLogin();
	var comment = $("input[name='comment']").val()
	var taskId = $("input[name='taskId']").val()
	var pass = $("input[name='pass']").val()
	var quoteorderId = $("input[name='quoteorderId']").val()
	var type = $("input[name='type']").val();
	if(pass =="false" && comment == ""){
		warnTips("comment","请填写备注");
		return false;
	}
	if(comment.length > 1024){
		warnTips("comment","备注内容不允许超过256个字符");
		return false;
	}
		if(type==1){
			$.ajax({
				type: "POST",
				url: "./complementTaskParallel.do",
				data: $('#complement').serialize(),
				dataType:'json',
				success: function(data){
					if (data.code == 0) {
						if(data.status == 1){
							layer.close(index);
							window.parent.location.href = page_url + '/order/quote/getQuoteDetail.do?viewType=3&quoteorderId='+data.data.quoteorderId
						}else{
							window.parent.location.reload();
						}
						
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
		}else{
			$.ajax({
				type: "POST",
				url: "./complementTask.do",
				data: $('#complement').serialize(),
				dataType:'json',
				success: function(data){
					if (data.code == 0) {
						if(data.status == 1){
							layer.close(index);
							window.parent.location.href = page_url + '/order/quote/getQuoteDetail.do?viewType=3&quoteorderId='+data.data.quoteorderId
						}else{
							window.parent.location.reload();
						}
						
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
		}
		

}

function closeQuoteVerify(){
	checkLogin();
	var quoteorderId = $("input[name='quoteorderId']").val()
	var reason= $("select[name='reason']").find("option:selected").val();
	if(reason==""){
		alert("请选择关闭原因");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "./closeQuoteVerify.do",
		data: $('#complement').serialize(),
		dataType:'json',
		success: function(data){
			if (data.code == 0) {
				if(data.status == 1){
					layer.close(index);
					window.parent.location.href = page_url + '/order/quote/getQuoteDetail.do?viewType=2&quoteorderId='+data.data.quoteorderId
				}else{
					window.parent.location.reload();
				}
				
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

}