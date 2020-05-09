$(function(){
	$("#addAfteCapitalPayForm").submit(function(){
		var traderMode = $("#traderModeRad:checked").val();
		if(traderMode == undefined || traderMode == 0 || traderMode.length == 0){
			warnTips("traderMode","请选择交易方式");//文本框ID和提示用语
			return false;
		}
		$("#traderMode").val($("#traderModeRad:checked").val());
		checkLogin();
		$.ajax({
			url:page_url+'/finance/capitalbill/saveAddAfterCapitalBill.do',
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