$(document).ready(function(){
	$('#myform').submit(function() {
		jQuery.ajax({
			url:'./saveeditscore.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				checkLogin();
				$(".warning").remove();
				$("input").removeClass("errorbor");
				var serviceScore = $("#serviceScore").val();
				var scoreReg = /^[1-9]$|^10$/;
				if(serviceScore == ""){
					warnTips("serviceScore","服务评分不允许为空");
					return false;
				}
				if(!scoreReg.test(serviceScore)){
					warnTips("serviceScore","服务评分格式错误");
					return false;
				}
				
				var skillScore = $("#skillScore").val();
				if(skillScore == ""){
					warnTips("skillScore","技能评分不允许为空");
					return false;
				}
				if(!scoreReg.test(skillScore)){
					warnTips("skillScore","技能评分格式错误");
					return false;
				}
				
				var scoreComments = $("#scoreComments").val();
				if(scoreComments.length > 256){
					warnErrorTips("scoreComments","scoreCommentsError","备注长度不允许超过256个字符");
					return false;
				}
			},
			success:function(data)
			{
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
			
		});
		return false;
	});
});
