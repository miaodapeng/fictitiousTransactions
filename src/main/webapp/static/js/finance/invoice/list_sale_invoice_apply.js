
function resetPage(){
	reset();
	$("#validStatus").val(0);
}
function saveOpenInvoiceAudit(invoiceApplyId,status){
	layer.confirm("您是否确认审核不通过？", {
		btn : [ '确定', '取消' ]
	}, function() {
		checkLogin();
		$.ajax({
			async : true,
			url : './saveOpenInvoiceAudit.do',
			data : {"invoiceApplyId":invoiceApplyId,"validStatus":status},
			type : "POST",
			dataType : "json",
			success : function(data) {
				refreshNowPageList(data);
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});
}
function exportInvoiceApplyList(){
	checkLogin();
	location.href = page_url + '/report/finance/exportInvoiceApplyList.do?' + $("#search").serialize();
}

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

function checkedOnly(obj){
    //$(obj).attr("disabled",true)
    //setTimeout(function(){$(obj).attr("disabled",false)}, 1500);
	var checkNum = Number($("#checkNum").html());
    var checkAmount = parseFloat($("#checkAmount").html());
    if($(obj).is(":checked")){
        var n = 0;
        $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']").each(function(){
            if($(this).is(":checked")){
                n++;
            }else{
                return false;
            }
        });
        if($("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']").length == n){
            $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkAllOpt']").prop("checked",true);
        }
        checkNum++;
        checkAmount = checkAmount + parseFloat($(obj).attr("amount"));
    }else{
        $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkAllOpt']").prop("checked",false);
        checkNum--;
        checkAmount = checkAmount - parseFloat($(obj).attr("amount"));
    }
    $("#checkNum").html(checkNum);
    $("#checkAmount").html(checkAmount.toFixed(2));
}

function checkAllOpt(obj){
    var checkNum = 0;
    var checkAmount = 0.00;
    if($(obj).is(":checked")){
        $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']").each(function(){
            $(this).prop("checked",true);
            checkNum++;
            checkAmount = checkAmount + parseFloat($(this).attr("amount"));
        });
    }else{
        $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']").each(function(){
            $(this).prop("checked",false);
        });
    }
    $("#checkNum").html(checkNum);
    $("#checkAmount").html(checkAmount.toFixed(2));
}

function invoiceSign(sign) {
    var invoiceApplyIdArr = [];var num = 0;
    $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']:checked").each(function(){
        if($(this).is(":checked")){
            invoiceApplyIdArr.push($(this).val());
        }
        if($(this).attr("isSign") == sign) {
        	num++;
        }
    });
    if(num > 0){
        var str = "";
        if(sign == 0){
            str = "已选择的记录中存在未标记发票，请验证！";
        } else {
            str = "已选择的记录中存在已标记发票，请验证！"
        }
		layer.alert(str, {icon: 2},
			function (index) {
				layer.close(index);
			}
		);
		return false;
	}
    if(invoiceApplyIdArr.length == 0){
        var str = "";
        if(sign == 0){
            str = "请选择需要取消标记的发票记录！";
        } else {
            str = "请选择需要标记的发票记录！"
        }
        layer.alert(str, {icon: 2},
            function (index) {
                layer.close(index);
            }
        );
        return false;
    }
    var str = "";
    if(sign == 0){
        str = "您是否确认取消标记已选择的发票记录？";
    } else {
        str = "您是否确认标记已选择的发票记录？"
    }
    layer.confirm(str, {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            async : false,
            url : './updateInvoiceApplySign.do',
            data : {"invoiceApplyIdArr":JSON.stringify(invoiceApplyIdArr),"isSign":sign},
            type : "POST",
            dataType : "json",
            success : function(data) {
                refreshNowPageList(data);
            }
        });
    });
}

function invoiceBatch(formToken){
    var invoiceApplyIdArr = [];var num = 0;
    $("#invoice_apply_list_tab").find("input[type='checkbox'][name='checkName']:checked").each(function(){
        if($(this).is(":checked")){
            invoiceApplyIdArr.push($(this).val());
        }
        if($(this).attr("isAuto") != 3) {
            num++;
        }
    });
    if(num > 0){
        var str = "已选择的记录中存在部分不能开电子发票，请验证！";
        /*if(sign == 0){
            str = "已选择的记录中存在未标记发票，请验证！";
        } else {
            str = "已选择的记录中存在已标记发票，请验证！"
        }*/
        layer.alert(str, {icon: 2},
            function (index) {
                layer.close(index);
            }
        );
        return false;
    }
    if(invoiceApplyIdArr.length == 0){
        var str = "请选择需要打印的发票记录";
        /*if(sign == 0){
            str = "请选择需要取消标记的发票记录！";
        } else {
            str = "请选择需要标记的发票记录！"
        }*/
        layer.alert(str, {icon: 2},
            function (index) {
                layer.close(index);
            }
        );
        return false;
    }
    var str = "您是否确认批量开票";
    var lock = false;//默认未锁定
    /*if(sign == 0){
        str = "您是否确认取消标记已选择的发票记录？";
    } else {
        str = "您是否确认标记已选择的发票记录？"
    }*/
    layer.confirm(str, {
        btn : [ '确定', '取消' ]
    }, function() {
        if (!lock) {
            lock = true;// 锁定
            $.ajax({
                type: "POST",
                url: "./openEInvoiceBatchPush.do",
                data: {"invoiceApplyIdArr": JSON.stringify(invoiceApplyIdArr), "formToken": formToken},
                dataType: 'json',
                success: function (data) {
                    refreshPageList(data)
                },
                error: function (data) {
                    if (data.status == 1001) {
                        layer.alert("当前操作无权限")
                    }
                }

            });
        }
    });
}