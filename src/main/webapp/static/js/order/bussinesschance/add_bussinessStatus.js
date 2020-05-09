$(function() {
	
	$("#myform").submit(function(){
		checkLogin();
		var bussinessLevel = $("#bussinessLevel").val();
		if(bussinessLevel == ''){
			warnErrorTips("bussinessLevel","bussinessLevelError","商机等级不能为空");
			return  false;
		}
		var bussinessStage = $("#bussinessStage").val();
		if(bussinessStage == ''){
			warnErrorTips("bussinessStage","bussinessStageError","商机阶段");
			return  false;
		}
		var enquiryType = $("#enquiryType").val();
		if(enquiryType == ''){
			warnErrorTips("enquiryType","enquiryTypeError","询价类型");
			return  false;
		}
		var type = $("#type").val();
		if(type == 1){
            var orderRate = $("#orderRate").val();
			if(orderRate == ''){
				warnErrorTips("orderRate","orderRateError","成单几率");
				return  false;
			}
			var amount = $("#amount").val().trim();
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			if (amount.length ==0) {
				warnTips("amount","交易金额不允许为空");//文本框ID和提示用语
				return false;
			} else if(amount.length>0 && !reg.test(amount)){
				warnTips("amount","交易金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
				return false;
			}else if(Number(amount)>100000000){
				warnTips("amount","交易金额不允许超过一亿");//文本框ID和提示用语
				return false;
			}
			var orderTime = $("#orderTimeStr").val();
			if(orderTime == ''){
				warnTips("orderTimeStr","预计成单时间不允许为空");
				return  false;
			}
		}
		var posturl = '/order/bussinesschance/saveAddBussinessStatus.do';
		$.ajax({
			url:page_url+posturl,
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					var bussinessChance=data.data;
					window.parent.location.href=page_url+"/order/bussinesschance/toSalesDetailPage.do?bussinessChanceId="
												+bussinessChance.bussinessChanceId+"&traderId="+bussinessChance.traderId;
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
	})
	
});
