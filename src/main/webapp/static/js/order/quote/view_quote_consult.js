function replyQuoteConsult(quoteorderId,consultStatus){
	checkLogin();
	
	/*var updateStr = "";
	//循环验证哪一行数据需要保存
	$("input[name^='goodsCategoryUser_']").each(function(i){
		if($(this).val() == "y"){
			updateStr += i;
		}
	});*/
	
	var updateStr = "";
	//循环验证哪一行数据有改动
	$("input[name^='referenceCostPrice_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name^='referencePrice_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name^='referenceDeliveryCycle_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name='reportStatus']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name^='reportComments_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name^='registrationNumber_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	$("input[name^='supplierName_']").each(function(i){
		if($(this).val() != $(this).attr("alt")){
			updateStr += i;
		}
	});
	
	var flag = true;
	//参考成本
	var referenceCostPriceArr = new Array();
	$("input[name^='referenceCostPrice_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			referenceCostPriceArr[referenceCostPriceArr.length] = $(this).val();
			if(!vailNum(this)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	//参考价格（产品部门报价）
	var referencePriceArr = new Array();
	$("input[name^='referencePrice_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			referencePriceArr[referencePriceArr.length] = $(this).val();
			if(!vailNum(this)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	//参考货期（产品部门回复）
	var referenceDeliveryCycleArr = new Array();
	$("input[name^='referenceDeliveryCycle_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			referenceDeliveryCycleArr[referenceDeliveryCycleArr.length] = $(this).val();
			if(!vailStr(this,32)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	//报备结果0不需要报备 1等待报备 2成功 3失败
	var reportStatusArr = new Array();
	$("input[name='reportStatus']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			reportStatusArr[reportStatusArr.length] = $(this).val();
		}
	});
	
	//报备原因
	var reportCommentsArr = new Array();
	$("input[name^='reportComments_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			reportCommentsArr[reportCommentsArr.length] = $(this).val();
			if(!vailStr(this,64)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	//注册证号
	var registrationNumberArr = new Array();
	$("input[name^='registrationNumber_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			registrationNumberArr[registrationNumberArr.length] = $(this).val();
			if(!vailStr(this,256)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	//供应商名称
	var supplierNameArr = new Array();
	$("input[name^='supplierName_']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			supplierNameArr[supplierNameArr.length] = $(this).val();
			if(!vailStr(this,256)){
				flag = false;
				return flag;
			}
		}
	});
	if(flag==false){
		return false;
	}
	
	var quoteContent = $("#quoteContent").val().trim();
	if(quoteContent.length == 0){
		$("#quoteContent").val("答复报价（系统默认生成）");
	}else{
		if(!vailStr($("#quoteContent"),512)){
			flag = false;
			return flag;
		}
	}
	if(flag==false){
		return false;
	}
	
	var quoteorderConsultIdArr = new Array();
	$("input[name='quoteorderConsultId']").each(function(i){
		if(updateStr.indexOf(i) != -1){
			quoteorderConsultIdArr[quoteorderConsultIdArr.length] = $(this).val();
		}
	});
	waitWindowNew('no');
	$.ajax({
		async : false,
		url : './saveReplyQuoteConsult.do',
		data : {"content":$("#quoteContent").val(),"quoteorderId":quoteorderId,"beforeParams":$("input[name='beforeParams']").val(),"formToken":$("input[name='formToken']").val(),
				"referenceCostPriceArr":JSON.stringify(referenceCostPriceArr),"referencePriceArr":JSON.stringify(referencePriceArr),
				"referenceDeliveryCycleArr":JSON.stringify(referenceDeliveryCycleArr),"reportStatusArr":JSON.stringify(reportStatusArr),
				"reportCommentsArr":JSON.stringify(reportCommentsArr),"registrationNumberArr":JSON.stringify(registrationNumberArr),
				"supplierNameArr":JSON.stringify(supplierNameArr),"quoteorderConsultIdArr":JSON.stringify(quoteorderConsultIdArr),"consultStatus":consultStatus},
		type : "POST",
		dataType : "json",
		success : function(data) {
			layer.alert(data.message, {
				icon : (data.code == 0 ? 1 : 2)
			}, function() {
				if (parent.layer != undefined) {
					parent.layer.close(index);
				}
				location.reload();
			});

			$("#quoteContent").val("");
			location.reload();
			/*layer.alert(data.message, {	icon : (data.code == 0 ? 1 : 2)},
				function(index) {
					if(data.code == 0){
						$("#quoteContent").val("");
						location.reload();
					}else{
						layer.close(index);
					}
				}
			);*/
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

function editConsultStatus(quoteorderId,consultStatus){
	checkLogin();
	layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editConsultStatus.do",
				data: {'quoteorderId':quoteorderId,'consultStatus':consultStatus,"beforeParams":$("input[name='beforeParams']").val(),"formToken":$("input[name='formToken']").val()},
				dataType:'json',
				success: function(data){
					layer.alert(data.message, {
						icon : (data.code == 0 ? 1 : 2)
					}, function() {
						if (parent.layer != undefined) {
							parent.layer.close(index);
						}
						location.reload();
					});
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

function changeReport(num,index){
	checkLogin();
	$("#reportStatus"+index).val(num);
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
		layer.alert("价格输入格式错误！仅允许使用数字，最多精确到小数点后两位。", {
			icon : 2
		}, function(index) {
			$(obj).addClass("errorbor");
			layer.close(index);
			$(obj).focus();
		});
		return false;
	}else{
		$(obj).removeClass("errorbor");
		return true;
	}
}

function vailStr(obj,num){
	checkLogin();
	var str = $(obj).val().trim();
	if(str.length!=0 && str.length>num){
		layer.alert("内容长度应该在0-" + num + "个字符之间。", {
			icon : 2,
			yes : function(index) {
				$(obj).addClass("errorbor");
				layer.close(index);
				$(obj).focus();
			}
		});
		return false;
	}else{
		$(obj).removeClass("errorbor");
		return true;
	}
}