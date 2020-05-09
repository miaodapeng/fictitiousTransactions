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

    // 点击替换
    $('.change-button').click(function() {
        //获取你输入的关键字；
        var searchText = $(".input-before").val();
        //创建正则表达式，g表示全局的，如果不用g，则查找到第一个就不会继续向下查找了；
        var regExp = new RegExp(searchText, 'g');
        
        $(".before-content-word p").each(function() {
            // 修正前或修正后为空不能替换 不可修改
            if ($(".input-before").val()=='' || $(".input-after").val()=='') {
                alert('输入的修正内容不完整，无法替换');
                return false;
            // 输入修正前的内容不存在 不可修改
            } else if ($(".before-content-word p").text().indexOf(searchText)== -1) {
                alert('修正前内容与当前录音文字不匹配，无法提交。')
                return false;
            }
            // 获取原文内容
            var html = $(this).html();
            // 给修改后内容赋值
            var newhtml = html.replace(regExp, '<span class="active">' + $(".input-after").val() + '</span>');
            $(".after-content-word p").html(newhtml);
            // 展示修改后内容
            $('.before-content').css('width','50%');
            $('.after-content').show();
        })   
    });

})