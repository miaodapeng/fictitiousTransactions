$(function() {
	$("#orgName").focus();
	$('#orgform').submit(function() {
		checkLogin();
		var url = $("input[name='orgId']").val()*1 > 0 ? './saveeditorg.do' : './saveaddorg.do'
		jQuery.ajax({
			url:url,
			data:$('#orgform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var orgName = $("#orgName").val();
				var orgNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,32}$/;
				if(orgName == ""){
					warnTips("orgName","部门名称不允许为空");
					return false;
				}
				if(orgName.length < 1 || orgName.length > 32){
					warnTips("orgName","部门名称长度不允许超过32个字符");
					return false;
				}
				if(!orgNameReg.test(orgName)){
					warnTips("orgName","部门名称不允许使用特殊字符");
					return false;
				}
				
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