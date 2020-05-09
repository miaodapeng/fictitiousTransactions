$(function () {
    //校验
    $.validator.addMethod("requiredCheck", function (value, element, params) {
        var hasVal = false;
        $(params).each(function () {
            if ($(this)[0].checked) {
                hasVal = true;
            }
        })
        return hasVal;
    });

    $.validator.addMethod("larger", function (value, element, params) {
        return parseFloat(value) > params;
    });

    $.validator.addMethod("registrationNumber", function (value, element, params) {
        return validRegistrationNumber;
    });

    var getGroupName = function (el) {
        var name = [];
        $(el).each(function () {
            name.push($(this).attr('name'));
        })

        return name.join(',');
    }

    // 生产企业生产许可证号或备案凭证编号
    jQuery.validator.addMethod("productCompanyLicence", function(value, element) {
        return this.optional(element) || /^[\u4e00-\u9fa5a-zA-Z0-9]+$/.test(value);
    }, "只可包含中文、英文、数字");

    // 生产企业生产许可证号或备案凭证编号
    jQuery.validator.addMethod("temperature", function(value, element) {
        return this.optional(element) || /^\-?[0-9]+(.[0-9]+)?~-?[0-9]+(.[0-9]+)?$/.test(value);
    }, "请正确填写温度范围；列：-3.5~10.0");


    //校验框架
    var canSubmit = true;
    $('.J-form').validate({
        errorWrap: true,
        ignore: '.ignore input',
        rules: {
            'registration.registrationNumber': {
                required: true,
                maxlength: 64,
                registrationNumber: true
            },
            'registration.manageCategoryLevel': {
                required: true
            },
            'registration.productCompany.productCompanyChineseName': {
                required: true,
                maxlength: 64
            },
            'registration.productCompany.productCompanyLicence': {
                required: true,
                maxlength: 25,
                productCompanyLicence:true
            },
            'registration.issuingDateStr': {
                required: true
            },
            'registration.effectiveDateStr': {
                required: true
            },
            goodsType: {
                required: true
            },
            effectiveDays: {
                required: true,
                digits: true,
                larger: 0
            },
            brandType: {
                required: true
            },
            conditionOne: {
                required: true
            },
            temperature: {
                temperature:true
            },
            standardCategoryType: {
                required: true
            },
            newStandardCategoryId: {
                required: true
            },
            oldStandardCategoryId: {
                required: true
            },
            upload0: {
                required: true
            },
            upload1: {
                required: true
            }
        },
        messages: {
            'registration.registrationNumber': {
                required: '请填写注册证号/备案凭证号',
                registrationNumber: '注册证号/备案凭证号已关联首营信息，请重新选择'
            },
            'registration.manageCategoryLevel': {
                required: '请选择管理类别'
            },
            'registration.productCompany.productCompanyChineseName': {
                required: '请填写生产企业'
            },
            'registration.productCompany.productCompanyLicence': {
                required: '请填写生产企业生产许可证号或备案凭证编号'
            },
            'registration.issuingDateStr': {
                required: '请选择批准日期'
            },
            'registration.effectiveDateStr': {
                required: '请选择有效期'
            },
            goodsType: {
                required: '请选择商品类型'
            },
            effectiveDays: {
                required: '请填写产品有效期限',
                digits: '请填写大于0的整数',
                larger: '请填写大于0的整数'
            },
            brandType: {
                required: '请选择商品品牌'
            },
            conditionOne: {
                required: '请选择存储条件'
            },
            standardCategoryType: {
                required: '请选择国标类型'
            },
            newStandardCategoryId: {
                required: '请选择新国际分类'
            },
            oldStandardCategoryId: {
                required: '请选择旧国际分类'
            },
            upload0: {
                required: '请上传注册证附件/备案凭证附件'
            },
            upload1: {
                required: '请上传营业执照'
            }
        },
        groups: {
            prodDate: 'prodStartDate,prodEndDate',
            condition: getGroupName('.J-condition')
        },
        submitHandler: function (form) {
            if (canSubmit) {
                canSubmit = false;
                window.localStorage.setItem('addsuccess', true);
                form.submit();
            }
        }
    })

    // $('.J-condition').rules("add", {
    //     requiredCheck: '.J-condition',
    //     messages: {
    //         requiredCheck: '请选择存储条件'
    //     }
    // })

    // $('.J-condition').change(function () {
    //     $(this).valid();
    // })

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    //展开收起
    $('.J-toggle-show').click(function () {
        var $optionalWrap = $(this).siblings('.J-optional');
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

    //导入
    var parseLoadData = function (data) {
        var parseData = {};
        for (var item in data) {
            parseData['registration.' + item] = data[item];
            if (item === 'productCompany') {
                for (var item1 in data[item]) {
                    parseData['registration.productCompany.' + item1] = data.productCompany[item1];
                }
            }
        }

        return parseData;
    };

    //日期空间初始化
    var Pikadays = [];
    $('.J-date').each(function (i) {
        var $this = $(this);
        Pikadays[i] = new Pikaday({
            format: 'yyyy-mm-dd',
            field: $(this)[0],
            firstDay: 1,
            yearRange: [1900, 2050],
            onSelect: function (date) {
                var date1 = new Date(date).valueOf();
                if (i == 0) {
                    if (!$('.J-date-2').val()) {
                        Pikadays[1].setDate(new Date(date1 + 5 * 365 * 24 * 60 * 60 * 1000));
                    }

                    Pikadays[1].setMinDate(date1);
                }

                if (i == 1) {
                    Pikadays[0].setMaxDate(date1);
                }
            }
        });
    })

    var checkDateRange = function () {
        var val1 = $('.J-date').eq(0).val();
        var val2 = $('.J-date').eq(1).val();

        if (val1) {
            Pikadays[1].setMinDate(new Date($.trim(val1)));
        }

        if (val2) {
            Pikadays[0].setMaxDate(new Date($.trim(val2)));
        }
    };

    checkDateRange();

    // var changeDate = function () {
    //     $('.J-block-1').find('.J-date').each(function (i) {
    //         var val = parseInt($(this).val());
    //         if (val == 0 || !val) {
    //             $(this).val('');
    //         } else {
    //             var date = new Date(val).valueOf();

    //             Pikadays[i].setDate(new Date(date + 5 * 365 * 24 * 60 * 60 * 1000));
    //         }
    //     })

    //     if ($('.J-date-1').val() && !$('.J-date-2').val()) {
    //         var date = new Date($('.J-date-1').val()).valueOf();

    //         Pikadays[1].setDate(new Date(date + 5 * 365 * 24 * 60 * 60 * 1000));
    //     }
    // };

    var register = null, prevRegister = null;
    var validRegistrationNumber = true;
    var prevNum = $.trim($('.J-suggest-input').val());
    var loadInfo = function () {
        register = $.trim($('.J-suggest-input').val());

        if (register && ((prevRegister && register !== prevRegister) || !prevRegister)) {
            prevRegister = register;
            $.ajax({
                url: page_url + '/firstengage/baseinfo/getFirstSearchInfoById.do',
                data: {
                    registrationNumber: register
                },
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data && data.code === 0) {
                        var resData = parseLoadData(data.data)
                        if (resData['registration.firstEngageId'] && prevNum !== register) {
                            validRegistrationNumber = false;
                        } else {
                            validRegistrationNumber = true;
                            $('.J-block-1').find('input').each(function () {
                                var name = $(this).attr('name');
                                if (name !== 'registration.registrationNumber' && !$.trim($(this).val())) {
                                    $(this).val(resData[name]);
                                }

                                if (name === 'registration.manageCategoryLevel' && !$('[name="registration.manageCategoryLevel"]:checked').length) {
                                    $('[name=\'' + name + '\']').each(function () {
                                        $(this)[0].checked = false;
                                        if ($(this).val() == resData[name]) {
                                            $(this)[0].checked = true;
                                        }
                                    })
                                }
                            })
                            // changeDate();

                            if (data.data) {
                                var $wrap = $('.J-suggest-input').parents('.form-block:first');
                                if (!$('.J-optional', $wrap).hasClass('show')) {
                                    $('.J-toggle-show', $wrap).trigger('click');
                                }
                            }

                            Pikadays[0].setDate(new Date($('.J-date').eq(0).val()));
                        }

                        checkDateRange();
                    }
                    $('.J-suggest-input').valid();
                }
            })
        }
    };

    //建议词搜索初始化
    new Suggest({
        el: '.J-suggest-input',
        url: page_url + '/firstengage/baseinfo/getRegistrationInfo.do',
        params: 'registrationStr',
        parseData: function (data) {
            var list = data.listData || [];
            var reslist = [];
            $.each(list, function (i, item) {
                reslist.push({
                    word: item.registrationNumber,
                    disabled: !!item.firstEngageId
                })
            })
            return reslist;
        }
    });

    $('.J-suggest-input').blur(function () {
        setTimeout(function () {
            loadInfo();
        }, 100)
    })

    //商品有效期显示隐藏
    $('.J-prod-type input').change(function () {
        var val = $('.J-prod-type input:checked').val();

        if (val == 316 || !val) {
            $('.J-prod-date').addClass('ignore').hide();
        } else {
            $('.J-prod-date').removeClass('ignore').show();
        }
    })

    $('.J-prod-type input').trigger('change');

    //温度显示隐藏
    $('[name=conditionOne]').change(function () {
        var val = $('[name=conditionOne]:checked').val();

        if (val) {
            $('.J-temp-wrap').show();
        } else {
            $('.J-temp-wrap').hide();
        }
    })

    $('[name=conditionOne]').trigger('change');

    //国际分类选择显示隐藏
    $('.J-inter-type input').change(function () {
        var val = $('.J-inter-type input:checked').val();
        if (val == 2) {
            $('.J-inter-old').removeClass('ignore').show();
            $('.J-inter-new').addClass('ignore').hide();
        } else if (val == 1) {
            $('.J-inter-new').removeClass('ignore').show();
            $('.J-inter-old').removeClass('ignore').show();
        }
    })

    $('.J-inter-type input').trigger('change');

    //旧国标国搜索下拉框
    new SuggestSelect({
        placeholder: '请选择',
        wrap: '.J-old-wrap',
        data: JSON.parse($('.J-old-data').html()),
        input: '.J-old-value'
    })

    //新国标三级联动初始化
    new DialogSearch({
        el: '.J-lv-search-select',
        data: JSON.parse($('.J-new-data').html()),
        input: '.J-inter-value',
        label: '.J-lv-search-select .J-text',
        placeholder: '请输入分类名/国际码',
        title: '请选择新国标分类',
        searchUrl: page_url + '/firstengage/baseinfo/getnewstandcategory.do',
        params: 'categoryName',
        searchList: [
            {
                label: '新国际分类',
                name: 'label',
                width: ''
            }
        ]
    })

    //选择框实例化
    Select.use('.J-select');

    //上传组件初始化
    timeout = null;
    var attachmentFunction = [975, 1000, 976, 977, 978, 979, 980, 981];
    var attachmentsName = ['zczAttachments', 'yzAttachments', 'smsAttachments', 'wsAttachments', 'scAttachments', 'sbAttachments', 'djbAttachments', 'cpAttachments'];
    $('.J-upload').each(function (i) {
        var _this = this;
        new Upload({
            limit: 5,
            url: GLOBAL.IMGUPLOADURL,
            wrapper: $(this),
            uploadName: 'upload' + i,
            list: JSON.parse($(this).siblings('.J-upload-data').val() || '[]'),
            onchange: function () {
                $(_this).find('.J-upload-item').each(function (ii) {
                    var data = $(this).data('item');
                    $(this).find('.J-item-name').remove();
                    $(this).append('<input type="hidden" class="J-item-name" name="registration.' + attachmentsName[i] + '[' + ii + '].attachmentFunction" value="' + attachmentFunction[i] + '">');
                    $(this).append('<input type="hidden" class="J-item-name" name="registration.' + attachmentsName[i] + '[' + ii + '].uri" value="' + data.filePath + '/' + data.fileName + '">');
                })
                if (i == 0 || i == 1) {
                    $(_this).find('[name^=upload]').valid();
                }
            },
            filters: {
                mime_types: [
                    { title: "Image files", extensions: "jpg,jpeg,png" }
                ],
                max_file_size: '5MB'
            },
            onError: function (error) {
                var errorMsg = {
                    TYPE: '上传图片格式为：JPG、PNG、JEPG格式',
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

    window.localStorage.removeItem('addsuccess');

    //增加关闭提示
    GLOBAL.addtip();
})