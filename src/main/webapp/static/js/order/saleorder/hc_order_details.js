$(function(){
    document.onkeydown = function(e){
        if(!e){
            e = window.event;
        }
        if((e.keyCode || e.which) == 13){
            return false;
        }
    }
});
function isTraderAllowInvoice(traderId,traderName,invoiceType,type) {
    checkLogin();
    var url=page_url+"/trader/customer/isTraderAllowInvoice.do"
    $.ajax({
        type: "POST",
        url: url,
        data: {'traderId':traderId,'traderName':traderName,'invoiceType':invoiceType},
        dataType:'json',
        success: function(data){
            if(data.code==-1){
                layer.alert(data.message)
            }else{
                if(type==1){
                    $("#invoiceApply").click();
                }else{
                    $("#advanceInvoiceApply").click();
                }
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
                return;
            }
            layer.alert("验证客户能否开票失败")
        }
    });
}
function closeSaleorder(saleorderId){
    checkLogin();
    layer.confirm("订单被关闭后无法修改和跟进，是否确认？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./closeSaleorder.do",
            data: {'saleorderId':saleorderId,'status':3},
            dataType:'json',
            success: function(data){
                /*layer.alert(data.message,{
                    closeBtn: 0,
                    btn: ['确定'] //按钮
                }, function(){
                    window.location.reload();
                });*/
                window.location.reload();
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function(){
    });
}

function syncNewTraderName(saleorderId,invoiceStatus,traderId) {
    checkLogin();
    if(invoiceStatus==1||invoiceStatus==2){
        layer.alert("已开票的订单，无法更新客户名称")
        return;
    }
    var data={'saleorderId':saleorderId,'traderId':traderId};
    var url=page_url+"/order/saleorder/syncNewTraderName.do"
    var index=layer.confirm('确认更新订单中的客户名称吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType:'json',
            success: function(data){
                if (data.code == 0) {
                    window.location.reload();
                } else {
                    layer.alert(data.message);
                }

            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function(){
        layer.close(index);
    });
}
function operationCheck(saleorderId){
    var taskName=$("#taskName").val();
    if(taskName=='运营部审核') {
        $.ajax({
            type: "POST",
            url: "../saleorder/getOrderTraderAptitudeStatus.do",
            data: {'saleorderId': saleorderId},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    var customer = data.data;
                    if (customer.aptitudeStatus == null || customer.aptitudeStatus == 3 || customer.aptitudeStatus == 2) {
                        var index1 = layer.confirm("客户资质尚未提交审核，确认审核通过订单吗？", {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            layer.close(index1)
                            $("#orderComplement").click();
                        }, function () {
                            layer.close(index1)
                        });

                    } else if (customer.aptitudeStatus == 0) {
                        var index1 = layer.confirm("客户资质还在审核中，确认审核通过订单吗？", {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            layer.close(index1)
                            $("#orderComplement").click();
                        }, function () {
                            layer.close(index1)
                        });
                    } else {
                        $("#orderComplement").click();
                    }
                } else {
                    layer.alert(data.message);
                }
            },
            error: function (data) {
                if (data.status == 1001) {
                    layer.alert("当前操作无权限")
                }
            }
        });
    }else {
        $("#orderComplement").click();
    }
}
function editApplyValidSaleorder(saleorderId,taskId,formToken) {
    $.ajax({
        type: "POST",
        url: "./editApplyValidSaleorder.do",
        data: {'saleorderId':saleorderId,'taskId':taskId,'formToken':formToken},
        dataType:'json',
        success: function(data){
            if (data.code == 0) {
                window.location.reload();
            } else {
                layer.alert(data.message);
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}
function applySaleorder(saleorderId,taskId,isZeroPrice,type) {
    var formToken = $("input[name='formToken']").val();
    $.ajax({
        type: "POST",
        url: "../saleorder/checkSaleorder.do",
        data: {'saleorderId':saleorderId},
        dataType:'json',
        success: function(data){
            if (data.code == 0) {
                var msg = "";
                if(isZeroPrice == 1){
                    msg = "有产品价格为0,是否确认操作";
                }else{
                    msg = "您是否确认申请审核该订单？";
                }
                if(type==1) {
                    layer.confirm(msg, {
                        btn: ['确定', '取消'] //按钮
                    }, function () {
                        editApplyValidSaleorder(saleorderId, taskId, formToken);
                    }, function () {
                    });
                }else{
                    editApplyValidSaleorder(saleorderId, taskId, formToken);
                }
            } else {
                layer.alert(data.message);
                return false;
            }

        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}
function applyValidSaleorder(saleorderId,taskId,isZeroPrice){
    checkLogin();
    var _self=self;
    $.ajax({
        type: "POST",
        url: "../saleorder/getOrderTraderAptitudeStatus.do",
        data: {'saleorderId':saleorderId},
        dataType:'json',
        success: function(data){
            if (data.code == 0) {
                var customer=data.data;
                if(customer.aptitudeStatus==null||customer.aptitudeStatus==3||customer.aptitudeStatus==2){
                    layer.confirm("客户资质尚未提交审核，请前往维护", {
                        btn: ['继续提交','去维护'] //按钮
                    }, function(){
                        applySaleorder(saleorderId,taskId,isZeroPrice,2)
                    }, function(){
                        window.location.reload();
                        var frontPageId = $(window.parent.parent.document).find('.active').eq(1).attr('id');
                        var url=page_url+"/trader/customer/getFinanceAndAptitude.do?traderId="+customer.traderId+"&traderCustomerId="+customer.traderCustomerId;
                        var item = { 'id': customer.traderCustomerId, 'name':"财务与资质信息", 'url': url, 'closable': true };
                        _self.parent.parent.closableTab.addTab(item);
                        _self.parent.parent.closableTab.resizeMove();
                        $(window.parent.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
                    });

                }else{
                    applySaleorder(saleorderId,taskId,isZeroPrice,1)
                }
            } else {
                layer.alert(data.message);
            }
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}


function validSaleorder(saleorderId){
    checkLogin();
    layer.confirm("您是否确认生效该订单？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var isNotDelPriceZero = $("#isNotDelPriceZero").val();
        if (isNotDelPriceZero == 1) {
            layer.confirm("订单中包含单价为0的产品，是否确认生效？", {
                btn: ['确定','取消'] //按钮
            },function(){
                $.ajax({
                    type: "POST",
                    url: "./validSaleorder.do",
                    data: {'saleorderId':saleorderId,'validStatus':1},
                    dataType:'json',
                    success: function(data){
                        /*layer.alert(data.message,{
                            closeBtn: 0,
                            btn: ['确定'] //按钮
                        }, function(){
                            window.location.reload();
                        });*/
                        if (data.code == 0) {
                            window.location.reload();
                        } else {
                            layer.alert(data.message);
                        }
                    },
                    error:function(data){
                        if(data.status ==1001){
                            layer.alert("当前操作无权限")
                        }
                    }
                });
            }, function(){
            });
        } else {
            $.ajax({
                type: "POST",
                url: "./validSaleorder.do",
                data: {'saleorderId':saleorderId,'validStatus':1},
                dataType:'json',
                success: function(data){
                    /*layer.alert(data.message,{
                        closeBtn: 0,
                        btn: ['确定'] //按钮
                    }, function(){
                        window.location.reload();
                    });*/
                    if (data.code == 0) {
                        window.location.reload();
                    } else {
                        layer.alert(data.message);
                    }
                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            });
        }
    }, function(){
    });
}

function noValidSaleorder(saleorderId){
    checkLogin();
    layer.confirm("您是否确认撤销该订单生效状态？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./noValidSaleorder.do",
            data: {'saleorderId':saleorderId,'validStatus':0},
            dataType:'json',
            success: function(data){
                /*layer.alert(data.message,{
                    closeBtn: 0,
                    btn: ['确定'] //按钮
                }, function(){
                    window.location.reload();
                });*/
                window.location.reload();
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function(){
    });
}

function contractReturnDel(attachmentId){
    checkLogin();
    var formToken = $("input[name='formToken']").val();
    layer.confirm("确认删除该条合同回传吗？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./contractReturnDel.do",
            data: {'attachmentId':attachmentId,'formToken':formToken},
            dataType:'json',
            success: function(data){
                if (data.code == -1) {
                    layer.alert(data.message);
                } else {
                    window.location.reload();
                }
                /*
                layer.alert(data.message,{
                    closeBtn: 0,
                    btn: ['确定'] //按钮
                }, function(){
                    window.location.reload();
                });*/
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function(){
    });
}
function requestCheck(saleorderId,taskId){
    checkLogin();
    var alertCount=$("#alertCount").val();
    if(alertCount==0){
        layer.alert("如有合同审核不合格，请先删除不合格合同，重新上传后申请审核！");
        $("#alertCount").val(Number(alertCount)+1);
    }else{
        layer.confirm("您是否确认申请审核合同？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            //提交审核方法
            var formToken = $("input[name='formToken']").val();
            $.ajax({
                type: "POST",
                url: "./editApplyValidContractReturn.do",
                data: {'saleorderId':saleorderId,'taskId':taskId,'formToken':formToken},
                dataType:'json',
                success: function(data){
                    if (data.code == 0) {
                        window.location.reload();
                    } else {
                        layer.alert(data.message);
                    }

                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            });
        }, function(){
        });

    }
}
function deliveryOrderReturnDel(attachmentId){
    checkLogin();
    var formToken = $("input[name='formToken']").val();
    layer.confirm("确认删除该条送货单回传吗？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type: "POST",
            url: "./deliveryOrderReturnDel.do",
            data: {'attachmentId':attachmentId,'formToken':formToken},
            dataType:'json',
            success: function(data){
                if (data.code == -1) {
                    layer.alert(data.message);
                } else {
                    window.location.reload();
                }
                /*
                layer.alert(data.message,{
                    closeBtn: 0,
                    btn: ['确定'] //按钮
                }, function(){
                    window.location.reload();
                });*/
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, function(){
    });
}

function vailNum(obj){
    checkLogin();
    if($(obj).val().length>14){
        layer.alert("价格长度最大不允许14个字符。", {
            icon : 2
        }, function(index) {
            $(obj).addClass("errorbor");
            layer.close(index);
            $(obj).focus();
        });
        return false;
    }
    var isNum = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
    if($(obj).val() != undefined && $(obj).val()!="" && !isNum.test($(obj).val())){
        if($(obj).val() == '0'){
            layer.alert("参考成本不能为0或者为空", {
                icon : 2
            }, function(index) {
                $(obj).addClass("errorbor");
                layer.close(index);
                $(obj).focus();
            });
        }else{
            layer.alert("价格输入格式错误！仅允许使用数字，最多精确到小数点后两位。", {
                icon : 2
            }, function(index) {
                $(obj).addClass("errorbor");
                layer.close(index);
                $(obj).focus();
            });
        }
        return false;
    }else{
        $(obj).removeClass("errorbor");
        return true;
    }
}


function vailNumCost(obj){
    checkLogin();
    if($(obj).val().length>14){
        layer.alert("价格长度最大不允许14个字符。", {
            icon : 2
        }, function(index) {
            $(obj).addClass("errorbor");
            layer.close(index);
            $(obj).focus();
        });
        return false;
    }
    var isNum = /^(([0-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
    if($(obj).val() != undefined && $(obj).val()!="" && !isNum.test($(obj).val())){
        if($(obj).val() == '0'){
            layer.alert("参考成本不能为0或者为空", {
                icon : 2
            }, function(index) {
                $(obj).addClass("errorbor");
                layer.close(index);
                $(obj).focus();
            });
        }else{
            layer.alert("价格输入格式错误！仅允许使用数字，最多精确到小数点后两位。", {
                icon : 2
            }, function(index) {
                $(obj).addClass("errorbor");
                layer.close(index);
                $(obj).focus();
            });
        }
        return false;
    }else{
        $(obj).removeClass("errorbor");
        return true;
    }
}

function editReferenceCostPrice(saleorderGoodsId,obj){
    var referenceCostPrice = $(obj).val();
    var saleorderGoodsIdArr = new Array();
    saleorderGoodsIdArr[saleorderGoodsIdArr.length] = saleorderGoodsId;
    var referenceCostPriceArr = new Array();
    referenceCostPriceArr[referenceCostPriceArr.length] = $(obj).val();
    var saleorderId = $("input[name='saleorderId']").val();
    //修改销售订单中对应产品的参考成本

    $.ajax({
        async : false,
        url : './saveReferenceCostPrice.do',
        data : {"referenceCostPriceArr":JSON.stringify(referenceCostPriceArr),"saleorderGoodsIdArr":JSON.stringify(saleorderGoodsIdArr),"saleorderId":saleorderId},
        type : "POST",
        dataType : "json",
        success : function(data) {

        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    })
}

function checkCostPrice(obj){
    var saleorderId = $("input[name='saleorderId']").val();
    //判断归属当前登陆人的产品的成本价有没有填写
    var flag = true;
    //是否有为0的的参考成本
    var isZero = false;
    $("input[goodscategoryuser^='y']").each(function(i){
        if($(this).val() == '0.00' || $(this).val() == '0'|| $(this).val() == ""){
            isZero = true;
        }
    });
    var updateStr = "";
    //循环验证哪一行数据有改动
    $("input[name^='referenceCostPrice_']").each(function(i){
        if($(this).val() != $(this).attr("alt")){
            updateStr += i;
        }
    });
    //参考成本
    var referenceCostPriceArr = new Array();
    $("input[name^='referenceCostPrice_']").each(function(i){
        if(updateStr.indexOf(i) != -1){
            referenceCostPriceArr[referenceCostPriceArr.length] = $(this).val();
            if(!vailNumCost(this)){
                flag = false;
                return flag;
            }
        }
    });
    if(flag==false){
        return false;
    }
    var saleorderGoodsIdArr = new Array();
    $("input[name='saleorderGoodsId']").each(function(i){
        if(updateStr.indexOf(i) != -1){
            saleorderGoodsIdArr[saleorderGoodsIdArr.length] = $(this).val();
        }
    });

    //修改销售订单中对应产品的参考成本
    if(isZero==false){
        $.ajax({
            async : false,
            url : './saveReferenceCostPrice.do',
            data : {"referenceCostPriceArr":JSON.stringify(referenceCostPriceArr),"saleorderGoodsIdArr":JSON.stringify(saleorderGoodsIdArr),"saleorderId":saleorderId},
            type : "POST",
            dataType : "json",
            success : function(data) {
                layer.alert("提交成功");
                window.location.reload();
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        })
    }else{
        index = layer.confirm("当前参考成本为0或者为空，若跳过</br>请务必在本月底填写完整", {
            btn: ['确定','取消'] //按钮
        }, function(){
            layer.close(index);
            $.ajax({
                async : false,
                url : './saveReferenceCostPrice.do',
                data : {"referenceCostPriceArr":JSON.stringify(referenceCostPriceArr),"saleorderGoodsIdArr":JSON.stringify(saleorderGoodsIdArr),"saleorderId":saleorderId},
                type : "POST",
                dataType : "json",
                success : function(data) {
                    layer.alert("提交成功");
                    window.location.reload();
                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            })
        });
    }

    return  false;
}

function saveCostPrice(obj){
    var saleorderId = $("input[name='saleorderId']").val();
    //判断归属当前登陆人的产品的成本价有没有填写
    var flag = true;
//	$("input[goodscategoryuser^='y']").each(function(i){
//			if($(this).val() == '0.00' || $(this).val() == ""){
//				layer.alert("参考成本不能为0或者为空", {
//					icon : 2
//				}, function(index) {
//					$(this).addClass("errorbor");
//					layer.close(index);
//					$(this).focus();
//				});
//				flag = false;
//			}
//	});
    var updateStr = "";
    //循环验证哪一行数据有改动
    $("input[name^='referenceCostPrice_']").each(function(i){
        if($(this).val() != $(this).attr("alt")){
            updateStr += i;
        }
    });
    //参考成本
    var referenceCostPriceArr = new Array();
    $("input[name^='referenceCostPrice_']").each(function(i){
        if(updateStr.indexOf(i) != -1){
            referenceCostPriceArr[referenceCostPriceArr.length] = $(this).val();
            if(!vailNumCost(this)){
                flag = false;
                return flag;
            }
        }
    });
    if(flag==false){
        return false;
    }
    var saleorderGoodsIdArr = new Array();
    $("input[name='saleorderGoodsId']").each(function(i){
        if(updateStr.indexOf(i) != -1){
            saleorderGoodsIdArr[saleorderGoodsIdArr.length] = $(this).val();
        }
    });
    //修改销售订单中对应产品的参考成本
    $.ajax({
        async : false,
        url : './saveReferenceCostPrice.do',
        data : {"referenceCostPriceArr":JSON.stringify(referenceCostPriceArr),"saleorderGoodsIdArr":JSON.stringify(saleorderGoodsIdArr),"saleorderId":saleorderId},
        type : "POST",
        dataType : "json",
        success : function(data) {
            layer.alert("提交成功");
            window.location.reload();
        },
        error:function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    })

    return  false;
}

function confirmMsg(saleorderId){
    checkLogin();
    index = layer.confirm("销售直发订单退货售后前请确认收货，是否继续？", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var timestamp = Date.parse(new Date());
        var	url = page_url+"/order/saleorder/addAfterSalesPage.do?flag=th&&saleorderId="+saleorderId;
        $("#addAfter").attr('tabTitle','{"num":"view'+timestamp+'","title":"新增售后","link":"'+url+'"}');
        $("#addAfter").click();
        layer.close(index);
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
            refreshNowPageList(data);//刷新列表数据
        },
        error:function(data){
            $(".tip-loadingNewData").remove();
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}
