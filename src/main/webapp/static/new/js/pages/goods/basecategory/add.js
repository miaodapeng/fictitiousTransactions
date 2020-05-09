$(function () {

    //校验
    var canSubmit = true;

    $('.J-form').validate({
        errorWrap: true,
        rules: {
            baseCategoryName: {
                required: true
            },
            baseCategoryType: {
                required: true
            }
        },
        messages: {
            baseCategoryName: {
                required: '请输入分类名称'
            },
            baseCategoryType: {
                required: '分类类型'
            }
        },
        submitHandler: function (form) {
            var baseAttributeId = [];
            var baseAttributeValueIds = [];

            $('.J-attr-item').each(function(){
                var name = $(this).find('.J-attr-name').val();
                var value = $(this).find('.J-attr-value').val();
                if(name && value){
                    baseAttributeId.push(name);
                    baseAttributeValueIds.push(value);
                }
            })

            if ($('[name=baseCategoryLevel]').val() == '3' && $('[name=baseCategoryId]').val() != null && baseAttributeId.length) {
                var categoryId = $('[name=baseCategoryId]').val();

                $.ajax({
                    url: './checkCategoryAttr.do',
                    data: {
                        baseCategoryId: categoryId,
                        attrName: baseAttributeId.join(','),
                        attrValue: baseAttributeValueIds.join(',')
                    },
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == -1) {
                            var dialog = artDialog.confirm('修改类目，会导致所有子商品参数信息丢失且不可恢复，并且会下架所有子商品，请确认是否继续修改？', '', {
                                fn: function () {
                                    if (canSubmit) {
                                        $('.J-continue-add').val('1');
                                        canSubmit = false;
                                        window.localStorage.setItem('addCategorysuccess', true);
                                        form.submit();
                                    }
                                }, text: '确认'
                            }, {
                                    fn: function () {
                                        dialog.close();
                                    }, text: '取消'
                                });
                        } else {
                            if (canSubmit) {
                                canSubmit = false;
                                window.localStorage.setItem('addCategorysuccess', true);
                                form.submit();
                            }
                        }
                    }
                })
            } else {
                if (canSubmit) {
                    canSubmit = false;
                    window.localStorage.setItem('addCategorysuccess', true);
                    form.submit();
                }
            }
        }
    })

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    //选择属性名和属性值
    var attrData = JSON.parse($('.J-attr-json').html());
    var selectOptions = [];

    $.each(attrData, function (i, obj) {
        selectOptions.push({
            label: obj.attrName,
            value: obj.attrId
        });
    });

    var parseData = function (data) {
        var resData = [];

        $.each(data, function (i, obj) {
            resData.push({
                label: obj.attrValueName,
                value: obj.attrValueId
            })
        })

        return resData;
    };

    var initAttrItem = function ($item) {
        var suggest = new SuggestSelect({
            placeholder: '请选择',
            wrap: $item.find('.J-attr-name-wrap'),
            data: selectOptions,
            input: $item.find('.J-attr-name'),
            onchange: function () {
                var attrValue = [];
                var $attrvalue = $item.find('.J-attr-value-wrap');

                $.each(attrData, function (i, obj) {
                    if (obj.attrId == $item.find('.J-attr-name').val()) {
                        attrValue = parseData(obj.attrValue);
                    }
                })

                $attrvalue.after('<div class="J-attr-value-wrap"></div>');
                $attrvalue.remove();
                new SuggestSelect({
                    placeholder: '请选择',
                    multi: true,
                    multiAll: true,
                    wrap: $item.find('.J-attr-value-wrap'),
                    data: attrValue,
                    input: $item.find('.J-attr-value')
                })
            }
        });

        suggest.onchange();
    };

    $('.J-attr-item').each(function () {
        initAttrItem($(this));
    });

    //check Add and Delete
    var checkLen = function () {
        var $items = $('.J-attr-item');
        var len = $items.length;

        if (len > 1) {
            $('.J-attr-del').show();
        } else {
            $('.J-attr-del').hide();
        }

        if (len >= 50) {
            $('.J-attr-add').hide();
        } else {
            $('.J-attr-add').show();
        }

        $('.J-attr-num').html(len);
    };

    checkLen();

    $('.J-attr-wrap').on('click', '.J-attr-del', function () {
        $(this).parents('.J-attr-item:first').remove();
        checkLen();
    });

    $('.J-attr-add').click(function () {
        $('.J-attr-wrap').append($('.J-attr-tmpl').html());
        checkLen();
        initAttrItem($('.J-attr-item:last'));
    });

    Select.use('select');

    //增加关闭提示
    GLOBAL.addtip();
})