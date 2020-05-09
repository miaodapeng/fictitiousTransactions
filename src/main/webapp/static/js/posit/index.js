$(function(){
});

//删除职位
function delPosit(positionId){
	checkLogin();
	if(positionId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./deleteposit.do",
					data: {'positionId':positionId},
					dataType:'json',
					success: function(data){
						refreshPageList(data);//刷新父页面列表数据
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