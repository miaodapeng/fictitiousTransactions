$(function() {
	var $form = $("#editBrandForm");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./editBrand.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){
				var brandName = $("#brandName").val();
				if (brandName.length==0) {
					warnTips("brandName","品牌名称不能为空");//文本框ID和提示用语
					return false;
				}
				if (brandName.length<1 || brandName.length >32) {
					warnTips("brandName","品牌名称不允许超过32个字符");//文本框ID和提示用语
					return false;
				}
				/*var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if(!nameReg.test(brandName)){
					warnTips("brandName","品牌名称不允许使用特殊字符");
					return false;
				}*/
			},
			success:function(data){
				if(data.code == 0 && data.status == -1){
					warnTips("brandName",data.message);
					return false;
				}else{
					refreshPageList(data);
				}
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