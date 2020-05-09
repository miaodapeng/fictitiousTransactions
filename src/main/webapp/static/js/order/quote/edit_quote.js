function updateCustomer(){
	checkLogin();
	clearErroeMes();//清除錯誤提示信息
	
	$("#isPolicymaker").val($("input[name='isPolicymakerRad']:checked").val());//是否采购关键人
	$("#purchasingType").val($("input[name='purchasingTypeRad']:checked").val());//采购方式
	$("#paymentTerm").val($("input[name='paymentTermRad']:checked").val());//付款条件
	$("#purchasingTime").val($("input[name='purchasingTimeRad']:checked").val());//采购时间
	
	var traderContactInfo = $("#traderContactInfo").val();
	if (traderContactInfo.length==0) {
		warnTips("traderContactInfo","联系人不允许为空");//文本框ID和提示用语
		return false;
	}else{
		var obj = $("#traderContactInfo").val().split("!@#");
		$("#traderContactId").val(obj[0]);
		$("#traderContactName").val(obj[1]);
		$("#mobile").val(obj[2]);
		$("#telephone").val(obj[3]);
	}
	$("#traderAddressId").val($("#address :selected").attr("id"));
/*	var address = $("#address").val();
	if (address==undefined || address.length==0) {
		warnTips("address","联系地址不允许为空");//文本框ID和提示用语
		return false;
	}*/
	
	if ($("#isPolicymaker").val()=="") {
		warnTips("isPolicymaker","联系人情况不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#purchasingType").val()=="") {
		warnTips("purchasingType","采购方式不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#paymentTerm").val()=="") {
		warnTips("paymentTerm","付款方式不允许为空");//文本框ID和提示用语
		return false;
	}
	if ($("#purchasingTime").val()=="") {
		warnTips("purchasingTime","采购时间不允许为空");//文本框ID和提示用语
		return false;
	}
	var projectProgress = $("#projectProgress").val();
	if(projectProgress!="" && projectProgress.length>256){
		warnTips("projectProgress","项目进展情况长度应该在0-256个字符之间");//文本框ID和提示用语
		return false;
	}
	$.ajax({
		async:false,
		url:'./updateQuoteCustomer.do',
		data:$("#editQuoteForm").serialize(),
		type:"POST",
		dataType : "json",
		success:function(data){
			parent.location.reload();
			/*layer.alert(data.message, 
					{ icon: (data.code==0?1:2) },
					function () {
						$('#cancle').click();
						parent.location.reload();
					}
				);*/
			
			/*if(data.code==0){
				layer.alert('操作成功', {
					  closeBtn: 0,
					  btn: ['确定'] //按钮
					}, function(){
						window.location.href = page_url + '/order/quote/getQuoteDetail.do?quoteorderId='+data.data.quoteorderId;
					});
			}*/
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
	return false;
}