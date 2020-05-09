$(function() {
	$('#auditInvoiceForm').submit(function() {
		checkLogin();
		$.ajax({
			url : "./saveInvoiceAudit.do",
			data : $('#auditInvoiceForm').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function() {  
				var validComments = $("#validComments").val();
				if(validComments == ""){
					warnTips("validComments","审核备注不允许为空");
					return false;
				}
				if(validComments.length < 1 || validComments.length > 60){
					warnTips("validComments","审核备注长度不允许超过60个字符");
					return false;
				}
				/*var orgNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,32}$/;
				if(!orgNameReg.test(orgName)){
					warnTips("orgName","部门名称不允许使用特殊字符");
					return false;
				}*/
			},
			success:function(data) {
				/*if(parent.$("#hideInvoiceNo").val().length == 0){
					parent.$("#invoiceNo").val("");
				} else {
					parent.$("#invoiceNo").val(parent.$("#hideInvoiceNo").val());
				}*/
				parent.$("#invoiceNo").val("");
				parent.$("#hideInvoiceNo").val("");
				parent.$("#searchForm").attr("action", page_url + "/finance/invoice/buyInvoiceAuditList.do");
				parent.$("#searchSpan").click();
//				refreshPageList(data);
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