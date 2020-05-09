$(function(){
});


/*
 * 用户账号启用/禁用
 */
function setDisabled(userId,status){
	checkLogin();
	if(userId > 0){
		var msg = "";
		if(status == 0){
			msg = "是否启用该账号？";
		}
		if(status == 1){
			msg = "是否禁用该账号？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changedisabled.do",
				data: {'userId':userId},
				dataType:'json',
				success: function(data){
					refreshPageList(data)
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

//初始化职位
function initPosit(){
	checkLogin();
	var orgId = $("select[name='orgId']").val();
	if(orgId > 0){
		$.ajax({
			type: "POST",
			url: page_url+'/system/posit/getposit.do',
			data: {'orgId':orgId},
			dataType:'json',
			success: function(data){
				if(data.code == 0){
					var posit = data.listData;
					var option = "";
					$.each(posit,function(i,n){
						option += "<option value='"+posit[i]['positionId']+"'>"+posit[i]['positionName']+"</option>";
					});
					$("select[name='positionId'] option:gt(0)").remove();
					$("select[name='positionId']").append(option);
				}else{
					layer.msg(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else{
		$("select[name='positionId'] option:gt(0)").remove();
	}
	
}