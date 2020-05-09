$(function () {
    //校验
    var canSubmit = true;
    $('.J-form').validate({
        errorWrap: true,
        rules: {
            brandNature: {
                required: true
            },
            brandName: {
                required: function () {
                    return $('[name=brandNature]:checked').val() == 1;
                }
            },
            brandNameEn: {
                required: function () {
                    return $('[name=brandNature]:checked').val() == 2;
                }
            },

            upload0: {
                required: true
            }
        },
        messages: {
            brandNature: {
                required: '请选择品牌类型'
            },
            brandName: {
                required: '请填写品牌名称（中文名）'
            },
            brandNameEn: {
                required: '请填写品牌名称（英文名）'
            },

            upload0: {
                required: '请上传品牌Logo'
            }
        },
        submitHandler: function (form) {
            if (canSubmit) {
                canSubmit = false;
                window.localStorage.setItem('addBrandsuccess', true);
                form.submit();
            }
        }
    })

    //上传
    var timeout = null;
    var attachmentFunction = [0, 988];
    var attachmentsName = ['logoUri', 'proof'];
    $('.J-upload').each(function (i) {
        var _this = this;
        new Upload({
            limit: $(this).data('limit'),
            wrapper: $(this),
            uploadName: 'upload' + i,
            url: GLOBAL.IMGUPLOADURL,
            list: JSON.parse($(this).siblings('.J-upload-data').val() || '[]'),
            onchange: function (data) {
                $(_this).find('.J-upload-item').each(function (ii) {
                    var data = $(this).data('item');
                    $(this).find('.J-item-name').remove();
                    var num = i == 0 ? '' : '[' + ii + ']';
                    $(this).append('<input type="hidden" class="J-item-name" name="' + attachmentsName[i] + num + '.attachmentFunction" value="' + attachmentFunction[i] + '">');
                    $(this).append('<input type="hidden" class="J-item-name" name="' + attachmentsName[i] + num + '.uri" value="' + data.filePath + '/' + data.fileName + '">');
                })
                if (i == 0) {
                    $(_this).find('[name^=upload]').valid();
                }
            },
            filters: {
                mime_types: [
                    { title: "Image files", extensions: "jpg,jpeg,png,bmp" }
                ],
                max_file_size: '5MB'
            },
            onError: function (error) {
                var errorMsg = {
                    TYPE: '上传图片格式为：JPG、JEPG、PNG、BMP格式',
                    SIZE: '上传图片大小不能超过5M'
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

    //生产企业下拉搜索框
    new SuggestSelect({
        placeholder: '请选择生产企业',
        wrap: '.J-suggest-wrap',
        data: JSON.parse($('.J-factory-json').html()),
        input: '.J-factory-input',
        multi: true,
        multiAll: false,
        onchange: function () {
            $('.J-factory-input').valid();
        }
    })

    $('[valid-max]').each(function () {
        $(this).rules('add', {
            maxlength: $(this).attr('valid-max')
        })
    })

    var checkBrandName = function () {
        if ($('[name=brandNature]:checked').val() == 1) {
            $('.J-en-must').hide();
            $('.J-cn-must').show();
            $('.J-brandName').eq(1).valid();
        } else {
            $('.J-en-must').show();
            $('.J-cn-must').hide();
            $('.J-brandName').eq(0).valid();
        }
    }

    $('[name=brandNature]').change(function () {
        checkBrandName();
    })

    checkBrandName();

    //富文本
    UE.getEditor('content');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl'
            || action == 'uploadimage' || action == 'uploadfile'
            || action == 'uploadvideo') {//上传附件类型
            return page_url + '/fileUpload/ueditFileUpload.do?uploadType=uedit';//自定义编辑器中附件上传路径
        } else {
            return this._bkGetActionUrl.call(this, action);//插件默认上传
        }
    }

    //增加关闭提示
    GLOBAL.addtip();
});

