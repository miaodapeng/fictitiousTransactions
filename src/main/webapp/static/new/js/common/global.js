//获取当前页面请求路径
var curWwwPath = window.document.location.href;
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
if (projectName != '/erp') {
    projectName = "";
}
var page_url = curWwwPath.substring(0, pos) + projectName;

var parentWindow = $(window.parent.document);
var parentParentWindow = $(window.parent.parent.document);

// 1、关闭当前页，跳到上一页，控制它的上一页（刷新/不刷新）；
// 2、不关当前页，控制它的上一页（刷新/不刷新）；// ；页面控制页面
// 15/16/17
function pagesContrlpages(closeSelf, freshSelf, freshFrontPage) {
    //上一页的Id；
    var frontPageId = parentWindow.find('.active').eq(1).children('iframe').attr('data-frontpageid');
    var frontPageFrame = parentWindow.find("#" + frontPageId).children('iframe');
    var frontPageSrc = parentWindow.find("#" + frontPageId).children('iframe').attr('src');
    var frontPageIndex = parentWindow.find("#" + frontPageId).index();
    var frontPageDataUrl = parentWindow.find("#" + frontPageId).children('iframe').attr('data-url');
    var selfId = parentWindow.find('.active').eq(0).attr('id');
    if (freshFrontPage) {
        // 效果，页面在加载数据的时候有提示框一直在加载
        var div = '<div class="tip-loadingNewData" id="loadingNewData-' + frontPageId + '" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue"></i></div>';
        $('#' + frontPageId, window.parent.document).prepend(div); //jq获取上一页框架的父级框架；
        if (frontPageDataUrl != undefined && frontPageDataUrl != '') {
            frontPageSrc = frontPageDataUrl;
        }
        frontPageFrame.attr('src', frontPageSrc);
    }
    // 刷新当前页
    if (freshSelf) {
        reloading();
    }
    // 关闭当前页    
    if (closeSelf) {
        parentWindow.find("#" + frontPageId).addClass('active');
        parentWindow.find('.nav li').removeClass('active').eq(frontPageIndex).addClass('active');
        parentWindow.find('#' + selfId + ' i[tabclose]').click(); //关闭当前页。
    }
}
// 18
//关闭上一页;
function closeFrontPage(closeFrontPage) {
    var frontPageId = parentWindow.find('.active').eq(1).children('iframe').attr('data-frontpageid');
    var frontPageIndex = parentWindow.find("#" + frontPageId).index();
    if (closeFrontPage) {
        parentWindow.find('.title li').eq(frontPageIndex).find('i[tabclose]').click();
    }
}
// 19
// 调用时加上参数，ID，名称，标题名称，是否关闭
function openNewPage(num, title, url) {
    var frontPageId = parentWindow.find('.active').eq(1).attr('id');
    var newPageId;
    var id = num;
    var closable = 1;
    var item = { 'id': id, 'name': title, 'url': url, 'closable': closable == 1 ? true : false };
    self.parent.closableTab.addTab(item);
    self.parent.closableTab.resizeMove();
    parentWindow.find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
}


//---------------------------------------------------------- 上面是老框架的代码 暂时没办法啃 后面有机会直接干掉   -------------------------------------------------------------------


var GLOBAL = {};

//查看大图
GLOBAL.showLargePic = function (elem) {
    if (!$('.J-large-pic-wrap').length) {
        $('body').append(
            '<div class="vd-large-pic-wrap J-large-wrap" style="display: none;">' +
            '<div class= "vd-large-pic-cnt" >' +
            '<img class="J-large-img" src="" alt="">' +
            '</div>' +
            '<div class="vd-large-pic-mask"></div>' +
            '</div >'
        )

        $(document).on('click', elem, function () {
            $('.J-large-wrap').show().find('.J-large-img').attr('src', $(this).data('src'));
        })

        var type = /firefox/.test(navigator.userAgent.toLowerCase()) && !(!window.ActiveXObject && /function[\s\S]+?\[native\s+code\]/.test(window.ActiveXObject + '')/* ie 11+ */);
        var event = type ? 'DOMMouseScroll' : 'mousewheel';

        $('.J-large-wrap').on(event, function (e) {
            e.preventDefault();
        })

        $('.J-large-wrap').click(function () {
            $(this).hide();
        })
    }
};

GLOBAL.addtip = function (tip) {
    var tip = tip || '确认放弃操作，关闭当前页面么？';
    parentWindow.find('.title .active .glyphicon').attr('cz-tip', tip);
};

GLOBAL.removetip = function (tip) {
    parentWindow.find('.title .active .glyphicon').attr('cz-tip', '');
};

GLOBAL.removetip();

GLOBAL.showTip = function (TYPE, tip) {
    $('.J-alert-tip').remove();
    $('body').append('<div class="vd-alert-wrap J-alert-tip"><div class="vd-alert-cnt alert-' + TYPE + '">' + tip + '</div>');
    $('.J-alert-tip').show();
};

//气泡提示
GLOBAL.globalTipTimeout = null;
GLOBAL.showGlobalTip = function (tip, type, storage) {
    GLOBAL.globalTipTimeout && clearTimeout(GLOBAL.globalTipTimeout);

    var type = type || 'success';
    var TYPE = {
        success: 'succ',
        error: 'err',
        warn: 'warn'
    }[type];

    var tip = tip || '';

    if (storage) {
        if (localStorage.getItem(storage)) {
            GLOBAL.showTip(TYPE, tip);
            localStorage.removeItem(storage);
        }
    } else {
        GLOBAL.showTip(TYPE, tip);
    }

    GLOBAL.globalTipTimeout = setTimeout(function () {
        $('.J-alert-tip').hide();
    }, 2000)
};

GLOBAL.checkKeyCode = function (code) {
    var enterCode = [8, 37, 38, 39, 40, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105];
    return enterCode.indexOf((code)) == -1;
};

GLOBAL.IMGUPLOADURL = page_url + '/firstengage/baseinfo/fileUploadImg.do';

GLOBAL.strTimeToNum = function (str) {
    var num = new Date(str).valueOf();

    return num - 8 * 60 * 60 * 1000;
};

void function () {
    $(document).on('click', '[tabTitle]', function () {
        var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
        var newPageId;
        var tabTitle = $(this).attr('tabTitle');
        if (typeof (tabTitle) == 'undefined') {
            alert('参数错误');
        } else {
            tabTitle = $.parseJSON(tabTitle);
        }
        var id = tabTitle.num;
        // var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
        var name = tabTitle.title;
        var uri = tabTitle.link;
        var needRandom = tabTitle.random;

        if (needRandom == '1') {
            id += (new Date()).valueOf();
        }

        var closable = 1;
        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
        if (tabTitle.needTip) {
            item.needTip = tabTitle.needTip;
        }
        self.parent.closableTab.addTab(item);
        self.parent.closableTab.resizeMove();
        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
    })

    if ($('.J-pager-wrap').length) {
        var $wrap = $('.J-pager-wrap');
        var $input = $wrap.find('.J-pager-input');
        var $jump = $wrap.find('.J-pager-jump');

        $input.on('keydown', function (e) {
            if (GLOBAL.checkKeyCode(e.keyCode)) {
                e.preventDefault();
                return false;
            }
        })

        $jump.on('click', function (e) {
            var val = $.trim($input.val());

            if (!val) {
                $input.focus();
                return false;
            }

            if (!/^\d*$/.test(val)) {
                $input.val('').focus();
            } else {
                window.location.href = $(this).data('link') + val;
            }
        })
    }

    //全局ajaxerror报错
    $(document).ajaxError(function () {
        GLOBAL.showGlobalTip('网络异常或请求错误。', 'warn');
    });

    //全局code等于-1时，提示错误信息
    // $(document).ajaxComplete(function (evt, req, settings) {
    //     if (req && req.responseJSON ) {
    //         GLOBAL.showGlobalTip(req.responseJSON.message || '请求错误。', 'warn');
    //     }

    //     // if (req && req.responseJSON && req.responseJSON.success !== 'undefined' && !req.responseJSON.success) {
    //     //     GLOBAL.showGlobalTip(req.responseJSON.message || '请求错误。', 'warn');
    //     // }
    // });

    //全局统一将当前页面的tab换成title的值
    var $currentTab = parentWindow.find('.active').eq(0).find('.shuaxin').next('span');
    var title = $('title').html();
    $currentTab.html(title);

    //全局统一将tab的url当前页面的url
    // var $currentIframe = parentWindow.find('.active').eq(1).find('iframe');

    // if ($currentIframe.length && $currentIframe[0].src.split('?')[0] != window.location.href.split('?')[0]) {
    //     $currentIframe.attr('src', window.location.href);
    // }

}.call(this);