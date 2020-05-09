$(function () {

    $('.J-delete').click(function () {
        var id = $(this).data('id');

        var dialog = artDialog.confirm('确认删除该科室？', '', {
            fn: function () {
                $.ajax({
                    url: page_url + '/firstengage/baseinfo/deletedepthospital.do',
                    data: {
                        id: id,
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
                            }, { type: "warn" })
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

    //成功提示
    GLOBAL.showGlobalTip('保存成功', null, 'addsuccess');
})