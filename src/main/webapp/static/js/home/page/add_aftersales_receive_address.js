$(function() {
	$("#myform").submit(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		if($("#province").val() == 0){
			warnErrorTips("province","proError","省份不允许为空");
			return false;
		}
		if($("#city").val() == 0){
			warnErrorTips("city","proError","地市不允许为空");
			return false;
		}
		if($("#zone").val()==0&&$("#zone")[0].options.length>1){
			warnErrorTips("zone","proError","区县不允许为空");
			return false;
		}
		if($("#address").val() == ''){
			warnErrorTips("address","addressError","发货地址不允许为空");//文本框ID和提示用语
			return false;
		}
		if($("#address").val().length >128){
			warnErrorTips("address","addressError","地址长度不允许超过128个字符");
			return  false;
		}
		if($("#contactName").val() == ''){
			warnErrorTips("contactName","contactNameError","发货联系人不允许为空");//文本框ID和提示用语
			return false;
		}
		if($("#contactName").val().length >64){
			warnErrorTips("contactName","contactNameError","发货联系人不允许超过64个字符");
			return  false;
		}
		if($("#mobile").val() == ''){
			warnErrorTips("mobile","mobileError","联系人手机不允许为空");//文本框ID和提示用语
			return false;
		}
		var mobileReg = /^1\d{10}$|^$/;
		if(!mobileReg.test($("#mobile").val())){
			warnErrorTips("mobile","mobileError","联系人手机格式错误");
			return  false;
		}
		var addressId = $("input[name='addressId']").val();
		var url ='';
		if(addressId == undefined || addressId == ''){
			url = '/home/page/saveAddAfterSalesReceiveAdderss.do';
		}else{
			url = '/home/page/saveEditAfterSalesReceiveAdderss.do';
		}
		$.ajax({
			url:page_url+url,
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					window.parent.location.reload();
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
		
	})
	
});
