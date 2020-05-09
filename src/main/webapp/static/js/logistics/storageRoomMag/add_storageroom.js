$(function(){
	$("#addStorageroomForm").submit(function() {
		var sb =true;
		var storageRoomName = $("#storageRoomName").val();
		if (storageRoomName.length == 0) {
			warnTips("storageRoomName","库房名称不能为空");
			return false;
		}
		if (storageRoomName.length<1 || storageRoomName.length >16) {
			warnTips("storageRoomName","库房名称为1-16个汉字长度");
			return false;
		}
		var storageRoomNameReg = /^[a-zA-Z0-9\u4e00-\u9fa5\.\(\)\,]{1,128}$/;
		if(!storageRoomNameReg.test(storageRoomName)){
			warnTips("storageRoomName","库房名称不允许使用特殊字符");
			return false;
		}
		var warehouseId = $("#warehouseId").val();
		if (warehouseId.length == 0) {
			warnTips("warehouseId","所属仓库允许为空");
			return false;
		}
		var comments = $("#comments").val();
		if (comments.length >128) {
			warnTips("comments","库房备注不允许超过128个字符");
			return false;
		}
		//判断输入的值是否是数字、小数
		function isN(s){
			var p=/^[+-]?\d{1,5}(?:\.\d{1,1})?$/;
		    return p.test(s);
		}
		function isNs(s){
			var p=/^[+]?\d{1,5}(?:\.\d{1,1})?$/;
		    return p.test(s);
		}
		function show(txt,warnId,endId){
			$("form :input").parents('li').find('.warning').remove();
			$("form :input").removeClass("errorbor");
			$("#"+warnId).siblings('.warning').remove();
			$("#"+endId).after('<div class="warning">'+txt+'</div>');
			$("#"+warnId).focus();
			$("#"+warnId).addClass("errorbor");
		}
		var temperatureMin = $("#temperatureMin").val();
		if(temperatureMin!=""){
			var warnId="temperatureMin";
			var txt="请填写正确的温度数字（范围在-100~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "wd";
			if(!isN(temperatureMin)){
				show(txt,warnId,endId);
				return false;
			}
		}
		var temperatureMax =  $.trim($("#temperatureMax").val());
		if((temperatureMin!="" && temperatureMax=="")||(temperatureMin=="" && temperatureMax!="")){
			var warnId="";
			if(temperatureMin==""){
				warnId ="temperatureMin";
			}else{
				warnId="temperatureMax";
			}
			var txt="库房温度不允许为空";
			var endId = "wd";
			show(txt,warnId,endId);
			return false;
		}
		if(temperatureMax!=""){
			var warnId="temperatureMax";
			var txt="请填写正确的温度数字（范围在-100~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "wd";
			if(!isN(temperatureMax)){
				show(txt,warnId,endId);
				return false;
			}
		}
		if(parseFloat(temperatureMin)>parseFloat(temperatureMax)){
			var warnId="temperatureMax";
			var txt="请填写正确的温度数字（范围在-100~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "wd";
			show(txt,warnId,endId);
			$("#temperatureMin").focus();
			$("#temperatureMin").addClass("errorbor");
			return false;
		}
		var humidityMin = $("#humidityMin").val();
		if(humidityMin!=""){
			var warnId="humidityMin";
			var txt="请填写正确的湿度数字（范围在0~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "sd";
			if(!isNs(humidityMin)){
				show(txt,warnId,endId);
				return false;
			}
		}
		
		var humidityMax = $("#humidityMax").val();
		if((humidityMin!="" && humidityMax=="")||(humidityMin=="" && humidityMax!="")){
			var warnId="";
			if(humidityMin==""){
				warnId="humidityMin";
			}else{
				 warnId="humidityMax";
			}
			var txt="库房最高湿度不允许为空";
			var endId = "sd";
			show(txt,warnId,endId);
			return false;
		}
		
		var humidityMax = $("#humidityMax").val();
		if(humidityMax!=""){
			var warnId="humidityMax";
			var txt="请填写正确的湿度数字（范围在0~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "sd";
			if( !isNs(humidityMax)){
				show(txt,warnId,endId);
				return false;
			}	
		}
		if(parseFloat(humidityMin)>parseFloat(humidityMax)){
			var warnId="humidityMax";
			var txt="请填写正确的湿度数字（范围在0~100之间，精确到小数点后1位，且后一数字需大于前一数字）";
			var endId = "sd";
			show(txt,warnId,endId);
			$("#humidityMin").focus();
			$("#humidityMin").addClass("errorbor");
			return false;
		}
		//库房是否存在
		$.ajax({
			type : "POST",
			url : page_url+"/warehouse/storageRoomMag/getStorageRoomByName.do",
			data : {"storageRoomName":storageRoomName,"warehouseId":warehouseId},
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.code == 0){
					//库房已经存在
					if(data.data.storageroomId != null 
							&& data.data.storageroomId !=undefined
							&& data.data.storageroomId >0 
							&& data.data.storageroomId != $("input[name='storageroom.storageroomId']").val()){
						sb = false;
						return false;
					}
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		if(!sb){
			warnTips("storageRoomName","同一仓库下库房名称不允许重复");
			return false;
		}
		return sb;
	});
})
