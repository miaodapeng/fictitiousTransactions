$(function(){
	
	$("#myform").submit(function(){
		checkLogin();
		if($("#statusComments").val()==''){
			$('#statusComments').siblings('div').css("display","");
			return  false;
		}else{
			$('#statusComments').siblings('div').css("display","none");
		}
		if($("#closedComments").val()==''){
			warnTips("closedComments","备注不允许为空");
			return  false;
		}else{
			delWarnTips("closedComments");
		}
		if($('#closedComments').val().length>128){
			warnTips("closedComments","备注不能大于128字符");
			return false;
		}else{
			delWarnTips("closedComments");
		}
		$.ajax({
			url:page_url+'/order/bussinesschance/closeSreviceBussnessChance.do',
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

