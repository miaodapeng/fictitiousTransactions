
function delUnit(unitId){
	checkLogin();
	if(unitId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./delunitbyid.do",
					data: {'unitId':unitId},
					dataType:'json',
					success: function(data){
						refreshNowPageList(data);
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