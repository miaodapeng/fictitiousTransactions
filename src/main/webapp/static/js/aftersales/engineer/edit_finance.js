$(document).ready(function(){
	$('#myform').submit(function() {
		jQuery.ajax({
			url:'./saveeditfinance.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				checkLogin();
				$(".warning").remove();
				$("select").removeClass("errorbor");
				$("input").removeClass("errorbor");
				
				if($("#bank").val() == ""){
					warnTips("bank","开户银行不允许为空");
					return false;
				}
				if($("#bank").val().length>256){
					warnTips("bank","开户银行长度不能超过256字符");
					return false;
				}
				
				if($("#bankAccount").val() ==''){
					warnTips("bankAccount","银行帐号不允许为空");
					return false;
				}
				if($("#bankAccount").val()!=''&&$("#bankAccount").val().length>32){
					warnTips("bankAccount","银行帐号长度不能超过32字符");
					return false;
				}
				
				if($("#bankCode").val()!=''&&$("#bankCode").val().length>32){
					warnTips("bankCode","开户行支付联行号长度不能超过32字符");
					return false;
				}
			},
			success:function(data)
			{
				refreshPageList(data);
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
