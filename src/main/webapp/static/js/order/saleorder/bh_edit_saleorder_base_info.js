$(function(){
	$("#editBhSaleorderForm").submit(function(){
		checkLogin();
		clearErroeMes();//清除錯誤提示信息
		var prepareComments = $("#prepareComments").val();
		if(prepareComments!="" && prepareComments.length>256){
			warnTips("prepareComments","申请原因长度应该在0-256个字符之间");
			return false;
		}
		
		var marketingPlan = $("#marketingPlan").val();
		if(marketingPlan!="" && marketingPlan.length>256){
			warnTips("marketingPlan","后期营销计划长度应该在0-256个字符之间");
			return false;
		}
		$.ajax({
			url:page_url+'/order/saleorder/saveEditBhSaleorder.do',
			data:$('#editBhSaleorderForm').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				if (data.code == 0) {
					window.parent.location.reload();
				} else {
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