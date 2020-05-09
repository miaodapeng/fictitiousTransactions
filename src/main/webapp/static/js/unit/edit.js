$(function() {
	var $form = $("#editUnitform");
	$form.submit(function() {
		checkLogin();
		$(".warning").remove();
		$.ajax({
			async:false,
			url:'./editUnit.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){  
				var unitName = $("#unitName").val().trim();
				var unitNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,16}$/;
				var unitNameEnReg = /^[A-Za-z]+$/;
				if (unitName.length==0) {
					warnTips("unitName","单位名称不能为空");//文本框ID和提示用语
					return false;
				}				
				if(unitName.length < 1 || unitName.length > 16){
					warnTips("unitName","单位名称不允许超过16个字符");
					return false;
				}
				if(!unitNameReg.test(unitName)){
					warnTips("unitName","单位名称不允许使用特殊字符");
					return false;
				}				
				var unitNameEn = $("#unitNameEn").val().trim();
				if(unitNameEn.length != 0 && (unitNameEn.length < 1 || unitNameEn.length > 16)){
					warnTips("unitNameEn","英文单位名称不允许超过16个字符");
					return false;
				}				
				if(unitNameEn.length != 0 && !unitNameEnReg.test(unitNameEn)){
					warnTips("unitNameEn","英文单位名称只允许使用英文字符");
					return false;
				}				
				var sort = $("#sort").val().trim();
				if (sort.length==0) {
					warnTips("sort","排序编号不能为空");//文本框ID和提示用语
					return false;
				}else{
					var re = /^[0-9]{1,4}$/;
					if(!re.test(sort)){
						warnTips("sort","排序编号必须是0-9999之间的正整数");//文本框ID和提示用语
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