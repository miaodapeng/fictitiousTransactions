$(function(){
	$("#submit").click(function(){
		checkLogin();
		if($("#receiveTime").val()==''){
			warnTips("receiveTime","商机时间不允许为空");
			return  false;
		}else{
			delWarnTips("receiveTime");
		}
		var communication=$('input:radio[name="communication"]:checked').val();
		if(communication==undefined||communication==""){
			$("#communication").css("display","");
			return false;
		}else{
			$("#communication").css("display","none");
			
		}
		if($("#content").val()==""){
			$('#content1').css("display","");
			$("#content").addClass("errorbor");
			return false;
		}else{
			$('#content1').css("display","none");
			$("#content").removeClass("errorbor");
		}
		if($("#content").val().length>512){
			$('#content2').css("display","");
			$("#content").addClass("errorbor");
			return false;
		}else{
			$('#content2').css("display","none");
			$("#content").removeClass("errorbor");
		}
		if($("#goodsCategory").val()==''){
			$('#goodsCategory1').css("display","");
			$("#goodsCategory").addClass("errorbor");
			return false;
		}else{
			$('#goodsCategory1').css("display","none");
			$("#goodsCategory").removeClass("errorbor");
		}
		if($('#goodsBrand').val()!=''&&$('#goodsBrand').val().length>32){
			warnTips("goodsBrand","产品品牌不允许超过32字符");
			return false;
		}else{
			delWarnTips("goodsBrand");
		}
//		var nameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,32}$/;
//		if($('#goodsBrand').val()!='' && !nameReg.test($('#goodsBrand').val())){
//			warnTips("goodsBrand","产品品牌不允许使用特殊字符");
//			return false;
//		}else{
//			delWarnTips("goodsBrand");
//		}
		if($('#goodsName').val()!=''&&$('#goodsName').val().length>100){
			warnTips("goodsName","产品名称不允许超过100字符");
			$("#goodsName").addClass("errorbor");
			return false;
		}else{
			delWarnTips("goodsName");
		}
		if($("#traderContactId").val()==''){
			$('#traderContactId1').css("display","");
			$("#traderContactId").addClass("errorbor");
			return false;
		}else{
			$('#traderContactId1').css("display","none");
			$("#traderContactId").removeClass("errorbor");
		}
		
		
	})
});


