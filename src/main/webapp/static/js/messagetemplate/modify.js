$(function(){
	$("#myform").submit(function(){
		$(".warning").remove();
		
		if($("#templateType").val() == -1){
			warnTips("templateType","消息模板类型不允许为空");
			return  false;
		}
		if($("#title").val() == ""){
			warnTips("title","消息模板标题不允许为空");
			return  false;
		}
		if($("#title").val().length > 200){
			warnTips("title","消息模板标题长度不允许超过200个字符");
			return  false;
		}
		
		if($("#content").val() == ""){
			warnTips("content","消息模板内容不允许为空");
			return  false;
		}
		if($("#content").val().length > 10000){
			warnTips("content","消息模板内容不允许超过10000个字符");
			return  false;
		}
		return true;
	});
});