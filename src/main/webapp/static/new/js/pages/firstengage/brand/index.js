$(function () {
    $('.J-one-del').click(function () {
        var id = $(this).data('id');

        var dialog = new artDialog({
            content: $('.J-del-tmpl').html(),
            init: function () {
                $('.J-del-form').validate({
                    errorWrap: true,
                    rules: {
                        content: {
                            required: true,
                            minlength: 10,
                            maxlength: 300
                        }
                    },
                    messages: {
                        content: {
                            required: '请填写删除原因，10-300个字',
                            minlength: '请填写删除原因，10-300个字',
                            maxlength: '请填写删除原因，10-300个字'
                        }
                    }
                })
            },
            width: 400,
            button: [{
                name: '提交',
                highlight: true,
                callback: function () {
                    if ($('.J-del-form').valid()) {
                        $.ajax({
                            url: page_url + '/firstengage/brand/delBrand.do',
                            data: {
                                brandId: id,
                                comment: $('.J-del-form [name="content"]').val()
                            },
                            type: 'post',
                            dataType: 'json',
                            traditional: true,
                            success: function (res) {
                                if (res.code === 0) {
                                    window.localStorage.setItem('needShowTip', true);
                                    window.location.reload();
                                } else {
                                    var dia = artDialog.alert(res.message || '操作异常', null, {
                                        fn: function () {
                                            dia.close();
                                        }, text: '我知道了'
                                    }, { type: 'warn' });
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
    })

    //操作提示
    GLOBAL.showGlobalTip('删除成功', null, 'needShowTip');
})