function startCheckAptitude(traderCustomerId,taskId) {
    layer.confirm("您是否确认申请审核该用户？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./startCheckAptitude.do",
            data: {'traderCustomerId':traderCustomerId,'taskId':taskId},
            dataType:'json',
            success: function(data){
                if (data.code == 0) {
                    window.location.reload();
                } else {
                    layer.alert(data.message);
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

function changeAptitude(traderCustomerId,aptitudeStatus1,taskId){

    var aptitudeStatus=1;
    $.ajax({
        type: "POST",
        url: "./getAptitudeStatus.do",
        data: {'traderCustomerId':traderCustomerId},
        dataType:'json',
        success: function(data){
            if (data.code == 0) {
                aptitudeStatus=data.data;
                if(aptitudeStatus==0){
                    var index=layer.confirm("资质处于审核中，确认修改吗？", {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.ajax({
                            type: "POST",
                            url: "./recallChangeAptitude.do",
                            data: {'taskId':taskId,'traderCustomerId':traderCustomerId},
                            dataType:'json',
                            success: function(data){
                                if (data.code == 0) {
                                    window.location.reload();
                                } else {
                                    layer.close(index);
                                    layer.alert(data.message);
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
                }else if(aptitudeStatus==1){
                    $("#changeAptitudeTitle").click();
                }else{
                    layer.alert("客户资质审核状态已发生改变，请刷新页面");
                }
            } else {
                layer.alert(data.message);
            }

        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });

}

function checkVerifyStatus(traderCustomerId) {
    $.ajax({
        type: "POST",
        url: "./isCanCheckAptitude.do",
        data: {'traderCustomerId': traderCustomerId},
        dataType: 'json',
        success: function (data) {
            if (data.code == 0) {
                $("#checkAptitudePage").click()
            } else {
                layer.alert(data.message);
            }

        },
        error: function (data) {
            if (data.status == 1001) {
                layer.alert("当前操作无权限")
            }
        }
    })
}