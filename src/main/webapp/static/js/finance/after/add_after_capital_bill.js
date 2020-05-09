$(function(){
	$("#addAfterCapitalBillForm").submit(function(){
		clearErroeMes();//清除錯誤提示信息
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
		}
		
		var comments = $("#comments").val();
		if(comments!="" && comments.length>512){
			warnTips("comments","交易备注长度应该在0-512个字符之间");
			return false;
		}
		var path = "";
		if($("#billType").val() == "1"){//收款
			path = "/finance/capitalbill/saveAddCapitalBill.do";
		}else if($("#billType").val() == "2"){//付款
			path = "/finance/capitalbill/saveRefundCapitalBill.do";
		}
		checkLogin();
		$.ajax({
			url:page_url + path,
			data:$('#addAfterCapitalBillForm').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				if (data.code == 0) {
					window.parent.location.reload();
				} else {
					layer.alert(data.message);
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