
/**
 * 发布、取消发布公告
 * @param noticeId
 * @param isEnable
 * @returns
 */
function setEnable(noticeId,isEnable){
	checkLogin();
	if(noticeId > 0){
		var msg = "";
		if(isEnable == 0){
			msg = "是否发布该公告？";
		}
		if(isEnable == 1){
			msg = "是否取消发布该公告？";
		}
		layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changedenable.do",
				data: {'noticeId':noticeId},
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
/**
 * 置顶、取消置顶
 * @param noticeId
 * @param isTop
 * @returns
 */
function setTop(noticeId,isTop){
	checkLogin();
	if(noticeId > 0){
		var msg = "";
		if(isTop == 0){
			msg = "是否置顶该公告？";
		}
		if(isTop == 1){
			msg = "是否取消置顶该公告？";
		}
		layer.confirm(msg, {
			btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./changedtop.do",
				data: {'noticeId':noticeId},
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