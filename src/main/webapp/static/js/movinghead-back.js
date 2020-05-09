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
    var whiteSpace = $('<div class="white-space"></div>');

    var fixdiv = $('.fixdiv');
    var superdiv = $('.superdiv');

    var listPagesSearch = $('.list-pages-search').html() ? $('.list-pages-search') : $('.searchfunc');
    //创建滑动表头里面的元素；公用
    // 1
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
    // 2
    // movingHeadSpecial：有横向滚动条的表格使用，表头跟着滑动；
    function movingHeadSpecial() {
        var superdivWid0 = superdiv.width();
        var superdivWid1 = superdiv.width() + 21;
        var windowHeight = $(window).height();
        var fixdivPosTo, setFixHeit0, setFixHeit1, nextSiblingHeit, windowTop, fixdivScrollLeft;
        var nextSibling = $(fixdiv).next();
        var fixHeight = fixdiv.outerHeight() + 20;
        var listenNum0 = [0],
            listenNum1 = 0;
        var tableWidth = superdiv.find('table').outerWidth() + 11; //
        if (listPagesSearch.attr('class') == "searchfunc") {
            fixdivPosTop = listPagesSearch.outerHeight();
        } else {
            fixdivPosTop = listPagesSearch.outerHeight() + 10;
        }
        // 控制superdiv的宽度；
        // 这里判断页面是否有分页，控制住表格的高度
        if (nextSibling.html() != undefined) {
            nextSiblingHeit = nextSibling.outerHeight();
            setFixHeit0 = windowHeight - nextSiblingHeit;
            if ($(fixdiv).hasClass('fixdivfix')) {
                $(fixdiv).css({ 'height': setFixHeit0 + 'px', 'top': '0px' });
            }
        } else {
            setFixHeit0 = windowHeight;
        }
       
        $(window).scroll(function() {
            windowTop = $(window).scrollTop();
            if (windowTop > fixdivPosTop && ($(fixdiv).outerHeight() > ($(window).height() - nextSiblingHeit))) {
                if (nextSibling.html() != undefined) {
                    nextSibling.addClass('fixPage');
                    nextSibling.hasClass('pages_container')? (nextSiblingHeit = nextSibling.outerHeight()+19):( nextSiblingHeit = nextSibling.outerHeight()+10); 
                   setFixHeit0 = $(window).height() - nextSiblingHeit;
                } else {
                    setFixHeit0 = windowHeight;
                }
                $(fixdiv).addClass('fixdivfix').css({ 'height': setFixHeit0 + 'px', 'top': '0px' });
                superdiv.addClass('pt35').width(tableWidth).prepend(whiteSpace).prepend(replaceTheadDiv);
                createReplaceTheadLi();
                $(fixdiv).scrollTop(30);
                $(fixdiv).scroll(function() {
                    var windowHeight = $(window).height();
                    fixdivScrollTop = $(this).scrollTop();
                    fixdivScrollLeft = $(this).scrollLeft();
                    if (listenNum1 < 2) {
                        listenNum0.push(fixdivScrollTop);
                        listenNum1++;
                    }
                    if (fixdivScrollTop > 0) {
                        replaceThead.show().addClass('replaceTheadMody').css('top', fixdivScrollTop + 'px');
                        $('.white-space').show();
                        superdiv.css({ 'padding-right': '10px', 'width': superdivWid1 + 'px' });
                    }
                    if (fixdivScrollTop < 20) {
                        $('.replaceThead ul').empty();
                        $('.superdiv').removeClass('pt35');
                        // nextSibling.removeClass('fixPage').addClass('list-bottom');
                        nextSibling.removeClass('fixPage');
                        replaceThead.hide();
                        $(fixdiv).removeClass('fixdivfix').css({ 'height': fixHeight + 'px' });
                        $(window).scrollTop(fixdivPosTop);
                        $('.white-space').hide();
                        superdiv.css({ 'padding-right': '0px', 'width': superdivWid0 + 'px' });
                    }
                })
            }
        })
        //判断本框架的父级元素是否含有active ，如果有那就先隐藏，当滚动时出现。
        if ($(window.parent.document).find('.tab-pane').hasClass('active')) {
            $('.replaceThead').hide();
        }
    }
    // 3
    //movingHeadNormal列表页中没有横向滚动条的表格使用，功能：头部滚动；
    function movingHeadNormal() {
        normalListPage.prepend(replaceTheadDiv);
        createReplaceTheadLi();
        replaceThead.addClass('replaceTheadMody');
        if ($(window.parent.document).find('.tab-pane').hasClass('active')) {
            $('.replaceThead').hide();
        }
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