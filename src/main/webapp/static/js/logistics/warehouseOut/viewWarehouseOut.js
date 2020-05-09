/**
 * 申请修改订单前校验
 * @param saleorderId
 */
var placeUrl=$("#placeUrl").val()

function applyToModifyHcSaleorderForTrader(saleorderId) {
    checkLogin();
    $.ajax({
        type: "POST",
        url: "../saleorder/applyToModifyHcSaleorderForTrader.do",
        data: {'saleorderId': saleorderId},
        dataType: 'json',
        success: function (data) {
            if (data.code == 0) {
                var msg = "您是否确认申请修改该订单？";
                index = layer.confirm(msg, {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
                    var id = "modify_apply" + saleorderId;
                    var name = "修改订单";
                    var uri = "order/saleorder/modifyApplyInit.do?saleorderId=" + saleorderId;
                    var closable = 1;
                    var item = {'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false};
                    self.parent.closableTab.addTab(item);
                    self.parent.closableTab.resizeMove();
                    $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);

                    layer.close(index);

                }, function () {

                });
            } else {
                layer.alert(data.message);
                return false;
            }

        },
        error: function (data) {
            if (data.status == 1001) {
                layer.alert("当前操作无权限")
            }
        }
    });
}

//全选
function selectall(obj, name) {
    checkLogin();
    if ($(obj).is(":checked")) {
        $("input[name='" + $(obj).val() + "']").not(':disabled').prop("checked", true);
        if (name != "") {
            $("input[id='" + name + "']").not(':disabled').prop("checked", true);
        }
    } else {
        $("input[name='" + $(obj).val() + "']").not(':disabled').prop('checked', false);
        if (name != "") {
            $("input[id='" + name + "']").not(':disabled').prop('checked', false);
        }
    }
}

//根据日期选择
function checkbarcode(m, f, id) {
    checkLogin();
    var flag = 0;
    if (!m) return false;
    $("input[name='" + id + "']").each(function () {
        if ($(this).attr("alt") == m) {
            $(this).prop('checked', f);
        }
        if (!$(this).is(":checked")) {
            flag = 1;
        }
    });
    if (flag == 1) {
        $("input[name='all_" + id + "']").prop('checked', false);
    } else {
        $("input[name='all_" + id + "']").prop('checked', true);
    }
}


function checkbycode(m, f, id) {
    checkLogin();
    var flag = 0;
    if (!m) return false;
    $("input[name='" + id + "']").each(function () {
        debugger;
        if ($(this).attr("alt") == m) {
            $(this).prop('checked', f);
        }
        if (!$(this).is(":checked")) {
            flag = 1;
        }
    });
}

//批量撤销
function cancelWlogAll(id, type) {
    checkLogin();
    var wlogIds = "";
    var wds = "";
    if (id == "b_checknox") {
        $.each($("input[name='" + id + "']:checked"), function () {
            var rs = $(this).val().split(",");
            wlogIds += $(this).val() + "#";
        });
    } else {
        $.each($("input[name='" + id + "']:checked"), function () {
            var rs = $(this).val().split(",");
            wlogIds += rs[0] + "_";
        });
    }

    if (wlogIds == "") {
        layer.alert('请选择需要撤销的记录');
        return false;
    }
    var URL = "";
    if (id == "b_checknox") {
        URL = "/warehouse/warehousesout/editIsEnablePicking.do";
    } else if (id == "c_checknox") {
        URL = "/warehouse/warehousesout/editIsEnableWlog.do";
    } else if (id == "d_checknox") {
        URL = "/warehouse/warehousesout/editIsEnableWlog.do";
    }
    layer.confirm('确定撤销？', function (index) {
        $.ajax({
            async: false,
            url: page_url + URL,
            data: {
                "wlogIds": wlogIds,
                "type": type
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    layer.alert("撤销成功", {icon: 1}, function (index) {
                        location.reload();
                    });
                } else {
                    layer.alert(data.message, {icon: 2});
                }
            },
            error: function (data) {
                if (data.status == 1001) {
                    layer.alert("当前操作无权限")
                }
            }
        });
    });

}

//批量撤销(不刷新页面)
function cancelWlogAllNoF(id) {
    checkLogin();
    var wlogIds = "";
    $.each($("input[name='" + id + "']:checked"), function () {
        var rs = $(this).val().split(",");
        wlogIds += rs[0] + "_";
    });
    if (wlogIds == "") {
        layer.alert('请选择需要撤销的记录');
        return false;
    }
    var URL = "";
    if (id == "b_checknox") {
        URL = "/warehouse/warehousesout/editIsEnablePicking.do";
    } else if (id == "c_checknox") {
        URL = "/logistics/warehousein/editIsEnableWlog.do";
    } else if (id == "d_checknox") {
        URL = "/logistics/warehousein/editIsEnableWlog.do";
    }
    layer.confirm('确定撤销？', function (index) {
        $.ajax({
            async: false,
            url: page_url + URL,
            data: {
                "wlogIds": wlogIds,
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    layer.alert("撤销成功");
                    $.each($("input[name='" + id + "']:checked"), function () {
                        var n = $(this).parents("tr").index();
                        $("#jh_table").find("tr:eq(" + (n + 1) + ")").remove();
                    });
                    if ($("#jh_table").find("tr").length == 1) {
                        var htm = "<tr><td colspan='7'>暂无记录</td></tr>";
                        $("#jh_table").append(htm);
                        $(".table-style4").remove();
                    }
                } else {
                    layer.alert(data.message, {icon: 2});
                }
            },
            error: function (data) {
                if (data.status == 1001) {
                    layer.alert("当前操作无权限")
                }
            }
        });
    });

}

//打印拣货单
function preview(myDiv) {
    checkLogin();
    $("#mdiv").show();
    $("#" + myDiv).printArea();
    $("#mdiv").hide();

};

//打印捡货单
function printPick(id) {
    checkLogin();
    var wlogIds = "";
    $.each($("input[name='" + id + "']:checked"), function () {
        var rs = $(this).val().split(",");
        wlogIds += rs[1] + "_";
    });
    if (wlogIds == "") {
        layer.alert('请选择需要打印的拣货记录');
        return false;
    }
    $("#salegoodsIds").val(wlogIds);
    $("#searchs").submit();
}

//打印出货单
function printOutOrderById(id, type, fromId) {
    checkLogin();
    var wlogIds = "";
    $.each($("input[name='" + id + "']:checked"), function () {
        wlogIds += $(this).val() + "_";
    });
    if (wlogIds == "") {
        layer.alert('请选择需要打印的拣货记录');
        return false;
    }
    $("#wdlIds").val(wlogIds + "#" + type);
    if (type == "0") {
        $("#type_f").val("0");
    } else if (type == "1") {//带效期
        $("#type_f").val("1");
    }
    $("#" + fromId).submit();
}

//打印出货单
function printOutOrder(id, type) {
    checkLogin();
    var wlogIds = "";
    $.each($("input[name='" + id + "']:checked"), function () {
        wlogIds += $(this).val() + "_";
    });
    if (wlogIds == "") {
        layer.alert('请选择需要打印的拣货记录');
        return false;
    }
    $("#wdlIds").val(wlogIds + "#" + type);
    if (type == "0") {
        $("#type_f").val("0");
    } else if (type == "1") {//带效期
        $("#type_f").val("1");
    } else if (type == "2") {//医械购出库单
        $("#type_f").val("2");
    }else if (type == "4") {//科研购出库单
        $("#type_f").val("4");
    }else if (type == "5") {//带价格出库单
        $("#type_f").val("5");
    }else if (type == "6") {//不带价格出库单
        $("#type_f").val("6");
    }
    $("#searchc").submit();
}

//打印送货单
function printSHOutOrder(id, type, orderId) {
    checkLogin();
    $("#btype_sh").val(type);
    $("#expressId_xs").val(id);
    $("#searchSh").submit();
}

//打印物流单
function printview(wlName, type, orderId, btype, lno, eid, shType, shOrderId, pid) {
    checkLogin();
    var companyId = $("#companyId").val();
    var logisticsNo = lno;
    var expressId = eid;

    var url = page_url + "/warehouse/warehousesout/printExpress.do?orderId=" + orderId;
    var name = "";
    var w = 800;
    var h = 600;
    if (wlName == "EMS") {
        url += "&logisticsId=4&type=" + type;
        name = "EMS快递单";
    } else if (wlName == "中通快递") {
        url += "&logisticsId=2&type=" + type;
        name = "中通快递单";
    } else if (wlName == "顺丰速运") {
        url += "&logisticsId=1&type=" + type;
        name = "顺丰快递单";
    } else if (wlName == "中通快运") {
        url += "&logisticsId=18&type=" + type;
        name = "中通快运单";
    } else if (wlName == "德邦快递") {
        url += "&logisticsId=7&type=" + type;//type：2直发1普发
        name = "德邦快递单";
    }
    url += "&btype=" + btype;//业务类型
    if (shType != "") {
        url += "&shType=" + shType;//售后类型
    }
    if (shOrderId != null) {
        url += "&shOrderId=" + shOrderId;//售后id
    }
    if (companyId == "1") {
        if (wlName != "顺丰速运" && wlName != "中通快递") {
            window.open(url, name, "top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
        } else {
            $.ajax({
                type: "POST",
                url: page_url + "/warehouse/warehousesout/printSFexpress.do",
                data: {
                    'type': type,
                    'orderId': orderId,
                    'btype': btype,
                    'logisticsNo': logisticsNo,
                    'logisticsName': wlName,
                    'expressId': expressId,
                    'shType': shType,
                    'shOrderId': shOrderId,
                    'pid': pid
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        layer.alert(data.message + "无法下单打印！");
                        flag = 1;
                    } else {
                        //refreshNowPageList(data);//刷新列表数据
                    }
                },
                error: function (data) {
                    if (data.status == 1001) {
                        layer.alert("当前操作无权限")
                    }
                }
            });
        }
    } else {
        window.open(url, name, "top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
    }

}


function canelAll(obj, name) {
    checkLogin();
    var status = 0;
    $.each($("input[name='" + name + "']"), function () {
        var datestatus = 0;
        if ($(this).is(":checked") == false) {
            status = 1;
        }
        // 日期选择
        var alt = $(this).attr("alt");
        $.each($("input[alt='" + alt + "']"), function () {
            if ($(this).is(":checked") == false) {
                datestatus = 1;
            }
        })
        if (datestatus == 1) {
            $("input[name='" + alt + "']").prop('checked', false);
        } else {
            $("input[name='" + alt + "']").prop("checked", true);
        }

    });
    if (status == 1) {
        $("input[name='all_" + name + "']").prop('checked', false);
    } else {
        $("input[name='all_" + name + "']").prop("checked", true);
    }

}

//删除快递单
function delwl(id,type,orderId,logisticsName,logisticsNo,invoiceValidStatus) {
    checkLogin();
    // add by Tomcat.Hui 2020/1/7 13:37 .Desc: VDERP-1039 票货同行 已开票订单不允许删除. start
    if (invoiceValidStatus == 1) {
        layer.alert("已开具发票，无法删除。");
    } else {
        layer.confirm("您是否确认删除？", {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: page_url + "/warehouse/warehousesout/delExpress.do",
                data: {
                    'expressId': id,
                    'businessType': type,
                    'saleorderId': orderId,
                    'logisticsNo':logisticsNo,
                    'logisticsName':logisticsName
                },
                dataType: 'json',
                success: function (data) {
                    //window.location.reload();
                    //refreshNowPageList(data);
                    layerPFF();
                },
                error: function (data) {
                    if (data.status == 1001) {
                        layer.alert("当前操作无权限")
                    }
                }
            });
        }, function () {
        });
    }
    // add by Tomcat.Hui 2020/1/7 13:37 .Desc: VDERP-1039 票货同行 已开票订单不允许删除. end

}

/**
 * 导出未出库条码
 */
function exportOutBarcodeList() {
    checkLogin();
    //要出库的销售产品ID
    var goodsIds = "";
    $.each($("input[name='g_checknox']:checked"), function () {
        var rs = $(this).val();
        goodsIds += rs + "_";
    });
    if (goodsIds == "") {
        layer.alert("请选择要出库的商品！");
        return false;
    }
    location.href = page_url + '/report/logistics/exportOutBarcodeList.do?goodsIdsList=' + goodsIds;
}

function sendOutMsg(obj) {
    checkLogin();
    var layerParams = $(obj).attr('layerParams');
    if (typeof (layerParams) == 'undefined') {
        alert('参数错误');
    } else {
        layerParams = $.parseJSON(layerParams);
    }
    var link = layerParams.link;

    sendValue = $("#sendValue").val();
    if (sendValue == "1") {
        layer.confirm("该运单短信已发送，是否再次发送？", {
            btn: ['确定', '取消'] //按钮
        }, function () {

            var index = layer.open({
                type: 2,
                shadeClose: false,
                area: [layerParams.width, layerParams.height],
                title: layerParams.title,
                content: encodeURI(link),
                success: function (layero, index) {
                }
            });
            $('.layui-layer-btn1').click();
        }, function () {
        });
    } else {
        var index = layer.open({
            type: 2,
            shadeClose: false,
            area: [layerParams.width, layerParams.height],
            title: layerParams.title,
            content: encodeURI(link),
            success: function (layero, index) {
            }
        });
    }
}

//验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}

function sendmsgSubForm() {
    checkLogin();
    var phoneNo = $("input[name='" + $('input:radio:checked').attr("id") + "']").val();
    if ((isPhoneNo($.trim(phoneNo)) == false) || phoneNo == "") {
        warnTips($("input[name='" + $('input:radio:checked').attr("id") + "']").attr("id"), "该客户无手机号，无法发送短信");
        return false;
    }
    var saleorderNo = $("#saleorderNo").val();
    var logisticsName = $("#logisticsName").val();
    var logisticsNo = $("#logisticsNo").val();
    $.ajax({
        type: "POST",
        url: page_url + "/warehouse/warehousesout/toSendOutMsg.do",
        data: {
            "phoneNo": phoneNo,
            "saleorderNo": saleorderNo,
            "logisticsName": logisticsName,
            "logisticsNo": logisticsNo
        },
        dataType: 'json',
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.message);
            } else {
                layerPFF();
                $("#close-layer").click();
            }
        },
        error: function (data) {
            if (data.status == 1001) {
                layer.alert("当前操作无权限")
            }
        }
    });
}

//删除消息
function delComments(saleorderWarehouseCommentsId) {
    layer.confirm("您是否确认删除？", {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            async: true,
            type: "POST",
            url: page_url + '/warehouse/warehousesout/updateComments.do',
            data: {
                'saleorderWarehouseCommentsId': saleorderWarehouseCommentsId,
                'isDelete': 1
            },
            dataType: 'json',
            success: function (data) {
                layerPFF();
            },
            error: function (data) {
                if (data.status == 1001) {
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function () {
    });
}

//更新出库备注
function updateComments() {
    var saleorderId = $("#saleorderId").val();
    var comments = $("#comments").val();
    var saleorderWarehouseCommentsId = $("#saleorderWarehouseCommentsId").val();
    if (comments == "") {
        warnTips("comments", "备注内容不允许为空");
        return false;
    }
    if (comments.length > 60) {
        warnTips("comments", "备注内容不允许超过60个字");
        return false;
    }
    $.ajax({
        type: "POST",
        url: page_url + "/warehouse/warehousesout/updateComments.do",
        data: {
            "saleorderId": saleorderId,
            "comments": comments,
            "saleorderWarehouseCommentsId": saleorderWarehouseCommentsId
        },
        dataType: 'json',
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.message);
            } else {
                layerPFF();
                $("#close-layer").click();
            }
        },
        error: function (data) {
            if (data.status == 1001) {
                layer.alert("当前操作无权限")
            }
        }
    });
}

//打印所有的出库记录
function printAllOutOrder() {
    $("#searchall").submit();
}

function checcinputShow(){
    $(".isProblem").each(function (i) {
        if($(this).val() == 0){
            $(".problemRemark").eq(i).hide();
        }else{
            $(".problemRemark").eq(i).show();
        }
    })
}

$('.isProblem').change(function () {
    checcinputShow()
})

$('.J-IsProblem-edit-btn').click(function () {

    $('.J-isProblem-edit').show();
    $('.J-reason-edit').show();
    $('.J-isProblem-show').hide();
    $('.J-reason-show').hide();
    $('.J-IsProblem-save-btn').show();
    $(this).hide();
    checcinputShow();
});


//保存
$('.J-IsProblem-save-btn').click(function () {
    var warehouseGoodsId = [];
    var IsProblems = [];
    var problemRemark=[];
    $(".warehouseGoodsOperateLogId").each(function () {
        warehouseGoodsId.push($(this).val())
    });

    var cansubmit= true

    $(".isProblem").each(function (i) {
        IsProblems.push($(  this).val())
        if($(this).val() == '1' && !$(".problemRemark").eq(i).val()){
            cansubmit = false;
        }
    });

    if(!cansubmit){
        alert('手动将问题库存改为“是”时，需要填写问题原因')
        return false;
    }
    $(".problemRemark").each(function(){
        problemRemark.push($(this).val())
    })

    $.ajax({
        url: page_url + '/warehouse/warehousesout/updateWarehouseProblem.do',
        type: 'post',
        data: {
            'warehouseGoodsId':warehouseGoodsId,
            'IsProblems': IsProblems,
            'problemRemark':problemRemark,
            'goodId':$("#goodId").val()
        },
        traditional:true,
        datetype:'json',
        success: function (data) {

           window.location.reload();
        }
    })
    $('.J-isProblem-edit').hide();
    $('.J-reason-edit').hide();
    $('.J-isProblem-show').show();
    $('.J-reason-show').show();
    $('.J-IsProblem-edit-btn').show();
    $(this).hide();
});


// add by Tomcat.Hui 2020/1/6 10:19 .Desc: VDERP-1039 票货同行. start
function openEInvoice(invoiceApplyId,relatedId,formToken){
    $("#openEinvoiceId").attr("disabled", true);
    var lock = false;//默认未锁定
    layer.confirm("确定开具电子发票？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        if (!lock) {
            lock = true;// 锁定
            $.ajax({
                type: "POST",
                url: "./openEInvoicePush.do",
                data: {"invoiceApplyId":invoiceApplyId,"relatedId":relatedId,"formToken":formToken},
                dataType:'json',
                success: function(data){
                    refreshPageList(data)
                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            });
        }
    }, function(){
        $("#openEinvoiceId").attr("disabled", false);
    });
}

function batchDownEInvoice(){
    checkLogin();
    var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
    $("body").prepend(div); //jq获取上一页框架的父级框架；
    $.ajax({
        type: "POST",
        url: page_url + "/finance/invoice/batchDownEInvoice.do",
        dataType:'json',
        success: function(data){
            $(".tip-loadingNewData").remove();
            window.location.reload();
            //refreshNowPageList(data);//刷新列表数据
        },
        error:function(data){
            $(".tip-loadingNewData").remove();
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}

function EInvoiceCheck(invoiceApplyId,relatedId,formToken){

    $.ajax({
        async:false,
        type: "POST",
        url: page_url + "/warehouse/warehousesout/checkInvoiceParams.do",
        dataType:'json',
        data: {"invoiceApplyId":invoiceApplyId,"relatedId":relatedId,"formToken":formToken},
        success: function(data){
            if (data.code != 0) {
                layer.alert(data.message);
            } else {
                $.ajax({
                    type: "POST",
                    url: page_url + "/finance/invoice/openEInvoicePush.do",
                    data: {"invoiceApplyId":invoiceApplyId,"relatedId":relatedId,"formToken":formToken},
                    dataType:'json',
                    success: function(data){
                        if (data.code != 0) {
                            layer.alert(data.message);
                        } else {
                            $("#close-layer").click();
                            var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
                            parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
                            parent.location.reload();
                        }
                    },
                    error:function(data){
                        if(data.status ==1001){
                            layer.alert("当前操作无权限")
                        }
                    }
                });
            }

        },
        error:function(data){
            $(".tip-loadingNewData").remove();
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            } else {
                layer.alert(data.message);
            }
        }
    });
}
// add by Tomcat.Hui 2020/1/6 10:19 .Desc: VDERP-1039 票货同行. end



//addis 批量出库
function autoWarehouse(idType,primaryKey) {
    checkLogin();
    var idCnt = "";
    var url="";
    var id="";
    if(idType===1||idType===2){
        id="afterSalesId";
        url='/warehouse/businessWarehouseOut/warehouseSMEnd.do?'+id+'='+primaryKey;

    }else{
        id="saleorderId"
        url='/warehouse/warehousesout/warehouseSMEnd.do?'+id+'='+primaryKey;
    }
    $.each($("input[name='b_checknox']"), function (i,n) {
        if($(this).prop("checked")){
            var batchNumber = $(this).siblings("input[name='batchNumber']").val();
            var expirationDate = $(this).siblings("input[name='expirationDate']").val();
            var goodsId = $(this).siblings("input[name='goodsId']").val();
            var num = $(this).siblings("input[name='num']").val();
            var warehousePickingDetailId = $(this).siblings("input[name='warehousePickingDetailId']").val();
            idCnt += batchNumber + "," +expirationDate+","+goodsId+","+ num+","+warehousePickingDetailId/*+","+tdArr.eq(9).find('input').val()*/;
            idCnt += "#";
        }
    });

    $.ajax({
        url:page_url+'/warehouse/warehousesout/getInLibraryBarcode.do?'+id+'='+primaryKey,
        type:'post',
        async : 'false',
        data:{"idCnt":idCnt,"bussinessType":idType},
        dataType:'json',
       success:function (data) {

          if(data.code===-1){
              layer.alert(data.message)
          }else {
              var idCnt2="";
              for(var i=0;i<data.listData.length;i++){
                  idCnt2+=data.listData[i].warehouseGoodsOperateLogId+","+data.listData[i].num;
                  idCnt2+="#"
              }
              $.ajax({
                      url:page_url+url,
                      type:'post',
                      async :'false',
                      data:{"idCnt":idCnt2,"batchType":1,"formToken":$("#formToken").val(),"businessType":$("#bussinessType").val()},
                      dataType:'html',
                      success:function (data) {
                          layer.alert("操作成功");
                          window.location.reload();
                      },
                      error:function () {
                          if(data.status ==1001){
                              layer.alert("当前操作无权限")
                          }
                      }
                  }
              )

          }
       }
    });




}




