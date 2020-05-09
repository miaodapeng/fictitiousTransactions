/*$(document).ready(function(){

});*/
/*window.onload = function(){
	
}*/

// 删除功能
function delAction(actionId){
	checkLogin();
	if(actionId > 0){
		layer.confirm("您是否确认删除？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./deleteaction.do",
					data: {'actionId':actionId},
					dataType:'json',
					success: function(data){
						refreshPageList(data);//刷新父页面列表数据
						/*if(data.code == 0){
							$("#searchSpan").click();
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

function testQRcu(){
	checkLogin();
	$.ajax({
		type: "POST",
		url: "./testQRcu.do",
		dataType:'json',
		success: function(data){
			layer.msg(data.message+"--"+data.data);
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}