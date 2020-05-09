$(function(){
	$("#auditAccountPeriodReason :input").keydown(function (e) {
	      if (e.which == 13) {
	    	  btnSub();
	        return false;
	      }
	})
})
function btnSub() {
	checkLogin();
	var $form = $("#auditAccountPeriodReason");
	var auditReason = $("#auditReason").val();
	if (auditReason.length < 1 || auditReason.length > 128) {
		warn2Tips("auditReason", "审核原因长度应该在1-128个字符之间");// 文本框ID和提示用语
		return false;
	}
	var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
	if (!nameReg.test(auditReason)) {
		warn2Tips("auditReason", "审核原因不允许使用特殊字符");
		return false;
	}
	$.ajax({
		async : false,
		url : './editAccountPeriodAudit.do',
		data : $form.serialize(),
		type : "POST",
		dataType : "json",
		success : function(data) {
//			if(parent.layer!=undefined){
//				parent.layer.close(index);
//			}
//			if(data.code == 0){
//				parent.pagesContrlpages(false,true,true);
//			}else{
//				if(data.code==0){
//					layer.alert(data.message, 
//						{ icon: 2 },
//						function (index) {
//							layer.close(index);
//						}
//					);
//				}
//			}
			refreshPageList(data);
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
};