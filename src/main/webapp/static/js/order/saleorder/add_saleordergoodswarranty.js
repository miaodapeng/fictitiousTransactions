function uploadFile(obj,num){
	checkLogin();
	var imgPath = $(obj).val();
	if(imgPath == '' || imgPath == undefined){
		return false;
	}
	var oldName=imgPath.substr(imgPath.lastIndexOf('\\')+1);
	var domain = $("#domain").val();
	//判断上传文件的后缀名
	var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
	if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'pdf') {
		layer.alert("文件格式不正确");
		return;
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
		  layer.alert("上传附件不得超过2M");
	    return false;
	  }
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
				$("#img_icon_" + num).attr("class", "iconsuccesss ml7 mt3").show();
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
	var index = layer.confirm("确认删除", {
		  btn: ['确定','取消'] //按钮
		}, function(){
			$("#img_icon_" + num).hide();
			$("#img_view_" + num).hide();
			if(num == 1){
				$("#img_del_" + num).hide();
			}
			$("#name_"+ num).val("");
			$("#uri_"+ num).val("");
			$("#file_"+ num).val("");
			layer.close(index);
	}, function(){
	});
}

$(document).ready(function(){
	$('#myform').submit(function() {
		checkLogin();
		$(".warning").remove();
		$("select").removeClass("errorbor");
		$("input").removeClass("errorbor");
		$("textarea").removeClass("errorbor");
		
		var serviceNo = $("#serviceNo").val();
		if(serviceNo.length > 64){
			warnTips("serviceNo","服务单号长度不允许超过64个字符");
			return false;
		}
		
		var goodsSn = $("#goodsSn").val();
		if(goodsSn == ""){
			warnTips("goodsSn","设备系列号不允许为空");
			return false;
		}
		if(goodsSn.length > 64){
			warnTips("goodsSn","设备系列号长度不允许超过64个字符");
			return false;
		}
		
		var importantPartsSn = $("#importantPartsSn").val();
		if(importantPartsSn.length > 128){
			warnTips("importantPartsSn","重要配件系列号长度不允许超过128个字符");
			return false;
		}
		var terminalName = $("#terminalName").val();
		if(terminalName.length > 128){
			warnTips("terminalName","终端名称长度不允许超过128个字符");
			return false;
		}
		if($("select[name='province']").val() == 0){
			$("select[name='province']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">省份不允许为空</div>');
			return false;
		}
		if($("select[name='city']").val() == 0){
			$("select[name='city']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">地市不允许为空</div>');
			return false;
		}
		if($("select[name='zone']").val() == 0 && $("select[name='zone'] option").length > 1){
			$("select[name='zone']").addClass("errorbor");
			$("#zone").siblings('.warning').remove();
			$("#zone").after('<div class="warning">区县不允许为空</div>');
			return false;
		}
		
		var address = $("#address").val();
		if(address.length > 128){
			warnTips("address","终端地址长度不允许超过128个字符");
			return false;
		}
		
		var zipCodeRe= /^[1-9][0-9]{5}$/;
		var zipCode = $("#zipCode").val();
		if(zipCode != "" && !zipCodeRe.test(zipCode)){
			warnTips("zipCode","邮政编码格式不正确");
			return false;
		}
		
		var usedDepartment = $("#usedDepartment").val();
		if(usedDepartment.length > 64){
			warnTips("usedDepartment","使用科室长度不允许超过64个字符");
			return false;
		}
		
		var personLiable = $("#personLiable").val();
		if(personLiable == ""){
			warnTips("personLiable","负责人不允许为空");
			return false;
		}
		if(personLiable.length > 32){
			warnTips("personLiable","负责人长度不允许超过32个字符");
			return false;
		}
		
		var phone = $("#phone").val();
		var phoneRe = /^1\d{10}$|^(\d{3,4}-?)?\d{7,9}(-?\d{2,6})?$/;
		if(phone == ""){
			warnTips("phone","联系电话不允许为空");
			return false;
		}
		if(!phoneRe.test(phone)){
			warnTips("phone","联系电话格式不正确");
			return false;
		}
		
		var personLiableEquipment = $("#personLiableEquipment").val();
		if(personLiableEquipment.length > 32){
			warnTips("personLiableEquipment","设备科负责人长度不允许超过32个字符");
			return false;
		}
		
		var equipmentPhone = $("#equipmentPhone").val();
		if(equipmentPhone !="" && !phoneRe.test(equipmentPhone)){
			warnTips("equipmentPhone","联系电话格式不正确");
			return false;
		}
		
		var installAgency = $("#installAgency").val();
		if(installAgency.length > 64){
			warnTips("installAgency","装机机构长度不允许超过64个字符");
			return false;
		}
		
		var installPerson = $("#installPerson").val();
		if(installPerson.length > 32){
			warnTips("installPerson","装机人员长度不允许超过32个字符");
			return false;
		}
		
		var checkTime = $("#checkTime").val();
		if(checkTime == ""){
			warnTips("checkTime","安装验收日期不允许为空");
			return false;
		}
		
		var checkPerson = $("#checkPerson").val();
		if(checkPerson.length > 32){
			warnTips("checkPerson","验收人长度不允许超过32个字符");
			return false;
		}
		
		var dailyVolume = $("#dailyVolume").val();
		if(dailyVolume.length > 32){
			warnTips("dailyVolume","日标本量长度不允许超过32个字符");
			return false;
		}
		
		var comments = $("#comments").val();
		if(comments.length > 256){
			warnTips("comments","备注长度不允许超过256个字符");
			return false;
		}
		// 录保卡
		var uri_1 = $("#uri_1").val();
		if("" == uri_1)
		{
			warnTips("name_1","录保卡不允许为空");
			return false;
		}
		
		return true;
	});
});