function btnSub(relatedId){
	checkLogin();
	$.ajax({
		async:false,
		url:'./saveOpenInvoceApply.do',
		data:{"relatedId":relatedId,"isAuto":$("input[name='isAuto']:checked").val(),"invoiceProperty":$("input[name='invoiceProperty']:checked").val(),"formToken":$("input[name='formToken']").val()},
		type:"POST",
		dataType : "json",
		beforeSend:function(){
			var isAuto = $("input[name='isAuto']:checked").val();
			if (isAuto.length==0) {
				warn2Tips("errTitle","开票方式不允许为空");//文本框ID和提示用语
				return false;
			}
			/*var invoiceProperty = $("input[name='invoiceProperty']:checked").val();
			if (invoiceProperty.length==0) {
				warn2Tips("err2Title","开票类型不允许为空");//文本框ID和提示用语
				return false;
			}*/
			var invoiceType = $("#invoiceType").val();
			if ((invoiceType == 429 || invoiceType == 682 || invoiceType == 684 || invoiceType == 686 || invoiceType == 972) && isAuto == 3) {
				layer.alert("电子发票无法开具增值税专用发票，请确认！")
				return false;
			}
		},
		success:function(data){
//			refreshPageList(data);
			if(data.code==0){
				layer.alert(data.message, 
					{ icon: 1 },
					function (index) {
						layer.close(index);
						var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
						parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
						parent.location.reload();
						$("#close-layer").click();
					}
				);
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

$(function(){
	document.onkeydown = function(e){
	    if(!e){
	     e = window.event;
	    }
	    if((e.keyCode || e.which) == 13){
	   	 	return false;
	    }
	}
})