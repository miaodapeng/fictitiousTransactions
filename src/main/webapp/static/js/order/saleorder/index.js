$(function () {
});


function searchReset() {
    checkLogin();
    $("form").find("input[type='text']").val('');
    $.each($("form select"), function (i, n) {
        $(this).children("option:first").prop("selected", true);
    });
    //时间重置默认
    var pre1MonthDate = $("form").find("input[name='pre1MonthDate']").val();
    var nowDate = $("form").find("input[name='nowDate']").val();
    $("form").find("input[name='starttime']").val(pre1MonthDate);
    $("form").find("input[name='endtime']").val(nowDate);
}

function exportSaleOrderList() {
    checkLogin();
    location.href = page_url + '/report/sale/exportSaleOrderList.do?' + $("#search").serialize();
}

function exportSaleOrderDetailList() {
    checkLogin();
    location.href = page_url + '/report/sale/exportSaleOrderDetailList.do?' + $("#search").serialize();
}

function synchronizingMeinianOrder() {
    checkLogin();
    layer.confirm("确定同步美年订单？",
        {
            btn: ['确定', '取消'] //按钮
        }, function()
        {
            $.ajax({
                async: false,
                url: page_url + '/order/miannian/synchronizingMeinianOrder.do',
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (  data.length > 2) {
                        var mes;
                        var successData=10;
                        var successMessage;
                        var temp= false;
                        var json = eval('(' + data + ')');
                        if (data.length > 0) {
                            mes = "以下订单同步结果：" + "<br>" + "<table border='1' style='border-collapse: collapse'>" +
                                "<tr>" +
                                "<th style='height: 26px;width: 130px'>订单号</th>" +
                                "<th style='height: 26px;width: 130px' >失败原因</th>" +
                                "</tr>"

                            for (var key in json) {
                                // noinspection JSAnnotator
                                if (json[key] == 1) {
                                    temp = true;
                                    mes = mes + "<tr>" +
                                        "<td>" + key + "</td>" +
                                        "<td>" + "采购单位编码不存在" + "</td>" +
                                        "</tr>"
                                } else if (key != "successDatas" && key != "successData" ) {
                                    temp = true;
                                    mes = mes + "<tr>" +
                                        "<td>" + key + "</td>" +
                                        "<td>" + "不存在以下产品编码：" + json[key] + "</td>" +
                                        "</tr>"
                                }
                                if (key == 'successData')  {
                                    successData = json[key];
                                }
                                if (key == 'successDatas') {
                                    successMessage = json[key];
                                }
                            }
                            mes = mes + "</table>";
                        } else {
                            mes = "所有订单同步成功"
                        }


                        if ( (successData-10) != 0){
                            if (temp) {
                                layer.alert(mes+"</br>"+"成功的同步了"+(successData-10)+"数据"+"<p>"+"销售订单单号:"+successMessage+"</p>");
                            } else {
                                layer.alert("成功的同步了"+(successData-10)+"数据"+"<p>"+"销售订单单号:"+successMessage+"</p>");
                            }

                        } else{
                            layer.alert(mes);
                        }

                    }  else if ( data == "E") {
                        layer.msg("美年接口数据请求异常")
                    } else {
                        layer.msg("暂无数据同步或者同步订单已经完成")
                    }

                }
            })
        }
    );



}

function exportFinanceSaleOrderList() {
    checkLogin();
    location.href = page_url + '/report/finance/exportFinanceSaleOrderList.do?' + $("#search").serialize();
}

function exportFinanceSaleOrderDetailList() {
    checkLogin();
    location.href = page_url + '/report/finance/exportFinanceSaleOrderDetailList.do?' + $("#search").serialize();
}

function exportBuyOrderList() {
    checkLogin();
    location.href = page_url + '/report/supply/exportBuyOrderList.do?' + $("#search").serialize();
}
