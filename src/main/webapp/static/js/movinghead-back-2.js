// 头部滚动
$(function() {
    // 让thead依据滚动条滚动，
    // 确定需要操作的元素，thead ，开始对其特征进行分析，并加以样式行为控制；
    // 要控制的样式：1、给thead添加绝对定位；
    // 可获取数据：1、thead在文档中的位置；2、滚动条滑动的位移；
    // 需逻辑计算数据：暂无；
    var normalListPage = $('.normal-list-page'); // 无滚动条的表格
    var thead = $('.table thead');
    var tbodyTr = $('.table tbody tr');
    var movingTop, setWid, setLastLiWid, replaceThead;
    var theadThs = thead.find('th');
    var thLeng = theadThs.length;
    var tbodyTrLeng = tbodyTr.length;
    // 创建DOM 
    var replaceTheadDiv = $('<div class="replaceThead"><ul></ul></div>');
    var fixdiv = $('.fixdiv');
    var superdiv = $('.superdiv');
    var listPagesSearch = $('.list-pages-search').html() ? $('.list-pages-search') : $('.searchfunc');
    //创建滑动表头里面的元素；公用
    function createReplaceTheadLi() {
        for (var i = 0; i < thLeng; i++) {
            var liHtml = theadThs.eq(i).text();
            setWid = theadThs.eq(i).outerWidth();
            $('.replaceThead ul').append('<li style="width:' + setWid + 'px;">' + liHtml + '</li>');
        }
        setLastLiWid = $('.replaceThead ul li:last-child').width() - 1;
        $('.replaceThead ul li:last-child').width(setLastLiWid);
        replaceThead = $('.replaceThead');
    }
    // movingHeadSpecial：有横向滚动条的表格使用，表头跟着滑动；
    function movingHeadSpecial() {
        superdiv.prepend(replaceTheadDiv);
        createReplaceTheadLi();
        var windowHeight = $(window).height();
        var fixdivPosTo,setFixHeit0, setFixHeit1, nextSiblingHeit;
        if (listPagesSearch.attr('class')=="searchfunc") {
            fixdivPosTop = listPagesSearch.outerHeight();
        }else{
            fixdivPosTop = listPagesSearch.outerHeight()+10;
        }
        var nextSibling = $(fixdiv).next();
        // 这里判断页面是否有分页，控制住表格的高度
        if (nextSibling.html() != undefined) {
            nextSibling.addClass('fixPage');
            nextSiblingHeit=nextSibling.outerHeight()+20;
            setFixHeit0 = windowHeight - fixdivPosTop - nextSiblingHeit;
            setFixHeit1 = windowHeight - nextSiblingHeit;
        } else {
            setFixHeit0 = windowHeight - fixdivPosTop;
            setFixHeit1 = windowHeight - 10;
        }
        $(fixdiv).addClass('fixdivfix').css({ 'height': setFixHeit0 + 'px', 'top': fixdivPosTop + 'px' });
        if (superdiv.outerHeight() > windowHeight) {
            $(fixdiv).scroll(function() {
                var windowHeight = $(window).height();
                if (nextSibling.html() != undefined) {
                    nextSibling.addClass('fixPage');
                    setFixHeit0 = windowHeight - fixdivPosTop - 47;
                    setFixHeit1 = windowHeight - 57;
                } else {
                    setFixHeit0 = windowHeight - fixdivPosTop;
                    setFixHeit1 = windowHeight - 10;
                }
                fixdivScrollTop = $(fixdiv).scrollTop();
                if (fixdivScrollTop > 0) {
                    $(fixdiv).addClass('fixdivfix').css({ 'height': setFixHeit1 + 'px', 'top': '10px' });
                    replaceThead.show().css('top', fixdivScrollTop + 'px');
                } else {
                    replaceThead.hide();
                    $(fixdiv).css({ 'height': setFixHeit0 + 'px', 'top': fixdivPosTop + 'px' });
                }
            })
        } else {
            $(fixdiv).scroll(function() {
                fixdivScrollTop = $(fixdiv).scrollTop();
                if (fixdivScrollTop > 0) {
                    replaceThead.show().css('top', fixdivScrollTop + 'px');
                } else {
                    replaceThead.hide();
                }
            })
        }
    }
    //movingHeadNormal列表页中没有横向滚动条的表格使用，功能：头部滚动；
    function movingHeadNormal() {
        normalListPage.prepend(replaceTheadDiv);
        createReplaceTheadLi();
        replaceThead.addClass('replaceTheadMody');
        $(window).scroll(function() {
            var winScrollTop = $(this).scrollTop();
            var theadOrinalPos = thead.offset().top;
            var windowHeight = $(window).height();
            if (winScrollTop > theadOrinalPos) {
                movingTop = winScrollTop - theadOrinalPos;
                replaceThead.show().css('top', movingTop + 'px');
            } else {
                replaceThead.hide();
            }
        })
    }
    $(fixdiv).html() ? movingHeadSpecial() : movingHeadNormal();
    $(window).resize(function() {
        $('.replaceThead ul').empty();
        $(fixdiv).html() ? movingHeadSpecial() : movingHeadNormal();
    })

})