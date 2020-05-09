$(function() {
    // point current page
    $(".side-nav li ul li").on('click', function() {
        if ($(this).hasClass("active")) {
        } else {
            $(".side-nav li ul li").removeClass("active");
            $(this).addClass("active");
        }
    });
    $('.panel-modify .tab li').bind('click hover', function() {
        var listBodyId = 'list-' + $(this).attr('id');
        $(this).parent().parent().find('.list-tab').hide();
        $('#' + listBodyId).show();
        $(this).parent().find('li').removeClass('select');
        $(this).addClass('select');
    });
    var preId = 'tab_seed_';
    var itemGroup = $('#side-nav > li');
    $(itemGroup).each(function() {
        var itemGroupIndex = 'menu_group' + $(this).index();
        var item = $(this).children('ul').children('li');
        $(item).each(function() {
            $(this).click(function(event) { 
                var siblingFrame=$(window.parent.document).contents();
                var nav=siblingFrame.find('.nav');
                var id = itemGroupIndex + '_item' + $(this).index();
                var name = $(this).children('a').html();
                var uri = $(this).children('a').attr('link');
                var closable = 1;
                var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
                var titleexisted=siblingFrame.find('#tab_seed_'+id);
                var frameexisted=siblingFrame.find('#tab_container_'+id);
                var existedhtml=titleexisted.html();
                var titlePosL,a,b,c;
                var windowWid=$(window.parent).width()-145;
                if(existedhtml==undefined){
                     self.parent.closableTab.addTab(item);
                     self.parent.closableTab.resizeMove(id);
                }else{
                   titlePosL=titleexisted.offset().left;
                    a=nav.position().left;
                   if (titlePosL<60) {
                        b=a-titlePosL+200;
                        nav.animate({ 'left':  b+'px' }); 
                   }
                   if(titlePosL>(windowWid)){
                        b=a-titlePosL+windowWid+40;
                        nav.animate({ 'left':  b+'px' }); 
                   }
                   titleexisted.siblings().removeClass('active');
                   frameexisted.siblings().removeClass('active');
                   titleexisted.addClass('active');
                   frameexisted.addClass('active');
                }
            });
        });
    });
})
