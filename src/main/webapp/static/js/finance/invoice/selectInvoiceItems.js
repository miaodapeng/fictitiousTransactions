function submitSelected(saleorderId){

    if (sumbitCheck()) {
        //组装数据
        var invoiceApplyDetails = new Array();

        var comments = "";
        if ($("#isAdvanceTax").val() == 1) {
            comments = $("#advanceInvoiceReason").val();
        } else {
            comments = $("#invoiceComments").val();
        }

        $("#table_items").children("tr[name='data_tr']").each(function (i) {

            var td = $(this).children("td[name='td_nums']");
            var saleorderGoodsId = $(this).children("td[name='saleorderGoodsId']").text();

            var applyNum = parseInt($(td).children("input[name='applyNum']").val());
            var totalAmount = parseFloat($(this).children("td[name='applyAmount']").text());
            var price = parseFloat($(this).children("td[name='price']").text());

            var detail = {
                detailgoodsId : saleorderGoodsId
            };
            if (applyNum > 0) {
                detail.num = applyNum;
                detail.totalAmount = totalAmount;
                detail.price = price;
                if ($("#invoiceMethod").val() == 1) {
                    //手动开票增加变更后名称
                    var td_changedName = $(this).children("td[name='td_changedName']");
                    var changedGoodsName = $(td_changedName).children("textarea[name='changedName']").val();
                    detail.changedGoodsName = changedGoodsName;
                }
                invoiceApplyDetails.push(detail);
            }
        });

        if (invoiceApplyDetails.length > 0) {
            $.ajax({
                async:false,
                url:page_url + "/finance/invoice/saveOpenInvoceApply.do",
                data:{
                    "relatedId":saleorderId,
                    "isAuto":$("#invoiceMethod").val(),
                    "comments":comments,
                    "invoiceType" : $("#invoiceType").val(),
                    "isAdvance":$("#isAdvanceTax").val(),
                    "detailString":JSON.stringify(invoiceApplyDetails),
                    "formToken":$("input[name='formToken']").val()
                },
                type:"POST",
                dataType : "json",
                //traditional: true,
                success:function(data){
                    if(data.code==0){
                        $("#close-layer").click();
                        var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
                        parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
                        parent.location.reload();
                        return false;
                    }else{
                        layer.alert(data.message,{icon:2});
                        warn2Tips("errorTitle",data.message);//文本框ID和提示用语
                    }
                },error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            })
        } else {
            layer.alert("未选择任何商品");
        }
    }
}

//提交前的校验
function sumbitCheck(){

    //手动开票
    if ($("#invoiceMethod").val() == 1) {
        var break_flag = true;
        $("#table_items").children("tr[name='data_tr']").each(function (i) {
            var td_changedName = $(this).children("td[name='td_changedName']");
            var changedGoodsName = $(td_changedName).children("textarea[name='changedName']").val();
            console.log(changedGoodsName);
            if (changedGoodsName == undefined || changedGoodsName == '' || changedGoodsName == null) {
                layer.alert("修改后的商品名称不能为空");
                break_flag = false;
            }
        });

        if (break_flag == false) {
            return false;
        }
    }

    //提前开票必须有备注
    if($("#isAdvanceTax").val() == 1){
        var content = $("#advanceInvoiceReason").val();
        if ( content == undefined || content == '' || content == null) {
            layer.alert("提前开票原因不能为空");
            return false;
        }
    }

    return true;
}



function initData(){
    freshParams();
    freshTotalAmount();
    isAdvanceChange();
}

//输入数量变化单行总金额计算
function onNumChange(saleorderGoodsId){
    var applyNum_id = "applyNum_" + saleorderGoodsId;
    var alreadyNum_id = "alreadyNum_" + saleorderGoodsId;
    var price_id =  "price_" + saleorderGoodsId;
    var applyAmount_id = "applyAmount_" + saleorderGoodsId;
    var totalAmount_id = "totalAmount_" + saleorderGoodsId;

    var appliedAmount_id = "appliedAmount_" + saleorderGoodsId;
    var invoicedAmount_id = "invoicedAmount_" + saleorderGoodsId;

    var applyNum = parseInt($("#" + applyNum_id).val());
    var maxNum = parseInt($("#maxApplyNum_" + saleorderGoodsId).text());
    var totalAmount =  parseFloat($("#" + totalAmount_id).text());
    var price =  parseFloat($("#" + price_id).text());

    //已申请金额 已开票金额
    var appliedAmount = parseFloat($("#" + appliedAmount_id).text());
    var invoicedAmount = parseFloat($("#" + invoicedAmount_id).text());

    //VDERP-2144 【功能】【申请开票】在申请开票页面删除未开票数量小于1的商品数量时，金额计算展示错误
    var reg = /^[0-9]{1,}$/;
    // var reg = /^([0-9]\\d*(\\.[0-9]*[0-9])?)|(0\\.[0-9]*[1-9])|0$/;

    if (!reg.test($("#" + applyNum_id).val())) {
        $("#" + applyNum_id).val(maxNum);
        applyNum = maxNum;
        // warnTips("retainageAmountError","只能输入数字");
    }

    if ( applyNum > maxNum|| applyNum < 0) {
        applyNum = maxNum;
        $("#" + applyNum_id).val(applyNum);
    }
    var applyAmount = 0;
    if (applyNum >= maxNum){
        //用总额 - 已开票金额
        applyAmount = totalAmount - appliedAmount - invoicedAmount;
    } else {
        applyAmount = applyNum * price;
    }
    $("#" + applyAmount_id).text(applyAmount.toFixed(2));
    //刷新统计
    freshTotalAmount();
    isAdvanceChange();
}

//数量最大化
function maxItems(obj){
    if ($(obj).is(':checked')) {
        $("#table_items").children("tr[name='data_tr']").each(function (i) {
            var td = $(this).children("td[name='td_nums']");
            var maxNum = parseInt($(td).children("span[name='maxApplyNum']").text());
            $(td).children("input[name='applyNum']").val(maxNum);
        });
        freshParams();
        freshTotalAmount();
        isAdvanceChange();
    }
}

//联动刷新申请金额
function freshParams(){
    $("#table_items").children("tr[name='data_tr']").each(function (i) {
        var td = $(this).children("td[name='td_nums']");
        var applyNum = parseInt($(td).children("input[name='applyNum']").val());
        var maxNum = parseInt($(td).children("span[name='maxApplyNum']").text());
        var price = parseFloat($(this).children("td[name='price']").text());
        var saleorderGoodsId = $(this).children("td[name='saleorderGoodsId']").text();
        var totalAmount_id = "totalAmount_" + saleorderGoodsId;
        var totalAmount = parseFloat($("#" + totalAmount_id).text());
        var alreadyNum = parseFloat($(this).children("td[name='alreadyNum']").text());
        var invoicedNum = parseFloat($(this).children("td[name='invoicedNum']").text());
        var invoicedAmount = parseFloat($(this).children("td[name='invoicedAmount']").text());
        var appliedAmount = parseFloat($(this).children("td[name='appliedAmount']").text());

        var applyAmount = 0;
        if (applyNum >= maxNum){
            //用总额 - 已开票金额
            applyAmount = totalAmount - appliedAmount - invoicedAmount;
        } else {
            applyAmount =applyNum * price;
        }
        console.log("---applyAmount:" + applyAmount.toFixed(2));
        $(this).children("td[name='applyAmount']").text(applyAmount.toFixed(2));
    });
}

//联动刷新统计信息(本次开票申请数量 本次开票申请金额)
function freshTotalAmount(){
    var totalNum = 0;
    var totalPrice = 0;
    $("#table_items").children("tr[name='data_tr']").each(function (i) {
        var td = $(this).children("td[name='td_nums']");
        var applyNum = parseInt($(td).children("input[name='applyNum']").val());
        var price = parseInt($(this).children("td[name='price']").text());
        var goodsTotalPrice = parseFloat($(this).children("td[name='applyAmount']").text());
        totalNum = applyNum + totalNum;
        totalPrice = goodsTotalPrice + totalPrice;
    });

    $("#apply_totalAmount").text(totalPrice.toFixed(2));
    $("#apply_totalNum").text(totalNum);
    console.log("totalPrice:"+ totalPrice.toFixed(2));
}


//计算是否为提前开票 20191210 增加  是否提前开票逻辑按照已选商品进行判断而非整个订单
function isAdvanceChange(){
    var flag = false;
    $("#table_items").children("tr[name='data_tr']").each(function (i) {
        var td = $(this).children("td[name='td_nums']");
        var canInvoicedNum = parseInt($(this).children("td[name='canInvoicedNum']").text());
        var alreadyNum = parseInt($(this).children("td[name='alreadyNum']").text());
        var selectNum = parseInt($(td).children("input[name='applyNum']").val());
        console.log("canInvoicedNum:" + canInvoicedNum);
        console.log("selectNum:" + selectNum);

        if (selectNum > (canInvoicedNum - alreadyNum)) {
            flag = true;

        }
    });

    if (flag) {
        $("#isAdvanceTax").val(1);
        $("#div_advance_comments").show();
        $("#div_comments").hide();
    } else {
        $("#isAdvanceTax").val(0);
        $("#div_advance_comments").hide();
        $("#div_comments").show();
    }
}