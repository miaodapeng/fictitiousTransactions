function search(type) {
	checkLogin();
	clearErroeMes();// 清除錯誤提示信息
	var traderName = encodeURI($("#searchTraderName").val());   
	if(type != 3){
		var traderType = $("input[name='traderType']").val();
	}else{
		var traderType = $("input[name='traderType']:checked").val();
	}
	
	/*
	 * if(searchTraderName==""){ warnTips("errorMes","请输入终端信息");//文本框ID和提示用语
	 * return false; }
	 */
	$("#terminalDiv")
			.attr(
					'layerParams',
					'{"width":"70%","height":"80%","title":"确认客户","link":"'
							+ page_url
							+ '/logistics/filedelivery/getTraderList.do?searchTraderName='
							+ traderName + '&traderType=' + traderType + '"}');
	$("#terminalDiv").click();
}

function agingSearchTrader() {
	checkLogin();
	$("#searchTraderName").val("");
	$("#showTraderInfo").find("input").each(function(i) {
		$(this).val("");
	});
	$("#showTraderInfo").hide();
	$("#traderNameLi").show();
}
function changeContact(obj) {
	checkLogin();
	var arr = $(obj).find("option:selected").text().split(' | ');
	$("#showTraderInfo").find("#traderContactName").val(arr[0]);
	$("#showTraderInfo").find("#mobile").val(arr[1]);
	$("#showTraderInfo").find("#telephone").val(arr[2]);
}
function changeDelievryType(obj) {
	checkLogin();
	var val = $(obj).val();
	if(val == 1){
		$("#wl").prop('checked', true);
		$("#cw").prop('checked', false);
	}else if(val == 2){
		$("#wl").prop('checked', false);
		$("#cw").prop('checked', true);
	}
}

function changeAddress(obj) {
	checkLogin();
	var arr = $(obj).find("option:selected").text().split(' | ');
	$("#showTraderInfo").find("#area").val(arr[0]);
	$("#showTraderInfo").find("#address").val(arr[1]);
}
$("#addFileDeliveryForm").submit(function(){
	checkLogin();
	$('#sub').attr("disabled",true);
	var traderType = $("input[name='traderType']:checked").val();
	var traderName = $("#traderName").val();
	var traderContact = $("#traderContactId").val();
	var traderAddress = $("#traderAddressId").val();
	var content = $("#content").val();
	var delievryType = $("input[name='delievryType']:checked").val();
	if(typeof(delievryType) == "undefined" || delievryType == ""){
		warn2Tips("delievryTypeWarn", "寄送文件类型不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	if (traderName == undefined || traderName == "") {
		warn2Tips("traderNameWarn", "客户不允许为空");
		if (!$('#searchTraderName').hasClass('errorbor')) {
			$('#searchTraderName').addClass('errorbor');
		}
		$('#sub').attr("disabled",false);
		return false;
	}
	if (traderContact == undefined || traderContact == ""
			|| traderContact == 'Value') {
		warn2Tips("traderContactId", "联系人不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	var traderContactName = $("#traderContactName").val();
	if(typeof(traderContactName) == "undefined" || traderContactName==""){
		warn2Tips("traderContactId", "联系人姓名不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	var mobile = $("#mobile").val();
	var telephone= $("#telephone").val();
	if((typeof(mobile) == "undefined" || mobile=="")&& (typeof(telephone) == "undefined" || telephone=="")){
		warn2Tips("traderContactId", "联系人联系方式不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	if (traderAddress == undefined || traderAddress == ""
			|| traderAddress == 'Value') {
		warn2Tips("traderAddressId", "地址不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	var area = $("#area").val();
	if(area == undefined || area == ""){
		warn2Tips("traderAddressId", "地区不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	var areas = area.trim().split(/\s+/);
	if(areas.length < 3){
		warn2Tips("traderAddressId", "地区没有达到三级，请到客户的“联系人/地址”维护信息");
		$('#sub').attr("disabled",false);
		return false;
	}
	var address = $("#address").val();
	if(address==""){
		warn2Tips("traderAddressId", "详细地址不能为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	if (content == undefined || content == "") {
		warn2Tips("content", "寄送内容不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
	if(content.length > 1024){
		warn2Tips("content","寄送内容不允许超过1024个字符");
		$('#sub').attr("disabled",false);
		return false;
	}
	var deliveryDept = $("input[name='deliveryDept']:checked").val();
	if(typeof(deliveryDept) == "undefined" || deliveryDept == ""){
		warn2Tips("deliveryDeptWarn", "承运部门不允许为空");
		$('#sub').attr("disabled",false);
		return false;
	}
})
function saveFileDelivery() {
	checkLogin();
	var formToken = $("input[name='formToken']").val();
	var traderType = $("input[name='traderType']:checked").val();
	var traderName = $("#traderName").val();
	var traderContact = $("#traderContactId").val();
	var traderAddress = $("#traderAddressId").val();
	var content = $("#content").val();
	if (traderName == undefined || traderName == "") {
		warnTips("traderNameWarn", "客户不允许为空");
		if (!$('#searchTraderName').hasClass('errorbor')) {
			$('#searchTraderName').addClass('errorbor');
		}
		return false;
	}
	if (traderContact == undefined || traderContact == ""
			|| traderContact == 'Value') {
		warn2Tips("traderContactId", "联系人不允许为空");
		return false;
	}
	if (traderAddress == undefined || traderAddress == ""
			|| traderAddress == 'Value') {
		warn2Tips("traderAddressId", "地址不允许为空");
		return false;
	}
	if (content == undefined || content == "") {
		warn2Tips("content", "寄送内容不允许为空");
		return false;
	}
	var sendType = $("input[name='sendType']:checked").val();
	var traderId = $("#traderId").val();
	var traderContactId = $("#traderContactId").find("option:selected").val();
	var traderContactName = $("#traderContactName").val();
	var mobile = $("#mobile").val();
	var telephone = $("#telephone").val();
	var traderAddressId = $("#traderAddressId").find("option:selected").val();
	var address = $("#address").val();
	var area = $("#area").val();
	var content = $("#content").val();

	$.ajax({
		async : false,
		url : page_url + '/logistics/filedelivery/saveFileDelivery.do',
		data : {
			"sendType" : sendType,
			"traderId" : traderId,
			"traderType" : traderType,
			"traderName" : traderName,
			"traderContactId" : traderContactId,
			"traderContactName" : traderContactName,
			"mobile" : mobile,
			"telephone" : telephone,
			"traderAddressId" : traderAddressId,
			"address" : address,
			"area" : area,
			"content" : content,
			"formToken":formToken
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.code == 0){
				layer.alert(data.message, { icon : 1});
			}else{
				layer.alert(data.message, { icon : 2});
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	})

}
