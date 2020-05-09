$(function() {
	var $form = $("#addActionform");
	$form.submit(function() {
		var flag = $(".addElement #isMenu_y").is(":checked");
		flag == true ? $(".addElement input[name='isMenu']").val(1) : $(".addElement input[name='isMenu']").val(0);
		checkLogin();
		$.ajax({
			async:false,
			url : './saveAction.do',
			data: $form.serialize(),
			type: "POST",
			dataType : "json",
			beforeSend:function(){
				var platformId = $("#platformId").val();
				if (platformId == '0') {
					warnTips("platformId","系统名称不能为空");//文本框ID和提示用语
					return false;
				}
				var actiongroupId = $("#actiongroupId").val().trim();
				if (actiongroupId.length==0) {
					warnTips("actiongroupId","请选择节点分组");//文本框ID和提示用语
					return false;
				}
				var controllerName = $("#controllerName").val().trim();
				if (controllerName.length==0) {
					warnTips("controllerName","控制器名称不能为空");//文本框ID和提示用语
					return false;
				}
				var actionName = $("#actionName").val().trim();
				if (actionName.length==0) {
					warnTips("actionName","功能名称不能为空");//文本框ID和提示用语
					return false;
				}
				var moduleName = $("#moduleName").val().trim();
				if (moduleName.length==0) {
					warnTips("moduleName","模块名称不能为空");//文本框ID和提示用语
					return false;
				}
				var actionDesc = $("#actionDesc").val().trim();
				if (actionDesc.length==0) {
					warnTips("actionDesc","功能描述不能为空");//文本框ID和提示用语
					return false;
				}
				var sort = $("#sort").val().trim();
				if (sort.length==0) {
					warnTips("sort","排序编号不能为空");//文本框ID和提示用语
					return false;
				}else{
					var re = /^[0-9]+$/ ;
					if(!re.test(sort)){
						warnTips("sort","排序编号必须为正整数");//文本框ID和提示用语
						return false;
					}
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
		})
		return false;
	});
});