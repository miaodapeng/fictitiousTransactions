$(function(){
	$("#myform").submit(function(){
		checkLogin();
		$(".warning").remove();
		jQuery.ajax({
			url:'./savemodifypassword.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				//密码
				var oldpassword = $("input[name='oldpassword']").val();
				var password = $("input[name='password']").val();
				var repassword = $("input[name='repassword']").val();
				var passwordReg = /^[a-zA-Z0-9]{6,16}$/;
				var passwordMessage = "密码由6-16位英文或数字组成";
				var repasswordMessage = "密码不一致";
				if(oldpassword == ""){
					warnTips("oldpassword","旧密码不允许为空");
					return  false;
				}
				if(password == ""){
					warnTips("password","新密码不允许为空");
					return  false;
				}
				if(!passwordReg.test(password)){
					warnTips("password",passwordMessage);
					return  false;
				}
				
				if(password == oldpassword){
					warnTips("password","新密码不允许和旧密码一致");
					return  false;
				}
				
				if(repassword == ""){
					warnTips("repassword","确认新密码不允许为空");
					return  false;
				}
				
				if(password != repassword){
					warnTips("repassword",repasswordMessage);
					return  false;
				}
			},
			success:function(data)
			{
				if(data.code == 0){
					layer.alert("密码已修改，下次登录请用新密码登录！", {
						closeBtn: 0,
						btn: ['确定'] //按钮
					}, function(){
						parent.layer.closeAll();
					});
				}else{
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
	});
});