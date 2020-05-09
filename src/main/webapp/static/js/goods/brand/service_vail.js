$(function() {
	var $form = $("#serviceVailform");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./saveTestVail.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			success:function(data){
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
});