$(function(){
	$("#type").bind("change",function(){
		$("#search").submit();        
    });
})

//根据id 修改关注状态   待确定 ~
function updateFocusState(approvalId){
	checkLogin();
	layer.confirm("您是否确认修改关注状态？", {
		  btn: ['确定','取消']
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/approval/approval/updateFocusState.do",
				data: {'approvalId':approvalId},
				dataType:'json',
				success: function(data){
					window.location.reload();
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

//根据id 修改收录状态
function updateIsInclude(approvalId){
	checkLogin();
	layer.confirm("您是否确认修改收录状态？", {
		  btn: ['确定','取消']
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/approval/approval/updateIsInclude.do",
				data: {'approvalId':approvalId},
				dataType:'json',
				success: function(data){
					window.location.reload();
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


//查看详情
function queryByApprovalId(approvalId){
	checkLogin();
	layer.confirm("您是否确认查看？", {
		  btn: ['确定','取消']
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/approval/approval/approval_info.do",
				data: {'approvalId':approvalId},
				dataType:'json',
				success: function(data){
					window.location.reload();
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


//第二种方法修改关注状态
function changeFocusState(num,id) {
    var message="";
    var btn="";
    if(num==1){
        message="确认取消关注吗？";
        btn="确认";
    }else{
        message="确认关注吗？";
        btn="确认";
    }
    index = layer.confirm(message, {
        btn: [btn,'取消'] //按钮
    }, function(){
        $.ajax({
            url:page_url + '/approval/approval/updateFocusState.do',
            dataType:'json',
            type:"POST",
            data:{"approvalId": id,"focusState":num},
            success:function(data){
                if(data.status<=0){
                    location.reload();
                }else{
                    layer.alert("操作失败！");
                }
            },
            error:function(data){
                if(data.status ==1001)
                {
                    flag=false;
                    layer.alert("当前操作无权限");
                }
            }
        });
    });
}


//第二种方法修改收录状态
function changeIsInclude(num,id) {
    var message="";
    var btn="";
    if(num==1){
        message="确认不再收录吗？";
        btn="确认";
    }else{
        message="确认重新收录吗？";
        btn="确认";
    }
    index = layer.confirm(message, {
        btn: [btn,'取消'] //按钮
    }, function(){
        $.ajax({
            url:page_url + '/approval/approval/updateIsInclude.do',
            dataType:'json',
            type:"POST",
            data:{"approvalId": id,"isInclude":num},
            success:function(data){
                if(data.status<=0){
                    location.reload();
                }else{
                    layer.alert("操作失败！");
                }
            },
            error:function(data){
                if(data.status ==1001)
                {
                    flag=false;
                    layer.alert("当前操作无权限");
                }
            }
        });
    });
}


//批量删除      delApprovalBatch()   来自approval_index.jsp
function delApprovalBatch() {
	checkLogin();
	var approvalIds = "";
	$.each($("input[name='checkOne']:checked"), function() {
		approvalIds += $(this).val() + "_";
	});
	if (approvalIds == "") {
		layer.alert('请选择不再收录的批准公示');
		return false;
	}
	layer.confirm('确定不再收录批准公示？', function(index) {
		$.ajax({
			async : false,
			url: "./delApprovalBatch.do",
			data : {"approvalIds" : approvalIds,},
			type : "POST",
			dataType : "json",
			success : function(data) {
				//用来判断用户是否点击确定
				if (data.code == 0) {
					$.ajax({
						type: "POST",
						url: "./delApprovalBatch.do",
						data: {'approvalIds':approvalIds},
						dataType:'json',
						success: function(data){
							
							layer.alert(data.message)
							refreshNowPageList(data);
						},
						error:function(data){
							if(data.status ==1001){
								layer.alert("当前操作无权限")
							}
						}
					});
				} else {
					layer.confirm(data.message, function(index) {
						$.ajax({
							type: "POST",
							url: "./delApprovalBatch.do",
							data: {'approvalIds':approvalIds},
							dataType:'json',
							success: function(data){
								layer.alert(data.message)
								refreshNowPageList(data);
							},
							error:function(data){
								if(data.status ==1001){
									layer.alert("当前操作无权限")
								}
							}
						});
					});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});
	
	
}