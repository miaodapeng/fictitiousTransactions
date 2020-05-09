$(function() {
    // 1
    var firstLi;
    $('.chose-com-cont').click(function() {
        $('.chose-company ul').show();
    })
    // 2
    $('.chose-company ul li').each(function() {
        $(this).click(function() {
            firstLi = $(this).text();
            $('.chose-com-cont').addClass('selectedcolor').children('span').html(firstLi);
            $('.chose-company ul').hide();
        })
    })
    // 3
    $('.chose-company ul').hover(function() {
        $('.chose-company ul').show();
    }, function() {
        $('.chose-company ul').hide();
    })
})