$(function() {
	$('#storeform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveeditstore.do',
			data:$('#storeform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var name = $("#name").val();
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{2,128}$/;
				
				if(name == ""){
					warnTips("name","店铺名称不允许为空");
					return false;
				}
				if(name.length < 2 || name.length > 128){
					warnTips("name","店铺名称长度应该在2-128个字符之间");
					return false;
				}
				if(!nameReg.test(name)){
					warnTips("name","店铺名称不允许使用特殊字符");
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