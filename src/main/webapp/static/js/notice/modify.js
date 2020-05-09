$(function(){
	$("#myform").submit(function(){
		$(".warning").remove();
		
		if($("#noticeCategory").val() == 0){
			warnTips("noticeCategory","公告类型不允许为空");
			return  false;
		}
		if($("#title").val() == ""){
			warnTips("title","公告标题不允许为空");
			return  false;
		}
		if($("#title").val().length > 200){
			warnTips("title","公告标题长度不允许超过200个字符");
			return  false;
		}
		
		if(UE.getEditor('content').getContentTxt() == ""){
			warnTips("content","公告内容不允许为空");
			return  false;
		}
		if(UE.getEditor('content').getContentTxt().length > 10000){
			warnTips("content","公告标题长度不允许超过10000个字符");
			return  false;
		}
		
		return true;
	});
});