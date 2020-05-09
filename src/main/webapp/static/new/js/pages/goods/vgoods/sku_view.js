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

    //后台报错显示
    var errorTip = function (tip) {
        var dia = artDialog.alert(tip, null, {
            fn: function () {
                dia.close();
            }, text: '我知道了'
        }, { type: "warn" });
    };

    //新增或者编辑sku
    var skuTmpl = template($('.J-sku-tmpl').html());

    var editSku = function (params, id) {
        var title = id ? '编辑SKU' : '新增SKU';
        var message = params.type === '1' ? '制造商型号' : '规格';

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
                                    window.localStorage.setItem('skuViewOption', 'true');
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
        var id = $(this).data('skuid');

        params.type = $(this).data('type');
        params.spuId = $(this).data('spuid');
        params.spuName = $(this).data('spuname');

        editSku(params, id);
    })

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
                    rules: {
                        content: obj.rules
                    },
                    messages: {
                        content: obj.messages
                    }
                })
            },
            width: 420,
            button: [{
                name: '提交',
                highlight: true,
                callback: function () {
                    if ($('.J-dlg-form').valid()) {
                        var data = obj.data || {};
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

    $('.J-sku-option').click(function () {
        var type = $(this).data('dlg');
        var tip = $.trim($(this).html());
        var _this= this;

        if (type == 0) {
            var dialog = artDialog.confirm('确认' + tip + '么？', '', {
                fn: function () {
                    $.ajax({
                        url: $(_this).data('href'),
                        data: $(_this).data('params'),
                        type: 'post',
                        dataType: 'json',
                        success: function (res) {
                            dialog.close();
                            if (res.code === 0) {
                                window.localStorage.setItem('skuViewOption', 'true');
                                window.location.reload();
                            } else {
                                errorTip(res.message || '操作异常');
                            }
                        }
                    })
                }, text: '确认'
            }, {
                    fn: function () {
                        dialog.close();
                    }, text: '取消'
                });
        } else {
            optionDialog({
                tip: '确认' + tip + '么？',
                url: $(this).data('href'),
                data: $(this).data('params'),
                cntName: 'lastCheckReason',
                placeholder: '必填：请填写' + tip + '原因，最多64个字',
                rules: {
                    required: true,
                    maxlength: 64
                },
                messages: {
                    required: '请填写' + tip + '原因，最多64个字',
                    maxlength: '请填写' + tip + '原因，最多64个字'
                },
                callback: function () {
                    window.localStorage.setItem('skuViewOption', 'true');
                    window.location.reload();
                }
            })
        }
    })

    $('.J-sku-del').click(function () {
        optionDialog({
            url: '/goods/vgoods/deleteSku.do',
            tip: '确认删除该商品信息么？',
            placeholder: '必填：请填写删除原因，最少10个字，最多300个字',
            rules: {
                required: true,
                minlength: 10,
                maxlength: 300
            },
            data: {
                skuId: $(this).data('id'),
                spuId: $(this).data('spuid')
            },
            cntName: 'deleteReason',
            messages: {
                required: '请填写删除原因，10-300个字',
                minlength: '请填写删除原因，10-300个字',
                maxlength: '请填写删除原因，10-300个字'
            },
            callback: function () {
                pagesContrlpages(true);
            }
        })
    });

    var getTime = function (num) {
        var date = new Date(num);

        return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDay() + '日';
    }

    //成本价，批量价 
    $('.J-price-one').click(function () {
        var _this = this;
        new artDialog({
            title: '成本价',
            content: $('.J-price-one-tmpl').html(),
            init: function () {
                $.ajax({
                    url: $(_this).data('href'),
                    // url: '/erp/goods/goods/showPriceList.do?goodsChannelPriceId=6924&priceType=1',
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            $('.J-dlg-loading').hide();
                            $.each(res.data, function (i, item) {
                                $('.J-dlg-cnt').find('.J-dlg-list').append('<tr><td>' + getTime(item.startTime) + '-' + getTime(item.endTime) + '</td><td>' + item.batchPrice.toFixed(2) + '</td></tr>');
                            })
                            $('.J-dlg-cnt').show();
                        }
                    }
                })
            },
            width: 600,
            button: [{
                name: '我知道了',
                highlight: true,
                callback: function () { }
            }],
        })
    })

    $('.J-price-more').click(function () {
        var _this = this;
        new artDialog({
            title: '批量价',
            content: $('.J-price-more-tmpl').html(),
            init: function () {
                $.ajax({
                    url: $(_this).data('href'),
                    //  url: '/erp/goods/goods/showPriceList.do?goodsChannelPriceId=6924&priceType=2',
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            $('.J-dlg-loading').hide();
                            $.each(res.data, function (i, item) {
                                $('.J-dlg-cnt').find('.J-dlg-list').append('<tr><td>' + item.minNum + '-' + item.maxNum + '</td><td>' + item.batchPrice.toFixed(2) + '</td></tr>');
                            })

                            $('.J-dlg-cnt').show();
                        }
                    }
                })
            },
            width: 600,
            button: [{
                name: '我知道了',
                highlight: true,
                callback: function () { }
            }],
        })
    })

    //去除顿号
    $('.J-spring-filter').each(function () {
        var val = $.trim($(this).html());
        if (val && /、$/.test(val)) {
            $(this).html(val.substring(0, val.lastIndexOf('、')));
        }
    })

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'skuViewOption');

    //保存成功提示
    GLOBAL.showGlobalTip('保存成功', null, 'addSkuSuccess');

    //查看大图
    GLOBAL.showLargePic('.J-show-big');

    //当前登陆用户的角色
    var isSupplyAssistant = $("#isSupplyAssistant").val();
    //当前sku的审核进度
    var checkStatus = $("#checkStatus").val();

    if (isSupplyAssistant != null && isSupplyAssistant != '' && isSupplyAssistant == 'true'){
        /**
         * 供应管理组助理 在订单审核中时不能编辑和删除
         */
        if (checkStatus == 1){
            $('#editSkuBtn').hide();
            $('#deleteSkuBtn').hide();
            /**
             * 供应管理组助理 在订单审核不通过时只有编辑 审核通过和 删除的权限
             */
        } else if(checkStatus == 2){
            $("#vetoBtn").hide();
            /**
             * 供应管理组助理 在订单审核通过时只有编辑 审核不通过和 删除的权限
             */
        } else if(checkStatus == 3){
            $('#approveBtn').hide();
        }
    } else {
        /**
         * 供应链账号在审核中时没有操作权限
         */
        if (checkStatus == 1){
            $('#editSkuBtn').hide();
            $('#deleteSkuBtn').hide();
            $('#approveBtn').hide();
            $("#vetoBtn").hide();
            /**
             * 供应链账号在订单审核中只有编辑和删除的权限
             */
        } else if(checkStatus == 2 || checkStatus == 3){
            $('#approveBtn').hide();
            $("#vetoBtn").hide();
        }
    }
})