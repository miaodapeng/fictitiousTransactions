var json = [];
//nameList与widthList的数组长度要一致
var nameList = ['请选择', '客户编码', '客户名称'] //table的列名
var widthList = [100, 100, 100] //table每列的宽度
$(function ()
{
    $.ajax(
        {
        type: "POST",
        url: page_url +"/order/miannian/getAllCustomers.do",
        dataType: "json",
        async : false,
        success: function (result)
        {
            var listData = result.listData;
            json = listData;

        },
        error : function () {
            alert("请求错误")
        }
    });

    nicePage.setCfg({
        table: 'table',
        bar: 'pageBar',
        limit: 20,
        color: '#1E9FFF',
        layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    });


    $('#test').find('input[type=checkbox]').bind('click', function () {
        $('#test').find('input[type=checkbox]').not(this).attr("checked", false);
    });


    $('body').on('click', 'tr', function () {
        var that = this;
        var childrenTd = $(that).children("td").eq(1);
      //  alert('选中的是第' + ($(this).index() * 1 + 1) + '第' + (parentTr.index() * 1 + 1) + '值' + $(that).text());
        var meiNianCode = '';
        var tepm = $(childrenTd).text();
        if (checkNumber(tepm)) {
            meiNianCode = tepm;

            if (meiNianCode.length > 4){
                layer.confirm("您是否确认绑定客户？",
                    {
                        btn: ['确定', '取消'] //按钮
                    }, function()
                    {
                        var traderCustomerId = $("#traderCustomerId").val();
                        $.ajax({
                            async : false,
                            type: "POST",
                            url: page_url +"/order/miannian/updateToMeiNianCode.do",
                            dataType: "json",
                            data: {
                                "traderCustomerId" : traderCustomerId,
                                "meiNianCode" : meiNianCode
                            },
                            success: function (result) {
                                if (result.message.trim() != "success") {
                                    layer.msg("绑定失败,客户编码已经绑定过了");
                                } else {
                                    layer.close(index);
                                    window.parent.location.reload();
                                }

                            }
                        })
                    });
            }
        }
    })


})

//验证字符串是否是数字
function checkNumber(theObj) {
    var reg = /^[0-9]+.?[0-9]*$/;
    if (reg.test(theObj)) {
        return true;
    }
    return false;
}