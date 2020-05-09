$(function(){
    //后台报错显示
    var errorTip = function (tip) {
        var dia = artDialog.alert(tip, null, {
            fn: function () {
                dia.close();
            }, text: '我知道了'
        }, { type: "warn" });
    };
    //设置
    $('.J-set').click(function(){
        var dialog = new artDialog({
            title: '选择推送的平台弹框',
            content: $('.J-dlg-tmpl').html(),
            init: function () {
                $('.J-dlg-form').validate({
                    rules: {
                        platfromIds: {
                            required: true
                        }
                    },
                    messages: {
                        platfromIds: {
                            required: '请选择平台'
                        }
                    }
                })
            },
            width: 600,
            button: [{
                name: '提交',
                highlight: true,
                callback: function () {
                    if ($('.J-dlg-form').valid()) {
                        $.ajax({
                            url: page_url + '/vgoods/operate/pushGoodsInfo.do',
                            data: $('.J-dlg-form').serialize(),
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code === 0) {
                                    window.localStorage.setItem('operateSetSuccess', 'true');
                                    window.location.reload();
                                } else {
                                    errorTip(res.message || '操作异常');
                                }
                            }
                        })
                    }
                    return false;
                }
            }, {
                name: '取消'
            }],
        })
    });

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'operateSetSuccess');

    //查看大图
    GLOBAL.showLargePic('.J-show-big');
})