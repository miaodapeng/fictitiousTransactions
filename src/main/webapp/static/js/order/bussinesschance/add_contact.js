$(function() {
	
	$("#myform").submit(function(){
		checkLogin();
		//姓名
		var realName = $("#name").val();
		var realNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.]{2,16}$/;
		var realMessage = "联系人姓名不允许使用特殊字符";
		if(realName == ''){
			warnTips("name","联系人姓名不允许为空");
			return  false;
		}else{
			delWarnTips("name");
		}
		if(realName.length < 2 || realName.length > 20){
			warnTips("name","联系人姓名长度应该在2-20个字之间");
			return false;
		}else{
			delWarnTips("name");
		}
		/*if(!realNameReg.test(realName)){
			warnTips("name",realMessage);
			return  false;
		}else{
			delWarnTips("name");
		}*/
		
		//电话
		var telephone = $("#telephone").val();
		var telephoneMessage = "联系方式不允许为空";
		
		if(telephone == '' ){
			warnTips("telephone",telephoneMessage);
			return  false;
		}
		if(telephone.length >50 ){
			warnTips("telephone","联系方式不允许超过50个字符");
			return  false;
		}
		$("#name", window.parent.document).val($("#name").val());
		$("#telephone", window.parent.document).val($("#telephone").val());
		$("#traderContactId", window.parent.document).append("<option value='1'>" + $("#name").val()+'|'+$("#telephone").val() + "</option>");
		$('#close-layer').click();
	})
	
});
