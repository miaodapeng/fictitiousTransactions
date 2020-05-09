function saveRatioEdit(){
    layer.confirm("您是否确认修改税率？", {
        btn : [ '确定', '取消' ]
    }, function() {
        checkLogin();
        $.ajax({
            async : true,
            url : page_url + '/order/saleorder/saveOrderRatioEdit.do',
            data : $("#editOrderRatioEdit").serialize(),
            type : "POST",
            dataType : "json",
            success : function(data) {
                refreshNowPageList(data);
            },error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    });
}
