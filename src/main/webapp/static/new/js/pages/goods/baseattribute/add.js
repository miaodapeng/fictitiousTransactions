$(function () {
    //校验
    var canSubmit = true;

    $('.J-form').validate({
        errorWrap: true,
        rules: {
            baseAttributeName: {
                required: true
            },
            attrValue: {
                required: true
            },
            unitId: {
                required: function () {
                    return $('.J-set-unit')[0].checked
                }
            }
        },
        messages: {
            baseAttributeName: {
                required: '请填写属性名称'
            },
            attrValue: {
                required: '请填写属性值'
            },
            unitId: {
                required: '请选择单位'
            }
        },
        submitHandler: function (form) {
            if (canSubmit) {
                canSubmit = false;

                $('.J-item-wrap').each(function (i) {
                    $(this).find('.J-sort-num').attr('name', 'attrValue[' + i + '].sort');
                    $(this).find('[name="baseAttributeValueId"]').attr('name', 'attrValue[' + i + '].baseAttributeValueId');
                    $(this).find('[name="valueCategoryNum"]').attr('name', 'attrValue[' + i + '].valueCategoryNum');
                    $(this).find('.J-attr-value').attr('name', 'attrValue[' + i + '].attrValue');
                    $(this).find('.J-attr-unit .J-unit-value').attr('name', 'attrValue[' + i + '].unitId');
                });

                window.localStorage.setItem('addAttrsuccess', true);
                form.submit();
            }
        }
    })

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    //获取单位数据
    var $unitSelect = $('.J-unit-tmpl');
    var unitData = [];

    $unitSelect.find('option').each(function () {
        unitData.push({
            label: $(this).attr('cz-label'),
            value: $(this).attr('cz-value')
        })
    });

    $unitSelect.remove();

    //初始化单位选择
    var initUnit = function ($item) {
        new SuggestSelect({
            placeholder: '请选择',
            wrap: $item.find('.J-attr-unit-wrap'),
            data: unitData,
            input: $item.find('.J-unit-value'),
            onchange: function () {
                $item.find('.J-unit-value').valid();
            }
        })
    };

    $('.J-item-wrap').each(function () {
        initUnit($(this))
    });

    //新增\删除属性
    var attrTmpl = $('.J-attr-tmpl').html();
    var $add = $('.J-attr-add');
    var $wrap = $('.J-attr-wrap');

    var changeVal = function () {
        $('.J-item-wrap').each(function (i) {
            $(this).find('.J-sort-num').val(i + 1);

            var $attrvalue = $(this).find('.J-attr-value');
            var $errorwrap = $attrvalue.siblings('.feedback-block');
            var $error = $errorwrap.find('label');

            $attrvalue.attr('id', 'attrvalue' + i);
            $errorwrap.length && $errorwrap.attr('wrapfor', 'attrvalue' + i);
            $error.length && $error.attr('for', 'attrvalue' + i);

            var $attrUnit = $(this).find('.J-unit-value');
            var $errorwrapUnit = $attrUnit.siblings('.feedback-block');
            var $errorUnit = $errorwrapUnit.find('label');

            $attrUnit.attr('id', 'attrUnit' + i);
            $errorwrapUnit.length && $errorwrapUnit.attr('wrapfor', 'attrUnit' + i);
            $errorUnit.length && $errorUnit.attr('for', 'attrUnit' + i);
        });
    };

    changeVal();

    var checkAttrLen = function () {
        var attrLength = $('.J-item-wrap').length;

        if (attrLength >= 50) {
            $add.hide();
        } else {
            $add.show();
        }

        if (attrLength <= 1) {
            $('.J-attr-del').hide();
        } else {
            $('.J-attr-del').show();
        }

        $('.J-attr-num').html(attrLength);
    };

    checkAttrLen();

    $add.click(function () {
        var $prev = $('.J-attr-wrap .J-item-wrap:last');
        $wrap.append(attrTmpl);

        if ($prev.length) {
            var unit = $prev.find('.J-unit-value').val();
            $('.J-attr-wrap .J-item-wrap:last').find('.J-unit-value').val(unit);
        }

        initUnit($('.J-attr-wrap .J-item-wrap:last'));
        checkAttrLen();
        changeVal();
        checkUnit();
    });

    $wrap.on('click', '.J-attr-del', function () {
        $(this).parents('.J-item-wrap:first').remove();
        checkAttrLen();
        changeVal();
    });

    //是否显示单位
    $setUnit = $('.J-set-unit');

    var checkUnit = function () {
        if ($setUnit[0].checked) {
            $('.J-attr-unit').show();
        } else {
            $('.J-attr-unit').hide();
        }
    };

    checkUnit();

    $setUnit.change(function () {
        checkUnit();
    });

    //排序
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
            var $items = $('.J-sort-num');
            var len = $items.length;
            var val = parseInt($(_this).val());

            if (val === 0 || val) {
                val = val > len ? len : (val < 1 ? 1 : val);

                var $parent = $(_this).parents('.J-item-wrap:first');
                var index = $parent.index();

                setTimeout(function () {
                    var $sort = $parent.clone(true);

                    if (val <= index) {
                        $('.J-item-wrap').eq(val - 1).before($sort);
                    } else {
                        $('.J-item-wrap').eq(val - 1).after($sort);
                    }

                    var $unit = $sort.find('.J-attr-unit-wrap');
                    $unit.after('<div class="J-attr-unit-wrap"></div>')
                    $unit.remove();
                    initUnit($sort);
                    $parent.remove();
                    changeVal();
                    checkAttrLen();
                })
            } else {
                changeVal();
            }

        }, 110);
    });

    //选择器初始化
    Select.use('select');

    //增加关闭提示
    GLOBAL.addtip();

});