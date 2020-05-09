function updateTerminal(searchTraderName) {
	checkLogin();
	$("#updateTerminalDiv").attr(
			'layerParams',
			'{"width":"60%","height":"60%","title":"编辑终端","link":"' + page_url + '/trader/customer/getTerminalList.do?optType=editQuoteTerminal"}');
//			'{"width":"60%","height":"60%","title":"编辑终端","link":"' + page_url + '/trader/customer/getTerminalList.do?searchTraderName=' + searchTraderName + '&optType=editQuoteTerminal"}');
//	$("#updateTerminalDiv").click();
}

function searchTerminal(){
	checkLogin();
//	$("#updateTerminalInfo").find("#errorMes").removeClass("errorbor");
	delWarnTips("errorTxtMsg");
	
	var searchTraderName = $("#updateTerminalInfo").find("#searchTraderName").val()==undefined?"":$("#updateTerminalInfo").find("#searchTraderName").val();
/*	if(searchTraderName==""){
		warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
		return false;
	}*/
	$("#updateTerminalInfo").find("#terminalDiv").attr('layerParams','{"width":"70%","height":"80%","title":"报价终端","link":"'+ page_url+'/trader/customer/getTerminalList.do?searchTraderName=' + encodeURI(searchTraderName) + '&optType=addQuoteTerminal"}');
	$("#updateTerminalInfo").find("#terminalDiv").click();
}

function agingSearchTerminal(){
	checkLogin();
	$("#searchTraderName").val("");
	$("#terminalTraderNameDiv").html("");
	$("#quotePayMoneForm").find(".terminal").each(function(i){
		$(this).val("");
	});
	$("#terminalNameDetail").hide();
	$("#terminalNameCheck").show();
}


function delQuoteGoods(quoteId,quoteGoodsId){
	checkLogin();
	layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if($("#quotePayMoneForm").find("#quoteCustomerNature").val() == "465"){//客户为分销
				//终端信息
				var terminalTraderName = "";var terminalTraderId = "";var terminalTraderType ="";var salesArea = "";var salesAreaId = "";
				if($("#quotePayMoneForm").find("#terminalTraderName") != undefined && $("#quotePayMoneForm").find("#terminalTraderName").val().length > 0){
					terminalTraderName = $("#quotePayMoneForm").find("#terminalTraderName").val();
					terminalTraderId = $("#quotePayMoneForm").find("#terminalTraderId").val();
					terminalTraderType = $("#quotePayMoneForm").find("#terminalTraderType").val();
				}else{
					terminalTraderName = $("#updateTerminalInfo").find("#searchTraderName").val().trim();
				}
				var salesArea = ($("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#province :selected").text()) + " " 
				+ ($("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#city :selected").text()) + " " 
				+ ($("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#zone :selected").text());
				var province = $("#updateTerminalInfo").find("#province :selected").val()=="0"?"":$("#updateTerminalInfo").find("#province :selected").val();
				var city = $("#updateTerminalInfo").find("#city :selected").val();
				var zone = $("#updateTerminalInfo").find("#zone :selected").val();
				salesAreaId = (zone=="0"?(city=="0"?province:city):zone);
				/*$("#confirmForm").find("#terminalTraderName").val(terminalTraderName);
				$("#confirmForm").find("#terminalTraderId").val(terminalTraderId);
				$("#confirmForm").find("#terminalTraderType").val(terminalTraderType);
				$("#confirmForm").find("#salesArea").val(salesArea);
				$("#confirmForm").find("#salesAreaId").val(salesAreaId);*/
			}
			
			$.ajax({
				type: "POST",
				url: "./delQuoteGoodsById.do",
				data: {'quoteorderId':quoteId,'quoteorderGoodsId':quoteGoodsId
						,'terminalTraderId':terminalTraderId,'terminalTraderName':terminalTraderName,'terminalTraderType':terminalTraderType
						,'salesArea':salesArea,'salesAreaId':salesAreaId},
				dataType:'json',
				success: function(data){
					if(data.code==0){
						$("#paymentType").val("419");
						layer.alert(data.message, {
							icon : 1
						}, function(index) {
							layer.close(index);
							location.reload();
						});
					}else{
						layer.alert(data.message, {	icon : 2});
					}
					/*layer.alert(data.message, {
						icon : (data.code == 0 ? 1 : 2)
					}, function() {
						if (parent.layer != undefined) {
							parent.layer.close(index);
						}
						location.reload();
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

function totleMoney(obj){
	checkLogin();
	$("form :input").parents('li').find('.warning').remove();
	
	$("#prepaidAmount").val("");$("#accountPeriodAmount").val("");$("#retainageAmount").val("");
	$("#prepaidAmount").val(0.00);
	$("#accountPeriodAmount").val(0.00);
	$("#retainageAmount").val(0.00);
	var num = $(obj).val();
	if(num!="0"){
		if(num=="419"){
			$("#accountPeriodLi").hide();
			$("#retainageLi").hide();
			
			$("#accountAmountId").hide();
		}else if(num=="424"){
			$("#accountPeriodLi").show();
			$("#retainageLi").show();
			
			$("#accountAmountId").show();
		}else{
			$("#accountPeriodLi").show();
			$("#retainageLi").hide();
			
			$("#accountAmountId").show();
		}
		
		var totleMoney= $("#goodsTotleMoney").val();
		switch (num) {
		case "419":
			$("#prepaidAmount").removeAttr("readonly");
			$("#prepaidAmount").val(Number(totleMoney).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			break;
		case "420":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			$("#prepaidAmount").val((Number(totleMoney)*0.8).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.2).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "421":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			$("#prepaidAmount").val((Number(totleMoney)*0.5).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.5).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "422":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			$("#prepaidAmount").val((Number(totleMoney)*0.3).toFixed(2));
			$("#accountPeriodAmount").val((Number(totleMoney)*0.7).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "423":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			$("#accountPeriodAmount").val(Number(totleMoney).toFixed(2));
			$("#prepaidAmount").attr("readonly",true);
			$("#accountPeriodAmount").attr("readonly",true);
			break;
		case "424":
			$("#prepaidAmount").removeAttr("readonly");
			$("#accountPeriodAmount").removeAttr("readonly");
			break;
		default:
			
		}
	}else{
		var totleMoney= $("#goodsTotleMoney").val();
		$("#prepaidAmount").val(Number(totleMoney).toFixed(2));
		$("#prepaidAmount").attr("readonly",true);
	}
	return false;

}

function quotePayMoneSub(){
	checkLogin();
	var $form = $("#quotePayMoneForm");
	//-----------------------------------------终端验证--------------------------------------------------------
	if($form.find("#terminalTraderName").val() == undefined || $form.find("#terminalTraderName").val().trim().length == 0){
		$form.find("#terminalTraderName").val($("#searchTraderName").val());
	}
	if($("#quoteorderId").val() != undefined && $("#quoteorderId").length > 0){
		var salesArea = ($("#updateTerminalInfo").find("#province :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#province :selected").text()) + " " 
		+ ($("#updateTerminalInfo").find("#city :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#city :selected").text()) + " " 
		+ ($("#updateTerminalInfo").find("#zone :selected").text()=="请选择"?"":$("#updateTerminalInfo").find("#zone :selected").text());
		$form.find("#salesArea").val(salesArea);
		var province = $("#updateTerminalInfo").find("#province :selected").val()=="0"?"":$("#updateTerminalInfo").find("#province :selected").val();
		var city = $("#updateTerminalInfo").find("#city :selected").val();
		var zone = $("#updateTerminalInfo").find("#zone :selected").val();
		$form.find("#salesAreaId").val(zone=="0"?(city=="0"?province:city):zone);
	}
	//-----------------------------------------终端验证--------------------------------------------------------
	$("form :input").parents('li').find('.warning').remove();
	$form.find("#logisticsCollection").val($form.find("#logisticsCheckBox").is(":checked")==true?1:0);
	var quoteorderId = $form.find("#quoteorderId").val();
	
	var num = $form.find("#paymentType").val();
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	 
	if(num=="424"){//自定义
		var prepaidAmount = $("#prepaidAmount").val();
		if(prepaidAmount.length > 14){
			warnTips("prepaidAmountError","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		
		if(prepaidAmount!=""){
			if(!reg.test(prepaidAmount)){
				warnTips("prepaidAmount","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
		/*}else{
			warnTips("prepaidAmount","预付金额必须大于0");//文本框ID和提示用语
			return false;*/
		}
		
		var accountPeriodAmount = $("#accountPeriodAmount").val();
		if(accountPeriodAmount.length > 14){
			warnTips("accountPeriodAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		
		if(accountPeriodAmount!=""){
			if(!reg.test(accountPeriodAmount)){
				warnTips("accountPeriodAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}/*else if(Number(accountPeriodAmount)<=0){
				warnTips("accountPeriodAmountError","账期支付必须大于0");//文本框ID和提示用语
				return false;
			}*/
			var accountPeriodLeft = Number($("#accountPeriodLeft").val());//账期最大限额
			if(Number(accountPeriodAmount) > accountPeriodLeft){
				warnTips("accountPeriodAmountError","帐期支付金额超过帐期剩余额度:"+accountPeriodLeft);//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("accountPeriodAmountError","账期支付金额不允许小于0");//文本框ID和提示用语
			return false;
		}
		
		var retainageAmount = $("#retainageAmount").val();
		if(retainageAmount.length > 14){
			warnTips("retainageAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		if(retainageAmount!=""){
			if(!reg.test(retainageAmount)){
				warnTips("retainageAmountError","金额输入错误！仅允许使用大于零的数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}/*else if(Number(retainageAmount)<=0){
				warnTips("retainageAmountError","尾款必须大于0");//文本框ID和提示用语
				return false;
			}*/else if(Number(retainageAmount) > Number($("#goodsTotleMoney").val())*0.1){
				warnTips("retainageAmountError","尾款不允许超过合同金额10%");//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("retainageAmountError","尾款不允许小于0");//文本框ID和提示用语
			return false;
		}
		var retainageAmountMonth = $("#retainageAmountMonth").val();
		if(retainageAmountMonth.length>0){
			var re = /^[0-9]+$/ ;
			if(retainageAmountMonth=="0" || !re.test(retainageAmountMonth)){
				warnTips("retainageAmountError","尾款期限必须为大于0的整数");//文本框ID和提示用语
				return false;
			}else if(Number(retainageAmountMonth) > 24){
				warnTips("retainageAmountError","尾款期限不允许超过24个月");//文本框ID和提示用语
				return false;
			}
		}
		
		if(prepaidAmount!="" && accountPeriodAmount!="" && retainageAmount!=""){
			var goodsTotleMoney = $("#goodsTotleMoney").val();
			if(Number(prepaidAmount) + Number(accountPeriodAmount) + Number(retainageAmount) != Number(goodsTotleMoney)){
				warnTips("retainageAmountError"," 支付金额总额与总金额不符");//文本框ID和提示用语
				return false;
			}
		}
	}else if(num!="419"){//419先货后款，预付100%
		var accountPeriodAmount = $("#accountPeriodAmount").val();
		if(accountPeriodAmount.length > 14){
			warnTips("accountPeriodAmount","金额输入错误！长度应该在1-12个数字之间");//文本框ID和提示用语
			return false;
		}
		
		if(accountPeriodAmount!=""){
			if(!reg.test(accountPeriodAmount)){
				warnTips("accountPeriodAmountError","金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}
			var accountPeriodLeft = Number($("#accountPeriodLeft").val());//账期最大限额
			if(Number(accountPeriodAmount) > accountPeriodLeft){
				warnTips("accountPeriodAmountError","帐期支付金额超过帐期剩余额度:"+accountPeriodLeft);//文本框ID和提示用语
				return false;
			}
		}else{
			warnTips("accountPeriodAmountError","账期支付金额必须大于0");//文本框ID和提示用语
			return false;
		}
	}
	
	var period = $("#period").val().trim();
	if (period.length==0) {
		warnTips("errorMsg","报价有效期不能为空");//文本框ID和提示用语
		return false;
	}else{
		var re = /^[0-9]+$/ ;
		if(period=="0" || !re.test(period)){
			warnTips("errorMsg","报价有效期必须为正整数");//文本框ID和提示用语
			return false;
		}else if(Number(period)>30){
			warnTips("errorMsg","报价有效期不允许超过30天");//文本框ID和提示用语
			return false;
		}
	}
	
	var additionalClause = $("#additionalClause").val();
	if(additionalClause.length > 256){
		warnTips("additionalClause","附加条款长度应该在0-256个字符之间");//文本框ID和提示用语
		return false;
	}
	
	var comments = $("#comments").val();
	if(comments.length > 1024){
		warnTips("comments","内部备注长度应该在0-1024个字符之间");//文本框ID和提示用语
		return false;
	}
	
	$form.submit();
	
	return false;
}

function updateQuoteValId(quoteorderId){
	checkLogin();
	layer.confirm("您是否确认生效该报价？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		//验证报价单中产品货期和报价是否为空
		$.ajax({
			async : false,
			url : './getQuoteGoodsPriceAndCycle.do',
			data : {"quoteorderId":quoteorderId},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					$("#editQuoteOperationForm").submit();
				}else if(data.code == 1){
					layer.confirm(data.message, {
						  btn: ['确认','取消']
						}, function(i){
							$("#editQuoteOperationForm").submit();
						}, function(j){
							
						});
				}else{
					layer.alert(data.message, {icon : 2});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		
	});
}

function sendQuoteConsult(quoteorderNo,quoteorderId,num){
	checkLogin();
	if(num==0){
		layer.alert("报价应至少含有一个产品才可发起咨询", {icon : 2});return false;
	}
	var quoteContent = $("#quoteContent").val().trim();
	if(quoteContent.length == 0){
		$("#quoteContent").val("咨询报价（系统默认生成）");
	}
	/*if(quoteContent.length<2 || quoteContent.length>512){
		layer.alert("咨询内容长度应该在2-512个字符之间", 2);
		return false;
	}*/
	$.ajax({
		async : false,
		url : './addQuoteConsultSave.do',
		data : {"content":$("#quoteContent").val().trim(),"quoteorderId":quoteorderId,"quoteorderNo":quoteorderNo,"beforeParams":$("input[name='beforeParams']").val()},
		type : "POST",
		dataType : "json",
		success : function(data) {
			layer.alert(data.message, {
				icon : (data.code == 0 ? 1 : 2)
			}, function(index) {
				/*if (parent.layer != undefined) {
					parent.layer.close(index);
				}*/
				if(data.code == 0){
					layer.close(index);
					$("#quoteContent").val("");
					location.reload();
				}
			});
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

function applyValidQuoteorder(quoteorderId,taskId,isZeroPrice){
	checkLogin();
	var msg = "";
	if(isZeroPrice == 1){
		msg = "有产品价格为0,是否确认操作";
	}else{
		msg = "您是否确认申请审核该订单？";
	}
	var formToken = $("input[name='formToken']").val();
	layer.confirm(msg, {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editApplyValidQuoteorder.do",
				data: {'quoteorderId':quoteorderId,'taskId':taskId,'formToken':formToken},
				dataType:'json',
				success: function(data){
					console.log(data);
					if (data.code == 0) {
						layer.alert(data.message);
						if(data.status == 1){
							window.location.href = page_url + '/order/quote/getQuoteDetail.do?viewType=3&quoteorderId='+data.data.quoteorderId;
						}else{
							window.location.reload();
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
		}, function(){
	});
}

$(function(){
	$("select[name='province']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		var quoteorderId = $("#quoteorderId").val()
		if(regionId != 0){
			var province = $(this).find("option:selected").text();
			var salesArea = province;
			$.ajax({
				async : false,
				url : './updateQuoteTerminal.do',
				data : {"salesAreaId":regionId,"salesArea":salesArea,"quoteorderId":quoteorderId},
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
	});
})

$(function(){
	$("select[name='city']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		var quoteorderId = $("#quoteorderId").val()
		if(regionId != 0){
			var province = $("select[name='province']").find("option:selected").text();
			var city = $(this).find("option:selected").text();
			var salesArea = province+" "+city;
			$.ajax({
				async : false,
				url : './updateQuoteTerminal.do',
				data : {"salesAreaId":regionId,"salesArea":salesArea,"quoteorderId":quoteorderId},
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
	});
})

$(function(){
	$("select[name='zone']").change(function(){
		checkLogin();
		var regionId = $(this).val();
		var quoteorderId = $("#quoteorderId").val()
		if(regionId != 0){
			var province = $("select[name='province']").find("option:selected").text();
			var city = $("select[name='city']").find("option:selected").text();
			var zone = $(this).find("option:selected").text();
			var salesArea = province+" "+city+" "+zone;
			$.ajax({
				async : false,
				url : './updateQuoteTerminal.do',
				data : {"salesAreaId":regionId,"salesArea":salesArea,"quoteorderId":quoteorderId},
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
	});
})
