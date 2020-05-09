$(function () {
    //展开收起
    $('.J-toggle-show').click(function () {
        var $optionalWrap = $(this).siblings().find('.J-optional-more');
        var isShow = $optionalWrap.hasClass('show');
        var $less = $(this).find('.J-less');
        var $more = $(this).find('.J-more');

        if (isShow) {
            $optionalWrap.removeClass('show').slideUp(200);
            $less.hide();
            $more.show();
        } else {
            $optionalWrap.addClass('show').slideDown(200);
            $less.show();
            $more.hide();
        }
    })

    //删除
    var delItem = function (id) {
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
            width: 420,
            button: [{
                name: '确定',
                highlight: true,
                callback: function () {
                    if ($('.J-del-form').valid()) {
                        $.ajax({
                            url: page_url + '/firstengage/baseinfo/deleteFirstEngage.do',
                            data: {
                                ids: id,
                                comment: $('.J-del-form [name="content"]').val()
                            },
                            type: 'post',
                            dataType: 'json',
                            traditional: true,
                            success: function (res) {
                                if (res.code === 0) {
                                    pagesContrlpages(true);
                                } else {
                                    var dia = artDialog.alert(res.message || '操作异常', null, {
                                        fn: function () {
                                            dia.close();
                                        }, text: '我知道了'
                                    }, { type: "warn" });
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

    $('.J-del').click(function () {
        var id = $(this).data('id');

        delItem([id]);
    })

    //过期处理
    $('.J-overday').click(function () {
        var id = $(this).data('id');

        var dialog = artDialog.confirm('确认已处理注册证过期内容？', '', {
            fn: function () {
                $.ajax({
                    url: page_url + '/firstengage/baseinfo/dealstatus.do',
                    data: {
                        registrationNumberId: id,
                    },
                    type: 'post',
                    dataType: 'json',
                    traditional: true,
                    success: function (res) {
                        if (res.code === 0) {
                            window.localStorage.setItem('needShowTip', 'true');
                            window.location.reload();
                        } else {
                            var dia = artDialog.alert(res.message || '操作异常', null, {
                                fn: function () {
                                    dia.close();
                                }, text: '我知道了'
                            }, { type: "warn" });
                        }
                    }
                })
            }, text: '提交'
        }, {
                fn: function () {
                    dialog.close();
                }, text: '取消'
            });
    })

    //审核操作
    var tmpl = template($('.J-audit-tmpl').html());
    $('.J-audit').click(function () {
        var _this = this;
        var type = $(this).data('type');
        var auditTip = $.trim($(this).html());

        var auditReq = function () {
            $.ajax({
                url: page_url + '/firstengage/baseinfo/check.do',
                data: {
                    firstEngageId: $(_this).data('id'),
                    status: type,
                    reason: $('.J-audit-form [name="content"]').val()
                },
                type: 'post',
                dataType: 'json',
                success: function (res) {
                    if (res.code === 0) {
                        window.localStorage.setItem('needShowTip', 'true');
                        window.location.reload();
                    }
                }
            })
        };

        var dialog = new artDialog({
            content: tmpl({ auditTip: auditTip, type: type }),
            init: function () {
                if (type != 3) {
                    $('.J-audit-form').validate({
                        errorWrap: true,
                        rules: {
                            content: {
                                required: true,
                                maxlength: 64
                            }
                        },
                        messages: {
                            content: {
                                required: '请填写原因，最多64个字',
                                maxlength: '请填写原因，最多64个字'
                            }
                        }
                    })
                }
            },
            width: 420,
            button: [{
                name: '确定',
                highlight: true,
                callback: function () {
                    if (type == 3) {
                        auditReq();
                    } else {
                        if ($('.J-audit-form').valid()) {
                            auditReq();
                        }
                    }

                    return false;
                }
            }, {
                name: '取消'
            }],
        })
    })

    //查看大图
    GLOBAL.showLargePic('.J-show-big');

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'needShowTip');
    GLOBAL.showGlobalTip('保存成功', null, 'addsuccess');

})