$(function () {
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

    //计步器
    $('.J-input-number').each(function () {
        var $parent = $(this).parents('.J-prod-item:first');
        new InputNumber({
            el: this,
            value: $(this).data('num'),
            onchange: function (val) {
                var data = $parent.find('.J-edit-prod').data('item');
                data.quantity = val;

                $parent.find('.J-edit-prod').data('item', data);
                $parent.find('[name=objectJson]').val(JSON.stringify(data));
                $parent.find('[name=quantity]').val(val);
            }
        })
    })

    //校验列表数据
    var checkListLen = function () {
        var len = $('.J-prod-item').length;

        if (len > 0) {
            $('.J-list-nodata').hide();
        } else {
            $('.J-list-nodata').css('display', 'table-row');
        }

        if (len >= 99) {
            $('.J-add-prod').addClass('btn-disabled');
        } else {
            $('.J-add-prod').removeClass('btn-disabled');
        }

        $('.J-combo-num').html(len);
    };

    //添加/编辑商品
    var prodItemTmpl = template($('.J-prod-item-tmpl').html());
    var $edit = null;

    var addProdItem = function (data, type) {
        var $item = $(prodItemTmpl({ data: data }));
        if (type === 'edit') {
            var $parent = $edit.parents('.J-prod-item:first');

            $parent.after($item);
            $parent.remove();
        } else {
            $('.J-list-wrap').append($item);
        }

        new InputNumber({
            el: $item.find('.J-input-number'),
            value: $item.find('.J-input-number').data('num'),
            onchange: function (val) {
                var data = $item.find('.J-edit-prod').data('item');
                data.quantity = val;

                $item.find('.J-edit-prod').data('item', data);
                $item.find('[name=objectJson]').val(JSON.stringify(data));
                $item.find('[name=quantity]').val(val);
            }
        })

        checkListLen();
        $('[name=details]').valid();
    };

    checkListLen();

    var defaultItem = {
        skuSign: '',
        skuId: '',
        departmentIds: '',
        quantity: 1,
        purpose: '',
        remark: '',
        skuUnitName: '',
        checkStatus: '',
        status: ''
    };

    // var departmentList = [{
    //     label: '科室',
    //     value: ''
    // }];

    var setDepartment = function (list) {
        $('.J-department-wrap').empty();
        var list = list || [];

        if (list.length) {
            $.each(list, function (i, item) {
                $('.J-department-wrap').append('<label class="input-wrap">' +
                    '<input type="checkbox" value="' + item.departmentId + '" name="departmentIds" data-name="' + item.departmentName + '" >' +
                    '<span class="input-ctnr"></span>' + item.departmentName +
                    '</label >');
            })
            $('.J-department-block').show();
        } else {
            $('.J-department-block').hide();
        }
    };

    var showProd = function (data) {
        var prodData = $.extend({}, defaultItem, data);
        var tmpl = template($('.J-prod-add-tmpl').html());
        var dialog = new artDialog({
            content: tmpl(prodData),
            title: '添加商品',
            init: function () {
                new InputNumber({
                    el: '.J-dlg-input-number',
                    name: 'quantity',
                    value: $('.J-dlg-input-number').data('num')
                })

                new SuggestSelect({
                    placeholder: '请选择商品名称',
                    wrap: '.J-suggest',
                    input: '.J-prod-input',
                    searchUrl: '/goods/vgoods/searchSkuWithDepartment.do',
                    asyncSearch: true,
                    asyncSearchName: 'skuName',
                    searchPlaceholder: '请搜索商品名/订货号',
                    dataparse: function (res) {
                        var resData = [];

                        $.each(res.data, function (i, item) {
                            resData.push({
                                label: item.skuName,
                                value: item.skuId,
                                departments: item.departments,
                                skuUnitName: item.skuUnitName,
                                purpose: item.purpose,
                                status: item.status,
                                checkStatus: item.checkStatus
                            })
                        })

                        return resData;
                    },
                    onchange: function (data, params) {
                        $('.J-prod-input').valid();

                        setDepartment(params.departments);

                        $('.J-sku-unit-txt').html(params.skuUnitName);
                        $('.J-sku-unit-value').val(params.skuUnitName);

                        if (params.purpose && !$('.J-dlg-form [name=purpose]').val()) {
                            $('.J-dlg-form [name=purpose]').val(params.purpose);
                        }

                        $('.J-dlg-form [name=status]').val(params.status);
                        $('.J-dlg-form [name=checkStatus]').val(params.checkStatus);
                    }
                })

                $('.J-suggest .J-text').html(prodData.skuName);

                setDepartment(prodData.departments);

                var $form = $('.J-dlg-form');

                $('[name=departmentIds]', $form).each(function () {
                    var $this = $(this);
                    $.each(prodData.departmentIds.split(','), function (i, obj) {
                        if ($this.val() == obj) {
                            $this[0].checked = true;
                        }
                    })
                });

                $('.J-dlg-form').validate({
                    errorWrap: true,
                    rules: {
                        skuSign: {
                            required: true
                        },
                        departmentIds: {
                            required: function () {
                                return $('[name=setMealType]:checked').val() == 2;
                            }
                        },
                        skuId: {
                            required: true
                        },
                        purpose: {
                            required: true,
                            maxlength: 200
                        },
                        remark: {
                            maxlength: 500
                        }
                    },
                    messages: {
                        skuSign: {
                            required: '请选择商品标记'
                        },
                        departmentIds: {
                            required: '请选择科室'
                        },
                        skuId: {
                            required: '请选择产品名称'
                        },
                        purpose: {
                            required: '请填写用途'
                        }
                    }
                })
            },
            width: 680,
            button: [{
                name: '确定',
                highlight: true,
                callback: function () {
                    var $form = $('.J-dlg-form');
                    if ($('.J-dlg-form').valid()) {
                        var formData = {};

                        $.each($('.J-dlg-form').serializeArray(), function (i, obj) {
                            if (!formData[obj.name]) {
                                formData[obj.name] = obj.value;
                            } else {
                                formData[obj.name] += ',' + obj.value;
                            }
                        })

                        var skuName = $.trim($('.J-suggest .J-text').html());
                        formData.skuName = skuName;

                        var departmentNames = [];
                        $('[name=departmentIds]:checked', $form).each(function () {
                            departmentNames.push($(this).data('name'));
                        });
                        formData.departmentNames = departmentNames.join('、');

                        formData.departments = [];
                        $('[name=departmentIds]', $form).each(function () {
                            formData.departments.push({
                                departmentId: $(this).val(),
                                departmentName: $(this).data('name')
                            });
                        });

                        if (data) {
                            addProdItem(formData, 'edit');
                        } else {
                            addProdItem(formData);
                        }

                        dialog.close();
                    }

                    return false;
                }
            }, {
                name: '取消'
            }]
        })
    };

    $('.J-add-prod').click(function (e) {
        e.preventDefault();

        if ($('.J-prod-item').length < 99) {
            showProd();
        }
    })

    $('.J-list-wrap').on('click', '.J-edit-prod', function (e) {
        e.preventDefault();
        $edit = $(this);
        var data = $(this).data('item');
        showProd(data);
    })

    //套餐类型修改，判断是否需要科室字段
    $('[name=setMealType]').change(function () {
        if ($('[name=setMealType]:checked').val() == 2) {
            var flag = true;
            $('[name=departmentIds]').each(function () {
                if (!$(this).val()) {
                    flag = false;
                }
            })

            if (!flag) {
                var dia = artDialog.alert('套餐明细中存在科室为空的商品，请同步修改。', null, {
                    fn: function () {
                        dia.close();
                    }, text: '我知道了'
                }, { type: "warn" });
            }
        }
    });

    //删除商品
    $('.J-list-wrap').on('click', '.J-item-del', function () {
        var _this = this;
        var dialog = artDialog.confirm('确认删除么？', '', {
            fn: function () {
                $(_this).parents('.J-prod-item:first').remove();
            }, text: '确认'
        }, {
                fn: function () {
                    dialog.close();
                }, text: '我再想想'
            });
    });

    //校验
    var canSubmit = true;

    $('.J-form').validate({
        errorWrap: true,
        rules: {
            setMealName: {
                required: true,
                maxlength: 50
            },
            setMealType: {
                required: true
            },
            setMealDescript: {
                maxlength: 500
            },
            details: {
                required: function () {
                    return $('.J-prod-item').length <= 0;
                }
            },
            departmentIds: {
                required: function () {
                    return $('[name=setMealType]:checked').val() == 2;
                }
            }
        },
        messages: {
            setMealName: {
                required: '请填写套餐名称',
                maxlength: '输入文字超过字符限制'
            },
            setMealType: {
                required: '请选择套餐类型'
            },
            details: {
                required: '请添加套餐明细'
            },
            setMealDescript: {
                maxlength: '文字超过字符限制，限制输入'
            },
            departmentIds: {
                required: '请选择科室'
            }
        },
        submitHandler: function (form) {
            if (canSubmit) {
                $('.J-prod-item').each(function (i) {
                    $(this).find('input:hidden').each(function () {
                        $(this).attr('name', 'setMealSkuMappingVoList[' + i + '].' + $(this).attr('name'));
                    })
                })

                window.localStorage.setItem('AddMealSuccess', true);
                canSubmit = false;
                form.submit();
            }
        }
    })

    window.localStorage.setItem('AddMealSuccess', '');

    //增加关闭提示
    GLOBAL.addtip();
})
