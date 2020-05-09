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
            }, text: '提交'
        }, {
                fn: function () {
                    dialog.close();
                }, text: '取消'
            });
    })

    //跳转到商品分类
    var linked = false;
    var linkToCate = function () {
        var hosts = window.location.href.split('#');
        var needAnchor = hosts[hosts.length - 1] === 'cateList';

        if (needAnchor) {
            $('html, body').animate({ scrollTop: $('#cateList').offset().top }, 0)
        }
    };

    //翻页刷新列表
    var id = $('.J-attr-id').val();
    var needPager = true;
    var pageSize = 10;

    var refreshList = function (page) {
        $('.J-attr-list, .J-pager').hide();

        $.ajax({
            url: page_url + '/category/base/getCateforyListByAttrId.do',
            data: {
                pageNo: page,
                pageSize: pageSize,
                baseAttributeId: id
            },
            dataType: 'json',
            success: function (res) {
                if (res && res.code == 0) {

                    $('.J-attr-list').empty();

                    $.each(res.listData, function (i, obj) {
                        $('.J-attr-list').append('<div class= "cate-tr">' +
                            '<div class="cate-item">' + ((page - 1) * 10 + i + 1) + '</div>' +
                            '<div class="cate-item">' + obj.categoryJoinName + '</div>' +
                            '</div>');
                    })

                    if (!res.listData.length) {
                        $('.J-attr-list').append('<div class="cate-list-nodata"><div><i class="vd-icon icon-caution1"></i></div>没有匹配的数据</div>');
                    }

                    if (res.page.totalRecord > pageSize && needPager) {
                        needPager = false
                        new Pager({
                            el: '.J-pager',
                            total: res.page.totalRecord,
                            pageNum: 1,
                            pageSize: pageSize,
                            callback: function (num) {
                                refreshList(num)
                            }
                        })
                    }

                    $('.J-attr-list, .J-pager').show();

                    if(!linked){
                        linkToCate();
                    }
                }
            }
        })
    };

    refreshList(1);

    //操作提示
    GLOBAL.showGlobalTip('保存成功', null, 'addAttrsuccess');

});