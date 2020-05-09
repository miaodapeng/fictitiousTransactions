$(function() {
	$("#customerForm").submit(function(){
		checkLogin();
		$(".warning").remove();
		var sb = true;
		$.each($(".bt"),function(i,n){
			var tab = $(n).find('.infor_name').find('lable').html();
			var ok = false;
			$.each($(n).find('.inputfloat').find("input[type='radio']"),function(o,p){
				if($(p).prop("checked")){
					ok = true;
				}
			});
			if(!ok){
				sb = false;
				$(n).find("div:last").append('<div class="warning">'+'请选择'+tab+'</div>')
				return false;
			}
		});
		
		var comments = $("#comments").val();
		if(comments.length > 128){
			warnTips("comments","备注长度不允许超过128个字符");
			return false;
		}
		var logisticsComments = $("#logisticsComments").val();
		if(logisticsComments.length > 128){
			warnTips("logisticsComments","物流信息备注长度不允许超过128个字符");
			return false;
		}
		var financeComments = $("#financeComments").val();
		if(financeComments.length > 128){
			warnTips("financeComments","财务信息备注长度不允许超过128个字符");
			return false;
		}
		var brief = $("#brief").val();
		if(brief.length > 512){
			warnTips("brief","简介长度不允许超过512个字符");
			return false;
		}
		
		var history = $("#history").val();
		if(history.length > 1024){
			warnTips("history","历史背景长度不允许超过1024个字符");
			return false;
		}
		
		var businessCondition = $("#businessCondition").val();
		if(businessCondition.length > 1024){
			warnTips("businessCondition","现在生意情况长度不允许超过1024个字符");
			return false;
		}
		
		var businessPlan = $("#businessPlan").val();
		if(businessPlan.length > 1024){
			warnTips("businessPlan","公司发展计划长度不允许超过1024个字符");
			return false;
		}
		
		var advantage = $("#advantage").val();
		if(advantage.length > 1024){
			warnTips("advantage","公司优势长度不允许超过1024个字符");
			return false;
		}
		
		var primalProblem = $("#primalProblem").val();
		if(primalProblem.length > 1024){
			warnTips("primalProblem","面临主要问题长度不允许超过1024个字符");
			return false;
		}
		
		return sb;
	});
});