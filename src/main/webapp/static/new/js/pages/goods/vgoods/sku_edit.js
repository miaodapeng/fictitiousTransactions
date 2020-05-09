$(function () {
    //校验
    $.validator.addMethod('decimal2', function (value, element, params) {
        return !value || /^[0-9]+(\.[0-9]{1,2})?$/.test(value);
    }, '数字保留小数点后两位');

    $.validator.addMethod('decimal1', function (value, element, params) {
        return !value || /^[0-9]+(\.[0-9]{1})?$/.test(value);
    }, '数字保留小数点后一位');

    $.validator.addMethod('number', function (value, element, params) {
        return !value || /^\d+(\.\d+)?$/.test(value);
    }, '必须是数字');




    $.validator.addMethod('needOne', function (value, element, params) {
        var flag = false;
        $('.J-tech-params .J-sort-item').each(function () {
            var $name = $(this).find('.J-sort-name');
            var $value = $(this).find('.J-sort-value');

            if ($.trim($name.val()) && $.trim($value.val())) {
                flag = true;
            }
        })

        return flag;
    }, '请填写技术参数');

    var canSubmit = true;

    $('.J-form').validate({
        errorWrap: true,
        rules: {
            model: {
                required: function () {
                    return $('.J-sku-type').val() == '1';
                },
            },
            spec: {
                required: function () {
                    return $('.J-sku-type').val() != '1';
                },
            },
            skuName: {
                required: true
            },
            materialCode: {
                maxlength: 50
            },
            supplyModel: {
                maxlength: 50
            },
            paramsValid: {
                needOne: true
            },
            packageLength: {
                decimal2: true,
                maxlength: 11
            },
            packageWidth: {
                decimal2: true,
                maxlength: 11
            },
            packageHeight: {
                decimal2: true,
                maxlength: 11
            },
            grossWeight: {
                decimal2: true,
                maxlength: 11
            },
            goodsLength: {
                decimal2: true,
                maxlength: 11
            },
            goodsWidth: {
                decimal2: true,
                maxlength: 11
            },
            goodsHeight: {
                decimal2: true,
                maxlength: 11
            },
            baseUnitId: {
                required: true,
                maxlength: 100
            },
            packingList: {
                maxlength: 200
            },
            unitId: {
                required: true
            },
            changeNum: {
                required: true,
                digits: true,
                maxlength: 16
            },
            minOrder: {
                required: true,
                number: true,
                maxlength: 20
            },
            afterSaleContent: {
                required: function () {
                    return $('.J-sku-type').val() == '1';
                }
            },
            qaYears: {
                decimal1: true,
                required: function () {
                    return $('.J-sku-type').val() == '1';
                },
                maxlength: 10
            },
            qaRule: {
                maxlength: 250
            },
            qaOutPrice: {
                maxlength: 20
            },
            qaResponseTime: {
                maxlength: 20
            },
            supplierExtendGuaranteePrice: {
                decimal2: true
            },
            returnGoodsConditions: {
                required: true
            },
            freightIntroductions: {
                required: true,
                maxlength: 250
            },
            exchangeGoodsConditions: {
                maxlength: 250
            },
            exchangeGoodsMethod: {
                maxlength: 250
            },
            goodsComments: {
                maxlength: 500
            },
            storageConditionOne: {
                required: true
            },
            storageConditionTwo: {
                required: true
            }
        },
        messages: {
            model: {
                required: '请填写制造商型号'
            },
            spec: {
                required: '请填写规格'
            },
            skuName: {
                required: '请填写商品名称'
            },
            baseUnitId: {
                required: '请选择SKU商品单位'
            },
            unitId: {
                required: '请选择商品最小单位'
            },
            changeNum: {
                required: '请填写内含最小商品数量',
                digits: '请输入整数'
            },
            afterSaleContent: {
                required: '请选择售后内容'
            },
            qaYears: {
                required: '请填写质保年限'
            },
            returnGoodsConditions: {
                required: '请选择退货条件'
            },
            freightIntroductions: {
                required: '请填写运费说明'
            },
            storageConditionOne: {
                required: '请选择存储条件1'
            },
            storageConditionTwo: {
                required: '请选择存储条件2'
            },
            minOrder: {
                required: '请填写最小起订量',
                digits: '请输入整数'
            }
        },
        submitHandler: function (form) {
            if (canSubmit) {
                canSubmit = false;
                window.localStorage.setItem('addSkuSuccess', 'true');
                $(".defaultZero").each(function(){
                    if($(this).val()==''){
                        $(this).val(0);
                    }
                })
                if (checkMinOrder()){
                    form.submit();
                }else{
                    canSubmit = true;
                }
            }
        }
    })

    window.localStorage.setItem('addSkuSuccess', '')

    $('.J-tech-params').on('blur', '.J-sort-name,.J-sort-value', function () {
        var $label = $('label[for=paramsValid]');

        if ($label.length && $label.css('display') !== 'none') {
            $('[name=paramsValid]').valid();
        }
    })

    //选择初始化
    Select.use('select');

    //默认带入商品名
    $('.J-model').change(function () {
        if (!$.trim($('.J-prod-name').val())) {
            $('.J-prod-name').val($.trim($('.J-spu-name').data('html')) + $(this).val());
        }
    })

    //上传
    var timeout = null;
    $('.J-upload').each(function (i) {
        var _this = this;
        new Upload({
            limit: 5,
            url: GLOBAL.IMGUPLOADURL,
            wrapper: $(this),
            uploadName: 'upload' + i,
            list: JSON.parse($(this).siblings('.J-upload-data').val() || '[]'),
            filters: {
                mime_types: [
                    { title: "Image files", extensions: "jpg,png,jpeg,bmp" }
                ],
                max_file_size: '5MB'
            },
            onchange: function () {
                $(_this).find('.J-upload-item').each(function (ii) {
                    var data = $(this).data('item');
                    $(this).find('.J-item-name').remove();
                    $(this).append('<input type="hidden" class="J-item-name" name="' + $(_this).attr("type") + '"  value="' + data.filePath + '/' + data.fileName + '" >');
                })
                if (i == 0) {
                    $(_this).find('[name^=upload]').valid();
                }
            },
            onError: function (error) {
                var errorMsg = {
                    TYPE: '上传图片格式为：JPG、PNG、JEPG、BMP格式',
                    SIZE: '图片大小不超过5M'
                }
                if (error) {
                    var $error = $(_this).siblings('.J-upload-error');
                    $error.show().find('label').html(errorMsg[error]).show();
                    timeout && clearTimeout(timeout);
                    timeout = setTimeout(function () {
                        $error.hide();
                    }, 3000)
                }
            }
        });
    })

    //文件上传
    $('.J-file-upload').each(function (i) {
        var _this = this;
        new Upload({
            limit: 10,
            url: GLOBAL.IMGUPLOADURL,
            wrapper: $(this),
            type: 'file',
            uploadName: 'uploadFile' + i,
            list: JSON.parse($(this).siblings('.J-upload-data').val() || '[]'),
            filters: {
                mime_types: [
                    { title: "Upload files", extensions: "*" }
                ],
                max_file_size: '10MB'
            },
            onchange: function () {
                $(_this).find('.J-upload-item').each(function (ii) {
                    var data = $(this).data('item');
                    $(this).find('.J-item-name').remove();
                    $(this).append('<input type="hidden" class="J-item-name" name="' + $(_this).attr("type") + '"  value="' + data.filePath + '/' + data.fileName + '" >');
                })
                if (i == 0) {
                    $(_this).find('[name^=upload]').valid();
                }
            },
            onError: function (error) {
                var errorMsg = {
                    SIZE: '文件大小不超过10M'
                }
                if (error) {
                    var $error = $(_this).siblings('.J-upload-error');
                    $error.show().find('label').html(errorMsg[error]).show();
                    timeout && clearTimeout(timeout);
                    timeout = setTimeout(function () {
                        $error.hide();
                    }, 3000)
                }
            }
        });
    })


    //排序序号
    var setNum = function ($wrap) {
        $('.J-sort-num', $wrap).each(function (i) {
            $(this).val(i + 1);
        })
    };

    $('.J-sort-wrap').each(function () {
        setNum($(this));
    })

    var sortInput = function (params) {
        var $wrap = $(params.wrap);

        var flag = true;
        var checkVal = function (el) {
            var $this = $(el);

            if (flag) {
                setTimeout(function () {
                    var val = $.trim($this.val());
                    if (val && !(/^\d*$/.test(val))) {
                        val = val.replace(/[^\d]+/g, '');
                        $this.val(val);
                    }
                }, 10)
            }
        };

        $wrap.on('compositionstart', '.J-sort-num', function () {
            flag = false;
        })

        $wrap.on('compositionend', '.J-sort-num', function () {
            flag = true;
        })

        $wrap.on('keyup', '.J-sort-num', function () {
            checkVal(this);
        });

        $wrap.on('change', '.J-sort-num', function () {
            checkVal(this);
            var _this = this;

            setTimeout(function () {
                var $items = $('.J-sort-num', $wrap);
                var len = $items.length;
                var val = parseInt($(_this).val());

                if (val === 0 || val) {
                    val = val > len ? len : (val < 1 ? 1 : val);

                    var $parent = $(_this).parents('.J-sort-item:first');
                    var index = $parent.index();

                    setTimeout(function () {
                        var $sort = $parent.clone(true);

                        if (val <= index) {
                            $('.J-sort-item', $wrap).eq(val - 1).before($sort);
                        } else {
                            $('.J-sort-item', $wrap).eq(val - 1).after($sort);
                        }

                        $parent.remove();
                        setNum($wrap);
                    })
                } else {
                    setNum($wrap);
                }

            }, 110);
        });

    };

    $('.J-sort-wrap').each(function () {
        sortInput({
            wrap: $(this)
        })
    })

    //检查添加或者删除是否显示
    var checkItemNum = function ($wrap) {
        var len = $('.J-sort-item', $wrap).length;
        var min = 1;
        var max = 30;

        if (len >= max) {
            $('.J-sort-add-option', $wrap).hide();
        } else {
            $('.J-sort-add-option', $wrap).show();
        }

        if (len <= min) {
            $('.J-sort-del', $wrap).hide();
        } else {
            $('.J-sort-del', $wrap).show();
        }
    };

    //获取name
    var paramsNames = [];

    $('.J-sort-wrap').each(function () {
        var $name = $(this).find('.J-sort-name').eq(0);
        var $value = $(this).find('.J-sort-value').eq(0);

        paramsNames.push({
            itemName: $name.attr('name'),
            itemValue: $value.attr('name')
        })
    })

    //参数添加
    var paramsTmpl = template($('.J-sort-tmpl').html());

    var addParamsItem = function ($wrap, params) {
        var defaults = {
            name: '',
            value: '',
            itemName: '',
            itemValue: ''
        };
        var index = $wrap.data('index') || 0;
        var paramsName = paramsNames[index];

        var data = $.extend({}, defaults, params, paramsName);
        var $list = $('.J-sort-list', $wrap);

        $list.append(paramsTmpl(data));

        setNum($wrap);
        checkItemNum($wrap);
    };

    $('.J-sort-add').click(function () {
        var $wrap = $(this).parents('.J-sort-wrap:first');
        addParamsItem($wrap)
    });

    //参数导入
    $('.J-sort-import').click(function () {
        var $wrap = $(this).parents('.J-sort-wrap:first');
        new artDialog({
            title: '复制文本导入参数',
            content: $('.J-import-tmpl').html(),
            init: function () { },
            width: 680,
            button: [{
                name: '导入',
                highlight: true,
                callback: function () {
                    var cnt = $.trim($('.J-import-cnt').val());

                    if (cnt) {
                        var params = cnt.split(/[;；]/);

                        $.each(params, function (i, item) {
                            if ($.trim(item)) {
                                var itemArr = item.split(/[:：]/);
                                var needInput = true;

                                $wrap.find('.J-sort-item').each(function () {
                                    if (needInput) {
                                        var $name = $(this).find('.J-sort-name');
                                        var $value = $(this).find('.J-sort-value');

                                        if (!($.trim($name.val())) && !($.trim($value.val()))) {
                                            $name.val(itemArr[0]);
                                            $value.val(itemArr[1] || '');
                                            needInput = false;
                                        }
                                    }
                                })

                                if ($wrap.find('.J-sort-item').length < 30 && needInput) {
                                    addParamsItem($wrap, {
                                        name: itemArr[0],
                                        value: itemArr[1] || ''
                                    })
                                }

                                var $label = $('label[for=paramsValid]');

                                if ($label.length && $label.css('display') !== 'none') {
                                    $('[name=paramsValid]').valid();
                                }
                            }
                        })
                    }
                }
            }, {
                name: '取消'
            }],
        })
    })

    //删除参数
    $(document).on('click', '.J-sort-del', function () {
        var $wrap = $(this).parents('.J-sort-wrap:first');

        $(this).parents('.J-sort-item:first').remove();
        checkItemNum($wrap);
        setNum($wrap);
    })

    $('.J-sort-wrap').each(function () {
        checkItemNum($(this));
    })

    //单位同步
    $('.J-sku-unit').change(function () {
        if ($(this).val()) {
            $('.J-sku-unit-value').html('（' + $.trim($(this).find('option:selected').html()) + '）');
        } else {
            $('.J-sku-unit-value').html('');
        }
    })

    $('.J-sku-unit').trigger('change');

    $('.sku-unit-select').change(function () {
        if ($(this).val()){
            $('.sku-unit-value').html('（' + $.trim($(this).find('option:selected').html()) + '）');
        } else {
            $('.sku-unit-value').html('');
        }
    });

    $('.sku-unit-select').trigger('change');

    //增加关闭提示
    GLOBAL.addtip();
});

/**
 * 校验最小起订量是否合法
 * 1.最大为9999;
 * 2.必须为正整数;
 * @returns {boolean}
 */
function checkMinOrder() {
    var minOrder = $('#minOrder').val();
    if (parseFloat(minOrder) > 9999){
        $('#minOrderMsg').html('最小起订量不得超过9999');
        return false;
    } else {
        $('#minOrderMsg').html('');
    }
    var type="^[0-9]*[1-9][0-9]*$";
    var r=new RegExp(type);
    var flag=r.test(minOrder);
    if(!flag){
        $('#minOrderMsg').html('最小起订量为正整数');
        return false;
    } else {
        $('#minOrderMsg').html('');
        return true;
    }
}