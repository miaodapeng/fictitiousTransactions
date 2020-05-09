$(function() {
	var $form = $("#addPositForm");
	$form.submit(function() {
		checkLogin();
		var scop = $form.find("input[name='sysOptionId']:checked").val();
		$("#type").val(scop);
		$.ajax({
			async:false,
			url : './saveposition.do',
			data: $form.serialize(),
			type: "POST",
			dataType : "json",
			beforeSend:function(){
				var positionName = $("#positionName").val();
				if (positionName.length==0) {
					warnTips("positionName","职位名称不能为空");//文本框ID和提示用语
					return false;
				}
				if(positionName.length<1 || positionName.length >32){
					warnTips("positionName","职位名称为1-32个汉字长度");//文本框ID和提示用语
					return false;
				}
				var positionNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,40}$/;
				if(!positionNameReg.test(positionName)){
					warnTips("positionName","职位名称不允许使用特殊字符");
					return false;
				}
				
				var orgId = $("#orgId").val().trim();
				if (orgId==0 || orgId=="0") {
					warnTips("orgId","请选择所属部门");//文本框ID和提示用语
					return false;
				}
				/*var scopId = $("#type").val();
				if(scopId=="" || scopId.length==0){
					warnTips("type","请选择职位类型");//文本框ID和提示用语
					return false;
				}*/
				
				var level = $("#level").val();
				if(level=="" || level.length==0){
					warnTips("level","请选择职位级别");//文本框ID和提示用语
					return false;
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
	$("input[name='sysOptionId']").mouseup(function(){
		$(this).siblings('.warning').remove();
	});
});

