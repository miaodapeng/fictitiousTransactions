$(function () {

    //校验
    var canSubmit = true;
    var addSpuRules={
        categoryId: {
            required: true
        },
        spuLevel: {
            required: true
        },
        brandId: {
            required: true
        },
        spuType: {
            required: true
        },
        firstEngageId: {
            required: true
        },
        assignmentAssistantId: {
            required: true
        },
        assignmentManagerId: {
            required: true
        },

        spuName: {
            required: true,
            maxlength: 128
        },
        showName: {
            required: true,
            maxlength: 128
        },
        departmentIds: {
            required: true
        },
        registrationIcon: {
            maxlength: 128
        },
        skuInfo: {
            required: true,
            maxlength: 100
        }
    };
    var updateRules={
        categoryId: {
            required: true
        },
        spuLevel: {
            required: true
        },
        brandId: {
            required: true
        },
        spuType: {
            required: true
        },
        firstEngageId: {
            required: true
        },

        spuName: {
            required: true,
            maxlength: 128
        },
        showName: {
            required: true,
            maxlength: 128
        },
        departmentIds: {
            required: true
        },
        registrationIcon: {
            maxlength: 128
        },
        skuInfo: {
            required: true,
            maxlength: 100
        }
    };
    var addMessages={
        categoryId: {
            required: '请选择分类'
        },
        spuLevel: {
            required: '请选择商品等级'
        },
        brandId: {
            required: '请选择品牌'
        },
        spuType: {
            required: '请选择商品类型'
        },
        firstEngageId: {
            required: '请选择首营信息'
        },
        spuName: {
            required: '请填写通用名'
        },
        showName: {
            required: '请填写商品名称'
        },
        departmentIds: {
            required: '请选择科室'
        },
        assignmentAssistantId: {
            required: '请选择归属的产品助理'
        },
        assignmentManagerId: {
            required: '请选择归属产品经理'
        },
        skuInfo: {
            required: function () {
                var val = $('.J-prod-type:checked').val();
                var isHaocai = val == 317 || val == 318 || val == 653;
                return isHaocai ? '请填写规格' : '请填写制造商型号'
            }
        }
    };

    updateMessages={
        categoryId: {
            required: '请选择分类'
        },
        spuLevel: {
            required: '请选择商品等级'
        },
        brandId: {
            required: '请选择品牌'
        },
        spuType: {
            required: '请选择商品类型'
        },
        firstEngageId: {
            required: '请选择首营信息'
        },
        spuName: {
            required: '请填写通用名'
        },
        showName: {
            required: '请填写商品名称'
        },
        departmentIds: {
            required: '请选择科室'
        },
        skuInfo: {
            required: function () {
                var val = $('.J-prod-type:checked').val();
                var isHaocai = val == 317 || val == 318 || val == 653;
                return isHaocai ? '请填写规格' : '请填写制造商型号'
            }
        }
    };
    var spuId=$("#spuId").val();
    var rules=addSpuRules;
    var messages=addMessages;
    if(spuId!=undefined&&spuId>0){
        rules=updateRules
        messages=updateMessages;
    }

    $('.assign').bind("change", function () {
        var user_id=($(this).val());
        if (user_id != undefined && user_id != '') {
            $(this).valid()
        }
    });
    $('.J-form').validate({
        errorWrap: true,
        rules: rules ,
        messages: messages,
        submitHandler: function (form) {
            if ($.trim($('[name=spuId]').val())) {
                var dialog = artDialog.confirm('商品信息有改动，会自动将所有子商品设置待审核状态，上架商品会自动下架，是否确认操作', '', {
                    fn: function () {
                        if (canSubmit) {
                            canSubmit = false;
                            window.localStorage.setItem('addSpuSuccess', true);
                            setCateHistory(historyName);
                            form.submit();
                        }
                    }, text: '确认'
                }, {
                        fn: function () {
                            dialog.close();
                        }, text: '取消'
                    });
            } else {
                canSubmit = false;
                window.localStorage.setItem('addSpuSuccess', true);
                setCateHistory(historyName);
                form.submit();
            }
        }
    })

    Select.use('.J-select');

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

    //重新渲染属性
    var refreshAttr = function (data) {
        if (data.length) {
            $('.J-attr-list').empty();
            $.each(data, function (i, item) {
                $('.J-attr-list').append(
                    '<label class="input-wrap"><input type="checkbox" name="baseAttributeIds" value="' + item.baseAttributeId + '">' +
                    '<span class="input-ctnr"></span>' + item.baseAttributeName +
                    '</label >'
                );
            })

            $('.J-attr-wrap').show();
        } else {
            $('.J-attr-list').empty();
            $('.J-attr-wrap').hide();
        }
    }

    //初始化页面时，没有属性值，隐藏属性模块
    if ($('.J-attr-wrap').length && $('.J-attr-wrap input:checkbox').length) {
        $('.J-attr-wrap').show();
    } else {
        $('.J-attr-wrap').hide();
    }

    //实例化选择分类
    var historyName = null;
    var setCateHistory = function (category) {
        var history = JSON.parse(localStorage.getItem('prod_category') || "[]") || [];

        if (category) {
            var historyList = [];
            $.each(history, function (i, item) {
                if (historyList.length < 9 && category.value != item.value) {
                    historyList.push(item);
                }
            });

            localStorage.setItem('prod_category', JSON.stringify([category].concat(historyList)));
        }
    };
    new DialogSearch({
        el: '.J-category-select',
        async: true,
        dataUrl: page_url + '/category/base/getCategoryList.do',
        input: '.J-category-value',
        label: '.J-category-select .J-text',
        searchUrl: page_url + '/category/base/getCategoryListByKeyWords.do',
        params: 'keyWords',
        placeholder: '请输入商品名/分类名/国际码',
        needTab: ['商品分类', '历史分类'],
        dataparse: function (data) {
            var resData = [];

            $.each(data.listData, function (i, lv1) {
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
                                    value: lv3.baseCategoryId,
                                    baseCategoryType: lv3.baseCategoryType
                                })
                            })
                        }

                        if (lv2item.child.length) {
                            lv1item.child.push(lv2item);
                        }
                    })
                }

                if (lv1item.child.length) {
                    resData.push(lv1item);
                }
            });

            return resData;
        },
        searchList: [
            {
                label: '分类',
                name: 'label',
                width: '380px'
            },
            {
                label: '分类类型',
                name: 'typeName',
                width: ''
            }
        ],
        parseSearchData: function (res) {
            var resData = [];

            $.each(res.listData, function (i, item) {
                resData.push({
                    value: item.baseCategoryId,
                    label: item.categoryJoinName,
                    baseCategoryType: item.baseCategoryType,
                    typeName: item.baseCategoryType == 1 ? '医疗器械' : '非医疗器械'
                })
            })

            return resData;
        },
        historyName: 'prod_category',
        onselect: function (obj, params) {
            if ($('.J-attr-wrap').length) {
                $.ajax({
                    url: page_url + '/goods/vgoods/getAttributeInfoByCategoryId.do',
                    data: {
                        categoryId: obj.value
                    },
                    dataType: 'json',
                    success: function (res) {
                        if (res.code == 0) {
                            refreshAttr(res.data || []);
                        }
                    }
                })
            }

            //判断是不是显示首营信息
            if (params.baseCategoryType == 1){
                $('.J-first-block').show();
                $('.J-firstenage-value')[0].disabled = false;
            }else{
                $('.J-first-block').hide();
                $('.J-firstenage-value')[0].disabled = true;
            }

            historyName = {
                value: obj.value,
                label: obj.label,
                baseCategoryType: params.baseCategoryType,
                typeName: params.baseCategoryType == 1 ? '医疗器械' : '非医疗器械'
            };

            setTimeout(function(){
                $('.J-category-value').valid();
            }, 100);
        }
    })

    //商品名称自动带入
    var $common = $('.J-common-name');
    var setProdName = function () {
        var $brand = $('.J-brandId');
        var $prodName = $('.J-prod-name');
        var level = $('.J-spu-level').val();

        if (level != 2) {
            if ($common.length && $common.val() && $brand.val() && !$prodName.val()) {
                $prodName.val($.trim($('.J-brand-wrap .J-text').html()) + $common.val()).valid();
            }
        } else {
            if ($brand.val() && !$prodName.val()) {
                $prodName.val($.trim($('.J-brand-wrap .J-text').html())).valid();
            }
        }
    };

    if ($common.length) {
        $common.blur(function () {
            setProdName();
        })
    }


    /** 标签 */
    //设置传给后台的标签的值
    var setTagVal = function () {
        var values = [];
        $('.J-tag-wrap .J-tag-item').each(function () {
            values.push($(this).data('value'));
        })

        $('.J-tag-value').val(values.join('@_@'));
    };

    //新增一个标签
    var addTag = function (val) {
        $('.J-tag-wrap').append('<div class="tag-item J-tag-item" data-value="' + val + '">' + val + '<i class="vd-icon icon-delete J-tag-del"></i></div>');
        setTagVal();
    };

    //初始化已添加的标签
    var tagVal = $('.J-tag-value').val();

    if (tagVal) {
        $.each(tagVal.split('@_@'), function (i, obj) {
            addTag(obj);
        });
    }

    //隐藏添加标签的输入框
    var hideTagInput = function () {
        $('.J-tag-new').hide();
        $('.J-tag-add').show();
        $('.J-tag-input').val('');
    };

    //点击添加标签
    $('.J-tag-add').click(function () {
        $('.J-tag-new').show();
        $(this).hide();
    })

    //点击取消
    $('.J-tag-add-cancel').click(function () {
        hideTagInput();
    })

    //点击确定
    $('.J-tag-add-confirm').click(function () {
        var value = $.trim($('.J-tag-input').val());
        if (value) {
            addTag(value);
            hideTagInput();
        } else {
            $('.J-tag-input').focus();
        }
    })

    //输入框回车
    $('.J-tag-input').keydown(function (e) {
        if (e.keyCode === 13 || e.keyCode === 108) {
            addTag($.trim($(this).val()));
            hideTagInput();
            return false;
        }
    })

    //删除已添加的标签
    $('.J-tag-wrap').on('click', '.J-tag-del', function () {
        $(this).parents('.J-tag-item:first').remove();
        setTagVal();
    })

    //点击建议的标签
    $('.J-tag-list').on('click', '.J-tag-item', function () {
        addTag($(this).data('value'));
    })

    //初始化品牌信息  
    new SuggestSelect({
        placeholder: '请选择商品品牌',
        wrap: '.J-brand-wrap',
        // async: true,
        // needRefresh: true,
        // url: page_url + '/firstengage/brand/brandName.do?pageSize=10',
        input: '.J-brandId',
        searchUrl: page_url + '/firstengage/brand/brandName.do?pageSize=10',
        asyncSearch: true,
        asyncSearchName: 'brandName',
        dataparse: function (data) {
            var resData = [];
            $.each(data.listData, function (i, obj) {
                resData.push({
                    label: obj.brandName,
                    value: obj.brandId
                })
            })

            return resData;
        },
        onchange: function (data) {
            $('.J-brandId').valid();
            setProdName();
        }
    })

    if ($('.J-brand-wrap').data('name')){
        $('.J-brand-wrap .J-text').html($('.J-brand-wrap').data('name'));
    }

    //商品类型切换
    var checkProdType = function () {
        var val = $('.J-prod-type:checked').val();

        var isHaocai = val == 317 || val == 318 || val == 653;

        if (isHaocai) {
            $('.J-prod-type-txt').html('规格');
        } else {
            $('.J-prod-type-txt').html('制造商型号');
        }

        var $error = $('[wrapfor=skuInfo]');

        if ($error.length) {
            $error.find('.error').hide();
        }
    };

    checkProdType();

    $('.J-prod-type').change(function () {
        checkProdType();
    })

    //首营信息
    var firstTmpl = template($('.J-first-tmpl').html());
    var refreshFirstenage = function (id, relabel) {
        $.ajax({
            url: page_url + '/goods/vgoods/getFirstEngageById.do',
            data: {
                firstEngageId: id
            },
            dataType: 'json',
            success: function (res) {
                if (res.code == 0) {
                    $('.J-first-detail').empty();
                    $('.J-first-detail').append(firstTmpl(res.data)).show();
                    if (relabel) {
                        $('.J-firstenage-select .J-text').html(res.data.registrationNumber);
                    }
                }
            }
        })
    };

    if ($('.J-firstenage-value').val()) {
        refreshFirstenage($('.J-firstenage-value').val(), true);
    }

    new DialogSelect({
        el: '.J-firstenage-select',
        title: '请选择注册证号/备案凭证号',
        placeholder: '请输入注册证号/备案凭证号/生产企业/商品品牌',
        input: '.J-firstenage-value',
        searchList: [{
            label: '注册证号/备案凭证号',
            name: 'registrationNumber',
            width: '200px'
        },
        {
            label: '生产企业',
            name: 'productCompanyChineseName',
            width: '200px'
        },
        {
            label: '商品品牌',
            name: 'brandName',
            width: ''
        }],
        dataUrl: page_url + '/goods/vgoods/searchFirstEngages.do?',
        searchName: 'searchValue',
        valueName: 'firstEngageId',
        searchInitVal: function(){
            return $('.J-brandId').val() ? $.trim($('.J-brand-wrap .J-text').html()) : ''
        },
        onselect: function (id, obj) {
            refreshFirstenage(id);
            $('.J-firstenage-select .J-text').html(obj.registrationNumber);
            $('.J-firstenage-value').valid();
        }
    })

    //科室
    var setSuggestTimeout = null;
    var setSuggestLabels = function () {
        var ids = [];

        $('.J-department').each(function () {
            if ($(this)[0].checked) {
                ids.push($(this).val());
            }
        })

        if (!ids.length) {
            $('.J-tag-add').siblings('.J-tag-item').remove();
        } else {
            $.ajax({
                url: page_url + '/goods/vgoods/getFeeItemByDeptIds.do',
                data: {
                    departmentIds: ids.join(',')
                },
                dataType: 'json',
                success: function (res) {
                    if (res.code == 0 && res.data) {
                        $('.J-tag-add').siblings('.J-tag-item').remove();
                        $.each(res.data, function (i, item) {
                            $('.J-tag-add').before('<div class="tag-item J-tag-item" data-value="' + item.feePro + '">' + item.feePro + '</div>')
                        });
                    }
                }
            })
        }
    }

    $('.J-department').change(function () {
        setSuggestTimeout && clearTimeout(setSuggestTimeout);

        setSuggestTimeout = setTimeout(function () {
            setSuggestLabels();
        }, 300);
    });    

    window.localStorage.setItem('addSpuSuccess', '')

    //增加关闭提示
    GLOBAL.addtip();
})