$(function(){
	$("#addAfteCapitalSaleSkForm").submit(function(){
		clearErroeMes();//清除錯誤提示信息
		var traderMode = $("#traderModeRad:checked").val();
		if(traderMode == undefined || traderMode == 0 || traderMode.length == 0){
			warn2Tips("traderMode","请选择交易方式");//文本框ID和提示用语
			return false;
		}
		$("#traderMode").val($("#traderModeRad:checked").val());
		
		var amount = $("#amount").val().trim();
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if (amount.length == 0) {
			warn2Tips("amount","交易金额不允许为空");//文本框ID和提示用语
			return false;
		} else if(amount.length>0 && !reg.test(amount)){
			warn2Tips("amount","交易金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(amount)>300000000){
			warn2Tips("amount","交易金额不允许超过三亿");//文本框ID和提示用语
			return false;
		}
		
		var payer = $("#payer").val();
		if(payer.length == 0 || payer.length>128){
			warn2Tips("payer","交易名称长度应该在0-128个字符之间");
			return false;
		}
		
		var comments = $("#comments").val();
		if(comments!="" && comments.length>512){
			warn2Tips("comments","交易备注长度应该在0-512个字符之间");
			return false;
		}
		checkLogin();
		$.ajax({
			url:page_url+'/finance/capitalbill/saveAddCapitalBill.do',
			data:$('#addAfteCapitalSaleSkForm').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data){
				if (data.code == 0) {
//					$("#close-layer").click();
					/*layer.alert(data.message, { icon: 1 },
						function (index) {
							layer.close(index);
						}
					);*/
					parent.location.reload();
				} else {
					layer.alert(data.message, { icon: 2 },
						function (index) {
							layer.close(index);
						}
					);
				}
			},error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		return false;
	})
});