$(function () {
   

    //搜索
    $('.J-search').click(function () {
        var word = $('.J-search-word').val();
        var history = JSON.parse(localStorage.getItem('searchHistory')) || [];
        var historyList = [];

        if (word) {
            new Set([word].concat(history)).forEach((index, item) => {
                if (historyList.length < 5) {
                    historyList.push(item);
                }
            })

            localStorage.setItem('searchHistory', JSON.stringify(historyList))
        }
    })

    //显示搜索历史
    var $historyLsit = $('.J-search-history');
    var $searchInput = $('.J-search-word');

    var checkHistoryShow = function(){
        $(document).click();
        if (!$searchInput.val()) {
            var history = JSON.parse(localStorage.getItem('searchHistory')) || [];
            $historyLsit.empty();

            if (history.length) {
                $.each(history, function (i, item) {
                    $historyLsit.append('<li class="J-history-item history-item" data-word="' + item + '"><span>' + item + '</span></li>')
                })
                $historyLsit.show();
            }
        }
    };

    $searchInput.focus(function () {
        checkHistoryShow();
    })

    $searchInput.keyup(function(){
        checkHistoryShow();
    })

    $searchInput.click(function (e) {
        e.stopPropagation();
    })

    $(document).click(function () {
        $historyLsit.hide();
    })

    $(document).on('click', '.J-history-item', function () {
        $searchInput.val($(this).data('word'));
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
                        }else{
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

    //删除 && 批量删除

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
            width: 400,
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
                                    window.localStorage.setItem('needShowTip', true);
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
                    }
                    return false;
                }
            }, {
                name: '取消'
            }],
        })
    }

    $('.J-multi-del').click(function () {
        var $checked = $('.J-select-item:checked');
        var ids = [];

        $checked.each(function () {
            ids.push($(this).val());
        })

        delItem(ids);
    })

    $('.J-one-del').click(function () {
        var id = $(this).parents('.J-option-select-wrap:first').data('id');

        delItem([id]);
    })

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'needShowTip');

})