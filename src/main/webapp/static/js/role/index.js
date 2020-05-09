$(function(){
});


//删除角色
function delRole(roleId){
	checkLogin();
	if(roleId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./deleterole.do",
					data: {'roleId':roleId},
					dataType:'json',
					success: function(data){
						refreshPageList(data);
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