//删除消息模板
function delwl(id){
	checkLogin();
	layer.confirm("您是否确认删除？", {
		  btn: ['确定','取消']
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/system/messagetemplate/delMessageTemplate.do",
				data: {'messageTemplateId':id},
				dataType:'json',
				success: function(data){
					window.location.reload();
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