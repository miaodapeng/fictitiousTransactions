$(function() {
	$("#addRoleForm").submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveRole.do',
			data:$('#addRoleForm').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var roleName = $("#roleName").val();
				if (roleName.length == 0) {
					warnTips("roleName","角色名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (roleName.length<1 || roleName.length >20) {
					warnTips("roleName","角色名称为1-20个汉字长度");//文本框ID和提示用语
					return false;
				}
				var roleNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if(!roleNameReg.test(roleName)){
					warnTips("roleName","角色名称不允许使用特殊字符");
					return false;
				}
				if($("input[name='platformId']:checked").length <= 0){
					warnTips("platform","应用系统为空，无法提交。");
					return  false;
				}
			},
			success:function(data){
				refreshPageList(data);//刷新父页面列表数据
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
