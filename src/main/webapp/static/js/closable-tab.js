// 1
var closableTab = {

    //添加tab
    addTab: function (tabItem) { //tabItem = {id,name,url,closable} 
        var id = "tab_seed_" + tabItem.id;
        var container = "tab_container_" + tabItem.id;
        var nav_width;

        $("li[id^=tab_seed_]").removeClass("active");
        $("div[id^=tab_container_]").removeClass("active");
        var bottomBox = $('.bottom-box');
        var loadingNewData = 0;
        var needTip = tabItem.needTip || '';

        if (!$('#' + id)[0]) {
            // var loadIndex = layer.loading();
            var li_tab = '<li role="presentation" class="" id="' + id + '"><a href="#' + container + '"  role="tab" data-toggle="tab" title="' + tabItem.name + '"><div onclick="reloading(this)" class="shuaxin"><i class="iconfresh" ></i></div><span>' + tabItem.name;
            if (tabItem.closable) {
                li_tab = li_tab + '</span><i class="glyphicon small" tabclose="' + id + '" onclick="closableTab.closeTab(this,closableTab.resizeMove)" cz-tip="' + needTip + '"></i></a></li> ';
            } else {
                li_tab = li_tab + '</span></a></li>';
            }
            var tabpanel = '<div role="tabpanel" class="tab-pane" id="' + container + '" style="width: 100%;">' +
                '<iframe name="singlepage" src="' + tabItem.url + '"id="tab_frame' + container + '" data-url="" data-frontpageid="" frameborder="0" style="overflow-y: hidden;width:100%;height:100%"  /*onload="closableTab.frameLoad(this)*/"></iframe>' +
                '</div>';
            // 单页框架
            $('.nav-tabs').append(li_tab);
            $('.tab-content').append(tabpanel);
            var div = '<div class="tip-loadingNewData" id="loadingNewData-' + container + '"><i class="iconloadingblue"></i></div>';

            $('#' + container, window.parent.document).prepend(div); //jq获取上一页框架的父级框架；
            if (bottomBox.height() != '70' || ($('.bottom-box') == undefined)) {
                $('.tip-loadingNewData').addClass('tip-loadingNewData0');
            }
        }
        $("#" + id).addClass("active");
        $("#" + container).addClass("active");
        $('.nav-tabs li').eq(0).css('text-align', 'center');
        $('.t_right').hide();
        // 关闭新增页加载
        var iframe = document.getElementById("tab_frame" + container);
        if(iframe==null){
            window.location.href=tabItem.url.substring(1)

        }else{
            if (iframe.attachEvent) {
                iframe.attachEvent("onload", function () {
                    $("#loadingNewData-" + container).remove();
                });
            } else {
                iframe.onload = function () {
                    $("#loadingNewData-" + container).remove();
                };
            }
        }
    },
    close: function (item, callback) {
        var nav_widthminus;
        var val = $(item).attr('tabclose');
        var containerId = "tab_container_" + val.substring(9);
        if (!$('#' + containerId).siblings().hasClass("active")) {
            if ($('#' + containerId).hasClass('active')) {
                $('#' + val).prev().addClass('active');
                $('#' + containerId).prev().addClass('active');
            }
        }
        $("#" + val).remove();
        $("#" + containerId).remove();
        callback();
    },
    //关闭tab
    closeTab: function (item, callback) {
        var _this = this;
        var needTip = $(item).attr('cz-tip');
        if (needTip) {
            if (window.layer) {
                layer.confirm(needTip, {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    layer.closeAll('dialog');
                    _this.close(item, callback);
                }, function () {
                });
            } else {
                if (confirm(needTip)) {
                    _this.close(item, callback);
                }
            }
        }else{
            _this.close(item, callback);
        }
    },
    //标题栏移动
    resizeMove: function () {
        var a, b, c;
        var windowWid = $(window).width(); //当前静态页的所在框架的里面的window的宽度。
        var nav = $('.nav');
        var navLi = $('.nav li');
        var liLength = navLi.length;
        var liAllWidth = 0;
        //计算所有标题的宽度总和；
        for (var i = 0; i < liLength; i++) {
            var this_index = i;
            liAllWidth += navLi.eq(this_index).outerWidth();
        } (this_index);
        nav.width(liAllWidth);
        nav.css('min-width', (liAllWidth + 13) + 'px');
        a = windowWid - liAllWidth - 146;
        if (a < 0) {
            $('.nav').animate({ 'left': a + 'px' });
            $('.t_left').show();
        } else {
            $('.nav').animate({ 'left': '0px' });
            $('.t_left').hide();
        }
        $('.glyphicon').hover(function () {
            $(this).css('background-position', '-209px -58px');
        }, function () {
            $(this).css('background-position', '-64px -43px');
        })
    }
}
$(window).resize(function () {
    closableTab.resizeMove();
});
// 2
$(function () {
    var left = 0;
    $('.t_left').click(function () {
        var navleft = $('.nav').position().left;
        if (navleft < -90) {
            left = navleft + 101;
            $('.nav').animate({
                "left": left + "px"
            }, 'fast');
        } else {
            $('.nav').animate({
                "left": "0px"
            }, 'fast');
            $('.t_left').hide();
        }
        $('.t_right').show();
        return false;
    })
    $('.t_right').click(function () {
        var navleft = $('.nav').position().left;
        var ulWidth = $('.nav').width();
        var winWidth = $(window).width();
        var chazhi = winWidth - ulWidth - 145;
        var chazhi1 = chazhi + 20;
        if (navleft > chazhi) {
            left = navleft - 145;
            $('.nav').animate({
                "left": left + "px"
            }, 'fast');
            $('.t_left').show();
        } else {
            $('.nav').animate({
                "left": chazhi + "px"
            }, 'fast');
            $('.t_right').hide();
        }
        return false;
    })
})
