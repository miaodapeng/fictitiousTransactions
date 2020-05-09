

var startTime = "",endTime = "";
$(function(){
	$("#sInvoiceNo").focus();
	startTime = $("#de_startTime").val();
	endTime = $("#de_endTime").val();
})
function resetPage(){
	reset();
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);
	$("#advanceValidStatus").val(0);
}
function checkedOnly(obj){
	if($(obj).is(":checked")){
		var n = 0;
		$("#list_table").find("input[type='checkbox'][name='checkName']").each(function(){
			if($(this).is(":checked")){
				n++;
			}else{
				return false;
			}
		});
		if($("#list_table").find("input[type='checkbox'][name='checkName']").length == n){
			$("#list_table").find("input[type='checkbox'][name='checkAllOpt']").prop("checked",true);
		}
	}else{
		$("#list_table").find("input[type='checkbox'][name='checkAllOpt']").prop("checked",false);
	}
}

function saveOpenInvoiceAudit(invoiceApplyId,status){
	layer.confirm("您是否确认审核通过？", {
		btn : [ '确定', '取消' ]
	}, function() {
		checkLogin();
		$.ajax({
			async : false,
			url : './saveOpenInvoiceAudit.do',
			data : {"invoiceApplyId":invoiceApplyId,"advanceValidStatus":status,"isAdvance":1},
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

function checkAllOpt(obj){
	if($(obj).is(":checked")){
		$("#list_table").find("input[type='checkbox'][name='checkName']").each(function(){
			$(this).prop("checked",true);
		});
	}else{
		$("#list_table").find("input[type='checkbox'][name='checkName']").each(function(){
			$(this).prop("checked",false);
		});
	}
}
/*$(function(){
	$("input[name='startTime']").val(getPreMonth(new Date()));
	$("input[name='endTime']").val(formatDate(new Date()));
});*/

function auditAdvanceInvoiceApply(validStatus){
	var invoiceApplyIdArr = [];
	$("#list_table").find("input[type='checkbox'][name='checkName']").each(function(){
		if($(this).is(":checked")){
			invoiceApplyIdArr.push($(this).val());
		}
	});
	if(invoiceApplyIdArr.length == 0){
		layer.alert("请选择需要审核的记录！", {icon: 2},
			function (index) {
				layer.close(index);
			}
		);
		return false;
	}
	layer.confirm("您是否确认审核通过？", {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			async : false,
			url : './saveOpenInvoiceAuditBatch.do',
			data : {"invoiceApplyIdArr":JSON.stringify(invoiceApplyIdArr),"advanceValidStatus":validStatus,"isAdvance":1},
			type : "POST",
			dataType : "json",
			success : function(data) {
				refreshNowPageList(data);
			}
		});
	});
}

function exportInvoiceAdvanceApplyList(){
	location.href = page_url + '/report/finance/exportInvoiceAdvanceApplyList.do?' + $("#search").serialize();
}