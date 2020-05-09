function search() {
	checkLogin();
	$("#search").submit();
}

function alerttest(){
	checkLogin();
	layer.alert('这功能没做，别点了', {icon: 1});
}

// 切换快递，改变相应的费用
function changeFree(param, regionId){
	// 选中快递公司的id
	var logisticsId = $(param).val();
	checkLogin();
	$.ajax({
		async : false,
		url : page_url + '/logistics/filedelivery/changeDeliveryFree.do',
		data : {
			"logisticsId" : logisticsId,
			"regionId":regionId
		},
		type : "POST",
		dataType : "json",
		
		success : function(data) {
			if(data && data.code == 0){
				$("#amount").val(data.data.deliverFree);
			}
		},
		error:function(data){
			layer.alert("当前操作无权限")
		}
	})
}

function editExpress(fileDeliveryId,expressId,expressDetailId){
	checkLogin();
	location.href=page_url+"/logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId="+fileDeliveryId+"&expressId="+expressId+"&expressDetailId="+expressDetailId;
}

function auditPass(fileDeliveryId){
	checkLogin();
	$.ajax({
		async : false,
		url : page_url + '/logistics/filedelivery/editVerifyStatusPass.do',
		data : {
			"fileDeliveryId" : fileDeliveryId,
			"beforeParams":fileDeliveryId
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert(data.message, { icon : 1},function(){
					goUrl('/logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId='+fileDeliveryId);
				});
				
			}else{
				layer.alert(data.message, { icon : 2},function(){
					goUrl('/logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId='+fileDeliveryId);
				});
				
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

$("#addExpressForm").submit(function(){
	checkLogin();
	var sendType = $("#sendType").val();
	var relatedId = $("input[name='relatedId']").val();
	var reg1 = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var re = /^[0-9]+$/;
	if(sendType == '489'){
		var logisticsId = $("input[name='logisticsId']:checked").val();
		var logisticsNo = $("input[name='logisticsNo']").val();
		var logisticsName = $("input[name='logisticsId']:checked").attr("alt");
		var num = $("input[name='num']").val();
		var amount = $("input[name='amount']").val();
		var logisticsComments = $("input[name='logisticsComments']").val();
		var deliveryStatus = $("input[name='deliveryStatus']").val();
		var companyId = $("#companyId").val();
		if(companyId=="1"){
			if(logisticsName!="顺丰速运" && logisticsName!="中通快递"){
				if (logisticsNo == undefined || logisticsNo == "") {
					warn2Tips("logisticsNo", "快递单号不允许为空");
					return false;
				}
				var reg = /^[A-Za-z0-9]+$/;
				if(!reg.test(logisticsNo)){
					warn2Tips("logisticsNo", "物流单号只允许数字和字母组合");// 文本框ID和提示用语
					return false;
				}
				if (logisticsNo.length > 64 ){
					warn2Tips("logisticsNo", "快递单号不允许超过64个字符");
					return false;
				}
			}
		}else{
			if (logisticsNo == undefined || logisticsNo == "") {
				warn2Tips("logisticsNo", "快递单号不允许为空");
				return false;
			}
			var reg = /^[A-Za-z0-9]+$/;
			if(!reg.test(logisticsNo)){
				warn2Tips("logisticsNo", "物流单号只允许数字和字母组合");// 文本框ID和提示用语
				return false;
			}
			if (logisticsNo.length > 64 ){
				warn2Tips("logisticsNo", "快递单号不允许超过64个字符");
				return false;
			}
		}
		if (num == undefined || num == "") {
			warn2Tips("num", "计重数量不允许为空");
			return false;
		}
		if(Number(num)>10000){
			warn2Tips("num","计重数量不允许超过一万");//文本框ID和提示用语
			return false;
		}
		if(num=="0" || !re.test(num)){
			warn2Tips("num","计重数量必须为正整数");//文本框ID和提示用语
			return false;
		}
		if (amount == undefined || amount == "") {
			warn2Tips("amount", "费用不允许为空");
			return false;
		}
		if(Number(amount)>10000000){
			warn2Tips("amount","费用不允许超过一千万");//文本框ID和提示用语
			return false;
		}
		if(amount.length>0 && !reg1.test(amount)){
			warn2Tips("amount","费用输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}
		if (logisticsComments.length > 1024 ){
			warn2Tips("logisticsComments", "物流备注不允许超过1024个字符");
			return false;
		}
		
	}
})

function saveExpress(sendType){
	checkLogin();
	var relatedId = $("input[name='relatedId']").val();
	if(sendType == '489'){
		var logisticsId = $("input[name='logisticsId']:checked").val();
		var logisticsNo = $("input[name='logisticsNo']").val();
		var num = $("input[name='num']").val();
		var amount = $("input[name='amount']").val();
		var logisticsComments = $("input[name='logisticsComments']").val();
		var deliveryStatus = $("input[name='deliveryStatus']").val();
		var logisticsName = $("input[name='logisticsId']:checked").attr("alt");
		var companyId = $("#companyId").val();
		if(companyId=="1"){
			if(logisticsName!="顺丰速运" && logisticsName!="中通快递"){
				if (logisticsNo == undefined || logisticsNo == "") {
					warn2Tips("logisticsNo", "物流单号不允许为空");
					return false;
				}
				if (logisticsNo.length <= 32 ){
					warn2Tips("logisticsNo", "物流单号不允许超过32个字符");
					return false;
				}
			}
		}else{
			if (logisticsNo == undefined || logisticsNo == "") {
				warn2Tips("logisticsNo", "物流单号不允许为空");
				return false;
			}
			if (logisticsNo.length <= 32 ){
				warn2Tips("logisticsNo", "物流单号不允许超过32个字符");
				return false;
			}
		}
		if (num == undefined || num == "") {
			warn2Tips("num", "计重数量不允许为空");
			return false;
		}
		
		if (amount == undefined || amount == "") {
			warn2Tips("amount", "费用不允许为空");
			return false;
		}
		$.ajax({
			async : false,
			url : page_url + '/logistics/filedelivery/saveExpress.do',
			data : {
				"logisticsId" :logisticsId,
				"relatedId" : relatedId,
				"logisticsNo" : logisticsNo,
				"num" : num,
				"amount" : amount,
				"logisticsComments" : logisticsComments,
				"deliveryStatus" : deliveryStatus
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert(data.message, { icon : 1});
				}else{
					layer.alert(data.message, { icon : 2});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}

		})
	}else{
		var deliveryStatus = $("input[name='deliveryStatus']:checked").val();
		$.ajax({
			async : false,
			url : page_url + '/logistics/filedelivery/saveExpress.do',
			data : {
				"relatedId" : relatedId,
				"deliveryStatus" : deliveryStatus
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					layer.alert(data.message, { icon : 1});
				}else{
					layer.alert(data.message, { icon : 2});
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	}
}

function applyValidSaleorder(fileDeliveryId,taskId){
	checkLogin();
	var formToken = $("input[name='formToken']").val();
	layer.confirm("您是否确认申请审核该订单？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				type: "POST",
				url: "./editApplyValidFileDelivery.do",
				data: {'fileDeliveryId':fileDeliveryId,'taskId':taskId,'formToken':formToken},
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
$(function(){
	 var companyId = $("#companyId").val();
	  if(companyId=="1"){
		  if($("input[name='logisticsId']:checked").attr("alt")=="顺丰速运" || $("input[name='logisticsId']:checked").attr("alt")=="中通快递"){
			  $("#lno").hide();
		  }
		  $("input[name='logisticsId']").click(function(){
			  if($(this).attr("alt")=='顺丰速运' || $(this).attr("alt")=='中通快递'){
				  $("#lno").hide();
			  }else{
				  $("#lno").show();
			  }
		  })
	  }
	 
})