function btnSub(relatedIdList){
	checkLogin();
	clear2ErroeMes();
	var logisticsId = $("#logisticsId").val();
	if (logisticsId.length == 0) {
		warn2Tips("logisticsId", "快递公司不允许为空！");// 文本框ID和提示用语
		return false;
	}
	
	var logisticsNo = $("#logisticsNo").val();
	if (logisticsNo.length == 0) {
		warn2Tips("logisticsNo", "快递单号不允许为空！");// 文本框ID和提示用语
		return false;
	}
	if (logisticsNo.length > 64) {
		warn2Tips("logisticsNo", "快递单号长度应该在6-64个字符之间");// 文本框ID和提示用语
		return false;
	}
	var reg = /^[A-Za-z0-9]+$/;
	if(!reg.test(logisticsNo)){
		warn2Tips("logisticsNo", "快递单号只允许数字和字母组合");// 文本框ID和提示用语
		return false;
	}
	var logisticsComments = $("#logisticsComments").val();
	if (logisticsComments.length > 1024) {
		warn2Tips("logisticsComments","备注不允许超过1024个字符");//文本框ID和提示用语
		return false;
	}
	
//	if(relatedIdList!=undefined && relatedIdList!=""){
//		$("#relatedIdArr").val(JSON.stringify(relatedIdList));
//	}
	$.ajax({
		type: "POST",
		url: "./saveExpress.do",
		data: $("#sendSaleInvoiceForm").serialize(),
		dataType:'json',
		success: function(data){
			refreshPageList(data);//刷新父页面列表数据
			/*if(data.code == 0){
				$("#searchSpan").click();
			}else{
				layer.msg(data.message);
			}*/
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}