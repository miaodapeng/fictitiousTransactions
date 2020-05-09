//删除部门
function delOrg(orgId){
	checkLogin();
	if(orgId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./deleteorg.do",
					data: {'orgId':orgId},
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