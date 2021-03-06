$(function() {
	var traderType = $("#traderType").val();
	$("#submit").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
//		售后对象的验证
		if($("#afterSalesTraderId").val() == 0 && traderType == 1){
			warnErrorTips("afterSalesTraderId","afterSalesTraderIdError","售后对象不允许为空");
			return false;
		}
		if($("#afterSalesTraderId").val() == -1 && traderType == 1){
			warnErrorTips("afterSalesTraderId","afterSalesTraderIdError","请先添加售后对象售后对象");
			return false;
		}
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
		
		//2018-08-09 暂时注释掉旧沟通内容的验证
		/**
		if($("input[name='tagId']").length == 0 && $("input[name='tagName']").length == 0){
			$(".addtags").show();
			warnErrorTips("defineTag","tagError"," 沟通内容不允许为空");
			return false;
		}
		**/
		var contactContent = $("#contactContent").val();
		if(contactContent.length == 0){
			warnErrorTips("contactContent","contactContentError","沟通内容不允许为空");//文本域提示用语
			return false;
		}
		if(contactContent.length > 256){
			warnErrorTips("contactContent","contactContentError","沟通内容不允许超过256个字符");//文本域提示用语
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
			url:page_url+'/aftersales/order/saveaddcommunicate.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					layerPFF();
					//window.parent.location.reload();				
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
