$(function(){
	$("input[name='roleId']").click(function(){
		//角色集合
		var roleIds = '';
		$.each($("input[name='roleId']:checked"),function(i,n){
			roleIds += $(this).val()+',';
		});
		$("input[name='roleIds']").val(roleIds);
	});
	
	//角色集合
	var roleIds = '';
	$.each($("input[name='roleId']:checked"),function(i,n){
		roleIds += $(this).val()+',';
	});
	$("input[name='roleIds']").val(roleIds);
	
	$('#myform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveeditmanager.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				if($("#companyId").val() == 0){
					warnTips("companyId","分公司不允许为空");
					return  false;
				}
				//员工姓名
				var realName = $("input[name='realName']").val();
				var realNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.]{2,16}$/;
				var realMessage = "员工姓名不允许使用特殊字符";
				if(realName == ''){
					warnTips("realName","员工姓名不允许为空");
					return  false;
				}
				if(realName.length < 2 || realName.length > 16){
					warnTips("realName","员工姓名长度应该在2-16个字符之间");
					return false;
				}
				if(!realNameReg.test(realName)){
					warnTips("realName",realMessage);
					return  false;
				}
				//用户名
				var username = $("input[name='username']").val();
				var usernameReg = /^[a-zA-Z0-9\.]{2,32}$/;
				var usernameMessage = "用户名不允许使用汉字和特殊字符";
				if(username == ''){
					warnTips("username","用户名不允许为空");
					return  false;
				}
				if(username.length < 2 || username.length > 32){
					warnTips("username","用户名长度应该在2-32个字符之间");
					return false;
				}
				if(!usernameReg.test(username)){
					warnTips("username",usernameMessage);
					return  false;
				}
				//密码
				var userId = $("input[name='userId']").val();
				var password = $("input[name='password']").val();
				var repassword = $("input[name='repassword']").val();
				var passwordReg = /^[a-zA-Z0-9]{6,16}$/;
				var passwordMessage = "密码由6-16位英文或数字组成";
				var repasswordMessage = "密码不一致";
				if(password != "" || repassword != ''){
					if(!passwordReg.test(password)){
						warnTips("password",passwordMessage);
						return  false;
					}
					
					if(password != repassword){
						warnTips("repassword",repasswordMessage);
						return  false;
					}
				}
					
				if($("input[name='roleId']:checked").length <= 0){
					warnTips("roleDiv","角色不能为空");
					return  false;
				}
				
				var flag = false;
				$.ajax({
					type: "POST",
					url: page_url+'/system/user/getuser.do',
					data: {'username':username,'companyId':$("#companyId").val(),'userId':$("input[name='userId']").val()},
					dataType:'json',
					async : false,
					success: function(data){
						if(data.code != 0){
							layer.alert(data.message);
							return false;
						}else{
							flag = true;
						}
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
				return flag;
			},
			success:function(data)
			{
				refreshPageList(data);
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