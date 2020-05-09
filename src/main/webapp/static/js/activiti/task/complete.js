var completeTask = function(apiRoot,params) {
	checkLogin();
	if (params.tid > 0) {
		var api = apiRoot + '/activiti/task/complete.do';
		$.ajax({
			type : "POST",
			async : false,
			url : api,
			data : JSON.stringify(params),
			dataType : 'json',
			contentType : "application/json;charset=utf-8",
			success : function(data) {
				layer.closeAll('iframe');
				layer.closeAll('dialog');
				refreshNowPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}