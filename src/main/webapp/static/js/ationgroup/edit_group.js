$(function() {
	$("#editActionGroupForm").submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./updateActionGroup.do',
			data:$('#editActionGroupForm').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var parentId = $("#parentId").val().trim();
				if (parentId.length==0) {
					warnTips("parentId","请选择部门分组");//文本框ID和提示用语
					return false;
				}
				var name = $("#name").val().trim();
				if (name.length==0) {
					warnTips("name","功能分组名称不能为空");//文本框ID和提示用语
					return false;
				}
				var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{2,128}$/;
				if(!nameReg.test(name)){
					warnTips("name","功能分组名称不允许使用特殊字符");
					return false;
				}
				if (name.length<4 || name.length >32) {
					warnTips("name","功能分组名称为4~32个汉字长度");//文本框ID和提示用语
					return false;
				}
				
				var orderNo = $("#orderNo").val().trim();
				if (orderNo.length==0) {
					warnTips("orderNo","排序编号不能为空");//文本框ID和提示用语
					return false;
				}else{
					var re = /^[0-9]+$/ ;
					if(!re.test(orderNo)){
						warnTips("orderNo","排序编号必须为正整数");//文本框ID和提示用语
						return false;
					}
				}
				if(parentId==$("#actiongroupId").val()){
					warnTips("parentId","部门分组不能是当前功能分组");//文本框ID和提示用语
					return false;
				}
				var iconStyle = $("#iconStyle").val().trim();
				if (iconStyle.length > 32) {
					warnTips("iconStyle","图标长度应该在0-32个字符之间");//文本框ID和提示用语
					return false;
				}else{
					var re = /^[A-Za-z0-9_-]*$/ ;
					if(!re.test(iconStyle)){
						warnTips("iconStyle","图标内容不允许存在特殊符号");//文本框ID和提示用语
						return false;
					}
				}
			},
			success:function(data){
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
		return false;
	});
});