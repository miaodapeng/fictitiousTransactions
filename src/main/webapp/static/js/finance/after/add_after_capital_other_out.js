$(function(){
	//如果付款方式是银行，就展示
	if($('input[name="traderMode"]:checked').val() == 521){
		$("#payTypeName").removeAttr("hidden"); 
	}
	$("#addAfteCapitalPayForm").submit(function(){
		var amount = $("#amount").val().trim();
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if (amount.length == 0) {
			warnTips("amount","交易金额不允许为空");//文本框ID和提示用语
			return false;
		} else if(amount.length>0 && !reg.test(amount)){
			warnTips("amount","交易金额输入错误！仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
			return false;
		}else if(Number(amount)>300000000){
			warnTips("amount","交易金额不允许超过三亿");//文本框ID和提示用语
			return false;
		}else if(Number(amount) == 0){
			warnTips("amount","交易金额必须大于零");//文本框ID和提示用语
			return false;
		}
		checkLogin();
		$.ajax({
			url:page_url+'/finance/capitalbill/saveAddCapitalBill.do',
			data:$('#addAfteCapitalPayForm').serialize(),
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