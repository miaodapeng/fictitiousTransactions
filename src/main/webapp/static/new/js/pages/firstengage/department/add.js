$(function () {
    //生产企业下拉搜索框
    // new SuggestSelect({
    //     placeholder: '请选择生产企业',
    //     wrap: '.J-suggest-wrap',
    //     data: JSON.parse($('.J-factory-json').html()),
    //     input: '.J-factory-input'
    // })

    $.validator.addMethod('requiredFee', function (val, ele) {
        var flag = true;

        $('.J-select-lv1').each(function () {
            if (!$(this).val()) {
                flag = false;
            }
        });

        return flag;
    });

    //校验
    var canSubmit = true;
    $('.J-form').validate({
        errorWrap: true,
        rules: {
            departmentName: {
                required: true
            },
            feeOne: {
                requiredFee: true
            }
        },
        messages: {
            departmentName: {
                required: '请填写科室名称'
            },
            feeOne: {
                requiredFee: '请选择收费项目'
            }
        },
        submitHandler: function (form) {
            if (canSubmit) {
                canSubmit = false;
                window.localStorage.setItem('addsuccess', true);
                form.submit();
            }
        }
    })

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    //收费项目多级联动
    var $list = $('.J-select-list');

    var lv1Data = JSON.parse($('.J-fee-json').html()) || [];
    var lv2Data = [], lv3Data = [];

    lv1Data = [{ label: '请选择', value: '' }].concat(lv1Data);


    var getOption = function (item) {
        return '<option value="' + item.value + '">' + item.label + '</option>';
    }

    //设置二级或三级数据
    var setData = function (data, lv) {
        var parentList = lv == 2 ? lv1Data : lv2Data;
        var list = lv == 2 ? [{ label: '全部', value: '' }] : [];

        $.each(parentList, function (i, item) {
            if (item.value == data) {
                list = list.concat(item.list || []);
            }
        })

        lv == 2 ? (lv2Data = list) : (lv3Data = list);
    };

    //重绘select
    var setSelectDom = function (list, el) {
        $.each(list, function (i, obj) {
            $(el).append(getOption(obj));
        })
    };

    //校验是否显示删除和增加
    var checkDel = function () {
        var len = $list.find('.J-select-wrap').length;

        if (len > 1) {
            $list.find('.J-select-del').show();
        } else {
            $list.find('.J-select-del').hide();
        }

        if (len >= 20) {
            $('.J-select-add').hide();
        } else {
            $('.J-select-add').show();
        }
    };

    checkDel();

    //删除
    $list.on('click', '.J-select-del', function () {
        $(this).parents('.J-select-wrap').remove();
        checkDel();
    })

    //增加
    $('.J-select-add').click(function () {
        $list.append($('.J-select-tmpl').html());

        var $wrap = $list.find('.J-select-wrap:last');
        setSelectDom(lv1Data, $wrap.find('.J-select-lv1'));
        Select.use($wrap.find('.J-select-lv1'));
        checkDel();
    })

    //第一级change事件
    $list.on('change', '.J-select-lv1', function () {
        setData($(this).val(), 2);
        var $wrap = $(this).parents('.J-select-wrap:first');
        var lv2Value = $wrap.find('.J-select-lv2').attr('cz-value');

        $('.J-lv2-wrap,.J-lv3-wrap', $wrap).hide();
        $('.J-select-lv2', $wrap).empty().val('');
        $('.J-select-lv3', $wrap).val('');

        setSelectDom(lv2Data, $wrap.find('.J-select-lv2'));

        if (lv2Value) {
            $('.J-select-lv2', $wrap).val(lv2Value).trigger('change');;
            $wrap.find('.J-select-lv2').attr('cz-value', '');
        }

        Select.unuse($wrap.find('.J-select-lv2'));

        setTimeout(function(){
            Select.use($wrap.find('.J-select-lv2'));
        }, 0)

        if ($(this).val() && lv2Data.length > 0) {
            $('.J-lv2-wrap', $wrap).show();
        }

        $(this).valid();
    });

    //第一级change事件
    $list.on('change', '.J-select-lv2', function () {
        setData($(this).val(), 3);
        var $wrap = $(this).parents('.J-select-wrap:first');
        var lv3Value = $wrap.find('.J-select-lv3').attr('cz-value');

        $('.J-lv3-wrap', $wrap).hide();
        $('.J-select-lv3', $wrap).val('');

        if ($(this).val() && lv3Data.length > 0) {
            if (lv3Value) {
                $('.J-select-lv3', $wrap).val(lv3Value);
                $wrap.find('.J-select-lv3').attr('cz-value', '');
            }

            $('.J-select-lv3', $wrap).siblings('.J-multi-wrap').remove();
            $('.J-select-lv3').before('<div class="J-multi-wrap"></div>');

            new SuggestSelect({
                wrap: $wrap.find('.J-multi-wrap'),
                search: false,
                data: lv3Data,
                multi: true,
                input: $wrap.find('.J-select-lv3'),
                placeholder: '全部'
            })

            $('.J-lv3-wrap', $wrap).show();
        }
    });

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    //初始化第一级
    $list.find('.J-select-wrap').each(function () {
        setSelectDom(lv1Data, $(this).find('.J-select-lv1'));
        var lv1Value = $.trim($(this).find('.J-select-lv1').attr('cz-value'));

        if (lv1Value) {
            $('.J-select-lv1', $(this)).val(lv1Value).trigger('change');
            $(this).find('.J-select-lv1').attr('cz-value', '');
        }
    })

    Select.use('.J-select-lv1');

    //增加关闭提示
    GLOBAL.addtip();
});

