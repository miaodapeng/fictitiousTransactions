$(function() {
    // 点击隐藏弹框
    $('.icon-delete').click(function() {
        $('.voice-to-text').hide();
        $('.pageFull').hide();
    })

    // 监听修正前输入框内容
    $(".input-before").bind("input propertychange",function(event){
        console.log('修正前',$(".input-before").val());
    });
    // 监听修正后输入框内容
    $(".input-after").bind("input propertychange",function(event){
        console.log('修正后',$(".input-after").val());
    });
    // 监听点评输入框内容
    $(".review-input").bind("input propertychange",function(event){
        console.log('点评',$(".review-input").val());
    });

    //根据有没有修改后内容进行展示
    var updatedContent = $("#updatedContent").text();
    if(updatedContent==''){
        $('.before-content').css('width','100%');
        $('.after-content').hide();
    }else{
        // 展示修改后内容
        $('.before-content').css('width','50%');
        $('.after-content').show();
    }

    // 点击替换
    $('.change-button').click(function() {
        //获取你输入的关键字；
        var searchText = $(".input-before").val();
        //创建正则表达式，g表示全局的，如果不用g，则查找到第一个就不会继续向下查找了；
        var regExp = new RegExp(searchText, 'g');

        var objContext;
        if(updatedContent == ''){
            objContext = $(".before-content-word p");
        }else {
            objContext = $(".after-content-word p");
        }
        objContext.each(function() {
            // 修正前或修正后为空不能替换 不可修改
            if ($(".input-before").val()=='' || $(".input-after").val()=='') {
                layer.alert('输入的修正内容不完整，无法替换');
                return false;
            // 输入修正前的内容不存在 不可修改
            } else if (objContext.text().indexOf(searchText)== -1) {
                layer.alert('修正前内容与当前录音文字不匹配，无法提交');
                return false;
            }
            // 获取原文内容
            var html = $(this).html();
            // 给修改后内容赋值
            var newhtml = html.replace(regExp, '<span class="active">' + $(".input-after").val() + '</span>');
            // 更新修改记录、保存更新内容
            var controversialContent = $("#controversialContent").val();
            if(controversialContent.length > 20){
                layer.alert('输入的争议内容不允许超过20个字');
                return false;
            }
            var modifyContent = $("#modifyContent").val();
            if(modifyContent.length > 20){
                layer.alert('输入的修正内容不允许超过20个字');
                return false;
            }
            var communicateRecordId = $("#communicateRecordId").val();

            $.ajax({
                url : page_url + '/phoneticTranscription/phonetic/addModificationRecord.do',
                data : {
                    "controversialContent" : controversialContent,
                    "modifyContent" : modifyContent ,
                    "relatedId" : communicateRecordId,
                    "type": 0
                },
                type : "POST",
                dataType : "json",
                success : function(data) {
                    if(data.code == -1){
                        layer.alert(data.message);
                    }else{
                        $("#controversialContent").val("");
                        $("#modifyContent").val("");
                        $("#commentContent").val("");
                        location.reload();
                    }
                },
                error: function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }else
                        layer.alert(data.message)}
            });
        })
    });
    // 遍历争议列表，隐藏非当前沟通记录下的争议
    /*var j = 0;
    $("input[id='controversialContentText']").each(function(i){
        var obj = $(this);
        $(".after-content-word p").each(function() {
            //获取你输入的关键字；
            var searchText = obj.val();
            //创建正则表达式，g表示全局的，如果不用g，则查找到第一个就不会继续向下查找了；
            var regExp = new RegExp(searchText, 'g');
            if ($(".after-content-word p").text().indexOf(searchText)== -1){
                obj.parent().parent().hide();
            }else if($(".after-content-word p").text().indexOf(searchText) >=0 ){
                j++;
                obj.parent().parent().find("td:first").text(j);
                obj.parent().parent().show();
            }
        });
    });*/

})
// 删除规则
function delRecords(modificationRecordId){
    var communicateRecordId = $("#communicateRecordId").val();
    layer.confirm('确定删除？', function(index){
        $.ajax({
            url : page_url + '/phoneticTranscription/phonetic/addModificationRecord.do',
            data : {
                "modificationRecordId" : modificationRecordId,
                "relatedId" : communicateRecordId,
                "isDel" : 1
            },
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.code == -1){
                    layer.alert("操作失败");
                }else{
                    $("#controversialContent").val("");
                    $("#modifyContent").val("");
                    $("#commentContent").val("");
                    location.reload();
                }
            },
            error: function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }else
                    layer.alert("操作失败")}
        });
    });
}
// 将规则设置为全局
function updateAllModificationRecord(modificationRecordId,controversialContent,relatedId){
    $.ajax({
        url : page_url + '/phoneticTranscription/phonetic/updateAllModificationRecord.do',
        data : {
            "modificationRecordId" : modificationRecordId,
            "controversialContent" : controversialContent,
            "relatedId" : relatedId,
            "type" : 1
        },
        type : "POST",
        dataType : "json",
        success : function(data) {
            if(data.code == -1){
                layer.alert(data.message);
            }else{
                $("#controversialContent").val("");
                $("#modifyContent").val("");
                $("#commentContent").val("");
                location.reload();
            }
        },
        error: function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }else
                layer.alert("操作失败")}
    });
}
// 添加点评
function addComments() {
    var commentContent = $("#commentContent").val();
    var communicateRecordId = $("#communicateRecordId").val();
    if(commentContent == '' || $.trim(commentContent).length == 0 ){
        layer.alert('点评内容不允许为空');
        return false;
    }
    if(commentContent.length > 100){
        layer.alert('点评内容不允许超过100个字');
        return false;
    }
    $.ajax({
        url : page_url + '/phoneticTranscription/phonetic/addComments.do',
        data : {
            "content" : commentContent,
            "relatedId" : communicateRecordId
        },
        type : "POST",
        dataType : "json",
        success : function(data) {
            if(data.code == -1){
                layer.alert("操作失败");
            }else{
                $("#controversialContent").val("");
                $("#modifyContent").val("");
                $("#commentContent").val("");
                location.reload();
            }
        },
        error: function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }else
                layer.alert("操作失败")}
    });
}
function playrecordNew(url){
    checkLogin();
    if(url != ''){
        self.parent.parent.layer.myopen({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            area: ['360px','120px'],
            title: '录音',
            resize:true,
            shade: 0,
            move: '.layui-layer-title',
            content: ['./system/call/getrecordpaly.do?url='+url],
            success: function(layero, index) {
                //layer.iframeAuto(index);
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }

}