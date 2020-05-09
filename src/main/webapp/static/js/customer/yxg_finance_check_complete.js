var canSubmit=true;
function complementTask(){
    var comment = $("input[name='comment']").val()
    var pass = $("input[name='pass']").val()

    var type = $("input[name='type']").val()
    if(type==null||type==0)
        type=1
    if(pass =="false" && (comment == "" || comment==undefined)){
        warnTips("comment","请填写备注");
        return false;
    }
    if(comment.length > 1024){
        warnTips("comment","备注内容不允许超过256个字符");
        return false;
    }
    checkLogin();

    if (canSubmit) {
        canSubmit = false;
    } else {
        console.log("请勿重复提交")
        return false;
    }
    $.ajax({
        type: "POST",
        url: "./finishYxgFinanceCheck.do",
        data: $('#complement').serialize(),
        dataType: 'json',
        success: function (data) {
            if (data.code == 0) {
                if($("#traderType").val()=='1'){
                    var st=data.data.split(",");
                    var str=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+st[0]+"&traderCustomerId="+st[1];
                    $("#finace").attr('href',str);
                }
                window.parent.parent.location.reload();
                // layer.close(index);
                // window.parent.closeTab && window.parent.closeTab();
            } else {
                layer.alert(data.message);
            }
        },
        error: function (data) {
            if (data.status == 1001) {
                layer.alert("当前操作无权限")
            }
            canSubmit = true;
        }
    });

}