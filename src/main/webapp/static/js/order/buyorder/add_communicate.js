$(function() {
	$("#submit").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		if($("#traderContactId").val() == 0){
			warnErrorTips("traderContactId","traderContactIdError","联系人不允许为空");
			return false;
		}
		if($("#begin").val() == ''){
			warnErrorTips("begin","timeError","开始时间不能为空");
			return false;
		}
		if($("#end").val() == ''){
			warnErrorTips("end","timeError","结束时间不能为空");
			return false;
		}
		var communicateMode=$('input:radio[name="communicateMode"]:checked').val();
		if(communicateMode==undefined||communicateMode==""){
			warnErrorTips("communicateMode","communicateError","沟通方式不允许为空");
			return false;
		}
		
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			$(".addtags").show();
			warnErrorTips("defineTag","tagError"," 沟通内容不允许为空");
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
		$.ajax({
			url:page_url+'/order/buyorder/saveaddcommunicate.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					var datas=data.data.split(",");
					if("wsx"==datas[1]){
						window.parent.location.href=page_url+"/order/buyorder/viewBuyorder.do?buyorderId="+datas[0];
					}else{
						window.parent.location.href=page_url+"/order/buyorder/viewBuyordersh.do?buyorderId="+datas[0];
					}
					
				}else{
					layer.alert(data.message);
				}
				
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

function changeDate(obj){
	checkLogin();
	$("#endHidden").val(obj.value)
}
