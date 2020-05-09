$(function() {
	
	$(".table-largest span").click(function(){
		checkLogin();
		$("#comments").val($(this).text());
	})
	
	$("#myform").submit(function(){
		checkLogin();
		if($("#province").val() == 0){
			$("#province").addClass("errorbor");
			$("#province").parent('div').siblings("div").find(".pr").css("display","");
			return false;
		}else{
			$("#province").removeClass("errorbor");
			$("#province").parent('div').siblings("div").find(".pr").css("display","none");
		}
		if($("#city").val() == 0){
			$("#city").addClass("errorbor");
			$("#city").parent('div').siblings("div").find(".ci").css("display","");
			return false;
		}else{
			$("#city").removeClass("errorbor");
			$("#city").parent('div').siblings("div").find(".ci").css("display","none");
		}
		if($("#zone").val()==0&&$("#zone")[0].options.length>1){
			$("#zone").addClass("errorbor");
			$("#zone").parent('div').siblings("div").find(".zo").css("display","");
			return false;
		}else{
			$("#zone").removeClass("errorbor");
			$("#zone").parent('div').siblings("div").find(".zo").css("display","none");
		}
		//var pattern = new RegExp("[`~!@$^&*=|{}':;'\\[\\].<>/?~！@￥……&*——|{}【】‘；：”“'。、？]");
		if($("#address").val()==''){
			warnTips("address","地址不允许为空");
			return  false;
		}else{
			delWarnTips("address");
		}
//		if(pattern.test($("#address").val())){
//			warnTips("address","地址不能包含特殊字符");
//			return  false;
//		}else{
//			delWarnTips("address");
//		}
		if($("#address").val().length >128){
			warnTips("address","地址长度不能超过128个字符");
			return  false;
		}else{
			delWarnTips("address");
		}
		var re= /^[0-9][0-9]{5}$/;
		if($("#zipCode").val()!='' && !re.test($("#zipCode").val())){
			warnTips("zipCode","邮编格式不正确");
			return  false;
		}else{
			delWarnTips("zipCode");
		}
		if($("#comments").val()!='' && $("#comments").val().length > 128){
			warnTips("commentsError","备注长度不能超过128字符");
			return  false;
		}else{
			delWarnTips("commentsError");
		}
		$.ajax({
			url:page_url+'/trader/customer/saveAddress.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					window.parent.location.reload();
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
		
	})
	
});
