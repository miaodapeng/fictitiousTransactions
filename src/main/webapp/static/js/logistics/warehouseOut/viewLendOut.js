/**
 * 申请修改订单前校验
 * @param saleorderId
 */
var parh=$("#path").val()
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

//批量撤销
function cancelWlogAll(id, type) {
	var selectNum = 0;
	var totalNum = parseInt($('#kdNum').val());
	var houseoutNum = parseInt($('#houseoutNum').val());
	
	$('.J-select-item:checked').each(function(){
		selectNum += $(this).data('num');
	})
	if(selectNum > houseoutNum-totalNum){
		console.log(houseoutNum-totalNum);
		layer.alert("撤销数量大于可撤销数量");
		return false;
	}
	
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
    console.log(type);
    if (id == "b_checknox") {
        URL = "/warehouse/warehousesout/editIsEnablePicking.do";
    } else if (id == "c_checknox") {
        URL = "/warehouse/warehousesout/editIsEnableWlog.do";
    } else if (id == "d_checknox") {
        URL = "/warehouse/warehousesout/editIsEnableWlog.do";
    }
    var lendoutId = $("#orderId").val();
    layer.confirm('确定撤销？', function (index) {
        $.ajax({
            async: false,
            url: page_url + URL,
            data: {
                "wlogIds": wlogIds,
                "type": type,
                "lendOutId":lendoutId,
                "num":selectNum
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
function delwl(id, type, orderId) {
    checkLogin();
    layer.confirm("您是否确认删除？", {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: page_url + "/warehouse/warehousesout/delExpress.do",
            data: {
                'expressId': id,
                'businessType': type,
                'saleorderId': orderId
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
            'problemRemark':problemRemark
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

function handlerEdit(){
	window.location.reload()
}

function closelendout(lendOutId) {
	var warehousePickList = $('#test').val();
	if(warehousePickList !=null){
		layer.alert("请先撤销出库");
		return false;
	} 
	layer.confirm("确认关闭该外借单吗？", {
        btn: ['确定', '取消'] //按钮
    },function(){
	$.ajax({
		async:true,
		url:page_url+'/warehouse/warehousesout/saveLendOutEdit.do',
		data:{
			"lendOutId":lendOutId,
			"lendOutStatus":2,
		},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				//$('body').load(window.location.href);
				//window.parent && window.parent.handlerEdit();
				window.location.reload()
			}else{
				layer.alert(data.message);
				return false;
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	}) 
    }, function () {
    });
}
function verify() {
	var tradercontact = $("#tradercontact").text();
	var traderAddress = $("#traderAddress").text();
	if(tradercontact == ''){
		alert('请选择收货联系人');
		return false;
	}
	if(traderAddress == ''){
		alert('请选择收货联系人');
		return false;
	}
}

function viewLendOutPicking(lendOutId) {
	var tradercontact = $("#tradercontact").text();
	var traderAddress = $("#traderAddress").text();
	if(tradercontact == ''){
		alert('请选择收货联系人');
		return false;
	}
	if(traderAddress == ''){
		alert('请选择收货联系人');
		return false;
	}
	var businessType = $("input[name='businessType']").val();
	$.ajax({
		async:true,
		url:page_url+'/warehouse/warehousesout/viewLendOutPicking.do',
		data:{
			"lendOutId":lendOutId,
			"businessType":businessType,
		},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code == 1){
				window.location.href = 'logistics/warehouseOut/view_lendoutPicking';
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	}) 
}

function noBarcode() {
	layer.alert("尚未生成条码，无法查看");
}