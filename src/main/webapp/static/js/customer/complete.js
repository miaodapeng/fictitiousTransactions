
var canSubmit=true;
function complementTask(type){
	var comment = $("input[name='comment']").val()
	var taskId = $("input[name='taskId']").val()
	var pass = $("input[name='pass']").val()
	var traderCustomerId = $("input[name='traderCustomerId']").val()
	var type = $("input[name='type']").val()
	if(type==null||type==0)
		type=1
	if(pass =="false" && comment == ""){
		warnTips("comment","请填写备注");
		return false;
	}
	if(comment.length > 1024){
		warnTips("comment","备注内容不允许超过256个字符");
		return false;
	}
	checkLogin();
	if(type==1) {
		$.ajax({
			type: "POST",
			url: "./complementTask.do",
			data: $('#complement').serialize(),
			dataType: 'json',
			success: function (data) {
				if (data.code == 0) {
					if (data.status == 1) {
						layer.close(index);
					} else {
						window.parent.location.reload();
					}
				} else {
					layer.alert(data.message);
				}
			},
			error: function (data) {
				if (data.status == 1001) {
					layer.alert("当前操作无权限")
				}
			}
		});

	}else if(type==2){
		if(canSubmit){
			canSubmit=false;
		}else{
			console.log("请勿重复提交")
			return false;
		}
		if(!window.parent.checkUpContent()){
			return false;
		}
		$.ajax({
			type: "POST",
			url: "./completeCheckAptitude.do",
			data: $('#complement').serialize(),
			dataType: 'json',
			success: function (data) {
				if (data.code == 0) {
					layer.close(index);
					window.parent.closeTab && window.parent.closeTab();
				} else {
					layer.alert(data.message);
				}
			},
			error: function (data) {
				if (data.status == 1001) {
					layer.alert("当前操作无权限")
				}
				canSubmit=true;
			}
		});
	}
}