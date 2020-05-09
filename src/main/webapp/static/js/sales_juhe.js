(function($) {
    $(function() {
        // index 弹层轮播
        var imgw = function() {
            $(".index-slide-banner").slide({
                mainCell: ".bd ul",
                titCell: ".hd",
                effect: "leftLoop",
                autoPage: true,
                autoPlay: true
            });
        }
        imgw();
        $('.front-page-close').click(function() {
            $('.front-page-slide').hide('slow');
        })
        
       
        // 动态设置轮播中的 小圆圈位置
        function setWid() {
            var hd_container = $('.hd-container');
            var hd_wid = $('.hd-container .hd').width() / 2 + 4;
            hd_container.css('margin-left', '-' + hd_wid + 'px');
        }
        setWid();
        // 销售主页
        // 多条项目点击选中一个，其他隐藏
        function chooseOne(obj, e) {
            var a = obj.a,
                b = obj.b;
            var a_li = a.find('li'),
                b_li = b.find('li');
            a_li.each(function() {
                $(this).on(e, function() {
                    var _index = $(this).index();
                    $(this).addClass('active').siblings().removeClass('active');
                    b_li.eq(_index).addClass('active').siblings().removeClass('active');
                })
            })
        }
        var vd_choose_title = $('.vd-choose-title'),
            vd_choose_content = $('.vd-choose-content');
        var choose_one = {
            a: vd_choose_title,
            b: vd_choose_content
        };
        chooseOne(choose_one, 'click'); //第二个参数，mouseover/click --悬浮/点击
        //编辑问答
        //新增问答
    })
})(jQuery)