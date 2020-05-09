function complementTask(){
	checkLogin();
	var comment = $("input[name='comment']").val()
	var taskId = $("input[name='taskId']").val()
	var pass = $("input[name='pass']").val()
	var quoteorderId = $("input[name='quoteorderId']").val()
	if(pass =="false" && comment == ""){
		warnTips("comment","请填写备注");
		return false;
	}
	if(comment.length > 1024){
		warnTips("comment","备注内容不允许超过256个字符");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "./complementTask.do",
		data: $('#complement').serialize(),
		dataType:'json',
		success: function(data){
			if (data.code == 0) {
				if(data.status == 1){
					layer.close(index);
				}else{
					window.parent.location.reload();
				}
				
			} else {
				layer.alert(data.message);
			}
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});

}