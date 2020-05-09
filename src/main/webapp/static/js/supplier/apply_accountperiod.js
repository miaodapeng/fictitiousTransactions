$(function() {
	if($("#message").val() != ""){
		layer.alert($("#message").val(), {
		  closeBtn: 0,
		  btn: ['确定'] //按钮
		}, function(){
			parent.layer.closeAll();
		});
	}
	
	$('#myform').submit(function() {
		checkLogin();
		$("input").removeClass("errorbor");
		$(".warning").remove();
		
		jQuery.ajax({
			url:'./saveaccountperiodapply.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var accountPeriodApply = $("#accountPeriodApply").val();
				var accountPeriodApplyReg = /^(([1-9]\d{0,11})|0)(\.\d{1,2})?$/;
				
				if(accountPeriodApply == ''){
					warnTips("accountPeriodApplyLabel","本次申请金额不允许为空");
					$("#accountPeriodApply").addClass("errorbor");
					return false;
				}
//				if(accountPeriodApply.length > 15){
//					warnTips("accountPeriodApplyLabel","本次申请金额长度不能超过15个字符");
//					$("#accountPeriodApply").addClass("errorbor");
//					return false;
//				}
				if(Number(accountPeriodApply)>300000000){
					warnTips("accountPeriodApplyLabel","本次申请金额不允许超过三亿");
					$("#accountPeriodApply").addClass("errorbor");
					return false;
				}
				if(!accountPeriodApplyReg.test(accountPeriodApply)){
					warnTips("accountPeriodApplyLabel","本次申请金额格式错误");
					$("#accountPeriodApply").addClass("errorbor");
					return false;
				}
				
				var accountPeriodDaysApply = $("#accountPeriodDaysApply").val();
				var accountPeriodDaysApplyReg = /^[1-9]\d{0,9}$/;
				if(accountPeriodDaysApply =='' ){
					warnTips("accountPeriodDaysApplyLabel","本次申请天数不允许为空");
					$("#accountPeriodDaysApply").addClass("errorbor");
					return false;
				}
				if(accountPeriodDaysApply.length > 2){
					warnTips("accountPeriodDaysApplyLabel","本次申请天数长度不能超过2个字符");
					$("#accountPeriodDaysApply").addClass("errorbor");
					return false;
				}
				if(!accountPeriodDaysApplyReg.test(accountPeriodDaysApply)){
					warnTips("accountPeriodDaysApplyLabel","本次申请天数格式错误");
					$("#accountPeriodDaysApply").addClass("errorbor");
					return false;
				}
				
				var periodAmount = $("input[name='accountPeriodNow']").val();
				var periodDay = $("input[name='accountPeriodDaysNow']").val();
				if(accountPeriodApply*1 == periodAmount*1 && accountPeriodDaysApply*1 == periodDay*1){
					warnTips("accountPeriodDaysApplyLabel","申请金额、天数与原额度、天数相同，请确认申请内容");
					return false;
				}
				
				var comments = $("#comments").val();
				if(comments ==""){
					warnTips("comments","申请原因不允许为空");
					return false;
				}
				if(comments.length > 128){
					warnTips("comments","申请原因长度不允许超过128个字符");
					return false;
				}
			},
			success:function(data)
			{
				if(data.code == 0){
					layer.alert(data.message,{
						  closeBtn: 0,
						  btn: ['确定'] //按钮
						}, function(){
							var st=data.data.split(",");
							var str=page_url+"/trader/supplier/getFinanceAndAptitude.do?traderId="+st[0]+"&traderSupplierId="+st[1];
							$("#finace").attr('href',str);
							window.parent.location.reload();
						});
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
	});
});