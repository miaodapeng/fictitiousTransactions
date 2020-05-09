function colse(){//关闭
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/order/buyorder/saveCloseAfterSales.do',
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

function editAfterSales(){//编辑
	checkLogin();
	$("#myform").attr("action", page_url + "/order/buyorder/editAfterSalesPage.do");
	$("#myform").submit();
}

//售后实退金额小于0，需要付款
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

function applyAudit(){//申请审核
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url:page_url+'/order/buyorder/editApplyAudit.do',
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

function passAudit(){//审核通过
	checkLogin();
	var type = $("input[name='type']").val();
	var msg ="";
	if(type ==546){
		msg ="是否确认审核通过？您需要确认退货手续费及发票是否需寄回。";
	}else if(type ==547){
		msg ="是否确认审核通过？您需要确认换货手续费及发票是否需寄回。";
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

//执行退款运算
function executeRefundOperation(){
	checkLogin();
	index = layer.confirm("是否确认当前操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
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
}
