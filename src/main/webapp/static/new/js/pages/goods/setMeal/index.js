$(function () {

    //后台报错显示
    var errorTip = function (tip) {
        var dia = artDialog.alert(tip, null, {
            fn: function () {
                dia.close();
            }, text: '我知道了'
        }, { type: "warn" });
    };

    var delItem = function (id) {
        var dialog = new artDialog({
            content: $('.J-dlg-tmpl').html(),
            init: function () {
                $('.J-dlg-form').validate({
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
            width: 420,
            button: [{
                name: '提交',
                highlight: true,
                callback: function () {
                    if ($('.J-dlg-form').valid()) {
                        $.ajax({
                            url: page_url + '/vgoods/setMeal/deleteSetMeal.do',
                            data: {
                                setMealIds: id,
                                deletedReason: $('.J-dlg-form').find('[name=content]').val()
                            },
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code === 0) {
                                    window.localStorage.setItem('mealOptionSuccess', 'true')
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
    };

    $('.J-one-del').click(function () {
        var id = $(this).data('id');
        delItem(id);
    })

    $('.J-multi-del').click(function () {
        var ids = [];

        $('.J-select-item:checked').each(function () {
            ids.push($(this).val());
        })

        delItem(ids.join(','));
    })

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'mealOptionSuccess');
})