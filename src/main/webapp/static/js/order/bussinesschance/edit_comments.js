$(function(){
	
	$("#myform").submit(function(){
		checkLogin();
		
		if($("#comments").val()==''){
			warnTips("comments","备注不允许为空");
			return  false;
		}else{
			delWarnTips("comments");
		}
		if($('#comments').val().length>128){
			warnTips("comments","备注不能大于128字符");
			return false;
		}else{
			delWarnTips("comments");
		}
		$.ajax({
			url:page_url+'/order/bussinesschance/saveSalesBussnessChanceComments.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{

				if(data.code==0){
					window.parent.location.reload();
				}else{
					layer.alert("操作失败！")
				}

				
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		return false;
	})
});

