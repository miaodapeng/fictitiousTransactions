function btnSub(invoiceApplyId,yyValidStatus){
	checkLogin();
	var comments = $("#comments").val();
	if (comments.length > 512) {
		warn2Tips("comments", "审核不通过原因最大允许512位字符");// 文本框ID和提示用语
		return false;
	}
	$.ajax({
		async : false,
		url : './saveYyOpenInvoiceAudit.do',
		data : {"invoiceApplyId":invoiceApplyId,"yyValidStatus":yyValidStatus,"comments":comments},
		type : "POST",
		dataType : "json",
		success : function(data) {
			refreshPageList(data);
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
}