function btnSub(relatedId){
	checkLogin();
	var isAuto = $("input[name='isAuto']:checked").val();
	if (isAuto.length==0) {
		warn2Tips("errTitle","开票方式不允许为空");//文本框ID和提示用语
		return false;
	}
	var comments = $("#comments").val().trim();
	if (comments.length==0) {
		warn2Tips("comments","提前开票备注不允许为空");//文本框ID和提示用语
		return false;
	}
	if (comments.length<2 || comments.length>512) {
		warn2Tips("comments","提前开票备注长度应该在2-512个字符之间");//文本框ID和提示用语
		return false;
	}
	var invoiceType = $("#invoiceType").val();
	if ((invoiceType == 429 || invoiceType == 682 || invoiceType == 684 || invoiceType == 686 || invoiceType == 972) && isAuto == 3) {
		layer.alert("电子发票无法开具增值税专用发票，请确认！")
		return false;
	}
	$.ajax({
		async:false,
		url:'./saveOpenInvoceApply.do',
		data:{"relatedId":relatedId,"isAuto":isAuto,"comments":comments,"isAdvance":1,"formToken":$("input[name='formToken']").val()},
		type:"POST",
		dataType : "json",
		success:function(data){
			if(data.code==0){
				$("#close-layer").click();
				var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
			    parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
				parent.location.reload();
				/*layer.alert(data.message, 
					{ icon: 1 },
					function (index) {
						layer.close(index);
						$("#close-layer").click();
						location.reload();
					}
				);*/
				return false;
			}else{
				layer.alert(data.message,{icon:2});
				warn2Tips("errorTitle",data.message);//文本框ID和提示用语
			}
		},error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})
}

//禁用Enter键表单自动提交  
function gosumit(relatedId) {
	alert(window.event.keyCode);
	if (window.event.keyCode == 13) {
		btnSub(relatedId);
		return false;
	}
}