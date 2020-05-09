$(function () {
    var $searchWrap = $('.J-search-wrap');
    var $sortWrap = $('.J-sort-wrap');
    var pathname = window.location.pathname;
    var sortParams = [];
    var searchParams = '';

    var getSearchParams = function () {
        var params = [];

        var $tab = $('.J-list-tab');

        if ($tab.length) {
            params.push($tab.data('name') + '=' + $tab.find('.current').data('value'));
        }

        $searchWrap.find('input,select').each(function () {
            if ($(this).attr('name')) {
                params.push($(this).attr('name') + '=' + $(this).val());
            }
        })

        return params.join('&');
    };

    searchParams = getSearchParams();

    var getSortParams = function () {
        $sortWrap.find('select').each(function () {
            sortParams.push([$(this).attr('name'), $(this).val()]);
        })
    };

    getSortParams();

    var setSortParams = function (name, value) {
        $.each(sortParams, function (i, obj) {
            if (obj[0] == name) {
                sortParams[i][1] = value;
            }
        })
    };

    var parseSortParams = function () {
        var str = [];
        $.each(sortParams, function (i, obj) {
            str.push(obj.join('='));
        })

        return str.join('&');
    };

    $searchWrap.find('.J-search').click(function () {
        window.location.href = pathname + '?' + getSearchParams();
    })

    $sortWrap.find('select').change(function () {
        setSortParams($(this).attr('name'), $(this).val());
        window.location.href = pathname + '?' + searchParams + '&' + parseSortParams();
    })

    //搜索清空
    $('.J-reset').click(function () {
        $searchWrap.find('input').val('').trigger('change');

        $searchWrap.find('select').each(function () {
            var val = $(this).find('option').eq(0).val();

            $(this).val(val);
        })

        // Select.unuse('.J-search-wrap .J-select');
        // setTimeout(function () {
        //     Select.use('.J-search-wrap .J-select');
        // })

        $searchWrap.find('.J-search').trigger('click');
    })

    //日期空间初始化
    $('.J-date-range').each(function () {
        var $input1 = $(this).find('input').eq(0);
        var pikadayStart = new Pikaday({
            format: 'yyyy-mm-dd',
            field: $(this).find('input').eq(0)[0],
            firstDay: 1,
            yearRange: [1900, 2050],
            onSelect: function (val) {
                pikadayEnd.setMinDate(val);
            }
        });
        var pikadayEnd = new Pikaday({
            format: 'yyyy-mm-dd',
            field: $(this).find('input').eq(1)[0],
            firstDay: 1,
            yearRange: [1900, 2050],
            onSelect: function (val) {
                pikadayStart.setMaxDate(val);
            }
        });

        var val1 = $(this).find('input').eq(0).val();
        var val2 = $(this).find('input').eq(1).val();
        if (val1) {
            var times = GLOBAL.strTimeToNum($.trim(val1))
            pikadayEnd.setMinDate(new Date(times));
        }

        if (val2) {
            var times = GLOBAL.strTimeToNum($.trim(val2))
            pikadayStart.setMaxDate(new Date(times));
        }
    })

    $('.J-search-select').change(function () {
        $searchWrap.find('.J-search-word').attr('placeholder', $(this).find('option:selected').data('place'));
    }).trigger('change');

    //选择框初始化
    Select.use('.J-select')

    //操作栏选择下拉
    $(document).on('click', '.J-option-select-icon', function (e) {
        e.stopPropagation();
        var $wrap = $(this).parents('.J-option-select-wrap:first');
        var opened = $wrap.hasClass('open');
        $('.J-option-select-wrap').removeClass('open');

        if (opened) {
            $wrap.removeClass('open');
        } else {
            $wrap.addClass('open');
        }
    })

    //操作栏是否有下拉
    $('.J-option-select-wrap').each(function () {
        if (!$(this).find('.option-select-item').length) {
            $(this).find('.J-option-select-icon').hide();
        }
    })

    $(document).click(function () {
        $('.J-option-select-wrap').removeClass('open');
    })

    //操作悬浮
    if ($('.J-fix-wrap').length) {
        $(window).scroll(function () {
            var $fix = $('.J-fix-wrap');
            var scroll = $(window).scrollTop();
            var top = $fix.offset().top;

            if (scroll > top) {
                $fix.find('.J-fix-cnt').addClass('fixed');
            } else {
                $fix.find('.J-fix-cnt').removeClass('fixed');
            }
        })
    }

    //全选
    var $tableWrap = $('.J-table-wrap');

    var checkSelectAll = function () {
        var selectAll = true, hasSelect = false;
        $tableWrap.find('.J-select-item:not(:disabled)').each(function (i, item) {
            if (!$(this)[0].checked) {
                selectAll = false
            } else {
                hasSelect = true;
            }
        })

        $tableWrap.find('.J-select-all')[0].checked = $tableWrap.find('.J-select-item:not(:disabled)').length && selectAll;
        $('.J-multi-del')[0] && ($('.J-multi-del')[0].disabled = !hasSelect);

        if (!($tableWrap.find('.J-select-item:not(:disabled)').length)) {
            $tableWrap.find('.J-select-all')[0].disabled = true;
        }
    }

    $tableWrap.find('.J-select-item:not(:disabled)').change(function () {
        checkSelectAll();
    });

    $tableWrap.find('.J-select-all').change(function () {
        var $this = $(this);
        $tableWrap.find('.J-select-item:not(:disabled)').each(function () {
            $(this)[0].checked = $this[0].checked;
        });


        $('.J-multi-del')[0] && ($('.J-multi-del')[0].disabled = !$this[0].checked);
    })

    if ($tableWrap.find('.J-select-all').length) {
        checkSelectAll();
    }

    //展示或收起更多删选条件
    if ($('.J-search-show-toggle').length) {
        $('.J-search-show-toggle').click(function () {
            var $moreCnt = $('.J-search-more-cnt');

            if ($(this).hasClass('show')) {
                $(this).removeClass('show');
                $moreCnt.slideUp(200);
                $(this).find('.J-more').show();
                $(this).find('.J-less').hide();
            } else {
                $(this).addClass('show');
                $moreCnt.slideDown(200);
                $(this).find('.J-more').hide();
                $(this).find('.J-less').show();

            }
        })
    }

    //tab已选中的点击
    $('.J-list-tab .current').click(function (e) {
        e.preventDefault();
    })
    $(".J-search-wrap input").keyup(function(e){
        if(e.keyCode == 13 || e.keyCode == 108){
            $(this).parents('.J-search-wrap:first').find('.J-search').click();
        }
    });
})