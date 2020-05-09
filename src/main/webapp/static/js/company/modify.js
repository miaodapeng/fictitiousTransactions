$(function() {
	$("#companyName").focus();
	$('#companyform').submit(function() {
		jQuery.ajax({
			url:'./saveeditcompany.do',
			data:$('#companyform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var companyName = $("#companyName").val();
				var domain = $("#domain").val();
				var companyNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{2,128}$/;
				var domainReg = /^((http:\/\/)|(https:\/\/))?([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}/;
				
				if(companyName == ""){
					warnTips("companyName","公司名称不允许为空");
					return false;
				}
				if(companyName.length < 2 || companyName.length > 128){
					warnTips("companyName","公司名称长度应该在2-128个字符之间");
					return false;
				}
				if(!companyNameReg.test(companyName)){
					warnTips("companyName","公司名称不允许使用特殊字符");
					return false;
				}
				if(domain == ""){
					warnTips("domain","访问地址不允许为空");
					return false;
				}
				if(!domainReg.test(domain)){
					warnTips("domain","访问地址格式错误");
					return false;
				}
			},
			success:function(data)
			{
				if($("input[name='companyId']").val() > 0){
					refreshNowPageList(data);
				}else{
					refreshPageList(data);
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