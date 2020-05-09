$(function() {
	$('#myform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:'./saveaddcategory.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				var categoryName = $("#categoryName").val();
				if (categoryName.length==0) {
					warnTips("categoryName","分类名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (categoryName.length > 32) {
					warnTips("categoryName","分类名称不允许超过32个字符");//文本框ID和提示用语
					return false;
				}
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,32}$/;
				if(!nameReg.test(categoryName)){
					warnTips("categoryName","分类名称不允许使用特殊字符");
					return false;
				}
				
				var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
				var minAmount = $("#minAmount").val();
				if(minAmount.length>0){
					if(!reg.test(minAmount)){
						warnTips("minAmount","包邮最小起订金额仅允许使用数字，最多精确到小数后两位");//文本框ID和提示用语
					}else if(Number(minAmount)>10000000){
						warnTips("minAmount","包邮最小起订金额不允许超过一千万");//文本框ID和提示用语
					}
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