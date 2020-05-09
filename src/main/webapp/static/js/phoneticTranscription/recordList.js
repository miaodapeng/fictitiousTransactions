function del(id) {

    var msg = "确定删除该记录？";
    layer.confirm(msg, {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./delRecord.do",
            data: {'id':id},
            dataType:'json',
            success: function(data){
                if (data.code == 0){
                    parent.layer.close(index);
                    //alert(data.message);
                    self.location.reload();
                }else {
                    layer.alert(data.message)
                }
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    })

}

function resetForm() {
    $("form").find("input[type='text']").val('');
    $.each($("form select"),function(i,n){
        $(this).children("option:first").prop("selected",true);
    });
    $("#search").submit();
}