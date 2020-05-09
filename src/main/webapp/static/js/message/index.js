$(function(){

	$("#searchSpan").click(function(){
		$("#search").submit();
	})
	$("#resetSpan").click(function(){
		$("select[name='category']").val('-1');
		$("select[name='isView']").val('-1');
	})
	
});

/*
 * 置顶
 */
function read(userId,messageUserId){
	checkLogin();
	if(userId > 0){
			$.ajax({
				type: "POST",
				url: page_url+"/system/message/editMessageUserIsView.do",
				data: {'userId':userId,'messageUserId':messageUserId},
				dataType:'json',
				success: function(data){
					refreshPageList(data)
					if(data.data == 0){
						$(window.parent.frames['side-bar-frame']).contents().find("#msg").removeClass("newstips");
					}
                    var parentWin = window.parent;
                    parentWin && parentWin.queryNoReadMsgNum && parentWin.queryNoReadMsgNum(userId);
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
	}
	
}

function allRead(userId){
	checkLogin();
	if(userId > 0){
		layer.confirm("是否全部标记为已读？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/system/message/editMessageUserIsView.do",
				data: {'userId':userId,'messageUserId':0},
				dataType:'json',
				success: function(data){
					refreshPageList(data)
					$(window.parent.frames['side-bar-frame']).contents().find("#msg").removeClass("newstips");
                    var parentWin = window.parent;
                    parentWin && parentWin.queryNoReadMsgNum && parentWin.queryNoReadMsgNum(userId);
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}, function(){
		});
	}
}
/*
 * 禁用
 */
function setDisabled(userId,status){
	checkLogin();
	if(userId > 0&&status==1){
		layer.confirm("是否启用该客户？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/trader/customer/isDisabledCustomer.do",
				data: {'id':userId,'isDisabled':status},
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

