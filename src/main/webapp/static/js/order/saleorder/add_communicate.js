$(function() {
	$("#submit").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
/*		if($("#traderContactId").val() == 0){
			warnTips("traderContactId","联系人不允许为空");
			return false;
		}
		*/
		if($("#begin").val() == '' || $("#end").val() == ''){
			warnTips("end"," 沟通时间不允许为空");
			return false;
		}
		
		var communicateGoal=$('input:radio[name="communicateGoal"]:checked').val();
		if(communicateGoal==undefined||communicateGoal==""){
			$("#communicateGoal").css("display","");
			return false;
		}else{
			$("#communicateGoal").css("display","none");
		}
		
		var communicateMode=$('input:radio[name="communicateMode"]:checked').val();
		if(communicateMode==undefined||communicateMode==""){
			$("#communicateMode").css("display","");
			return false;
		}else{
			$("#communicateMode").css("display","none");
		}
		
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			warnTips("tag_show_ul"," 沟通内容不允许为空");
			return false;
		}
		var nextDate = $("input[name='nextDate']").val();
		if(nextDate.length != 0 && $("#begin").val()>=nextDate+' 23:59:59'){
			warnTips("nextDate"," 下次沟通时间不能在沟通时间之前");
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
		$.ajax({
			url:page_url+'/order/saleorder/saveCommunicate.do',
			data:$('#addCommunicate').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data){
				$('#cancle').click();
				parent.location.reload();
				/*
				layer.alert(data.message, {
					icon : (data.code == 0 ? 1 : 2)
				}, function() {
					$('#cancle').click();
					parent.location.reload();
				});*/
				
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
