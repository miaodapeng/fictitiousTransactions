function saveOpenInvoiceAudit(invoiceApplyId,status){
	layer.confirm("您是否确认审核不通过？", {
		btn : [ '确定', '取消' ]
	}, function() {
		checkLogin();
		$.ajax({
			async : true,
			url : './saveOpenInvoiceAudit.do',
			data : {"invoiceApplyId":invoiceApplyId,"validStatus":status},
			type : "POST",
			dataType : "json",
			success : function(data) {
				refreshNowPageList(data);
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	});
}
