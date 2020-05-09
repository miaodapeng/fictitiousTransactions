$(function(){
	$("#submit").click(function(){
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		$("select").removeClass("errorbor");
		$("textarea").removeClass("errorbor");
		if($("#receiveTime").val()==''){
			warnTips("receiveTime","商机时间不允许为空");
			return  false;
		}else{
			delWarnTips("receiveTime");
		}
		var source=$('input:radio[name="source"]:checked').val();
		
		if(source==undefined||source==""){
			//$('input:radio[name="source"]').parent("ul").siblings("div").css("display","");
			$("#source").css("display","");
			return false;
		}else{
			$("#source").css("display","none");
		}
		var communication=$('input:radio[name="communication"]:checked').val();
		if(communication==undefined||communication==""){
			//$('input:radio[name="communication"]').parent("ul").siblings("div").css("display","");
			$("#communication").css("display","");
			return false;
		}else{
			$("#communication").css("display","none");
		}
//		if($("#content").val()==""){
//			$('#content1').css("display","");
//			$("#content").addClass("errorbor");
//			return false;
//		}else{
//			$('#content1').css("display","none");
//			$("#content").removeClass("errorbor");
//		}
		if($('#content').val()==''){
			warnTips("content","询价产品不允许为空");
			return false;
		}
		if($("#content").val().length>512){
			warnTips("content","询价产品内容不允许超过512字符");
			return false;
		}
//		if($("#goodsCategory").val()==''){
//			$('#goodsCategory1').css("display","");
//			$("#goodsCategory").addClass("errorbor");
//			return false;
//		}else{
//			$('#goodsCategory1').css("display","none");
//			$("#goodsCategory").removeClass("errorbor");
//		}
		if($('#goodsCategory').val()==''){
			warnTips("goodsCategory","产品分类不允许为空");
			return false;
		}else{
			delWarnTips("goodsCategory");
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
		if($("#name_1").val()!='' && $("i").css("display")=='none'){
			return false;
		}
		if($('#goodsName').val()!=''&&$('#goodsName').val().length>100){
			warnTips("goodsName","产品名称不允许超过100字符");
			return false;
		}else{
			delWarnTips("goodsName");
		}
		if($('#traderName').val()!=''&&$('#traderName').val().length>256){
			warnTips("traderName","客户名称不允许超过256字符");
			return false;
		}else{
			delWarnTips("traderName");
		}

	//	as Bert to 2019002022
/*
        if($('#province option:checked').val() == 0){
            warnTips("text","省份不可以为空!");
            return false;
        }

        if($('#city option:checked').val() == 0){
            warnTips("text","城市不可以为空!");
            return false;
        }

        if($('#zone option:checked').val( ) == 0){
            warnTips("text","县区不可以为空!");
            return false;
        }
*/



		if($('#traderContactName').val()!=''&&$('#traderContactName').val().length>256){
			warnTips("traderContactName","联系人名称不允许超过256字符");
			return false;
		}else{
			delWarnTips("traderContactName");
		}
		var mobileReg = /^1\d{10}$|^$/;
		if($('#mobile').val()!=''&&!mobileReg.test($('#mobile').val())){
			warnTips("mobile","手机号格式错误");
			return  false;
		}else{
			delWarnTips("mobile");
		}
		if($('#mobile').val()!=''&&$('#mobile').val().length>16){
			warnTips("mobile","手机号不允许超过16字符");
			return false;
		}else{
			delWarnTips("mobile");
		}
		var telephoneReg = /^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$|^$/;
		if($('#telephone').val()!=''&&!telephoneReg.test($('#telephone').val())){
			warnTips("telephone","电话格式错误");
			return  false;
		}else{
			delWarnTips("telephone");
		}
		if($('#telephone').val()!=''&&$('#telephone').val().length>32){
			warnTips("telephone","电话不允许超过32字符");
			return false;
		}else{
			delWarnTips("telephone");
		}
		if($('#otherContact').val()!=''&&$('#otherContact').val().length>64){
			warnTips("otherContact","其他联系方式不允许超过64字符");
			return false;
		}else{
			delWarnTips("otherContact");
		}
		if($('#userId').val()==''){
			warnTips("userId","分配销售不允许为空");
			return false;
		}else{
			delWarnTips("userId");
		}
		if($("#comments").val()!='' && $("#comments").val().length > 128){
			warnTips("comments","备注长度不允许超过128字符");
			return  false;
		}else{
			delWarnTips("comments");
		}
		
	})
});
function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg'&& strExtension != 'gif' && strExtension != 'png' && strExtension != 'pdf' && strExtension != 'doc' && strExtension != 'docx' && strExtension != 'xls' && strExtension != 'xlsx') {
		$('#upload1').css("display","");
		return false;
	}else{
		$('#upload1').css("display","none");
	}
	  var fileSize = 0;
	  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;      
	  if (isIE && !obj.files) {     
	     var filePath = obj.value;      
	     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  
	     var file = fileSystem.GetFile (filePath);        
	     fileSize = file.Size;     
	  }else { 
	     fileSize = obj.files[0].size;   
	  } 
	  fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
	  if(fileSize>2048){
		  //$("#name_"+num ).val(oldName);
		  //$("#uri_"+num ).val("");
		  //$('#upload2').css("display","");
		  //$("#uri_"+num ).siblings("i").css("display","none");
		  layer.alert("图片文件大小应为2MB以内",{ icon: 2 });
	    return false;
	  }else{
		 // $('#upload2').css("display","none");
	  }
	  $("#img_icon_" + num).attr("class", "iconloading mt5").show();
		$("#img_view_" + num).hide();
		$("#img_del_" + num).hide();
		var domain = $("#domain").val();
	$.ajaxFileUpload({
		url : page_url + '/fileUpload/ajaxFileUpload.do', //用于文件上传的服务器端请求地址
		secureuri : false, //一般设置为false
		fileElementId : $(obj).attr("id"), //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值   //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
		dataType : 'json',//返回值类型 一般设置为json
		complete : function() {//只要完成即执行，最后执行
		},
		//服务器成功响应处理函数
		success : function(data) {
			if (data.code == 0) {
				$("#name_"+num ).val(oldName);
				$("#uri_"+num ).val(data.filePath+"/"+data.fileName);
				$("#img_icon_" + num).attr("class", "iconsuccesss mt5").show();
				$("#img_view_" + num).attr("href", 'http://' + domain + data.filePath+"/"+data.fileName).show();
				$("#img_del_" + num).show();
			} else {
				layer.alert(data.message);
			}
		},
		//服务器响应失败处理函数
		error : function(data, status, e) {
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}else{
				layer.alert(data.responseText);
			}
			
		}
	});
	
}

function del(num){
	checkLogin();
	index = layer.confirm("您是否确认该操作？", {
		  btn: ['确定','取消'] //按钮
		}, function(){
		$("#img_icon_" + num).hide();
		$("#img_view_" + num).hide();
		$("#img_del_" + num).hide();
		$("#name_1").val("");
		$("#uri_1").val("")
		layer.close(index);
		}, function(){
		});
	
}


function addQuoteInit(traderId,bussinessChanceId,traderCustomerId){
	checkLogin();
	//验证客户信息是否被禁用
	$.ajax({
		type: "POST",
		url: page_url+"/order/quote/getTraderCustomerStatus.do",
		data: {'traderCustomerId':traderCustomerId},
		dataType:'json',
		success: function(data){
			if(data.code==0){
				//验证商机情况信息是否完整
				var bussinessLevelStrHide = $("#bussinessLevelStrHide").val();
				var bussinessStageStrHide = $("#bussinessStageStrHide").val();
				var enquiryTypeStrHide = $("#enquiryTypeStrHide").val();
				var orderRateStrHide = $("#orderRateStrHide").val();
				var amountHide = $("#amountHide").val();
				var orderTimeHide = $("#orderTimeHide").val();
				if(bussinessLevelStrHide!="" && bussinessStageStrHide!="" && enquiryTypeStrHide!="" && orderRateStrHide!="" && amountHide!="" && orderTimeHide!="" ){
					$("#addQuoteDiv").attr('tabtitle','{"num":"addQuote'+traderId+bussinessChanceId+'","link":"./order/quote/addQuote.do?traderId='+traderId+'&bussinessChanceId='+bussinessChanceId+'","title":"新增报价"}');
					$("#addQuoteDiv").click();
				}else{
					layer.alert("请先补充商机情况信息",{ icon: 2 });
				}
			}else{
				layer.alert(data.message,{ icon: 2 });
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
	
}
