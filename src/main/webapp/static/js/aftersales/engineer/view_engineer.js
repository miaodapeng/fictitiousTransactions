/**
 * 启用程师
 * @param engineerId
 * @param status
 * @returns
 */
function setEnable(engineerId){
	checkLogin();
	if(engineerId > 0){
		var msg = "";
		msg = "是否启用该工程师？";
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./savechangeenable.do",
				data: {'engineerId':engineerId},
				dataType:'json',
				success: function(data){
//					refreshPageList(data)
					var str=page_url+"/aftersales/engineer/view.do?engineerId="+engineerId;
					self.location.href=str;
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