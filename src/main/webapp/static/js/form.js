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

    $(".pop-new-data-supplier").on('click', function() {
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
        var index = self.parent.layer.open({
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
             var layerParams = $(this).attr('layerParams');
             // alert(argument)
               if (typeof(layerParams) == 'undefined') {
                    alert('参数错误');
                } else {
                    layerParams = $.parseJSON(layerParams);
                }
             var index = self.parent.changecareerlayer.addLayer(layerParams);
    })
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
        var index = self.parent.layer.myopen({
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
    $('.parentAddTitle').click(function() {
        var frontPageId = $(window.parent.parent.document).find('.active').eq(1).attr('id');
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
        self.parent.parent.closableTab.addTab(item);
        self.parent.parent.closableTab.resizeMove();
        $(window.parent.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
    });

    function a(){
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
    }
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
    var bluemouthleft,bluemouthtop,window_heigt,window_wid;
    var scrolllTop=0;
    $(window).scroll(function(){
        scrolllTop=$(window).scrollTop();
    })
    $('.customername i').hover(function(){
        //解决需要悬浮框的元素在可见区域的底部不完全显示的问题；
        //思路：被选元素的位置，获取鼠标元素相对窗口的(x,y)坐标，此时元素上已绑定事件，
        var customernameshow=$(this).parents('.customername').find('.customernameshow');
        var customernameshow_heit=customernameshow.outerHeight();
        window_heit=$(window).height();
        window_wid=$(window).width();
        bluemouthleft = $(this).position().left + 25;//相对父级元素的位置 左边距
        bluemouthtop = $(this).position().top;////相对父级元素的位置 上边距1
        bluemouthtop1 = $(this).offset().top;//在整个文档中的绝对位置 y轴(bluemouthtop1)
        bluemouthleft1 = $(this).offset().left;//在整个文档中的绝对位置 x轴(bluemouthleft1)
        var x1=window_wid-bluemouthleft1;
        var y1=bluemouthtop1-scrolllTop;//滚动条的滚动位置
        if(x1<220){
             customernameshow.show().css({ 'left':(bluemouthleft-230)+'px'});
        }else{
             customernameshow.show().css({ 'left': bluemouthleft + 'px' });
        }
       if ($('body>div').height()<window_heit) {
            $('body>div').height('100%');
       }//当实际内容高度小于屏幕高度，要将其设置成height100%；这样浮层才可以看见
        if(window_heit-y1<customernameshow_heit){
            var y2=customernameshow_heit-window_heit+y1;
            customernameshow.css({ 'top':'-'+y2+'px' });
        }
    }, function() {
         var customernameshow=$(this).parents('.customername').find('.customernameshow');
         customernameshow.hide();
    })
    //上传文件
    $('.uploadErp').each(function() {
        $(this).change(function() {
            $(this).next().val($(this).val());
        });
    });
// 下拉框模糊搜索
if (  $('.multiple-options').html()!= undefined &&   $('.multiple-options').html()!='') {
        $('.multiple-options').searchableSelect();
          $('.multiple-options').each(function(){
            var wid = $(this).outerWidth();
            $(this).siblings('.searchable-select').width(wid);
            $(this).siblings('.searchable-select').find('input').width(wid-22);
      })
}
     
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

//2018/5/22 弹窗加载完调用 layerPFF-->layer Parent Frame Fresh; 弹窗父级iframe重新加载 参数URL
function layerPFF(reset_url) {//reset_url格式参考：/erp/order/quote/index.do；
    var ppdiv_id = $(window.parent.parent.document).find('.active').eq(1).attr('id');
    var bottom_box = $(window.parent.parent.document).find('.bottom-box');
    var p_iframe = $(window.parent.parent.document).find('.active').eq(1).find('iframe');
    var div;
    if (reset_url == '' || reset_url == undefined || reset_url == null) {
        if(p_iframe.attr('data-url') != undefined && p_iframe.attr('data-url') != ''){
            reset_url = p_iframe.attr('data-url');
        }else{
            reset_url = p_iframe.attr('src');
        }
    }
    if (bottom_box.height() != '70' || (bottom_box == undefined)) {
        div = '<div class="tip-loadingNewData tip-loadingNewData0" id="loadingNewData-' + ppdiv_id + '"><i class="iconloadingblue"></i></div>';
    } else {
        div = '<div class="tip-loadingNewData" id="loadingNewData-' + ppdiv_id + '"><i class="iconloadingblue"></i></div>';
    }//判断整个页面有没有callcenter，控制loading层的高度；
    $('#' + ppdiv_id, window.parent.parent.document).prepend(div); //jq获取上一页框架的父级框架；
    p_iframe.attr('src', reset_url);
    // 关闭新增页加载
    if (window.parent.attachEvent) {
        window.parent.attachEvent("onload", function() {
            $("#loadingNewData-" + ppdiv_id, window.parent.parent.document).remove();
        });
    } else if (window.parent.addEventListener) {
        window.parent.addEventListener("load", function() {
            $("#loadingNewData-" + ppdiv_id, window.parent.parent.document).remove();
        }, false);
    }
} 

//加载更多，正在加载遮层；开关
function loadMoreOpen(){
             var bottomBox = $('.bottom-box',window.parent.document);
             var loadingNewData = 0;
             var this_iframe = $(window.parent.document).find('.active').eq(1).attr('id');
             var div0 = '<div class="tip-loadingNewData tip-loadingNewData0" id="loadingNewData-' + this_iframe + '"><i class="iconloadingblue"></i></div>'; 
             var div = '<div class="tip-loadingNewData" id="loadingNewData-' + this_iframe + '"><i class="iconloadingblue"></i></div>';
                if (bottomBox.height() != '70'||  ($('.bottom-box')==undefined)) {
                    $('#' + this_iframe,window.parent.document).prepend(div0);
                }else{
                    $('#' + this_iframe,window.parent.document).prepend(div);
                }
}
function loadMoreClose(){
 var tip_loadingNewData = $(window.parent.document).find('.active').eq(1).find('.tip-loadingNewData');
     tip_loadingNewData.remove();
}
  // 加载更多，正在加载遮层，添加方法。addtitle
function loadMoreAddTitle(){
     $('.loadMoreAddtitle').click(function() {
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
}

 //  鼠标悬浮 蓝色框，红色框显示提示语
function loadMoreBlueKuang(){
        var bluemouthleft,bluemouthtop,window_heigt,window_wid;
        var scrolllTop=0;
        $(window).scroll(function(){
            scrolllTop=$(window).scrollTop();
        })
        $('.customername i').hover(function(){
                //解决需要悬浮框的元素在可见区域的底部不完全显示的问题；
                //思路：被选元素的位置，获取鼠标元素相对窗口的(x,y)坐标，此时元素上已绑定事件，
                var customernameshow=$(this).parents('.customername').find('.customernameshow');
                var customernameshow_heit=customernameshow.outerHeight();
                    window_heit=$(window).height();
                    window_wid=$(window).width();
                    bluemouthleft = $(this).position().left + 25;//相对父级元素的位置 左边距
                    bluemouthtop = $(this).position().top;////相对父级元素的位置 上边距1
                    bluemouthtop1 = $(this).offset().top;//在整个文档中的绝对位置 y轴(bluemouthtop1)
                    bluemouthleft1 = $(this).offset().left;//在整个文档中的绝对位置 x轴(bluemouthleft1)
                var x1=window_wid-bluemouthleft1;
                var y1=bluemouthtop1-scrolllTop;//滚动条的滚动位置
                if(x1<220){
                     customernameshow.show().css({ 'left':(bluemouthleft-230)+'px'});
                }else{
                     customernameshow.show().css({ 'left': bluemouthleft + 'px' });
                }
               if ($('body>div').height()<window_heit) {
                    $('body>div').height('100%');
               }//当实际内容高度小于屏幕高度，要将其设置成height100%；，这样浮层才可以看见
                if(window_heit-y1<customernameshow_heit){
                    var y2=customernameshow_heit-window_heit+y1;
                    customernameshow.css({ 'top':'-'+y2+'px' });
                }
        }, function() {
             var customernameshow=$(this).parents('.customername').find('.customernameshow');
             customernameshow.hide();
        })
}
 //  动态元素绑定新增弹层事件 
function dnyPopNewdata(){
     $(".pop-new-data-dny").on('click', function() {
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
}