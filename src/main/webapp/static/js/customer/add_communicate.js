$(function() {
	$("#form").submit(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		if($("#traderContactId").val() == 0){
			warnTips("traderContactId","联系人不允许为空");
			return false;
		}
		
		if($("#begin").val() == ''){
			$("#begin").addClass("errorbor");
			$("#end").after('<div class="warning">沟通开始时间不允许为空</div>');
			return false;
		}
		
		if($("#end").val() == ''){
			$("#end").addClass("errorbor");
			$("#end").after('<div class="warning">沟通结束时间不允许为空</div>');
			return false;
		}
		if($("input[name='communicateGoal']:checked").length == 0){
			$("#communicateGoalDiv").after('<div class="warning">沟通目的不允许为空</div>');
			return false;
		}
		if($("input[name='communicateMode']:checked").length == 0){
			$("#communicateModeDiv").after('<div class="warning">沟通方式不允许为空</div>');
			return false;
		}
		
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			$(".addtags").show();
			warnTips("tag_show_ul"," 沟通内容不允许为空");
			return false;
		}
		
		var nextDate = $("input[name='nextDate']").val();
		if(nextDate.length != 0 && $("#begin").val()>=nextDate+' 23:59:59'){
			warnTipsDate("nextDate","下次沟通时间不能在沟通时间之前");
			return false;
		}
		
		if($("#nextContactContent").val().length > 256){
			warnTips("nextContactContent"," 下次沟通内容长度不允许超过256个字符");
			return false;
		}
		if($("#comments").val().length > 128){
			warnTips("comments"," 备注长度不允许超过128个字符");
			return false;
		}
		return true;
	});
});
