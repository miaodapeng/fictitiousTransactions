var parentWindow = $(window.parent.document);
var parentParentWindow = $(window.parent.parent.document);
$(function() {
    // 调取弹层 1
    $(".pop-new-data1").on('click', function() {
        layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(this).attr('layerParams');

        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var link = layerParams.link;
        if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 == link.length)) {
            link += "pop=pop";
        } else if (link.indexOf("?") < 0) {
            link += "?pop=pop";
        } else if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 != link.length)) {
            link += "&pop=pop";
        }
        var index = layer.open({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: link,
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
    });
    // 2
    $(".pop-new-data").on('click', function() {
        layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(this).attr('layerParams');
        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var link = layerParams.link;
        if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 == link.length)) {
            link += "pop=pop";
        } else if (link.indexOf("?") < 0) {
            link += "?pop=pop";
        } else if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 != link.length)) {
            link += "&pop=pop";
        }
        var index = layer.open({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: encodeURI(encodeURI(link)),
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
    });
    // 3
    // 左侧切换职位弹层；
    $(".pop-new-data-left").on('click', function() {
        self.parent.layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(this).attr('layerParams');
        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var link = layerParams.link;
        if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 == link.length)) {
            link += "pop=pop";
        } else if (link.indexOf("?") < 0) {
            link += "?pop=pop";
        } else if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 != link.length)) {
            link += "&pop=pop";
        }
        var index = self.parent.layer.open({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: link,
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
    });
    // 4
    // 呼叫中心弹层，头部关闭按钮隐藏
    $(".pop-new-data2").on('click', function() {

        self.parent.layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(this).attr('layerParams');
        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var link = layerParams.link;                                                                                                                    
        if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 == link.length)) {
            link += "pop=pop";
        } else if (link.indexOf("?") < 0) {
            link += "?pop=pop";
        } else if (link.indexOf("?") > 0 && (link.indexOf("?") + 1 != link.length)) {
            link += "&pop=pop";
        }
        var index = self.parent.layer.open({
            type: 2,
            shadeClose: true, //点击遮罩关闭
            closeBtn: 0,
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: false,
            content: link,
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
    });
    // 5
    $(".trasfer").on('click', function() {
        layer.config({
            extend: 'vedeng.com/style.css', //加载您的扩展样式
            skin: 'vedeng.com'
        });
        var layerParams = $(this).attr('layerParams');
        if (typeof(layerParams) == 'undefined') {
            alert('参数错误');
        } else {
            layerParams = $.parseJSON(layerParams);
        }
        var index = layer.open({
            type: 2,
            shadeClose: false, //点击遮罩关闭
            //area: 'auto',
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: encodeURI(encodeURI(layerParams.link)),
            success: function(layero, index) {
                //layer.iframeAuto(index);
            }
        });
    });
    // 6
    $('.addtitle').click(function() {
        var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
        var newPageId;
        var tabTitle = $(this).attr('tabTitle');
        if (typeof(tabTitle) == 'undefined') {
            alert('参数错误');
        } else {
            tabTitle = $.parseJSON(tabTitle);
        }
        var id = tabTitle.num;
        // var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
        var name = tabTitle.title;
        var uri = tabTitle.link;
        var closable = 1;
        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
        self.parent.closableTab.addTab(item);
        self.parent.closableTab.resizeMove();
        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
    });
    // 7
    //弹层中的元素被点击的时候新增分页 添加addtitle1;
    $('.addtitle1').click(function() {
        var frontPageId = $(window.parent.parent.document).find('.active').eq(1).attr('id');
        var tabTitle = $(this).attr('tabTitle');
        if (typeof(tabTitle) == 'undefined') {
            alert('参数错误');
        } else {
            tabTitle = $.parseJSON(tabTitle);
        }
        var id = tabTitle.num;
        // var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
        var name = tabTitle.title;
        var uri = tabTitle.link;
        var closable = 1;
        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
        self.parent.parent.closableTab.addTab(item);
        self.parent.parent.closableTab.resizeMove();
        $(window.parent.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
    });
    //8
    //不是在表格里面的要用弹层的元素 添加addtitle2
    $('.addtitle2').click(function() {
        var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
        // var index = $(this).index();
        var tabTitle = $(this).attr('tabTitle');
        if (typeof(tabTitle) == 'undefined') {
            alert('参数错误');
        } else {
            tabTitle = $.parseJSON(tabTitle);
        }
        var id = tabTitle.num;
        // var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
        var name = tabTitle.title;
        var uri = tabTitle.link;
        var closable = 1;
        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
        self.parent.parent.closableTab.addTab(item);
        self.parent.parent.closableTab.resizeMove();
        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
    });
    // 9
    //用特定的按钮关闭Layer，此方法只能在弹出层内容中使用。(by John)
    $('#close-layer').on('click', function() {
        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
        parent.layer.close(index); //执行关闭;
    })
    // 10
    // callcenter弹层右上侧关闭按钮;
    $('#close-title').on('click', function() {
        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
        parent.layer.close(index); //执行关闭;
    })
    // 11
    $('.setLayerWidth').each(function() {
        $(this).click(function() {
            var setwidth = $(this).attr('setwidth');
            var width = $.parseJSON(setwidth);
            var x = width.width;
            var left = width.left;
            self.parent.document.getElementById('layui-layer1').style.width = x + 'px';
            self.parent.document.getElementById('layui-layer1').style.left = left + '%';
        })
    })
    // 12
    //改变标题栏标题
    $('.changetitle').click(function() {
        var this_ = $(this);
        changeTitle(this_);
    });
    // 13
    function changeTitle(item) {
        var changetitle = $(item).attr('changetitle');
        if (typeof(changetitle) === 'undefined') {
            alert('参数错误');
        } else {
            changetitle = $.parseJSON(changetitle);
        }
        var title = changetitle.title;
        $(window.parent.document).find('.active').eq(0).find('span').html(title);
    }
    // 14
    // 详情页表格鼠标悬浮到红色/蓝色栏目上对应的解释说明展示出来。
    var bluemouthleft;
    $('.customername i').hover(function() {
        bluemouthleft = $(this).position().left + 25;
        bluemouthtop = $(this).position().top;
        $(this).parents('.customername').find('.customernameshow').show().css({ 'left': bluemouthleft + 'px', 'top': bluemouthtop + 'px' });
        
    }, function() {
        $(this).parents('.customername').find('.customernameshow').hide();
    })
    //上传文件
    $('.uploadErp').each(function() {
        $(this).change(function() {
            $(this).next().val($(this).val());
        });
    });

})
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
//20
//2017.11.10 一个 页面多个框架，使所有框架只用一个纵向滚动条
function setIframeHeight(iframe) {
    if (iframe) {
        var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
        if (iframeWin.document.body) {
            iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                iframe.height = (iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight) + 11;
            }
        }
    }
}          
// 弹层提交遮层
var LayerSlayer={
    open:function(){
         var div=$('<div class="submit-layer"><i class="iconloading"></i></div>');
         $('body').prepend(div);
},
    close:function(){
         $('.submit-layer').remove();
    }
}

