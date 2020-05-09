$(function(){
	$("#myform").submit(function(){
		checkLogin();
		if($("#traderContactId").val() == 0){
			layer.alert("联系人不允许为空");
			return false;
		}
		var mobile = $("#traderContactId").find("option:selected").attr("title");
		var mobileReg = /^1\d{10}$|^$/;
		if(mobile == ""){
			layer.alert("联系人手机号码不允许为空");
			return false;
		}
		if(!mobileReg.test(mobile)){
			layer.alert("联系人手机格式错误");
			return false;
		}
		if($("#traderAddressId").val() == 0){
			layer.alert("联系地址不允许为空");
			return false;
		}
	});
});
