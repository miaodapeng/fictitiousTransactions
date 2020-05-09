$(function() {
	// 回车事件
	document.onkeydown = function(e) {
		if (!e) {
			e = window.event;
		}
		if ((e.keyCode || e.which) == 13) {
			var no = $("#invoiceNo").val();
			if (no != '') {
				var arr = no.split(',');
				$("#invoiceNo").val(arr[3]);
				search();
			}
		}
	}
})

function resetInvoice() {
    $("form").find("input[type='text']").val('');
    $("#hideInvoiceNo").val("");
}

function search(){
	/*if ($("#invoiceNo").val() == "" || $("#invoiceNo").val().length != 8) {
		$("#invoiceNo").focus();
		$("#invoiceNo").addClass("errorbor");
		return false;
	} else {
		$("#invoiceNo").removeClass("errorbor");
		$("#hideInvoiceNo").val($("#invoiceNo").val());
	}*/

	$("#hideInvoiceNo").val($("#invoiceNo").val());
    // $("#searchForm").attr("action",	page_url + "/finance/invoice/buyInvoiceAuditList.do");
	$("#searchForm").submit();
}
// 点击发票号
function buyAuditDetail(invoiceNo, colorType, isEnable, type) {
	$("#hideInvoiceNo").val(invoiceNo);
	$("#searchForm").attr("action", page_url + "/finance/invoice/buyInvoiceAudit.do?colorType="	+ colorType + "&isEnable=" + isEnable + "&type=" + type);
	$("#searchForm").submit();
}

function selectChange(colorType, isEnable) {
	$("#searchForm").attr("action", 	page_url + "/finance/invoice/buyInvoiceAuditList.do?colorType="	+ colorType + "&isEnable=" + isEnable);
	$("#searchForm").submit();
}

function auditInvoice(invoiceId,invoiceNo,validStatus,colorType,isEnable,type){
	layer.confirm(validStatus==1?"确认审核通过？":"确认审核不通过？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			checkLogin();
			$("#passBtn").attr("disabled",true);
			$("#unPassBtn").attr("disabled",true);
			$.ajax({
				type: "GET",
				url: "./saveInvoiceAudit.do",
				data: {"invoiceId":invoiceId,"invoiceNo":invoiceNo,"validStatus":validStatus,"colorType":colorType,"isEnable":isEnable,"type":type,"formToken":$("input[name='formToken']").val()},
				dataType:'json',
				success: function(data){
//					refreshNowPageList(data);
					if(data.code == 0){
						$("#passBtn").removeAttr("disabled");$("#unPassBtn").removeAttr("disabled");
						$("#invoiceNo").val("");
						$("#hideInvoiceNo").val("");
						$("#searchForm").attr("action",page_url + "/finance/invoice/buyInvoiceAuditList.do");
						$("#searchForm").submit();
					}else{
						layer.alert(data.message, {
							icon : 2
						}, function(lay) {
							layer.close(lay);
						});
					}
				},error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
		}
	)
}