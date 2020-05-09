$(function() {

    $("#upload_file").change(function(){
        checkLogin();
        $("#upload_file_tmp").val($("#upload_file").val());
    })

    $("#submit").click(function(){
        checkLogin();

        var taxNum =$("#taxNum").val();
        var preTaxNum=$("#preTaxNum").val();
        var checkStatus=$("#checkStatus").val();
        if((taxNum ==undefined||taxNum=='')&&!((preTaxNum==undefined||preTaxNum=='')&&(checkStatus!=undefined&&checkStatus==1))){
            index = layer.confirm("税务登记号为空，需财务部进行审核？", {
                btn: ['确定','取消'] //按钮
            }, function(){
                save();
                layer.close(index);
            }, function(){
                layer.close(index)
            });
            return false;
        }


        save();
        return false;
    })

});

function completeCheck() {
    var url='/trader/customer/finishYxgFinanceCheck.do'
    $.ajax({
        url:page_url+url,
        data:$('#myform').serialize(),
        type:"POST",
        dataType : "json",
        async: false,
        success:function(data)
        {
            if(data.code==0){
                if($("#traderType").val()=='1'){
                    var st=data.data.split(",");
                    var str=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+st[0]+"&traderCustomerId="+st[1];
                    $("#finace").attr('href',str);
                }
                window.parent.location.reload();
            }else{
                layer.msg(data.message);
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}
function save() {
    var url='/trader/customer/saveYxgTraderFinance.do'
    $.ajax({
        url:page_url+url,
        data:$('#myform').serialize(),
        type:"POST",
        dataType : "json",
        async: false,
        success:function(data)
        {
            if(data.code==0){
                if($("#traderType").val()=='1'){
                    var st=data.data.split(",");
                    var str=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+st[0]+"&traderCustomerId="+st[1];
                    $("#finace").attr('href',str);
                }
                window.parent.location.reload();
            }else{
                layer.msg(data.message);
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}

function checkOfYes() {
    checkLogin();
    $("#pass").val(true);
    index = layer.confirm("确定通过审核？", {
        btn: ['确定','取消'], //按钮
        title: '操作确认'
    },function(){
        completeCheck();
        layer.close(index);
    }, function(){
        layer.close(index)
    });
}
