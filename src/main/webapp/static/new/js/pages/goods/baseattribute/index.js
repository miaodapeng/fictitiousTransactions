$(function () {
    $('.J-one-del').click(function () {
        var id = $(this).data("id");

        var dialog = artDialog.confirm('确认删除该属性？', '', {
            fn: function () {
                $.ajax({
                    url: page_url + '/goods/baseattribute/delAttribute.do',
                    data: {
                        baseAttributeId: id
                    },
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            window.localStorage.setItem('needShowAttrTip', true);
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
    GLOBAL.showGlobalTip('操作成功', null, 'needShowAttrTip');
})