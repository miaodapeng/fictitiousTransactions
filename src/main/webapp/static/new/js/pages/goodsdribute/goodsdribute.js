$(function () {
    //后台报错显示
    var errorTip = function (tip) {
        var dia = artDialog.alert(tip, null, {
            fn: function () {
                dia.close();
            }, text: '我知道了'
        }, { type: "warn" });
    };

    var checkCanEdit = function () {
        var len = $('.J-select-item:checked').length;

        if (!len) {
            $('.J-distribute').addClass('btn-disabled');
        } else {
            $('.J-distribute').removeClass('btn-disabled');
        }
    };

    checkCanEdit();

    $('.J-select-item,.J-select-all').change(function () {
        checkCanEdit();
    })

    var distribute = function () {
        var dialog = new artDialog({
            title: '分配商品归属',
            content: $('.J-add-tmpl').html(),
            init: function () {
                Select.use('.J-add-form select');
            },
            width: 500,
            button: [{
                name: '确认',
                highlight: true,
                callback: function () {
                    var $form = $('.J-add-form');
                    var $manager = $('[name=manager]', $form);
                    var $assistant = $('[name=assistant]', $form);

                    if ($manager.val() || $assistant.val()) {
                        var ids = [];

                        $('.J-select-item:checked').each(function () {
                            ids.push($(this).val());
                        })

                        var sendData = {
                            assignmentManagerId: $manager.val(),
                            assignmentAssistantId: $assistant.val(),
                            spuIds: ids.join('@')
                        };

                        var addDistribution=function(senddata){
                            $.ajax({
                                url: page_url + '/goods/goodsdistribute/addDistribution.do',
                                data: sendData,
                                type: 'post',
                                dataType: 'json',
                                success: function (res) {
                                    // conDlg.close();
                                    if (res.code == 0) {
                                        window.localStorage.setItem('distributeSuccess', 'true');
                                        window.location.reload();
                                    } else {
                                        errorTip(res.message || '操作异常');
                                    }
                                    window.location.reload();
                                },
                                error:function(data){
                                    if(data.status ==1001){
                                        errorTip("当前操作无权限");
                                    }
                                }
                            })
                        }
                        $.ajax({
                            url: page_url + '/goods/goodsdistribute/isHaveDistribution.do',
                            data: sendData,
                            dataType: 'json',
                            success: function (res) {
                                dialog.close();
                                if (res.code == 0 && res.data == 1) {
                                    var conDlg = artDialog.confirm('所选数据中部分商品已有归属人，提交后将覆盖。', '', {
                                        fn: function () {
                                            addDistribution(sendData)
                                        }, text: '提交'
                                    }, {
                                            fn: function () {
                                            // conDlg.close();
                                            }, text: '取消'
                                        });
                                }else if(res.code == 0 && res.data == 2){
                                    addDistribution(sendData)
                                    // alert("操作成功")
                                }
                                else {
                                    errorTip(res.message || '操作异常');
                                }
                            },
                            error:function(data){
                                if(data.status ==1001){
                                    errorTip("当前操作无权限");
                                }
                            }
                        })
                    } else {
                        dialog.close();
                    }

                    return false;
                }
            }, {
                name: '取消'
            }],
        })
    };

    $('.J-distribute').click(function () {
        if ($('.J-select-item:checked').length) {
            distribute()
        }
    })

    $('.J-change-distribute').click(function () {
        var dialog = new artDialog({
            title: '归属人更换',
            content: $('.J-change-tmpl').html(),
            init: function () {
                Select.use('.J-change-form select');
            },
            width: 500,
            button: [{
                name: '确认',
                highlight: true,
                callback: function () {
                    var conDlg = artDialog.confirm('提交后，原归属人名下的商品将全部转移到新的归属人名下，是否继续操作?', '', {
                        fn: function () {
                            var $form = $('.J-change-form');
                            var $managerBefore = $('[name=managerBefore]', $form);
                            var $assistantBefore = $('[name=assistantBefore]', $form);
                            var $managerAfter = $('[name=managerAfter]', $form);
                            var $assistantAfter = $('[name=assistantAfter]', $form);

                            if ($managerBefore.val() || $assistantBefore.val() || $managerAfter.val() || $assistantAfter.val()) {
                                $.ajax({
                                    url: page_url + '/goods/goodsdistribute/editDistribution.do',
                                    data: {
                                        assignmentManagerIdOld: $managerBefore.val(),
                                        assignmentAssistantIdOld: $assistantBefore.val(),
                                        assignmentManagerIdNew: $managerAfter.val(),
                                        assignmentAssistantIdNew: $assistantAfter.val()
                                    },
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (res) {
                                        dialog.close();
                                        if (res.code == 0) {
                                            window.localStorage.setItem('distributeSuccess', 'true');
                                            window.location.reload();
                                        } else {
                                            errorTip(res.message || '操作异常');
                                        }
                                    }
                                })
                            } else {
                                dialog.close();
                            }
                        },
                        error:function(data){
                            if(data.status ==1001){
                                errorTip("当前操作无权限");
                            }
                        }
                        , text: '确定'
                    }, {
                        fn: function () {
                        }, text: '取消'
                    });

                    return false;
                }
            }, {
                name: '取消'
            }],
        })
    })

    //搜索建议词
    var parseBrandName = function (data) {
        var list = data.listData || [];
        var reslist = [];
        $.each(list, function (i, item) {
            reslist.push({
                word: item.brandName,
            })
        })
        return reslist;
    }

    $('.J-suggest-input').each(function () {
        var _this = this;
        new Suggest({
            el: this,
            url: page_url + $(this).data('url'),
            params: $(this).attr('name'),
            parseData: function (data) {
                return parseBrandName(data);
            }
        });
    })

    //筛选多选
    var getMultiData = function ($item) {
        var data = [];
        $item.siblings('select').find('option').each(function () {
            data.push({
                label: $.trim($(this).html()),
                value: $(this).val()
            })
        })

        return data;
    };

    $('.J-muiti-select').each(function () {
        var data = getMultiData($(this));

        $(this).siblings('select,.select').remove();

        //初始化品牌信息  
        new SuggestSelect({
            placeholder: '全部',
            wrap: $(this),
            data: data,
            multi: true,
            multiAll: false,
            input: $(this).siblings('.J-value'),
        })
    });

    var categoryData = [];
    var categoryLv2Data = [];

    var getSelectDom = function(data){
        var options =  '<option value="">请选择</option>';

        $.each(data, function (i, item) {
            options += '<option value="'+ item.value +'">'+ item.label +'</option>';
        })

        return options;
    };

    $.ajax({
        url: page_url + '/category/base/getCategoryList.do',
        dataType: 'json',
        success: function (res) {
            if(res.code == 0){
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

                            lv1item.child.push(lv2item);
                        })
                    }

                    categoryData.push(lv1item);
                })
            }

            var val1 = $('.J-select-cate1').data('value');
            var val2 = $('.J-select-cate2').data('value');
            var val3 = $('.J-select-cate3').data('value');

            $('.J-select-cate1').append('<select name="categoryLv1Name">' + getSelectDom(categoryData) + '</select>');
            $('.J-select-cate1 select').val(val1).trigger('change');
            Select.use('.J-select-cate1 select');

            console.log(val1, val2, val3)

            if(val2 && $('.J-select-cate2 select').length){
                Select.unuse('.J-select-cate2 select');
                $('.J-select-cate2 select').val(val2).trigger('change');
                setTimeout(function () {
                    Select.use('.J-select-cate2 select');
                }, 0)
            }

            if(val3 && $('.J-select-cate3 select').length){
                Select.unuse('.J-select-cate3 select');
                $('.J-select-cate3 select').val(val3);
                setTimeout(function () {
                    Select.use('.J-select-cate3 select');
                }, 0)
            }
        }
    });

    $('.J-select-cate1').on('change', 'select', function () {
        $('.J-select-cate2, .J-select-cate3').empty();

        var val = $.trim($(this).val());
        if(val){
            $.each(categoryData, function (i, item) {
                if(item.value == val && item.child.length){
                    categoryLv2Data = item.child;
                    $('.J-select-cate2').append('<select name="categoryLv2Name">' + getSelectDom(item.child) + '</select>');
                    Select.use('.J-select-cate2 select');
                }
            })
        }
    });

    $('.J-select-cate2').on('change', 'select', function () {
        $('.J-select-cate3').empty();

        var val = $.trim($(this).val());
        if(val){
            $.each(categoryLv2Data, function (i, item) {
                if(item.value == val && item.child.length){
                    $('.J-select-cate3').append('<select name="categoryLv3Name">' + getSelectDom(item.child) + '</select>');
                    Select.use('.J-select-cate3 select');
                }
            })
        }
    });

    $('.J-select-cate1').on('change', 'select', function () {
        $('.J-select-cate2, .J-select-cate3').empty();

        var val = $.trim($(this).val());
        if(val){
            $.each(categoryData, function (i, item) {
                if(item.value == val && item.child.length){
                    $('.J-select-cate2').append('<select name="categoryLv2Name">' + getSelectDom(item.child) + '</select>');
                    Select.use('.J-select-cate2 select');
                }
            })
        }
    });

    // //分类搜索三级
    // new LvSelect({
    //     el: '.J-category-wrap',
    //     input: '.J-category-value',
    //     async: true,
    //     url:
    //     parseData: function (res) {
    //         var categoryData = [];
    //
    //         $.each(res.listData, function (i, lv1) {
    //             var lv1item = {
    //                 label: lv1.baseCategoryName,
    //                 value: lv1.baseCategoryId,
    //                 child: []
    //             };
    //
    //             if (lv1.secondCategoryList) {
    //                 $.each(lv1.secondCategoryList, function (ii, lv2) {
    //                     var lv2item = {
    //                         label: lv2.baseCategoryName,
    //                         value: lv2.baseCategoryId,
    //                         child: []
    //                     };
    //
    //                     if (lv2.thirdCategoryList) {
    //                         $.each(lv2.thirdCategoryList, function (iii, lv3) {
    //                             lv2item.child.push({
    //                                 label: lv3.baseCategoryName,
    //                                 value: lv3.baseCategoryId
    //                             })
    //                         })
    //                     }
    //
    //                     if (lv2item.child.length) {
    //                         lv1item.child.push(lv2item);
    //                     }
    //                 })
    //             }
    //
    //             if (lv1item.child.length) {
    //                 categoryData.push(lv1item);
    //             }
    //         })
    //
    //         console.log(categoryData)
    //
    //         return categoryData;
    //     }
    // })

    //操作提示
    GLOBAL.showGlobalTip('操作成功', null, 'distributeSuccess');
});