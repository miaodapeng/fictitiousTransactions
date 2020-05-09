//左侧栏的样式.
$(function() {
    //滚动条
    $(window).resize(function() {
        $(".nano").height($(window).height() - 40);
        $(".nano").nanoScroller({
            alwaysVisible: true
        });
    })
    //重置外框为当前高度0
    $(".nano").height($(window).height() - 40);

    $(".nano").nanoScroller({
        alwaysVisible: true
    });
    $("#bottom").click(function() {
        $(".nano").nanoScroller({
            scroll: 'bottom'
        });
    });
    $("#top").click(function() {
        $(".nano").nanoScroller({
            scroll: 'top'
        });
    });
    // 用户信息样式控制,事件套事件。
    var parentWin = window.parent;
    $('.iconuser').hover(function() {
        $('.users').show();
        $('.users').hover(function() {
            $('.users').show();
        }, function() {
            $('.users').hide();
        });

        parentWin && parentWin.hideMsg && parentWin.hideMsg();
    }, function() {
        $('.users').hide();
        parentWin && parentWin.hideMsg && parentWin.showMsg();
    });
    $('.users').hover(function() {
        $(this).show();
        parentWin && parentWin.hideMsg && parentWin.hideMsg();
    }, function() {
        $(this).hide();
        parentWin && parentWin.hideMsg && parentWin.showMsg();
    });
    var sideBottomLi=$('.sidebar-bottom li');
   $(sideBottomLi).each(function() {
        var sideBottomLiIndex = 'sideBottomLi_group' + $(this).index();
        var sideBottomItem = $(this).children('a');
        $(sideBottomItem).each(function() {
            $(this).click(function(event) {
                var id = sideBottomLiIndex + '_item' + $(this).index();
                var name = $(this).attr('name');
                var uri = $(this).attr('link');
                var closable = 1;
                var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
                self.parent.closableTab.addTab(item);
                self.parent.closableTab.resizeMove(id);
            });
        });
    });
})