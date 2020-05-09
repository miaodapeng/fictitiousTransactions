// 1
var closableTab = {
    //frame加载完成后设置父容器的高度，使iframe页面与父页面无缝对接
    // frameLoad:function (frame){

    //      var mainheight = $(frame).contents().find('body').height();
    //      $(frame).parent().height(mainheight);
    //  },

    //添加tab
    addTab: function(tabItem) { //tabItem = {id,name,url,closable}  
        var id = "tab_seed_" + tabItem.id;
        var container = "tab_container_" + tabItem.id;
        var nav_width;
        // var resetUrl=tabItem.url;;
        // if(){
        //     =tabItem.url;
        // }
        $("li[id^=tab_seed_]").removeClass("active");
        $("div[id^=tab_container_]").removeClass("active");

        if (!$('#' + id)[0]) {
            var li_tab = '<li role="presentation" class="" id="' + id + '"><a href="#' + container + '"  role="tab" data-toggle="tab"> <i class="iconfresh" onclick="reloading(this)"></i><span>' + tabItem.name;
            if (tabItem.closable) {
                li_tab = li_tab + '</span><i class="glyphicon small" tabclose="' + id + '" onclick="closableTab.closeTab(this,closableTab.move)"></i></a></li> ';
            } else {
                li_tab = li_tab + '</span></a></li>';
            }
            var tabpanel = '<div role="tabpanel" class="tab-pane" id="' + container + '" style="width: 100%;">' +
                '<iframe name="singlepage" src="' + tabItem.url + '"id="tab_frame' + container + '" data-url="" frameborder="0" style="overflow-y: hidden;width:100%;height:100%"  /*onload="closableTab.frameLoad(this)*/"></iframe>' +
                '</div>';
            // 单页框架
            $('.nav-tabs').append(li_tab);
            $('.tab-content').append(tabpanel);
        }
        $("#" + id).addClass("active");
        $("#" + container).addClass("active");
        $('.nav-tabs li').eq(0).css('text-align', 'center');
        $('.t_right').hide();
    },
    //关闭tab
    closeTab: function(item, callback) {
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
    //标题栏移动
    move: function() {
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
        }(this_index);
        nav.width(liAllWidth);
        a = windowWid - liAllWidth - 146;
        if (a < 0) {
            $('.nav').animate({ 'left': a + 'px' });
            $('.t_left').show();
        } else {
            $('.nav').animate({ 'left': '0px' });
            $('.t_left').hide();
        }
        $('.glyphicon').hover(function() {
            $(this).css('background-position', '-209px -58px');
        }, function() {
            $(this).css('background-position', '-64px -43px');
        })
    }
}
$(window).resize(function() {
    closableTab.move();
});
// 2
$(function() {
    var left = 0;
    $('.t_left').click(function() {
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
    $('.t_right').click(function() {
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