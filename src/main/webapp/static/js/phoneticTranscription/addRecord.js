
function formSubmit() {

    var text1 = $("#controversialContent").val();
    var text2 = $("#modifyContent").val();

    if (text1 == "" ) {
        warnTips("controversialContentMsg","修正前内容不能为空");
        return false;
    }

    if (text2 == ""){
        warnTips("modifyContentMsg","修正后内容不能为空");
        return false;
    }

    $.ajax({
        url:'./saveRecordFromList.do',
        data:$('#myform').serialize(),
        type:"POST",
        dataType : "json",
        beforeSend:function(){
            $(".warning").remove();
        },
        success:function(data){
            if (data.code == 0){
                parent.layer.close(index);
                parent.location.reload();
                //alert(data.message);
            }else {
               // alert(data.message);
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