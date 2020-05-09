$(function() {
	$("#supplierForm").submit(function(){
		checkLogin();
		$(".warning").remove();
		var comments = $("input[name='comments']").val();
		if(comments.length > 128){
			warnTips("comments","备注长度不允许超过128个字符");
			return false;
		}
		var brief = $("textarea[name='brief']").val();
		
		if(brief.length > 512){
			warnTips("brief","简介长度不允许超过512个字符");
			return false;
		}
		
		return true;
	});
	
});