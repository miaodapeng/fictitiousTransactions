var animateTime = 200; //滑动特效时间
var animateEasing = 'swing'; //滑动效果
var sideBarWidth = 145; //全局变量
var topBarHeight = 75; //全局变量
var isSideBarOpen = true; //是否打开菜单栏
var isTopBarOpen = true; //是否打开呼叫中心面板
var tagTitleHeight = 30; //标签标题栏高度

var mainPage = {
    closeCallPanel: function(closeBotton) { //关闭呼叫中心面板
        var mainFrame = $(".main-frame"); //主框架
        var topBarFrameset = $(".top-bar-frameset-box");
        var windowHeight = window.innerHeight;

        $(topBarFrameset).animate({ height: 0 }, animateTime, animateEasing);
        $(mainFrame).animate({ height: windowHeight }, animateTime, animateEasing);
        $('.container').animate({ height: windowHeight }, animateTime, animateEasing);
        $('.tab-content').animate({ height: windowHeight + tagTitleHeight }, animateTime, animateEasing);
        // $('#hidden-top-bar').html('&#9650');
        if (closeBotton == true) {
            $('#hidden-top-bar').hide();
        }
        isTopBarOpen = false
    },
    openCallPanel: function() { //开启呼叫中心面板
        var mainFrame = $(".main-frame"); //主框
        var topBarFrameset = $(".top-bar-frameset-box");
        var windowHeight = window.innerHeight;

        $(topBarFrameset).animate({ height: topBarHeight }, animateTime, animateEasing);
        $(mainFrame).animate({ height: windowHeight - topBarHeight }, animateTime, animateEasing);
        $('.container').animate({ height: windowHeight - topBarHeight }, animateTime, animateEasing);
        $('.tab-content').animate({ height: windowHeight - topBarHeight - tagTitleHeight }, animateTime, animateEasing);
        // $('#hidden-top-bar').html('&#9660');
        $('#hidden-top-bar').show();
        isTopBarOpen = true;
    }
};
$(function() {
    // 1
    function resizeMainHeight() { //当浏览器窗口变化时，重置标检内容区的大小
        var y1 = $(window).height();
        var y4 = window.innerHeight;
        var y2 = $('.top-bar-frameset-box').height();
        var y3 = y1 - y2 - 35;
        $('.tab-content').height(y3);
    }
// 2
    function resizeMainWidth() {
        var x11 = window.innerWidth;
        var x12 = $(window).width();
        if (x11 == x12 || x11 > x12) {
            x11 = x12;
            var x2 = $('.side-bar-frameset-box').width();
            x3 = x11 - x2;
            $('.main-frameset-box').width(x3);
        }

    };
    resizeMainWidth();
    resizeMainHeight();
    // 打开标签页面
    function frontPage() {
        if (typeof(self.parent.closableTab) != 'undefined') {
            var item = {
                'id': 'index1',
                'name': '首页',
                'url': 'welcome.do',
            };
            self.parent.closableTab.addTab(item);
        }
    }
    frontPage();
    // 3
    $('.iconmessagecha').click(function() {
        $('.messageContainer').hide('slow');
    })

    $(".notice").slideToggle("slow");
    //强制页面在主框架打开
    if (self.location.href != top.location.href) {
        top.location.href = self.location.href;
    }
    //Refresh side-bar page
    //window.onload = function() {
    //self.frames['side-bar-frame'].location.reload(true);
    mainPage.openCallPanel();
    //呼叫中心面板开关
    $('#hidden-top-bar').click(function(e) {
        var topBarFrameset = $(".top-bar-frameset-box");

        if (topBarFrameset.height() <= 0) {
            mainPage.openCallPanel();
        } else {
            mainPage.closeCallPanel(false);
        }
        resizeMainWidth();
        resizeMainHeight();
        $('.bottomdown').toggleClass('bottomup');
    });
    //菜单栏开关  john
    $('#hidden-side-bar').click(function(e) {
        var sideBarFrameset = $(".side-bar-frameset-box"); //布局div
        var sideBarFrame = $(".side-bar-frame"); //框架,用于左右移动
        var mainFrameset = $(".main-frameset-box"); //主框架
        //alert($(sideBarFrameset).width());
        if ($(sideBarFrameset).width() <= 0) { //开启菜单

            $(sideBarFrame).animate({ "margin-left": 0 }, animateTime, animateEasing);
            $(mainFrameset).animate({ width: $(mainFrameset).width() - sideBarWidth }, animateTime, animateEasing);
            $(sideBarFrameset).animate({ width: sideBarWidth }, animateTime, animateEasing);
            // $(this).html('&#9668');
            isSideBarOpen = true;
            $('.messageContainer').animate({ bottom: '40px' }, 500)
        } else { //关闭菜单
            $(sideBarFrame).animate({ "margin-left": -sideBarWidth }, animateTime, animateEasing);
            $(mainFrameset).animate({ width: sideBarWidth + $(mainFrameset).width() }, animateTime, animateEasing);
            $(sideBarFrameset).animate({ width: 0 }, animateTime, animateEasing);
            // $(this).html('&#9658');
            isSideBarOpen = false;
            $('.messageContainer').animate({ bottom: '0px' }, 500)
        }
        $('.iconarrow').toggle();
        $('.sideleft').toggleClass('sideright');
    });

    $(".notice-close").click(function() {
        $(".notice").slideToggle("slow");
    });
});
// 20
//点击头部刷按钮，刷新框架页面
function reloading() {
    var this_index = $('.nav-tabs').find('.active').index();
    var url = $('#tab-content').find('iframe').eq(this_index).attr('src');
    $('#tab-content').find('iframe').eq(this_index).attr('src', url);
}
// 21
// 点击头部刷按钮，刷新框架页面,这个方法针对index.html整个文件。
function reloading(reseturl){
    var url;
      if(reseturl==undefined){
         url = $('#tab-content').find('.active').children('iframe').attr('src'); 
      }else{
         url = reseturl;
      }
       $('#tab-content').find('.active').children('iframe').attr('src', url);
}
// 不能刷新；