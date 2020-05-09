//金额变动验证
function check_pay(obj,residue_money,checkout_money,now_money){
	var reg1 = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	if($(obj).val().length>0 && !reg1.test($(obj).val())){
		layer.alert("结款金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
		$(obj).val(now_money);
	}
	if($(obj).val()>residue_money || $(obj).val()>checkout_money){
		if($(obj).val()<=residue_money && $(obj).val()>checkout_money){
			layer.confirm("超出可结款金额确认要结款？", {
				  btn: ['确定','取消'] //按钮
				}, function(index){
					layer.close(index);
				}, function(index){
					$(obj).val(now_money);	
			});
			
		}else{
			layer.alert("超出可结款金额");
			$(obj).val(now_money);	
		}
	}
}
//异步忽略操作
function ignoreadd(obj,id,type){
	checkLogin();
	var matchedObject = $("#matchedObject").val();
	$.ajax({
		async : false,
		url : page_url + '/finance/bankbill/editBankBill.do',
		data : {
			"bankBillId" : id,
			"status":1,
			"matchedObject" : matchedObject
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
					parent.layer.close(index);
					parent.location.reload();
			}else{
				layer.alert(data.message, { icon : 2});
			}
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}
//手动匹配确认结款
function salemoneyaddorhand(id){
	checkLogin();
	if($("input[name='checked']:checked").length ==0){
		layer.alert("请选择需要结款的订单");
		return false;
	}
	var lock = false;
	layer.confirm('确认结款?', {title:'操作确认'},function(index){

		var formToken = $("input[name='formToken']").val();
		var payer = $("input[name='checked']:checked").parent().nextAll().find("input[name='payer']").val();
		var comments = $("input[name='checked']:checked").parent().nextAll().find("input[name='comments']").val();
		var saleorderId = $("input[name='checked']:checked").parent().nextAll().find("input[name='saleorderId']").val();
		var receivedAmount = $("input[name='checked']:checked").parent().nextAll().find("input[name='receivedAmount']").val();
		var amount = $("input[name='checked']:checked").parent().nextAll().find("input[name='amount']").val();
		var traderSubject = $("input[name='checked']:checked").parent().nextAll().find("select[name='traderSubject']").val();
		if (payer.length > 128 ){
			layer.alert("拟结款名称不允许超过128个字符");
			return false;
		}
		if(payer.length  == 0){
			layer.alert("拟结款名称不允许为空");
			return false;
		}
		if (comments.length > 512 ){
			layer.alert("备注不允许超过512个字符");
			return false;
		}
		if(!lock) {
			lock = true;
			$.ajax({
				async : false,
				url : page_url + '/finance/bankbill/addCapitalBill.do',
				data : {
					"bankBillId" : id,
					"saleorderId" : saleorderId,
					"payer":payer,
					"amount":amount,
					"comments" : comments,
					"traderSubject":traderSubject,
					"receivedAmount":receivedAmount,
					"formToken":formToken
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.code == 0){
							window.parent.layer.close(index);
							window.parent.location.reload();
					}else{
						layer.alert(data.message, { icon : 2});
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
		
		
		  
		  layer.close(index);
		}); 
}

function matchBankBill(bankBillId,payApplyId,tranFlow,taskId,bankTag,payType){
	layer.confirm('确认匹配?', {title:'操作确认'},function(index){
			var paymentType = 0;
			if(bankTag == 2){
				//南京银行
				paymentType = 641;
			}else if(bankTag == 3){
				//中国银行
				paymentType = 716;
			}
			var formToken = $("input[name='formToken']").val();
			if(payType == 517){
				var url = page_url + '/finance/buyorder/payApplyPassByBankBill.do';
			}else{
				var url = page_url + '/finance/capitalbill/saveAddAfterCapitalBillForBank.do';
			}
			$.ajax({
				async : false,
				url : url,
				data : {
					"payApplyId" : payApplyId,
					"bankBillId" : bankBillId,
					"tranFlow" : tranFlow,
					"taskId":taskId,
					"paymentType":paymentType,
					"formToken":formToken
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.code == 0){
							layer.alert(data.message, { icon : 1});
							window.location.reload();
					}else{
						layer.alert(data.message, { icon : 2});
						window.location.reload();
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		  layer.close(index);
		}); 
}

//确认借款
function salemoneyadd(id,saleorderId,receivedAmount,obj){
	checkLogin();
	var lock = false;
	layer.confirm('确认结款?', {title:'操作确认'},function(index){
		var formToken = $("input[name='formToken']").val();
		var payer = $(obj).parent().prevAll().find("input[name='payer']").val();
		var comments = $(obj).parent().prevAll().find("input[name='comments']").val();
		var amount = $(obj).parent().prevAll().find("input[name='amount']").val();
		var traderSubject = $(obj).parent().prevAll().find("select[name='traderSubject']").val();
		if (payer.length > 128 ){
			layer.alert("拟结款名称不允许超过128个字符");
			return false;
		}
		if(payer.length  == 0){
			layer.alert("拟结款名称不允许为空");
			return false;
		}
		if (comments.length > 512 ){
			layer.alert("备注不允许超过512个字符");
			return false;
		}
		
		if(!lock) {
			lock = true;
			$.ajax({
				async : false,
				url : page_url + '/finance/bankbill/addCapitalBill.do',
				data : {
					"bankBillId" : id,
					"saleorderId" : saleorderId,
					"payer":payer,
					"amount":amount,
					"comments" : comments,
					"traderSubject":traderSubject,
					"receivedAmount":receivedAmount,
					"formToken":formToken
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.code == 0){
							layer.alert(data.message, { icon : 1});
							window.location.reload();
					}else{
						layer.alert(data.message, { icon : 2});
						window.location.reload();
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			})
		}
		  layer.close(index);
		}); 
}
function searchReset() {
	$("form").find("input[type='text']").val('');
	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});
	//交易时间重置默认
	var nowDate = $("form").find("input[name='nowDate']").val();
	$("form").find("input[name='beginTime']").val(nowDate);
	$("form").find("input[name='endTime']").val(nowDate);
}
function noEmpty(obj,val){
	if($(obj).val() == ""){
		$(obj).val(val);
	}
}
function search() {
	if($("input[name='search']").val() == ""){
		layer.alert("请输入关键词");
		return false;
	}
	$("#search").submit();
}

function manualMatchInfo(obj,bankBillId){
	$("#terminalDiv")
	.attr(
			'layerParams',
			'{"width":"70%","height":"80%","title":"手动匹配","link":"'
					+ page_url
					+ '/finance/bankbill/getManualMatchInfo.do?bankBillId='
					+ bankBillId + '"}');
	$("#terminalDiv").click();
	
}
//手动匹配付款
function manualMatchPayInfo(obj,bankBillId){
	$("#terminalDiv")
	.attr(
			'layerParams',
			'{"width":"70%","height":"80%","title":"手动匹配","link":"'
					+ page_url
					+ '/finance/bankbill/getManualMatchPayInfo.do?bankBillId='
					+ bankBillId + '"}');
	$("#terminalDiv").click();
	
}

//手动匹配确认结款
function paymoneyaddorhand(bankBillId,tranFlow,bankTag,residueAmount){
	checkLogin();
	if($("input[name='checked']:checked").length ==0){
		layer.alert("请选择需要付款的订单");
		return false;
	}
	var lock = false;
	var amount = $("input[name='checked']:checked").parent().nextAll().find("input[name='amount']").val();
	if(amount*1>residueAmount*1){
		layer.alert("付款申请金额不能大于银行流水剩余金额");
		return false;
	}
	layer.confirm('确认匹配?', {title:'操作确认'},function(index){
		var paymentType = 0;
		if(bankTag == 2){
			//南京银行
			paymentType = 641;
		}else if(bankTag == 3){
			//中国银行
			paymentType = 716;
		}
		var formToken = $("input[name='formToken']").val();
		var payApplyId = $("input[name='checked']:checked").parent().nextAll().find("input[name='payApplyId']").val();
		var taskId = $("input[name='checked']:checked").parent().nextAll().find("input[name='taskId']").val();
		var payType = $("input[name='checked']:checked").parent().nextAll().find("input[name='payType']").val();
		if(payType == 517){
			var url = page_url + '/finance/buyorder/payApplyPassByBankBill.do';
		}else{
			var url = page_url + '/finance/capitalbill/saveAddAfterCapitalBillForBank.do';
		}
		$.ajax({
			async : false,
			url : url,
			data : {
				"payApplyId" : payApplyId,
				"bankBillId" : bankBillId,
				"tranFlow" : tranFlow,
				"taskId":taskId,
				"paymentType":paymentType,
				"formToken":formToken
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
						layer.alert(data.message, { icon : 1});
						window.location.reload();
				}else if(data.code==1){
					layer.alert("该流水已经生成");
					window.location.reload();
				}

				else{
					layer.alert(data.message, { icon : 2});
					window.location.reload();
				}
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	  layer.close(index);
	});
 
}



// 批量确认
function numbersConfirm(){
	// 获取所有选中的单选框
	var checkedObj = $(".check_box_checked:checked");
	if(checkedObj && checkedObj.length > 0){
		// 再次确认是否批量操作
		layer.confirm('您确定要批量操作吗?', {title:'操作确认'},function(index){
				layer.close(index);
				// 循环
				for (var i = 0; i < checkedObj.length; i++) {
					// 找到被选中的单选框
					if($(checkedObj[i]).is(":checked")){
						// 银行流水id
						var bankBillId = $(checkedObj[i]).val();
						// 付款申请id 
						var payApplyId = $(checkedObj[i]).attr("payapplyid");
						// 流水
						var tranFlow = $(checkedObj[i]).attr("tranflow");
						// taskId
						var taskId = $(checkedObj[i]).attr("taskinfopayid");
						// 银行标识
						var bankTag = $(checkedObj[i]).attr("banktag");
						// 支付类型
						var payType = $(checkedObj[i]).attr("paytype");
						//token
						var paymentType = 0;
						if(bankTag == 2){
							//南京银行
							paymentType = 641;
						}else if(bankTag == 3){
							//中国银行
							paymentType = 716;
						}
						if(payType == 517){
							var url = page_url + '/finance/buyorder/payApplyPassByBankBillNoFormToken.do';
						}else{
							var url = page_url + '/finance/capitalbill/saveAddAfterCapitalBillForBankNoFormToken.do';
						}
						$.ajax({
							async : false,
							url : url,
							data : {
								"payApplyId" : payApplyId,
								"bankBillId" : bankBillId,
								"tranFlow" : tranFlow,
								"taskId":taskId,
								"paymentType":paymentType,
								"formToken":$("input[name='formToken']").val()
							},
							type : "POST",
							dataType : "json",
							// 成功不做操作
							success : function(data) {
								if(data.code == 0){
									layer.close(index);
								}if(data.code == 1){

									layer.alert("该流水已经生成");
									window.location.reload();
								}

								else{
									console.log("操作失败");
								}

							},
							// 操作失败
							error:function(data){
								if(data.status ==1001){
									layer.alert("当前操作无权限")
								}
							}
						})
					}
				}
				layer.close(index);
				// 页面重新加载
				window.location.reload();
		});
	}
	// 如果没有选中的单选框
	else{
		layer.alert("当前无选中的按钮");
	}
	
}













