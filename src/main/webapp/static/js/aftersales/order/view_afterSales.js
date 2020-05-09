
//新增ajax补充录音字段的coidURI
function checkcoidURI(recordId){

	$.ajax({
		type: "POST",
		url: page_url + "/aftersales/order/getRecordCoidURI.do",
		data:{"recordId":recordId},
		dataType:'json',
		success: function(data){
			if(data.code==0){
				playrecord(data.data);
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
		}
	});
}
function lendout() {
	layer.alert("存在未完成的外借单，无法 操作");
}
function colse(){//关闭
	checkLogin();
	index = layer.confirm("您是否确认该订单关闭？确认后无法操作、无法更改状态！", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/order/saleorder/saveCloseAfterSales.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});
}
//直发确认收货
function updateArrival(afterSalesGoodsId,arrivalNum){
	checkLogin();
	index = layer.confirm("请确认当前产品是否已全部收货？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/aftersales/order/updateAfterSalesGoodsArrival.do',
				data:{'afterSalesGoodsId':afterSalesGoodsId,'arrivalNum':arrivalNum},
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});
	
}

function editAfterSales(num){//编辑
	checkLogin();
	if(num == 1){
		$("#myform").attr("action", page_url + "/order/saleorder/editAfterSalesPage.do");
	}else if(num==2){
		$("#myform").attr("action", page_url + "/order/buyorder/editAfterSalesPage.do");
	}else{
		$("#myform").attr("action", page_url + "/aftersales/order/editAfterSalesPage.do");
	}
	$("#myform").submit();
}

function applyAudit(flag){//申请审核
	checkLogin();
    if (flag == 1){
        alert("请先完善退货信息！");
        return;
    }
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){

			$.ajax({
				url:page_url+'/order/saleorder/editApplyAudit.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});
}

function contractReturnDel(attachmentId){
	checkLogin();
	layer.confirm("确认删除该条合同回传吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/aftersales/order/contractReturnDel.do",
				data: {'attachmentId':attachmentId},
				dataType:'json',
				success: function(data){
					self.location.reload();
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

function contractReturnDel2(attachmentId){
	checkLogin();
	layer.confirm("确认删除该条退票材料吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: page_url+"/aftersales/order/contractReturnDel.do",
				data: {'attachmentId':attachmentId},
				dataType:'json',
				success: function(data){
					self.location.reload();
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


function delAfterTrader(afterSalesTraderId){
	checkLogin();
	layer.confirm("确认删除该条记录吗？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./delAfterTrader.do",
				data: {'afterSalesTraderId':afterSalesTraderId},
				dataType:'json',
				success: function(data){
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

function passAudit(){//审核通过
	checkLogin();
	var type = $("input[name='type']").val();
	var msg ="";
	if(type ==539){
		msg ="是否确认审核通过？您需要确认退货手续费及发票是否需寄回。";
	}else if(type ==540){
		msg ="是否确认审核通过？您需要确认换货手续费及发票是否需寄回。";
	}else if(type ==541){
		msg ="是否确认审核通过？您需要确认安调手续费及发票是否需寄回。";
	}else if(type ==584){
		msg ="是否确认审核通过？您需要确认维修手续费及发票是否需寄回。";
	}else{
		msg ="是否确认审核通过？";
	}
	index = layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/aftersales/order/editPassAudit.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});

}

function confirmComplete(){//确认完成
	checkLogin();
	index = layer.confirm("您是否确认该订单已完成？确认后无法操作、无法更改状态！", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/aftersales/order/editConfirmComplete.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});

}

//售后实退金额大于0，申请退款
function applyPay(){
	checkLogin();
	index = layer.confirm("是否确认当前操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/aftersales/order/saveRealRefundAmountApplyPay.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						self.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
	layer.close(index);
		}, function(){
		});
}

//执行退款运算
function executeRefundOperation(){
	checkLogin();
	var type = $("input[name='type']").val();
	if(type == 539){
		var afterSalesId = $("input[name='afterSalesId']").val();
		index = layer.confirm("是否确认当前操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				// changed by Tomcat.Hui 2020/3/10 5:11 下午 .Desc: . .

				$.ajax({
					url:page_url+'/aftersales/order/executeRefundOperation.do',
					data:$('#myform').serialize(),
					type:"POST",
					dataType : "json",
					async: false,
					success:function(data)
					{
						if(data.code==0){
							self.location.reload();
						}else{
							layer.alert(data.message);
						}

					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限");
						}
					}
				});
				return false;
		layer.close(index);
			}, function(){
			});
	}else{
		index = layer.confirm("是否确认当前操作？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
			// changed by Tomcat.Hui 2020/3/10 5:11 下午 .Desc: VDERP-1850 【2月】退货售后单执行退款运算时，退票的校验条件修改 去掉原有限制 .
			$.ajax({
					url:page_url+'/aftersales/order/executeRefundOperation.do',
					data:$('#myform').serialize(),
					type:"POST",
					dataType : "json",
					async: false,
					success:function(data)
					{
						if(data.code==0){
							self.location.reload();
						}else{
							layer.alert(data.message);
						}
						
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限");
						}
					}
				});
			// changed by Tomcat.Hui 2020/3/10 5:11 下午 .Desc: VDERP-1850 【2月】退货售后单执行退款运算时，退票的校验条件修改 去掉原有限制 .
			return false;
		layer.close(index);
			}, function(){
			});
	}

}
//	删除售后费用类型
	function delectCostTypeList(afterSalesCostId){
		checkLogin();
		layer.confirm("确认删除该售后费用记录吗？", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: page_url+"/aftersales/order/delectAfterSalesCost.do",
					data: {'afterSalesCostId':afterSalesCostId},
					dataType:'json',
					success: function(data){
						self.location.reload();
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

	function editError(){
		layer.alert('实退金额为0，无法修改退款信息');
	}
	
	function editAfterSaleOrder() {
	var url = '';
	var typeFlag = $('#typeFlag').val();
	if (typeFlag == 0){
		url =  './editAfterSalesApplyPage.do'
	} else if (typeFlag == 1) {
		url = './editAfterSalesDetail.do';
	}
		$.ajax({
			type: 'POST',
			url: url,
			data:$('#editAfterSaleOrderForm').serialize(),
			dataType : "html",
			success: function(result) {
				var htmlCont = result;
				var open = layer.open({
					type: 1,
					title: '编辑',
					shadeClose: false,
					area : ['750px', '330px'],
					content: htmlCont,
					success: function(layero, index){

					}
				});
				$('#layerIndex').val(open);
			}
		});
	}

