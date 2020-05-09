$(function () {
    var checkSelectAll = function () {
        var selectFlag = true;

        $('.J-select-spu').each(function () {
            if (!$(this)[0].checked) {
                selectFlag = false;
            }
        })

        $('.J-select-list-all')[0].checked = selectFlag;
    };

    var checkOutput = function () {
        var len = $('.J-select-spu:checked').length;

        if (len > 0) {
            $('.J-prod-export').removeClass('btn-disabled');
        } else {
            $('.J-prod-export').addClass('btn-disabled');
        }
    };

    var checkStock = function () {
        var len = $('.J-select-sku:checked').length;

        if (len > 0) {
            $('.J-prod-stock').removeClass('btn-disabled');
        } else {
            $('.J-prod-stock').addClass('btn-disabled');
        }
    };

    $('.J-select-list-all').change(function () {
        var $this = $(this);

        $('.J-select-spu').each(function () {
            $(this)[0].checked = $this[0].checked;
        })

        checkOutput();
    })

    $('.J-select-spu').change(function () {
        checkSelectAll();
        checkOutput();
    })

    $(document).on('change', '.J-select-sku', function () {
        checkStock();
    })

    //批量设置备货
    $('.J-prod-stock').click(function () {
        if (!$('.J-select-sku:checked').length) {
            return false;
        }

        var dialog = new artDialog({
            content: $('.J-stock-tmpl').html(),
            init: function () {
                var $form = $('.J-stock-form');
                $form.validate({
                    errorWrap: true,
                    rules: {
                        setGoods: {
                            required: true
                        }
                    },
                    messages: {
                        setGoods: {
                            required: '请选择设置项'
                        }
                    }
                })
            },
            width: 420,
            button: [{
                name: '确认',
                highlight: true,
                callback: function () {
                    if ($('.J-stock-form').valid()) {
                        var skuIds = [];

                        $('.J-select-sku:checked').each(function () {
                            skuIds.push($(this).val());
                        })

                        $.ajax({
                            url: page_url + '/goods/vgoods/backupSku.do',
                            data: {
                                skuIds: skuIds.join(','),
                                hasBackupMachine: $('.J-stock-form input:checked').val()
                            },
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code == 0) {
                                    window.localStorage.setItem('spuListViewOption', 'true');
                                    window.location.reload();
                                } else {
                                    errorTip(res.message || '请求错误。');
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

    //获取商品数量
    $.ajax({
        url: page_url + '/goods/vgoods/count.do',
        dataType: 'json',
        success: function (res) {
            if (res.code == 0 && res.data) {
                for (var item in res.data) {
                    $('.J-' + item).html('(' + res.data[item] + ')');
                }
            }
        },
        error: function () { }
    })

    //新增或者编辑sku
    var skuTmpl = template($('.J-sku-tmpl').html());

    var editSku = function (params, id) {
        var title = id ? '编辑SKU' : '新增SKU';
        var isHaocai = params.type == 317 || params.type == 318 || params.type == 653;
        var message = isHaocai ? '规格' : '制造商型号';
        params.isHaocai = isHaocai;

        var dialog = new artDialog({
            title: title,
            content: skuTmpl(params),
            init: function () {
                var $form = $('.J-sku-form');
                $form.validate({
                    errorWrap: true,
                    rules: {
                        content: {
                            required: true
                        }
                    },
                    messages: {
                        content: {
                            required: '请填写' + message
                        }
                    }
                })

                if (id) {
                    $.ajax({
                        url: page_url + '/goods/vgoods/viewTempSkuAjax.do',
                        data: {
                            skuId: id
                        },
                        dataType: 'json',
                        success: function (res) {
                            if (res.code == 0) {
                                $('.J-cnt', $form).val(res.data.skuInfo);
                                $('.J-sku-loading').hide();
                                $form.show();
                            }
                        }
                    })
                } else {
                    $('.J-sku-loading').hide();
                    $('.J-sku-form').show();
                }
            },
            width: 680,
            button: [{
                name: '确认',
                highlight: true,
                callback: function () {
                    if ($('.J-sku-form').valid()) {
                        $.ajax({
                            url: page_url + '/goods/vgoods/saveTempSku.do',
                            data: {
                                spuId: params.spuId,
                                skuId: id,
                                skuInfo: $('.J-sku-form .J-cnt').val()
                            },
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code == 0) {
                                    window.localStorage.setItem('spuListViewOption', 'true');
                                    window.location.reload();
                                } else {
                                    errorTip(res.message || '请求错误。');
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


    $(document).on('click', '.J-sku-edit', function () {
        var params = {};
        var $parent = $(this).parents('.J-list:first');
        var id = $(this).data('skuid');

        params.type = $.trim($parent.find('.J-spu-type').data('type'));
        params.spuId = $(this).data('spuid');
        params.spuName = $(this).data('spuname');

        editSku(params, id);
    })

    //后台报错显示
    var errorTip = function (tip) {
        var dia = artDialog.alert(tip, null, {
            fn: function () {
                dia.close();
            }, text: '我知道了'
        }, { type: "warn" });
    };

    //操作弹窗
    var optionDialog = function (obj) {
        var dialog = new artDialog({
            content: $('.J-dlg-tmpl').html(),
            init: function () {
                $('.J-dlg-tip').html(obj.tip);

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
                        var data = $.extend({}, obj.data, { deleteReason: $('.J-dlg-form .J-dlg-cnt').val() });

                        $.ajax({
                            url: page_url + obj.url,
                            data: data,
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code == 0) {
                                    obj.callback && obj.callback();
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
    }

    //删除sku
    $(document).on('click', '.J-del-sku', function () {
        var _this = this;
        optionDialog({
            url: '/goods/vgoods/deleteSku.do',
            tip: '确认删除该商品信息么？',
            data: {
                skuId: $(this).data('id'),
                spuId: $(this).data('spuid')
            },
            callback: function () {
                var $wrap = $(_this).parents('.J-item-wrap:first');
                var $pageWrap = $wrap.find('.J-page-wrap');

                turnPage($pageWrap, 1);
                GLOBAL.showGlobalTip('操作成功');
                // window.location.reload();
            }
        })
    });

    //删除spu
    $('.J-del-spu').click(function () {
        optionDialog({
            url: '/goods/vgoods/deleteSpu.do',
            tip: '温馨提示：删除SPU会删掉对应的所有SKU信息。<br>确认删除该商品信息么？',
            data: {
                spuId: $(this).data('id')
            },
            callback: function () {
                window.localStorage.setItem('spuListViewOption', 'true');
                window.location.reload();
            }
        })
    });


    //sku翻页
    var skuListTmpl = template($('.J-sku-list-tmpl').html());

    var initPager = function ($wrap, page) {

        $wrap.attr('cz-page', page.pageNo);
        $wrap.find('.J-page-txt').html(page.pageNo);
        $wrap.find('.J-page-txt-total').html(page.totalPage);

        if (page.totalRecord <= 5) {
            $wrap.hide();
        } else {
            if (page.pageNo == 1) {
                $('.J-page-prev', $wrap).addClass('disabled');
            } else {
                $('.J-page-prev', $wrap).removeClass('disabled');
            }

            if (page.pageNo == page.totalPage) {
                $('.J-page-next', $wrap).addClass('disabled');
            } else {
                $('.J-page-next', $wrap).removeClass('disabled');
            }
        }
    };

    var turnPage = function ($wrap, page) {
        var spuId = $wrap.data('spuid');
        var total = Math.ceil($wrap.data('total') / 5);
        var lv = $wrap.data('lv');
        var wiki = $wrap.data('spuwiki');
        var spuName = $wrap.data('spuname');

        var spuInfo = {
            spuId: spuId,
            spulv: lv,
            spuWiki: wiki,
            spuName: spuName,
            auth: $wrap.data('auth'),
            tempauth: $wrap.data('tempauth')
        };

        if (!(page < 1 || page > total)) {
            $.ajax({
                url: page_url + '/goods/vgoods/listSku.do',
                data: {
                    pageNo: page,
                    pageSize: 5,
                    spuId: spuId
                },
                dataType: 'json',
                success: function (res) {
                    if (res.code == 0) {
                        $wrap.siblings('.J-sku-item').remove();
                        $wrap.before(skuListTmpl($.extend({}, res.data, spuInfo)));

                        initPager($wrap, res.data.page);
                    }
                }
            })
        }
    };

    $('.J-page-prev').click(function () {
        var $wrap = $(this).parents('.J-page-wrap:first');
        var page = parseInt($wrap.attr('cz-page') || 1);

        turnPage($wrap, page - 1);
    });

    $('.J-page-next').click(function () {
        var $wrap = $(this).parents('.J-page-wrap:first');
        var page = parseInt($wrap.attr('cz-page') || 1);

        turnPage($wrap, page + 1);
    });

    $('.J-page-wrap').each(function () {
        var total = $(this).data('total');

        $(this).find('.J-page-txt-total').html(Math.ceil(total / 5));
    })

    var parseData = {
        brandName: function (data) {
            var list = data.listData || [];
            var reslist = [];
            $.each(list, function (i, item) {
                reslist.push({
                    word: item.brandName,
                })
            })
            return reslist;
        },
        productCompanyName: function (data) {
            var list = data.data || [];
            var reslist = [];
            $.each(list, function (i, item) {
                reslist.push({
                    word: item.label,
                })
            })
            return reslist;
        },
        departmentName: function (data) {
            var list = data.data || [];
            var reslist = [];
            $.each(list, function (i, item) {
                reslist.push({
                    word: item.label,
                })
            })
            return reslist;
        }
    }

    //搜索建议词
    $('.J-suggest-input').each(function () {
        var _this = this;
        new Suggest({
            el: this,
            url: page_url + $(this).data('url'),
            params: $(this).attr('name'),
            parseData: function (data) {
                return parseData[$(_this).attr('name')](data);
            }
        });
    })

    //分类搜索三级
    new LvSelect({
        el: '.J-category-wrap',
        input: '.J-category-value',
        async: true,
        url: page_url + '/category/base/getCategoryList.do',
        parseData: function (res) {
            var categoryData = [];

            $.each(res.listData, function (i, lv1) {
                var lv1item = {
                    label: lv1.baseCategoryName,
                    value: lv1.baseCategoryId,
                    child: []
                };

                if (lv1.secondCategoryList) {
                    $.each(lv1.secondCategoryList, function (ii, lv2) {
                        var lv2item = {
                            label: lv2.baseCategoryName,
                            value: lv2.baseCategoryId,
                            child: []
                        };

                        if (lv2.thirdCategoryList) {
                            $.each(lv2.thirdCategoryList, function (iii, lv3) {
                                lv2item.child.push({
                                    label: lv3.baseCategoryName,
                                    value: lv3.baseCategoryId
                                })
                            })
                        }

                        if (lv2item.child.length) {
                            lv1item.child.push(lv2item);
                        }
                    })
                }

                if (lv1item.child.length) {
                    categoryData.push(lv1item);
                }
            })

            return categoryData;
        }
    })

    //新国标三级
    new LvSelect({
        el: '.J-stand-wrap',
        input: '.J-stand-value',
        async: true,
        url: page_url + '/firstengage/baseinfo/allstandard.do',
        parseData: function (res) {
            var newStandData = [];
            $.each(res.data, function (i, lv1) {
                var lv1item = {
                    label: lv1.categoryName,
                    value: lv1.standardCategoryId,
                    child: []
                };

                if (lv1.standardCategoryList && lv1.standardCategoryList.length) {
                    $.each(lv1.standardCategoryList, function (ii, lv2) {
                        var lv2item = {
                            label: lv2.categoryName,
                            value: lv2.standardCategoryId,
                            child: []
                        };

                        if (lv2.standardCategoryList && lv2.standardCategoryList.length) {
                            $.each(lv2.standardCategoryList, function (iii, lv3) {
                                lv2item.child.push({
                                    label: lv3.categoryName,
                                    value: lv3.standardCategoryId
                                })
                            })
                        }

                        if (lv2item.child.length) {
                            lv1item.child.push(lv2item);
                        }
                    })
                }

                if (lv1item.child.length) {
                    newStandData.push(lv1item);
                }
            })

            return newStandData;
        }
    })

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'spuListViewOption');
})