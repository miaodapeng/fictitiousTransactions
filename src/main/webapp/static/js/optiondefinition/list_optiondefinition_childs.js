$(document).ready(function(){

});


function setDisabled(id,status,parentId){
	checkLogin();
	if(id > 0){
		var msg = "";
		if(status == 1){
			msg = "是否启用该数据字典？";
		}
		if(status == 0){
			msg = "是否禁用该数据字典？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changestatus.do",
				data: {'sysOptionDefinitionId':id,'parentId':parentId},
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