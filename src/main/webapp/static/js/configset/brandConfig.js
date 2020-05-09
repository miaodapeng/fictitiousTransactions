//启用或者关闭分组
function openOrcloseBrand(brandPId,type){
	checkLogin();
	if(type==0){
		msg="确认禁用该品牌吗，确认禁用后，该品牌将不计入五行剑法的计算统计中！确认后立即生效！";
	}else{
		msg="确认启用该品牌吗，确认启用后，该品牌将计入五行剑法的计算统计中！确认后立即生效!";
	}
	layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changeBrandEnable.do",
				data: {'salesPerformanceBrandConfigId':brandPId,'isEnable':type},
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

function checkBrands(){
	checkLogin();
	var obj=$("input[name='checkOne']:checked");
	var brandIds="";
	for(var i=0; i<obj.length; i++){
		if(obj[i]!=null) {
			brandIds+=obj[i].value+","; //如果选中，将value添加到变量s中
		}
	}
	if(brandIds==''){
		layer.alert("未选择任何数据！");
		return false;
	}else{
		brandIds=brandIds.substring(0,brandIds.length-1);
	}
	var groupId=$("#groupIds").val();
	$.ajax({
		type: "POST",
		url: "./addBrandConfig.do",
		data: {'brandIds':brandIds,'groupId':groupId},
		dataType:'json',
		success: function(data){
			parent.layer.close(index);
			parent.location.reload();
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}