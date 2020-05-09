/**
 * 框架控制
 * @author John
 * @date 2017-07-09
 */
var animateTime = 0; //滑动特效时间
var animateEasing = 'swing'; //滑动效果
var sideBarWidth = 145; //全局变量
var topBarHeight = 70; //全局变量
var titleHeit = 35; //全局变量 标题高度
var isSideBarOpen = true; //是否打开菜单栏
var leftBox = ".left-box"; //左菜单
var rightBox = ".right-box"; //右主框架
var bottomBox = ".bottom-box"; //呼叫中心面板

//
//
//JQuery 监听
$(function() { //
    //左菜单开关，监听按钮
    $('#hidden-side-bar').click(function() {
        if ($(leftBox).css("width") == sideBarWidth + "px") {
            closeSideBar();
            $(this).removeClass('side-actived'); //控制当前按钮效果的class
            //控制消息框的位置 ，首先判断是否有，有了才好操作。
            if ($('.main-frameset-box').hasClass('right-box0')) {
                $('.messageContainer').animate({ 'bottom': '0px' }, 500);
            } else {
                $('.messageContainer').animate({ 'bottom': '73px' }, 500);
            }

        } else {
            openSideBar();
            $(this).addClass('side-actived'); //控制当前按钮效果的class
            $('.messageContainer').animate({ 'bottom': '38px' }, 500);
        }
        $('.iconarrow').toggle();
        $('.sideleft').toggleClass('sideright');

    });

    //呼叫中心面析开关，监听按钮
    $('#hidden-top-bar').click(function() {
        if ($('.hidden-side-bar ').hasClass('side-actived')) {
            if ($(bottomBox).css("height") == topBarHeight + "px") {
                closeCallPanel();
                $(this).removeClass('panel-actived'); //控制当前按钮效果的class
               
            } else {
                openCallPanel();
                $(this).addClass('panel-actived'); //控制当前按钮效果的class
            }
            $('.messageContainer').animate({ 'bottom': '38px' }, 500);
        } else {
             if ($(bottomBox).css("height") == topBarHeight + "px") {
                closeCallPanel();
                $(this).removeClass('panel-actived'); //控制当前按钮效果的class
                 $('.messageContainer').animate({ 'bottom': '0px' }, 500);

            } else {
                openCallPanel();
                $(this).addClass('panel-actived'); //控制当前按钮效果的class
                 $('.messageContainer').animate({ 'bottom': '73px' }, 500);

            }
        }

        $('.bottomdown').toggleClass('bottomup');
        rightBoxHeight();
        $('.main-frameset-box').toggleClass('right-box0');
    });

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

    //左菜单开
    var openSideBar = function() {
        $(rightBox).css({ width: "calc(100% - " + sideBarWidth + "px)" });
        $(bottomBox).css({ width: "calc(100% - " + sideBarWidth + "px)" });
        $(leftBox).animate({ width: sideBarWidth + "px" }, animateTime, animateEasing);
    }

    //左菜单关
    var closeSideBar = function() {
        $(leftBox).animate({ width: "0px" }, animateTime, animateEasing, function() {
            $(rightBox).css({ width: "100%" });
            $(bottomBox).css({ width: "100%" });
        });
    }

    //呼叫中心面板开
    var openCallPanel = function() {
        $(rightBox).css({ height: "calc(100% - " + topBarHeight + "px)" });
        $(bottomBox).animate({ height: topBarHeight + "px" }, animateTime, animateEasing);
    }

    //呼叫中心面板关
    var closeCallPanel = function() {
        $(bottomBox).animate({ height: "0px" }, animateTime, animateEasing, function() {
            $(rightBox).css({ height: "100%" });
        });
    }
    //右侧框架的高度
    var rightBoxHeight = function() {
        var winHeight = $(window).height();
        var bottomBoxHeight = $(bottomBox).height();
        var setRightHeight;
        if (bottomBoxHeight == undefined) {
            setRightHeight = winHeight - titleHeit;
        } else {
            setRightHeight = winHeight - bottomBoxHeight - titleHeit;
        }
        $('#tab-content').height(setRightHeight);
        $('#tab-content').find('iframe').height(setRightHeight);
    }
    rightBoxHeight();
    $(window).resize(function() {
        rightBoxHeight();
    });
    // 动态生成的标签调用js时，js没有任何的反应，解决办法，
});
// 3
// 点击头部刷按钮，刷新框架页面,这个方法针对index.html整个文件。
function reloading(_this){

    var  m=$(_this).parents('li').index();
    var  getIframe=$('.tab-pane').eq(m).find('iframe');
    var resetUrl=getIframe.attr('data-url');
    
    //获取iframe上级DIV
    var  getDiv=$('.tab-pane').eq(m).find('iframe').parent("div");
    var divId = getDiv.attr('id');
    if (resetUrl=='') {
        resetUrl=getIframe.attr('src');
    }
   if(divId != '' && ($('.tip-loadingNewData') == undefined || $('.tip-loadingNewData').html() == undefined)){
        //load遮层
        var div = '<div class="tip-loadingNewData" id="loadingNewData-'+divId+'"><i class="iconloadingblue"></i></div>';
        $('#' + divId, window.document).prepend(div); //jq获取上一页框架的父级框架；
        if ($('.bottom-box').height() != '70'|| ($('.bottom-box')==undefined)) {
                $('.tip-loadingNewData').addClass('tip-loadingNewData0');
        }
    }
      getIframe.attr('src', resetUrl);
      
      // 关闭新增页加载
      var iframe = document.getElementById("tab_frame" + divId);
      if (iframe.attachEvent){
          iframe.attachEvent("onload", function(){
            $("#loadingNewData-"+divId).remove();
          });
      } else {
          iframe.onload = function(){
              $("#loadingNewData-"+divId).remove();
          };
      }
}
// 4
// 不能刷新；
function cantReloading(id){
    $(id).find('.iconfresh').remove();
}
// 5
// 关闭消息提示框；
function closeMessage(){
    $('.messageContainer').hide('slow');
}

