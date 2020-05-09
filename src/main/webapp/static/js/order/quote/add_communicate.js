$(function() {
	$("#submit").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		if($("#traderContactId").val() == 0){
			warnTips("traderContactId","联系人不允许为空");
			return false;
		}
		if($("#begin").val() == ''){
			warnTipsDate("timeErrorMsg","开始时间不允许为空");
			$("#begin").addClass("errorbor");
			return false;
		}
		if($("#end").val() == ''){
			warnTipsDate("timeErrorMsg","结束时间不允许为空");
			$("#end").addClass("errorbor");
			return false;
		}
		
		var communicateMode=$('input:radio[name="communicateMode"]:checked').val();
		if(communicateMode==undefined||communicateMode==""){
			$("#communicateMode").css("display","");
			return false;
		}else{
			$("#communicateMode").css("display","none");
		}
		
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			//warnTips("tag_show_ul"," 沟通内容不允许为空");
            layer.alert("沟通内容不允许为空")
			return false;
		}
		var nextDate = $("input[name='nextDate']").val();
		if(nextDate.length != 0 && $("#begin").val()>=(nextDate+" 23:59:59")){
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
		$.ajax({
			url:page_url+'/order/quote/saveCommunicate.do',
			data:$('#addCommunicate').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data){
				if(data.code == 0){
					$('#cancle').click();
					/*
					 * if(parent.layer!=undefined){
					 * parent.layer.close(index); }
					 */
					parent.location.reload();
				}else{
					layer.alert(data.message, { icon : 2});
				}
				/*layer.alert(data.message, {
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
