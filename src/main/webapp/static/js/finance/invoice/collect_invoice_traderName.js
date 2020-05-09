function delCollectInvoiceTrader(){
    var traderCustomerIdArr = [];var traderName = "";
    var p = 0;
    $("input[name='traderCustomerId']").each(function(){
        if($(this).is(':checked')){
            traderCustomerIdArr.push($(this).val());
            p++;
            traderName = traderName + p + "：" + $(this).attr("alt") + "<br/>";
        }
    });
    if(traderCustomerIdArr.length == 0){
        layer.alert("请选择需要删除的客户", { icon: 2 });
        return false;
    }
    layer.confirm(traderName, {
        btn : [ '确定', '取消' ], title : "您确定删除以下客户吗？"
    }, function() {
        waitWindowNew("no");
        checkLogin();
        $.ajax({
            async : true,
            url : './delCollectInvoiceTrader.do',
            data : {"traderCustomerIdArr":JSON.stringify(traderCustomerIdArr)},
            type : "POST",
            dataType : "json",
            success : function(data) {
                //window.location.reload();
                $("#searchSpan").click();
            },error:function(data){
                if(data.status ==1001){
                    $("#searchSpan").click();
                    // window.location.reload();
                    layer.alert("当前操作无权限")
                }
            }
        });
    });
}

function resetCollectPage() {
    $("#traderName").val("");
    $("input[type='checkbox'][name='traderCustomerId']").each(function(){
        $(this).prop("checked", false);
    });
    $("#searchSpan").click();
}