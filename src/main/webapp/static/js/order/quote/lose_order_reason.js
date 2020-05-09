$(function() {
	var $form = $("#followOrderForm");
	$form.submit(function() {
		checkLogin();
		$.ajax({
			async:false,
			url:'./editLoseOrderStatus.do',
			data:$form.serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function(){
				var followOrderStatusComments = $("#followOrderStatusComments").val();
				if (followOrderStatusComments.length==0) {
					warnTips("followOrderStatusComments","失单原因不能为空");//文本框ID和提示用语
					return false;
				}
				if (followOrderStatusComments.length<1 || followOrderStatusComments.length >30) {
					warnTips("followOrderStatusComments","失单原因长度应该在1-30个字符之间");//文本框ID和提示用语
					return false;
				}
				/*var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
				if(!nameReg.test(followOrderStatusComments)){
					warnTips("followOrderStatusComments","失单原因不允许使用特殊字符");
					return false;
				}*/
			},
			success:function(data){
				parent.location.reload();
				/*layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function() {
					if (parent.layer != undefined) {
						parent.layer.close(index);
					}
					parent.location.reload();
				});*/
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