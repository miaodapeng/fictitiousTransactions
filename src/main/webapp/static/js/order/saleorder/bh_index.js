$(function(){
	
	
});

function getDeptUser(obj){
	checkLogin();
	var deptId = $(obj).val();
	if(deptId==""){
		$("#userId").html("<option value=''>全部</option>");
	}else{
		$.ajax({
			async:false,
			url:page_url + '/system/user/getUserListByOrgId.do',
			data:{"orgId":deptId},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var list = data.listData;
					if(list!=null && list.length>0){
						var ht = "<option value=''>全部</option>";
						for(var i=0;i<list.length;i++){
							ht += '<option value='+list[i].userId+'">' + list[i].username + '</option>';
						}
						$("#userId").html(ht);
					}
				}else{
					layer.alert("获取对应分类信息失败，请稍后重试或联系管理员！");
					return false;
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	}
}