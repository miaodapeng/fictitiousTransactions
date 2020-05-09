//启用或者关闭分组
function openOrClose(groupId,type){
	checkLogin();
	if(type==0){
		msg="确认禁用改部门吗，确认禁用后，该部门所有成员将不计入五行剑法的计算统计中！确认后立即生效";
		layer.confirm(msg, {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./changeEnable.do",
					data: {'salesPerformanceGroupId':groupId,'isStatistics':type},
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
	}else{
		$.ajax({
			type: "POST",
			url: "./changeEnable.do",
			data: {'salesPerformanceGroupId':groupId,'isStatistics':type},
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
	}
}

function deleteGroupById(salesPerformanceGroupId){
        checkLogin();
        layer.confirm("删除后，该部门现有的配置参数将被清空，确认删除吗？", {
            btn: ['确认删除','取消'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                url: "./deleteGroupById.do",
                data: {'salesPerformanceGroupId':salesPerformanceGroupId},
                dataType:'json',
                success: function(data){
                    if (data.code == 0 || data.code == 1){
                        window.location.reload();
                    }
                    if( data.code == 2 ) {
                       idex =  layer.confirm("该部门内还有未移除的人员,不可被删除！", {
                            btn: ['知道了'] //按钮
                        }, function () {
                            layer.close(idex);
                        });
                    }
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



function deleteDeptById(salesPerformanceDeptId){
    checkLogin();
    layer.confirm("删除后，该小组现有的配置参数将被清空，确认删除吗？", {
        btn: ['确认删除','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./deleteOneGroupConfigSetData.do",
            data: {'salesPerformanceDeptId':salesPerformanceDeptId},
            dataType:'json',
            success: function(data){
                if (data.code == 1 || data.code == 0 ){
                    window.location.reload();
                }
                if ( data.code == 2) {
                    idex =  layer.confirm("该小组内还有未移除的人员,不可被删除！", {
                        btn: ['知道了'] //按钮
                    }, function () {
                        layer.close(idex);
                    });
                }
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

function deleteDeptUserById(salesPerformanceDeptUserId){
    checkLogin();
    layer.confirm("确认移除该人员吗，确认移除后，该人员将不计入五行剑法的计算统计中！确认后立即生效！", {
        btn: ['确认删除','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./deleteDeptUser.do",
            data: {'salesPerformanceDeptUserId':salesPerformanceDeptUserId},
            dataType:'json',
            success: function(data){
                if (data.code == 0){
                    window.location.reload();
                }
                if (data.code == 1) {
                    idex =  layer.confirm("删除失败！", {
                        btn: ['知道了'] //按钮
                    }, function () {
                        layer.close(idex);
                    });
                }
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