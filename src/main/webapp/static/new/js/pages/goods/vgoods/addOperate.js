$(function () {


    // 校验富文本必填方法
    $.validator.addMethod('requiredRichTxt', function (val, ele) {
        return $('[name=oprateInfoHtml]').length && $('[name=oprateInfoHtml]').val();
    });

/*    $.validator.addMethod('requiredOne', function (val, ele) {
        var flag = false;

        $('[name=seoKeyWordsArray]').each(function () {
            if ($.trim($(this).val())) {
                flag = true;
            }
        })

        return flag;
    });*/

    //校验
    var canSubmit = true;
    $('.J-form').validate({
        errorWrap: true,
        rules: {
            upload: {
                required: true
            },
            richTxt: {
                requiredRichTxt: true
            },
            keywordNeedValue: {
                requiredOne: true
            }
            /*,seoKeyWordsArray: {
                maxlength: 25
            },*/
            /*seoTitle: {
                maxlength: 50
            },*/
            /*seoDescript: {
                maxlength: 200
            }*/
        },
        messages: {
            upload: {
                required: '请上传商品图片'
            },
            richTxt: {
                requiredRichTxt: '请填写商品详情'
            },
            /*keywordNeedValue: {
                requiredOne: '请填写SEO关键词'
            },*/
            submitHandler: function (form) {
                if (canSubmit) {
                    canSubmit = false;
                    changeKeywordsName();
                    window.localStorage.setItem('addOperateSuccess', true);
                    form.submit();
                }
            }
        }
    })

    //上传
    var timeout = null;
    new Upload({
        limit: 5,
        wrapper: $('.J-upload'),
        uploadName: 'upload',
        url: page_url + '/vgoods/operate/fileUploadImg.do',
        dragable: true,
        list: JSON.parse($('.J-upload-data').val() || '[]'),
        onchange: function (data) {
            $('.J-upload').find('.J-upload-item').each(function (ii) {
                var data = $(this).data('item');
                $(this).find('.J-item-name').remove();
                $(this).append('<input type="hidden" class="J-item-name" name="goodsImage" value="' + data.httpUrl + data.filePath + data.fileName + '">');
            })
            $('.J-upload').find('[name=upload]').valid();
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
                var $error = $('.J-upload-error');
                $error.show().find('label').html(errorMsg[error]).show();
                timeout && clearTimeout(timeout);
                timeout = setTimeout(function () {
                    $error.hide();
                }, 3000)
            }
        }
    });

    //富文本
    UEDITOR_CONFIG.maximumWords = 2000;
    var editor = UE.getEditor('content');
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

    editor.addListener('blur', function () {
        setTimeout(function () {
            $('[name=richTxt]').valid();
        }, 100)
    })

    //改变关键词name
    /*var changeKeywordsName = function () {
        $('.J-keywords').each(function (i) {
            $(this).attr('name', 'seoKeyWordsArray[' + i + ']');
        })
    }

    var changKeyWordId = function () {
        $('[name=seoKeyWordsArray]').each(function (i) {
            $(this).attr('id', 'seoKeyWordsArray' + i);
            var $wrapfor = $(this).siblings('[wrapfor]');
            $wrapfor.attr('wrapfor', 'seoKeyWordsArray' + i);
            $wrapfor.find('label').attr('for', 'seoKeyWordsArray' + i);
        })
    };*/

    //校验关键词数量
    var checkKeywordLen = function () {
        var len = $('.J-keyword-item').length;

        if (len > 1) {
            $('.J-keyword-del').show();
        } else {
            $('.J-keyword-del').hide();
        }

        if (len < 10) {
            $('.J-keyword-add').show();
        } else {
            $('.J-keyword-add').hide();
        }

        changKeyWordId();
    };

    checkKeywordLen();

    //新增关键词
    $('.J-keyword-add').click(function () {
        $('.J-keyword-list').append($('.J-keyword-tmpl').html());
        checkKeywordLen();
    })

    /*$('.J-keyword-list').on('blur', '[name=seoKeyWordsArray]', function () {
        $('[name=keywordNeedValue]').valid();
    })*/

    $(document).on('blur', 'input', function () {
        $(this).valid();
    })

    $('.J-keyword-list').on('click', '.J-keyword-del', function () {
        $(this).parents('.J-keyword-item:first').remove();
        checkKeywordLen();
    });

    var getOperateInfo = function () {
        var infoStr = window.localStorage.getItem('operateInfo') || '[]';
        var info = JSON.parse(infoStr);
        var maxIndex = -1;
        var maxTime = 0;
        
        $.each(info, function(i, item){
            if (item && item.time > maxTime){
                maxIndex = i;
            }
        });

        if (info.length < 10){
            maxIndex += 1;
        }else{
            if (maxIndex == 9) {
                maxIndex = 0
            } else {
                maxIndex += 1;
            }
        }

        return {
            info: info,
            index: maxIndex
        };

    };

    $('.J-rich-preview').click(function (e) {
        e.preventDefault();
        var val = $('[name=oprateInfoHtml]').val();
        var info = getOperateInfo();
        var infoArr = info.info;
        var index = info.index;
        var date = new Date().valueOf();

        if (val) {
            infoArr[index] = {
                value: val,
                time: date
            };

            window.localStorage.setItem('operateInfo', JSON.stringify(infoArr));
            window.open($(this).attr('href') + '?randomStr=' + index);
        }
    })

    //增加关闭提示
    GLOBAL.addtip();
});