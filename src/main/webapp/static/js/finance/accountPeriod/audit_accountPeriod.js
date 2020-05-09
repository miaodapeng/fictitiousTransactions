function auditAccountPeriod(traderAccountPeriodApplyId){
	layer.confirm("您是否确认审核通过该账期申请？", {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
		checkLogin();
		$.ajax({
			async : false,
			url : './editAccountPeriodAudit.do',
			data : {"traderAccountPeriodApplyId" : traderAccountPeriodApplyId,"status":1},
			type : "POST",
			dataType : "json",
			success : function(data) {
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function() {
					/*if (parent.layer != undefined) {
						parent.layer.close(index);
					}*/
					location.reload();
				});
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
//		$("#auditAccountPeriodForm").submit();
	});
}