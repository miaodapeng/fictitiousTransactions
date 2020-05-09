$(function () {
    $('.J-cate-toggle').click(function () {
        var $parent = $(this).parents('.J-item-wrap:first');
        var isHidden = $parent.hasClass('hidden');

        if (isHidden) {
            $parent.removeClass('hidden');
        } else {
            $parent.addClass('hidden');
        }
    });

    //删除
    $('.J-del').click(function () {
        var id = $(this).data("id");
        var lv = $(this).data('lv');

        var dialog = artDialog.confirm('您确认删除此分类吗？', '', {
            fn: function () {
                $.ajax({
                    url: page_url + '/category/base/deleteCategory.do',
                    data: {
                        baseCategoryId: id,
                        baseCategoryLevel: lv
                    },
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            window.localStorage.setItem('needShowCateTip', true);
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

    //操作提示
    GLOBAL.showGlobalTip('删除成功', null, 'needShowCateTip');
});