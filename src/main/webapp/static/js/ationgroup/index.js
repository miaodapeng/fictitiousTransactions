$(document).ready(function(){
});
//删除功能功能
function delActiongroup(actiongroupId){
	checkLogin();
	if(actiongroupId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./deleteactiongroup.do",
					data: {'actiongroupId':actiongroupId},
					dataType:'json',
					success: function(data){
						refreshNowPageList(data);
						/*if(data.code == 0){
							layer.msg(data.message);
							location.reload();
						}else{
							layer.msg(data.message);
						}*/
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