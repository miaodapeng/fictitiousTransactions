$(function () {
    //展开收起
    $('.J-toggle-show').click(function () {
        var $optionalWrap = $('.J-optional-more');
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

    //操作栏选择下拉
    $(document).on('click', '.J-option-select-icon', function (e) {
        e.stopPropagation();
        var $wrap = $(this).parents('.J-option-select-wrap:first');
        var opened = $wrap.hasClass('open');
        $('.J-option-select-wrap').removeClass('open');

        if (opened) {
            $wrap.removeClass('open');
        } else {
            $wrap.addClass('open');
        }
    })

    //操作栏是否有下拉
    $('.J-option-select-wrap').each(function () {
        if (!$(this).find('.option-select-item').length) {
            $(this).find('.J-option-select-icon').hide();
        }
    })

    $(document).click(function () {
        $('.J-option-select-wrap').removeClass('open');
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

                if (obj.placeholder) {
                    $('.J-dlg-cnt').attr('placeholder', obj.placeholder);
                }

                $('.J-dlg-form').validate({
                    errorWrap: true,
                    rules: obj.rules,
                    messages: obj.messages
                })
            },
            width: 420,
            button: [{
                name: '提交',
                highlight: true,
                callback: function () {
                    if ($('.J-dlg-form').valid()) {
                        var data = obj.data;
                        if (obj.cntName) {
                            data[obj.cntName] = $('.J-dlg-form .J-dlg-cnt').val();
                        }
                        $.ajax({
                            url: page_url + obj.url,
                            data: data,
                            type: 'post',
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code === 0) {
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

    var confirmDialog = function (obj) {
        var dialog = artDialog.confirm(obj.tip, '', {
            fn: function () {
                $.ajax({
                    url: obj.url,
                    data: obj.data || {},
                    type: 'post',
                    dataType: 'json',
                    success: function (res) {
                        if (res.code === 0) {
                            window.localStorage.setItem('spuViewOption', 'true');
                            window.location.reload();
                        } else {
                            errorTip(res.message || '操作异常');
                        }
                    }
                })
            }, text: obj.confirmTxt || '确认'
        }, {
                fn: function () {
                    dialog.close();
                }, text: '取消'
            });
    }

    $('.J-del-spu').click(function () {
        optionDialog({
            url: '/goods/vgoods/deleteSpu.do',
            tip: '温馨提示：删除SPU会删掉对应的所有SKU信息。<br>确认删除该商品信息么？',
            placeholder: '必填：请填写删除原因，最少10个字，最多300个字',
            data: {
                spuId: $(this).data('id')
            },
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
            },
            cntName: 'deleteReason',
            callback: function () {
                pagesContrlpages(true);
            }
        })
    });

    $(document).on('click', '.J-del-sku', function () {
        optionDialog({
            url: '/goods/vgoods/deleteSku.do',
            tip: '确认删除该商品信息么？',
            placeholder: '必填：请填写删除原因，最少10个字，最多300个字',
            data: {
                skuId: $(this).data('id'),
                spuId: $(this).data('spuid')
            },
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
            },
            cntName: 'deleteReason',
            callback: function () {
                turnPage(1);
                GLOBAL.showGlobalTip('操作成功');
            }
        })
    });

    //spu审核通过
    $('.J-spu-verify').click(function () {
        var tip = $.trim($(this).html());
        confirmDialog({
            tip: '确认' + tip + '么？',
            url: '/goods/vgoods/checkSpu.do',
            data: {
                spuId: $(this).data('id'),
                spuCheckStatus: 3
            },
            callback: function () {
                window.localStorage.setItem('spuViewOption', 'true');
                window.location.reload();
            }
        })
    })

    //spu审核不通过
    $('.J-spu-noverify').click(function () {
        var tip = $.trim($(this).html());
        optionDialog({
            url: '/goods/vgoods/checkSpu.do',
            tip: '确认' + tip + '么？',
            placeholder: '必填：请填写审核不通过原因，最多64个字',
            data: {
                spuCheckStatus: 2,
                spuId: $(this).data('id')
            },
            cntName: 'lastCheckReason',
            rules: {
                content: {
                    required: true,
                    maxlength: 64
                }
            },
            messages: {
                content: {
                    required: '请填写审核不通过原因，最多64个字',
                    maxlength: '请填写审核不通过原因，最多64个字'
                }
            },
            callback: function () {
                window.localStorage.setItem('spuViewOption', 'true');
                window.location.reload();
            }
        })
    })

    /*审核首营信息*/
    //审核通过
    $('.J-first-pass').click(function () {
        var id = $(this).data('id');
        confirmDialog({
            tip: '确认审核通过么？',
            url: '/firstengage/baseinfo/check.do',
            data: {
                firstEngageId: id,
                status: 3
            },
            callback: function () {
                window.localStorage.setItem('spuViewOption', 'true');
                window.location.reload();
            }
        })
    })

    //审核不通过
    $('.J-first-refuse').click(function () {
        var id = $(this).data('id');
        optionDialog({
            url: '/firstengage/baseinfo/check.do',
            tip: '确认审核不通过么？',
            placeholder: '必填：请填写审核不通过原因，最多64个字',
            data: {
                status: 2,
                firstEngageId: id
            },
            cntName: 'reason',
            rules: {
                content: {
                    required: true,
                    maxlength: 64
                }
            },
            messages: {
                content: {
                    required: '请填写审核不通过原因，最多64个字',
                    maxlength: '请填写审核不通过原因，最多64个字'
                }
            },
            callback: function () {
                new artDialog({
                    content: '<div class="del-wrap"><div class="del-tip"><i class="vd-icon icon-caution2"></i>当前首营信息审核不通过，请暂停对该SPU进行审核。<br>请通知提交人对首营信息进行编辑后，重新提交审核。</div></div >',
                    init: function () { },
                    width: 420,
                    close: function () {
                        window.localStorage.setItem('spuViewOption', 'true');
                        window.location.reload();
                    },
                    button: [{
                        name: '我知道了',
                        highlight: true
                    }]
                })
            }
        })
    })

    //转为普通商品
    $('.J-change-spu').click(function () {
        var _this = this;
        var dialog = artDialog.confirm('转为普通商品会影响SPU和对应所有SKU的状态。', '', {
            fn: function () {
                window.location.href = $(_this).data('href');
            }, text: '转为普通商品'
        }, {
                fn: function () {
                    dialog.close();
                }, text: '取消'
            });
    })

    //新增sku
    var skuTmpl = template($('.J-sku-tmpl').html());

    var editSku = function (params, id) {
        var title = id ? '编辑SKU' : '新增SKU';
        var message = params.type == 1 ? '制造商型号' : '规格';

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
                                    turnPage(1);
                                    GLOBAL.showGlobalTip('添加成功');
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
        var id = $(this).data('skuid');

        params.type = $(this).data('type');
        params.spuId = $(this).data('spuid');
        params.spuName = $(this).data('spuname');

        editSku(params, id);
    })

    //sku翻页
    var skuListTmpl = template($('.J-sku-list-tmpl').html());
    var pager = null;
    var $list = $('.J-sku-list');
    var spuid = $list.data('spuid');
    var turnPage = function (page) {
        var spulv = $list.data('spulv');
        var spuName = $list.data('spuname');

        var spuInfo = {
            spulv: spulv,
            spuName: spuName,
        };

        $.ajax({
            url: page_url + '/goods/vgoods/listSku.do',
            data: {
                pageNo: page,
                pageSize: 5,
                spuId: spuid
            },
            dataType: 'json',
            success: function (res) {
                if (res.code == 0) {
                    $list.find('.J-tr-item').remove();
                    if (res.data.page.totalRecord > 0) {
                        $('.J-tr-nodata').hide();
                        $('.J-tr-nodata').before(skuListTmpl($.extend({}, res.data, spuInfo)));

                        $('.J-sku-num').html(res.data.page.totalRecord);

                        if (!pager && res.data.page.totalPage > 1) {
                            pager = new Pager({
                                el: '.J-pager',
                                total: res.data.page.totalRecord,
                                pageNum: 1,
                                pageSize: 5,
                                needJump: false,
                                callback: function (num) {
                                    turnPage(num)
                                }
                            })
                        }

                        if (pager) {
                            pager.reset({
                                total: res.data.page.totalRecord,
                                pageNum: res.data.page.pageNo
                            })
                        }

                        $('.J-option-select-wrap').each(function () {
                            if (!$(this).find('.option-select-item').length) {
                                $(this).find('.J-option-select-icon').hide();
                            }
                        })
                    } else {
                        $('.J-tr-nodata').show();
                    }
                }
            }
        })
    };

    turnPage(1);

    //去除顿号
    $('.J-spring-filter').each(function(){
        var val = $.trim($(this).html());
        if (val && /、$/.test(val)){
            $(this).html(val.substring(0, val.lastIndexOf('、')));
        }
    })


    //查看大图
    GLOBAL.showLargePic('.J-show-big');

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'spuViewOption');

    //操作提示
    GLOBAL.showGlobalTip('保存成功', null, 'addSpuSuccess');
});